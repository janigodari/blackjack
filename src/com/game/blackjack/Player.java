package com.game.blackjack;

public class Player {

    private final String name;
    private Integer moneyAmount;


    public Player(String name, Integer moneyAmount) {
        this.name = name;
        this.moneyAmount = moneyAmount;
    }

    public void addAmount(Integer amount){
        moneyAmount = moneyAmount + amount;
    }

   public Integer getAmount(Integer amount){
        if(amount > moneyAmount){
            return 0;
        }
        moneyAmount = moneyAmount - amount;
        return amount;
    }

    public Integer getCurrentBankAmount(){
        return moneyAmount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
