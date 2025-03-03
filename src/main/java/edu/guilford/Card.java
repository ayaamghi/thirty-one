package edu.guilford;


/***
 * Represents a playing card with a suit and rank.
 */
public class Card {


    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
        
    }

    /***
     * Represents the rank of a card. Ace is 11.
     */
    public enum Rank {
        //ordinal doesn't work for face and ace cards
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
        private final int value; 
        Rank(int value) { 
            this.value = value; 
        }
        public int getValue() { 
            return value; 
        }


    }
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getScoringValue() {
        return rank.getValue();
    }


    @Override
    public String toString() {
        return rank + " of " + suit;
    }



    

}
