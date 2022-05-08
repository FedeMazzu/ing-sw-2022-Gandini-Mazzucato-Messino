package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

/**
 * Class to represent the Character 3
 * effect: choose an island and resolve island as if Mother nature had ended her movement there.
 * Mother nature will still move and the island where she ends her movement will also be resolved
 * WE ASSUME THE CHOICE IS DONE BEFORE STARTING THE STANDARD TURN
 * @author filippogandini
 */
public class Character3 extends Character {
    
    public Character3(Game game, BoardObserver boardObserver){
        this.boardObserver = boardObserver;
        this.game = game;
        this.id =3;
        this.price = 3;
    }

    public void effect(int islandId, Player player){
        //ask islandId to the player
        //int islandId = 4; //just for example
        game.getBoard().getUnionFind().getIsland(islandId).islandClaim();
        player.spendMoney(price);
        raisePrice();
    }
}
