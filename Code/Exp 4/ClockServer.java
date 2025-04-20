import java.io.*;
import java.net.*;

public class ClockServer {
    public static void main(String[] args) {
        final int port = 9090;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client in a separate thread
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Get input and output streams
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();

            // Receive client's time
            DataInputStream in = new DataInputStream(inFromClient);
            long clientTime = in.readLong();
            System.out.println("Received client time: " + clientTime);

            // Get server's current time
            long serverTime = System.currentTimeMillis();
            System.out.println("Server time: " + serverTime);

            // Send server's time to client
            DataOutputStream out = new DataOutputStream(outToClient);
            out.writeLong(serverTime);

            // Close the connection
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
