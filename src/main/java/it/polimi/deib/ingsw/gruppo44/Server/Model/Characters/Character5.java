package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Character;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 5
 * In set up put the 4 No Entry tiles on this card.
 * effect: Place a no Entry tile pn an island of your choice.
 * The first time mother nature ends her movement there, put the no Entry tile back onto this card,
 * do not calculate influence on that island or place any tower
 * @author
 */
public class Character5 extends Character {
    public Character5(Game game) {
        this.game = game;
        this.id = 5;
        this.price = 2;
    }
    @Override
    public void effect(){

    }
}