import java.io.*;
import java.net.*;

public class RemoteCommandClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                System.out.print("Enter Command: ");
                String command = keyboard.readLine();
                out.println(command);
                if (command.equalsIgnoreCase("exit")) break;
                System.out.println("\nCommand Output:");
                String line;
                while (!(line = in.readLine()).equals("END")) System.out.println(line);
                System.out.println();
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
