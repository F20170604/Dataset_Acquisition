import java.io.*;
import java.util.*;
public class newWordAdditionProcess extends Game {
    public static boolean wordFill(Word word) {
        for(int i=word.y;i<word.y+word.length;i++) { // start from column y and fill till column  y+word length
            grid[word.x][i]=word;
            Game.firstPositionAvailaible[i]=word.x-1; // make depth of next word of that column equal to row just above current row, row indexing start from above
            // Top most Row is indexed 0
            // Left most Column is indexed 0
        }
        return word.x<screenHeight; // if word entered in column reaches limit of column, function returns false and game gets over
    }

    public static String nextWordPredictor(String englishWord) {
        storage.allWords.add(englishWord);
        String nextWord = englishWord;
        while(storage.allWords.contains(nextWord)) // keep predicting next word until and unless a unique word is found
            nextWord = predict(nextWord);
        return nextWord;
    }

    public static String predict(String englishWord) {
        String prediction = "";
        try {
            FileWriter fileWriter = new FileWriter("inputRelated.txt"); // write english word to file inputRelated.txt
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.print(englishWord);
            printWriter.close();

            ProcessBuilder pb = new ProcessBuilder("python","getRelatedWord.py"); // invoke python code to get related word using input from file inputRelated.txt
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = in.readLine();
            prediction=s;
        } catch(Exception e) {

        }
        return prediction;
    }
}