package it.polimi.deib.ingsw.gruppo44.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test the School class
 * @author filippogandini
 */
class SchoolTest {
    private School school;
    private Player player;
    @BeforeEach
    void setUp() {
        player = new Player("Filippo","127000000000",3084,Magician.MONK,GameMode.TwoPlayersBasic);
        school = new School(player,7);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void School__wocd() {
        for (Color color : Color.values()) {
            assertEquals(0, school.getEntranceStudentsNum(color));
            assertEquals(0, school.getHallStudentsNum(color));
            assertEquals(0, school.getEntranceStudentsNum(color));
            assertFalse(school.hasProfessor(color));
        }
    }

    @Test
    /**
     * tests the situation in the first round when the professors aren't owned by someone
     * note that it also tests the private method earnProfessor
     */
    public void addHallStudent1__wocd(){
        for(Color color: Color.values()){
            if(color != Color.GREEN) school.addHallStudent(color);

        }
        for(Color color: Color.values()){
            if(color != Color.GREEN) {
                assertEquals(1, school.getHallStudentsNum(color));
                assertTrue(school.hasProfessor(color));
            }else{
                assertEquals(0, school.getHallStudentsNum(color));
                assertFalse(school.hasProfessor(color));
            }
        }
        assertEquals(0,player.getMoney());
    }
    @Test
    /**
     * tests the situation when the professor are already owned by someone
     * note that it also tests the private method earnProfessor
     */
    public void addHallStudent2__wocd(){
        Player player1 = new Player("Riccardo","127000000001",3084,Magician.KING,GameMode.TwoPlayersBasic);
        School school1 = new School(player1,7);
        school.addSchool(school1);
        for(Color color: Color.values()){
            if(color != Color.GREEN){
                school1.addHallStudent(color);
            }
            //called after the method for school1, school shouldn't earn the professor except for GREEN
            school.addHallStudent(color);
        }

        for(Color color: Color.values()){
            if(color != Color.GREEN) {
                assertTrue(school1.hasProfessor(color));
                assertFalse(school.hasProfessor(color));
            }else{
                assertTrue(school.hasProfessor(color));
                assertFalse(school1.hasProfessor(color));
            }
        }

        assertEquals(0,player.getMoney());
        //for testing the %3 branch
        school.addHallStudent(Color.GREEN);
        school.addHallStudent(Color.GREEN);
        assertEquals(1,player.getMoney());
    }

    @Test
    /**
     * tests the situation in the first round when the professors aren't owned by someone
     * note that it also tests the private method earnProfessor
     */
    public void addEntranceStudent__wocd(){
        for(Color color: Color.values()){
            if(color != Color.GREEN) school.addEntranceStudent(color);
        }
        for(Color color: Color.values()){
            if(color != Color.GREEN) {
                assertEquals(1, school.getEntranceStudentsNum(color));
            }else{
                assertEquals(0, school.getEntranceStudentsNum(color));
            }
        }
    }

}