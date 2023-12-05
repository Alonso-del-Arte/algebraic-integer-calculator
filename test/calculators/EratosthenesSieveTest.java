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
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
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
    
    private static void assertPrime(int p) {
        if (p == -2 || p == 2) return;
        if (p % 2 == 0) {
            String message = "Number " + p 
                    + " is said to be prime but it's even and composite";
            fail(message);
        }
        double squareRoot = Math.sqrt(Math.abs(p));
        for (int d = 3; d < squareRoot; d += 2) {
            boolean notDivisible = p % d != 0;
            String message = "Number " + p 
                    + " is said to be prime, it should not be divisible by " 
                    + d;
            assert notDivisible : message;
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
     * Negative threshold should give negative prime numbers, in order from 
     * closest to zero to farthest from zero.
     */
    @Test
    public void testListPrimesTurnsNegativeToPositive() {
        int threshold = -30;
        Integer[] smallPrimes = {-2, -3, -5, -7, -11, -13, -17, -19, -23, -29};
        List<Integer> expected = Arrays.asList(smallPrimes);
        List<Integer> actual = EratosthenesSieve.listPrimes(threshold);
        assertEquals(expected, actual);
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
    
    @Test
    public void testRandomPrime() {
        System.out.println("randomPrime");
        int bound = 1000;
        int numberOfCalls = RANDOM.nextInt(bound / 10) + 100;
        Set<Integer> primes = new HashSet<>();
        for (int i = 0; i < numberOfCalls; i++) {
            int prospectivePrime = EratosthenesSieve.randomPrime(bound);
            String msg = "Number " + prospectivePrime 
                    + " is said to be a prime bounded between 0 and " + bound;
            assert prospectivePrime > 0 && prospectivePrime < bound : msg;
            assertPrime(prospectivePrime);
            primes.add(prospectivePrime);
        }
        int expected = 11 * numberOfCalls / 20;
        int actual = primes.size();
        String msg = "Expected at least " + expected + " distinct primes for " 
                + numberOfCalls + " calls, got " + actual;
        assert expected < actual : msg;
    }
    
    @Test
    public void testRandomPrimeBadBoundCausesException() {
        int bound = RANDOM.nextInt(3) - 1;
        try {
            int badPrime = EratosthenesSieve.randomPrime(bound);
            String message = "Bad bound " + bound 
                    + " should've caused exception, not given result " 
                    + badPrime;
            fail(message);
        } catch (NoSuchElementException nsee) {
            System.out.println("Bad bound " + bound 
                    + " correctly caused NoSuchElementException");
            String excMsg = nsee.getMessage();
            assert excMsg != null : "Exception message should not be null";
            assert !(excMsg.isBlank() || excMsg.isEmpty()) 
                    : "Exception message should not be blank nor empty";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for bad bound " + bound;
            fail(message);
        }
    }
    
    @Test
    public void testRandomOddPrime() {
        System.out.println("randomOddPrime");
        int bound = 1000;
        int numberOfCalls = RANDOM.nextInt(bound / 10) + 100;
        Set<Integer> primes = new HashSet<>();
        for (int i = 0; i < numberOfCalls; i++) {
            int prospectivePrime = EratosthenesSieve.randomOddPrime(bound);
            String msg = "Number " + prospectivePrime 
                    + " is said to be an odd prime bounded between 0 and " 
                    + bound;
            assert prospectivePrime % 2 != 0 : msg;
            assert prospectivePrime > 0 && prospectivePrime < bound : msg;
            assertPrime(prospectivePrime);
            primes.add(prospectivePrime);
        }
        int expected = 53 * numberOfCalls / 100;
        int actual = primes.size();
        String msg = "Expected at least " + expected 
                + " distinct odd primes for " + numberOfCalls + " calls, got " 
                + actual;
        assert expected < actual : msg;
    }
    
    @Test
    public void testRandomOddPrimeBadBoundCausesException() {
        int bound = RANDOM.nextInt(5) - 2;
        try {
            int badPrime = EratosthenesSieve.randomOddPrime(bound);
            String message = "Bad bound " + bound 
                    + " should've caused exception, not given result " 
                    + badPrime;
            fail(message);
        } catch (NoSuchElementException nsee) {
            System.out.println("Bad bound " + bound 
                    + " correctly caused NoSuchElementException");
            String excMsg = nsee.getMessage();
            assert excMsg != null : "Exception message should not be null";
            assert !(excMsg.isBlank() || excMsg.isEmpty()) 
                    : "Exception message should not be blank nor empty";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for bad bound " + bound;
            fail(message);
        }
    }
    
    @Test
    public void testRandomPrimeOtherThan() {
        System.out.println("randomPrimeOtherThan");
        int threshold = 2000;
        List<Integer> pooledPrimes = EratosthenesSieve.listPrimes(threshold);
        int numberOfCalls = pooledPrimes.size();
        Set<Integer> primes = new HashSet<>();
        for (int p : pooledPrimes) {
            int otherPrime = EratosthenesSieve.randomPrimeOtherThan(p);
            assertPrime(otherPrime);
            String msg = "Prime " + otherPrime + " should be prime other than " 
                    + p;
            assert otherPrime != p : msg;
            primes.add(otherPrime);
        }
        int expected = 53 * numberOfCalls / 100;
        int actual = primes.size();
        String msg = "Expected at least " + expected + " distinct primes for " 
                + numberOfCalls + " calls, got " + actual;
        assert expected < actual : msg;
    }
    
    @Test
    public void testRandomPrimeOtherThanMatchesSignOfP() {
        int threshold = 500;
        List<Integer> pooledPrimes = EratosthenesSieve.listPrimes(threshold);
        for (int p : pooledPrimes) {
            int otherPositivePrime = EratosthenesSieve.randomPrimeOtherThan(p);
            String msg = "Both " + p + " and " + otherPositivePrime 
                    + " should be positive";
            assert p > 0 && otherPositivePrime > 0 : msg;
            int otherNegativePrime = EratosthenesSieve.randomPrimeOtherThan(-p);
            msg = "Both -" + p + " and " + otherNegativePrime 
                    + " should be negative";
            assert otherNegativePrime < 0 : msg;
        }
    }
    
    @Test
    public void testRandomPrimeOtherThanNegativePDoesNotMatchP() {
        int threshold = 200;
        List<Integer> pooledPrimes = EratosthenesSieve.listPrimes(threshold);
        int numberOfCalls = 200;
        for (int p : pooledPrimes) {
            for (int i = 0; i < numberOfCalls; i++) {
                int otherNegativePrime 
                        = EratosthenesSieve.randomPrimeOtherThan(-p);
                String msg = "Prime " + otherNegativePrime + " other than -" + p 
                        + " should not be -" + p;
                assert otherNegativePrime != -p : msg;
            }
        }
    }
    
    
    @Test
    public void testRandomPrime1Mod4() {
        int n = 1;
        int m = 4;
        Set<Integer> expected = new HashSet<>(EratosthenesSieve
                .listPrimesMod(n, m));
        Set<Integer> actual = new HashSet<>();
        int numberOfCalls = 20 * expected.size();
        for (int i = 0; i < numberOfCalls; i++) {
            int prospectivePrime = EratosthenesSieve.randomPrimeMod(n, m);
            assertPrime(prospectivePrime);
            String message = "Prime " + prospectivePrime 
                    + " should be congruent to " + n + " modulo " + m;
            assertEquals(message, n, prospectivePrime % m);
            actual.add(prospectivePrime);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testRandomPrime3Mod4() {
        int n = 3;
        int m = 4;
        Set<Integer> expected = new HashSet<>(EratosthenesSieve
                .listPrimesMod(n, m));
        Set<Integer> actual = new HashSet<>();
        int numberOfCalls = 20 * expected.size();
        for (int i = 0; i < numberOfCalls; i++) {
            int prospectivePrime = EratosthenesSieve.randomPrimeMod(n, m);
            assertPrime(prospectivePrime);
            String message = "Prime " + prospectivePrime 
                    + " should be congruent to " + n + " modulo " + m;
            assertEquals(message, n, prospectivePrime % m);
            actual.add(prospectivePrime);
        }
        assertEquals(expected, actual);
    }
    
    @Test
    public void testRandomPrimeMod() {
        int start = 100;
        int threshold = start * start;
        int m = start + RANDOM.nextInt(start);
        List<Integer> somePrimes = EratosthenesSieve.listPrimes(threshold);
        for (int n = 1; n < m; n++) {
            List<Integer> filtered = filterMod(somePrimes, n, m);
            if (!filtered.isEmpty()) {
                int prospectivePrime = EratosthenesSieve.randomPrimeMod(n, m);
                assertPrime(prospectivePrime);
                String msg = "Prime " + prospectivePrime 
                        + " should be congruent to " + n + " modulo " + m;
                assert NumberTheoreticFunctionsCalculator
                        .mod(prospectivePrime, m) == n : msg;
            }
        }
    }
    
    @Test
    public void testRandomPrimeModForNoneAvailable() {
        int root = RANDOM.nextInt(128) + 2;
        int n = root * root;
        int m = n * root * (RANDOM.nextInt(4) + 1);
        String msgPart = "Trying to get a prime congruent to " + n + " modulo " 
                + m + ' ';
        try {
            int badPrime = EratosthenesSieve.randomPrimeMod(n, m);
            String message = msgPart 
                    + "should have caused an exception, not given result " 
                    + badPrime;
            fail(message);
        } catch (NoSuchElementException nsee) {
            System.out.println(msgPart 
                    + "correctly caused NoSuchElementException");
            String excMsg = nsee.getMessage();
            String msg = "Exception message should contain parameters n = " + n 
                    + " and m = " + m;
            String nStr = Integer.toString(n);
            String mStr = Integer.toString(m);
            assert excMsg.contains(nStr) : msg;
            assert excMsg.contains(mStr) : msg;
            System.out.println("\"" + excMsg + "\"");            
        } catch (RuntimeException re) {
            String message = msgPart + "caused " + re.getClass().getName() 
                    + " instead of NoSuchElementException";
            fail(message);
        }
    }
    
}
