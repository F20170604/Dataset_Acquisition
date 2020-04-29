import java.util.Random;
public class wordCreator {
    static String[] colors = {"red", "blue", "green", "yellow"}; // colors to choose from
    static Random rand = new Random(); // Random generator
    static Word createWord(String spelling) {
        String color = colors[rand.nextInt(4)]; // choose any index between 0 to 3 both inclusive
        int length = spelling.length();
        int score = length; // score of that word is it's length - generally, longer the word, more is its complexity
        int column = rand.nextInt(Game.screenLength-length); // get random column between 0 to screenLength-length, both inclusive
        // screenLength-length, so that the word does not get out of screen
        int row = getMaxDepthPossible(column, length); // get row to which word brick will fall to
        return new Word(spelling, color, score, length, row, column); // return Word object
    }

    static Word createWord(String spelling, int x) { // same as createWord(String spelling), only difference is that we can provide custom column to any Word object
        String color = colors[rand.nextInt(4)];
        int length = spelling.length();
        int score = length;
        int y = getMaxDepthPossible(x, length);
        return new Word(spelling, color, score, length, x, y);
    }

    public static int getMaxDepthPossible(int x, int length) {
        int maxDepth = Game.screenHeight; // initialise maxDepth as screenHeight, since a word can at most fell by screen's height
        for(int i=x;i<x+length;i++) {
            // since our word cell is extended to various columns, we take the minimum depth the word can fall in every column
            // e.g. suppose our words extends to column 1, 2, 3
            // column 1 is filled till row 4, seen from above
            // column 2 is filled till row 6, seen from above
            // column 3 is filled till row 7, seen from above
            // so our word can only reach till row 4. 
            // firstPositionAvailaible[1] = 3, firstPositionAvailaible[2] = 5, firstPositionAvailaible[1] = 6
            // we choose the minimum = 3, hence starting our word from row number 3.
            maxDepth=Math.min(maxDepth, Game.firstPositionAvailaible[i]);
        }
        return maxDepth;
    }
}