package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Character;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

import java.util.List;
import java.util.Random;

/**
 * Class to represent the first Character
 * effect: take one student from the card and move it on an island on an island of
 * your choice. Then draw a student from the bag and put it on this card.
 * @author zenomess
 */
public class Character1 extends Character {
    private int price;
    private List<Color> students;

    public void Character1(Game game){
        price = 1;
        Random rand = new Random();
        int randIndex;
        for(int i=0; i<4; i++){
            randIndex = rand.nextInt(game.getBoard().getNotOwnedObjects().getStudents().size());
            Color tempColor = game.getBoard().getNotOwnedObjects().getStudents().get(randIndex);
            students.add(tempColor);
            game.getBoard().getNotOwnedObjects().getStudents().remove(randIndex);
        }
    }

    @Override
    public void effect(Game game) {
        Random rand = new Random();
        int randIndex1;
        int randIndex2;
        int randIndex3;
        randIndex1 = rand.nextInt(12);
        randIndex2 = rand.nextInt(students.size());
        Color tempColor = students.get(randIndex2);
        game.getBoard().getUnionFind().getIsland(randIndex1).addStudent(tempColor);
        students.remove(randIndex2);
        randIndex3 = rand.nextInt(game.getBoard().getNotOwnedObjects().getStudents().size());
        Color tempColor1 = game.getBoard().getNotOwnedObjects().getStudents().get(randIndex3);
        students.add(tempColor1);
        price++;
    }

}

