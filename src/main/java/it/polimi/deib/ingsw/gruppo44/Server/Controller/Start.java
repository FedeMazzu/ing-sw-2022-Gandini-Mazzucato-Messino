package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.util.List;


/**
 *
 */
public class Start implements Stage {
    private final GameStage gameStage = GameStage.START;
    private final GameController gameController;
    private Game game;

    public Start(GameController gameController) {
        this.gameController = gameController;
    }

    public void handle(){
        // ask input from user for the Game Mode and enter if
        GameMode gameMode = GameMode.TwoPlayersBasic;//just as an example
        game = new Game(gameMode);
        gameController.setGame(game);
        //ask for names magician.. and set them here taking the references from the Game
        //set the users
        User user;
        String IP, name;
        int port;
        Magician magician;
        Player player;
        for(int i=0; i< gameMode.getTeamsNumber(); i++) {
            for (int j = 0; j < gameMode.getTeamPlayers(); j++) {
                player = game.getTeams().get(i).getPlayers().get(j);
                //ask input to the user
                name = "Andrea";
                IP = "127.000.000.000";
                port = 3084;
                magician = Magician.MONK;

                player.setMagician(magician);
                player.setName(name);

                user = new User(player,IP,port);
                gameController.addUser(user);
            }
        }

        //at the end
        gameController.setGameStage(GameStage.PLANNING);
    }



}
