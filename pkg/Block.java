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
import java.io.*;

//defines the block of a blockchain
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
            addXMLNode(index, previousHash, transactionHash, hash);
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

    public int getHash() {
        return hash;
    }

    public List<byte[]> getTransactions() {
        return transactions;
    }

    //The files uploaded by the institution are hashed. The hashing process is initiated here.
    public String generateHash() {
        int transactionsSize = transactions.size();
        String hash;
        if (transactionsSize > 0) {
            hash = generateHashInternal(transactions.get(0)).toString();
        } 
        else{hash ="";}
        return hash;
    }

    //computation of hash occurs in this method
    public static byte[] generateHashInternal(byte[] message) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(message);
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            byte[] byteArray = new byte[] {};
            return (byteArray);
        }
    }

    //The blockchain is stored in the form of an XML file. For the first block, this function is run to create and set up the XML.
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
            indx.appendChild(doc.createTextNode(String.valueOf(index)));
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

    //For every other block that is added, this code ensure that the block is add to the XML tree.
    private void addXMLNode(int index, int previousHash, String transactionHash, int hash) {
        try {
            // read the existing XML file into a DOM document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("Server/blockchain.xml"));

            // create the new block element
            Element block2 = doc.createElement("block");

            // add the index element
            Element index2 = doc.createElement("index");
            index2.appendChild(doc.createTextNode(String.valueOf(index)));
            block2.appendChild(index2);

            // add the previousHash element
            Element previousHash2 = doc.createElement("previousHash");
            previousHash2.appendChild(doc.createTextNode(String.valueOf(previousHash)));
            block2.appendChild(previousHash2);

            // add the transaction element
            Element transaction2 = doc.createElement("transaction");
            transaction2.appendChild(doc.createTextNode(transactionHash));
            block2.appendChild(transaction2);

            // add the hash element
            Element hash2 = doc.createElement("hash");
            hash2.appendChild(doc.createTextNode(String.valueOf(hash)));
            block2.appendChild(hash2);

            // append the new block element to the blockchain element
            Element blockchain = doc.getDocumentElement();
            blockchain.appendChild(block2);

            // write the updated document back to the same file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            FileWriter writer = new FileWriter(new File("Server/blockchain.xml"));
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            System.out.println("XML file updated.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();;
        }

    }
}
