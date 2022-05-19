package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CharacterGuiLogic {

    private ImageView image;
    private ImageView coin;
    private Rectangle highlight;
    private int id;

    public CharacterGuiLogic(ImageView image, ImageView coin, Rectangle highlight) {
        this.image = image;
        this.coin = coin;
        this.highlight = highlight;
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
    }
}
