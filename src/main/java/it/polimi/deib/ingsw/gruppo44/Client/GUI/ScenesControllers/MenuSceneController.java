package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;


import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GameData;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
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
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuSceneController {
    @FXML
    private Button createGameButton, joinGameButton, loadGameButton,createButton,joinButton,selectButton;
    @FXML
    private Label gameNameLabel, gameModeLabel, errorLabel, waitingLabel, openGamesLabel,nameLabel,magicianLabel;
    @FXML
    private TextField gameNameTextField,nameTextField;
    @FXML
    private ListView<GameMode> gameModeListView;
    @FXML
    private ListView<Map<String, GameMode>> openGamesListView;
    @FXML
    private ListView<Magician> magicianListView;

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
        oos.writeObject(ClientChoice.JoinGameCHOISE);
        oos.flush();
        openGames = (Map<String, GameMode>) ois.readObject();
        if(!openGames.isEmpty()) {

            openGamesListView.getItems().addAll(openGames);

            createGameButton.setVisible(false);
            joinGameButton.setVisible(false);
            loadGameButton.setVisible(false);
            openGamesListView.setVisible(true);
            joinButton.setVisible(true);
            openGamesLabel.setVisible(true);
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
        Map<String, GameMode> gameChoice = openGamesListView.getSelectionModel().getSelectedItem();
        String gameName = (String)gameChoice.keySet().toArray()[0];
        GameMode gameMode = gameChoice.get(gameName);
        Eriantys.getCurrentApplication().setGameMode(gameMode);
        oos.writeObject(gameName);
        oos.flush();

        openGamesListView.setVisible(false);
        openGamesLabel.setVisible(false);
        joinButton.setVisible(false);
        waitingLabel.setVisible(true);
        new Thread(this::getStartingAck).start();
    }

    /**
     * select magician and nickname
     */
    public void select (MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();

        //sending magician and name
        Magician magicianChoice = magicianListView.getSelectionModel().getSelectedItem();
        String name = nameLabel.getText();
        if(magicianChoice == null || name.length() == 0){
            errorLabel.setText("Invalid input");
            errorLabel.setVisible(true);
        }
        else{
            errorLabel.setVisible(false);
            Eriantys.getCurrentApplication().setGameData(new GameData(magicianChoice));
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
            Data data = (Data) ois.readObject();
            //need to be in the event thread
            setMagicianId(data);
            //need to be after setMagicianId
            Eriantys.getCurrentApplication().switchToSchools2PScene();
            //Eriantys.getCurrentApplication().loadGameScenes();
            Eriantys.getCurrentApplication().getGameData().setData(data);
            //new Thread(new WaitCards()).start();

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
}

