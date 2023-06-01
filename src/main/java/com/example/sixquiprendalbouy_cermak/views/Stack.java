package com.example.sixquiprendalbouy_cermak.views;

import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.CardStack;
import com.example.sixquiprendalbouy_cermak.models.players.AbstractPlayer;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Stack extends HBox {
    private @Getter @Setter CardView firstCard;
    private final @Getter CardStack cardStack ;
    private @Getter ArrayList<CardView> cardViews = new ArrayList<>();

    private final int cardHeight = 115;
    private final int cardWidth = 65;

    public Stack(CardStack cardStack){
        this.cardStack = cardStack;
        this.firstCard = new CardView(cardStack.getCard(0),cardWidth,cardHeight);
    }

    public int getTopValue(){
        return cardStack.getTopValue();
    }

    public void simpleAdd(CardView cardView){
        cardViews.add(cardView);
        this.getChildren().add(cardView.getComponent());
    }

    public void addInStack(CardView cardView, PlayedCardsBox playedCardsBox){
        List<Card> res = cardStack.addMayTakeIfBelowOr6th(cardView.getCard());
        cardViews.add(cardView);
        if (res == null){
            this.getChildren().add(cardView.getComponent());
        } else {

            AbstractPlayer playerOfThisCard = playedCardsBox.getPlayedCards().get(cardView);
            addPenality(res, playerOfThisCard);
            System.out.println(playerOfThisCard.getName() + " : " + playerOfThisCard.getScore());

            this.getChildren().removeAll();
            this.getChildren().add(cardView.getComponent());
        }
    }

    public void addPenality(List<Card> cards, AbstractPlayer player){
        int sumPenality = 0;
        for(Card card:cards){
            sumPenality += card.penalty;
        }
        player.addScore(sumPenality);
    }

}
