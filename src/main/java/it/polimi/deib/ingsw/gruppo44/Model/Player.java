package it.polimi.deib.ingsw.gruppo44.Model;

/**
 * class to represent the player
 * @author filippogandini
 */

public class Player {
    private String name;
    private int port;
    private String IP;
    private School school;
    private int money;
    private Magician magician;
    private Card[] deck = {Card.ONE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, Card.EIGHT, Card.NINE, Card.TEN};

    public Player(String name, String IP, int port, School school, Magician magician) {
        this.name = name;
        this.IP = IP;
        this.port = port;
        this.school = school;
        this.money = 0;
        this.magician = magician;
    }

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

    public School getSchool() {
        return school;
    }
}
