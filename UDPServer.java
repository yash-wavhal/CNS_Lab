package udp;
import java.io.*;
import java.net.*;

public class UDPServer {
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(PORT);
            System.out.println("UDP Server is running on port " + PORT);
            byte[] receiveBuffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(packet);
            String fileName = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Receiving file: " + fileName);
            FileOutputStream fos = new FileOutputStream("received_" + fileName);
            boolean receiving = true;
            while (receiving) {
                receiveBuffer = new byte[BUFFER_SIZE];
                packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);
                if (packet.getLength() == 0) {
                    receiving = false;
                    System.out.println("File transfer complete.");
                } else {
                    fos.write(packet.getData(), 0, packet.getLength());
                }
            }

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

