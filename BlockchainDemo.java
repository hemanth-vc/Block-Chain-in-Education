import pkg.*;

public class BlockchainDemo {

public static void main(String[] args) {
    Blockchain blockchain = new Blockchain();
    blockchain.addBlock(new String[]{"Test Transaction 1", "Test Transaction 1.1"});
    blockchain.addBlock(new String[]{"Test Transaction 2"});
    blockchain.addBlock(new String[]{"Test Transaction 3"});

    System.out.println("Blocks hash:");
    for(Block block : blockchain.getBlocks()) {
        System.out.println("block #" + block.getIndex()  + ": " + block.getHash());
    }
}
}