package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import com.example.sixquiprendalbouy_cermak.views.Display;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import lombok.Getter;
import lombok.Setter;

public class Player extends AbstractPlayer{


    public Player(String name){
        this.setName(name);
    }

    @Override
    public void turn(Display ds) {
        ds.playerTurn(this);
    }

    @Override
    public void chooseAStack(CardView card, Display ds) {
        ds.chooseStack(card,this);
    }
}
