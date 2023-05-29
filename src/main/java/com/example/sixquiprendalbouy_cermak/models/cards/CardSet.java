package com.example.sixquiprendalbouy_cermak.models.cards;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/** la class CardSet permet de représenter la main d'un joueurs
 *  attribut :
 *      - cards : la main d'un joueur
 *  methode :
 *      - CardSet(Collection<Card> ls) : constructeur de la class
 *      - remains() : permet de récupéré la main d'un joueurs sur une autre classe
 *      - take(Card c) : permet de retiré une carte de la liste
 * */

public class CardSet {

    private @Getter @Setter ArrayList<Card> cards = new ArrayList<>();

    //---------------------------------------------------------------------------------------------

    public CardSet(List<Card> ls) {
        cards.addAll(ls);
    }

    //---------------------------------------------------------------------------------------------

    public Collection<Card> remains() {
        return Collections.unmodifiableList(cards);
    }

    public void take(Card c) {
        boolean remove = cards.remove(c);
        if (!remove) throw new IllegalArgumentException();
    }

}
