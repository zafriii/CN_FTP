import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
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
    private ArrayList<String> fileList;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        fileList = new ArrayList<>();
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
        fileList.clear();
        try {
            output.println(1);
            System.out.println("Requesting file list...");
            String response;
            while ((response = input.readLine()) != null && !response.equals("END")) {
                System.out.println("File: " + response);
                fileList.add(response);
            }
            System.out.println("End of file list");
        } catch (SocketException e) {
            System.out.println("Server connection lost during file list request");
        } catch (IOException e) {
            System.out.println("Error requesting file list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendFile(File file) {
        try {
            // Send request type 2 for file transfer
            output.println(2);
            output.flush();

            // Send file name and file size
            output.println(file.getName());
            output.println(file.length());
            output.flush();

            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            // Send file data
            try (BufferedInputStream fileInput = new BufferedInputStream(fileInputStream);
                    OutputStream socketOutput = socket.getOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInput.read(buffer)) != -1) {
                    socketOutput.write(buffer, 0, bytesRead);
                }
                socketOutput.flush();
            }

            System.out.println("File sent: " + file.getName());

        } catch (IOException e) {
            System.out.println("Error sending file: " + e.getMessage());
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

    public ArrayList<String> getFileList() {
        return fileList;
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        client.start();
    }
}
