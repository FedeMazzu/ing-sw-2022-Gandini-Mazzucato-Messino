package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.Model.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * School in the Virtual View
 */
public class SchoolData implements Serializable {
    private Map<Color, Integer> hallStudents, entranceStudents;
    private Map<Color, Boolean> professors;
    private int playerMoney;
    private String playerName;
    private Tower teamTower;
    private Magician magician; //identifier
    private List<Integer> availableCards;

    public SchoolData( Tower teamTower) {
        hallStudents = new HashMap<>();
        entranceStudents = new HashMap<>();
        professors = new HashMap<>();
        this.teamTower = teamTower;
        //naive initialization
    }

    /**
     * set the number of hall students
     */
    public void setHallStudents(Color color,int value){
        hallStudents.put(color,value);
    }
    /**
     * set the number of entrance students
     */
    public void setEntranceStudents(Color color,int value){
        entranceStudents.put(color,value);
    }
    /**
     * set the professors
     */
    public void setProfessors(Color color,boolean value){
        professors.put(color,value);
    }

    public void setPlayerMoney(int playerMoney){
        this.playerMoney = playerMoney;
    }

    /**
     * @param color of the professor to check
     * @return true if the school has the professor of the passed color (false otherwise)
     */
    public boolean hasProfessor(Color color){
        return professors.get(color);
    }

    /**
     * @param color
     * @return the number of hallStudents of color
     */
    public int getHallStudentsNum(Color color){
        return hallStudents.get(color);
    }

    /**
     * @param color
     * @return the number of entranceStudents of color
     */
    public int getEntranceStudentsNum(Color color){
        return entranceStudents.get(color);
    }

    public void setMagician(Magician magician) {
        this.magician = magician;
    }

    public Magician getMagician() {
        return magician;
    }

    public void setPlayerName(String playerName) {this.playerName = playerName;}

    public String getPlayerName() {return playerName;}

    public Tower getTeamTower() {return teamTower;}

    public int getPlayerMoney() {
        return playerMoney;
    }

    public List<Integer> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<Integer> availableCards) {
        this.availableCards = availableCards;
    }
}
