package pkg;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class Block {
    private int index;
    private int previousHash;
    private int hash;
    private List<byte[]> transactions;

    public Block(int index, int previousHash, List<byte[]> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;

        byte[] transactionHash = generateHash();
        Object[] content = {
                transactionHash,
                previousHash,
                index
        };
        this.hash = Arrays.hashCode(content);
    }

    public int getIndex() {
        return index;
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public byte[] generateHash() {
        int transactionsSize = transactions.size();
        byte[] hash;
        if (transactionsSize == 1) {
            hash = generateHashInternal(transactions.get(0));
        } else {
            hash = generateHashInternal(transactions.get(0));
            for (int i = 1; i < transactionsSize; i++) {
                byte[] temp = ";.;".getBytes();
                byte[] temp1 = generateHashInternal(transactions.get(0));
                byte[] result = new byte[hash.length + temp.length + temp1.length];
                System.arraycopy(hash, 0, result, 0, hash.length);
                System.arraycopy(temp, 0, result, hash.length, temp.length);
                System.arraycopy(temp1, 0, result, hash.length+temp.length, temp1.length);
                hash = result;
            }
        }
        return hash;
    }

    private byte[] generateHashInternal(byte[] message) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(message);
            System.out.println("Hash of the message is: " + new String(hash));
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            byte[] byteArray = new byte[] {};
            return (byteArray);
        }
    }

    public int getHash() {
        return hash;
    }

    public List<byte[]> getTransactions() {
        return transactions;
    }
}
