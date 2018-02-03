package main_application;

import base.Partition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

public class Task implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private BigInteger minimum;
    private BigInteger maximum;
    //private ArrayList<BigInteger> primes;
    //private ArrayList<Integer> primes;


    public Task(CyclicBarrier cyclicBarrier, BigInteger minimum, BigInteger maximum) {
        this.cyclicBarrier = cyclicBarrier;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /*
    public ArrayList<BigInteger> getPrimes() {
        return primes;
    }
*/
    public static void main(String[] args) {
        /*
        Task task = new Task(null, BigInteger.valueOf(1), BigInteger.valueOf(100));
        task.execute();
        System.out.println(task.initPrimesList());

        System.out.println("Actual primes:");
        SieveOfEratosthenes sieveOfEratosthenes = new SieveOfEratosthenes();
        System.out.println(sieveOfEratosthenes.calcPrimesUnder(BigInteger.valueOf(100)));
        // First prime = 2 -> start at 3
        */

        ArrayList<Partition> partitions = initPartitions();
        for (Partition partition : partitions) {
            System.out.println(partition.getMinimumIndex() + " - " + partition.getMaximumIndex());
            ArrayList<Integer> primes = initPrimesList(partition.getMinimumIndex(), partition.getMaximumIndex());
            System.out.println(primes);
            System.out.println(String.valueOf(partition.getMinimumIndex() + 10) + ": " + getPreviousPrimeIndex(primes, partition.getMinimumIndex() + 10));
            System.out.println(String.valueOf(partition.getMinimumIndex() + 10) + ": " + getNextPrimeIndex(primes, partition.getMinimumIndex() + 10));
            System.out.println("========================================================");
        }

    }

    public static ArrayList<Partition> initPartitions() {
        ArrayList<Partition> partitions = new ArrayList<>();

        for (int i = 0; i < Configuration.instance.numberOfProcessors; i++) {
            // 3 is the first number because to evaluate the assumption the number requires a previous prime -> 2 is the first prime
            int min = i > 0 ? partitions.get(i - 1).getMaximumIndex() + 1 : 3;
            int max = i == Configuration.instance.numberOfProcessors - 1 ? Configuration.instance.maximum : min + (Configuration.instance.sizeOfPartition - 1);
            partitions.add(new Partition(min, max));
        }

        return partitions;
    }

    public static ArrayList<Integer> initPrimesList(int min, int max) {
        ArrayList<Integer> primes = new ArrayList<>();

        for (int i = min - 1;; i--) {
            if (isPrime(i)) {
                primes.add(i);
                break;
            }
        }

        for (int i = min; i <= max; i++)
            if (isPrime(i))
                primes.add(i);

        for (int i = max + 1;; i++) {
            if (isPrime(i)) {
                primes.add(i);
                break;
            }
        }

        /*
        for (BigInteger bi = minimum; bi.compareTo(maximum) < 0; bi = bi.add(BigInteger.ONE))
            if (isPrime(bi))
                primes.add(bi);
        */

        return primes;
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
    }

    private boolean isPrime(BigInteger number) {
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }

    private static Integer getPreviousPrimeIndex(ArrayList<Integer> primes, int number) {
        List<Integer> lesserPrimes = primes.stream()
                .filter(prime -> prime < number)
                .collect(Collectors.toList());
        return lesserPrimes.get(lesserPrimes.size() - 1);
    }

    private static Integer getNextPrimeIndex(ArrayList<Integer> primes, int number) {
        return primes.stream()
                .filter(prime -> prime > number)
                .findFirst()
                .get();
    }

    private void execute() {
        //primes = initPrimesList();
        /*
        PollardRho pollardRho = new PollardRho();
        List<BigInteger> factors;
        for (BigInteger bi = minimum; bi.compareTo(maximum) < 0; bi = bi.add(BigInteger.ONE)) {
            factors = pollardRho.factor(bi);
            BigInteger sumOfFactors = factors.stream().reduce(BigInteger.ZERO, BigInteger::add);

        }*/
    }

    public void run() {
        execute();
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
