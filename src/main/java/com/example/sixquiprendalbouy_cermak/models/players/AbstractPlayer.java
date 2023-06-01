package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.views.Display;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractPlayer {
    private @Getter @Setter CardSet hand;
    private @Getter @Setter int score;
    private @Getter @Setter String name;

    public abstract void turn(Display ds);

    public int addScore(int i){
        this.score +=i;
        return score+i;
    }

}
