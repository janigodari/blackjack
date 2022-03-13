package com.game.blackjack;

public class Card {

    private final Integer number;
    private final String color;
    private final String symbol;

    public Card(Integer number, String color, String symbol) {
        this.number = number;
        this.color = color;
        this.symbol = symbol;
    }

    public Integer getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "number=" + number +
                ", color='" + color + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
