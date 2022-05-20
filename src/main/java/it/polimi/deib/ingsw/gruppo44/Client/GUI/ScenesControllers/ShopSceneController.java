package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.CharacterGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.MessagesMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;

public class ShopSceneController implements Initializable {

    private List<CharacterGuiLogic> characters;

    @FXML
    private Button cardsbutton,notBuyButton;

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
        characters = new ArrayList<>();

        for(int i=0;i<3;i++){

            CharacterGuiLogic tempChar = new CharacterGuiLogic((ImageView) currScene.lookup("#character"+(i+1)),
                    (ImageView) currScene.lookup("#m"+(i+1)),
                    (Rectangle) currScene.lookup("#rect"+(i+1))
            );
            characters.add(tempChar);
        }
    }

    public CharacterGuiLogic cglFromId(int id){
        CharacterGuiLogic ans = null;
        for(CharacterGuiLogic cgl:characters){
            if(cgl.getId() == id) ans = cgl;
        }
        return ans;
    }

    public List<CharacterGuiLogic> getCharacters() {
        return characters;
    }

    public void doNotBuy(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        oos.writeBoolean(false);
        oos.flush();
        notBuyButton.setVisible(false);
        for(CharacterGuiLogic cgl: characters){
            cgl.getHighlight().setVisible(false);
        }
        MessagesMethods.setForMovingStudents();
    }

    public Button getNotBuyButton() {
        return notBuyButton;
    }

    public void effectImage1(MouseEvent mouseEvent) throws IOException {
        sendCharacter(characters.get(0).getId());
    }
    public void effectImage2(MouseEvent mouseEvent) throws IOException {
        sendCharacter(characters.get(1).getId());

    }
    public void effectImage3(MouseEvent mouseEvent) throws IOException {
        sendCharacter(characters.get(2).getId());

    }

    private void sendCharacter(int id) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();

        //sending the server the decision of using a character
        oos.writeBoolean(true);
        oos.flush();

        //sending the character id used
        oos.writeInt(id);
        oos.flush();

        notBuyButton.setVisible(false);
        for(CharacterGuiLogic cgl: characters) cgl.getHighlight().setVisible(false);

        MessagesMethods.setForMovingStudents();

    }
}
