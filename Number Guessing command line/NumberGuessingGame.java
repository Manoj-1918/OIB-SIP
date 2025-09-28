import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    // Function to generate a random number
    static int generateNumber(int lower, int upper) {
        return rand.nextInt(upper - lower + 1) + lower;
    }

    // Function to play one round of the game
    static void playRound(int lower, int upper, int attemptsAllowed) {
        int numberToGuess = generateNumber(lower, upper);
        int attempts = 0;
        boolean guessed = false;

        System.out.println("\nI have chosen a number between " + lower + " and " + upper + ".");
        System.out.println("You have " + attemptsAllowed + " attempts to guess it.\n");

        while (attempts < attemptsAllowed) {
            System.out.print("Enter your guess: ");
            int guess = sc.nextInt();
            attempts++;

            if (guess == numberToGuess) {
                guessed = true;
                break;
            } else if (guess < numberToGuess) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }

            System.out.println("Attempts left: " + (attemptsAllowed - attempts));
        }

        showResult(guessed, attempts, attemptsAllowed, numberToGuess);
    }

    // Function to show the result
    static void showResult(boolean guessed, int attempts, int attemptsAllowed, int numberToGuess) {
        if (guessed) {
            System.out.println("\n🎉 Congratulations! You guessed the number in " + attempts + " attempts.");
            int score = (attemptsAllowed - attempts + 1) * 10;
            System.out.println("Your score: " + score);
        } else {
            System.out.println("\n😢 Sorry! You ran out of attempts.");
            System.out.println("The number was: " + numberToGuess);
        }
    }

    // Function to ask if user wants to play again
    static boolean askReplay() {
        System.out.print("\nDo you want to play again? (yes/no): ");
        String choice = sc.next().toLowerCase();
        return choice.equals("yes");
    }

    // Main game loop
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the lower Bound:");
        int lowerBound = sc.nextInt();
        System.out.println();
        System.out.print("Enter the upper bound and hould be atleast 10 higher than lower bound: ");
        int upperBound = sc.nextInt(), attemptsAllowed = 7;

        System.out.println("🎮 Welcome to the Number Guessing Game!");

        boolean playAgain = true;
        while (playAgain) {
            playRound(lowerBound, upperBound, attemptsAllowed);
            playAgain = askReplay();
        }

        System.out.println("\n👋 Thanks for playing! Goodbye!");
        sc.close();
    }
}
