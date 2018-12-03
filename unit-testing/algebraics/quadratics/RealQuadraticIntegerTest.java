/*
 * Copyright (C) 2018 Alonso del Arte
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
package algebraics.quadratics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.NotDivisibleException;
import calculators.NumberTheoreticFunctionsCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the RealQuadraticInteger class, which defines objects that 
 * represent real quadratic integers.
 * @author Alonso del Arte
 */
public class RealQuadraticIntegerTest {
    
    /**
     * The ring <b>Z</b>[&radic;2], numbers of the form  <i>a</i> + 
     * <i>b</i>&radic;2. This is one of the rings in which RealQuadraticInteger 
     * will be tested.
     */
    private static final RealQuadraticRing RING_Z2 = new RealQuadraticRing(2);

    private static final RealQuadraticRing RING_ZPHI = new RealQuadraticRing(5);

    private static final RealQuadraticRing RING_OQ13 = new RealQuadraticRing(13);
    
    /**
     * A ring that will be randomly chosen during setUpClass().
     */
    private static RealQuadraticRing ringRandom;

    /**
     * A ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> specifically for 
     * testing {@link RealQuadraticInteger#toStringAlt()}, {@link 
     * RealQuadraticInteger#toASCIIStringAlt()}, {@link 
     * RealQuadraticInteger#toTeXStringAlt()} and {@link 
     * RealQuadraticInteger#toHTMLStringAlt}. This will be the same as 
     * ringRandom if the parameter <i>d</i> of that one is of the form 4<i>k</i> 
     * + 1. Otherwise, its parameter <i>d</i> will be the next higher squarefree 
     * number of the form 4<i>k</i> + 1.
     */
    private static RealQuadraticRing ringRandomForAltTesting;
    
    public static final int MAXIMUM_RING_D = 211;
    
    private static List<QuadraticInteger> testIntegers, testAdditiveInverses, testConjugates, testNorms;
    private static List<Integer> testNormsRegParts;
    
    /**
     * The value of 0 as a RealQuadraticInteger. Which RealQuadraticRing this 
     * zero is in will depend on the needs of the tests.
     */
    private static RealQuadraticInteger zeroRQI;
    
    private static int randomRegPart, randomSurdPart, randomRegForHalfInts, randomSurdForHalfInts, totalTestIntegers;
    
    private static final RealQuadraticInteger GOLDEN_RATIO = new RealQuadraticInteger(1, 1, RING_ZPHI, 2);
    
    @BeforeClass
    public static void setUpClass() {
        int randomDiscr = NumberTheoreticFunctionsCalculator.randomSquarefreeNumber(MAXIMUM_RING_D);
        if (randomDiscr == 2 || randomDiscr == 5 || randomDiscr == 13) {
            randomDiscr++; // This is just in case we get 2, 5 or 13.
        }
        boolean ringRandomd1mod4 = (randomDiscr % 4 == 1);
        ringRandom = new RealQuadraticRing(randomDiscr);
        if (ringRandom.hasHalfIntegers()) {
            ringRandomForAltTesting = ringRandom;
        } else {
            int nextD = ringRandom.getRadicand();
            do {
                nextD++;
            } while (!(NumberTheoreticFunctionsCalculator.isSquareFree(nextD) && (nextD % 4 == 1)));
            ringRandomForAltTesting = new RealQuadraticRing(nextD);
            System.out.println(ringRandomForAltTesting.toASCIIString() + " has been chosen for testing toStringAlt(), toASCIIStringAlt, toTeXStringAlt and toHTMLStringAlt.");
        }
        System.out.println(ringRandom.toASCIIString() + " has been randomly chosen for testing purposes.");
        int maxAB = (int) Math.floor(Math.sqrt(Integer.MAX_VALUE/(32 * (randomDiscr + 1))));
        System.out.println("Maximum for regular and surd parts is " + maxAB);
        Random ranNumGen = new Random();
        randomRegPart = ranNumGen.nextInt(2 * maxAB) - maxAB;
        randomSurdPart = ranNumGen.nextInt(2 * maxAB) - maxAB;
        if (randomSurdPart == 0) {
            randomSurdPart = 1; // We want to make sure none of these random imaginary quadratic integers are purely real.
        }
        randomRegForHalfInts = 2 * randomRegPart + 1;
        randomSurdForHalfInts = 2 * randomSurdPart + 1;
        zeroRQI = new RealQuadraticInteger(0, 0, RING_Z2);
        testIntegers = new ArrayList<>();
        testAdditiveInverses = new ArrayList<>();
        testConjugates = new ArrayList<>();
        testNorms = new ArrayList<>();
        testNormsRegParts = new ArrayList<>();
        int currNorm;
        RealQuadraticInteger currRQI = new RealQuadraticInteger(randomRegPart, randomSurdPart, RING_Z2);
        testIntegers.add(currRQI);
        currRQI = new RealQuadraticInteger(-randomRegPart, -randomSurdPart, RING_Z2);
        testAdditiveInverses.add(currRQI);
        currRQI = new RealQuadraticInteger(randomRegPart, -randomSurdPart, RING_Z2);
        testConjugates.add(currRQI);
        currNorm = randomRegPart * randomRegPart - 2 * randomSurdPart * randomSurdPart;
        currRQI = new RealQuadraticInteger(currNorm, 0, RING_Z2);
        testNorms.add(currRQI);
        testNormsRegParts.add(currNorm);
        currRQI = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, RING_ZPHI, 2);
        testIntegers.add(currRQI);
        currRQI = new RealQuadraticInteger(-randomRegForHalfInts, -randomSurdForHalfInts, RING_ZPHI, 2);
        testAdditiveInverses.add(currRQI);
        currRQI = new RealQuadraticInteger(randomRegForHalfInts, -randomSurdForHalfInts, RING_ZPHI, 2);
        testConjugates.add(currRQI);
        currNorm = (randomRegForHalfInts * randomRegForHalfInts - 5 * randomSurdForHalfInts * randomSurdForHalfInts)/4;
        currRQI = new RealQuadraticInteger(currNorm, 0, RING_ZPHI);
        testNorms.add(currRQI);
        testNormsRegParts.add(currNorm);
        currRQI = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, RING_OQ13, 2);
        testIntegers.add(currRQI);
        currRQI = new RealQuadraticInteger(-randomRegForHalfInts, -randomSurdForHalfInts, RING_OQ13, 2);
        testAdditiveInverses.add(currRQI);
        currRQI = new RealQuadraticInteger(randomRegForHalfInts, -randomSurdForHalfInts, RING_OQ13, 2);
        testConjugates.add(currRQI);
        currNorm = (randomRegForHalfInts * randomRegForHalfInts - 13 * randomSurdForHalfInts * randomSurdForHalfInts)/4;
        currRQI = new RealQuadraticInteger(currNorm, 0, RING_OQ13, 1);
        testNorms.add(currRQI);
        testNormsRegParts.add(currNorm);
        if (ringRandomd1mod4) {
            currRQI = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, ringRandom, 2);
            testIntegers.add(currRQI);
            currRQI = new RealQuadraticInteger(-randomRegForHalfInts, -randomSurdForHalfInts, ringRandom, 2);
            testAdditiveInverses.add(currRQI);
            currRQI = new RealQuadraticInteger(randomRegForHalfInts, -randomSurdForHalfInts, ringRandom, 2);
            testConjugates.add(currRQI);
            currNorm = (randomRegForHalfInts * randomRegForHalfInts - randomDiscr * randomSurdForHalfInts * randomSurdForHalfInts)/4;
            currRQI = new RealQuadraticInteger(currNorm, 0, ringRandom);
            testNorms.add(currRQI);
            testNormsRegParts.add(currNorm);
        } else {
            currRQI = new RealQuadraticInteger(randomRegPart, randomSurdPart, ringRandom);
            testIntegers.add(currRQI);
            currRQI = new RealQuadraticInteger(-randomRegPart, -randomSurdPart, ringRandom);
            testAdditiveInverses.add(currRQI);
            currRQI = new RealQuadraticInteger(randomRegPart, -randomSurdPart, ringRandom);
            testConjugates.add(currRQI);
            currNorm = randomRegPart * randomRegPart - randomDiscr * randomSurdPart * randomSurdPart;
            currRQI = new RealQuadraticInteger(currNorm, 0, ringRandom);
            testNorms.add(currRQI);
            testNormsRegParts.add(currNorm);
        }
        totalTestIntegers = testIntegers.size();
    }
    
    /**
     * Test of algebraicDegree method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        int expResult = 2; // Quadratic integers with nonzero "surd" part should have algebraic degree 2
        int result;
        for (int i = 0; i < totalTestIntegers; i++) {
            result = testIntegers.get(i).algebraicDegree();
            assertEquals(expResult, result);
            result = testAdditiveInverses.get(i).algebraicDegree();
            assertEquals(expResult, result);
            result = testConjugates.get(i).algebraicDegree();
            assertEquals(expResult, result);
        }
        expResult = 1; // Purely real and rational nonzero integers should have algebraic degree 1
        for (QuadraticInteger normIQI : testNorms) {
            result = normIQI.algebraicDegree();
            assertEquals(expResult, result);
        }
        /* And last but not least, 0 should have algebraic degree 0 regardless 
           of which ring it comes from. */
        expResult = 0;
        result = zeroRQI.algebraicDegree();
        assertEquals(expResult, result);
        zeroRQI = new RealQuadraticInteger(0, 0, RING_ZPHI);
        result = zeroRQI.algebraicDegree();
        assertEquals(expResult, result);
    }

    /**
     * Test of trace method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
        long expResult = 2 * randomRegPart;
        long result;
        for (int i = 0; i < totalTestIntegers; i++) {
            result = testIntegers.get(i).trace();
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                assertEquals(randomRegForHalfInts, result);
            } else {
                assertEquals(expResult, result);
            }
        }
    }

    /**
     * Test of norm method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        long expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = (randomRegForHalfInts * randomRegForHalfInts - testIntegers.get(i).getRing().getRadicand() * randomSurdForHalfInts * randomSurdForHalfInts)/4;
            } else {
                expResult = randomRegPart * randomRegPart - testIntegers.get(i).getRing().getRadicand() * randomSurdPart * randomSurdPart;
            }
            result = testIntegers.get(i).norm();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of minPolynomial method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testMinPolynomial() {
        System.out.println("minPolynomial");
        long[] expResult = {0, 0, 1};
        long[] result;
        RealQuadraticInteger baseSurdDist, rationalInt;
        for (int i = 0; i < totalTestIntegers; i++) {
            System.out.println("Minimal polynomial for " + testIntegers.get(i).toASCIIString() + " is said to be " + testIntegers.get(i).minPolynomialString());
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult[1] = -randomRegForHalfInts;
                expResult[0] = (randomRegForHalfInts * randomRegForHalfInts - randomSurdForHalfInts * randomSurdForHalfInts * testIntegers.get(i).getRing().getRadicand())/4;
            } else {
                expResult[1] = (-2) * randomRegPart;
                expResult[0] = randomRegPart * randomRegPart - randomSurdPart * randomSurdPart * testIntegers.get(i).getRing().getRadicand();
            }
            result = testIntegers.get(i).minPolynomial();
            assertArrayEquals(expResult, result);
            /* Now to test the mimimal polymomial of the integer sqrt(d) */
            expResult[1] = 0;
            expResult[0] = -testIntegers.get(i).getRing().getRadicand();
            baseSurdDist = new RealQuadraticInteger(0, 1, testIntegers.get(i).getRing());
            System.out.println("Minimal polynomial of " + baseSurdDist.toASCIIString() + " is said to be " + baseSurdDist.minPolynomialString());
            result = baseSurdDist.minPolynomial();
            assertArrayEquals(expResult, result);
        }
        // Next, some rational integers
        expResult[2] = 0;
        expResult[1] = 1;
        for (int i = 1; i < 10; i++) {
            expResult[0] = -i;
            rationalInt = new RealQuadraticInteger(i, 0, ringRandom);
            result = rationalInt.minPolynomial();
            assertArrayEquals(expResult, result);
        }
        // And last but not least, 0
        expResult[0] = 0;
        result = zeroRQI.minPolynomial();
        assertArrayEquals(expResult, result);
        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
        result = zeroRQI.minPolynomial();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of minPolynomialString method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        String expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            expResult = "x^2";
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                if (randomRegForHalfInts < 0) {
                    expResult = expResult + "+" + ((-1) * randomRegForHalfInts);
                } else {
                    expResult = expResult + "-" + randomRegForHalfInts;
                }
                expResult = expResult + "x+" + ((randomRegForHalfInts * randomRegForHalfInts - randomSurdForHalfInts * randomSurdForHalfInts * testIntegers.get(i).getRing().getRadicand())/4);
            } else {
                if (randomRegPart < 0) {
                    expResult = expResult + "+" + ((-2) * randomRegPart);
                } else {
                    expResult = expResult + "-" + (2 * randomRegPart);
                }
                expResult = expResult + "x+" + (randomRegPart * randomRegPart - randomSurdPart * randomSurdPart * testIntegers.get(i).getRing().getRadicand());
            }
            expResult = expResult.replace("+1x", "+x");
            expResult = expResult.replace("-1x", "-x");
            expResult = expResult.replace("+0x", "");
            expResult = expResult.replace("-0x", "");
            expResult = expResult.replace("+-", "-");
            result = testIntegers.get(i).minPolynomialString().replace(" ", ""); // Strip out spaces
            assertEquals(expResult, result);
        }
        // Now to test the polynomial strings of a few purely real integers
        RealQuadraticInteger degreeOneInt;
        for (int j = 1; j < 8; j++) {
            degreeOneInt = new RealQuadraticInteger(j, 0, ringRandom);
            expResult = "x-" + j;
            result = degreeOneInt.minPolynomialString().replace(" ", "");
            assertEquals(expResult, result);
            degreeOneInt = new RealQuadraticInteger(-j, 0, ringRandom);
            expResult = "x+" + j;
            result = degreeOneInt.minPolynomialString().replace(" ", "");
            assertEquals(expResult, result);
        }
        /* I'm not terribly concerned about this one, so it's here more for the 
           sake of completeness than anything else. Feel free to delete if 
           inconvenient. */
        assertEquals("x", zeroRQI.minPolynomialString());
    }

    /**
     * Test of conjugate method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
    @Test
    public void testConjugate() {
        System.out.println("conjugate");
        QuadraticInteger expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            try {
                expResult = testNorms.get(i).divides(testIntegers.get(i));
                result = testIntegers.get(i).conjugate();
                System.out.println("Conjugate of " + testIntegers.get(i).toASCIIString() + " is " + result.toASCIIString());
                assertEquals(expResult, result);
                assertEquals(testConjugates.get(i), result);
                result = result.conjugate();
                assertEquals(result, testIntegers.get(i));
            } catch (AlgebraicDegreeOverflowException adoe) {
                fail("AlgebraicDegreeOverflowException should not have occurred during test of conjugate().\n" + adoe.getMessage() + "\nThere may be a mistake in the setup of the test.");
            } catch (NotDivisibleException nde) {
                fail("NotDivisibleException should not have occurred during test of conjugate().\n" + nde.getMessage() + "\nThere may be a mistake in the setup of the test.");
            }
        }
    }

    /**
     * Test of abs method, of class RealQuadraticInteger.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        double expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = Math.abs(testIntegers.get(i).getRing().getRadSqrt() * randomSurdForHalfInts + randomRegForHalfInts);
                expResult /= 2;
            } else {
                expResult = Math.abs(testIntegers.get(i).getRing().getRadSqrt() * randomSurdPart + randomRegPart);
            }
            result = testIntegers.get(i).abs();
            System.out.println("|" + testIntegers.get(i).toASCIIString() + "| = " + result);
            assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        }
    }

    /**
     * Test of getRealPartNumeric method, of class RealQuadraticInteger.
     */
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartMultNumeric");
        double expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = testIntegers.get(i).getRing().getRadSqrt() * randomSurdForHalfInts + randomRegForHalfInts;
                expResult /= 2;
            } else {
                expResult = testIntegers.get(i).getRing().getRadSqrt() * randomSurdPart + randomRegPart;
            }
            result = testIntegers.get(i).getRealPartNumeric();
            System.out.println(testIntegers.get(i).toASCIIString() + " = " + result);
            assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        }
    }

    /**
     * Test of getImagPartNumeric method, of class RealQuadraticInteger.
     */
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartwRadMultNumeric");
        double expResult = 0.0;
        double result;
        for (int i = 0; i < totalTestIntegers; i++) {
            result = testIntegers.get(i).getImagPartNumeric();
            assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        }
    }
    
    /**
     * Test of getCausingRing method, of class RealQuadraticInteger, inherited from 
 QuadraticInteger.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        assertEquals(RING_Z2, testIntegers.get(0).getRing());
        assertEquals(RING_ZPHI, testIntegers.get(1).getRing());
        assertEquals(RING_OQ13, testIntegers.get(2).getRing());
        assertEquals(ringRandom, testIntegers.get(3).getRing());
    }
    /**
     * Test of getRegPartMult method, of class ImaginaryQuadraticInteger.
     */
    @Test
    public void testGetRegPartMult() {
        System.out.println("getRealPartMult");
        int expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = randomRegForHalfInts;
            } else {
                expResult = randomRegPart;
            }
            result = testIntegers.get(i).getRegPartMult();
            assertEquals(expResult, result);
        }
    }
    
    /**
     * Test of getSurdPartMult method, of class ImaginaryQuadraticInteger.
     */
    @Test
    public void testGetSurdPartMult() {
        System.out.println("getImagPartMult");
        int expResult, result;
        for (int i = 0; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = randomSurdForHalfInts;
            } else {
                expResult = randomSurdPart;
            }
            result = testIntegers.get(i).getSurdPartMult();
            assertEquals(expResult, result);
        }
    }
    
    /**
     * Test of getDenominator method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        for (int i = 0; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                assertEquals(2, testIntegers.get(i).getDenominator());
            } else {
                assertEquals(1, testIntegers.get(i).getDenominator());
            }
        }
    }

    /**
     * Test of toString method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger. For functions that return Strings, spaces are desirable 
     * but not required. Therefore the tests should strip out spaces before 
     * asserting equality.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult, result;
        for (int i = 1; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = randomRegForHalfInts + "/2+" + randomSurdForHalfInts + "\u221A(" + testIntegers.get(i).getRing().getRadicand() + ")/2";
            } else {
                if (randomRegPart == 0) {
                    expResult = randomSurdPart + "\u221A(" + testIntegers.get(i).getRing().getRadicand() + ")";
                } else {
                    expResult = randomRegPart + "+" + randomSurdPart + "\u221A(" + testIntegers.get(i).getRing().getRadicand() + ")";
                }
            }
            expResult = expResult.replace("+-", "-");
            expResult = expResult.replace("+1\u221A", "+\u221A");
            expResult = expResult.replace("-1\u221A", "-\u221A");
            result = testIntegers.get(i).toString().replace(" ", "");
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of toStringAlt method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger. For functions that return Strings, spaces are desirable 
     * but not required. Therefore the tests should strip out spaces before 
     * asserting equality. If the test of toString fails, the result of this 
     * test is irrelevant.
     */
    @Test
    public void testToStringAlt() {
        System.out.println("toStringAlt");
        String expResult, result;
        int nonThetaPart;
        for (int i = 1; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                nonThetaPart = (randomRegForHalfInts - randomSurdForHalfInts)/2;
                if (nonThetaPart == 0) {
                    expResult = randomSurdForHalfInts + "\u03B8";
                } else {
                    expResult = nonThetaPart + "+" + randomSurdForHalfInts + "\u03B8";
                }
                if (testIntegers.get(i).getRing().getRadicand() == 5) {
                    expResult = expResult.replace("\u03B8", "\u03C6");
                }
                expResult = expResult.replace("+-", "-");
            } else {
                expResult = testIntegers.get(i).toString().replace(" ", "");
            }
            result = testIntegers.get(i).toStringAlt().replace(" ", "");
            assertEquals(expResult, result);
        }
        // Lastly the special case of the golden ratio
        expResult = "\u03C6";
        result = GOLDEN_RATIO.toStringAlt();
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult, result;
        for (int i = 1; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult = randomRegForHalfInts + "/2+" + randomSurdForHalfInts + "sqrt(" + testIntegers.get(i).getRing().getRadicand() + ")/2";
            } else {
                if (randomRegPart == 0) {
                    expResult = randomSurdPart + "sqrt(" + testIntegers.get(i).getRing().getRadicand() + ")";
                } else {
                    expResult = randomRegPart + "+" + randomSurdPart + "sqrt(" + testIntegers.get(i).getRing().getRadicand() + ")";
                }
            }
            expResult = expResult.replace("+-", "-");
            expResult = expResult.replace("+1sqrt", "+sqrt");
            expResult = expResult.replace("-1sqrt", "-sqrt");
            result = testIntegers.get(i).toASCIIString().replace(" ", "");
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of toASCIIStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToASCIIStringAlt() {
        System.out.println("toASCIIStringAlt");
        String expResult, result;
        int nonThetaPart;
        for (int i = 1; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                nonThetaPart = (randomRegForHalfInts - randomSurdForHalfInts)/2;
                if (nonThetaPart == 0) {
                    expResult = randomSurdForHalfInts + "theta";
                } else {
                    expResult = nonThetaPart + "+" + randomSurdForHalfInts + "theta";
                }
                if (testIntegers.get(i).getRing().getRadicand() == 5) {
                    expResult = expResult.replace("theta", "phi");
                }
                expResult = expResult.replace("+-", "-");
            } else {
                expResult = testIntegers.get(i).toASCIIString();
            }
            result = testIntegers.get(i).toASCIIStringAlt().replace(" ", "");
            assertEquals(expResult, result);
        }
        // Lastly the special case of the golden ratio
        expResult = "phi";
        result = GOLDEN_RATIO.toASCIIStringAlt();
        assertEquals(expResult, result);    }

    /**
     * Test of toTeXString method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of toTeXStringSingleDenom method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testToTeXStringSingleDenom() {
        System.out.println("toTeXStringSingleDenom");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of toTeXStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToTeXStringAlt() {
        System.out.println("toTeXStringAlt");
        String expResult, result;
        int nonThetaPart;
        for (int i = 1; i < totalTestIntegers; i++) {
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                nonThetaPart = (randomRegForHalfInts - randomSurdForHalfInts)/2;
                if (nonThetaPart == 0) {
                    expResult = randomSurdForHalfInts + "\\theta";
                } else {
                    expResult = nonThetaPart + "+" + randomSurdForHalfInts + "\\theta";
                }
                if (testIntegers.get(i).getRing().getRadicand() == 5) {
                    expResult = expResult.replace("\\theta", "\\phi");
                }
                expResult = expResult.replace("+-", "-");
            } else {
                expResult = testIntegers.get(i).toTeXString().replace(" ", "");
            }
            result = testIntegers.get(i).toTeXStringAlt().replace(" ", "");
            assertEquals(expResult, result);
        }
        // Lastly the special case of the golden ratio
        expResult = "\\phi";
        result = GOLDEN_RATIO.toTeXStringAlt();
        assertEquals(expResult, result);    }

    /**
     * Test of toHTMLString method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of toHTMLStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToHTMLStringAlt() {
        System.out.println("toHTMLStringAlt");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of hashCode method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        RealQuadraticInteger temporaryHold;
        int testHash, tempHash;
        int prevHash = 0;
        for (int i = 0; i < totalTestIntegers; i++) {
            testHash = testIntegers.get(i).hashCode();
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                temporaryHold = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, testIntegers.get(i).getRing(), 2);
            } else {
                temporaryHold = new RealQuadraticInteger(randomRegPart, randomSurdPart, testIntegers.get(i).getRing());
            }
            tempHash = temporaryHold.hashCode();
            System.out.println(testIntegers.get(i).toASCIIString() + " hashed to " + testHash);
            System.out.println(temporaryHold.toASCIIString() + " hashed to " + tempHash);
            assertEquals(testHash, tempHash);
            assertFalse(testHash == prevHash);
            prevHash = testHash;
        }
        /* Now to test purely real integers register as equal regardless of what 
           real quadratic ring they might be from */
        RealQuadraticInteger altZeroRQI = new RealQuadraticInteger(0, 0, RING_Z2);
        assertEquals(altZeroRQI, zeroRQI);
        for (int j = 0; j < totalTestIntegers - 1; j++) {
            temporaryHold = new RealQuadraticInteger(testNormsRegParts.get(j), 0, testNorms.get(totalTestIntegers - 1).getRing());
            tempHash = temporaryHold.hashCode();
            testHash = testNorms.get(j).hashCode();
            System.out.println(temporaryHold.toString() + " from " + temporaryHold.getRing().toASCIIString() + " hashed as " + tempHash);
            System.out.println(testNorms.get(j).toString() + " from " + testNorms.get(j).getRing().toASCIIString() + " hashed as " + testHash);
            assertEquals(tempHash, testHash);
        }
    }

    /**
     * Test of equals method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        RealQuadraticInteger temporaryHold, transitiveHold;
        for (int i = 0; i < totalTestIntegers; i++) {
            assertTrue(testIntegers.get(i).equals(testIntegers.get(i))); // Reflexive test
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                temporaryHold = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, testIntegers.get(i).getRing(), 2);
                transitiveHold = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, testIntegers.get(i).getRing(), 2);
            } else {
                temporaryHold = new RealQuadraticInteger(randomRegPart, randomSurdPart, testIntegers.get(i).getRing());
                transitiveHold = new RealQuadraticInteger(randomRegPart, randomSurdPart, testIntegers.get(i).getRing());
            }
            assertTrue(testIntegers.get(i).equals(testIntegers.get(i))); // First consistency test
            assertEquals(testIntegers.get(i), temporaryHold);
            assertEquals(temporaryHold, testIntegers.get(i)); // Symmetric test
            assertEquals(temporaryHold, transitiveHold);
            assertEquals(transitiveHold, testIntegers.get(i)); // Transitive test
            assertTrue(testIntegers.get(i).equals(testIntegers.get(i))); // Second consistency test
            // assertFalse(testIntegers.get(i).equals(null)); // Null test is apparently unnecessary
        }
        RealQuadraticInteger kindaDiffZero;
        for (int j = 0; j < totalTestIntegers - 1; j++) {
            assertFalse(testIntegers.get(j).equals(testIntegers.get(j + 1)));
            assertFalse(testIntegers.get(j + 1).equals(testIntegers.get(j))); // Symmetric test for not equals
            temporaryHold = new RealQuadraticInteger(testNormsRegParts.get(j), 0, testNorms.get(j + 1).getRing());
            assertEquals(testNorms.get(j), temporaryHold);
            kindaDiffZero = new RealQuadraticInteger(0, 0, testIntegers.get(j + 1).getRing());
            assertEquals(zeroRQI, kindaDiffZero); // Making sure purely real integers can register as equal
        }
    }

    /**
     * Test of parseQuadraticInteger, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger. Whatever is output by toString, toStringAlt, 
     * toASCIIString, toASCIIStringAlt, toTeXString, toTeXStringSingleDenom, 
     * toTeXStringAlt, toHTMLString or toHTMLStringAlt should be parseable by 
     * parseQuadraticInteger. With the following caveats: 
     * NEED TO REVISE CAVEAT EXAMPLES
     * &omega; should always be understood to mean -1/2 + sqrt(-3)/2 and &phi; 
     * should be understood to mean 1/2 + sqrt(5)/2, while &theta; means 1/2 
     * + sqrt(d)/2 with d = 1 mod 4, but d may be ambiguous.
     */
    @Ignore
    @Test
    public void testParseQuadraticInteger() {
        System.out.println("parseQuadraticInteger");
        fail("Haven't written the test yet.");
//        String numberString;
//        QuadraticInteger numberIQI;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            numberString = testIntegers.get(i).toString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getCausingRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toASCIIString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toASCIIStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getCausingRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toTeXString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toTeXStringSingleDenom();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toTeXStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getCausingRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toHTMLString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toHTMLStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getCausingRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//        }
//        // A few special cases to check
//        numberString = "i";
//        numberIQI = new ImaginaryQuadraticInteger(0, 1, RING_GAUSSIAN);
//        assertEquals(numberIQI, QuadraticInteger.parseQuadraticInteger(numberString));
//        numberString = "0 + i";
//        assertEquals(numberIQI, QuadraticInteger.parseQuadraticInteger(numberString));
//        numberString = "-sqrt(-2)";
//        numberIQI = new ImaginaryQuadraticInteger(0, -1, RING_ZI2);
//        assertEquals(numberIQI, QuadraticInteger.parseQuadraticInteger(numberString));
//        numberString = "0 - sqrt(-2)";
//        assertEquals(numberIQI, QuadraticInteger.parseQuadraticInteger(numberString));
//        /* Lastly, to check the appropriate exception is thrown for non-numeric 
//           strings */
//        numberString = "one plus imaginary unit";
//        try {
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            fail("Attempting to call parseImaginaryQuadraticInteger on \"" + numberString + "\" should have triggered NumberFormatException, not given " + numberIQI.toASCIIString() + ".");
//        } catch (NumberFormatException nfe) {
//            System.out.println("Attempting to call parseImaginaryQuadraticInteger on \"" + numberString + "\" correctly triggered NumberFormatException: " + nfe.getMessage());
//        }
    }
    
    /**
     * Test of optional behaviors of parseImaginaryQuadraticInteger, of class 
     * RealQuadraticInteger, inherited from QuadraticInteger. This includes 
     * recognizing "j" as an alternative notation for &radic;&minus;1. If the 
     * optional behaviors are required, change the print statements under catch 
     * {@link NumberFormatException} to fails.
     */
    @Ignore
    @Test
    public void testParseQuadraticIntegerOptions() {
        System.out.println("parseQuadraticInteger, optional behaviors");
        fail("Haven't written the test yet.");
//        String numberString = "j";
//        QuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 1, RING_GAUSSIAN);
//        QuadraticInteger result;
//        try {
//            result = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(expResult, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("Testing the option to use 'j' instead of 'i' triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//            System.out.println("This is an acceptable but not preferable response for dealing with \"" + numberString + "\".");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have caused " + e.getClass().getName() + "\"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "j + 1";
//        expResult = expResult.plus(1);
//        try {
//            result = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(expResult, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("Testing an optional behavior triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//            System.out.println("This is an acceptable but not preferable response for dealing with \"" + numberString + "\".");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have caused " + e.getClass().getName() + "\"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "\u221A-7 - 1";
//        expResult = new ImaginaryQuadraticInteger(1, 1, RING_OQI7);
//        try {
//            result = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(expResult, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("Testing an optional behavior triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//            System.out.println("This is an acceptable but not preferable response for dealing with \"" + numberString + "\".");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have caused " + e.getClass().getName() + "\"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "";
//        try {
//            result = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(zeroIQI, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("Testing the option to have an empty String stand for 0 triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//            System.out.println("This is an acceptable response for dealing with an empty String.");
//        } catch (Exception e) {
//            String failMessage = "Empty String should not have caused " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        // Try the empty String again but this time specifying Z[sqrt(-2)]
//        try {
//            result = QuadraticInteger.parseQuadraticInteger(RING_ZI2, numberString);
//            assertEquals(zeroIQI, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("Testing the option to have an empty String stand for 0 triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//            System.out.println("This is an acceptable response for dealing with an empty String even if a ring is specified.");
//        } catch (Exception e) {
//            String failMessage = "Empty String should not have caused " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
    }
        
    /**
     * Test of plus method, of class RealQuadraticInteger.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testAddendA, testAddendB;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                currRing = new RealQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = -3; w < 48; w += 2) {
                        testAddendA = new RealQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = -3; y < 48; y += 2) {
                                testAddendB = new RealQuadraticInteger(x, y, currRing, currDenom);
                                expResult = new RealQuadraticInteger(v + x, w + y, currRing, currDenom);
                                failMessage = "Adding two integers from the same ring should not have triggered AlgebraicDegreeOverflowException \"";
                                try {
                                    result = testAddendA.plus(testAddendB);
                                    assertEquals(expResult, result);
                                } catch (AlgebraicDegreeOverflowException adoe) {
                                    failMessage = failMessage + adoe.getMessage() + "\"";
                                    fail(failMessage);
                                }
                            }
                            // Now to test plus(int)
                            if (currRing.hasHalfIntegers()) {
                                expResult = new RealQuadraticInteger(v + 2 * x, w, currRing, 2);
                            } else {
                                expResult = new RealQuadraticInteger(v + x, w, currRing);
                            }
                            result = testAddendA.plus(x);
                            assertEquals(expResult, result);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < totalTestIntegers; i++) {
            // Testing that adding additive inverses give 0 each time
            failMessage = "Adding test integer to its additive inverse should not have triggered AlgebraicDegreeOverflowException \"";
            try {
                result = testIntegers.get(i).plus(testAdditiveInverses.get(i));
                assertEquals(zeroRQI, result);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = failMessage + adoe.getMessage() + "\"";
                fail(failMessage);
            }
            // Now testing that adding 0 does not change the number
            result = testIntegers.get(i).plus(0);
            assertEquals(testIntegers.get(i), result);
        }
        /* And now to test that adding algebraic integers from two different 
           quadratic integer rings triggers AlgebraicDegreeOverflowException */
        for (int j = 0; j < totalTestIntegers - 1; j++) {
            try {
                result = testIntegers.get(j).plus(testIntegers.get(j + 1));
                failMessage = "Adding " + testIntegers.get(j).toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
                fail(failMessage);
            } catch (AlgebraicDegreeOverflowException adoe) {
                System.out.println("Adding " + testIntegers.get(j).toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
            }
            /* However, if one of them is purely real, there should be a result, 
               even if it takes us to a different ring */
            failMessage = "Adding " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " should not have caused";
            try {
                result = testNorms.get(j).plus(testIntegers.get(j + 1));
                System.out.println("Adding " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " gives result " + result.toASCIIString());
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = failMessage + "AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (Exception e) {
                failMessage = failMessage + "Exception \"" + e.getMessage() + "\"";
                fail(failMessage);
            }
            failMessage = "Adding " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " should not have caused";
            try {
                result = testIntegers.get(j).plus(testNorms.get(j + 1));
                System.out.println("Adding " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " gives result " + result.toASCIIString());
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = failMessage + "AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (Exception e) {
                failMessage = failMessage + "Exception \"" + e.getMessage() + "\"";
                fail(failMessage);
            }
        }
    }
    
    /**
     * Test of minus method, of class RealQuadraticInteger.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testMinuend, testSubtrahend;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                currRing = new RealQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = -3; w < 48; w += 2) {
                        testMinuend = new RealQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = -3; y < 48; y += 2) {
                                testSubtrahend = new RealQuadraticInteger(x, y, currRing, currDenom);
                                expResult = new RealQuadraticInteger(v - x, w - y, currRing, currDenom);
                                try {
                                    result = testMinuend.minus(testSubtrahend);
                                    assertEquals(expResult, result);
                                } catch (AlgebraicDegreeOverflowException adoe) {
                                    failMessage = "Subtracting two integers from the same ring should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                                    fail(failMessage);
                                }
                            }
                            // Now to test minus(int)
                            if (currRing.hasHalfIntegers()) {
                                expResult = new RealQuadraticInteger(v - 2 * x, w, currRing, 2);
                            } else {
                                expResult = new RealQuadraticInteger(v - x, w, currRing);
                            }
                            result = testMinuend.minus(x);
                            assertEquals(expResult, result);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < totalTestIntegers; i++) {
            // Testing that subtracting itself gives 0 each time
            expResult = new RealQuadraticInteger(0, 0, testIntegers.get(i).getRing());
            try {
                result = testIntegers.get(i).minus(testIntegers.get(i));
                assertEquals(expResult, result);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "Subtracting test integer from itself should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage();
                fail(failMessage);
            }
            // Now testing that subtracting 0 does not change the number
            result = testIntegers.get(i).minus(0);
            assertEquals(testIntegers.get(i), result);
        }
        /* And now to test that subtracting algebraic integers from two 
           different quadratic integer rings triggers 
           AlgebraicDegreeOverflowException */
        for (int j = 0; j < totalTestIntegers - 1; j++) {
            try {
                result = testIntegers.get(j).minus(testIntegers.get(j + 1));
                failMessage = "Subtracting " + testIntegers.get(j + 1).toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
                fail(failMessage);
            } catch (AlgebraicDegreeOverflowException adoe) {
                System.out.println("Subtracting " + testIntegers.get(j + 1).toASCIIString() + " from " + testIntegers.get(j).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
            }
            /* However, if one of them is purely real, there should be some kind 
               of result */
            failMessage = "Subtracting " + testIntegers.get(j + 1).toASCIIString() + " from " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " should not have triggered";
            try {
                result = testNorms.get(j).minus(testIntegers.get(j + 1));
                System.out.println(testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " minus " + testIntegers.get(j + 1).toASCIIString() + " is " + result.toASCIIString());
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (Exception e) {
                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
                fail(failMessage);
            }
        }
    }
    
    /**
     * Test of times method, of class RealQuadraticInteger.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testMultiplicandA, testMultiplicandB;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                currRing = new RealQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = -3; w < 48; w += 2) {
                        testMultiplicandA = new RealQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = -3; y < 48; y += 2) {
                                testMultiplicandB = new RealQuadraticInteger(x, y, currRing, currDenom);
                                if (currRing.hasHalfIntegers()) {
                                    expResult = new RealQuadraticInteger((v * x + w * y * iterDiscr)/2, (v * y + w * x)/2, currRing, currDenom);
                                } else {
                                    expResult = new RealQuadraticInteger(v * x + w * y * iterDiscr, v * y + w * x, currRing, currDenom);
                                }
                                try {
                                    result = testMultiplicandA.times(testMultiplicandB);
                                    assertEquals(expResult, result);
                                } catch (AlgebraicDegreeOverflowException adoe) {
                                    failMessage = "Multiplying two integers from the same ring should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                                    fail(failMessage);
                                }
                            }
                            // Now to test times(int)
                            expResult = new RealQuadraticInteger(v * x, w * x, currRing, currDenom);
                            result = testMultiplicandA.times(x);
                            assertEquals(expResult, result);
                        }
                    }
                }
            }
        }
        // Complex integer times its conjugate should match its norm
        for (int i = 0; i < totalTestIntegers; i++) {
            try {
                result = testIntegers.get(i).times(testConjugates.get(i));
                assertEquals(testNorms.get(i), result);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "Multiplying an integer by its conjugate should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage();
                fail(failMessage);
            }
        }
        /* And now to test that multiplying algebraic integers from two 
           different quadratic integer rings triggers 
           AlgebraicDegreeOverflowException, provided they both have nonzero 
           "regular" parts */
        for (int j = 0; j < totalTestIntegers - 1; j++) {
            try {
                result = testIntegers.get(j).times(testIntegers.get(j + 1));
                failMessage = "Multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
                fail(failMessage);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "Necessary degree should be 4, not " + adoe.getNecessaryAlgebraicDegree() + ", for multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + ".";
                assertEquals(failMessage, 4, adoe.getNecessaryAlgebraicDegree());
                System.out.println("Multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
            }
            /* However, if one of them is purely real, there should be a result, 
               even if it takes us to a different ring */
            failMessage = "Multiplying " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " should not have caused";
            try {
                result = testNorms.get(j).times(testIntegers.get(j + 1));
                System.out.println("Multiplying " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " gives result " + result.toASCIIString());
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (Exception e) {
                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
                fail(failMessage);
            }
            failMessage = "Multiplying " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " by " + testIntegers.get(j).toASCIIString() + " should not have caused";
            try {
                result = testIntegers.get(j).times(testNorms.get(j + 1));
                System.out.println("Multiplying " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " by " + testIntegers.get(j).toASCIIString() + " gives result " + result.toASCIIString());
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (Exception e) {
                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
                fail(failMessage);
            }
        }
    }

    /**
     * Test of divides method, of class RealQuadraticInteger.
     */
    @Test
    public void testDivides() {
        System.out.println("divides(RealQuadraticInteger)");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testQuotient, testDivisor, testDividend;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscr)) {
                currRing = new RealQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = 3; w < 54; w += 2) {
                        testQuotient = new RealQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = 3; y < 54; y += 2) {
                                testDivisor = new RealQuadraticInteger(x, y, currRing, currDenom);
                                try {
                                    testDividend = testQuotient.times(testDivisor);
                                } catch (AlgebraicDegreeOverflowException adoe) {
                                    testDividend = zeroRQI; // This is just to avoid "variable result might not have been initialized" error
                                    failMessage = "Check results of times() test for incorrect triggering of AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                                    fail(failMessage);
                                }
                                try {
                                    result = testDividend.divides(testDivisor);
                                    assertEquals(testQuotient, result);
                                } catch (AlgebraicDegreeOverflowException adoe) {
                                    failMessage = "Dividing one integer by another from the same ring should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
                                    fail(failMessage);
                                } catch (NotDivisibleException nde) {
                                    failMessage = "Dividing " + testDividend.toASCIIString() + " by " + testDivisor.toASCIIString() + " should not have triggered NotDivisibleException \"" + nde.getMessage() + "\"";
                                    fail(failMessage);
                                }
                                
                            }
                            // Now to test divides(int)
                            testDividend = new RealQuadraticInteger(v * x, w * x, currRing, currDenom);
                            expResult = new RealQuadraticInteger(v, w, currRing, currDenom);
                            try {
                                result = testDividend.divides(x);
                                assertEquals(expResult, result);
                            } catch (NotDivisibleException nde) {
                                failMessage = "Dividing " + testDividend.toASCIIString() + " by " + x + " should not have triggered NotDivisibleException\"" + nde.getMessage() + "\"";
                                fail(failMessage);
                            }
                            
                        }
                    }
                }
            }
        }
        // TODO: Rethink following part of the test, consider split off or remove altogether
        /* Now to test dividing a purely real integer held in an 
           RealQuadraticInteger object divided by a purely real integer in 
           an int */
//        System.out.println("divides(int)");
//        int testDivRealPartMult;
//        for (int iterDiscrOQ = -11; iterDiscrOQ > -84; iterDiscrOQ -= 8) {
//            if (NumberTheoreticFunctionsCalculator.isSquareFree(iterDiscrOQ)) {
//                currRing = new RealQuadraticRing(iterDiscrOQ);
//                testDivRealPartMult = (-iterDiscrOQ + 1)/4;
//                testDividend = new RealQuadraticInteger(testDivRealPartMult, 0, currRing);
//                try {
//                    result = testDividend.divides(2);
//                    failMessage = "Trying to divide " + testDividend.toString() + " by 2 in " + currRing.toString() + " should have triggered NotDivisibleException, not given result " + result.toString();
//                    fail(failMessage);
//                } catch (NotDivisibleException nde) {
//                    System.out.println("Trying to divide " + testDividend.toASCIIString() + " by 2 in " + currRing.toASCIIString() + " correctly triggered NotDivisibleException \"" + nde.getMessage() + "\"");
//                } catch (Exception e) {
//                    System.out.println("Encountered this exception: " + e.getClass().getName() + " \"" + e.getMessage() + "\"");
//                    failMessage = "Trying to divide " + testDividend.toString() + " by 2 in " + currRing.toString() + " triggered the wrong exception.";
//                    fail(failMessage);
//                }
//            }
//        }
        for (int i = 0; i < totalTestIntegers; i++) {
            try {
                result = testNorms.get(i).divides(testConjugates.get(i));
                System.out.println(testNorms.get(i).toASCIIString() + " divided by " + testConjugates.get(i).toASCIIString() + " is " + result.toASCIIString());
                assertEquals(testIntegers.get(i), result);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "AlgebraicDegreeOverflowException should not have occurred \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (NotDivisibleException nde) {
                System.out.println(testNorms.get(i).toASCIIString() + " divided by " + testConjugates.get(i).toASCIIString() + " is (" + nde.getFractNumers()[0] + " + " + nde.getFractDenoms()[1] + "sqrt(" + ((QuadraticRing) nde.getCausingRing()).getRadicand() + "))/" + nde.getFractDenoms()[0]);
                failMessage = "NotDivisibleException should not have occurred in dividing a norm by a conjugate.";
                fail(failMessage);
            }
            try {
                result = testNorms.get(i).divides(testIntegers.get(i));
                System.out.println(testNorms.get(i).toASCIIString() + " divided by " + testIntegers.get(i).toASCIIString() + " is " + result.toASCIIString());
                assertEquals(testConjugates.get(i), result);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "AlgebraicDegreeOverflowException should not have occurred \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (NotDivisibleException nde) {
                System.out.println(testNorms.get(i).toASCIIString() + " divided by " + testIntegers.get(i).toASCIIString() + " is (" + nde.getFractNumers()[0] + " + " + nde.getFractDenoms()[1] + "sqrt(" + ((QuadraticRing) nde.getCausingRing()).getRadicand() + "))/" + nde.getFractDenoms()[0]);
                failMessage = "NotDivisibleException should not have occurred in dividing a norm by a conjugate.";
                fail(failMessage);
            }
            // Check to make sure division by zero causes a suitable exception
            try {
                result = testIntegers.get(i).divides(zeroRQI);
                failMessage = "Dividing " + testIntegers.get(i).toASCIIString() + " by 0 + 0i should have caused an exception, not given result " + result.toASCIIString();
                fail(failMessage);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "AlgebraicDegreeOverflowException is the wrong exception to throw for division by 0 + 0i \"" + adoe.getMessage() + "\"";
                fail(failMessage);
            } catch (NotDivisibleException nde) {
                failMessage = "NotDivisibleException is the wrong exception to throw for division by 0 + 0i \"" + nde.getMessage() + "\"";
                fail(failMessage);
            } catch (IllegalArgumentException iae) {
                System.out.println("IllegalArgumentException correctly triggered upon attempt to divide by 0 + 0i \"" + iae.getMessage() + "\"");
            } catch (ArithmeticException ae) {
                System.out.println("ArithmeticException correctly triggered upon attempt to divide by 0 + 0i \"" + ae.getMessage() + "\"");
            } catch (Exception e) {
                failMessage = "Wrong exception thrown for attempt to divide by 0 + 0i. " + e.getMessage();
                fail(failMessage);
            }
            try {
                result = testIntegers.get(i).divides(0);
                failMessage = "Dividing " + testIntegers.get(i).toASCIIString() + " by 0 should have caused an exception, not given result " + result.toASCIIString();
                fail(failMessage);
            } catch (NotDivisibleException nde) {
                failMessage = "NotDivisibleException is the wrong exception to throw for division by 0 \"" + nde.getMessage() + "\"";
                fail(failMessage);
            } catch (IllegalArgumentException iae) {
                System.out.println("IllegalArgumentException correctly triggered upon attempt to divide by 0 \"" + iae.getMessage() + "\"");
            } catch (ArithmeticException ae) {
                System.out.println("ArithmeticException correctly triggered upon attempt to divide by 0. \"" + ae.getMessage() + "\"");
            } catch (Exception e) {
                failMessage = "Wrong exception thrown for attempt to divide by 0. " + e.getMessage();
                fail(failMessage);
            }
        }
        /* Check that dividing a real quadratic integer from one ring by a 
           rational integer from another ring does give the same result as if 
           the purely real integer was presented as being from the same ring. */
        testDividend = new RealQuadraticInteger(3, 1, RING_ZPHI);
        testDivisor = new RealQuadraticInteger(2, 0, RING_Z2);
        expResult = new RealQuadraticInteger(3, 1, RING_ZPHI, 2);
        failMessage = "Trying to divide " + testDividend.toASCIIString() + " by " + testDivisor.toASCIIString() + " from " + testDivisor.getRing().toASCIIString() + " should not have triggered";
        try {
            result = testDividend.divides(testDivisor);
            assertEquals(expResult, result);
        } catch (NotDivisibleException nde) {
            failMessage = failMessage + " NotDivisibleException \"" + nde.getMessage() + "\"";
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
            fail(failMessage);
        } catch (Exception e) {
            failMessage = failMessage + e.getClass().getName() + "\"" + e.getMessage() + "\"";
            fail(failMessage);
        }
        /* And now to test that dividing an algebraic integer from one real 
           quadratic ring by an algebraic integer from another real quadratic 
           ring triggers AlgebraicDegreeOverflowException */
        QuadraticInteger temp;
        for (int j = 0; j < totalTestIntegers - 1; j++) {
            failMessage = "Dividing " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " should not have";
            try {
                result = testIntegers.get(j).divides(testIntegers.get(j + 1));
                failMessage = failMessage + " resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
                fail(failMessage);
            } catch (AlgebraicDegreeOverflowException adoe) {
                failMessage = "Necessary degree should be 4, not " + adoe.getNecessaryAlgebraicDegree() + ", for multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + ".";
                assertEquals(failMessage, 4, adoe.getNecessaryAlgebraicDegree());
                System.out.println("Dividing " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
            } catch (NotDivisibleException nde) {
                failMessage = failMessage + " triggered NotDivisibleException \"" + nde.getMessage() + "\"";
                fail(failMessage);
            }
            // TODO: Review following part of test, consider split off or remove altogether
            /* However, if the divisor is rational, there should be a result, 
               even if it takes us to a different ring */
//            temp = testIntegers.get(j + 1).times(testNorms.get(j));
//            failMessage = "Dividing " + temp.toASCIIString() + " from " + temp.getCausingRing().toASCIIString() + " by " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getCausingRing().toASCIIString() + " should not have caused";
//            try {
//                result = temp.divides(testNorms.get(j));
//                System.out.println("Dividing " + temp.toASCIIString() + " from " + temp.getCausingRing().toASCIIString() + " by " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getCausingRing().toASCIIString() + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (NotDivisibleException nde) {
//                failMessage = failMessage + " NotDivisibleException \"" + nde.getMessage() + "\"";
//                fail(failMessage);
//            } catch (Exception e) {
//                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
//                fail(failMessage);
//            }
        }
    }
    
    /**
     * Test of the times, norm and abs methods, simultaneously, of class 
     * RealQuadraticInteger. If the independent tests for any of those are 
     * failing, the result of this test is meaningless.
     */
    @Test
    public void testSimultTimesAndNormAndAbs() {
        System.out.println("times and norm, simultaneous test");
        RealQuadraticInteger baseUnit = new RealQuadraticInteger(-3, -1, RING_OQ13, 2);
        QuadraticInteger currUnit = new RealQuadraticInteger(-1, 0, RING_OQ13);
        double currAbs = 1.0;
        long threshold = Integer.MAX_VALUE / 16;
        long currNorm;
        while (currAbs < threshold) {
            currUnit = currUnit.times(baseUnit); // Should be a positive number with norm -1
            System.out.println(currUnit.toASCIIString() + " = " + currUnit.getRealPartNumeric());
            currNorm = currUnit.norm();
            assertEquals(-1, currNorm);
            currUnit = currUnit.times(baseUnit); // Should be a negative number with norm 1
            System.out.println(currUnit.toASCIIString() + " = " + currUnit.getRealPartNumeric());
            currNorm = currUnit.norm();
            assertEquals(1, currNorm);
            currAbs = currUnit.abs();
        }
    }

    /**
     * Test of RealQuadraticInteger class constructor. The main thing we're 
     * testing here is that an invalid argument triggers an 
     * {@link IllegalArgumentException}.
     */
    @Test
    public void testConstructor() {
        System.out.println("RealQuadraticInteger (constructor)");
        RealQuadraticInteger quadrInt = new RealQuadraticInteger(1, 3, RING_Z2, 1); // This should work fine
        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
        quadrInt = new RealQuadraticInteger(7, 5, RING_ZPHI, 2); // This should also work fine
        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
        quadrInt = new RealQuadraticInteger(6, 4, RING_OQ13, -2); // This should also work, right?
        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
        // Test 3-parameter constructor
        quadrInt = new RealQuadraticInteger(5, 3, RING_OQ13);
        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
        RealQuadraticInteger comparisonInt = new RealQuadraticInteger(5, 3, RING_OQ13, 1);
        assertEquals(quadrInt, comparisonInt); // It should be the case that 5 + 3sqrt(-7) = 5 + 3sqrt(-7)
        comparisonInt = new RealQuadraticInteger(5, 3, RING_OQ13, 2);
        assertNotEquals(quadrInt, comparisonInt); // 5 + 3sqrt(-7) = 5/2 + 3sqrt(-7)/2 would be wrong
        try {
            quadrInt = new RealQuadraticInteger(3, 1, ringRandom, 4);
            System.out.println("Somehow created " + quadrInt.toASCIIString() + " without problem.");
            fail("Attempt to create RealQuadraticInteger with denominator 4 should have caused an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use denominator 4 correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception for denominator 4. \"" + e.getMessage() + "\"";
            fail(failMessage);
        }
        try {
            quadrInt = new RealQuadraticInteger(3, 2, RING_ZPHI, 2);
            System.out.println("Somehow created " + quadrInt.toASCIIString() + " without problem.");
            fail("Attempt to create RealQuadraticInteger with mismatched parities of a and b should have caused an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use mismatched parities correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception for mismatched parities. \"" + e.getMessage() + "\"";
            fail(failMessage);
        }
    }    

}
