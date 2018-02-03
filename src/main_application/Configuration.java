package main_application;

public enum Configuration {
    instance;

    public final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
    //public BigInteger range = new BigInteger("493009335");
    public final int maximum = 493009335;//1000000000;
    public int sizeOfPartition = maximum / numberOfProcessors;

}
