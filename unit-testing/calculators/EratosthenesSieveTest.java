/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculators;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the EratosthenesSieve class.
 * @author Alonso del Arte
 */
public class EratosthenesSieveTest {
    
    /**
     * Test of listPrimes method, of class EratosthenesSieve.
     */
    @Test
    public void testListPrimes() {
        System.out.println("listPrimes");
        int threshold = 10;
        Integer[] smallPrimes = {2, 3, 5, 7};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another of listPrimes method, of class EratosthenesSieve.
     */
    @Test
    public void testListPrimesTo100() {
        int threshold = 100;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another of listPrimes method, of class EratosthenesSieve.
     */
    @Test
    public void testEratosthenesSieveCanTrim() {
        int threshold = 80;
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        System.out.println("EratosthenesSieve reports " + actual.size() 
                + " primes between 1 and " + threshold);
        threshold = 20;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(smallPrimes));
        actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testModifyPrimeSubset() {
        int threshold = 20;
        ArrayList<Integer> subset = EratosthenesSieve.listPrimes(threshold);
        for (int i = 0; i < subset.size(); i++) {
            int p = -subset.get(i);
            subset.set(i, p);
        }
        threshold = 40;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
        subset.clear();
        threshold = 139;
        Integer[] fewMorePrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 
            41,43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 
            109, 113, 127, 131, 137, 139};
        expected = new ArrayList<>(Arrays.asList(fewMorePrimes));
        actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
}
