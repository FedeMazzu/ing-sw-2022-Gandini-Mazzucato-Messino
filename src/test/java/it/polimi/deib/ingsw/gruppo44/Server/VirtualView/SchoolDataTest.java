package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test SchoolData.
 * @author filippogandini
 */
class SchoolDataTest {
    private SchoolData schoolData;
    private Game game;
    private School school;
    private Player player;
    @BeforeEach
    void setUp() {
        game = new Game("TestGame",GameMode.TwoPlayersBasic);
        player = game.getTeams().get(0).getPlayers().get(0);
        school = player.getSchool();
        schoolData = school.getSchoolObserver().getSchoolData();
    }


    @AfterEach
    void tearDown() {
    }

    /**
     * tests the update of the virtual view when the Model changes
     */
    @Test
    public void update__woct(){
        school.addEntranceStudent(Color.GREEN);
        assertEquals(1,schoolData.getEntranceStudentsNum(Color.GREEN));
        assertEquals(0,schoolData.getEntranceStudentsNum(Color.BLUE));

        school.addEntranceStudent(Color.GREEN);
        assertEquals(2,schoolData.getEntranceStudentsNum(Color.GREEN));

        //works in this GameMode
        Player player2 = game.getTeams().get(1).getPlayers().get(0);
        School school1 = player2.getSchool();
        //school1 is needed to use the private method earnProfessor
        school.addEntranceStudent(Color.PINK);
        school.addHallStudent(Color.PINK);

        assertTrue(schoolData.hasProfessor(Color.PINK));
        assertFalse(schoolData.hasProfessor(Color.GREEN));
        assertFalse(school1.getSchoolObserver().getSchoolData().hasProfessor(Color.PINK));

    }



}