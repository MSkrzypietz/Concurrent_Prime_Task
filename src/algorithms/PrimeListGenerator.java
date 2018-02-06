package algorithms;

// Source: https://medium.com/@agilanbtdw/prime-number-generation-in-java-using-segmented-sieve-of-eratosthenes-187af1dcd051

import java.util.ArrayList;

public class PrimeListGenerator {
    private int array[];

    public PrimeListGenerator() {}

    public ArrayList<Integer> calculate(int n, int m) {
        ArrayList<Integer> primesList = new ArrayList<>();

        int j = 0;
        int sqt = (int) Math.sqrt(m);
        array = new int[sqt + 1];
        int[] primes = new int[sqt + 1];

        initialise(sqt + 1);
        for (int i = 2; i <= sqt; i++) {
            if (array[i] == 1) {
                primes[j] = i;
                j++;
                for (int k = i + i; k <= sqt; k += i) {
                    array[k] = 0;
                }
            }
        }

        int diff = (m - n + 1);
        array = new int[diff];
        initialise(diff);
        for (int k = 0; k < j; k++) {
            int div = n / primes[k];
            div *= primes[k];
            while (div <= m) {
                if(div>=n && primes[k]!=div)
                    array[div-n] = 0;
                div += primes[k];
            }
        }
        for (int i = 0; i < diff; i++) {
            if (array[i] == 1 && (i+n) !=1)
                primesList.add(i + n);
        }

        return primesList;
    }

    private void initialise(int sqt) {
        for (int i = 0; i < sqt; i++) {
            array[i] = 1;
        }
    }
}