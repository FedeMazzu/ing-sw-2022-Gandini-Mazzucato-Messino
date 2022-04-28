package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class to represent the teams
 * @author filippogandini
 */
public class Team implements Serializable {
    private List<Player> players;
    private int towerCount;
    private Tower tower;

    /**
     * Constructor
     * @param tower type of the team
     * @param gameMode which defines the number of player for Team
     */
    public Team(Tower tower, GameMode gameMode, Game game) {
        this.tower = tower;
        towerCount = gameMode.getTeamTowers();
        players = new ArrayList<>();
        for(int i=0; i< gameMode.getTeamPlayers(); i++) players.add(new Player(gameMode,game.getBoard()));
    }


    /**
     * method to add a player to the team
     * @param player added to the team
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * note that it exposes the rep
     * @return the list of players in the team
     */
    public List<Player> getPlayers() {
        return players;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTowerCount(int count) {this.towerCount = count;}
    public int getTowerCount(){return towerCount;}

}
