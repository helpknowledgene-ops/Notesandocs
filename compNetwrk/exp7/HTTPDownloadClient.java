import java.io.*;
import java.net.*;

public class HTTPDownloadClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out.println("GET / HTTP/1.1");
            out.println("Host: localhost");
            out.println();

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
