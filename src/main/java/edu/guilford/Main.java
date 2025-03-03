package edu.guilford;

import java.util.ArrayList;
import java.util.Collections;
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
            for (Player player : players) {


                discard.push(player.discardCard(player.getHand().get(0)));

                if (stockpile.isEmpty()) {
                    player.addToHand(discard.pop()); //very annoying bug where .get(0) was used instead of .pop() and that somehow meant that the end hand ended up being all duplicates of one card
                } else {
                    player.addToHand(stockpile.poll());
                }

                player.scoreAllSuits();

                System.out.println(player + " has " + player.getLives() + " lives and " + player.getScore() + " points with hand " + player.getHand()); 

            }

            System.out.println("Player " + players.get(0) + " knocks"); 
            knock(players, players.get( 0));
            // each round, players removed if they have 0 lives
            removePlayers(players);
            round++;
            //print out a big demarkation line 
            System.out.println("-------------------------------------------------"); 
        }

        System.out.println("The winner is " + players.get(0));

    }

    private static void removePlayers(ArrayList<Player> players) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (!player.isAlive()) {
                iterator.remove();
            }
        }
    }

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