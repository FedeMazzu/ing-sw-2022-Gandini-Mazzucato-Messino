package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

/**
 * Class to represent the Character 9
 * effect: choose a color of Students: during the influence calculation this turn,
 * that color adds no influence
 * @author
 */
public class Character9 extends Character {

    public Character9(Game game, BoardObserver boardObserver) {
        this.boardObserver = boardObserver;
        this.game = game;
        this.id = 9;
        this.price = 3;
    }

    public void effect(Player player) {
        //handled in UnionFind
        player.spendMoney(price);
        raisePrice();
    }
}
