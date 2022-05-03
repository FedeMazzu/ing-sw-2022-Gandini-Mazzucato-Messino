package it.polimi.deib.ingsw.gruppo44.Server.Model.Characters;

import it.polimi.deib.ingsw.gruppo44.Server.Model.BoardObserver;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.NotOwnedObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to represent the Character 1
 * effect: take one student from the card and move it on an island of
 * your choice. Then draw a student from the bag and put it on this card.
 * @author zenomess
 */
public class Character1 extends Character {
    private List<Color> students;
    private final int numStudents = 4;

    public  Character1(Game game, BoardObserver boardObserver){
        this.boardObserver = boardObserver;
        this.game = game;
        this.id =1; //attribute of the superclass
        this.price = 1;
        students = new ArrayList<>();
    }

    @Override
    public void effect() {
        // note that it's necessary to move the initialization here
        // because it needs to wait the instantiation of the board before getting the unionFind
        Random rand = new Random();
        NotOwnedObjects notOwnedObjects = game.getBoard().getNotOwnedObjects();
        int randIndex;
        for(int i=0; i<numStudents; i++){
            randIndex = rand.nextInt(notOwnedObjects.getStudentsSize());
            Color tempColor = notOwnedObjects.drawStudent(randIndex);
            students.add(tempColor);
        }


        //ask input to the user to choose the island and the color
        int islandId = 5; //just for example
        int chosenStudentRandIndex = rand.nextInt(students.size());
        Color tempColor = students.remove(chosenStudentRandIndex);
        game.getBoard().getUnionFind().getIsland(islandId).addStudent(tempColor);

        //drawing the new student and putting it on the card
        int newStudentIndex = rand.nextInt(notOwnedObjects.getStudentsSize());
        Color newStudent = notOwnedObjects.drawStudent(newStudentIndex);
        students.add(newStudent);
        // raises the price only after the first usage
        raisePrice();
    }


}

