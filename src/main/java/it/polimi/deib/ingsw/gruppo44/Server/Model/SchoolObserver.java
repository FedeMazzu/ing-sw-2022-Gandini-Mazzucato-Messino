package it.polimi.deib.ingsw.gruppo44.Server.Model;
import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.Serializable;
import java.util.Map;

/**
 * School observer
 * @author filippogandini
 */
public class SchoolObserver implements Observer, Serializable {
    private School school;
    private SchoolData schoolData;

    public SchoolObserver(School school) {
        this.school = school;
        schoolData = new SchoolData(school.getPlayer().getTeamTower());
        update();
    }

    @Override
    public void update() {
        updateNumStudents();
        updateProfessors();
        schoolData.setPlayerMoney(school.getPlayer().getMoney());
    }

    /**
     * called when a player earns or spends money
     */
    public void updateMoney(){
        schoolData.setPlayerMoney(school.getPlayer().getMoney());
    }

    /**
     * called when a card is played
     */
    public void updateCards(){
        schoolData.setAvailableCards((school.getPlayer().showAvailableCards()));
    }

    /**
     * checks the students numbers of the school and updates schoolData
     */
    private void updateNumStudents(){
        try {
            for (Color color : Color.values()) {
                schoolData.setHallStudents(color, school.getHallStudentsNum(color));
                schoolData.setEntranceStudents(color, school.getEntranceStudentsNum(color));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }    /**
     * checks the professors of the school and updates this schoolData
     */
    private void updateProfessors(){
        try {
            for (Color color : Color.values()) {
                schoolData.setProfessors(color, school.hasProfessor(color));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public SchoolData getSchoolData() {
        return schoolData;
    }
}
