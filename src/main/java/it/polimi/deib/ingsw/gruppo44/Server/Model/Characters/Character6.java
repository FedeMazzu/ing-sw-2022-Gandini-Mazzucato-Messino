package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Character;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 6
 * effect: when resolving a Conquering on an island,
 * Towers do not count towards influence
 * @author
 */
public class Character6 extends Character {
    public Character6(Game game) {
        this.game = game;
        this.id = 6;
        this.price = 3;
    }
    @Override
    public void effect() {

    }
}
