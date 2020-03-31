package sample.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.KeySpec;

public class Decryption {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER = "AES/CBC/PKCS5Padding";
    private static final String KEYFACTORY = "PBKDF2WithHmacSHA1";

    public static String decrypt(File file, String password) throws Exception {

        String fName;
        FileInputStream fis=null;
        FileOutputStream fos=null;

        byte[] salt = new byte[8];
        byte[] iv = new byte[16];

        try {
            fis = new FileInputStream(file);
            int read=fis.read(salt);
            if(read!= salt.length){
                throw new IOException();
            }
        } catch(IOException e) {
            return "File Read Error:  Please try again";
        } finally {
            if(fis!=null)
                fis.close();
        }

        try {
            fis=new FileInputStream(file);
            long skip = fis.skip(salt.length);

            if(skip!=salt.length) {
                return "File Salt Read Error, please try again.";
            }

            int read = fis.read(iv);
            if(read!= iv.length){
                return "File IV Read Failure, please try again.";
            }
        } catch(IOException e) {
            return "File Read Error Occurred.  Please try again";
        } finally {
            if(fis!=null)
                fis.close();
        }

        SecretKeyFactory factory = SecretKeyFactory
                .getInstance(KEYFACTORY);
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
                128);
        SecretKey tmp = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);

        // file decryption
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

        //not sure about this bit. Worked with help of internet
        fName = file.toString().substring(0, file.toString().length()-4);
        //fis = new FileInputStream(file);
        //fos = new FileOutputStream(fName);
        try {

            fos = new FileOutputStream(fName);
            fis = new FileInputStream(file);

            byte[] in = new byte[64];
            int read;
            fis.skip(salt.length+iv.length);

            while ((read = fis.read(in)) != -1) {
                byte[] output = cipher.update(in, 0, read);
                if (output != null)
                    fos.write(output);
            }

            byte[] output = cipher.doFinal();

            if (output != null)
                fos.write(output);
        } catch(IOException e) {

            return "File Read Error Occurred, Please try again.";

        } finally {
            if(fis!=null)
                fis.close();
            if(fos!=null) {
                fos.flush();
                fos.close();
            }
        }
        return fName;
    }
}
