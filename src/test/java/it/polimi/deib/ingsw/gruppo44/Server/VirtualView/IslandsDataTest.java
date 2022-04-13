package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
     * tests the implementation of the pattern observer adding a student to an island
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

    /**
     * tests the implementation of the pattern observer doing some merges
     * Note: numbers designed for these particular merges
     */
    @Test
    public void update__IslandClaim__merge_2P(){
        List<Team> teams = uf.teams;
        //give all prof to the first player
        Team t1 = teams.get(0);
        Player p1 = t1.getPlayers().get(0);
        for(Color c:Color.values()){
            p1.getSchool().TESTsetProfessor(c);
        }
        board.moveMotherNature(1);
        board.moveMotherNature(1);
        Island is = board.getUnionFind().getIsland(board.getMotherNaturePosition());

        assertEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(1));

        board.moveMotherNature(1);
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(2));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(1));

        board.moveMotherNature(2);
        //adding one student to island 6
        uf.getIsland(6).addStudent(Color.BLUE);
        board.moveMotherNature(1);
        board.moveMotherNature(8);
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(1));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(5));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(6));
        assertNotEquals(uf.findGroup(board.getMotherNaturePosition()),islandsData.findGroup(10));

    }
}