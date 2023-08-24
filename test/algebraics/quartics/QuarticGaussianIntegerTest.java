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
package algebraics.quartics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.IntegerRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import arithmetic.NotDivisibleException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the QuarticGaussianInteger class.
 * @author Alonso del Arte
 */
public class QuarticGaussianIntegerTest {
    
    private static final QuarticGaussianInteger ONE 
            = new QuarticGaussianInteger(1, 0, 0, 0);
    
    private static final QuarticGaussianInteger NEG_ONE 
            = new QuarticGaussianInteger(-1, 0, 0, 0);
    
    private static final QuarticGaussianInteger SQRT1I 
            = new QuarticGaussianInteger(0, 1, 0, 0);
    
    private static final QuarticGaussianInteger IMAG_UNIT 
            = new QuarticGaussianInteger(-1, 0, 1, 0);
    
    private static final QuarticGaussianInteger NEG_IMAG_UNIT 
            = new QuarticGaussianInteger(1, 0, -1, 0);
    
    private static final QuarticGaussianInteger FUND_UNIT_WOLFRAM 
            = new QuarticGaussianInteger(1, 1, -1, 0);
    
    private static final QuarticGaussianInteger FUND_UNIT_LMFDB 
            = new QuarticGaussianInteger(1, -1, -1, 1);
    
    private static final QuarticGaussianInteger ONE_PLUS_IMAG_UNIT 
            = new QuarticGaussianInteger(0, 0, 1, 0);
    
    private static final QuarticGaussianInteger SQRT1ICUBED 
            = new QuarticGaussianInteger(0, 0, 0, 1);
    
    public static final double TEST_DELTA = 0.00000001;
    
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        QuarticGaussianRing expRing = new QuarticGaussianRing();
        String msg = "Ring of " + SQRT1I.toString() + " should be " 
                + expRing.toString();
        IntegerRing ring = SQRT1I.getRing();
        assertEquals(msg, expRing, ring);
    }
    
    /**
     * Test of algebraicDegree method, of class QuarticGaussianInteger.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        int expResult = 4;
        int result = SQRT1I.algebraicDegree();
        assertEquals(expResult, result);
    }

    /**
     * Test of trace method, of class QuarticGaussianInteger.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
        QuarticGaussianInteger instance = new QuarticGaussianInteger(0, 0, 0, 0);
        long expResult = 0L;
        long result = instance.trace();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of norm method, of class QuarticGaussianInteger.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        QuarticGaussianInteger instance = new QuarticGaussianInteger(0, 0, 0, 0);
        long expResult = 0L;
        long result = instance.norm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialCoeffs method, of class QuarticGaussianInteger.
     */
    @Test
    public void testMinPolynomialCoeffs() {
        System.out.println("minPolynomialCoeffs");
        long[] expResult = {2L, 0L, 2L, 0L, 1L};
        long[] result = SQRT1I.minPolynomialCoeffs();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of minPolynomialString method, of class QuarticGaussianInteger.
     */
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        String expResult = "x\u2074-2x\u2072+2";
        String result = SQRT1I.minPolynomialString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of minPolynomialStringTeX method, of class QuarticGaussianInteger.
     */
    @Test
    public void testMinPolynomialStringTeX() {
        System.out.println("minPolynomialStringTeX");
        String expResult = "x^4-2x^2+2";
        String result = SQRT1I.minPolynomialStringTeX().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of minPolynomialStringHTML method, of class QuarticGaussianInteger.
     */
    @Test
    public void testMinPolynomialStringHTML() {
        System.out.println("minPolynomialStringHTML");
        String expected = "<i>x</i><sup>4</sup>&minus;2<i>x</i><sup>2</sup>+2";
        String actual = SQRT1I.minPolynomialStringHTML().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Test of toString method, of class QuarticGaussianInteger.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "1+i";
        String result = ONE_PLUS_IMAG_UNIT.toString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class QuarticGaussianInteger.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "sqrt(1+i)";
        String result = SQRT1I.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXString method, of class QuarticGaussianInteger.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        String expResult = "\\sqrt{1+i}";
        String result = SQRT1I.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class QuarticGaussianInteger.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        String expResult = "&radic;(1+<i>i</i>";
        String result = SQRT1I.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class QuarticGaussianInteger.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuarticGaussianInteger sameNumber = new QuarticGaussianInteger(1, 1, -1, 
                0);
        assertEquals(FUND_UNIT_WOLFRAM, sameNumber);
        sameNumber = new QuarticGaussianInteger(1, -1, -1, 1);
        assertEquals(FUND_UNIT_LMFDB, sameNumber);
        assertNotEquals(ONE, NEG_ONE);
        assertNotEquals(IMAG_UNIT, NEG_IMAG_UNIT);
        assertNotEquals(SQRT1I, SQRT1ICUBED);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuarticGaussianInteger sameNumber = new QuarticGaussianInteger(1, 1, -1, 
                0);
        assertEquals(FUND_UNIT_WOLFRAM.hashCode(), sameNumber.hashCode());
        sameNumber = new QuarticGaussianInteger(1, -1, -1, 1);
        assertEquals(FUND_UNIT_LMFDB.hashCode(), sameNumber.hashCode());
        assertNotEquals(ONE.hashCode(), NEG_ONE.hashCode());
        assertNotEquals(IMAG_UNIT.hashCode(), NEG_IMAG_UNIT.hashCode());
        assertNotEquals(SQRT1I.hashCode(), SQRT1ICUBED.hashCode());
    }
    
    /**
     * Test of convertToQuadraticInteger method, of class 
     * QuarticGaussianInteger.
     */
    @Test
    public void testConvertToQuadraticInteger() {
        System.out.println("convertToQuadraticInteger");
        ImaginaryQuadraticRing quadGaussianRing = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(1, 1, 
                quadGaussianRing);
        ImaginaryQuadraticInteger actual 
                = ONE_PLUS_IMAG_UNIT.convertToQuadraticInteger();
        assertEquals(expected, actual);
        try {
            actual = SQRT1I.convertToQuadraticInteger();
            String msg = "Trying to convert " + SQRT1I.toString() 
                    + " to a quadratic integer should not have given result " 
                    + actual.toString();
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Trying to convert " + SQRT1I.toString() 
                    + " correctly caused AlgebraicDegreeOverflowException");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " is the wrong exception to throw for trying to convert " 
                    + SQRT1I.toString() + " to a quadratic integer";
            System.out.println("\"" + e.getMessage() + "\"");
            fail(msg);
        }
    }
    
    /**
     * Test of convertFromQuadraticInteger, of class QuarticGaussianInteger.
     */
    @Test
    public void testConvertFromQuadraticInteger() {
        System.out.println("convertFromQuadraticInteger");
        ImaginaryQuadraticRing quadGaussianRing = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger quadInt = new ImaginaryQuadraticInteger(3, 1, 
                quadGaussianRing);
        QuarticGaussianInteger expected = new QuarticGaussianInteger(2, 0, 1, 0);
        QuarticGaussianInteger actual 
                = QuarticGaussianInteger.convertFromQuadraticInteger(quadInt);
        assertEquals(expected, actual);
    }

    /**
     * Test of plus method, of class QuarticGaussianInteger.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        QuarticGaussianInteger summandA = new QuarticGaussianInteger(1, 0, 0, 1);
        int purelyRealSummand = 7;
        QuarticGaussianInteger expected = new QuarticGaussianInteger(8, 0, 0, 1);
        QuarticGaussianInteger actual = summandA.plus(purelyRealSummand);
        assertEquals(expected, actual);
        assertEquals(ONE_PLUS_IMAG_UNIT, ONE.plus(IMAG_UNIT));
    }
    
    @Test
    public void testNegate() {
        System.out.println("negate");
        QuarticGaussianInteger someNumber = new QuarticGaussianInteger(-7, 3, 8, 
                -1);
        QuarticGaussianInteger expected = new QuarticGaussianInteger(7, -3, -8, 
                1);
        QuarticGaussianInteger actual = someNumber.negate();
        assertEquals(expected, actual);
        assertEquals(someNumber, actual.negate());
        assertEquals(NEG_ONE, ONE.negate());
        assertEquals(NEG_IMAG_UNIT, IMAG_UNIT.negate());
    }

    /**
     * Test of minus method, of class QuarticGaussianInteger.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        QuarticGaussianInteger minuend = new QuarticGaussianInteger(1, 0, 0, 1);
        int purelyRealSubtrahend = 7;
        QuarticGaussianInteger expected = new QuarticGaussianInteger(-6, 0, 0, 1);
        QuarticGaussianInteger result = minuend.minus(purelyRealSubtrahend);
        assertEquals(expected, result);
        assertEquals(ONE, ONE_PLUS_IMAG_UNIT.minus(IMAG_UNIT));
    }

    /**
     * Test of times method, of class QuarticGaussianInteger.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        QuarticGaussianInteger multiplicandA = new QuarticGaussianInteger(1, 0, 
                0, 1);
        int purelyRealMultiplicand = 7;
        QuarticGaussianInteger expected = new QuarticGaussianInteger(7, 0, 0, 7);
        QuarticGaussianInteger actual = multiplicandA.plus(purelyRealMultiplicand);
        assertEquals(expected, actual);
        assertEquals(NEG_ONE, IMAG_UNIT.plus(IMAG_UNIT));
    }

    /**
     * Test of divides method, of class QuarticGaussianInteger.
     */
    @Test
    public void testDivides() {
        System.out.println("divides");
        QuarticGaussianInteger dividend = new QuarticGaussianInteger(21, 7, 42, 
                84);
        int purelyRealDivisor = 7;
        QuarticGaussianInteger expResult = new QuarticGaussianInteger(3, 1, 6, 
                12);
        try {
            QuarticGaussianInteger result = dividend.divides(purelyRealDivisor);
            assertEquals(expResult, result);
        } catch (NotDivisibleException nde) {
            String msg = "Trying to divide " + dividend.toString() + " by " 
                    + purelyRealDivisor 
                    + " should have given result, not caused NotDivisibleException";
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(msg);
        }
        dividend = new QuarticGaussianInteger(20, 7, 43, 85);
        try {
            QuarticGaussianInteger result = dividend.divides(purelyRealDivisor);
            String msg = "Trying to divide " + dividend.toString() + " by " 
                    + purelyRealDivisor 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (NotDivisibleException nde) {
            System.out.println("Trying to divide " + dividend.toString() + " by " 
                    + purelyRealDivisor 
                    + " correctly triggered NotDivisibleException");
            System.out.println("\"" + nde.getMessage() + "\"");
        }
        try {
            QuarticGaussianInteger result 
                    = SQRT1ICUBED.divides(ONE_PLUS_IMAG_UNIT);
            assertEquals(SQRT1I, result);
        } catch (NotDivisibleException nde) {
            String msg = "Trying to divide " + SQRT1ICUBED.toString() + " by " 
                    + ONE_PLUS_IMAG_UNIT.toString() 
                    + " should have given result, not caused NotDivisibleException";
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(msg);
        }
//        try {
//            QuarticGaussianInteger result = SQRT1ICUBED.divides(dividend);
//            String failMsg = "Trying to divide " + SQRT1ICUBED.toString() + " by "
//        } catch (NotDivisibleException nde) {
//            String failMsg = "Trying to divide " + SQRT1ICUBED.toString() + " by " + ONE_PLUS_IMAG_UNIT.toString() + " should have given result, not caused NotDivisibleException";
//            System.out.println("\"" + nde.getMessage() + "\"");
//            fail(failMsg);
//        }
    }

    /**
     * Test of divides method, of class QuarticGaussianInteger.
     */
    @Test
    public void testDivisionByZero() {
        QuarticGaussianInteger dividend = new QuarticGaussianInteger(21, 7, 42, 
                84);
        int purelyRealDivisor = 0;
        try {
            QuarticGaussianInteger result = dividend.divides(purelyRealDivisor);
            String msg = "Trying to divide " + dividend.toString() 
                    + " by 0 should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (NotDivisibleException nde) {
            String msg = "Trying to divide " + dividend.toString() 
                    + " by 0 should not have caused NotDivisibleException";
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to divide " + dividend.toASCIIString() 
                    + " by 0 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is acceptable for division of " 
                    + dividend.toASCIIString() + " by 0");
            System.out.println("\"" + ae.getMessage() + "\"");
        }
        try {
            QuarticGaussianInteger result = SQRT1ICUBED.divides(purelyRealDivisor);
            String msg = "Trying to divide " + SQRT1ICUBED.toString() 
                    + " by 0 should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (NotDivisibleException nde) {
            String msg = "Trying to divide " + SQRT1ICUBED.toString() + " by " 
                    + ONE_PLUS_IMAG_UNIT.toString() + " should have given result, not caused NotDivisibleException";
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to divide " + SQRT1ICUBED.toASCIIString() 
                    + " by 0 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is acceptable for trying to divide " + SQRT1ICUBED.toASCIIString() + " by 0");
            System.out.println("\"" + ae.getMessage() + "\"");
        }
    }

    /**
     * Test of abs method, of class QuarticGaussianInteger.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        double expResult = 1.18920712;
        double result = SQRT1I.abs();
        assertEquals(expResult, result, TEST_DELTA);
    }

    /**
     * Test of getRealPartNumeric method, of class QuarticGaussianInteger.
     */
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartNumeric");
        double expResult = 1.09868411;
        double result = SQRT1I.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.643594253;
        result = SQRT1ICUBED.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = -1.0;
        result = NEG_ONE.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 1.0;
        result = ONE_PLUS_IMAG_UNIT.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = -0.45508986;
        result = FUND_UNIT_LMFDB.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
    }

    /**
     * Test of getImagPartNumeric method, of class QuarticGaussianInteger.
     */
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartNumeric");
        double expResult = 0.45508986;
        double result = SQRT1I.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 1.55377397;
        result = SQRT1ICUBED.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.0;
        result = ONE.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 1.0;
        result = ONE_PLUS_IMAG_UNIT.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.09868411;
        result = FUND_UNIT_LMFDB.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
    }

    /**
     * Test of angle method, of class QuarticGaussianInteger.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        QuarticGaussianInteger instance = new QuarticGaussianInteger(0, 0, 0, 0);
        double expResult = 0.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
