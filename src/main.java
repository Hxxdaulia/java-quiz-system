/* **************************************
@author   Haad Arshad Aulia

Level 8 Quiz Program – Procedural ADT Style
QuestionRecord accessed only via ADT methods.

Abstract Data Type: QuestionRecord
----------------------------------
This ADT defines the structure and operations for a quiz question.

Data stored in the ADT:
- question: the question text (String)
- answer: the correct answer (String)
- used: a boolean flag to mark if the question has been asked

Allowed operations:
- createQuestion(question, answer): creates a new QuestionRecord
- getQuestion(q): returns the question text
- getAnswer(q): returns the answer text
- isUsed(q): returns whether the question has been used
- setUsed(q, boolean): sets the used flag of the question

All access to QuestionRecord fields is done through these ADT methods.
****************************************/

import java.util.Random;
import java.util.Scanner;
import java.io.*;

// Record type for QuestionRecord
class QuestionRecord {
    String question;
    String answer;
    boolean used;
} // END QuestionRecord
public class MyQuiz {

    // Main program: runs the quiz with optional new question set creation
    public static void main(String[] args) throws IOException {
        greetingMessage(); // print welcome instructions

        boolean createNewSet = askYesNo("Do you want to create a new question set? (yes/no): ");
        String fileName;
        if (createNewSet) {
            fileName = inputString("Enter a filename for this question set (e.g., 'history.csv'): ");
            createNewQuestionSet(fileName);
        } else {
            fileName = inputString("Enter the filename of the question set to use (e.g., 'science.csv'): ");
        }

        QuestionRecord[] quizBank = readQuestionsFromFile(fileName);

        final int START_MONEY = 500;
        final int NUMBER_OF_PLAYERS = 4;
        String[] playerNames = getPlayers(NUMBER_OF_PLAYERS);

        boolean playAgain = true;
        while (playAgain) {
            runQuizForPlayers(quizBank, START_MONEY, playerNames);
            playAgain = askYesNo("Do you want to play another round? (yes/no): ");
        }

        System.out.println("Thanks for playing!");
    } // END main

    // Print welcome message and game instructions
    public static void greetingMessage() {
        System.out.println("Welcome to my quiz!");
        System.out.println("You start with £500. Each correct answer doubles your money, each wrong answer halves it.");
        System.out.println("You can type 'PASS' to skip a question.");
    } // END greetingMessage


    // Get names of all players
    public static String[] getPlayers(final int NUMBER_OF_PLAYERS) {
        String[] names = new String[NUMBER_OF_PLAYERS];
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            String input = inputString("Enter name for Player " + (i + 1) + ": ");
            while (!isValid(input)) {
                System.out.println("Player name cannot be empty. Try again.");
                input = inputString("Enter name for Player " + (i + 1) + ": ");
            }
            names[i] = input;
        }
        return names;
    } // END getPlayers

    // Run quiz for each player in sequence
    public static void runQuizForPlayers(QuestionRecord[] quizBank, int startMoney, String[] playerNames) {
        for (int p = 0; p < playerNames.length; p++) {
            quizBank = resetQuestions(quizBank); // reset all used flags
            int playerMoney = startMoney;
            System.out.println("It is " + playerNames[p] + "'s turn.");

            playerMoney = playTurn(quizBank, playerMoney);

            System.out.println(playerNames[p] + " finished with £" + playerMoney + "\n");
        }
    } // END runQuizForPlayers

    // Play a single turn for one player
    public static int playTurn(QuestionRecord[] quizBank, int playerMoney) {
        Random dice = new Random();

        for (int qNum = 0; qNum < quizBank.length; qNum++) {
            QuestionRecord q = pickRandomQuestion(quizBank, dice);
            boolean answeredCorrectly = false;
            boolean passed = false;

            while (!answeredCorrectly && !passed) {
                String userAnswer = askQuestion(getQuestion(q));

                if (userAnswer.equalsIgnoreCase("PASS")) {
                    System.out.println("Next question.\n");
                    passed = true;
                } else {
                    answeredCorrectly = isAnswerCorrect(q, userAnswer);
                    int newMoney = updateMoney(answeredCorrectly, playerMoney);
                    System.out.println(generateResultMessage(answeredCorrectly, playerMoney, newMoney));
                    playerMoney = newMoney;
                }
            }
        }
        return playerMoney;
    } // END playTurn

    // Ask a question and ensure non-empty answer
    public static String askQuestion(String question) {
        String input = inputString(question);
        while (!isValid(input)) {
            System.out.println("Answer cannot be empty. Try again.");
            input = inputString(question);
        }
        return input;
    } // END askQuestion

    // Pick a random unused question from the quiz bank
    public static QuestionRecord pickRandomQuestion(QuestionRecord[] quizBank, Random dice) {
        int index = dice.nextInt(quizBank.length);
        while (isUsed(quizBank[index])) {
            index = dice.nextInt(quizBank.length);
        }
        quizBank[index] = setUsed(quizBank[index], true);
        return quizBank[index];
    } // END pickRandomQuestion

    // Reset all questions to unused state
    public static QuestionRecord[] resetQuestions(QuestionRecord[] quizBank) {
        for (int i = 0; i < quizBank.length; i++) {
            quizBank[i] = setUsed(quizBank[i], false);
        }
        return quizBank;
    } // END resetQuestions

    // Check if answer is correct using ADT accessor
    public static boolean isAnswerCorrect(QuestionRecord q, String userAnswer) {
        return userAnswer.equalsIgnoreCase(getAnswer(q));
    } // END isAnswerCorrect

    // Update player's money based on correctness
    public static int updateMoney(boolean answeredCorrectly, int currentMoney) {
        if (answeredCorrectly) {
            return currentMoney * 2;
        }
        return currentMoney / 2;
    } // END updateMoney

    // Generate feedback message for the player
    public static String generateResultMessage(boolean answeredCorrectly, int playerMoney, int newMoney) {
        return "Correct? " + answeredCorrectly + ". You had £" + playerMoney + ", now you have £" + newMoney + ".\n";
    } // END generateResultMessage

    // Prompt user for a string input
    public static String inputString(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextLine().trim();
    } // END inputString

    // **********************************************
    // Check if a string input is valid (non-empty)
    // **********************************************
    public static boolean isValid(String input) {
        return input.length() > 0;
    } // END isValid

    // Ask a yes/no question and return boolean
    public static boolean askYesNo(String message) {
        String response = inputString(message);
        while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
            response = inputString("Please type 'yes' or 'no': ");
        }
        return response.equalsIgnoreCase("yes");
    } // END askYesNo

    // Create a new question set and save to CSV
    public static void createNewQuestionSet(String fileName) throws IOException {
        int numberOfQuestions = 0;
        while (numberOfQuestions <= 0) {
            numberOfQuestions = inputInt("How many questions? ");
        }

        PrintWriter writer = new PrintWriter(new FileWriter(fileName));

        for (int i = 0; i < numberOfQuestions; i++) {
            String question = inputString("Enter question " + (i + 1) + ": ");
            String answer = inputString("Enter answer " + (i + 1) + ": ");
            QuestionRecord q = createQuestion(question, answer);
            writer.println(getQuestion(q) + "," + getAnswer(q));
        }

        writer.close();
        System.out.println("Question set saved to " + fileName + "\n");
    } // END createNewQuestionSet

    // Prompt user for an integer input
    public static int inputInt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        int input = Integer.parseInt(scanner.nextLine());
        return input;
    } // END inputInt

    // Read question set from file into ADT array
    public static QuestionRecord[] readQuestionsFromFile(String fileName) throws IOException {
        int linesCount = countLines(fileName);
        QuestionRecord[] quizBank = new QuestionRecord[linesCount];

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                String[] parts = line.split(",");
                QuestionRecord q = createQuestion(parts[0].trim(), parts[1].trim());
                quizBank[index] = q;
                index++;
            }
        }
        reader.close();
        return quizBank;
    } // END readQuestionsFromFile

    // Count number of lines in a CSV file
    public static int countLines(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int count = 0;
        while (reader.readLine() != null) count++;
        reader.close();
        return count;
    } // END countLines

    /* **********************************************************************
       Abstract Data Type for QuestionRecord
       Only allowed operations are below:
    ********************************************************************** */

    // **********************************************
    // Create a new QuestionRecord
    // **********************************************
    public static QuestionRecord createQuestion(String questionText, String answerText) {
        QuestionRecord q = new QuestionRecord();
        q.question = questionText;
        q.answer = answerText;
        q.used = false;
        return q;
    } // END createQuestion

    // Get question text from QuestionRecord
    public static String getQuestion(QuestionRecord q) {
        return q.question;
    } // END getQuestion

    // Get answer text from QuestionRecord
    public static String getAnswer(QuestionRecord q) {
        return q.answer;
    } // END getAnswer

    // Get used flag from QuestionRecord
    public static boolean isUsed(QuestionRecord q) {
        return q.used;
    } // END isUsed

    // Set used flag for QuestionRecord
    public static QuestionRecord setUsed(QuestionRecord q, boolean used) {
        q.used = used;
        return q;
    } // END setUsed

} // END class MyQuiz

