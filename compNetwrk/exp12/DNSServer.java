import java.io.*;
import java.net.*;
import java.util.HashMap;

public class DNSServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("DNS Server Started...");
            System.out.println("Waiting for Client...");

            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            HashMap<String, String> dnsTable = new HashMap<>();
            dnsTable.put("www.google.com", "142.250.183.110");
            dnsTable.put("www.yahoo.com", "98.137.11.163");
            dnsTable.put("www.amazon.com", "176.32.103.205");
            dnsTable.put("www.facebook.com", "157.240.22.35");
            dnsTable.put("www.openai.com", "104.18.33.45");

            String domain;
            while ((domain = in.readLine()) != null) {
                if (domain.equalsIgnoreCase("exit")) break;
                String ip = dnsTable.get(domain);
                if (ip != null) out.println(ip);
                else out.println("Domain Not Found");
            }
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
