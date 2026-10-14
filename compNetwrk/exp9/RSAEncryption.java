import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class RSAEncryption {
    public static void main(String[] args) {
        try {
            // Generate RSA Key Pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PublicKey publicKey = pair.getPublic();
            PrivateKey privateKey = pair.getPrivate();

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Plain Text: ");
            String plainText = sc.nextLine();

            // Encryption
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String cipherText = Base64.getEncoder().encodeToString(encryptedBytes);

            System.out.println("\nEncrypted Text:");
            System.out.println(cipherText);

            // Decryption
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedText = new String(decryptedBytes);

            System.out.println("\nDecrypted Text:");
            System.out.println(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
