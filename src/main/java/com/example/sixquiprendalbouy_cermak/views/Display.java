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
import java.util.List;
import java.util.TreeMap;

public class Display {
    private final Game game;
    private final Stage stage;
    private final int cardHeight = 115;
    private final int cardWidth = 65;


    @Getter PlayedCardsBox playedCardsBox = new PlayedCardsBox();

    TreeMap<CardView,AbstractPlayer> playedCards = playedCardsBox.getPlayedCards();

    private CardView selectedCard;
    private CardSet currentSet;
    private Stack[] allStack = new Stack[4];


    public Display(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.selectedCard = null;
        playedCardsBox.setPlayedCards(new TreeMap<CardView,AbstractPlayer>());
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

    public VBox initCardStack(){
        int i = 0;
        VBox layout = new VBox();
        for(CardStack cardStack: game.getCardStacks()){
            Stack stack = new Stack(cardStack);
            for(Card card: cardStack.getCards()){
                CardView cardView = new CardView(card,cardWidth,cardHeight);
                stack.simpleAdd(cardView);
            }
            layout.getChildren().add(stack);
            allStack[i] = stack;
            game.getCardStacks()[i] = stack.getCardStack();
            i++;
        }

        return layout;
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

            /*
            Pour l'instant est il vraiment utile de récupéré une carte déjà jouer ???

            if (playedCardsBox.getPlayedCards().size() == game.getPlayerID()) {

              //  int cardId = playedCardsBox.getPlayedCards().indexOf(selectedCard); non car la selectedCard a changé losqu'on a cliqué dessus
                int cardId = playedCardsBox.getPlayedCards().size()-1;

                playedCardsBox.getPlayedCards().get(cardId).toggleCard();
                handBox.getChildren().add(playedCardsBox.getPlayedCards().get(cardId).getComponent());
                playedCardsBox.getPlayedCards().get(cardId).getComponent().setOnMouseClicked(u -> onCardClicked( cardView, playedCardsBox, player, handBox, game.getPlayedCards()));
                currentSet.add(playedCardsBox.getPlayedCards().get(cardId).getCard());
                playedCardsBox.getPlayedCards().remove(cardId);
                game.getPlayedCards().remove(cardId);

            }*/

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
        }else {
            //selectedCard.getComponent().setOnMouseClicked(i -> onStackClicked(i, stack, realStack));
            List<Card> cardList =realStack.addMayTakeIfBelowOr6th(selectedCard.getCard());
            if (cardList.size()!=0){
                for (Card card : cardList){
                    player.addScore(card.penalty);
                }
            }
            //stack.resetStack(selectedCard);
            playedCardsBox.removeCard(selectedCard);
            selectedCard = null;
            dropAllPlayedCardInStacks(playedCardsBox.getPlayedCards());


        }
    }

    public void resetPlayedCards() {
        playedCards = new TreeMap<CardView,AbstractPlayer>();

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

    public void dropAllPlayedCardInStacks(TreeMap<CardView, AbstractPlayer>playedCards) {

        Stack goodStack;
        //ArrayList<CardView> playedCards = (ArrayList<CardView>) playedCardsBox.getPlayedCards().clone();
        if(playedCards.size()!=0) {
            CardView card = playedCards.firstKey();
            AbstractPlayer player = playedCards.get(card);
            //for (CardView card : playedCards) {
                goodStack = compareWithStackValues(card);
                if (goodStack != null) {
                    goodStack.addInStack(card, playedCardsBox);
                    playedCardsBox.removeCard(card);
                    dropAllPlayedCardInStacks(playedCardsBox.getPlayedCards());
                } else {
                    chooseStack(card, player);
                }
            //}
        }
        else{
            game.getPlayers()[0].turn(this);
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

    public void chooseStack(CardView card, AbstractPlayer player){
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
}




