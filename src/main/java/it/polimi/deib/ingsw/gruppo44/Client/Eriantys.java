package it.polimi.deib.ingsw.gruppo44.Client;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.CardsSceneController;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class Eriantys extends Application {
    private static Eriantys currentApplication;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private CardsSceneController cardsSceneController;
    private GameData gameData;
    private Stage stage;
    private GameMode gameMode;
    private Map<Magician, Integer> magicianId;
    private Scene cardsScene;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        currentApplication = this;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startingScene.fxml"));
        Scene startingScene = new Scene(root);
        stage.setScene(startingScene);
        stage.setTitle("Eriantys");
        stage.setResizable(false);
        //stage.setFullScreen(true);
        stage.show();
    }


    public void loadGameScenes() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/cardsScene.fxml"));
        cardsScene = new Scene(root);
        //add here the loading of the other game scenes
    }
    public void switchToMenuScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToCardsScene() throws IOException{
        Platform.runLater(()->{
            stage.setScene(cardsScene);
            stage.show();
        });
    }


    public static Eriantys getCurrentApplication(){return currentApplication;}

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setGameData(GameData gameData) {this.gameData = gameData;}

    public GameData getGameData() {return gameData;}

    public CardsSceneController getCardsSceneController() {return cardsSceneController;}

    public void setCardsSceneController(CardsSceneController cardsSceneController) {this.cardsSceneController = cardsSceneController;}

    public Map<Magician, Integer> getMagicianId() {
        return magicianId;
    }

    public void setMagicianId(Map<Magician, Integer> magicianId) {
        this.magicianId = magicianId;
    }
}

