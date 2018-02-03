package main_application;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Application {

    private CyclicBarrier cyclicBarrier;
    private List<Task> tasks;

    public void build() {
        for (int i = 0; i < Configuration.instance.numberOfProcessors; i++) {
            //tasks.add()
        }
    }

    public void execute() {
        for (Task service : tasks)
            new Thread(service).start();
    }

    public static void main(String[] args) {
        /*
        BigInteger N = new BigInteger("493009335");
        PollardRho pollardRho = new PollardRho();
        List<BigInteger> factors = pollardRho.factor(N);
        BigInteger sumOfFactors = factors.stream().reduce(BigInteger.ZERO, BigInteger::add);
        System.out.println(sumOfFactors);

        SieveOfEratosthenes sieveOfEratosthenes = new SieveOfEratosthenes();
        System.out.println(sieveOfEratosthenes.calcPrimesUnder(new BigInteger("1000")));
        */

        Application application = new Application();
        application.build();
        application.execute();
    }

}