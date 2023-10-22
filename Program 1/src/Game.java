import java.util.Scanner;
import java.util.*;

public class Game {
    Board theBoard;
    String originalBoardID;

    /**
     *  Solve the provided board
     * @param label Name of board (for printing)
     * @param b Board to be solved
     */
    public void playGiven(String label, Board b) {
        theBoard = b;
        originalBoardID = b.getId();
        b.setGameName(label);
        System.out.println("Board initial: " + b.getGameName() + " \n" + theBoard.toString());
        solve();
    }

    /**
     * Create a random board (which is solvable) by jumbling jumnbleCount times.
     * Solve
     * @param label Name of board (for printing)
     * @param jumbleCount number of random moves to make in creating a board
     */
    public void playRandom(String label, int jumbleCount) {
        theBoard = new Board();
        theBoard.makeBoard(jumbleCount);
        System.out.println(label + "\n" + theBoard);
        playGiven(label, theBoard);
    }

    public static void main(String[] args) {
        String[] games = {"102453786", "123740658", "023156478", "413728065", "145236078", "123456870"};
        String[] gameNames = {"Easy Board", "Game1", "Game2", "Game3", "Game4", "Game5 No Solution"};
        Game g = new Game();
        Scanner in = new Scanner(System.in);
        Board b;
        String resp;
        for (int i = 0; i < games.length; i++) {
            b = new Board(games[i]);
            g.playGiven(gameNames[i], b);
            System.out.println("Click any key to continue\n");
            resp = in.nextLine();
        }
        boolean playAgain = true;

        int JUMBLECT = 18;  // how much jumbling to do in random board
        while (playAgain) {
            g.playRandom("Random Board", JUMBLECT);

            System.out.println("Play Again?  Answer Y for yes\n");
            resp = in.nextLine().toUpperCase();
            playAgain = (resp != "") && (resp.charAt(0) == 'Y');
        }
    }


    public void solve() {
        // have a boolean variable that keeps track of the while
        // while loop running until solved

        // linked list of boards
        // put initial board on the queue

        // loop through the characters of UPDL and a variable to check the last move. Check if any of the moves return true
        // from makeMove. If it does then

        // make a copy of the board so that when we change the board it doesn't change the original on each iteration

        boolean isSolved = false;

        LinkedList<Board> queue = new LinkedList<Board>();
        queue.insert(theBoard);
        char lastMove = ' ';
        int boardsAdded = 0;
        int boardsRemoved = 0;

        while (!isSolved) {

            Board currentBoard = queue.delete();
            String possibleMoves = "ULDR";
            boardsRemoved ++;
            lastMove = currentBoard.getLastMove();
//            if (boardsRemoved < 5) {
//                queue.printContents();
//            }

            for (int i = 0; i < possibleMoves.length(); i++) {
                Board nextBoard = new Board(currentBoard); // this has to be a deep copy **FIX THIS**

                // have user enter in a value to continue
                if (nextBoard.makeMove(possibleMoves.charAt(i), lastMove)) {

                    char currentMove = possibleMoves.charAt(i);
                    nextBoard.addMoves(currentMove); // add the move
                    queue.insert(nextBoard);
                    boardsAdded ++;

                    if (nextBoard.isSolved(nextBoard.getId())) {
                        isSolved = true;
                        System.out.print("Moves required: ");
                        nextBoard.printAllMoves();
                        System.out.println(theBoard.getGameName() + " Queue Added = " + boardsAdded + " Removed = " + boardsRemoved);
                        theBoard.printSteps(nextBoard.getMoves());
                    }
                }
            }
        }
    }
}

