package com.example.sixquiprendalbouy_cermak.models.cards;


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
}
