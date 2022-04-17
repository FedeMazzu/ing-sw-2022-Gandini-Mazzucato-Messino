package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Character;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 4
 * effect: you may move mother nature up to 2 additional islands than is
 * indicated by the assistant card you have played
 * @author
 */

public class Character4 extends Character {

    public Character4(Game game) {
        this.game = game;
        this.id = 4;
        this.price = 1;
    }

    //should pass to Board's method moveMotherNature the correct value, avoiding repeating the move
    @Override
    public void effect(){}
}
