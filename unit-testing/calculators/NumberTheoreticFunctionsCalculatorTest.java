/*
 * Copyright (C) 2019 Alonso del Arte
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
import algebraics.NonEuclideanDomainException;
import algebraics.NonUniqueFactorizationDomainException;
import algebraics.NotDivisibleException;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.IllDefinedQuadraticInteger;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import algebraics.quartics.Zeta8Integer;
import algebraics.quartics.Zeta8Ring;
import static algebraics.quadratics.ImaginaryQuadraticRingTest.TEST_DELTA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
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
 * <li><a href="http://oeis.org/A000045">A000040</a>: The Fibonacci numbers</li>
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
     * A List of the first few prime numbers, to be used in some of the tests. 
     * It will be populated during {@link #setUpClass() setUpClass()}.
     */
    private static List<Integer> primesList;
    
    /**
     * The size of primesList, to be determined during {@link #setUpClass() 
     * setUpClass()}. This value will be reported on the console before the 
     * tests begin.
     */
    private static int primesListLength;
    
    /**
     * A List of composite numbers, which may or may not include 
     * {@link #PRIME_LIST_THRESHOLD PRIME_LIST_THRESHOLD}. It will be populated 
     * during {@link #setUpClass() setUpClass()}.
     */
    private static List<Integer> compositesList;
    
    /**
     * A List of Fibonacci numbers. It will be populated during {@link 
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
    
    /**
     * Sets up a List of the first few consecutive primes, the first few 
     * composite numbers, the first few Fibonacci numbers and the Heegner 
     * "companion primes." This provides most of what is needed for the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        /* First, to generate a list of the first few consecutive primes, using 
        the sieve of Eratosthenes. */
        int threshold, halfThreshold;
        if (PRIME_LIST_THRESHOLD < 0) {
            threshold = (-1) * PRIME_LIST_THRESHOLD;
        } else {
            threshold = PRIME_LIST_THRESHOLD;
        }
        halfThreshold = threshold / 2;
        primesList = new ArrayList<>();
        primesList.add(2); // Add 2 as a special case
        boolean[] primeFlags = new boolean[halfThreshold];
        for (int i = 0; i < halfThreshold; i++) {
            primeFlags[i] = true; // Presume all odd numbers prime for now
        }
        int currPrime = 3;
        int twiceCurrPrime, currIndex;
        while (currPrime < threshold) {
            primesList.add(currPrime);
            twiceCurrPrime = 2 * currPrime;
            for (int j = currPrime * currPrime; j < threshold; j += twiceCurrPrime) {
                currIndex = (j - 3)/2;
                primeFlags[currIndex] = false;
            }
            do {
                currPrime += 2;
                currIndex = (currPrime - 3)/2;
            } while (currIndex < (halfThreshold - 1) && !primeFlags[currIndex]);
        }
        primesListLength = primesList.size();
        /* Now to make a list of composite numbers, from 4 up to and perhaps 
           including PRIME_LIST_THRESHOLD. */
        compositesList = new ArrayList<>();
        for (int c = 4; c < PRIME_LIST_THRESHOLD; c += 2) {
            compositesList.add(c);
            if (!primeFlags[c/2 - 1]) {
                compositesList.add(c + 1);
            }
        }
        System.out.println("setUpClass() has generated a list of the first " + primesListLength + " consecutive primes.");
        System.out.println("prime(" + primesListLength + ") = " + primesList.get(primesListLength - 1));
        System.out.println("There are " + (PRIME_LIST_THRESHOLD - (primesListLength + 1)) + " composite numbers up to " + PRIME_LIST_THRESHOLD + ".");
        // And now to make a list of Fibonacci numbers
        fibonacciList = new ArrayList<>();
        fibonacciList.add(0);
        fibonacciList.add(1);
        threshold = (Integer.MAX_VALUE - 3)/4; // Repurposing this variable
        currIndex = 2; // Also repurposing this one
        int currFibo = 1;
        while (currFibo < threshold) {
            currFibo = fibonacciList.get(currIndex - 2) + fibonacciList.get(currIndex - 1);
            fibonacciList.add(currFibo);
            currIndex++;
        }
        currIndex--; // Step one back to index last added Fibonacci number
        System.out.println("setUpClass() has generated a list of the first " + fibonacciList.size() + " Fibonacci numbers.");
        System.out.println("Fibonacci(" + currIndex + ") = " + fibonacciList.get(currIndex));
        /* And last but not least, to make what I'm calling, for lack of a 
           better term, "the Heegner companion primes." */
        int absD, currDiff, currCompCand, currSqrIndex, currSqrDMult;
        boolean numNotFoundYet;
        for (int d = 0; d < NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS.length; d++) {
            absD = (-1) * NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS[d];
            currIndex = 0;
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
                    if (Math.sqrt(currDiff) == Math.floor(Math.sqrt(currDiff))) {
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
        System.out.println("setUpClass() has generated a list of \"Heegner companion primes\": ");
        for (int dReport = 0; dReport < NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS.length; dReport++) {
            System.out.print(HEEGNER_COMPANION_PRIMES[dReport] + " for " + NumberTheoreticFunctionsCalculator.HEEGNER_NUMBERS[dReport] + ", ");
        }
        System.out.println();
    }
    
    /**
     * Test of sortListAlgebraicIntegersByNorm method, of class 
     * NumberTheoreticFunctionsCalculator. If norms may be negative, the 
     * algebraic integers should be sorted by the absolute value of the norm. No 
     * expectation is laid out for what should happen if the algebraic integers 
     * to be sorted come from different rings.
     */
    @Test
    public void testSortListAlgebraicIntegersByNorm() {
        System.out.println("sortListAlgebraicIntegersByNorm");
        QuadraticRing ring = new ImaginaryQuadraticRing(-7);
        QuadraticInteger numberA = new ImaginaryQuadraticInteger(1, 0, ring); // Unit
        QuadraticInteger numberB = new ImaginaryQuadraticInteger(-1, 1, ring, 2); // -1/2 + sqrt(-7)/2, norm 2
        QuadraticInteger numberC = new ImaginaryQuadraticInteger(1, 2, ring); // 1 + 2sqrt(-7), norm 29
        List<AlgebraicInteger> expResult = new ArrayList<>();
        expResult.add(numberA);
        expResult.add(numberB);
        expResult.add(numberC);
        List<AlgebraicInteger> unsortedList = new ArrayList<>();
        unsortedList.add(numberC);
        unsortedList.add(numberA);
        unsortedList.add(numberB);
        List<AlgebraicInteger> result = NumberTheoreticFunctionsCalculator.sortListAlgebraicIntegersByNorm(unsortedList);
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(7);
        numberA = new RealQuadraticInteger(8, 3, ring); // Unit
        numberB = new RealQuadraticInteger(3, -1, ring); // 3 - sqrt(-7), norm 2
        numberC = new RealQuadraticInteger(18, 7, ring); // 18 + 7sqrt(7), norm -19
        expResult.clear();
        expResult.add(numberA);
        expResult.add(numberB);
        expResult.add(numberC);
        unsortedList.clear();
        unsortedList.add(numberC);
        unsortedList.add(numberA);
        unsortedList.add(numberB);
        result = NumberTheoreticFunctionsCalculator.sortListAlgebraicIntegersByNorm(unsortedList);
        assertEquals(expResult, result);
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
        r = new IllDefinedQuadraticRing(50);
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

    /**
     * Test of isPrime method, of class NumberTheoreticFunctionsCalculator. The 
     * numbers listed in Sloane's A000040, as well as those same numbers 
     * multiplied by &minus;1, should all be identified as prime. Likewise, the 
     * numbers listed in Sloane's A018252, as well as those same numbers 
     * multiplied by &minus;1, should all be identified as not prime. As for 0, 
     * I'm not sure; if you like you can uncomment the line for it and perhaps 
     * change assertFalse to assertTrue.
     */
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
        for (Integer compositeNum : compositesList) {
            assertFalse(NumberTheoreticFunctionsCalculator.isPrime(compositeNum));
            assertFalse(NumberTheoreticFunctionsCalculator.isPrime(-compositeNum));
        }
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
                        String failMessage = "Trying to divide " + numberFromNonUFD.toASCIIString() + " by 2 should not have caused NotDivisibleException: \"" + nde.getMessage() + "\"";
                        fail(failMessage);
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
                        String failMessage = "Trying to divide " + numberFromNonUFD.toASCIIString() + " by 2 should not have caused NotDivisibleException: \"" + nde.getMessage() + "\"";
                        fail(failMessage);
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
            String failMessage = "Somehow determined that " + z.toASCIIString() + " is ";
            if (compositeFlag) {
                failMessage = failMessage + "not ";
            }
            failMessage = failMessage + " prime.";
            System.out.println(failMessage);
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("isPrime on unsupported number domain correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        }
    }
    
    /**
     * Test of isIrreducible method, of class 
     * NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testIsIrreducible() {
        System.out.println("isIrreducible");
        QuadraticRing currRing;
        QuadraticInteger currQuadrInt;
        String assertionMessage;
        /* The number 1 + sqrt(d) should be irreducible but not prime in each
           domain Z[sqrt(d)] for squarefree negative d = 3 mod 4. But (1 + 
           sqrt(d))^2 should not be, nor the conjugate of that number. */
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
        for (int discrUFDIm : NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D) {
            currRing = new ImaginaryQuadraticRing(discrUFDIm);
            for (int v = -3; v < 8; v++) {
                for (int w = 9; w < 12; w++) {
                    currInt = new ImaginaryQuadraticInteger(v, w, currRing);
                    assertionMessage = currInt.toString() + " should be found to be both prime and irreducible, or neither.";
                    assertEquals(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(currInt), NumberTheoreticFunctionsCalculator.isIrreducible(currInt));
                }
            }
        }
        for (int discrUFDRe : NumberTheoreticFunctionsCalculator.NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D) {
            currRing = new RealQuadraticRing(discrUFDRe);
            for (int x = -3; x < 8; x++) {
                for (int y = 1; y < 5; y++) {
                    currInt = new RealQuadraticInteger(x, y, currRing);
                    if (Math.abs(currInt.norm()) == 1) {
                        System.out.println("Skipping " + currInt.toASCIIString() + " for this test.");
                    } else {
                        assertionMessage = currInt.toString() + " should be found to be both prime and irreducible, or neither.";
                        assertEquals(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(currInt), NumberTheoreticFunctionsCalculator.isIrreducible(currInt));
                    }
                }
            }
        }
        for (int discrNonUFDIm = -5; discrNonUFDIm > -200; discrNonUFDIm -= 4) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(discrNonUFDIm)) {
                currRing = new ImaginaryQuadraticRing(discrNonUFDIm);
                currInt = new ImaginaryQuadraticInteger(2, 0, currRing);
                assertionMessage = "2 in " + currRing.toString() + " should be found to be irreducible";
                assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currInt));
                assertionMessage = assertionMessage + " but not prime";
                assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isPrime(currInt));
            }
        }
        for (int discrNonUFDRe = 10; discrNonUFDRe < 200; discrNonUFDRe += 5) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(discrNonUFDRe)) {
                currRing = new RealQuadraticRing(discrNonUFDRe);
                for (int p = 3; p < 20; p += 2) {
                    if (NumberTheoreticFunctionsCalculator.isPrime(p)) {
                        currInt = new RealQuadraticInteger(p, 0, currRing);
                        if (NumberTheoreticFunctionsCalculator.isPrime(currInt)) {
                            assertionMessage = "Since " + p + " is said to be prime in " + currRing.toString() + ", it should also be said to be irreducible.";
                            assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isIrreducible(currInt));
                        }
                    }
                }
            }
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
        // And lastly to check for exceptions for bad arguments.
        try {
            byte attempt = NumberTheoreticFunctionsCalculator.symbolLegendre(7, 2);
            fail("Calling Legendre(7, 2) should have triggered an exception, not given result " + attempt + ".");
        } catch (IllegalArgumentException iae) {
            System.out.println("Calling Legendre(7, 2) correctly triggered IllegalArgumentException. \"" + iae.getMessage() + "\"");
        }
    }

    /**
     * Test of symbolJacobi method, of class NumberTheoreticFunctionsCalculator. 
     * First, it checks that Legendre(<i>a</i>, <i>p</i>) = Jacobi(<i>a</i>, 
     * <i>p</i>), where <i>p</i> is an odd prime. Next, it checks that 
     * Jacobi(<i>n</i>, <i>pq</i>) = Legendre(<i>n</i>, <i>p</i>) 
     * Legendre(<i>n</i>, <i>q</i>). If the Legendre symbol test fails, the 
     * result of this test is meaningless. Then follows the actual business of 
     * checking Jacobi(<i>n</i>, <i>m</i>).
     */
    @Test
    public void testJacobiSymbol() {
        System.out.println("symbolJacobi");
        System.out.println("Checking overlap with Legendre symbol...");
        byte expResult, result;
        for (int i = 1; i < primesListLength; i++) {
            for (int a = 5; a < 13; a++) {
                expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(a, primesList.get(i));
                result = NumberTheoreticFunctionsCalculator.symbolJacobi(a, primesList.get(i));
                assertEquals(expResult, result);
            }
        }
        System.out.println("Now checking Jacobi symbol per se...");
        int p, q, m;
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
            System.out.println("Calling Jacobi(7, 2) correctly triggered IllegalArgumentException. \"" + iae.getMessage() + "\"");
        }
    }

    /**
     * Test of symbolKronecker method, of class 
     * NumberTheoreticFunctionsCalculator. First, it checks that 
     * Legendre(<i>a</i>, <i>p</i>) = Kronecker(<i>a</i>, <i>p</i>), where 
     * <i>p</i> is an odd prime. Next, it checks that Jacobi(<i>n</i>, <i>m</i>) 
     * = Kronecker(<i>n</i>, <i>m</i>). If either the Legendre symbol test or 
     * the Jacobi symbol test fails, the result of this test is meaningless. 
     * Then follows the actual business of checking Kronecker(<i>n</i>, 
     * &minus;2), Kronecker(<i>n</i>, &minus;1) and Kronecker(<i>n</i>, 2). On 
     * another occasion I might add a few multiplicative tests.
     */
    @Test
    public void testKroneckerSymbol() {
        System.out.println("symbolKronecker");
        byte expResult, result;
        System.out.println("Checking overlap with Legendre symbol...");
        for (int i = 1; i < primesListLength; i++) {
            for (int a = 7; a < 11; a++) {
                expResult = NumberTheoreticFunctionsCalculator.symbolLegendre(a, primesList.get(i));
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(a, primesList.get(i));
                assertEquals(expResult, result);
            }
        }
        System.out.println("Checking overlap with Jacobi symbol...");
        for (int j = -10; j < 10; j++) {
            for (int b = 5; b < 15; b += 2) {
                expResult = NumberTheoreticFunctionsCalculator.symbolJacobi(j, b);
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(j, b);
                assertEquals(expResult, result);
            }
        }
        System.out.println("Now checking Kronecker symbol per se...");
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
            if (m < 0) {
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 3, -2);
                assertEquals(expResult, result);
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 5, -2);
                assertEquals(expResult, result);
            } else {
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 1, -2);
                assertEquals(expResult, result);
                result = NumberTheoreticFunctionsCalculator.symbolKronecker(m + 7, -2);
                assertEquals(expResult, result);
            }
        }
    }
    
    /**
     * Test of isDivisibleBy method, of class 
     * NumberTheoreticFunctionsCalculator. 
     */
    @Test
    public void testIsDivisibleBy() {
        System.out.println("isDivisibleBy");
        QuadraticRing r = new RealQuadraticRing(14);
        QuadraticInteger a = new RealQuadraticInteger(0, -1, r);
        QuadraticInteger b = new RealQuadraticInteger(4, 1, r);
        String assertionMessage = a.toString() + " should be found to be divisible by " + b.toString() + ".";
        assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isDivisibleBy(a, b));
        assertionMessage = b.toString() + " should not be found to be divisible by " + a.toString() + ".";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isDivisibleBy(b, a));
        b = new RealQuadraticInteger(7, -2, r);
        assertionMessage = a.toString() + " should be found to be divisible by " + b.toString() + ".";
        assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isDivisibleBy(a, b));
        assertionMessage = b.toString() + " should not be found to be divisible by " + a.toString() + ".";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isDivisibleBy(b, a));
        b = new RealQuadraticInteger(0, 0, r); // Zero
        assertionMessage = a.toString() + " should not be found to be divisible by " + b.toString() + ".";
        assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isDivisibleBy(a, b));
        assertionMessage = b.toString() + " should be found to be divisible by " + a.toString() + ".";
        assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isDivisibleBy(b, a));
        a = new RealQuadraticInteger(7, 1, r);
        r = new ImaginaryQuadraticRing(-7); // Making sure a and b are from different rings now
        b = new ImaginaryQuadraticInteger(0, 1, r);
        try {
            boolean divisibleFlag = NumberTheoreticFunctionsCalculator.isDivisibleBy(a, b);
            String failMessage = "Trying to ascertain the divisibility of " + a.toString() + " by " + b.toString() + " should have caused an exception, not given the result that it is";
            if (!divisibleFlag) {
                failMessage = failMessage + " not";
            }
            failMessage = failMessage + " divisible.";
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Trying to ascertain the divisibility of " + a.toASCIIString() + " by " + b.toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException.");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = "Trying to ascertain the divisibility of " + a.toString() + " by " + b.toString() + " should have caused AlgebraicDegreeOverflowException, not " + e.getClass().getName() + ".";
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
            fail(failMessage);
        }
    }
    
    /**
     * Test of isSquareFree method, of class NumberTheoreticFunctionsCalculator. 
     * Prime numbers should be found to be squarefree, squares of primes should 
     * not.
     */
    @Test
    public void testIsSquareFree() {
        System.out.println("isSquareFree");
        String assertionMessage;
        int number;
        for (int i = 0; i < primesListLength - 1; i++) {
            number = primesList.get(i) * primesList.get(i + 1); // A squarefree semiprime, pq
            assertionMessage = number + " should have been found to be squarefree";
            assertTrue(assertionMessage, NumberTheoreticFunctionsCalculator.isSquareFree(number));
            number *= primesList.get(i); // Repeat one prime factor, (p^2)q
            assertionMessage = number + " should not have been found to be squarefree";
            assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isSquareFree(number));
            number /= primesList.get(i + 1); // Now this should be p^2
            assertionMessage = number + " should not have been found to be squarefree";
            assertFalse(assertionMessage, NumberTheoreticFunctionsCalculator.isSquareFree(number));
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
    @Test
    public void testEuclideanGCD() {
        System.out.println("euclideanGCD");
        int result;
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
     * fleshed out support. However, it should be able to answer that the 
     * fundamental unit of the ring of algebraic integers of 
     * <b>Q</b>(&zeta;<sub>8</sub>) is &zeta;<sub>8</sub>.
     */
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
        ring = new IllDefinedQuadraticRing(12);
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
        expResult = new Zeta8Integer(0, 1, 0, 0);
        result = NumberTheoreticFunctionsCalculator.fundamentalUnit(expResult.getRing());
        assertEquals(expResult, result);
    }
    
    /**
     * Test of fundamentalUnit method, of class 
     * NumberTheoreticFunctionsCalculator. This test is specifically for the 
     * specific case of <b>Z</b>[&radic;103], the fundamental unit of which is  
     * a number with a value of approximately 455055.9999978. An implementation 
     * of a brute force algorithm is likely to fail this test if it takes too 
     * long, even if it would eventually come up with the right result.
     */
    @Ignore
    @Test(timeout = 30000)
    public void testFundamentalUnitZSqrt103() {
        System.out.println("fundamentalUnit(Z[sqrt(103)])");
        RealQuadraticRing ring = new RealQuadraticRing(103);
        RealQuadraticInteger expResult = new RealQuadraticInteger(227528, 22419, ring);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.fundamentalUnit(ring);
        System.out.println("Fundamental unit of " + ring.toASCIIString() + " is said to be " + result.toASCIIString() + ".");
        assertEquals(expResult, result);
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
     * Test of getOneInRing method, of class NumberTheoreticFunctionsCalculator.
     */
    @Test
    public void testGetOneInRing() {
        System.out.println("getOneInRing");
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(1, 0, NumberTheoreticFunctionsCalculator.RING_GAUSSIAN);
        AlgebraicInteger result = NumberTheoreticFunctionsCalculator.getOneInRing(NumberTheoreticFunctionsCalculator.RING_GAUSSIAN);
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(1, 0, NumberTheoreticFunctionsCalculator.RING_EISENSTEIN);
        result = NumberTheoreticFunctionsCalculator.getOneInRing(NumberTheoreticFunctionsCalculator.RING_EISENSTEIN);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(1, 0, NumberTheoreticFunctionsCalculator.RING_ZPHI);
        result = NumberTheoreticFunctionsCalculator.getOneInRing(NumberTheoreticFunctionsCalculator.RING_ZPHI);
        assertEquals(expResult, result);
        Zeta8Ring z8r = new Zeta8Ring();
        Zeta8Integer expResDeg4 = new Zeta8Integer(1, 0, 0, 0);
        result = NumberTheoreticFunctionsCalculator.getOneInRing(z8r);
        assertEquals(expResDeg4, result);
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
        ring = new Zeta8Ring();
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
     * Test of randomSquarefreeNumber method, of class 
     * NumberTheoreticFunctionsCalculator. This test doesn't check whether the 
     * distribution is random enough, only that the numbers produced are indeed 
     * squarefree, that they don't have repeated prime factors. The test will 
     * keep going until either an assertion fails or a number is produced with 7 
     * for a least significant digit, whichever happens first.
     */
    @Test
    public void testRandomNegativeSquarefreeNumber() {
        System.out.println("randomSquarefreeNumber");
        /* Our test bound will be the square of the largest prime in our finite 
           list */
        int testBound = primesList.get(primesListLength - 1) * primesList.get(primesListLength - 1);
        int potentialRanSqFreeNum;
        String assertionMessage;
        do {
            potentialRanSqFreeNum = NumberTheoreticFunctionsCalculator.randomSquarefreeNumber(testBound);
            System.out.println("Function came up with this pseudorandom squarefree number: " + potentialRanSqFreeNum);
            // Check that the pseudorandom number is indeed squarefree
            double squaredPrime, ranNumDiv, flooredRanNumDiv, sqFrHolder;
            for (int i = 0; i < primesListLength; i++) {
                squaredPrime = primesList.get(i) * primesList.get(i);
                sqFrHolder = potentialRanSqFreeNum; // Ensure we're dividing a double by another double
                ranNumDiv = sqFrHolder / squaredPrime;
                flooredRanNumDiv = (int) Math.floor(ranNumDiv);
                assertionMessage = "Since " + potentialRanSqFreeNum + " is said to be squarefree, it should not be divisible by " + squaredPrime;
                assertNotEquals(assertionMessage, ranNumDiv, flooredRanNumDiv, TEST_DELTA);
            }
            /* And lastly, check that it is no more than the test bound but not 
               less than or equal to 0. */
            assertionMessage = "The number " + potentialRanSqFreeNum + " is not greater than the bound " + testBound;
            assertTrue(assertionMessage, potentialRanSqFreeNum < testBound);
            assertionMessage = "The number " + potentialRanSqFreeNum + " is greater than 0";
            assertTrue(assertionMessage, potentialRanSqFreeNum > 0);
        } while (potentialRanSqFreeNum % 10 != 7);
    }
    
}