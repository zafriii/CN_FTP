import javax.naming.ldap.SortKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.InternalFrameAdapter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FileServer extends JFrame {

    private Container c;
    private Font f;
    private JLabel capLabel, cap2Label;
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel, connectLabel, deviceNumberLabel,
            connectedLabel;
    private JButton bt1;

    private JPanel filesPanel;

    private String serverIP;
    private int port = 3121;
    private Boolean status;
    private int connectedDevices;
    private ArrayList<String> availableFiles;
    private boolean serverRunning = false;
    private ServerSocket serverSocket;

    FileServer() {
        status = false;
        connectedDevices = 0;
        availableFiles = new ArrayList<>();
        availableFiles.add("FirstFile.cpp");
        availableFiles.add("SPringframe.txt");

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
        serverLabel.setBounds(50, 100, 200, 40);
        c.add(serverLabel);

        servernumLabel = new JLabel("354.53.34.34");
        servernumLabel.setBounds(120, 100, 150, 40);
        c.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        c.add(portLabel);

        portnumLabel = new JLabel(Integer.toString(port));
        portnumLabel.setBounds(160, 120, 150, 40);
        c.add(portnumLabel);

        connectLabel = new JLabel("Status:");
        connectLabel.setBounds(600, 120, 150, 40);
        c.add(connectLabel);

        connectedLabel = new JLabel("Stopped");
        connectedLabel.setBounds(650, 120, 150, 40);
        connectedLabel.setForeground(Color.RED);
        c.add(connectedLabel);

        connectLabel = new JLabel("Device Connected:");
        connectLabel.setBounds(600, 150, 150, 40);
        c.add(connectLabel);

        deviceNumberLabel = new JLabel(Integer.toString(connectedDevices));
        deviceNumberLabel.setBounds(740, 150, 150, 40);
        deviceNumberLabel.setForeground(Color.BLUE);
        c.add(deviceNumberLabel);

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
        // addFile("Welcome Home.mp4", "File Size: 34MB", 0);
        // addFile("Website logo.png", "File Size: 334KB", 1);

        // Add more files as needed...

        bt1 = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        bt1.setText("START");
        bt1.setBounds(300, 600, 180, 35);
        bt1.setBackground(Color.GREEN);
        bt1.setForeground(Color.BLACK);
        bt1.setFont(f);
        c.add(bt1);

        bt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!serverRunning) {
                    try {
                        serverSocket = new ServerSocket(port);
                        startServer(scrollPane, serverSocket);
                        System.out.println("Server running on port: " + port);
                        serverRunning = true;

                    } catch (IOException ie) {
                        JOptionPane.showMessageDialog(scrollPane, "Couldn't start the server");
                    }
                } else {
                    try {
                        serverSocket.close();
                        System.out.println("Server stopped");
                        serverRunning = false;
                        bt1.setText("Start");
                        bt1.setBackground(Color.GREEN);
                        connectedLabel.setText("Stopped");
                        connectedLabel.setForeground(Color.RED);

                    } catch (IOException ie) {

                    }
                }
            }
        });

    }

    private void startServer(JScrollPane scrollPane, ServerSocket serverSocket) {
        bt1.setText("Stop");
        bt1.setBackground(Color.RED);
        connectedLabel.setText("Running");
        connectedLabel.setForeground(Color.GREEN);

        Thread serverThread = new Thread(() -> {
            while (true) {
                if (!serverRunning)
                    break;
                try {

                    Socket socket = serverSocket.accept();
                    System.out.println("New server connected");
                    connectedDevices++;
                    deviceNumberLabel.setText(Integer.toString(connectedDevices));

                    // sendFileList(socket, scrollPane);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(scrollPane, "An error occured.");
                }
            }

        });

        serverThread.start();

    }

    private void sendFileList(Socket socket, JScrollPane scrollPane) {

        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeInt(availableFiles.size());

            for (String s : availableFiles) {
                byte[] stringBytes = s.getBytes();
                dataOutputStream.writeInt(stringBytes.length);
                dataOutputStream.write(stringBytes);
            }

            int fileNameLength = dataInputStream.readInt();

            if (fileNameLength > -1) {
                byte[] fileNameBytes = new byte[fileNameLength];
                dataInputStream.readFully(fileNameBytes, -1, fileNameBytes.length);
                String fileName = new String(fileNameBytes);

                int fileContentLength = dataInputStream.readInt();

                if (fileContentLength > -1) {
                    byte[] fileContentBytes = new byte[fileContentLength];
                    dataInputStream.readFully(fileContentBytes, -1, fileContentLength);

                    try {
                        File newFile = new File("files/" + fileName);
                        FileOutputStream fo = new FileOutputStream(newFile);
                        fo.write(fileContentBytes);
                        fo.close();

                        availableFiles.add(fileName);
                        addFile(fileName, "2MB", 1);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(scrollPane, "Couldnot receive file.");
                        ;
                    }
                }
            }
        } catch (IOException ie) {
            JOptionPane.showMessageDialog(scrollPane, "An error occured.");
        }

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


    // Enlist all the files in the files directory
    private void getFiles() {
        String directoryPath = "files";
        File directory = new File(directoryPath);
    
    
        if (directory.exists() && directory.isDirectory()) {
    
          ArrayList<String> filenames = new ArrayList<>();
          File[] files = directory.listFiles();
    
          for (File file : files) {
            availableFiles.add(file.getName());
          }
    
        } else {
          System.out.println("Error: Directory not found or not readable.");
        }
    }

    public static void main(String[] args) {
        FileServer frame = new FileServer();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
