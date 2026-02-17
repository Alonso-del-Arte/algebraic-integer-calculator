/*
 * Copyright (C) 2026 Alonso del Arte
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
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator.randomPowerOfTwo;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberOtherThan;
import java.math.BigInteger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests for the ImaginaryQuadraticInteger class.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticIntegerTest {
    
    private static final char MINUS_SIGN_CHAR = '\u2212';
    
    private static final String MINUS_SIGN 
            = Character.toString(MINUS_SIGN_CHAR);
    
    private static final char SQRT_SYMBOL = '\u221A';
    
    private static final char OMEGA_CHAR = '\u03C9';
    
    private static final char THETA_CHAR = '\u03B8';
    
    private static final String OMEGA_WORD = "omega";
    
    private static final String THETA_WORD = "theta";
    
    private static final String NEGATIVE_OMEGA_WORD_ASCII = "-omega";
    
    /**
     * The ring of Gaussian integers, <b>Z</b>[<i>i</i>], numbers of the form 
     * <i>a</i> + <i>bi</i>. This is one of the rings in which 
     * ImaginaryQuadraticInteger will be tested.
     */
    private static final ImaginaryQuadraticRing RING_GAUSSIAN 
            = new ImaginaryQuadraticRing(-1);
    
    /**
     * The ring of Eisenstein integers, <b>Z</b>[&omega;], numbers of the form 
     * <i>a</i> + <i>b</i>&omega;, where &omega; = -1/2 + (&radic;-3)/2. This is 
     * one of the rings in which ImaginaryQuadraticInteger will be tested.
     */
    private static final ImaginaryQuadraticRing RING_EISENSTEIN 
            = new ImaginaryQuadraticRing(-3);
    
    private static ImaginaryQuadraticRing chooseRing() {
        int d = -randomSquarefreeNumber(256);
        return new ImaginaryQuadraticRing(d);
    }
    
    private static ImaginaryQuadraticRing chooseRingDOtherThan(int avoidedD) {
        int d = avoidedD;
        while (d == avoidedD) {
            d = -randomSquarefreeNumber(256);
        }
        return new ImaginaryQuadraticRing(d);
    }
    
    private static ImaginaryQuadraticRing chooseRingWithHalfInts() {
        int d = -randomSquarefreeNumberMod(3, 4);
        return new ImaginaryQuadraticRing(d);
    }
    
    private static ImaginaryQuadraticRing chooseRingWHalfIntsNotEisenstein() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        return new ImaginaryQuadraticRing(d);
    }
    
    @Test
    public void testToStringPurelyRealPositive() {
        int a = randomNumber(Short.MAX_VALUE);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a);
        String actual = number.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringPurelyRealNegative() {
        int a = randomNumber(Short.MAX_VALUE) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, 0, ring);
        String expected = MINUS_SIGN + a;
        String actual = number.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussian() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = randomNumber(bound) | powerOfTwo;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = a + "+" + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianPurelyImaginaryPositive() {
        int bound = Short.MAX_VALUE;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, 
                RING_GAUSSIAN);
        String expected = b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianPurelyImaginaryNegative() {
        int bound = Short.MAX_VALUE;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, -b, 
                RING_GAUSSIAN);
        String expected = MINUS_SIGN + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianNegativeRealPositiveImaginary() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = randomNumber(bound) | powerOfTwo;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, b, 
                RING_GAUSSIAN);
        String expected = MINUS_SIGN + a + "+" + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianNegativeRealNegativeImaginary() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = randomNumber(bound) | powerOfTwo;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -b, 
                RING_GAUSSIAN);
        String expected = MINUS_SIGN + a + MINUS_SIGN + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianPositiveRealNegativeImaginary() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = randomNumber(bound) | powerOfTwo;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -b, 
                RING_GAUSSIAN);
        String expected = a + MINUS_SIGN + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianNoOmitLeastSignificantZeroInRealPart() {
        int bound = 4096;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = a + "+" + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianNoOmitLeastSignifZeroInRealPartNegImag() {
        int bound = 4096;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -b, 
                RING_GAUSSIAN);
        String expected = Integer.toString(a) + MINUS_SIGN + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianNoOmitLeastSignificantZeroInNegRealPart() {
        int bound = 4096;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, b, 
                RING_GAUSSIAN);
        String expected = MINUS_SIGN + a + "+" + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringGaussianNoOmitLeastSignif0InNegRealPartNegImag() {
        int bound = 4096;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -b, 
                RING_GAUSSIAN);
        String expected = MINUS_SIGN + a + MINUS_SIGN + b + "i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringImaginaryUnit() {
        QuadraticInteger imagUnit = new ImaginaryQuadraticInteger(0, 1, 
                RING_GAUSSIAN);
        String expected = "i";
        String actual = imagUnit.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegativeImaginaryUnit() {
        QuadraticInteger imagUnit = new ImaginaryQuadraticInteger(0, -1, 
                RING_GAUSSIAN);
        String expected = MINUS_SIGN + "i";
        String actual = imagUnit.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNonZeroRealPlusOneImag() {
        int shift = randomNumber(8);
        int powerOfTwo = 1 << shift;
        int a = randomNumber(256) | powerOfTwo;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 1, 
                RING_GAUSSIAN);
        String expected = a + "+i";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNonZeroRealMinusOneImag() {
        int shift = randomNumber(8);
        int powerOfTwo = 1 << shift;
        int a = randomNumber(256) | powerOfTwo;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -1, 
                RING_GAUSSIAN);
        String expected = a + MINUS_SIGN + 'i';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toString function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegativeRe() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, b, ring);
        String expected = MINUS_SIGN + a + "+" + b + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringNegativeReNegativeIm() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -b, ring);
        String expected = MINUS_SIGN + a + MINUS_SIGN + b + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringPositiveReNegativeIm() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -b, ring);
        String expected = a + MINUS_SIGN + b + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringZeroRePositiveIm() {
        int bound = 8192;
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, ring);
        String expected = Integer.toString(b) + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringZeroReNegativeIm() {
        int bound = 8192;
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, -b, ring);
        String expected = MINUS_SIGN + b + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringPositiveReMultipleOfTen() {
        int bound = 8192;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegativeReMultipleOfTen() {
        int bound = 8192;
        int a = 10 * RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, b, ring);
        String expected = MINUS_SIGN + a + "+" + b + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringZeroRePlusOneOfRoot() {
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 1, ring);
        String expected = "" + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringZeroReMinusOneOfRoot() {
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, -1, ring);
        String expected = MINUS_SIGN + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + Math.abs(ring.getRadicand()) + ')';
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringHalfIntPositiveRePositiveIm() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        String expected = a + "/2+" + b + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntPositiveReNegativeIm() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -b, ring, 2);
        String expected = a + "/2" + MINUS_SIGN + b + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeRePositiveIm() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, b, ring, 2);
        String expected = MINUS_SIGN + a + "/2+" + b + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeReNegativeIm() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 3;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -b, ring, 
                2);
        String expected = MINUS_SIGN + a + "/2" + MINUS_SIGN + b + SQRT_SYMBOL 
                + '(' + MINUS_SIGN + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntPositiveRePlusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 1, ring, 2);
        String expected = a + "/2+" + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntPositiveReMinusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -1, ring, 2);
        String expected = a + "/2" + MINUS_SIGN + SQRT_SYMBOL + '(' + MINUS_SIGN 
                + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeRePlusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, 1, ring, 2);
        String expected = MINUS_SIGN + a + "/2+" + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringHalfIntNegativeReMinusHalfOfRoot() {
        int bound = 256;
        int a = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -1, ring, 
                2);
        String expected = MINUS_SIGN + a + "/2" + MINUS_SIGN + SQRT_SYMBOL + '(' 
                + MINUS_SIGN + ring.getAbsNegRad() + ')' + "/2";
        String actual = number.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringAltPurelyRealGaussian() {
        int a = randomNumber();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, 
                RING_GAUSSIAN);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + RING_GAUSSIAN.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPurelyRealFromRingD2Mod4SameAsToString() {
        int a = randomNumber();
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPurelyRealFromRingD3Mod4SameAsToString() {
        int a = randomNumber();
        int d = -randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltGaussianSameAsToString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltD2Mod4SameAsToString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
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
        int d = -randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltOmega() {
        QuadraticInteger omega = new ImaginaryQuadraticInteger(-1, 1, 
                RING_EISENSTEIN, 2);
        String expected = "" + OMEGA_CHAR;
        String actual = omega.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + omega.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveOddMultipleOfOmega() {
        int b = RANDOM.nextInt(2, 8192) | 1;
        int a = -b;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = Integer.toString(b) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number + " in terms of " + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltPositiveEvenMultipleOfOmega() {
        int b = RANDOM.nextInt(2, 8192) << 1;
        int a = -b;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = Integer.toString(b) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number + " in terms of " + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeOddMultipleOfOmega() {
        int a = RANDOM.nextInt(2, 8192) | 1;
        int b = -a;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + Integer.toString(a) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number + " in terms of " + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeEvenMultipleOfOmega() {
        int a = RANDOM.nextInt(2, 8192) << 1;
        int b = -a;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + Integer.toString(a) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number + " in terms of " + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeOmega() {
        QuadraticInteger omega = new ImaginaryQuadraticInteger(1, -1, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + OMEGA_CHAR;
        String actual = omega.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + omega.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPurelyRealPositiveEisensteinContext() {
        int a = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, 
                RING_EISENSTEIN);
        String expected = Integer.toString(a);
        String actual = number.toStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringAltZeroEisensteinContext() {
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 0, 
                RING_EISENSTEIN);
        String expected = "0";
        String actual = number.toStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringAltPurelyRealNegativeEisensteinContext() {
        int a = randomNumber() | Integer.MIN_VALUE;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, 
                RING_EISENSTEIN);
        String expected = MINUS_SIGN + Integer.toString(-a);
        String actual = number.toStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusPositiveOddMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = RANDOM.nextInt(2, bound) | 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + omegaPart + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusPositiveEvenMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = RANDOM.nextInt(2, bound) << 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + omegaPart + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusNegativeOddMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = -RANDOM.nextInt(2, bound) | 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + MINUS_SIGN + (-omegaPart) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusNegativeEvenMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = -RANDOM.nextInt(2, bound) << 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + MINUS_SIGN + (-omegaPart) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusPositiveOddMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = RANDOM.nextInt(2, bound) | 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + (-nonOmegaPart) + "+" + omegaPart 
                + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusPositiveEvenMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = RANDOM.nextInt(2, bound) << 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + (-nonOmegaPart) + "+" + omegaPart 
                + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusNegativeOddMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = -RANDOM.nextInt(2, bound) | 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + (-nonOmegaPart) + MINUS_SIGN 
                + (-omegaPart) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusNegativeEvenMultipleOfOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = -RANDOM.nextInt(2, bound) << 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + (-nonOmegaPart) + MINUS_SIGN 
                + (-omegaPart) + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveIntPlusNegativeOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = -1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + MINUS_SIGN + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + (-nonOmegaPart) + "+" + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeIntPlusNegativeOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = -1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = MINUS_SIGN + (-nonOmegaPart) + MINUS_SIGN 
                + OMEGA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPurelyRealD1Mod4ContextNotEisenstein() {
        int a = randomNumber();
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "");
        String actual = number.toStringAlt();
        String message = "Purely real number in the context of " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger theta = new ImaginaryQuadraticInteger(1, 1, ring, 2);
        String expected = "" + THETA_CHAR;
        String actual = theta.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + theta.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltNegativeTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger theta = new ImaginaryQuadraticInteger(-1, -1, ring, 2);
        String expected = MINUS_SIGN + THETA_CHAR;
        String actual = theta.toStringAlt().replace(" ", "");
        String message = "toStringAlt() for " + theta.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToStringAltPositiveOddMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int a = RANDOM.nextInt(2, 8192) | 1;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, a, ring, 2);
        String expected = Integer.toString(a) + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltPositiveEvenMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int i = RANDOM.nextInt(2, 8192) & (-2);
        int a = i / 2;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, a, ring);
        String expected = Integer.toString(i) + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeOddMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int a = RANDOM.nextInt(2, 8192) | 1;
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -a, ring, 2);
        String expected = MINUS_SIGN + a + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringAltNegativeEvenMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int i = RANDOM.nextInt(2, 8192) & (-2);
        int a = i / 2;
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -a, ring);
        String expected = MINUS_SIGN + i + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the toStringAlt function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testToStringAlt() {
        System.out.println("toStringAlt");
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
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
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = MINUS_SIGN + (-nonThetaPart) + MINUS_SIGN 
                + THETA_CHAR;
        String actual = number.toStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringPurelyRealPositive() {
        int a = randomNumber(Short.MAX_VALUE);
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a);
        String actual = number.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringPurelyRealNegative() {
        int a = -randomNumber(Short.MAX_VALUE) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a);
        String actual = number.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringGaussian() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = randomNumber(bound) | powerOfTwo;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = a + "+" + b + "i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringGaussianNegativeRealPositiveImaginary() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = -(randomNumber(bound) | powerOfTwo);
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = a + "+" + b + "i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringGaussianNegativeRealNegativeImaginary() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = -(randomNumber(bound) | powerOfTwo);
        int b = -RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = Integer.toString(a) + b + "i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringGaussianPositiveRealNegativeImaginary() {
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = Short.MAX_VALUE;
        int a = randomNumber(bound) | powerOfTwo;
        int b = -RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = Integer.toString(a) + b + "i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringGaussianPurelyImaginaryPositive() {
        int bound = Short.MAX_VALUE;
        int b = RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, 
                RING_GAUSSIAN);
        String expected = b + "i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringGaussianPurelyImaginaryNegative() {
        int bound = Short.MAX_VALUE;
        int b = -RANDOM.nextInt(2, bound);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, 
                RING_GAUSSIAN);
        String expected = b + "i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringImaginaryUnit() {
        QuadraticInteger imagUnit = new ImaginaryQuadraticInteger(0, 1, 
                RING_GAUSSIAN);
        String expected = "i";
        String actual = imagUnit.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringNegativeImaginaryUnit() {
        QuadraticInteger imagUnit = new ImaginaryQuadraticInteger(0, -1, 
                RING_GAUSSIAN);
        String expected = "-i";
        String actual = imagUnit.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringPositiveRealPlusOneImag() {
        int shift = randomNumber(8);
        int powerOfTwo = 1 << shift;
        int a = randomNumber(256) | powerOfTwo;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 1, 
                RING_GAUSSIAN);
        String expected = a + "+i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringPositiveRealMinusOneImag() {
        int shift = randomNumber(8);
        int powerOfTwo = 1 << shift;
        int a = randomNumber(256) | powerOfTwo;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -1, 
                RING_GAUSSIAN);
        String expected = a + "-i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringNegativeRealPlusOneImag() {
        int a = randomNumber() | Integer.MIN_VALUE;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 1, 
                RING_GAUSSIAN);
        String expected = a + "+i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringNegativeRealMinusOneImag() {
        int a = randomNumber() | Integer.MIN_VALUE;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, -1, 
                RING_GAUSSIAN);
        String expected = a + "-i";
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toASCIIString function, of the ImaginaryQuadraticInteger 
     * class.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + "sqrt(" + ring.getRadicand() + ')';
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToASCIIStringNegativeRe() {
        int bound = 8192;
        int a = -RANDOM.nextInt(1, bound);
        int b = RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = a + "+" + b + "sqrt(" + ring.getRadicand() + ')';
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToASCIIStringNegativeReNegativeIm() {
        int bound = 8192;
        int a = -RANDOM.nextInt(1, bound);
        int b = -RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = Integer.toString(a) + b + "sqrt(" + ring.getRadicand() 
                + ')';
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void testToASCIIStringPositiveReNegativeIm() {
        int bound = 8192;
        int a = RANDOM.nextInt(1, bound);
        int b = -RANDOM.nextInt(2, bound);
        QuadraticRing ring = chooseRingDOtherThan(-1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = Integer.toString(a) + b + "sqrt(" + ring.getRadicand() 
                + ')';
        String actual = number.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPurelyRealGaussian() {
        int a = randomNumber();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, 
                RING_GAUSSIAN);
        String expected = number.toString().replace(" ", "")
                .replace(MINUS_SIGN, "-");
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + RING_GAUSSIAN.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPurelyRealFromRingD2Mod4SameAsToString() {
        int a = randomNumber();
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "")
                .replace(MINUS_SIGN, "-");
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPurelyRealFromRingD3Mod4SameAsToString() {
        int a = randomNumber();
        int d = -randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = number.toString().replace(" ", "")
                .replace(MINUS_SIGN, "-");
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltGaussianSameAsToString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_GAUSSIAN);
        String expected = number.toString().replace(" ", "")
                .replace(MINUS_SIGN, "-");
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toASCIIStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltD2Mod4SameAsToString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = number.toASCIIString().replace(" ", "");
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toASCIIStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltD3Mod4SameAsToASCIIString() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        int d = -randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        String expected = number.toASCIIString().replace(" ", "");
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Given " + expected 
                + ", toASCIIStringAlt() should give the same result";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltOmega() {
        QuadraticInteger omega = new ImaginaryQuadraticInteger(-1, 1, 
                RING_EISENSTEIN, 2);
        String expected = OMEGA_WORD;
        String actual = omega.toASCIIStringAlt().replace(" ", "");
        String message = "toASCIIStringAlt() for " + omega.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltPositiveMultipleOfOmega() {
        fail("ASSESS FOR BRITTLENESS");
        int b = RANDOM.nextInt(2, 8192);
        int a = -b;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = Integer.toString(b) + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number + " in terms of " + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeOddMultipleOfOmega() {
        int a = 2 * RANDOM.nextInt(2, 8192) + 1;
        int b = -a;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = "-" + Integer.toString(a) + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeEvenMultipleOfOmega() {
        int a = RANDOM.nextInt(2, 8192) << 1;
        int b = -a;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
                RING_EISENSTEIN, 2);
        String expected = "-" + Integer.toString(a) + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + OMEGA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeOmega() {
        QuadraticInteger negOmega = new ImaginaryQuadraticInteger(1, -1, 
                RING_EISENSTEIN, 2);
        String expected = NEGATIVE_OMEGA_WORD_ASCII;
        String actual = negOmega.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation for " + negOmega.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPurelyRealPositiveEisensteinContext() {
        int a = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, 
                RING_EISENSTEIN);
        String expected = Integer.toString(a);
        String actual = number.toASCIIStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltZeroEisensteinContext() {
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 0, 
                RING_EISENSTEIN);
        String expected = "0";
        String actual = number.toASCIIStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPurelyRealNegativeEisensteinContext() {
        int a = randomNumber() | Integer.MIN_VALUE;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, 
                RING_EISENSTEIN);
        String expected = Integer.toString(a);
        String actual = number.toASCIIStringAlt().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPositiveIntPlusPositiveMultipleOfOmega() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(2, bound);
        int omegaPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + omegaPart + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPositiveIntPlusNegativeMultipleOfOmega() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(2, bound);
        int omegaPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = Integer.toString(nonOmegaPart) + omegaPart 
                + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeIntPlusPositiveMultipleOfOmega() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(2, bound);
        int omegaPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + omegaPart + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeIntPlusNegativeMultipleOfOmega() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(2, bound);
        int omegaPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = Integer.toString(nonOmegaPart) + omegaPart 
                + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPositiveIntPlusOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPositiveIntPlusNegativeOmega() {
        int bound = 128;
        int nonOmegaPart = RANDOM.nextInt(1, bound);
        int omegaPart = -1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + NEGATIVE_OMEGA_WORD_ASCII;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeIntPlusOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound);
        int omegaPart = 1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + "+" + OMEGA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeOddIntPlusNegativeOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound) | 1;
        int omegaPart = -1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + NEGATIVE_OMEGA_WORD_ASCII;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeEvenIntPlusNegativeOmega() {
        int bound = 128;
        int nonOmegaPart = -RANDOM.nextInt(1, bound) & -2;
        int omegaPart = -1;
        int a = 2 * nonOmegaPart - omegaPart;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, omegaPart, 
                RING_EISENSTEIN, 2);
        String expected = nonOmegaPart + NEGATIVE_OMEGA_WORD_ASCII;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning omega notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPurelyRealD1Mod4ContextNotEisenstein() {
        int a = randomNumber(32768) - 16384;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        String expected = Integer.toString(a).replace('-', MINUS_SIGN_CHAR);
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Purely real number in the context of " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger theta = new ImaginaryQuadraticInteger(1, 1, ring, 2);
        String expected = THETA_WORD;
        String actual = theta.toASCIIStringAlt().replace(" ", "");
        String message = "toASCIIStringAlt() for " + theta.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger theta = new ImaginaryQuadraticInteger(-1, -1, ring, 2);
        String expected = "-theta";
        String actual = theta.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation for " + theta.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPositiveOddMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int a = RANDOM.nextInt(2, 8192) | 1;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, a, ring, 2);
        String expected = Integer.toString(a) + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltPositiveEvenMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int i = RANDOM.nextInt(2, 8192) & (-2);
        int a = i / 2;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, a, ring);
        String expected = Integer.toString(a) + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeOddMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int a = RANDOM.nextInt(2, 8192) | 1;
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -a, ring, 2);
        String expected = Integer.toString(-a) + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeEvenMultipleOfTheta() {
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        int i = RANDOM.nextInt(2, 8192) & (-2);
        int a = i / 2;
        QuadraticInteger number = new ImaginaryQuadraticInteger(-a, -a, ring);
        String expected = Integer.toString(-a) + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Writing " + number.toString() + " in terms of " 
                + THETA_CHAR;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltPositiveIntPlusNegativeMultipleOfTheta() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "-" + (-thetaPart) + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeIntPlusPositiveMultipleOfTheta() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "+" + thetaPart + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltNegativeIntPlusNegativeMultipleOfTheta() {
        fail("ASSESS FOR BRITTLENESS");
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = -RANDOM.nextInt(2, bound);
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "" + thetaPart + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToASCIIStringAltPositiveIntPlusTheta() {
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = 1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "+" + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltPositiveIntPlusNegativeTheta() {
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = -1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "-" + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeIntPlusTheta() {
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = 1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "+" + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testToASCIIStringAltNegativeIntPlusNegativeTheta() {
        int bound = 128;
        int nonThetaPart = -RANDOM.nextInt(1, bound);
        int thetaPart = -1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "-" + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the toASCIIStringAlt function, of the ImaginaryQuadraticInteger 
     * class.
     */
    @Test
    public void testToASCIIStringAlt() {
        fail("ASSESS FOR BRITTLENESS");
        System.out.println("toASCIIStringAlt");
        int bound = 128;
        int nonThetaPart = RANDOM.nextInt(1, bound);
        int thetaPart = RANDOM.nextInt(2, bound) | 1;
        int a = 2 * nonThetaPart + thetaPart;
        QuadraticRing ring = chooseRingWHalfIntsNotEisenstein();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, thetaPart, 
                ring, 2);
        String expected = nonThetaPart + "+" + thetaPart + THETA_WORD;
        String actual = number.toASCIIStringAlt().replace(" ", "");
        String message = "Reckoning theta notation of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of toTeXString method, of class ImaginaryQuadraticInteger. For 
     * methods that return Strings, spaces are desirable but not required.
     * Therefore the tests should strip out spaces before asserting equality.
     */
//    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        fail("REWRITE THIS TEST");
    }
    
    /**
     * Test of toTeXStringSingleDenom method, of class ImaginaryQuadraticInteger. For 
     * methods that return Strings, spaces are desirable but not required.
     * Therefore the tests should strip out spaces before asserting equality.
     */
//    @Test
    public void testToTeXStringSingleDenom() {
        System.out.println("toTeXStringSingleDenom");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toTeXStringAlt method, of class ImaginaryQuadraticInteger. For 
     * methods that return Strings, spaces are desirable but not required.
     * Therefore the tests should strip out spaces before asserting equality.
     * If the test of the toTeXString method fails, the result of this test is 
     * irrelevant.
     */
//    @Test
    public void testToTeXStringAlt() {
        System.out.println("toTeXStringAlt");
        fail("REWRITE THIS TEST");
    }
    
    /**
     * Test of toHTMLString method, of class ImaginaryQuadraticInteger. For 
     * methods that return Strings, spaces are desirable but not required.
     * Therefore the tests should strip out spaces before asserting equality.
     */
//    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of toHTMLStringAlt method, of class ImaginaryQuadraticInteger. For 
     * methods that return Strings, spaces are desirable but not required.
     * Therefore the tests should strip out spaces before asserting equality.
     * If the test of the toHTMLString method fails, the result of this test is 
     * irrelevant.
     */
//    @Test
    public void testToHTMLStringAlt() {
        System.out.println("toHTMLStringAlt");
        fail("REWRITE THIS TEST");
    }
    
    /**
     * Test of the algebraicDegree function, of the ImaginaryQuadraticInteger  
     * class, inherited from {@link QuadraticInteger}. Quadratic integers with 
     * nonzero imaginary part should have algebraic degree 2.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        int d = -randomSquarefreeNumber(1024);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(d);
        int a = randomNumber();
        int b = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        int expected = 2;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testAnglePurelyRealNegative() {
        int a = -randomNumber(8192) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        double expected = Math.PI;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAnglePurelyRealPositive() {
        int a = randomNumber(8192) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        double expected = 0.0;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 0, ring);
        double expected = 0.0;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAnglePurelyImaginaryNegative() {
        int b = -randomNumber(8192) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, ring);
        double expected = -Math.PI / 2;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAnglePurelyImaginaryPositive() {
        int b = randomNumber(8192) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, ring);
        double expected = Math.PI / 2;
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of the angle function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        int bound = 16384;
        int a = RANDOM.nextInt(bound) + 1;
        int b = RANDOM.nextInt(bound) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        double y = ring.getAbsNegRadSqrt() * b;
        double expected = Math.atan2(y, a);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAnglePositiveReNegativeIm() {
        int bound = 16384;
        int a = RANDOM.nextInt(bound) + 1;
        int b = -RANDOM.nextInt(bound) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        double y = ring.getAbsNegRadSqrt() * b;
        double expected = Math.atan2(y, a);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleNegativeReNegativeIm() {
        int bound = 16384;
        int a = -RANDOM.nextInt(bound) - 1;
        int b = -RANDOM.nextInt(bound) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        double y = ring.getAbsNegRadSqrt() * b;
        double expected = Math.atan2(y, a);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleNegativeRePositiveIm() {
        int bound = 16384;
        int a = -RANDOM.nextInt(bound) - 1;
        int b = RANDOM.nextInt(bound) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        double y = ring.getAbsNegRadSqrt() * b;
        double expected = Math.atan2(y, a);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleHalfIntsPositiveRePositiveIm() {
        int bound = 8192;
        int a = 2 * RANDOM.nextInt(bound) + 1;
        int b = 2 * RANDOM.nextInt(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        double y = ring.getAbsNegRadSqrt() * b / 2;
        double x = 0.5 * a;
        double expected = Math.atan2(y, x);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleHalfIntsPositiveReNegativeIm() {
        int bound = 8192;
        int a = 2 * RANDOM.nextInt(bound) + 1;
        int b = -2 * RANDOM.nextInt(bound) - 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        double y = ring.getAbsNegRadSqrt() * b / 2;
        double x = 0.5 * a;
        double expected = Math.atan2(y, x);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleHalfIntsNegativeReNegativeIm() {
        int bound = 8192;
        int a = -2 * RANDOM.nextInt(bound) - 1;
        int b = -2 * RANDOM.nextInt(bound) - 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        double y = ring.getAbsNegRadSqrt() * b / 2;
        double x = 0.5 * a;
        double expected = Math.atan2(y, x);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAngleHalfIntsNegativeRePositiveIm() {
        int bound = 8192;
        int a = -2 * RANDOM.nextInt(bound) - 1;
        int b = 2 * RANDOM.nextInt(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        double y = ring.getAbsNegRadSqrt() * b / 2;
        double x = 0.5 * a;
        double expected = Math.atan2(y, x);
        double actual = number.angle();
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Another test of the algebraicDegree function, of the 
     * ImaginaryQuadraticInteger inherited from {@link QuadraticInteger}. Purely 
     * real nonzero integers should have algebraic degree 1.
     */
    @Test
    public void testAlgebraicDegreeOrdinaryIntegers() {
        int d = -randomSquarefreeNumber(1024);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(d);
        int a = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        int expected = 1;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Another test of the algebraicDegree function, of the 
     * ImaginaryQuadraticInteger class, inherited from {@link QuadraticInteger}. 
     * Zero should have algebraic degree 0 regardless of which ring it comes 
     * from.
     */
    @Test
    public void testAlgebraicDegreeZero() {
        int d = -randomSquarefreeNumber(1024);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 0, ring);
        int expected = 0;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString() 
                + " from " + ring.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the trace function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
        QuadraticRing ring = chooseRing();
        int bound = Integer.MIN_VALUE / -8;
        int a = randomNumber(bound);
        int b = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        long expected = 2L * a;
        long actual = number.trace();
        String message = "Reckoning trace of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testTraceHalfInteger() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int bound = 1 << 24;
        int signAdjust = RANDOM.nextBoolean() ? -1 : 1;
        int expected = signAdjust * 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber() + 1;
        QuadraticInteger number = new ImaginaryQuadraticInteger(expected, b, 
                ring, 2);
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
        QuadraticInteger num = new ImaginaryQuadraticInteger(a, b, ring);
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
        QuadraticInteger num = new ImaginaryQuadraticInteger(a, b, ring);
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
        QuadraticInteger instance = new ImaginaryQuadraticInteger(a, b, ring);
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
        QuadraticInteger instance = new ImaginaryQuadraticInteger(a, b, ring, 
                2);
        BigInteger expected = BigInteger.valueOf(a);
        BigInteger actual = instance.fullTrace();
        String message = "Reckoning trace of " + instance.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormOfZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 0, ring);
        long expected = 0L;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormOfPurelyReal() {
        int propA = randomNumber();
        int a = propA == 0 ? 1 : propA;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        long expected = (long) a * (long) a;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormOfPurelyImaginary() {
        int propB = randomNumber();
        int b = propB == 0 ? 1 : propB;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, ring);
        long expected = (long) b * (long) b * (long) ring.getAbsNegRad();
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the norm function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        int bound = 2 * Short.MAX_VALUE;
        int propA = randomNumber(bound);
        int a = propA == 0 ? 1 : propA;
        int propB = randomNumber(bound);
        int b = propB == 0 ? 1 : propB;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        long expected = (long) a * (long) a + (long) b * (long) b 
                * (long) ring.getAbsNegRad();
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNormHalfInt() {
        int bound = -2 * Byte.MIN_VALUE;
        int a = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        long expected = ((long) a * (long) a + (long) b * (long) b 
                * (long) ring.getAbsNegRad()) / 4L;
        long actual = number.norm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testFullNormSameAs32BitNorm() {
        QuadraticRing ring = chooseRing();
        int d = ring.getRadicand();
        int bound = 256;
        int halfBound = bound / 2;
        int a = randomNumber(bound) - halfBound;
        int b = randomNumber(bound) - halfBound;
        System.out.println("For d = " + d + ", choosing b = " + b);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        BigInteger expected = BigInteger.valueOf(number.norm());
        BigInteger actual = number.fullNorm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testFullNorm() {
        System.out.println("fullNorm");
        QuadraticRing ring = chooseRing();
        int d = ring.getRadicand();
        int powerOfTwo = 1 << 30;
        int a = randomNumber() | powerOfTwo;
        int b = randomNumber() | powerOfTwo;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        BigInteger wrappedA = BigInteger.valueOf(a);
        BigInteger wrappedB = BigInteger.valueOf(b);
        BigInteger wrappedD = BigInteger.valueOf(d);
        BigInteger aSquared = wrappedA.multiply(wrappedA);
        BigInteger bSquared = wrappedB.multiply(wrappedB);
        BigInteger bSquaredTimesD = bSquared.multiply(wrappedD.abs());
        BigInteger expected = aSquared.add(bSquaredTimesD);
        BigInteger actual = number.fullNorm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testFullNormHalfInt() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int d = ring.getRadicand();
        int bound = -256 * d;
        int a = randomNumber(bound) | 1;
        int b = randomNumber(bound) | 1;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        BigInteger wrappedA = BigInteger.valueOf(a);
        BigInteger wrappedB = BigInteger.valueOf(b);
        BigInteger wrappedD = BigInteger.valueOf(d);
        BigInteger aSquared = wrappedA.multiply(wrappedA);
        BigInteger bSquared = wrappedB.multiply(wrappedB);
        BigInteger bSquaredTimesD = bSquared.multiply(wrappedD.abs());
        BigInteger four = BigInteger.valueOf(4);
        BigInteger expected = aSquared.add(bSquaredTimesD).divide(four);
        BigInteger actual = number.fullNorm();
        String message = "Reckoning norm of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testMinPolynomialCoeffsForZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger zero = new ImaginaryQuadraticInteger(0, 0, ring);
        long[] expecteds = {0L, 1L, 0L};
        long[] actuals = zero.minPolynomialCoeffs();
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test
    public void testMinPolynomialCoeffsForUnaryInteger() {
        QuadraticRing ring = chooseRing();
        int propA = RANDOM.nextInt(Short.MAX_VALUE) - Byte.MAX_VALUE;
        int a = propA == 0 ? 1 : propA;
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        long[] expecteds = {-a, 1L, 0L};
        long[] actuals = number.minPolynomialCoeffs();
        String message = "Reckoning minimum polynomial coefficients for " 
                + number.toString();
        assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Test of the minPolynomialCoeffs function, of the 
     * ImaginaryQuadraticInteger class.
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
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
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
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
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
        QuadraticInteger instance = new ImaginaryQuadraticInteger(a, b, ring) {
            
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
    
    @Test
    public void testMinPolynomialStringForZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger zero = new ImaginaryQuadraticInteger(0, 0, ring);
        String expected = "x";
        String actual = zero.minPolynomialString().replace(" ", "");
        String message = "Reckoning minimum polynomial text for " 
                + zero.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of minPolynomialString method, of class ImaginaryQuadraticInteger.
     * Spaces in the results are desirable but not required. Therefore the tests 
     * should strip out spaces before asserting equality. It is understood that 
     * "0x" is implied in the minimal polynomial of purely imaginary integers 
     * and therefore "+0x" and "&minus;0x" should both be excluded from the 
     * output.
     */@org.junit.Ignore
// TODO: Break this test up into smaller tests
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        fail("REWRITE THIS TEST");
//        String expected, actual;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expected = "x\u00B2";
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                if (randomRealForHalfInts < 0) {
//                    expected = expected + "+" + ((-1) * randomRealForHalfInts);
//                } else {
//                    expected = expected + MINUS_SIGN + randomRealForHalfInts;
//                }
//                expected = expected + "x+" + ((randomRealForHalfInts 
//                        * randomRealForHalfInts + randomImagForHalfInts 
//                        * randomImagForHalfInts 
//                        * testIntegers.get(i).getRing().getAbsNegRad()) / 4);
//            } else {
//                if (randomRealPart < 0) {
//                    expected = expected + "+" + ((-2) * randomRealPart);
//                } else {
//                    expected = expected + MINUS_SIGN + (2 * randomRealPart);
//                }
//                expected = expected + "x+" + (randomRealPart * randomRealPart 
//                        + randomImagPart * randomImagPart 
//                        * testIntegers.get(i).getRing().getAbsNegRad());
//            }
//            expected = expected.replace("+1x", "+x");
//            expected = expected.replace("\u22121x", "\u2212x");
//            expected = expected.replace("+0x", "");
//            expected = expected.replace("\u22120x", "");
//            actual = testIntegers.get(i).minPolynomialString().replace(" ", "");
//            assertEquals(expected, actual);
//        }
//        // Now to test the polynomial strings of a few purely real integers
//        ImaginaryQuadraticInteger degreeOneInt;
//        for (int j = 1; j < 8; j++) {
//            degreeOneInt = new ImaginaryQuadraticInteger(j, 0, ringRandom);
//            expected = "x\u2212" + j;
//            actual = degreeOneInt.minPolynomialString().replace(" ", "");
//            assertEquals(expected, actual);
//            degreeOneInt = new ImaginaryQuadraticInteger(-j, 0, ringRandom);
//            expected = "x+" + j;
//            actual = degreeOneInt.minPolynomialString().replace(" ", "");
//            assertEquals(expected, actual);
//        }
//        /* I'm not terribly concerned about this one, so it's here more for the 
//           sake of completeness than anything else. Feel free to delete if 
//           inconvenient. */
//        assertEquals("x", zeroIQI.minPolynomialString());
    }
    
    /**
     * Test of minPolynomialStringTeX method, of class 
     * ImaginaryQuadraticInteger. Spaces in the results are desirable but not 
     * required. Therefore the tests should strip out spaces before asserting 
     * equality.
     */@org.junit.Ignore
    @Test
    public void testMinPolynomialStringTeX() {
        System.out.println("minPolynomialStringTeX");
        fail("REWRITE THIS TEST");
//        String expected, actual;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expected = "x^2";
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                if (randomRealForHalfInts < 0) {
//                    expected = expected + "+" + ((-1) * randomRealForHalfInts);
//                } else {
//                    expected = expected + "-" + randomRealForHalfInts;
//                }
//                expected = expected + "x+" + ((randomRealForHalfInts 
//                        * randomRealForHalfInts + randomImagForHalfInts 
//                        * randomImagForHalfInts 
//                        * testIntegers.get(i).getRing().getAbsNegRad()) 
//                        / 4);
//            } else {
//                if (randomRealPart < 0) {
//                    expected = expected + "+" + ((-2) * randomRealPart);
//                } else {
//                    expected = expected + "-" + (2 * randomRealPart);
//                }
//                expected = expected + "x+" + (randomRealPart * randomRealPart 
//                        + randomImagPart * randomImagPart 
//                        * testIntegers.get(i).getRing().getAbsNegRad());
//            }
//            expected = expected.replace("+1x", "+x");
//            expected = expected.replace("-1x", "-x");
//            expected = expected.replace("+0x", "");
//            expected = expected.replace("-0x", "");
//            actual = testIntegers.get(i).minPolynomialStringTeX()
//                    .replace(" ", ""); // Strip out spaces
//            assertEquals(expected, actual);
//        }
//        // Now to test the polynomial strings of a few purely real integers
//        ImaginaryQuadraticInteger degreeOneInt;
//        for (int j = 1; j < 8; j++) {
//            degreeOneInt = new ImaginaryQuadraticInteger(j, 0, ringRandom);
//            expected = "x-" + j;
//            actual = degreeOneInt.minPolynomialStringTeX().replace(" ", "");
//            assertEquals(expected, actual);
//            degreeOneInt = new ImaginaryQuadraticInteger(-j, 0, ringRandom);
//            expected = "x+" + j;
//            actual = degreeOneInt.minPolynomialStringTeX().replace(" ", "");
//            assertEquals(expected, actual);
//        }
//        // For the sake of completeness
//        assertEquals("x", zeroIQI.minPolynomialStringTeX());
    }
    
    /**
     * Test of minPolynomialStringHTML method, of class 
     * ImaginaryQuadraticInteger. Spaces in the results are desirable but not 
     * required. Therefore the tests should strip out spaces before asserting 
     * equality.
     */@org.junit.Ignore
    @Test
    public void testMinPolynomialStringHTML() {
        System.out.println("minPolynomialStringHTML");
        fail("REWRITE THIS TEST");
//        String expected, actual;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            expected = "<i>x</i><sup>2</sup>";
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                if (randomRealForHalfInts < 0) {
//                    expected = expected + "+" + ((-1) * randomRealForHalfInts);
//                } else {
//                    expected = expected + "&minus;" + randomRealForHalfInts;
//                }
//                expected = expected + "<i>x</i>+" + ((randomRealForHalfInts 
//                        * randomRealForHalfInts + randomImagForHalfInts 
//                        * randomImagForHalfInts * testIntegers.get(i)
//                                .getRing().getAbsNegRad()) / 4);
//            } else {
//                if (randomRealPart < 0) {
//                    expected = expected + "+" + ((-2) * randomRealPart);
//                } else {
//                    expected = expected + "&minus;" + (2 * randomRealPart);
//                }
//                expected = expected + "<i>x</i>+" + (randomRealPart 
//                        * randomRealPart + randomImagPart * randomImagPart 
//                        * testIntegers.get(i).getRing().getAbsNegRad());
//            }
//            expected = expected.replace("+1<i>x</i>", "+<i>x</i>");
//            expected = expected.replace("&minus;1<i>x</i>", "&minus;<i>x</i>");
//            expected = expected.replace("+0<i>x</i>", "");
//            expected = expected.replace("&minus;0<i>x</i>", "");
//            actual = testIntegers.get(i).minPolynomialStringHTML().replace(" ", ""); // Strip out spaces
//            assertEquals(expected, actual);
//        }
//        // Now to test the polynomial strings of a few purely real integers
//        ImaginaryQuadraticInteger degreeOneInt;
//        for (int j = 1; j < 8; j++) {
//            degreeOneInt = new ImaginaryQuadraticInteger(j, 0, ringRandom);
//            expected = "<i>x</i>&minus;" + j;
//            actual = degreeOneInt.minPolynomialStringHTML().replace(" ", "");
//            assertEquals(expected, actual);
//            degreeOneInt = new ImaginaryQuadraticInteger(-j, 0, ringRandom);
//            expected = "<i>x</i>+" + j;
//            actual = degreeOneInt.minPolynomialStringHTML().replace(" ", "");
//            assertEquals(expected, actual);
//        }
//        // For the sake of completeness
//        assertEquals("<i>x</i>", zeroIQI.minPolynomialStringHTML());
    }
    
    /**
     * Test of the conjugate function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testConjugate() {
        System.out.println("conjugate");
        int a = RANDOM.nextInt();
        int propB = RANDOM.nextInt();
        int b = (propB == 0) ? 1 : propB;
        int d = -randomSquarefreeNumber(16384);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        QuadraticInteger expected = new ImaginaryQuadraticInteger(a, -b, ring);
        QuadraticInteger actual = number.conjugate();
        String message = "Calculating conjugate of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Another test of the conjugate function, of the ImaginaryQuadraticInteger 
     * class.
     */
    @Test
    public void testConjugateUnary() {
        int a = RANDOM.nextInt();
        int d = -randomSquarefreeNumber(16384);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger expected = new ImaginaryQuadraticInteger(a, 0, ring);
        QuadraticInteger actual = expected.conjugate();
        String message = expected.toString() + " should be its own conjugate";
        assertEquals(message, expected, actual);
    }
    
    /**
     * Another test of the conjugate function, of the ImaginaryQuadraticInteger 
     * class.
     */
    @Test
    public void testConjugateHalfIntegers() {
        int a = RANDOM.nextInt() | 1;
        int b = RANDOM.nextInt() | 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 2);
        QuadraticInteger expected = new ImaginaryQuadraticInteger(a, -b, ring, 
                2);
        QuadraticInteger actual = number.conjugate();
        String message = "Calculating conjugate of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testAbsZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 0, ring);
        double expected = 0.0;
        double actual = number.abs();
        String message = "Calculating abs(" + number.toString() + ")";
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAbsPurelyRealPositive() {
        int a = randomNumber(Short.MAX_VALUE) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        double expected = a;
        double actual = number.abs();
        String message = "Calculating abs(" + number.toString() + ")";
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAbsPurelyRealNegative() {
        int a = -randomNumber(Short.MAX_VALUE) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, 0, ring);
        double expected = Math.abs(a);
        double actual = number.abs();
        String message = "Calculating abs(" + number.toString() + ")";
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAbsPurelyImaginaryPositive() {
        int b = randomNumber(Short.MAX_VALUE) + 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, ring);
        double expected = ring.getAbsNegRadSqrt() * b;
        double actual = number.abs();
        String message = "Calculating abs(" + number.toString() + ")";
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    @Test
    public void testAbsPurelyImaginaryNegative() {
        int b = -randomNumber(Short.MAX_VALUE) - 1;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, b, ring);
        double expected = ring.getAbsNegRadSqrt() * Math.abs(b);
        double actual = number.abs();
        String message = "Calculating abs(" + number.toString() + ")";
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of the abs function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        int shift = randomNumber(16);
        int powerOfTwo = 1 << shift;
        int bound = -Short.MIN_VALUE;
        int halfBound = bound / 2;
        int a = (randomNumber(bound) - halfBound) | powerOfTwo;
        int b = (randomNumber(bound) - halfBound) | powerOfTwo;
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        double realLeg = ((double) a) * a;
        double imagLeg = ((double) b) * b * ring.getAbsNegRad();
        double hypotenuseSquared = realLeg + imagLeg;
        double expected = Math.sqrt(hypotenuseSquared);
        double actual = number.abs();
        String message = "Calculating abs(" + number.toString() + ")";
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of getRealPartNumeric method, of class ImaginaryQuadraticInteger.
     */
//    @Test
    public void testGetRealPartMultNumeric() {
        System.out.println("getRealPartMultNumeric");
        fail("REWRITE THIS TEST");
//        double expResult = (double) randomRealForHalfInts/2;
//        double result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            result = testIntegers.get(i).getRealPartNumeric();
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                assertEquals(expResult, result, QuadraticRingTest.TEST_DELTA);
//            } else {
//                assertEquals(randomRealPart, result, QuadraticRingTest.TEST_DELTA);
//            }
//        }
    }

    /**
     * Test of getImagPartNumeric method, of class ImaginaryQuadraticInteger.
     */
//    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartNumeric");
        fail("REWRITE THIS TEST");
//        double expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            result = testIntegers.get(i).getImagPartNumeric();
//            if (testIntegers.get(i).getRing().hasHalfIntegers()) {
//                expResult = ((double) randomImagForHalfInts 
//                        * testIntegers.get(i).getRing().getAbsNegRadSqrt()) / 2;
//            } else {
//                expResult = (double) randomImagPart 
//                        * testIntegers.get(i).getRing().getAbsNegRadSqrt();
//            }
//            assertEquals(expResult, result, QuadraticRingTest.TEST_DELTA);
//        }
    }
    
    /**
     * Test of the isReApprox function, of the ImaginaryQuadraticInteger class.
     */
//    @Test
    public void testIsReApprox() {
        System.out.println("isReApprox");
        fail("REWRITE THIS TEST");
//        int a = 2 * (RANDOM.nextInt(32768) - 16384) + 1;
//        int b = 2 * (RANDOM.nextInt(32768) - 16384) + 1;
//        ImaginaryQuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
//                RING_EISENSTEIN, 2);
//        String msg = "Real part given as " + number.getRealPartNumeric() 
//                + " of " + number.toString() 
//                + " should be considered exact, not an approximation";
//        assert !number.isReApprox() : msg;
    }

    /**
     * Test of the isImApprox function, of the ImaginaryQuadraticInteger class.
     */
//    @Test
    public void testIsImApprox() {
        System.out.println("isImApprox");
        fail("REWRITE THIS TEST");
//        int a = 2 * (RANDOM.nextInt(32768) - 16384) + 1;
//        int b = 2 * (RANDOM.nextInt(32768) - 16384) + 1;
//        ImaginaryQuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
//                RING_EISENSTEIN, 2);
//        String msg = "Imaginary part given as " + number.getImagPartNumeric() 
//                + " of " + number.toString() 
//                + " should be regarded as an approximation";
//        assert number.isImApprox() : msg;
    }
    
    /**
     * Another test of the isImApprox function, of the ImaginaryQuadraticInteger 
     * class. If the imaginary part is 0, then 0.0 is an exact value, not an 
     * approximation.
     */
//    @Test
    public void testIsNotImApproxIfImagPartZero() {
        fail("REWRITE THIS TEST");
//        int a = RANDOM.nextInt(32768) - 16384;
//        int b = 0;
//        ImaginaryQuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
//                RING_ZI2);
//        String msg = "Imaginary part given as " + number.getImagPartNumeric() 
//                + " of " + number.toString() 
//                + " should be considered exact, not an approximation";
//        assert !number.isImApprox() : msg;
    }

//    @Test
    public void testIsNotImApproxIfGaussian() {
        fail("REWRITE THIS TEST");
//        int a = RANDOM.nextInt(32768) - 16384;
//        int b = RANDOM.nextInt(32768) - 16384;
//        ImaginaryQuadraticInteger number = new ImaginaryQuadraticInteger(a, b, 
//                RING_GAUSSIAN);
//        String msg = "Imaginary part given as " + number.getImagPartNumeric() 
//                + " of " + number.toString() 
//                + " should be considered exact, not an approximation";
//        assert !number.isImApprox() : msg;
    }

    /* (TEMPORARY JAVADOC DISABLE) *
     * Test of getTwiceRealPartMult method, of class ImaginaryQuadraticInteger.
     */
    // (AT)Test
//    public void testGetTwiceRealPartMult() {
//        System.out.println("getTwiceRealPartMult");
//        long expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            result = testIntegers.get(i).getTwiceRealPartMult();
//            if (testIntegers.get(i).getCausingRing().hasHalfIntegers()) {
//                expResult = randomRealForHalfInts;
//            } else {
//                expResult = 2 * randomRealPart;
//            }
//            assertEquals(expResult, result);
//        }
//    }

    /* (TEMPORARY JAVADOC DISABLE) *
     * Test of getTwiceImagPartMult method, of class ImaginaryQuadraticInteger.
     */
    //(AT)Test
//    public void testGetTwiceImagPartMult() {
//        System.out.println("getTwiceImagPartMult");
//        long expResult, result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            result = testIntegers.get(i).getTwiceImagPartMult();
//            if (testIntegers.get(i).getCausingRing().hasHalfIntegers()) {
//                expResult = randomImagForHalfInts;
//            } else {
//                expResult = 2 * randomImagPart;
//            }
//            assertEquals(expResult, result);
//        }
//    }
    
    /**
     * Test of the getRegPartMult function, of the ImaginaryQuadraticInteger 
     * class.
     */
    @Test
    public void testGetRegPartMult() {
        System.out.println("getRealPartMult");
        int expected = randomNumber();
        int b = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(expected, b, 
                ring);
        int actual = number.getRegPartMult();
        String message = "Getting real part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetRegPartMultHalfInt() {
        int bound = 512;
        int expected = 2 * randomNumber(bound) + 1;
        int b = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(expected, b, 
                ring, 2);
        int actual = number.getRegPartMult();
        String message = "Getting real part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getSurdPartMult function, of the ImaginaryQuadraticInteger 
     * class.
     */
    @Test
    public void testGetSurdPartMult() {
        System.out.println("getImagPartMult");
        int a = randomNumber();
        int expected = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, expected, 
                ring);
        int actual = number.getSurdPartMult();
        String message = "Getting imaginary part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetSurdPartMultHalfInt() {
        int bound = 512;
        int a = 2 * randomNumber(bound) + 1;
        int expected = 2 * randomNumber(bound) + 1;
        QuadraticRing ring = chooseRingWithHalfInts();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, expected, 
                ring, 2);
        int actual = number.getSurdPartMult();
        String message = "Getting imaginary part of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getRing function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        int a = randomNumber();
        int propB = randomNumber();
        int b = propB == 0 ? 1 : propB;
        QuadraticRing expected = chooseRing();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, expected);
        QuadraticRing actual = number.getRing();
        String message = "Getting ring of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getDenominator function, of the ImaginaryQuadraticInteger 
     * class.
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
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 
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
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring, 
                expected);
        int actual = number.getDenominator();
        String message = "Getting denominator of " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of hashCode method, of class ImaginaryQuadraticInteger. It is 
     * expected that if two ImaginaryQuadraticInteger objects are equal, their 
     * hash codes are equal as well. It is also expected that a + b&radic;c and 
     * a + b&radic;d will get different hash codes. But it is definitely not 
     * expected that hash codes will be unique among all possible 
     * ImaginaryQuadraticInteger objects.
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
                number = new ImaginaryQuadraticInteger(a, b, ring, 2);
            } else {
                int a = randomNumber();
                int b = randomNumber();
                QuadraticRing ring = chooseRing();
                number = new ImaginaryQuadraticInteger(a, b, ring);
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
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        assertEquals(number, number);
    }
    
    @Test
    public void testNotEqualsNull() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        Object obj = provideNull();
        String msg = "Number " + number.toString() + " should not equal null";
        assert !number.equals(obj) : msg;
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger number = new ImaginaryQuadraticInteger(a, b, ring);
        QuadraticInteger numberDiffClass 
                = new QuadraticIntegerTest.QuadraticIntegerImpl(a, b, ring);
        String msg = "Number " + number.toString() + " of class " 
                + number.getClass().getName() + " should not equal " 
                + numberDiffClass.toString() + " of class " 
                + numberDiffClass.getClass().getName();
        assert !number.equals(numberDiffClass) : msg;
    }
    
    /**
     * Test of the equals function, of the ImaginaryQuadraticInteger class.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber();
        QuadraticInteger someNumber = new ImaginaryQuadraticInteger(a, b, ring);
        QuadraticInteger sameNumber = new ImaginaryQuadraticInteger(a, b, ring);
        assertEquals(someNumber, sameNumber);
    }
    
    @Test
    public void testNotEqualsDiffRe() {
        QuadraticRing ring = chooseRing();
        int aA = randomNumber();
        int aB = ~aA;
        int b = randomNumber();
        QuadraticInteger someNumber = new ImaginaryQuadraticInteger(aA, b, 
                ring);
        QuadraticInteger diffNumber = new ImaginaryQuadraticInteger(aB, b, 
                ring);
        assertNotEquals(someNumber, diffNumber);
    }
    
    @Test
    public void testNotEqualsDiffIm() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int bA = randomNumber();
        int bB = ~bA;
        QuadraticInteger someNumber = new ImaginaryQuadraticInteger(a, bA, 
                ring);
        QuadraticInteger diffNumber = new ImaginaryQuadraticInteger(a, bB, 
                ring);
        assertNotEquals(someNumber, diffNumber);
    }
    
    @Test
    public void testNotEqualsDiffDenom() {
        QuadraticRing ring = chooseRingWithHalfInts();
        int a = 2 * randomNumber() + 1;
        int b = 2 * randomNumber() + 1;
        QuadraticInteger someNumber = new ImaginaryQuadraticInteger(a, b, ring);
        QuadraticInteger diffNumber = new ImaginaryQuadraticInteger(a, b, ring, 2);
        assertNotEquals(someNumber, diffNumber);
    }
    
    @Test
    public void testNotEqualsDiffRing() {
        int a = randomNumber();
        int b = randomNumber();
        QuadraticRing ringA = chooseRing();
        QuadraticInteger someNumber = new ImaginaryQuadraticInteger(a, b, 
                ringA);
        int bound = 512;
        int d = -randomSquarefreeNumberOtherThan(-ringA.getRadicand(), bound);
        QuadraticRing ringB = new ImaginaryQuadraticRing(d);
        QuadraticInteger diffNumber = new ImaginaryQuadraticInteger(a, b, 
                ringB);
        String message = someNumber.toString() + " should not equal " 
                + diffNumber.toString();
        assertNotEquals(message, someNumber, diffNumber);
    }
    
    /**
     * Test of the inferStep function, of the ImaginaryQuadraticInteger class. A 
     * line is chosen so that the step is either 1 + sqrt(d) or 1/2 + sqrt(d)/2 
     * (the latter only if ringRandom is a domain with "half-integers").
     */@org.junit.Ignore
    @Test
    public void testInferStep() {
        System.out.println("inferStep");
        fail("REWRITE THIS TEST");
//        int startCoord = -RANDOM.nextInt(128) - 1;
//        int endCoord = -startCoord * RANDOM.nextInt(32);
//        int offset = RANDOM.nextInt(16) - 8;
//        ImaginaryQuadraticInteger startPoint 
//                = new ImaginaryQuadraticInteger(startCoord - offset, startCoord, 
//                        ringRandom);
//        ImaginaryQuadraticInteger endPoint 
//                = new ImaginaryQuadraticInteger(endCoord - offset, endCoord, 
//                        ringRandom);
//        QuadraticInteger expected = new ImaginaryQuadraticInteger(1, 1, 
//                ringRandom);
//        if (ringRandom.hasHalfIntegers()) {
//            try {
//                expected = expected.divides(2);
//            } catch (NotDivisibleException nde) {
//                System.out.println("\"" + nde.getMessage() + "\"");
//                String msg = "Trying to halve " + expected.toString() 
//                        + " should not have caused NotDivisibleException";
//                fail(msg);
//            }
//        }
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(startPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. The step to be inferred from 5 - 5i to -5 + 5i is -1 + i.
     */@org.junit.Ignore
    @Test
    public void testInferStepGaussian() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(5, 
//                -5, RING_GAUSSIAN);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-5, 
//                5, RING_GAUSSIAN);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(-1, 
//                1, RING_GAUSSIAN);
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. The step to be inferred from -2 to -11 + 3sqrt(-2) is -3 + 
     * sqrt(-2).
     */@org.junit.Ignore
    @Test
    public void testInferStepZi2() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(-2, 
//                0, RING_ZI2);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-11, 
//                3, RING_ZI2);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(-3, 
//                1, RING_ZI2);
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. The step to be inferred from -7 to -5 - 2sqrt(-3) is 1/2 - 
     * sqrt(-3)/2.
     */
//    @Test
    public void testInferStepEisenstein() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(-7, 
//                0, RING_EISENSTEIN);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-5, 
//                -2, RING_EISENSTEIN);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(1, 
//                -1, RING_EISENSTEIN, 2);
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. The step to be inferred from -1/2 - 3sqrt(-7)/2 to -11 - 3sqrt(-7) 
     * is -7/2 - sqrt(-7)/2.
     */@org.junit.Ignore
    @Test
    public void testInferStepOQi7() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(-1, 
//                -3, RING_OQI7, 2);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-11, 
//                -3, RING_OQI7);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(-7, 
//                -1, RING_OQI7, 2);
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. The step to be inferred from -5/2 - 3sqrt(d)/2 to 11/2 - 
     * 3sqrt(d)/2 is 1.
     */
//    @Test
    public void testInferStepHorizontal() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(-5, 
//                -3, ringRandomForAltTesting, 2);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(11,  
//                -3, ringRandomForAltTesting, 2);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(1, 0, 
//                ringRandomForAltTesting);
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. The step to be inferred from -5/2 - 3sqrt(d)/2 to -5/2 + 
     * 13sqrt(d)/2 is sqrt(d).
     */
//    @Test
    public void testInferStepVertical() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(-5, 
//                -3, ringRandomForAltTesting, 2);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-5, 
//                13, ringRandomForAltTesting, 2);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(0, 1, 
//                ringRandomForAltTesting);
//        ImaginaryQuadraticInteger actual 
//                = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the inferStep function, of the ImaginaryQuadraticInteger 
     * class. Trying to infer a step between imaginary quadratic integers from 
     * different rings should cause {@link 
     * algebraics.AlgebraicDegreeOverflowException}.
     */
//    @Test
    public void testInferStepAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger beginPoint = new ImaginaryQuadraticInteger(-5, 
//                -5, RING_GAUSSIAN);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-1, 
//                -3, RING_OQI7, 2);
//        try {
//            ImaginaryQuadraticInteger result 
//                    = ImaginaryQuadraticInteger.inferStep(beginPoint, endPoint);
//            String msg = "Trying to infer step between " + beginPoint.toString() 
//                    + " and " + endPoint.toString() 
//                    + " should have caused an exception, not given result " 
//                    + result.toString();
//            fail(msg);
//        } catch (AlgebraicDegreeOverflowException adoe) {
//            System.out.println("Trying to infer step between " 
//                    + beginPoint.toASCIIString() + " and " 
//                    + endPoint.toASCIIString() 
//                    + " correctly caused AlgebraicDegreeOverflowException");
//            System.out.println("\"" + adoe.getMessage() + "\"");
//        } catch (RuntimeException re) {
//            String msg = re.getClass().getName() 
//                    + " is the wrong exception to trying to infer step between " 
//                    + beginPoint.toString() + " and " + endPoint.toString();
//            fail(msg);
//        }
    }
    
    /**
     * Test of to, of class ImaginaryQuadraticInteger. This test is about 
     * imaginary quadratic integers such that lines connecting them on the 
     * complex plane are diagonals going in different directions.
     */@org.junit.Ignore
    @Test
    public void testTo() {
        System.out.println("to");
        fail("Haven't rewritten test yet");
//        List<ImaginaryQuadraticInteger> expResult = new ArrayList<>();
//        ImaginaryQuadraticInteger startPoint = new ImaginaryQuadraticInteger(1, 1, RING_GAUSSIAN);
//        expResult.add(startPoint);
//        ImaginaryQuadraticInteger betweenPoint = new ImaginaryQuadraticInteger(2, 2, RING_GAUSSIAN);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(3, 3, RING_GAUSSIAN);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(4, 4, RING_GAUSSIAN);
//        expResult.add(betweenPoint);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(5, 5, RING_GAUSSIAN);
//        expResult.add(endPoint);
//        List<ImaginaryQuadraticInteger> result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
//        expResult.clear();
//        startPoint = new ImaginaryQuadraticInteger(-2, 0, RING_ZI2);
//        expResult.add(startPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-5, 1, RING_ZI2);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-8, 2, RING_ZI2);
//        expResult.add(betweenPoint);
//        endPoint = new ImaginaryQuadraticInteger(-11, 3, RING_ZI2);
//        expResult.add(endPoint);
//        result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
//        expResult.clear();
//        startPoint = new ImaginaryQuadraticInteger(-7, 0, RING_EISENSTEIN);
//        expResult.add(startPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-13, -1, RING_EISENSTEIN, 2);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-6, -1, RING_EISENSTEIN);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-11, -3, RING_EISENSTEIN, 2);
//        expResult.add(betweenPoint);
//        endPoint = new ImaginaryQuadraticInteger(-5, -2, RING_EISENSTEIN);
//        expResult.add(endPoint);
//        result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
//        expResult.clear();
//        startPoint = new ImaginaryQuadraticInteger(-1, -3, RING_OQI7, 2);
//        expResult.add(startPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-4, -2, RING_OQI7);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-15, -5, RING_OQI7, 2);
//        expResult.add(betweenPoint);
//        endPoint = new ImaginaryQuadraticInteger(-11, -3, RING_OQI7);
//        expResult.add(endPoint);
//        result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
    }
    
    /**
     * Test of to, of class ImaginaryQuadraticInteger. If the end point 
     * parameter is the same as the starting point, to() should return a list 
     * with a single imaginary quadratic integer in it.
     */@org.junit.Ignore
    @Test
    public void testToSinglePoint() {
        fail("Haven't rewritten test yet");
//        ImaginaryQuadraticInteger startPoint = new ImaginaryQuadraticInteger(10, 43, ringRandom);
//        List<ImaginaryQuadraticInteger> expResult = new ArrayList<>();
//        expResult.add(startPoint);
//        List<ImaginaryQuadraticInteger> result = startPoint.to(startPoint);
//        assertEquals(expResult, result);
    }
    
    /**
     * Test of to, of class ImaginaryQuadraticInteger. A ring that contains 
     * algebraic integers from two different imaginary quadratic rings must 
     * necessarily have an algebraic degree of at least 4, hence calling this 
     * function on two such imaginary quadratic integers should trigger {@link 
     * algebraics.AlgebraicDegreeOverflowException}. However, such a ring would 
     * also contain real numbers, so the requested range would be infinite.
     */@org.junit.Ignore
    @Test
    public void testToCrossDomain() {
        fail("Haven't rewritten test yet");
//        ImaginaryQuadraticInteger startPoint = new ImaginaryQuadraticInteger(5, 3, RING_OQI7, 2);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(-7, 2, ringRandom);
//        try {
//            List<ImaginaryQuadraticInteger> result = startPoint.to(endPoint);
//            String failMessage = "Trying to get range from " + startPoint.toString() + " to " + endPoint.toString() + " should have caused an exception, not given result " + result.toString() + ".";
//            fail(failMessage);
//        } catch (AlgebraicDegreeOverflowException adoe) {
//            System.out.println("Trying to get range from " + startPoint.toASCIIString() + " to " + endPoint.toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException.");
//            System.out.println("\"" + adoe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = e.getClass().getName() + " is the wrong exception for trying to get range from " + startPoint.toString() + " to " + endPoint.toString() + ".";
//            fail(failMessage);
//        }
    }
    
    /**
     * Test of to, of class ImaginaryQuadraticInteger. This test is about 
     * imaginary quadratic integers such that lines connecting them on the 
     * complex plane are vertical lines, going up or down.
     */@org.junit.Ignore
    @Test
    public void testToOnSameRealLine() {
        System.out.println("to on same real line");
        fail("Haven't rewritten test yet");
//        List<ImaginaryQuadraticInteger> expResult = new ArrayList<>();
//        ImaginaryQuadraticInteger startPoint = new ImaginaryQuadraticInteger(1, 1, RING_ZI2);
//        expResult.add(startPoint);
//        ImaginaryQuadraticInteger betweenPoint = new ImaginaryQuadraticInteger(1, 2, RING_ZI2);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(1, 3, RING_ZI2);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(1, 4, RING_ZI2);
//        expResult.add(betweenPoint);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(1, 5, RING_ZI2);
//        expResult.add(endPoint);
//        List<ImaginaryQuadraticInteger> result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
//        expResult.clear();
//        startPoint = new ImaginaryQuadraticInteger(-7, 5, RING_EISENSTEIN, 2);
//        expResult.add(startPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-7, 3, RING_EISENSTEIN, 2);
//        expResult.add(betweenPoint);
//        endPoint = new ImaginaryQuadraticInteger(-7, 1, RING_EISENSTEIN, 2);
//        expResult.add(endPoint);
//        result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
    }
    
    /**
     * Test of to, of class ImaginaryQuadraticInteger. This test is about 
     * imaginary quadratic integers such that lines connecting them on the 
     * complex plane are horizontal lines, going left or right.
     */@org.junit.Ignore
    @Test
    public void testToOnSameImagLine() {
        System.out.println("to on same imaginary line");
        fail("Haven't rewritten test yet");
//        List<ImaginaryQuadraticInteger> expResult = new ArrayList<>();
//        ImaginaryQuadraticInteger startPoint = new ImaginaryQuadraticInteger(-1, 1, RING_ZI2);
//        expResult.add(startPoint);
//        ImaginaryQuadraticInteger betweenPoint = new ImaginaryQuadraticInteger(0, 1, RING_ZI2);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(1, 1, RING_ZI2);
//        expResult.add(betweenPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(2, 1, RING_ZI2);
//        expResult.add(betweenPoint);
//        ImaginaryQuadraticInteger endPoint = new ImaginaryQuadraticInteger(3, 1, RING_ZI2);
//        expResult.add(endPoint);
//        List<ImaginaryQuadraticInteger> result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
//        expResult.clear();
//        startPoint = new ImaginaryQuadraticInteger(-29, -5, RING_OQI7, 2);
//        expResult.add(startPoint);
//        betweenPoint = new ImaginaryQuadraticInteger(-31, -5, RING_OQI7, 2);
//        expResult.add(betweenPoint);
//        endPoint = new ImaginaryQuadraticInteger(-33, -5, RING_OQI7, 2);
//        expResult.add(endPoint);
//        result = startPoint.to(endPoint);
//        assertEquals(expResult, result);
    }
    
    /**
     * Test of parseQuadraticInteger, of class ImaginaryQuadraticInteger, 
     * inherited from QuadraticInteger. Whatever is output by toString, 
     * toStringAlt, toASCIIString, toASCIIStringAlt, toTeXString, 
     * toTeXStringSingleDenom, toTeXStringAlt, toHTMLString or toHTMLStringAlt 
     * should be parseable by parseQuadraticInteger. With the following caveats: 
     * &omega; should always be understood to mean -1/2 + sqrt(-3)/2 and &phi; 
     * should be understood to mean 1/2 + sqrt(5)/2, while &theta; means 1/2 
     * + sqrt(d)/2 with d = 1 mod 4, but d may be ambiguous.
     */@org.junit.Ignore
    @Test
    public void testParseQuadraticInteger() {
        System.out.println("parseQuadraticInteger");
        fail("REWRITE THIS TEST");
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
     * ImaginaryQuadraticInteger. This includes recognizing "j" as an 
     * alternative notation for &radic;-1. If the optional behaviors are 
     * required, change the print statements under catch {@link 
     * NumberFormatException} to fails.
     */
    @Ignore
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
    
    /* (TEMP JAVADOC DISABLE) *
     * Test of parseQuaterImaginary of class ImaginaryQuadraticInteger.
     */
    //(AT)Test
//    public void testParseQuaterImaginary() {
//        System.out.println("parseQuaterImaginary");
//        String numberString = "10";
//        ImaginaryQuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 2, ringGaussian);
//        ImaginaryQuadraticInteger result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//        assertEquals(expResult, result);
//        numberString = " 3211 "; // Contains spaces to test these are indeed stripped out
//        expResult = new ImaginaryQuadraticInteger(-7, -22, ringGaussian);
//        result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//        assertEquals(expResult, result);
//        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//        char decimalDot = dfs.getDecimalSeparator();
//        numberString = "10" + decimalDot + "200";
//        result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//        assertEquals(IMAG_UNIT_I, result);
//        numberString = "10" + decimalDot + "3";
//        try {
//            result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//            String failMessage = "\"" + numberString + "\" should have triggered NumberFormatException, not given result " + result.toASCIIString();
//            fail(failMessage);
//        } catch (NumberFormatException nfe) {
//            System.out.println("\"" + numberString + "\" correctly triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have triggered " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "not actually a number at all this time, sorry";
//        try {
//            result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//            String failMessage = "\"" + numberString + "\" should have triggered NumberFormatException, not given result " + result.toASCIIString();
//            fail(failMessage);
//        } catch (NumberFormatException nfe) {
//            System.out.println("\"" + numberString + "\" correctly triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = "Empty String should not have caused " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "";
//        try {
//            result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//            assertEquals(zeroIQI, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("It is acceptable for an empty String to trigger NumberFormatException \"" + nfe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have triggered " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//    }
    
    /**
     * Test of parseQuaterImaginary of class ImaginaryQuadraticInteger.
     */
//    @Test
    public void testParseQuaterImaginary() {
        System.out.println("parseQuaterImaginary");
        fail("REWRITE THIS TEST");
//        String numberString = "10";
//        ImaginaryQuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 2, RING_GAUSSIAN);
//        QuadraticInteger result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//        assertEquals(expResult, result);
//        numberString = " 3211 "; // Contains spaces to test these are indeed stripped out
//        expResult = new ImaginaryQuadraticInteger(-7, -22, RING_GAUSSIAN);
//        result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//        assertEquals(expResult, result);
//        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//        char decimalDot = dfs.getDecimalSeparator();
//        numberString = "10" + decimalDot + "200";
//        result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//        assertEquals(NumberTheoreticFunctionsCalculator.IMAG_UNIT_I, result);
//        numberString = "10" + decimalDot + "3";
//        try {
//            result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//            String failMessage = "\"" + numberString + "\" should have triggered NumberFormatException, not given result " + result.toASCIIString();
//            fail(failMessage);
//        } catch (NumberFormatException nfe) {
//            System.out.println("\"" + numberString + "\" correctly triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have triggered " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "not actually a number at all this time, sorry";
//        try {
//            result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//            String failMessage = "\"" + numberString + "\" should have triggered NumberFormatException, not given result " + result.toASCIIString();
//            fail(failMessage);
//        } catch (NumberFormatException nfe) {
//            System.out.println("\"" + numberString + "\" correctly triggered NumberFormatException \"" + nfe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = "Empty String should not have caused " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
//        numberString = "";
//        try {
//            result = ImaginaryQuadraticInteger.parseQuaterImaginary(numberString);
//            assertEquals(zeroIQI, result);
//        } catch (NumberFormatException nfe) {
//            System.out.println("It is acceptable for an empty String to trigger NumberFormatException \"" + nfe.getMessage() + "\"");
//        } catch (Exception e) {
//            String failMessage = "\"" + numberString + "\" should not have triggered " + e.getClass().getName() + " \"" + e.getMessage() + "\"";
//            fail(failMessage);
//        }
    }

    /**
     * Test of plus method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        ImaginaryQuadraticRing currRing;
        QuadraticInteger expResult, result, testAddendA, testAddendB;
        int currDenom;
        String failMessage;
        for (int iterDiscr = -1; iterDiscr > -200; iterDiscr--) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
                currRing = new ImaginaryQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = -3; w < 48; w += 2) {
                        testAddendA = new ImaginaryQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = -3; y < 48; y += 2) {
                                testAddendB = new ImaginaryQuadraticInteger(x, y, currRing, currDenom);
                                expResult = new ImaginaryQuadraticInteger(v + x, w + y, currRing, currDenom);
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
                                expResult = new ImaginaryQuadraticInteger(v + 2 * x, w, currRing, 2);
                            } else {
                                expResult = new ImaginaryQuadraticInteger(v + x, w, currRing);
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
     * Test of plus method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Adding additive inverse should give 0.
     */
//    @Test
    public void testPlusAdditiveInverses() {
        fail("REWRITE THIS TEST");
//        String failMessage;
//        QuadraticInteger result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            failMessage = "Adding " + testIntegers.get(i).toASCIIString() + " to " + testAdditiveInverses.get(i).toASCIIString() + " should not have triggered AlgebraicDegreeOverflowException \"";
//            try {
//                result = testIntegers.get(i).plus(testAdditiveInverses.get(i));
//                assertEquals(zeroIQI, result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + adoe.getMessage() + "\"";
//                fail(failMessage);
//            }
//            result = testIntegers.get(i).plus(0);
//            assertEquals(testIntegers.get(i), result);
//        }
    }
    
    /**
     * Test of plus method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Adding imaginary quadratic integers from 
     * different rings should cause {@link 
     * algebraics.AlgebraicDegreeOverflowException} unless either summand is 
     * purely real.
     */
//    @Test
    public void testPlusAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
//        QuadraticInteger result;
//        String failMessage;
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
     * Test of negate method, of class ImaginaryQuadraticInteger, inherited from 
     * QuadraticInteger.
     */
//    @Test
    public void testNegate() {
        System.out.println("negate");
        fail("REWRITE THIS TEST");
//        assertEquals(IMAG_UNIT_NEG_I, IMAG_UNIT_I.negate());
//        ImaginaryQuadraticInteger someNumber 
//                = new ImaginaryQuadraticInteger(randomRealPart, -randomRealPart, 
//                        RING_ZI2);
//        ImaginaryQuadraticInteger expected 
//                = new ImaginaryQuadraticInteger(-randomRealPart, randomRealPart, 
//                        RING_ZI2);
//        QuadraticInteger actual = someNumber.negate();
//        assertEquals(expected, actual);
    }

    /**
     * Test of minus method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        ImaginaryQuadraticRing currRing;
        QuadraticInteger expResult, result, testMinuend, testSubtrahend;
        int currDenom;
        String failMessage;
        for (int iterDiscr = -1; iterDiscr > -200; iterDiscr--) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
                currRing = new ImaginaryQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = -3; w < 48; w += 2) {
                        testMinuend = new ImaginaryQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = -3; y < 48; y += 2) {
                                testSubtrahend = new ImaginaryQuadraticInteger(x, y, currRing, currDenom);
                                expResult = new ImaginaryQuadraticInteger(v - x, w - y, currRing, currDenom);
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
                                expResult = new ImaginaryQuadraticInteger(v - 2 * x, w, currRing, 2);
                            } else {
                                expResult = new ImaginaryQuadraticInteger(v - x, w, currRing);
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
     * Test of minus method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Subtracting a number from itself should give 0 
     * as a result, regardless of what that number is.
     */
//    @Test
    public void testMinusNumberItself() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger expResult;
//        QuadraticInteger result;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            // Testing that subtracting itself gives 0 each time
//            expResult = new ImaginaryQuadraticInteger(0, 0, testIntegers.get(i).getRing());
//            try {
//                result = testIntegers.get(i).minus(testIntegers.get(i));
//                assertEquals(expResult, result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                String failMessage = "Subtracting test integer from itself should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage();
//                fail(failMessage);
//            }
//            // Now testing that subtracting 0 does not change the number
//            result = testIntegers.get(i).minus(0);
//            assertEquals(testIntegers.get(i), result);
//        }
    }
    
    /**
     * Test of minus method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Subtracting from an imaginary quadratic integer 
     * with nonzero imaginary part another imaginary quadratic integer that also 
     * has nonzero imaginary part but comes from a different ring should be an 
     * algebraic integer of degree 4. Hence the operation should cause an {@link 
     * algebraics.AlgebraicDegreeOverflowException}.
     */
//    @Test
    public void testMinusAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
//        QuadraticInteger result;
//        String failMessage;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            try {
//                result = testIntegers.get(j).minus(testIntegers.get(j + 1));
//                failMessage = "Subtracting " + testIntegers.get(j + 1).toASCIIString() + " to " + testIntegers.get(j).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                System.out.println("Subtracting " + testIntegers.get(j + 1).toASCIIString() + " from " + testIntegers.get(j).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
//            }
//            /* However, if one of them is purely real, there should be some kind 
//               of result */
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
     * Test of times method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        ImaginaryQuadraticRing currRing;
        QuadraticInteger expResult, result, testMultiplicandA, testMultiplicandB;
        int currDenom;
        String failMessage;
        for (int iterDiscr = -1; iterDiscr > -200; iterDiscr--) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
                currRing = new ImaginaryQuadraticRing(iterDiscr);
                if (currRing.hasHalfIntegers()) {
                    currDenom = 2;
                } else {
                    currDenom = 1;
                }
                for (int v = -3; v < 48; v += 2) {
                    for (int w = -3; w < 48; w += 2) {
                        testMultiplicandA = new ImaginaryQuadraticInteger(v, w, currRing, currDenom);
                        for (int x = -3; x < 48; x += 2) {
                            for (int y = -3; y < 48; y += 2) {
                                testMultiplicandB = new ImaginaryQuadraticInteger(x, y, currRing, currDenom);
                                if (currRing.hasHalfIntegers()) {
                                    expResult = new ImaginaryQuadraticInteger((v * x + w * y * iterDiscr)/2, (v * y + w * x)/2, currRing, currDenom);
                                } else {
                                    expResult = new ImaginaryQuadraticInteger(v * x + w * y * iterDiscr, v * y + w * x, currRing, currDenom);
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
                            expResult = new ImaginaryQuadraticInteger(v * x, w * x, currRing, currDenom);
                            result = testMultiplicandA.times(x);
                            assertEquals(expResult, result);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Test of times method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. Multiplying an imaginary quadratic integer by 
     * its conjugate should give its norm.
     */
//    @Test
    public void testTimesConjugate() {
        fail("REWRITE THIS TEST");
//        for (int i = 0; i < totalTestIntegers; i++) {
//            try {
//                QuadraticInteger result = testIntegers.get(i).times(testConjugates.get(i));
//                assertEquals(testNorms.get(i), result);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                String failMessage = "Multiplying an integer by its conjugate should not have triggered AlgebraicDegreeOverflowException \"" + adoe.getMessage();
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of times method, of class ImaginaryQuadraticInteger, inherited from 
     * {@link QuadraticInteger}. This test multiplies numbers that come from 
     * different rings to make sure {@link 
     * algebraics.AlgebraicDegreeOverflowException} is thrown correctly. Of 
     * course testing that that exception itself works correctly falls to {@link 
     * algebraics.AlgebraicDegreeOverflowExceptionTest}.
     */
//    @Test
    public void testTimesAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
//        QuadraticInteger result;
//        String failMessage;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            try {
//                result = testIntegers.get(j).times(testIntegers.get(j + 1));
//                failMessage = "Multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " should not have resulted in " + result.toASCIIString() + " without triggering AlgebraicDegreeOverflowException.";
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = "Necessary degree should be 4, not " + adoe.getNecessaryAlgebraicDegree() + ", for multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + ".";
//                assertEquals(failMessage, 4, adoe.getNecessaryAlgebraicDegree());
//                System.out.println("Multiplying " + testIntegers.get(j).toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " correctly triggered AlgebraicDegreeOverflowException (algebraic degree " + adoe.getNecessaryAlgebraicDegree() + " needed).");
//            }
//            failMessage = "Multiplying " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " should not have caused";
//            try {
//                result = testNorms.get(j).times(testIntegers.get(j + 1));
//                System.out.println("Multiplying " + testNorms.get(j).toASCIIString() + " from " + testNorms.get(j).getRing().toASCIIString() + " by " + testIntegers.get(j + 1).toASCIIString() + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                failMessage = failMessage + " AlgebraicDegreeOverflowException \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (Exception e) {
//                failMessage = failMessage + " Exception \"" + e.getMessage() + "\"";
//                fail(failMessage);
//            }
//            failMessage = "Multiplying " + testNorms.get(j + 1).toASCIIString() + " from " + testNorms.get(j + 1).getRing().toASCIIString() + " by " + testIntegers.get(j).toASCIIString() + " should not have caused";
//            try {
//                result = testIntegers.get(j).times(testNorms.get(j + 1));
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
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}.
     */
@org.junit.Ignore
    @Test
    public void testDivides() {
        System.out.println("divides(ImaginaryQuadraticInteger)");
        ImaginaryQuadraticRing currRing;
        ImaginaryQuadraticInteger testDividend, testDivisor, expResult;
        QuadraticInteger result;
        String failMessage;
        for (int iterDiscr = -1; iterDiscr > -100; iterDiscr--) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
                currRing = new ImaginaryQuadraticRing(iterDiscr);
                testDividend = new ImaginaryQuadraticInteger(-iterDiscr + 1, 0, currRing);
                testDivisor = new ImaginaryQuadraticInteger(1, 1, currRing);
                expResult = new ImaginaryQuadraticInteger(1, -1, currRing);
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
     * Simultaneous test the times and divides methods, of class 
     * ImaginaryQuadraticInteger. This test multiplies several algebraic 
     * integers in the rings <b>Z</b>[<i>i</i>] to <b>Z</b>[&radic;-199], then 
     * divides to get back the first number. So if the test of the times method 
     * fails, the result of this test is meaningless. 
     */
//    @Test
    public void testTimesDivides() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticRing currRing;
//        QuadraticInteger expResult, result, testQuotient, testDivisor, testDividend;
//        int currDenom;
//        String failMessage;
//        for (int iterDiscr = -1; iterDiscr > -100; iterDiscr--) {
//            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscr)) {
//                currRing = new ImaginaryQuadraticRing(iterDiscr);
//                if (currRing.hasHalfIntegers()) {
//                    currDenom = 2;
//                } else {
//                    currDenom = 1;
//                }
//                for (int v = -3; v < 48; v += 2) {
//                    for (int w = 3; w < 54; w += 2) {
//                        testQuotient = new ImaginaryQuadraticInteger(v, w, currRing, currDenom);
//                        for (int x = -3; x < 48; x += 2) {
//                            for (int y = 3; y < 54; y += 2) {
//                                testDivisor = new ImaginaryQuadraticInteger(x, y, currRing, currDenom);
//                                try {
//                                    testDividend = testQuotient.times(testDivisor);
//                                } catch (AlgebraicDegreeOverflowException adoe) {
//                                    testDividend = zeroIQI; // This is just to avoid "variable result might not have been initialized" error
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
//                                    failMessage = "Dividing " + testDividend.toString() + " by " + testDivisor.toString() + " should not have triggered NotDivisibleException \"" + nde.getMessage() + "\"";
//                                    fail(failMessage);
//                                }
//                                
//                            }
//                            // Now to test divides(int)
//                            testDividend = new ImaginaryQuadraticInteger(v * x, w * x, currRing, currDenom);
//                            expResult = new ImaginaryQuadraticInteger(v, w, currRing, currDenom);
//                            try {
//                                result = testDividend.divides(x);
//                                assertEquals(expResult, result);
//                            } catch (NotDivisibleException nde) {
//                                failMessage = "Dividing " + testDividend.toString() + " by " + x + " should not have triggered NotDivisibleException\"" + nde.getMessage() + "\"";
//                                fail(failMessage);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
    
    /**
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. Specifically with integer divisors.
     */
    @Test
    public void testDividesInt() {
        System.out.println("divides(int)");
        ImaginaryQuadraticRing currRing;
        ImaginaryQuadraticInteger testDividend;
        QuadraticInteger result;
        int testDivRealPartMult;
        String failMessage;
        for (int iterDiscrOQ = -11; iterDiscrOQ > -84; iterDiscrOQ -= 8) {
            if (NumberTheoreticFunctionsCalculator.isSquarefree(iterDiscrOQ)) {
                currRing = new ImaginaryQuadraticRing(iterDiscrOQ);
                testDivRealPartMult = (-iterDiscrOQ + 1)/4;
                testDividend = new ImaginaryQuadraticInteger(testDivRealPartMult, 0, currRing);
                try {
                    result = testDividend.divides(2);
                    failMessage = "Trying to divide " + testDividend.toString() + " by 2 in " + currRing.toString() + " should have triggered NotDivisibleException, not given result " + result.toString();
                    fail(failMessage);
                } catch (NotDivisibleException nde) {
                    System.out.println("Trying to divide " + testDividend.toASCIIString() + " by 2 in " + currRing.toASCIIString() + " correctly triggered NotDivisibleException");
                    System.out.println("\"" + nde.getMessage() + "\"");
                } catch (Exception e) {
                    System.out.println("Encountered this exception: " + e.getClass().getName() + " \"" + e.getMessage() + "\"");
                    failMessage = "Trying to divide " + testDividend.toString() + " by 2 in " + currRing.toString() + " triggered the wrong exception.";
                    fail(failMessage);
                }
            }
        }
    }
    
    /**
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. A conjugate should divide its norm.
     */
//    @Test
    public void testDividesConjugate() {
        fail("REWRITE THIS TEST");
//        QuadraticInteger result;
//        String failMessage;
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
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. Division by zero presented as an imaginary 
     * quadratic integer (0 + 0<i>i</i>) should cause either 
     * IllegalArgumentException or ArithmeticException to be thrown. Any other 
     * exception will fail the test, and that includes {@link 
     * algebraics.NotDivisibleException}. Not throwing any exception at all will 
     * also fail the test.
     */
//    @Test
    public void testDivisionByZeroIQI() {
        fail("REWRITE THIS TEST");
//        for (int i = 0; i < totalTestIntegers; i++) {
//            try {
//                QuadraticInteger result = testIntegers.get(i).divides(zeroIQI);
//                String failMessage = "Dividing " + testIntegers.get(i).toString() + " by 0 + 0i should have caused an exception, not given result " + result.toString();
//                fail(failMessage);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                String failMessage = "AlgebraicDegreeOverflowException is the wrong exception to throw for division by 0 + 0i \"" + adoe.getMessage() + "\"";
//                fail(failMessage);
//            } catch (NotDivisibleException nde) {
//                String failMessage = "NotDivisibleException is the wrong exception to throw for division by 0 + 0i \"" + nde.getMessage() + "\"";
//                fail(failMessage);
//            } catch (IllegalArgumentException iae) {
//                System.out.println("IllegalArgumentException correctly triggered upon attempt to divide by 0 + 0i \"" + iae.getMessage() + "\"");
//            } catch (ArithmeticException ae) {
//                System.out.println("ArithmeticException correctly triggered upon attempt to divide by 0 + 0i \"" + ae.getMessage() + "\"");
//            } catch (Exception e) {
//                String failMessage = "Wrong exception thrown for attempt to divide by 0 + 0i. " + e.getMessage();
//                fail(failMessage);
//            }
//        }
    }
    
    /**
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. Division by zero should cause either 
     * IllegalArgumentException or ArithmeticException to be thrown. Any other 
     * exception will fail the test, and that includes {@link 
     * algebraics.NotDivisibleException}. Not throwing any exception at all will 
     * also fail the test.
     */
//    @Test
    public void testDivisionByZeroInt() {
        fail("REWRITE THIS TEST");
//        String msg;
//        for (int i = 0; i < totalTestIntegers; i++) {
//            try {
//                QuadraticInteger result = testIntegers.get(i).divides(0);
//                msg = "Dividing " + testIntegers.get(i).toString() 
//                        + " by 0 should not have given result " 
//                        + result.toString();
//                fail(msg);
//            } catch (NotDivisibleException nde) {
//                msg = "NotDivisibleException is the wrong exception to throw for division by 0 \"" + nde.getMessage() + "\"";
//                fail(msg);
//            } catch (IllegalArgumentException iae) {
//                System.out.println("IllegalArgumentException correctly triggered upon attempt to divide by 0 \"" + iae.getMessage() + "\"");
//            } catch (ArithmeticException ae) {
//                System.out.println("ArithmeticException correctly triggered upon attempt to divide by 0. \"" + ae.getMessage() + "\"");
//            } catch (Exception e) {
//                msg = "Wrong exception thrown for attempt to divide by 0. " + e.getMessage();
//                fail(msg);
//            }
//        }
    }
    
    /**
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. Dividing an imaginary quadratic integer by 
     * a purely real integer, even if the purely real integer is presented as 
     * being from another ring, should still give a result if it is indeed 
     * divisible.
     */
//    @Test
    public void testDividesIntAsIQI() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger testDivisor;
//        QuadraticInteger expResult, result;
//        QuadraticInteger testDividend = new ImaginaryQuadraticInteger(3, 1, RING_EISENSTEIN);
//        testDivisor = new ImaginaryQuadraticInteger(2, 0, RING_GAUSSIAN);
//        expResult = new ImaginaryQuadraticInteger(3, 1, RING_EISENSTEIN, 2);
//        String failMessage = "Trying to divide " + testDividend.toString() + " by " + testDivisor.toString() + " from " + testDivisor.getRing().toString() + " should not have triggered";
//        try {
//            result = testDividend.divides(testDivisor);
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
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. Trying to divide an imaginary quadratic 
     * integer by an imaginary quadratic integer from another ring, with both of 
     * them having nonzero real part and nonzero imaginary part, should cause 
     * {@link algebraics.AlgebraicDegreeOverflowException}.
     */
//    @Test
    public void testDividesAlgebraicDegreeOverflow() {
        fail("REWRITE THIS TEST");
//        QuadraticInteger result, temp;
//        String msg;
//        for (int j = 0; j < totalTestIntegers - 1; j++) {
//            msg = "Dividing " + testIntegers.get(j).toString() + " by " 
//                    + testIntegers.get(j + 1).toString() + " should not have";
//            try {
//                result = testIntegers.get(j).divides(testIntegers.get(j + 1));
//                msg = msg + " resulted in " + result.toString() 
//                        + " without triggering AlgebraicDegreeOverflowException";
//                fail(msg);
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                msg = "Necessary degree should be 4, not " 
//                        + adoe.getNecessaryAlgebraicDegree() 
//                        + ", for multiplying " + testIntegers.get(j).toString() 
//                        + " by " + testIntegers.get(j + 1).toString();
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
//            temp = testIntegers.get(j + 1).times(testNorms.get(j));
//            msg = "Dividing " + temp.toString() + " from " 
//                    + temp.getRing().toString() + " by " 
//                    + testNorms.get(j).toASCIIString() + " from " 
//                    + testNorms.get(j).getRing().toString() 
//                    + " should not have caused";
//            try {
//                result = temp.divides(testNorms.get(j));
//                System.out.println("Dividing " + temp.toASCIIString() + " from " 
//                        + temp.getRing().toASCIIString() + " by " 
//                        + testNorms.get(j).toASCIIString() + " from " 
//                        + testNorms.get(j).getRing().toASCIIString() 
//                        + " gives result " + result.toASCIIString());
//            } catch (AlgebraicDegreeOverflowException adoe) {
//                msg = msg + " AlgebraicDegreeOverflowException \"" 
//                        + adoe.getMessage() + "\"";
//                fail(msg);
//            } catch (NotDivisibleException nde) {
//                msg = msg + " NotDivisibleException \"" + nde.getMessage() 
//                        + "\"";
//                fail(msg);
//            } catch (Exception e) {
//                msg = msg + " Exception \"" + e.getMessage() + "\"";
//                fail(msg);
//            }
//        }
    }
    
    /**
     * Test of divides method, of class ImaginaryQuadraticInteger, inherited 
     * from {@link QuadraticInteger}. Dividing a purely imaginary number from 
     * one domain by a purely imaginary number from another domain either gives 
     * the proper real quadratic result or triggers {@link 
     * algebraics.UnsupportedNumberDomainException}. It will be enough to check 
     * &radic;(&minus;10)/&radic;(&minus;2), which should give &radic;5 only if 
     * there is a way for this to give a real quadratic integer as a result.
     */
//    @Test
    public void testDividesCrossDomainResult() {
        fail("REWRITE THIS TEST");
//        QuadraticInteger testDividend = new ImaginaryQuadraticInteger(0, 1, 
//                new ImaginaryQuadraticRing(-10));
//        QuadraticInteger testDivisor = new ImaginaryQuadraticInteger(0, 1, 
//                RING_ZI2);
//        RealQuadraticRing expectedRingRe = new RealQuadraticRing(5);
//        QuadraticInteger expected = new RealQuadraticInteger(0, 1, 
//                expectedRingRe);
//        String msgPart = "Dividing " + testDividend.toString() + " by " 
//                + testDivisor.toString() + " should not have";
//        try {
//            QuadraticInteger actual = testDividend.divides(testDivisor);
//            String msg = "Dividing " + testDividend.toString() + " by " 
//                    + testDivisor.toString() + " should result in " + expected.toString() + ".";
//            assertEquals(msg, expected, actual);
//        } catch (UnsupportedNumberDomainException unde) {
//            System.out.println("Dividing " + testDividend.toASCIIString() 
//                    + " by " + testDivisor.toASCIIString() 
//                    + " triggered UnsupportedNumberDomainException \"" 
//                    + unde.getMessage() + "\"");
//        } catch (AlgebraicDegreeOverflowException adoe) {
//            String msg = msgPart + " triggered AlgebraicDegreeOverflowException \"" 
//                    + adoe.getMessage() + "\"";
//            fail(msg);
//        } catch(NotDivisibleException nde) {
//            String msg = msgPart + " triggered NotDivisibleException \"" + nde.getMessage() + "\"";
//            fail(msg);
//        } catch (RuntimeException re) {
//            String msg = msgPart + " caused" + re.getClass().getName() + "\"" 
//                    + re.getMessage() + "\"";
//            fail(msg);
//        }
    }
    
    /**
     * Test of the mod function, of the QuadraticInteger class. The number 14 + 
     * sqrt(-2) modulo 14 should be sqrt(-2).
     */
//    @Test
    public void testMod() {
        System.out.println("mod");
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(14, 
//                1, RING_ZI2);
//        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(14, 0, 
//                RING_ZI2);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(0, 1, 
//                RING_ZI2);
//        QuadraticInteger actual = dividend.mod(divisor);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. If m is 
     * divisible by n, m mod n should be 0.
     */
//    @Test
    public void testModZero() {
        fail("REWRITE THIS TEST");
//        int a = 2 * RANDOM.nextInt(128) + 1;
//        int b = 2 * RANDOM.nextInt(a) + 1;
//        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(a, b, 
//                RING_EISENSTEIN, 2);
//        ImaginaryQuadraticInteger division = new ImaginaryQuadraticInteger(b, 
//                -a, RING_EISENSTEIN, 2);
//        QuadraticInteger dividend = division.times(divisor);
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(0, 0, 
//                RING_EISENSTEIN);
//        QuadraticInteger actual = dividend.mod(divisor);
//        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. One 
     * pseudorandom quadratic integer is divided by another and the remainder is 
     * taken. That remainder is not checked directly. Instead, it is subtracted 
     * from the dividend, resulting in a number which divided by the divisor 
     * should give 0.
     */
//    @Test
    public void testOtherMod() {
        fail("REWRITE THIS TEST");
//        int a = 2 * RANDOM.nextInt(224) + 33;
//        int b = 2 * RANDOM.nextInt(a) + 1;
//        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(a, b, 
//                RING_OQI7, 2);
//        int multiplier = RANDOM.nextInt(21) + 3;
//        ImaginaryQuadraticInteger dividend 
//                = new ImaginaryQuadraticInteger(multiplier * a, multiplier * b 
//                        - b, RING_OQI7);
//        QuadraticInteger remainder = dividend.mod(divisor);
//        QuadraticInteger roundedDividend = dividend.minus(remainder);
//        String msg = "Since " + dividend.toString() + " mod " 
//                + divisor.toString() + " is said to be " + remainder.toString() 
//                + ", " + roundedDividend.toString() 
//                + " should be a multiple of " + divisor.toString();
//        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(0, 0, 
//                RING_OQI7);
//        QuadraticInteger actual = roundedDividend.mod(divisor);
//        assertEquals(msg, expected, actual);
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. If the 
     * dividend and the divisor are from different imaginary rings, an {@link 
     * AlgebraicDegreeOverflowException} should occur.
     */@org.junit.Ignore
    @Test
    public void testModCrossDomain() {
        fail("REWRITE THIS TEST");
//        int a = RANDOM.nextInt(2048) + 1;
//        int b = RANDOM.nextInt(a) | a;
//        a -= 1024;
//        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(a, b, 
//                RING_ZI2);
//        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(a, b, 
//                RING_OQI7);
//        try {
//            QuadraticInteger result = dividend.mod(divisor);
//            String msg = dividend.toString() + " mod " + divisor.toString() 
//                    + " should not have given result " + result.toString();
//            fail(msg);
//        } catch (AlgebraicDegreeOverflowException adoe) {
//            System.out.println(dividend.toString() + " mod " 
//                    + divisor.toString() 
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
     * Test of the times, norm and abs functions, simultaneously, of class 
     * ImaginaryQuadraticInteger. This test also does a little bit with 
     * getRealPartNumeric and getImagPartNumeric. So if the independent tests 
     * for any of those are failing, the result of this test is meaningless.
     */
//    @Test
    public void testSimultTimesAndNormAndAbs() {
        fail("REWRITE THIS TEST, or maybe delete this test");
    }
    
    /**
     * Test of applyOmega method, of class ImaginaryQuadraticInteger.
     */
//    @Test
    public void testApplyOmega() {
        System.out.println("applyOmega");
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger expResult = new ImaginaryQuadraticInteger(-1, 1, RING_EISENSTEIN, 2);
//        ImaginaryQuadraticInteger result = ImaginaryQuadraticInteger.applyOmega(0, 1);
//        assertEquals(expResult, result);
//        expResult = new ImaginaryQuadraticInteger(1, 1, RING_EISENSTEIN, 2);
//        result = ImaginaryQuadraticInteger.applyOmega(1, 1);
//        assertEquals(expResult, result);
//        expResult = new ImaginaryQuadraticInteger(0, -1, RING_EISENSTEIN);
//        result = ImaginaryQuadraticInteger.applyOmega(-1, -2);
//        assertEquals(expResult, result);
    }
    
    /**
     * Simultaneous test of applyOmega method, of class 
     * ImaginaryQuadraticInteger, and applyTheta method, of class {@link 
     * QuadraticInteger}. Since &omega; = &minus;1/2 + &radic;&minus;3/2 and 
     * &theta; = 1/2 + &radic;&minus;3/2 for <b>Z</b>[&omega;], the results of 
     * applyOmega and applyTheta should differ.
     */
//    @Test
    public void testApplyOmegaThetaDiffer() {
        fail("REWRITE THIS TEST");
//        ImaginaryQuadraticInteger thruOmega;
//        QuadraticInteger thruTheta;
//        for (int a = -1; a < 9; a++) {
//            for (int b = 1; b < 9; b++) {
//                thruOmega = ImaginaryQuadraticInteger.applyOmega(a, b);
//                thruTheta = QuadraticInteger.applyTheta(a, b, RING_EISENSTEIN);
//                assertNotEquals(thruOmega, thruTheta);
//            }
//        }
    }
    
    @Test
    public void testToUnaryInteger() {
        System.out.println("toUnaryInteger");
        int a = randomNumber();
        QuadraticRing ring = chooseRing();
        QuadraticInteger instance = new ImaginaryQuadraticInteger(a, 0, ring);
        UnaryInteger expected = new UnaryInteger(a);
        UnaryInteger actual = instance.toUnaryInteger();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToUnaryIntegerOverflowsAlgebraicDegree() {
        int a = randomNumber();
        int b = randomNumber() | randomPowerOfTwo();
        QuadraticRing ring = chooseRing();
        QuadraticInteger instance = new ImaginaryQuadraticInteger(a, b, ring);
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

    // TODO: Break up this constructor test into smaller tests
    /**
     * Test of ImaginaryQuadraticInteger class constructor. The main thing we're 
     * testing here is that an invalid argument triggers an 
     * {@link IllegalArgumentException}.
     */
//    @Test
    public void testConstructor() {
        fail("REWRITE THIS TEST, probably using assertThrows()");
// Denominator 4
// Denominator 2 with mismatched parities
    }
    
    @Test
    public void testConstructorRejectsNullRing() {
        int a = randomNumber();
        int b = randomNumber();
        int denom = RANDOM.nextBoolean() ? 2 : 1;
        String msg = "Should not be able to create instance with a = " + a 
                + ", b = " + b + ", denom = " + denom + " and null ring";
        Throwable t = assertThrows(() -> {
            QuadraticInteger badInstance = new ImaginaryQuadraticInteger(a, b, 
                    null, denom);
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
            QuadraticInteger badInstance = new ImaginaryQuadraticInteger(a, b, 
                    null);
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
    
}