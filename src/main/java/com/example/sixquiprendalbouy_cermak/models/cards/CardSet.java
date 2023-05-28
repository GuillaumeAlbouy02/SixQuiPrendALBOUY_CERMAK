package com.example.sixquiprendalbouy_cermak.models.cards;

import java.util.*;

public class CardSet {

    private final ArrayList<Card> cards = new ArrayList<>();

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
