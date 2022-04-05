package it.polimi.deib.ingsw.gruppo44.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to represent the schools
 * @author filippogandini
 */
public class School {
    private Player player;
    private List<School> schools;
    private Map<Color, Integer> hallStudents, entranceStudents;
    private Map<Color, Boolean> professors;
    private int towersNumber;

    /**
     * Constructor. It initializes the maps keeping track of the students and professors
     * @param player associated to the school
     */
    public School(Player player){
        this.player = player;
        schools = new ArrayList<>();
        hallStudents = new HashMap<>();
        entranceStudents = new HashMap<>();
        professors = new HashMap<>();
        for(Color color : Color.values()){
            hallStudents.put(color, 0);
            entranceStudents.put(color, 0);
            professors.put(color, false);
        }
        towersNumber = 0;
    }

    public void addHallStudent(Color color){
        hallStudents.put(color, hallStudents.get(color)+1);
    }

    public void addEntranceStudent(Color color){
        entranceStudents.put(color, entranceStudents.get(color)+1);
    }

    /**
     * method called when adding a student in a position multiple of 3 (start counting from one)
     */
    private void getCoin(){
        player.addCoin();
    }

    /**
     * method to get the professor if deserved
     * @param color of the professor it's being checked
     */
    private void getProfessor(Color color) {
        boolean getProfessor = true;
        int numStudents = hallStudents.get(color);
        int numStudentsOtherSchool;
        for(School s : schools) {
            if(s.hasProfessor(color)) {
                if (s.getHallStudents().get(color) >= numStudents) {
                    getProfessor = false;
                    break;
                }
            }
        }
        if(getProfessor){
            professors.put(color, true);
        }
    }

    public Map<Color, Integer> getHallStudents() {
        return hallStudents;
    }

    public boolean hasProfessor(Color color){
        return professors.get(color);
    }
}
