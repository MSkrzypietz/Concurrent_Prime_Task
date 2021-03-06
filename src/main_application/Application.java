package main_application;

import Configuration.Configuration;
import base.Partition;

import java.util.ArrayList;

public class Application {

    private ArrayList<Task> tasks;

    private Application() {}

    public static void main(String[] args) {

        long runtimeStart = System.currentTimeMillis();

        Application application = new Application();
        application.build();
        application.execute();

        System.out.println("runtime (ms)        : " + (System.currentTimeMillis() - runtimeStart));

    }

    private void execute() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (Task task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void build() {
        ArrayList<Partition> partitions = initPartitions();
        tasks = new ArrayList<>();

        for (Partition partition : partitions)
            tasks.add(new Task(partition.getMinimumIndex(), partition.getMaximumIndex()));
    }

    private ArrayList<Partition> initPartitions() {
        ArrayList<Partition> partitions = new ArrayList<>();

        for (int i = 0; i < Configuration.instance.numberOfProcessors; i++) {
            // 3 is the first number because to evaluate the assumption the number requires a previous prime -> 2 is the first prime
            int min = i > 0 ? partitions.get(i - 1).getMaximumIndex() + 1 : 3;
            int max = i == Configuration.instance.numberOfProcessors - 1 ? Configuration.instance.maximum : min + (Configuration.instance.sizeOfPartition - 1);
            partitions.add(new Partition(min, max));
        }

        return partitions;
    }

}