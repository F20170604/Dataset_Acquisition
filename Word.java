class Word {
    final String spelling;
    final String color;
    int score;
    final int length;
    final int x, y;
    int mod = (int)1e9+7;
    Word(String spelling, String color, int score, int length, int x, int y) {
        this.spelling = spelling; // initialise spelling
        this.color = color; // initialise color
        this.score = score; // initialise score
        this.length = length; // initialise length of word
        this.x = x; // initialise row
        this.y = y; // initialise starting column
    }

    public boolean equals(Object o) {
        if (o instanceof Word) { // if object to be compared with is a valid instance
            Word p = (Word)o;
            boolean spellingEqual = p.spelling.equals(spelling); // compare spellings
            boolean colorEqual = p.color.equals(color); // compare colors
            boolean scoreEqual = p.score == score; // compare scores
            boolean lengthEqual = p.length == length; // compare lengths
            boolean coordinatesEqual = (p.x == x) && (p.y == y); // compare starting coordinates
            return spellingEqual && colorEqual && scoreEqual && lengthEqual && coordinatesEqual; // return all checks are true or not
        }
        return false; // if invalid object
    }

    public int hashCode() {
        int numberHashCode = new Integer(score).hashCode() * 31 * 31 * 31 * 31 * 31 % mod
            + new Integer(length).hashCode() * 31 * 31 * 31 * 31 % mod
            + new Integer(x).hashCode() * 31 * 31 * 31 % mod
            + new Integer(y).hashCode() * 31 * 31 % mod;
        int stringHashCode = spelling.hashCode() * 31  % mod + color.hashCode() % mod;

        return (numberHashCode + stringHashCode) % mod; 
    }
}