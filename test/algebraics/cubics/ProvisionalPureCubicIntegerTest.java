/*
 * Copyright (C) 2025 Alonso del Arte
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
package algebraics.cubics;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import fractions.Fraction;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertDoesNotThrow;
import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the ProvisionalPureCubicInteger class. Normally I would do this sort 
 * of work in a file not checked into version control. But as I expect this work 
 * to take me a long time, I decided it would be better to have this checked 
 * into version control.
 * @author Alonso del Arte
 */
public class ProvisionalPureCubicIntegerTest {
    
    private static final char CUBIC_ROOT_SYMBOL = '\u221B';
    
    private static final char EXPONENT_TWO_SYMBOL = '\u00B2';
    
    private static PureCubicRing chooseRing() {
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        return new PureCubicRing(d);
    }
    
    private static PureCubicRing chooseRingD1Mod9() {
        int d;
        do {
            d = randomSquarefreeNumber(300);
        } while (d % 9 != 1);
        return new PureCubicRing(d);
    }
    
    private static Fraction wrapInteger(int n) {
        return new Fraction(n);
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int bound = 2048;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        PureCubicRing ring = chooseRing();
        CubicInteger instance = new ProvisionalPureCubicInteger(a, b, c, ring);
        int d = ring.getRadicand();
        String expected = a + "+" + b + Character.toString(CUBIC_ROOT_SYMBOL) 
                + d + "+" + c + ("(" + CUBIC_ROOT_SYMBOL) + d + ")" 
                + EXPONENT_TWO_SYMBOL;
        String actual = instance.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringIntegersFromFractions() {
        int bound = 2048;
        int numerA = randomNumber(bound) + 1;
        int numerB = randomNumber(bound) + 2;
        int numerC = randomNumber(bound) + 2;
        Fraction a = new Fraction(numerA);
        Fraction b = new Fraction(numerB);
        Fraction c = new Fraction(numerC);
        PureCubicRing ring = chooseRing();
        CubicInteger instance = new ProvisionalPureCubicInteger(a, b, c, ring);
        int d = ring.getRadicand();
        String expected = numerA + "+" + numerB 
                + Character.toString(CUBIC_ROOT_SYMBOL) + d + "+" + numerC 
                + ("(" + CUBIC_ROOT_SYMBOL) + d + ")" + EXPONENT_TWO_SYMBOL;
        String actual = instance.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringIntegersFromThirds() {
        PureCubicRing ring = chooseRingD1Mod9();
        Fraction a = new Fraction(randomSquarefreeNumberMod(1, 3), 3);
        Fraction b = new Fraction(randomSquarefreeNumberMod(1, 3), 3);
        Fraction c = new Fraction(randomSquarefreeNumberMod(1, 3), 3);
        CubicInteger instance = new ProvisionalPureCubicInteger(a, b, c, ring);
        int d = ring.getRadicand();
        String expected = a.toString() + "+" + b.toString()
                + Character.toString(CUBIC_ROOT_SYMBOL) + d + "+" 
                + c.toString() + ("(" + CUBIC_ROOT_SYMBOL) + d + ")" 
                + EXPONENT_TWO_SYMBOL;
        String actual = instance.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toASCIIString function, of the ProvisionalPureCubicInteger 
     * class.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int bound = 2048;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        PureCubicRing ring = chooseRing();
        CubicInteger instance = new ProvisionalPureCubicInteger(a, b, c, ring);
        int d = ring.getRadicand();
        String expected = a + "+" + b + "cbrt(" + d + ")+" + c + "cbrt(" + d 
                + ")^2";
        String actual = instance.toASCIIString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Test of toTeXString method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
//        ProvisionalPureCubicInteger instance = null;
//        String expResult = "";
//        String result = instance.toTeXString();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toHTMLString method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
//        ProvisionalPureCubicInteger instance = null;
//        String expResult = "";
//        String result = instance.toHTMLString();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    // TODO: Write tests for algebraic degrees 0, 1, 2

    /**
     * Test of the algebraicDegree function, of the ProvisionalPureCubicInteger 
     * class.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        int bound = 2048;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        PureCubicRing ring = chooseRing();
        CubicInteger instance = new ProvisionalPureCubicInteger(a, b, c, ring);
        int expected = 3;
        int actual = instance.algebraicDegree();
        String message = "Reckoning degree for " + instance.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Test of trace method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
//        ProvisionalPureCubicInteger instance = null;
//        long expResult = 0L;
//        long result = instance.trace();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of norm method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
//        ProvisionalPureCubicInteger instance = null;
//        long expResult = 0L;
//        long result = instance.norm();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialCoeffs method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testMinPolynomialCoeffs() {
        System.out.println("minPolynomialCoeffs");
//        ProvisionalPureCubicInteger instance = null;
//        long[] expResult = null;
//        long[] result = instance.minPolynomialCoeffs();
//        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialString method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
//        ProvisionalPureCubicInteger instance = null;
//        String expResult = "";
//        String result = instance.minPolynomialString();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialStringTeX method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testMinPolynomialStringTeX() {
        System.out.println("minPolynomialStringTeX");
//        ProvisionalPureCubicInteger instance = null;
//        String expResult = "";
//        String result = instance.minPolynomialStringTeX();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialStringHTML method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testMinPolynomialStringHTML() {
        System.out.println("minPolynomialStringHTML");
//        ProvisionalPureCubicInteger instance = null;
//        String expResult = "";
//        String result = instance.minPolynomialStringHTML();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abs method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
//        ProvisionalPureCubicInteger instance = null;
//        double expResult = 0.0;
//        double result = instance.abs();
//        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRealPartNumeric method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartNumeric");
//        ProvisionalPureCubicInteger instance = null;
//        double expResult = 0.0;
//        double result = instance.getRealPartNumeric();
//        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the getImagPartNumeric function, of the 
     * ProvisionalPureCubicInteger class.
     */
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartNumeric");
        int bound = 2048;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        CubicRing ring = chooseRing();
        ProvisionalPureCubicInteger instance 
                = new ProvisionalPureCubicInteger(a, b, c, ring);
        double expected = 0.0;
        double actual = instance.getImagPartNumeric();
        double delta = 0.00000001;
        String message = "Imaginary part of " + instance.toString() 
                + " should be zero";
        assertEquals(message, expected, actual, delta);
    }

    /**
     * Test of isReApprox method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testIsReApprox() {
        System.out.println("isReApprox");
//        ProvisionalPureCubicInteger instance = null;
//        boolean expResult = false;
//        boolean result = instance.isReApprox();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isImApprox method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testIsImApprox() {
        System.out.println("isImApprox");
//        ProvisionalPureCubicInteger instance = null;
//        boolean expResult = false;
//        boolean result = instance.isImApprox();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of angle method, of class ProvisionalPureCubicInteger.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
//        ProvisionalPureCubicInteger instance = null;
//        double expResult = 0.0;
//        double result = instance.angle();
//        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testIntParamsConstructorRejectsNullRing() {
        int bound = 2048;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        String msg = "Null ring should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, b, c, 
                    null);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullFractionA() {
        Fraction b = wrapInteger(randomNumber());
        Fraction c = wrapInteger(randomNumber());
        CubicRing ring = chooseRing();
        String msg = "Null fraction A should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(null, b, 
                    c, ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullFractionB() {
        Fraction a = wrapInteger(randomNumber());
        Fraction c = wrapInteger(randomNumber());
        CubicRing ring = chooseRing();
        String msg = "Null fraction B should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, null,  
                    c, ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullFractionC() {
        Fraction a = wrapInteger(randomNumber());
        Fraction b = wrapInteger(randomNumber());
        CubicRing ring = chooseRing();
        String msg = "Null fraction C should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, b, 
                    null, ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testFractionParamsConstructorRejectsNullRing() {
        Fraction a = wrapInteger(randomNumber());
        Fraction b = wrapInteger(randomNumber());
        Fraction c = wrapInteger(randomNumber());
        String msg = "Null ring should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, b, c, 
                    null);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testFractionParamsConstructorRequiresPureCubicRing() {
        Fraction a = wrapInteger(randomNumber());
        Fraction b = wrapInteger(randomNumber());
        Fraction c = wrapInteger(randomNumber());
        CubicRing ring = new CubicRingTest.CubicRingImpl();
        String msg = "Ring of class " + ring.getClass().getName() 
                + " instead of " + PureCubicRing.class.getName() 
                + " should cause exception";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, b, c, 
                    ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testIntParamsConstructorRequiresPureCubicRing() {
        int a = randomNumber();
        int b = randomNumber();
        int c = randomNumber();
        CubicRing ring = new CubicRingTest.CubicRingImpl();
        String msg = "Ring of class " + ring.getClass().getName() 
                + " instead of " + PureCubicRing.class.getName() 
                + " should cause exception";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, b, c, 
                    ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testFractionParamsConstructorRejectsNonIntegerFractions() {
        PureCubicRing ring = chooseRing();
        int denom = randomNumber(128) + 2;
        Fraction a = new Fraction(randomSquarefreeNumberMod(1, denom), denom);
        Fraction b = new Fraction(randomSquarefreeNumberMod(1, denom), denom);
        Fraction c = new Fraction(randomSquarefreeNumberMod(1, denom), denom);
        Fraction aCubed = a.times(a).times(a);
        Fraction bCubed = b.times(b).times(b);
        Fraction cCubed = c.times(c).times(c);
        int d = ring.getRadicand();
        Fraction norm = aCubed.plus(bCubed.times(d)).plus(cCubed.times(d * d))
                .minus(a.times(b.times(c.times(3 * d))));
        String radicStr = " " + CUBIC_ROOT_SYMBOL + "(" + d + ")";
        String msg = "Given that " + a.toString() + " + " + b.toString() 
                + radicStr + " + " + c.toString() + radicStr 
                + EXPONENT_TWO_SYMBOL + " has norm " + norm.toString() 
                + ", constructor should have thrown an exception";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new ProvisionalPureCubicInteger(a, b, c, 
                    ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String normStr = norm.toString();
        String containsMsg = "Exception message should contain norm \"" 
                + normStr + "\"";
        assert excMsg.contains(normStr) : containsMsg;
    }
    
    @Test
    public void testFractionParamsConstructorDoesAcceptFractionsForInteger() {
        PureCubicRing ring = chooseRingD1Mod9();
        Fraction a = new Fraction(randomSquarefreeNumberMod(1, 3), 3);
        Fraction b = new Fraction(randomSquarefreeNumberMod(1, 3), 3);
        Fraction c = new Fraction(randomSquarefreeNumberMod(1, 3), 3);
        Fraction aCubed = a.times(a).times(a);
        Fraction bCubed = b.times(b).times(b);
        Fraction cCubed = c.times(c).times(c);
        int d = ring.getRadicand();
        Fraction norm = aCubed.plus(bCubed.times(d)).plus(cCubed.times(d * d))
                .minus(a.times(b.times(c.times(3 * d))));
        String radicStr = " " + CUBIC_ROOT_SYMBOL + "(" + d + ")";
        String msg = "Given that " + a.toString() + " + " + b.toString() 
                + radicStr + " + " + c.toString() + radicStr 
                + EXPONENT_TWO_SYMBOL + " has norm " + norm.toString() 
                + ", constructor should not have thrown an exception";
        assertDoesNotThrow(() -> {
            CubicInteger instance = new ProvisionalPureCubicInteger(a, b, c, 
                    ring);
            System.out.println(msg);
            System.out.println("Successfully instantiated " 
                    + instance.toString());
        }, msg);
    }

}
