public class NodeInfo<E> {

    private E value;

    private int level;

    public NodeInfo(E value, int level) {
        this.level = level;
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
