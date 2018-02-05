package main_application;

import algorithms.Factorizer;
import algorithms.PrimeListGenerator;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Task implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private Integer minimum;
    private Integer maximum;
    private ArrayList<Integer> primes;
    private Integer currentPreviousPrimeIndex = 0;

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

    private void execute() {
        initPrimesList();
        Factorizer factorizer = new Factorizer();

        for (int i = minimum; i <= maximum; i++) {
            Integer sumOfFactors = factorizer.sumOfPrimeFactors(i);
            while (i > primes.get(currentPreviousPrimeIndex + 1)) {
                currentPreviousPrimeIndex++;
            }
            int previousPrime = primes.get(currentPreviousPrimeIndex);
            if (i - sumOfFactors != previousPrime)
                continue;
            // index of the next prime is currentPreviousPrimeIndex + 1 unless the current number is a prime then
            // the next prime is currentPreviousPrimeIndex + 2
            int nextPrime = i == primes.get(currentPreviousPrimeIndex + 1) ? primes.get(currentPreviousPrimeIndex + 2) : primes.get(currentPreviousPrimeIndex + 1);
            System.out.println("Number = " + i + "; sumOfFactors = " + sumOfFactors + "; previous prime = " + previousPrime + "; next prime = " + nextPrime);
            if (i + sumOfFactors != nextPrime)
                continue;
            System.out.println("ASSUMPTION FALSIFIED!!!! " + i + " is smaller with the same properties");
            System.out.println("Sum of the prime factors of i =" + sumOfFactors);
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