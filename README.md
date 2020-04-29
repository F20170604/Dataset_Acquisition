# Dataset_Acquisition
Backend for Gamification of NLP Dataset Acquisition Platform

# PROJECT DESCRIPTION

This project is all about the development of a Dataset Acquisition Platform for Words which can be presented as an intuitive game to people.

Our game is inspired from the game of Candy Crush where several shapes fall on each other and as soon as they form a pattern of similar colours, that group of colours disappear and give points to the user.

Our game is a little bit different. In our game, a specific number of pre-defined English words having different scores and random colours will fall down randomly in our grid. As soon as the user enters an English word and its corresponding translation in the second language i.e. Konkani in our specific case, one of the 3 following things can happen:
1. User enters a word present in the grid: The game will search for the largest group of words having same colour, connected to the input and remove it. The full score corresponding to each word removed will get added to the user’s score.
2. User enters a word not present in the grid: The game will then search for the largest group of words having same colour, connected to the word most closely related to the input and remove it. In this case half of the score corresponding to each word removed will get added to the user’s score.
3. User enters an input which has been already once or is invalid: In this case, our game will ask once again for the input.

After generating the score, our game will then add a new word to the game which is related to our user’s input.

Our game also has an incentive for the user - For every 10 words played by the user, the game provides an opportunity to the user to double the score of any word present in the grid.

The game ends as soon as one column of the columns get filled with words.

## Game Features
* Automated Board Refill if in any point of time all words are scored up by the user
* Parallel saving of English and Second Language Words in the form os a .csv file so even if our game ends
abruptly, our data is not lost
* Custom word placement in the grid - Administrator can choose its own position of words in the grid instead of randomly placing them while board filling.
* Polyglot Codebase - Using Java as the main language for backend gives us 2.5x faster operations in comparison to Python. Python is used for Machine Learning happening and is only invoked when needed.
* Hash Tables are used for Data Queries to provide amortised O(1) Time complexity for search, add and replace operations.

Our game is inspired from the game of Candy Crush. But it will be different in ways where instead of just colours, we will emphasise on both the colour relation as well as the Semantic Strength in the game.
It’s a simple and easy to play game with a lightweight UI to improve time latency while queries are made between FrontEnd and BackEnd.


# PROJECT DESIGN

### System Design
![alt text](https://github.com/Ayush517/Dataset_Acquisition/blob/master/images/System%20Design.png "System Design")

### Project Architecture
![alt text](https://github.com/Ayush517/Dataset_Acquisition/blob/master/images/Package%20Architecture.png "Package Architecture")

### Languages Used

Python - For Word Prediction using Machine Learning through word2Vec using GoogleNews-vectors- negative300 as pre-trained embeddings which have been provided by Google.

Java - For Software Architecture and Backend

Python and Java interact with each other and transfer data using Process Builder making this a Polyglot Codebase.



# Backend - Brief Explanation

### Method Declaration
* Classes
  - Functions
  
* Game - Parent Class, Handles everything, Root of our system.
  - Game() - Initialises the Game Parameters and resets everything. Calls boardFill() and startGame().
  - boardFill() - Fills board with pre-chosen words having random positions and colours.
  - startGame() - Starts/Stops the game.
  - takeInputAndProceed() - Takes input from user, adds it to database and processes it by forwarding to various functions.
* Word - Attributes associated with each word in the grid i.e. spelling, colour, score, length, x and y coordinates.
  - Word() - creates a Word object using given parameters.
  - boolean equals(Object o) - for comparison of each attribute present in our custom Word Class.
  - int hashCode() - generate unique hashCode for hashing comparisons using Prime Number 31 to generate hashCode and modulus 1000000007 to prevent Integer overflow.
* storage - HashTables to store words encountered during and currently present in the game.
  - boolean add(String spelling, Word word) - adds spelling as Key and object Word as its corresponding Value in the HashMap. Returns if word addition possible or not. If duplicate, then returns false.
  - boolean remove(String spelling) - removes spelling entry from HashMap. Returns deletion possible or not.
  - replace(String spelling, Word word) - Replaces entry in HashMap.
* wordCreator - Word Object creation.
  - Word createWord(String spelling) - randomly chooses colour and column. Calls getMaxDepthPossible to get coordinate of row where the word will fall. Assigns all the values and creates an object of class Word. Word extends to multiple columns depending upon its length.
  - Word createWord(String spelling, int x) - Similar to createWord(String spelling). Only Difference is that instead of randomly choosing column, it uses parameter x as column position.
  - int getMaxDepthPossible(int x, int length) - Returns row number where the word will fall.
* newWordAdditionProcess - Addition of new Word in Grid.
  - boolean wordFill(Word word) - Fills corresponding columns and row in our grid with our Word object.
  - String nextWordPredictor(String englishWord) - Gets a new word related to the input and one which hasn’t been previously present/used in the game.
  - String predict(String englishWord) - Calls Python code named getRelatedWord.py to get a related word using pre-trained word2Vec. Data is transferred using text file named inputRelated.txt
* datasetCreationProcess - Parallel Dataset Creation
  - addEntry(String englishWord, String secondLanguageWord) - Add entry to our CSV file.
* graphAndScore - Present inside scoreGeneratorProcess class
  - graphAndScore(int score, ArrayList<int[]> graph) - Structure to return multiple values of interest in
single return statement i.e. an Integer and an ArrayList.
* scoreGeneratorProcess - Generates score and removes group of connected same coloured words
  - scoreGeneratorProcess(String englishWord) - Default Constructor. Uses string passed as parameter to calculate and remove.
  - String getMostRelatedWord(String englishWord) - Check whether the input is present in our grid. If present return that, else return the most closely related word.
  - graphAndScore getConnectedGraphAndScore(Word word) - Get graph of connected words having same colour using Breadth First Search.
  - modifyScore(int addScore) - Add the score achieved returned from getConnectedGraphAndScore function.
  - graphRemoval(ArrayList<int[]> graph) - Removes connected words from grid.
  - structureMaintainer() - Drops down words wherever possible.
* doubleScoreMechanism - Doubles Score of object associated with spelling
  - boolean doubleScoreMechanism(String spelling) - Default Constructor. Returns Double Scoring Process completed successfully or not.
  - boolean mutationPossible(String spelling) - Returns score doubling of Word object associated with String spelling possible or not.
  - doubleScore(String spelling) - Doubles the score of associated object in the grid.
  
### Variable Declaration
* Classes
  - Variables
  
* Game - Parent Class, Handles everything, Root of our system.
  - static int doubleWordScorePossible - Stores the number of chances available to double score of any word
  - static int currentWordsInBoard - Stores current number of words in the game
  - static int wordsDone - Stores number of words inputted by user
  - static int n - Stores number of words provided by the Administrator before game starts. Top Entry of initial.txt file
  - static final int screenHeight=100 - Stores screen height i.e. Number of Rows
  - static final int screenLength=100 - Stores screen length i.e. Number of Columns
  - static int currScore - Stores current score of user
  - static int[] firstPositionAvailaible - Stores the deepest position available for dropping of word
  - static Word[][] grid - Our Games’ Main Grid of Word Objects
  - static Scanner fileInput = new Scanner(System.in) - To get input from file
  - static File file - Input File for words needed for Board Filling
  - static Scanner ob = new Scanner(System.in) - To get input from user
  - static boolean continueGame - Game continues or ends
  - static int startIndexReadingFromFile - Stores the index from which we start taking words from input file. Every time chooses minimum(25, number of words left in file) words.
* Word - Attributes associated with each word in the grid - spelling, colour, score, length, x and y coordinates.
  - final String spelling - Spelling of the word
  - final String colour - Colour of the word present in grid
  - int score - Score associated with that word
  - final int length - Length of the word. Used as length of Word brick that falls down in game.
  - final int x, y - Row and Column from where our word starts
* storage - HashTables to store words encountered during and currently present in the game.
  - static HashMap<String, Word> dict1 - Stores Words currently present in our board.
  - static HashSet<String> allWords - Stores all words seen in the game to prevent duplicate entries
* wordCreator - Word Object creation.
  - static String[] colors = {"red", "blue", "green", “yellow"} - Colours to choose randomly from
  - static Random rand = new Random() - Random generator
* graphAndScore - Present inside scoreGeneratorProcess class
  - int score - Stores score associated with connected words
  - ArrayList<int[]> graph - Graph storing coordinates of connected words
* scoreGeneratorProcess - Generates score and removes group of connected same coloured words
  - static String enteredEnglishWord - Stores englishWord needed for processing score and deleting graph
  
# Work Left

- Rectify StructureMaintainer - Currently supports only 1 level drop. Update to multi-level drop needed
- Improve Python Codebase Performance
  - Caching/Service Linking needed to pre train word2Vec used embeddings just once instead of again and again for every function call
  - Improve Python-Java Communication Interface : Might use MicroServices Architecture
- Multi Colour support for a Word Cell
