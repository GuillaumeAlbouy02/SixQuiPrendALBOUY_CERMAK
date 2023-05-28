package com.example.sixquiprendalbouy_cermak.models;

import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.CardStack;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import com.example.sixquiprendalbouy_cermak.models.players.AbstractPlayer;
import com.example.sixquiprendalbouy_cermak.models.players.Bot;
import com.example.sixquiprendalbouy_cermak.models.players.Player;
import com.example.sixquiprendalbouy_cermak.views.Display;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

public class Game {
    private @Getter Stage stage;
    private @Getter Display ds;
    private @Getter @Setter AbstractPlayer[] players;
    private Player[] realPlayers;
    private Cards cards;
    private @Getter CardStack[] cardStacks = new CardStack[4];

    public Game(Stage stage) {
        this.stage = stage;
        this.ds = new Display(this, stage);
        this.cards=new Cards();
    }

    public void startGame(){
        ds.dsCreatePlayers();
    }

    public void createPlayers(int nbPlayers, int nbBots){
        players = new AbstractPlayer[nbPlayers+nbBots];
        realPlayers = new Player[nbPlayers];
        for (int i=0; i<nbPlayers;i++){
            Player player = new Player("Player"+(i+1));
            players[i]=player;
            realPlayers[i] = player;

        }
        for (int c=nbPlayers; c<nbPlayers+nbBots;c++){
            AbstractPlayer bot = new Bot();
            players[c]=bot;
        }
        List<CardSet> hands = cards.distributeRandomCards(nbPlayers+nbBots,new Random());
        for (int j = 0;j<hands.size();j++){
            players[j].setHand(hands.get(j));
        }
        play();

    }

    public void play(){

        for (int i = 0; i<4; i++){
            cardStacks[i] = new CardStack(cards.takeFromRemain(new Random()));
        }
        ds.playerTurn(realPlayers[0]);


    }
}

