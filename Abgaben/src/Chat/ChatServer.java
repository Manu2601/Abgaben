package Chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private HashMap<PrintWriter, String> clientOutputStreams;

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

    public void startServer() {
        clientOutputStreams = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is running on port 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.put(writer, "");

                Thread clientThread = new Thread(new ClientHandler(clientSocket, writer));
                clientThread.start();
                System.out.println("New client connected: " + clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message) {
        for (PrintWriter writer : clientOutputStreams.keySet()) {
            writer.println(message);
            writer.flush();
        }
    }

    public void sendUserList(PrintWriter writer) {
        StringBuilder userList = new StringBuilder("!userlist: ");
        for (String username : clientOutputStreams.values()) {
            userList.append(username).append(",");
        }
        userList.deleteCharAt(userList.length() - 1);
        writer.println(userList.toString());
        writer.flush();
    }

    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String username;

        public ClientHandler(Socket clientSocket, PrintWriter writer) {
            this.clientSocket = clientSocket;
            this.writer = writer;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                username = reader.readLine().trim();
                clientOutputStreams.put(writer, username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String message;
                sendUserList(writer); // Send initial user list to the connected client

                while ((message = reader.readLine()) != null) {
                    if (message.startsWith("!userlist")) {
                        sendUserList(writer);
                    } else {
                        broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientOutputStreams.remove(writer);
                broadcastMessage(username + " has left the chat.");
            }
        }
    }
}
