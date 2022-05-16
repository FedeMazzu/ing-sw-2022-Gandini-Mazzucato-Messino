package it.polimi.deib.ingsw.gruppo44.Client.View;
import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.CloudGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.CardsSceneController;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.SchoolGuiLogic;
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

   /* public void setClientMoney(int clientMoney) {
        this.clientMoney = clientMoney;
    }*/

    public void setIslandsData(IslandsData islandsData) {
        this.islandsData = islandsData;

        Platform.runLater(()->{
            for(int i=0; i<12; i++){
                IslandGuiLogic igl = Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().get(i);
                //Lacks of setting the typeof tower
                //TEMPORARY
                igl.getNumTowers().setVisible(false);
                igl.getTower().setVisible(islandsData.getHasTower(i));
                Map<Color,Label> students= igl.getStudents();
                for(Color color: Color.values()){
                    students.get(color).setText("x"+islandsData.getStudentsNum(i,color));
                }

            }
        });


    }

    public void setCloudsData(CloudsData cloudsData) {
        this.cloudsData = cloudsData;
        Platform.runLater(()->{
            for (int i=0; i< Eriantys.getCurrentApplication().getGameMode().getCloudsNumber();i++){
                CloudGuiLogic cgl = Eriantys.getCurrentApplication().getIslandsSceneController().getClouds().get(i);
                //temporary
                cgl.getCircle().setVisible(false);
                Map<Color,Label> students= cgl.getStudents();
                for(Color color: Color.values()){
                    students.get(color).setText("x"+cloudsData.getStudentsNum(i,color));
                }
            }
        });
    }


    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
        Platform.runLater(()->{
            for(int i=0; i<12; i++){
                IslandGuiLogic igl = Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().get(i);
                igl.getMotherNature().setVisible(i==motherNaturePosition);

            }
        });
    }

    public void setCharacters(Map<Integer, Integer> characters) {
        this.characters = characters;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
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
        setIslandsData(data.getIslandsData());
        setCloudsData(data.getCloudsData());
        characters = data.getBoardData().getCharacters();
        setMotherNaturePosition(data.getBoardData().getMotherNaturePosition());
        for(SchoolData sd: data.getSchoolDataList()){
            putSchoolData(sd.getMagician(),sd);
            if(sd.getMagician().equals(clientMagician)){
                //money already updated when calling putDataSchool
                //clientMoney = sd.getPlayerMoney();
                setAvailableCards(sd.getAvailableCards());
            }
        }
    }
}

