package pkg;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//defines the operations on blockchain
public class Blockchain {
    private static int currentIndex = 0;
    private List<Block> blocks;

    public Blockchain() {
        this.blocks = new LinkedList<>();
        // create the first block
        Block firstBlock = new Block(currentIndex, 0, Arrays.asList("First block".getBytes()));
        this.blocks.add(firstBlock);
    }

    public void addBlock(List<byte[]> transactions) {
        Block block = new Block(++currentIndex, blocks.get(blocks.size() - 1).getHash(), transactions);
        this.blocks.add(block);
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
