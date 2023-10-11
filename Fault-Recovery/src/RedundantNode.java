import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class RedundantNode {
    public static void main(String[] args) throws IOException, InterruptedException {
        // The redundancy of  Active Node, start this after Active Node.
        int sendingInterval = 1;
        long timer = 0;
        Random RNG = new Random();
        long startTime;

        // Network setup
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("About to open a connection...");
        Socket socket = new Socket(address.getHostName(), 33076);
        System.out.println("Connection opened.");
        OutputStream notification = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(notification);

        // Heartbeat loop setup
        startTime = System.currentTimeMillis();
        int dividend;
        int divisor;
        int result;
        int num = 0;

        while(true){
            timer = (System.currentTimeMillis() - startTime);
            //SEND
            writer.println("Redundant Node Running for: " + timer + " ms.");
            // Pit-a-pat
            writer.flush();
            System.out.println("Calculating Equation #" + (++num) + " ...");
            Thread.sleep(sendingInterval*1000);

            // This part simulates a function that calculates the input data.
            // The difference is this node won't crash.
            divisor = RNG.nextInt(1,20);
            dividend = RNG.nextInt();
            result = dividend / divisor;
            System.out.println("Result: " + divisor);

            // This will not be reached within 10 minutes, just to trick IDE it's not an endless loop.
            if(timer > 1000000){
                writer.println("The assigned calculation tasks are completed within" + timer + " ms successfully.");
                writer.flush();
                break;
            }
        }

        System.out.println("The last result is " + result);
        System.out.println("Sender shuts down.");

        socket.close();
    }
}
