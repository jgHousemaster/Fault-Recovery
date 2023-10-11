import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FaultMonitor {
    public static void main(String[] args) throws IOException, InterruptedException {
        // The monitor using the heartbeat tactic, start this program first.
        int checkingInterval = 1;
        int expireTime = 5;
        long lastUpdatedTime;
        long timer;
        boolean fault_detected = false;

        // Network setup
        // For Active Node
        ServerSocket server = new ServerSocket(33075);
        System.out.println("Waiting for Active Node...");
        Socket client = server.accept();
        System.out.println("Active Node connected!");
        InputStream in = client.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inr);
        String line;

        // For Redundant Node
        ServerSocket serverR = new ServerSocket(33076);
        System.out.println("Waiting for Redundant Node...");
        Socket clientR = serverR.accept();
        System.out.println("Redundant Node connected!");
        InputStream inR = clientR.getInputStream();
        InputStreamReader inrR = new InputStreamReader(inR);
        BufferedReader readerR = new BufferedReader(inrR);

        // Update time
        lastUpdatedTime = System.currentTimeMillis();

        // Heartbeat Monitor
        while(true){
            timer = System.currentTimeMillis() - lastUpdatedTime;

            if (!fault_detected) {
                // Active Node running
                // RECEIVE
                line = reader.readLine();

                if(line != null){
                    // Heartbeat is alive
                    System.out.println(line);
                    line = readerR.readLine(); // To clean out the cache for Redundant Node.
                    lastUpdatedTime = System.currentTimeMillis();
                }
                // Active Node is down
                if(timer > expireTime*1000){
                    // Time expired
                    System.out.println("---------------------------------\n" +
                            "Warning: The Active Node is down.\n" +
                            "Switching to Redundant Node.\n" +
                            "---------------------------------");
                    JOptionPane.showMessageDialog(null, "---------------------------------\n" +
                            "Warning: The Active Node is down.\n" +
                            "Switching to Redundant Node.\n" +
                            "---------------------------------");
                    fault_detected = true;
                }
            }
            else {
                // Redundant Node running
                line = readerR.readLine();
                if (line != null){
                    System.out.println(line);
                }
            }

            // This will be reached after 10 minutes, just to trick IDE it's not an endless loop.
            if(timer > 1000000){
                System.out.println("System is shutting down...");
                break;
            }
        }

        server.close();
    }
}
