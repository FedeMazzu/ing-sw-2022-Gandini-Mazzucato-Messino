package it.polimi.deib.ingsw.gruppo44.Common;

import java.io.IOException;

/**
 * Interface to define the stages. It's an implementation of the State pattern
 */
public interface Stage {
    public void handle() throws IOException, ClassNotFoundException, InterruptedException;
}
