import java.util.Scanner;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class GuessTheNumber {

    // Method to write the player's score to a file
    public static void WriteScore(int numberToGuess, int numberOfTries, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) { // 'true' enables appending
            // Write the player's name, the number to guess, and the number of tries to the file
            writer.write(name + "|" + numberToGuess + "|" + numberOfTries);
            writer.newLine();
        } catch (IOException e) {
            // Handle any IO exceptions that occur
            e.printStackTrace();
        }
    }

    // Method to read and display scores from the file
    public static void ReadScores() {
        File file = new File("scores.txt");
        // Check if the file exists
        if (!file.exists()) {
            System.out.println("No scores available. Play the game to save your first score!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line into parts (name, number to guess, number of tries)
                String[] parts = line.split("\\|");
                String name = parts[0];
                int numberToGuess = Integer.parseInt(parts[1]);
                int numberOfTries = Integer.parseInt(parts[2]);
                // Display the score information
                System.out.println(name + " guessed " + numberToGuess + " in " + numberOfTries + " tries.");
            }
        } catch (IOException e) {
            // Handle any IO exceptions that occur
            e.printStackTrace();
        }
    }

    // Method to play the guessing game
    public static void GuessGame(Scanner sc) { // Pass the Scanner object as a parameter
        Random rand = new Random();
        // Generate a random number between 1 and 100
        int numberToGuess = rand.nextInt(100) + 1;
        int numberOfTries = 0;
        int userGuess = 0;
        boolean hasGuessed = false;

        System.out.println("Welcome to Guess The Number!");
        System.out.println("I have selected a number between 1 and 100. Try to guess it!");

        // Loop until the user guesses the correct number
        while (!hasGuessed) {
            System.out.print("Enter your guess: ");
            userGuess = sc.nextInt();
            numberOfTries++;

            // Check if the guess is correct, too low, or too high
            if (userGuess == numberToGuess) {
                hasGuessed = true;
            } else if (userGuess < numberToGuess) {
                System.out.println("The number is higher than " + userGuess + ".");
            } else {
                System.out.println("The number is lower than " + userGuess + ".");
            }
        }

        // Congratulate the user and ask if they want to save their score
        System.out.println("Congratulations! You have guessed the number in " + numberOfTries + " tries.");
        System.out.print("Would you like to save your score? (y/n): ");
        String saveScore = sc.next();
        if (saveScore.equals("y")) {
            System.out.print("Please enter your name: ");
            String name = sc.next();
            WriteScore(numberToGuess, numberOfTries, name);
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        System.out.println("Welcome to Guess The Number!");
        boolean running = true;
        Scanner sc = new Scanner(System.in); // Keep the Scanner open until the program exits

        while (running) {
            // Display the menu options
            System.out.println("1. Play the game");
            System.out.println("2. View scores");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");

            int option = sc.nextInt();

            // Handle the user's menu selection
            switch (option) {
                case 1:
                    // Start the guessing game
                    GuessGame(sc); // Pass the Scanner object to the GuessGame method
                    break;
                case 2:
                    // Display the scores
                    ReadScores();
                    break;
                case 3:
                    // Exit the program
                    running = false;
                    break;
                default:
                    // Handle invalid menu options
                    System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close(); // Close the Scanner when the program exits
    }
}