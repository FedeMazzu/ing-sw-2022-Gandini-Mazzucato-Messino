package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.BoardData;

import java.io.Serializable;

/**
 * Observer of the mother nature position (and maybe of the shop)
 * @author filippogandini
 */
public class BoardObserver implements Observer, Serializable {
    private Board board;
    private BoardData boardData;
    private GameMode gameMode;

    public BoardObserver(Board board, GameMode gameMode) {
        this.board = board;
        boardData = new BoardData();
        this.gameMode = gameMode;
    }

    @Override
    public void update() {
        //updating mother nature position
        boardData.setMotherNaturePosition(board.getMotherNaturePosition());


        if(gameMode.isExpertMode()) {
            //updating Characters price (first call it initializes
            for (Character character : board.getShop().getCharacters()) {
                boardData.putCharacter(character.getId(), character.getPrice());
            }
        }

    }

    public BoardData getBoardData() {
        return boardData;
    }
}
