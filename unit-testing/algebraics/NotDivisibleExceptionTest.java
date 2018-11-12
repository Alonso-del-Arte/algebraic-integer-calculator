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

import algebraics.quadratics.ImaginaryQuadraticRingTest;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.QuadraticInteger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the NotDivisibleException class. The purpose of this test class is 
 * only to make sure the exception object works as it should. Testing whether 
 * this exception is thrown for the right reasons or not is the responsibility 
 * of other test classes.
 * @author Alonso del Arte
 */
public class NotDivisibleExceptionTest {
    
    private static NotDivisibleException notDivGaussian;
    private static NotDivisibleException notDivEisenstein;
    // TODO: Add one for a real quadratic integer ring

    @BeforeClass
    public static void setUpClass() {
        ImaginaryQuadraticRing ringGaussian = new ImaginaryQuadraticRing(-1);
        String initMsg = "Initialization state, not the result of an actually thrown exception.";
        long[] initNumers = {0L, 0L};
        long[] initDenoms = {0L, 0L};
        notDivGaussian = new NotDivisibleException(initMsg, initNumers, initDenoms, ringGaussian);
        ImaginaryQuadraticRing ringEisenstein = new ImaginaryQuadraticRing(-3);
        notDivEisenstein = new NotDivisibleException(initMsg, initNumers, initDenoms, ringEisenstein);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(5, 1, ringGaussian);
        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(3, 1, ringGaussian);
        QuadraticInteger division;
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
            // This would merit a fail if occurred in a test.
        } catch (NotDivisibleException nde) {
            notDivGaussian = nde;
        }
        System.out.println("NotDivisibleException for the Gaussian integers example has this message: \"" + notDivGaussian.getMessage() + "\"");
        dividend = new ImaginaryQuadraticInteger(61, 0, ringEisenstein);
        divisor = new ImaginaryQuadraticInteger(1, 9, ringEisenstein);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
            // This would merit a fail if occurred in a test.
        } catch (NotDivisibleException nde) {
            notDivEisenstein = nde;
        }
        System.out.println("NotDivisibleException for the Eisenstein integers example has this message: \"" + notDivEisenstein.getMessage() + "\"");
    }
    
    /**
     * Test of getResFractNumers method, of class NotDivisibleException.
     */
    @Test
    public void testGetResFractNumers() {
        System.out.println("getResFractNumers");
        long[] expResult = {8L, -1L};
        long[] result = notDivGaussian.getResFractNumers();
        assertArrayEquals(expResult, result);
        expResult[0] = 1L;
        expResult[1] = -9L;
        result = notDivEisenstein.getResFractNumers();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getResFractDenoms method, of class NotDivisibleException.
     */
    @Test
    public void testGetResFractDenoms() {
        System.out.println("getResFractDenoms");
        long[] expResult = {5L, 5L};
        long[] result = notDivGaussian.getResFractDenoms();
        assertArrayEquals(expResult, result);
        expResult[0] = 4L;
        expResult[1] = 4L;
        result = notDivEisenstein.getResFractDenoms();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getRing method, of class NotDivisibleException.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        ImaginaryQuadraticRing expResult = new ImaginaryQuadraticRing(-1);
        IntegerRing result = notDivGaussian.getRing();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticRing(-3);
        result = notDivEisenstein.getRing();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumericRealPart method, of class NotDivisibleException.
     */
    @Test
    public void testGetNumericRealPart() {
        System.out.println("getNumericRealPart");
        double expResult, result;
        expResult = 1.0 / 4.0;
        result = notDivEisenstein.getNumericRealPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        expResult = 8.0 / 5.0;
        result = notDivGaussian.getNumericRealPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
    }

    /**
     * Test of getNumericImagPartMult method, of class NotDivisibleException.
     */
    @Test
    public void testGetNumericImagPartMult() {
        System.out.println("getNumericImagPartMult");
        double expResult, result;
        expResult = -9.0 / 4.0;
        result = notDivEisenstein.getNumericImagPartMult();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        expResult = -1.0 / 5.0;
        result = notDivGaussian.getNumericImagPartMult();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
    }

    /**
     * Test of getNumericImagPart method, of class NotDivisibleException.
     */
    @Test
    public void testGetNumericImagPart() {
        System.out.println("getNumericImagPart");
        double expResult, result;
        expResult = -9.0 * Math.sqrt(3) / 4.0;
        result = notDivEisenstein.getNumericImagPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        expResult = -1.0 / 5.0;
        result = notDivGaussian.getNumericImagPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
    }

    private void sortIQIArray(ImaginaryQuadraticInteger[] iqiArray) {
        ImaginaryQuadraticInteger swapper;
        boolean swapFlag;
        do {
            swapFlag = false;
            for (int i = 0; i < iqiArray.length - 1; i++) {
                if (iqiArray[i].norm() > iqiArray[i + 1].norm()) {
                    swapper = iqiArray[i];
                    iqiArray[i] = iqiArray[i + 1];
                    iqiArray[i + 1] = swapper;
                    swapFlag = true;
                }
            }
        } while (swapFlag);
    }
    
    /**
     * Test of getBoundingIntegers method, of class NotDivisibleException.
     */
    @Test
    public void testGetBoundingIntegers() {
        System.out.println("getBoundingIntegers");
        fail("The test case is a prototype.");
    }

    /**
     * Test of roundTowardsZero method, of class NotDivisibleException.
     */
    @Test
    public void testRoundTowardsZero() {
        System.out.println("roundTowardsZero");
        fail("The test case is a prototype.");
    }

    /**
     * Test of roundAwayFromZero method, of class NotDivisibleException.
     */
    @Test
    public void testRoundAwayFromZero() {
        System.out.println("roundAwayFromZero");
        fail("The test case is a prototype.");
    }
    
}
