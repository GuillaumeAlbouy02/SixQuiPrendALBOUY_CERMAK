package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.views.Display;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;

public class Player extends AbstractPlayer {


    public Player(String name, CardSet hand) {
        this.setHand(hand);
        this.getHand().getCards().sort(Card::compareTo);
        this.setName(name);
    }

    @Override
    public void turn(Display ds) {
        ds.playerTurn(this);
    }

    @Override
    public void chooseAStack(CardView card, Display ds) {
        ds.chooseStack(card, this);
    }
}
