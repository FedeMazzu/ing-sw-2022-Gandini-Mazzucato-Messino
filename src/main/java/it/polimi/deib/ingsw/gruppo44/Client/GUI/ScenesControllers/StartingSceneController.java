package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.NetworkHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;


public class StartingSceneController {
    @FXML
    private Button ipButton;
    @FXML
    private TextField ipTextField;
    @FXML
    private Label ipLabel;
    @FXML
    private Label errorIp;

    /**
     * manages the insertion of the server ip address
     * @param actionEvent
     */
    public void enterIp(ActionEvent actionEvent) throws IOException {
        String serverIp = ipTextField.getText();
        boolean correct = false;
        if (serverIp.length() == 15) {
            correct = true;
            for (int i = 0; i < 15; i++) {
                if (i == 3 || i == 7 || i == 11) {
                    if (serverIp.charAt(i) != '.') {
                        correct = false;
                    }
                } else if (serverIp.charAt(i) < '0' || serverIp.charAt(i) > '9') {
                    correct = false;
                }
            }
        }
        if (correct){
            NetworkHandler nh =new NetworkHandler(serverIp);
            if(nh.isConnectionEstablished()){
                Eriantys.getCurrentApplication().switchToMenuScene();
            }else{
                errorIp.setText("Server not Found!");
                errorIp.setVisible(true);
            }
        }else{
            errorIp.setText("Incorrect IP, try again...");
            errorIp.setVisible(true);
        }

        }

}
