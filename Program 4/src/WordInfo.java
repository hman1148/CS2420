import java.util.Objects;

public class WordInfo {

    private String word;
    private int count;
    private int lengthValue;
    private int wordVal;

    private static int[] SCORES = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    public WordInfo(String word, int count) {
        this.word = word;
        this.count = count;
        this.lengthValue = getLengthValue();
        this.wordVal = getWordValue();
    }

    private int getLengthValue() {
        switch (this.getWord().length()) {
            case 1:
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            default:
                if(this.word.length() >= 8 ) return 6;
                else return -1;
        }
    }

    private int getBonus() {
        if (this.count == 0) {
            return 5;
        } else if (this.count >= 1 && this.count <= 5) {
            return 4;
        } else if (this.count >= 6 && this.count <= 10) {
            return 3;
        } else if (this.count >= 11 && this.count <= 15) {
            return 2;
        } else {
            return 1;
        }
    }

    private int getWordValue() {
       int val = 0;
        for (int i = 0; i < this.word.length(); i++) {
            val +=  SCORES[this.word.charAt(i) - 'a'];
        }
        return val;
    }

    public int computeScore() {
        return (this.wordVal) * (this.lengthValue) * getBonus();
    }


    public String getWord() {
        return word;
    }

    public void incrementCount() {
        this.count += 1;
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public boolean equals(Object w2) {
       if (w2 == this) {
           return true;
       }
       if (!(w2 instanceof WordInfo)) {
           return false;
       }
       WordInfo w = (WordInfo) w2;
       return (this.word.compareTo(w.word) == 0);
    }

    @Override
    public String toString() {
        return "Word = '" + word + '\'' + ", Word Repeats = " + count + "";
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}

