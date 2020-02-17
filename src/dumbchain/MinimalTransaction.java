package dumbchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a basic transaction for the blockchain
 */
public class MinimalTransaction implements Tx, Serialized {

    private String hash;
    private String value;


    public MinimalTransaction() {
        this("");
    }

    public MinimalTransaction(String value) {
        this.setValue(value);
    }

    /**
     * Calculate te hash of the transaction
     *
     * @return hash
     */
    public String calculateHash() {
        return Utils.SHA256(this.value);
    }

    /**
     * Serialize the object
     *
     * @return JSON
     */
    @Override
    public String serialize() {
        List<String> serializedData = new ArrayList<>();
        serializedData.add(hash);
        serializedData.add(value);
        return new GsonBuilder().setPrettyPrinting().create().toJson(serializedData);
    }

    /**
     * Getter of the hash
     *
     * @return hash
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * Getter of the value
     *
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the a value for the transaction and update its hash
     *
     * @param newValue value
     */
    public void setValue(String newValue) {
        this.value = newValue;
        this.hash = calculateHash();
    }

}
