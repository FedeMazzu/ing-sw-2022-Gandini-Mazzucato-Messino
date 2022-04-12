package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.util.List;


/**
 *
 */
public class Start implements Stage {
    private final GameStage gameStage = GameStage.START;
    private final GameController gameController;
    private Game game;
    private Data data;
    public Start(GameController gameController) {
        this.gameController = gameController;
    }

    public void handle(){
        // ask input from user for the Game Mode and enter if
        GameMode gameMode = GameMode.TwoPlayersBasic;//just as an example
        game = new Game(gameMode);
        data = new Data();
        gameController.setGame(game);
        gameController.setData(data);
        //ask for names magician.. and set them here taking the references from the Game
        //set the users
        User user;
        String IP, name;
        int port;
        Magician magician;
        Player player;
        SchoolData schoolData;
        School school;
        for(int i=0; i< gameMode.getTeamsNumber(); i++) {
            for (int j = 0; j < gameMode.getTeamPlayers(); j++) {
                player = game.getTeams().get(i).getPlayers().get(j);
                school = player.getSchool();
                schoolData = school.getSchoolObserver().getSchoolData();
                //to initialize the School entrance with random values
                game.getBoard().getNotOwnedObjects().fillEntrance(school);
                //ask input to the user
                name = "Andrea";
                IP = "127.000.000.000";
                port = 3084;
                magician = Magician.MONK;

                player.setMagician(magician);
                player.setName(name);

                user = new User(player,IP,port);
                gameController.addUser(user);
                //
                schoolData.setMagician(magician); //setting the identifier
                data.addSchoolData(schoolData);

            }
        }

        //at the end
        gameController.setGameStage(GameStage.PLANNING);
    }



}
