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
package algebraics;

import calculators.NumberTheoreticFunctionsCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.BeforeClass;
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
        int maxAB;
        int randomDiscr = NumberTheoreticFunctionsCalculator.randomSquarefreeNumber(MAXIMUM_RING_D);
        if (randomDiscr == 2 || randomDiscr == 5 || randomDiscr == 13) {
            randomDiscr++; // This is just in case we get 2, 5 or 13.
        }
        boolean ringRandomd1mod4 = (randomDiscr % 4 == -3);
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
        maxAB = (int) Math.floor(Math.sqrt(Integer.MAX_VALUE/(4 * (randomDiscr + 1))));
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
        currNorm = (randomRegForHalfInts * randomRegForHalfInts + 3 * randomSurdForHalfInts * randomSurdForHalfInts)/4;
        currRQI = new RealQuadraticInteger(currNorm, 0, RING_ZPHI);
        testNorms.add(currRQI);
        testNormsRegParts.add(currNorm);
        currRQI = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, RING_OQ13, 2);
        testIntegers.add(currRQI);
        currRQI = new RealQuadraticInteger(-randomRegForHalfInts, -randomSurdForHalfInts, RING_OQ13, 2);
        testAdditiveInverses.add(currRQI);
        currRQI = new RealQuadraticInteger(randomRegForHalfInts, -randomSurdForHalfInts, RING_OQ13, 2);
        testConjugates.add(currRQI);
        currNorm = (randomRegForHalfInts * randomRegForHalfInts + 7 * randomSurdForHalfInts * randomSurdForHalfInts)/4;
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
            currNorm = (randomRegForHalfInts * randomRegForHalfInts + (-randomDiscr) * randomSurdForHalfInts * randomSurdForHalfInts)/4;
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
            currNorm = randomRegPart * randomRegPart + (-randomDiscr) * randomSurdPart * randomSurdPart;
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
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                expResult[1] = -randomRegForHalfInts;
                expResult[0] = (randomRegForHalfInts * randomRegForHalfInts + randomSurdForHalfInts * randomSurdForHalfInts * testIntegers.get(i).getRing().getRadicand())/4;
            } else {
                expResult[1] = (-2) * randomRegPart;
                expResult[0] = randomRegPart * randomRegPart + randomSurdPart * randomSurdPart * testIntegers.get(i).getRing().getAbsNegRad();
            }
            result = testIntegers.get(i).minPolynomial();
            assertArrayEquals(expResult, result);
            /* Now to test the mimimal polymomial of the purely imaginary 
               integer sqrt(d) */
            expResult[1] = 0;
            expResult[0] = testIntegers.get(i).getRing().getAbsNegRad();
            baseSurdDist = new RealQuadraticInteger(0, 1, testIntegers.get(i).getRing());
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
        fail("Haven't written the test yet.");
    }

    /**
     * Test of getRealPartMultNumeric method, of class RealQuadraticInteger.
     */
    @Test
    public void testGetRealPartMultNumeric() {
        System.out.println("getRealPartMultNumeric");
        double expResult = (double) randomRegForHalfInts/2;
        double result;
        for (int i = 0; i < totalTestIntegers; i++) {
            result = testIntegers.get(i).getRealPartMultNumeric();
            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
                assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
            } else {
                assertEquals(randomRegPart, result, ImaginaryQuadraticRingTest.TEST_DELTA);
            }
        }
    }

    /**
     * Test of getImagPartwRadMultNumeric method, of class RealQuadraticInteger.
     */
    @Test
    public void testGetImagPartwRadMultNumeric() {
        System.out.println("getImagPartwRadMultNumeric");
        double expResult = 0.0;
        double result;
        for (int i = 0; i < totalTestIntegers; i++) {
            result = testIntegers.get(i).getImagPartwRadMultNumeric();
            assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        }
    }
    
    /**
     * Test of getRing method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
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
     * QuadraticInteger.
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
     * QuadraticInteger.
     */
    @Test
    public void testToStringAlt() {
        System.out.println("toStringAlt");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of toASCIIString method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of toASCIIStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testToASCIIStringAlt() {
        System.out.println("toASCIIStringAlt");
        fail("Haven't written the test yet.");
    }

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
        fail("Haven't written the test yet.");
    }

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
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toASCIIString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toASCIIStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toTeXString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toTeXStringSingleDenom();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toTeXStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toHTMLString();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberIQI);
//            numberString = testIntegers.get(i).toHTMLStringAlt();
//            numberIQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
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
        fail("Haven't written the test yet.");
    }

    /**
     * Test of minus method, of class RealQuadraticInteger.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of times method, of class RealQuadraticInteger.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        fail("Haven't written the test yet.");
    }

    /**
     * Test of divides method, of class RealQuadraticInteger.
     */
    @Test
    public void testDivides() {
        System.out.println("divides");
        fail("Haven't written the test yet.");
    }

}
