import java.util.Arrays;

import pkg.*;

public class BlockchainDemo {

public static void main(String[] args) {
    Blockchain blockchain = new Blockchain();
    blockchain.addBlock(Arrays.asList("Test Transaction 1".getBytes(), "Test Transaction 1.1".getBytes()));
    blockchain.addBlock(Arrays.asList("Test Transaction 2".getBytes()));
    blockchain.addBlock(Arrays.asList("Test Transaction 3".getBytes()));

    System.out.println("Blocks hash:");
    for(Block block : blockchain.getBlocks()) {
        System.out.println("block #" + block.getIndex()  + ": " + block.getHash());
    }
}
}