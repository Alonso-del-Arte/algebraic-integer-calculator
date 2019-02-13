/*
 * Copyright (C) 2019 Alonso del Arte
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
import algebraics.NotDivisibleException;
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
    
    private static final Zeta8Integer ONE = new Zeta8Integer(1, 0, 0, 0);
    
    private static final Zeta8Integer NEG_ONE = new Zeta8Integer(-1, 0, 0, 0);
    
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
        Zeta8Integer num = new Zeta8Integer(0, 1, 1, 0);
        result = num.algebraicDegree();
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
        num = new Zeta8Integer(0, 8, 0, 8);
        result = num.algebraicDegree();
        assertEquals(expResult, result);
        num = new Zeta8Integer(0, 8, 0, -8);
        result = num.algebraicDegree();
        assertEquals(expResult, result);
        expResult = 1;
        result = ONE.algebraicDegree();
        assertEquals(expResult, result);
        result = NEG_ONE.algebraicDegree();
        assertEquals(expResult, result);
        num = new Zeta8Integer(7, 0, 0, 0);
        result = num.algebraicDegree();
        assertEquals(expResult, result);
        expResult = 0;
        num = new Zeta8Integer(0, 0, 0, 0);
        result = num.algebraicDegree();
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
     * Test of abs method, of class Zeta8Integer.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        double expResult = 1.0;
        double result = ZETA_8.abs();
        assertEquals(expResult, result, TEST_DELTA);
        result = ZETA_8_CUBED.abs();
        assertEquals(expResult, result, TEST_DELTA);
        result = IMAG_UNIT_I.abs();
        assertEquals(expResult, result, TEST_DELTA);
        result = NEG_IMAG_UNIT_I.abs();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = Math.sqrt(2);
        result = SQRT_2.abs();
        assertEquals(expResult, result, TEST_DELTA);
        result = SQRT_NEG_2.abs();
        assertEquals(expResult, result, TEST_DELTA);
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
        expResult = "\u2212i";
        result = NEG_IMAG_UNIT_I.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1";
        result = ONE.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\u22121";
        result = NEG_ONE.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\u03B6\u2088\u2212(\u03B6\u2088)\u00B3";
        result = SQRT_2.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\u03B6\u2088+(\u03B6\u2088)\u00B3";
        result = SQRT_NEG_2.toString().replace(" ", "");
        assertEquals(expResult, result);
        Zeta8Integer num = new Zeta8Integer(4, -3, 2, -1);
        expResult = "4\u22123\u03B6\u2088+2i\u2212(\u03B6\u2088)\u00B3";
        result = num.toString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(-5, 0, -21, 3);
        expResult = "\u22125\u221221i+3(\u03B6\u2088)\u00B3";
        result = num.toString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(0, 2, 0, 0);
        expResult = "2\u03B6\u2088";
        result = num.toString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(0, -2, 0, 0);
        expResult = "\u22122\u03B6\u2088";
        result = num.toString().replace(" ", "");
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
        expResult = "1";
        result = ONE.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "-1";
        result = NEG_ONE.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "zeta_8-(zeta_8)^3";
        result = SQRT_2.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "zeta_8+(zeta_8)^3";
        result = SQRT_NEG_2.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        Zeta8Integer num = new Zeta8Integer(4, -3, 2, -1);
        expResult = "4-3zeta_8+2i-(zeta_8)^3";
        result = num.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(-5, 0, -21, 3);
        expResult = "-5-21i+3(zeta_8)^3";
        result = num.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(0, 2, 0, 0);
        expResult = "2zeta_8";
        result = num.toASCIIString().replace(" ", "");
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
        expResult = "1";
        result = ONE.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "-1";
        result = NEG_ONE.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\\zeta_8-(\\zeta_8)^3";
        result = SQRT_2.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\\zeta_8+(\\zeta_8)^3";
        result = SQRT_NEG_2.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        Zeta8Integer num = new Zeta8Integer(4, -3, 2, -1);
        expResult = "4-3\\zeta_8+2i-(\\zeta_8)^3";
        result = num.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(-5, 0, -21, 3);
        expResult = "-5-21i+3(\\zeta_8)^3";
        result = num.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(0, 2, 0, 0);
        expResult = "2\\zeta_8";
        result = num.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class Zeta8Integer.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        String expResult = "&zeta;<sub>8</sub>";
        String result = ZETA_8.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "(&zeta;<sub>8</sub>)<sup>3</sup>";
        result = ZETA_8_CUBED.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "<i>i</i>";
        result = IMAG_UNIT_I.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "&minus;<i>i</i>";
        result = NEG_IMAG_UNIT_I.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1";
        result = ONE.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "&minus;1";
        result = NEG_ONE.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "&zeta;<sub>8</sub>&minus;(&zeta;<sub>8</sub>)<sup>3</sup>";
        result = SQRT_2.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "&zeta;<sub>8</sub>+(&zeta;<sub>8</sub>)<sup>3</sup>";
        result = SQRT_NEG_2.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        Zeta8Integer num = new Zeta8Integer(4, -3, 2, -1);
        expResult = "4&minus;3&zeta;<sub>8</sub>+2<i>i</i>&minus;(&zeta;<sub>8</sub>)<sup>3</sup>";
        result = num.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(-5, 0, -21, 3);
        expResult = "&minus;5&minus;21<i>i</i>+3(&zeta;<sub>8</sub>)<sup>3</sup>";
        result = num.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        num = new Zeta8Integer(0, 2, 0, 0);
        expResult = "2&zeta;<sub>8</sub>";
        result = num.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Zeta8Integer. This checks that 
     * different numbers hash differently. It is not guaranteed that distinct 
     * numbers will always get distinct hash codes, but it is expected that 
     * distinct numbers with equal or close to equal norms will get distinct 
     * hash codes.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        System.out.println(ZETA_8.toASCIIString() + " hashed as " + ZETA_8.hashCode());
        System.out.println(ZETA_8_CUBED.toASCIIString() + " hashed as " + ZETA_8_CUBED.hashCode());
        System.out.println(IMAG_UNIT_I.toASCIIString() + " hashed as " + IMAG_UNIT_I.hashCode());
        System.out.println(NEG_IMAG_UNIT_I.toASCIIString() + " hashed as " + NEG_IMAG_UNIT_I.hashCode());
        System.out.println(ONE.toASCIIString() + " hashed as " + ONE.hashCode());
        System.out.println(NEG_ONE.toASCIIString() + " hashed as " + NEG_ONE.hashCode());
        System.out.println(SQRT_2.toASCIIString() + " hashed as " + SQRT_2.hashCode());
        System.out.println(SQRT_NEG_2.toASCIIString() + " hashed as " + SQRT_NEG_2.hashCode());
        assertNotEquals(ONE.hashCode(), NEG_ONE.hashCode());
        assertNotEquals(ONE.hashCode(), ZETA_8.hashCode());
        assertNotEquals(ONE.hashCode(), ZETA_8_CUBED.hashCode());
        assertNotEquals(ONE.hashCode(), IMAG_UNIT_I.hashCode());
        assertNotEquals(ONE.hashCode(), NEG_IMAG_UNIT_I.hashCode());
        assertNotEquals(ONE.hashCode(), SQRT_2.hashCode());
        assertNotEquals(ONE.hashCode(), SQRT_NEG_2.hashCode());
        assertNotEquals(NEG_ONE.hashCode(), ZETA_8.hashCode());
        assertNotEquals(NEG_ONE.hashCode(), ZETA_8_CUBED.hashCode());
        assertNotEquals(NEG_ONE.hashCode(), IMAG_UNIT_I.hashCode());
        assertNotEquals(NEG_ONE.hashCode(), NEG_IMAG_UNIT_I.hashCode());
        assertNotEquals(NEG_ONE.hashCode(), SQRT_2.hashCode());
        assertNotEquals(NEG_ONE.hashCode(), SQRT_NEG_2.hashCode());
        assertNotEquals(ZETA_8.hashCode(), ZETA_8_CUBED.hashCode());
        assertNotEquals(ZETA_8.hashCode(), IMAG_UNIT_I.hashCode());
        assertNotEquals(ZETA_8.hashCode(), NEG_IMAG_UNIT_I.hashCode());
        assertNotEquals(ZETA_8.hashCode(), SQRT_2.hashCode());
        assertNotEquals(ZETA_8.hashCode(), SQRT_NEG_2.hashCode());
        assertNotEquals(ZETA_8_CUBED.hashCode(), IMAG_UNIT_I.hashCode());
        assertNotEquals(ZETA_8_CUBED.hashCode(), NEG_IMAG_UNIT_I.hashCode());
        assertNotEquals(ZETA_8_CUBED.hashCode(), SQRT_2.hashCode());
        assertNotEquals(ZETA_8_CUBED.hashCode(), SQRT_NEG_2.hashCode());
        assertNotEquals(IMAG_UNIT_I.hashCode(), NEG_IMAG_UNIT_I.hashCode());
        assertNotEquals(IMAG_UNIT_I.hashCode(), SQRT_2.hashCode());
        assertNotEquals(IMAG_UNIT_I.hashCode(), SQRT_NEG_2.hashCode());
        assertNotEquals(NEG_IMAG_UNIT_I.hashCode(), SQRT_2.hashCode());
        assertNotEquals(NEG_IMAG_UNIT_I.hashCode(), SQRT_NEG_2.hashCode());
        assertNotEquals(SQRT_2.hashCode(), SQRT_NEG_2.hashCode());
    }

    /**
     * Test of equals method, of class Zeta8Integer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Zeta8Integer sameNum = new Zeta8Integer(0, 1, 0, 0);
        Zeta8Integer diffNum = new Zeta8Integer(1, 0, 0, 0);
        assertEquals(sameNum, sameNum);
        assertNotEquals(sameNum, diffNum);
        assertEquals(ZETA_8, ZETA_8);
        assertEquals(ZETA_8, sameNum);
        assertNotEquals(ZETA_8, diffNum);
        sameNum = new Zeta8Integer(0, 0, 0, 1);
        diffNum = new Zeta8Integer(0, 0, 7, -4);
        assertEquals(sameNum, sameNum);
        assertNotEquals(sameNum, diffNum);
        assertEquals(ZETA_8_CUBED, ZETA_8_CUBED);
        assertEquals(ZETA_8_CUBED, sameNum);
        assertNotEquals(ZETA_8_CUBED, diffNum);
        sameNum = new Zeta8Integer(0, 0, 1, 0);
        diffNum = new Zeta8Integer(0, 2, 0, 0);
        assertEquals(sameNum, sameNum);
        assertNotEquals(sameNum, diffNum);
        assertEquals(IMAG_UNIT_I, IMAG_UNIT_I);
        assertEquals(IMAG_UNIT_I, sameNum);
        assertNotEquals(IMAG_UNIT_I, diffNum);
        sameNum = new Zeta8Integer(0, 0, -1, 0);
        diffNum = new Zeta8Integer(0, -2, 0, 0);
        assertEquals(sameNum, sameNum);
        assertNotEquals(sameNum, diffNum);
        assertEquals(NEG_IMAG_UNIT_I, NEG_IMAG_UNIT_I);
        assertEquals(NEG_IMAG_UNIT_I, sameNum);
        assertNotEquals(NEG_IMAG_UNIT_I, diffNum);
        sameNum = new Zeta8Integer(0, 1, 0, -1);
        diffNum = new Zeta8Integer(0, 0, 3, 0);
        assertEquals(sameNum, sameNum);
        assertNotEquals(sameNum, diffNum);
        assertEquals(SQRT_2, SQRT_2);
        assertEquals(SQRT_2, sameNum);
        assertNotEquals(SQRT_2, diffNum);
        sameNum = new Zeta8Integer(0, 1, 0, 1);
        diffNum = new Zeta8Integer(0, 0, -3, 0);
        assertEquals(sameNum, sameNum);
        assertNotEquals(sameNum, diffNum);
        assertEquals(SQRT_NEG_2, SQRT_NEG_2);
        assertEquals(SQRT_NEG_2, sameNum);
        assertNotEquals(SQRT_NEG_2, diffNum);
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
        expResult = new Zeta8Integer(-2, 0, 0, 0);
        result = SQRT_NEG_2.times(SQRT_NEG_2);
        assertEquals(expResult, result);
        expResult = new Zeta8Integer(2, 0, 0, 0);
        result = SQRT_2.times(SQRT_2);
        assertEquals(expResult, result);
        expResult = new Zeta8Integer(0, 0, 2, 0);
        result = SQRT_2.times(SQRT_NEG_2);
        assertEquals(expResult, result);
        expResult = new Zeta8Integer(1, 0, -1, 0);
        Zeta8Integer multiplicandA = new Zeta8Integer(1, -1, 0, 0); // 1 - zeta_8 times...
        Zeta8Integer multiplicandB = new Zeta8Integer(1, 1, 0, 0); // ... 1 + zeta_8 should...
        result = multiplicandA.times(multiplicandB); // ...equal 1 - i
        assertEquals(expResult, result);
        expResult = new Zeta8Integer(1, 0, 1, 0);
        multiplicandA = new Zeta8Integer(0, 1, -1, 0); // zeta_8 - i times...
        multiplicandB = new Zeta8Integer(0, 1, 1, 0); // ... zeta_8 + i should...
        result = multiplicandA.times(multiplicandB); // ...equal 1 + i
        assertEquals(expResult, result);
        expResult = new Zeta8Integer(2, 0, 0, 0);
        multiplicandA = new Zeta8Integer(1, 0, -1, 0); // 1 - i times...
        multiplicandB = new Zeta8Integer(1, 0, 1, 0); // ... 1 + i should...
        result = multiplicandA.times(multiplicandB); // ...equal 2
        assertEquals(expResult, result);
    }

    /**
     * Test of divides method, of class Zeta8Integer.
     */
    @Test
    public void testDivides() {
        System.out.println("divides");
        Zeta8Integer dividend = new Zeta8Integer(2, 4, 10, 8);
        Zeta8Integer expResult = new Zeta8Integer(1, 2, 5, 4);
        Zeta8Integer result;
        try {
            result = dividend.divides(2);
            assertEquals(expResult, result);
            result = SQRT_NEG_2.divides(SQRT_2);
            assertEquals(IMAG_UNIT_I, result);
            result = SQRT_NEG_2.divides(IMAG_UNIT_I);
            assertEquals(SQRT_2, result);
            dividend = new Zeta8Integer(1, 0, 1, 0); // 1 + i
            result = dividend.divides(SQRT_2);
            assertEquals(ZETA_8, result);
            dividend = new Zeta8Integer(-1, 0, 1, 0); // -1 + i
            result = dividend.divides(SQRT_NEG_2);
            assertEquals(ZETA_8, result);
            dividend = new Zeta8Integer(-2, 0, 0, 0);
            result = dividend.divides(SQRT_NEG_2);
            assertEquals(SQRT_NEG_2, result);
            dividend = new Zeta8Integer(2, 0, 0, 0);
            result = dividend.divides(SQRT_2);
            assertEquals(SQRT_2, result);
            dividend = new Zeta8Integer(0, 0, 2, 0);
            result = dividend.divides(SQRT_2);
            assertEquals(SQRT_NEG_2, result);
            result = dividend.divides(SQRT_NEG_2);
            assertEquals(SQRT_2, result);
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException should not have occurred: \"" + nde.getMessage() + "\"";
            fail(failMessage);
        }
        dividend = new Zeta8Integer(1, 1, 1, 1);
        Zeta8Integer divisor = new Zeta8Integer(0, 43, 0, 0);
        try {
            result = dividend.divides(divisor);
            String failMessage = "Trying to divide " + dividend.toString() + " by " + divisor.toString() + " should have triggered NotDivisibleException, not given result " + result.toString();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            System.out.println("Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " correctly triggered NotDivisibleException.");
            System.out.println("\"" + nde.getMessage() + "\"");
        }
        // And lastly, to test division by 0
        dividend = new Zeta8Integer(1, 2, 3, 4);
        divisor = new Zeta8Integer(0, 0, 0, 0);
        try {
            result = dividend.divides(divisor);
            String failMessage = "Attempted division by zero should have triggered an exception, not given result " + result.toString() + ".";
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempted division by zero correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("Attempted division by zero correctly triggered ArithmeticException.");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException should not have occurred for division by 0: \"" + nde.getMessage() + "\"";
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is wrong exception to throw for division by 0.";
            fail(failMessage);
        }
        try {
            result = dividend.divides(0);
            String failMessage = "Attempted division by zero should have triggered an exception, not given result " + result.toString() + ".";
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempted division by zero correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException should not have occurred for division by 0: \"" + nde.getMessage() + "\"";
            fail(failMessage);
        }
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
    
    /**
     * Test of angle method, of class Zeta8Integer.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        double expResult = 0.0;
        double result = ONE.angle();
        String assertionMessage = "Angle of " + ONE.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        result = SQRT_2.angle();
        assertionMessage = "Angle of " + SQRT_2.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        expResult = 0.78539816;
        result = ZETA_8.angle();
        assertionMessage = "Angle of " + ZETA_8.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        expResult = 1.57079633;
        result = IMAG_UNIT_I.angle();
        assertionMessage = "Angle of " + IMAG_UNIT_I.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        result = SQRT_NEG_2.angle();
        assertionMessage = "Angle of " + SQRT_NEG_2.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        expResult = -1.57079633;
        result = NEG_IMAG_UNIT_I.angle();
        assertionMessage = "Angle of " + NEG_IMAG_UNIT_I.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        expResult = 2.35619449;
        result = ZETA_8_CUBED.angle();
        assertionMessage = "Angle of " + ZETA_8_CUBED.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
        expResult = 3.14159265;
        result = NEG_ONE.angle();
        assertionMessage = "Angle of " + NEG_ONE.toString() + " should be " + expResult + " radians";
        assertEquals(assertionMessage, expResult, result, TEST_DELTA);
    }
    
}
