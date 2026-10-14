import java.io.*;
import java.net.*;

public class TelnetClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6000);

            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            String command;

            while (true) {
                System.out.print("TELNET> ");
                command = keyboard.readLine();

                out.println(command);
                System.out.println(in.readLine());

                if (command.equalsIgnoreCase("exit"))
                    break;
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
