package server;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread{
    Socket clientSocket;

    public Connection(Socket socket)  {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader textInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter textOutput = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {

            String received;
            while ((received = textInput.readLine()) != null) {
                System.out.println(received);
                String action = received.split(":")[0];

                if (action.equals("GET")) {
                    textOutput.println(ScoreSaver.getHighScore());
                } else if (action.equals("SET")) {
                    ScoreSaver.checkScore(Integer.parseInt(received.split(":")[1]));
                }
            }


        } catch (IOException e) {
            System.out.println("Error with client " + clientSocket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}


