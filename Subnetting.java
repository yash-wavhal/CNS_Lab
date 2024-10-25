package subnetting;
import java.util.Scanner;

public class Subnetting {
    public static int[] convertToIntArray(String ip) {
        String[] parts = ip.split("\\.");
        int[] ipAddress = new int[4];
        for (int i = 0; i < 4; i++) {
            ipAddress[i] = Integer.parseInt(parts[i]);
        }
        return ipAddress;
    }
    public static void printIPAddress(int[] ip) {
        System.out.println(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
    }
    public static int calculateSubnetBits(int subnets) {
        int bits = 0;
        while (Math.pow(2, bits) < subnets) {
            bits++;
        }
        return bits;
    }
    public static int[] calculateSubnetMask(int subnetBits) {
        int[] subnetMask = {255, 255, 255, 0};
        int mask = 0;
        for (int i = 0; i < subnetBits; i++) {
            mask += (1 << (7 - i));
        }
        subnetMask[3] = mask;
        return subnetMask;
    }
    public static int calculateHostsPerSubnet(int subnetBits) {
        return (int) Math.pow(2, (8 - subnetBits)) - 2;  // Subtracting 2 for network and broadcast addresses
    }
    public static void printSubnets(int[] ipAddress, int[] subnetMask, int subnets, int hostsPerSubnet) {
        System.out.println("Subnet Addresses:");
        int increment = 256 / subnets;
        for (int i = 0; i < subnets; i++) {
            int[] subnetAddress = ipAddress.clone();
            subnetAddress[3] = i * increment;
            printIPAddress(subnetAddress);
        }

        System.out.println("\nEach subnet can have " + hostsPerSubnet + " usable hosts.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a Class C IP address (e.g., 192.168.1.0):");
        String ipInput = scanner.nextLine();
        int[] ipAddress = convertToIntArray(ipInput);

        System.out.println("Enter the number of subnets you want:");
        int subnets = scanner.nextInt();
        int subnetBits = calculateSubnetBits(subnets);
        int[] subnetMask = calculateSubnetMask(subnetBits);
        System.out.println("Subnet Mask:");
        printIPAddress(subnetMask);
        int hostsPerSubnet = calculateHostsPerSubnet(subnetBits);

        printSubnets(ipAddress, subnetMask, subnets, hostsPerSubnet);

        scanner.close();
    }
}

