package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 4
 * effect: you may move mother nature up to 2 additional islands than is
 * indicated by the assistant card you have played
 * @author
 */

public class Character4 extends Character {

    public Character4(Game game, BoardObserver boardObserver) {
        this.boardObserver = boardObserver;
        this.game = game;
        this.id = 4;
        this.price = 1;
    }


    public void effect(){
        //client controller enables to move two steps more
        raisePrice();
    }
}
