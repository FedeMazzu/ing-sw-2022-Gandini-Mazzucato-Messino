package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;


/**
 * Class to represent the Character 6
 * effect: when resolving a Conquering on an island,
 * Towers do not count towards influence
 */
public class Character6 extends Character {
    public Character6(Game game, BoardObserver boardObserver) {
        this.boardObserver = boardObserver;
        this.game = game;
        this.id = 6;
        this.price = 3;
    }

    public void effect(Player player) {
        //managed in class UnionFind inside the influence score computation
        player.spendMoney(price);
        raisePrice();
    }
}
