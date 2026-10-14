import java.io.*;
import java.net.*;

public class TelnetServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("TELNET Server Started...");
            System.out.println("Waiting for Client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client Connected.");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            String message;

            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    out.println("Connection Closed.");
                    break;
                }

                System.out.println("Client: " + message);
                out.println("Server Response: Command Received -> " + message);
            }

            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
