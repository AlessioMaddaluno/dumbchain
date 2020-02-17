package dumbchain;

/**
 * Define an interface for a mining algorithm for the blockchain blocks.
 */
public interface MiningStrategy {
    /**
     * Implements the algorithm itself
     *
     * @param block block to mine
     */
    void mine(Block block);

    /**
     * Validate an hash based of the algorithm implemented
     *
     * @param hash hash to validate
     * @return true if hash is valid, false otherwise
     */
    boolean validateHash(String hash);
}
