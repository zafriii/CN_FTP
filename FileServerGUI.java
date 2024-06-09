import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileServerGUI extends JFrame {
    private static final int PORT = 3232;
    private Server server;
    private Container container;
    private Font f;
    private JLabel titleLabel;
    private JLabel serverLabel, servernumLabel, portLabel, portnumLabel,
            statusLabelKey, statusLabel, deviceCountKey, deviceCountLabel, tableTitleLable;
    private JButton serverToggleButton;
    private JPanel filesPanel;
    private ClientCountUpdater clientCountUpdater;

    FileServerGUI() {
        server = new Server(PORT);
        initComponents();
    }

    public void initComponents() {
        container = this.getContentPane();
        container.setLayout(null);

        titleLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        titleLabel.setText("Sky Vault file transfer Server");
        titleLabel.setBounds(220, 1, 700, 100);
        titleLabel.setFont(f);
        container.add(titleLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(50, 100, 150, 40);
        container.add(serverLabel);

        servernumLabel = new JLabel("localhost");
        servernumLabel.setBounds(110, 100, 150, 40);
        container.add(servernumLabel);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(50, 120, 150, 40);
        container.add(portLabel);

        portnumLabel = new JLabel(Integer.toString(PORT));
        portnumLabel.setBounds(140, 120, 150, 40);
        container.add(portnumLabel);

        statusLabelKey = new JLabel("Status:");
        statusLabelKey.setBounds(600, 120, 150, 40);
        container.add(statusLabelKey);

        statusLabel = new JLabel("Stopped");
        statusLabel.setBounds(650, 120, 150, 40);
        statusLabel.setForeground(Color.RED);
        container.add(statusLabel);

        deviceCountKey = new JLabel("Device Connected:");
        deviceCountKey.setBounds(600, 150, 150, 40);
        container.add(deviceCountKey);

        deviceCountLabel = new JLabel("0");
        deviceCountLabel.setBounds(740, 150, 150, 40);
        deviceCountLabel.setForeground(Color.BLUE);
        container.add(deviceCountLabel);

        tableTitleLable = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        tableTitleLable.setText("Available Files");
        tableTitleLable.setBounds(290, 150, 700, 100);
        tableTitleLable.setFont(f);
        container.add(tableTitleLable);

        filesPanel = new JPanel();
        filesPanel.setLayout(null);
        filesPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane);

        addFile("Welcome Home.mp4", "File Size: 34MB", 0);
        addFile("Website logo.png", "File Size: 334KB", 1);

        serverToggleButton = new JButton();
        f = new Font("Times New Roman", Font.BOLD, 12);
        serverToggleButton.setText("Start Server");
        serverToggleButton.setBounds(300, 600, 180, 35);
        serverToggleButton.setBackground(Color.GREEN);
        serverToggleButton.setForeground(Color.BLACK);
        serverToggleButton.setFont(f);
        container.add(serverToggleButton);

        serverToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverToggleButton.getText().equals("Start Server")) {
                    server.start();
                    serverToggleButton.setText("Stop Server");
                    statusLabel.setText("Running");
                    statusLabel.setForeground(Color.GREEN);
                    serverToggleButton.setBackground(Color.RED);

                    clientCountUpdater = new ClientCountUpdater();
                    clientCountUpdater.execute();
                } else {
                    server.stop();
                    serverToggleButton.setText("Start Server");
                    statusLabel.setText("Stopped");
                    statusLabel.setForeground(Color.RED);
                    serverToggleButton.setBackground(Color.GREEN);
                }
            }
        });

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

    private class ClientCountUpdater extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            while (!isCancelled()) {
                int count = server.getClientCount();
                publish(count);
                Thread.sleep(1000); // Update every second
            }
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int latestCount = chunks.get(chunks.size() - 1);
            deviceCountLabel.setText("" + latestCount);
        }
    }

    public static void main(String[] args) {
        FileServerGUI frame = new FileServerGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 700);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
