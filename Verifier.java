import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import java.io.*;
import java.security.*;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//verifier should take the file from student,compute hash
//take input from xml file,decrypt the encrypted hash using public key
//verify if both are same
public class Verifier {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        userInput();
    }

    // The files uploaded by the institution are hashed. The hashing process is
    // initiated here.
    public static String generateHash(List<byte[]> transactions) {
        int transactionsSize = transactions.size();
        String hash = "";
        if (transactionsSize == 1) {
            hash = generateHashInternal(transactions.get(0)).toString();
        }
        return hash;
    }

    // computation of hash occurs in this method
    private static byte[] generateHashInternal(byte[] message) {
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

    // All user interactions are handled by this function
    private static void userInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Give the path of the certificate you would like to verify.");
            String path;
            path = scanner.nextLine();
            try {
                FileInputStream transcript = new FileInputStream(path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(
                    "If you would like to change/re-enter the file path, click N. \nTo continue, press Y.\n To quit, press Q.");
            String input = scanner.nextLine();
            char c = input.toLowerCase().charAt(0);
            switch (c) {
                case 'y':
                    FileInputStream transcript1 = new FileInputStream(path);
                    List<byte[]> transcript1Bytes = new ArrayList<byte[]>();
                    transcript1Bytes.add(transcript1.readAllBytes());
                    String hash = generateHash(transcript1Bytes);
                    verifyHash(hash);
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
    }

    public static void verifyHash(String hash) {
        try {
            File xmlFile = new File("Server/blockchain.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList blockList = doc.getElementsByTagName("block");

            for (int i = 0; i < blockList.getLength(); i++) {
                Element block = (Element) blockList.item(i);
                Element transaction = (Element) block.getElementsByTagName("transaction").item(0);

                String transactionValue = transaction.getTextContent().trim();

                StringTokenizer tokenizer = new StringTokenizer(transactionValue, ";.;");

                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken();
                    if (word.equals(hash)) {
                        System.out.println("The certificate produced is authentic.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
