package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.BoardData;

import java.io.Serializable;

/**
 * Observer of the mother nature position (and maybe of the shop)
 * @author filippogandini
 */
public class BoardObserver implements Observer, Serializable {
    private Board board;
    private BoardData boardData;

    public BoardObserver(Board board) {
        this.board = board;
        boardData = new BoardData();
    }

    @Override
    public void update() {
        boardData.setMotherNaturePosition(board.getMotherNaturePosition());
    }

    public BoardData getBoardData() {
        return boardData;
    }
}
