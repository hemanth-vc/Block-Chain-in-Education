import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pkg.*;

public class BlockchainDemo {
    // paths variable stores all the student files that are to be uploaded to the
    // current block
    private static ArrayList<String> paths = new ArrayList<String>();

    public static void main(String[] args) {

        try {
            /*
             * // hard-coded for testing
             * FileInputStream transcript1 = new FileInputStream("General/smiley.jpg");
             * byte[] transcript1Bytes = transcript1.readAllBytes();
             */

            // Take the user input
            userInput();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // All user interactions are handled by this function
    private static void userInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            FileInputStream transcript;
            String path;
            System.out.println("Enter the path of certificate no. you would like to upload or verify.");
            path = scanner.nextLine();
            transcript = new FileInputStream(path);
            System.out.println(
                    "What operation would you like to perform? Choose by entering 1 or 2. \n1. Upload Certificate \n2. Verify Certificate authenticity.");
            int a = scanner.nextInt();
            scanner.nextLine();
            switch (a) {
                case 1:
                    try {
                        List<byte[]> listOfByteArrays = new ArrayList<>();

                        paths.add(path);
                        try {
                            FileInputStream transcript1 = new FileInputStream(path);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("This file won't be added to the transaction.");
                        }

                        System.out.println("The following file will be added: ");

                        System.out.println(paths.get(0));

                        System.out.println(
                                "If you would like to change/re-enter the file path, click N. \nTo continue adding the above files to the block chain, press Y.\n To quit, press Q.");
                        String input = scanner.nextLine();
                        char c = input.toLowerCase().charAt(0);
                        switch (c) {
                            case 'y':

                                listOfByteArrays.add(transcript.readAllBytes());

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

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    try {
                        List<byte[]> transcript1Bytes = new ArrayList<byte[]>();
                        transcript1Bytes.add(transcript.readAllBytes());
                        
                        String hash = Block.generateHashInternal(transcript1Bytes.get(0)).toString();
                        Verifier.verifyHash(hash);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}