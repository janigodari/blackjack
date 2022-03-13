package com.game.blackjack;
import java.util.ArrayList;
import java.util.Scanner;

public class GameCoordinator {

    private final ArrayList<Card> playerCards = new ArrayList<>();
    private final ArrayList<Card> dealerCards = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private Integer currentBet = 0;

    private final Dealer dealer = new Dealer(new Deck());
    private Player player = null;

    public void startGame(){
        player = getPlayer();
        System.out.println("\nHello " + player.getName());

        playerCards.addAll(dealer.getPlayerCard());
        dealerCards.addAll(dealer.getDealerCard());

        printPlayerCards(playerCards);
        printDealerCards(dealerCards);
        printBalance(player);

        placeBet();

        printMenuOptions();
    }

    private Player getPlayer(){

        Scanner getData = new Scanner(System.in);
        String userName;
        System.out.print("Enter Name: ");
        userName = getData.nextLine();

        int amount = 0;

        do {
            System.out.print("Enter buy-in amount: ");
            try {
                amount = getData.nextInt();
            }catch (Exception exception){
                System.out.println("Error! Number required");
            }
            getData.nextLine();
        }while (amount <= 0);

        return new Player(userName, amount);
    }

    private Integer calculateCardsPoints(ArrayList<Card> cards){
        int totalPoints = 0;
        for (Card card : cards) {
            if(card.getNumber() == 1 ){
                totalPoints = totalPoints + 11;
            }else if(card.getNumber() > 10){
                totalPoints = totalPoints + 10;
            }else{
                totalPoints = totalPoints + card.getNumber();
            }
        }

        return totalPoints;
    }

    private void printPlayerCards(ArrayList<Card> playerCards) {
        System.out.print("Getting Your Cards");
        printLoadingScreen();
        System.out.println();
        System.out.print("Your Cards: ");
        for (Card card: playerCards){
            System.out.print(String.join(" ", "[", card.getNumber().toString(), card.getSymbol(), card.getColor(), "]"));
        }
        int totalPoints = calculateCardsPoints(playerCards);
        System.out.println(" = " + totalPoints);
        System.out.println();
    }

    private void printDealerCards(ArrayList<Card> dealerCards) {
        System.out.print("Getting Dealer Cards");
        printLoadingScreen();
        System.out.println();
        System.out.print("Dealers Cards: ");
        for (Card card: dealerCards){
            System.out.print(String.join(" ", "[", card.getNumber().toString(), card.getSymbol(), card.getColor(), "]"));
        }
        int totalPoints = calculateCardsPoints(dealerCards);
        System.out.println(" = " + totalPoints);
    }

    private void waitTime(){
        try {
            Thread.sleep(500);
        }catch (Exception exc){
            System.out.println("Exception while waiting");
        }
    }

    private void  printLoadingScreen(){
        for (int i = 0; i < 3; i++){
            waitTime();
            System.out.print(".");
        }
    }

    private void printBalance(Player player){
        System.out.println("Your Balance: " + player.getCurrentBankAmount() + "$");
        System.out.println();
    }

    private void placeBet(){
        System.out.print("Place Bet: ");
        currentBet = scanner.nextInt();

        if (player.getCurrentBankAmount() < currentBet){
            System.out.println("You have enough money");
            scanner.nextLine();
            placeBet();
        }else{
            player.getAmount(currentBet);
        }

    }

    private void printMenuOptions(){
        int option = getMenuOption();
        boolean optionSelected = false;

        while (!optionSelected){
            switch (option){
                case 1:
                    standOption();
                    optionSelected = true;
                    break;

                case 2:
                    hitOption();
                    optionSelected = true;
                    break;

                case 3:
                    doubleOption();
                    optionSelected = true;
                    break;

                default:
                    System.out.println("You didnt choose any of the other options");
                    scanner.nextLine();
                    option = getMenuOption();
                    break;
            }
        }
    }

    private int getMenuOption(){

        int myChoice;
        System.out.println("Choose option: ");
        System.out.print("Press: 1-Stand 2-Hit 3-Double");
        System.out.println("Choice: ");
        myChoice = scanner.nextInt();

        return myChoice;
    }

    private boolean checkBlackJack(Integer totalPoints){
        return totalPoints == 21;
    }

    private void standOption(){
        while(calculateCardsPoints(playerCards) > calculateCardsPoints(dealerCards)){
            Card nextCard = dealer.getNextCard();
            dealerCards.add(nextCard);
            printDealerCards(dealerCards);
        }

        if(calculateCardsPoints(dealerCards) > 21){
            System.out.println("Player has won");
            player.addAmount(currentBet*2);
            System.out.println("Your balance is: " + player.getCurrentBankAmount());
        }else if(calculateCardsPoints(dealerCards).equals((calculateCardsPoints(playerCards)))) {
            System.out.println("You have equal cards");
            player.addAmount(currentBet);
        }else {
            System.out.println("Dealer has won");
            System.out.println("Your balance is: " + player.getCurrentBankAmount());
        }
    }

    private void hitOption() {
        Card nextCard = dealer.getNextCard();
        playerCards.add(nextCard);
        printPlayerCards(playerCards);

        if (calculateCardsPoints(playerCards) > 21) {
            System.out.println("You Lose");
            System.out.println("Your balance is: " + player.getCurrentBankAmount());

        } else {
            if (calculateCardsPoints(playerCards).equals((calculateCardsPoints(dealerCards)))) {
                System.out.println("You are equal");
                player.addAmount(currentBet);
            } else if (calculateCardsPoints(dealerCards) > 21) {
                System.out.println("Dealer Loses");
                player.addAmount(currentBet * 2);
            }
                dealerCards.add(nextCard);
                printDealerCards(dealerCards);
        }
    }

    private void doubleOption() {
        player.getAmount(currentBet);
        Card nextCard = dealer.getNextCard();
        playerCards.add(nextCard);
        currentBet = currentBet * 2;
        printPlayerCards(playerCards);
        while (true) {
            if (checkBlackJack(calculateCardsPoints(playerCards))) {
                System.out.println("Player Has won");
                player.addAmount(currentBet * 2);
                System.out.println("Your balance is: " + player.getCurrentBankAmount());
                break;
            } else if (calculateCardsPoints(playerCards) > 21) {
                System.out.println("You Lose");
                System.out.println("Your balance is: " + player.getCurrentBankAmount());
                break;
            } else if (calculateCardsPoints(playerCards) > calculateCardsPoints(dealerCards)) {
                dealerCards.add(nextCard);
                printDealerCards(dealerCards);
            } else if (calculateCardsPoints(playerCards) < calculateCardsPoints(dealerCards)) {
                if (calculateCardsPoints(dealerCards) > 21) {
                    System.out.println("Dealer Loses");
                    player.addAmount(currentBet * 2);
                    System.out.println("Your balance is: " + player.getCurrentBankAmount());
                    break;
                }
                System.out.println("You Win");
                System.out.println("Your balance is: " + player.getCurrentBankAmount());
                break;
            } else if (calculateCardsPoints(playerCards).equals((calculateCardsPoints(dealerCards)))) {
                System.out.println("You are equal");
                player.addAmount(currentBet);
                break;
            } else if (calculateCardsPoints(dealerCards) > 21) {
                System.out.println("Dealer Loses");
                player.addAmount(currentBet * 2);
                break;
            }
        }
    }
        }