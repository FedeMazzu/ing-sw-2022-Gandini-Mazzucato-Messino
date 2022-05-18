package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopSceneController implements Initializable {

    @FXML
    private Button cardsbutton;

    @FXML
    private ImageView character1;

    @FXML
    private ImageView character2;

    @FXML
    private ImageView character3;

    @FXML
    private Button islandsbutton;

    @FXML
    private Button schoolsbutton;

    @FXML
    void switchToCards(ActionEvent event) {

    }

    @FXML
    void switchToIslands(ActionEvent event) {

    }

    @FXML
    void switchToSchools(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setShopSceneController(this);
    }
}
