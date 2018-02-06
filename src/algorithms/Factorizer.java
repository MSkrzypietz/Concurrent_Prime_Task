package algorithms;

public class Factorizer {

    public Factorizer() {}

    public int sumOfPrimeFactors(int n) {
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