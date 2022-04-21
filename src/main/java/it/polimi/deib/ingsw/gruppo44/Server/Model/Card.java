package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.Serializable;

/**
 * enum to represent the cards/Assistants
 * @author filippogandini
 */

public enum Card implements Serializable {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);
    private final int value; //the mother nature steps are computable as  int(Value/2) (+1 if value is odd)
    //private final Magician magician;
    private boolean played = false;

    private Card(int value) {
        this.value = value;
    }

    /**
     * method to signal that a card has been played
     */
    public void playCard(){
        played = true;
    }

    /**
     * method useful just for testing. Useful to set the status for the PlayerTest class
     */
    public void setPlayedFalse(){
        this.played = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isPlayed() {
        return played;
    }
}



