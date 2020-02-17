package dumbchain;

/**
 * Define a Transaction interface.
 */
public interface Tx {
    /**
     * Getter of the transaction hash
     *
     * @return hash
     */
    String getHash();

    /**
     * Calculate a hash of the TX. It's recommended to use its own attributes as well.
     *
     * @return hash calculated
     */
    String calculateHash();
}
