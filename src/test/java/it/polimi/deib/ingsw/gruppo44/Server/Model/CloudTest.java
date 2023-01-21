package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    private Game game;
    private Cloud cloud;

    @BeforeEach
    public void SetUp(){
        game = new Game(GameMode.TwoPlayersStandard);
        cloud = game
                .getBoard()
                .getClouds()
                .get(0);
    }

    @AfterEach
    public void TearDown() { cloud = null; }


    @Test
    public void isEmpty() {
        cloud.wipeCloud(game
                .getTeams()
                .get(0)
                .getPlayers()
                .get(0));

        assertTrue(cloud.isEmpty());
    }

    @Test
    public void addStudent__woct() {
        cloud.wipeCloud(game
                .getTeams()
                .get(0)
                .getPlayers()
                .get(0));
        cloud.addStudent(Color.GREEN);
        cloud.addStudent(Color.RED);
        cloud.addStudent(Color.GREEN);

        assertEquals(2,
                cloud.getStudentsNum(Color.GREEN));
        assertEquals(1,
                cloud.getStudentsNum(Color.RED));
        assertEquals(0,
                cloud.getStudentsNum(Color.YELLOW));
    }

    @Test
    public void wipeCloud() {
        cloud.wipeCloud(game
                .getTeams()
                .get(0)
                .getPlayers()
                .get(0));

        assertEquals(0,
                cloud.getStudentsNum(Color.YELLOW));
        assertEquals(0,
                cloud.getStudentsNum(Color.RED));
        assertEquals(0,
                cloud.getStudentsNum(Color.GREEN));
        assertEquals(0,
                cloud.getStudentsNum(Color.BLUE));
        assertEquals(0,
                cloud.getStudentsNum(Color.PINK));
    }

    @Test
    public void addStudent__nwoud() {
        cloud.wipeCloud(game
                .getTeams()
                .get(0)
                .getPlayers()
                .get(0));
        cloud.addStudent(Color.GREEN);
        cloud.addStudent(Color.GREEN);
        cloud.addStudent(Color.GREEN);

        assertNotEquals(2,
                cloud.getStudentsNum(Color.GREEN));
        assertNotEquals(1
                ,cloud.getStudentsNum(Color.GREEN));
        assertEquals(0,
                cloud.getStudentsNum(Color.RED));
        assertEquals(0,
                cloud.getStudentsNum(Color.YELLOW));
        assertEquals(0,
                cloud.getStudentsNum(Color.BLUE));
        assertEquals(0,
                cloud.getStudentsNum(Color.PINK));
    }


}