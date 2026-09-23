import java.io.*;
import java.net.*;

public class HTTPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("HTTP Server Running on Port 8080...");

            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader in =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream out = socket.getOutputStream();

                String requestLine = in.readLine();

                if (requestLine == null) {
                    socket.close();
                    continue;
                }

                System.out.println(requestLine);

                if (requestLine.startsWith("GET")) {
                    String html =
                        "<html><body>" +
                        "<h1>Welcome to Java HTTP Server</h1>" +
                        "<p>Web Page Download Successful.</p>" +
                        "</body></html>";

                    String response =
                        "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + html.length() + "\r\n\r\n" +
                        html;

                    out.write(response.getBytes());

                } else if (requestLine.startsWith("POST")) {
                    String line;
                    int contentLength = 0;

                    while (!(line = in.readLine()).isEmpty()) {
                        if (line.startsWith("Content-Length")) {
                            contentLength =
                                Integer.parseInt(line.split(":")[1].trim());
                        }
                    }

                    char[] body = new char[contentLength];
                    in.read(body);

                    FileWriter fw = new FileWriter("UploadedPage.html");
                    fw.write(body);
                    fw.close();

                    String response =
                        "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n\r\n" +
                        "Upload Successful";

                    out.write(response.getBytes());
                }

                out.flush();
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
