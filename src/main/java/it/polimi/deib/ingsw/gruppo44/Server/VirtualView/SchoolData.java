package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.School;

import java.util.List;
import java.util.Map;

/**
 * School observer in the Virtual View
 */
public class SchoolData implements Observer {
    private School school;
    private Map<Color, Integer> hallStudents, entranceStudents;
    private Map<Color, Boolean> professors;
    private int schoolTowersNumber;//note that it's just for the GUI, the towers are owned by the team, not the school.
    private final int maxEntranceStudentsNum;
    private final Magician magician; //to identify the player who owns the School

    public SchoolData(School school) {
        this.school = school;
        this.maxEntranceStudentsNum = school.getMaxEntranceStudentsNum();
        magician = school.getPlayer().getMagician();
        //in this case it is an initialization
        update();
    }

    @Override
    public void update() {
        updateNumStudents();
        updateProfessors();

    }

    /**
     * checks the students numbers of the school and updates this class
     */
    public void updateNumStudents(){
        try {
            for (Color color : Color.values()) {
                hallStudents.put(color, school.getHallStudentsNum(color));
                entranceStudents.put(color, school.getEntranceStudentsNum(color));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }    /**
     * checks the professors of the school and updates this class
     */
    public void updateProfessors(){
        try {
            for (Color color : Color.values()) {
                professors.put(color, school.hasProfessor(color));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }
}
