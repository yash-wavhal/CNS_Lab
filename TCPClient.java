package tcp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            System.out.println("Connected to server.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose an option: \n1. Say Hello \n2. File Transfer \n3. Calculator");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // a. Say Hello to Each Other
                    out.println("HELLO");
                    String helloResponse = in.readLine();
                    System.out.println("Server says: " + helloResponse);
                    break;

                case 2:
                    // b. File Transfer
                    out.println("FILE");
                    receiveFile(socket, "received_example.txt");
                    break;

                case 3:
                    // c. Calculator
                    System.out.println("Enter a calculation (e.g., 12 + 5): ");
                    String expression = scanner.nextLine();
                    out.println("CALCULATOR");
                    out.println(expression);
                    String result = in.readLine();
                    System.out.println("Calculation result: " + result);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void receiveFile(Socket socket, String saveAs) {
        try {
            byte[] buffer = new byte[4096];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(saveAs);

            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            System.out.println("File received and saved as " + saveAs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

