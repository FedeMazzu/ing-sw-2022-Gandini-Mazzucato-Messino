package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.Messages.MovingStudentsMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Ticket;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character12;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character3;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Scanner;

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

    public Action(GameController gameController) {
        this.gameController = gameController;
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
        this.board = gameController.getGame().getBoard();
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


        //Sending data to the currUser
        sendData(currSchool, oos);
        //Move students
        moveStudents(currSchool, ois);
        //sending data to other waiting players
        sendStudentsMoveToOthers(currUser);

        //Move MotherNature
        moveMotherNature(ois, oos);
        sendMotherNatureMoveToOthers(currUser);


        //checking the end of the game and sending the information to all the clients
        endGame = gameController.checkEndOfGame();
        sendEndGameInformation();
        if (endGame) return;
        //Choosing cloud
        chooseACloud(currPlayer, ois, oos);
        sendCloudChoiceToOthers(currUser);
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
            case 1:
                //handleCharacter1();
                break;
            case 2:
                //handleCharacter2();
                break;
                //...
            case 3:
                handleCharacter3(currUser);
                break;
            case 12:
                handleCharacter12(currUser);
                break;
            default:
        }
    }
    private void handleCharacter3(User user) throws IOException, ClassNotFoundException {
        //DA MODIFICARE SE IL TIMING E` LIBERO DURANTE IL TURNO (IN QUEL CASO E` TUTO SBAGLIATO)
        ObjectInputStream ois = user.getOis();
        int islandChosen = ois.readInt();
        Character char3 = board.getShop().getSingleCharacter(3);
        ((Character3) char3).effect(islandChosen);
        endGame = gameController.checkEndOfGame();
        sendIslandDataToOthers(user);
        sendEndGameInformation();
        if(endGame) return;
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
        ((Character12) char12).effect(colorChosen);

        //Send the Schools to all the clients including the currUSer
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            ObjectOutputStream oosTemp = gameController.getUser(i).getOos();
            for(SchoolData sd: gameController.getData().getSchoolDataList()){
                oosTemp.writeObject(sd);
                oosTemp.flush();
            }
        }
        sendUpdatedPrice(user);
        playStandardTurn(user);
    }


    private void sendCharacterChosenToOthers(User currUser,int charId) throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            if(tempUser == currUser) continue;
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.writeInt(charId);
            tempOos.flush();
        }
    }

    private void sendCharacterBooleanToOthers(User currUser,boolean usingCharacter) throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            if(tempUser == currUser) continue;
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.writeBoolean(usingCharacter);
            tempOos.flush();
        }
    }


    public void sendUpdatedPrice(User currUser) throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();//needed!
            if(currUser.equals(tempUser)) continue;
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
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
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
    private void sendCloudChoiceToOthers(User currUser) throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();//needed!
            if(currUser.equals(tempUser)) continue;
            tempOos.writeObject(gameController.getData().getCloudsData());
            tempOos.flush();
            tempOos.writeObject(currUser.getPlayer().getSchool().getSchoolObserver().getSchoolData());
            tempOos.flush();
        }
    }

    /**
     * sends the information of the mother nature move to the WaitingClients
     * @param currUser
     * @throws IOException
     */
    private void sendMotherNatureMoveToOthers(User currUser) throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            if(currUser.equals(tempUser)) continue;

            tempOos.writeObject(gameController.getData().getIslandsData());
            tempOos.flush();
            tempOos.writeInt(gameController.getData().getBoardData().getMotherNaturePosition());
            tempOos.flush();
        }
    }

    private void sendIslandDataToOthers(User currUser) throws IOException {
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            if(currUser.equals(tempUser)) continue;
            tempOos.writeObject(gameController.getData().getIslandsData());
            tempOos.flush();
        }
    }

    /**
     * sends the information of the moved students to the WaitingClients
     * @param currUser
     * @throws IOException
     */
    private void sendStudentsMoveToOthers(User currUser) throws IOException {
        SchoolData schoolData = currUser.getPlayer().getSchool().getSchoolObserver().getSchoolData();
        /*System.out.print("School updated: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();*/

        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            ObjectOutputStream tempOos = tempUser.getOos();
            tempOos.reset();//needed!
            if(currUser.equals(tempUser)) continue;

            tempOos.writeObject(schoolData);
            tempOos.flush();
            tempOos.writeObject(gameController.getData().getIslandsData());
            tempOos.flush();
        }
    }

    /**
     * sends the data to the user
     * @param currSchool
     * @param oos
     * @throws IOException
     */
    private void sendData(School currSchool, ObjectOutputStream oos) throws IOException {
        String currData;
        currData = "In your school:\n";
        for(Color c : Color.values()){
            currData +="Student "+c+": "+currSchool.getEntranceStudentsNum(c)+"\n";
            if(currSchool.hasProfessor(c)) currData += "Prof" + c + "\n";
        }

        currData+="Islands:\n";

        for(int i=0;i<12;i++){
            if(board.getUnionFind().getGroup(i)!=-1) continue;

            currData+="Island ID: "+i+" ";
            for(Color c: Color.values()){
                currData+=c+" "+board.getUnionFind().getIsland(i).getStudentNum(c)+"| ";
            }
            if(board.getUnionFind().getIsland(i).getHasTower()){
                currData+="Num Towers: "+board.getUnionFind().getGroupSize(i);
            }
            currData+="\n";
        }
        oos.writeObject(currData);
        oos.flush();
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
        oos.writeObject(cloudsData);
        oos.flush();
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
        MovingStudentsMESSAGE msm = (MovingStudentsMESSAGE) ois.readObject();
        for(int i=0;i<gameController.getGameMode().getCloudStudents();i++){

            if(msm.isOnisland(i)){
                switch (msm.getStudent(i)){
                    case GREEN:
                        currSchool.removeEntranceStudent(Color.GREEN);
                        board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.GREEN);
                        break;
                    case RED:
                        currSchool.removeEntranceStudent(Color.RED);
                        board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.RED);
                        break;
                    case YELLOW:
                        currSchool.removeEntranceStudent(Color.YELLOW);
                        board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.YELLOW);
                        break;
                    case PINK:
                        currSchool.removeEntranceStudent(Color.PINK);
                        board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.PINK);
                        break;
                    case BLUE:
                        currSchool.removeEntranceStudent(Color.BLUE);
                        board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.BLUE);
                        break;
                    default:
                        System.out.println("Incorrect value");
                        System.exit(0);
                }

            }
            else{
                switch (msm.getStudent(i)){
                    case GREEN:
                        currSchool.addHallStudent(Color.GREEN);
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


        }
    }

    /**
     * manages mother nature move
     * @param oos
     * @throws IOException
     */
    private void moveMotherNature(ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        oos.writeObject("How many steps? max "+(int)Math.ceil(((double)turnOrder.peek().getPriority()/2.0)));
        oos.flush();

        int stepsChoice = ois.readInt();
        System.out.println("Position before: "+board.getMotherNaturePosition());
        board.moveMotherNature(stepsChoice);
        System.out.println("Position after: "+board.getMotherNaturePosition());
    }
}
