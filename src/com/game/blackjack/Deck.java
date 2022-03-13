package com.game.blackjack;

import java.util.Collections;
import java.util.Stack;

public class Deck {

    private static final String red = "Red";
    private static final String black = "Black";

    private static final String heart = "Hearts";
    private static final String diamonds = "Diamonds";
    private static final String spades = "Spades";
    private static final String clubs = "Clubs";

    Stack<Card> deck = new Stack<>();

    public Deck() {
        deckInitialize();
    }

    private void deckInitialize(){

        for(int i = 1; i <= 13; i++){
            for (int j = 1; j <= 4; j++){
                if (j == 1){
                    deck.add(new Card(i, clubs, black));
                }

                if (j == 2){
                    deck.add(new Card(i, spades, black));
                }

                if (j == 3){
                    deck.add(new Card(i, heart, red));
                }
                if (j == 4){
                    deck.add(new Card(i, diamonds, red));
                }
            }
        }

        Collections.shuffle(deck);
    }

    public Card getNextCard(){
        if (deck.size() < 5){
            deckInitialize();
        }
      return deck.pop();
    }
}
