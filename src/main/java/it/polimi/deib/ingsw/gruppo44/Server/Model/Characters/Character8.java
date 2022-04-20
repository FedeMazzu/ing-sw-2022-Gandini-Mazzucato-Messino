package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 8
 * effect; during the influence calculation this turn, you count as having two more influence
 * @author
 */
public class Character8 extends Character {

    public Character8(Game game) {
        this.game = game;
        this.id = 8;
        this.price = 2;
    }
    @Override
    public void effect() {

        raisePrice();
    }
}
