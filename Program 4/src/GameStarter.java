import java.io.*;
import java.util.*;

public class GameStarter {
    private String fileName;
    private HashTable<WordInfo> H;
    private HashTable<String> legalWords;
    private int totalScore;

    public GameStarter() {
        H = new HashTable<WordInfo>();
        legalWords = dictionary();
    }

    private HashTable<String> dictionary() {
        legalWords = new HashTable<>();
        try {
            Scanner sc = new Scanner(new File("dictionary.txt"));
            while (sc.hasNext()) {
                String word = sc.next();
                legalWords.insert(word);
            }
         } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return legalWords;
    }

    private boolean legalWordInHash(String word) {
        if (legalWords.find(word) != null) {
            return true;
        } else {
            return false;
        }
    }

    public int scoreWord(String word) {
        WordInfo currentWord = new WordInfo(word, 0);
        WordInfo wordInTable = H.find(currentWord);
        int total = 0;

        if (wordInTable == null) {
            if (this.legalWordInHash(word)) {
                H.insert(currentWord);
                H.setNumItemsInTable(this.H.getNumItemsInTable() + 1);
                total += currentWord.computeScore();
            }
        } else {
            wordInTable.incrementCount();
            total += wordInTable.computeScore();
        }
        return total;
    }
    public void playGame(String filename) {
        fileName = filename;
        System.out.println("FILE " + filename);
        try {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNext()) {
                String word = sc.next();
                totalScore += this.scoreWord(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finalGameStats() {
        System.out.println("Game score: " + this.totalScore);
        System.out.println("Total number of finds: " + H.getFindCounter());
        System.out.println("Total number of probes done: " + H.getNumProbs());
        System.out.println("The total length of the hash table: " + H.lengthOfTable());
        System.out.println("Total number of items stored in the hashed table: " + H.getNumItemsInTable() + "\n");
        H.printContents();
        System.out.print("\n");
    }

    @Override
    public String toString() {
        return "Current Game: " + this.fileName;
    }

    public static void main(String[] args) {
         String[] games = {"game0.txt", "game1.txt", "game2.txt", "game3.txt", "game4.txt","test.txt"};
         for (String filename : games) {
            GameStarter g = new GameStarter();
            g.playGame(filename);
            System.out.println(g);
            g.finalGameStats();
        }
    }
}
