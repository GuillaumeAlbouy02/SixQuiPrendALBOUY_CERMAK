package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import lombok.Getter;
import lombok.Setter;

public class Player extends AbstractPlayer{
    private @Getter String name;

    public Player(String name){
        this.name=name;

    }

}
