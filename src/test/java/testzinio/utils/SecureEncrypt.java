/**
 * SecureEncrypt.java.
 *
 * SecureEncrypt Class provide different utils function to encrypt and decrypt data.
 *
 * @author Jose Antonio Torre
 * @version 1.0
 */

package testzinio.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

/**
 * Class SecureEncrypt
 */
public class SecureEncrypt {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    /**
     * Function: setKey
     * Description: Store the key passed as parameter in the secretKey variable.
     * @param myKey key used to encrypt or decrypt data.
     */
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function: encrypt
     * Description: Encrypt a string using a secret key.
     * @param strToEncrypt String to encrypt.
     * @param secret Key used to encrypt the string
     * @return The string encrypted
     */
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * Function: decrypt
     * Description: Decrypt a string using a secret key.
     * @param strToDecrypt String to decrypt
     * @param secret Key used to decrypt the string
     * @return the string decrypted.
     */
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}

