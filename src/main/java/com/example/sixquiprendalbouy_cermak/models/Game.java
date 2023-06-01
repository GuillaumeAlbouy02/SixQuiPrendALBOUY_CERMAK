package com.example.sixquiprendalbouy_cermak.models;

import com.example.sixquiprendalbouy_cermak.models.cards.Card;
import com.example.sixquiprendalbouy_cermak.models.cards.CardSet;
import com.example.sixquiprendalbouy_cermak.models.cards.CardStack;
import com.example.sixquiprendalbouy_cermak.models.cards.Cards;
import com.example.sixquiprendalbouy_cermak.models.players.AbstractPlayer;
import com.example.sixquiprendalbouy_cermak.models.players.Bot;
import com.example.sixquiprendalbouy_cermak.models.players.Player;
import com.example.sixquiprendalbouy_cermak.views.Display;
import com.example.sixquiprendalbouy_cermak.views.card.CardView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Game {
    private final @Getter Stage stage;
    private final @Getter Display ds;
    private @Getter @Setter AbstractPlayer[] players;
    private @Getter Player[] realPlayers;
    private final Cards cards;
    private final @Getter CardStack[] cardStacks = new CardStack[4];
    private @Getter @Setter ArrayList<Card> playedCards;

    private @Getter int turn;
    private @Getter int playerID;

    public Game(Stage stage) {
        this.stage = stage;
        this.ds = new Display(this, stage);
        this.cards=new Cards();
        this.turn = 2;
        this.playerID = 0;
        this.playedCards = new ArrayList<>();;
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
            AbstractPlayer bot = new Bot("Bot"+(c-nbPlayers+1));
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
        players[playerID].turn(ds);
    }

    public void nextTurn(){

        playerID++;
        if(players.length <= playerID){
            playerID = 0;
            ds.dropAllPlayedCardInStacks((TreeMap<CardView, AbstractPlayer>) ds.getPlayedCardsBox().getPlayedCards().clone());
           }
        else {
            turn(players[playerID]);
        }

    }

    public void turn(AbstractPlayer player){
        if (player.getHand().getCards().size() == 0){
            endGame();
        } else {
            player.turn(ds);
        }
    }

    public void endGame(){
        // Faut faire une fonction qui termine le jeu
    }

    public void endTurn(int playerNb){
        ds.dsEndTurn(playerNb);
    }
}

