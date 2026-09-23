import java.io.*;
import java.net.*;

public class UDPFileClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();

        InetAddress address =
                InetAddress.getByName("localhost");

        FileInputStream fis =
                new FileInputStream("sample.txt");

        byte[] buffer = new byte[1024];

        int bytes;

        while ((bytes = fis.read(buffer)) != -1) {

            DatagramPacket packet =
                    new DatagramPacket(buffer, bytes,
                            address, 6000);

            socket.send(packet);
        }

        DatagramPacket endPacket =
                new DatagramPacket("END".getBytes(),
                        3, address, 6000);

        socket.send(endPacket);

        fis.close();
        socket.close();

        System.out.println("File Sent.");
    }
}
