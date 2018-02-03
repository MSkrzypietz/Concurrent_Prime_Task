package base;

public class Partition {

    // minimumIndex and maximumIndex are both inclusive
    private int minimumIndex;
    private int maximumIndex;

    public Partition(int minimumIndex, int maximumIndex) {
        this.minimumIndex = minimumIndex;
        this.maximumIndex = maximumIndex;
    }

    public int getMinimumIndex() {
        return minimumIndex;
    }

    public int getMaximumIndex() {
        return maximumIndex;
    }

}
