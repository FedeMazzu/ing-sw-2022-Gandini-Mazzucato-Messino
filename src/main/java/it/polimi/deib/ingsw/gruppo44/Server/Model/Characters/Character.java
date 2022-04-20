package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * abstract class to represent the characters
 * Factory pattern to instantiate the characters?
 * @author filippogandini
 */
public abstract class Character {
    protected int price;
    protected boolean alreadyUsed = false;
    protected Game game;//Passed in the constructor. It must be protected for direct access in subclasses
    protected int id; // It must be protected for direct access in subclasses

    /**
     * method to edit the game because of the effect
     */
    public abstract void effect();

    /**
     * raises the price after the first usage
     */
    public void raisePrice(){
        if(!alreadyUsed){
            price++;
            alreadyUsed = true;
        }
    }

    public int getPrice() {return price;}

    public int getId() {return id;}
}