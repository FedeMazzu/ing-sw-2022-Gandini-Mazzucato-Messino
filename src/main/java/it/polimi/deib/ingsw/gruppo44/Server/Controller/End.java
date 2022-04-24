package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;

import java.io.Serializable;

public class End implements Stage, Serializable {
    private final GameStage gameStage = GameStage.END;
    private final GameController gameController;

    public End(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle() {

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
                    //FAI UNA SCHERMATA APPOSTA PER IL PAREGGIO
                }
            }
        }
        for(Player player : tempWinner.getPlayers()){
            //print winners
            System.out.println(player.getMagician()+" WON!");
        }

        gameController.endGame();
    }
}
