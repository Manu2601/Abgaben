package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient extends JFrame implements ActionListener {

    private JTextArea chatTextArea;
    private JList<String> userList;
    private JTextField messageTextField;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String username;
    private DefaultListModel<String> connectedUsersModel;

    public ChatClient(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Chat Panel
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatTextArea = new JTextArea(15, 30);
        chatTextArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        chatTextArea.setLineWrap(true);
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        // User List Panel
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BorderLayout());
        connectedUsersModel = new DefaultListModel<>();
        userList = new JList<>(connectedUsersModel);

        // User List Title
        JLabel userListTitle = new JLabel("User");
        userListTitle.setHorizontalAlignment(SwingConstants.CENTER);
        userListPanel.add(userListTitle, BorderLayout.NORTH);

        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(150, 0));
        userListPanel.add(userListScrollPane, BorderLayout.CENTER);

        // Message Panel
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messageTextField = new JTextField(30);
        messageTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        JButton sendButton = new JButton("Senden");
        sendButton.addActionListener(this);
        messagePanel.add(messageTextField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        // Add panels to the main frame
        add(chatPanel, BorderLayout.CENTER);
        add(userListPanel, BorderLayout.EAST);
        add(messagePanel, BorderLayout.SOUTH);

        // Prompt for username
        username = promptForUsername();

        // Set up network connection
        setUpNetworking();

        // Start reading messages from the server
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        // Send message when Enter key is pressed
        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        setVisible(true);
    }

    private String promptForUsername() {
        return JOptionPane.showInputDialog(this, "Enter your username:");
    }

    private void setUpNetworking() {
        try {
            socket = new Socket("192.168.55.174", 5000);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Network connection established...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            writer.println(username + ": " + message);
            writer.flush();
            messageTextField.setText("");
        }
    }

    private void processUserList(String userList) {
        connectedUsersModel.clear();
        String[] users = userList.split(",");
        for (String user : users) {
            connectedUsersModel.addElement(user.trim());
        }
    }

    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    if (message.startsWith("!userlist:")) {
                        processUserList(message.substring(10));
                    } else {
                        chatTextArea.append(message + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Senden")) {
            sendMessage();
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient("Einfacher Chat-Client");
        chatClient.setVisible(true);
    }
}
