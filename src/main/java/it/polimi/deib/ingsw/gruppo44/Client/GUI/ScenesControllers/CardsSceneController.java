package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

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
    private List<ImageView> images;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new ArrayList<ImageView>();
        images.add(im1);
        images.add(im2);
        images.add(im3);
        images.add(im4);
        images.add(im5);
        images.add(im6);
        images.add(im7);
        images.add(im8);
        images.add(im9);
        images.add(im10);

    }
}
