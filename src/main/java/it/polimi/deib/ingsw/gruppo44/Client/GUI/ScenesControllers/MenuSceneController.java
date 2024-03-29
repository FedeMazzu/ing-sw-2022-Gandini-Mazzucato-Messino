package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;


import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.GameDataGUI;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.WaitProcesses.WaitCards;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages.Action;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Controller of the menu scene
 */
public class MenuSceneController {



    @FXML
    private ImageView magicianIV;
    @FXML
    private Button selectUsedMagicianButton, createGameButton, joinGameButton, loadGameButton,createButton,joinButton,selectButton, confirmLoadGameButton, joinLoadedGameButton;
    @FXML
    private Label whoLabel,gameNameLabel, gameModeLabel, errorLabel, waitingLabel, openGamesLabel,nameLabel,magicianLabel;
    @FXML
    private TextField gameNameTextField,nameTextField;
    @FXML
    private ListView<GameMode> gameModeListView;
    @FXML
    private ListView<Map.Entry<String,GameMode>> openGamesListView;
    @FXML
    private ListView<Magician> magicianListView;
    @FXML
    private ListView<String> loadGameListView,loadedOpenGames;

    private GameMode[]gameModes = GameMode.values();

    /**
     * choose the option select a game
     * @param actionEvent
     * @throws IOException
     */
    public void createGame(ActionEvent actionEvent) throws IOException {
        //communicating the decision to the server
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        oos.writeObject(ClientChoice.CreateGameCHOISE);


        gameModeListView.getItems().addAll(gameModes);
        createGameButton.setVisible(false);
        joinGameButton.setVisible(false);
        loadGameButton.setVisible(false);
        errorLabel.setVisible(false);
        gameNameLabel.setVisible(true);
        gameModeLabel.setVisible(true);
        gameNameTextField.setVisible(true);
        gameModeListView.setVisible(true);
        createButton.setVisible(true);

    }

    /**
     * create the game with the name and Mode selected
     * @param actionEvent
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void create(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        String name = gameNameTextField.getText();
        GameMode gameMode = gameModeListView.getSelectionModel().getSelectedItem();
        if(name.length()==0 || gameMode == null){
            errorLabel.setText("Invalid input... try again");
            errorLabel.setVisible(true);
            //label with error
        }else{
            Eriantys.getCurrentApplication().setGameMode(gameMode);
            errorLabel.setVisible(false);
            CreateGameMESSAGE createGameMESSAGE = new CreateGameMESSAGE(name,gameMode);
            oos.writeObject(createGameMESSAGE);
            oos.flush();
            gameNameLabel.setVisible(false);
            gameModeLabel.setVisible(false);
            gameNameTextField.setVisible(false);
            gameModeListView.setVisible(false);
            createButton.setVisible(false);
            //label waiting for other players to join
            waitingLabel.setVisible(true);

            new Thread(this::getStartingAck).start();

        }

    }

    /**
     * choose the joinGame option
     * @param action
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void joinGame(ActionEvent action) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
        Map<String, GameMode> openGames;
        List<String> openGamesLoaded;
        oos.writeObject(ClientChoice.JoinGameCHOISE);
        oos.flush();
        openGames = (Map<String, GameMode>) ois.readObject();
        openGamesLoaded = (List<String>)ois.readObject();
        if(!openGames.isEmpty() || !openGamesLoaded.isEmpty()) {
            errorLabel.setVisible(false);
            createGameButton.setVisible(false);
            joinGameButton.setVisible(false);
            loadGameButton.setVisible(false);
            if(!openGames.isEmpty()) {

                for (Map.Entry<String, GameMode> game : openGames.entrySet()) {
                    openGamesListView.getItems().add(game);
                }
                openGamesListView.setVisible(true);
                joinButton.setVisible(true);
                openGamesLabel.setVisible(true);
            }
            if(!openGamesLoaded.isEmpty()){
                loadedOpenGames.getItems().addAll(openGamesLoaded);
                loadedOpenGames.setVisible(true);
                joinLoadedGameButton.setVisible(true);
            }


        }else{
            errorLabel.setText("There aren't available games");
            errorLabel.setVisible(true);
        }
    }

    /**
     * join the selected game
     * @param actionEvent
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void join(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        Map.Entry<String, GameMode> gameChoice = openGamesListView.getSelectionModel().getSelectedItem();
        String gameName = (String)gameChoice.getKey();
        GameMode gameMode = gameChoice.getValue();
        Eriantys.getCurrentApplication().setGameMode(gameMode);
        oos.writeObject(gameName);
        oos.flush();

        openGamesListView.setVisible(false);
        openGamesLabel.setVisible(false);
        joinButton.setVisible(false);
        loadedOpenGames.setVisible(false);
        joinLoadedGameButton.setVisible(false);
        waitingLabel.setVisible(true);
        new Thread(this::getStartingAck).start();
    }

    /**
     * select magician and nickname
     */
    public void select (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();

        //sending magician and name
        Magician magicianChoice = magicianListView.getSelectionModel().getSelectedItem();
        String name = nameTextField.getText();
        if(name.length() == 0 || magicianChoice == null){
            errorLabel.setVisible(true);
        }
        else{
            errorLabel.setVisible(false);
            Eriantys.getCurrentApplication().setGameData(new GameDataGUI(magicianChoice));
            oos.writeObject(magicianChoice);
            oos.flush();
            oos.writeObject(name);
            oos.flush();

            //set components invisible while we wait for others to join
            nameLabel.setVisible(false);
            nameTextField.setVisible(false);
            magicianListView.setVisible(false);
            magicianLabel.setVisible(false);
            selectButton.setVisible(false);

            waitingLabel.setText("Waiting for your turn of choosing a card");
            waitingLabel.setVisible(true);
            //receiving the data of the initialized game
            new Thread(() -> receiveDataAndSetTheGame()).start();

        }

    }

    private void setMagicianId(Data data) {
        Map<Magician,Integer> magicianId= new HashMap<>();
        int i=1;
        for(SchoolData sd: data.getSchoolDataList()){
            magicianId.put(sd.getMagician(),i);
            i++;
        }
        Eriantys.getCurrentApplication().setMagicianId(magicianId);
    }


    private void getStartingAck(){
        try {
            ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
            //getting starting ack
            ois.readBoolean();
            List<Magician> availableMagicians = (List<Magician>) ois.readObject();
            Platform.runLater(()-> {
                waitingLabel.setVisible(false);
                magicianListView.getItems().addAll(availableMagicians);
                nameLabel.setVisible(true);
                magicianLabel.setVisible(true);
                selectButton.setVisible(true);
                magicianListView.setVisible(true);
                nameTextField.setVisible(true);
            });
        }catch(IOException | ClassNotFoundException e){

        }
    }

    public void loadGame(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        //ask the server for the list of loadable games
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
        ObjectOutputStream oos =  Eriantys.getCurrentApplication().getOos();
        oos.writeObject(ClientChoice.LoadGameCHOICE);
        oos.flush();
        List<String> games = (List<String>) ois.readObject();
        if(!games.isEmpty()) {
            createGameButton.setVisible(false);
            joinGameButton.setVisible(false);
            loadGameButton.setVisible(false);
            errorLabel.setVisible(false);
            loadGameListView.getItems().clear();
            loadGameListView.getItems().addAll(games);
            loadGameListView.setVisible(true);
            confirmLoadGameButton.setVisible(true);
        }else{
            errorLabel.setText("There aren't available games");
            errorLabel.setVisible(true);
        }

    }

    public void confirmLoading (ActionEvent actionEvent) throws IOException {
        String chosenGame = loadGameListView.getSelectionModel().getSelectedItem();
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();

        oos.writeObject(chosenGame);
        oos.flush();

        loadGameListView.setVisible(false);
        confirmLoadGameButton.setVisible(false);
        new Thread(()-> {
            try {
                prepareForMagicianReloading();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void showMagician(MouseEvent mouseEvent) {
        Magician magician = magicianListView.getSelectionModel().getSelectedItem();
        if (magician != null) {
            magicianIV.setImage(new Image("/images/magicians/" + magician + ".jpg"));
        }
    }

    public void joinLoadedGame(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        String gameName;
        gameName = loadedOpenGames.getSelectionModel().getSelectedItem();
        if(gameName != null){
            oos.writeObject(gameName);
            oos.flush();
            loadedOpenGames.setVisible(false);
            joinLoadedGameButton.setVisible(false);
            errorLabel.setVisible(false);
            openGamesListView.setVisible(false);
            joinButton.setVisible(false);
            openGamesLabel.setVisible(false);
            new Thread(()-> {
                try {
                    prepareForMagicianReloading();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    private void prepareForMagicianReloading() throws IOException, ClassNotFoundException {
        List<Magician> usedMagicians= (List<Magician>) Eriantys.getCurrentApplication().getOis().readObject();
        Platform.runLater(()->{
            magicianListView.getItems().clear();
            magicianListView.getItems().addAll(usedMagicians);
            magicianListView.setVisible(true);
            whoLabel.setVisible(true);
            selectUsedMagicianButton.setVisible(true);
        });

    }


    public void selectUsedMagician(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();

        Magician magician = magicianListView.getSelectionModel().getSelectedItem();
        if(magician!=null){
            Eriantys.getCurrentApplication().setGameData(new GameDataGUI(magician));
            oos.writeObject(magician);
            oos.flush();
            magicianListView.setVisible(false);
            whoLabel.setVisible(false);
            selectUsedMagicianButton.setVisible(false);
            new Thread(()-> receiveDataAndGameModeAndSetTheGame()).start();
        }
    }

    private void receiveDataAndGameModeAndSetTheGame() {
        try {
            ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
            //receiving the gameMode
            Eriantys.getCurrentApplication().setGameMode((GameMode) ois.readObject());

            Data data = (Data) ois.readObject();
            //need to be in the event thread
            setMagicianId(data);
            //need to be after setMagicianId

            Eriantys.getCurrentApplication().loadGameScenes();
            Eriantys.getCurrentApplication().getGameData().setData(data);
            new Thread(new WaitCards()).start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void receiveDataAndSetTheGame(){
        try {
            ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
            Data data = (Data) ois.readObject();
            //need to be in the event thread
            setMagicianId(data);
            //need to be after setMagicianId

            Eriantys.getCurrentApplication().loadGameScenes();
            Eriantys.getCurrentApplication().getGameData().setData(data);
            new Thread(new WaitCards()).start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

