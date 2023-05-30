package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.models.Game;
import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.CardStack;
import com.example.sixquiprendalbouy_cermak.models.players.Player;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Display {
    private Game game;
    private Stage stage;
    private int cardHeight = 115;
    private int cardWidth = 65;
    private ArrayList<CardView> playedCards;

    private CardView selectedCard;
    private CardSet currentSet;


    public Display(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.selectedCard = null;
        this.playedCards = new ArrayList<>(game.getPlayerNumber());
    }

    public void dsCreatePlayers() {
        VBox layout = new VBox();

        Label welcome = new Label("Welcome to Le Six Qui Prend, please choose the number of players and bots");
        Button start = new Button("Start game");


        Label playerLabel = new Label("Number of players :");
        Spinner<Integer> playerSpinner = new Spinner<>(1, 10, 1);
        HBox playerBox = new HBox(playerLabel, playerSpinner);

        Label botLabel = new Label("Number of bots :");
        Spinner<Integer> botSpinner = new Spinner<>(0, 10, 1);
        HBox botBox = new HBox(botLabel, botSpinner);

        start.setOnAction(e -> {
            if (playerSpinner.getValue() + botSpinner.getValue() > 10 || playerSpinner.getValue() + botSpinner.getValue() < 2) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("The total number of players must be between 2 and 10 ");
                alert.showAndWait();
            } else {
                game.createPlayers(playerSpinner.getValue(), botSpinner.getValue());
            }
        });

        layout.getChildren().addAll(welcome, playerBox, botBox, start);
        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.show();

    }

    public void playerTurn(Player player) {
        currentSet = player.getHand();

        VBox playedCardsBox = new VBox();
        if (playedCards != null) {
            for (int t = 0; t < playedCards.size(); t++) {
                playedCardsBox.getChildren().add(playedCards.get(t).getComponent());
            }
        }


        VBox layout = new VBox();
        HBox mainLayout = new HBox(layout, playedCardsBox);

        Button endTurn = new Button("End turn");

        HBox[] stacks = new HBox[4];
        HBox yourCards = new HBox();

        Label playerName = new Label(player.getName());
        layout.getChildren().add(playerName);

        for (int i = 0; i < 4; i++) {
            HBox stack = new HBox();

            for (int j = 0; j < game.getCardStacks()[i].getCardCount(); j++) {
                CardStack realStack = game.getCardStacks()[i];
                CardView cardView = new CardView(game.getCardStacks()[i].getCard(j), cardWidth, cardHeight);
                stack.getChildren().add(cardView.getComponent());

            }
            stacks[i] = stack;
            layout.getChildren().add(stacks[i]);
        }


        for (int c = 0; c < currentSet.getCards().size(); c++) {
            CardView cardView = new CardView(currentSet.getCards().get(c), cardWidth, cardHeight);
            cardView.getComponent().setOnMouseClicked(e -> onCardClicked(e, cardView, playedCardsBox, currentSet, yourCards, game.getPlayedCards()));
            yourCards.getChildren().add(cardView.getComponent());
        }


        endTurn.setOnAction(e -> {
            game.nextTurn();
        });

        layout.getChildren().addAll(yourCards, endTurn);
        Scene scene = new Scene(mainLayout, 1000, 1000);
        stage.setScene(scene);


    }

    private void onCardClicked(MouseEvent e, CardView cardView, VBox playedCardsBox, CardSet hand, HBox handBox, ArrayList<Card> gameCardsPlayed) {
        if (selectedCard == null) {
            selectedCard = cardView;
            Translate translate = new Translate();
            translate.setY(-20);
            selectedCard.getComponent().getTransforms().add(translate);
        } else if (selectedCard != cardView) {
            Translate translate1 = new Translate();
            translate1.setY(20);
            selectedCard.getComponent().getTransforms().add(translate1);
            selectedCard = cardView;
            Translate translate2 = new Translate();
            translate2.setY(-20);
            selectedCard.getComponent().getTransforms().add(translate2);
        } else {
            Translate translate = new Translate();
            translate.setY(20);
            selectedCard.getComponent().getTransforms().add(translate);
            selectedCard.toggleCard();
            handBox.getChildren().remove(selectedCard.getComponent());

            currentSet.take(selectedCard.getCard());
            if (playedCards.size() == game.getPlayerNumber()) {
                int cardId = playedCards.size() - 1;
                playedCards.get(cardId).toggleCard();
                handBox.getChildren().add(playedCards.get(cardId).getComponent());
                playedCards.get(cardId).getComponent().setOnMouseClicked(u -> onCardClicked(u, cardView, playedCardsBox, currentSet, handBox, game.getPlayedCards()));
                currentSet.add(playedCards.get(cardId).getCard());
                playedCards.remove(cardId);
                //playedCardsBox.getChildren().remove(cardId);
                game.getPlayedCards().remove(cardId);

                //TODO résoudre problème : erreur lorsque l'on clique sur une carte que l'on a remise dans la main
            }
            playedCardsBox.getChildren().add(selectedCard.getComponent());

            playedCards.add(selectedCard);
            selectedCard.getComponent().setOnMouseClicked(l -> {
            });
            game.getPlayedCards().add(selectedCard.getCard());
            selectedCard = null;


        }


    }

    private void onStackClicked(MouseEvent e, HBox stack, CardStack realStack) {
        if(selectedCard==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a card from your hand first");
            alert.showAndWait();
        }
        else {


            selectedCard.getComponent().setOnMouseClicked(i->onStackClicked(i,stack, realStack));

            stack.getChildren().add(selectedCard.getComponent());

            realStack.addMayTakeIfBelowOr6th(selectedCard.getCard());
            selectedCard = null;

        }


    }

    public void resetPlayedCards() {
        playedCards=new ArrayList<>();

    }


    public void dsEndTurn(int playerNb){
        selectedCard = playedCards.get(playerNb-1);
        selectedCard.toggleCard();
        VBox layout = new VBox();
        HBox[] stacks = new HBox[4];

        for (int i = 0; i < 4; i++) {
            HBox stack = new HBox();

            for (int j = 0; j < game.getCardStacks()[i].getCardCount(); j++) {
                CardStack realStack = game.getCardStacks()[i];
                CardView cardView = new CardView(game.getCardStacks()[i].getCard(j), cardWidth, cardHeight);
                stack.getChildren().add(cardView.getComponent());
                cardView.getComponent().setOnMouseClicked(e -> onStackClicked(e, stack, realStack));
            }
            stacks[i] = stack;
            layout.getChildren().add(stacks[i]);


        }
        Button next = new Button("Next Player");
        layout.getChildren().add(selectedCard.getComponent());
        layout.getChildren().add(next);

        Scene sceneEnd = new Scene(layout,1000,1000);
        stage.setScene(sceneEnd);

    }
}




