package Go_back_N;
import java.util.Random;
import java.util.Scanner;

public class SlidingWindowProtocol {

    static Random random = new Random();


    public static void goBackN(int totalFrames, int windowSize) {
        int sentFrames = 0;

        while (sentFrames < totalFrames) {
            int windowEnd = Math.min(sentFrames + windowSize, totalFrames);
            System.out.println("\nSending frames " + sentFrames + " to " + (windowEnd - 1));

            for (int i = sentFrames; i < windowEnd; i++) {
                boolean success = random.nextBoolean(); 
                if (success) {
                    System.out.println("Frame " + i + " successfully received.");
                } else {
                    System.out.println("Frame " + i + " lost or damaged. Resending window from frame " + i);
                    sentFrames = i;
                    break;
                }

                if (i == windowEnd - 1) {
                    sentFrames = windowEnd;
                }
            }
        }

        System.out.println("All frames successfully transmitted using Go-Back-N.\n");
    }
    public static void selectiveRepeat(int totalFrames, int windowSize) {
        boolean[] received = new boolean[totalFrames];
        int sentFrames = 0;

        while (sentFrames < totalFrames) {
            int windowEnd = Math.min(sentFrames + windowSize, totalFrames);
            System.out.println("\nSending frames " + sentFrames + " to " + (windowEnd - 1));
            for (int i = sentFrames; i < windowEnd; i++) {
                if (!received[i]) {
                    boolean success = random.nextBoolean();
                    if (success) {
                        System.out.println("Frame " + i + " successfully received.");
                        received[i] = true;
                    } else {
                        System.out.println("Frame " + i + " lost or damaged. Will retransmit.");
                    }
                }
            }
            while (sentFrames < totalFrames && received[sentFrames]) {
                sentFrames++;
            }
        }

        System.out.println("All frames successfully transmitted using Selective Repeat.\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sliding Window Protocol Simulation in Peer-to-Peer Mode");
        System.out.println("Enter total number of frames to send:");
        int totalFrames = scanner.nextInt();

        System.out.println("Enter the window size:");
        int windowSize = scanner.nextInt();

        System.out.println("\nChoose a mode:\n1. Go-Back-N\n2. Selective Repeat");
        int mode = scanner.nextInt();

        switch (mode) {
            case 1:
                goBackN(totalFrames, windowSize);
                break;
            case 2:
                selectiveRepeat(totalFrames, windowSize);
                break;
            default:
                System.out.println("Invalid mode selected.");
        }

        scanner.close();
    }
}

