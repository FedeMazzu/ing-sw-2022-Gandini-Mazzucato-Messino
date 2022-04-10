package it.polimi.deib.ingsw.gruppo44.Server.Model;

/**
 * abstract class to represent the chararcters
 * @author filippogandini
 */
public abstract class Character {
    private int price;
    private int superprice;

    /**
     * method to edit the game because of the effect
     */
    public abstract void effect();

    /**
     * @return the current price of the character
     */
    //public int computePrice(){}


}
