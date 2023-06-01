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

/** La class CardStack permet de représenter les lignes de carte
 *
 *  attribut :
 *      - cards : represente une ligne de carte
 *      - sumPenalty : represente la somme des penalité des cartes présente sur la ligne
 *      - topValue : represente la valeur la plus haute de la ligne
 *
 *  methode :
 *      - CardStack(Card firstCard) : Initialise la ligne avec une première carte
 *      - getCardCount() : permet de donner le nombre de carte sur la ligne
 *      - resetWithTopCard(Card card) : permet de vider la ligne et d'y mettre la nouvelle carte
 *      - addMayTakeIfBelowOr6th(Card c) : permet de vérifier les règles du jeu tel que le 6 qui prend ou bien
 *      que la carte est supérieur
 * */
public class CardStack {

    @Getter
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

    public Card getCard(int i){
        return cards.get(i);
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

    public void resetWithTopCard(Card card) {
        this.cards.add(card);
        this.sumPenalty = card.penalty;
        this.topValue = card.value;
    }
}
