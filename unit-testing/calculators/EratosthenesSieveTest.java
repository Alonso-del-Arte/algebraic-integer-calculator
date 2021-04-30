/*
 * Copyright (C) 2021 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>.
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
     * Test of the listPrimes function, of the EratosthenesSieve class.
     */
    @Test
    public void testListPrimes() {
        System.out.println("listPrimes");
        int threshold = 10;
        Integer[] smallPrimes = {2, 3, 5, 7};
        ArrayList<Integer> expected 
                = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * The first 25 positive primes should be given in order.
     */
    @Test
    public void testListPrimesTo100() {
        int threshold = 100;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        ArrayList<Integer> expected 
                = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * The primes cache should be updated after a query that requires raising 
     * the threshold, but then a query for primes with a lower threshold should 
     * not give primes in excess of the threshold.
     */
    @Test
    public void testEratosthenesSieveCanTrim() {
        int threshold = 80;
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        System.out.println("EratosthenesSieve reports " + actual.size() 
                + " primes between 1 and " + threshold);
        threshold = 20;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19};
        ArrayList<Integer> expected 
                = new ArrayList<>(Arrays.asList(smallPrimes));
        actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * Modifications to a received list of primes should not affect the list of 
     * primes held by EratosthenesSieve.
     */
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
        ArrayList<Integer> expected 
                = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
        subset.clear();
        threshold = 139;
        Integer[] fewMorePrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,  
            41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 
            109, 113, 127, 131, 137, 139};
        expected = new ArrayList<>(Arrays.asList(fewMorePrimes));
        actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * Negative threshold should be turned to positive threshold, e.g., -30 
     * should be understood as +30.
     */
    @Test
    public void testListPrimesTurnsNegativeToPositive() {
        int threshold = -30;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        ArrayList<Integer> expected 
                = new ArrayList<>(Arrays.asList(smallPrimes));
        ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * A threshold of 0 should give an empty list, not cause any exception.
     */
    @Test
    public void testThresholdZeroGivesEmptyList() {
        int threshold = 0;
        ArrayList<Integer> expected = new ArrayList<>();
        try {
            ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
            assertEquals(expected, actual);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred for threshold " + threshold;
            fail(msg);
        }
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * A threshold of 1 should give an empty list, not cause any exception.
     */
    @Test
    public void testThresholdOneGivesEmptyList() {
        int threshold = 1;
        ArrayList<Integer> expected = new ArrayList<>();
        try {
            ArrayList<Integer> actual = EratosthenesSieve.listPrimes(threshold);
            assertEquals(expected, actual);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred for threshold " + threshold;
            fail(msg);
        }
    }
    
}
