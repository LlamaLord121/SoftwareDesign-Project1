package Project2;

public class  GameLogic {

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
            String output = "Correct Letter(s): " + correctLetters
                    + "\nCorrect Letter(s) in Correct Position(s): "
                    + correctPositions;

            return output;
        }
    }

    public GameLogic(int letterCount) {
        String w = WordBankReader.getWord(letterCount);
        this.goalWord = (w != null) ? w.toLowerCase() : null;
        this.length = letterCount;
    }

    public boolean isReady() {
        return goalWord != null;
    }

    public int getWordLength() {
        return length;
    }

    public String getGoalWord() {
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