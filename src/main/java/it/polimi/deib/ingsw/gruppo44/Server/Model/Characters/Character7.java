package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 7
 * In set up draw 6 students and place them on this card
 * effect: You may take 3 students from this card and replace them with the same number of students from
 * your entrance
 * @author
 */
public class Character7 extends Character {
    public Character7(Game game) {
        this.game = game;
        this.id = 7;
        this.price = 1;
    }
    @Override
    public void effect() {

        raisePrice();
    }
}
