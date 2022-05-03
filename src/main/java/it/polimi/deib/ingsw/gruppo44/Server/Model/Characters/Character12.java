package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 12
 * effect: choose a type of student: every player (including yourself) must return 3 students
 * of that type from their dining room to the bag. If any player as fewer than 3 students of
 * that type,return as many students as they have.
 * @author
 */
public class Character12 extends Character {
    public Character12(Game game, BoardObserver boardObserver) {
        this.boardObserver = boardObserver;
        this.game = game;
        this.id = 12;
        this.price = 3;
    }

    public void effect() {

        raisePrice();
    }
}
