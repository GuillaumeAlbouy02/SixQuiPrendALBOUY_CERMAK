package com.example.sixquiprendalbouy_cermak.models.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * La class Cards permet de définir l'ensemble des Cartes
 * <p>
 * attributs :
 * -MIN_CARD_VALUE : represente la valeur minimal d'une carte
 * -MAX_CARD_VALUE : represente la valeur max d'une carte
 * -DEFAULT_CARDS_COUNT_PER_PLAYER : represente le nombre de carte distribuer pour chaque joueurs
 * -cards : represente la liste des cartes
 * <p>
 * methode :
 * -cardOf(int i ) : permet de choisir la carte i_ème de paquet de carte
 * -createCards() : permet de crée l'ensemble du paquet de carte
 * -cardPenalty(int number) : permet d'attribuer une pénalité en fonction du numéro de la valeur de la carte
 * -distributeRandomCards(int nPlayer, Random rand, int nCards): permet de distribuer aux joueurs leurs cartes
 */

public class Cards {

    public static final int MIN_CARD_VALUE = 1;
    public static final int MAX_CARD_VALUE = 104;

    public static final int DEFAULT_CARDS_COUNT_PER_PLAYER = 10;

    public static final List<Card> cards = createCards();
    private List<Card> remain;

    public Cards() {
        this.remain = new ArrayList<>(cards);
    }

    public static Card cardOf(int i) {
        return cards.get(i - 1);
    }

    private static List<Card> createCards() {
        List<Card> res = new ArrayList<>();
        for (int i = MIN_CARD_VALUE; i <= MAX_CARD_VALUE; i++) {
            res.add(new Card(i, cardPenalty(i)));
        }
        return Collections.unmodifiableList(res);
    }

    public static int cardPenalty(int number) {
        if (number == 55) {
            return 7;
        } else if (number % 11 == 0) {
            return 5;
        } else if (number % 10 == 0) {
            return 3;
        } else if (number % 5 == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    public static List<CardSet> distributeRandomCards(int nPlayer, Random rand) {
        return distributeRandomCards(nPlayer, rand, DEFAULT_CARDS_COUNT_PER_PLAYER);
    }

    public static List<CardSet> distributeRandomCards(int nPlayer, Random rand, int nCards) {
        if (nPlayer < 0 || nPlayer > 10) throw new IllegalArgumentException();
        List<Card> remain = new ArrayList<>(cards);
        List<Card>[] playerCards = new List[nPlayer];
        for (int j = 0; j < nPlayer; j++) {
            playerCards[j] = new ArrayList<>(nCards);
        }
        for (int i = 0; i < nCards; i++) {
            for (int j = 0; j < nPlayer; j++) {
                int idx = rand.nextInt(remain.size());
                Card card = remain.remove(idx);
                playerCards[j].add(card);
            }
        }
        List<CardSet> res = new ArrayList<>(nPlayer);
        for (int j = 0; j < nPlayer; j++) {
            res.add(new CardSet(playerCards[j]));
        }
        return res;
    }

    public Card takeFromRemain(Random rand) {
        int idx = rand.nextInt(remain.size());
        return remain.remove(idx);
    }
    public void removeAllCard(List<CardSet> removedCards){
        for(CardSet cardSet:removedCards){
            remain.removeAll(cardSet.getCards());
        }
    }
}
