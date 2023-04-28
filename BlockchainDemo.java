import java.io.FileInputStream;
import java.util.Arrays;

import pkg.*;

public class BlockchainDemo {

public static void main(String[] args) {

    try {
        Blockchain blockchain = new Blockchain();
        FileInputStream transcript1 = new FileInputStream("General/smiley.jpg");
        byte[] transcript1Bytes = transcript1.readAllBytes();
        blockchain.addBlock(Arrays.asList(transcript1Bytes, transcript1Bytes));
        blockchain.addBlock(Arrays.asList("Test Transaction 1".getBytes(), "Test Transaction 1.1".getBytes()));
        blockchain.addBlock(Arrays.asList("Test Transaction 2".getBytes()));
        blockchain.addBlock(Arrays.asList("Test Transaction 3".getBytes()));
    
        System.out.println("Blocks hash:");
        for(Block block : blockchain.getBlocks()) {
            System.out.println("block #" + block.getIndex()  + ": " + block.getHash());
        }
    
    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
    }
}
}