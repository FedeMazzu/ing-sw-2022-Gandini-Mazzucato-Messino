package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Stage to model the end of the game
 */
public class End implements Stage, Serializable {
    private final GameStage gameStage = GameStage.END;
    private final GameController gameController;

    public End(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle() throws IOException {

        Team tempWinner = gameController.getGame().getTeams().get(0);
        int tempMinScore = 9;
        for(Team team:gameController.getGame().getTeams()){
            if(tempMinScore > team.getTowerCount()){
                tempWinner = team;
                tempMinScore = team.getTowerCount();
            }
            else if(tempMinScore == team.getTowerCount()){
                int profTempWinner = 0, profTempTeam = 0;
                for(Player player:team.getPlayers()){
                    for(Color c: Color.values()){
                        if(player.getSchool().hasProfessor(c)) profTempTeam++;
                    }
                }
                for(Player player:tempWinner.getPlayers()){
                    for(Color c: Color.values()){
                        if(player.getSchool().hasProfessor(c)) profTempWinner++;
                    }
                }
                if(profTempWinner < profTempTeam){
                    tempWinner = team;
                }
                else if(profTempTeam == profTempWinner){
                    //draw not handled yet
                }
            }
        }
        User user;
        ObjectOutputStream oos;
        //ObjectInputStream ois;
        String winningMessage ="";
        for(Player player : tempWinner.getPlayers()){
            winningMessage += player.getMagician()+" WON!";
        }
        for(Team team : gameController.getGame().getTeams()){
            for(Player player : team.getPlayers()){
                user = player.getUser();
                oos = user.getOos();
                oos.writeObject(winningMessage);
                oos.flush();
                user.closeSocket();
            }
        }

        gameController.endGame();
    }
}
