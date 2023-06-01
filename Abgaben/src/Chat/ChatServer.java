package Chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private ArrayList<PrintWriter> clientOutputStreams;

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

    public void startServer() {
        clientOutputStreams = new ArrayList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is running on port 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread clientThread = new Thread(new ClientHandler(clientSocket, writer));
                clientThread.start();
                System.out.println("New client connected: " + clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message) {
        for (PrintWriter writer : clientOutputStreams) {
            writer.println(message);
            writer.flush();
        }
    }

    public void removeClient(PrintWriter writer) {
        clientOutputStreams.remove(writer);
    }

    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter writer;
        private String username;

        public ClientHandler(Socket clientSocket, PrintWriter writer) {
            this.clientSocket = clientSocket;
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    if (username == null) {
                        username = message;
                        sendUserJoinedMessage();
                    } else {
                        broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                removeClient(writer);
                sendUserLeftMessage();
            }
        }

        private void sendUserJoinedMessage() {
            broadcastMessage(username + " has joined the chat.");
        }

        private void sendUserLeftMessage() {
            broadcastMessage(username + " has left the chat.");
        }
    }
}
