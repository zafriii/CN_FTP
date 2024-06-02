import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class FileClientGUI1 extends JFrame {

    private Container c;
    private Font f;
    private JLabel capLabel;
    private JTextField tf1, tf2;
    private JLabel serverLabel, portLabel;
    private JButton bt1;
    private Cursor cr;

    FileClientGUI1() {
        initComponents();
    }

    public void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);

        ConnectionManager connectionManager = new ConnectionManager();

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Sky Vault file transfer Client");

        capLabel.setBounds(220, 50, 700, 100);
        capLabel.setFont(f);
        c.add(capLabel);

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Connect to a server");
        capLabel.setBounds(270, 100, 700, 100);
        capLabel.setFont(f);
        c.add(capLabel);

        serverLabel = new JLabel("Server IP:");
        serverLabel.setBounds(250, 250, 150, 40);
        c.add(serverLabel);

        tf1 = new JTextField();
        tf1.setBounds(350, 250, 280, 40);
        tf1.setForeground(Color.GRAY);
        c.add(tf1);

        portLabel = new JLabel("PORT Address:");
        portLabel.setBounds(250, 300, 150, 40);
        c.add(portLabel);

        tf2 = new JTextField();
        tf2.setBounds(350, 300, 280, 40);
        tf2.setForeground(Color.GRAY);
        c.add(tf2);

        Font poppinsFont = new Font("Poppins", Font.BOLD, 10);

        bt1 = new JButton();
        bt1.setText("Connect");
        bt1.setBounds(320, 400, 200, 40);
        bt1.setBackground(Color.GREEN);
        bt1.setForeground(Color.WHITE);
        bt1.setFont(poppinsFont);
        bt1.setFont(f);
        bt1.setCursor(cr);
        c.add(bt1);

        bt1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String ip = tf1.getText();
                    int port = Integer.parseInt(tf2.getText());
                    connectionManager.connect(ip, port);
                    FileClientGUI2 frame = new FileClientGUI2(connectionManager);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(0, 0, 800, 700);
                    frame.setTitle("Sky Vault File Transer");
                   // frame.setResizable(true);
                    dispose();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(FileClientGUI1.this, "Failed to connect");
                }

            }

        });

    }

    public static void main(String[] args) {
        FileClientGUI1 frame = new FileClientGUI1();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 600);
        frame.setTitle("Sky Vault file transfer");

        frame.setResizable(false);
    }

}
