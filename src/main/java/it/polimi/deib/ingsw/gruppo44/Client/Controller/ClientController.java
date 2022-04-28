package it.polimi.deib.ingsw.gruppo44.Client.Controller;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages.*;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * manages the client stages
 * @author filippogandini
 */
public class ClientController implements Runnable {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);
    private Stage stage;
    private ClientStage clientStage;
    private boolean endGame;
    private GameMode gameMode;

    public ClientController(ObjectInputStream ois, ObjectOutputStream oos) {
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run() {
        try {
            //starting stage
            askOptions();
            endGame = false;
            while(!endGame) {
                switch (clientStage) {
                    case CREATEGAME: //create a game
                        stage = new CreateGame(this);
                        break;
                    case JOINGAME: //join a game
                        stage = new JoinGame(this);
                        break;
                    case LOADGAME:
                        stage = new LoadGame(this);
                    case SETUP:
                        stage = new Setup(this);
                        break;
                    case ClientPLANNING:
                        stage = new ClientPlanning(this);
                        break;
                    case ClientACTION:
                        stage = new ClientAction(this);
                        break;
                    default: //case END
                        stage = new ClientEND(this);
                }
                stage.handle();
            }


            } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void askOptions() {
        System.out.println("what do you want to do?\n" +
                "1- Create a new Game\n" +
                "2- Join a game\n" +
                "3- Load a game");
        switch (sc.nextInt()){
            case 1:
                clientStage = ClientStage.CREATEGAME;
                break;
            case 2:
                clientStage = ClientStage.JOINGAME;
                break;
            default://case 3
                clientStage = ClientStage.LOADGAME;
        }
    }

    public void endGame(){
        endGame = true;
    }
    public void setGameMode(GameMode gameMode) {this.gameMode = gameMode;}

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setClientStage(ClientStage clientStage) {
        this.clientStage = clientStage;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }
}
