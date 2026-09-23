import java.io.*;
import java.net.*;

public class RemoteCommandServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server waiting for client...");
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String command;
            while ((command = in.readLine()) != null) {
                if (command.equalsIgnoreCase("exit")) break;
                try {
                    ProcessBuilder pb;
                    if (System.getProperty("os.name").toLowerCase().contains("win")) {
                        pb = new ProcessBuilder("cmd", "/c", command);
                    } else {
                        pb = new ProcessBuilder("sh", "-c", command);
                    }
                    Process process = pb.start();
                    BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = result.readLine()) != null) out.println(line);
                    out.println("END");
                } catch (Exception e) {
                    out.println("Error: " + e.getMessage());
                    out.println("END");
                }
            }
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
