package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.util.ArrayList;
import java.util.List;


/**
 * class to represent the player
 * @author filippogandini
 */

public class Player {
    private String name;
    private School school;
    private int money;
    private Magician magician; // the identifier of the player
    private Card[] deck;
    private NotOwnedObjects notOwnedObjects;

    /**
     * constructor, the other attributes are initialized by the setters called in the start stage of the Game controller
     * @param gameMode necessary to establish the max number of students allowed in the entrance
     */
    public Player(GameMode gameMode, Board board){
        this.school = new School(this,gameMode.getPlayerEntranceStudents());
        if(gameMode.isExpertMode()) this.money = 0;
        this.deck = new Card[]{Card.ONE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, Card.EIGHT, Card.NINE, Card.TEN};
        notOwnedObjects = board.getNotOwnedObjects();
    }

    /**
     * method to increase the money value of one
     */
    public void addCoin(){
        money++;
        notOwnedObjects.giveCoin();
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
     * method to play a character. Game reference in player?
     */
    /*public void playCharacter(Character character) {
        try {
            if (money > character.getPrice()) {
                for (int i = 0; i < character.getPrice(); i++) {
                    money--;
                    notOwnedObjects.receiveCoin();
                }
                character.effect(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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

    public void setMagician(Magician magician) {
        this.magician = magician;
    }

    public void setName(String name) {
        this.name = name;
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
