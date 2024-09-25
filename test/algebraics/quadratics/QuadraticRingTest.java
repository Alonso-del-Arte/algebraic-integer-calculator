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

import arithmetic.PowerBasis;
import calculators.NumberTheoreticFunctionsCalculator;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberOtherThan;
import fractions.Fraction;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;
import static testframe.api.Asserters.assertThrows;

/**
 * Tests of the QuadraticRing class.
 * @author Alonso del Arte
 */
public class QuadraticRingTest {
    
    private static final Random RANDOM = new Random();
    
    /**
     * The delta value to use when assertEquals() requires a delta value.
     */
    static final double TEST_DELTA = 0.00000001;
    
    /**
     * Test of the getRadicand function, of the QuadraticRing class.
     */
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        int d = RANDOM.nextInt();
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing ring = new QuadraticRingImpl(d);
        int expected = d;
        int actual = ring.getRadicand();
        assertEquals(expected, actual);
    }

    /**
     * Test of the getAbsNegRad function, of the QuadraticRing class.
     */
    @Test
    public void testGetAbsNegRad() {
        System.out.println("getAbsNegRad");
        int d = RANDOM.nextInt();
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing ring = new QuadraticRingImpl(d);
        int expected = Math.abs(d);
        int actual = ring.getAbsNegRad();
        assertEquals(expected, actual);
    }

    /**
     * Test of the getAbsNegRadSqrt function, of the QuadraticRing class.
     */
    @Test
    public void testGetAbsNegRadSqrt() {
        System.out.println("getAbsNegRadSqrt");
        int d = RANDOM.nextInt();
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing ring = new QuadraticRingImpl(d);
        double expected = Math.sqrt(Math.abs(d));
        double actual = ring.getAbsNegRadSqrt();
        assertEquals(expected, actual, TEST_DELTA);
    }

    /**
     * Test of the getPowerBasis function, of the QuadraticRing class.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        int d = RANDOM.nextInt();
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing ring = new QuadraticRingImpl(d);
        Fraction one = new Fraction(1);
        Fraction[] ones = {one, one};
        PowerBasis expected = new PowerBasis(ones);
        PowerBasis actual = ring.getPowerBasis();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        int d = randomSquarefreeNumber(8192);
        QuadraticRing ring = new QuadraticRingImpl(d);
        assertEquals(ring, ring);
    }
    
    @Test
    public void testNotEqualsNull() {
        int d = -randomSquarefreeNumber(8192);
        QuadraticRing ring = new QuadraticRingImpl(d);
        assertNotEquals(ring, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        int d = randomSquarefreeNumber(8192);
        QuadraticRing unexpected = new QuadraticRingImpl(d);
        QuadraticRing actual = new QuadraticRing(d) {
            
            @Override
            public boolean isPurelyReal() {
                return true;
            }
            
        };
        assertNotEquals(unexpected, actual);
    }

    /**
     * Test of the equals function, of the QuadraticRing class.
     */
    @Test
    public void testEquals() {
        int signum = RANDOM.nextBoolean() ? 1 : -1;
        int d = signum * randomSquarefreeNumber(8192);
        QuadraticRing someRing = new QuadraticRingImpl(d);
        QuadraticRing sameRing = new QuadraticRingImpl(d);
        assertEquals(someRing, sameRing);
    }
    
    @Test
    public void testNotEqualsDiffDiscr() {
        int dA = -randomSquarefreeNumber(8192);
        int dB = randomSquarefreeNumber(8192);
        QuadraticRing ringA = new QuadraticRingImpl(dA);
        QuadraticRing ringB = new QuadraticRingImpl(dB);
        String message = ringA.toString() 
                + " should not be considered equal to " + ringB.toString();
        assertNotEquals(message, ringA, ringB);
    }

    /**
     * Test of the hashCode function, of the QuadraticRing class.
     */
    @Test
    public void testHashCode() {
        int initialCapacity = randomNumber(64) + 16;
        Set<QuadraticRing> rings = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            int signum = (i % 2 == 0) ? 1 : -1;
            int d = signum * randomSquarefreeNumber(8192);
            QuadraticRing ring = new QuadraticRingImpl(d);
            rings.add(ring);
            hashes.add(ring.hashCode());
        }
        int expected = rings.size();
        int actual = hashes.size();
        String message = "Set of " + expected 
                + " should correspond to as many hashes";
        assertEquals(message, expected, actual);
    }

    /**
     * Test of getMaxAlgebraicDegree method, of class QuadraticRing.
     */
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        int d = RANDOM.nextInt();
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing ring = new QuadraticRingImpl(d);
        assertEquals(2, ring.getMaxAlgebraicDegree());
    }

    @Test
    public void testApplyImaginary() {
        int d = -randomSquarefreeNumber(16384);
        QuadraticRing expected = new ImaginaryQuadraticRing(d);
        QuadraticRing actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyReal() {
        int propD = randomSquarefreeNumber(4096);
        int d = (propD == 1) ? 2 : propD;
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing expected = new RealQuadraticRing(d);
        QuadraticRing actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyRejectsZero() {
        try {
            QuadraticRing badRing = QuadraticRing.apply(0);
            String message = "apply(0) should not have given " 
                    + badRing.toString();
            fail(message);
        } catch (IllegalArgumentException iae) {
            System.out.println("apply(0) caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            assert excMsg != null : "Message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for apply(0)";
            fail(message);
        }
    }

    @Test
    public void testApplyRejectsSquareMultiple() {
        int d = RANDOM.nextInt() * 36;
        try {
            QuadraticRing badRing = QuadraticRing.apply(d);
            String message = "apply(" + d + ") should not have given " 
                    + badRing.toString();
            fail(message);
        } catch (IllegalArgumentException iae) {
            System.out.println("apply(" + d 
                    + ") caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            assert excMsg != null : "Message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for apply(" + d + ")";
            fail(message);
        }
    }
    
    @Test
    public void testConstructorRejectsNonSquarefreeD() {
        int n = randomSquarefreeNumber(Byte.MAX_VALUE);
        int signum = (n % 2 == 0) ? 1 : -1;
        int square = n * n;
        int d = signum * square * randomSquarefreeNumber(Short.MAX_VALUE);
        String msg = "Parameter d = " + d 
                + " should be rejected for quadratic ring, divisible by " 
                + square;
        Throwable t = assertThrows(() -> {
            QuadraticRing badRing = new QuadraticRingImpl(d);
            System.out.println("Should not have been able to create " 
                    + badRing.toString() + " with d = " + d);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    public static class QuadraticRingImpl extends QuadraticRing {

        @Override
        public boolean isPurelyReal() {
            return this.radicand > 0;
        }

        public QuadraticRingImpl(int d) {
            super(d);
        }

    }
    
}
