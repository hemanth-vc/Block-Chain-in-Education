import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import pkg.*;

public class BlockchainDemo {

    private static ArrayList<String> paths = new ArrayList<String>();

    public static void main(String[] args) {

        try {
            Blockchain blockchain = new Blockchain();
            // hard-coded for testing
            FileInputStream transcript1 = new FileInputStream("General/smiley.jpg");
            byte[] transcript1Bytes = transcript1.readAllBytes();
            blockchain.addBlock(Arrays.asList(transcript1Bytes, transcript1Bytes));
            userInput();
            blockchain.addBlock(Arrays.asList("Test Transaction 1".getBytes(), "Test Transaction 1.1".getBytes()));
            blockchain.addBlock(Arrays.asList("Test Transaction 2".getBytes()));
            blockchain.addBlock(Arrays.asList("Test Transaction 3".getBytes()));

            System.out.println("Blocks hash:");
            for (Block block : blockchain.getBlocks()) {
                System.out.println("block #" + block.getIndex() + ": " + block.getHash());
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //handle all the exceptions gracefully. To be done by Hemanth
    private static void userInput() {
        // user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many transactions do you want to make?");
        int n = scanner.nextInt();
        String path;
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the path of file no. " + i);
            path = scanner.nextLine();
            paths.add(path);
            try {
                FileInputStream transcript = new FileInputStream(path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("This file won't be added to the transaction.");
            }
        }
        int size = paths.size();
        if (size > 0) {
            System.out.println("The following files will be added: ");
        } else {
            System.out.println("No files will be added.");
        }
        for (int i = 0; i < size; i++) {
            System.out.println(paths.get(i));
        }
        System.out.println(
                "If you would like to change/re-enter the file paths, click N. \nTo continue adding the above files to the block chain, press Y.\n To quit, press Q.");
        String input = scanner.nextLine();
        char c = input.toLowerCase().charAt(0);
        switch (c) {
            case 'y':
                //to take the path arraylist and add all the files in a single block. The outcome of the function should look like: blockchain.addBlock(Arrays.asList(transcript1Bytes, transcript1Bytes))
            case 'n':
                userInput();
            case 'q':
                return;
            default:
                System.out.println("Exiting the program."); break;
        }
    }

}