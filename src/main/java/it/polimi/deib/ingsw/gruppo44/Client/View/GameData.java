package it.polimi.deib.ingsw.gruppo44.Client.View;
import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.CharacterGuiLogic;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            if(Eriantys.getCurrentApplication().getGameMode().isExpertMode()){
                Label money = schoolGuiLogic.getMoney();
                Integer currMoney = schoolData.getPlayerMoney();
                money.setText(String.valueOf(currMoney));
            }else{
                schoolGuiLogic.getCoin().setVisible(false);
                schoolGuiLogic.getMoney().setVisible(false);
            }

            //updating name and magician redundantly
            Label magicianLabel = schoolGuiLogic.getMagician();
            Label name = schoolGuiLogic.getName();
            magicianLabel.setText(String.valueOf(magician));
            name.setText(schoolData.getPlayerName());

            //updating the tower and the nuj of towers
            String numTower = String.valueOf(schoolData.getNumTower());
            schoolGuiLogic.getNumTower().setText(numTower);
            schoolGuiLogic.getTower().setImage(new Image("/images/pawns/"+schoolData.getTeamTower().getId()+"tower.png"));

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
                if(islandsData.getGroup(i) != -1){
                    igl.coverMergedIsland();
                }
                else{
                    //Lacks of setting the typeof tower
                    //TEMPORARY
                    if(islandsData.getHasTower(i)){
                        igl.getTower().setImage(new Image("/images/pawns/"+islandsData.getTowerType(i).getId()+"tower.png"));
                        if(islandsData.getGroupSize(i)>1) {
                            igl.getNumTowers().setText("x" + islandsData.getGroupSize(i));
                            igl.getNumTowers().setVisible(true);
                        }
                    }
                    else igl.getNumTowers().setVisible(false);
                    igl.getTower().setVisible(islandsData.getHasTower(i));
                    Map<Color,Label> students= igl.getStudents();
                    for(Color color: Color.values()){
                        int studentsNum = islandsData.getStudentsNum(i,color);
                        if(studentsNum == 0){
                            igl.getStudents().get(color).setVisible(false);
                            igl.getStudentsSymbols().get(color).setVisible(false);
                        }else {
                            students.get(color).setText("x" + studentsNum);
                            igl.getStudents().get(color).setVisible(true);
                            igl.getStudentsSymbols().get(color).setVisible(true);
                        }
                    }

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
                    int numStudents = cloudsData.getStudentsNum(i,color);
                    if(numStudents==0) {
                        cgl.getStudentsSymbols().get(color).setVisible(false);
                        cgl.getStudents().get(color).setVisible(false);
                    }else{
                        students.get(color).setText("x" + numStudents);
                        cgl.getStudentsSymbols().get(color).setVisible(true);
                        cgl.getStudents().get(color).setVisible(true);
                    }
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


    public void setCharacters(Map<Integer, Integer> currCharacters) {
        System.out.println(currCharacters);
        System.out.println(characters);

        Platform.runLater(()->{
            for(int val:currCharacters.keySet()){
                if(currCharacters.get(val)>characters.get(val)) {
                    Eriantys.getCurrentApplication().getShopSceneController().cglFromId(val).increasePrice();
                }
            }
            this.characters = currCharacters;
        });
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

    public CloudsData getCloudsData() {
        return cloudsData;
    }

    public void setAvailableCards(List<Integer> ParAvailableCards) {
        this.availableCards = ParAvailableCards;
        Platform.runLater(()->{
            CardsSceneController csc = Eriantys.getCurrentApplication().getCardsSceneController();
            ImageView[] images = csc.getImages();
            for(int i=1; i<=10; i++){
                if(!availableCards.contains(i)) images[i-1].setVisible(false);
                else images[i-1].setOpacity(0.98);
            }
        });
    }

    public void setData(Data data) {
        setIslandsData(data.getIslandsData());
        setCloudsData(data.getCloudsData());
        if(Eriantys.getCurrentApplication().getGameMode().isExpertMode()) {
            characters = data.getBoardData().getCharacters();
            Platform.runLater(() -> {
                List<CharacterGuiLogic> characterGuiLogicList = Eriantys.getCurrentApplication().getShopSceneController().getCharacters();
                int index = 0;
                for (int charId : characters.keySet()) {
                    characterGuiLogicList.get(index).getImage().setImage(new Image("/images/characters/pg" + charId + ".png"));
                    characterGuiLogicList.get(index).setId(charId);
                    index++;
                }

            });
        }
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

