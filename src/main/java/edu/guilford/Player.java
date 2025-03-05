package edu.guilford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;

public class Player {

    ArrayList<Card> hand = new ArrayList<>();
    private int score;
    private int lives;
    HashMap<Card.Suit, Integer> scoresInHand;
    Card.Suit highScoreSuit;
    String name;

    static String[] names = { "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy",
            "Kevin", "Linda", "Mallory", "Nancy", "Oscar", "Peggy", "Quentin", "Romeo", "Sue", "Trent", "Ursula",
            "Victor", "Walter", "Xavier", "Yvonne", "Zelda" };

    // private Strategy strategy;

    public Player() {
        hand = new ArrayList<>();
        lives = 3;
        scoresInHand = new HashMap<>();
        // get a random english name for the player
        name = names[(int) (Math.random() * names.length)];
    }

    public Player(ArrayList<Card> hand, int lives) {
        this.lives = lives;
        this.hand = hand;
        scoresInHand = new HashMap<>();
        // this.strategy = strategy;
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

    /***
     * Change lives based off of scores from the game
     * @param scores
     * @param didKnock
     * @param contains31
     */
    public void changeLives(HashMap<Integer, Player> scores, boolean didKnock, boolean contains31) {
        if (contains31 && score != 31) {
            lives = 0;
        } else if (didKnock && Collections.min(scores.keySet()) == score) {
            lives -= 2;
        } else if (Collections.min(scores.keySet()) == score) {
            lives--;
        }
    }

    // public void changeLives(boolean lost, boolean didKnock, int highestScore) {

    // //if they lost and knocked, subtract two, if they just lost subtract one, if
    // they lost and highestscore was 31 remove all lives, check for 31 first
    // if (highestScore == 31 && lost) {
    // lives = 0;
    // } else if (lost && didKnock) {
    // lives -= 2;
    // } else if (lost) {
    // lives--;
    // }
    // }

    public boolean isAlive() {
        return lives > 0;
    }

    public Card discardCard(Card card) {
        hand.remove(card);
        return card;
    }

    private static class CardReplacementResults {
        private Card card;
        private HashMap<Card.Suit, Integer> scores;

        public CardReplacementResults(Card card, HashMap<Card.Suit, Integer> scores) {
            this.card = card;
            this.scores = scores;
        }

        public Card getCard() {
            return card;
        }

        public HashMap<Card.Suit, Integer> getScores() {
            return scores;
        }

        public Card.Suit getHighScoreSuit() {
            return Collections.max(scores.entrySet(), HashMap.Entry.comparingByValue()).getKey();
        }

    }

    /***
     * To see theoritical scores of all suits after replacing a card in the hand
     * with a card from the discard pile, since u can see the card in the discard
     * pile before picking it up
     * 
     * @param card
     */
    public Card scoreAllSuits(Card card) {
        ArrayList<Card> handCopy = new ArrayList<>(hand);

        // find the best card to replace in the hand
        Card bestCard = hand.get(0);
        int bestScore = 0;
        for (Card c : hand) {
            handCopy.remove(c);
            handCopy.add(card);
            HashMap<Card.Suit, Integer> scores = scoreAllSuits(handCopy);
            int score = Collections.max(scores.values());
            if (score > bestScore) {
                bestScore = score;
                bestCard = c;
            }
            handCopy.remove(card);
            handCopy.add(c);
        }

        // either return the best card to replace or return null
        if (bestScore > score) {
            return bestCard;
        }
        return null;
    }

    /***
     * Score all suits in a hand
     * @param hand
     * @return
     */
    public HashMap<Card.Suit, Integer> scoreAllSuits(ArrayList<Card> hand) {
        HashMap<Card.Suit, Integer> scores = new HashMap<>();
        scores.put(Card.Suit.SPADES, 0);
        scores.put(Card.Suit.HEARTS, 0);
        scores.put(Card.Suit.DIAMONDS, 0);
        scores.put(Card.Suit.CLUBS, 0);

        for (Card card : hand) {
            scores.put(card.getSuit(), scores.get(card.getSuit()) + card.getScoringValue());
        }
        return scores;
    }

    /***
     * ONly method that actually changes the values of the player
     * 
     */
    public void scoreAllSuits() {
        scoresInHand = scoreAllSuits(hand);
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
                name +
                ", score=" + score +
                ", lives=" + lives +
                '}';
    }

    // shouldKnock
    public boolean shouldKnock() {
        if(score <= 31 || score > 20) {
            return true;
        }
        return false;
    }

    // look at the card at the top of the discard,
    // see if grabbing it increases the total score of the hand,
    // if it doesnt then look at ur own score, if its low OR you have one card or
    // more of a different suit then look at the top of the stockpile--
    // if score is above 27, then do nothing if discard isnt good
    // when choosing where to put the discard, if the card is good and the stockpile
    // is large, put it at bottom of stockpile, if the card is bad put it on the
    // discard pile

    /***
     * Check if the card is higher then the lowest card of the highest scoring suit
     * @param card
     * @return true if true 
     */
    public boolean isThisCardHigherThenLowestOfHighSuit(Card card) {

        // get the lowest rank of the highest suit
        Card lowestRankHighestSuit = lowestRankHighestSuit();
        if (card.getSuit() == highScoreSuit && card.getScoringValue() > lowestRankHighestSuit.getScoringValue()) {
            return true;
        }
        return false;
    }

    /***
     * Get lowest rank highest suit through .stream() and .min()
     * @return card 
     */
    public Card lowestRankHighestSuit() {
        return hand.stream()
                .filter(c -> c.getSuit() == highScoreSuit)
                .min((c1, c2) -> Integer.compare(c1.getRank().getValue(), c2.getRank().getValue()))
                .orElse(null);
    }

    /***
     * Returns lowest card in general 
     * @return
     */
    public Card lowestCard() {
        return hand.stream()
                .min((c1, c2) -> Integer.compare(c1.getRank().getValue(), c2.getRank().getValue()))
                .orElse(null);
    }

    /***
     * Check if the card in the discard pile is worth taking 
     * 
     * @param discard
     * @return true if the card is worth taking
     * @see #isThisCardHigherThenLowestOfHighSuit(Card)
     */
    public boolean shouldTakeFromDiscard(Card discard) {
        if (isThisCardHigherThenLowestOfHighSuit(discard)) {
            return true;
        } else if (discard.getRank().getValue() > score) {
            return true;
        }
        return false;
    }

    /***
     * Check if the card should be placed at the bottom of the stockpile
     * @return true if the card should be placed at the bottom of the stockpile
     * @see #isThisCardHigherThenLowestOfHighSuit(Card)
     * 
     */
    public boolean shouldPlaceAtBottomOfStockpile(Card toDiscard, Queue<Card> stockpile) {
        return isThisCardHigherThenLowestOfHighSuit(toDiscard) && stockpile.size() > 1;
    }



    // public boolean should

    // //card to discard
    // public boolean shouldDiscard(Card card) {
    // return scoresInHand.get(card.getSuit()) < 5;
    // }

}
