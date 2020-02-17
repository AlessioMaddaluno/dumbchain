package dumbchain;

import java.util.ArrayList;
import java.util.List;

/**
 * Caretaker implementation for the blockchain state.
 * Offer a semplified way to restore previous states of the blockchain.
 */

public class Caretaker {

    //Set of previous states of the blockchain
    List<Dumbchain.ChainMemento> savedStates = new ArrayList<>();
    private Dumbchain blockchain;

    /**
     * Creates a new Cartaker
     *
     * @param blockchain blockchain
     */
    public Caretaker(Dumbchain blockchain) {
        this.blockchain = blockchain;
    }

    /**
     * Save the state of the blockchain
     */
    public void saveState() {
        savedStates.add(blockchain.createSnapshot());
    }

    /**
     * Restore the last state of the blockchain
     */
    public void restore() {

        if (this.savedStates.size() == 0)
            throw new RuntimeException("No previous states avaiable");

        // Pop the last state
        int lastState = this.savedStates.size() - 1;
        this.blockchain.restorePreviousState(savedStates.get(lastState));
        savedStates.remove(lastState);
    }


}
