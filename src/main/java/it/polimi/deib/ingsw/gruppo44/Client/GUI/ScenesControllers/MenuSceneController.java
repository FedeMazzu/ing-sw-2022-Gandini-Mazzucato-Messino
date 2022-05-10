package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;


import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class MenuSceneController {
    @FXML
    private Button createGameButton, joinGameButton, loadGameButton,createButton,joinButton;
    @FXML
    private Label gameNameLabel, gameModeLabel, errorLabel, waitingLabel, openGamesLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<GameMode> gameModeListView;
    @FXML
    private ListView<Map<String, GameMode>> openGamesListView;

    private GameMode[]gameModes = GameMode.values();

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
        nameTextField.setVisible(true);
        gameModeListView.setVisible(true);
        createButton.setVisible(true);



       /* oos.writeObject(ClientChoice.CreateGameCHOISE);
        oos.flush();
        //maybe need to check if the server receives the option

        CreateGameMESSAGE createGameMESSAGE = new CreateGameMESSAGE(askGameName(),askGameMode());
        oos.writeObject(createGameMESSAGE);
        oos.flush();
        System.out.println("Waiting for the other players...");
        //getting a boolean indicating if the game was joined correctly
        getStartingAck();
        clientController.setClientStage(ClientStage.SETUP);*/
    }

    public void create(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        String name = nameTextField.getText();
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
            nameTextField.setVisible(false);
            gameModeListView.setVisible(false);
            createButton.setVisible(false);
            //label waiting for other players to join
            waitingLabel.setVisible(true);

            getStartingAck();

        }

    }

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

    public void join(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        Map<String, GameMode> gameChoice = openGamesListView.getSelectionModel().getSelectedItem();
        String gameName = gameChoice.keySet().toString();
        GameMode gameMode = gameChoice.get(gameName);
        Eriantys.getCurrentApplication().setGameMode(gameMode);
        System.out.println(gameName);
        oos.writeObject(gameChoice);
        oos.flush();

        openGamesListView.setVisible(false);
        openGamesLabel.setVisible(false);
        joinButton.setVisible(false);
        waitingLabel.setVisible(true);

        getStartingAck();

    }


    private boolean getStartingAck() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
        return ois.readBoolean();
    }
}
