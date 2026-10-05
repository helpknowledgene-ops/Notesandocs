import java.io.*;
import java.net.*;

public class ScreenClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int size = dis.readInt();
            byte[] imageBytes = new byte[size];
            dis.readFully(imageBytes);
            FileOutputStream fos = new FileOutputStream("RemoteScreen.png");
            fos.write(imageBytes);
            fos.close(); dis.close(); socket.close();
            System.out.println("Screenshot Received Successfully.");
            System.out.println("Saved as RemoteScreen.png");
        } catch (Exception e) { e.printStackTrace(); }
    }
}
