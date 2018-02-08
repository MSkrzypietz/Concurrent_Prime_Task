package Configuration;

public enum Configuration {
    instance;

    public final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
    public final int maximum = 493009335;
    public int sizeOfPartition = maximum / numberOfProcessors;

}
