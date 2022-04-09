package it.polimi.deib.ingsw.gruppo44.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    private Cloud cloud;

    @BeforeEach
    public void SetUp(){
        cloud = new Cloud(3);
    }

    @AfterEach
    public void TearDown() { cloud = null; }


    @Test
    public void isEmpty() {
        assertTrue(cloud.isEmpty());
    }

    @Test
    public void setStudents__woct() {
        cloud.setStudents(Color.GREEN, 2);
        cloud.setStudents(Color.RED, 1);
        assertEquals(2,cloud.getStudentsNum(Color.GREEN));
        assertEquals(1,cloud.getStudentsNum(Color.RED));
        assertEquals(0,cloud.getStudentsNum(Color.YELLOW));
        assertEquals(0,cloud.getStudentsNum(Color.BLUE));
        assertEquals(0,cloud.getStudentsNum(Color.PINK));
    }

    @Test
    public void wipeCloud() {
        assertEquals(0,cloud.getStudentsNum(Color.YELLOW));
        assertEquals(0,cloud.getStudentsNum(Color.RED));
        assertEquals(0,cloud.getStudentsNum(Color.GREEN));
        assertEquals(0,cloud.getStudentsNum(Color.BLUE));
        assertEquals(0,cloud.getStudentsNum(Color.PINK));
        assertTrue(cloud.isEmpty());
    }

    @Test
    public void setStudents__nwoud() {
        cloud.setStudents(Color.GREEN, 3);
        assertNotEquals(2,cloud.getStudentsNum(Color.GREEN));
        assertNotEquals(1,cloud.getStudentsNum(Color.GREEN));
        assertEquals(0,cloud.getStudentsNum(Color.RED));
        assertEquals(0,cloud.getStudentsNum(Color.YELLOW));
        assertEquals(0,cloud.getStudentsNum(Color.BLUE));
        assertEquals(0,cloud.getStudentsNum(Color.PINK));
    }


}