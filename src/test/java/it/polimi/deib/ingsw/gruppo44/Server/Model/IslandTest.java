package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    Game game;
    Board board;
    UnionFind uf;

    @BeforeEach
    void setUp() {
        game = new Game(GameMode.TwoPlayersStandard);
        board = game.getBoard();
        uf = board.getUnionFind();
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    public void islandClaim__putTower__2P(){
        List<Team> teams = uf.teams;
        //give all prof to the first player
        Team t1 = teams.get(0);
        Player p1 = t1.getPlayers().get(0);
        for(Color c:Color.values()){
            p1.getSchool().TESTsetProfessor(c);
        }
        board.moveMotherNature(1);
        Island is = board.getUnionFind().getIsland(board.getMotherNaturePosition());
        assertTrue(is.getHasTower());
        assertEquals(is.getTowerColor(), t1.getTower());

        Team t2 = teams.get(1);
        Player p2 = t2.getPlayers().get(0);
        for(Color c:Color.values()){
            p2.getSchool().TESTsetProfessor(c);
            p1.getSchool().TESTnoProfessor(c);
        }
        board.moveMotherNature(12);
        is = board.getUnionFind().getIsland(board.getMotherNaturePosition());
        assertTrue(is.getHasTower());
        assertEquals(is.getTowerColor(), t2.getTower());

    }

    @Test
    public void IslandClaim__merge_2P(){
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

        assertEquals(2,uf.getGroupSize(board.getMotherNaturePosition()));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(1));

        board.moveMotherNature(1);
        assertEquals(3,uf.getGroupSize(board.getMotherNaturePosition()));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(2));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(1));

        board.moveMotherNature(2);
        //adding one student to island 6
        uf.getIsland(6).addStudent(Color.BLUE);
        board.moveMotherNature(1);
        board.moveMotherNature(8);
        assertEquals(6,uf.getGroupSize(board.getMotherNaturePosition()));
        assertEquals(7,uf.getSize());
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(1));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(5));
        assertEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(6));
        assertNotEquals(uf.findGroup(board.getMotherNaturePosition()),uf.findGroup(10));


    }
}