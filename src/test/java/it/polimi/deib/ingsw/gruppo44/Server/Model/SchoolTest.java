package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test the School class
 * @author filippogandini
 */
class SchoolTest {
    private Game game;
    private School school;
    private Player player;
    @BeforeEach
    void setUp() {
        game = new Game(GameMode.TwoPlayersStandard);
        player = game.getTeams().get(0).getPlayers().get(0);
        school = player.getSchool();
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
     */
    public void addHallStudent1__wocd(){
        school.addEntranceStudent(Color.GREEN);
        school.addEntranceStudent(Color.GREEN);
        int GreenEntranceStudents = school.getEntranceStudentsNum(Color.GREEN);
        for(int i=0; i<GreenEntranceStudents; i++){
            school.addHallStudent(Color.GREEN);
        }
        assertEquals(GreenEntranceStudents,school.getHallStudentsNum(Color.GREEN));
        assertEquals(0,school.getEntranceStudentsNum(Color.GREEN));
        assertEquals(0,school.getHallStudentsNum(Color.RED));
    }
    @Test
    /**
     * tests the situation when the professor are already owned by someone
     * note that it also tests the private method earnProfessor
     */
    public void addHallStudent2__wocd(){
        //works on this GameMode
        Player player1 = game.getTeams().get(1).getPlayers().get(0);
        School school1 = player1.getSchool();
        school.addSchool(school1);
        for(Color color: Color.values()){
            if(color != Color.GREEN){
                school1.addEntranceStudent(color);
                school1.addHallStudent(color);
            }
            //called after the method for school1, school shouldn't earn the professor except for GREEN
            school.addEntranceStudent(color);
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
        school.addEntranceStudent(Color.GREEN);
        school.addEntranceStudent(Color.GREEN);
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


    @Test
    public void swapStudentsForChar10_wocd(){
        School sc = new School(player,7,8);
        sc.addEntranceStudent(Color.GREEN);
        sc.addEntranceStudent(Color.GREEN);
        sc.addEntranceStudent(Color.GREEN);
        sc.addEntranceStudent(Color.BLUE);
        sc.addEntranceStudent(Color.BLUE);
        sc.addEntranceStudent(Color.BLUE);
        assertEquals(3,sc.getEntranceStudentsNum(Color.GREEN));
        assertEquals(3,sc.getEntranceStudentsNum(Color.BLUE));
        sc.addHallStudent(Color.GREEN);
        assertEquals(2,sc.getEntranceStudentsNum(Color.GREEN));
        assertEquals(1, sc.getHallStudentsNum(Color.GREEN));
        sc.swapStudentsForChar10(Color.GREEN, Color.BLUE);

        assertEquals(3,sc.getEntranceStudentsNum(Color.GREEN));
        assertEquals(0, sc.getHallStudentsNum(Color.GREEN));
        assertEquals(2, sc.getEntranceStudentsNum(Color.BLUE));
        assertEquals(1, sc.getHallStudentsNum(Color.BLUE));

    }
}