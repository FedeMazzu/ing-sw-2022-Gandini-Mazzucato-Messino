package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test the implementation of the observer pattern for the islands
 */
class IslandsDataTest {
    private IslandsData islandsData;
    private Game game;
    private Board board;
    private UnionFind uf;

    @BeforeEach
    void setUp() {
        game = new Game(GameMode.TwoPlayersBasic);
        board = game.getBoard();
        uf = board.getUnionFind();
        islandsData = uf.getIslandsObserver().getIslandsData();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * tests the implementation of the pattern observer after adding a student to an island
     */
    @Test
    public void update__wocd(){
        int islandId = 3;
        Island island = uf.getIsland(islandId);
        int numGREENstudents = island.getStudentNum(Color.GREEN);
        assertEquals(numGREENstudents, islandsData.getStudentsNum(islandId,Color.GREEN));
        island.addStudent(Color.GREEN);
        assertEquals(numGREENstudents+1, islandsData.getStudentsNum(islandId,Color.GREEN));
    }
}