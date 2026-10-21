import java.io.*;
import java.net.*;

public class DNSClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                System.out.print("Enter Domain Name : ");
                String domain = keyboard.readLine();
                out.println(domain);
                if (domain.equalsIgnoreCase("exit")) break;
                System.out.println("IP Address : " + in.readLine());
                System.out.println();
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
