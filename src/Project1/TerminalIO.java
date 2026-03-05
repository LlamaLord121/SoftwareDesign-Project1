package Project1;

import java.util.Scanner;

public class TerminalIO {
    private static Scanner scanner = new Scanner(System.in);

    public static String promptInput(int attemptCount, int letterCount) {
        System.out.print("[Attempt " + attemptCount + "] Enter a " + letterCount + " letter word (Type exit to exit): ");
        return scannerInput();
    }

    public static String scannerInput() {
        String word = scanner.nextLine();
        return word.trim().toLowerCase();
    }

    public static void output(String output) {
        System.out.println(output);
    }

    public static boolean validateInput(String word, int length) {
        if (word.length() != length || !word.matches("[a-z]+")) { // Found a simple method to check for legal characters
            return false;
        }
        return true;
    }

    public static void closeScanner() {
        scanner.close();
    }
}
