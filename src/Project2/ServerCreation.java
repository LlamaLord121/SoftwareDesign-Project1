package Project2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCreation {

    public static final int LOCALPORT = 5000;

    public static void main(String[] args) {
        System.out.println("Server starting locally");

        try (ServerSocket serverSocket = new ServerSocket(LOCALPORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                ServerSession session = new ServerSession(clientSocket);
                session.run();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}