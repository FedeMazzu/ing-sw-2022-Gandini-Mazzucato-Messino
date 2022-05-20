package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CharacterGuiLogic {

    private ImageView image;
    private ImageView coin;
    private Rectangle highlight;
    private Label idLabel;
    private int id;

    public CharacterGuiLogic(ImageView image, ImageView coin, Rectangle highlight,Label idLabel) {
        this.image = image;
        this.coin = coin;
        this.highlight = highlight;
        this.idLabel = idLabel;
    }

    public ImageView getImage() {
        return image;
    }

    public ImageView getCoin() {
        return coin;
    }

    public Rectangle getHighlight() {
        return highlight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        Platform.runLater(()->{
            idLabel.setText(String.valueOf(id));
        });
    }

    public Label getIdLabel() {
        return idLabel;
    }
}
