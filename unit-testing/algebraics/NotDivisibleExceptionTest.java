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
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the NotDivisibleException class. The purpose of this test class is 
 * only to make sure the exception object works as it should. Testing whether 
 * this exception is thrown for the right reasons or not is the responsibility 
 * of other test classes. However, these tests do require that the division 
 * function in the pertinent classes satisfies the requirements of the 
 * NotDivisibleException class. 
 * @author Alonso del Arte
 */
public class NotDivisibleExceptionTest {
    
    /**
     * An exception to correspond to (5 + i)/(3 + i) after setUpClass().
     */
    private static NotDivisibleException notDivGaussian;

    /**
     * An exception to correspond to 61/(1 + 9sqrt(-3)) after setUpClass().
     */
    private static NotDivisibleException notDivEisenstein;
    
    /**
     * An exception to correspond to (-3 + 8sqrt(2))/7 after setUpClass().
     */
    private static NotDivisibleException notDivZ2;
    
    /**
     * An exception to correspond to (sqrt(5))/(15/2 - 3sqrt(5)/2) after 
     * setUpClass().
     */
    private static NotDivisibleException notDivZPhi;

    @BeforeClass
    public static void setUpClass() {
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        String initMsg = "Initialization state, not the result of an actually thrown exception.";
        long[] initNumers = {0L, 0L};
        long[] initDenoms = {1L, 1L};
        notDivGaussian = new NotDivisibleException(initMsg, initNumers, initDenoms, currRing);
        QuadraticInteger dividend = new ImaginaryQuadraticInteger(5, 1, currRing);
        QuadraticInteger divisor = new ImaginaryQuadraticInteger(3, 1, currRing);
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
        currRing = new ImaginaryQuadraticRing(-3);
        notDivEisenstein = new NotDivisibleException(initMsg, initNumers, initDenoms, currRing);
        dividend = new ImaginaryQuadraticInteger(61, 0, currRing);
        divisor = new ImaginaryQuadraticInteger(1, 9, currRing);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
        } catch (NotDivisibleException nde) {
            notDivEisenstein = nde;
        }
        System.out.println("NotDivisibleException for the Eisenstein integers example has this message: \"" + notDivEisenstein.getMessage() + "\"");
        currRing = new RealQuadraticRing(2);
        notDivZ2 = new NotDivisibleException(initMsg, initNumers, initDenoms, currRing);
        dividend = new RealQuadraticInteger(-3, 8, currRing);
        divisor = new RealQuadraticInteger(7, 0, currRing);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
        } catch (NotDivisibleException nde) {
            notDivZ2 = nde;
        }
        System.out.println("NotDivisibleException for the Z[sqrt(2)] example has this message: \"" + notDivZ2.getMessage() + "\"");
        currRing = new RealQuadraticRing(5);
        notDivZPhi = new NotDivisibleException(initMsg, initNumers, initDenoms, currRing);
        dividend = new RealQuadraticInteger(0, 1, currRing);
        divisor = new RealQuadraticInteger(15, -3, currRing, 2);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
        } catch (NotDivisibleException nde) {
            notDivZPhi = nde;
        }
        System.out.println("NotDivisibleException for the Z[phi] example has this message: \"" + notDivZPhi.getMessage() + "\"");
    }
    
    /**
     * Test of getFractNumers method, of class NotDivisibleException.
     */
    @Test
    public void testGetFractNumers() {
        System.out.println("getFractNumers");
        long[] expResult = {8L, -1L};
        long[] result = notDivGaussian.getFractNumers();
        assertArrayEquals(expResult, result);
        expResult[0] = 1L;
        expResult[1] = -9L;
        result = notDivEisenstein.getFractNumers();
        assertArrayEquals(expResult, result);
        expResult[0] = -3L;
        expResult[1] = 8L;
        result = notDivZ2.getFractNumers();
        assertArrayEquals(expResult, result);
        expResult[0] = 1L;
        expResult[1] = 1L;
        result = notDivZPhi.getFractNumers();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getFractDenoms method, of class NotDivisibleException.
     */
    @Test
    public void testGetFractDenoms() {
        System.out.println("getFractDenoms");
        long[] expResult = {5L, 5L};
        long[] result = notDivGaussian.getFractDenoms();
        assertArrayEquals(expResult, result);
        expResult[0] = 4L;
        expResult[1] = 4L;
        result = notDivEisenstein.getFractDenoms();
        assertArrayEquals(expResult, result);
        expResult[0] = 7L;
        expResult[1] = 7L;
        result = notDivZ2.getFractDenoms();
        assertArrayEquals(expResult, result);
        expResult[0] = 6L;
        expResult[1] = 6L;
        result = notDivZPhi.getFractDenoms();
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
        expResult = 1.18767;
        result = notDivZ2.getNumericRealPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        expResult = 0.53934;
        result = notDivZPhi.getNumericRealPart();
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
        expResult = 0.0;
        result = notDivZ2.getNumericImagPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
        result = notDivZPhi.getNumericImagPart();
        assertEquals(expResult, result, ImaginaryQuadraticRingTest.TEST_DELTA);
    }

    private void sortAlgIntArray(AlgebraicInteger[] algIntArray) {
        AlgebraicInteger swapper;
        boolean swapFlag;
        do {
            swapFlag = false;
            for (int i = 0; i < algIntArray.length - 1; i++) {
                if (algIntArray[i].norm() > algIntArray[i + 1].norm()) {
                    swapper = algIntArray[i];
                    algIntArray[i] = algIntArray[i + 1];
                    algIntArray[i + 1] = swapper;
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
        ImaginaryQuadraticInteger[] expResult = new ImaginaryQuadraticInteger[4];
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        expResult[0] = new ImaginaryQuadraticInteger(1, 0, currRing);
        expResult[1] = new ImaginaryQuadraticInteger(1, -1, currRing);
        expResult[2] = new ImaginaryQuadraticInteger(2, 0, currRing);
        expResult[3] = new ImaginaryQuadraticInteger(2, -1, currRing);
        AlgebraicInteger[] result = notDivGaussian.getBoundingIntegers();
        sortAlgIntArray(result);
        assertArrayEquals(expResult, result);
        currRing = new ImaginaryQuadraticRing(-3);
        expResult[0] = new ImaginaryQuadraticInteger(0, -2, currRing);
        expResult[1] = new ImaginaryQuadraticInteger(-1, -5, currRing, 2);
        expResult[2] = new ImaginaryQuadraticInteger(1, -5, currRing, 2);
        expResult[3] = new ImaginaryQuadraticInteger(0, -3, currRing);
        result = notDivEisenstein.getBoundingIntegers();
        sortAlgIntArray(result);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of roundTowardsZero method, of class NotDivisibleException.
     */
    @Test
    public void testRoundTowardsZero() {
        System.out.println("roundTowardsZero");
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(1, 0, currRing);
        AlgebraicInteger result = notDivGaussian.roundTowardsZero();
        assertEquals(expResult, result);
        currRing = new ImaginaryQuadraticRing(-3);
        expResult = new ImaginaryQuadraticInteger(0, -2, currRing);
        result = notDivEisenstein.roundTowardsZero();
        assertEquals(expResult, result);
    }

    /**
     * Test of roundAwayFromZero method, of class NotDivisibleException.
     */
    @Test
    public void testRoundAwayFromZero() {
        System.out.println("roundAwayFromZero");
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(2, -1, currRing);
        AlgebraicInteger result = notDivGaussian.roundAwayFromZero();
        assertEquals(expResult, result);
        currRing = new ImaginaryQuadraticRing(-3);
        expResult = new ImaginaryQuadraticInteger(0, -3, currRing);
        result = notDivEisenstein.roundAwayFromZero();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        String exceptionMessage = "This is an exception message for an invalidly constructed NotDivisibleException";
        long[] mismatchLenNumers = {0L, 1L, 2L, 3L};
        long[] mismatchLenDenoms = {1L, 2L, 3L, 4L, 5L};
        QuadraticRing ring = new RealQuadraticRing(19);
        try {
            throw new NotDivisibleException(exceptionMessage, mismatchLenNumers, mismatchLenDenoms, ring);
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException incorrectly created: \"" + nde.getMessage() + "\"";
            fail(failMessage);
        } catch(IllegalArgumentException iae) {
            System.out.println("Attempt to create NotDivisibleException with arrays of mismatched length correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch(Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception in this situation of arrays of mismatched length.";
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
        }
        long[] wrongLenNumers = {0L, 1L, 3L};
        long[] wrongLenDenoms = {2L, 2L, 2L};
        try {
            throw new NotDivisibleException(exceptionMessage, wrongLenNumers, wrongLenDenoms, ring);
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException incorrectly created: \"" + nde.getMessage() + "\"";
            fail(failMessage);
        } catch(IllegalArgumentException iae) {
            System.out.println("Attempt to create NotDivisibleException with arrays of excessive length correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch(Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception in this situation of arrays of excessive length.";
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
        }
        long[] wrongElemNumers = {1L, 3L};
        long[] wrongElemDenoms = {0L, 2L};
        try {
            throw new NotDivisibleException(exceptionMessage, wrongElemNumers, wrongElemDenoms, ring);
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException incorrectly created: \"" + nde.getMessage() + "\"";
            fail(failMessage);
        } catch(IllegalArgumentException iae) {
            System.out.println("Attempt to create NotDivisibleException with one instance of 0 as a denominator correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch(Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception in this situation involving a 0 as a denominator.";
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
        }
    }
    
}
