package com.example.sixquiprendalbouy_cermak.views.card;



import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;

import java.util.Comparator;


public class CardView implements Comparable<CardView> {

    @Getter
    protected Pane component;
    protected StackPane stackFrontOrBack;
    protected ImageView frontImageView;
    protected ImageView backImageView;

    /** may be null if unknown.. forced to back side */
    protected @Getter Card card;

    protected boolean frontSide;

    //---------------------------------------------------------------------------------------------

    public CardView(Card card, int width, int height){
        this.card = card;
        this.frontSide = true;
        if (card == null) {
            this.frontSide = false;
        }
        component = new Pane();
        Image frontImage = CardImages.getFrontCardImage(card);
        Image backImage = CardImages.getBacksideImage();
        frontImageView = new ImageView(frontImage);
        frontImageView.setPreserveRatio(true);
        frontImageView.setFitWidth(width);

        backImageView = new ImageView(backImage);
        backImageView.setPreserveRatio(true);
        backImageView.setFitWidth(width);

        stackFrontOrBack = new StackPane(backImageView, frontImageView);
        component.getChildren().add(stackFrontOrBack);
        component.setPrefSize(width, height);
    }

    public void toggleCard() {
        this.frontSide = !frontSide;
        frontImageView.setVisible(frontSide);
        backImageView.setVisible(!frontSide);
    }


    //---------------------------------------------------------------------------------------------


    @Override
    public String toString() {
        return "CardView{" +
                card +
                '}';
    }

    @Override
    public int compareTo(CardView otherCard){
        return Integer.compare(this.card.value,otherCard.card.value);

    }
}
