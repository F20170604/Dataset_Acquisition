import java.util.*;
import java.io.*;
import org.python.util.PythonInterpreter; 
import org.python.core.*; 
public class scoreGeneratorProcess extends Game {
    static String enteredEnglishWord;
    scoreGeneratorProcess(String englishWord) {
        enteredEnglishWord = englishWord;
        String mostRelatedWord = getMostRelatedWord(englishWord); // get the word present in grid equal or most related to the inputted englishWord
        graphAndScore stats = getConnectedGraphAndScore(storage.dict1.get(mostRelatedWord)); // get score & graph of connected words having same color as that of mostRelatedWord
        modifyScore(stats.score); // add score to current score
        graphRemoval(stats.graph); // remove graph
        if(currentWordsInBoard<=0) { // if board gets empty
            boardFill(); // fill the board
            return; // end process
        }
        structureMaintainer(); // maintain structure i.e. drop words further wherever possible
    }

    public String getMostRelatedWord(String englishWord) {
        double relationScore = 0;
        String  mostRelatedWord = "";
        if(storage.dict1.containsKey(englishWord)) { // if inputted word is present in the grid
            mostRelatedWord = englishWord;
            return mostRelatedWord; // return the word
        }
        for(String word : storage.dict1.keySet()) { // search for the most related word among the words present in the grid
            double connectionScore=0;
            try {
                FileWriter fileWriter = new FileWriter("inputConnection.txt");
                PrintWriter printWriter = new PrintWriter(fileWriter, false);
                printWriter.println(englishWord);
                printWriter.println(word);
                printWriter.close();

                ProcessBuilder pb = new ProcessBuilder("python","getConnection.py"); // get Semantic strength using Python via pre trained word2Vec embeddings
                Process p = pb.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String s = in.readLine(); // read ouput of Python code as string
                connectionScore = Double.valueOf(s); // get corresponding connection score as a double value
            } catch(Exception e) {

            }

            if(connectionScore > relationScore) { // if current words' connection strength is maximum yet
                relationScore = connectionScore;
                mostRelatedWord = word;
            }
        }
        return mostRelatedWord;
    }

    public graphAndScore getConnectedGraphAndScore(Word word) {
        int factor=1;
        if(!word.spelling.equals(enteredEnglishWord)) // if the mostRelatedWord is not present in the grid, then we only add 0.5x score. Hence, the division factor of 2
            factor=2;
        ArrayList<int[]> graph = new ArrayList<>(); // to store coordinates of connected words
        HashSet<String> graphWords = new HashSet<>(); // hashSet to store unique words
        String graphColor = word.color; // color of our mostRelatedWord
        int score=0; // to store score corresponding to the graph
        Queue<int[]> queue = new LinkedList(); // queue to store next neighbours
        int[][] visited = new int[screenHeight][screenLength]; // to save visited words
        visited[word.x][word.y]=1; // mark mostRelatedWord as visited
        queue.add(new int[]{word.x, word.y}); // add it to queue
        while(!queue.isEmpty()) { // while there are neighbours having same colour - Implementing Breadth First Search
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                int[] location = queue.poll();
                int x = location[0];
                int y = location[1];
                visited[x][y]=1;
                if(grid[x][y]!=null && grid[x][y].color.equals(graphColor)) { // if color is same
                    String currSpelling = grid[x][y].spelling;
                    if(!graphWords.contains(currSpelling)) { //  if words' score isn't added before
                        score+=grid[x][y].score;
                        graphWords.add(currSpelling);
                    }
                    graph.add(location); // add cell coordinate to be removed from graph
                    //  check neighbours in cardinal directions
                    if(x-1>=0 && visited[x-1][y]==0)
                        queue.add(new int[]{x-1, y});
                    if(x+1<screenHeight && visited[x+1][y]==0)
                        queue.add(new int[]{x+1, y});
                    if(y-1>=0 && visited[x][y-1]==0)
                        queue.add(new int[]{x, y-1});
                    if(y+1<screenLength && visited[x][y+1]==0)
                        queue.add(new int[]{x, y+1});
                }
            }
        }
        score/=factor; // divide score by factor = 1 or factor = 2, depends on mostRelatedWord
        graphAndScore output = new graphAndScore(score, graph);
        return output;
    }

    public void modifyScore(int addScore) {
        currScore+=addScore; // add score of deleted graph to users' score
    }

    public void graphRemoval(ArrayList<int[]> graph) {
        for(int i=0;i<graph.size();i++) {
            int x = graph.get(i)[0];
            int y = graph.get(i)[1];
            if(storage.dict1.containsKey(grid[x][y].spelling)) { // words extend to multiple columns, hence decrement currentWordsInBoard only once for multiple similar words
                currentWordsInBoard--;
                storage.dict1.remove(grid[x][y].spelling);
            }
            grid[x][y]=null; // make that cell in grid empty
        }
    }

    public void structureMaintainer() {
        //  traverse throughout the grid and lower down words by one level wherever possible
        // BUG : Only lowers by 1 level for now. Multiple Level Lowering also a possibility. To implement.
        for(int i=screenHeight-2;i>=0;i--) {
            int j=0;
            for(;j<screenLength;) {
                if(grid[i][j]==null) {
                    j++;
                }
                else {
                    int l = grid[i][j].length;
                    boolean poss=true;
                    for(int k=j;k<j+l;k++)
                        if(grid[i+1][k]!=null) {
                            poss=false;
                            break;
                        }
                    if(poss) {
                        for(int k=j;k<j+l;k++) {
                            grid[i+1][k]=grid[i][k];
                            grid[i][k]=null;
                        }
                    }
                    j+=l;
                }
            }
        }
        Arrays.fill(firstPositionAvailaible, screenHeight);
        for(int i=0;i<screenHeight;i++)
            for(int j=0;j<screenLength;j++) {
                if(grid[i][j]!=null && firstPositionAvailaible[i]==-1)
                    firstPositionAvailaible[j] = i-1;
            }
    }

    class graphAndScore {
        int score;
        ArrayList<int[]> graph;
        graphAndScore(int score, ArrayList<int[]> graph) {
            this.score = score;
            this.graph = graph;
        }
    }
}