package sample.encryption;

import capstone.fileio.FileIO;
import sample.UIMain.EncodeDecode;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;


/**
 * This class performs the file encryption. First a password is created by the
 * user.  This password is combined with an 8-byte SecureRandom which is then used to generate the key
 * used to initialize the cipher.  An initialization vector, which introduces randomness when ciphering similar plain text, is created.
 * The SecureRandom, IV, and ciphertext are then output to a file, adding the ".aes" extension.
 */
public class Encryption {

    static FileIO fio;
    private static final String ALGORITHM = "AES";
    private static final String CIPHER = "AES/CBC/PKCS5Padding";
    private static final String KEYFACTORY = "PBKDF2WithHmacSHA1";
//    private static final String KEYFACTORY = "PBKDF2WithHmacMD5"; // did not work

    /**
     * The Encryption.encrypt method performs the encryption operation.  This method is passed 2 arguments.
     * The first is the File to be encrypted, and the second argument is the users password.
     */
    public static String encrypt(File file, String password) throws Exception {
        FileOutputStream outFile;
        // output file stream
        try (
                // file to be encrypted as input stream
                FileInputStream inFile = new FileInputStream(file)) {
            // output file stream
            outFile = new FileOutputStream(file + ".aes");

            //create salt
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);

            // Write the salt to output file stream
            outFile.write(salt);

            //Generate Secret Key
            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance(KEYFACTORY);
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);

            // Initialize the cipher
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

            //output the iv to the file output stream
            outFile.write(iv);

            //encrypt the input file
            byte[] input = new byte[64];
            int bytesRead;
            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) {
                    outFile.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                outFile.write(output);
            }
        }

        outFile.flush();
        outFile.close();

        return file + ".aes";
    }
}
