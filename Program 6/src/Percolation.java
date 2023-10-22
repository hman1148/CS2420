import java.util.Arrays;

public class Percolation {

    String BLUE = "\u001B[44m  \u001B[0m";
    String PURPLE = "\u001B[45m  \u001B[0m";
    String BLACK = "\u001B[40m  \u001B[0m";
    String RED = "\u001B[41m  \u001B[0m";
    String[] sym = {BLACK, PURPLE, BLUE, RED};


    private int TOP;
    private int BOTTOM; // some default value for both top and bottom;
    private boolean grid[][];
    
    private int sizeX;
    private int sizeY;

    private UnionFind unionFind;
    private boolean[][] partOfPercolation;

    public Percolation(int sizeX, int sizeY) {

        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.TOP = sizeX * sizeX;
        this.BOTTOM = sizeX * sizeX + 1;

        grid = new boolean[sizeX][sizeY];
        for (boolean[] booleans : grid) {
            Arrays.fill(booleans, false);
        }

        this.partOfPercolation = new boolean[this.sizeX][this.sizeY];
        for (boolean[] booleans : partOfPercolation) {
            Arrays.fill(booleans, false);
        }

        this.unionFind = new UnionFind((sizeX * sizeY) + 2);
    }

    public void run() {
        int counter = 0;
        int lastX;
        int lastY;
        int randomX = 0;
        int randomY = 0;
        boolean lastPercolation;

        while (unionFind.find(TOP) != unionFind.find(BOTTOM)) {

            randomX = (int) (Math.random() * (sizeX));
            randomY = (int) (Math.random() * (sizeY));

            if (this.grid[randomX][randomY]) {
                continue;
            }
            this.grid[randomX][randomY] = true;
            counter ++;

            // check if randomY + 1 is not out of bounds
            if (randomY + 1 < sizeY) {
                // if so check if that coordinate is open
                if (this.grid[randomX][randomY + 1]) {
                    unionFind.union(this.getID(randomX, randomY), this.getID(randomX, randomY + 1));
                }
            }

            if (randomY - 1 >= 0) {
                if (this.grid[randomX][randomY - 1]) {
                    unionFind.union(this.getID(randomX, randomY), this.getID(randomX, randomY - 1));
                }
            }

            if (randomX + 1 < sizeX) {
                if (this.grid[randomX + 1][randomY]) {
                    unionFind.union(this.getID(randomX, randomY), this.getID(randomX + 1, randomY));
                }
            } else {
                unionFind.union(this.getID(randomX, randomY), this.BOTTOM);
            }

            if (randomX - 1 >= 0) {
                if (this.grid[randomX - 1][randomY]) {
                    unionFind.union(this.getID(randomX, randomY), this.getID(randomX - 1, randomY));
                }
            } else {
                unionFind.union(this.getID(randomX, randomY), this.TOP);
            }

            if (counter == 50) {
                counter = 0;
                this.printGrid(grid, this.partOfPercolation);
            }
        }
        lastX = randomX;
        lastY = randomY;
        this.printGrid(grid, partOfPercolation);
        
        this.partOfPercolation(grid, partOfPercolation, lastX, lastY);
        this.printGrid(grid, partOfPercolation);
    }

    public int getID(int x, int y) {
        return (this.sizeX * x) + y;
    }

    public void printGrid(boolean[][] grid, boolean[][] visted) {
        int SIZE = this.sizeX;

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {

                if (visted[x][y]) {
                    System.out.print(sym[3]);
                } else if (!grid[x][y]) {
                    System.out.print(sym[0]);
                } else if (grid[x][y] && (unionFind.find(this.getID(x, y)) != unionFind.find(TOP))) {
                    System.out.print(sym[1]); // purple
                } else {
                    System.out.print(sym[2]); // blue
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void partOfPercolation(boolean[][] grid, boolean[][] partOfPercolation, int x, int y) {
        if (partOfPercolation[x][y]) {
            return;
        }

        partOfPercolation[x][y] = true;
        if (y + 1 < sizeY && grid[x][y + 1]) {
            partOfPercolation(grid, partOfPercolation, x, y + 1);
        }

        if (y - 1 >= 0 && grid[x][y - 1]) {
            partOfPercolation(grid, partOfPercolation, x, y - 1);
        }

        if (x + 1 < sizeX && grid[x + 1][y]) {
            partOfPercolation(grid, partOfPercolation, x + 1, y);
        }

        if (x -1  >= 0 && grid[x - 1][y]) {
            partOfPercolation(grid, partOfPercolation, x - 1, y);
        }
    }
}
