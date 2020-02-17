package dumbchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for useful algorithms for the blockchain
 */

public class Utils {

    /**
     * Calculate the SHA256 of a string.
     * Thanks to thatman from Stack Overflow
     * Credits: https://stackoverflow.com/a/44436308
     *
     * @param data data
     * @return hash
     */
    public static String SHA256(String data) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte[] byteData = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Creates a Merkle Tree
     * An HUGE thanks to BitcoinJ project
     * Credits: https://github.com/bitcoinj/bitcoinj
     *
     * @param elements hashes
     * @return Merkle Tree
     */
    //
    public static List<String> createMerkleTree(List<Tx> elements) {
        ArrayList<String> tree = new ArrayList<>();
        for (Tx t : elements) {
            t.getHash();
            tree.add(t.getHash());
        }
        int levelOffset = 0;
        for (int levelSize = elements.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            for (int left = 0; left < levelSize; left += 2) {
                int right = Math.min(left + 1, levelSize - 1);
                String tleft = tree.get(levelOffset + left);
                String tright = tree.get(levelOffset + right);
                tree.add(Utils.SHA256(tleft + tright));
            }

            levelOffset += levelSize;
        }
        return tree;
    }


}
