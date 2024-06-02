import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;

public class FileClientGUI2 extends JFrame{

    private Container c;
    private Font f;
    private JLabel capLabel, cap2Label, cap3Label, cap4Label; 
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel, connectLabel, connectedLabel;
    private JButton bt1, bt2;
    
    private JPanel filesPanel;

    FileClientGUI2() {
        initComponents();
    }

    public void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Sky Vault file transfer Client");
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

        connectedLabel = new JLabel("Connected");
        connectedLabel.setBounds(650, 120, 150, 40);
        connectedLabel.setForeground(Color.GREEN);
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
        scrollPane.setBounds(50, 250, 700, 200);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(scrollPane);

        // Add files to the filesPanel
        addFile("Welcome Home.mp4", "File Size: 34MB", 0);
        addFile("Website logo.png", "File Size: 334KB", 1);

        // Add more files as needed...

        cap3Label = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 15);
        cap3Label.setText("Upload a new file to the Cloud");
        cap3Label.setBounds(270, 450, 700, 100);
        cap3Label.setFont(f);
        c.add(cap3Label);

        bt1 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 15);
        bt1.setText("Choose");
        bt1.setBounds(150, 540, 100, 30);
        bt1.setFont(f);
        c.add(bt1);

        cap4Label = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 15);
        cap4Label.setText("You have choosen index.html");
        cap4Label.setBounds(280, 510, 700, 100);
        cap4Label.setFont(f);
        c.add(cap4Label);

        bt2 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        bt2.setText("UPLOAD");
        bt2.setBounds(300, 600, 180, 35);
        bt2.setBackground(Color.BLUE);
        bt2.setForeground(Color.WHITE);
        bt2.setFont(f);
        c.add(bt2);
    }

    // Method to add a file entry to the filesPanel
    private void addFile(String fileName, String fileSize, int index) {
        JLabel fileLabel = new JLabel(fileName);
        fileLabel.setBounds(100, 30 + index * 80, 150, 40);
        filesPanel.add(fileLabel);

        JLabel file2Label = new JLabel(fileSize);
        file2Label.setBounds(100, 60 + index * 80, 150, 40);
        filesPanel.add(file2Label);

        JButton downloadButton = new JButton("Download");
        downloadButton.setBounds(400, 60 + index * 80, 100, 30);
        downloadButton.setBackground(Color.GREEN);
        downloadButton.setForeground(Color.BLACK);
        filesPanel.add(downloadButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(530, 60 + index * 80, 100, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        filesPanel.add(deleteButton);
       

    }

    public static void main(String[] args) {
        FileClientGUI2 frame = new FileClientGUI2();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
    }
}


///////////////////

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class FileClientGUI2 extends JFrame {
    private Container c;
    private Font f;
    private JLabel capLabel, cap2Label, cap3Label, cap4Label;
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel, connectLabel, connectedLabel;
    private JButton bt1, bt2;
    private JPanel filesPanel;
    private JTextField fileField;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String serverIP;
    private int port;
    private ArrayList<File> selectedFiles;

    // FileClientGUI2(String serverIP, int port) {
    //     this.serverIP = serverIP;
    //     this.port = port;
    //     this.selectedFiles = new ArrayList<>();
    //     initComponents();
    //     connectToServer();
    // }

    FileClientGUI2(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
        this.selectedFiles = new ArrayList<>();
        initComponents();
        connectToServer();
    }

    public void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Sky Vault file transfer Client");
        capLabel.setBounds(220, 1, 700, 100);
        capLabel.setFont(f);
        c.add(capLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(50, 100, 150, 40);
        c.add(serverLabel);

        servernumLabel = new JLabel(serverIP);
        servernumLabel.setBounds(110, 100, 150, 40);
        c.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        c.add(portLabel);

        portnumLabel = new JLabel(String.valueOf(port));
        portnumLabel.setBounds(140, 120, 150, 40);
        c.add(portnumLabel);

        connectLabel = new JLabel("Status:");
        connectLabel.setBounds(600, 120, 150, 40);
        c.add(connectLabel);

        connectedLabel = new JLabel("Connected");
        connectedLabel.setBounds(650, 120, 150, 40);
        connectedLabel.setForeground(Color.GREEN);
        c.add(connectedLabel);

        cap2Label = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        cap2Label.setText("Selected Files");
        cap2Label.setBounds(290, 150, 700, 100);
        cap2Label.setFont(f);
        c.add(cap2Label);

        filesPanel = new JPanel();
        filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));
        filesPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 200);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(scrollPane);

        cap3Label = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 15);
        cap3Label.setText("Upload new files to the Cloud");
        cap3Label.setBounds(270, 450, 700, 100);
        cap3Label.setFont(f);
        c.add(cap3Label);

        bt1 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 15);
        bt1.setText("Choose");
        bt1.setBounds(300, 540, 100, 30);
        bt1.setFont(f);
        bt1.addActionListener(e -> chooseFile());
        c.add(bt1);

        // cap4Label = new JLabel();
        // f = new Font("Times New Roman", Font.BOLD, 15);
        // cap4Label.setText("You have chosen: ");
        // cap4Label.setBounds(280, 510, 700, 100);
        // cap4Label.setFont(f);
        // c.add(cap4Label);

        bt2 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        bt2.setText("UPLOAD");
        bt2.setBounds(270, 600, 180, 35);
        bt2.setBackground(Color.BLUE);
        bt2.setForeground(Color.WHITE);
        bt2.setFont(f);
        bt2.addActionListener(e -> uploadFiles());
        c.add(bt2);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                selectedFiles.add(file);
                JLabel fileLabel = new JLabel(file.getName());
                // JButton downloadButton = new JButton("Download");
                // JButton deleteButton = new JButton("Delete");

                JButton downloadButton = new JButton("Download");
                downloadButton.setBackground(Color.BLUE);
                downloadButton.setForeground(Color.WHITE);

                JButton deleteButton = new JButton("Delete");
                deleteButton.setBackground(Color.RED);
                deleteButton.setForeground(Color.WHITE);

                // // Add action listener for download button
                // downloadButton.addActionListener(e -> downloadFile(file.getName()));

                // // Add action listener for delete button
                // deleteButton.addActionListener(e -> deleteFile(file.getName()));



                downloadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Implement download functionality here
                        // Example: downloadFile(fileName);
                        JOptionPane.showMessageDialog(null, "Downloading file: ");
                    }
                });
        
    
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the file?", "Delete File", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            // Implement delete functionality here
                            // Example: deleteFile(fileName);
                            JOptionPane.showMessageDialog(null, "Deleting file: ");
                        }
                    }
                });
                    

                // Create a panel to hold the file label, download button, and delete button
                JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                filePanel.add(fileLabel);
                filePanel.add(downloadButton);
                filePanel.add(deleteButton);

                filesPanel.add(filePanel);
            }
            filesPanel.revalidate();
            filesPanel.repaint();
        }
    }

    

    private void uploadFiles() {
        if (!selectedFiles.isEmpty()) {
            try (Socket socket = new Socket(serverIP, port);
                 DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
    
                for (File file : selectedFiles) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        dos.writeUTF(file.getName());
                        dos.writeLong(file.length());
    
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please choose at least one file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private void connectToServer() {
        try {
            socket = new Socket(serverIP, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            connectedLabel.setText("Connected");
        } catch (IOException e) {
            connectedLabel.setText("Disconnected");
            connectedLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }

    private void downloadFile(String fileName) {
        // Implement download file functionality here
    }

    private void deleteFile(String fileName) {
        // Implement delete file functionality here
    }

    public static void main(String[] args) {
        FileClientGUI2 frame = new FileClientGUI2("localhost", 3432); // Dummy IP and port for testing
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
    }
}


