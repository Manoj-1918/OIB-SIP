import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int lowerBound = 1;
    private int upperBound = 100;
    private int attemptsAllowed = 7;
    private int numberToGuess;
    private int attempts;
    private int totalScore = 0; // ✅ persistent score across rounds
    private Random rand = new Random();

    private JLabel messageLabel;
    private JTextField guessField;
    private JButton guessButton, playAgainButton;
    private JLabel attemptsLabel, roundScoreLabel, totalScoreLabel; // ✅ added totalScoreLabel

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game 🎮");
        setSize(800, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        messageLabel = new JLabel("I have chosen a number between 1 and 100. Guess it!", SwingConstants.CENTER);
        guessField = new JTextField();
        guessButton = new JButton("Guess");
        playAgainButton = new JButton("Play Again");
        attemptsLabel = new JLabel("Attempts left: " + attemptsAllowed, SwingConstants.CENTER);
        roundScoreLabel = new JLabel("Round Score: 0", SwingConstants.CENTER);
        totalScoreLabel = new JLabel("Total Score: 0", SwingConstants.CENTER); // ✅ persistent score label

        add(messageLabel);
        add(guessField);
        add(guessButton);
        add(attemptsLabel);
        add(roundScoreLabel);
        add(totalScoreLabel);
        add(playAgainButton);

        // initialize game
        startNewGame();

        // guess button action
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        // allow pressing Enter to guess
        guessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        // play again button
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        setVisible(true);
    }

    private void startNewGame() {
        numberToGuess = rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
        attempts = 0;
        messageLabel.setText("I have chosen a number between " + lowerBound + " and " + upperBound + ". Guess it!");
        attemptsLabel.setText("Attempts left: " + attemptsAllowed);
        roundScoreLabel.setText("Round Score: 0");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
    }

    private void handleGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            if (guess == numberToGuess) {
                int roundScore = (attemptsAllowed - attempts + 1) * 10;
                totalScore += roundScore; // ✅ add to total score

                messageLabel.setText("🎉 Correct! You guessed it in " + attempts + " attempts.");
                roundScoreLabel.setText("Round Score: " + roundScore);
                totalScoreLabel.setText("Total Score: " + totalScore); // ✅ update label
                endGame();
            } else if (guess < numberToGuess) {
                messageLabel.setText("Too low! Try again.");
            } else {
                messageLabel.setText("Too high! Try again.");
            }

            attemptsLabel.setText("Attempts left: " + (attemptsAllowed - attempts));

            if (attempts >= attemptsAllowed && guess != numberToGuess) {
                messageLabel.setText("😢 Out of attempts! Number was: " + numberToGuess);
                endGame();
            }

            guessField.setText("");
        } catch (NumberFormatException ex) {
            messageLabel.setText("❌ Please enter a valid number!");
        }
    }

    private void endGame() {
        guessButton.setEnabled(false);
        guessField.setEditable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGameGUI());
    }
}
