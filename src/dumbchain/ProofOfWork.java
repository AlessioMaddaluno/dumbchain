package dumbchain;


/**
 * Implements a Proof of Work variable algorithm for mining a block
 */
public class ProofOfWork implements MiningStrategy {

    // complexity of the mining - how many zeros the hash must have
    private int complexity;
    private String hashPrefix;

    /**
     * Default constructor. Set the complexity to 0 (Automining)
     */
    public ProofOfWork() {
        this(0);
    }

    /**
     * Creates a new algorithm setting a defined complexity
     *
     * @param complexity complexity
     */
    public ProofOfWork(int complexity) {
        this.complexity = complexity;
        generatePrefix();
    }

    /**
     * Generate the correct hash prefix
     */
    private void generatePrefix() {
        this.hashPrefix = new String(new char[complexity]).replace('\0', '0');
    }

    /**
     * The algorithm itself
     *
     * @param block bock to mine
     */
    @Override
    public void mine(Block block) {

        // The program iterates until doesnt get a correct hash for the block
        // The only difference between the iterations is the nonce value
        while (!block.getHash().substring(0, complexity).equals(this.hashPrefix)) {
            int nonce = block.getNonce();
            block.setNonce(++nonce);
            block.updateHash();
        }

    }

    /**
     * Check if an hash is valid for the algorithm
     *
     * @param hash hash
     * @return true if it is, false otherwise
     */
    @Override
    public boolean validateHash(String hash) {
        return hash.substring(0, this.getComplexity()).equals(this.hashPrefix);
    }

    /**
     * Getter of the complexity
     *
     * @return complexity
     */
    public int getComplexity() {
        return this.complexity;
    }

}
