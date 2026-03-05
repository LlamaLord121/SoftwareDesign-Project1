package Project1;

import Project2.KeyboardIO;

/*

 */
public class Main {
    public static void main(String[] args) {
        KeyboardIO keyboard = new KeyboardIO();
        keyboard.go();

        TerminalIO.output("--- Welcome to Cole and Kane's basic word game ---");
        while (true) {
            boolean gameResult = gameplayLoop(5, 0);

            if (!promptPlayAgain()) {
                break;
            }

            TerminalIO.output("\nStarting a new game\n");
        }
        TerminalIO.output("Thanks for playing our game!");
        TerminalIO.closeScanner();
    }

    private static boolean promptPlayAgain() {
        while (true) {
            System.out.print("\nWould you like to play again? (yes/no): ");
            String response = TerminalIO.scannerInput();

            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    private static boolean gameplayLoop(int wordLength, int attemptLimit) {
        GameLogic currentGame = new GameLogic(wordLength);

        String input = "";
        int attemptCount = 1;

        while (true) {
            input = TerminalIO.promptInput(attemptCount, wordLength);

            if (input.equals("exit")) {
                TerminalIO.output("Exiting Game. The correct word: " + currentGame.getGoalWord());
                break;
            }

            if (TerminalIO.validateInput(input, wordLength)) {
                attemptCount += 1;
                // Add attempt count checker here if you want to limit player attempts
            } else {
                TerminalIO.output("Invalid input");
                continue;
            }

            GameLogic.GuessResult guessResult = currentGame.evaluateGuess(input);

            if (guessResult.victory) {
                TerminalIO.output("You won! You correctly guessed " + currentGame.getGoalWord() + " in " + (attemptCount - 1) + " attempts.");
                return true;
            } else {
                TerminalIO.output(guessResult.toString());
            }
        }
        return false;
    }
}