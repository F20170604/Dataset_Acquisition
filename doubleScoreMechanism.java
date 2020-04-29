public class doubleScoreMechanism extends Game {

    boolean doubleScoreMechanism(String spelling) {
        boolean operationAchieved = mutationPossible(spelling); // check if modifying the score is possible or not
        if(operationAchieved) // if possible
            doubleScore(spelling); // double the score of that word
        return operationAchieved; // return operation successful or not
    }

    public static boolean mutationPossible(String spelling) {
        // if player has enough chance to double score and the word chosen by player exists in the grid.
        if(doubleWordScorePossible > 0 && storage.dict1.containsKey(spelling)) { 
            return true;
        }
        return false;
    }

    public void doubleScore(String spelling) {
        doubleWordScorePossible--; // decrement number of chances availaible after this successful operation
        Word newWord = storage.dict1.get(spelling); // get corresponding Word object from dict1 HashMap
        newWord.score*=2; // double the score assosciated with that object
        storage.dict1.replace(spelling, newWord); // replace the Word in the HashMap
        for(int i = newWord.y; i < newWord.y + newWord.length ; i++) // replace the Word in the grid
            grid[newWord.x][i] = newWord;
    }
}