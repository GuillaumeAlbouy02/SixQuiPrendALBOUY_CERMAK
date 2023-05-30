package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PlayedCardsBox {
    private final @Getter VBox playedCardBox = new VBox();
    private @Setter @Getter ArrayList<CardView> playedCards = new ArrayList<>();

    public void addCard(CardView cardView){
        playedCardBox.getChildren().add(cardView.getComponent());
        playedCards.add(cardView);
    }
}
