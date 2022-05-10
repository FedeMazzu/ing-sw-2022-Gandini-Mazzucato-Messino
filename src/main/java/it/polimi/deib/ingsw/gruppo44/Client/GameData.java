package it.polimi.deib.ingsw.gruppo44.Client;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

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
            availableCards = schoolData.getAvailableCards();
        }
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

    public void setAvailableCards(List<Integer> availableCards) {
        this.availableCards = availableCards;
    }

    public void setData(Data data) {
        islandsData =data.getIslandsData();
        cloudsData = data.getCloudsData();
        characters = data.getBoardData().getCharacters();
        motherNaturePosition = data.getBoardData().getMotherNaturePosition();
        for(SchoolData sd: data.getSchoolDataList()){
            schoolDataMap.put(sd.getMagician(),sd);
            if(sd.getMagician().equals(clientMagician)){
                clientMoney = sd.getPlayerMoney();
                availableCards = sd.getAvailableCards();
            }
        }
    }
}

