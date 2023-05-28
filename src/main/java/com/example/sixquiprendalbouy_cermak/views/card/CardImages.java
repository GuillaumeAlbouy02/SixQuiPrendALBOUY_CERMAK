package com.example.sixquiprendalbouy_cermak.views.card;


import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import javafx.scene.image.Image;

import java.net.URL;

public class CardImages {

    private static final Image backsideImage = createBacksizeImage();

    private static final Image[] cardImages = createCardImages();

    //---------------------------------------------------------------------------------------------

    public static Image getBacksideImage() {
        return backsideImage;
    }

    public static Image getFrontCardImage(Card card) {
        return (card != null)? cardImages[card.value] : backsideImage;
    }

    private static Image createBacksizeImage() {
        URL imgUrl = CardImages.class.getResource("backside.png");
        return new Image(imgUrl.toExternalForm());
    }

    private static Image[] createCardImages() {
        Image[] res = new Image[1+Cards.MAX_CARD_VALUE];
        res[0] = null;
        for(int i = 1; i <= Cards.MAX_CARD_VALUE; i++) {
            URL imgUrl = CardImages.class.getResource(i + ".png");
            res[i] = new Image(imgUrl.toExternalForm());
        }
        return res;
    }

}
