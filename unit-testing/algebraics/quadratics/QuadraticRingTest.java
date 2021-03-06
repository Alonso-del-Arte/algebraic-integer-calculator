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
package algebraics.quadratics;

import arithmetic.PowerBasis;
import calculators.NumberTheoreticFunctionsCalculator;
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
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
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
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
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
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
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
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
        QuadraticRing ring = new QuadraticRingImpl(d);
        Fraction one = new Fraction(1);
        Fraction[] ones = {one, one};
        PowerBasis expected = new PowerBasis(ones);
        PowerBasis actual = ring.getPowerBasis();
        assertEquals(expected, actual);
    }

    /**
     * Test of equals method, of class QuadraticRing.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        QuadraticRing instance = null;
        boolean expResult = false;
//        boolean result = instance.equals(obj);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class QuadraticRing.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
//        QuadraticRing instance = null;
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxAlgebraicDegree method, of class QuadraticRing.
     */
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        int d = RANDOM.nextInt();
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
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
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
        QuadraticRing expected = new ImaginaryQuadraticRing(d);
        QuadraticRing actual = QuadraticRing.apply(d);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyRandomReal() {
        int d = RANDOM.nextInt(4096) + 2;
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) d++;
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

    public class QuadraticRingImpl extends QuadraticRing {

        @Override
        public boolean isPurelyReal() {
            return this.radicand > 0;
        }

        public QuadraticRingImpl(int d) {
            super(d);
        }

    }
    
}
