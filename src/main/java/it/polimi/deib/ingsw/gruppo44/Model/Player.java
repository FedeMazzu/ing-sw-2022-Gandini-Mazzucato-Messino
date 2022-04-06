package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * class to represent the player
 * @author filippogandini
 */

public class Player {
    private final String name;
    private final int port;
    private final String IP;
    private final School school;
    private int money;
    private final Magician magician;
    private Card[] deck;

    /**
     * Constructor. Note that the instantiation of a player implies the instantiation of a School
     */
    public Player(String name, String IP, int port, Magician magician) {
        this.name = name;
        this.IP = IP;
        this.port = port;
        this.school = new School(this);
        this.money = 0;
        this.magician = magician;
        this.deck = new Card[]{Card.ONE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, Card.EIGHT, Card.NINE, Card.TEN};
    }

    /**
     * method to increase the money value of one
     */
    public void addCoin(){
        money++;
    }

    /**
     * method to signal the play of a card
     * @param value of the card played
     */
    public void playCard(int value){
        Card playingCard;
        for(Card card: deck){
            if(value == card.getValue()){
                card.playCard();
                break;
            }
        }

    }

    /**
     * Perhaps this can be useful for the view
     * @return a list of the cards not played yet
     */
    public List<Card> showAvailableCards(){
        List<Card> availableCards = new ArrayList<>();
        for(Card card : deck){
            if(!card.isPlayed()) availableCards.add(card);
        }
        return availableCards;
    }

    /**
     * the deck's index is value-1 because the card X is in position X-1
     * @param value associated to the card
     * @return the card
     */
    public Card getCard(int value){
        return deck[value-1];
    }

    public int getMoney() {
        return money;
    }
    public School getSchool() {
        return school;
    }
    public Magician getMagician() {return magician; }

}
