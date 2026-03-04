package Project2;

import java.io.*;
import java.net.Socket;

public class ServerSession {

    private final Socket socket;
    private GameLogic game;

    private static final int WORDLEN = 5;

    public ServerSession(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
            Socket s = socket;
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true)
            //autoflush for simplicity
    ) {
            game = new GameLogic(WORDLEN);

            out.println("Welcome to Fake Wordle");
            out.println("Word length: " + WORDLEN);

            String line;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    handleGuess(line, out);
                }
            }

        } catch (IOException e) {
            System.out.println("Session ended");
        }
    }

    private void handleGuess(String guessRaw, PrintWriter out) {
        String guess = guessRaw.toLowerCase();

        //flexible to potential changes to WORDLEN
        if (guess.length() != WORDLEN) {
            out.println("Error - Your guess must be " + WORDLEN + " letters long");
            return;
        }

        if (!guess.matches("[a-z]+")) {
            out.println("Error - Invalid characters / symbols. Only A through Z allowed.");
            return;
        }

        GameLogic.GuessResult r = game.evaluateGuess(guess);

        out.println("RESULT " + guess
                + " correctLetters=" + r.correctLetters
                + " correctPositions=" + r.correctPositions
                + " victory=" + r.victory);
    }
}