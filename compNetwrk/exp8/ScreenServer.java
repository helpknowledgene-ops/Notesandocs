import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class ScreenServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Waiting for client...");
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected.");
            Robot robot = new Robot();
            Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = robot.createScreenCapture(screen);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(imageBytes.length);
            dos.write(imageBytes);
            dos.flush();
            System.out.println("Screenshot Sent Successfully.");
            dos.close(); socket.close(); serverSocket.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
