package it.polimi.deib.ingsw.gruppo44.Client;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.CardsSceneController;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.SchoolGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to contain the game's data in the client application
 * @author
 */
public class GameData {
    //curr Client data
    private Magician clientMagician;
    private int clientMoney;
    private List<Integer> availableCards;

    // Game data
    private IslandsData islandsData;
    private CloudsData cloudsData;
    private Map<Magician, SchoolData> schoolDataMap;
    private int motherNaturePosition;
    // Map<CharacterId, CharacterPrice>
    private Map<Integer,Integer> characters;

    public GameData(Magician clientMagician) {
        this.clientMagician = clientMagician;
        schoolDataMap = new HashMap<>();
    }


    public void putSchoolData(Magician magician, SchoolData schoolData){
        schoolDataMap.put(magician,schoolData);
        if(clientMagician.equals(magician)){
            clientMoney = schoolData.getPlayerMoney();
            setAvailableCards(schoolData.getAvailableCards());
        }
        Platform.runLater(()->{

            SchoolGuiLogic schoolGuiLogic = Eriantys.getCurrentApplication().getSchoolsScene2pController().getSchoolInfo().get(magician);
            Map<Color, Label> hallStudents = schoolGuiLogic.getHallStudents();
            Map<Color, Label> entranceStudents = schoolGuiLogic.getEntranceStudents();
            Map<Color,ImageView> prof = schoolGuiLogic.getProf();
            for(Color color:Color.values()){
                //hallStudents update
                hallStudents.get(color).setText("x"+schoolData.getHallStudentsNum(color));
                //entranceStudents update
                entranceStudents.get(color).setText("x"+schoolData.getEntranceStudentsNum(color));
                //prof update
                prof.get(color).setVisible(schoolData.hasProfessor(color));
            }
            //updating money
            Label money = schoolGuiLogic.getMoney();
            Integer currMoney = schoolData.getPlayerMoney();
            money.setText(String.valueOf(currMoney));

            //updating name and magician redundantly
            Label magicianLabel = schoolGuiLogic.getMagician();
            Label name = schoolGuiLogic.getName();
            magicianLabel.setText(String.valueOf(magician));
            name.setText(schoolData.getPlayerName());

        });
    }

    public void setClientMoney(int clientMoney) {
        this.clientMoney = clientMoney;
    }

    public void setIslandsData(IslandsData islandsData) {
        this.islandsData = islandsData;
    }

    public void setCloudsData(CloudsData cloudsData) {
        this.cloudsData = cloudsData;
    }


    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public void setCharacters(Map<Integer, Integer> characters) {
        this.characters = characters;
    }


    public Magician getClientMagician() {
        return clientMagician;
    }

    public int getClientMoney() {
        return clientMoney;
    }

    public Map<Magician, SchoolData> getSchoolDataMap() {
        return schoolDataMap;
    }

    public IslandsData getIslandsData() {
        return islandsData;
    }


    /**
     * @return a Map of the characters which have price <= clientMoney
     */
    public Map<Integer, Integer> getAffordableCharacters() {
        Map<Integer,Integer> affordableCharacters = new HashMap<>();

        //DEEP COPY
        int price;
        for(Integer characterId: characters.keySet()){
            price = characters.get(characterId);
            if(price <= clientMoney) affordableCharacters.put(characterId,price);
        }

        return affordableCharacters;
    }

    public List<Integer> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<Integer> ParAvailableCards) {
        this.availableCards = ParAvailableCards;
        Platform.runLater(()->{
            CardsSceneController csc = Eriantys.getCurrentApplication().getCardsSceneController();
            ImageView[] images = csc.getImages();
            for(int i=1; i<=10; i++){
                if(!availableCards.contains(i)) images[i-1].setVisible(false);
                else images[i-1].setOpacity(1.0);
            }
        });
    }

    public void setData(Data data) {
        islandsData =data.getIslandsData();
        cloudsData = data.getCloudsData();
        characters = data.getBoardData().getCharacters();
        motherNaturePosition = data.getBoardData().getMotherNaturePosition();
        for(SchoolData sd: data.getSchoolDataList()){
            putSchoolData(sd.getMagician(),sd);
            if(sd.getMagician().equals(clientMagician)){
                clientMoney = sd.getPlayerMoney();
                setAvailableCards(sd.getAvailableCards());
            }
        }
    }
}

