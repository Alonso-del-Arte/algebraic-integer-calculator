/*
 * Copyright (C) 2023 Alonso del Arte
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the EratosthenesSieve class.
 * @author Alonso del Arte
 */
public class EratosthenesSieveTest {
    
    private static final Random RANDOM = new Random();
    
    private static void assertContainsSame(List<Integer> expected, 
            List<Integer> actual) {
        int expLen = expected.size();
        int actLen = actual.size();
        String msgLen = "expected has " + expLen + " elements and actual has " 
                + actLen;
        assertEquals(msgLen, expLen, actLen);
        for (int i = 0; i < expLen; i++) {
            String msg = "Comparing element at position " + i;
            int expNum = expected.get(i);
            int actNum = actual.get(i);
            assertEquals(msg, expNum, actNum);
        }
    }
    
    /**
     * Test of the listPrimes function, of the EratosthenesSieve class.
     */
    @Test
    public void testListPrimes() {
        System.out.println("listPrimes");
        int threshold = 10;
        Integer[] smallPrimes = {2, 3, 5, 7};
        List<Integer> expected = Arrays.asList(smallPrimes);
        List<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertContainsSame(expected, actual);
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
        List<Integer> expected = Arrays.asList(smallPrimes);
        List<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertContainsSame(expected, actual);
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
        List<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        System.out.println("EratosthenesSieve reports " + actual.size() 
                + " primes between 1 and " + threshold);
        threshold = 20;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19};
        List<Integer> expected = Arrays.asList(smallPrimes);
        actual = EratosthenesSieve.listPrimes(threshold);
        assertContainsSame(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * Modifications to a received list of primes should not affect the list of 
     * primes held by EratosthenesSieve.
     */
    @Test
    public void testModifyPrimeSubset() {
        int threshold = 20;
        List<Integer> subset = EratosthenesSieve.listPrimes(threshold);
        for (int i = 0; i < subset.size(); i++) {
            int p = -subset.get(i);
            subset.set(i, p);
        }
        threshold = 40;
        Integer[] smallPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        List<Integer> expected = Arrays.asList(smallPrimes);
        List<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertContainsSame(expected, actual);
        subset.clear();
        threshold = 139;
        Integer[] fewMorePrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,  
            41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 
            109, 113, 127, 131, 137, 139};
        expected = Arrays.asList(fewMorePrimes);
        actual = EratosthenesSieve.listPrimes(threshold);
        assertContainsSame(expected, actual);
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
        List<Integer> expected = Arrays.asList(smallPrimes);
        List<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertContainsSame(expected, actual);
    }
    
    /**
     * Another test of the listPrimes function, of the EratosthenesSieve class. 
     * A threshold of 0 should give an empty list, not cause any exception.
     */
    @Test
    public void testThresholdZeroGivesEmptyList() {
        int threshold = 0;
        try {
            List<Integer> list = EratosthenesSieve.listPrimes(threshold);
            assertEquals(0, list.size());
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
        try {
            List<Integer> list = EratosthenesSieve.listPrimes(threshold);
            assertEquals(0, list.size());
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred for threshold " + threshold;
            fail(msg);
        }
    }
    
    @Test
    public void testListPrimesFromRange() {
        int start = 100;
        int threshold = 110;
        Integer[] primeQuadruplet = {101, 103, 107, 109};
        List<Integer> expected = Arrays.asList(primeQuadruplet);
        List<Integer> actual = EratosthenesSieve.listPrimes(start, threshold);
        assertContainsSame(expected, actual);
    }
    
    private static List<Integer> filterMod(List<Integer> list, int n, int m) {
        return list.stream().filter(p -> p % m == n)
                .collect(Collectors.toList());
    }
    
    @Test
    public void testListPrimesMod() {
        int modulus = RANDOM.nextInt(64) + 36;
        int threshold = modulus * modulus / 2;
        List<Integer> primes = EratosthenesSieve.listPrimes(threshold);
        for (int n = 0; n < modulus; n++) {
            List<Integer> expected = filterMod(primes, n, modulus);
            List<Integer> actual = EratosthenesSieve.listPrimesMod(n, modulus)
                    .stream().filter(p -> p <= threshold)
                    .collect(Collectors.toList());
            assertContainsSame(expected, actual);
        }
    }
    
}
