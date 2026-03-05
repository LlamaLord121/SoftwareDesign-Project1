package Project2;

import java.io.*;
import java.net.Socket;

public class ServerSession {

    private final Socket socket;
    private GameLogic game;
    private int attemptCount;

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
            out.println("Welcome to Fake Wordle\nRight click to disable (eliminated) keys, repeat to re-enable.");
            if (!startNewGame(out)) return;

            String line;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    handleGuess(line, out, in);
                }
            }

        } catch (IOException e) {
            System.out.println("Session ended");
        }
    }

    private boolean startNewGame(PrintWriter out) {
        game = new GameLogic(WORDLEN);
        attemptCount = 0;
        if (!game.isReady()) {
            out.println("Error - Could not load word bank. Please check WordBank.txt and restart.");
            return false;
        }
        out.println("Word length: " + WORDLEN);
        return true;
    }

    private void handleGuess(String guessRaw, PrintWriter out, BufferedReader in) throws IOException {
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

        attemptCount++;
        GameLogic.GuessResult r = game.evaluateGuess(guess);

        if (r.victory) {
            out.println("You guessed " + guess.toUpperCase() + " correctly in " + attemptCount + " attempt(s)!");
            out.println("Play again? (yes/no)");

            String response;
            while ((response = in.readLine()) != null) {
                response = response.trim().toLowerCase();
                if (response.equals("yes") || response.equals("y")) {
                    if (!startNewGame(out)) return;
                    break;
                } else if (response.equals("no") || response.equals("n")) {
                    out.println("Thanks for playing!");
                    return;
                } else {
                    out.println("Please enter yes or no");
                }
            }
        } else {
            out.println(guess.toUpperCase()
                    + " - Letters in word: " + r.correctLetters
                    + ", In correct position: " + r.correctPositions);
        }
    }
}