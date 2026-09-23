import java.io.*;
import java.net.*;

public class UDPFileServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(6000);

        byte[] buffer = new byte[1024];

        FileOutputStream fos =
                new FileOutputStream("Received_UDP.txt");

        System.out.println("Waiting for File...");

        while (true) {

            DatagramPacket packet =
                    new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            String data = new String(packet.getData(), 0,
                    packet.getLength());

            if (data.equals("END"))
                break;

            fos.write(packet.getData(), 0, packet.getLength());
        }

        fos.close();
        socket.close();

        System.out.println("File Received.");
    }
}