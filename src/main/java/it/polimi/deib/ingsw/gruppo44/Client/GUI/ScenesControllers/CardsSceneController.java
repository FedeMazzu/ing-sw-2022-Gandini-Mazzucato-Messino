package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CardsSceneController implements Initializable {
    @FXML
    private ImageView im1,im2,im3,im4,im5,im6,im7,im8,im9,im10;
    private ImageView[]images;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setCardsSceneController(this);
        images = new ImageView[]{im1,im2,im3,im4,im5,im6,im7,im8,im9,im10};

    }

    public ImageView[] getImages() {return images;}
}
