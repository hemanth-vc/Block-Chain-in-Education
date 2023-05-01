

import java.io.*;
import java.security.*;
//verifier should take the file from student,compute hash
//take input from xml file,decrypt the encrypted hash using public key
//verify if both are same
public class Verifier {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    	FileWriter fw = new FileWriter("try.txt");
        String filename = "try.txt"; // replace with your file name
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data);

        System.out.println("File hash (SHA-256): " + bytesToHex(hash));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

