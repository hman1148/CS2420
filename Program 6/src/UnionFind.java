import java.util.Arrays;

public class UnionFind {

    private int[] unionCollection;

    public UnionFind(int size) {

        unionCollection = new int[size];
        // have to set all the values in the array to -1
        Arrays.fill(unionCollection, -1);
    }
    public void union(int item1, int item2) {

        item1 = find(item1);
        item2 = find(item2);

        if (item1 == item2) {
            return;
        }

        if (this.unionCollection[item2] < this.unionCollection[item1]) {
            this.unionCollection[item1] = item2;
        } else {
            if (this.unionCollection[item1] == this.unionCollection[item2]) {
                this.unionCollection[item1] --;
            }
            this.unionCollection[item2] = item1;
        }
    }
    public int find(int item) {

        if (item <= 0) {
            item = 0;
        }

        if (unionCollection[item] < 0) {
            return item;
        }
        unionCollection[item] = find(unionCollection[item]);
        return unionCollection[item];
    }
}
