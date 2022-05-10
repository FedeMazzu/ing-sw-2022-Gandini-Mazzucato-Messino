package it.polimi.deib.ingsw.gruppo44.Client.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class Eriantys extends Application {
    private static Eriantys currentApplication;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        currentApplication = this;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startingScene.fxml"));
        Scene startingScene = new Scene(root);
        stage.setScene(startingScene);
        stage.setTitle("Eriantys");
        //stage.setFullScreen(true);
        stage.show();
    }

    public void switchToMenuScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
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


}
