package dumbchain;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the block used to store data into the blockchain.
 */

public class Block {

    private int id;              // Id of the block
    private String hash;         // Hash of the block
    private String previousHash;  // The hash of the previous block on the chain
    private List<Tx> transactions = new ArrayList<>(); // Set of transaction stored in the block
    private long timeStamp;     // The timeStamp of the block creation
    private int nonce;          // A data used to alter the state of the object for mining purpose
    private int txCounter;      // The number of TX stored
    private String merkleRoot;  // The root of the Merkle Tree

    Block(int id, String previousHash, int nonce, long timeStamp) {
        this.id = id;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.merkleRoot = "0";
        this.txCounter = 0;
        this.hash = this.calculateHash();
    }

    Block(Block b) {
        this.id = b.getId();
        this.previousHash = b.getPreviousHash();
        this.timeStamp = b.getTimeStamp();
        this.merkleRoot = b.getMerkleRoot();
        this.txCounter = b.getTxCounter();
        this.hash = b.getHash();
        this.nonce = b.getNonce();
        this.transactions = new ArrayList<>(b.getTxs());
    }

    /**
     * Add a TX in the block.
     *
     * @param tx transaction.
     */
    public void addTx(Tx tx) {
        if (tx == null)
            throw new RuntimeException("Invalid TX (null)");
        //add the transaction
        transactions.add(tx);
        //update the hash-dependent values of the block
        this.updateHash();
        this.updateMerkleTreeRoot();
        //update the counter
        this.txCounter++;
    }

    /**
     * Validate the transaction stored in the block.
     *
     * @return True if is valid, false otherwise.
     */
    public boolean validateTransactions() {
        List<String> tree = merkleTree();
        String root = tree.get(tree.size() - 1);
        return root.equals(this.getMerkleRoot());
    }

    /**
     * Calculate the hash of the block.
     *
     * @return the hash of the block
     */
    public String calculateHash() {
        // The data used for computer the hash are basically the instance data (attributes) of the object itself
        return Utils.SHA256(previousHash + timeStamp + nonce + transactions + txCounter + merkleRoot);
    }

    /**
     * Creates a merkle tree based on the txs hashes.
     *
     * @return merkle tree
     */
    public List<String> merkleTree() {
        return Utils.createMerkleTree(this.transactions);
    }

    /**
     * Update the merkle tree root.
     */
    public void updateMerkleTreeRoot() {
        List<String> treeList = merkleTree();
        this.merkleRoot = treeList.get(treeList.size() - 1);
    }

    /**
     * Update the hash of the block
     */
    public void updateHash() {
        this.hash = this.calculateHash();
    }

    /**
     * Hash getter
     *
     * @return hash
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * Previous hash getter
     *
     * @return previous hash
     */
    public String getPreviousHash() {
        return this.previousHash;
    }

    /**
     * Previous Hash setter
     *
     * @param hash new previous hash value
     */
    public void setPreviousHash(String hash) {
        this.previousHash = hash;
    }

    /**
     * TXs getter
     *
     * @return transactions
     */

    public List<Tx> getTxs() {
        return this.transactions;
    }

    /**
     * Merkle root getter
     *
     * @return merkle root
     */
    public String getMerkleRoot() {
        return this.merkleRoot;
    }

    /**
     * Merkle root setter
     *
     * @param root new merkle root value
     */
    public void setMerkleRoot(String root) {
        this.merkleRoot = root;
    }

    /**
     * Time stamp getter
     *
     * @return time stamp
     */
    public long getTimeStamp() {
        return this.timeStamp;
    }

    /**
     * timeStamp setter
     *
     * @param timeStamp new timeStamp value
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Tx counter getter
     *
     * @return tx counter
     */
    public int getTxCounter() {
        return this.txCounter;
    }

    /**
     * Id getter
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Id setter
     *
     * @param id new id value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Nonce getter
     *
     * @return hash
     */
    public int getNonce() {
        return this.nonce;
    }

    /**
     * Nonce setter
     *
     * @param newNonce new nonce value
     */
    public void setNonce(int newNonce) {
        this.nonce = newNonce;
    }

    /**
     * Transaction  setter
     *
     * @param txs new tx list
     */
    public void setTransactions(List<Tx> txs) {
        this.transactions = txs;
    }

}
