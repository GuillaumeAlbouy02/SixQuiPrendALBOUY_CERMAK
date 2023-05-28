package com.example.sixquiprendalbouy_cermak.models.cards;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * card stack, implementing game rules:
 * <ul>
 *     <li>when adding a card with value less than top value,
 *     then take all the stack cards and put your card
 *     </li>
 *     <li>when adding a 6-th card,
 *     then take all the stack cards (penalties)
 *     and put your card that become the first of the stack.
 *     </li>
 *     <li>else when adding a card (at position 2,3,4,5),
 *     then your are lucky, just put your card on the stack
 *     </li>
 * </ul>
 */
public class CardStack {

    private final List<Card> cards = new ArrayList<>(5);

    @Getter // derived field, may also be recomputed each time
    private int sumPenalty;

    @Getter // derived field, may also be recomputed each time
    private int topValue;

    //---------------------------------------------------------------------------------------------

    public CardStack(Card firstCard) {
        Objects.requireNonNull(firstCard);
        resetWithTopCard(firstCard);
    }

    //---------------------------------------------------------------------------------------------

    public int getCardCount() {
        return cards.size();
    }

    public List<Card> addMayTakeIfBelowOr6th(Card c) {
        Objects.requireNonNull(c);
        List<Card> res;
        if (c.value < topValue) {
            res = new ArrayList<>(cards);
            this.cards.clear();
            resetWithTopCard(c);
        } else {
            if (cards.size() == 5) {
                res = new ArrayList<>(cards);
                this.cards.clear();
                resetWithTopCard(c);
            } else {
                cards.add(c);
                this.sumPenalty += c.penalty;
                this.topValue = c.value;
                res = null;
            }
        }
        return res;
    }

    protected void resetWithTopCard(Card card) {
        this.cards.add(card);
        this.sumPenalty = card.penalty;
        this.topValue = card.value;
    }
}
