package it.polimi.deib.ingsw.gruppo44.Server.Model;

/**
 * abstract class to represent the characters
 * @author filippogandini
 */
public abstract class Character {
    private int price;

    /**
     * method to edit the game because of the effect
     */
    public abstract void effect(Game game);

    public int getPrice() {return price;}
}
