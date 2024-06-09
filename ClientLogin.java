import java.io.IOException;
import java.net.Socket;

public class ClientLogin {
    private Socket socket;
    private String serverIp;
    private int port;

    public boolean connectServer(String ip, int port) {
        this.serverIp = ip;
        this.port = port;
        try {
            socket = new Socket(ip, port);
            return true;
        } catch (IOException ie) {
            System.out.println("Error Occured while connecting to the server");
            return false;
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    public String getServerIP() {
        return serverIp;
    }

    public int getPort() {
        return port;
    }
}
