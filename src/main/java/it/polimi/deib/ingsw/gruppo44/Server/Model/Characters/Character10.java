package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 10
 * effect: you may exchange up to 2 students between your entrance and your dining room
 * @author
 */
public class Character10 extends Character {
    public Character10(Game game, BoardObserver boardObserver) {
        this.boardObserver = boardObserver;
        this.game = game;
        this.id = 10;
        this.price = 1;
    }

    public void effect() {

        raisePrice();
    }
}
