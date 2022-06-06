package it.polimi.deib.ingsw.gruppo44.Server;

/**
 * interface to observe many objects of the same type
 */
public interface MultipleObserver {

    /**
     * updates the data related to the object which corresponds to the passed identifier
     * @param identifier
     */
    public void update(int identifier);
}
