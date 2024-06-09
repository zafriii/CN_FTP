import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

public class ClientPageGUI extends JFrame {

    private Container container;
    private Font f;
    private JLabel titleLabel, tableTitleLabel, formTitleLabel, fileNameLabel;
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel, statusLabelKey, statusLabel;
    private JButton chooseFileButton, uploadFileButton;

    private JPanel filesPanel;
    private ClientLogin clientLogin;

    ClientPageGUI(ClientLogin clientLogin) {
        this.clientLogin = clientLogin;
        initComponents();
    }

    public void initComponents() {
        container = this.getContentPane();
        container.setLayout(null);

        titleLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        titleLabel.setText("Sky Vault file transfer Client");
        titleLabel.setBounds(220, 1, 700, 100);
        titleLabel.setFont(f);
        container.add(titleLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(50, 100, 150, 40);
        container.add(serverLabel);

        servernumLabel = new JLabel(clientLogin.getServerIP());
        servernumLabel.setBounds(110, 100, 150, 40);
        container.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        container.add(portLabel);

        portnumLabel = new JLabel(Integer.toString(clientLogin.getPort()));
        portnumLabel.setBounds(140, 120, 150, 40);
        container.add(portnumLabel);

        statusLabelKey = new JLabel("Status:");
        statusLabelKey.setBounds(600, 120, 150, 40);
        container.add(statusLabelKey);

        statusLabel = new JLabel("Connected");
        statusLabel.setBounds(650, 120, 150, 40);
        statusLabel.setForeground(Color.GREEN);
        container.add(statusLabel);

        tableTitleLabel = new JLabel();
        tableTitleLabel.setText("Available Files");
        tableTitleLabel.setBounds(290, 150, 700, 100);
        tableTitleLabel.setFont(f);
        container.add(tableTitleLabel);

        filesPanel = new JPanel();
        filesPanel.setLayout(null);
        filesPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 200);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane);

        addFile("Welcome Home.mp4", "File Size: 34MB", 0);
        addFile("Website logo.png", "File Size: 334KB", 1);

        formTitleLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 15);
        formTitleLabel.setText("Upload a new file to the Cloud");
        formTitleLabel.setBounds(270, 450, 700, 100);
        formTitleLabel.setFont(f);
        container.add(formTitleLabel);

        chooseFileButton = new JButton();
        chooseFileButton.setText("Choose");
        chooseFileButton.setBounds(150, 540, 100, 30);
        chooseFileButton.setFont(f);
        container.add(chooseFileButton);

        fileNameLabel = new JLabel();
        fileNameLabel.setText("Choose a file to upload");
        fileNameLabel.setBounds(280, 510, 700, 100);
        fileNameLabel.setFont(f);
        container.add(fileNameLabel);

        uploadFileButton = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        uploadFileButton.setText("UPLOAD");
        uploadFileButton.setBounds(300, 600, 180, 35);
        uploadFileButton.setBackground(Color.BLUE);
        uploadFileButton.setForeground(Color.WHITE);
        uploadFileButton.setFont(f);
        container.add(uploadFileButton);
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
        ClientLogin cl = new ClientLogin();
        cl.connectServer("45.23.56.23", 3232);
        ClientPageGUI frame = new ClientPageGUI(cl);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
