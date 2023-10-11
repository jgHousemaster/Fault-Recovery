import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class ActiveNode {
    public static void main(String[] args) throws IOException, InterruptedException {
        // The simulation process being monitored. Start this after the Fault Monitor.
        int sendingInterval = 1;
        long timer = 0;
        Random RNG = new Random();
        long startTime;

        // Network setup
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("About to open a connection...");
        Socket socket = new Socket(address.getHostName(), 33075);
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
            writer.println("Active Node Running for: " + timer + " ms.");
            // Pit-a-pat
            writer.flush();
            System.out.println("Calculating Equation #" + (++num) + " ...");
            Thread.sleep(sendingInterval*1000);

            // This part simulates a function that calculates the input data.
            divisor = RNG.nextInt(0,20);
            dividend = RNG.nextInt();
            result = dividend / divisor;
            System.out.println("Result: " + divisor);

            // This will not be reached, just to trick IDE it's not an endless loop.
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
