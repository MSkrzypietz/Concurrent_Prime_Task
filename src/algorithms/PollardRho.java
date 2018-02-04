package algorithms;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

// Algorithm source: https://introcs.cs.princeton.edu/java/99crypto/PollardRho.java.html

public class PollardRho {

    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE  = new BigInteger("1");
    private final static BigInteger TWO  = new BigInteger("2");
    private final static SecureRandom random = new SecureRandom();

    private ArrayList<BigInteger> factors;

    public PollardRho() {
        factors = new ArrayList<>();
    }

    private BigInteger rho(BigInteger N) {
        BigInteger divisor;
        BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do {
            x  =  x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    public List<BigInteger> factor(BigInteger N) {
        ArrayList<BigInteger> factors = new ArrayList<>();
        if (N.compareTo(ONE) == 0) return new ArrayList<>();
        if (N.isProbablePrime(20)) {
            factors.add(N);
            return factors;
        }
        BigInteger divisor = rho(N);
        factors.addAll(factor(divisor));
        factors.addAll(factor(N.divide(divisor)));
        return factors;
    }

}