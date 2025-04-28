package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PacManServer {
    public static void main(String[] args) {
        final int PORT = 7286;
        ScoreSaver.init();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Start a new thread to handle this client
                new Connection(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
