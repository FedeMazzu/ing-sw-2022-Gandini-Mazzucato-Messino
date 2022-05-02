package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.*;
import java.net.Socket;
import java.util.*;


/**
 * stage to initialize the game
 * @author
 */
public class Start implements Stage, Serializable {
    private final GameStage gameStage = GameStage.START;
    private final GameController gameController;
    private Game game;
    private Data data;
    private Map<Magician,Boolean> freeMagician;
    public Start(GameController gameController) {
        this.gameController = gameController;
        freeMagician = new HashMap<>();
        for(Magician magician : Magician.values()) freeMagician.put(magician,false);
    }

    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        Random rand = new Random();
        GameMode gameMode= gameController.getGameMode();
        game = new Game(gameMode);
        data = new Data();
        gameController.setGame(game);
        gameController.setData(data);
        TurnHandler turnHandler = new TurnHandler(gameMode);
        gameController.setTurnHandler(turnHandler);
        //saving the reference of the islands and clouds Data in the Virtual View
        data.setBoardData(game.getBoard().getBoardObserver().getBoardData());
        data.setIslandsData(game.getBoard().getUnionFind().getIslandsObserver().getIslandsData());
        data.setCloudsData(game.getBoard().getCloudsObserver().getCloudsData());

        //Waiting for the players to join
        int numUsers = gameController.getNumUsers();
        while(numUsers < gameMode.getTeamPlayers()*gameMode.getTeamsNumber()) {
            synchronized (gameController) {
                gameController.wait();
            }
            numUsers = gameController.getNumUsers();
        }
        gameController.setGameIsFull(true);
        System.out.println("Start ready");

        String  name;
        Magician magician;
        Player player;
        User user;
        SchoolData schoolData;
        School school;
        Socket socket;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        PriorityQueue<Ticket> turnOrder = turnHandler.getTurnOrder();

        //sending an ack that the correct amount of players has connected
        for(int i=0; i< gameController.getNumUsers(); i++){
            oos = gameController.getUser(i).getOos();
            oos.writeBoolean(true);
            oos.flush();
        }

        int randPrio;
        int userIndex = 0;
        for(int i=0; i < gameMode.getTeamsNumber(); i++) {
            for (int j = 0; j < gameMode.getTeamPlayers(); j++) {
                user = gameController.getUser(userIndex);
                userIndex++;
                player = game.getTeams().get(i).getPlayers().get(j);
                player.setUser(user);
                randPrio = rand.nextInt(4); //random seed to start the game, 4 is the maximum priority possible
                turnOrder.add(new Ticket(player,randPrio));


            }
        }

        while(!turnOrder.isEmpty()){

            player = turnOrder.peek().getPlayer();
            user = player.getUser();
            school = player.getSchool();
            schoolData = school.getSchoolObserver().getSchoolData();
            //to initialize the School entrance with random values
            game.getBoard().getNotOwnedObjects().fillEntrance(school);
            //ask input to the user
            //where we look if we don't want duplicate names
            ois = user.getOis();
            oos = user.getOos();
            name = (String) ois.readObject();

            player.setName(name);
            user.setPlayer(player);

            magician = askMagician(ois,oos); //we will need the user to talk to the right client
            player.setMagician(magician);
            //
            schoolData.setMagician(magician); //setting the identifier
            schoolData.setPlayerName(name); //setting the name of the player who owns the school(for the GUI)
            data.addSchoolData(schoolData);
            turnHandler.endOfTurn(); //the player is put in the card deque and polled from the prioQ
        }

        for(int i=0;i<gameMode.getTeamsNumber()*gameMode.getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            oos = tempUser.getOos();
            oos.writeObject(gameController.getData());
            oos.flush();
        }

        //at the end
        gameController.setGameStage(GameStage.PLANNING);
    }



    /**
     * ask the client the magician
     * @param ois ObjectInputStream related
     * @param oos ObjectOutputStream related
     * @return the chosen magician
     * @throws IOException
     */
    private Magician askMagician(ObjectInputStream ois,ObjectOutputStream oos) throws IOException {
        int choice;
        String availableMagicians ="";
        while(true) {

            for (Magician magician : Magician.values()) {
                if (!freeMagician.get(magician)) {
                    availableMagicians += magician.getId() + " - " + magician.getName()+"\n";
                }
            }
            oos.writeObject(availableMagicians);
            oos.flush();

            choice = ois.readInt();
            for (Magician magician : Magician.values()) {
                if (choice == magician.getId())
                    if (!freeMagician.get(magician)) {
                        freeMagician.put(magician, true);
                        oos.writeBoolean(true);
                        oos.flush();
                        return magician;
                    }
            }
            oos.writeBoolean(false);
            oos.flush();
            System.out.println("Magician already selected.. try again");
        }
    }


}
