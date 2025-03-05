package edu.guilford;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        int numberOfPlayers = 16;
        Deck deck = new Deck();

        deck.shuffle();

        ArrayList<Player> players = new ArrayList<>();

        // create an array list, calling default constructor for player, and adding 3
        // cards to each player's hand

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player());
        }

        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                player.addToHand(deck.deal());
            }
        }

        // A stack object representing the discard pile. Cards are added to the top of
        // the stack and removed from the top of the stack.
        Stack<Card> discard = new Stack<>();

        Queue<Card> stockpile = new LinkedList<>();
        stockpile.addAll(deck.getCards());

        int round = 1;
        while (players.size() > 1) {
            System.out.println("Round " + round);
            System.out.println(players); 
            if(round == 1) {
                discard.push(stockpile.poll()); 
                for (Player player : players) {
                    player.scoreAllSuits();
                    Card toReplace = player.lowestCard();
                    if(player.shouldTakeFromDiscard(discard.peek())) {
                        player.addToHand(discard.pop());
                        if(!player.shouldPlaceAtBottomOfStockpile(toReplace, stockpile)) { 
                            discard.add(toReplace);
                        }
                    } else {
                        stockpile.add(toReplace);
                    }
                    player.scoreAllSuits();
                   
                
                }
                }
            else {

            if(stockpile.isEmpty()) {
                stockpile.addAll(discard);
                 discard.push(stockpile.poll());
            }
            boolean noKnock = true;
            Player knocked = null;
            if(noKnock)
            {
            for (Player player : players) {
                player.scoreAllSuits();

                if(player.shouldKnock() && noKnock == true) { 
                    knocked = player;
                    noKnock = false;
                    continue;
                }
                Card toReplace = player.lowestCard();
                if(player.shouldTakeFromDiscard(discard.peek())) {
                    player.addToHand(discard.pop());
                    if(!player.shouldPlaceAtBottomOfStockpile(toReplace, stockpile)) { 
                        discard.add(toReplace);
                    }
                } else {
                    stockpile.add(toReplace);
                }
                player.scoreAllSuits();
                }
            }

            if (knocked != null) {
                knock(players, knocked);
            }
            }
            // each round, players removed if they have 0 lives
            removePlayers(players);
            round++;
            // print out a big demarkation line
            System.out.println("-------------------------------------------------");
        }

    if(players.isEmpty())

    {
        System.out.println("No players left");
    }else
    {
        System.out.println("The winner is " + players.get(0));
    }

    }

    /***
     * Removes players using Iterator to avoid ConcurrentModificationException
     * @param players
     */
    private static void removePlayers(ArrayList<Player> players) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (!player.isAlive()) {
                iterator.remove();
            }
        }
    }

    /***
     * Knock method, calculates scores and changes lives
     * @param players
     * @param knocked
     */
    private static void knock(ArrayList<Player> players, Player knocked) {

        HashMap<Integer, Player> scores = new HashMap<>();

        for (Player player : players) {
            scores.put(player.getScore(), player);
        }

        boolean contains31 = scores.keySet().contains(31);

        for (Player player : players) {
            player.changeLives(scores, player == knocked, contains31);
        }
    }

    // private static class KnockResults {
    // private Player winner;
    // private ArrayList<Player> players;
    // public KnockResults(Player winner, ArrayList<Player> players) {
    // this.winner = winner;
    // this.players = players;
    // }
    // public Player getWinner() {
    // return winner;
    // }
    // public ArrayList<Player> getPlayers() {
    // return players;
    // }

    // //setters
    // public void setWinner(Player winner) {
    // this.winner = winner;
    // }
    // public void setPlayers(ArrayList<Player> players) {
    // this.players = players;
    // }
    // }

}