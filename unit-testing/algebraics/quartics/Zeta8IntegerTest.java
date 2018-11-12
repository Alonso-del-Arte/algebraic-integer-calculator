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
package algebraics.quartics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class Zeta8IntegerTest {
    
    /**
     * The number &zeta;<sub>8</sub> = (&radic;2)/2 + (&radic;&minus;2)/2.
     */
    private static final Zeta8Integer ZETA_8 = new Zeta8Integer(0, 1, 0, 0);
    
    /**
     * The number (&zeta;<sub>8</sub>)<sup>3</sup> = &minus;(&radic;2)/2 + 
     * (&radic;&minus;2)/2.
     */
    private static final Zeta8Integer ZETA_8_CUBED = new Zeta8Integer(0, 0, 0, 1);
    
    /**
     * The imaginary unit <i>i</i> = &radic;&minus;1 = 
     * (&zeta;<sub>8</sub>)<sup>2</sup>.
     */
    private static final Zeta8Integer IMAG_UNIT_I = new Zeta8Integer(0, 0, 1, 0);
    
    /**
     * The additive inverse of the imaginary unit, &minus;<i>i</i> = 
     * &minus;&radic;&minus;1 = &minus;(&zeta;<sub>8</sub>)<sup>2</sup> = 
     * (&zeta;<sub>8</sub>)<sup>6</sup>.
     */
    private static final Zeta8Integer NEG_IMAG_UNIT_I = new Zeta8Integer(0, 0, -1, 0);
    
    /**
     * The square root of 2, roughly 1.414213562373. Note that &radic;2 = 
     * &zeta;<sub>8</sub> &minus; (&zeta;<sub>8</sub>)<sup>3</sup>.
     */
    private static final Zeta8Integer SQRT_2 = new Zeta8Integer(0, 1, 0, -1);
    
    /**
     * The square root of &minus;2, roughly 1.414213562373 times <i>i</i>. Note 
     * that &radic;&minus;2 = &zeta;<sub>8</sub> + 
     * (&zeta;<sub>8</sub>)<sup>3</sup>.
     */
    private static final Zeta8Integer SQRT_NEG_2 = new Zeta8Integer(0, 1, 0, 1);
    
    public static final double TEST_DELTA = 0.00000001;
    
    /**
     * Test of algebraicDegree method, of class Zeta8Integer.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        int expResult = 4;
        int result = ZETA_8.algebraicDegree();
        assertEquals(expResult, result);
        result = ZETA_8_CUBED.algebraicDegree();
        assertEquals(expResult, result);
        expResult = 2;
        result = IMAG_UNIT_I.algebraicDegree();
        assertEquals(expResult, result);
        result = NEG_IMAG_UNIT_I.algebraicDegree();
        assertEquals(expResult, result);
        result = SQRT_2.algebraicDegree();
        assertEquals(expResult, result);
        result = SQRT_NEG_2.algebraicDegree();
        assertEquals(expResult, result);
    }

    /**
     * Test of trace method, of class Zeta8Integer.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
        fail("The test case is a prototype.");
    }

    /**
     * Test of norm method, of class Zeta8Integer.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        long expResult = 1L;
        long result = ZETA_8.norm();
        assertEquals(expResult, result);
        result = ZETA_8_CUBED.norm();
        assertEquals(expResult, result);
        result = IMAG_UNIT_I.norm();
        assertEquals(expResult, result);
        result = NEG_IMAG_UNIT_I.norm();
        assertEquals(expResult, result);
        expResult = 4L;
        result = SQRT_2.norm();
        assertEquals(expResult, result);
        result = SQRT_NEG_2.norm();
        assertEquals(expResult, result);
    }

    /**
     * Test of minPolynomial method, of class Zeta8Integer.
     */
    @Test
    public void testMinPolynomial() {
        System.out.println("minPolynomial");
        long[] expResult = {1, 0, 0, 0, 1};
        long[] result = ZETA_8.minPolynomial();
        assertArrayEquals(expResult, result);
        result = ZETA_8_CUBED.minPolynomial();
        assertArrayEquals(expResult, result);
        expResult[2] = 1;
        expResult[4] = 0;
        result = IMAG_UNIT_I.minPolynomial();
        assertArrayEquals(expResult, result);
        result = NEG_IMAG_UNIT_I.minPolynomial();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of minPolynomialString method, of class Zeta8Integer.
     */
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        String expResult = "x^4+1";
        String result = ZETA_8.minPolynomialString().replace(" ", "");
        assertEquals(expResult, result);
        result = ZETA_8_CUBED.minPolynomialString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "x^2+1";
        result = IMAG_UNIT_I.minPolynomialString();
        assertEquals(expResult, result);
        result = NEG_IMAG_UNIT_I.minPolynomialString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "x^2-2";
        result = SQRT_2.minPolynomialString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "x^2+2";
        result = SQRT_NEG_2.minPolynomialString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Zeta8Integer.
     */
    @Test
    public void testToString() {
        System.out.println("toASCIIString");
        String expResult = "\u03B6\u2088";
        String result = ZETA_8.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "(\u03B6\u2088)\u00B3";
        result = ZETA_8_CUBED.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "i";
        result = IMAG_UNIT_I.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "-i";
        result = NEG_IMAG_UNIT_I.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\u03B6\u2088-(\u03B6\u2088)\u00B3";
        result = SQRT_2.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\u03B6\u2088+(\u03B6\u2088)\u00B3";
        result = SQRT_NEG_2.toString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class Zeta8Integer.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "zeta_8";
        String result = ZETA_8.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "(zeta_8)^3";
        result = ZETA_8_CUBED.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "i";
        result = IMAG_UNIT_I.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "-i";
        result = NEG_IMAG_UNIT_I.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "zeta_8-(zeta_8)^3";
        result = SQRT_2.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "zeta_8+(zeta_8)^3";
        result = SQRT_NEG_2.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXString method, of class Zeta8Integer.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        String expResult = "\\zeta_8";
        String result = ZETA_8.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "(\\zeta_8)^3";
        result = ZETA_8_CUBED.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "i";
        result = IMAG_UNIT_I.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "-i";
        result = NEG_IMAG_UNIT_I.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\\zeta_8-(\\zeta_8)^3";
        result = SQRT_2.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\\zeta_8+(\\zeta_8)^3";
        result = SQRT_NEG_2.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class Zeta8Integer.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Zeta8Integer.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Zeta8Integer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        fail("The test case is a prototype.");
    }

    /**
     * Test of plus method, of class Zeta8Integer.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        Zeta8Integer expResult = new Zeta8Integer(0, 1, 1, 1);
        Zeta8Integer result = IMAG_UNIT_I.plus(SQRT_NEG_2);
        assertEquals(expResult, result);
    }

    /**
     * Test of minus method, of class Zeta8Integer.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        Zeta8Integer expResult = new Zeta8Integer(0, -1, 1, -1);
        Zeta8Integer result = IMAG_UNIT_I.minus(SQRT_NEG_2);
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Zeta8Integer.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        Zeta8Integer result = IMAG_UNIT_I.times(SQRT_2);
        assertEquals(SQRT_NEG_2, result);
        Zeta8Integer expResult = SQRT_2.times(-1);
        result = result.times(IMAG_UNIT_I);
        assertEquals(expResult, result);
    }

    /**
     * Test of divides method, of class Zeta8Integer.
     */
    @Test
    public void testDivides() {
        System.out.println("divides");
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertToQuadraticInteger method, of class Zeta8Integer.
     */
    @Test
    public void testConvertToQuadraticInteger() {
        System.out.println("convertToQuadraticInteger");
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 1, currRing);
        QuadraticInteger result = IMAG_UNIT_I.convertToQuadraticInteger();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(0, -1, currRing);
        result = NEG_IMAG_UNIT_I.convertToQuadraticInteger();
        assertEquals(expResult, result);
        currRing = new ImaginaryQuadraticRing(-2);
        expResult = new ImaginaryQuadraticInteger(0, 1, currRing);
        result = SQRT_NEG_2.convertToQuadraticInteger();
        assertEquals(expResult, result);
        currRing = new RealQuadraticRing(2);
        expResult = new RealQuadraticInteger(0, 1, currRing);
        result = SQRT_2.convertToQuadraticInteger();
        assertEquals(expResult, result);
        try {
            result = ZETA_8.convertToQuadraticInteger();
            String failMsg = "Trying to convert " + ZETA_8.toASCIIString() + " to a quadratic integer should have triggered an exception, not given result " + result.toASCIIString();
            fail(failMsg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Trying to convert " + ZETA_8.toASCIIString() + " to a quadratic integer correctly triggered AlgebraicDegreeOverflowException.");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception for the inappropriate conversion of " + ZETA_8.toASCIIString();
            fail(failMsg);
        }
    }

    /**
     * Test of convertFromQuadraticInteger method, of class Zeta8Integer.
     */
    @Test
    public void testConvertFromQuadraticInteger() {
        System.out.println("convertFromQuadraticInteger");
        QuadraticRing currRing = new ImaginaryQuadraticRing(-1);
        QuadraticInteger quadInt = new ImaginaryQuadraticInteger(0, 1, currRing);
        Zeta8Integer result = Zeta8Integer.convertFromQuadraticInteger(quadInt);
        assertEquals(IMAG_UNIT_I, result);
        quadInt = new ImaginaryQuadraticInteger(0, -1, currRing);
        result = Zeta8Integer.convertFromQuadraticInteger(quadInt);
        assertEquals(NEG_IMAG_UNIT_I, result);
        currRing = new ImaginaryQuadraticRing(-2);
        quadInt = new ImaginaryQuadraticInteger(0, 1, currRing);
        result = Zeta8Integer.convertFromQuadraticInteger(quadInt);
        assertEquals(SQRT_NEG_2, result);
        currRing = new RealQuadraticRing(2);
        quadInt = new RealQuadraticInteger(0, 1, currRing);
        result = Zeta8Integer.convertFromQuadraticInteger(quadInt);
        assertEquals(SQRT_2, result);
        currRing = new RealQuadraticRing(3);
        quadInt = new RealQuadraticInteger(1, 1, currRing);
        try {
            result = Zeta8Integer.convertFromQuadraticInteger(quadInt);
            String failMessage = "Trying to convert " + quadInt.toASCIIString() + " to an integer in O_Q(zeta_8) should have triggered an exception, not given result " + result.toASCIIString();
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to convert " + quadInt.toASCIIString() + " to an integer in O_Q(zeta_8) correctly triggered UnsupportedNumberDomainException.");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception for trying to convert " + quadInt.toASCIIString() + " to an integer in O_Q(zeta_8)";
            fail(failMsg);
        }
    }

    /**
     * Test of getRealPartNumeric method, of class Zeta8Integer.
     */
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartNumeric");
        double expResult = 0.0;
        double result = IMAG_UNIT_I.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        result = NEG_IMAG_UNIT_I.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        result = SQRT_NEG_2.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.70710678;
        result = ZETA_8.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult *= -1;
        result = ZETA_8_CUBED.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult *= -2;
        result = SQRT_2.getRealPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
    }

    /**
     * Test of getImagPartNumeric method, of class Zeta8Integer.
     */
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartNumeric");
        double expResult = 0.0;
        double result = SQRT_2.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.70710678;
        result = ZETA_8.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        result = ZETA_8_CUBED.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult *= 2;
        result = SQRT_NEG_2.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 1.0;
        result = IMAG_UNIT_I.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
        expResult *= -1;
        result = NEG_IMAG_UNIT_I.getImagPartNumeric();
        assertEquals(expResult, result, TEST_DELTA);
    }
    
}
