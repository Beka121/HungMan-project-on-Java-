import java.util.*;
import java.nio.file.*;
import java.io.IOException;

public class Hangman {
    private static final int MAX_TRIES = 6; // Maximum number of mistakes
    private static String[] WORDS;

    static {
        try {
            List<String> wordsList = Files.readAllLines(Paths.get("src/words.txt")); // Make sure the path is correct
            WORDS = wordsList.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Error loading words file.");
            WORDS = new String[]{"default"}; // Fallback word
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        String word = WORDS[rand.nextInt(WORDS.length)];
        char[] guessedWord = new char[word.length()];
        Arrays.fill(guessedWord, '-');

        Set<Character> guesses = new HashSet<>();
        int mistakes = 0;

        System.out.println("Welcome to Hangman!");

        while (mistakes < MAX_TRIES && new String(guessedWord).contains("-")) {
            System.out.println("\nWord: " + new String(guessedWord));
            System.out.println("Mistakes: " + mistakes + "/" + MAX_TRIES);
            System.out.print("Enter a letter: ");

            String input = scanner.nextLine().toLowerCase().trim();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input. Please enter a single letter.");
                continue;
            }

            char letter = input.charAt(0);
            if (guesses.contains(letter)) {
                System.out.println("You already guessed this letter!");
                continue;
            }

            guesses.add(letter);

            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    guessedWord[i] = letter;
                    found = true;
                }
            }

            if (!found) {
                mistakes++;
                System.out.println("Wrong guess.");
            }

            drawHangman(mistakes);
        }

        System.out.println("\n" + (mistakes == MAX_TRIES ? "You lost! The word was: " + word : "You won! The word was: " + word));
    }

    private static void drawHangman(int mistakes) {
        String[] hangman = {
                """
               +---+
               |   |
                   |
                   |
                   |
                   |
              =========
            """,
                """
               +---+
               |   |
               O   |
                   |
                   |
                   |
              =========
            """,
                """
               +---+
               |   |
               O   |
               |   |
                   |
                   |
              =========
            """,
                """
               +---+
               |   |
               O   |
              /|   |
                   |
                   |
              =========
            """,
                """
               +---+
               |   |
               O   |
              /|\\  |
                   |
                   |
              =========
            """,
                """
               +---+
               |   |
               O   |
              /|\\  |
              /    |
                   |
              =========
            """,
                """
               +---+
               |   |
               O   |
              /|\\  |
              / \\  |
                   |
              =========
            """
        };
        System.out.println(hangman[mistakes]);
    }
}
