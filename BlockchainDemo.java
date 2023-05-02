import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pkg.*;

public class BlockchainDemo {
    //paths variable stores all the student files that are to be uploaded to the current block
    private static ArrayList<String> paths = new ArrayList<String>();

    public static void main(String[] args) {

        try {
            /* // hard-coded for testing
            FileInputStream transcript1 = new FileInputStream("General/smiley.jpg");
            byte[] transcript1Bytes = transcript1.readAllBytes(); */

            //Take the user input
            userInput();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //All user interactions are handled by this function
    private static void userInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("How many certificates do you want to upload?");
            int n = scanner.nextInt();
            scanner.nextLine();
            String path;
            List<byte[]> listOfByteArrays = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                System.out.println("Enter the path of file no. " + (i+1));
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

                for (int i = 0; i < size; i++) {
                    System.out.println(paths.get(i));
                }
                System.out.println(
                        "If you would like to change/re-enter the file paths, click N. \nTo continue adding the above files to the block chain, press Y.\n To quit, press Q.");
                String input = scanner.nextLine();
                char c = input.toLowerCase().charAt(0);
                switch (c) {
                    case 'y':
                        for (String p : paths) {
                            FileInputStream transcript = new FileInputStream(p);
                            listOfByteArrays.add(transcript.readAllBytes());
                        }
                        Blockchain blockchain = new Blockchain();
                        blockchain.addBlock(listOfByteArrays);
                        System.out.println("Blocks hash:");
                        for (Block block : blockchain.getBlocks()) {
                            System.out.println("block #" + block.getIndex() + ": " + block.getHash());
                        }
                        break;
                    case 'n':
                        userInput();
                        break;
                    case 'q':
                        return;
                    default:
                        System.out.println("Exiting the program.");
                        break;
                }
            } else {
                System.out.println("No files will be added.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}