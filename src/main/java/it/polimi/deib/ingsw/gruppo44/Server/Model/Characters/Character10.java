package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.School;

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

    public void effect(Color h1, Color e1, Color h2, Color e2, School school) {

        if(h1!=null && e1!=null) school.swapStudentsForChar10(h1,e1);
        if(h2!=null && e2!=null) school.swapStudentsForChar10(h2,e2);

        raisePrice();
    }
}
