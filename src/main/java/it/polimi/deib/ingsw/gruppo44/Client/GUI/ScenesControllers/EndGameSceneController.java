package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the end game scene
 */
public class EndGameSceneController implements Initializable {

    @FXML
    private Label winLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setEndGameSceneController(this);
    }

    public Label getWinLabel() {
        return winLabel;
    }
}
