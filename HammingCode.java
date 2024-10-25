package hamming;
import java.util.Scanner;

public class HammingCode {

    public static int[] encodeHammingCode(int[] data) {
        int m = data.length;
        int r = 0;

        while (Math.pow(2, r) < (m + r + 1)) {
            r++;
        }

        int[] encoded = new int[m + r];
        int j = 0;

        for (int i = 1; i <= encoded.length; i++) {
            if (Math.pow(2, j) == i) {
                j++;
            } else {
                encoded[i - 1] = data[m - i + j];
            }
        }
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int parityValue = 0;

            for (int k = parityIndex; k < encoded.length; k += (2 * (parityIndex + 1))) {
                for (int l = k; l < k + parityIndex + 1 && l < encoded.length; l++) {
                    parityValue ^= encoded[l];
                }
            }

            encoded[parityIndex] = parityValue;
        }

        return encoded;
    }

    public static int[] detectAndCorrectHammingCode(int[] encoded) {
        int r = 0;
        int n = encoded.length;

        // Find the number of parity bits
        while (Math.pow(2, r) < n + 1) {
            r++;
        }

        int errorPosition = 0;
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int parityValue = 0;

            for (int k = parityIndex; k < n; k += (2 * (parityIndex + 1))) {
                for (int l = k; l < k + parityIndex + 1 && l < n; l++) {
                    parityValue ^= encoded[l];
                }
            }
            if (parityValue != 0) {
                errorPosition += parityIndex + 1;
            }
        }
        if (errorPosition != 0) {
            System.out.println("Error detected at position: " + errorPosition);
            encoded[errorPosition - 1] ^= 1; // Correct the error by flipping the bit
        } else {
            System.out.println("No errors detected.");
        }

        return encoded;
    }
    public static int[] extractData(int[] encoded) {
        int r = 0;
        int n = encoded.length;
        while (Math.pow(2, r) < n + 1) {
            r++;
        }

        int[] data = new int[n - r];
        int j = 0;
        for (int i = 1; i <= n; i++) {
            if (Math.pow(2, j) == i) {
                j++;
            } else {
                data[i - j - 1] = encoded[i - 1];
            }
        }

        return data;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of data bits:");
        int dataBits = scanner.nextInt();

        int[] data = new int[dataBits];
        System.out.println("Enter the data bits one by one:");
        for (int i = 0; i < dataBits; i++) {
            data[i] = scanner.nextInt();
        }
        int[] encoded = encodeHammingCode(data);
        System.out.println("Encoded Hamming Code:");
        for (int bit : encoded) {
            System.out.print(bit + " ");
        }
        System.out.println();
        System.out.println("Introduce error by flipping a bit? (yes/no):");
        String error = scanner.next();
        if (error.equalsIgnoreCase("yes")) {
            System.out.println("Enter the position to flip (1-based index):");
            int position = scanner.nextInt();
            encoded[position - 1] ^= 1;
        }
        int[] correctedCode = detectAndCorrectHammingCode(encoded);
        System.out.println("Corrected Hamming Code:");
        for (int bit : correctedCode) {
            System.out.print(bit + " ");
        }
        System.out.println();
        int[] extractedData = extractData(correctedCode);
        System.out.println("Extracted original data:");
        for (int bit : extractedData) {
            System.out.print(bit + " ");
        }
        System.out.println();

        scanner.close();
    }
}

