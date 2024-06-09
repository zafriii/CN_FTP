import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientLoginGUI extends JFrame {

    private Container container;
    private Font f;
    private JLabel titleLabel;
    private JLabel formHeaderLabel;
    private JTextField serverIPField, portField;
    private JLabel serverLabel, portLabel;
    private JButton connectButton;

    ClientLoginGUI() {
        initComponents();
    }

    public void initComponents() {
        container = this.getContentPane();
        container.setLayout(null);

        titleLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        titleLabel.setText("SkyVault file transfer Client");

        titleLabel.setBounds(220, 50, 700, 100);
        titleLabel.setFont(f);
        container.add(titleLabel);

        formHeaderLabel = new JLabel();
        formHeaderLabel.setText("Connect to a server");
        formHeaderLabel.setBounds(270, 100, 700, 100);
        formHeaderLabel.setFont(f);
        container.add(formHeaderLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(250, 250, 150, 40);
        container.add(serverLabel);

        serverIPField = new JTextField();
        serverIPField.setBounds(350, 250, 280, 40);
        serverIPField.setForeground(Color.GRAY);
        container.add(serverIPField);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(250, 300, 150, 40);
        container.add(portLabel);

        portField = new JTextField();
        portField.setBounds(350, 300, 280, 40);
        portField.setForeground(Color.GRAY);
        container.add(portField);

        connectButton = new JButton();
        connectButton.setText("Connect");
        connectButton.setBounds(320, 400, 200, 40);
        connectButton.setBackground(Color.GREEN);
        connectButton.setForeground(Color.WHITE);
        connectButton.setFont(f);
        container.add(connectButton);

        connectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String ip = serverIPField.getText();
                int port = Integer.parseInt(portField.getText());

                ClientLogin clientLogin = new ClientLogin();
                boolean connection = clientLogin.connectServer(ip, port);

                if (!connection) {
                    JOptionPane.showMessageDialog(container, "Couldn't connect to the server");
                    return;
                }

                dispose();

                ClientPageGUI clientPageGUI = new ClientPageGUI(clientLogin);
                clientPageGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientPageGUI.setTitle("Skyvault Client");
                clientPageGUI.setBounds(0, 0, 800, 700);
                clientPageGUI.setVisible(true);

            }

        });

    }

    public static void main(String[] args) {
        ClientLoginGUI frame = new ClientLoginGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 600);
        frame.setTitle("Sky Vault file transfer");
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
