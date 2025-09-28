Number Guessing Game (Java Swing GUI)

A fun Number Guessing Game built with Java Swing, where the player tries to guess a randomly chosen number between 1 and 100.
The game tracks both round scores and a total cumulative score across multiple rounds.

Features
Random number generation between a configurable range (default: 1–100).
Limited number of attempts (default: 7).
Real-time feedback (Too high! / Too low!).
Round Score based on attempts left.
Total Score accumulated across rounds.
"Play Again" button to restart with a new random number.
Simple and interactive GUI built with Java Swing.

Requirements

Java JDK 8 or higher
Any IDE that supports Java (Eclipse, IntelliJ IDEA, NetBeans) or command-line execution

How to Run
Clone this repository:
git clone https://github.com/your-username/number-guessing-game.git
cd number-guessing-game

Compile the program:
javac NumberGuessingGameGUI.java

Run the game:
java NumberGuessingGameGUI

Scoring System
Each round’s score = (Attempts Left + 1) × 10
Total score accumulates across multiple rounds.
Example:
If you guess correctly in 3 attempts → Round Score = 50
After 3 rounds, Total Score could be 120 (depends on performance).
