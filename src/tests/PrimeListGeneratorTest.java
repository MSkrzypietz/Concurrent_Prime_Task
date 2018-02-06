package tests;

import algorithms.PrimeListGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeListGeneratorTest {

    @Test
    void testCalculate() {
        PrimeListGenerator primeListGenerator = new PrimeListGenerator();
        ArrayList<Integer> expectedOutput = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11,
                13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97));
        assertTrue(expectedOutput.containsAll(primeListGenerator.calculate(1, 100)));
    }

}
