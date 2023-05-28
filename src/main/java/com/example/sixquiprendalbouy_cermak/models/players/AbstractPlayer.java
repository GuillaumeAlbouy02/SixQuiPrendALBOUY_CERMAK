package com.example.sixquiprendalbouy_cermak.models.players;

import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import lombok.Getter;
import lombok.Setter;

public class AbstractPlayer {
    private @Getter @Setter CardSet hand;
    private @Getter @Setter int score;

}
