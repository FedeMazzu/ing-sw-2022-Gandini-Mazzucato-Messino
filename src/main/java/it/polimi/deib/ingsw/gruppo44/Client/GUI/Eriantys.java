package it.polimi.deib.ingsw.gruppo44.Client.GUI;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.*;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Class to connect Gui and GameData
 */
public class Eriantys extends Application {
    private static Eriantys currentApplication;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private CardsSceneController cardsSceneController;
    private SchoolsSceneController schoolsSceneController;
    private IslandsSceneController islandsSceneController;
    private Socket socket;

    private ShopSceneController shopSceneController;
    private EndGameSceneController endGameSceneController;
    private GameDataGUI gameDataGUI;
    private Stage stage;
    private GameMode gameMode;
    private Map<Magician, Integer> magicianId;
    private Scene cardsScene;
    private Scene schoolScene2P;
    private Scene islandsScene;
    private Scene endGameScene;
    private Scene startingScene;

    private Scene shopScene;




    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        currentApplication = this;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startingScene.fxml"));
        startingScene = new Scene(root);
        stage.setScene(startingScene);
        stage.setTitle("Eriantys");
        stage.getIcons().add(new Image("/images/StageIcon.png"));
        stage.setResizable(false);
        stage.setOnCloseRequest(event ->{
            stage.close();
            System.exit(0);
        });
        //stage.setFullScreen(true);
        stage.show();
    }


    public void loadGameScenes() throws IOException {
        //CARDS
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/cardsScene.fxml"));
        cardsScene = new Scene(root);
        //SCHOOLS 2P
        Parent schools2P = FXMLLoader.load(getClass().getResource("/fxml/schoolsScene.fxml"));
        schoolScene2P = new Scene(schools2P);
        //need to be called after creating the scene
        schoolsSceneController.buildDataStructures();

        //ISLANDS
        Parent islands = FXMLLoader.load(getClass().getResource("/fxml/islands.fxml"));
        islandsScene = new Scene(islands);
        //need to be called after creating the scene
        islandsSceneController.buildDataStructures();

        //SHOP
        if(gameMode.isExpertMode()) {
            Parent shop = FXMLLoader.load(getClass().getResource("/fxml/shopScene.fxml"));
            shopScene = new Scene(shop);
            shopSceneController.buildDataStructures();
        }

        //END GAME
        Parent endGame = FXMLLoader.load(getClass().getResource("/fxml/endGameScene.fxml"));
        endGameScene = new Scene(endGame);
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

    public void switchToShopScene(){
        Platform.runLater(()->{
            stage.setScene(shopScene);
            stage.show();
        });
    }
    public void switchToEndGameScene() {
        Platform.runLater(()->{
            stage.setScene(endGameScene);
            stage.show();
        });
    }

    public void switchToStartingScene(){
        Platform.runLater(()->{
            stage.setScene(startingScene);
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

    public EndGameSceneController getEndGameSceneController() {
        return endGameSceneController;
    }

    public void setEndGameSceneController(EndGameSceneController endGameSceneController) {
        this.endGameSceneController = endGameSceneController;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setGameData(GameDataGUI gameDataGUI) {this.gameDataGUI = gameDataGUI;}

    public GameDataGUI getGameData() {return gameDataGUI;}

    public CardsSceneController getCardsSceneController() {return cardsSceneController;}

    public void setCardsSceneController(CardsSceneController cardsSceneController) {this.cardsSceneController = cardsSceneController;}

    public Map<Magician, Integer> getMagicianId() {
        return magicianId;
    }

    public void setMagicianId(Map<Magician, Integer> magicianId) {
        this.magicianId = magicianId;
    }

    public SchoolsSceneController getSchoolsSceneController() {
        return schoolsSceneController;
    }

    public void setSchoolsSceneController(SchoolsSceneController schoolsSceneController) {
        this.schoolsSceneController = schoolsSceneController;
    }

    public IslandsSceneController getIslandsSceneController() {
        return islandsSceneController;
    }

    public void setIslandsSceneController(IslandsSceneController islandsSceneController) {
        this.islandsSceneController = islandsSceneController;
    }

    public ShopSceneController getShopSceneController() {
        return shopSceneController;
    }

    public void setShopSceneController(ShopSceneController shopSceneController) {
        this.shopSceneController = shopSceneController;
    }
}

