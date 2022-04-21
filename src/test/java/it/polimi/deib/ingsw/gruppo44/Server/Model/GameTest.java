package it.polimi.deib.ingsw.gruppo44.Server.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test the Game
 * @author filippogandini
 */
class GameTest {
    private Game game;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * tests the serialization/deserialization of the game with TwoPlayersBasic mode
     */
    @Test
    public void saveGame__TwoPlayersBasic(){
        game = new Game(GameMode.TwoPlayersBasic);
        Map<Color,Integer> cloudStudents = new HashMap<>();
        Map<Color,Integer> schoolStudents = new HashMap<>();
        Map<Color,Integer> islandStudents = new HashMap<>();
        for(Color color: Color.values()){
            //testing one random element for type
            cloudStudents.put(color, game.getBoard().getClouds().get(0).getStudentsNum(color));
            schoolStudents.put(color, game.getTeams().get(0).getPlayers().get(0).getSchool().getEntranceStudentsNum(color));
            islandStudents.put(color,game.getBoard().getUnionFind().getIsland(6).getStudentNum(color));
        }
        //serialization
        game.saveGame("savedGame.ser");
        game = null;

        //deserialization
        game = Game.loadGame("savedGame.ser");

        //tests the serialization/deserialization of the clouds, schools, islands
        for(Color color: Color.values()){
            assertEquals(cloudStudents.get(color),game.getBoard().getClouds().get(0).getStudentsNum(color));
            assertEquals(schoolStudents.get(color),game.getTeams().get(0).getPlayers().get(0).getSchool().getEntranceStudentsNum(color));
            assertEquals(islandStudents.get(color),game.getBoard().getUnionFind().getIsland(6).getStudentNum(color));
        }
    }
}