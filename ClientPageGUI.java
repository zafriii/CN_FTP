import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

public class ClientPageGUI extends JFrame {

    private Container container;
    private Font f;
    private JLabel titleLabel, tableTitleLabel, formTitleLabel, fileNameLabel;
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel, statusLabelKey, statusLabel;
    private JButton chooseFileButton, uploadFileButton;
    private JPanel filesPanel;
    private Client client;
    private ConnectionUpdater connectionUpdater;
    private int fileCounter;

    ClientPageGUI(Client client) {
        this.client = client;
        client.start();
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

        servernumLabel = new JLabel(client.getServerIP());
        servernumLabel.setBounds(110, 100, 150, 40);
        container.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        container.add(portLabel);

        portnumLabel = new JLabel(Integer.toString(client.getPort()));
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
        filesPanel.setLayout(new GridLayout(0, 1));
        filesPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 200);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(scrollPane);

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

        connectionUpdater = new ConnectionUpdater();
        connectionUpdater.execute();
        ;
    }

    // Method to add a file entry to the filesPanel
    private void addFile(String fileName, int index) {
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.CENTER));
    

        JLabel fileLabel = new JLabel(fileName);
        jp.add(fileLabel);

        JButton downloadButton = new JButton("Download");
        downloadButton.setBounds(400, 30, 100, 30);
        downloadButton.setBackground(Color.GREEN);
        downloadButton.setForeground(Color.BLACK);
        jp.add(downloadButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(530, 30, 100, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        jp.add(deleteButton);

        filesPanel.add(jp);

        filesPanel.revalidate(); 
        filesPanel.repaint();
    
    }

    private class ConnectionUpdater extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            while (!isCancelled()) {
                int isConnected = client.getConnectionStatus() ? 1 : 0;
                publish(isConnected);
                updateFileList();
                Thread.sleep(10000);
            }
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int currStatus = chunks.get(chunks.size() - 1);
            if (currStatus == 1) {
                statusLabel.setText("Connected");
                statusLabel.setForeground(Color.GREEN);
            } else {
                statusLabel.setText("Disconnected");
                statusLabel.setForeground(Color.RED);
            }
        }
    }

    private void updateFileList() {
        ArrayList<String> files = client.getFileList();

        fileCounter = 1; 

        filesPanel.removeAll();
        filesPanel.revalidate();
        filesPanel.revalidate();

        for (int i = 0; i < files.size(); i++) {
            addFile(files.get(i), i);
        }
    }

    // public static void main(String[] args) {
    // ClientLogin cl = new ClientLogin();
    // cl.connectServer("45.23.56.23", 3232);
    // ClientPageGUI frame = new ClientPageGUI(cl);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setBounds(0, 0, 800, 700);
    // frame.setTitle("Sky Vault file transfer");
    // frame.setResizable(false);
    // frame.setVisible(true);
    // }
}
