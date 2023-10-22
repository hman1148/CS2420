public class Main {
    
    public static void main(String[] args) {

        TeaCup tea = new TeaCup();
        tea.partOne(10, 10, "");
        System.out.println();
        for (int i = 1; i < 25; i++) {
            tea.partTwo(i);
        }
    }

}