rimport javax.swing.JButton;
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


