package tests;

import main_application.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    private Task task;

    @BeforeEach
    void init() {
        task = new Task(new CyclicBarrier(1), 493000000, 493009335);
        task.run();
    }

    @Test
    void testCheckAssumption() {
        assertFalse(task.checkAssumption(493000000));
        assertFalse(task.checkAssumption(493002000));
        assertFalse(task.checkAssumption(493005000));
        assertFalse(task.checkAssumption(493000201));
        assertFalse(task.checkAssumption(493000050));
        assertFalse(task.checkAssumption(493000001));
        assertFalse(task.checkAssumption(493008000));


        assertTrue(task.checkAssumption(493009335));
    }

    @Test
    void testGetPreviousPrime() {
        assertEquals(2, task.getPreviousPrime(3));
        assertEquals(7, task.getPreviousPrime(10));
        assertEquals(19, task.getPreviousPrime(23));
        assertEquals(41, task.getPreviousPrime(43));
        assertEquals(61, task.getPreviousPrime(63));
    }

    @Test
    void testNextPreviousPrime() {
        assertEquals(5, task.getNextPrime(3));
        assertEquals(17, task.getNextPrime(13));
        assertEquals(37, task.getNextPrime(35));
        assertEquals(37, task.getNextPrime(33));
        assertEquals(53, task.getNextPrime(47));
    }

}
