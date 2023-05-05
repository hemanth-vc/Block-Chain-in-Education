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

    // The files uploaded by the institution are hashed. The hashing process is
    // initiated here.
    public static String generateHash(List<byte[]> transactions) {
        int transactionsSize = transactions.size();
        String hash;
        if (transactionsSize > 0) {
            hash = generateHashInternal(transactions.get(0)).toString();
        } else {
            hash = "";
        }
        return hash;
    }

    // computation of hash occurs in this method
    private static byte[] generateHashInternal(byte[] message) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(message);
            // System.out.println("Hash of the message is: " + new String(hash));
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            byte[] byteArray = new byte[] {};
            return (byteArray);
        }
    }

    // All user interactions are handled by this function
    public void userInput(List<byte[]> transcript1Bytes) {
        try {
            String hash = generateHash(transcript1Bytes);
            System.out.println(hash);
            verifyHash(hash);
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
                    if (word.equals(hash)){
                        System.out.println("The certificate produced is authentic.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
