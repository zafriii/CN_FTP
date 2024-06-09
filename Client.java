import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private final String hostname;
    private final int port;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private Timer connectionCheckTimer;
    private boolean connectionStatus;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() {
        startConnectionCheck();
    }

    public boolean connectToServer() throws IOException {
        socket = new Socket(hostname, port);
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to the server");
        return true;
    }

    private void startConnectionCheck() {
        connectionCheckTimer = new Timer(true);
        connectionCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isConnected()) {
                    connectionStatus = true;
                    requestFileList();
                } else {
                    connectionStatus = false;
                    System.out.println("Disconnected from server, trying to reconnect...");
                    reconnectToServer();
                }
            }
        }, 0, 10000); // Check connection every 10 seconds
    }

    public boolean isConnected() {
        try {
            socket.sendUrgentData(0xFF); // Send a TCP urgent byte to check the connection
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void reconnectToServer() {
        try {
            connectToServer();
        } catch (IOException e) {
            System.out.println("Reconnection attempt failed: " + e.getMessage());
        }
    }

    private void requestFileList() {
        try {
            output.println(1); // Send request type 1 for file list
            System.out.println("Requesting file list...");
            String response;
            while ((response = input.readLine()) != null && !response.equals("END")) {
                System.out.println("File: " + response);
            }
            System.out.println("End of file list");
        } catch (SocketException e) {
            System.out.println("Server connection lost during file list request");
        } catch (IOException e) {
            System.out.println("Error requesting file list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getServerIP() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public boolean getConnectionStatus() {
        return connectionStatus;
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        client.start();
    }
}
