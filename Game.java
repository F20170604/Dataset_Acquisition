import java.util.*;
import java.io.*;
public class Game {
    static int doubleWordScorePossible;
    static int currentWordsInBoard;
    static int wordsDone;
    static int n;
    static final int screenHeight=100;
    static final int screenLength=100;
    static int currScore;
    static int[] firstPositionAvailaible;
    static Word[][] grid;
    static Scanner fileInput = new Scanner(System.in);
    static File file;
    static Scanner ob = new Scanner(System.in);
    static boolean continueGame;
    static int startIndexReadingFromFile;
    public Game() {
        doubleWordScorePossible = 0; // Number of Double Chances = 0
        wordsDone = 0; // Number of words done = 0
        currScore = 0; // Current Player Score = 0
        currentWordsInBoard = 0; // Current Number of words on the board = 0
        startIndexReadingFromFile = 0; // Start reading from the first word
        firstPositionAvailaible = new int[screenLength]; // Number of positions to fall = Number of columns
        Arrays.fill(firstPositionAvailaible, screenHeight); // Maimum Depth for any word fall = Screen Height
        grid = new Word[screenHeight+1][screenLength+1]; // Initialise Grid as empty
        continueGame=true; // start Game
        file = new File("initial.txt"); // File to read Administrator defined words
        try {
            fileInput = new Scanner(file);
        } catch(Exception e) {

        }
        n = fileInput.nextInt(); // Read the number of words present in the file
        boardFill(); // Fill the board with words
        startGame(); // Enable game to be played by user
    }

    public static void startGame() {
        while(continueGame) // if game can be played - continue, else if any of the columns is completely filled - stop
            takeInputAndProceed();    // take input from the user and proceed
        System.out.println("Game Over"); // when game gets over, print
    }

    public static void boardFill() {
        Arrays.fill(firstPositionAvailaible, screenHeight); // Every time we fill  the board, Maimum Depth for any word fall = Screen Height
        for(int i=startIndexReadingFromFile; i<startIndexReadingFromFile+25 && i<n; i++) { // either choose 25 words or less than that, as many are availaible in input.txt
            String spelling = fileInput.nextLine(); // Read word from file
            storage.allWords.add(spelling); // Save that word in allWords which keeps a track of all words encountered in the game
            Word word = wordCreator.createWord(spelling); // create Word object and attach the attributes to the spelling
            storage.dict1.put(spelling, word); // Add (spelling, word) to dict1 HashMap for O(1) operations
            newWordAdditionProcess.wordFill(word); // Fill the grid corresponding to x and y attributes in word
            currentWordsInBoard++; // increment number of words on board
        }
    }

    public static void takeInputAndProceed() {
        String englishWord = ob.nextLine(); // Get user inputted English Word
        String secondLanguageWord = ob.nextLine(); // Get user Inputted second Language Word i.e. Konkani Word in our specific case
        if(englishWord==null || secondLanguageWord==null || englishWord.length()==0 || secondLanguageWord.length()==0) { // If user inputs null word
            continueGame = true; // continue game
            System.out.println("Invalid Input");
            return; // return to startGame and start taking input again
        }
        if(storage.allWords.contains(englishWord) && !storage.dict1.containsKey(englishWord)) { // If user inputs duplicate word
            continueGame = true; // continue game
            System.out.println("Word already Entered");
            return; // return to startGame and start taking input again
        }
        wordsDone++; // if input is valid, increment the number of words inputted by user
        if(wordsDone%10==0) // for every 10 words
            doubleWordScorePossible++;    // increment the number of chances availaible for doubling the score of any word present in the grid
        try {
            datasetCreationProcess.addEntry(englishWord, secondLanguageWord); // add entry to our dataset
        } catch(Exception e) { 

        }
        new scoreGeneratorProcess(englishWord); // Proceed to Generating score for input and removal of corresponding graph
        String nextWord = newWordAdditionProcess.nextWordPredictor(englishWord); // Get next word
        Word word = wordCreator.createWord(nextWord); // Create Word object for next word
        continueGame = newWordAdditionProcess.wordFill(word); // Add the next word to our grid
    }
}