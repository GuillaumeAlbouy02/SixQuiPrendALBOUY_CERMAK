package com.example.sixquiprendalbouy_cermak.models.cards;

/**
 * La class Card permet de définir l'objet "Carte"
 * <p>
 * attribut :
 * - value : Donne la valeur de la carte comprise entre [1,104]
 * - penality : Donne la penalité de la carte
 * <p>
 * methode :
 * - toString() : permet de retourner la valeurs de la carte en chaîne de caractère
 */

public class Card {

    public final int value;
    public final int penalty;

    /*pp*/ Card(int value, int penalty) {
        this.value = value;
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "Card-" + value;
    }

    public int compareTo(Card otherCard) {
        return Integer.compare(this.value, otherCard.value);
    }
}
