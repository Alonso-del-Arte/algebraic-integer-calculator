/*
 * Copyright (C) 2025 Alonso del Arte
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
import static algebraics.MockRingTest.provideNull;
import static algebraics.quadratics.QuadraticRingTest.RANDOM;
import algebraics.unary.UnaryInteger;
import arithmetic.NotDivisibleException;
import calculators.NumberTheoreticFunctionsCalculator;
import static calculators.NumberTheoreticFunctionsCalculator.isSquarefree;
import static calculators.NumberTheoreticFunctionsCalculator
        .nextHighestSquarefree;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator.randomPowerOfTwo;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberOtherThan;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.testframe.api.Asserters.assertContainsSameOrder;
import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests for the RealQuadraticInteger class, which defines objects that 
 * represent real quadratic integers.
 * @author Alonso del Arte
 */
public class RealQuadraticIntegerTest {
    
    private static final String MINUS_SIGN = "\u2212";
    
    private static final char SQRT_SYMBOL = '\u221A';
    
    private static final char PHI_CHAR = '\u03C6';
    
    private static final char THETA_CHAR = '\u03B8';
    
    private static final QuadraticRing RING_ZPHI = new RealQuadraticRing(5);
    
    private static RealQuadraticRing chooseRing() {
        int propD = randomSquarefreeNumber(256);
        int d = (propD == 1) ? 5 : propD;
        return new RealQuadraticRing(d);
    }
    
    private static RealQuadraticRing chooseRingDOtherThan(int avoidedD) {
        int d = avoidedD;
        while (d == avoidedD || d == 1) {
            d = randomSquarefreeNumber(256);
        }
        return new RealQuadraticRing(d);
    }
    
    private static RealQuadraticRing chooseRingWithHalfInts() {
        int d;
        do {
            d = 4 * (randomNumber(256) + 1) + 1;
        } while (!isSquarefree(d));
        return new RealQuadraticRing(d);
    }
    
    private static RealQuadraticRing chooseRingWithHalfIntsNotPhi() {
        int d;
        do {
            d = 4 * (randomNumber(256) + 3) + 1;
        } while (!isSquarefree(d));
        return new RealQuadraticRing(d);
    }
    
    /**
     * Test of the algebraicDegree function, of the RealQuadraticInteger class, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        int expected = 2;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Another test of the algebraicDegree function, of the RealQuadraticInteger 
     * class, inherited from QuadraticInteger. Purely real and rational nonzero 
     * integers should have algebraic degree 1.
     */
    @Test
    public void testAlgebraicDegreeOne() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        int expected = 1;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Another test of the algebraicDegree function, of the RealQuadraticInteger 
     * class, inherited from QuadraticInteger. Zero should have algebraic degree 
     * 0 regardless of what ring it comes from.
     */
    @Test
    public void testAlgebraicDegreeZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, 0, ring);
        int expected = 0;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString() 
                + " from " + ring.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the trace function, of the RealQuadraticInteger class, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
        QuadraticRing ring = chooseRing();
        int bound = Integer.MIN_VALUE / -8;
        int halfBound = bound / 2;
        int a = randomNumber(bound) - halfBound;
        int b = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        long expected = 2L * a;
        long actual = number.trace();
        String message = "Reckoning trace of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testTraceHalfInteger() {
        int d = randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        int bound = 1 << 24;
        int signAdjust = RANDOM.nextBoolean() ? -1 : 1;
        int expected = signAdjust * 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber() + 1;
        QuadraticInteger number = new RealQuadraticInteger(expected, b, ring, 
                2);
        long actual = number.trace();
        String message = "Reckoning trace of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testTraceEdgeCaseLow() {
        int bound = 65536;
        int a = Integer.MIN_VALUE | randomNumber(bound);
        int b = randomNumber() | 8;
        QuadraticRing ring = chooseRing();
        QuadraticInteger num = new RealQuadraticInteger(a, b, ring);
        long expected = 2L * a;
        long actual = num.trace();
        String message = "Reckoning trace of " + num.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testTraceEdgeCasesHigh() {
        int bound = 65536;
        int a = Integer.MAX_VALUE ^ randomNumber(bound);
        int b = randomNumber() | 8;
        QuadraticRing ring = chooseRing();
        QuadraticInteger num = new RealQuadraticInteger(a, b, ring);
        long expected = 2L * a;
        long actual = num.trace();
        String message = "Reckoning trace of " + num.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testFullTrace() {
        System.out.println("fullTrace");
        int a = randomNumber();
        int b = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger instance = new RealQuadraticInteger(a, b, ring);
        BigInteger expected = BigInteger.valueOf(2L * a);
        BigInteger actual = instance.fullTrace();
        String message = "Reckoning trace of " + instance.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testFullTraceHalfInts() {
        int a = randomNumber() | 1;
        int b = randomNumber() | 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger instance = new RealQuadraticInteger(a, b, ring, 2);
        BigInteger expected = BigInteger.valueOf(a);
        BigInteger actual = instance.fullTrace();
        String message = "Reckoning trace of " + instance.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormOfZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, 0, ring);
        long expected = 0L;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormNoSurdPart() {
        int propA = randomNumber();
        int a = propA == 0 ? 1 : propA;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        long expected = (long) a * (long) a;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormNoRegPart() {
        int bound = Integer.MIN_VALUE / (-256);
        int propB = randomNumber(bound);
        int b = propB == 0 ? 1 : propB;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, b, ring);
        long expected = -((long) b * (long) b * (long) ring.getRadicand());
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the norm function, of the RealQuadraticInteger class, inherited 
     * from QuadraticInteger.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        QuadraticRing ring = chooseRing();
        int bound = 256;
        int halfBound = bound / 2;
        int a = randomNumber(bound) - halfBound;
        int b = (randomNumber(bound) | (randomNumber(16) + 1)) - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        long expected = a * a - ring.getRadicand() * b * b;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testNormHalfInteger() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int bound = 256;
        int halfBound = bound / 2;
        int a = 2 * randomNumber(bound) + 1 - halfBound;
        int b = 2 * randomNumber(bound) + 1 - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        long expected = (a * a - ring.getRadicand() * b * b) / 4;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testFullNorm() {
        System.out.println("fullNorm");
        int bound = 1024;
        int decrement = randomNumber(bound);
        int d = nextHighestSquarefree(Integer.MAX_VALUE - decrement);
        QuadraticRing ring = new RealQuadraticRing(d);
        int a = randomNumber(bound);
        int b = Integer.MAX_VALUE - randomNumber(bound);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        BigInteger wrappedA = BigInteger.valueOf(a);
        BigInteger wrappedB = BigInteger.valueOf(b);
        BigInteger wrappedD = BigInteger.valueOf(d);
        BigInteger aSquared = wrappedA.multiply(wrappedA);
        BigInteger bSquared = wrappedB.multiply(wrappedB);
        BigInteger bSquaredTimesD = bSquared.multiply(wrappedD);
        BigInteger expected = aSquared.subtract(bSquaredTimesD);
        BigInteger actual = number.fullNorm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testFullNormHalfInt() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int d = ring.getRadicand();
        int bound = 256 * d;
        int a = randomNumber(bound) | 1;
        int b = randomNumber(bound) | 1;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        BigInteger wrappedA = BigInteger.valueOf(a);
        BigInteger wrappedB = BigInteger.valueOf(b);
        BigInteger wrappedD = BigInteger.valueOf(d);
        BigInteger aSquared = wrappedA.multiply(wrappedA);
        BigInteger bSquared = wrappedB.multiply(wrappedB);
        BigInteger bSquaredTimesD = bSquared.multiply(wrappedD);
        BigInteger four = BigInteger.valueOf(4);
        BigInteger expected = aSquared.subtract(bSquaredTimesD).divide(four);
        BigInteger actual = number.fullNorm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testMinPolynomialCoeffsForZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger zero = new RealQuadraticInteger(0, 0, ring);
        long[] expecteds = {0L, 1L, 0L};
        long[] actuals = zero.minPolynomialCoeffs();
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test
    public void testMinPolynomialCoeffsForUnaryInteger() {
        QuadraticRing ring = chooseRing();
        int propA = RANDOM.nextInt(Short.MAX_VALUE) - Byte.MAX_VALUE;
        int a = propA == 0 ? 1 : propA;
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        long[] expecteds = {-a, 1L, 0L};
        long[] actuals = number.minPolynomialCoeffs();
        String message = "Reckoning minimum polynomial coefficients for " 
                + number.toString();
        assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Test of the minPolynomialCoeffs function, of the RealQuadraticInteger 
     * class, inherited from QuadraticInteger.
     */
    @Test
    public void testMinPolynomialCoeffs() {
        System.out.println("minPolynomialCoeffs");
        QuadraticRing ring = chooseRing();
        int bound = 1 << 16;
        int halfBound = bound >> 1;
        int a = RANDOM.nextInt(bound) - halfBound;
        int propB = RANDOM.nextInt(bound) - halfBound;
        int b = propB == 0 ? 1 : propB;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        long norm = (long) a * a - (long) b * b * ring.getRadicand();
        long trace = 2L * a;
        long[] expecteds = {norm, -trace, 1L};
        long[] actuals = number.minPolynomialCoeffs();
        String message = "Reckoning minimum polynomial coefficients for " 
                + number.toString();
        assertArrayEquals(message, expecteds, actuals);
    }
    
    @Test
    public void testMinPolynomialCoeffsForHalfInts() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int bound = 1 << 16;
        int halfBound = bound >> 1;
        int a = (RANDOM.nextInt(bound) - halfBound) | 1;
        int b = (RANDOM.nextInt(bound) - halfBound) | 1;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        long norm = ((long) a * a - (long) b * b * ring.getRadicand()) / 4L;
        long[] expecteds = {norm, -a, 1L};
        long[] actuals = number.minPolynomialCoeffs();
        String message = "Reckoning minimum polynomial coefficients for " 
                + number.toString();
        assertArrayEquals(message, expecteds, actuals);
    }
    
    @Test
    public void testMinPolynomialCoeffsExcessiveDegree() {
        QuadraticRing ring = chooseRing();
        int bound = 1 << 16;
        int halfBound = bound >> 1;
        int a = RANDOM.nextInt(bound) - halfBound;
        int b = RANDOM.nextInt(bound) - halfBound;
        final int erroneousDegree = 3 + RANDOM.nextInt(bound);
        QuadraticInteger instance = new RealQuadraticInteger(a, b, ring) {
            
            @Override
            public int algebraicDegree() {
                return erroneousDegree;
            }
            
        };
        String msg = "Given that " + instance.toString() 
                + " has been erroneously declared to have algebraic degree " 
                + erroneousDegree 
                + ", minPolynomialCoeffs() should've caused exception";
        Throwable t = assertThrows(() -> {
            long[] coeffs = instance.minPolynomialCoeffs();
            System.out.println(msg + ", not given result " 
                    + Arrays.toString(coeffs));
        }, AlgebraicDegreeOverflowException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String numStr = Integer.toString(erroneousDegree);
        String message = "Exception message should include erroneous degree " 
                + numStr;
        assert excMsg.contains(numStr) : message;
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test of the minPolynomialString function, of the RealQuadraticInteger 
     * class, inherited from QuadraticInteger. Spaces in the results are 
     * desirable but not required. Therefore this test strips out spaces before 
     * asserting equality.
     */
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        fail("BRITTLE TEST, NEEDS REWRITE");
        int bound = 128;
        int halfBound = bound / 2;
        int power = randomPowerOfTwo();
        int adjust = (power < 65536) ? power : (power >> 16);
        int a = (randomNumber(bound) - halfBound) | adjust;
        int b = (randomNumber(bound) - halfBound) | adjust;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        int d = ring.getRadicand();
        int trace = -2 * a;
        int norm = a * a - d * b * b;
        String intermediate = "x\u00B2+" + trace + "x+" + norm;
        String expected = intermediate.replace("+-", MINUS_SIGN);
        String actual = number.minPolynomialString().replace(" ", "");
        String message = "Reckoning minimal polynomial of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
//        String expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expResult = "x\u00B2";
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                if (randomRegForHalfInts < 0) {
//                    expResult = expResult + "+" + ((-1) * randomRegForHalfInts);
//                } else {
//                    expResult = expResult + "\u2212" + randomRegForHalfInts;
//                }
//                expResult = expResult + "x+" + ((randomRegForHalfInts * randomRegForHalfInts - randomSurdForHalfInts * randomSurdForHalfInts * testIntegers.get(i).getRing().getRadicand())/4);
//            } else {
//                if (randomRegPart < 0) {
//                    expResult = expResult + "+" + ((-2) * randomRegPart);
//                } else {
//                    expResult = expResult + "\u2212" + (2 * randomRegPart);
//                }
//                expResult = expResult + "x+" + (randomRegPart * randomRegPart - randomSurdPart * randomSurdPart * testIntegers.get(i).getRing().getRadicand());
//            }
//            expResult = expResult.replace("+1x", "+x");
//            expResult = expResult.replace("\u22121x", "\u2212x");
//            expResult = expResult.replace("+0x", "");
//            expResult = expResult.replace("\u22120x", "");
//            expResult = expResult.replace("+-", "\u2212");
//            result = testIntegers.get(i).minPolynomialString().replace(" ", ""); // Strip out spaces
//            assertEquals(expResult, result);
//        }
//        // Now to test the polynomial strings of a few purely real integers
//        RealQuadraticInteger degreeOneInt;
//        for (int j = 1; j < 8; j++) {
//            degreeOneInt = new RealQuadraticInteger(j, 0, ringRandom);
//            expResult = "x\u2212" + j;
//            result = degreeOneInt.minPolynomialString().replace(" ", "");
//            assertEquals(expResult, result);
//            degreeOneInt = new RealQuadraticInteger(-j, 0, ringRandom);
//            expResult = "x+" + j;
//            result = degreeOneInt.minPolynomialString().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        /* I'm not terribly concerned about this one, so it's here more for the 
//           sake of completeness than anything else. Feel free to delete if 
//           inconvenient. */
//        assertEquals("x", zeroRQI.minPolynomialString());

    /**
     * Test of minPolynomialStringTeX method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger. Spaces in the results are desirable but 
     * not required. Therefore the tests should strip out spaces before 
     * asserting equality.
     */
    @Test
    public void testMinPolynomialStringTeX() {
        System.out.println("minPolynomialStringTeX");
        fail("REWRITE THIS TEST");
//        String expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expResult = "x^2";
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                if (randomRegForHalfInts < 0) {
//                    expResult = expResult + "+" + ((-1) * randomRegForHalfInts);
//                } else {
//                    expResult = expResult + "-" + randomRegForHalfInts;
//                }
//                expResult = expResult + "x+" + ((randomRegForHalfInts * randomRegForHalfInts - randomSurdForHalfInts * randomSurdForHalfInts * testIntegers.get(i).getRing().getRadicand())/4);
//            } else {
//                if (randomRegPart < 0) {
//                    expResult = expResult + "+" + ((-2) * randomRegPart);
//                } else {
//                    expResult = expResult + "-" + (2 * randomRegPart);
//                }
//                expResult = expResult + "x+" + (randomRegPart * randomRegPart - randomSurdPart * randomSurdPart * testIntegers.get(i).getRing().getRadicand());
//            }
//            expResult = expResult.replace("+1x", "+x");
//            expResult = expResult.replace("-1x", "-x");
//            expResult = expResult.replace("+0x", "");
//            expResult = expResult.replace("-0x", "");
//            expResult = expResult.replace("+-", "-");
//            result = testIntegers.get(i).minPolynomialStringTeX().replace(" ", ""); // Strip out spaces
//            assertEquals(expResult, result);
//        }
//        // Now to test the polynomial strings of a few purely real integers
//        RealQuadraticInteger degreeOneInt;
//        for (int j = 1; j < 8; j++) {
//            degreeOneInt = new RealQuadraticInteger(j, 0, ringRandom);
//            expResult = "x-" + j;
//            result = degreeOneInt.minPolynomialStringTeX().replace(" ", "");
//            assertEquals(expResult, result);
//            degreeOneInt = new RealQuadraticInteger(-j, 0, ringRandom);
//            expResult = "x+" + j;
//            result = degreeOneInt.minPolynomialStringTeX().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        /* I'm not terribly concerned about this one, so it's here more for the 
//           sake of completeness than anything else. Feel free to delete if 
//           inconvenient. */
//        assertEquals("x", zeroRQI.minPolynomialStringTeX());
    }

    /**
     * Test of minPolynomialStringHTML method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger. Spaces in the results are desirable but 
     * not required. Therefore the tests should strip out spaces before 
     * asserting equality.
     */
    @Test
    public void testMinPolynomialStringHTML() {
        System.out.println("minPolynomialStringHTML");
        fail("REWRITE THIS TEST");
//        String expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expResult = "<i>x</i><sup>2</sup>";
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                if (randomRegForHalfInts < 0) {
//                    expResult = expResult + "+" + ((-1) * randomRegForHalfInts);
//                } else {
//                    expResult = expResult + "&minus;" + randomRegForHalfInts;
//                }
//                expResult = expResult + "<i>x</i>+" + ((randomRegForHalfInts * randomRegForHalfInts - randomSurdForHalfInts * randomSurdForHalfInts * testIntegers.get(i).getRing().getRadicand())/4);
//            } else {
//                if (randomRegPart < 0) {
//                    expResult = expResult + "+" + ((-2) * randomRegPart);
//                } else {
//                    expResult = expResult + "&minus;" + (2 * randomRegPart);
//                }
//                expResult = expResult + "<i>x</i>+" + (randomRegPart * randomRegPart - randomSurdPart * randomSurdPart * testIntegers.get(i).getRing().getRadicand());
//            }
//            expResult = expResult.replace("+1<i>x</i>", "+<i>x</i>");
//            expResult = expResult.replace("&minus;1<i>x</i>", "&minus;<i>x</i>");
//            expResult = expResult.replace("+0<i>x</i>", "");
//            expResult = expResult.replace("&minus;0<i>x</i>", "");
//            expResult = expResult.replace("+-", "&minus;");
//            result = testIntegers.get(i).minPolynomialStringHTML().replace(" ", ""); // Strip out spaces
//            assertEquals(expResult, result);
//        }
//        // Now to test the polynomial strings of a few purely real integers
//        RealQuadraticInteger degreeOneInt;
//        for (int j = 1; j < 8; j++) {
//            degreeOneInt = new RealQuadraticInteger(j, 0, ringRandom);
//            expResult = "<i>x</i>&minus;" + j;
//            result = degreeOneInt.minPolynomialStringHTML().replace(" ", "");
//            assertEquals(expResult, result);
//            degreeOneInt = new RealQuadraticInteger(-j, 0, ringRandom);
//            expResult = "<i>x</i>+" + j;
//            result = degreeOneInt.minPolynomialStringHTML().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        /* I'm not terribly concerned about this one, so it's here more for the 
//           sake of completeness than anything else. Feel free to delete if 
//           inconvenient. */
//        assertEquals("<i>x</i>", zeroRQI.minPolynomialStringHTML());
    }

    /**
     * Test of the conjugate function, of the RealQuadraticInteger class.
     */
    @Test
    public void testConjugate() {
        System.out.println("conjugate");
        int a = RANDOM.nextInt();
        int propB = RANDOM.nextInt();
        int b = (propB == 0) ? 1 : propB;
        int propD = randomSquarefreeNumber(16384);
        int d = (propD == 1) ? 2 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        QuadraticInteger expected = new RealQuadraticInteger(a, -b, ring);
        QuadraticInteger actual = number.conjugate();
        String message = "Calculating conjugate of " + number.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Another test of the conjugate function, of the RealQuadraticInteger 
     * class.
     */
    @Test
    public void testConjugateUnary() {
        int a = RANDOM.nextInt();
        int propD = randomSquarefreeNumber(16384);
        int d = (propD == 1) ? 2 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger expected = new RealQuadraticInteger(a, 0, ring);
        QuadraticInteger actual = expected.conjugate();
        String message = expected.toString() + " should be its own conjugate";
        assertEquals(message, expected, actual);
    }
    
    /**
     * Another test of the conjugate function, of the RealQuadraticInteger 
     * class.
     */
    @Test
    public void testConjugateHalfIntegers() {
        int a = RANDOM.nextInt() | 1;
        int b = RANDOM.nextInt() | 1;
        int d = randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        QuadraticInteger expected = new RealQuadraticInteger(a, -b, ring, 2);
        QuadraticInteger actual = number.conjugate();
        String message = "Calculating conjugate of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the abs function, of the RealQuadraticInteger class.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        RealQuadraticRing ring = chooseRing();
        int bound = 256;
        int a = -randomNumber(bound);
        int b = -(randomNumber(bound) | (randomNumber(16) + 1));
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        double expected = -(a + ring.getRadSqrt() * b);
        double actual = number.abs();
        String message = "Reckoning absolute value of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAbsAlreadyPositive() {
        RealQuadraticRing ring = chooseRing();
        int bound = 256;
        int a = randomNumber(bound);
        int b = randomNumber(bound) | (randomNumber(16) + 1);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        double expected = a + ring.getRadSqrt() * b;
        double actual = number.abs();
        String message = "Reckoning absolute value of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }

    /**
     * Test of the getRealPartNumeric function, of the RealQuadraticInteger 
     * class.
     */
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartMultNumeric");
        RealQuadraticRing ring = chooseRing();
        int bound = 256;
        int halfBound = bound / 2;
        int a = randomNumber(bound) - halfBound;
        int b = randomNumber(bound) - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        double expected = ring.getRadSqrt() * b + a;
        double actual = number.getRealPartNumeric();
        String message = "Reckoning floating point approximation of " 
                + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }

    @Test
    public void testGetRealPartNumericHalfInteger() {
        RealQuadraticRing ring = chooseRingWithHalfInts();
        int bound = 256;
        int halfBound = bound / 2;
        int a = (2 * randomNumber(bound) + 1) - halfBound;
        int b = (2 * randomNumber(bound) + 1) - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        double expected = (ring.getRadSqrt() * b + a) / 2;
        double actual = number.getRealPartNumeric();
        String message = "Reckoning floating point approximation of " 
                + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }

    /**
     * Test of the getImagPartNumeric function, of the RealQuadraticInteger 
     * class.
     */
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartwRadMultNumeric");
        QuadraticRing ring = chooseRing();
        int bound = 256;
        int halfBound = bound / 2;
        int a = randomNumber(bound) - halfBound;
        int b = randomNumber(bound) - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        double expected = 0.0;
        double actual = number.getImagPartNumeric();
        String message = "Reckoning imaginary part of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testGetImagPartNumericHalfInteger() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int bound = 256;
        int halfBound = bound / 2;
        int a = (2 * randomNumber(bound) + 1) - halfBound;
        int b = (2 * randomNumber(bound) + 1) - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        double expected = 0.0;
        double actual = number.getImagPartNumeric();
        String message = "Reckoning imaginary part of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of the isReApprox function, of the RealQuadraticInteger class.
     */
    @Test
    public void testIsReApprox() {
        System.out.println("isReApprox");
        int bound = 256;
        int halfBound = bound / 2;
        int a = randomNumber(bound) - halfBound;
        int mask = randomNumber(8) + 2;
        int b = (randomNumber(bound) - halfBound) | mask;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String msg = "Real part of " + number.toString() + " given as " 
                + number.getRealPartNumeric() 
                + " should be considered an approximation";
        assert number.isReApprox() : msg;
    }

    /**
     * Another test of the isReApprox function, of the RealQuadraticInteger 
     * class.
     */
    @Test
    public void testIsReApproxWithHalfInts() {
        int bound = 256;
        int halfBound = bound / 2;
        int a = 2 * randomNumber(bound) - halfBound + 1;
        int b = 2 * randomNumber(bound) - halfBound + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        String msg = "Real part of " + number.toString() + " given as " 
                + number.getRealPartNumeric() 
                + " should be considered an approximation";
        assert number.isReApprox() : msg;
    }

    /**
     * Another test of the isReApprox function, of the RealQuadraticInteger 
     * class. Given a + 0 * sqrt(d), where a and d are ordinary integers and d 
     * is positive, the real part as given by the getRealPartNumeric() function 
     * is not a rational approximation in floating point of an irrational 
     * number.
     */
    @Test
    public void testIsNotReApproxIfSurdPartZero() {
        int a = randomNumber();
        int b = 0;
        QuadraticRing ring = chooseRing();
        RealQuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String msg = "Real part of " + number.toString() + " in " 
                + ring.toString() + " given as " + number.getRealPartNumeric() 
                + " should be considered exact, not an approximation";
        assert !number.isReApprox() : msg;
    }

    /**
     * Test of the isImApprox function, of the RealQuadraticInteger class.
     */
    @Test
    public void testIsImApprox() {
        System.out.println("isImApprox");
        int a = randomNumber();
        int b = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String msg = "Imaginary part of " + number.toString() + " given as " 
                + number.getImagPartNumeric() 
                + " should be considered exact, not an approximation";
        assert !number.isImApprox() : msg;
    }
    
    @Test
    public void testIsImApproxHalfInts() {
        int bound = 8192;
        int halfBound = bound / 2;
        int a = 2 * randomNumber(bound) - halfBound + 1;
        int b = 2 * randomNumber(bound) - halfBound + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        String msg = "Imaginary part of " + number.toString() + " given as " 
                + number.getImagPartNumeric() 
                + " should be considered exact, not an approximation";
        assert !number.isImApprox() : msg;
    }
    
    @Test
    public void testAngleOfZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger zero = new RealQuadraticInteger(0, 0, ring);
        double expected = 0.0;
        double actual = zero.angle();
        String message = "Reckoning angle of " + zero.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }

    @Test
    public void testAngleOfPositive() {
        QuadraticRing ring = chooseRing();
        int bound = 4096;
        int halfBound = bound / 2;
        int a = RANDOM.nextInt(bound) - halfBound;
        int adjust = (a < 1) ? -a : 1;
        int b = RANDOM.nextInt(bound) + adjust;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        double expected = 0.0;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }

    @Test
    public void testAngleHalfIntsPositive() {
        int bound = 4096;
        int halfBound = bound / 2;
        int a = 2 * RANDOM.nextInt(bound) - halfBound + 1;
        int adjust = ((a < 1) ? -a : 0) & -2;
        int b = 2 * RANDOM.nextInt(bound) + adjust + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        double expected = 0.0;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }

    /**
     * Test of the angle function, of the RealQuadraticInteger class.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        QuadraticRing ring = chooseRing();
        int bound = 4096;
        int halfBound = bound / 2;
        int a = RANDOM.nextInt(bound) - halfBound;
        int adjust = (a < 1) ? -a : 1;
        int b = RANDOM.nextInt(bound) + adjust;
        QuadraticInteger number = new RealQuadraticInteger(-a, -b, ring);
        double expected = Math.PI;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleHalfIntsNegative() {
        int bound = 4096;
        int halfBound = bound / 2;
        int a = 2 * RANDOM.nextInt(bound) - halfBound + 1;
        int adjust = ((a < 1) ? -a : 0) & -2;
        int b = 2 * RANDOM.nextInt(bound) + adjust + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(-a, -b, ring, 2);
        double expected = Math.PI;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of the getRing function, of the RealQuadraticInteger class, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        int a = randomNumber();
        int propB = randomNumber();
        int b = propB == 0 ? 1 : propB;
        QuadraticRing expected = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, expected);
        QuadraticRing actual = number.getRing();
        String message = "Getting ring of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getRegPartMult function, of the RealQuadraticInteger class.
     */
    @Test
    public void testGetRegPartMult() {
        System.out.println("getRegPartMult");
        int expected = randomNumber();
        int b = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(expected, b, ring);
        int actual = number.getRegPartMult();
        String message = "Getting reg part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetRegPartMultHalfInt() {
        int bound = 512;
        int expected = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(expected, b, ring, 
                2);
        int actual = number.getRegPartMult();
        String message = "Getting real part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getSurdPartMult function, of the RealQuadraticInteger class.
     */
    @Test
    public void testGetSurdPartMult() {
        System.out.println("getImagPartMult");
        int a = randomNumber();
        int expected = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, expected, ring);
        int actual = number.getSurdPartMult();
        String message = "Getting surd part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetSurdPartMultHalfInt() {
        int bound = 512;
        int a = 2 * randomNumber(bound) + 1;
        int expected = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, expected, ring, 
                2);
        int actual = number.getSurdPartMult();
        String message = "Getting surd part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getDenominator function, of the RealQuadraticInteger class.
     */
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        int bound = 8192;
        int halfBound = bound / 2;
        int a = 2 * randomNumber(bound) + 1 - halfBound;
        int b = 2 * randomNumber(bound) + 1 - halfBound;
        int expected = 2;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 
                expected);
        int actual = number.getDenominator();
        String message = "Getting denominator of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testGetDenominatorOne() {
        int a = randomNumber();
        int b = randomNumber();
        int expected = 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 
                expected);
        int actual = number.getDenominator();
        String message = "Getting denominator of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringPositiveRegZeroSurd() {
        int bound = 16384;
        int a = RANDOM.nextInt(1, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a);
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, 0, ring);
        String expected = "0";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegativeRegZeroSurd() {
        int bound = 16384;
        int a = RANDOM.nextInt(1, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(-a, 0, ring);
        String expected = MINUS_SIGN + a;
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + SQRT_SYMBOL + '(' + ring.getRadicand() 
                + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringNegativeReg() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(-a, b, ring);
        String expected = MINUS_SIGN + a + "+" + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringNegativeRegNegativeSurd() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(-a, -b, ring);
        String expected = MINUS_SIGN + a + MINUS_SIGN + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringPositiveRegNegativeSurd() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, -b, ring);
        String expected = a + MINUS_SIGN + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringZeroRegPositiveSurd() {
        int bound = 8192;
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, b, ring);
        String expected = Integer.toString(b) + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringZeroRegNegativeSurd() {
        int bound = 8192;
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, -b, ring);
        String expected = MINUS_SIGN + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringPositiveRegMultipleOfTen() {
        int bound = 8192;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + SQRT_SYMBOL + '(' + ring.getRadicand() 
                + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegativeRegMultipleOfTen() {
        int bound = 8192;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(-a, b, ring);
        String expected = MINUS_SIGN + a + "+" + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringZeroRegPlusOneOfRoot() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, 1, ring);
        String expected = "" + SQRT_SYMBOL + '(' + ring.getRadicand() + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringZeroRegMinusOneOfRoot() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, -1, ring);
        String expected = MINUS_SIGN + SQRT_SYMBOL + '(' + ring.getRadicand() 
                + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringHalfIntPositiveRegPositiveSurd() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 2);
        String expected = a + "/2+" + b + SQRT_SYMBOL + '(' + ring.getRadicand() 
                + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntPositiveRegNegativeSurd() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, -b, ring, 2);
        String expected = a + "/2" + MINUS_SIGN + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeRegPositiveSurd() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(-a, b, ring, 2);
        String expected = MINUS_SIGN + a + "/2+" + b + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeReNegativeIm() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(-a, -b, ring, 2);
        String expected = MINUS_SIGN + a + "/2" + MINUS_SIGN + b + SQRT_SYMBOL 
                + '(' + ring.getRadicand() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntPositiveRePlusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, 1, ring, 2);
        String expected = a + "/2+" + SQRT_SYMBOL + '(' + ring.getRadicand() 
                + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntPositiveReMinusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(a, -1, ring, 2);
        String expected = a + "/2" + MINUS_SIGN + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeRePlusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(-a, 1, ring, 2);
        String expected = MINUS_SIGN + a + "/2+" + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeReMinusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new RealQuadraticInteger(-a, -1, ring, 
                2);
        String expected = MINUS_SIGN + a + "/2" + MINUS_SIGN + SQRT_SYMBOL + '(' 
                + ring.getRadicand() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringAltRationalFromRingD2Mod4SameAsToString() {
        int a = randomNumber();
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Rational number in the context of " + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltRationalFromRingD3Mod4SameAsToString() {
        int a = randomNumber();
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Rational number in the context of " + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltD2Mod4SameAsToString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltD3Mod4SameAsToString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPhi() {
        QuadraticInteger phi = new RealQuadraticInteger(1, 1, RING_ZPHI, 2);
        String expected = Character.toString(PHI_CHAR);
        String actual = phi.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation for " + phi.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltPositiveMultipleOfPhi() {
        int a = RANDOM.nextInt(2, 8192);
        QuadraticInteger number = new RealQuadraticInteger(a, a, RING_ZPHI, 2);
        String expected = Integer.toString(a) + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + PHI_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeMultipleOfPhi() {
        int a = RANDOM.nextInt(2, 8192);
        QuadraticInteger number = new RealQuadraticInteger(-a, -a, RING_ZPHI, 2);
        String expected = MINUS_SIGN + a + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + PHI_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativePhi() {
        QuadraticInteger phi = new RealQuadraticInteger(-1, -1, RING_ZPHI, 2);
        String expected = MINUS_SIGN + PHI_CHAR;
        String actual = phi.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation for " + phi.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveRationalPhiContext() {
        int a = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        QuadraticInteger number = new RealQuadraticInteger(a, 0, RING_ZPHI);
        String expected = Integer.toString(a);
        String actual = number.toStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringAltZeroPhiContext() {
        QuadraticInteger zero = new RealQuadraticInteger(0, 0, RING_ZPHI);
        String expected = "0";
        String actual = zero.toStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringAltNegativeRationalPhiContext() {
        int a = randomNumber() | Integer.MIN_VALUE;
        QuadraticInteger number = new RealQuadraticInteger(a, 0, RING_ZPHI);
        String expected = MINUS_SIGN + Integer.toString(-a);
        String actual = number.toStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringAltPositiveIntPlusPositiveMultipleOfPhi() {
        int bound = 128;
        int nonPhiPart = RANDOM.nextInt(1, bound);
        int phiPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = nonPhiPart + "+" + phiPart + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusNegativeMultipleOfPhi() {
        int bound = 128;
        int nonPhiPart = RANDOM.nextInt(1, bound);
        int phiPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = nonPhiPart + MINUS_SIGN + (-phiPart) + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusPositiveMultipleOfPhi() {
        int bound = 128;
        int nonPhiPart = -RANDOM.nextInt(1, bound);
        int phiPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = MINUS_SIGN + (-nonPhiPart) + "+" + phiPart + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusNegativeMultipleOfPhi() {
        int bound = 128;
        int nonPhiPart = -RANDOM.nextInt(1, bound);
        int phiPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = MINUS_SIGN + (-nonPhiPart) + MINUS_SIGN + (-phiPart) 
                + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusPositivePhi() {
        int bound = 128;
        int nonPhiPart = RANDOM.nextInt(1, bound);
        int phiPart = 1;
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = nonPhiPart + "+" + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusNegativePhi() {
        int bound = 128;
        int nonPhiPart = RANDOM.nextInt(1, bound);
        int phiPart = -1;
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = nonPhiPart + MINUS_SIGN + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusPositivePhi() {
        int bound = 128;
        int nonPhiPart = -RANDOM.nextInt(1, bound);
        int phiPart = 1;
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = MINUS_SIGN + (-nonPhiPart) + "+" + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusNegativePhi() {
        int bound = 128;
        int nonPhiPart = -RANDOM.nextInt(1, bound);
        int phiPart = -1;
        int a = 2 * nonPhiPart + phiPart;
        QuadraticInteger number = new RealQuadraticInteger(a, phiPart, 
                RING_ZPHI, 2);
        String expected = MINUS_SIGN + (-nonPhiPart) + MINUS_SIGN + PHI_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning phi notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltRationalD1Mod4ContextNotPhi() {
        int a = randomNumber();
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + expected + " in " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltTheta() {
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger theta = new RealQuadraticInteger(1, 1, ring, 2);
        String expected = "" + THETA_CHAR;
        String actual = theta.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + theta.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeTheta() {
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger theta = new RealQuadraticInteger(-1, -1, ring, 2);
        String expected = MINUS_SIGN + THETA_CHAR;
        String actual = theta.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + theta.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveMultipleOfTheta() {
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        int a = RANDOM.nextInt(2, 8192);
        QuadraticInteger number = new RealQuadraticInteger(a, a, ring, 2);
        String expected = Integer.toString(a) + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeMultipleOfTheta() {
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        int a = RANDOM.nextInt(2, 8192);
        QuadraticInteger number = new RealQuadraticInteger(-a, -a, ring, 2);
        String expected = MINUS_SIGN + a + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the toStringAlt function, of the RealQuadraticInteger class.
     */
    @Test
    public void testToStringAlt() {
        System.out.println("toStringAlt");
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = nonThetaPart + "+" + thetaPart + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltPositiveIntPlusNegativeMultipleOfTheta() {
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = nonThetaPart + MINUS_SIGN + (-thetaPart) + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeIntPlusPositiveMultipleOfTheta() {
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = MINUS_SIGN + (-nonThetaPart) + "+" + thetaPart 
                + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeIntPlusNegativeMultipleOfTheta() {
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = MINUS_SIGN + (-nonThetaPart) + MINUS_SIGN 
                + (-thetaPart) + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltPositiveIntPlusTheta() {
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = 1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = nonThetaPart + "+" + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusNegativeTheta() {
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = -1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = nonThetaPart + MINUS_SIGN + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusTheta() {
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = 1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = MINUS_SIGN + (-nonThetaPart) + "+" + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusNegativeTheta() {
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = -1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWithHalfIntsNotPhi();
        QuadraticInteger number = new RealQuadraticInteger(a, thetaPart, ring, 
                2);
        String expected = MINUS_SIGN + (-nonThetaPart) + MINUS_SIGN 
                + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringPositiveRegZeroSurd() {
        int bound = 16384;
        int a = RANDOM.nextInt(1, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a);
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(0, 0, ring);
        String expected = "0";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringNegativeRegZeroSurd() {
        int bound = 16384;
        int a = -RANDOM.nextInt(1, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a);
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toASCIIString function, of the RealQuadraticInteger class, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + "sqrt(" + ring.getRadicand() + ")";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToASCIIStringNegativeReg() {
        int bound = 8192;
        int a = -RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + "sqrt(" + ring.getRadicand() + ")";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToASCIIStringNegativeRegNegativeSurd() {
        int bound = 8192;
        int a = -RANDOM.nextInt(1, bound);
        int b = -RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = Integer.toString(a) + b + "sqrt(" 
                + ring.getRadicand() + ")";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToASCIIStringPositiveRegNegativeSurd() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = -RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        String expected = Integer.toString(a) + b + "sqrt(" 
                + ring.getRadicand() + ")";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Test of toASCIIStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToASCIIStringAlt() {
        System.out.println("toASCIIStringAlt");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toTeXString method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toTeXStringSingleDenom method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToTeXStringSingleDenom() {
        System.out.println("toTeXStringSingleDenom");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toTeXStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToTeXStringAlt() {
        System.out.println("toTeXStringAlt");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toHTMLString method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toHTMLStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToHTMLStringAlt() {
        System.out.println("toHTMLStringAlt");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of the hashCode function, of the RealQuadraticInteger class, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int initialCapacity = randomNumber(64) + 16;
        Set<QuadraticInteger> numbers = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>();
        for (int i = 0; i < initialCapacity; i++) {
            QuadraticInteger number;
            boolean includeHalfInts = RANDOM.nextBoolean();
            if (includeHalfInts) {
                int a = 2 * randomNumber() + 1;
                int b = 2 * randomNumber() + 1;
                QuadraticRing ring = chooseRingWithHalfInts();
                number = new RealQuadraticInteger(a, b, ring, 2);
            } else {
                int a = randomNumber();
                int b = randomNumber();
                QuadraticRing ring = chooseRing();
                number = new RealQuadraticInteger(a, b, ring);
            }
            numbers.add(number);
            hashes.add(number.hashCode());
        }
        int expected = numbers.size();
        int actual = hashes.size();
        String message = "Having created " + expected 
                + " distinct numbers, there should be as many hash codes";
        assertEquals(message, expected, actual);
    }

    @Test
    public void testReferentialEquality() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        assertEquals(number, number);
    }
    
    @Test
    public void testNotEqualsNull() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        Object obj = provideNull();
        String msg = "Number " + number.toString() + " should not equal null";
        assert !number.equals(obj) : msg;
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring);
        QuadraticInteger numberDiffClass 
                = new QuadraticIntegerTest.QuadraticIntegerImpl(a, b, ring);
        String msg = "Number " + number.toString() + " of class " 
                + number.getClass().getName() + " should not equal " 
                + numberDiffClass.toString() + " of class " 
                + numberDiffClass.getClass().getName();
        assert !number.equals(numberDiffClass) : msg;
    }
    
    @Test
    public void testNotEqualsDiffReg() {
        QuadraticRing ring = chooseRing();
        int aA = randomNumber();
        int aB = ~aA;
        int b = randomNumber();
        QuadraticInteger someNumber = new RealQuadraticInteger(aA, b, ring);
        QuadraticInteger diffNumber = new RealQuadraticInteger(aB, b, ring);
        assertNotEquals(someNumber, diffNumber);
    }
    
    /**
     * Test of the equals function, of the RealQuadraticInteger class.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger someNumber = new RealQuadraticInteger(a, b, ring);
        QuadraticInteger sameNumber = new RealQuadraticInteger(a, b, ring);
        assertEquals(someNumber, sameNumber);
    }
    
    @Test
    public void testNotEqualsDiffSurd() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int bA = randomNumber();
        int bB = ~bA;
        QuadraticInteger someNumber = new RealQuadraticInteger(a, bA, ring);
        QuadraticInteger diffNumber = new RealQuadraticInteger(a, bB, ring);
        String message = someNumber.toString() + " should not equal " 
                + diffNumber.toString();
        assertNotEquals(message, someNumber, diffNumber);
    }
    
    @Test
    public void testNotEqualsDiffDenom() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int a = 2 * randomNumber() + 1;
        int b = 2 * randomNumber() + 1;
        QuadraticInteger someNumber = new RealQuadraticInteger(a, b, ring);
        QuadraticInteger diffNumber = new RealQuadraticInteger(a, b, ring, 2);
        String message = someNumber.toString() + " should not equal " 
                + diffNumber.toString();
        assertNotEquals(message, someNumber, diffNumber);
    }
    
    @Test
    public void testNotEqualsDiffRing() {
        int a = randomNumber();
        int b = randomNumber();
        QuadraticRing ringA = chooseRing();
        QuadraticInteger someNumber = new RealQuadraticInteger(a, b, ringA);
        int bound = 512;
        int alternative = nextHighestSquarefree(bound);
        int dA = ringA.getRadicand();
        int propD = randomSquarefreeNumberOtherThan(dA, bound);
        int d = (propD == 1) ? 2 : propD;
        d = (d == dA) ? alternative : d;
        QuadraticRing ringB = new RealQuadraticRing(d);
        QuadraticInteger diffNumber = new RealQuadraticInteger(a, b, ringB);
        String message = someNumber.toString() + " should not equal " 
                + diffNumber.toString();
        assertNotEquals(message, someNumber, diffNumber);
    }
    
    private List<RealQuadraticInteger> 
        makeListOfRandomRealQuadraticsAllFromSameRing(RealQuadraticRing ring) {
        int initialCapacity = randomNumber(8) + 4;
        List<RealQuadraticInteger> list = new ArrayList<>(initialCapacity);
        int bound = 1024;
        int halfBound = bound / 2;
        boolean includeHalfInts = ring.hasHalfIntegers();
        for (int i = 0; i < initialCapacity; i++) {
            int a = randomNumber(bound) - halfBound;
            int b = randomNumber(bound) - halfBound;
            RealQuadraticInteger number = new RealQuadraticInteger(a, b, ring);
            list.add(number);
            if (includeHalfInts && a % 2 != 0 && b % 2 != 0) {
                RealQuadraticInteger halfInt = new RealQuadraticInteger(a, b, 
                        ring, 2);
                list.add(halfInt);
            }
        }
        return list;
    }
    
    @Test
    public void testCompareToAllSameRing() {
        RealQuadraticRing ring = chooseRing();
        List<RealQuadraticInteger> list 
                = makeListOfRandomRealQuadraticsAllFromSameRing(ring);
        List<RealQuadraticInteger> expected = new ArrayList<>(list);
        Collections.sort(expected, new RealQuadraticIntegerComparator());
        List<RealQuadraticInteger> actual = new ArrayList<>(list);
        Collections.sort(actual);
        assertContainsSameOrder(expected, actual);
    }
    
    /**
     * Test of the compareTo function, of the RealQuadraticInteger class.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        RealQuadraticRing ringA = chooseRing();
        List<RealQuadraticInteger> list 
                = makeListOfRandomRealQuadraticsAllFromSameRing(ringA);
        int avoidedD = ringA.getRadicand();
        RealQuadraticRing ringB = chooseRingDOtherThan(avoidedD);
        list.addAll(makeListOfRandomRealQuadraticsAllFromSameRing(ringB));
        RealQuadraticRing ringC = chooseRingWithHalfInts();
        list.addAll(makeListOfRandomRealQuadraticsAllFromSameRing(ringC));
        List<RealQuadraticInteger> expected = new ArrayList<>(list);
        Collections.sort(expected, new RealQuadraticIntegerComparator());
        List<RealQuadraticInteger> actual = new ArrayList<>(list);
        Collections.sort(actual);
        assertContainsSameOrder(expected, actual);
    }

    // TODO: Revise caveat examples
@org.junit.Ignore
    @Test
    public void testParseQuadraticInteger() {
        System.out.println("parseQuadraticInteger");
        fail("REWRITE THIS TEST");
//        for (int i = 0; i < totalTestIntegers; i++) {
//            numberString = testIntegers.get(i).toString();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toStringAlt();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toASCIIString();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toASCIIStringAlt();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toTeXString();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toTeXStringSingleDenom();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toTeXStringAlt();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toHTMLString();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//            numberString = testIntegers.get(i).toHTMLStringAlt();
//            numberRQI = QuadraticInteger.parseQuadraticInteger(testIntegers.get(i).getRing(), numberString);
//            assertEquals(testIntegers.get(i), numberRQI);
//        }
//        // A few special cases to check
//        numberString = "-sqrt(2)";
//        numberRQI = new RealQuadraticInteger(0, -1, RING_Z2);
//        assertEquals(numberRQI, QuadraticInteger.parseQuadraticInteger(numberString));
//        numberString = "0 - sqrt(-2)";
//        assertEquals(numberRQI, QuadraticInteger.parseQuadraticInteger(numberString));
//        /* Lastly, to check the appropriate exception is thrown for non-numeric 
//           strings */
//        numberString = "one plus imaginary unit";
//        try {
//            numberRQI = QuadraticInteger.parseQuadraticInteger(numberString);
//            fail("Attempting to call parseImaginaryQuadraticInteger on \"" + numberString + "\" should have triggered NumberFormatException, not given " + numberRQI.toASCIIString() + ".");
//        } catch (NumberFormatException nfe) {
//            System.out.println("Attempting to call parseImaginaryQuadraticInteger on \"" + numberString + "\" correctly triggered NumberFormatException: " + nfe.getMessage());
//        }
    }
    
    @org.junit.Ignore
    @Test
    public void testParseQuadraticIntegerOptions() {
        fail("REWRITE THIS TEST");
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
     * Test of plus method, of class RealQuadraticInteger, inherited from {@link 
     * QuadraticInteger}.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testAddendA, testAddendB;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
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
    }
    
    /**
     * Test of plus method, of class RealQuadraticInteger, inherited from {@link 
     * QuadraticInteger}. Adding an additive inverse to a real quadratic integer 
     * should give 0. Also, adding 0 to a real quadratic integer should give 
     * that real quadratic integer as a result.
     */@org.junit.Ignore
    @Test
    public void testPlusAdditiveInverses() {
        fail("REWRITE THIS TEST");
    }
    
    /**
     * Test of plus method, of class RealQuadraticInteger, inherited from {@link 
     * QuadraticInteger}. Adding real quadratic integers from different rings 
     * should cause {@link algebraics.AlgebraicDegreeOverflowException} unless 
     * either summand is a rational integer.
     */@org.junit.Ignore
    @Test
    public void testPlusAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
        QuadraticInteger result;
        String failMessage;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            try {
//                result = testIntegers.get(j).plus(testIntegers.get(j + 1));
//                failMessage = "Adding " + testIntegers.get(j).toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                System.out.println("Adding " + testIntegers.get(j).toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
//            }
//            failMessage = "Adding " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " should not have caused";
//            try {
//                result = testNorms.get(j).plus(testIntegers.get(j + 1));
//                System.out.println("Adding " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " to " + testIntegers.get(j + 1).toASCIIString() + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + "AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (Exception e) {
//                failMessage = failMessage + "Exception \"" + e.getMessage() + "\"";
//                fail(failMessage);
//            }
//            failMessage = "Adding " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " should not have caused";
//            try {
//                result = testIntegers.get(j).plus(testNorms.get(j + 1));
//                System.out.println("Adding " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + "AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (Exception e) {
//                failMessage = failMessage + "Exception \"" + e.getMessage() + "\"";
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of plus method, of class RealQuadraticInteger, inherited from {@link 
     * QuadraticInteger}. Specifically in <b>Z</b>[&radic;10].
     */
    @Test
    public void testPlusInRingZ10() {
        RealQuadraticRing ringZ10 = new RealQuadraticRing(10);
        RealQuadraticInteger negSeven = new RealQuadraticInteger(-7, 0, ringZ10);
        RealQuadraticInteger numNeg136plus44Sqrt10 = new RealQuadraticInteger(-136, 44, ringZ10);
        RealQuadraticInteger num117minus36Sqrt10 = new RealQuadraticInteger(117, -36, ringZ10);
        RealQuadraticInteger numSqrt10 = new RealQuadraticInteger(0, 1, ringZ10);
        RealQuadraticInteger expResult = new RealQuadraticInteger(-143, 44, ringZ10);
        QuadraticInteger result = negSeven.plus(numNeg136plus44Sqrt10);
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(117, -35, ringZ10);
        result = num117minus36Sqrt10.plus(numSqrt10);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of plus method, of class RealQuadraticInteger, inherited from {@link 
     * QuadraticInteger}. Specifically in 
     * <i>O</i><sub><b>Q</b>(&radic;13)</sub>.
     */@org.junit.Ignore
    @Test
    public void testPlusInRingOQ13() {
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger testSummandA = new RealQuadraticInteger(10, 3, RING_OQ13);
//        RealQuadraticInteger testSummandB = new RealQuadraticInteger(7, 1, RING_OQ13, 2);
//        QuadraticInteger expResult = new RealQuadraticInteger(27, 7, RING_OQ13, 2);
//        QuadraticInteger result = testSummandA.plus(testSummandB);
//        assertEquals(expResult, result);
//        testSummandA = new RealQuadraticInteger(3, 5, RING_OQ13, 2);
//        expResult = new RealQuadraticInteger(5, 3, RING_OQ13);
//        result = testSummandA.plus(testSummandB);
//        assertEquals(expResult, result);
    }
    
    /**
     * Test of negate method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testNegate() {
        fail("REWRITE THIS TEST");
        System.out.println("negate");
//        RealQuadraticInteger expected = new RealQuadraticInteger(-1, -1, 
//                RING_ZPHI, 2);
//        QuadraticInteger actual = GOLDEN_RATIO.negate();
//        assertEquals(expected, actual);
//        RealQuadraticInteger someNumber = new RealQuadraticInteger(
//                randomRegForHalfInts, randomSurdForHalfInts, 
//                ringRandomForAltTesting, 2);
//        expected = new RealQuadraticInteger(-randomRegForHalfInts, 
//                -randomSurdForHalfInts, ringRandomForAltTesting, 2);
//        actual = someNumber.negate();
//        assertEquals(expected, actual);
    }
    
    /**
     * Test of minus method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testMinuend, testSubtrahend;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
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
    }
    
    /**
     * Test of minus method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Subtracting a number from itself should give 0 
     * as a result, regardless of what the number is.
     */@org.junit.Ignore
    @Test
    public void testMinusNumberItself() {
        fail("REWRITE THIS TEST");
        RealQuadraticInteger expResult;
        QuadraticInteger result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expResult = new RealQuadraticInteger(0, 0, testIntegers.get(i).getRing());
//            try {
//                result = testIntegers.get(i).minus(testIntegers.get(i));
//                assertEquals(expResult, result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                String failMessage = "Subtracting test integer from itself should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage();
//                fail(failMessage);
//            }
//            result = testIntegers.get(i).minus(0);
//            assertEquals(testIntegers.get(i), result);
//        }
    }
    
    /**
     * Test of minus method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Subtracting a number from a different ring 
     * should be an algebraic integer of degree 4. Hence the operation should 
     * cause an {@link algebraics.AlgebraicDegreeOverflowException}.
     */@org.junit.Ignore
    @Test
    public void testMinusAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST, or maybe move this to base class test");
    }
    
    /**
     * Test of times method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testMultiplicandA, testMultiplicandB;
        int currDenom;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
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
    }
    
    /**
     * Test of times method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. A real quadratic integer times its conjugate 
     * should give its norm.
     */@org.junit.Ignore
    @Test
    public void testTimesConjugate() {
        fail("REWRITE THIS TEST");
        QuadraticInteger result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            try {
//                result = testIntegers.get(i).times(testConjugates.get(i));
//                assertEquals(testNorms.get(i), result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                String failMessage = "Multiplying an integer by its conjugate should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage();
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of times method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. If the product of two real quadratic integers 
     * is an algebraic integer of degree 4, {@link 
     * algebraics.AlgebraicDegreeOverflowException} should occur.
     */@org.junit.Ignore
    @Test
    public void testTimesAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
        String failMessage;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            try {
//                QuadraticInteger result = testIntegers.get(j).times(testIntegers.get(j + 1));
//                failMessage = "Multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = "Necessary degree should be 4, not " + adoe.getNecessaryAlgebraicDegree() + ", for multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + ".";
//                assertEquals(failMessage, 4, adoe.getNecessaryAlgebraicDegree());
//                System.out.println("Multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
//            }
//        }
    }
    
    /**
     * Test of times method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Multiplying a real quadratic integer by a 
     * rational real integer, even if presented as being from another ring, 
     * should give the proper result.
     */@org.junit.Ignore
    @Test
    public void testTimesIntAsRQI() {
        fail("REWRITE THIS TEST");
        String failMessage;
//        for (int i = 0; i < totalTestIntegers - 1; i++) {
//            failMessage = "Multiplying " + testNorms.get(i).toASCIIString() + " from " + testNorms.get(i).getRing().toASCIIString() + " by " + testIntegers.get(i + 1).toASCIIString() + " should not have caused";
//            try {
//                QuadraticInteger result = testNorms.get(i).times(testIntegers.get(i + 1));
//                System.out.println("Multiplying " + testNorms.get(i).toASCIIString() + " from " + testNorms.get(i).getRing().toASCIIString() + " by " + testIntegers.get(i + 1).toASCIIString() + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (Exception e) {
//                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
//                fail(failMessage);
//            }
//        }
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            failMessage = "Multiplying " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " by " + testIntegers.get(j).toASCIIString() + " should not have caused";
//            try {
//                QuadraticInteger result = testIntegers.get(j).times(testNorms.get(j + 1));
//                System.out.println("Multiplying " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " by " + testIntegers.get(j).toASCIIString() + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (Exception e) {
//                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
//                fail(failMessage);
//            }
//        }
    }

    /**
     * Test of divides method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}.
     */
    @Test
    public void testDivides() {
        System.out.println("divides(RealQuadraticInteger)");
        RealQuadraticRing currRing;
        RealQuadraticInteger testDividend, testDivisor, expResult;
        QuadraticInteger result;
        String failMessage;
        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
                currRing = new RealQuadraticRing(iterDiscr);
                testDividend = new RealQuadraticInteger(-iterDiscr + 1, 0, currRing);
                testDivisor = new RealQuadraticInteger(1, 1, currRing);
                expResult = new RealQuadraticInteger(1, -1, currRing);
                try {
                    result = testDividend.divides(testDivisor);
                    assertEquals(expResult, result);
                } catch (NotDivisibleException nde) {
                    failMessage = "NotDivisibleException should not have occurred for trying to divide " + testDividend.toASCIIString() + " by " + testDivisor.toASCIIString();
                    System.out.println(failMessage);
                    System.out.println("\"" + nde.getMessage() + "\"");
                    fail(failMessage);
                } catch (Exception e) {
                    failMessage = e.getClass().getName() + " should not have occurred for trying to divide " + testDividend.toASCIIString() + " by " + testDivisor.toASCIIString();
                    fail(failMessage);
                }
            }
        }
    }

    /**
     * Simultaneous test of the times and divides methods, of class 
     * RealQuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testTimesDivides() {
        fail("REWRITE THIS TEST");
        RealQuadraticRing currRing;
        QuadraticInteger expResult, result, testQuotient, testDivisor, testDividend;
        int currDenom;
        String failMessage;
//        for (int iterDiscr = 2; iterDiscr < 100; iterDiscr++) {
//            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
//                currRing = new RealQuadraticRing(iterDiscr);
//                if (currRing.hasHalfIntegers()) {
//                    currDenom = 2;
//                } else {
//                    currDenom = 1;
//                }
//                for (int v = -3; v < 48; v += 2) {
//                    for (int w = 3; w < 54; w += 2) {
//                        testQuotient = new RealQuadraticInteger(v, w, currRing, currDenom);
//                        for (int x = -3; x < 48; x += 2) {
//                            for (int y = 3; y < 54; y += 2) {
//                                testDivisor = new RealQuadraticInteger(x, y, currRing, currDenom);
//                                try {
//                                    testDividend = testQuotient.times(testDivisor);
//                                } catch (AlgebraicDegreeOverflowException adoe) {
//                                    testDividend = zeroRQI; // This is just to avoid "variable result might not have been initialized" error
//                                    failMessage = "Check results of times() test for incorrect triggering of AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                                    fail(failMessage);
//                                }
//                                try {
//                                    result = testDividend.divides(testDivisor);
//                                    assertEquals(testQuotient, result);
//                                } catch (AlgebraicDegreeOverflowException adoe) {
//                                    failMessage = "Dividing one integer by another from the same ring should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                                    fail(failMessage);
//                                } catch (NotDivisibleException nde) {
//                                    failMessage = "Dividing " + testDividend.toASCIIString() + " by " + testDivisor.toASCIIString() + " should not have triggered NotDivisibleException \"" + nde.getMessage() + "\"";
//                                    fail(failMessage);
//                                }
//                                
//                            }
//                            // Now to test divides(int)
//                            testDividend = new RealQuadraticInteger(v * x, w * x, currRing, currDenom);
//                            expResult = new RealQuadraticInteger(v, w, currRing, currDenom);
//                            try {
//                                result = testDividend.divides(x);
//                                assertEquals(expResult, result);
//                            } catch (NotDivisibleException nde) {
//                                failMessage = "Dividing " + testDividend.toASCIIString() + " by " + x + " should not have triggered NotDivisibleException\"" + nde.getMessage() + "\"";
//                                fail(failMessage);
//                            }
//                            
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * Test of divides method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Dividing the norm of a real quadratic integer 
     * by the conjugate of that real quadratic integer should give that real 
     * quadratic integer as a result.
     */@org.junit.Ignore
    @Test
    public void testDividesConjugate() {
        fail("REWRITE THIS TEST");
        QuadraticInteger result;
        String failMessage;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            try {
//                result = testNorms.get(i).divides(testConjugates.get(i));
//                System.out.println(testNorms.get(i).toASCIIString() + " divided by " + testConjugates.get(i).toASCIIString() + " is " + result.toASCIIString());
//                assertEquals(testIntegers.get(i), result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = "AlgebraicDegreeOverflowException should not have occurred \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (NotDivisibleException nde) {
//                Fraction[] fracts = nde.getFractions();
//                System.out.println(testNorms.get(i).toASCIIString() + " divided by " + testConjugates.get(i).toASCIIString() + " is (" + fracts[0].toString() + " + " + fracts[1].getNumerator() + "sqrt(" + ((QuadraticRing) nde.getCausingRing()).getRadicand() + "))/" + fracts[1].getDenominator() + ".");
//                failMessage = "NotDivisibleException should not have occurred in dividing a norm by a conjugate.";
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of divides method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Division by zero, regardless of what ring it is 
     * presented as being from, should cause either IllegalArgumentException or 
     * ArithmeticException to be thrown. Any other exception will fail the test, 
     * and that includes {@link algebraics.NotDivisibleException}. Not throwing 
     * any exception at all for division by zero will also fail the test.
     */@org.junit.Ignore
    @Test
    public void testDivisionByZeroRQI() {
        fail("REWRITE THIS TEST");
        String failMessage;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            RealQuadraticInteger zeroSurd = new RealQuadraticInteger(0, 1, zeroRQI.getRing());
//            String zeroStr = "0 + 0" + zeroSurd.toASCIIString();
//            try {
//                QuadraticInteger result = testIntegers.get(i).divides(zeroRQI);
//                failMessage = "Dividing " + testIntegers.get(i).toASCIIString() + " by " + zeroStr + " should have caused an exception, not given result " + result.toASCIIString();
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = "AlgebraicDegreeOverflowException is the wrong exception to throw for division by " + zeroStr + " \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (NotDivisibleException nde) {
//                failMessage = "NotDivisibleException is the wrong exception to throw for division by " + zeroStr + " \"" + nde.getMessage() + "\"";
//                fail(failMessage);
//            } catch (IllegalArgumentException iae) {
//                System.out.println("IllegalArgumentException correctly triggered upon attempt to divide by " + zeroStr);
//                System.out.println("\"" + iae.getMessage() + "\"");
//            } catch (ArithmeticException ae) {
//                System.out.println("ArithmeticException correctly triggered upon attempt to divide by " + zeroStr);
//                System.out.println("\"" + ae.getMessage() + "\"");
//            } catch (Exception e) {
//                failMessage = e.getClass().getName() + " is the wrong exception thrown for attempt to divide by " + zeroStr + ".";
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of divides method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Division by zero should cause either 
     * IllegalArgumentException or ArithmeticException to be thrown. Any other 
     * exception will fail the test, and that includes the {@link 
     * algebraics.NotDivisibleException}. Not throwing any exception at all for 
     * division by zero will also fail the test.
     */@org.junit.Ignore
    @Test
    public void testDivisionByZeroInt() {
        fail("REWRITE THIS TEST");
        String failMessage;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            try {
//                QuadraticInteger result = testIntegers.get(i).divides(0);
//                failMessage = "Dividing " + testIntegers.get(i).toASCIIString() + " by 0 should have caused an exception, not given result " + result.toASCIIString();
//                fail(failMessage);
//            } catch (NotDivisibleException nde) {
//                failMessage = "NotDivisibleException is the wrong exception to throw for division by 0 \"" + nde.getMessage() + "\"";
//                fail(failMessage);
//            } catch (IllegalArgumentException iae) {
//                System.out.println("IllegalArgumentException correctly triggered upon attempt to divide by 0");
//                System.out.println("\"" + iae.getMessage() + "\"");
//            } catch (ArithmeticException ae) {
//                System.out.println("ArithmeticException correctly triggered upon attempt to divide by 0.");
//                System.out.println("\"" + ae.getMessage() + "\"");
//            } catch (Exception e) {
//                failMessage = "Wrong exception thrown for attempt to divide by 0. " + e.getMessage();
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of divides method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Dividing a real quadratic integer by a rational 
     * integer, even if presented as coming from another ring, should give the 
     * proper result.
     */@org.junit.Ignore
    @Test
    public void testDividesApparentCrossDomain() {
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger testDividend = new RealQuadraticInteger(3, 1, RING_ZPHI);
//        RealQuadraticInteger testDivisor = new RealQuadraticInteger(2, 0, RING_Z2);
//        RealQuadraticInteger expResult = new RealQuadraticInteger(3, 1, RING_ZPHI, 2);
//        String failMessage = "Trying to divide " + testDividend.toASCIIString() + " by " + testDivisor.toASCIIString() + " from " + testDivisor.getRing().toASCIIString() + " should not have triggered";
//        try {
//            QuadraticInteger result = testDividend.divides(testDivisor);
//            assertEquals(expResult, result);
//        } catch (NotDivisibleException nde) {
//            failMessage = failMessage + " NotDivisibleException \"" + nde.getMessage() + "\"";
//            fail(failMessage);
//        } catch (AlgebraicDegreeOverflowException adoe) {
//            failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//            fail(failMessage);
//        } catch (Exception e) {
//            failMessage = failMessage + e.getClass().getName() + "\"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
    }
    
    /**
     * Test of divides method, of class RealQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. The division of an irrational real quadratic 
     * integer by an irrational real quadratic integer from another ring is an 
     * algebraic integer of degree 4, hence {@link 
     * algebraics.AlgebraicDegreeOverflowException} should occur.
     */@org.junit.Ignore
    @Test
    public void testDividesAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
        String msg;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            msg = "Dividing " + testIntegers.get(j).toASCIIString() + " by " 
//                    + testIntegers.get(j + 1).toASCIIString() 
//                    + " should not have";
//            try {
//                QuadraticInteger result 
//                        = testIntegers.get(j).divides(testIntegers.get(j + 1));
//                msg = msg + " resulted in " + result.toASCIIString() 
//                        + " without triggering AlgebraicDegreeOverflowException";
//                fail(msg);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                msg = "Necessary degree should be 4, not " 
//                        + adoe.getNecessaryAlgebraicDegree() 
//                        + ", for multiplying " 
//                        + testIntegers.get(j).toASCIIString() + " by " 
//                        + testIntegers.get(j + 1).toASCIIString();
//                assertEquals(msg, 4, adoe.getNecessaryAlgebraicDegree());
//                System.out.println("Dividing " 
//                        + testIntegers.get(j).toASCIIString() + " by " 
//                        + testIntegers.get(j + 1).toASCIIString() 
//                        + " correctly triggered exception for algebraic degree " 
//                        + adoe.getNecessaryAlgebraicDegree());
//            } catch (NotDivisibleException nde) {
//                msg = msg + " triggered NotDivisibleException \"" 
//                        + nde.getMessage() + "\"";
//                fail(msg);
//            }
//        }
    }
    
    /**
     * Test of the mod function, of the QuadraticInteger class. The number 112 + 
     * 11sqrt(5) modulo 21/2 + sqrt(5)/2 should be 1/2 + sqrt(5)/2, the golden 
     * ratio.
     */@org.junit.Ignore
    @Test
    public void testMod() {
        System.out.println("mod");
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger dividend = new RealQuadraticInteger(112, 11,  
//                RING_ZPHI);
//        RealQuadraticInteger divisor = new RealQuadraticInteger(21, 1, 
//                RING_ZPHI, 2);
//        RealQuadraticInteger expected = new RealQuadraticInteger(1, 1,  
//                RING_ZPHI, 2);
//        QuadraticInteger actual = dividend.mod(divisor);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. If m is 
     * divisible by n, m mod n should be 0.
     */@org.junit.Ignore
    @Test
    public void testModZero() {
        fail("REWRITE THIS TEST");
//        int a = 2 * randomNumber(128) + 1;
//        int b = 2 * randomNumber(a) + 1;
//        RealQuadraticInteger divisor = new RealQuadraticInteger(a, b, RING_OQ13, 
//                2);
//        RealQuadraticInteger division = new RealQuadraticInteger(b, -a, 
//                RING_OQ13, 2);
//        QuadraticInteger dividend = division.times(divisor);
//        RealQuadraticInteger expected = new RealQuadraticInteger(0, 0, 
//                RING_OQ13);
//        QuadraticInteger actual = dividend.mod(divisor);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. One 
     * pseudorandom quadratic integer is divided by another and the remainder is 
     * taken. That remainder is not checked directly. Instead, it is subtracted 
     * from the dividend, resulting in a number which divided by the divisor 
     * should give 0.
     */@org.junit.Ignore
    @Test
    public void testOtherMod() {
        fail("REWRITE THIS TEST");
        int a = 2 * randomNumber(224) + 33;
        int b = 2 * randomNumber(a) + 1;
//        RealQuadraticInteger divisor = new RealQuadraticInteger(a, b, 
//                RING_OQ13, 2);
//        int multiplier = randomNumber(21) + 3;
//        RealQuadraticInteger dividend 
//                = new RealQuadraticInteger(multiplier * a, multiplier * b 
//                        - b, RING_OQ13);
//        QuadraticInteger remainder = dividend.mod(divisor);
//        QuadraticInteger roundedDividend = dividend.minus(remainder);
//        String msg = "Since " + dividend.toString() + " mod " 
//                + divisor.toString() + " is said to be " + remainder.toString() 
//                + ", " + roundedDividend.toString() 
//                + " should be a multiple of " + divisor.toString();
//        RealQuadraticInteger expected = new RealQuadraticInteger(0, 0, 
//                RING_OQ13);
//        QuadraticInteger actual = roundedDividend.mod(divisor);
//        assertEquals(msg, expected, actual);
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. If the 
     * dividend and the divisor are from different real rings, an {@link 
     * AlgebraicDegreeOverflowException} should occur.
     */@org.junit.Ignore
    @Test
    public void testModCrossDomain() {
        fail("REWRITE THIS TEST");
        int a = randomNumber(2048) + 1;
        int b = randomNumber(a) + 127;
        a -= 1024;
//        RealQuadraticInteger dividend = new RealQuadraticInteger(a, b, RING_Z2);
//        RealQuadraticInteger divisor = new RealQuadraticInteger(a, b, RING_OQ13);
//        try {
//            QuadraticInteger result = dividend.mod(divisor);
//            String msg = dividend.toString() + " mod " + divisor.toString() 
//                    + " should not have given result " + result.toString();
//            fail(msg);
//        } catch (AlgebraicDegreeOverflowException adoe) {
//            System.out.println(dividend.toASCIIString() + " mod " 
//                    + divisor.toASCIIString()
//                    + " correctly caused AlgebraicDegreeOverflowException");
//            System.out.println("\"" + adoe.getMessage() + "\"");
//        } catch (RuntimeException re) {
//            String msg = re.getClass().getName() 
//                    + " is the wrong exception to throw for " 
//                    + dividend.toString() + " mod " + divisor.toString();
//            fail(msg);
//        }
    }
    
    /**
     * Test of the times, norm and abs methods, simultaneously, of class 
     * RealQuadraticInteger. If the independent tests for any of those are 
     * failing, the result of this test is meaningless.
     */@org.junit.Ignore
    @Test
    public void testSimultTimesAndNormAndAbs() {
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger baseUnit = new RealQuadraticInteger(-3, -1, RING_OQ13, 2);
//        QuadraticInteger currUnit = new RealQuadraticInteger(-1, 0, RING_OQ13);
//        double currAbs = 1.0;
//        long threshold = Integer.MAX_VALUE / 16;
//        long currNorm;
//        while (currAbs < threshold) {
//            currUnit = currUnit.times(baseUnit); // Should be a positive number with norm -1
//            System.out.println(currUnit.toASCIIString() + " = " + currUnit.getRealPartNumeric());
//            currNorm = currUnit.norm();
//            assertEquals(-1, currNorm);
//            currUnit = currUnit.times(baseUnit); // Should be a negative number with norm 1
//            System.out.println(currUnit.toASCIIString() + " = " + currUnit.getRealPartNumeric());
//            currNorm = currUnit.norm();
//            assertEquals(1, currNorm);
//            currAbs = currUnit.abs();
//        }
    }
    
    @Test
    public void testToUnaryInteger() {
        System.out.println("toUnaryInteger");
        int a = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger instance = new RealQuadraticInteger(a, 0, ring);
        UnaryInteger expected = new UnaryInteger(a);
        UnaryInteger actual = instance.toUnaryInteger();
        assertEquals(expected, actual);
    }
        
    @Test
    public void testToUnaryIntegerOverflowsAlgebraicDegree() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        QuadraticRing ring = chooseRing();
        QuadraticInteger instance = new RealQuadraticInteger(a, b, ring);
        String msg = "Should not be able to convert " + instance.toString() 
                + " to unary integer";
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = instance.toUnaryInteger();
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, AlgebraicDegreeOverflowException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String numStr = instance.toASCIIString();
        String containsMsg = "Exception message should contain \"" + numStr 
                + "\"";
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }

    /**
     * Test of applyPhi method, of class RealQuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testApplyPhi() {
        System.out.println("applyPhi");
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger expResult = new RealQuadraticInteger(1, 1, RING_ZPHI, 2);
//        RealQuadraticInteger result = RealQuadraticInteger.applyPhi(0, 1);
//        assertEquals(expResult, result);
//        expResult = new RealQuadraticInteger(3, 1, RING_ZPHI, 2);
//        result = RealQuadraticInteger.applyPhi(1, 1);
//        assertEquals(expResult, result);
    }
    
    /**
     * Simultaneous test of applyPhi method, of class RealQuadraticInteger, and 
     * applyTheta method, of class {@link QuadraticInteger}. Since &phi; = 1/2 + 
     * &radic;5/2 and &theta; = 1/2 + &radic;5/2 for <b>Z</b>[&phi;], the 
     * results of applyPhi and applyTheta should be the same.
     */@org.junit.Ignore
    @Test
    public void testApplyPhiThetaConcur() {
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger thruPhi;
//        QuadraticInteger thruTheta;
//        for (int a = 1; a < 8; a++) {
//            for (int b = 1; b < 8; b++) {
//                thruPhi = RealQuadraticInteger.applyPhi(a, b);
//                thruTheta = QuadraticInteger.applyTheta(a, b, RING_ZPHI);
//                assertEquals(thruPhi, thruTheta);
//            }
//        }
    }

    // TODO: Break up this constructor test into smaller tests
    /**
     * Test of RealQuadraticInteger class constructor. The main thing we're 
     * testing here is that an invalid argument triggers an 
     * {@link IllegalArgumentException}.
     */@org.junit.Ignore
    @Test
    public void testConstructor() {
        fail("REWRITE THIS TEST");
//        RealQuadraticInteger quadrInt = new RealQuadraticInteger(1, 3, RING_Z2, 1); // This should work fine
//        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
//        quadrInt = new RealQuadraticInteger(7, 5, RING_ZPHI, 2); // This should also work fine
//        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
//        quadrInt = new RealQuadraticInteger(6, 4, RING_OQ13, -2); // This should also work, right?
//        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
//        // Test 3-parameter constructor
//        quadrInt = new RealQuadraticInteger(5, 3, RING_OQ13);
//        System.out.println("Created " + quadrInt.toASCIIString() + " without problem.");
//        RealQuadraticInteger comparisonInt = new RealQuadraticInteger(5, 3, RING_OQ13, 1);
//        assertEquals(quadrInt, comparisonInt); // It should be the case that 5 + 3sqrt(-7) = 5 + 3sqrt(-7)
//        comparisonInt = new RealQuadraticInteger(5, 3, RING_OQ13, 2);
//        assertNotEquals(quadrInt, comparisonInt); // 5 + 3sqrt(-7) = 5/2 + 3sqrt(-7)/2 would be wrong
//        try {
//            quadrInt = new RealQuadraticInteger(3, 1, ringRandom, 4);
//            System.out.println("Somehow created " + quadrInt.toASCIIString() + " without problem.");
//            fail("Attempt to create RealQuadraticInteger with denominator 4 should have caused an IllegalArgumentException.");
//        } catch (IllegalArgumentException iae) {
//            System.out.println("Attempt to use denominator 4 correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = e.getClass().getName() + " is the wrong exception for denominator 4. \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        try {
//            quadrInt = new RealQuadraticInteger(3, 2, RING_ZPHI, 2);
//            System.out.println("Somehow created " + quadrInt.toASCIIString() + " without problem.");
//            fail("Attempt to create RealQuadraticInteger with mismatched parities of a and b should have caused an IllegalArgumentException.");
//        } catch (IllegalArgumentException iae) {
//            System.out.println("Attempt to use mismatched parities correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = e.getClass().getName() + " is the wrong exception for mismatched parities. \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
    }    

    @Test
    public void testConstructorRejectsNullRing() {
        int a = randomNumber();
        int b = randomNumber();
        int denom = RANDOM.nextBoolean() ? 2 : 1;
        String msg = "Should not be able to create instance with a = " + a 
                + ", b = " + b + ", denom = " + denom + " and null ring";
        Throwable t = assertThrows(() -> {
            QuadraticInteger badInstance = new RealQuadraticInteger(a, b, null, 
                    denom);
            System.out.println(msg + ", but did create " 
                    + badInstance.getClass().getName() + '@' 
                    + Integer.toHexString(System
                            .identityHashCode(badInstance)));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        assert !excMsg.isBlank() : "Message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorDefaultDenomRejectsNullRing() {
        int a = randomNumber();
        int b = randomNumber();
        String msg = "Should not be able to create instance with a = " + a 
                + ", b = " + b + " and null ring";
        Throwable t = assertThrows(() -> {
            QuadraticInteger badInstance = new RealQuadraticInteger(a, b, null);
            System.out.println(msg + ", but did create " 
                    + badInstance.getClass().getName() + '@' 
                    + Integer.toHexString(System
                            .identityHashCode(badInstance)));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        assert !excMsg.isBlank() : "Message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    private static class RealQuadraticIntegerComparator 
            implements Comparator<RealQuadraticInteger> {
        
        @Override
        public int compare(RealQuadraticInteger a, RealQuadraticInteger b) {
            return Double.compare(a.getRealPartNumeric(), 
                    b.getRealPartNumeric());
        }
        
    }
    
}
