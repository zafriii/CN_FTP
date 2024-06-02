import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

public class FileServer extends JFrame{

    private Container c;
    private Font f;
    private JLabel capLabel, cap2Label; 
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel, connectLabel, connectedLabel;
    private JButton bt1;
    
    private JPanel filesPanel;

    FileServer() {
        initComponents();
    }

    public void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Sky Vault file transfer Server");
        capLabel.setBounds(220, 1, 700, 100);
        capLabel.setFont(f);
        c.add(capLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(50, 100, 150, 40);
        c.add(serverLabel);

        servernumLabel = new JLabel("354.53.34.34");
        servernumLabel.setBounds(110, 100, 150, 40);
        c.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        c.add(portLabel);

        portnumLabel = new JLabel("3432");
        portnumLabel.setBounds(140, 120, 150, 40);
        c.add(portnumLabel);

        connectLabel = new JLabel("Status:");
        connectLabel.setBounds(600, 120, 150, 40);
        c.add(connectLabel);

    
        connectedLabel = new JLabel("Running");
        connectedLabel.setBounds(650, 120, 150, 40);
        connectedLabel.setForeground(Color.GREEN);
        c.add(connectedLabel);
     

        connectLabel = new JLabel("Device Connected:");
        connectLabel.setBounds(600, 150, 150, 40);
        c.add(connectLabel);

    
        connectedLabel = new JLabel("4");
        connectedLabel.setBounds(720, 150, 150, 40);
        connectedLabel.setForeground(Color.BLUE);
        c.add(connectedLabel);




        cap2Label = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        cap2Label.setText("Available Files");
        cap2Label.setBounds(290, 150, 700, 100);
        cap2Label.setFont(f);
        c.add(cap2Label);

        // Create a panel to hold the files
        filesPanel = new JPanel();
        filesPanel.setLayout(null);
        filesPanel.setBackground(Color.WHITE);

        // Create a scroll pane and add the filesPanel to it
        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(scrollPane);

        // Add files to the filesPanel
        addFile("Welcome Home.mp4", "File Size: 34MB", 0);
        addFile("Website logo.png", "File Size: 334KB", 1);

        // Add more files as needed...

        bt1 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        bt1.setText("START");
        bt1.setBounds(300, 600, 180, 35);
        bt1.setBackground(Color.GREEN);
        bt1.setForeground(Color.BLACK);
        bt1.setFont(f);
        c.add(bt1);

        
    }

    // Method to add a file entry to the filesPanel
    private void addFile(String fileName, String fileSize, int index) {
        JLabel fileLabel = new JLabel(fileName);
        fileLabel.setBounds(100, 30 + index * 80, 150, 40);
        filesPanel.add(fileLabel);

        JLabel file2Label = new JLabel(fileSize);
        file2Label.setBounds(100, 60 + index * 80, 150, 40);
        filesPanel.add(file2Label);

       
        

    }

    public static void main(String[] args) {
        FileServer frame = new FileServer();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
    }
}


/////////////////////////


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends JFrame {

    private Container c;
    private Font f;
    private JLabel capLabel, serverLabel, servernumLabel, portLabel, portnumLabel, connectLabel, connectedLabel, deviceLabel, devicenoLabel;
    private JButton bt1;
    private JPanel filesPanel;

    private boolean serverRunning = false;
    private int connectedDevices = 0;
    private ServerSocket serverSocket;

    FileServer() {
        initComponents();
    }

    public void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Sky Vault file transfer Server");
        capLabel.setBounds(220, 1, 700, 100);
        capLabel.setFont(f);
        c.add(capLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(50, 100, 150, 40);
        c.add(serverLabel);

        servernumLabel = new JLabel("localhost");
        servernumLabel.setBounds(110, 100, 150, 40);
        c.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        c.add(portLabel);

        portnumLabel = new JLabel("3432");
        portnumLabel.setBounds(140, 120, 150, 40);
        c.add(portnumLabel);

        connectLabel = new JLabel("Status:");
        connectLabel.setBounds(600, 120, 150, 40);
        c.add(connectLabel);

        connectedLabel = new JLabel("Not started");
        connectedLabel.setBounds(650, 120, 150, 40);
        connectedLabel.setForeground(Color.RED);
        c.add(connectedLabel);

        deviceLabel = new JLabel("Device Connected:");
        deviceLabel.setBounds(600, 150, 150, 40);
        c.add(deviceLabel);

        devicenoLabel = new JLabel("0");
        devicenoLabel.setBounds(720, 150, 150, 40);
        devicenoLabel.setForeground(Color.BLUE);
        c.add(devicenoLabel);

        bt1 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        bt1.setText("START");
        bt1.setBounds(300, 600, 180, 35);
        bt1.setBackground(Color.GREEN);
        bt1.setForeground(Color.BLACK);
        bt1.setFont(f);
        bt1.addActionListener(e -> toggleServer());
        c.add(bt1);

        // Create a panel to hold the files
        filesPanel = new JPanel();
        filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));
        filesPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(scrollPane);
    }

    private void toggleServer() {
        if (serverRunning) {
            stopServer();
        } else {
            startServer();
        }
    }

    private void startServer() {
        int port = 3432; // Port number to listen on

        try {
            if (!serverRunning) {
                serverSocket = new ServerSocket(port);
                serverRunning = true;
                connectedLabel.setText("Running");
                connectedLabel.setForeground(Color.GREEN);
                connectedDevices = 0; // Reset connected devices count
                bt1.setText("STOP");
                bt1.setBackground(Color.RED);
                bt1.setForeground(Color.WHITE);
                System.out.println("Server started. Waiting for clients...");

                // Listen for clients in a separate thread
                Thread serverThread = new Thread(() -> {
                    while (serverRunning) {
                        try {
                            Socket clientSocket = serverSocket.accept(); // Wait for a client to connect
                            connectedDevices++;
                            devicenoLabel.setText(String.valueOf(connectedDevices));
                            System.out.println("Client connected: " + clientSocket.getInetAddress());

                            // Handle client requests in a separate thread
                            Thread clientThread = new Thread(() -> handleClient(clientSocket));
                            clientThread.start();
                        } catch (IOException e) {
                            if (serverRunning) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
        try {
            if (serverRunning) {
                serverRunning = false;
                connectedLabel.setText("Not started");
                connectedLabel.setForeground(Color.RED);
                devicenoLabel.setText("0"); // Reset connected devices count
                serverSocket.close(); // Close the server socket
                bt1.setText("START");
                bt1.setBackground(Color.GREEN);
                bt1.setForeground(Color.BLACK);
                System.out.println("Server stopped.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {

            while (true) {
                String command = dis.readUTF(); // Read the command from the client

                if (command.equals("UPLOAD")) {
                    String fileName = dis.readUTF(); // Read the file name
                    long fileSize = dis.readLong(); // Read the file size

                    FileOutputStream fos = new FileOutputStream("server_files/" + fileName);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while (fileSize > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        fileSize -= bytesRead;
                    }
                    fos.close();
                    dos.writeUTF("UPLOAD_SUCCESS"); // Notify the client that upload was successful
                } else if (command.equals("DOWNLOAD")) {
                    String fileName = dis.readUTF(); // Read the file name
                    File file = new File("server_files/" + fileName);
                    if (file.exists()) {
                        dos.writeUTF("DOWNLOAD_READY"); // Notify the client that the file is ready for download
                        dos.writeLong(file.length()); // Send the file size
                        FileInputStream fis = new FileInputStream(file);
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                        }
                        fis.close();
                    } else {
                        dos.writeUTF("FILE_NOT_FOUND"); // Notify the client that the file does not exist
                    }
                } else if (command.equals("DELETE")) {
                    String fileName = dis.readUTF(); // Read the file name
                    File file = new File("server_files/" + fileName);
                    if (file.delete()) {
                        dos.writeUTF("DELETE_SUCCESS"); // Notify the client that deletion was successful
                    } else {
                        dos.writeUTF("DELETE_FAILURE"); // Notify the client that deletion failed
                    }
                } else {
                    // Unsupported command
                    System.out.println("Unsupported command: " + command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connectedDevices--;
            devicenoLabel.setText(String.valueOf(connectedDevices));
        }
    }

    public static void main(String[] args) {
        FileServer frame = new FileServer();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer - Server");
        frame.setResizable(false);
    }
}

