package main_application;

import algorithms.PollardRho;
import algorithms.PrimeListGenerator;
import base.Partition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

public class Task implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private Integer minimum;
    private Integer maximum;
    private ArrayList<Integer> primes;

    public Task(CyclicBarrier cyclicBarrier, Integer minimum, Integer maximum) {
        this.cyclicBarrier = cyclicBarrier;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    private void initPrimesList() {
        this.primes = new PrimeListGenerator().calculate(minimum, maximum);
        // add previous prime of minimum and next prime of maximum to check the assumption for minimum and maximum as well
        primes.add(0, getPreviousPrime(minimum));
        primes.add(getNextPrime(maximum));
        System.out.println(this.toString() + ": Primes initiated");
    }

    private Integer getPreviousPrime(int minimum) {
        for (int i = minimum - 1;; i--)
            if (isPrime(i))
                return i;
    }

    private Integer getNextPrime(int maximum) {
        for (int i = maximum + 1;; i++)
            if (isPrime(i))
                return i;
    }

    private boolean isPrime(long n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long) Math.sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }

    //TODO: replace this method to increase performance
    //TODO:-> private field currentIndex -> if currentIndex == number then currentIndex++
    private Integer getPreviousPrimeIndex(int number) {
        List<Integer> lesserPrimes = primes.stream()
                .filter(prime -> prime < number)
                .collect(Collectors.toList());
        return lesserPrimes.get(lesserPrimes.size() - 1);
    }

    //TODO: replace this method to increase performance
    //TODO:-> private field currentIndex -> if currentIndex == number then currentIndex++
    private Integer getNextPrimeIndex(int number) {
        List<Integer> greaterPrimes = primes.stream()
                .filter(prime -> prime > number)
                .collect(Collectors.toList());
        return greaterPrimes.get(0);
    }

    private void execute() {
        initPrimesList();
        PollardRho pollardRho = new PollardRho();

        for (int i = minimum; i <= maximum; i++) {
            // 1. get all prime factors
            // 2. get sum of all prime factors
            // 3. find previous prime of i
            // check if i - (2.) == (3.)
            //      -> false: continue to next number
            //      -> true: 4. find next prime of i
            //              check if i + (2.) == (4.)
            //                  -> false: continue to next number
            //                  -> true: falsified the assumption!

            if (i % 10000 == 0)
                System.out.println(i);
            List<BigInteger> factors = pollardRho.factor(BigInteger.valueOf(i));
            BigInteger sumOfFactors = factors.stream().reduce(BigInteger.ZERO, BigInteger::add);
            int previousPrime = getPreviousPrimeIndex(i);
            if (i - sumOfFactors.intValue() != previousPrime)
                continue;
            int nextPrime = getNextPrimeIndex(i);
            if (i + sumOfFactors.intValue() != nextPrime)
                continue;
            System.out.println("ASSUMPTION FALSIFIED!!!! " + i + " is smaller with the same properties");
            System.out.println("Sum of the prime factors of i =" + sumOfFactors.intValue());
            break;
        }

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
