package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.CharacterGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;

public class ShopSceneController implements Initializable {

    private List<CharacterGuiLogic> characters;
    private int character10counter;

    @FXML
    private Button swapButton, endEffectButton;
    @FXML
    private Label entranceLabel, hallLabel;
    @FXML
    private ListView entranceLV, hallLV;
    @FXML
    private Label id1, id2, id3;
    @FXML
    private Button chooseColorButton;

    @FXML
    private ListView<Color> colorLV;

    @FXML
    private Button  notBuyButton;

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
    void switchToIslands(ActionEvent event) {
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setShopSceneController(this);
    }

    public void buildDataStructures() {
        character10counter = 0;
        Scene currScene = rect1.getScene();
        characters = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            CharacterGuiLogic tempChar = new CharacterGuiLogic((ImageView) currScene.lookup("#character" + (i + 1)),
                    (ImageView) currScene.lookup("#m" + (i + 1)),
                    (Rectangle) currScene.lookup("#rect" + (i + 1)),
                    (Label) currScene.lookup(("#id" + (i + 1)))
            );
            characters.add(tempChar);
        }
    }

    public void setCharactersVisibility(boolean value) {
        for (CharacterGuiLogic cgl : characters) {
            if(value == false){
                cgl.getHighlight().setVisible(false);
                cgl.getCoin().setVisible(value);
            }else if(cgl.isPriceIncreased()){
                cgl.getCoin().setVisible(true);
            }
            cgl.getImage().setVisible(value);
            cgl.getIdLabel().setVisible(value);
        }
    }

    public CharacterGuiLogic cglFromId(int id) {
        CharacterGuiLogic ans = null;
        for (CharacterGuiLogic cgl : characters) {
            if (cgl.getId() == id) ans = cgl;
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
        for (CharacterGuiLogic cgl : characters) {
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
        for (CharacterGuiLogic cgl : characters) cgl.getHighlight().setVisible(false);
        Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("Character bought!");
        new Thread(() -> {
            try {
                handleCharacter(id);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * useful to handle character 10 effect
     * @param actionEvent
     */
    public void swapStudents(ActionEvent actionEvent) throws IOException {
        Color hallColor = (Color)hallLV.getSelectionModel().getSelectedItem();
        Color entranceColor = (Color)entranceLV.getSelectionModel().getSelectedItem();
        if(hallColor!=null && entranceColor!=null) {
            character10counter++;

            hallLV.getItems().remove(hallColor);
            entranceLV.getItems().remove(entranceColor);
            ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
            oos.writeBoolean(true);
            oos.flush();

            oos.writeObject(hallColor);
            oos.flush();
            oos.writeObject(entranceColor);
            oos.flush();

            if (character10counter == 2) {
                character10counter = 0;
                entranceLV.getItems().clear();
                hallLV.getItems().clear();

                entranceLV.setVisible(false);
                hallLV.setVisible(false);
                entranceLabel.setVisible(false);
                hallLabel.setVisible(false);
                swapButton.setVisible(false);
                endEffectButton.setVisible(false);

                setCharactersVisibility(true);
            }
        }
    }
    /**
     * useful to end character 10 effect before doing all the swaps
     * @param actionEvent
     */
    public void endEffect(ActionEvent actionEvent) throws IOException {
        character10counter = 0;
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        oos.writeBoolean(false);
        oos.flush();
        entranceLV.getItems().clear();
        hallLV.getItems().clear();

        entranceLV.setVisible(false);
        hallLV.setVisible(false);
        entranceLabel.setVisible(false);
        hallLabel.setVisible(false);
        swapButton.setVisible(false);
        endEffectButton.setVisible(false);

        setCharactersVisibility(true);

    }


    private void handleCharacter(int currentCharacter) throws IOException, ClassNotFoundException {
        boolean endGame = false;
        switch (currentCharacter) {
            case 1:
                break;
            case 2:
                handleCharacter2();
                break;
            case 3:
                endGame = handleCharacter3();
                break;
            case 4:
                handleCharacter4();
                break;
            case 6:
                handleCharacter6();
                break;
            case 8:
                handleCharacter8();
                break;
            case 9:
                handleCharacter9();
                break;
            case 10:
                handleCharacter10();
                break;
            case 12:
                handleCharacter12();
                break;
        }
        if (!endGame) {
            //receiving the updated money after using a character
            MessagesMethods.receiveSchoolsUpdated();
            //receiving the updated prices
            MessagesMethods.receiveUpdatedPrices();

            //to play the standard turn
            MessagesMethods.setForMovingStudents();
        }
    }

    private void handleCharacter2() throws IOException, ClassNotFoundException {
        //managed server side
    }

    private void handleCharacter4() {
        Eriantys.getCurrentApplication().getIslandsSceneController().setUsingCharacter4True();
    }

    private boolean handleCharacter3() throws IOException, ClassNotFoundException {
        Platform.runLater(() -> {
            Eriantys.getCurrentApplication().getIslandsSceneController().setUsingCharacter3True();
            for (IslandGuiLogic igl : Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().values()) {
                if (!igl.isCovered()) {
                    igl.getCircle().setVisible(true);
                }
            }
            Eriantys.getCurrentApplication().switchToIslandsScene();

        });

        MessagesMethods.receiveIslandsUpdated();

        boolean endGame = Eriantys.getCurrentApplication().getOis().readBoolean();
        if (endGame) {
            Eriantys.getCurrentApplication().switchToEndGameScene();
        }
        //to not move to the standard turn
        return endGame;
    }

    private void handleCharacter6() {
        //managed server side
    }

    private void handleCharacter8() {
        //managed server side
    }

    private void handleCharacter9() {
        setCharactersVisibility(false);
        for (Color c : Color.values()) {
            colorLV.getItems().add(c);
        }
        colorLV.setVisible(true);
        chooseColorButton.setVisible(true);

    }

    /** to handle character 9,12 effect
     *
     * @param actionEvent
     * @throws IOException
     */
    public void chooseColor(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        Color color = (Color) colorLV.getSelectionModel().getSelectedItem();
        if (color != null) {
            oos.writeObject(color);
            oos.flush();
        }
        chooseColorButton.setVisible(false);
        colorLV.getItems().clear();
        colorLV.setVisible(false);
        setCharactersVisibility(true);
        //Eriantys.getCurrentApplication().switchToIslandsScene();
    }


    private void handleCharacter10() throws IOException, ClassNotFoundException {
        setCharactersVisibility(false);
        SchoolData sd = Eriantys.getCurrentApplication().getGameData().getSchoolDataMap().get(Eriantys.getCurrentApplication().getGameData().getClientMagician());
        for (Color color : Color.values()) {
            for (int i = 0; i < sd.getEntranceStudentsNum(color); i++) {
                entranceLV.getItems().add(color);
            }
            for (int i = 0; i < sd.getHallStudentsNum(color); i++) {
                hallLV.getItems().add(color);
            }
        }

        entranceLV.setVisible(true);
        hallLV.setVisible(true);
        entranceLabel.setVisible(true);
        hallLabel.setVisible(true);
        swapButton.setVisible(true);
        endEffectButton.setVisible(true);

    }

    private void handleCharacter12() throws IOException, ClassNotFoundException {
        setCharactersVisibility(false);
        for (Color c : Color.values()) {
            colorLV.getItems().add(c);
        }
        colorLV.setVisible(true);
        chooseColorButton.setVisible(true);

    }

}
