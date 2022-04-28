package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Cloud;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class made to test CloudsData and the implementation of the observer pattern related
 * @author filippogandini
 */
class CloudsDataTest {
    private Game game;
    private Cloud cloud;
    private CloudsData cloudsData;

    @BeforeEach
    public void SetUp(){
        game = new Game(GameMode.TwoPlayersBasic);
        cloud = game.getBoard().getClouds().get(0);
        cloudsData = game.getBoard().getCloudsObserver().getCloudsData();
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    public void checkInitialization__wocd(){
        for(Color color: Color.values()){
            assertEquals(cloud.getStudentsNum(color),cloudsData.getStudentsNum(cloud.getCloudId(),color));
        }
    }

    @Test
    public void wipeCloud__wocd(){
        cloud.wipeCloud(game.getTeams().get(0).getPlayers().get(0));
        for(Color color: Color.values()){
            assertEquals(0,cloudsData.getStudentsNum(cloud.getCloudId(),color));
        }
    }

    @Test
    public void addStudent__woct(){
        cloud.wipeCloud(game.getTeams().get(0).getPlayers().get(0));
        cloud.addStudent(Color.RED);
        assertEquals(1,cloudsData.getStudentsNum(cloud.getCloudId(),Color.RED));
    }
}