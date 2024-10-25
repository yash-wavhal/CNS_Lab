package dns;
import java.net.*;
import java.util.Scanner;

public class DNSLookup {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nDNS Lookup Options:");
            System.out.println("1. Find IP address from URL");
            System.out.println("2. Find URL from IP address");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    // Option 1: Find IP address from URL
                    System.out.print("Enter URL (e.g., www.google.com): ");
                    String url = scanner.nextLine();
                    findIPAddress(url);
                    break;
                
                case 2:
                    // Option 2: Find URL from IP address
                    System.out.print("Enter IP address (e.g., 142.250.190.78): ");
                    String ipAddress = scanner.nextLine();
                    findHostname(ipAddress);
                    break;
                    
                case 3:
                    // Option 3: Exit
                    System.out.println("Exiting DNS Lookup.");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private static void findIPAddress(String url) {
        try {
            InetAddress inetAddress = InetAddress.getByName(url);
            System.out.println("IP Address for " + url + ": " + inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host: " + e.getMessage());
        }
    }
    private static void findHostname(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            String hostName = inetAddress.getCanonicalHostName();
            System.out.println("Hostname for " + ipAddress + ": " + hostName);
        } catch (UnknownHostException e) {
            System.out.println("Invalid IP Address: " + e.getMessage());
        }
    }
}
