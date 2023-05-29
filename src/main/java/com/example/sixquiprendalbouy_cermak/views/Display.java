package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.models.Game;
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

public class Display {
    private Game game;
    private Stage stage;
    private int cardHeight=115;
    private int cardWidth = 65;

    private CardView selectedCard;


    public Display(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.selectedCard=null;
    }

    public void dsCreatePlayers(){
        VBox layout = new VBox();

        Label welcome = new Label("Welcome to Le Six Qui Prend, please choose the number of players and bots");
        Button start = new Button("Start game");


        Label playerLabel = new Label("Number of players :");
        Spinner<Integer> playerSpinner = new Spinner<>(1,10,1);
        HBox playerBox = new HBox(playerLabel,playerSpinner);

        Label botLabel = new Label("Number of bots :");
        Spinner<Integer> botSpinner = new Spinner<>(0,10,1);
        HBox botBox = new HBox(botLabel,botSpinner);

        start.setOnAction(e->{
            if (playerSpinner.getValue()+botSpinner.getValue()>10 || playerSpinner.getValue()+botSpinner.getValue()<2){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The total number of players must be between 2 and 10 ");
            alert.showAndWait();
            }

            else{
                game.createPlayers(playerSpinner.getValue(), botSpinner.getValue());
            }
        });

        layout.getChildren().addAll(welcome,playerBox,botBox, start);
        Scene scene = new Scene(layout,500,500);
        stage.setScene(scene);
        stage.show();

    }

    public void playerTurn(Player player){
        CardSet playerHand = player.getHand();
        VBox layout = new VBox();
        HBox[] stacks= new HBox[4];
        HBox yourCards = new HBox();

        Label playerName = new Label(player.getName());
        layout.getChildren().add(playerName);

        for(int i=0;i<4;i++){
            HBox stack = new HBox();

            for(int j=0;j<game.getCardStacks()[i].getCardCount();j++) {
                CardStack realStack = game.getCardStacks()[i];
                CardView cardView = new CardView(game.getCardStacks()[i].getCard(j), cardWidth, cardHeight);
                stack.getChildren().add(cardView.getComponent());
                cardView.getComponent().setOnMouseClicked(e -> onStackClicked(e, stack, playerHand, yourCards, realStack));
            }
            stacks[i] = stack;
            layout.getChildren().add(stacks[i]);
        }


        for(int c=0;c<playerHand.getCards().size();c++){
            CardView cardView = new CardView(playerHand.getCards().get(c),cardWidth,cardHeight );
            cardView.getComponent().setOnMouseClicked(e->onCardClicked(e,cardView));
            yourCards.getChildren().add(cardView.getComponent());
        }

        layout.getChildren().add(yourCards);
        Scene scene = new Scene(layout, 1000,1000);
        stage.setScene(scene);





    }

    private void onCardClicked(MouseEvent e,CardView cardView){
        if(selectedCard==null) {
            selectedCard = cardView;
            Translate translate = new Translate();
            translate.setY(-20);
            selectedCard.getComponent().getTransforms().add(translate);
        }
        else if(selectedCard != cardView){
            Translate translate1 = new Translate();
            translate1.setY(20);
            selectedCard.getComponent().getTransforms().add(translate1);
            selectedCard = cardView;
            Translate translate2 = new Translate();
            translate2.setY(-20);
            selectedCard.getComponent().getTransforms().add(translate2);
        }
        else{
            Translate translate1 = new Translate();
            translate1.setY(20);
            selectedCard.getComponent().getTransforms().add(translate1);
            selectedCard=null;
        }



    }

    private void onStackClicked(MouseEvent e, HBox stack, CardSet hand, HBox handBox, CardStack realStack){
        if(selectedCard==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a card from your hand first");
            alert.showAndWait();
        }
        else {
            Translate translate = new Translate();
            translate.setY(20);
            selectedCard.getComponent().getTransforms().add(translate);
            selectedCard.getComponent().setOnMouseClicked(i->onStackClicked(i,stack,hand,handBox, realStack));
            handBox.getChildren().remove(selectedCard.getComponent());
            stack.getChildren().add(selectedCard.getComponent());
            hand.take(selectedCard.getCard());
            realStack.addMayTakeIfBelowOr6th(selectedCard.getCard());
            selectedCard = null;

            game.nextTurn();
        }

    }


}

