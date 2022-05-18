package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopSceneController implements Initializable {

    private ImageView [] images;
    private Label [] prices;
    private Rectangle [] highlights;

    @FXML
    private Button cardsbutton;

    @FXML
    private ImageView character1;

    @FXML
    private Label character1Label;

    @FXML
    private ImageView character2;

    @FXML
    private Label character2Label;

    @FXML
    private ImageView character3;

    @FXML
    private Label character3Label;

    @FXML
    private Button islandsbutton;

    @FXML
    private Rectangle rect1;

    @FXML
    private Rectangle rect2;

    @FXML
    private Rectangle rect3;

    @FXML
    private Button schoolsbutton;

    @FXML
    void switchToCards(ActionEvent event) {

    }

    @FXML
    void switchToIslands(ActionEvent event) {
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }

    @FXML
    void switchToSchools(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setShopSceneController(this);
    }

    public void buildDataStructures(){
        Scene currScene = rect1.getScene();
        images = new ImageView[3];
        prices = new Label[3];
        highlights = new Rectangle[3];
        for(int i=0;i<3;i++){
            images[i] = (ImageView) currScene.lookup("#character"+(i+1));
            prices[i] = (Label) currScene.lookup("#character"+(i+1)+"Label");
            highlights[i] = (Rectangle) currScene.lookup("#rect"+(i+1));

        }
    }

    public ImageView[] getImages() {
        return images;
    }

    public Label[] getPrices() {
        return prices;
    }

    public Rectangle[] getHighlights() {
        return highlights;
    }
}
