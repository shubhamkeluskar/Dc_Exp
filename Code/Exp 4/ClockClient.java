import java.io.*;
import java.net.*;

public class ClockClient {
    public static void main(String[] args) {
        final String serverName = "localhost";
        final int port = 9090;

        try {
            Socket socket = new Socket(serverName, port);
            System.out.println("Connected to server.");

            // Get input and output streams
            OutputStream outToServer = socket.getOutputStream();
            InputStream inFromServer = socket.getInputStream();

            // Get client's current time
            long clientTime = System.currentTimeMillis();
            System.out.println("Client time: " + clientTime);

            // Send client's time to server
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeLong(clientTime);

            // Receive server's time
            DataInputStream in = new DataInputStream(inFromServer);
            long serverTime = in.readLong();
            System.out.println("Server time: " + serverTime);

            // Calculate time difference
            long timeDifference = serverTime - clientTime;
            System.out.println("Time difference: " + timeDifference);

            // Adjust client's clock
            long adjustedTime = System.currentTimeMillis() + timeDifference;
            System.out.println("Adjusted time: " + adjustedTime);

            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
