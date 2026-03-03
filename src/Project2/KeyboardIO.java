package Project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class KeyboardIO implements ActionListener {

    private Map<String, JButton> keyMap = new HashMap<>();
    private JLabel wordDisplay;
    private JTextArea guessLog;
    private String currentWord = "";

    // Base key - standard size

    class GeneralKey extends JButton {
        public GeneralKey(String label) {
            super(label);
            setPreferredSize(new Dimension(45, 50));
            setOpaque(true);
        }
    }

    // Custom key (Enter/Backspace) - can override width

    class CustomKey extends GeneralKey {
        public CustomKey(String label, int width) {
            super(label);
            setPreferredSize(new Dimension(width, 50));
        }
    }

    public static void main(String[] args) {
        KeyboardIO keyboard = new KeyboardIO();
        keyboard.go();
    }

    // Call this from anywhere to push a new line into the scrolling box
    public void logMessage(String message) {
        if (guessLog.getDocument().getLength() == 0) {
            guessLog.append(message);
        } else {
            guessLog.append("\n" + message);
        }
        guessLog.setCaretPosition(guessLog.getDocument().getLength());
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Guess log

        guessLog = new JTextArea();
        guessLog.setFont(new Font("Arial", Font.PLAIN, 24));
        guessLog.setEditable(false);
        guessLog.setLineWrap(true);
        guessLog.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(guessLog);
        scrollPane.setMaximumSize(new Dimension(500, 160));
        scrollPane.setPreferredSize(new Dimension(500, 160));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);

        // Word display

        wordDisplay = new JLabel("", SwingConstants.CENTER);
        wordDisplay.setFont(new Font("Arial", Font.BOLD, 32));
        wordDisplay.setMaximumSize(new Dimension(500, 60));
        wordDisplay.setPreferredSize(new Dimension(500, 60));
        wordDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        wordDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(wordDisplay);

        // Keyboard

        String[] rows = {"QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM"};

        for (String row : rows) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            rowPanel.setMaximumSize(new Dimension(500, 54));
            rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            for (char c : row.toCharArray()) { // Build the keyboard button array
                GeneralKey btn = new GeneralKey(String.valueOf(c));
                btn.addActionListener(this);
                rowPanel.add(btn);
                keyMap.put(String.valueOf(c), btn);
            }
            mainPanel.add(rowPanel);
        }

        // Enter and Backspace on their own row, initialized separately so they can't be locked or colored

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        actionRow.setMaximumSize(new Dimension(500, 54));
        actionRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomKey enterBtn = new CustomKey("ENTER", 90); // 2x normal width
        enterBtn.addActionListener(this);

        CustomKey backBtn = new CustomKey("⌫", 55);
        backBtn.addActionListener(this);

        actionRow.add(enterBtn);
        actionRow.add(backBtn);
        mainPanel.add(actionRow);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String pressed = ((JButton) event.getSource()).getText();

        if (pressed.equals("ENTER")) {
            if (!currentWord.isEmpty()) {
                logMessage(currentWord);
                System.out.println("Submit guess: " + currentWord);
                currentWord = "";
            }
            // Logic Hook
        } else if (pressed.equals("⌫")) {
            if (!currentWord.isEmpty()) {
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            }
            // Logic Hook
        } else {
            currentWord += pressed;
            // Logic Hook
        }

        wordDisplay.setText(currentWord);
    }

    // Use to color the keys when they get locked
    public void colorKey(String letter, Color color) {
        if (keyMap.containsKey(letter)) {
            keyMap.get(letter).setBackground(color);
        }
    }
}