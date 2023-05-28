package com.example.sixquiprendalbouy_cermak.models.cards;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public class CardSet {

    private final TreeSet<Card> cards = new TreeSet<>();

    //---------------------------------------------------------------------------------------------

    public CardSet(Collection<Card> ls) {
        cards.addAll(ls);
    }

    //---------------------------------------------------------------------------------------------

    public Collection<Card> remains() {
        return Collections.unmodifiableSet(cards);
    }

    public void take(Card c) {
        boolean remove = cards.remove(c);
        if (!remove) throw new IllegalArgumentException();
    }

}
