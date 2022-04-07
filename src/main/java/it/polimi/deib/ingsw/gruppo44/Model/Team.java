package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * class to represent the teams
 * @author filippogandini
 */
public class Team implements Data{
    private List<Player> players;
    private int towerCount;
    private Tower tower;

    public Team(Tower tower) {
        this.tower = tower;
        players = new ArrayList<>();
    }

    /**
     * method to save the team's data
     */
    @Override
    public void save(){}

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
