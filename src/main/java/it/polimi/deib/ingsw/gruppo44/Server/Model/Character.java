package it.polimi.deib.ingsw.gruppo44.Server.Model;

/**
 * abstract class to represent the characters
 * @author filippogandini
 */
public abstract class Character {
    private int price;
    protected Game game;//Passed in the constructor. It must be protected for direct access in subclasses
    protected int id; // It must be protected for direct access in subclasses

    /**
     * method to edit the game because of the effect
     */
    public abstract void effect();

    public int getPrice() {return price;}

    public int getId() {return id;}
}
