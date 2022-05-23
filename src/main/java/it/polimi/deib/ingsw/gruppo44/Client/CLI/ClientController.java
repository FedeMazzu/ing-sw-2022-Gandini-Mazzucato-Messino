package it.polimi.deib.ingsw.gruppo44.Client.CLI;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages.*;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.GameDataGUI;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

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
    private int turnNumber;
    private GameDataCLI gameDataCLI;
    private int characterChoice;
    private int lastCardSelected;

    public ClientController(ObjectInputStream ois, ObjectOutputStream oos) {
        this.ois = ois;
        this.oos = oos;
        MessagesMethodsCLI.ois = ois;
        MessagesMethodsCLI.clientController = this;
        MessagesMethodsCLI.oos = oos;
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
                    case WaitingBeforeTurn:
                        stage = new WaitingBeforeTurn(this);
                        break;
                    case ClientACTION:
                        boolean usingCharacter = false;
                        if(gameMode.isExpertMode()) usingCharacter = AskIfUsingCharacter();
                        if (usingCharacter) stage = new ClientActionExpert(this,characterChoice);
                        else stage = new ClientActionStandard(this);
                        break;
                    case WaitingAfterTurn:
                        stage = new WaitingAfterTurn(this);
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

    /**
     * ask the user which character (if he can afford) at the start of the Action Phase in case of Expert Mode
     * @return
     */
    private boolean AskIfUsingCharacter() throws IOException {
        int currMoney = gameDataCLI.getClientMoney();
        Map<Integer,Integer> affordableCharacters = gameDataCLI.getAffordableCharacters();
        if(!affordableCharacters.isEmpty()) {
            System.out.print("you can afford these characters:\n" + affordableCharacters + "\n Which one do you want to use?(0 to avoid):->");
            characterChoice = sc.nextInt();
            if (characterChoice != 0) {
                //communicating decision to use a character
                oos.writeBoolean(true);
                oos.flush();
                // communicating the character to use
                oos.writeInt(characterChoice);
                oos.flush();
                return true;
            }
        }

        //in case the list is empty or the client chooses to not use a character
        oos.writeBoolean(false);
        oos.flush();
        return false;

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

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public GameDataCLI getGameDataCLI() {return gameDataCLI;}

    public void setGameData(GameDataCLI gameDataCLI) {this.gameDataCLI = gameDataCLI;}

    public int getLastCardSelected() {
        return lastCardSelected;
    }

    public void setLastCardSelected(int lastCardSelected) {
        this.lastCardSelected = lastCardSelected;
    }
}
