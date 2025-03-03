package edu.guilford;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of cards. Uses ArrayList to store the cards.
 */
public class Deck {

    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for(Card.Suit suit : Card.Suit.values()) {
            for(Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /***
     * Calls the shuffle method from the Collections class to shuffle the deck.
     */
    public void shuffle(){ 
        Collections.shuffle(cards);
    }

    /***
     * Removes the first card from the deck and returns it.
     * @return the first card in the deck
     */
    public Card deal() {
        return cards.remove(0);
    }

    /***
     * Returns the number of cards in the deck.
     * @return the number of cards in the deck
     */
    public int size() {
        return cards.size();
    }



    @Override
    public String toString() {
        String deckString = "";
        for(Card card : cards) {
            deckString += card + "\n";
        }
        return deckString;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


}
