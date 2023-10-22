public class TestUnion {
    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(50);

        int a = 20;
        int b = 49;

        unionFind.union(a,b);
        boolean val = unionFind.find(a) == unionFind.find(b);

        System.out.println(val);


    }


}
