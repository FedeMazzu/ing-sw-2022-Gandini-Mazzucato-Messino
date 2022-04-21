package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.School;

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
    //identifier
    private Magician magician;

    public SchoolData() {
        hallStudents = new HashMap<>();
        entranceStudents = new HashMap<>();
        professors = new HashMap<>();
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


}
