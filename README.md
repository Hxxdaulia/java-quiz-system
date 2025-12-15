This is a version you can paste straight into README.md and tweak if you want it shorter:

Java Quiz Game with Question Records
This repository contains Mini Project 8, a text‑based quiz game written in Java that uses an abstract data type (QuestionRecord) to manage questions, answers and usage flags. The program demonstrates procedural programming, console input/output, basic validation, file handling and simple game logic.

Features
Multiple‑choice style quiz where players answer questions and earn or lose money based on correctness

Money system that doubles the player’s money for a correct answer and halves it for a wrong answer

Question management via an ADT: create questions, track whether they have been used, and reset them between games

Ability to create a new question set from user input and save it as a CSV file

Ability to load questions from an existing CSV file into an in‑memory question bank

Input validation for strings, integers, and yes/no responses

Key methods / concepts
Some of the main methods implemented include:

isAnswerCorrect(QuestionRecord q, String userAnswer): checks if a player’s answer matches the correct answer (case‑insensitive)

updateMoney(boolean answeredCorrectly, int currentMoney): updates the player’s money by doubling or halving it

generateResultMessage(...): builds a feedback message showing correctness and money change

inputString(String message) and inputInt(String message): reusable console input helpers

askYesNo(String message): loops until the user provides a valid yes/no response

createNewQuestionSet(String fileName): lets the user build a new question set and saves it to CSV

readQuestionsFromFile(String fileName): loads questions from a CSV file into a QuestionRecord[]

countLines(String fileName): counts lines in a CSV file to size the array

createQuestion, getQuestion, getAnswer, isUsed, setUsed: operations on the QuestionRecord ADT

Concepts demonstrated:

Java console I/O with Scanner

Arrays and loops for processing collections of questions

Procedural decomposition into many small, focused methods

File I/O with BufferedReader, FileReader, PrintWriter and simple CSV parsing

Basic state tracking with fields inside a simple record/class

How to run
Clone or download this repository.

Navigate to the src directory (or wherever your .java files are stored).

Compile the code, for example:

javac *.java

Run the main class, for example:

java Main

Make sure any CSV files for question sets are in the same directory or update the file paths in the code accordingly.
