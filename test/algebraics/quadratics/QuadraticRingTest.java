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

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

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
     * Test of getRadicand method, of class QuadraticRing.
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
     * Test of getAbsNegRad method, of class QuadraticRing.
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
     * Test of getAbsNegRadSqrt method, of class QuadraticRing.
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
     * Test of getPowerBasis method, of class QuadraticRing.
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

    /**
     * Test of equals method, of class QuadraticRing.
     */
    @Test
    public void testEquals() {
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of hashCode method, of class QuadraticRing.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        fail("REWRITE THIS TEST");
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

    /**
     * Test of apply method, of class QuadraticRing.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        int d = -1;
        QuadraticRing expected = new ImaginaryQuadraticRing(d);
        QuadraticRing actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
        d = 2;
        expected = new RealQuadraticRing(d);
        actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyRandomImaginary() {
        int d = -RANDOM.nextInt(4096) - 1;
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing expected = new ImaginaryQuadraticRing(d);
        QuadraticRing actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyRandomReal() {
        int d = RANDOM.nextInt(4096) + 2;
        while (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) d++;
        QuadraticRing expected = new RealQuadraticRing(d);
        QuadraticRing actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyRejectsZero() {
        try {
            QuadraticRing badRing = QuadraticRing.apply(0);
            String msg = "apply(0) should not have given " + badRing.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("apply(0) caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            assert excMsg != null : "Message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for apply(0)";
            fail(msg);
        }
    }

    @Test
    public void testApplyRejectsSquareMultiple() {
        int d = RANDOM.nextInt() * 36;
        try {
            QuadraticRing badRing = QuadraticRing.apply(d);
            String msg = "apply(" + d + ") should not have given " 
                    + badRing.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("apply(" + d 
                    + ") caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            assert excMsg != null : "Message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for apply(" + d + ")";
            fail(msg);
        }
    }

    static class QuadraticRingImpl extends QuadraticRing {

        @Override
        public boolean isPurelyReal() {
            return this.radicand > 0;
        }

        QuadraticRingImpl(int d) {
            super(d);
        }

    }
    
}
