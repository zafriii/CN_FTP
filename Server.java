import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
    private final int port;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private final ConcurrentHashMap<ClientHandler, Boolean> clients = new ConcurrentHashMap<>();

    public Server(int port) {
        this.port = port;
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

    public void clientDisconnected(ClientHandler handler) {
        clients.remove(handler);
    }

    public int getClientCount() {
        return clients.size();
    }
}
