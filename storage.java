import java.util.*;
public class storage extends Game {
    static HashMap<String, Word> dict1 = new HashMap<>(); // HashMap to store Current words in the board
    static HashSet<String> allWords = new HashSet<>(); // HashSet to store all words encountered in the game
    boolean add(String spelling, Word word) {
        if(dict1.containsKey(word)) // if duplicate found
            return false;
        dict1.put(spelling, word);
        return true;
    }

    boolean remove(String spelling) {
        if(dict1.containsKey(spelling)) { // if word found in the board
            dict1.remove(spelling);
            return true;
        }
        return false;
    }

    void replace(String spelling, Word word) {
        dict1.put(spelling, word); // replace entry in HashMap
    }
}