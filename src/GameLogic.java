// Gameplay loop / reprompts logic assumed to live in main, likely makes more logical sense
//

public class GameLogic {
/*
 GameLogic
 * All of this is pending it actually working

 * Chooses goalWord word via WordBankReader.getWord(letterCount)
 
 * Compares a user guess to the goalWord and returns:
   correctLetters: total letters from guess present in goalWord without double counting
   correctPositions: letters in correct positions
   victory: true if all positions are correct

 * Handles duplicate letters correctly...I think?
*/

    private String goalWord;
    private int length;

    public static class GuessResult {
        public int correctLetters;
        public int correctPositions;
        public boolean victory;

        public GuessResult(int correctLetters, int correctPositions, boolean victory) {
            this.correctLetters = correctLetters;
            this.correctPositions = correctPositions;
            this.victory = victory;
        }

        public String toString() {
            return "GuessResult{correctLetters=" + correctLetters
                + ", correctPositions=" + correctPositions
                + ", victory=" + victory + "}";
        }
    }

    public GameLogic(int letterCount) {
        this.goalWord = WordBankReader.getWord(letterCount).toLowerCase();
        this.length = letterCount;
    }

    public int getWordLength() {
        return length;
    }

    public String getgoalWord() {
        return goalWord;
    }

    public GuessResult evaluateGuess(String guessRaw) {
        String guess = guessRaw.toLowerCase();
        int correctPositions = 0;
        int[] freq = new int[26];
        boolean[] matched = new boolean[length];

        for (int i = 0; i < length; i++) {
            char g = guess.charAt(i);
            char t = goalWord.charAt(i);
            if (g == t) {
                matched[i] = true;
                correctPositions++;
            } 
            else {
                int idx = t - 'a';
                if (idx >= 0 && idx < 26) {
                    freq[idx]++;
                }
            }
        }

    int inWordButWrongPos = 0;
      
    for (int i = 0; i < length; i++) {
        if (!matched[i]) {
        char g = guess.charAt(i);
        int idx = g - 'a';
        if (idx >= 0 && idx < 26 && freq[idx] > 0) {
            inWordButWrongPos++;
            freq[idx]--;
        }
    }
}

        int correctLetters = correctPositions + inWordButWrongPos;
        boolean victory = correctPositions == length;

        return new GuessResult(correctLetters, correctPositions, victory);
    }

    public boolean isVictory(String guessRaw) {
        return evaluateGuess(guessRaw).victory;
    }
}
