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

import algebraics.PowerBasis;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the QuadraticRing class.
 * @author Alonso del Arte
 */
public class QuadraticRingTest {
    
    /**
     * The delta value to use when assertEquals() requires a delta value.
     */
    public static final double TEST_DELTA = 0.00000001;
    
    /**
     * Test of getRadicand method, of class QuadraticRing.
     */
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        QuadraticRing instance = null;
        int expResult = 0;
        int result = instance.getRadicand();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRadSqrt method, of class QuadraticRing.
     */
    @Test
    public void testGetRadSqrt() {
        System.out.println("getRadSqrt");
        QuadraticRing instance = null;
        double expResult = 0.0;
        double result = instance.getRadSqrt();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAbsNegRad method, of class QuadraticRing.
     */
    @Test
    public void testGetAbsNegRad() {
        System.out.println("getAbsNegRad");
        QuadraticRing instance = null;
        int expResult = 0;
        int result = instance.getAbsNegRad();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAbsNegRadSqrt method, of class QuadraticRing.
     */
    @Test
    public void testGetAbsNegRadSqrt() {
        System.out.println("getAbsNegRadSqrt");
        QuadraticRing instance = null;
        double expResult = 0.0;
        double result = instance.getAbsNegRadSqrt();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPowerBasis method, of class QuadraticRing.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
//        QuadraticRing instance = null;
//        PowerBasis expResult = null;
//        PowerBasis result = instance.getPowerBasis();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
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
//        QuadraticRing instance = null;
//        int expResult = 0;
//        int result = instance.getMaxAlgebraicDegree();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of apply method, of class QuadraticRing.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        int d = 0;
        QuadraticRing expResult = null;
        QuadraticRing result = QuadraticRing.apply(d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class QuadraticRingImpl extends QuadraticRing {

        @Override
        public boolean isPurelyReal() {
            return false;
        }

        @Override
        public double getRadSqrt() {
            return 0.0;
        }

        @Override
        public int getAbsNegRad() {
            return 0;
        }

        @Override
        public double getAbsNegRadSqrt() {
            return 0.0;
        }

        public QuadraticRingImpl() {
            super(0);
        }

    }
    
}
