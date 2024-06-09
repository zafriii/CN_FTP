import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
    private final int port;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private final String FILES_DIR = "files";
    private ArrayList<String> fileList;
    private final ConcurrentHashMap<ClientHandler, Boolean> clients = new ConcurrentHashMap<>();

    public Server(int port) {
        this.port = port;
        fileList = new ArrayList<>();
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (ClientHandler client : clients.keySet()) {
                client.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            System.out.println("Server is listening on port " + port);

            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");
                    ClientHandler handler = new ClientHandler(socket, this);
                    clients.put(handler, true);
                    new Thread(handler).start();
                } catch (IOException e) {
                    if (running) {
                        System.out.println("Server exception: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void readFileList() {

        File dir = new File(FILES_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        String[] files = dir.list();
        if (files != null && files.length > 0) {
            for (String fileName : files) {
                fileList.add(fileName);
            }
        }

    }

    public void clientDisconnected(ClientHandler handler) {
        clients.remove(handler);
    }

    public int getClientCount() {
        return clients.size();
    }

    public ArrayList<String> getFileList() {
        if (fileList.size() > 0)
            fileList.clear();
        readFileList();
        return fileList;
    }
}
