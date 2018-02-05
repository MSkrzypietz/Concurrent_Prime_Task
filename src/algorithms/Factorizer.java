package algorithms;

import java.util.ArrayList;

public class Factorizer {

    public Factorizer() {}

    public Integer sumOfPrimeFactors(int n) {
        int sum = 0;

        for (int i = 2; i * i <= n; i++)
            while (n % i == 0) {
                sum += i;
                n = n / i;
            }

        if (n > 1)
            sum += n;

        return sum;
    }
}