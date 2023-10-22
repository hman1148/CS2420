public class State implements Comparable<State> {

    private Board board;
    private int costSoFar;
    private int estimateCost;
    private int priority;

    private String moves;

    public State(Board board, int costSoFar, int estimateCost, String moves) {
        this.board = board;
        this.costSoFar = costSoFar;
        this.estimateCost = estimateCost;
        this.moves = moves; // compute priority in constructor and create global variable for it
        this.priority = costSoFar + estimateCost;
    }

    public Board getBoard() {
        return board;
    }

    public int getCostSoFar() {
        return costSoFar;
    }

    public int getEstimateCost() {
        return estimateCost;
    }

    public int getPriority() {
        return priority;
    }

    public String getMoves() {
        return moves;
    }

    @Override
    public int compareTo(State state) {
        return this.priority - state.priority;
    }

    public String toString() {
        return "Priority: " + this.getPriority() + " BoardID: " + this.board.getId() + " Moves so far " + this.board.getMoves()
                + " Cost so far " + this.getCostSoFar() + " Cost until solved "  + this.getEstimateCost();
    }

    public int compareToSolution(String id, String solutionId) {
        int work = 0;

        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) != solutionId.charAt(i)) {
                work += 1;
            }
        }
        return work;
    }
}
