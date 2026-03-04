package Project2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class ClientNetworking {

    private KeyboardIO ui;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientNetworking(KeyboardIO ui) {
        this.ui = ui;
    }

    public void connectLocal() throws IOException {

        socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", ServerCreation.LOCALPORT));

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        Thread reader = new Thread(new Runnable() {
            public void run() {
                try {
                    String line;

                    while ((line = in.readLine()) != null) {
                        String msg = line.trim();

                        if (!msg.isEmpty()) {
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    ui.logMessage(msg);
                                }
                            });
                        }
                    }

                } catch (IOException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            ui.logMessage("Connection closed");
                        }
                    });
                }
            }
        });

        reader.start();
    }

    public void sendGuess(String guess) {
        if (out != null) {
            out.println(guess);
        }
    }

}