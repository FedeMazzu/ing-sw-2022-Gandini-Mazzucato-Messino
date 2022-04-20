package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

/**
 * Class to represent the Character 3
 * effect: choose an island and resolve island as idf Mother nature had ended her movement there.
 * Mother nature will still move and the island where she ends her movement will also be resolved
 * @author filippogandini
 */
public class Character3 extends Character {
    
    public Character3(Game game){
        this.game = game;
        this.id =3;
        this.price = 3;
    }

    public void effect(){
        //ask islandId to the player
        int islandId = 4; //just for example
        game.getBoard().getUnionFind().getIsland(islandId).islandClaim();

        raisePrice();
    }
}
