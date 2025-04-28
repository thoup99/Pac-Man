package game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PacManClient {
    static Socket clientSocket;
    static BufferedReader textInput;
    static PrintWriter textOutput;

    public static void init() {
        final String serverAddress = "localhost";
        final int PORT = 7286;

        try {
            clientSocket = new Socket(serverAddress, PORT);
            textInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            textOutput = new PrintWriter(clientSocket.getOutputStream(), true);

            System.out.println("Connected to server at " + serverAddress + ":" + PORT);

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    public static int getHighScore() {
        if (textInput == null) {
            return 0;
        }

        try {
            textOutput.println("GET:");
            textOutput.flush();
            return Integer.parseInt(textInput.readLine());
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            return 0;
        }
    }

    public static void submitScore(int score) {
        if (textInput == null) {
            return;
        }

        textOutput.println("SET:" + score);
    }
}

