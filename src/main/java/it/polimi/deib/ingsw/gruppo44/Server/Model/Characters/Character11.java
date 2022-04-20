package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 11
 * in set up draw four students and place them on this card
 * effect: take one student from this card and place it in your dining room.
 * Then, draw a new student from the bag and place it on this card
 * @author
 */
public class Character11 extends Character {

    public Character11(Game game) {
        this.game = game;
        this.id = 11;
        this.price = 2;
    }
    @Override
    public void effect() {

        raisePrice();
    }
}
