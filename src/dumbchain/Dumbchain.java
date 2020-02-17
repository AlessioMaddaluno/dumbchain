package dumbchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a basic (dumb) blockchain
 */
public class Dumbchain implements Serialized {

    // The max number of tx stored in a block
    public static int BLOCK_THRESHOLD = 5;
    // Block..chain
    public List<Block> chain = new ArrayList<>();
    // Mining algorithm
    private MiningStrategy miningStrategy;

    /**
     * Default constructor. Set PoW with automining
     */
    Dumbchain() {
        this(new ProofOfWork());
    }

    /**
     * Constructor
     *
     * @param miningStrategy mining algorithm
     */
    public Dumbchain(MiningStrategy miningStrategy) {
        this.miningStrategy = miningStrategy;
        // genesis block
        Block genesisBlock = BlockBuilder.newBlock(0).build();
        genesisBlock.addTx(new MinimalTransaction("GENESIS"));
        chain.add(genesisBlock);
    }

    /**
     * Creates a snapshot of the blockchain
     *
     * @return snapshot
     */
    public ChainMemento createSnapshot() {

        //List<Block> state = new ArrayList<>(this.chain);

        List<Block> state = new ArrayList<>();
        for (Block b : chain)
            state.add(new Block(b));

        return new ChainMemento(state);
    }

    /**
     * Set a new state of the blockchain
     *
     * @param snapshot new state
     */
    public void restorePreviousState(ChainMemento snapshot) {
        this.setChain(snapshot.getState());
    }

    /**
     * Creates a new block.
     */
    public void createBlock() {
        int chainSize = chain.size();
        // Set the previous hash
        String previousHash = (chainSize == 0) ? "0" : this.getHashLastBlock();
        // Use the blockbuilder class
        BlockBuilder builder = BlockBuilder.newBlock(chainSize);
        builder.previousHash(previousHash);
        //Add the block created
        this.addBlock(builder.build());
    }

    /**
     * Add a block in the blockchain.
     *
     * @param block block
     */
    public void addBlock(Block block) {
        Block previousBlock = chain.get(chain.size() - 1);
        // Check block integrity
        if (!block.getPreviousHash().equals(previousBlock.getHash()))
            throw new RuntimeException("Attempt to insert an invalid block");
        chain.add(block);
    }

    /**
     * Add a TX in the last block of the chain
     *
     * @param tx TX
     */
    public void addTx(Tx tx) {
        // Check if the last block is full or is mined
        if (getLastBlock().getTxCounter() == BLOCK_THRESHOLD || miningStrategy.validateHash(getLastBlock().getHash())) {
            this.createBlock();
            this.addTx(tx);
        } else {
            this.getLastBlock().addTx(tx);
        }

    }

    /**
     * Check if the blockchain is valid
     *
     * @return True if it is, false otherwise
     */
    public boolean validateBlockchain() {

        Block currentBlock;
        Block previousBlock;

        // Genesis control
        Block genesisBlock = chain.get(0);
        if (!genesisBlock.getHash().equals(genesisBlock.calculateHash()))
            throw new RuntimeException("Invalid Hash for genesis block");
        if (!this.miningStrategy.validateHash(genesisBlock.getHash()))
            throw new RuntimeException("Genesis block is not mined");

        // Check all the blocks
        for (int i = 1; i < chain.size(); i++) {
            currentBlock = chain.get(i);
            previousBlock = chain.get(i - 1);
            // Check if the block hash is correct
            if (!currentBlock.getHash().equals(currentBlock.calculateHash()))
                throw new RuntimeException("Invalid Hash for block with index: " + currentBlock.getId());
            //Check if the previous block hash is correct
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash()))
                throw new RuntimeException("Invalid previous Hash for block with index " + currentBlock.getId());
            //Check if the block is mined
            if (!miningStrategy.validateHash(currentBlock.getHash()))
                throw new RuntimeException("Unmined block with index " + currentBlock.getId());
        }
        return true;
    }

    /**
     * Print a Json serialized rappresentation of the blockchain
     *
     * @return JSON
     */
    @Override
    public String serialize() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(chain);
    }

    /**
     * Getter of the last block
     *
     * @return last block
     */
    public Block getLastBlock() {
        if (this.chain.size() > 0)
            return this.chain.get(this.chain.size() - 1);
        else
            throw new RuntimeException("The blockchain is empty");
    }

    public Block getBlock(int id) {
        if (id < 0 || id > chain.size() - 1)
            throw new RuntimeException("Invalid block Id");
        return chain.get(id);
    }

    /**
     * Getter of the last hash block
     *
     * @return hash
     */
    public String getHashLastBlock() {
        return getLastBlock().getHash();
    }

    /**
     * Getter of the miningStrategy
     *
     * @return hash
     */
    public MiningStrategy getMiningStrategy() {
        return this.miningStrategy;
    }

    /**
     * Setter of the mining strategy
     *
     * @param mS mining strategy
     */
    public void setMiningStrategy(MiningStrategy mS) {
        this.miningStrategy = mS;
    }

    /**
     * Getter of the chain
     *
     * @return chain
     */
    public List<Block> getChain() {
        return this.chain;
    }

    /**
     * Setter of the chain
     *
     * @param chain chain
     */
    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    /**
     * Inner class for manage the chain memento
     */
    public static class ChainMemento {
        private List<Block> snapshot;

        /**
         * Creates a new memento
         *
         * @param state state of the blockchain
         */
        public ChainMemento(List<Block> state) {
            this.snapshot = state;
        }

        /**
         * Getter of the state
         *
         * @return state
         */
        public List<Block> getState() {
            return this.snapshot;
        }
    }


}
