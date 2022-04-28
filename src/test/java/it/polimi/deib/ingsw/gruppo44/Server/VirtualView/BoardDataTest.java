package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Board;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class made to test BoardData and the implementation of the observer pattern related
 * @author filippogandini
 */

class BoardDataTest {
    private Game game;
    private Board board;
    private BoardData boardData;
    @BeforeEach
    void setUp() {
        game = new Game(GameMode.TwoPlayersExpert);
        board = game.getBoard();
        boardData = board.getBoardObserver().getBoardData();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void moveMotherNature__wocd(){
        //testing initialization
        assertEquals(board.getMotherNaturePosition(),boardData.getMotherNaturePosition());

        //testing the observation of the move
        board.moveMotherNature(3);
        assertEquals(3, boardData.getMotherNaturePosition());

    }
}