package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 2
 * effect: during this turn you take control of any number of Professors
 * even if you have the same number of Students as the player who currently controls them
 * @author
 */
public class Character2 extends Character {
    public Character2(Game game, BoardObserver boardObserver) {
        this.boardObserver = boardObserver;
        this.game = game;
        this.id = 2;
        this.price = 2;
    }

    public void effect() {

        raisePrice();
    }
}
