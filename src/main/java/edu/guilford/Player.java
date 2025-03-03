package edu.guilford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Player  {
    

    ArrayList<Card> hand = new ArrayList<>();
    private int score; 
    private int lives; 
    HashMap<Card.Suit, Integer> scoresInHand; 
    Card.Suit highScoreSuit;
    String name; 

    static String [] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy", "Kevin", "Linda", "Mallory", "Nancy", "Oscar", "Peggy", "Quentin", "Romeo", "Sue", "Trent", "Ursula", "Victor", "Walter", "Xavier", "Yvonne", "Zelda"};

   // private Strategy strategy;

   public Player() { 
         hand = new ArrayList<>();
         lives = 3;
         scoresInHand = new HashMap<>();
         //get a random english name for the player
            name = names[(int)(Math.random() * names.length)];
   }


    public Player(ArrayList<Card> hand, int lives) {
        this.lives = lives;
        this.hand = hand;
        scoresInHand = new HashMap<>();
      //  this.strategy = strategy;
    }


    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public void changeLives(HashMap<Integer, Player> scores, boolean didKnock, boolean contains31) { 
        if(contains31 && score != 31) { 
            lives = 0;
        }
        else if (didKnock && Collections.min(scores.keySet()) == score) {
            lives -= 2;
        } else if (Collections.min(scores.keySet()) == score) {
            lives--;
        }
    }

    // public void changeLives(boolean lost, boolean didKnock, int highestScore) {

    //     //if they lost and knocked, subtract two, if they just lost subtract one, if they lost and highestscore was 31 remove all lives, check for 31 first 
    //     if (highestScore == 31 && lost) {
    //         lives = 0;
    //     } else if (lost && didKnock) {
    //         lives -= 2;
    //     } else if (lost) {
    //         lives--;
    //     }
    // }
    
    public boolean isAlive() {
        return lives > 0;
    }
    

    public Card discardCard(Card card) {
        hand.remove(card);
        return card;
    }

    /***
     * Adds up every card in the hand and returns a HashMap with the score of each suit.
     */
    public void scoreAllSuits() { 
        scoresInHand.put(Card.Suit.SPADES, 0);
        scoresInHand.put(Card.Suit.HEARTS, 0);
        scoresInHand.put(Card.Suit.DIAMONDS, 0);
        scoresInHand.put(Card.Suit.CLUBS, 0);

        for (Card card : hand) {
            scoresInHand.put(card.getSuit(), scoresInHand.get(card.getSuit()) + card.getScoringValue());
        }

        score = Collections.max(scoresInHand.values());
        highScoreSuit = Collections.max(scoresInHand.entrySet(), HashMap.Entry.comparingByValue()).getKey();
    }


    public int getScore() { 
        return score; 
    }

    public Card.Suit getHighScoreSuit() {
        return highScoreSuit;
    }

    public int getLives() {
        return lives;
    }
    @Override
    public String toString() {
        return "Player{" +
                name  +
                ", score=" + score +
                ", lives=" + lives +
                '}';
    }

    //shouldKnock 
    public boolean shouldKnock() {
        return score >= 25;
    }

    //card to discard 
    public boolean shouldDiscard(Card card) {
        return scoresInHand.get(card.getSuit()) < 5;
    }

    










    
}
