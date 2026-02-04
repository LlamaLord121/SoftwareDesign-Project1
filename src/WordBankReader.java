import java.io.File;
import java.util.Scanner;
import java.util.Random;

/**
 * Word bank is designed so thaa multiple internal banks of varying
 * word lengths can be chosen from. This is to allow for easy expansion
 * into a program that can pick from a variety of word lengths.
 *
 * Each "bank" has a header formatted as follows: -(word length)
 * ex: -5 would preface a 5 letter length section
 */

public class WordBankReader {
    public static String getWord(int letterCount) {
        try { // Opening a file with scanner, wrapping it in a basic try/except block.
            File wordBank = new File("Src/WordBank.txt");
            Scanner scanner = new Scanner(wordBank);

            String lengthHeader = "-" + letterCount;
            boolean foundHeader = false;
            int wordCount = 0;

            while(scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (currentLine.startsWith("-")) {
                    if(foundHeader) {
                        break;
                    } else if (currentLine.equals(lengthHeader)) {
                        foundHeader = true;
                    }
                } else if (foundHeader && !currentLine.trim().isEmpty()) {
                    wordCount += 1;
                }
            }

            scanner.close();

            if (wordCount == 0) {
                return null;
            }

            Random random = new Random();
            int randomWord = random.nextInt(wordCount);

            scanner = new Scanner(wordBank);
            foundHeader = false;
            int currentIndex = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("-")) {
                    if (foundHeader) {
                        break;
                    }
                    if (line.equals(lengthHeader)) {
                        foundHeader = true;
                    }
                }
                else if (foundHeader && !line.trim().isEmpty()) {
                    if (currentIndex == randomWord) {
                        scanner.close();
                        return line.trim();
                    }
                    currentIndex++;
                }
            }

            scanner.close();
            return null;

        } catch (Exception e) { // Basic template error reporting format
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}