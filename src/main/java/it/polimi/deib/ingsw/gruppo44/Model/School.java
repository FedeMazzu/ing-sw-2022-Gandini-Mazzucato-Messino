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
    private final Player player;
    private List<School> schools;
    private Map<Color, Integer> hallStudents, entranceStudents;
    private Map<Color, Boolean> professors;
    private int schoolTowersNumber;//note that it's just for the GUI, the towers are owned by the team, not the school.
    private SchoolObserver schoolObserver;
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
        schoolObserver = new SchoolObserver(this);
        //it's necessary because the colors(keys) aren't assigned by default
        for(Color color : Color.values()){
            hallStudents.put(color, 0);
            entranceStudents.put(color, 0);
            professors.put(color, false);
        }
    }

    /**
     * method to add a student in the hall that checks(and provides) if the professor is earned
     * and if the player gets a coin
     * @param color of the student to add
     */
    public void addHallStudent(Color color){
        hallStudents.put(color, hallStudents.get(color)+1);
        earnProfessor(color);//earns the professor if deserved
        if(hallStudents.get(color) % 3 ==0) player.addCoin();
        notifyObserver();
    }

    public void addEntranceStudent(Color color){
        entranceStudents.put(color, entranceStudents.get(color)+1);
        notifyObserver();
    }
    public void addSchool(School school){
        schools.add(school);
    }

    /**
     * method to earn the professor if deserved
     * @param color of the professor it's being checked
     */
    private void earnProfessor(Color color) {
        boolean earnProfessor = true;
        int numStudents = hallStudents.get(color);
        int numStudentsOtherSchool;
        for(School s : schools) {
            if(s.hasProfessor(color)) {
                if (s.hallStudents.get(color) >= numStudents) {
                    earnProfessor = false;
                }else{
                    s.professors.put(color,false);
                }
                break; //because the professor can be owned at most by one school
            }
        }
        if(earnProfessor){
            professors.put(color, true);
        }
        notifyObserver();;
    }

    /**
     * method to notify the observer after a change
     */
    protected void notifyObserver(){ schoolObserver.update();}

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
}
