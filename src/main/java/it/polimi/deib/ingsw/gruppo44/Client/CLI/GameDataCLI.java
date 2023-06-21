package it.polimi.deib.ingsw.gruppo44.Client.CLI;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to contain the game's data in the client application for the CLI
 */
public class GameDataCLI {
    //curr Client data
    private final Magician clientMagician;
    private int clientMoney;
    private List<Integer> availableCards;

    // Game data
    private IslandsData islandsData;
    private CloudsData cloudsData;
    private final Map<Magician, SchoolData> schoolDataMap;
    // Map<CharacterId, CharacterPrice>
    private Map<Integer,Integer> characters;

    public GameDataCLI(Magician clientMagician) {
        this.clientMagician = clientMagician;
        schoolDataMap = new HashMap<>();
    }


    public void putSchoolData(Magician magician, SchoolData schoolData){
        schoolDataMap.put(magician,schoolData);
        if(clientMagician.equals(magician)){
            clientMoney = schoolData.getPlayerMoney();
            setAvailableCards(schoolData.getAvailableCards());
        }

    }

    public void setIslandsData(IslandsData islandsData) {
        this.islandsData = islandsData;




    }

    public void setCloudsData(CloudsData cloudsData) {
        this.cloudsData = cloudsData;

    }

    public void setCharacters(Map<Integer, Integer> currCharacters) {
        this.characters = currCharacters;

    }

    public Magician getClientMagician() {
        return clientMagician;
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
    }

    public void setData(Data data) {
        setIslandsData(data.getIslandsData());
        setCloudsData(data.getCloudsData());
        characters = data.getBoardData().getCharacters();
        for(SchoolData sd: data.getSchoolDataList()){
            putSchoolData(sd.getMagician(),sd);
            if(sd.getMagician().equals(clientMagician)){
                setAvailableCards(sd.getAvailableCards());
            }
        }



    }
}
