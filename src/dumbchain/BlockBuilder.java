package dumbchain;

import java.util.Date;

/**
 * Implements a build interface for the block class.
 */
public class BlockBuilder {

    private int id;                     // block id
    private String previousHash = "0";  // hash of the previous block in the chain
    private int nonce = 0;              // nonce - default set to 0
    private long timeStamp = new Date().getTime();

    /**
     * Set the block id
     *
     * @param id block id
     */
    BlockBuilder(int id) {
        this.id = id;
    }

    /**
     * Getter for blockbuilder.
     *
     * @param id block id
     * @return Blockbuilder instance for the block (id)
     */
    public static BlockBuilder newBlock(int id) {
        return new BlockBuilder(id);
    }

    /**
     * Setter for previousHash.
     *
     * @param previousHash previous hash
     */
    public void previousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    /**
     * Setter for nonce.
     *
     * @param nonce nonce
     */
    public void nonce(int nonce) {
        this.nonce = nonce;
    }

    /**
     * Setter for timeStamp.
     *
     * @param timeStamp time stamp
     */
    public void timeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Build a new block based of the blockbuilder attributes.
     *
     * @return block created
     */
    public Block build() {
        return new Block(this.id, this.previousHash, this.nonce, this.timeStamp);
    }

}
