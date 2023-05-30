package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import com.example.sixquiprendalbouy_cermak.views.Display;
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
}
