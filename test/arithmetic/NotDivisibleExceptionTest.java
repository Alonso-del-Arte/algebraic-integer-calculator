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
package arithmetic;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import fractions.Fraction;

import java.util.Arrays;

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
     * The delta value to use when assertEquals() requires a delta value.
     */
    public static final double TEST_DELTA = 0.00000001;
    
    /**
     * An exception to correspond to (5 + i)/(3 + i) after setUpClass().
     */
    private static NotDivisibleException notDivGaussian;

    /**
     * An exception to correspond to 61/(1 + 9sqrt(-3)) after setUpClass().
     */
    private static NotDivisibleException notDivEisenstein;
    
    /**
     * An exception to correspond to (3 - 8sqrt(2))/7 after setUpClass().
     */
    private static NotDivisibleException notDivZ2;
    
    /**
     * An exception to correspond to (sqrt(5))/(15/2 - 3sqrt(5)/2) after 
     * setUpClass().
     */
    private static NotDivisibleException notDivZPhi;

    /**
     * Sets up the NotDivisibleException objects to use in the tests. If any of 
     * these objects fail to initialize properly, it will have the exception 
     * detail message "Initialization state, not the result of an actually 
     * thrown exception."
     */
    @BeforeClass
    public static void setUpClass() {
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        String initMsg = "Initialization state, not the result of an actually thrown exception.";
        ImaginaryQuadraticInteger initDividend 
                = new ImaginaryQuadraticInteger(0, 0, currRing);
        ImaginaryQuadraticInteger initDivisor 
                = new ImaginaryQuadraticInteger(1, 0, currRing);
        Fraction zero = new Fraction(0);
        Fraction[] initFracts = {zero, zero};
        notDivGaussian = new NotDivisibleException(initMsg, initDividend, 
                initDivisor, initFracts);
        QuadraticInteger dividend = new ImaginaryQuadraticInteger(5, 1, currRing);
        QuadraticInteger divisor = new ImaginaryQuadraticInteger(3, 1, currRing);
        QuadraticInteger division;
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
            // This would merit a fail if occurred in a test.
        } catch (NotDivisibleException nde) {
            notDivGaussian = nde;
        }
        System.out.println("NotDivisibleException for the Gaussian integers example has this message: \"" + notDivGaussian.getMessage() + "\"");
        currRing = new ImaginaryQuadraticRing(-3);
        notDivEisenstein = new NotDivisibleException(initMsg, initDividend, 
                initDivisor, initFracts);
        dividend = new ImaginaryQuadraticInteger(61, 0, currRing);
        divisor = new ImaginaryQuadraticInteger(1, 9, currRing);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
        } catch (NotDivisibleException nde) {
            notDivEisenstein = nde;
        }
        System.out.println("NotDivisibleException for the Eisenstein integers example has this message: \"" + notDivEisenstein.getMessage() + "\"");
        currRing = new RealQuadraticRing(2);
        notDivZ2 = new NotDivisibleException(initMsg, initDividend, initDivisor, 
                initFracts);
        dividend = new RealQuadraticInteger(3, -8, currRing);
        divisor = new RealQuadraticInteger(7, 0, currRing);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
        } catch (NotDivisibleException nde) {
            notDivZ2 = nde;
        }
        System.out.println("NotDivisibleException for the Z[sqrt(2)] example has this message: \"" + notDivZ2.getMessage() + "\"");
        currRing = new RealQuadraticRing(5);
        notDivZPhi = new NotDivisibleException(initMsg, initDividend, 
                initDivisor, initFracts);
        dividend = new RealQuadraticInteger(0, 1, currRing);
        divisor = new RealQuadraticInteger(15, -3, currRing, 2);
        try {
            division = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is " + division.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.err.println("AlgebraicDegreeOverflowException should not have occurred in this context: " + adoe.getMessage());
        } catch (NotDivisibleException nde) {
            notDivZPhi = nde;
        }
        System.out.println("NotDivisibleException for the Z[phi] example has this message: \"" + notDivZPhi.getMessage() + "\"");
    }
    
    /**
     * Test of getFractions method, of class NotDivisibleException.
     */
    @Test
    public void testGetFractions() {
        System.out.println("getFractions");
        Fraction fractRe = new Fraction(8, 5);
        Fraction fractImMult = new Fraction(-1, 5);
        Fraction[] expResult = {fractRe, fractImMult};
        Fraction[] result = notDivGaussian.getFractions();
        assertArrayEquals(expResult, result);
        fractRe = new Fraction(1, 4);
        fractImMult = new Fraction(-9, 4);
        expResult[0] = fractRe;
        expResult[1] = fractImMult;
        result = notDivEisenstein.getFractions();
        assertArrayEquals(expResult, result);
        fractRe = new Fraction(3, 7);
        fractImMult = new Fraction(-8, 7);
        expResult[0] = fractRe;
        expResult[1] = fractImMult;
        result = notDivZ2.getFractions();
        assertArrayEquals(expResult, result);
        fractRe = new Fraction(1, 6);
        expResult[0] = fractRe;
        expResult[1] = fractRe;
        result = notDivZPhi.getFractions();
        assertArrayEquals(expResult, result);
    }
        
    /**
     * Test of getCausingDividend method, of class NotDivisibleException.
     */
    @Test
    public void testGetCausingDividend() {
        QuadraticRing ring = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(5, 1, ring);
        AlgebraicInteger result = notDivGaussian.getCausingDividend();
        assertEquals(expResult, result);
        ring = new ImaginaryQuadraticRing(-3);
        expResult = new ImaginaryQuadraticInteger(61, 0, ring);
        result = notDivEisenstein.getCausingDividend();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCausingDividend method, of class NotDivisibleException.
     */
    @Test
    public void testGetCausingDivisor() {
        QuadraticRing ring = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(3, 1, ring);
        AlgebraicInteger result = notDivGaussian.getCausingDivisor();
        assertEquals(expResult, result);
        ring = new ImaginaryQuadraticRing(-3);
        expResult = new ImaginaryQuadraticInteger(1, 9, ring);
        result = notDivEisenstein.getCausingDivisor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCausingRing method, of class NotDivisibleException.
     */
    @Test
    public void testGetCausingRing() {
        System.out.println("getCausingRing");
        QuadraticRing expResult = new ImaginaryQuadraticRing(-1);
        IntegerRing result = notDivGaussian.getCausingRing();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticRing(-3);
        result = notDivEisenstein.getCausingRing();
        assertEquals(expResult, result);
        expResult = new RealQuadraticRing(2);
        result = notDivZ2.getCausingRing();
        assertEquals(expResult, result);
        expResult = new RealQuadraticRing(5);
        result = notDivZPhi.getCausingRing();
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of getCausingRing method, of class NotDivisibleException. 
     * This one checks that the correct ring can be inferred if the dividend and 
     * divisor are of different rings, and the applicable radicands are coprime.
     */
    @Test
    public void testGetCausingRingInferredCoprime() {
        String msg = "Example with dividend and divisor from different rings";
        RealQuadraticRing dividendRing = new RealQuadraticRing(21);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 1, 
                dividendRing);
        RealQuadraticRing divisorRing = new RealQuadraticRing(5);
        RealQuadraticInteger divisor = new RealQuadraticInteger(0, 1, 
                divisorRing);
        Fraction regPartFract = new Fraction(0);
        Fraction surdPartFract = new Fraction(1, 5);
        Fraction[] fracts = {regPartFract, surdPartFract};
        RealQuadraticRing expResult = new RealQuadraticRing(105);
        NotDivisibleException notDivExcForInference 
                = new NotDivisibleException(msg, dividend, divisor, fracts);
        IntegerRing result = notDivExcForInference.getCausingRing();
        assertEquals(expResult, result);
    }

    /**
     * Another test of getCausingRing method, of class NotDivisibleException. 
     * This one checks that the correct ring can be inferred if the dividend and 
     * divisor are of different rings, and the applicable radicands have a 
     * common divisor greater than 1.
     */
    @Test
    public void testGetCausingRingInferredCommonDivisor() {
        String msg = "Example with dividend and divisor from different rings";
        RealQuadraticRing dividendRing = new RealQuadraticRing(21);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 4, 
                dividendRing);
        RealQuadraticRing divisorRing = new RealQuadraticRing(3);
        RealQuadraticInteger divisor = new RealQuadraticInteger(0, 3, 
                divisorRing);
        Fraction regPartFract = new Fraction(0);
        Fraction surdPartFract = new Fraction(4, 3);
        Fraction[] fracts = {regPartFract, surdPartFract};
        RealQuadraticRing expResult = new RealQuadraticRing(7);
        NotDivisibleException notDivExcForInference 
                = new NotDivisibleException(msg, dividend, divisor, fracts);
        IntegerRing result = notDivExcForInference.getCausingRing();
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
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 8.0 / 5.0;
        result = notDivGaussian.getNumericRealPart();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = -1.18767;
        result = notDivZ2.getNumericRealPart();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.53934;
        result = notDivZPhi.getNumericRealPart();
        assertEquals(expResult, result, TEST_DELTA);
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
        assertEquals(expResult, result, TEST_DELTA);
        expResult = -1.0 / 5.0;
        result = notDivGaussian.getNumericImagPart();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.0;
        result = notDivZ2.getNumericImagPart();
        assertEquals(expResult, result, TEST_DELTA);
        result = notDivZPhi.getNumericImagPart();
        assertEquals(expResult, result, TEST_DELTA);
    }

    /**
     * Test of getAbs method, of class NotDivisibleException.
     */
    @Test
    public void testGetAbs() {
        System.out.println("getNumericRealPart");
        double expResult, result;
        expResult = 1.61245;
        result = notDivGaussian.getAbs();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 3.90512;
        result = notDivEisenstein.getAbs();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 1.18767;
        result = notDivZ2.getAbs();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.53934;
        result = notDivZPhi.getAbs();
        assertEquals(expResult, result, TEST_DELTA);
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
        currRing = new RealQuadraticRing(2);
        RealQuadraticInteger currElem = new RealQuadraticInteger(-1, 0, currRing);
        String msg = "Array of bounding integers for the Z[sqrt(2)] example should contain " + currElem.toASCIIString();
        result = notDivZ2.getBoundingIntegers();
        assertTrue(msg, Arrays.asList(result).contains(currElem));
        currElem = new RealQuadraticInteger(-2, 0, currRing);
        msg = "Array of bounding integers for the Z[sqrt(2)] example should contain " + currElem.toASCIIString();
        assertTrue(msg, Arrays.asList(result).contains(currElem));
        currElem = new RealQuadraticInteger(0, -1, currRing);
        msg = "Array of bounding integers for the Z[sqrt(2)] example should contain " + currElem.toASCIIString();
        assertTrue(msg, Arrays.asList(result).contains(currElem));
        currRing = new RealQuadraticRing(5);
        currElem = new RealQuadraticInteger(1, 1, currRing, 2);
        msg = "Array of bounding integers for the Z[phi] example should contain " + currElem.toASCIIString();
        result = notDivZPhi.getBoundingIntegers();
        assertTrue(msg, Arrays.asList(result).contains(currElem));
    }

    /**
     * Test of roundTowardsZero method, of class NotDivisibleException. In the 
     * case of real quadratic integers, I've gone back and forth on what the 
     * result of this function should be. For now, that part of the test is 
     * inactive.
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
        // TODO: Figure out tests for real rings
//        currRing = new RealQuadraticRing(2);
//        expResult = new ImaginaryQuadraticInteger(1, 0, currRing);
//        result = notDivZ2.roundTowardsZero();
//        assertEquals(expResult, result);
//        currRing = new RealQuadraticRing(5);
//        expResult = new ImaginaryQuadraticInteger(0, 0, currRing);
//        result = notDivZPhi.roundTowardsZero();
//        assertEquals(expResult, result);
    }

    /**
     * Test of roundAwayFromZero method, of class NotDivisibleException. In the 
     * case of real quadratic integers, I've gone back and forth on what the 
     * result of this function should be. For now, that part of the test is 
     * inactive.
     */
    @Test
    public void testRoundAwayFromZero() {
        System.out.println("roundAwayFromZero");
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(2, -1, 
                currRing);
        AlgebraicInteger result = notDivGaussian.roundAwayFromZero();
        assertEquals(expResult, result);
        currRing = new ImaginaryQuadraticRing(-3);
        expResult = new ImaginaryQuadraticInteger(0, -3, currRing);
        result = notDivEisenstein.roundAwayFromZero();
        assertEquals(expResult, result);
        // TODO: Figure out tests for real rings
//        currRing = new RealQuadraticRing(2);
//        expResult = new ImaginaryQuadraticInteger(2, 0, currRing);
//        result = notDivZ2.roundAwayFromZero();
//        assertEquals(expResult, result);
//        currRing = new RealQuadraticRing(5);
//        expResult = new ImaginaryQuadraticInteger(1, 0, currRing);
//        result = notDivZPhi.roundAwayFromZero();
//        assertEquals(expResult, result);
    }
    
    /**
     * Test of the auxiliary NotDivisibleException constructor. This only tests 
     * that the exception message is correctly inferred, using {@link 
     * algebraics.quadratics.QuadraticInteger#toASCIIString()} (which is assumed 
     * to have been properly tested in the relevant test classes).
     */
    @Test
    public void testAuxiliaryConstructor() {
        System.out.println("Auxiliary Constructor");
        QuadraticRing ring = new ImaginaryQuadraticRing(-13);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(0, 1, 
                ring);
        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(2, 0, 
                ring);
        Fraction fractA = new Fraction(0);
        Fraction fractB = new Fraction(1, 2);
        Fraction[] fracts = {fractA, fractB};
        try {
            throw new NotDivisibleException(dividend, divisor, fracts);
        } catch (NotDivisibleException nde) {
            String expResult = dividend.toASCIIString() 
                    + " is not divisible by 2";
            String result = nde.getMessage();
            assertEquals(expResult, result);
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " should not have occurred for valid 3-parameter constructor call";
            fail(msg);
        }
        ring = new ImaginaryQuadraticRing(-15);
        dividend = new ImaginaryQuadraticInteger(0, 2, ring);
        divisor = new ImaginaryQuadraticInteger(4, 0, ring);
        try {
            throw new NotDivisibleException(dividend, divisor, fracts);
        } catch (NotDivisibleException nde) {
            String expResult = dividend.toASCIIString() 
                    + " is not divisible by 4";
            String result = nde.getMessage();
            assertEquals(expResult, result);
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " should not have occurred for valid 3-parameter constructor call";
            fail(msg);
        }
    }
    
    /**
     * Test of the NotDivisibleException constructor. The only thing we're 
     * testing here is that the constructor throws IllegalArgumentException if 
     * passed an array of fractions with a length that doesn't match the 
     * algebraic degree of the pertinent ring.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        String excMsg = "This is an exception message for an invalidly constructed NotDivisibleException";
        QuadraticRing ring = new RealQuadraticRing(19);
        RealQuadraticInteger initDividend = new RealQuadraticInteger(0, 0, ring);
        RealQuadraticInteger initDivisor = new RealQuadraticInteger(1, 0, ring);
        Fraction fractA = new Fraction(7);
        Fraction fractB = new Fraction(1, 2);
        Fraction fractC = new Fraction(3, 2);
        Fraction[] wrongLenFractArray = {fractA, fractB, fractC};
        try {
            throw new NotDivisibleException(excMsg, initDividend, initDivisor, wrongLenFractArray);
        } catch (NotDivisibleException nde) {
            String msg = "NotDivisibleException incorrectly created: \"" + nde.getMessage() + "\"";
            fail(msg);
        } catch(IllegalArgumentException iae) {
            System.out.println("Attempt to create NotDivisibleException with arrays of excessive length correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch(Exception e) {
            String msg = e.getClass().getName() + " is the wrong exception in this situation of arrays of excessive length.";
            System.out.println(msg);
            System.out.println("\"" + e.getMessage() + "\"");
        }
    }
    
}
