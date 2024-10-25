package udp;
import java.io.*;
import java.net.*;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost"; // Change this to the server's IP address if on a different machine
    private static final int SERVER_PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the file path to send:");
            String filePath = userInput.readLine();
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found.");
                return;
            }
            String fileName = file.getName();
            byte[] fileNameBytes = fileName.getBytes();
            DatagramPacket fileNamePacket = new DatagramPacket(fileNameBytes, fileNameBytes.length, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            socket.send(fileNamePacket);
            System.out.println("Sending file: " + fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] sendBuffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = fis.read(sendBuffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(sendBuffer, bytesRead, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                socket.send(packet);
            }
            DatagramPacket endPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            socket.send(endPacket);
            System.out.println("File transfer complete.");

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

