package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * class to represent the teams
 * @author filippogandini
 */
public class Team implements Data{
    private List<Player> players;
    private int towerNumber;
    private Tower tower;

    public Team(Tower tower) {
        this.tower = tower;
        towerNumber = 0;
        players = new ArrayList<>();
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * note that it exposes the rep
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    public Tower getTower() {
        return tower;
    }

    /**
     * method to save the team's data
     */
    @Override
    public void save(){}
}
