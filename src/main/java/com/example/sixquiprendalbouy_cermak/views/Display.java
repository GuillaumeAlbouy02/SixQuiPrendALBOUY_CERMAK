package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.models.Game;
import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
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
import javafx.stage.Stage;

public class Display {
    private Game game;
    private Stage stage;
    private int cardHeight=115;
    private int cardWidth = 65;


    public Display(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
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
        for(int i=0;i<4;i++){
            CardView cardView = new CardView(game.getCardStacks()[i].getFirstCard(), cardWidth,cardHeight);
            stacks[i] = new HBox(cardView.getComponent());
            layout.getChildren().add(stacks[i]);
        }

        HBox yourCards = new HBox();
        for(int c=0;c<playerHand.getCards().size();c++){
            CardView cardView = new CardView(playerHand.getCards().get(c),cardWidth,cardHeight );
            cardView.getComponent().setOnMouseClicked(e->onCardClicked(e,cardView, stacks[1],playerHand,yourCards ));
            yourCards.getChildren().add(cardView.getComponent());
        }

        layout.getChildren().add(yourCards);
        Scene scene = new Scene(layout, 1000,1000);
        stage.setScene(scene);





    }

    private void onCardClicked(MouseEvent e,CardView cardView, HBox stack, CardSet hand, HBox handBox){
        handBox.getChildren().remove(cardView.getComponent());
        stack.getChildren().add(cardView.getComponent());
        hand.take(cardView.getCard());
//TODO add stack choice by using a selectedCard attribute.


    }
}
