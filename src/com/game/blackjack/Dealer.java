package com.game.blackjack;

import java.util.ArrayList;

public class Dealer {

    Deck deck;

    public Dealer(Deck deck) {
        this.deck = deck;
    }


    public ArrayList<Card> getPlayerCard(){
        return  getTwoCards();
    }

    public ArrayList<Card> getDealerCard(){
        return getTwoCards();
    }

    private ArrayList<Card> getTwoCards(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(deck.getNextCard());
        cards.add(deck.getNextCard());
        return cards;
    }

    public Card getNextCard(){
        return  deck.getNextCard();
    }
}
