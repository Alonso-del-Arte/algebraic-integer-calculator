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
package algebraics.unary;

import algebraics.IntegerRing;
import arithmetic.NotDivisibleException;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator.randomPowerOfTwo;
import fractions.Fraction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertContainsSameOrder;
import static org.testframe.api.Asserters.assertDoesNotThrow;
import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the UnaryInteger class.
 * @author Alonso del Arte
 */
public class UnaryIntegerTest {
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegative() {
        int n = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger number = new UnaryInteger(-n);
        String expected = "\u2212" + n;
        String actual = number.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringNegative() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringNegative() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringNegative() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        String expected = "&minus;" + Integer.toString(-n);
        String actual = number.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        int expected = randomNumber();
        UnaryInteger instance = new UnaryInteger(expected);
        int actual = instance.getNumber();
        String message = "Getting number for " + instance.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        assertEquals(number, number);
    }
    
    @Test
    public void testNotEqualsNull() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        assertNotEquals(number, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        Object actual = LocalDateTime.now();
        assertNotEquals(number, actual);
    }
    
    @Test
    public void testNotEqualsDiffNum() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger someNumber = new UnaryInteger(n);
        UnaryInteger otherNumber = new UnaryInteger(-n + 1);
        assertNotEquals(someNumber, otherNumber);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger someNumber = new UnaryInteger(n);
        UnaryInteger sameNumber = new UnaryInteger(n);
        assertEquals(someNumber, sameNumber);
    }
    
    @Test
    public void testHashCode() {
        int initialCapacity = randomNumber(64) + 16;
        Set<UnaryInteger> integers = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            int bound = 2 * (initialCapacity + 1); 
            int n = i * randomNumber(bound) + i + 1;
            UnaryInteger number = new UnaryInteger(n);
            integers.add(number);
            hashes.add(number.hashCode());
        }
        int expected = integers.size();
        int actual = hashes.size();
        String message = "Each unique integer should have a unique hash code";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        int n = randomNumber() | randomPowerOfTwo();
        UnaryInteger instance = new UnaryInteger(n);
        int expected = 1;
        int actual = instance.algebraicDegree();
        String message = "Algebraic degree of " + instance.toString() 
                + " should be " + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testAlgebraicDegreeZero() {
        int expected = 0;
        UnaryInteger instance = new UnaryInteger(expected);
        int actual = instance.algebraicDegree();
        String message = "Algebraic degree of " + instance.toString() 
                + " should be " + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testTrace() {
        System.out.println("trace");
        int expected = randomNumber();
        UnaryInteger number = new UnaryInteger(expected);
        long actual = number.trace();
        String message = "Reckoning trace of " + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNorm() {
        System.out.println("norm");
        int expected = randomNumber();
        UnaryInteger number = new UnaryInteger(expected);
        long actual = number.norm();
        String message = "Reckoning norm of " + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialCoeffsZero() {
        int n = 0;
        UnaryInteger zero = new UnaryInteger(n);
        long[] expecteds = {n, 1L};
        long[] actuals = zero.minPolynomialCoeffs();
        String message = "Reckoning minimal polynomial coefficients for " 
                + zero.toString();
        assertArrayEquals(message, expecteds, actuals);
    }
    
    @Test
    public void testMinPolynomialCoeffs() {
        System.out.println("minPolynomialCoeffs");
        int n = randomNumber() | randomPowerOfTwo();
        UnaryInteger instance = new UnaryInteger(n);
        long[] expecteds = {-n, 1L};
        long[] actuals = instance.minPolynomialCoeffs();
        String message = "Reckoning minimal polynomial coefficients for " 
                + instance.toString();
        assertArrayEquals(message, expecteds, actuals);
    }
    
    @Test
    public void testMinPolynomialStringZero() {
        int n = 0;
        UnaryInteger zero = new UnaryInteger(n);
        String expected = "x";
        String actual = zero.minPolynomialString().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + zero.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringNegative() {
        int n = randomNumber() | Integer.MIN_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        String expected = "x+" + (-n);
        String actual = number.minPolynomialString().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        int n = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        UnaryInteger number = new UnaryInteger(n);
        String expected = "x\u2212" + n;
        String actual = number.minPolynomialString().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringTeXZero() {
        int n = 0;
        UnaryInteger zero = new UnaryInteger(n);
        String expected = "x";
        String actual = zero.minPolynomialStringTeX().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + zero.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringTeXNegative() {
        int n = randomNumber() | Integer.MIN_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        String expected = "x+" + (-n);
        String actual = number.minPolynomialStringTeX().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringTeX() {
        System.out.println("minPolynomialStringTeX");
        int n = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        UnaryInteger number = new UnaryInteger(n);
        String expected = "x-" + n;
        String actual = number.minPolynomialStringTeX().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringHTMLZero() {
        int n = 0;
        UnaryInteger zero = new UnaryInteger(n);
        String expected = "<i>x</i>";
        String actual = zero.minPolynomialStringHTML().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + zero.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringHTMLNegative() {
        int n = randomNumber() | Integer.MIN_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        String expected = "<i>x</i>+" + (-n);
        String actual = number.minPolynomialStringHTML().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinPolynomialStringHTML() {
        System.out.println("minPolynomialStringHTML");
        int n = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        UnaryInteger number = new UnaryInteger(n);
        String expected = "<i>x</i>&minus;" + n;
        String actual = number.minPolynomialStringHTML().replace(" ", "");
        String message = "Reckoning minimal polynomial for " + number.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        int n = randomNumber();
        UnaryInteger number = new UnaryInteger(n);
        IntegerRing expected = UnaryRing.Z;
        IntegerRing actual = number.getRing();
        String message = "Ring containing " + number.toString() + " should be " 
                + expected.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testAbsZero() {
        UnaryInteger zero = new UnaryInteger(0);
        double expected = 0.0;
        double actual = zero.abs();
        double delta = 0.1;
        String message = "Reckoning absolute value of " + zero.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testAbs() {
        System.out.println("abs");
        int n = randomNumber() | Integer.MIN_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        double expected = -1.0 * n;
        double actual = number.abs();
        double delta = 0.1;
        String message = "Reckoning absolute value of " + number.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testAbsAlreadyPositive() {
        int n = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        UnaryInteger number = new UnaryInteger(n);
        double expected = (double) n;
        double actual = number.abs();
        double delta = 0.1;
        String message = "Reckoning absolute value of " + number.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartNumeric");
        int n = randomNumber();
        UnaryInteger number = new UnaryInteger(n);
        double expected = (double) n;
        double actual = number.getRealPartNumeric();
        double delta = 0.1;
        String message = "Reckoning real part of " + number.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartNumeric");
        int n = randomNumber();
        UnaryInteger number = new UnaryInteger(n);
        double expected = 0.0;
        double actual = number.getImagPartNumeric();
        double delta = 0.1;
        String message = "Reckoning imaginary part of " + number.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testIsReApprox() {
        System.out.println("isReApprox");
        int n = randomNumber();
        UnaryInteger number = new UnaryInteger(n);
        String msg = "Real part of " + number.toString() 
                + " should not be considered an approximation";
        assert !number.isReApprox() : msg;
    }
    
    @Test
    public void testIsImApprox() {
        System.out.println("isImApprox");
        int n = randomNumber();
        UnaryInteger number = new UnaryInteger(n);
        String msg = "Imaginary part of " + number.toString() 
                + " should not be considered an approximation";
        assert !number.isImApprox() : msg;
    }
    
    @Test
    public void testAngleOfZero() {
        UnaryInteger zero = new UnaryInteger(0);
        double expected = 0.0;
        double actual = zero.angle();
        double delta = 0.00000001;
        String message = "Reckoning angle of " + zero.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testAngleOfPositive() {
        int n = (randomNumber() & Integer.MAX_VALUE) | randomPowerOfTwo();
        UnaryInteger number = new UnaryInteger(n);
        double expected = 0.0;
        double actual = number.angle();
        double delta = 0.00000001;
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testAngle() {
        System.out.println("angle");
        int n = randomNumber() | Integer.MIN_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        double expected = Math.PI;
        double actual = number.angle();
        double delta = 0.00000001;
        String message = "Reckoning angle of " + number.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    @Test
    public void testPlus() {
        System.out.println("plus");
        int a = randomNumber(Short.MAX_VALUE) + 1;
        int b = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger addendA = new UnaryInteger(a);
        UnaryInteger addendB = new UnaryInteger(b);
        UnaryInteger expected = new UnaryInteger(a + b);
        UnaryInteger actual = addendA.plus(addendB);
        String message = "Adding " + addendA.toString() + " to " 
                + addendB.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testPlusTooNegative() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MIN_VALUE + adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int addN = -2 * adjustment;
        UnaryInteger addend = new UnaryInteger(addN);
        long overflown = (long) n + addN;
        String msg = "Adding " + number.toString() + " and " + addend.toString() 
                + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger sum = number.plus(addend);
            System.out.println(msg + ", not given result " + sum.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testPlusTooPositive() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MAX_VALUE - adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int addN = 2 * adjustment;
        UnaryInteger addend = new UnaryInteger(addN);
        long overflown = (long) n + addN;
        String msg = "Adding " + number.toString() + " and " + addend.toString() 
                + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger sum = number.plus(addend);
            System.out.println(msg + ", not given result " + sum.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testPlusInt() {
        int a = randomNumber(Short.MAX_VALUE) + 1;
        int addend = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger number = new UnaryInteger(a);
        UnaryInteger expected = new UnaryInteger(a + addend);
        UnaryInteger actual = number.plus(addend);
        String message = "Adding " + number.toString() + " to " + addend;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testPlusIntTooNegative() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MIN_VALUE + adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int addend = -2 * adjustment;
        long overflown = (long) n + addend;
        String msg = "Adding " + number.toString() + " and " + addend  
                + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger sum = number.plus(addend);
            System.out.println(msg + ", not given result " + sum.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testPlusIntTooPositive() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MAX_VALUE - adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int addend = 2 * adjustment;
        long overflown = (long) n + addend;
        String msg = "Adding " + number.toString() + " and " + addend  
                + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger sum = number.plus(addend);
            System.out.println(msg + ", not given result " + sum.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testMinus() {
        System.out.println("minus");
        int a = randomNumber(Short.MAX_VALUE) + 1;
        int b = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger minuend = new UnaryInteger(a);
        UnaryInteger subtrahend = new UnaryInteger(b);
        UnaryInteger expected = new UnaryInteger(a - b);
        UnaryInteger actual = minuend.minus(subtrahend);
        String message = "Subtracting " + subtrahend.toString() + " from " 
                + minuend.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinusTooNegative() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MAX_VALUE - adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int subtractN = -2 * adjustment;
        UnaryInteger subtrahend = new UnaryInteger(subtractN);
        long overflown = (long) n - subtractN;
        String msg = "Subtracting " + subtrahend.toString() + " from " 
                + number.toString() + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger difference = number.minus(subtrahend);
            System.out.println(msg + ", not given result " 
                    + difference.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testMinusTooPositive() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MIN_VALUE + adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int subtractN = 2 * adjustment;
        UnaryInteger subtrahend = new UnaryInteger(subtractN);
        long overflown = (long) n - subtractN;
        String msg = "Subtracting " + subtrahend.toString() + " from " 
                + number.toString() + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger difference = number.minus(subtrahend);
            System.out.println(msg + ", not given result " 
                    + difference.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testMinusInt() {
        int a = randomNumber(Short.MAX_VALUE) + 1;
        int subtrahend = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger minuend = new UnaryInteger(a);
        UnaryInteger expected = new UnaryInteger(a - subtrahend);
        UnaryInteger actual = minuend.minus(subtrahend);
        String message = "Subtracting " + subtrahend + " from " 
                + minuend.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMinusIntTooNegative() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MAX_VALUE - adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int subtrahend = -2 * adjustment;
        long overflown = (long) n - subtrahend;
        String msg = "Subtracting " + subtrahend + " from " + number.toString() 
                + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger difference = number.minus(subtrahend);
            System.out.println(msg + ", not given result " 
                    + difference.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testMinusIntTooPositive() {
        int adjustment = randomNumber(Short.MAX_VALUE) + 1;
        int n = Integer.MIN_VALUE + adjustment;
        UnaryInteger number = new UnaryInteger(n);
        int subtrahend = 2 * adjustment;
        long overflown = (long) n - subtrahend;
        String msg = "Subtracting " + subtrahend + " from " + number.toString() 
                + " which overflows to " + overflown 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger difference = number.minus(subtrahend);
            System.out.println(msg + ", not given result " 
                    + difference.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testNegate() {
        System.out.println("negate");
        int n = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger number = new UnaryInteger(n);
        UnaryInteger expected = new UnaryInteger(-n);
        UnaryInteger actual = number.negate();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNegateTurnsNegativePositive() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        UnaryInteger expected = new UnaryInteger(-n);
        UnaryInteger actual = number.negate();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNegateKeepsZeroAsZero() {
        int n = 0;
        UnaryInteger expected = new UnaryInteger(n);
        UnaryInteger actual = expected.negate();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testTimes() {
        System.out.println("times");
        int signAdjust = (randomNumber(128) - 64 < 0) ? -1 : 1;
        int nA = randomNumber(Short.MAX_VALUE) * signAdjust;
        UnaryInteger number = new UnaryInteger(nA);
        int nB = randomNumber(Short.MAX_VALUE);
        UnaryInteger multiplicand = new UnaryInteger(nB);
        UnaryInteger expected = new UnaryInteger(nA * nB);
        UnaryInteger actual = number.times(multiplicand);
        String message = "Multiplying " + number.toString() + " by " 
                + multiplicand.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testTimesTooMuch() {
        int signAdjust = (randomNumber(128) - 64 < 0) ? -1 : 1;
        int nA = signAdjust * (Integer.MAX_VALUE - 16
                - randomNumber(Byte.MAX_VALUE));
        UnaryInteger number = new UnaryInteger(nA);
        int nB = Integer.MAX_VALUE - 16 - randomNumber(Byte.MAX_VALUE);
        UnaryInteger multiplicand = new UnaryInteger(nB);
        long result = (long) nA * nB;
        String msg = "Multiplying " + number.toString() + " by " 
                + multiplicand.toString() + " should overflow to " + result;
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = number.times(multiplicand);
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testTimesInt() {
        int signAdjust = (randomNumber(128) - 64 < 0) ? -1 : 1;
        int nA = randomNumber(Short.MAX_VALUE) * signAdjust;
        UnaryInteger number = new UnaryInteger(nA);
        int multiplicand = randomNumber(Short.MAX_VALUE);
        UnaryInteger expected = new UnaryInteger(nA * multiplicand);
        UnaryInteger actual = number.times(multiplicand);
        String message = "Multiplying " + number.toString() + " by " 
                + multiplicand;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testTimesIntTooMuch() {
        int signAdjust = (randomNumber(128) - 64 < 0) ? -1 : 1;
        int nA = signAdjust * (Integer.MAX_VALUE - 16
                - randomNumber(Byte.MAX_VALUE));
        UnaryInteger number = new UnaryInteger(nA);
        int multiplicand = Integer.MAX_VALUE - 16 
                - randomNumber(Byte.MAX_VALUE);
        long result = (long) nA * multiplicand;
        String msg = "Multiplying " + number.toString() + " by " + multiplicand
                + " should overflow to " + result;
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = number.times(multiplicand);
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, ArithmeticException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testDivisionByZero() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger dividend = new UnaryInteger(n);
        UnaryInteger divisor = new UnaryInteger(0);
        String msg = "Dividing " + dividend.toString() + " by " 
                + divisor.toString() + " should cause an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = dividend.divides(divisor);
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String dividendStr = dividend.toASCIIString();
        String divisorStr = divisor.toASCIIString();
        String containsMsg = "Exception message should contain " + dividendStr 
                + " and " + divisorStr;
        assert excMsg.contains(dividendStr) : containsMsg;
        assert excMsg.contains(divisorStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testDivides() {
        System.out.println("divides");
        int n = randomNumber(Byte.MAX_VALUE) + 1;
        UnaryInteger expected = new UnaryInteger(n);
        int nB = randomNumber(1024) + 16;
        UnaryInteger divisor = new UnaryInteger(nB);
        int nA = nB * n;
        UnaryInteger dividend = new UnaryInteger(nA);
        String message = "Dividing " + dividend.toString() + " by " 
                + divisor.toString();
        assertDoesNotThrow(() -> {
            UnaryInteger actual = dividend.divides(divisor);
            assertEquals(message, expected, actual);
        }, message);     
    }
    
    @Test
    public void testDivideByCoprime() {
        int sign = Integer.signum(2 * randomNumber() + 1);
        int positive = randomNumber(Byte.MAX_VALUE) + 3;
        int n = sign * positive;
        UnaryInteger divisor = new UnaryInteger(n);
        int multiplier = randomNumber(128) + 2;
        int remainder = randomNumber(positive - 1) + 1;
        int nA = n * multiplier + remainder;
        UnaryInteger dividend = new UnaryInteger(nA);
        String msg = "Trying to divide " + dividend.toString() + " by " 
                + divisor.toString() + " should cause NotDivisibleException";
        NotDivisibleException nde = assertThrows(() -> {
            UnaryInteger badResult = dividend.divides(divisor);
            System.out.println(msg + ", not given result " + badResult);
        }, NotDivisibleException.class, msg);
        assertEquals(dividend, nde.getCausingDividend());
        assertEquals(divisor, nde.getCausingDivisor());
        Fraction division = new Fraction(nA, n);
        Fraction[] expecteds = {division};
        Fraction[] actuals = nde.getFractions();
        String message = msg + " with fraction " + division.toString() 
                + " and message listing dividend and divisor";
        assertArrayEquals(message, expecteds, actuals);
        String excMsg = nde.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        assert excMsg.contains(Integer.toString(nA)) 
                || excMsg.contains(dividend.toString()) : message;
        assert excMsg.contains(Integer.toString(n)) 
                || excMsg.contains(divisor.toString()) : message;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testDivisionByIntZero() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger dividend = new UnaryInteger(n);
        int divisor = 0;
        String msg = "Dividing " + dividend.toString() + " by " + divisor 
                + " should cause an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = dividend.divides(divisor);
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String dividendStr = dividend.toASCIIString();
        String containsMsg = "Exception message should contain " + dividendStr 
                + " and 0";
        assert excMsg.contains(dividendStr) : containsMsg;
        assert excMsg.contains("0") : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testDividesInt() {
        int n = randomNumber(Byte.MAX_VALUE) + 1;
        UnaryInteger expected = new UnaryInteger(n);
        int divisor = randomNumber(1024) + 16;
        int nA = divisor * n;
        UnaryInteger dividend = new UnaryInteger(nA);
        String message = "Dividing " + dividend.toString() + " by " + divisor;
        assertDoesNotThrow(() -> {
            UnaryInteger actual = dividend.divides(divisor);
            assertEquals(message, expected, actual);
        }, message + " should not cause any exception");
    }
    
    @Test
    public void testDivideByCoprimeInt() {
        int sign = Integer.signum(2 * randomNumber() + 1);
        int positive = randomNumber(Byte.MAX_VALUE) + 3;
        int divisor = sign * positive;
        int multiplier = randomNumber(128) + 2;
        int remainder = randomNumber(positive - 1) + 1;
        int nA = divisor * multiplier + remainder;
        UnaryInteger dividend = new UnaryInteger(nA);
        String msg = "Trying to divide " + dividend.toString() + " by " 
                + divisor + " should cause NotDivisibleException";
        NotDivisibleException nde = assertThrows(() -> {
            UnaryInteger badResult = dividend.divides(divisor);
            System.out.println(msg + ", not given result " + badResult);
        }, NotDivisibleException.class, msg);
        assertEquals(dividend, nde.getCausingDividend());
        assertEquals(new UnaryInteger(divisor), nde.getCausingDivisor());
        Fraction division = new Fraction(nA, divisor);
        Fraction[] expecteds = {division};
        Fraction[] actuals = nde.getFractions();
        String message = msg + " with fraction " + division.toString() 
                + " and message listing dividend and divisor";
        assertArrayEquals(message, expecteds, actuals);
        String excMsg = nde.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        assert excMsg.contains(dividend.toASCIIString()) : message;
        assert excMsg.contains(Integer.toString(divisor)) : message;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testModZero() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger dividend = new UnaryInteger(n);
        UnaryInteger divisor = new UnaryInteger(0);
        String msg = "Taking " + dividend.toString() + " modulo " 
                + divisor.toString() + " should cause an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = dividend.mod(divisor);
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String dividendStr = dividend.toASCIIString();
        String divisorStr = divisor.toASCIIString();
        String containsMsg = "Exception message should contain " + dividendStr 
                + " and " + divisorStr;
        assert excMsg.contains(dividendStr) : containsMsg;
        assert excMsg.contains(divisorStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testMod() {
        System.out.println("mod");
        int divisorN = randomNumber(256) + 4;
        int dividendN = 256 * divisorN;
        UnaryInteger divisor = new UnaryInteger(divisorN);
        for (int n = 0; n < divisorN; n++) {
            UnaryInteger dividend = new UnaryInteger(dividendN + n);
            String message = "Reckoning " + dividend.toString() + " mod " 
                    + divisor.toString();
            UnaryInteger expected = new UnaryInteger(n);
            assertDoesNotThrow(() -> {
                UnaryInteger actual = dividend.mod(divisor);
                assertEquals(message, expected, actual);
            }, message + " should not cause any exception");
        }
    }
    
    @Test
    public void testModIntZero() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger dividend = new UnaryInteger(n);
        int divisor = 0;
        String msg = "Taking " + dividend.toString() + " modulo " + divisor 
                + " should cause an exception";
        Throwable t = assertThrows(() -> {
            UnaryInteger badResult = dividend.mod(divisor);
            System.out.println(msg + ", not given result " 
                    + badResult.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String dividendStr = dividend.toASCIIString();
        String divisorStr = Integer.toString(divisor);
        String containsMsg = "Exception message should contain " + dividendStr 
                + " and " + divisorStr;
        assert excMsg.contains(dividendStr) : containsMsg;
        assert excMsg.contains(divisorStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testModInt() {
        int divisor = randomNumber(256) + 4;
        int dividendN = 256 * divisor;
        for (int n = 0; n < divisor; n++) {
            UnaryInteger dividend = new UnaryInteger(dividendN + n);
            String message = "Reckoning " + dividend.toString() + " mod " 
                    + divisor;
            UnaryInteger expected = new UnaryInteger(n);
            assertDoesNotThrow(() -> {
                UnaryInteger actual = dividend.mod(divisor);
                assertEquals(message, expected, actual);
            }, message + " should not cause any exception");
        }
    }
    
    private static List<UnaryInteger> makeList() {
        int initialCapacity = randomNumber(64) + 16;
        List<UnaryInteger> list = new ArrayList<>(initialCapacity);
        int bound = 8192;
        int halfBound = bound / 2;
        for (int i = 0; i < initialCapacity; i++) {
            int n = randomNumber(bound) - halfBound + i;
            list.add(new UnaryInteger(n));
        }
        return list;
    }
    
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        List<UnaryInteger> actual = makeList();
        List<UnaryInteger> expected = new ArrayList<>(actual);
        Collections.sort(expected, new UnaryIntegerComparator());
        Collections.sort(actual);
        assertContainsSameOrder(expected, actual);
    }
    
    private static class UnaryIntegerComparator 
            implements Comparator<UnaryInteger> {
        
        @Override
        public int compare(UnaryInteger a, UnaryInteger b) {
            return Integer.compare(a.getNumber(), b.getNumber());
        }
        
    }
    
}
