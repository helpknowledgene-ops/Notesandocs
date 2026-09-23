import java.io.*;
import java.net.*;

public class HTTPUploadClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String html =
                "<html><body><h2>Uploaded by Client</h2></body></html>";

            out.println("POST /upload HTTP/1.1");
            out.println("Host: localhost");
            out.println("Content-Length: " + html.length());
            out.println();
            out.print(html);
            out.flush();

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
