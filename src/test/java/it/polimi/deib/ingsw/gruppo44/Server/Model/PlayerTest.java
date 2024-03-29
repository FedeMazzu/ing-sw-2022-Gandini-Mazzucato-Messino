package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test the Player class
 * @author filippogandini
 */

class PlayerTest {
    private Game game;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new Game(GameMode.TwoPlayersStandard);
        List<Team> teams = game.getTeams();
        //works because of the mood
        player = teams.get(0).getPlayers().get(0);
    }

    @AfterEach
    public void tearDown() {
        player = null;
    }

    @Test
    public void addCoin__woct(){
        player.addCoin();
        player.addCoin();
        assertEquals(2,player.getMoney());
        assertNotEquals(3,player.getMoney());
        assertNotEquals(1,player.getMoney());
    }
    @Test
    public void addCoin__nwoud(){
        player.addCoin();
        assertNotEquals(2,player.getMoney());
        assertNotEquals(0,player.getMoney());

    }

    @Test
    public void spendMoney__nwoud(){
        player.addCoin();
        player.addCoin();
        player.addCoin();
        assertEquals(3,player.getMoney());
        player.spendMoney(2);
        assertEquals(1,player.getMoney());

        //the method throws an exception
        player.spendMoney(2);
        assertEquals(1,player.getMoney());
    }


    @Test
    public void showAvailableCards__woct(){
        List<Integer> availableCards = new ArrayList<>();

        // using some cards
        player.playCard(3);
        player.playCard(7);
        availableCards = player.showAvailableCards();

        //checking if the played cards aren't available and if the others are
        for(int i=1; i<=10; i++){
            if(i==3 || i==7){
                assertFalse(availableCards.contains(player.getCard(i).getValue()));
            }else{
                assertTrue(availableCards.contains(player.getCard(i).getValue()));
            }
        }

    }
}