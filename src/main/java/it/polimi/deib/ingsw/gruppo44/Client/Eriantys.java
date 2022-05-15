package it.polimi.deib.ingsw.gruppo44.Client;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.CardsSceneController;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.IslandsSceneController;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.SchoolsScene2pController;
import it.polimi.deib.ingsw.gruppo44.Client.View.GameData;
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
    private SchoolsScene2pController schoolsScene2pController;
    private IslandsSceneController islandsSceneController;
    private GameData gameData;
    private Stage stage;
    private GameMode gameMode;
    private Map<Magician, Integer> magicianId;
    private Scene cardsScene;
    private Scene schoolScene2P;
    private Scene islandsScene;



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
        //CARDS
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/cardsScene.fxml"));
        cardsScene = new Scene(root);
        //SCHOOLS 2P
        Parent schools2P = FXMLLoader.load(getClass().getResource("/fxml/schoolsScene2P.fxml"));
        schoolScene2P = new Scene(schools2P);
        //need to be called after creating the scene
        schoolsScene2pController.buildDataStructures();

        //ISLANDS
        Parent islands = FXMLLoader.load(getClass().getResource("/fxml/islands.fxml"));
        islandsScene = new Scene(islands);
        //need to be called after creating the scene
        islandsSceneController.buildDataStructures();

        //add here the loading of the other game scenes
    }
    public void switchToMenuScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToCardsScene(){
        Platform.runLater(()->{
            stage.setScene(cardsScene);
            stage.show();
        });
    }

    public void switchToIslandsScene(){
        Platform.runLater(()->{
            stage.setScene(islandsScene);
            stage.show();
        });
    }

    public void switchToSchools2PScene() {
        Platform.runLater(()->{
            stage.setScene(schoolScene2P);
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

    public Scene getSchoolScene2P() {
        return schoolScene2P;
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

    public SchoolsScene2pController getSchoolsScene2pController() {
        return schoolsScene2pController;
    }

    public void setSchoolsScene2pController(SchoolsScene2pController schoolsScene2pController) {
        this.schoolsScene2pController = schoolsScene2pController;
    }

    public IslandsSceneController getIslandsSceneController() {
        return islandsSceneController;
    }

    public void setIslandsSceneController(IslandsSceneController islandsSceneController) {
        this.islandsSceneController = islandsSceneController;
    }
}

