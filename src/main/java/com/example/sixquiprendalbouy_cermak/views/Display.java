package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.models.Game;
import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.CardStack;
import com.example.sixquiprendalbouy_cermak.models.players.AbstractPlayer;
import com.example.sixquiprendalbouy_cermak.models.players.Player;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;
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
import lombok.Getter;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class Display {
    private final Game game;
    private final Stage stage;
    private final int cardHeight = 115;
    private final int cardWidth = 65;


    @Getter
    PlayedCardsBox playedCardsBox = new PlayedCardsBox();

    TreeMap<CardView, AbstractPlayer> playedCards = playedCardsBox.getPlayedCards();

    private CardView selectedCard;
    private CardSet currentSet;
    private @Getter Stack[] allStack = new Stack[4];


    public Display(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.selectedCard = null;
        playedCardsBox.setPlayedCards(new TreeMap<CardView, AbstractPlayer>());
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

    public VBox initCardStack() {
        int i = 0;
        VBox layout = new VBox();
        for (CardStack cardStack : game.getCardStacks()) {
            Stack stack = new Stack(cardStack);
            for (Card card : cardStack.getCards()) {
                CardView cardView = new CardView(card, cardWidth, cardHeight);
                stack.simpleAdd(cardView);
            }
            layout.getChildren().add(stack);
            allStack[i] = stack;
            game.getCardStacks()[i] = stack.getCardStack();
            i++;
        }

        return layout;
    }

    public void gameNextPlayer(){
        game.nextTurn();
    }

    public void playerTurn(Player player) {
        currentSet = player.getHand();

        VBox layout = initCardStack();
        HBox mainLayout = new HBox(layout, playedCardsBox);

        HBox yourCards = new HBox();

        Label playerName = new Label(player.getName());
        layout.getChildren().add(playerName);

        for (int c = 0; c < currentSet.getCards().size(); c++) {
            CardView cardView = new CardView(currentSet.getCards().get(c), cardWidth, cardHeight);
            cardView.getComponent().setOnMouseClicked(e -> onCardClicked(cardView, playedCardsBox, player, yourCards, game.getPlayedCards()));
            yourCards.getChildren().add(cardView.getComponent());
        }


        layout.getChildren().addAll(yourCards);
        Scene scene = new Scene(mainLayout, 1000, 1000);
        stage.setScene(scene);
    }


    private void onCardClicked(CardView cardView, PlayedCardsBox playedCardsBox, AbstractPlayer player, HBox handBox, ArrayList<Card> gameCardsPlayed) {
        Translate translateUp = new Translate();
        translateUp.setY(-20);

        Translate translateDown = new Translate();
        translateDown.setY(20);

        if (selectedCard == null) {
            selectedCard = cardView;
            selectedCard.getComponent().getTransforms().add(translateUp);
        } else if (selectedCard != cardView) {
            selectedCard.getComponent().getTransforms().add(translateDown);
            selectedCard = cardView;
            selectedCard.getComponent().getTransforms().add(translateUp);
        } else {
            // Si selectedCard == cardView
            selectedCard.getComponent().getTransforms().add(translateDown);
            handBox.getChildren().remove(selectedCard.getComponent());
            currentSet.take(selectedCard.getCard());

            playedCardsBox.addCard(selectedCard, player);

            selectedCard.getComponent().setOnMouseClicked(l -> {
            });
            game.getPlayedCards().add(selectedCard.getCard());
            selectedCard = null;
            game.nextTurn();
        }
    }

    private void onStackClicked(MouseEvent e, Stack stack, CardStack realStack, AbstractPlayer player) {
        if (selectedCard == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a card from your hand first");
            alert.showAndWait();
        } else {
            //selectedCard.getComponent().setOnMouseClicked(i -> onStackClicked(i, stack, realStack));
            stack.addInStack(selectedCard, playedCardsBox);

            //stack.resetStack(selectedCard);
            playedCardsBox.removeCard(selectedCard);
            selectedCard = null;
            dropAllPlayedCardInStacks(playedCardsBox.getPlayedCards());
        }
    }


    public void dsEndTurn(int playerNb) {
        selectedCard = playedCards.firstKey();
        AbstractPlayer player = playedCards.get(selectedCard);
        selectedCard.toggleCard();
        VBox layout = new VBox();
        Stack[] stacks = new Stack[4];
        int i = 0;
        for (CardStack cardStack : game.getCardStacks()) {
            Stack stack = new Stack(cardStack);

            for (int j = 0; j < cardStack.getCardCount(); j++) {
                CardStack realStack = cardStack;
                CardView cardView = new CardView(cardStack.getCard(j), cardWidth, cardHeight);
                stack.getChildren().add(cardView.getComponent());
                //cardView.getComponent().setOnMouseClicked(e -> onStackClicked(e, stack, realStack, game.getPlayers()[playerNb]));
            }
            stacks[i] = stack;
            layout.getChildren().add(stack);
            i++;
        }
        Button next = new Button("Next Player");
        playerNb++;
        int nb = playerNb;
        next.setOnAction(e -> game.endTurn(nb));
        layout.getChildren().add(selectedCard.getComponent());
        layout.getChildren().add(next);

        Scene sceneEnd = new Scene(layout, 1000, 1000);
        stage.setScene(sceneEnd);
    }

    public void dropAllPlayedCardInStacks(TreeMap<CardView, AbstractPlayer> playedCards) {

        Stack goodStack;
        //ArrayList<CardView> playedCards = (ArrayList<CardView>) playedCardsBox.getPlayedCards().clone();
        if (playedCards.size() != 0) {
            CardView card = playedCards.firstKey();
            AbstractPlayer player = playedCards.get(card);
            //for (CardView card : playedCards) {
            goodStack = compareWithStackValues(card);
            if (goodStack != null) {
                goodStack.addInStack(card, playedCardsBox);
                playedCardsBox.removeCard(card);
                dropAllPlayedCardInStacks(playedCardsBox.getPlayedCards());
            } else {
                player.chooseAStack(card, this);
            }
            //}
        } else {
            game.turn(game.getPlayers()[game.getPlayerID()]);
        }
    }

    public Stack compareWithStackValues(CardView cardView) {
        int score;
        int min = 104; // 104 est la valeur maximum qui peut attendre la difference entre deux carte
        Stack goodStack = null;
        int cardValues = cardView.getCard().value;
        for (Stack stack : allStack) {
            score = cardValues - stack.getTopValue();
            if (score > 0 && score < min) {
                goodStack = stack;
                min = score;
            }
        }
        return goodStack;
    }

    public void chooseStack(CardView card, AbstractPlayer player) {
        selectedCard = card;
        selectedCard.toggleCard();
        VBox layout = new VBox();
        Stack[] stacks = new Stack[4];
        int i = 0;
        for (CardStack cardStack : game.getCardStacks()) {
            Stack stack = new Stack(cardStack);

            for (int j = 0; j < cardStack.getCardCount(); j++) {
                CardStack realStack = cardStack;
                CardView cardView = new CardView(cardStack.getCard(j), cardWidth, cardHeight);
                stack.getChildren().add(cardView.getComponent());
                cardView.getComponent().setOnMouseClicked(e -> onStackClicked(e, stack, realStack, player));
            }
            stacks[i] = stack;
            layout.getChildren().add(stack);
            i++;
        }
        Button next = new Button("Next Player");

        next.setOnAction(e -> {
            playedCardsBox.removeCard(card);
            dropAllPlayedCardInStacks(playedCardsBox.getPlayedCards());
        });
        layout.getChildren().add(selectedCard.getComponent());
        layout.getChildren().add(next);

        Scene sceneEnd = new Scene(layout, 1000, 1000);
        stage.setScene(sceneEnd);
    }

    public void dsEndGame(TreeMap<Integer, AbstractPlayer> scoreTab) {
        VBox dsScoreTab = new VBox();
        Set<Integer> keys = scoreTab.keySet();
        for (Integer key : keys) {
            Label scorePlayer = new Label(scoreTab.get(key).getName() + " have " + key + "Ox");
            dsScoreTab.getChildren().add(scorePlayer);
        }

        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            game.startGame();
        });

        dsScoreTab.getChildren().add(newGame);

        Scene sceneEnd = new Scene(dsScoreTab, 1000, 1000);
        stage.setScene(sceneEnd);
    }
}




