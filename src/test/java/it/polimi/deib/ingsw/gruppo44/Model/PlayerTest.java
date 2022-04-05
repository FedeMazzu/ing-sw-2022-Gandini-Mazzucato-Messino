package it.polimi.deib.ingsw.gruppo44.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private School school;
    private Magician magician;

    @BeforeEach
    public void setUp() {
        school = new School(player);
        magician = Magician.MONK;
        player = new Player("Filippo","127000000000",3084,school,magician);
    }

    @AfterEach
    public void tearDown() {
        player = null;
        school = null;
        magician = null;
    }

    @Test
    public void addCoin__(){
        player.addCoin();
        assertEquals(1,player.getMoney());
    }
}