import java.io.*;
import java.util.Scanner;

public class FileAuthentication {
    public static void main(String[] args) {
        final String USERNAME = "admin";
        final String PASSWORD = "admin123";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String user = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();
        if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
            System.out.println("\nAuthentication Successful!");
            System.out.println("Accessing Protected File...\n");
            try {
                File file = new File("ProtectedFile.txt");
                if (!file.exists()) {
                    System.out.println("Protected file not found.");
                    return;
                }
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) System.out.println(line);
                br.close();
            } catch (IOException e) { e.printStackTrace(); }
        } else {
            System.out.println("\nAuthentication Failed!");
            System.out.println("Access Denied.");
        }
        sc.close();
    }
}
