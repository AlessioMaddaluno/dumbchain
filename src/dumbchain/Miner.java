package dumbchain;

import java.util.ArrayList;
import java.util.List;

/**
 * Emulates a miner for the blockchain
 */
public class Miner {

    public Dumbchain blockchain;
    //Transaction pool
    private List<Tx> txPool = new ArrayList<>();

    /**
     * Creates a new miner
     *
     * @param blockchain blockchain
     */
    public Miner(Dumbchain blockchain) {
        this.blockchain = blockchain;
    }

    /**
     * Insert a new Tx in the blockchain if it's possible
     *
     * @param tx tx
     */
    public void mine(Tx tx) {
        txPool.add(tx);
        //If there are enough transactions , mine the block
        if (txPool.size() == Dumbchain.BLOCK_THRESHOLD) {
            blockchain.createBlock();
            for (Tx t : txPool)
                blockchain.getLastBlock().addTx(t);
            blockchain.getMiningStrategy().mine(blockchain.getLastBlock());
            txPool.clear();
        }
    }

    /**
     * Mine the block based of the algorithm defined in the blockchain
     *
     * @param block block to mine
     */
    public void mineBlock(Block block) {

        if (blockchain.getMiningStrategy().validateHash(block.getHash()))
            throw new RuntimeException("Block already mined");
        blockchain.getMiningStrategy().mine(block);

    }

    /**
     * Tx pool getter
     *
     * @return txPool
     */
    public int getPoolCounter() {
        return this.txPool.size();
    }
}
