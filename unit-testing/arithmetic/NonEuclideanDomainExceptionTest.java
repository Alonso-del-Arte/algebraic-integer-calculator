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
package arithmetic;

import algebraics.AlgebraicInteger;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the NonEuclideanDomainException class. The purpose of this test 
 * class is only to make sure the exception object works as it should. Testing 
 * whether this exception is thrown for the right reasons or not is the 
 * responsibility of other test classes. A lot of the tests in this test class 
 * use the famous domain <b>Z</b>[&radic;-5], which is not an Euclidean domain 
 * nor even a principal ideal domain.
 * @author Alonso del Arte
 */
public class NonEuclideanDomainExceptionTest {
    
    /**
     * The famous ring <b>Z</b>[&radic;-5], consisting of numbers of the form a 
     * + b&radic;&minus;5. It is not an Euclidean domain, since, for example, 
     * the Euclidean GCD algorithm fails for gcd(2, 1 + &radic;&minus;5).
     */
    public static final ImaginaryQuadraticRing RING_ZI5 = new ImaginaryQuadraticRing(-5);
    
    /**
     * The ring <i>O</i><sub><b>Q</b>(&radic;&minus;19)</sub>, famous for being a 
     * principal ideal domain but not Euclidean.
     */
    public static final ImaginaryQuadraticRing RING_OQI19 = new ImaginaryQuadraticRing(-19);
    
    /**
     * The ring <b>Z</b>[&radic;14], which is Euclidean but not norm-Euclidean. 
     * The existence of an Euclidean function for this domain has been proven 
     * mathematically, but I have not seen anyone say what that function 
     * actually is.
     */
    public static final RealQuadraticRing RING_Z14 = new RealQuadraticRing(14);
    
    /**
     * The ring <i>O</i><sub><b>Q</b>(&radic;69)</sub>, perhaps the most famous 
     * example of a Euclidean domain that is not norm-Euclidean. The function is 
     * well known, but it will not be implemented on the Java side of this 
     * project, though it might be implemented on the Scala side.
     */
    public static final RealQuadraticRing RING_OQ69 = new RealQuadraticRing(69);
    
    /**
     * Just the number &radic;&minus;5.
     */
    public static final ImaginaryQuadraticInteger ZI5_RAMIFIER = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
    
    private static NonEuclideanDomainException nonEuclExc6, nonEuclExc29, nonEuclExc143A, nonEuclExc143B, nonEuclExc700, nonEuclExc13, nonEuclExc39, nonEuclExc48;
    
    /**
     * Sets up five NonEuclideanDomainException objects. First they are 
     * initialized with Gaussian or Eisenstein units, then exceptions are tried 
     * to be caught to set them with numbers from domains that are not 
     * norm-Euclidean.
     */
    @BeforeClass
    public static void setUpClass() {
        nonEuclExc6 = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.IMAG_UNIT_I, NumberTheoreticFunctionsCalculator.IMAG_UNIT_NEG_I);
        nonEuclExc29 = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.IMAG_UNIT_NEG_I, NumberTheoreticFunctionsCalculator.IMAG_UNIT_I);
        nonEuclExc143A = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY, NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY.times(-1));
        nonEuclExc143B = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY, NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY.times(-1));
        nonEuclExc700 = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY, NumberTheoreticFunctionsCalculator.COMPLEX_CUBIC_ROOT_OF_UNITY.times(-1));
        nonEuclExc13 = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.GOLDEN_RATIO, NumberTheoreticFunctionsCalculator.GOLDEN_RATIO);
        nonEuclExc39 = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.GOLDEN_RATIO, NumberTheoreticFunctionsCalculator.GOLDEN_RATIO);
        nonEuclExc48 = new NonEuclideanDomainException("Initialization state, not the result of an actually thrown exception.", NumberTheoreticFunctionsCalculator.GOLDEN_RATIO, NumberTheoreticFunctionsCalculator.GOLDEN_RATIO);
        QuadraticInteger iqia = new ImaginaryQuadraticInteger(2, 0, RING_ZI5);
        QuadraticInteger iqib = new ImaginaryQuadraticInteger(1, 1, RING_ZI5);
        AlgebraicInteger gcd;
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc6 = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc6.getMessage() + "\"");
        iqia = new ImaginaryQuadraticInteger(29, 0, RING_ZI5);
        iqib = new ImaginaryQuadraticInteger(-7, 5, RING_ZI5);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc29 = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc29.getMessage() + "\"");
        iqia = new ImaginaryQuadraticInteger(0, 11, RING_ZI5);
        iqib = new ImaginaryQuadraticInteger(0, 13, RING_ZI5);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc143A = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc143A.getMessage() + "\"");
        iqia = new ImaginaryQuadraticInteger(0, 11, RING_ZI5);
        iqib = new ImaginaryQuadraticInteger(13, 0, RING_ZI5);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc143B = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc143B.getMessage() + "\"");
        iqia = new ImaginaryQuadraticInteger(10, 0, RING_OQI19);
        iqib = new ImaginaryQuadraticInteger(3, 1, RING_OQI19, 2);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc700 = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc700.getMessage() + "\"");
        iqia = new RealQuadraticInteger(2, 0, RING_Z14);
        iqib = new RealQuadraticInteger(1, 1, RING_Z14);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc13 = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc13.getMessage() + "\"");
        iqia = new RealQuadraticInteger(0, 39, RING_Z14);
        iqib = new RealQuadraticInteger(-40, 12, RING_Z14);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc39 = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc39.getMessage() + "\"");
        iqia = new RealQuadraticInteger(18, 2, RING_OQ69);
        iqib = new RealQuadraticInteger(23, 3, RING_OQ69, 2);
        try {
            gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(iqia, iqib);
            System.out.println("Found gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") = " + gcd + " by applying the Euclidean GCD algorithm.");
        } catch (NonEuclideanDomainException nede) {
            nonEuclExc48 = nede;
        }
        System.out.println("NonEuclideanDomainException for the example gcd(" + iqia.toASCIIString() + ", " + iqib.toASCIIString() + ") example has this message: \"" + nonEuclExc48.getMessage() + "\"");
    }
    
    /**
     * Test of getEuclideanGCDAttemptedNumbers method, of class 
     * NonEuclideanDomainException.
     */
    @Test
    public void testGetEuclideanGCDAttemptedNumbers() {
        System.out.println("getEuclideanGCDAttemptedNumbers");
        QuadraticInteger iqia = new ImaginaryQuadraticInteger(2, 0, RING_ZI5);
        QuadraticInteger iqib = new ImaginaryQuadraticInteger(1, 1, RING_ZI5);
        QuadraticInteger[] expResult = new QuadraticInteger[]{iqia, iqib};
        AlgebraicInteger[] result = nonEuclExc6.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new ImaginaryQuadraticInteger(29, 0, RING_ZI5);
        iqib = new ImaginaryQuadraticInteger(-7, 5, RING_ZI5);
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc29.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new ImaginaryQuadraticInteger(0, 11, RING_ZI5);
        iqib = new ImaginaryQuadraticInteger(0, 13, RING_ZI5);
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc143A.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new ImaginaryQuadraticInteger(0, 11, RING_ZI5);
        try {
            iqib = iqib.divides(ZI5_RAMIFIER);
        } catch (NotDivisibleException nde) {
            System.out.println("NotDivisibleException \"" + nde.getMessage() + "\" should not have occurred.");
            fail("Tests for NotDivisibleException may need review.");
        }
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc143B.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new ImaginaryQuadraticInteger(10, 0, RING_OQI19);
        iqib = new ImaginaryQuadraticInteger(3, 1, RING_OQI19, 2);
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc700.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new RealQuadraticInteger(2, 0, RING_Z14);
        iqib = new RealQuadraticInteger(1, 1, RING_Z14);
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc13.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new RealQuadraticInteger(0, 39, RING_Z14);
        iqib = new RealQuadraticInteger(-40, 12, RING_Z14);
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc39.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
        iqia = new RealQuadraticInteger(18, 2, RING_OQ69);
        iqib = new RealQuadraticInteger(23, 3, RING_OQ69, 2);
        expResult = new QuadraticInteger[]{iqia, iqib};
        result = nonEuclExc48.getEuclideanGCDAttemptedNumbers();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of tryEuclideanGCDAnyway method, of class 
     * NonEuclideanDomainException. The expectation is that when the Euclidean 
     * algorithm (using either the norm function or the absolute value of the 
     * norm) can complete successfully, the correct result will be obtained. But 
     * when the Euclidean algorithm fails, the only requirement on the result is 
     * that its rational real integer part be negative, so that the caller can 
     * be aware that the result is unreliable.
     */
    @Test
    public void testTryEuclideanGCDAnyway() {
        System.out.println("tryEuclideanGCDAnyway");
        AlgebraicInteger result = nonEuclExc6.tryEuclideanGCDAnyway();
        System.out.print("gcd(2, 1 + sqrt(-5)) = " + result.toASCIIString() + "?");
        String assertionMessage = "Real part of attempted Euclidean GCD result on gcd(2, 1 + \u221A(-5)) should be negative.";
        assertTrue(assertionMessage, ((QuadraticInteger) result).getRegPartMult() < 0);
        System.out.println("????");
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(3, 2, RING_ZI5);
        result = nonEuclExc29.tryEuclideanGCDAnyway();
        System.out.print("gcd(29, -7 + 5sqrt(-5)) = " + result.toASCIIString() + "?");
        assertEquals(expResult, result);
        System.out.println(" Confirmed.");
        expResult = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
        result = nonEuclExc143A.tryEuclideanGCDAnyway();
        System.out.print("gcd(11sqrt(-5), 13sqrt(-5)) = " + result.toASCIIString() + "?");
        assertEquals(expResult, result);
        System.out.println(" Confirmed.");
        result = nonEuclExc143B.tryEuclideanGCDAnyway();
        System.out.print("gcd(11sqrt(-5), 13) = " + result.toASCIIString() + "?");
        assertionMessage = "Real part of attempted Euclidean GCD result on gcd(10, 3/2 + \u221A(-7)/2) should be negative.";
        assertTrue(assertionMessage, ((QuadraticInteger) result).getRegPartMult() < 0);
        System.out.println("????");
        result = nonEuclExc700.tryEuclideanGCDAnyway();
        System.out.print("gcd(10, 3/2 + sqrt(-7)/2) = " + result.toASCIIString() + "?");
        assertionMessage = "Real part of attempted Euclidean GCD result on gcd(10, 3/2 + \u221A(-7)/2) should be negative.";
        assertTrue(assertionMessage, ((QuadraticInteger) result).getRegPartMult() < 0);
        System.out.println("????");
        result = nonEuclExc13.tryEuclideanGCDAnyway();
        System.out.print("gcd(2, 1 + sqrt(14)) = " + result.toASCIIString() + "?");
        assertionMessage = "Rational part of attempted Euclidean GCD result on gcd(2, 1 + \u221A(14)) should be negative.";
        assertTrue(assertionMessage, ((QuadraticInteger) result).getRegPartMult() < 0);
        System.out.println("????");
        expResult = new RealQuadraticInteger(10, -3, RING_Z14);
        result = nonEuclExc39.tryEuclideanGCDAnyway();
        System.out.print("gcd(39sqrt(14), -10 + 3sqrt(14)) = " + result.toASCIIString() + "?");
        assertEquals(expResult, result);
        System.out.println(" Confirmed.");
        result = nonEuclExc48.tryEuclideanGCDAnyway();
        System.out.print("gcd(18 + 2sqrt(69), 23 + 3sqrt(69)/2) = " + result.toASCIIString() + "?");
        assertionMessage = "Rational part of attempted Euclidean GCD result on gcd(18 + 2\u221A(69), 23/2 + 3\u221A(69)/2) should be negative.";
        assertTrue(assertionMessage, ((QuadraticInteger) result).getRegPartMult() < 0);
        System.out.println("????");
    }
    
    /**
     * Another test of tryEuclideanGCDAnyway, of class 
     * NonEuclideanDomainException. This test uses the following facts: if 
     * <i>d</i> is even and negative, then the norms of 2 and &radic;<i>d</i> 
     * are both even but the two numbers are actually coprime and the Euclidean 
     * algorithm is unable to make that determination (except of course for 
     * <b>Z</b>[&radic;-2]); if <i>d</i> = 1 mod 4 (and still negative), then 
     * gcd(2, 1 + &radic;<i>d</i>) = 2 and the Euclidean algorithm should be 
     * able to make that determination even in a non-Euclidean domain, but if 
     * <i>d</i> = 3 mod 4, the Euclidean algorithm will fail to find gcd(2, 1 + 
     * &radic;<i>d</i>) = 1 except in the case <i>d</i> = -1, in which it 
     * instead finds that gcd(2, 1 + <i>i</i>) = 1 + <i>i</i>.
     * <p>As for <i>d</i> a positive, squarefree multiple of 10, it is clear 
     * that the Euclidean algorithm will fail for gcd(2, &radic;<i>d</i>).</p>
     */
    @Test
    public void testTryEuclideanGCDInSeveralRings() {
        System.out.println("tryEuclideanGCDAnyway in several different rings");
        QuadraticRing currRing;
        QuadraticInteger two, ramifier, negNorm;
        ImaginaryQuadraticInteger onePlusRam;
        AlgebraicInteger gcd;
        // Three initializations so as to avoid error message on last report out
        onePlusRam = new ImaginaryQuadraticInteger(0, 0, RING_ZI5);
        negNorm = new ImaginaryQuadraticInteger(0, 0, RING_ZI5);
        gcd =  new ImaginaryQuadraticInteger(0, 0, RING_ZI5);
        String assertionMessage; // This one can be initialized right before use
        for (int iterDiscr = -1; iterDiscr > -200; iterDiscr--) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                currRing = new ImaginaryQuadraticRing(iterDiscr);
                two = new ImaginaryQuadraticInteger(2, 0, currRing);
                onePlusRam = new ImaginaryQuadraticInteger(1, 1, currRing);
                ramifier = onePlusRam.minus(1);
                try {
                    if (iterDiscr % 2 == 0) {
                        gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(two, ramifier);
                        assertEquals(ramifier, gcd);
                    } else {
                        gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(two, onePlusRam);
                        if (iterDiscr == -1 || iterDiscr == -3) {
                            assertEquals(onePlusRam, gcd);
                        } else {
                            assertEquals(two, gcd);
                        }
                    }
                } catch (NonEuclideanDomainException nede) {
                    gcd = nede.tryEuclideanGCDAnyway();
                    if (iterDiscr % 4 == -3) {
                        assertionMessage = "gcd(2, " + onePlusRam.toASCIIString() + ") should be 2.";
                        assertEquals(assertionMessage, two, gcd);
                    } else {
                        assertionMessage = "Real part of attempted Euclidean GCD should be negative.";
                        assertTrue(assertionMessage, ((QuadraticInteger) gcd).getRegPartMult() < 0);
                    }
                }
                /* Also gcd(-1 - sqrt(d), -norm(1 + sqrt(d))) should be 1 + 
                   sqrt(d). */
                negNorm = onePlusRam.times(onePlusRam.conjugate());
                negNorm = negNorm.times(-1);
                try {
                    gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(onePlusRam.times(-1), negNorm);
                } catch (NonEuclideanDomainException nede) {
                    gcd = nede.tryEuclideanGCDAnyway();
                }
                assertEquals(onePlusRam, gcd);
            }
        }
        System.out.println("gcd(" + onePlusRam.times(-1).toASCIIString() + ", " + negNorm.toASCIIString() + ") = " + gcd.toASCIIString() + "? Confirmed.");
        for (int iterDiscrR = 10; iterDiscrR < 500; iterDiscrR += 10) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscrR)) {
                currRing = new RealQuadraticRing(iterDiscrR);
                two = new RealQuadraticInteger(2, 0, currRing);
                ramifier = new RealQuadraticInteger(0, 1, currRing);
                try {
                    gcd = NumberTheoreticFunctionsCalculator.euclideanGCD(two, ramifier);
                } catch (NonEuclideanDomainException nede) {
                    gcd = nede.tryEuclideanGCDAnyway();
                }
                System.out.println("gcd(2, " + ramifier.toASCIIString() + ") = " + gcd.toASCIIString() + "?????");
                assertEquals(two.times(-1), gcd);
            }
        }
    }
    
}
