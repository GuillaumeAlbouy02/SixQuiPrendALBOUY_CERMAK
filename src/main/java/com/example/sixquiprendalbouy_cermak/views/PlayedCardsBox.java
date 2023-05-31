package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class PlayedCardsBox {
    private final @Getter VBox playedCardBox = new VBox();
    /*private @Setter @Getter TreeMap<CardView,Integer> playedCards = new TreeMap<>(this::compare);*/
    private @Getter @Setter ArrayList<CardView> playedCards = new ArrayList<>();

    public void addCard(CardView cardView){
        playedCardBox.getChildren().add(cardView.getComponent());
        playedCards.add(cardView);
    }

    public int compare(CardView one, CardView two){
        Integer uno = one.getCard().value;
        Integer dos = two.getCard().value;
        return uno.compareTo(dos);

    }
}
