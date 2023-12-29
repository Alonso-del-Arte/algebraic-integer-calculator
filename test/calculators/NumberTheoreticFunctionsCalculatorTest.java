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

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import algebraics.UnsupportedNumberDomainException;
import algebraics.cubics.BadCubicRing;
import algebraics.quadratics.IllDefinedQuadraticInteger;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import arithmetic.NonEuclideanDomainException;
import arithmetic.NonUniqueFactorizationDomainException;
import arithmetic.NotDivisibleException;
import arithmetic.comparators.NormAbsoluteComparator;

import static calculators.NumberTheoreticFunctionsCalculator.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for a collection of number theoretic functions, including basic 
 * primality testing and the Euclidean GCD algorithm. Some of these tests use a 
 * finite list of small primes and another finite list of non-prime numbers, as 
 * well as a small list of Fibonacci numbers.
 * <p>The most relevant entries in Sloane's On-Line Encyclopedia of Integer 
 * Sequences (OEIS) are:</p>
 * <ul>
 * <li><a href="http://oeis.org/A000040">A000040</a>: The prime numbers</li>
 * <li><a href="http://oeis.org/A000045">A000045</a>: The Fibonacci numbers</li>
 * <li><a href="http://oeis.org/A002808">A002808</a>: The composite numbers</li>
 * <li><a href="http://oeis.org/A018252">A018252</a>: 1 and the composite 
 * numbers</li>
 * </ul>
 * @author Alonso del Arte
 */
public class NumberTheoreticFunctionsCalculatorTest {
    
    /**
     * {@link #setUpClass() setUpClass()} will generate a List of the first few 
     * consecutive primes. This constant determines how long that list will be. 
     * For example, if it's 1000, setUpClass() will generate a list of the 
     * primes between 1 and 1000. It should not be greater than {@link 
     * Integer#MAX_VALUE}.
     */
    public static final int PRIME_LIST_THRESHOLD = 1000;
    
    /**
     * A list of the first few prime numbers, to be used in some of the tests. 
     * It will be populated during {@link #setUpClass() setUpClass()}.
     */
    private static List<Integer> primesList;
    
    /**
     * A list of the first few squarefree numbers, to be used in some of the 
     * tests. It will be populated during {@link #setUpClass() setUpClass()}.
     */
    private static List<Integer> squarefreesList;
    
    /**
     * The size of primesList, to be determined during {@link #setUpClass() 
     * setUpClass()}. This value will be reported on the console before the 
     * tests begin.
     */
    private static int primesListLength;
    
    /**
     * A list of composite numbers, which may or may not include 
     * {@link #PRIME_LIST_THRESHOLD PRIME_LIST_THRESHOLD}. It will be populated 
     * during {@link #setUpClass() setUpClass()}.
     */
    private static List<Integer> compositesList;
    
    /**
     * A list of Fibonacci numbers. It will be populated during {@link 
     * #setUpClass() setUpClass()}.
     */
    private static List<Integer> fibonacciList;
    
    /**
     * An array of what I'm calling, for lack of a better term, "Heegner 
     * companion primes." For each Heegner number <i>d</i>, the Heegner 
     * companion prime <i>p</i> is a number that is prime in <b>Z</b> and is 
     * also prime in the ring of integers of 
     * <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub>. This array will be 
     * populated during setUpClass().
     */
    private static final int[] HEEGNER_COMPANION_PRIMES = new int[9];
    
    private static final Random RANDOM = new Random();
    
    private static void assertSquarefreeLimited(int n) {
        for (int p : primesList) {
            int squaredPrime = p * p;
            int remainder = n % squaredPrime;
            String message = "Number " + n  
                    + " said to be squarefree shouldn't be divisible by " 
                    + squaredPrime;
            assertNotEquals(message, 0, remainder);
        }
    }
    
    private static void assertSquarefree(int n) {
        int root = (int) Math.ceil(Math.sqrt(Math.abs(n)));
        for (int d = 2; d < root; d++) {
            int dSquared = d * d;
            int remainder = n % dSquared;
            String message = "Number " + n 
                    + " said to be squarefree should not be divisible by " 
                    + dSquared;
            assertNotEquals(message, 0, remainder);
        }
    }
    
    private static void setUpPrimeList() {
        int halfThreshold = PRIME_LIST_THRESHOLD / 2;
        primesList = new ArrayList<>(PRIME_LIST_THRESHOLD / 5);
        primesList.add(2);
        boolean[] primeFlags = new boolean[halfThreshold];
        for (int i = 0; i < halfThreshold; i++) {
            primeFlags[i] = true;
        }
        int currPrime = 3;
        int twiceCurrPrime, currIndex;
        while (currPrime < PRIME_LIST_THRESHOLD) {
            primesList.add(currPrime);
            twiceCurrPrime = 2 * currPrime;
            for (int j = currPrime * currPrime; j < PRIME_LIST_THRESHOLD; 
                    j += twiceCurrPrime) {
                currIndex = (j - 3) / 2;
                primeFlags[currIndex] = false;
            }
            do {
                currPrime += 2;
                currIndex = (currPrime - 3) / 2;
            } while (currIndex < (halfThreshold - 1) && !primeFlags[currIndex]);
        }
        primesListLength = primesList.size();
        compositesList = new ArrayList<>(PRIME_LIST_THRESHOLD 
                - primesListLength);
        for (int c = 4; c < PRIME_LIST_THRESHOLD; c += 2) {
            compositesList.add(c);
            if (!primeFlags[c / 2 - 1]) {
                compositesList.add(c + 1);
            }
        }
    }
    
    private static void setUpFibonacciList() {
        int initialCapacity = 50;
        fibonacciList = new ArrayList<>(initialCapacity);
        fibonacciList.add(0);
        fibonacciList.add(1);
        int threshold = (Integer.MAX_VALUE - 3) / 4;
        int currIndex = 2;
        int currFibo = 1;
        while (currFibo < threshold) {
            currFibo = fibonacciList.get(currIndex - 2) 
                    + fibonacciList.get(currIndex - 1);
            fibonacciList.add(currFibo);
            currIndex++;
        }
        currIndex--;
    }
    
    private static void setUpHeegnerList() {
        int absD, currDiff, currCompCand, currSqrIndex, currSqrDMult, currPrime;
        boolean numNotFoundYet;
        for (int d = 0; d < HEEGNER_NUMBERS.length; d++) {
            absD = (-1) * HEEGNER_NUMBERS[d];
            int currIndex = 0;
            do {
                currPrime = primesList.get(currIndex);
                currIndex++;
            } while (currPrime <= absD);
            numNotFoundYet = true;
            while (numNotFoundYet) {
                currCompCand = 4 * currPrime;
                currSqrIndex = 1;
                currDiff = absD;
                while (currDiff > 0) {
                    currSqrDMult = absD * currSqrIndex * currSqrIndex;
                    currDiff = currCompCand - currSqrDMult;
                    if (Math.sqrt(currDiff) 
                            == Math.floor(Math.sqrt(currDiff))) {
                        currDiff = 0;
                    }
                    currSqrIndex++;
                }
                if (currDiff < 0) {
                    numNotFoundYet = false;
                } else {
                    currIndex++;
                    currPrime = primesList.get(currIndex);
                }
            }
            HEEGNER_COMPANION_PRIMES[d] = currPrime;
        }
    }
    
    /**
     * Sets up a list of the first few consecutive primes, the first few 
     * composite numbers, the first few Fibonacci numbers and the Heegner 
     * "companion primes," for lack of a better term. This provides most of what 
     * is needed for the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        setUpPrimeList();
        setUpFibonacciList();
        setUpHeegnerList();
    }
    
    /**
     * Originally a test of sortListAlgebraicIntegersByNorm method, of class 
     * NumberTheoreticFunctionsCalculator, which was deprecated and then 
     * removed. Now it's a redundant test for {@link 
     * algebraics.NormAbsoluteComparator}. If norms may be negative, the 
     * algebraic integers should be sorted by the absolute value of the norm. No 
     * expectation is laid out for what should happen if the algebraic integers 
     * to be sorted come from different rings.
     */
    @Test
    public void testSortListAlgebraicIntegersByNorm() {
        System.out.println("sortListAlgebraicIntegersByNorm was deprecated and removed");
        System.out.println("This test actually now uses algebraics.NormAbsoluteComparator");
        QuadraticRing ring = new ImaginaryQuadraticRing(-7);
        QuadraticInteger numberA = new ImaginaryQuadraticInteger(1, 0, ring); // Unit
        QuadraticInteger numberB = new ImaginaryQuadraticInteger(-1, 1, ring, 2); // -1/2 + sqrt(-7)/2, norm 2
        QuadraticInteger numberC = new ImaginaryQuadraticInteger(1, 2, ring); // 1 + 2sqrt(-7), norm 29
        List<AlgebraicInteger> expected = new ArrayList<>();
        expected.add(numberA);
        expected.add(numberB);
        expected.add(numberC);
        List<AlgebraicInteger> actual = new ArrayList<>();
        actual.add(numberC);
        actual.add(numberA);
        actual.add(numberB);
        Comparator comparator = new NormAbsoluteComparator();
        actual.sort(comparator);
        assertEquals(expected, actual);
        ring = new RealQuadraticRing(7);
        numberA = new RealQuadraticInteger(8, 3, ring); // Unit
        numberB = new RealQuadraticInteger(3, -1, ring); // 3 - sqrt(-7), norm 2
        numberC = new RealQuadraticInteger(18, 7, ring); // 18 + 7sqrt(7), norm -19
        expected.clear();
        expected.add(numberA);
        expected.add(numberB);
        expected.add(numberC);
        actual.clear();
        actual.add(numberC);
        actual.add(numberA);
        actual.add(numberB);
        actual.sort(comparator);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMod() {
        System.out.println("mod");
        int m = RANDOM.nextInt(256) + 4;
        int numberOfSpans = 12;
        int stop = numberOfSpans * m;
        for (int i = -stop; i < stop; i += m) {
            for (int expected = 0; expected < m; expected++) {
                int n = i + expected;
                int actual = mod(n, m);
                String message = "Expecting " + n + " modulo " + m + " to be " 
                        + expected;
                assertEquals(message, expected, actual);
            }
        }
    }
    
    @Test
    public void testModZero() {
        int n = RANDOM.nextInt();
        try {
            int badResidue = mod(n, 0);
            String message = "Trying to calculate " + n 
                    + " modulo 0 should not have given " + badResidue;
            fail(message);
        } catch (ArithmeticException ae) {
            String excMsg = ae.getMessage();
            assert excMsg != null : "Exception message should not be null";
            String msg = "Exception message should contain parameter n = " + n;
            assert excMsg.contains(Integer.toString(n)) : msg;
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for " + n + " modulo 0";
            fail(message);
        }
    }
    
    @Test
    public void testModLong() {
        long m = 1L << 56;
        int bound = 256;
        long expected = RANDOM.nextLong(bound);
        for (int i = 0; i < bound; i++) {
            long n = Long.MIN_VALUE + m * i + expected;
            long actual = mod(n, m);
            String message = "Expecting " + n + " modulo " + m + " to be " 
                    + expected;
            assertEquals(message, expected, actual);
        }
    }
    
    @Test
    public void testModZeroLong() {
        long n = ((long) Integer.MAX_VALUE) + RANDOM.nextInt();
        try {
            long badResidue = mod(n, 0);
            String message = "Trying to calculate " + n 
                    + " modulo 0 should not have given " + badResidue;
            fail(message);
        } catch (ArithmeticException ae) {
            String excMsg = ae.getMessage();
            assert excMsg != null : "Exception message should not be null";
            String msg = "Exception message should contain parameter n = " + n;
            assert excMsg.contains(Long.toString(n)) : msg;
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for " + n + " modulo 0";
            fail(message);
        }
    }
    
    /**
     * Test of primeFactors method, of class NumberTheoreticFunctionsCalculator.
     * This test uses squares of primorials (4, 36, 900, 44100, etc.) and 
     * certain divisors of those numbers. This test also checks the 
     * factorization of the additive inverses of those numbers (e.g., 
     * &minus;44100). The expectation is that if &minus;<i>n</i> is a negative 
     * number, its factorization will be the same as that of <i>n</i>, but with 
     * a single &minus;1 at the beginning. This test does not set out any 
     * expectations for the factorization of 0.
     * <p>There is also testing of the prime factorizations of a few quadratic 
     * integers. Calling the prime factorization function on quadratic integers 
     * from domains that are not unique factorization domains should always 
     * trigger {@link NonUniqueFactorizationDomainException} even when those 
     * numbers are the products of primes in those domains. Testing of {@link 
     * NonUniqueFactorizationDomainException#tryToFactorizeAnyway()} should of 
     * course occur in {@link 
     * algebraics.NonUniqueFactorizationDomainExceptionTest}, not here.</p>
     * <p>Generally factors of numbers in quadratic integer rings should be 
     * given in order by norm. However, this is not required by these tests, 
     * which in some cases use sets rather than lists to make sure the returned 
     * factors are the expected factors, without regard for ordering.</p>
     * <p>For factorization in <b>Z</b>, it is a reasonable expectation that the 
     * factors be in ascending order, so the expected result is constructed as a 
     * list, not a set.</p>
     */
    // TODO: Break this test up into smaller tests
    @Test
    public void testPrimeFactors() {
        System.out.println("primeFactors(int)");
        int num = 1;
        int primeIndex = 0;
        List<Integer> result = new ArrayList<>();
        List<Integer> expResult = new ArrayList<>();
        boolean withinRange = true;
        int lastNumTested = 1;
        while (withinRange) {
            num *= primesList.get(primeIndex);
            expResult.add(primesList.get(primeIndex));
            /* Check the number has not overflown the integer data type and that
            we're not going beyond our finite list of primes */
            withinRange = (num > 0 && num < Integer.MAX_VALUE) && (primeIndex < primesListLength - 1);
            if (withinRange) {
                result = NumberTheoreticFunctionsCalculator.primeFactors(num);
                assertEquals(result, expResult);
                num *= -1; // Make num negative
                expResult.add(0, -1);
                result = NumberTheoreticFunctionsCalculator.primeFactors(num);
                assertEquals(result, expResult);
                lastNumTested = num;
                num *= -1; // And back to positive
                expResult.remove(0);
                // Now to test factorization on the square of a primorial
                num *= primesList.get(primeIndex);
                expResult.add(primesList.get(primeIndex));
                // Checking for integer data type overflow only at this point
                withinRange = (num > 0 && num < Integer.MAX_VALUE);
                if (withinRange) {
                    result = NumberTheoreticFunctionsCalculator.primeFactors(num);
                    assertEquals(result, expResult);
                    num *= -1; // Make num negative
                    expResult.add(0, -1);
                    result = NumberTheoreticFunctionsCalculator.primeFactors(num);
                    assertEquals(result, expResult);
                    lastNumTested = num;
                    num *= -1; // And back to positive
                    expResult.remove(0);
                }
            }
            primeIndex++;
        }
        System.out.print("Last integer in Z tested was " + lastNumTested + ", which has this factorization: " + result.get(0));
        for (int i = 1; i < result.size(); i++) {
            System.out.print(" \u00D7 ");
            System.out.print(result.get(i));
        }
        System.out.println(" ");
        // Now to test primeFactors() on imaginary quadratic integers
        System.out.println("primeFactors(ImaginaryQuadraticInteger)");
        QuadraticRing r;
        QuadraticInteger z = NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY.times(-1);
        /* The arbitrary initialization of z with -omega and factorsList with 
           omega is to avoid "variable might not have been initialized" 
           errors */
        List<AlgebraicInteger> factorsList = new ArrayList<>();
        factorsList.add(NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY);
        int facLen;
        String assertionMessage;
        for (int d : NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS) {
            r = new ImaginaryQuadraticRing(d);
            /* First to test purely real integers that are prime in Z in the 
               context of a particular ring r, e.g., 5 in Z[sqrt(-2)] */
            for (Integer p : primesList) {
                z = new ImaginaryQuadraticInteger(p, 0, r);
                try {
                    factorsList = NumberTheoreticFunctionsCalculator.primeFactors(z);
                    facLen = factorsList.size();
                } catch (NonUniqueFactorizationDomainException nufde) {
                    facLen = 0;
                    fail("NonUniqueFactorizationDomainException should not have happened in this context: " + nufde.getMessage());
                }
                if (NumberTheoreticFunctionsCalculator.isPrime(z)) {
                    assertionMessage = "Factor list of " + z.toString() + " in " + z.getRing().toString() + " should contain just one prime factor.";
                    assertEquals(assertionMessage, 1, facLen);
                } else {
                    assertionMessage = "Factor list of " + z.toString() + " in " + z.getRing().toString() + " should contain two or three factors.";
                    assertTrue(assertionMessage, facLen > 1);
                }
            }
            // Next, to look at some consecutive algebraic integers
            int expFacLen;
            for (int a = -4; a < 6; a++) {
                for (int b = 3; b > -2; b--) {
                    z = new ImaginaryQuadraticInteger(a, b, r);
                    try {
                        factorsList = NumberTheoreticFunctionsCalculator.primeFactors(z);
                        facLen = factorsList.size();
                        expFacLen = 0;
                        if ((factorsList.get(0).norm() == 1) && (factorsList.size() > 1)) {
                            expFacLen = 1;
                        }
                        if (z.norm() > 1) {
                            expFacLen++;
                        }
                        if (NumberTheoreticFunctionsCalculator.isPrime(z)) {
                            assertionMessage = z.toString() + " is expected to have " + expFacLen + " factor(s).";
                            assertEquals(assertionMessage, expFacLen, facLen);
                        } else {
                            expFacLen++;
                            assertionMessage = z.toString() + " is expected to have at least " + expFacLen + " factors.";
                            assertTrue(assertionMessage, facLen >= expFacLen);
                        }
                    } catch (NonUniqueFactorizationDomainException nufde) {
                        fail("NonUniqueFactorizationDomainException should not have happened in this context: " + nufde.getMessage());
                    }
                }
            }
            System.out.print("Last algebraic integer tested in " + r.toASCIIString() + " was " + z.toASCIIString() + ", which has this factorization: ");
            if (factorsList.get(0).getImagPartNumeric() == 0.0) {
                System.out.print(factorsList.get(0).toASCIIString());
            } else {
                System.out.print("(" + factorsList.get(0) + ")");
            }
            for (int currFactorIndex = 1; currFactorIndex < factorsList.size(); currFactorIndex++) {
                if (factorsList.get(currFactorIndex).getImagPartNumeric() == 0.0) {
                    System.out.print(" \u00D7 " + factorsList.get(currFactorIndex).toASCIIString());
                } else {
                    System.out.print(" \u00D7 (" + factorsList.get(currFactorIndex).toASCIIString() + ")");
                }
            }
            System.out.println();
        }
        // Now to test primeFactors() on real quadratic integers
        System.out.println("primeFactors(RealQuadraticInteger)");
        RealQuadraticInteger ramifier, ramified;
        List<AlgebraicInteger> expFactorsList;
        int splitter, splitPrimeNorm;
        RealQuadraticInteger splitPrime;
        HashSet<AlgebraicInteger> expFactorsSet, factorsSet;
        for (int discrR : NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D) {
            r = new RealQuadraticRing(discrR);
            ramifier = new RealQuadraticInteger(0, 1, r);
            ramified = new RealQuadraticInteger(discrR, 0, r);
            expFactorsList = new ArrayList<>();
            expFactorsList.add(ramifier);
            expFactorsList.add(ramifier); // Add the ramifier in there twice (on purpose)
            try {
                factorsList = NumberTheoreticFunctionsCalculator.primeFactors(ramified);
                facLen = factorsList.size();
                System.out.print(discrR + " = (" + factorsList.get(0).toASCIIString() + ")");
                for (int j = 1; j < facLen; j++) {
                    System.out.print(" \u00D7 (" + factorsList.get(j).toASCIIString() + ")");
                }
                System.out.println();
                if (NumberTheoreticFunctionsCalculator.isPrime(discrR)) {
                    assertionMessage = "Factorization of " + discrR + " in " + r.toASCIIString() + " should have exactly two factors.";
                    assertEquals(assertionMessage, 2, facLen);
                    if (discrR != 5) {
                        assertionMessage = "Factorization of " + discrR + " in " + r.toASCIIString() + " should be (" + ramifier.toASCIIString() + ")^2.";
                        assertEquals(assertionMessage, expFactorsList, factorsList);
                    }
                } else {
                    assertionMessage = "Factorization of " + discrR + " in " + r.toASCIIString() + " should have at least four factors.";
                    assertTrue(assertionMessage, facLen > 3);
                }
            } catch (NonUniqueFactorizationDomainException nufde) {
                fail("NonUniqueFactorizationDomainException should not have happened in this context: " + nufde.getMessage());
            }
            /* For this next test, we need a prime that splits, not ramifies, in 
               each of the real quadratic rings that are norm-Euclidean */
            splitter = -1;
            if (r.hasHalfIntegers()) {
                if (discrR == 5) {
                    splitPrimeNorm = 11;
                    splitPrime = new RealQuadraticInteger(7, 1, r, 2);
                    expFactorsSet = new HashSet<>();
                    expFactorsSet.add(splitPrime.conjugate());
                    expFactorsSet.add(splitPrime);
                } else {
                    do {
                        splitter += 2;
                        splitPrimeNorm = (splitter * splitter - discrR)/4;
                    } while (!NumberTheoreticFunctionsCalculator.isPrime(splitPrimeNorm));
                    splitPrime = new RealQuadraticInteger(splitter, 1, r, 2);
                    expFactorsSet = new HashSet<>();
                    expFactorsSet.add(splitPrime.conjugate());
                    expFactorsSet.add(splitPrime);
                }
            } else {
                if (discrR % 2 == 1) {
                    splitter++;
                }
                do {
                    splitter += 2;
                    splitPrimeNorm = splitter * splitter - discrR;
                } while (!NumberTheoreticFunctionsCalculator.isPrime(splitPrimeNorm));
                splitPrime = new RealQuadraticInteger(splitter, 1, r);
                expFactorsSet = new HashSet<>();
                expFactorsSet.add(splitPrime.conjugate());
                expFactorsSet.add(splitPrime);
            }
            System.out.println(splitPrimeNorm + " = (" + splitPrime.conjugate().toASCIIString() + ") \u00D7 (" + splitPrime.toASCIIString() + ")");
            z = new RealQuadraticInteger(splitPrimeNorm, 0, r);
            try {
                factorsList = NumberTheoreticFunctionsCalculator.primeFactors(z);
                assertionMessage = "Factorization of " + splitPrimeNorm + " in " + r.toString() + " should consist of two exactly prime factors";
                assertEquals(assertionMessage, 2, factorsList.size());
                factorsSet = new HashSet(factorsList);
                assertionMessage = "Factorization of " + splitPrimeNorm + " in " + r.toString() + " should be " + splitPrime.toString() + " times its conjugate";
                assertEquals(assertionMessage, expFactorsSet, factorsSet);
            } catch (NonUniqueFactorizationDomainException nufde) {
                fail("NonUniqueFactorizationDomainException should not have happened in this context: " + nufde.getMessage());
            }
        }
        /* And lastly to make sure calling prime factorization on a number from 
           an unsupported domain triggers UnsupportedNumberDomainException */
        r = new IllDefinedQuadraticRing(1);
        z = new IllDefinedQuadraticInteger(7, 8, r);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(z);
            System.out.print("Calling primeFactors(" + z.toASCIIString() + " should have triggered UnsupportedNumberDomainException, ");
            System.out.println("not given the result in which " + factorsList.get(0).toASCIIString() + " is a factor.");
            fail("Calling primeFactors(" + z.toString() + ") should have triggered UnsupportedNumberDomainException.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            fail("NonUniqueFactorizationDomainException should not have happened in this context: " + nufde.getMessage());
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Calling primeFactors(" + z.toASCIIString() + ") correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        }
    }
    
    @Test
    public void testPrimeFactorsFromUFDNotEuclidean() {
        RealQuadraticRing ring = new RealQuadraticRing(97);
        RealQuadraticInteger number = new RealQuadraticInteger(345, 35, ring, 2);
        try {
            List<AlgebraicInteger> factors = primeFactors(number);
            System.out.print("Factors of " + number.toASCIIString() 
                    + " are said to be: ");
            int lastIndex = factors.size() - 1;
            for (int i = 0; i < lastIndex; i++) {
                System.out.print(factors.get(i).toASCIIString() + ", ");
            }
            System.out.println(factors.get(lastIndex) + ".");
            QuadraticInteger product = new RealQuadraticInteger(1, 0, ring);
            QuadraticInteger m;
            for (AlgebraicInteger factor : factors) {
                m = (QuadraticInteger) factor;
                product = product.times(m);
            }
            assertEquals(number, product);
        } catch (NonUniqueFactorizationDomainException nufde) {
            String msg = "Trying to factor " + number.toString() 
                    + " should not have caused NonUniqueFactorizationDomainException";
            System.out.println(msg);
            System.out.println("\"" + nufde.getMessage() + "\"");
            fail(msg);
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " is the wrong exception to throw for trying to factor " 
                    + number.toString();
            fail(msg);
        }
    }

    /**
     * Test of isPrime method, of class NumberTheoreticFunctionsCalculator. The 
     * numbers listed in Sloane's A000040, as well as those same numbers 
     * multiplied by &minus;1, should all be identified as prime. Likewise, the 
     * numbers listed in Sloane's A018252, as well as those same numbers 
     * multiplied by &minus;1, should all be identified as not prime. As for 0, 
     * I'm not sure; if you like you can uncomment the line for it and perhaps 
     * change assertFalse to assertTrue.
     */
    // TODO: Break this test up into smaller tests
    @Test
    public void testIsPrime() {
        System.out.println("isPrime(int)");
        // assertFalse(NumberTheoreticFunctionsCalculator.isPrime(0));
        for (int i = 0; i < primesListLength; i++) {
            assertTrue(NumberTheoreticFunctionsCalculator.isPrime(primesList.get(i)));
            assertTrue(NumberTheoreticFunctionsCalculator.isPrime(-primesList.get(i)));
        }
        assertFalse(NumberTheoreticFunctionsCalculator.isPrime(1));
        assertFalse(NumberTheoreticFunctionsCalculator.isPrime(-1));
        compositesList.stream().map((compositeNum) -> {
            assertFalse(NumberTheoreticFunctionsCalculator.isPrime(compositeNum));
            return compositeNum;
        }).forEachOrdered((compositeNum) -> {
            assertFalse(NumberTheoreticFunctionsCalculator.isPrime(-compositeNum));
        });
        /* Now we're going to test odd integers greater than the last prime 
        in our List but smaller than the square of that prime. */
        int maxNumForTest = primesList.get(primesListLength - 1) * primesList.get(primesListLength - 1);
        int primeIndex = 1; // Which of course corresponds to 3, not 2, in a zero-indexed array
        boolean possiblyPrime = true; // Presume k to be prime until finding otherwise
        for (int k = primesList.get(primesListLength - 1) + 2; k < maxNumForTest; k += 2) {
            while (primeIndex < primesListLength && possiblyPrime) {
                possiblyPrime = (k % primesList.get(primeIndex) != 0);
                primeIndex++;
            }
            if (possiblyPrime) {
                assertTrue(NumberTheoreticFunctionsCalculator.isPrime(k));
                assertTrue(NumberTheoreticFunctionsCalculator.isPrime(-k));
            } else {
                assertFalse(NumberTheoreticFunctionsCalculator.isPrime(k));
                assertFalse(NumberTheoreticFunctionsCalculator.isPrime(-k));
            }
            primeIndex = 1; // Reset for next k
            possiblyPrime = true; // Reset for next k
        }
        /* Next, we're going to test indices of Fibonacci primes greater than 4, 
           which corresponds to 3 */
        for  (int m = 5; m < fibonacciList.size(); m++) {
            if (NumberTheoreticFunctionsCalculator.isPrime(fibonacciList.get(m))) {
                assertTrue(NumberTheoreticFunctionsCalculator.isPrime(m));
            }
        }
        /* One more thing before moving on to complex UFDs: testing 
           isPrime(long) */
        System.out.println("isPrime(long)");
        long longNum = Integer.MAX_VALUE;
        String assertionMessage = "2^31 - 1 should be found to be prime.";
        assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(longNum));
        longNum++;
        assertionMessage = "2^31 should not be found to be prime.";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(longNum));
        longNum += 11;
        assertionMessage = "2^31 + 11 should be found to be prime.";
        assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(longNum));
        int castNum = (int) longNum;
        assertionMessage = "-(2^31) + 11 should not be found to be prime.";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(castNum));
        // That does it for testing isPrime in the context of Z.
        System.out.println("isPrime(QuadraticInteger)");
        ImaginaryQuadraticRing ufdRing;
        ImaginaryQuadraticInteger numberFromUFD;
        for (int d = 0; d < NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS.length; d++) {
            ufdRing = new ImaginaryQuadraticRing(NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS[d]);
            // d should not be prime in the ring of Q(sqrt(d))
            numberFromUFD = new ImaginaryQuadraticInteger(NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS[d], 0, ufdRing);
            assertFalse(NumberTheoreticFunctionsCalculator.isPrime(numberFromUFD));
            // Nor should d + 4 be prime even if it is prime in Z
            numberFromUFD = new ImaginaryQuadraticInteger(-NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS[d] + 4, 0, ufdRing);
            assertFalse(NumberTheoreticFunctionsCalculator.isPrime(numberFromUFD));
            // The "Heegner companion primes" should indeed be prime
            numberFromUFD = new ImaginaryQuadraticInteger(HEEGNER_COMPANION_PRIMES[d], 0, ufdRing);
            assertionMessage = numberFromUFD.toString() + " should have been identified as prime in " + ufdRing.toString();
            assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromUFD));
        }
        // There are some special cases to test in the Gaussian integers
        QuadraticInteger gaussianInteger;
        ImaginaryQuadraticInteger twoStepImagIncr = new ImaginaryQuadraticInteger(0, 2, NumberTheoreticFunctionsCalculator.RING_GAUSSIAN);
        for (int g = 3; g < 256; g += 4) {
            if (NumberTheoreticFunctionsCalculator.isPrime(g)) {
                gaussianInteger = new ImaginaryQuadraticInteger(0, g, NumberTheoreticFunctionsCalculator.RING_GAUSSIAN);
                assertionMessage = gaussianInteger.toString() + " should have been identified as prime in Z[i]";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(gaussianInteger));
                gaussianInteger = gaussianInteger.plus(twoStepImagIncr);
                assertionMessage = gaussianInteger.toString() + " should not have been identified as prime in Z[i]";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(gaussianInteger));
            }
        }
        gaussianInteger = new ImaginaryQuadraticInteger(0, 15, NumberTheoreticFunctionsCalculator.RING_GAUSSIAN);
        assertionMessage = gaussianInteger.toString() + " should not have been identified as prime in Z[i]";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(gaussianInteger));
        /* There are also special cases in the Eisenstein integers, thanks to 
           the units. If p is a purely real integer that is prime among the 
           Eisenstein integers, then omega * p, omega^2 * p, -omega * p and 
           -omega^2 * p should all be prime also. */
        QuadraticInteger eisensteinInteger;
        for (int eisen = -29; eisen < 30; eisen++) {
            eisensteinInteger = new ImaginaryQuadraticInteger(eisen, 0, NumberTheoreticFunctionsCalculator.RING_EISENSTEIN);
            if (NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger)) {
                eisensteinInteger = eisensteinInteger.times(NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY);
                assertionMessage = eisensteinInteger.toString() + " should be found to be prime.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger));
                eisensteinInteger = eisensteinInteger.times(NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY);
                assertionMessage = eisensteinInteger.toString() + " should be found to be prime.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger));
                eisensteinInteger = eisensteinInteger.times(NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY);
                eisensteinInteger = eisensteinInteger.times(-1); // This should bring us to -eisen
                assertionMessage = eisensteinInteger.toString() + " should be found to be prime.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger));
                eisensteinInteger = eisensteinInteger.times(NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY);
                assertionMessage = eisensteinInteger.toString() + " should be found to be prime.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger));
                eisensteinInteger = eisensteinInteger.times(NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY);
                assertionMessage = eisensteinInteger.toString() + " should be found to be prime.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger));
            }
        }
        eisensteinInteger = new ImaginaryQuadraticInteger(-4, 4, NumberTheoreticFunctionsCalculator.RING_EISENSTEIN);
        assertionMessage = eisensteinInteger.toStringAlt() + " should not have been found to be prime.";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(eisensteinInteger));
        /* Now to test some complex numbers in Z[sqrt(-5)] and O_Q(sqrt(-31)), 
           and also some real numbers in Z[sqrt(10)] and O_Q(sqrt(65)). */
        ImaginaryQuadraticRing Zi5 = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticRing OQi31 = new ImaginaryQuadraticRing(-31);
        RealQuadraticRing Z10 = new RealQuadraticRing(10);
        RealQuadraticRing OQ65 = new RealQuadraticRing(65);
        QuadraticInteger numberFromNonUFD;
        int norm;
        for (int a = -1; a > -10; a--) {
            for (int b = 1; b < 10; b++) {
                numberFromNonUFD = new ImaginaryQuadraticInteger(a, b, Zi5);
                norm = a * a + 5 * b * b;
                if (NumberTheoreticFunctionsCalculator.isPrime(norm)) {
                    assertionMessage = numberFromNonUFD.toString() + " should have been identified as prime in " + Zi5.toString();
                    assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                } else {
                    assertionMessage = numberFromNonUFD.toString() + " should not have been identified as prime in " + Zi5.toString();
                    assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                }
                numberFromNonUFD = new ImaginaryQuadraticInteger(a, b, OQi31);
                norm = a * a + 31 * b * b;
                if ((a % 2) == (b % 2)) {
                    try {
                        numberFromNonUFD = numberFromNonUFD.divides(2);
                        norm /= 4;
                    } catch (NotDivisibleException nde) {
                        String msg = "Trying to divide " + numberFromNonUFD.toASCIIString() + " by 2 should not have caused NotDivisibleException: \"" + nde.getMessage() + "\"";
                        fail(msg);
                    }
                }
                if (NumberTheoreticFunctionsCalculator.isPrime(norm)) {
                    assertionMessage = numberFromNonUFD.toString() + " should have been identified as prime in " + OQi31.toString();
                    assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                } else {
                    assertionMessage = numberFromNonUFD.toString() + " should not have been identified as prime in " + OQi31.toString();
                    assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                }
                numberFromNonUFD = new RealQuadraticInteger(a, b, Z10);
                norm = a * a - 10 * b * b;
                if (NumberTheoreticFunctionsCalculator.isPrime(norm)) {
                    assertionMessage = numberFromNonUFD.toString() + " should have been identified as prime in " + OQi31.toString();
                    assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                } else {
                    assertionMessage = numberFromNonUFD.toString() + " should not have been identified as prime in " + OQi31.toString();
                    assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                }
                numberFromNonUFD = new RealQuadraticInteger(a, b, OQ65);
                norm = a * a - 65 * b * b;
                if ((a % 2) == (b % 2)) {
                    try {
                        numberFromNonUFD = numberFromNonUFD.divides(2);
                        norm /= 4;
                    } catch (NotDivisibleException nde) {
                        String msg = "Trying to divide " + numberFromNonUFD.toASCIIString() + " by 2 should not have caused NotDivisibleException: \"" + nde.getMessage() + "\"";
                        fail(msg);
                    }
                }
                if (NumberTheoreticFunctionsCalculator.isPrime(norm)) {
                    assertionMessage = numberFromNonUFD.toString() + " should have been identified as prime in " + OQi31.toString();
                    assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                } else {
                    assertionMessage = numberFromNonUFD.toString() + " should not have been identified as prime in " + OQi31.toString();
                    assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(numberFromNonUFD));
                }
            }
        }
        QuadraticRing r;
        AlgebraicInteger z;
        /* Now to check some purely real integers in the context of a few 
           non-UFDs */
        int re;
        for (int iterDiscr = -6; iterDiscr > -200; iterDiscr--) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                r = new ImaginaryQuadraticRing(iterDiscr);
                re = -iterDiscr + 1;
                z = new ImaginaryQuadraticInteger(re, 0, r);
                assertionMessage = re + " in " + r.toString() + " should not have been identified as prime.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(z));
            }
        }
        RealQuadraticInteger ramifier;
        for (int iterDiscrR : NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D) {
            r = new RealQuadraticRing(iterDiscrR);
            ramifier = new RealQuadraticInteger(0, 1, r);
            if (NumberTheoreticFunctionsCalculator.isPrime(iterDiscrR)) {
                assertionMessage = ramifier.toASCIIString() + " should have been identified as prime in " + r.toASCIIString() + ".";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(ramifier));
            } else {
                assertionMessage = ramifier.toASCIIString() + " should not have been identified as prime in " + r.toASCIIString() + ".";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(ramifier));
            }
        }
        /* The very last thing, to check UnsupportedNumberDomainException is 
           thrown when needed */
        r = new IllDefinedQuadraticRing(22);
        z = new IllDefinedQuadraticInteger(5, 4, r);
        try {
            boolean compositeFlag = !NumberTheoreticFunctionsCalculator.isPrime(z);
            String msg = "Somehow determined that " + z.toASCIIString() + " is ";
            if (compositeFlag) {
                msg = msg + "not ";
            }
            msg = msg + " prime.";
            System.out.println(msg);
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("isPrime on unsupported number domain correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        }
    }
    
    private void checkFactorsAreIrreducible(QuadraticInteger num) {
        List<AlgebraicInteger> factors = irreducibleFactors(num);
        QuadraticInteger product = QuadraticInteger.apply(1, 0, num.getRing());
        String msgFrag = " ought to be an irreducible factor of " 
                + num.toString();
        String msg;
        for (AlgebraicInteger factor : factors) {
            msg = factor.toString() + msgFrag;
            assert isIrreducible(factor) : msg;
            product = product.times((QuadraticInteger) factor);
        }
        msg = "Factors of " + num.toString() + " should multiply to that number";
        assertEquals(msg, num, product);
    }
    
    @Test
    public void testIrreducibleFactors() {
        System.out.println("irreducibleFactors");
        QuadraticRing ring = new ImaginaryQuadraticRing(-31);
        QuadraticInteger num = new ImaginaryQuadraticInteger(-29, 1, ring);
        checkFactorsAreIrreducible(num);
        ring = new RealQuadraticRing(26);
        num = new RealQuadraticInteger(28, 3, ring);
        checkFactorsAreIrreducible(num);
    }
    
    @Test(timeout = 10000)
    public void testIrreducibleFactorsOfRamifiedNumber() {
        QuadraticRing ring = new RealQuadraticRing(1601);
        QuadraticInteger num = new RealQuadraticInteger(1601, 0, ring);
        QuadraticInteger root = new RealQuadraticInteger(0, 1, ring);
        List<AlgebraicInteger> expected = new ArrayList<>();
        expected.add(root);
        expected.add(root);
        List<AlgebraicInteger> actual = irreducibleFactors(num);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIrreducibleFactorsForUnsupportedRing() {
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(70);
        IllDefinedQuadraticInteger num = new IllDefinedQuadraticInteger(21, 3, 
                ring);
        try {
            List<AlgebraicInteger> factors = irreducibleFactors(num);
            String msg = "Trying to find irreducible factors of " 
                    + num.toString() + " from unsupported ill-defined ring " 
                    + ring.toString() 
                    + " should have caused exception, not given result " 
                    + factors.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to find irreducible factors of " 
                    + num.toString() + " from unsupported ill-defined ring " 
                    + ring.toString() 
                    + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for irreducible factors of "
                    + num.toString() + " from unsupported ill-defined ring " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    /**
     * Test of isIrreducible method, of class 
     * NumberTheoreticFunctionsCalculator.
     */
    // TODO: Break this test up into smaller tests
    @Test
    public void testIsIrreducible() {
        System.out.println("isIrreducible");
        QuadraticRing currRing;
        QuadraticInteger currQuadrInt;
        String assertionMessage;
        /* The number 1 + sqrt(d) should be irreducible but not prime in each
           domain Z[sqrt(d)] for squarefree negative d = 3 mod 4. But (1 + 
           sqrt(d))^2 should not be, nor the complexConjugate of that number. */
        for (int iterDiscr = -5; iterDiscr > -200; iterDiscr -= 4) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                currRing = new ImaginaryQuadraticRing(iterDiscr);
                currQuadrInt = new ImaginaryQuadraticInteger(1, 1, currRing);
                assertionMessage = currQuadrInt.toString() + " should have been found to not be prime.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(currQuadrInt));
                assertionMessage = assertionMessage + "\nBut it should have been found to be irreducible.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
                try {
                    currQuadrInt = currQuadrInt.times(currQuadrInt); // Squaring currQuadrInt
                } catch (AlgebraicDegreeOverflowException adoe) {
                    fail("AlgebraicDegreeOverflowException should not have happened when multiplying an algebraic integer by itself: " + adoe.getMessage());
                }
                assertionMessage = currQuadrInt.toString() + " should not have been found to be irreducible.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
                currQuadrInt = currQuadrInt.conjugate();
                assertionMessage = currQuadrInt.toASCIIString() + " should not have been found to be irreducible.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
            }
        }
        for (int iterDiscrOQ = -7; iterDiscrOQ > -200; iterDiscrOQ -= 4) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscrOQ)) {
                currRing = new ImaginaryQuadraticRing(iterDiscrOQ);
                currQuadrInt = new ImaginaryQuadraticInteger(1, 1, currRing, 2);
                assertionMessage = currQuadrInt.toString() + " should have been found to be irreducible.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
                try {
                    currQuadrInt = currQuadrInt.times(currQuadrInt);
                } catch (AlgebraicDegreeOverflowException adoe) {
                    fail("AlgebraicDegreeOverflowException should not have happened when multiplying an algebraic integer by itself: " + adoe.getMessage());
                }
                assertionMessage = currQuadrInt.toString() + " should not have been found to be irreducible.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
                currQuadrInt = new ImaginaryQuadraticInteger(3, 1, currRing, 2);
                currQuadrInt = currQuadrInt.times(currQuadrInt);
                assertionMessage = currQuadrInt.toString() + " should not have been found to be irreducible.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
            }
        }
        for (int discrR = 10; discrR < 200; discrR += 10) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(discrR)) {
                currRing = new RealQuadraticRing(discrR);
                currQuadrInt = new RealQuadraticInteger(0, 1, currRing);
                assertionMessage = currQuadrInt.toString() + " should have been found to be irreducible.";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
                try {
                    currQuadrInt = currQuadrInt.times(currQuadrInt);
                } catch (AlgebraicDegreeOverflowException adoe) {
                    fail("AlgebraicDegreeOverflowException should not have happened when multiplying an algebraic integer by itself: " + adoe.getMessage());
                }
                assertionMessage = currQuadrInt.toString() + " should not have been found to be irreducible.";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currQuadrInt));
            }
        }
        /* The very last thing, to check UnsupportedNumberDomainException is 
           thrown when needed */
        IllDefinedQuadraticRing r = new IllDefinedQuadraticRing(22);
        IllDefinedQuadraticInteger z = new IllDefinedQuadraticInteger(5, 4, r);
        try {
            boolean compositeFlag = !NumberTheoreticFunctionsCalculator.isIrreducible(z);
            String failMessage = "Somehow determined that " + z.toASCIIString() + " is ";
            if (compositeFlag) {
                failMessage = failMessage + "not ";
            }
            failMessage = failMessage + " irrreducible.";
            System.out.println(failMessage);
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("isIrreducible on unsupported number domain correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        }
    }
    
    /**
     * Test of isIrreducible and isPrime, of class 
     * NumberTheoreticFunctionsCalculator, simultaneously. In unique 
     * factorization domains, all numbers that are said to be prime should also 
     * be said to be irreducible, and vice-versa, with the exception of 0 and 
     * units.
     */
    @Test
    public void testIsPrimeIsIrreducibleSimult() {
        System.out.println("isPrime, isIrreducible");
        QuadraticRing currRing;
        QuadraticInteger currInt;
        String assertionMessage;
        for (int discrUFDIm : NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D) {
            currRing = new ImaginaryQuadraticRing(discrUFDIm);
            for (int v = -3; v < 8; v++) {
                for (int w = 9; w < 12; w++) {
                    currInt = new ImaginaryQuadraticInteger(v, w, currRing);
                    assertionMessage = currInt.toString() + " should be found to be both prime and irreducible, or neither.";
                    assertEquals(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(currInt), isIrreducible(currInt));
                }
            }
        }
        for (int discrUFDRe : NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D) {
            currRing = new RealQuadraticRing(discrUFDRe);
            for (int x = -3; x < 8; x++) {
                for (int y = 1; y < 5; y++) {
                    currInt = new RealQuadraticInteger(x, y, currRing);
                    if (Math.abs(currInt.norm()) == 1) {
                        System.out.println("Skipping " + currInt.toASCIIString() + " for this test.");
                    } else {
                        assertionMessage = currInt.toString() + " should be found to be both prime and irreducible, or neither.";
                        assertEquals(assertionMessage, isPrime(currInt), isIrreducible(currInt));
                    }
                }
            }
        }
        for (int discrNonUFDIm = -5; discrNonUFDIm > -200; discrNonUFDIm -= 4) {
            if (isSquareFree(discrNonUFDIm)) {
                currRing = new ImaginaryQuadraticRing(discrNonUFDIm);
                currInt = new ImaginaryQuadraticInteger(2, 0, currRing);
                assertionMessage = "2 in " + currRing.toString() + " should be found to be irreducible";
                assertTrue(assertionMessage, isIrreducible(currInt));
                assertionMessage = assertionMessage + " but not prime";
                assertFalse(assertionMessage, isPrime(currInt));
            }
        }
        for (int discrNonUFDRe = 10; discrNonUFDRe < 200; discrNonUFDRe += 5) {
            if (isSquareFree(discrNonUFDRe)) {
                currRing = new RealQuadraticRing(discrNonUFDRe);
                for (int p = 3; p < 20; p += 2) {
                    if (isPrime(p)) {
                        currInt = new RealQuadraticInteger(p, 0, currRing);
                        if (isPrime(currInt)) {
                            assertionMessage = "Since " + p 
                                    + " is said to be prime in " 
                                    + currRing.toString() 
                                    + ", it should also be said to be irreducible.";
                            assertTrue(assertionMessage, isIrreducible(currInt));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Another test of isIrreducible method, of class 
     * NumberTheoreticFunctionsCalculator. Passing a null instance of {@link 
     * algebraics.AlgebraicInteger AlgebraicInteger} to {@link 
     * NumberTheoreticFunctionsCalculator#isIrreducible(algebraics.AlgebraicInteger) 
     * isIrreducible()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNullNeitherReducibleNorIrreducible() {
        try {
            boolean result = NumberTheoreticFunctionsCalculator.isIrreducible(null);
            String failMsg = "Trying to see if null is irreducible should have caused an exception, not given result " 
                    + result;
            fail(failMsg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to see if null is irreducible correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            assertNotNull("Exception message should not be null", excMsg);
            assert !excMsg.equals("") : "Exception message should not be empty";
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to see if null is irreducible";
            fail(failMsg);
        }
    }
    
    /**
     * Test of symbolLegendre method, of class 
     * NumberTheoreticFunctionsCalculator. Per quadratic reciprocity, 
     * Legendre(<i>p</i>, <i>q</i>) = Legendre(<i>q</i>, <i>p</i>) if <i>p</i> 
     * and <i>q</i> are both primes and either one or both of them are congruent 
     * to 1 mod 4. But if both are congruent to 3 mod 4, then Legendre(<i>p</i>, 
     * q) = &minus;Legendre(<i>q</i>, <i>p</i>). And of course 
     * Legendre(<i>p</i>, <i>p</i>) = 0. Some of this assumes that both <i>p</i> 
     * and <i>q</i> are positive. In the case of Legendre(<i>p</i>, 
     * &minus;<i>q</i>) with <i>q</i> being positive, reckon the congruence of 
     * <i>q</i> mod 4 rather than &minus;<i>q</i> mod 4.
     * <p>Another property to test for is that Legendre(<i>ab</i>, <i>p</i>) = 
     * Legendre(<i>a</i>, <i>p</i>) Legendre(<i>b</i>, <i>p</i>). That is to say 
     * that this is a multiplicative function. So here this is tested with 
     * Legendre(2<i>p</i>, <i>q</i>) = Legendre(2, <i>q</i>) Legendre(<i>p</i>, 
     * <i>q</i>).</p>
     * <p>Of course it's entirely possible that in implementing symbolLegendre 
     * the programmer could get dyslexic and produce an implementation that 
     * always gives the wrong result when gcd(<i>a</i>, <i>p</i>) = 1, meaning 
     * that it always returns &minus;1 when it should return 1, and vice-versa, 
     * and yet it passes the tests.</p>
     * <p>For that reason one should not rely only on the identities pertaining 
     * to multiplicativity and quadratic reciprocity. Therefore these tests also 
     * include some computations of actual squares modulo <i>p</i> to check some 
     * of the answers.</p>
     * <p>I chose to use the Fibonacci numbers for this purpose, since they are 
     * already being used in some of the other tests and they contain a good mix 
     * of prime numbers (including the even prime 2) and composite numbers.</p>
     */
    @Test
    public void testLegendreSymbol() {
        System.out.println("symbolLegendre");
        byte expResult, result;
        int p, q;
        // First to test Legendre(Fibonacci(n), p)
        for (int i = 3; i < fibonacciList.size(); i++) {
            for (int j = 1; j < primesListLength; j++) {
                p = primesList.get(j);
                int fiboM = fibonacciList.get(i) % p;
                if (fiboM == 0) {
                    expResult = 0;
                } else {
                    int halfPmark = (p + 1)/2;
                    int[] modSquares = new int[halfPmark];
                    boolean noSolutionFound = true;
                    int currModSqIndex = 0;
                    for (int n = 0; n < halfPmark; n++) {
                        modSquares[n] = (n * n) % p;
                    }
                    while (noSolutionFound && (currModSqIndex < halfPmark)) {
                        noSolutionFound = !(fiboM == modSquares[currModSqIndex]);
                        currModSqIndex++;
                    }
                    if (noSolutionFound) {
                        expResult = -1;
                    } else {
                        expResult = 1;
                    }
                }
                result = NumberTheoreticFunctionsCalculator.symbolLegendre(fibonacciList.get(i), p);
                assertEquals(expResult, result);
            }
        }
        // Now to test with both p and q being odd primes
        for (int pindex = 1; pindex < primesListLength; pindex++) {
            p = primesList.get(pindex);
            expResult = 0;
            result = NumberTheoreticFunctionsCalculator.symbolLegendre(p, p);
            assertEquals(expResult, result);
            for (int qindex = pindex + 1; qindex < primesListLength; qindex++) {
                q = primesList.get(qindex);
                expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(q, p);
                if (p % 4 == 3 && q % 4 == 3) {
                    expResult *= -1;
                }
                result = NumberTheoreticFunctionsCalculator.symbolLegendre(p, q);
                assertEquals(expResult, result);
                result = NumberTheoreticFunctionsCalculator.symbolLegendre(p, -q);
                assertEquals(expResult, result);
                /* And lastly, to test that Legendre(2p, q) = Legendre(2, q) 
                   Legendre(p, q). */
                expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(2, q);
                expResult *= NumberTheoreticFunctionsCalculator.symbolLegendre(p, q);
                result = NumberTheoreticFunctionsCalculator.symbolLegendre(2 * p, q);
                assertEquals(expResult, result);
            }
        }
    }
    
    @Test
    public void testLegendreSymbolSuggestJacobi() {
        try {
            byte attempt = symbolLegendre(13, 21);
            fail("Calling Legendre(13, 21) should have triggered an exception, not given result " 
                    + attempt + ".");
        } catch (IllegalArgumentException iae) {
            System.out.println("Calling Legendre(13, 21) correctly triggered IllegalArgumentException.");
            String excMsg = iae.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String msg = "Exception message should mention Jacobi symbol";
            assert excMsg.contains("Jacobi") : msg;
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for calling Legendre(13, 21)";
            fail(msg);
        }
    }

    @Test
    public void testLegendreSymbolSuggestKronecker() {
        try {
            byte attempt = symbolLegendre(7, 2);
            fail("Calling Legendre(7, 2) should have triggered an exception, not given result " 
                    + attempt + ".");
        } catch (IllegalArgumentException iae) {
            System.out.println("Calling Legendre(7, 2) correctly triggered IllegalArgumentException.");
            String excMsg = iae.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String msg = "Exception message should mention Kronecker symbol";
            assert excMsg.contains("Kronecker") : msg;
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for calling Legendre(7, 2)";
            fail(msg);
        }
    }

    /**
     * Test of symbolJacobi method, of class NumberTheoreticFunctionsCalculator. 
     * This test checks Jacobi(<i>n</i>, <i>pq</i>), where <i>n</i> is an 
     * integer from 15 to 19, <i>p</i> is an odd prime and <i>q</i> is the next 
     * higher prime.
     */
    @Test
    public void testJacobiSymbol() {
        System.out.println("symbolJacobi");
        int p, q, m;
        byte expResult, result;
        for (int pindex = 1; pindex < primesListLength; pindex++) {
            p = primesList.get(pindex);
            for (int qindex = pindex + 1; qindex < primesListLength; qindex++) {
                q = primesList.get(qindex);
                m = p * q;
                for (int n = 15; n < 20; n++) {
                    expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(n, p);
                    expResult *= NumberTheoreticFunctionsCalculator.symbolLegendre(n, q);
                    result = NumberTheoreticFunctionsCalculator.symbolJacobi(n, m);
                    assertEquals(expResult, result);
                }
            }
        }
        // And lastly to check for exceptions for bad arguments.
        try {
            byte attempt = NumberTheoreticFunctionsCalculator.symbolJacobi(7, 2);
            fail("Calling Jacobi(7, 2) should have triggered an exception, not given result " + attempt + ".");
        } catch (IllegalArgumentException iae) {
            System.out.println("Calling Jacobi(7, 2) correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        }
    }

    /**
     * Another test of symbolJacobi method, of class 
     * NumberTheoreticFunctionsCalculator. First, it checks that 
     * Legendre(<i>a</i>, <i>p</i>) = Jacobi(<i>a</i>, <i>p</i>), where <i>p</i> 
     * is an odd prime. Next, it checks that Jacobi(<i>n</i>, <i>pq</i>) = 
     * Legendre(<i>n</i>, <i>p</i>) Legendre(<i>n</i>, <i>q</i>). If the 
     * Legendre symbol test fails, the result of this test is meaningless. Then 
     * follows the actual business of checking Jacobi(<i>n</i>, <i>m</i>).
     */
    @Test
    public void testJacobiLegendreCorrespondence() {
        System.out.println("Checking overlap with Legendre symbol...");
        byte expResult, result;
        for (int i = 1; i < primesListLength; i++) {
            for (int a = 5; a < 13; a++) {
                expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(a, primesList.get(i));
                result = NumberTheoreticFunctionsCalculator.symbolJacobi(a, primesList.get(i));
                assertEquals(expResult, result);
            }
        }
    }
    
    /**
     * Test of symbolKronecker method, of class 
     * NumberTheoreticFunctionsCalculator. This test checks that 
     * Legendre(<i>a</i>, <i>p</i>) = Kronecker(<i>a</i>, <i>p</i>), where 
     * <i>p</i> is an odd prime.
     */
    @Test
    public void testKroneckerLegendreCorrespondence() {
        byte expResult, result;
        for (int i = 1; i < primesListLength; i++) {
            for (int a = 7; a < 11; a++) {
                expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(a, primesList.get(i));
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(a, primesList.get(i));
                assertEquals(expResult, result);
            }
        }
        System.out.println("Kronecker-Legendre correspondence checks out...");
    }
    
    /**
     * Test of symbolKronecker method, of class 
     * NumberTheoreticFunctionsCalculator. This test checks that 
     * Jacobi(<i>n</i>, <i>m</i>) = Kronecker(<i>n</i>, <i>m</i>), where 
     * <i>m</i> is odd.
     */
    @Test
    public void testKroneckerJacobiCorrespondence() {
        byte expResult, result;
        for (int j = -10; j < 10; j++) {
            for (int b = 5; b < 15; b += 2) {
                expResult = NumberTheoreticFunctionsCalculator.symbolJacobi(j, b);
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(j, b);
                assertEquals(expResult, result);
            }
        }
        System.out.println("Kronecker-Jacobi correspondence checks out...");
    }
    
    /**
     * Test of symbolKronecker method, of class 
     * NumberTheoreticFunctionsCalculator. This test checks Kronecker(<i>n</i>, 
     * &minus;2), Kronecker(<i>n</i>, &minus;1) and Kronecker(<i>n</i>, 2). If 
     * either the Legendre symbol test or the Jacobi symbol test fails, the 
     * result of this test is meaningless.
     */
    @Test
    public void testKroneckerSymbol() {
        System.out.println("symbolKronecker");
        byte expResult, result;
        for (int n = 1; n < 50; n++) {
            expResult = -1;
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(-n, -1);
            assertEquals(expResult, result);
            expResult = 1;
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(n, -1);
            assertEquals(expResult, result);
        }
        for (int m = -24; m < 25; m += 8) {
            expResult = -1;
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 3, 2);
            assertEquals(expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 5, 2);
            assertEquals(expResult, result);
            if (m < 0) {
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 1, -2);
                assertEquals(expResult, result);
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 7, -2);
                assertEquals(expResult, result);
            } else {
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 3, -2);
                assertEquals(expResult, result);
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 5, -2);
                assertEquals(expResult, result);
            }
            expResult = 0;
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m, 2);
            assertEquals(expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 2, 2);
            assertEquals(expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 4, 2);
            assertEquals(expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 6, 2);
            assertEquals(expResult, result);
            expResult = 1;
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 1, 2);
            assertEquals(expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 7, 2);
            assertEquals(expResult, result);
            expResult = (byte) Integer.signum(m + 1);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 1, -2);
            assertEquals(expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 3, -2);
            assertEquals(-expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 5, -2);
            assertEquals(-expResult, result);
            result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 7, -2);
            assertEquals(expResult, result);
        }
    }
    
    /**
     * Test of symbolKronecker method, of class 
     * NumberTheoreticFunctionsCalculator. This test checks three specific 
     * cases: Kronecker(33, &minus;70) = &minus;1, Kronecker(32, &minus;70) = 0 
     * and Kronecker(31, &minus;70) = 1.
     */
    @Test
    public void testKroneckerSymbolM70CasesWNegN() {
        assertEquals(-1, NumberTheoreticFunctionsCalculator.symbolKronecker(-33, 70));
        assertEquals(0, NumberTheoreticFunctionsCalculator.symbolKronecker(-32, 70));
        assertEquals(1, NumberTheoreticFunctionsCalculator.symbolKronecker(-31, 70));
    }

    /**
     * Test of symbolKronecker method, of class 
     * NumberTheoreticFunctionsCalculator. This test checks three specific 
     * cases: Kronecker(31, 70) = &minus;1, Kronecker(32, 70) = 0 and 
     * Kronecker(33, 70) = 1.
     */
    @Test
    public void testKroneckerSymbolM70Cases() {
        assertEquals(-1, symbolKronecker(31, 70));
        assertEquals(0, symbolKronecker(32, 70));
        assertEquals(1, symbolKronecker(33, 70));
    }
    
    /**
     * Test of the isDivisibleBy function, of the 
     * NumberTheoreticFunctionsCalculator class.
     */
    @Test
    public void testIsDivisibleBy() {
        System.out.println("isDivisibleBy");
        int d = randomSquarefreeNumber(128);
        if (RANDOM.nextBoolean()) d *= -1;
        if (d == 1) d = -1;
        QuadraticRing r = QuadraticRing.apply(d);
        int a = RANDOM.nextInt(64) - 32;
        int b = 2 * RANDOM.nextInt(32) + 1;
        QuadraticInteger division = QuadraticInteger.apply(a, b, r);
        QuadraticInteger divisor = QuadraticInteger.apply(b, -a, r);
        QuadraticInteger dividend = division.times(divisor);
        String msg = dividend.toString() + " should be divisible by " 
                + divisor.toString();
        assert isDivisibleBy(dividend, divisor) : msg;
    }
    
    /**
     * Another test of the isDivisibleBy function, of the 
     * NumberTheoreticFunctionsCalculator class.
     */
    @Test
    public void testIsNotDivisibleBy() {
        int d = randomSquarefreeNumber(128);
        if (RANDOM.nextBoolean()) d *= -1;
        if (d == 1) d = -1;
        QuadraticRing r = QuadraticRing.apply(d);
        int a = RANDOM.nextInt(64) - 32;
        int b = 2 * RANDOM.nextInt(32) + 3;
        QuadraticInteger division = QuadraticInteger.apply(a, -b, r);
        QuadraticInteger divisor = QuadraticInteger.apply(b, a, r);
        QuadraticInteger dividend = division.times(divisor).plus(1);
        String msg = dividend.toString() + " should not be divisible by " 
                + divisor.toString();
        assert !isDivisibleBy(dividend, divisor) : msg;
    }
    
    /**
     * Another test of the isDivisibleBy function, of the 
     * NumberTheoreticFunctionsCalculator class. If the dividend and the divisor 
     * come from different rings, an {@link AlgebraicDegreeOverflowException} 
     * should occur.
     */
    @Test
    public void testIsNotDivisbleByAlgebraicDegreeOverflow() {
        QuadraticRing r = new RealQuadraticRing(14);
        QuadraticInteger a = new RealQuadraticInteger(7, 1, r);
        r = new ImaginaryQuadraticRing(-7);
        QuadraticInteger b = new ImaginaryQuadraticInteger(0, 1, r);
        try {
            boolean divisibleFlag = isDivisibleBy(a, b);
            String msg = "Trying to ascertain the divisibility of " 
                    + a.toString() + " by " + b.toString() 
                    + " should not have given the result that it is is ";
            if (!divisibleFlag) {
                msg = msg + "not ";
            }
            msg = msg + "divisible";
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Trying to ascertain the divisibility of " 
                    + a.toASCIIString() + " by " + b.toASCIIString() 
                    + " correctly triggered AlgebraicDegreeOverflowException");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName()
                    + " is the wrong exception for divisibility of " 
                    + a.toString() + " by " + b.toString();
            System.out.println(msg);
            System.out.println("\"" + re.getMessage() + "\"");
            fail(msg);
        }
    }
    
    /**
     * Test of the isSquareFree function, of the 
     * NumberTheoreticFunctionsCalculator class. Prime numbers should be found 
     * to be squarefree, squares of primes should not.
     */
    @Test
    public void testIsSquareFree() {
        System.out.println("isSquareFree");
        String msg;
        String shouldBeMsg = " should have been found to be squarefree";
        String shouldNotBeMsg = " should NOT have been found to be squarefree";
        int number;
        for (int i = 0; i < primesListLength - 1; i++) {
            number = primesList.get(i) * primesList.get(i + 1); // pq
            msg = number + shouldBeMsg;
            assert isSquareFree(number) : msg;
            number *= primesList.get(i); // (p^2)q
            msg = number + shouldNotBeMsg;
            assert !isSquareFree(number) : msg;
            number /= primesList.get(i + 1); // p^2
            msg = number + shouldNotBeMsg;
            assert !isSquareFree(number) : msg;
        }
    }

    @Test
    public void testNextLowestSquarefree() {
        System.out.println("nextLowestSquarefree");
        fail("RESUME WORK HERE");
        int stop = squarefreesList.size() - 1;
        int curr;
        int next;
        for (int i = 0; i < stop; i++) {
            //
        }
    }
    
    /**
     * Test of the isPerfectSquare function, of the 
     * NumberTheoreticFunctionsCalculator class. A number like 81 should be 
     * found to be a perfect square.
     */
    @Test
    public void testIsPerfectSquare() {
        System.out.println("isPerfectSquare");
        int min = RANDOM.nextInt(128);
        int max = min + RANDOM.nextInt(128) + 1;
        int nSquared;
        String msgPart = " should be found to be a perfect square";
        String msg;
        for (int n = min; n < max; n++) {
            nSquared = n * n;
            msg = nSquared + msgPart;
            assert isPerfectSquare(nSquared) : msg;
        }
    }
    
    /**
     * Another test of the isPerfectSquare function, of the 
     * NumberTheoreticFunctionsCalculator class. Numbers like 26 and 27 should 
     * not be found to be perfect squares, even if they are perfect powers.
     */
    @Test
    public void testIsNotPerfectSquare() {
        Integer[] numbers = new Integer[1000];
        for (int fill = 0; fill < 1000; fill++) {
            numbers[fill] = fill;
        }
        int mSquared;
        for (int m = 0; m < 32; m++) {
            mSquared = m * m;
            numbers[mSquared] = 2;
        }
        Set<Integer> notSquares = new HashSet<>(Arrays.asList(numbers));
        String msgPart = " should NOT be found to be a perfect square";
        String msg;
        for (int notSquare : notSquares) {
            msg = notSquare + msgPart;
            assert !isPerfectSquare(notSquare) : msg;
        }
    }
    
    /**
     * Another test of the isPerfectSquare function, of the 
     * NumberTheoreticFunctionsCalculator class. Numbers like -4 and -9 should 
     * not be found to be perfect squares, even if they are perfect squares 
     * times -1.
     */
    @Test
    public void testNegativeIsNotPerfectSquare() {
        int max = -RANDOM.nextInt(128);
        int min = max - RANDOM.nextInt(128) - 1;
        String msgPart = " should NOT be found to be a perfect square";
        String msg;
        for (int n = min; n < max; n++) {
            msg = n + msgPart;
            assert !isPerfectSquare(n) : msg;
        }
    }
    
    /**
     * Test of kernel method, of class NumberTheoreticFunctionsCalculator. This 
     * checks the kernel function with numbers that are the product of two 
     * distinct primes as well as with numbers that are the product of powers of 
     * primes. For now there is no testing of 0 as an input.
     */
    @Test
    public void testKernel() {
        System.out.println("kernel");
        int currPrime, currNum, expResult, result;
        String assertionMessage;
        int currIndex = 1;
        do {
            currPrime = primesList.get(currIndex); // Get p
            currNum = currPrime;
            expResult = currPrime;
            result = NumberTheoreticFunctionsCalculator.kernel(currNum);
            assertionMessage = "Kernel of " + currNum + " should be found to be " + currNum + " itself.";
            assertEquals(assertionMessage, expResult, result);
            currNum *= currPrime; // p^2, expResult stays the same
            result = NumberTheoreticFunctionsCalculator.kernel(currNum);
            assertionMessage = "Kernel of " + currNum + " should be found to be " + currPrime + ".";
            assertEquals(assertionMessage, expResult, result);
            currPrime = -primesList.get(currIndex - 1); // Get q, a negative prime
            currNum *= currPrime; // p^2 q, which is negative
            expResult *= currPrime; // pq, which is also negative
            result = NumberTheoreticFunctionsCalculator.kernel(currNum);
            assertionMessage = "Kernel of " + currNum + " should be found to be " + expResult + ".";
            assertEquals(assertionMessage, expResult, result);
            currNum *= currPrime; // p^2 q^2, which is positive
            expResult *= -1; // -pq, which is also positive
            result = NumberTheoreticFunctionsCalculator.kernel(currNum);
            assertionMessage = "Kernel of " + currNum + " should be found to be " + expResult + ".";
            assertEquals(assertionMessage, expResult, result);
            currNum *= currPrime; // p^2 q^3, which is negative
            expResult *= -1; // Back to pq, which is negative
            result = NumberTheoreticFunctionsCalculator.kernel(currNum);
            assertionMessage = "Kernel of " + currNum + " should be found to be " + expResult + ".";
            assertEquals(assertionMessage, expResult, result);
            currIndex++;
        } while (currIndex < primesListLength && currPrime > -71);
    }
    
    /**
     * Test of moebiusMu method, of class NumberTheoreticFunctionsCalculator. I 
     * expect that &mu;(&minus;<i>n</i>) = &mu;(<i>n</i>), so this test checks 
     * for that. If you wish to limit the test to positive integers, you can 
     * just remove some of the assertions.
     */
    @Test
    public void testMoebiusMu() {
        System.out.println("moebiusMu");
        byte expResult = -1;
        byte result;
        // The primes p should all have mu(p) = -1
        for (int i = 0; i < primesListLength; i++) {
            result = NumberTheoreticFunctionsCalculator.moebiusMu(primesList.get(i));
            assertEquals(expResult, result);
            assertEquals(result, NumberTheoreticFunctionsCalculator.moebiusMu(-primesList.get(i)));
        }
        // Now to test mu(n) = 0 with n being a multiple of 4
        expResult = 0;
        for (int j = 0; j < 97; j += 4) {
            result = NumberTheoreticFunctionsCalculator.moebiusMu(j);
            assertEquals(expResult, result);
            assertEquals(result, NumberTheoreticFunctionsCalculator.moebiusMu(-j));
        }
        // And lastly, the products of two distinct primes p and q should give mu(pq) = 1
        expResult = 1;
        int num;
        for (int k = 0; k < primesListLength - 1; k++) {
            num = primesList.get(k) * primesList.get(k + 1);
            result = NumberTheoreticFunctionsCalculator.moebiusMu(num);
            assertEquals(expResult, result);
            assertEquals(result, NumberTheoreticFunctionsCalculator.moebiusMu(-num));
        }
    }

    /**
     * Test of euclideanGCD method, of class NumberTheoreticFunctionsCalculator.
     * At this time, I choose not to test the case gcd(0, 0). The value of such 
     * a test would be philosophical rather than practical.
     * <p>The Euclidean GCD algorithm is also tested on a few norm-Euclidean 
     * domains. In the case of a domain that is not Euclidean, {@link 
     * algebraics.NonEuclideanDomainException} should be thrown even if it's 
     * possible for the Euclidean algorithm to produce a result for the given 
     * pair of numbers. Testing of {@link 
     * algebraics.NonEuclideanDomainException#tryEuclideanGCDAnyway()} should of 
     * course happen in {@link algebraics.NonEuclideanDomainExceptionTest}, not 
     * here.</p>
     */
    // TODO: Break this test up into smaller tests
    @Test
    public void testEuclideanGCD() {
        System.out.println("euclideanGCD");
        long result;
        int expResult = 1; /* Going to test with consecutive integers, expect 
                              the result to be 1 each time */
        for (int i = -30; i < 31; i++) {
            result = NumberTheoreticFunctionsCalculator.euclideanGCD(i, i + 1);
            assertEquals(expResult, result);
        }
        /* Now test with consecutive odd numbers, result should also be 1 each 
           time as well */
        for (int j = -29; j < 31; j += 2) {
            result = NumberTheoreticFunctionsCalculator.euclideanGCD(j, j + 2);
            assertEquals(expResult, result);
        }
        /* And now consecutive Fibonacci numbers before moving on to even 
           numbers. This will probably be the longest part of the test. */
        for (int k = 1; k < fibonacciList.size(); k++) {
            result = NumberTheoreticFunctionsCalculator.euclideanGCD(fibonacciList.get(k - 1), fibonacciList.get(k));
            assertEquals(expResult, result);
        }
        expResult = 2; /* Now to test with consecutive even integers, result 
                          should be 2 each time */
        for (int m = -30; m < 31; m += 2) {
            result = NumberTheoreticFunctionsCalculator.euclideanGCD(m, m + 2);
            assertEquals(expResult, result);
        }
        // And now some of the same tests again but with the long data type
        long expResultLong = 1;
        long resultLong;
        for (long j = (long) Integer.MAX_VALUE; j < ((long) Integer.MAX_VALUE + 32); j++) {
            resultLong = NumberTheoreticFunctionsCalculator.euclideanGCD(j, j + 1);
            assertEquals(expResultLong, resultLong);
        }
        expResultLong = 2;
        for (long k = ((long) Integer.MAX_VALUE - 1); k < ((long) Integer.MAX_VALUE + 32); k += 2) {
            resultLong = NumberTheoreticFunctionsCalculator.euclideanGCD(k, k + 2);
            assertEquals(expResultLong, resultLong);
        }
        // Last but not least, euclideanGCD(QuadraticInteger, QuadraticInteger).
        QuadraticRing r;
        QuadraticInteger qia, qib;
        AlgebraicInteger expResultQI, resultQI;
        // sqrt(d) and 1 + sqrt(d) should be coprime in any ring
        for (Integer iterDiscr : NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D) {
            r = new ImaginaryQuadraticRing(iterDiscr);
            qia = new ImaginaryQuadraticInteger(0, 1, r);
            qib = new ImaginaryQuadraticInteger(1, 1, r);
            expResultQI = new ImaginaryQuadraticInteger(1, 0, r);
            try {
                resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, qib);
            } catch (AlgebraicDegreeOverflowException adoe) {
                resultQI = qia; // Just to avoid "may not have been initialized" warning
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
            } catch (NonEuclideanDomainException nede) {
                resultQI = qib; // Same reason as previous
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered NonEuclideanDomainException \"" + nede.getMessage() + "\"");
            } catch (NullPointerException npe) {
                resultQI = qia;
                System.out.println("NullPointerException encountered: " + npe.getMessage());
                System.out.println("This could indicate a problem with NotDivisibleException.getBoundingIntegers().");
            }
            assertEquals(expResultQI, resultQI);
        }
        // In most cases, gcd(sqrt(d), -d) = sqrt(d)
        for (int iterDiscrA = 0; iterDiscrA < NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D.length - 1; iterDiscrA++) {
            r = new ImaginaryQuadraticRing(NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D[iterDiscrA]);
            qia = new ImaginaryQuadraticInteger(0, 1, r);
            qib = new ImaginaryQuadraticInteger(NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D[iterDiscrA], 0, r);
            expResultQI = qia;
            try {
                resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, qib);
            } catch (AlgebraicDegreeOverflowException adoe) {
                resultQI = NumberTheoreticFunctionsCalculator.IMAG_UNIT_I; // Just to avoid "may not have been initialized" warning
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
            } catch (NonEuclideanDomainException nede) {
                resultQI = NumberTheoreticFunctionsCalculator.IMAG_UNIT_NEG_I; // Same reason as previous
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered NonEuclideanDomainException \"" + nede.getMessage() + "\"");
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                resultQI = qib;
                System.out.println("ArrayIndexOutOfBoundsException encountered: \"" + aioobe.getMessage() + "\"");
                System.out.println("This could indicate either a flaw in the implementation of the Euclidean algorithm or a problem with NotDivisibleException.getBoundingIntegers().");
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered ArrayIndexOutOfBoundsException: \"" + aioobe.getMessage() + "\"");
            }
            assertEquals(expResultQI, resultQI);
        }
        int b;
        for (int iterDiscrOQ = 0; iterDiscrOQ < NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D.length - 3; iterDiscrOQ++) {
            r = new ImaginaryQuadraticRing(NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D[iterDiscrOQ]);
            qia = new ImaginaryQuadraticInteger(1, 1, r, 2);
            b = (int) qia.norm() * HEEGNER_COMPANION_PRIMES[iterDiscrOQ + 4];
            expResultQI = qia;
            qia = qia.times(qia);
            try {
                resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, b);
                assertEquals(expResultQI, resultQI);
            } catch (AlgebraicDegreeOverflowException adoe) {
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + b + ") should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
            } catch (NonEuclideanDomainException nede) {
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + b + ") should not have triggered NonEuclideanDomainException" + nede.getMessage());
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                System.out.println("ArrayIndexOutOfBoundsException encountered: " + aioobe.getMessage());
                System.out.println("This could indicate either a flaw in the implementation of the Euclidean algorithm or a problem with NotDivisibleException.getBoundingIntegers().");
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + b + ") should not have triggered ArrayIndexOutOfBoundsException: \"" + aioobe.getMessage() + "\"");
            }
            try {
                resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(b, qia);
            } catch (NonEuclideanDomainException nede) {
                resultQI = NumberTheoreticFunctionsCalculator.IMAG_UNIT_NEG_I; // Same reason as previous
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + b + ") should not have triggered NonEuclideanDomainException" + nede.getMessage());
            }
            assertEquals(expResultQI, resultQI);
        }
        r = NumberTheoreticFunctionsCalculator.RING_GAUSSIAN;
        qia = new ImaginaryQuadraticInteger(-2, 2, r);
        qib = new ImaginaryQuadraticInteger(-2, 4, r);
        expResultQI = new ImaginaryQuadraticInteger(2, 0, r);
        try {
            resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, qib);
        } catch (AlgebraicDegreeOverflowException adoe) {
            resultQI = qia;
            fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
        } catch (NonEuclideanDomainException nede) {
            resultQI = qib;
            fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered NonEuclideanDomainException" + nede.getMessage());
        }
        assertEquals(expResultQI, resultQI);
        // Now to check the appropriate exceptions are thrown
        r = new ImaginaryQuadraticRing(-2);
        qib = new ImaginaryQuadraticInteger(-2, 4, r);
        try {
            resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, qib);
            fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should have triggered AlgebraicDegreeOverflowException, not given result " + resultQI.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") correctly triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
        } catch (NonEuclideanDomainException nde) {
            fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered NonEuclideanDomainException " + nde.getMessage());
        }
        r = new ImaginaryQuadraticRing(-5);
        qia = new ImaginaryQuadraticInteger(-2, 2, r);
        qib = new ImaginaryQuadraticInteger(-2, 4, r);
        try {
            resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, qib);
            fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should have triggered NonEuclideanDomainException, not given result " + resultQI.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
        } catch (NonEuclideanDomainException nde) {
            System.out.println("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") correctly triggered NonEuclideanDomainException " + nde.getMessage());
        }
        // Switching over to real quadratic integer rings
        for (int discrR : NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D) {
            r = new RealQuadraticRing(discrR);
            qia = new RealQuadraticInteger(2, 0, r);
            qib = new RealQuadraticInteger(1, 1, r);
            try {
                resultQI = NumberTheoreticFunctionsCalculator.euclideanGCD(qia, qib);
                System.out.println("gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") = " + resultQI.toASCIIString() + "?");
                if (discrR % 2 == 1) {
                    if (r.hasHalfIntegers()) {
//                        assertEquals(qia, resultQI); // IS THIS RIGHT???
                    } else {
                        if (discrR != 3) {
                            assertNotEquals(qib, resultQI);
                        }
                    }
                }
            } catch (AlgebraicDegreeOverflowException adoe) {
                fail("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"");
            } catch (NonEuclideanDomainException nde) {
                System.out.println("Attempting to calculate gcd(" + qia.toASCIIString() + ", " + qib.toASCIIString() + ") correctly triggered NonEuclideanDomainException " + nde.getMessage());
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                String failMessage = "ArrayIndexOutOfBoundsException encountered: \"" + aioobe.getMessage() + "\"";
                System.out.println(failMessage);
                System.out.println("This could indicate a problem with NotDivisibleException.getBoundingIntegers().");
                fail(failMessage);
            }
        }
    }
    
    /**
     * Test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. This is tested with just a few 
     * specific real quadratic integer rings. This also checks that {@link 
     * algebraics.UnsupportedNumberDomainException} is triggered for imaginary 
     * quadratic integer rings as well as for domains that don't yet have 
     * fleshed out support.
     */
    // TODO: Break this test up into smaller tests
    @Test
    public void testFundamentalUnit() {
        System.out.println("fundamentalUnit");
        QuadraticRing ring = new RealQuadraticRing(2);
        AlgebraicInteger expResult = new RealQuadraticInteger(1, 1, ring);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(3);
        expResult = new RealQuadraticInteger(2, 1, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(21);
        expResult = new RealQuadraticInteger(5, 1, ring, 2);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(22);
        expResult = new RealQuadraticInteger(197, 42, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(29);
        expResult = new RealQuadraticInteger(5, 1, ring, 2);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(41);
        expResult = new RealQuadraticInteger(32, 5, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(70);
        expResult = new RealQuadraticInteger(251, 30, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(74);
        expResult = new RealQuadraticInteger(43, 5, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(97);
        expResult = new RealQuadraticInteger(5604, 569, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(210);
        expResult = new RealQuadraticInteger(29, 2, ring);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
        ring = new ImaginaryQuadraticRing(-5);
        try {
            result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
            String failMessage = "Trying to get fundamental unit of " + ring.toASCIIString() + " should have caused IllegalArgumentException, not given result " + result.toASCIIString() + ".";
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to get fundamental unit of " + ring.toASCIIString() + " correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = "Trying to get fundamental unit of " + ring.toASCIIString() + " triggered wrong exception, " + e.getClass().getName() + ".";
            fail(failMessage);
        }
        ring = new IllDefinedQuadraticRing(1);
        try {
            result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
            String failMessage = "Trying to get fundamental unit of " + ring.toASCIIString() + " should have caused IllegalArgumentException, not given result " + result.toASCIIString() + ".";
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to get fundamental unit of " + ring.toASCIIString() + " correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = "Trying to get fundamental unit of " + ring.toASCIIString() + " triggered wrong exception, " + e.getClass().getName() + ".";
            fail(failMessage);
        }
    }
    
    /**
     * Another test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. This test is specifically for the 
     * specific case of <b>Z</b>[&radic;103], the fundamental unit of which is  
     * a number with a value of approximately 455055.9999978. An implementation 
     * of a brute force algorithm is likely to fail this test if it takes too 
     * long, even if it would eventually come up with the right result.
     */
    @Test(timeout = 30000)
    public void testFundamentalUnitZSqrt103() {
        System.out.println("fundamentalUnit(Z[sqrt(103)])");
        RealQuadraticRing ring = new RealQuadraticRing(103);
        RealQuadraticInteger expResult = new RealQuadraticInteger(227528, 22419, ring);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() 
                + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. This test is specifically for the 
     * specific case of <i>O</i><sub><b>Q</b>(&radic;109)</sub>, the fundamental 
     * unit of which is a number with a value of approximately 261.00383136138. 
     * One night I tried it and it gave me the erroneous result of 1, instead of 
     * <sup>261</sup>&frasl;<sub>2</sub> + 
     * <sup>25&radic;109</sup>&frasl;<sub>2</sub>. So I wrote this test.
     */
    @Test(timeout = 30000)
    public void testFundamentalUnitOQSqrt109() {
        System.out.println("fundamentalUnit(O_(Q(sqrt(109))))");
        RealQuadraticRing ring = new RealQuadraticRing(109);
        RealQuadraticInteger expResult = new RealQuadraticInteger(261, 25, ring, 2);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() 
                + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. This test is specifically for the 
     * specific case of <b>Z</b>[&radic;166], the fundamental unit of which is  
     * a number with a value of approximately 3401805129.9999999997. One time 
     * this function caused an <code>ArithmeticException</code> with the 
     * puzzling exception message "Overflow occurred, fundamental unit is 
     * greater than &minus;2147483638 + 166677057&radic;166." That exception 
     * should only occur when the "regular" and "surd" parts of the unit are 
     * outside the range of 32-bit integers.
     */
    @Test(timeout = 30000)
    public void testFundamentalUnitZSqrt166() {
        System.out.println("fundamentalUnit(Z[sqrt(166)])");
        RealQuadraticRing ring = new RealQuadraticRing(166);
        RealQuadraticInteger expected = new RealQuadraticInteger(1700902565, 
                132015642, ring);
        try {
            AlgebraicInteger actual = fundamentalUnit(ring);
            System.out.println("Fundamental unit of " + ring.toASCIIString() 
                + " is said to be " + actual.toASCIIString() + ".");
            assertEquals(expected, actual);
        } catch (ArithmeticException ae) {
            String msg = "Trying to compute fundamental unit of " 
                    + ring.toString() 
                    + " should not have caused ArithmeticException";
            System.out.println("\"" + ae.getMessage() + "\"");
            fail(msg);
        }
    }
    
    /**
     * Another test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. This test is specifically for the 
     * specific case of <b>Z</b>[&radic;199], the fundamental unit of which is  
     */
    @Test(timeout = 30000)
    public void testFundamentalUnitZSqrt199() {
        System.out.println("fundamentalUnit(Z[sqrt(199)])");
        RealQuadraticRing ring = new RealQuadraticRing(199);
        try {
            AlgebraicInteger result = fundamentalUnit(ring);
            System.out.println("Fundamental unit of " + ring.toASCIIString() 
                + " is said to be " + result.toASCIIString() + ".");
            assertEquals(1, result.norm());
        } catch (ArithmeticException ae) {
            System.out.println("Given that the fundamental unit of " 
                    + ring.toASCIIString()
                    + " is outside the range of QuadraticInteger to represent,");
            System.out.println("the fundamental unit function was correct to throw ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for an arithmetic overflow problem";
            fail(msg);
        }
    }
    
    /**
     * Another test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. Passing a null instance of {@link 
     * algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#fundamentalUnit(algebraics.IntegerRing) 
     * fundamentalUnit()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoFundamentalUnitForNullRing() {
        try {
            AlgebraicInteger result = fundamentalUnit(null);
            String failMsg = "Trying to get fundamental unit of null ring should have caused exception, not given result " 
                    + result.toString();
            fail(failMsg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to get fundamental unit of null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            assertNotNull("Exception message should not be null", excMsg);
            assert !excMsg.equals("") : "Exception message should not be empty";
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get fundamental unit of null ring";
            fail(failMsg);
        }
    }
    
    /**
     * Another test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. Passing an implementation of {@link 
     * algebraics.IntegerRing IntegerRing} that is not yet supported to {@link 
     * NumberTheoreticFunctionsCalculator#fundamentalUnit(algebraics.IntegerRing) 
     * fundamentalUnit()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoFundamentalUnitForUnsupportedRing() {
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(70);
        try {
            AlgebraicInteger result = fundamentalUnit(ring);
            String msg = "Trying to get fundamental unit of " + ring.toString() 
                    + " should have caused exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to get fundamental unit of " 
                    + ring.toASCIIString()
                    + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get fundamental unit of " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testNoSearchForUnitsWithNullMax() {
        try {
            Optional<AlgebraicInteger> result = searchForUnit(null);
            String msg = "Unit search with null max should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Unit search with null max correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String msg = "Exception message should not be null";
            assertNotNull(msg, excMsg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for unit search with null max";
            fail(msg);
        }
    }

    @Test
    public void testNoSearchForUnitsWithNullMinButGoodMax() {
        RealQuadraticRing ring = new RealQuadraticRing(53);
        RealQuadraticInteger max = new RealQuadraticInteger(7, 5, ring, 2);
        try {
            Optional<AlgebraicInteger> result = searchForUnit(null, max);
            String msg = "Unit search with max " + max.toString() 
                    + " but null min should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Unit search with max " + max.toASCIIString() 
                    + " but null min correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String msg = "Exception message should not be null";
            assertNotNull(msg, excMsg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for unit search with null min";
            fail(msg);
        }
    }

    @Test
    public void testNoSearchForUnitsWithNullMaxButGoodMin() {
        RealQuadraticRing ring = new RealQuadraticRing(53);
        RealQuadraticInteger min = new RealQuadraticInteger(3, 1, ring, 2);
        try {
            Optional<AlgebraicInteger> result = searchForUnit(min, null);
            String msg = "Unit search with min " + min.toString() 
                    + " but null max should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Unit search with min " + min.toASCIIString() 
                    + " but null max correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String msg = "Exception message should not be null";
            assertNotNull(msg, excMsg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for unit search with null max";
            fail(msg);
        }
    }

    @Test
    public void testNoSearchForUnitsWithNullMinAndMax() {
        try {
            Optional<AlgebraicInteger> result = searchForUnit(null, null);
            String msg = "Unit search with null min and max should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Unit search with null min and max correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String msg = "Exception message should not be null";
            assertNotNull(msg, excMsg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for unit search with null min and max";
            fail(msg);
        }
    }

    @Test
    public void testNoUnitSearchWithRingMismatch() {
        RealQuadraticRing ringA = new RealQuadraticRing(10);
        RealQuadraticRing ringB = new RealQuadraticRing(26);
        RealQuadraticInteger badMin = new RealQuadraticInteger(3, 1, ringA);
        RealQuadraticInteger badMax = new RealQuadraticInteger(11, 21, ringB);
        try {
            Optional<AlgebraicInteger> result = searchForUnit(badMin, badMax);
            String msg = "Searching for unit between " + badMin.toString() 
                    + " and " + badMax.toString() 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Searching for unit between " 
                    + badMin.toASCIIString() + " and " + badMax.toASCIIString() 
                    + " correctly caused AlgebraicDegreeOverflowException");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for mismatch of " 
                    + badMin.toString() + " and " + badMax.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testSearchForUnitProperMinMax() {
        RealQuadraticRing ring = new RealQuadraticRing(35);
        RealQuadraticInteger badMin = new RealQuadraticInteger(10, 3, ring);
        RealQuadraticInteger badMax = new RealQuadraticInteger(0, -1, ring);
        try {
            Optional<AlgebraicInteger> result = searchForUnit(badMin, badMax);
            String msg = "Searching for unit between " + badMin.toString() 
                    + " and " + badMax.toString() 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Searching for unit between " 
                    + badMin.toASCIIString() + " and " + badMax.toASCIIString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for minimum " 
                    + badMin.toString() + " that is actually greater than " 
                    + badMax.toString();
            fail(msg);
        }
    }
    
    /**
     * Test of placeInPrimarySector method, of class 
     * NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testPlaceInPrimarySector() {
        System.out.println("placeInPrimarySector");
        QuadraticRing ring = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(10, 3, ring);
        QuadraticInteger otherSectorNumber = new ImaginaryQuadraticInteger(3, -10, ring);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        otherSectorNumber = new ImaginaryQuadraticInteger(-10, -3, ring);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        otherSectorNumber = new ImaginaryQuadraticInteger(-3, 10, ring);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        ring = new ImaginaryQuadraticRing(-3);
        expResult = new ImaginaryQuadraticInteger(7, 1, ring, 2);
        otherSectorNumber = new ImaginaryQuadraticInteger(5, -3, ring, 2);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        otherSectorNumber = new ImaginaryQuadraticInteger(-1, -2, ring);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        otherSectorNumber = new ImaginaryQuadraticInteger(-7, -1, ring, 2);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        otherSectorNumber = new ImaginaryQuadraticInteger(-5, 3, ring, 2);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        otherSectorNumber = new ImaginaryQuadraticInteger(1, 2, ring);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(2);
        expResult = new RealQuadraticInteger(3, 1, ring);
        otherSectorNumber = new RealQuadraticInteger(-3, -1, ring);
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(0, 0, ring); // Zero
        result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(expResult);
        assertEquals(expResult, result);
        ring = new IllDefinedQuadraticRing(-55);
        otherSectorNumber = new IllDefinedQuadraticInteger(-3, -1, ring);
        try {
            result = NumberTheoreticFunctionsCalculator.placeInPrimarySector(otherSectorNumber);
            String failMessage = "Attempting to place in primary sector the number " + otherSectorNumber.toString() + " should have caused UnsupportedNumberDomainException, not given result " + result.toString();
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Attempting to place in primary sector the number " + otherSectorNumber.toASCIIString() + " correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        }
    }
    
    /**
     * Test of divideOutUnits method, of class 
     * NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testDivideOutUnits() {
        System.out.println("divideOutUnits");
        QuadraticRing ring = new RealQuadraticRing(7);
        QuadraticInteger expResult = new RealQuadraticInteger(3, 1, ring);
        QuadraticInteger numWUnitFactors = new RealQuadraticInteger(182115, 68833, ring);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.divideOutUnits(numWUnitFactors);
        assertEquals(expResult, result);
        numWUnitFactors = new RealQuadraticInteger(-11427, 4319, ring);
        result = NumberTheoreticFunctionsCalculator.divideOutUnits(numWUnitFactors);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(0, 0, ring); // Zero
        result = NumberTheoreticFunctionsCalculator.divideOutUnits(expResult);
        assertEquals(expResult, result);
        ring = new IllDefinedQuadraticRing(-55);
        numWUnitFactors = new IllDefinedQuadraticInteger(-3, -1, ring);
        try {
            result = NumberTheoreticFunctionsCalculator.divideOutUnits(numWUnitFactors);
            String failMessage = "Attempting to divide units out of " + numWUnitFactors.toString() + " should have caused UnsupportedNumberDomainException, not given result " + result.toString();
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Attempting to divide units out of " + numWUnitFactors.toASCIIString() + " correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        }
    }
    
    /**
     * Test of getNegOneInRing method, of class 
     * NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testGetNegOneInRing() {
        System.out.println("getNegOneInRing");
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(-1, 0, 
                RING_GAUSSIAN);
        AlgebraicInteger result = getNegOneInRing(RING_GAUSSIAN);
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(-1, 0, RING_EISENSTEIN);
        result = getNegOneInRing(RING_EISENSTEIN);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(-1, 0, RING_ZPHI);
        result = getNegOneInRing(RING_ZPHI);
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of getNegOneInRing method, of class 
     * NumberTheoreticFunctionsCalculator. Passing a null instance of {@link 
     * algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#getNegOneInRing(algebraics.IntegerRing) 
     * getNegOneInRing()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoNegOneForNullRing() {
        try {
            AlgebraicInteger result = getNegOneInRing(null);
            String msg = "Trying to get -1 for null ring should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to get -1 for null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            assertNotNull("Exception message should not be null", excMsg);
            assert !excMsg.equals("") : "Exception message should not be empty";
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get -1 for null ring";
            fail(failMsg);
        }
    }
    
    /**
     * Another test of getNegOneInRing method, of class 
     * NumberTheoreticFunctionsCalculator. Passing an instance of an unsupported
     * implementation of {@link algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#getNegOneInRing(algebraics.IntegerRing) 
     * getNegOneInRing()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoNegOneForUnsupportedRing() {
        BadCubicRing ring = new BadCubicRing();
        try {
            AlgebraicInteger result = getNegOneInRing(ring);
            String msg = "Trying to get -1 for unsupported ring " 
                    + ring.toString() 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to get -1 for unsupported ring " 
                    + ring.toString() 
                    + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get -1 for unsupported ring " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    /**
     * Test of getZeroInRing method, of class 
     * NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testGetZeroInRing() {
        System.out.println("getZeroInRing");
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 0, 
                RING_GAUSSIAN);
        AlgebraicInteger result = getZeroInRing(RING_GAUSSIAN);
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(0, 0, RING_EISENSTEIN);
        result = getZeroInRing(RING_EISENSTEIN);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(0, 0, RING_ZPHI);
        result = getZeroInRing(RING_ZPHI);
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of getZeroInRing method, of class 
     * NumberTheoreticFunctionsCalculator. Passing a null instance of {@link 
     * algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#getZeroInRing(algebraics.IntegerRing) 
     * getZeroInRing()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoZeroForNullRing() {
        try {
            AlgebraicInteger result = getZeroInRing(null);
            String msg = "Trying to get 0 for null ring should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to get 0 for null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            assertNotNull("Exception message should not be null", excMsg);
            assert !excMsg.equals("") : "Exception message should not be empty";
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get 0 for null ring";
            fail(failMsg);
        }
    }
    
    /**
     * Another test of getZeroInRing method, of class 
     * NumberTheoreticFunctionsCalculator. Passing an instance of an unsupported
     * implementation of {@link algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#getZeroInRing(algebraics.IntegerRing) 
     * getZeroInRing()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoZeroForUnsupportedRing() {
        BadCubicRing ring = new BadCubicRing();
        try {
            AlgebraicInteger result = getZeroInRing(ring);
            String msg = "Trying to get 0 for unsupported ring " 
                    + ring.toString() 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to get 0 for unsupported ring " 
                    + ring.toString() 
                    + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get 0 for unsupported ring " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    /**
     * Test of getOneInRing method, of class NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testGetOneInRing() {
        System.out.println("getOneInRing");
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(1, 0, 
                RING_GAUSSIAN);
        AlgebraicInteger result = getOneInRing(RING_GAUSSIAN);
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(1, 0, RING_EISENSTEIN);
        result = getOneInRing(RING_EISENSTEIN);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(1, 0, RING_ZPHI);
        result = getOneInRing(RING_ZPHI);
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of getOneInRing method, of class 
     * NumberTheoreticFunctionsCalculator. Passing a null instance of {@link 
     * algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#getOneInRing(algebraics.IntegerRing) 
     * getOneInRing()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoOneForNullRing() {
        try {
            AlgebraicInteger result = getOneInRing(null);
            String msg = "Trying to get 1 for null ring should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to get 1 for null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            assertNotNull("Exception message should not be null", excMsg);
            assert !excMsg.equals("") : "Exception message should not be empty";
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get 1 for null ring";
            fail(failMsg);
        }
    }
    
    /**
     * Another test of getOneInRing method, of class 
     * NumberTheoreticFunctionsCalculator. Passing an instance of an unsupported
     * implementation of {@link algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#getOneInRing(algebraics.IntegerRing) 
     * getOneInRing()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoOneForUnsupportedRing() {
        BadCubicRing ring = new BadCubicRing();
        try {
            AlgebraicInteger result = getOneInRing(ring);
            String msg = "Trying to get 1 for unsupported ring " 
                    + ring.toString() 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to get 1 for unsupported ring " 
                    + ring.toString() 
                    + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get 1 for unsupported ring " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testIsUFD() {
        System.out.println("isUFD");
        QuadraticRing ring = new ImaginaryQuadraticRing(-3);
        String msg = ring.toString() + " should be found to be UFD";
        assert isUFD(ring) : msg;
        ring = new RealQuadraticRing(29);
        msg = ring.toString() + " should be found to be UFD";
        assert isUFD(ring) : msg;
    }
    
    @Test
    public void testIsNotUFD() {
        QuadraticRing ring = new ImaginaryQuadraticRing(-31);
        String msg = ring.toString() + " should not be found to be UFD";
        assert !isUFD(ring) : msg;
        ring = new RealQuadraticRing(30);
        msg = ring.toString() + " should not be found to be UFD";
        assert !isUFD(ring) : msg;
    }
    
    @Test
    public void testNullRingNotUFD() {
        try {
            boolean result = isUFD(null);
            String msg = "Trying to determine if null ring is UFD should not have given result " 
                    + result;
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to determine if null ring is UFD correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            String msg = "NullPointerException message should not be null";
            assert excMsg != null : msg;
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to determine if null ring is UFD";
            fail(msg);
        }
    }
    
    @Test
    public void testMaybeUFDButNotYetSupported() {
        QuadraticRing ring = new IllDefinedQuadraticRing(-10);
        try {
            boolean result = isUFD(ring);
            String msg = "Trying to determine if " + ring.toString() 
                    + " is UFD should have caused an exception, not given result " 
                    + result;
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to determine if " + ring.toString() 
                    + " is UFD correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to determine if " 
                    + ring.toString() + " is UFD or not";
            fail(msg);
        }
    }
    
    /**
     * Test of fieldClassNumber method, of class 
     * NumberTheoreticFunctionsCalculator. Five specific imaginary quadratic 
     * rings and five specific real quadratic rings are tested for their known 
     * class numbers. Then one random imaginary and one random real are tested 
     * to make sure the reported class number is greater than 1. If this test 
     * fails or causes an error on one of the randomly chosen rings, check the 
     * result of the test of {@link 
     * NumberTheoreticFunctionsCalculator#randomSquarefreeNumber(int) 
     * randomSquarefreeNumber(int)}. Lastly, a check of 
     * <b>Z</b>[&radic;&minus;589], which in one early run of the test was said 
     * to have class number -12 even though it very clearly should have a class 
     * number of at least 3.
     */
    // TODO: Break this test up into smaller tests
    @Test
    public void testFieldClassNumber() {
        System.out.println("fieldClassNumber");
        int expResult = 1;
        IntegerRing ring = new ImaginaryQuadraticRing(-1);
        String assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        int result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        ring = new RealQuadraticRing(2);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        expResult = 2;
        ring = new ImaginaryQuadraticRing(-5);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        ring = new RealQuadraticRing(10);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        expResult = 3;
        ring = new ImaginaryQuadraticRing(-23);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        ring = new RealQuadraticRing(79);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        expResult = 4;
        ring = new ImaginaryQuadraticRing(-21);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        ring = new RealQuadraticRing(82);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        expResult = 5;
        ring = new ImaginaryQuadraticRing(-47);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        ring = new RealQuadraticRing(401);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
        int randomD = -NumberTheoreticFunctionsCalculator.randomSquarefreeNumber(1000);
        if (randomD > -164) {
            randomD = -165;
        }
        ring = new ImaginaryQuadraticRing(randomD);
        assertionMessage = ring.toString() + " should be found to have class number greater than 1.";
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        System.out.println(ring.toASCIIString() + ", which was chosen randomly, is said to have class number " + result + ".");
        assertTrue(assertionMessage, result > 1);
        randomD = NumberTheoreticFunctionsCalculator.randomSquarefreeNumber(20);
        if ((randomD % 5) != 0) {
            randomD *= 5; // Make sure it's divisible by 5
        }
        if (randomD == 5 || randomD == 10) {
            randomD = 15; // But also make sure it's not 5 itself, nor 10 which was already tested
        }
        ring = new RealQuadraticRing(randomD);
        assertionMessage = ring.toString() + " should be found to have class number greater than 1.";
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        System.out.println(ring.toASCIIString() + ", which was chosen randomly, is said to have class number " + result + ".");
        assertTrue(assertionMessage, result > 1);
        expResult = 16;
        ring = new ImaginaryQuadraticRing(-589);
        assertionMessage = ring.toString() + " should be found to have class number " + expResult;
        result = NumberTheoreticFunctionsCalculator.fieldClassNumber(ring);
        assertEquals(assertionMessage, expResult, result);
    }
    
    /**
     * Another test of fieldClassNumber method, of class 
     * NumberTheoreticFunctionsCalculator. Passing a null instance of {@link 
     * algebraics.IntegerRing IntegerRing} to {@link 
     * NumberTheoreticFunctionsCalculator#fieldClassNumber(algebraics.IntegerRing) 
     * fieldClassNumber()} should cause an exception, not give any particular 
     * result.
     */
    @Test
    public void testNoClassNumberForNullRing() {
        try {
            int result = NumberTheoreticFunctionsCalculator.fieldClassNumber(null);
            String failMsg = "Trying to get field class number of null ring should have caused exception, not given result " 
                    + result;
            fail(failMsg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to get field class number of null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            System.out.println("\"" + excMsg + "\"");
            assertNotNull("Exception message should not be null", excMsg);
            assert !excMsg.equals("") : "Exception message should not be empty";
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to get field class number of null ring";
            fail(failMsg);
        }
    }
    
    /**
     * Test of the randomNumber function, of the 
     * NumberTheoreticFunctionsCalculator class.
     */
    @Test
    public void testRandomNumber() {
        System.out.println("randomNumber");
        int capacity = 100;
        Set<Integer> numbers = new HashSet<>(capacity);
        int bound = capacity * capacity;
        for (int i = 0; i < capacity; i++) {
            numbers.add(NumberTheoreticFunctionsCalculator
                    .randomNumber(bound));
        }
        int expected = 7 * capacity / 10;
        int actual = numbers.size();
        String msg = "Given " + capacity 
                + " random number calls, there should be more than " + expected 
                + " distinct, got " + actual + " distinct";
        assert expected < actual : msg;
    }
    
    /**
     * Test of the randomSquarefreeNumber function, of the  
     * NumberTheoreticFunctionsCalculator class. This test doesn't check whether  
     * the distribution is random enough, only that the numbers produced are  
     * indeed squarefree, that they don't have repeated prime factors. The test  
     * will keep going until either an assertion fails or a number is produced 
     * with 9 for a least significant digit, whichever happens first.
     */
    @Test
    public void testRandomSquarefreeNumber() {
        System.out.println("randomSquarefreeNumber");
        int bound = primesList.get(primesListLength - 1) 
                * primesList.get(primesListLength - 1);
        boolean keepGoing;
        do {
            int potentialSquarefreeNumber = randomSquarefreeNumber(bound);
            assertSquarefreeLimited(potentialSquarefreeNumber);
            String msg = "The number " + potentialSquarefreeNumber 
                    + " is not greater than the bound " + bound;
            assert potentialSquarefreeNumber < bound : msg;
            msg = "The number " + potentialSquarefreeNumber 
                    + " is greater than 0";
            assert potentialSquarefreeNumber > 0 : msg;
            keepGoing = potentialSquarefreeNumber % 10 != 9;
        } while (keepGoing);
    }
    
    private static void assertModulus(int i, int n, int m) {
        String msg = "Number " + i + " should be congruent to " + n + " mod " 
                + m;
        int remainder = i % m;
        if (remainder < 0) remainder += m;
        assert remainder == n : msg;
    }
    
    /**
     * Another test of the randomSquarefreeNumberMod function, of the 
     * NumberTheoreticFunctionsCalculator class. Modulus 0 should cause 
     * ArithmeticException.
     */
    @Test
    public void testRandomSquarefreeNumberModZeroCausesException() {
        int n = RANDOM.nextInt();
        int m = 0;
        String msgPart = "Asking for random squarefree number congruent to " + n 
                + " modulo " + m;
        try {
            int badResult = NumberTheoreticFunctionsCalculator
                    .randomSquarefreeNumberMod(n, m);
            String msg = msgPart + " somehow gave " + badResult;
            fail(msg);
        } catch (ArithmeticException ae) {
            System.out.println(msgPart 
                    + " correctly caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = msgPart + " shouldn't have caused " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    /**
     * Test of the randomSquarefreeNumberMod function, of the 
     * NumberTheoreticFunctionsCalculator class. This test chooses a small prime 
     * number for the modulus.
     */
    @Test
    public void testRandomSquarefreeNumberMod() {
        System.out.println("randomSquarefreeNumberMod");
        int expected = 12;
        int numberOfCalls = 3 * expected / 2;
        int p = primesList.get(RANDOM.nextInt(primesListLength));
        for (int n = 0; n < p; n++) {
            Set<Integer> numbers = new HashSet<>(expected);
            for (int i = 0; i < numberOfCalls; i++) {
                int number = NumberTheoreticFunctionsCalculator
                        .randomSquarefreeNumberMod(n, p);
                assertSquarefree(number);
                assertModulus(number, n, p);
                String msg = "Number " + number + " should be greater than 0";
                assert number > 0 : msg;
                numbers.add(number);
            }
            int actual = numbers.size();
            String msg = "Calling randomSquarefreeNumberMod() " + numberOfCalls 
                    + " times should've given at least " + expected 
                    + " distinct numbers";
            assert expected < actual : msg;
        }
    }
    
    /**
     * Another test of the randomSquarefreeNumberMod function, of the 
     * NumberTheoreticFunctionsCalculator class. This test chooses the cube of a 
     * prime and tries to use a remainder for which it is impossible to get a 
     * squarefree number. For example, x = 4 mod 8 has infinitely many solutions 
     * but none of them are squarefree numbers.
     */
    @Test(timeout = 10000)
    public void testRandomSquarefreeNumberSquareModuloCube() {
        int prime = primesList.get(RANDOM.nextInt(primesListLength));
        int square = prime * prime;
        int cube = prime * square;
        System.out.println("Asking for number x = " + square + " mod " + cube);
        try {
            int badResult = NumberTheoreticFunctionsCalculator
                    .randomSquarefreeNumberMod(square, cube);
            String msg = "Number " + badResult 
                    + " is said to be a squarefree number congruent to "  
                    + square + " mod " + cube;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Asking for squarefree number congruent to " 
                    + square + " mod " + cube 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for squarefree congruent to " 
                    + square + " mod " + cube;
            fail(msg);
        }
    }
    
    /**
     * Test of the randomSquarefreeNumberOtherThan function, of the 
     * NumberTheoreticFunctionsCalculator class.
     */
    @Test
    public void testRandomSquarefreeNumberOtherThan() {
        System.out.println("randomSquarefreeNumberOtherThan");
        int bound = primesList.get(primesListLength - 1);
        Set<Integer> numbers = new HashSet<>(bound);
        for (int n = 0; n < bound; n++) {
            int potentialSquarefreeNumber 
                    = randomSquarefreeNumberOtherThan(n, bound);
            numbers.add(potentialSquarefreeNumber);
            String msg = "Asking for squarefree number other than " + n 
                    + " should not have given that number";
            assertNotEquals(msg, n, potentialSquarefreeNumber);
            assertSquarefreeLimited(potentialSquarefreeNumber);
            msg = "The number " + potentialSquarefreeNumber 
                    + " is not greater than the bound " + bound;
            assert potentialSquarefreeNumber < bound : msg;
            msg = "The number " + potentialSquarefreeNumber 
                    + " is greater than 0";
            assert potentialSquarefreeNumber > 0 : msg;
        }
        int expected = 2 * bound / 5;
        int actual = numbers.size();String msg = "After " + bound 
                + " random number calls, should've gotten at least " + expected 
                + " distinct, got " + actual + " distinct";
        assert expected < actual : msg;
    }
    
}
