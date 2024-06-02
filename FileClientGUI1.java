import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FileClientGUI1 extends JFrame {

    private Container c;
    private Font f;
    private JLabel capLabel;
    private JTextField tf1,tf2;
    private JLabel serverLabel, portLabel;
    private JButton bt1;
    private Cursor cr;



    FileClientGUI1() {
        initComponents();
    }

    public void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);


        capLabel = new JLabel();
        f = new Font ("Times New Roman",Font.BOLD,25);
        capLabel.setText("Sky Vault file transfer Client");
        // capLabel.setBounds(520,50,700,100);

        capLabel.setBounds(220,50,700,100);
        capLabel.setFont(f); 
        c.add(capLabel); 
        
        capLabel = new JLabel();
        f = new Font ("Times New Roman",Font.BOLD,25);
        capLabel.setText("Connect to a server");
        // capLabel.setBounds(570,150,700,100);
        capLabel.setBounds(270,100,700,100);
        capLabel.setFont(f); 
        c.add(capLabel); 



        serverLabel = new JLabel("Server IP:");
        // serverLabel.setBounds(450, 280, 150, 40);
        serverLabel.setBounds(250, 250, 150, 40);
        c.add( serverLabel);

        tf1 = new JTextField();
        // tf1.setBounds(600, 280, 280, 40);
        tf1.setBounds(350, 250, 280, 40);
        tf1.setForeground(Color.GRAY);
        c.add(tf1);

        portLabel = new JLabel("PORT Address:");
        // portLabel.setBounds(450, 340, 150, 40);
        portLabel.setBounds(250, 300, 150, 40);
        c.add(portLabel);

        tf2 = new JTextField();
        // tf2.setBounds(600, 340, 280, 40);
        tf2.setBounds(350, 300, 280, 40);
        tf2.setForeground(Color.GRAY);
        c.add(tf2);


        Font poppinsFont = new Font("Poppins", Font.BOLD, 10);

        bt1 = new JButton();
        // f = new Font ("Times New Roman",Font.BOLD,20);
        bt1.setText ("Connect");
        // bt1.setBounds(620,450,200,50);
        bt1.setBounds(320,400,200,40);
        bt1.setBackground(Color.GREEN); 
        bt1.setForeground(Color.WHITE);
        bt1.setFont(poppinsFont);
        bt1.setFont(f);
        bt1.setCursor(cr);
        c.add(bt1);




        bt1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e)
            {
                  
            dispose() ;    

            FileClientGUI2 frame = new FileClientGUI2();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // frame.setBounds(0, 0, 1520, 850);
            frame.setBounds(0, 0, 800, 700);
            frame.setTitle("Sky Vault file transfer");
            frame.setResizable(false);
                
        }
         
        });
     
    }


    public static void main(String[] args) {
        FileClientGUI1 frame = new FileClientGUI1();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setBounds(0, 0, 1520, 850);
        frame.setBounds(0, 0, 800, 600);
        frame.setTitle("Sky Vault file transfer");

        frame.setResizable(false);
    }


    
}


//////////////////////////////



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;


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

        capLabel = new JLabel();
        f = new Font("Times New Roman", Font.BOLD, 25);
        capLabel.setText("Sky Vault file transfer Client");
        capLabel.setBounds(220, 50, 700, 100);
        capLabel.setFont(f);
        c.add(capLabel);

        capLabel = new JLabel();
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
                String serverIP = tf1.getText();
                int port = Integer.parseInt(tf2.getText());

                // Attempt to connect to the server
                try {
                    // Create a socket to connect to the server
                    Socket socket = new Socket(serverIP, port);
                    
                    // If the connection is successful, proceed to GUI2
                    FileClientGUI2 frame = new FileClientGUI2(serverIP, port);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(0, 0, 800, 700);
                    frame.setTitle("Sky Vault file transfer");
                    frame.setResizable(false);

                    // Close the current GUI1 window
                    dispose();
                } catch (IOException ex) {
                    // If connection fails, show an error message
                    JOptionPane.showMessageDialog(c, "Failed to connect to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
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


