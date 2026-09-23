import java.io.*;
import java.net.*;

public class TCPFileServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server waiting for connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());

            String fileName = dis.readUTF();

            FileOutputStream fos = new FileOutputStream("Received_" + fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = dis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            dis.close();
            socket.close();
            serverSocket.close();

            System.out.println("File received successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}