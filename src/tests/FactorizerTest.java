package tests;

import algorithms.Factorizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorizerTest {

    @Test
    void testFactor() {
        Factorizer factorizer = new Factorizer();

        assertEquals(74, factorizer.sumOfPrimeFactors(493009335));
        assertEquals(6, factorizer.sumOfPrimeFactors(8));
        assertEquals(9, factorizer.sumOfPrimeFactors(14));
        assertEquals(10, factorizer.sumOfPrimeFactors(25));
        assertEquals(14, factorizer.sumOfPrimeFactors(100));
    }

}
