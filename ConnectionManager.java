import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionManager {
    private Socket socket; 
    private PrintWriter out; 
    private BufferedReader in; 

    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port); 
        out = new PrintWriter(socket.getOutputStream(), true); 
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }


    public void close() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
    }
    
}
