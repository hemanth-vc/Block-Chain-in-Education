package pkg;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class Block {
    private int index;
    private int previousHash;
    private int hash;
    private List<byte[]> transactions;

    public Block(int index, int previousHash, List<byte[]> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        String transactionHash = generateHash();

        Object[] content = {
                transactionHash,
                previousHash,
                index
        };
        this.hash = Arrays.hashCode(content);
        if (index != 0) {
            // addXMLNode(index, 0, transactionHash, hash);
        } else if (index == 0) {
            generateXML(index, 0, transactionHash, hash);
        }
    }

    public int getIndex() {
        return index;
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public String generateHash() {
        int transactionsSize = transactions.size();
        String hash;
        if (transactionsSize == 1) {
            hash = generateHashInternal(transactions.get(0)).toString();
        } else {
            hash = generateHashInternal(transactions.get(0)).toString();
            String temp = ";.;";
            for (int i = 1; i < transactionsSize; i++) {
                hash = hash + temp;
                String temp1 = generateHashInternal(transactions.get(i)).toString();
                hash = hash + temp1;
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

    private void generateXML(int index, int previousHash, String transactionHash, int hash) {
        try {
            // Create a new XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // create the root element
            Element blockchain = doc.createElement("blockchain");
            doc.appendChild(blockchain);

            // create a new block element
            Element block = doc.createElement("block");
            blockchain.appendChild(block);

            // add the index element
            Element indx = doc.createElement("index");
            indx.appendChild(doc.createTextNode(indx.toString()));
            block.appendChild(indx);

            // add the previousHash element
            Element prevHash = doc.createElement("previousHash");
            prevHash.appendChild(doc.createTextNode(String.valueOf(previousHash)));
            block.appendChild(prevHash);

            // add the transaction element
            Element transaction = doc.createElement("transaction");
            transaction.appendChild(doc.createTextNode(transactionHash));
            block.appendChild(transaction);

            // add the hash element
            Element hasH = doc.createElement("hash");
            hasH.appendChild(doc.createTextNode(String.valueOf(hash)));
            block.appendChild(hasH);

            // write the document to a file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            FileWriter writer = new FileWriter(new File("Server/blockchain.xml"));
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            System.out.println("XML file created.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

/*     //to be completed by Hemanth
        private void addXMLNode(int index, int previousHash, String transactionHash, int hash) {
        // read in the existing XML file
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("blockchain.xml"));

        // get the root element
        Element root = doc.getDocumentElement();

        // create a new block element
        Element block = doc.createElement("block");

        // add the index element
        Element indx = doc.createElement("index");
        indx.appendChild(doc.createTextNode("2"));
        block.appendChild(indx);

        // add the previousHash element
        Element prevHash = doc.createElement("previousHash");
        prevHash.appendChild(doc.createTextNode("XYZ789"));
        block.appendChild(prevHash);

        // add the transaction element
        Element transaction = doc.createElement("transaction");
        transaction.appendChild(doc.createTextNode("50 BTC"));
        block.appendChild(transaction);

        // add the hash element
        Element hasH = doc.createElement("hash");
        hasH.appendChild(doc.createTextNode("GHI789"));
        block.appendChild(hasH);

        // add the new block to the root element
        root.appendChild(block);

        // write the updated document to a file
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(new File("blockchain.xml"));
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        System.out.println("New block added to XML file.");
    } */
}
