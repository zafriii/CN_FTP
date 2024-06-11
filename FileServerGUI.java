import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private FileListUpdater fileListUpdater;
    private ArrayList<String> fileList;

    FileServerGUI() {
        server = new Server(PORT);
        fileList = new ArrayList<>();
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
        filesPanel.setLayout(new GridLayout(0, 1));
        filesPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setBounds(50, 250, 700, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane);

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
                    fileListUpdater = new FileListUpdater();
                    fileListUpdater.execute();
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
    private void addFile(String fileName) {
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel fileLabel = new JLabel(fileName);
        jp.add(fileLabel);

        filesPanel.add(jp);
        filesPanel.revalidate();
        filesPanel.repaint();

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

    private class FileListUpdater extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            while (!isCancelled()) {
                fileList = server.getFileList();
                publish(1);
                Thread.sleep(5000);
            }
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            filesPanel.removeAll();
            filesPanel.revalidate();
            filesPanel.revalidate();
            for (String s : fileList) {
                addFile(s);
            }
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
