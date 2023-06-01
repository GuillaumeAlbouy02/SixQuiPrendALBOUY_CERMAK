package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.models.players.AbstractPlayer;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class PlayedCardsBox extends VBox {
    private @Setter @Getter TreeMap<CardView,AbstractPlayer> playedCards = new TreeMap<>();

    //private @Setter @Getter TreeMap<String, AbstractPlayer> treePlayedCards = new TreeMap<>();
    //private @Setter @Getter ArrayList<CardView> playedCards = new ArrayList<>();

    public void addCard(CardView cardView, AbstractPlayer player){
        this.getChildren().add(cardView.getComponent());
        //playedCards.add(cardView);
        playedCards.put(cardView,player);
        cardView.toggleCard();
        //treePlayedCards.put(cardView.getCard().toString(), player);
    }

    public void removeCard(CardView cardView){
        this.getChildren().remove(cardView.getComponent());
        playedCards.remove(cardView);
        cardView.toggleCard();
    }

}
