package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.Game;
import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import com.example.sixquiprendalbouy_cermak.views.Display;
import com.example.sixquiprendalbouy_cermak.views.Stack;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;

public class Bot extends AbstractPlayer {
    private final int cardHeight = 115;
    private final int cardWidth = 65;

    public Bot(String name, CardSet hand) {
        this.setHand(hand);
        this.getHand().getCards().sort(Card::compareTo);
        this.setName(name);
    }

    @Override
    public void turn(Display ds) {
        CardView selectedCard = new CardView(this.getHand().getCards().get(0), cardWidth, cardHeight);
        ds.getPlayedCardsBox().addCard(selectedCard, this);
        ds.gameNextPlayer();
    }


    @Override
    public void chooseAStack(CardView card, Display ds) {
        int min = 104;
        Stack goodStack = null;
        for (Stack stack : ds.getAllStack()) {
            if (stack.getCardStack().getSumPenalty() < min) {
                goodStack = stack;
                min = stack.getCardStack().getSumPenalty();
            }
        }
        goodStack.addInStack(card,ds.getPlayedCardsBox());
        ds.getPlayedCardsBox().removeCard(card);
        ds.dropAllPlayedCardInStacks(ds.getPlayedCardsBox().getPlayedCards());

    }
}
