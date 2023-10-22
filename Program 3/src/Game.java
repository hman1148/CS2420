import java.util.Scanner;
public class Game {
    Board theBoard;
    String originalBoardID;

    /**
     *  Solve the provided board
     * @param label Name of board (for printing)
     * @param b Board to be solved
     */

    public void playGiven(String label, Board b, int type) {
        theBoard = b;
        originalBoardID = b.getId();
        b.setGameName(label);
        System.out.println("Board initial: " + b.getGameName() + " \n" + theBoard.toString());
        // Determine what game type to run
        if (type == 1) {
            solve1();
        } else {
            solve2();
        }
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
        playGiven(label, theBoard, 1);
        playGiven(label, theBoard, 2);
    }

    public static void main(String[] args) {
        String[] games = {"102453786", "123740658", "023156478", "413728065", "145236078", "123456870"};
        String[] gameNames = {"Easy Board", "Game1", "Game2", "Game3", "Game4", "Game5 No Solution"};
        Game g = new Game();
        Scanner in = new Scanner(System.in);
        Board a;
        Board b;
        String resp;
        for (int i = 0; i < games.length; i++) {
            a = new Board(games[i]);
            b = new Board(games[i]);
            g.playGiven(gameNames[i], a, 1);
            g.playGiven(gameNames[i], b, 2);
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

    public void solve1() {

        boolean isSolved = false;

        // A* Algorithm
        AVLTree<State> priorityQueue = new AVLTree<>();
        State state = new State(theBoard, 0,0, theBoard.getMoves());
        priorityQueue.insert(state); // add the state to the AVL tree

        char lastMove = ' ';
        int boardsAdded = 0;
        int boardsRemoved = 0;

        while (!isSolved) {

            State currentState = priorityQueue.findMin();
            priorityQueue.deleteMin();

            Board currentBoard = currentState.getBoard();
            String possibleMoves = "ULDR";

            boardsRemoved ++;
            lastMove = currentBoard.getLastMove();

            for (int i = 0; i < possibleMoves.length(); i++) {
                Board nextBoard = new Board(currentBoard);

                // have user enter in a value to continue
                if (nextBoard.makeMove(possibleMoves.charAt(i), lastMove)) {

                    State nextPossbileState = new State(nextBoard, currentState.getCostSoFar() + 1, currentState.compareToSolution(nextBoard.getId(),nextBoard.getSolvedId()), currentBoard.getMoves());
                    priorityQueue.insert(nextPossbileState);

                    char currentMove = possibleMoves.charAt(i);
                    nextBoard.addMoves(currentMove); // add the move
                    boardsAdded ++;

                    if (nextBoard.isSolved(nextBoard.getId())) {
                        isSolved = true;

                        // A* star method
                        System.out.print("Moves required: ");
                        nextBoard.printAllMoves();
                        System.out.println("Results from A* Algorithm");
                        System.out.println(theBoard.getGameName() + " Queue Added = " + boardsAdded + " Removed = " + boardsRemoved);
                        theBoard.printSteps(nextBoard.getMoves());
                    }
                }
            }
        }
    }

    public void solve2() {

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
                        System.out.println("Results from BruteForce Algorithm");
                        System.out.println(theBoard.getGameName() + " Queue Added = " + boardsAdded + " Removed = " + boardsRemoved);
                        theBoard.printSteps(nextBoard.getMoves());
                    }
                }
            }
        }
    }
}