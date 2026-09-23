import java.io.*;
import java.net.*;

public class TCPFileClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            File file = new File("sample.txt");

            DataOutputStream dos =
                    new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(file.getName());

            FileInputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[4096];
            int count;

            while ((count = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, count);
            }

            fis.close();
            dos.close();
            socket.close();

            System.out.println("File Sent Successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}