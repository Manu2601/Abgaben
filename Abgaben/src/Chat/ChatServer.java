package Chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private ArrayList<PrintWriter> clientOutputStreams;
    private ArrayList<String> connectedUsers;

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

    public void startServer() {
        clientOutputStreams = new ArrayList<>();
        connectedUsers = new ArrayList<>();

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

    public void addConnectedUser(String username) {
        connectedUsers.add(username);
        broadcastUserList();
    }

    public void removeConnectedUser(String username) {
        connectedUsers.remove(username);
        broadcastUserList();
    }

    private void broadcastUserList() {
        StringBuilder userList = new StringBuilder("[userlist]");
        for (String user : connectedUsers) {
            userList.append(user).append(",");
        }
        userList.deleteCharAt(userList.length() - 1); // Remove the last comma
        for (PrintWriter writer : clientOutputStreams) {
            writer.println(userList.toString());
            writer.flush();
        }
    }

    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String username;

        public ClientHandler(Socket clientSocket, PrintWriter writer) {
            this.clientSocket = clientSocket;
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    if (username == null) {
                        username = message;
                        addConnectedUser(username);
                        sendUserJoinedMessage();
                    } else {
                        broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                removeConnectedUser(username);
                sendUserLeftMessage();
                try {
                    reader.close();
                    writer.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
