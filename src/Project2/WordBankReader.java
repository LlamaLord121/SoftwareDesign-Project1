package Project2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordBankReader {
    public static String getWord(int letterCount) {
        try {
            //changed from earlier WordBankReader to attempt to help with some read errors. May not be entirely needed
            InputStream inputStream = WordBankReader.class.getResourceAsStream("/WordBank.txt");

            if (inputStream == null) {
                System.out.println("WordBank.txt not found");
                return null;
            }

            Scanner scanner = new Scanner(inputStream);

            String lengthHeader = "-" + letterCount;
            boolean foundHeader = false;
            ArrayList<String> words = new ArrayList<>();

            while(scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (currentLine.startsWith("-")) {
                    if(foundHeader) {
                        break;
                    } else if (currentLine.equals(lengthHeader)) {
                        foundHeader = true;
                    }
                } else if (foundHeader && !currentLine.trim().isEmpty()) {
                    words.add(currentLine.trim());
                }
            }

            scanner.close();

            if (words.isEmpty()) {
                return null;
            }

            Random random = new Random();
            return words.get(random.nextInt(words.size()));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}