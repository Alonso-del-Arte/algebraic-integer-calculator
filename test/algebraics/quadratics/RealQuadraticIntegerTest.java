/*
 * Copyright (C) 2024 Alonso del Arte
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
import arithmetic.NotDivisibleException;
import calculators.NumberTheoreticFunctionsCalculator;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberOtherThan;
import fractions.Fraction;

import static calculators.NumberTheoreticFunctionsCalculator.isSquarefree;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests for the RealQuadraticInteger class, which defines objects that 
 * represent real quadratic integers.
 * @author Alonso del Arte
 */
public class RealQuadraticIntegerTest {
    
    private static RealQuadraticRing chooseRing() {
        int propD = randomSquarefreeNumber(256);
        int d = (propD == 1) ? 5 : propD;
        return new RealQuadraticRing(d);
    }
    
    private static RealQuadraticRing chooseRingWithHalfInts() {
        int d;
        do {
            d = 4 * (randomNumber(256) + 1) + 1;
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
        int a = randomNumber();
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
        int expected = 2 * randomNumber() + 1;
        int b = 2 * randomNumber() + 1;
        QuadraticInteger number = new RealQuadraticInteger(expected, b, ring, 
                2);
        long actual = number.trace();
        String message = "Reckoning trace of " + number.toString();
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
        long expected = (long) b * (long) b * (long) ring.getAbsNegRad();
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
@org.junit.Ignore
    @Test
    public void testNormHalfInteger() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int bound = 256;
        int halfBound = bound / 2;
        int a = 2 * randomNumber(bound) + 1 - halfBound;
        int b = 2 * randomNumber(bound) + 1 - halfBound;
        QuadraticInteger number = new RealQuadraticInteger(a, b, ring, 
                2);
        long expected = (a * a - ring.getRadicand() * b * b) / 4;
        long actual = number.norm();
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
     * Test of minPolynomialString method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger. Spaces in the results are desirable but 
     * not required. Therefore the tests should strip out spaces before 
     * asserting equality.
     */@org.junit.Ignore
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        fail("REWRITE THIS TEST");
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
    }

    /**
     * Test of minPolynomialStringTeX method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger. Spaces in the results are desirable but 
     * not required. Therefore the tests should strip out spaces before 
     * asserting equality.
     */@org.junit.Ignore
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
     */@org.junit.Ignore
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
     */@org.junit.Ignore
    @Test
    public void testIsImApprox() {
        System.out.println("isImApprox");
        fail("REWRITE THIS TEST");
        int a = 2 * (randomNumber(32768) - 16384) + 1;
        int b = 2 * (randomNumber(32768) - 16384) + 1;
//        RealQuadraticInteger number = new RealQuadraticInteger(a, b, RING_OQ13, 
//                2);
//        String msg = "Imaginary part of " + number.toString() + " given as " 
//                + number.getImagPartNumeric() 
//                + " should be considered exact, not an approximation";
//        assert number.isReApprox() : msg;
    }

    /**
     * Test of getImagPartNumeric method, of class RealQuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testAngle() {
        System.out.println("angle");
        fail("REWRITE THIS TEST");
        double expResult, result;
//        QuadraticInteger num = GOLDEN_RATIO;
//        for (int i = 0; i < 10; i++) {
//            num = num.plus(i);
//            expResult = 0.0;
//            result = num.angle();
//            assertEquals(expResult, result, QuadraticRingTest.TEST_DELTA);
//            num = num.times(-1); // Negate number
//            expResult = Math.PI;
//            result = num.angle();
//            assertEquals(expResult, result, QuadraticRingTest.TEST_DELTA);
//            num = num.times(-1); // Back to positive
//        }
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
     * Test of getRegPartMult method, of class ImaginaryQuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testGetRegPartMult() {
        System.out.println("getRealPartMult");
        fail("REWRITE THIS TEST");
        int expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = randomRegForHalfInts;
//            } else {
//                expResult = randomRegPart;
//            }
//            result = testIntegers.get(i).getRegPartMult();
//            assertEquals(expResult, result);
//        }
    }
    
    /**
     * Test of getSurdPartMult method, of class ImaginaryQuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testGetSurdPartMult() {
        System.out.println("getImagPartMult");
        fail("REWRITE THIS TEST");
        int expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = randomSurdForHalfInts;
//            } else {
//                expResult = randomSurdPart;
//            }
//            result = testIntegers.get(i).getSurdPartMult();
//            assertEquals(expResult, result);
//        }
    }
    
    /**
     * Test of getDenominator method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        fail("REWRITE THIS TEST");
//        for (int i = 0; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                assertEquals(2, testIntegers.get(i).getDenominator());
//            } else {
//                assertEquals(1, testIntegers.get(i).getDenominator());
//            }
//        }
    }

    /**
     * Test of toString method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger. For functions that return Strings, spaces are desirable 
     * but not required. Therefore the tests should strip out spaces before 
     * asserting equality.
     */@org.junit.Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        fail("REWRITE THIS TEST");
        String expResult, result;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = randomRegForHalfInts + "/2+" + randomSurdForHalfInts + "\u221A(" + testIntegers.get(i).getRing().getRadicand() + ")/2";
//            } else {
//                if (randomRegPart == 0) {
//                    expResult = randomSurdPart + "\u221A(" + testIntegers.get(i).getRing().getRadicand() + ")";
//                } else {
//                    expResult = randomRegPart + "+" + randomSurdPart + "\u221A(" + testIntegers.get(i).getRing().getRadicand() + ")";
//                }
//            }
//            expResult = expResult.replace("+-", "-");
//            expResult = expResult.replace("+1\u221A", "+\u221A");
//            expResult = expResult.replace("-1\u221A", "-\u221A");
//            expResult = expResult.replace("-", "\u2212");
//            result = testIntegers.get(i).toString().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // And last but not least, 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toString();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toString();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toStringAlt method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger. For functions that return Strings, spaces are desirable 
     * but not required. Therefore the tests should strip out spaces before 
     * asserting equality. If the test of toString fails, the result of this 
     * test is irrelevant.
     */@org.junit.Ignore
    @Test
    public void testToStringAlt() {
        System.out.println("toStringAlt");
        fail("REWRITE THIS TEST");
        String expResult, result;
        int nonThetaPart;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                nonThetaPart = (randomRegForHalfInts - randomSurdForHalfInts)/2;
//                if (nonThetaPart == 0) {
//                    expResult = randomSurdForHalfInts + "\u03B8";
//                } else {
//                    expResult = nonThetaPart + "+" + randomSurdForHalfInts + "\u03B8";
//                }
//                if (testIntegers.get(i).getRing().getRadicand() == 5) {
//                    expResult = expResult.replace("\u03B8", "\u03C6");
//                }
//                expResult = expResult.replace("+-", "-");
//            } else {
//                expResult = testIntegers.get(i).toString().replace(" ", "");
//            }
//            expResult = expResult.replace("-", "\u2212");
//            result = testIntegers.get(i).toStringAlt().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // Gotta check on 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toStringAlt();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toStringAlt();
//        assertEquals(expResult, result);
//        // Lastly the special case of the golden ratio
//        expResult = "\u03C6";
//        result = GOLDEN_RATIO.toStringAlt();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        fail("REWRITE THIS TEST");
        String expResult, result;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = randomRegForHalfInts + "/2+" + randomSurdForHalfInts + "sqrt(" + testIntegers.get(i).getRing().getRadicand() + ")/2";
//            } else {
//                if (randomRegPart == 0) {
//                    expResult = randomSurdPart + "sqrt(" + testIntegers.get(i).getRing().getRadicand() + ")";
//                } else {
//                    expResult = randomRegPart + "+" + randomSurdPart + "sqrt(" + testIntegers.get(i).getRing().getRadicand() + ")";
//                }
//            }
//            expResult = expResult.replace("+-", "-");
//            expResult = expResult.replace("+1sqrt", "+sqrt");
//            expResult = expResult.replace("-1sqrt", "-sqrt");
//            result = testIntegers.get(i).toASCIIString().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // And last but not least, 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toASCIIString();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toASCIIString();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToASCIIStringAlt() {
        System.out.println("toASCIIStringAlt");
        fail("REWRITE THIS TEST");
        String expResult, result;
        int nonThetaPart;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                nonThetaPart = (randomRegForHalfInts - randomSurdForHalfInts)/2;
//                if (nonThetaPart == 0) {
//                    expResult = randomSurdForHalfInts + "theta";
//                } else {
//                    expResult = nonThetaPart + "+" + randomSurdForHalfInts + "theta";
//                }
//                if (testIntegers.get(i).getRing().getRadicand() == 5) {
//                    expResult = expResult.replace("theta", "phi");
//                }
//                expResult = expResult.replace("+-", "-");
//            } else {
//                expResult = testIntegers.get(i).toASCIIString().replace(" ", "");
//            }
//            result = testIntegers.get(i).toASCIIStringAlt().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // Gotta check on 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toASCIIStringAlt();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toASCIIStringAlt();
//        assertEquals(expResult, result);
//        // Lastly the special case of the golden ratio
//        expResult = "phi";
//        result = GOLDEN_RATIO.toASCIIStringAlt();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXString method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        fail("REWRITE THIS TEST");
        String expResult, result;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = "\\frac{" + randomRegForHalfInts + "}{2}+\\frac{" + randomSurdForHalfInts + "\\sqrt{" + testIntegers.get(i).getRing().getRadicand() + "}}{2}";
//            } else {
//                if (randomRegPart == 0) {
//                    expResult = randomSurdPart + "\\sqrt{" + testIntegers.get(i).getRing().getRadicand() + "}";
//                } else {
//                    expResult = randomRegPart + "+" + randomSurdPart + "\\sqrt{" + testIntegers.get(i).getRing().getRadicand() + "}";
//                }
//            }
//            expResult = expResult.replace("+1\\sqrt", "+\\sqrt");
//            expResult = expResult.replace("-1\\sqrt", "-\\sqrt");
//            expResult = expResult.replace("\\frac{-", "-\\frac{");
//            expResult = expResult.replace("+-", "-");
//            result = testIntegers.get(i).toTeXString().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // Last but not least, 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toTeXString();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toTeXString();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXStringSingleDenom method, of class RealQuadraticInteger, 
     * inherited from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToTeXStringSingleDenom() {
        System.out.println("toTeXStringSingleDenom");
        fail("REWRITE THIS TEST");
        String expResult, result;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = "\\frac{" + randomRegForHalfInts + "+" + randomSurdForHalfInts + "\\sqrt{" + testIntegers.get(i).getRing().getRadicand() + "}}{2}";
//            } else {
//                if (randomRegPart == 0) {
//                    expResult = randomSurdPart + "\\sqrt{" + testIntegers.get(i).getRing().getRadicand() + "}";
//                } else {
//                    expResult = randomRegPart + "+" + randomSurdPart + "\\sqrt{" + testIntegers.get(i).getRing().getRadicand() + "}";
//                }
//            }
//            expResult = expResult.replace("+-", "-");
//            expResult = expResult.replace("+1\\sqrt", "+\\sqrt");
//            expResult = expResult.replace("-1\\sqrt", "-\\sqrt");
//            result = testIntegers.get(i).toTeXStringSingleDenom().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // Gotta check on 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toTeXStringSingleDenom();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toTeXStringSingleDenom();
//        assertEquals(expResult, result);
//        /* This last one is to make sure that a least significant digit that is 
//           1 but is not also the most significant digit does not get erroneously 
//           chopped off */
//        RealQuadraticInteger testNum = new RealQuadraticInteger(5, 11, RING_ZPHI, 2);
//        expResult = "\\frac{5+11\\sqrt{5}}{2}";
//        result = testNum.toTeXStringSingleDenom().replace(" ", "");
//        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToTeXStringAlt() {
        System.out.println("toTeXStringAlt");
        fail("REWRITE THIS TEST");
        String expResult, result;
        int nonThetaPart;
//        for (int i = 1; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                nonThetaPart = (randomRegForHalfInts - randomSurdForHalfInts)/2;
//                if (nonThetaPart == 0) {
//                    expResult = randomSurdForHalfInts + "\\theta";
//                } else {
//                    expResult = nonThetaPart + "+" + randomSurdForHalfInts + "\\theta";
//                }
//                if (testIntegers.get(i).getRing().getRadicand() == 5) {
//                    expResult = expResult.replace("\\theta", "\\phi");
//                }
//                expResult = expResult.replace("+-", "-");
//            } else {
//                expResult = testIntegers.get(i).toTeXString().replace(" ", "");
//            }
//            result = testIntegers.get(i).toTeXStringAlt().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // Gotta check on 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toTeXStringAlt();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toTeXStringAlt();
//        assertEquals(expResult, result);
//        // Lastly the special case of the golden ratio
//        expResult = "\\phi";
//        result = GOLDEN_RATIO.toTeXStringAlt();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        fail("REWRITE THIS TEST");
        String expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = randomRegForHalfInts + "/2+" + randomSurdForHalfInts + "&radic;(" + testIntegers.get(i).getRing().getRadicand() + ")/2";
//            } else {
//                if (randomRegPart == 0) {
//                    expResult = randomSurdPart + "&radic;(" + testIntegers.get(i).getRing().getRadicand() + ")";
//                } else {
//                    expResult = randomRegPart + "+" + randomSurdPart + "&radic;(" + testIntegers.get(i).getRing().getRadicand() + ")";
//                }
//            }
//            expResult = expResult.replace("+-", "-");
//            expResult = expResult.replace("+1&radic;", "+&radic;");
//            expResult = expResult.replace("-1&radic;", "-&radic;");
//            expResult = expResult.replace("-", "&minus;");
//            result = testIntegers.get(i).toHTMLString().replace(" ", "");
//            assertEquals(expResult, result);
//        }
//        // And last but not least, 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toHTMLString();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toHTMLString();
//        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLStringAlt method, of class RealQuadraticInteger, inherited 
     * from QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testToHTMLStringAlt() {
        System.out.println("toHTMLStringAlt");
        fail("REWRITE THIS TEST");
        String expResult, result;
        RealQuadraticInteger currRQI;
        int nonThetaPart;
        for (int m = -32; m < 32; m++) {
            for (int n = -9; n < 9; n++) {
                if ((m % 2) == (n % 2)) {
                    nonThetaPart = (m - n)/2;
                    if (nonThetaPart == 0) {
                        expResult = n + "&theta;";
                    } else {
                        expResult = nonThetaPart + "+" + n + "&theta;";
                    }
                    expResult = expResult.replace("+-", "-");
                    expResult = expResult.replace("+1&theta;", "+&theta;");
                    expResult = expResult.replace("-1&theta;", "-&theta;");
                    if (expResult.equals("0&theta;")) {
                        expResult = "0";
                    }
                    if (expResult.equals("1&theta;")) {
                        expResult = "&theta;";
                    }
                    expResult = expResult.replace("+0&theta;", "");
                    expResult = expResult.replace("-", "&minus;");
//                    currRQI = new RealQuadraticInteger(m, n, RING_OQ13, 2);
//                    result = currRQI.toHTMLStringAlt().replace(" ", "");
//                    assertEquals(expResult, result);
//                    // No need to change expResult to test in ringRandomForAltTesting
//                    currRQI = new RealQuadraticInteger(m, n, ringRandomForAltTesting, 2);
//                    result = currRQI.toHTMLStringAlt().replace(" ", "");
//                    assertEquals(expResult, result);
//                    // Do need to change expResult for Z[phi]
//                    expResult = expResult.replace("&theta;", "&phi;");
//                    currRQI = new RealQuadraticInteger(m, n, RING_ZPHI, 2);
//                    result = currRQI.toHTMLStringAlt().replace(" ", "");
//                    assertEquals(expResult, result);
                }
            }
        }
        /* For integers in rings without "half-integers," we expect 
           toHTMLString() and toHTMLStringAlt() to give the same result. */
//        for (int i = 0; i < totalTestIntegers; i++) {
//            if (!testIntegers.get(i).getRing().hasHalfIntegers()) {
//                assertEquals(testIntegers.get(i).toHTMLString(), testIntegers.get(i).toHTMLStringAlt());
//            }
//        }
//        // Gotta check on the special case of the golden ratio
//        expResult = "&phi;";
//        result = GOLDEN_RATIO.toHTMLStringAlt();
//        assertEquals(expResult, result);
//        // And last but not least, 0 and 1
//        expResult = "0";
//        zeroRQI = new RealQuadraticInteger(0, 0, ringRandom);
//        result = zeroRQI.toHTMLStringAlt();
//        assertEquals(expResult, result);
//        expResult = "1";
//        oneRQI = new RealQuadraticInteger(1, 0, ringRandom);
//        result = oneRQI.toHTMLStringAlt();
//        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class RealQuadraticInteger, inherited from 
     * QuadraticInteger.
     */@org.junit.Ignore
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        fail("REWRITE THIS TEST");
        RealQuadraticInteger temporaryHold;
        int testHash, tempHash;
        int prevHash = 0;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            testHash = testIntegers.get(i).hashCode();
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                temporaryHold = new RealQuadraticInteger(randomRegForHalfInts, randomSurdForHalfInts, testIntegers.get(i).getRing(), 2);
//            } else {
//                temporaryHold = new RealQuadraticInteger(randomRegPart, randomSurdPart, testIntegers.get(i).getRing());
//            }
//            tempHash = temporaryHold.hashCode();
//            System.out.println(testIntegers.get(i).toASCIIString() + " hashed to " + testHash);
//            System.out.println(temporaryHold.toASCIIString() + " hashed to " + tempHash);
//            assertEquals(testHash, tempHash);
//            assertFalse(testHash == prevHash);
//            prevHash = testHash;
//        }
//        /* Now to test purely real integers register as equal regardless of what 
//           real quadratic ring they might be from */
//        RealQuadraticInteger altZeroRQI = new RealQuadraticInteger(0, 0, RING_Z2);
//        assertEquals(altZeroRQI, zeroRQI);
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            temporaryHold = new RealQuadraticInteger(testNormsRegParts.get(j), 0, testNorms.get(totalTestIntegers - 1).getRing());
//            tempHash = temporaryHold.hashCode();
//            testHash = testNorms.get(j).hashCode();
//            System.out.println(temporaryHold.toASCIIString() + " from " + temporaryHold.getRing().toASCIIString() + " hashed as " + tempHash);
//            System.out.println(testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " hashed as " + testHash);
//            assertEquals(tempHash, testHash);
//        }
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
        int propD = randomSquarefreeNumberOtherThan(ringA.getRadicand(), bound);
        int d = propD == 1 ? 2 : propD;
        QuadraticRing ringB = new RealQuadraticRing(d);
        QuadraticInteger diffNumber = new RealQuadraticInteger(a, b, ringB);
        String message = someNumber.toString() + " should not equal " 
                + diffNumber.toString();
        assertNotEquals(message, someNumber, diffNumber);
    }
    
    /**
     * Test of compareTo method, of class RealQuadraticInteger, implementing 
     * Comparable.
     */@org.junit.Ignore
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        fail("REWRITE THIS TEST");
        RealQuadraticRing ringZ10 = new RealQuadraticRing(10);
        RealQuadraticInteger negSeven = new RealQuadraticInteger(-7, 0, ringZ10);
        RealQuadraticInteger numNeg136plus44Sqrt10 = new RealQuadraticInteger(-136, 44, ringZ10);
        RealQuadraticInteger num117minus36Sqrt10 = new RealQuadraticInteger(117, -36, ringZ10);
        RealQuadraticInteger numSqrt10 = new RealQuadraticInteger(0, 1, ringZ10);
        int comparison = negSeven.compareTo(numNeg136plus44Sqrt10);
        String assertionMessage = negSeven.toString() + " should be found to be less than " + numNeg136plus44Sqrt10;
        assertTrue(assertionMessage, comparison < 0);
        comparison = numNeg136plus44Sqrt10.compareTo(num117minus36Sqrt10);
        assertionMessage = numNeg136plus44Sqrt10.toString() + " should be found to be less than " + num117minus36Sqrt10;
        assertTrue(assertionMessage, comparison < 0);
        comparison = num117minus36Sqrt10.compareTo(numSqrt10);
        assertionMessage = num117minus36Sqrt10.toString() + " should be found to be less than " + numSqrt10;
        assertTrue(assertionMessage, comparison < 0);
        comparison = negSeven.compareTo(negSeven);
        assertEquals(0, comparison);
        comparison = numNeg136plus44Sqrt10.compareTo(numNeg136plus44Sqrt10);
        assertEquals(0, comparison);
        comparison = num117minus36Sqrt10.compareTo(num117minus36Sqrt10);
        assertEquals(0, comparison);
        comparison = numSqrt10.compareTo(numSqrt10);
        assertEquals(0, comparison);
        comparison = numSqrt10.compareTo(num117minus36Sqrt10);
        assertionMessage = numSqrt10.toString() + " should be found to be greater than " + num117minus36Sqrt10.toString();
        assertTrue(assertionMessage, comparison > 0);
        comparison = num117minus36Sqrt10.compareTo(numNeg136plus44Sqrt10);
        assertionMessage = num117minus36Sqrt10.toString() + " should be found to be greater than " + numNeg136plus44Sqrt10.toString();
        assertTrue(assertionMessage, comparison > 0);
        comparison = numNeg136plus44Sqrt10.compareTo(negSeven);
        assertionMessage = numNeg136plus44Sqrt10.toString() + " should be found to be greater than " + negSeven.toString();
        assertTrue(assertionMessage, comparison > 0);
        try {
            comparison = numSqrt10.compareTo(null);
            String failMsg = "Comparing " + numSqrt10.toString() + " to null should have caused an exception, not given result " + comparison + ".";
            fail(failMsg);
        } catch (NullPointerException npe) {
            System.out.println("Comparing " + numSqrt10.toASCIIString() + " to null correctly triggered NullPointerException.");
            System.out.println("NullPointerException had this message: \"" + npe.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception to throw for comparing " + numSqrt10.toString() + " to null.";
            fail(failMsg);
        }
    }

    /**
     * Another test of compareTo method, of class RealQuadraticInteger, 
     * implementing Comparable. This one checks that {@link 
     * Collections#sort(java.util.List)} can use compareTo to sort a list of 
     * real quadratic integers in ascending order.
     */@org.junit.Ignore
    @Test
    public void testCompareToThroughCollectionSort() {
        fail("REWRITE THIS TEST");
//        RealQuadraticRing ringZ10 = new RealQuadraticRing(10);
//        RealQuadraticInteger negSeven = new RealQuadraticInteger(-7, 0, ringZ10);
//        RealQuadraticInteger numNeg136plus44Sqrt10 = new RealQuadraticInteger(-136, 44, ringZ10);
//        RealQuadraticInteger num117minus36Sqrt10 = new RealQuadraticInteger(117, -36, ringZ10);
//        RealQuadraticInteger numSqrt10 = new RealQuadraticInteger(0, 1, ringZ10);
//        List<RealQuadraticInteger> expected = new ArrayList<>();
//        expected.add(negSeven);
//        expected.add(numNeg136plus44Sqrt10);
//        expected.add(num117minus36Sqrt10);
//        expected.add(numSqrt10);
//        List<RealQuadraticInteger> actual = new ArrayList<>();
//        actual.add(num117minus36Sqrt10);
//        actual.add(negSeven);
//        actual.add(numSqrt10);
//        actual.add(numNeg136plus44Sqrt10);
//        Collections.sort(actual);
//        assertEquals(expected, actual);
//        RealQuadraticInteger surd = new RealQuadraticInteger(0, 1, RING_OQ13);
//        expected.add(surd);
//        actual.add(surd);
//        surd = new RealQuadraticInteger(0, 1, RING_ZPHI);
//        expected.add(1, surd);
//        actual.add(surd);
//        expected.add(1, GOLDEN_RATIO);
//        actual.add(GOLDEN_RATIO);
//        surd = new RealQuadraticInteger(0, 1, RING_Z2);
//        expected.add(1, surd);
//        actual.add(surd);
//        expected.add(1, oneRQI);
//        actual.add(oneRQI);
//        Collections.sort(actual);
//        assertEquals(expected, actual);
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
     */@org.junit.Ignore
    @Test
    public void testParseQuadraticInteger() {
        System.out.println("parseQuadraticInteger");
        fail("REWRITE THIS TEST");
        String numberString;
        QuadraticInteger numberRQI;
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
    
    /**
     * Test of optional behaviors of parseImaginaryQuadraticInteger, of class 
     * RealQuadraticInteger, inherited from QuadraticInteger. This includes 
     * recognizing "j" as an alternative notation for &radic;&minus;1. If the 
     * optional behaviors are required, change the print statements under catch 
     * {@link NumberFormatException} to fails.
     */
    @org.junit.Ignore
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
        String failMessage;
        QuadraticInteger result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            failMessage = "Adding " + testIntegers.get(i).toASCIIString() + " to " + testAdditiveInverses.get(i).toASCIIString() + " should not have triggered AlgebraicDegreeOverflowException \"";
//            try {
//                result = testIntegers.get(i).plus(testAdditiveInverses.get(i));
//                assertEquals(zeroRQI, result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + adoe.getMessage() + "\"";
//                fail(failMessage);
//            }
//            result = testIntegers.get(i).plus(0);
//            assertEquals(testIntegers.get(i), result);
//        }
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
        fail("REWRITE THIS TEST");
        QuadraticInteger result;
        String failMessage;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            try {
//                result = testIntegers.get(j).minus(testIntegers.get(j + 1));
//                failMessage = "Subtracting " + testIntegers.get(j + 1).toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                System.out.println("Subtracting " + testIntegers.get(j + 1).toASCIIString() + " from " + testIntegers.get(j).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
//            }
//            failMessage = "Subtracting " + testIntegers.get(j + 1).toASCIIString() + " from " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " should not have triggered";
//            try {
//                result = testNorms.get(j).minus(testIntegers.get(j + 1));
//                System.out.println(testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " minus " + testIntegers.get(j + 1).toASCIIString() + " is " + result.toASCIIString());
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
    public void testConstructorNullRing() {
        int a = 43;
        int b = -577;
        try {
            RealQuadraticInteger badQuadInt = new RealQuadraticInteger(a, b, 
                    null, 2);
            String msg = "Should not have been able to create " 
                    + badQuadInt.toString() + " with null ring";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to use null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            if (excMsg == null) {
                fail("Exception message should not be null");
            } else {
                System.out.println("\"" + excMsg + "\"");
                String expected = "ring";
                String msg = "Exception message contains the word \"" + expected 
                        + "\"";
                assert excMsg.toLowerCase().contains(expected) : msg;
            }
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use null ring";
            fail(msg);
        }
    }
    
}
