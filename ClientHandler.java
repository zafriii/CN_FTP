import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private static final String FILES_DIR = "files";
    private volatile boolean running = true; 

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            String message;
            while (running && (message = input.readLine()) != null) {
                try {
                    int requestType = Integer.parseInt(message);
                    if (requestType == 1) {
                        System.out.println("File req received");
                        sendFileList(output);
                    } else if (requestType == 2) {
                        receiveFile(input);
                    } else {
                        output.println("Invalid request type");
                    }
                } catch (NumberFormatException e) {
                    output.println("Invalid input, expected an integer");
                }
            }
        } catch (IOException e) {
            System.out.println("Client handler exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            server.clientDisconnected(this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendFileList(PrintWriter output) {
        File dir = new File(FILES_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            output.println("Files directory not found");
            output.println("END");
            return;
        }

        String[] files = dir.list();
        if (files != null && files.length > 0) {
            for (String fileName : files) {
                output.println(fileName);
            }
        } else {
            output.println("No files available");
        }
        output.println("END");  // Indicate the end of the file list
    }

    private void receiveFile(BufferedReader input) {
        try {
            String fileName = input.readLine();
            long fileSize = Long.parseLong(input.readLine());
            File file = new File(FILES_DIR, fileName);

            try (BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(file))) {
                char[] buffer = new char[1024];
                long bytesRead = 0;
                while (bytesRead < fileSize) {
                    int read = input.read(buffer, 0, (int) Math.min(buffer.length, fileSize - bytesRead));
                    if (read == -1)
                        break;
                    fileOutput.write(new String(buffer, 0, read).getBytes());
                    bytesRead += read;
                }
            }

            System.out.println("File received: " + fileName);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error receiving file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnect() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
