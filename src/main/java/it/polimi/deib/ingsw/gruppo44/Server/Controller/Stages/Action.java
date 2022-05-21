package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Ticket;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.*;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

import static it.polimi.deib.ingsw.gruppo44.Server.Model.Color.GREEN;

/**
 * stage to model the action phase
 * @author
 */
public class Action implements Stage, Serializable {
    private final GameStage gameStage = GameStage.ACTION;
    private final GameController gameController;
    private PriorityQueue<Ticket> turnOrder;
    private Scanner sc = new Scanner(System.in);
    private Board board;
    private boolean endGame;
    private int userNum;

    public Action(GameController gameController) {
        this.gameController = gameController;
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
        this.board = gameController.getGame().getBoard();
        userNum= gameController.getNumUsers();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException {

        endGame = false;
        System.out.println("--------------ACTION PHASE--------------");
        Player currPlayer;
        User currUser;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        while(!turnOrder.isEmpty()){
            currPlayer = turnOrder.peek().getPlayer();
            currUser = currPlayer.getUser();
            oos = currUser.getOos();
            ois = currUser.getOis();
            boolean usingCharacter = false;

            //the protocol changes if the Game Mode is Expert AND the player chooses to use a Character
            if(gameController.getGameMode().isExpertMode()){
                usingCharacter = ois.readBoolean();
                sendCharacterBooleanToOthers(currUser,usingCharacter);
            }


            if(usingCharacter) {
                handleUsingCharacter(currUser);
                if(endGame) break;
            } else {
                //the protocol is the same in the standard mode and in the expert mode if the player doesn't use a character
                playStandardTurn(currUser);
                if(endGame) break;

            }
            gameController.getTurnHandler().endOfTurn();

        }

        if(endGame) gameController.setGameStage(GameStage.END);
        else gameController.setGameStage(GameStage.CLEANUP);
    }

    private void playStandardTurn(User currUser) throws IOException, ClassNotFoundException {
        Player currPlayer = currUser.getPlayer();
        ObjectOutputStream oos = currUser.getOos();
        ObjectInputStream ois = currUser.getOis();
        School currSchool = currPlayer.getSchool();

        //Move students and send after each move
        moveStudents(currSchool, ois);


        //Move MotherNature
        moveMotherNature(ois, oos);
        sendMotherNatureMoveToAll();


        //checking the end of the game and sending the information to all the clients
        endGame = gameController.checkEndOfGame();
        sendEndGameInformation();
        if (endGame) return;
        //Choosing cloud
        chooseACloud(currPlayer, ois, oos);
        sendCloudChoiceToAll(currUser);
    }

    /**
     * handles the case oin the ExpertMode in which the player wants to use a character
     * @param currUser
     */
    private void handleUsingCharacter(User currUser) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = currUser.getOos();
        ObjectInputStream ois = currUser.getOis();

        //receiving the id of the Character
        int characterId = ois.readInt();
        sendCharacterChosenToOthers(currUser,characterId);
        switch (characterId){
            case 2:
                handleCharacter2(currUser);
                break;
                //...
            case 3:
                handleCharacter3(currUser);
                break;
            case 4:
                handleCharacter4(currUser);
                break;
            case 6:
                handleCharacter6(currUser);
                break;
            case 8:
                handleCharacter8(currUser);
                break;
            case 9:
                handleCharacter9(currUser);
                break;
            case 10:
                handleCharacter10(currUser);
                break;
            case 12:
                handleCharacter12(currUser);
                break;
            default:
        }
    }

    private void handleCharacter2(User user) throws IOException, ClassNotFoundException {

        user.getPlayer().getSchool().setCharacter2Used(true);
        Character char2 = board.getShop().getSingleCharacter(2);
        ((Character2) char2).effect(user.getPlayer());

        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);
        user.getPlayer().getSchool().setCharacter2Used(false);

    }


    private void handleCharacter3(User user) throws IOException, ClassNotFoundException {

        ObjectInputStream ois = user.getOis();
        int islandChosen = ois.readInt();
        Character char3 = board.getShop().getSingleCharacter(3);
        ((Character3) char3).effect(islandChosen,user.getPlayer());
        endGame = gameController.checkEndOfGame();
        sendIslandDataToAll();
        sendEndGameInformation();
        if(endGame) return;
        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);
    }

    private void handleCharacter4(User user) throws IOException, ClassNotFoundException {
        // the effect is handled by the client
        Character char4 = board.getShop().getSingleCharacter(4);
        ((Character4) char4).effect(user.getPlayer());
        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);
    }

    private void handleCharacter6(User user) throws IOException, ClassNotFoundException {
        board.getUnionFind().setCharacterUsed(6);
        Character char6 = board.getShop().getSingleCharacter(6);
        ((Character6) char6).effect(user.getPlayer());
        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);
        board.getUnionFind().setCharacterUsed(0);
    }

    private void handleCharacter8(User user) throws IOException, ClassNotFoundException {
        board.getUnionFind().setCharacterUsed(8);
        Character char8 = board.getShop().getSingleCharacter(8);
        ((Character8) char8).effect(user.getPlayer());
        int index = 0;
        for(Team t: gameController.getGame().getTeams()){
            if(t.getPlayers().contains(user.getPlayer())){
                board.getUnionFind().setCurrTeamIndexForChar8(index);
                break;
            }
            index++;
        }
        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);
        board.getUnionFind().setCharacterUsed(0);
        board.getUnionFind().setCurrTeamIndexForChar8(-1);
    }

    private void handleCharacter9(User user) throws IOException, ClassNotFoundException {
        board.getUnionFind().setCharacterUsed(9);
        Character char9 = board.getShop().getSingleCharacter(9);
        ((Character9) char9).effect(user.getPlayer());
        ObjectInputStream ois = user.getOis();
        Color colorChosen = (Color) ois.readObject();
        board.getUnionFind().setColorChosenForChar9(colorChosen);
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            if(tempUser == user) continue;
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.writeObject(colorChosen);
            tempOos.flush();
        }
        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);
        board.getUnionFind().setCharacterUsed(0);

    }

    private void handleCharacter10(User user) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = user.getOis();
        boolean swap = false;
        swap = ois.readBoolean();
        Color h1 = null ,e1 = null,h2 = null,e2 = null;
        if(swap){
           h1 = (Color) ois.readObject();
           e1 = (Color) ois.readObject();

           swap = ois.readBoolean();
           if(swap){
               h2 = (Color) ois.readObject();
               e2 = (Color) ois.readObject();
           }
        }

        Character char10 = board.getShop().getSingleCharacter(10);
        ((Character10) char10).effect(h1,e1,h2,e2,user.getPlayer().getSchool(),user.getPlayer());

        //this includes the school updated
        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);

    }

    /**
     * handles the turn playing with the character 12
     * @param user
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void handleCharacter12(User user) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = user.getOis();
        Color colorChosen = (Color) ois.readObject();
        Character char12 = board.getShop().getSingleCharacter(12);
        ((Character12) char12).effect(colorChosen,user.getPlayer());

        sendUpdatedMoneyToAll(user);
        sendUpdatedPrice(user);
        playStandardTurn(user);

    }


    private void sendSchoolsDataToAll() throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();
            for(SchoolData sd: gameController.getData().getSchoolDataList()) {
                tempOos.writeObject(sd);
                tempOos.flush();
            }
        }
    }


    private void sendCharacterChosenToOthers(User currUser,int charId) throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            if(tempUser == currUser) continue;
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.writeInt(charId);
            tempOos.flush();
        }
    }

    private void sendCharacterBooleanToOthers(User currUser,boolean usingCharacter) throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            if(tempUser == currUser) continue;
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.writeBoolean(usingCharacter);
            tempOos.flush();
        }
    }


    public void sendUpdatedPrice(User currUser) throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();//needed!
            tempOos.writeObject(gameController.getData().getBoardData().getCharacters());
            tempOos.flush();
        }
    }


    /**
     * sends to all the clients if the game has ended
     * @param
     * @throws IOException
     */
    private void sendEndGameInformation() throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();
            tempOos.writeBoolean(endGame);
            tempOos.flush();
        }
    }


    /**
     * sends the information of the cloud chosen to the WaitingClients
     * @param currUser
     * @throws IOException
     */
    private void sendCloudChoiceToAll(User currUser) throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();//needed!
            tempOos.writeObject(gameController.getData().getCloudsData());
            tempOos.flush();
        }
        sendSchoolsDataToAll();
    }

    /**
     * sends the information of the mother nature move to every client
     * @throws IOException
     */
    private void sendMotherNatureMoveToAll() throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();
            tempOos.writeObject(gameController.getData().getIslandsData());
            tempOos.flush();
            tempOos.reset();
            tempOos.writeInt(gameController.getData().getBoardData().getMotherNaturePosition());
            tempOos.flush();
        }
    }

    private void sendIslandDataToAll() throws IOException {
        for(int i=0;i<userNum;i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();
            tempOos.writeObject(gameController.getData().getIslandsData());
            tempOos.flush();
        }
    }

    /**
     * sends the information of the moved students to every client
     * @throws IOException
     */
    private void sendStudentsMoveToAll() throws IOException {
        sendSchoolsDataToAll();
        sendIslandDataToAll();
    }

    /**
     * sending the updated money pof the client who used a character to every user
     * @param user
     * @throws IOException
     */
    private void sendUpdatedMoneyToAll(User user) throws IOException {
        sendSchoolsDataToAll();
    }


    /**
     * manages the choice of a cloud
     * @param currPlayer
     * @param ois
     * @param oos
     * @throws IOException
     */
    private void chooseACloud(Player currPlayer,ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        String cloudsData ="";
        for(int i=0;i< board.getClouds().size();i++){
            Cloud cloud = board.getClouds().get(i);
            if(!cloud.isEmpty()) {
                cloudsData+="Cloud ID "+i+":-";
                for (Color c : Color.values()) {
                    cloudsData += " " + c + " " + cloud.getStudentsNum(c) + "|";
                }
                cloudsData += "\n";
            }
        }
        //it isn't needed for the GUI
        //oos.writeObject(cloudsData);
        //oos.flush();
        int cloudChosen = ois.readInt();
        board.getClouds().get(cloudChosen).wipeCloud(currPlayer);
    }


    /**
     * manages the player moves of students
     * @param currSchool
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void moveStudents(School currSchool, ObjectInputStream ois) throws IOException, ClassNotFoundException {


        for(int i=0;i<gameController.getGameMode().getCloudStudents();i++){
            Map<Color,Integer> entry = (Map<Color, Integer>) ois.readObject();
            Color color = (Color) entry.keySet().toArray()[0];

            if(entry.get(color) != -1){
                switch (color){
                    case GREEN:
                        currSchool.removeEntranceStudent(GREEN);
                        board.getUnionFind().getIsland(entry.get(color)).addStudent(GREEN);
                        break;
                    case RED:
                        currSchool.removeEntranceStudent(Color.RED);
                        board.getUnionFind().getIsland(entry.get(color)).addStudent(Color.RED);
                        break;
                    case YELLOW:
                        currSchool.removeEntranceStudent(Color.YELLOW);
                        board.getUnionFind().getIsland(entry.get(color)).addStudent(Color.YELLOW);
                        break;
                    case PINK:
                        currSchool.removeEntranceStudent(Color.PINK);
                        board.getUnionFind().getIsland(entry.get(color)).addStudent(Color.PINK);
                        break;
                    case BLUE:
                        currSchool.removeEntranceStudent(Color.BLUE);
                        board.getUnionFind().getIsland(entry.get(color)).addStudent(Color.BLUE);
                        break;
                    default:
                        System.out.println("Incorrect value");
                        System.exit(0);
                }

            }
            else{
                switch (color){
                    case GREEN:
                        currSchool.addHallStudent(GREEN);
                        break;
                    case RED:
                        currSchool.addHallStudent(Color.RED);
                        break;
                    case YELLOW:
                        currSchool.addHallStudent(Color.YELLOW);
                        break;
                    case PINK:
                        currSchool.addHallStudent(Color.PINK);
                        break;
                    case BLUE:
                        currSchool.addHallStudent(Color.BLUE);
                        break;
                    default:
                        System.out.println("Incorrect value");
                        System.exit(0);
                }
            }
            //send to everyone
            sendStudentsMoveToAll();

        }
    }

    /**
     * manages mother nature move
     * @param oos
     * @throws IOException
     */
    private void moveMotherNature(ObjectInputStream ois, ObjectOutputStream oos) throws IOException {

        int stepsChoice = ois.readInt();
        System.out.println("Position before: "+board.getMotherNaturePosition());
        board.moveMotherNature(stepsChoice);
        System.out.println("Position after: "+board.getMotherNaturePosition());
    }

}
