public class TeaCup {

    private int[][] profitChart;

    private int maxTeaCups;
    private int maxSellSize;
    private final int[] valuePerSize = {0, 1, 3, 5, 9, 10, 15, 17, 18, 19, 22, 25, 27}; 

    public TeaCup() {
        // Initialize Table
        profitChart =  new int[13][25];
        this.maxTeaCups = 25;
        this.maxSellSize = 13;

        for (int i = 1; i < maxTeaCups; i++) {
            this.profitChart[1][i] = i;
        }

        for (int teaCupSize = 1; teaCupSize < maxSellSize; teaCupSize++) { // 24
            this.profitChart[1][teaCupSize] = teaCupSize * this.valuePerSize[1];
            for (int availableTeacups = 1; availableTeacups < maxTeaCups; availableTeacups++) { // 13
                // get the difference between the teacups
                if (teaCupSize > availableTeacups) {
                    this.profitChart[teaCupSize][availableTeacups] = this.profitChart[teaCupSize - 1][availableTeacups];
                } else {
                    int use = this.profitChart[teaCupSize][availableTeacups - teaCupSize] + this.valuePerSize[teaCupSize];
                    int dont = this.profitChart[teaCupSize - 1][availableTeacups];
                    this.profitChart[teaCupSize][availableTeacups] = Math.max(use, dont); // creates a table with as diagram in the example
                }
            }
        }
    }

    public void partOne(int currentAmount, int totalTeaCups, String path) {
        if (totalTeaCups == 0) {
            System.out.println(path);
            return;
        }

        if (currentAmount <= 0 || totalTeaCups <= 0) {
            return;
        }

        partOne(currentAmount, totalTeaCups - currentAmount, " " + currentAmount + path);
        partOne(currentAmount - 1, totalTeaCups, path);
    }

    public void partTwo(int amount) {
        int maxValue = this.profitChart[this.maxSellSize - 1][amount];
        int maxValueChange;
        int amountChange = amount;
        int optimalRow = 0;

        StringBuilder path = new StringBuilder();

        while (amountChange > 0) {
            optimalRow = 0;
            boolean foundChange = false;
            maxValueChange = this.profitChart[this.maxSellSize - 1][amountChange];

            for (int i = 0; i < this.profitChart.length; i++) {
                if (this.profitChart[i][amountChange] == maxValueChange && !foundChange) {
                    optimalRow = i;
                    path.append(" ").append(optimalRow);
                    break;
                }
             }
            amountChange = amountChange - optimalRow;
        }
        System.out.println("Total Teacups of size " + amount + " $" + maxValue + " " + path);
    }
}
