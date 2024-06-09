import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTemp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3232);

        while (true) {
            Socket socket = serverSocket.accept();
        }
    }
}
