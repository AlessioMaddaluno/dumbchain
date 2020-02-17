package GUI;

import dumbchain.*;

import java.util.List;

/**
 * Provides a simplify API for Dumbchain communication
 */

public class GUIModel {

    private Dumbchain blockchain;
    private Caretaker careTaker;
    private Miner miner;

    public GUIModel() {
        this.resetBlockchain();
    }

    /**
     * Mine a block
     *
     * @param blockId the id block
     */
    public void mineBlock(int blockId) {
        careTaker.saveState();
        miner.mineBlock(blockchain.getBlock(blockId));
    }

    /**
     * Validate the blockchain
     *
     * @return "OK" if is valid, an error message otherwise
     */
    public String validateBlockchain() {
        try {
            this.blockchain.validateBlockchain();
            return "OK";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    /**
     * Restore a previous state of the blockchain
     *
     * @return "OK" if is restored, an error message otherwise
     */
    public String restoreState() {
        try {
            careTaker.restore();
            return "OK";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    /**
     * Resets the blockchain
     */
    public void resetBlockchain() {
        this.blockchain = new Dumbchain(new ProofOfWork(3));
        this.careTaker = new Caretaker(this.blockchain);
        miner = new Miner(this.blockchain);
        miner.mineBlock(blockchain.getLastBlock());
    }

    /**
     * Add a transaction directly on the blockchain
     *
     * @param txValue tx value
     */
    public void addDirectTX(String txValue) {
        careTaker.saveState();
        blockchain.addTx(new MinimalTransaction(txValue));
    }

    /**
     * Add a transactio through the miner
     *
     * @param txValue tx value
     * @return True if is the last tx of miner's pool, false otherwise
     */
    public boolean addMinerTX(String txValue) {

        //Check if is the last tx of the miner's pool
        boolean lastTX = false;
        if (miner.getPoolCounter() == Dumbchain.BLOCK_THRESHOLD - 1) {
            lastTX = true;
        }
        careTaker.saveState();
        miner.mine(new MinimalTransaction(txValue));
        return lastTX;
    }

    /**
     * Get the miner tx pool state
     *
     * @return tx pool counter
     */
    public int getMinerPoolState() {
        return miner.getPoolCounter();
    }

    /**
     * Getter of blockchain threshold
     *
     * @return threshold
     */
    public int getBlockThreshold() {
        return Dumbchain.BLOCK_THRESHOLD;
    }

    /**
     * Getter chain size
     *
     * @return chain size
     */
    public int getChainSize() {
        return blockchain.getChain().size();
    }


    /**
     * Parse blockchain data into a String[][]
     *
     * @return parsed data
     */
    public String[][] getChainData() {

        List<Block> chain = blockchain.getChain();
        String[][] data = new String[chain.size()][];

        int j = 0;

        for (Block b : chain) {
            data[j] = new String[5];
            data[j][0] = Integer.toString(b.getId());
            data[j][1] = b.getHash();
            data[j][2] = b.getPreviousHash();
            data[j][3] = Integer.toString(b.getNonce());
            data[j][4] = Integer.toString(b.getTxCounter());
            j++;
        }
        return data;
    }

    /**
     * Parse transaction data into a String[][]
     *
     * @return parsed data
     */
    public String[][] getTXData() {

        List<Block> chain = blockchain.getChain();
        int txCounter = 0;
        for (Block b : chain)
            txCounter += b.getTxCounter();

        String[][] data = new String[txCounter][];

        int i = 0;
        for (Block b : chain) {
            for (Tx tx : b.getTxs()) {
                data[i] = new String[3];
                data[i][0] = Integer.toString(b.getId());
                data[i][1] = tx.getHash();
                data[i][2] = ((MinimalTransaction) tx).getValue();
                i++;
            }
        }

        return data;
    }

}
