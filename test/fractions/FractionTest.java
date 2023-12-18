/*
 * Copyright (C) 2023 Alonso del Arte
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
package fractions;

import static calculators.NumberTheoreticFunctionsCalculator.euclideanGCD;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.EratosthenesSieve.randomPrime;

import clipboardops.TestImagePanel;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Fraction class. When I started working on this test class in 
 * late 2018, I was still new to test-driven development (TDD). Also, I did not 
 * realize how important the Fraction class would become to the whole project. 
 * In 2020, or perhaps earlier, I had become dissatisfied with this test class, 
 * but I was hesitant to make any big changes. I finally undertook the task of 
 * making all the changes I wanted in 2021. I will almost certainly still make 
 * changes in the future, but they will probably be much smaller changes.
 * @author Alonso del Arte
 */
public class FractionTest {
    
    private static final Random RANDOM = new Random();
    
    /**
     * Test of the getNumerator function, of class Fraction. A separate test 
     * checks that a Fraction object expresses a fraction in lowest terms, this 
     * test does not, because the denominator is 1033, which is prime, and the 
     * numerator is a number between 1 and 1032, ensuring the fraction is in 
     * lowest terms.
     */
    @Test
    public void testGetNumerator() {
        System.out.println("getNumerator");
        int denominator = 1033;
        int expected = randomNumber(denominator - 2) + 1;
        Fraction fraction = new Fraction(expected, denominator);
        long actual = fraction.getNumerator();
        assertEquals(expected, actual);
    }

    /**
     * Test of the getDenominator function, of class Fraction. A separate test 
     * checks that a Fraction object expresses a fraction in lowest terms, this 
     * test does not.
     */
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        int expected = randomNumber(32768) + 16384;
        Fraction fraction = new Fraction(1, expected);
        long actual = fraction.getDenominator();
        assertEquals(expected, actual);
    }

    /**
     * Test of the equals function, of class Fraction. A Fraction instance 
     * should be equal to itself.
     */
    @Test
    public void testReferentialEquality() {
        Fraction someFraction = new Fraction(-1, 2);
        assertEquals(someFraction, someFraction);
    }
    
    /**
     * Another test of the equals function, of class Fraction. No Fraction 
     * instance should be equal to null, not even if its value is 0.
     */
    @Test
    public void testNotEqualsNull() {
        Fraction someFraction = new Fraction(0);
        assertNotEquals(someFraction, null);
    }
    
    /**
     * Another test of the equals function, of class Fraction. No Fraction 
     * instance should be equal to an instance of a different class.
     */
    @Test
    public void testNotEqualsDiffClass() {
        Fraction someFraction = new Fraction(12, 25);
        TestImagePanel obj = new TestImagePanel();
        assertNotEquals(someFraction, obj);
    }
    
    /**
     * Test of the equals function, of class Fraction.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Fraction someFraction = new Fraction(3, 4);
        Fraction sameFraction = new Fraction(6, 8);
        assertEquals(someFraction, sameFraction);
    }
    
    /**
     * Another test of the equals function, of class Fraction. A Fraction 
     * instance should not be equal to null to another Fraction instance which 
     * in lowest terms has the same denominator but a different numerator.
     */
    @Test
    public void testNotEqualsDifferentNumerator() {
        Fraction someFraction = new Fraction(12, 13);
        Fraction otherFraction = new Fraction(14, 13);
        assertNotEquals(someFraction, otherFraction);
    }
    
    /**
     * Another test of the equals function, of class Fraction. A Fraction 
     * instance should not be equal to null to another Fraction instance which 
     * in lowest terms has the same numerator but a different denominator.
     */
    @Test
    public void testNotEqualsDifferentDenominator() {
        Fraction someFraction = new Fraction(13, 12);
        Fraction otherFraction = new Fraction(13, 14);
        assertNotEquals(someFraction, otherFraction);
    }
    
    /**
     * Test of the plus function, of class Fraction.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        Fraction addendA = new Fraction(11, 24);
        Fraction addendB = new Fraction(107, 224);
        Fraction expected = new Fraction(629, 672);
        Fraction actual = addendA.plus(addendB);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of plus function, of class Fraction. Since addition is 
     * commutative, a + b should equal b + a.
     */
    @Test
    public void testPlusCommutative() {
        int numerator = RANDOM.nextInt();
        if (numerator == Integer.MAX_VALUE) {
            numerator--;
        }
        int denominator = randomNumber(Math.abs(numerator)) + 1;
        Fraction addendA = new Fraction(numerator, denominator);
        Fraction addendB = new Fraction(numerator + 1, denominator);
        Fraction expected = addendA.plus(addendB);
        Fraction actual = addendB.plus(addendA);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of plus function, of class Fraction.
     */
    @Test
    public void testPlusInt() {
        Fraction addendA = new Fraction(7, 8);
        int addendB = 3;
        Fraction expected = new Fraction(31, 8);
        Fraction actual = addendA.plus(addendB);
        assertEquals(expected, actual);
    }

    /**
     * Test of the minus function, of class Fraction.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        Fraction minuend = new Fraction(109, 224);
        Fraction subtrahend = new Fraction(13, 24);
        Fraction expected = new Fraction(-37, 672);
        Fraction actual = minuend.minus(subtrahend);
        assertEquals(expected, actual);
    }

    /**
     * Another test of the minus function, of class Fraction.
     */
    @Test
    public void testMinusInt() {
        Fraction minuend = new Fraction(7, 8);
        int subtrahend = 1;
        Fraction expected = new Fraction(-1, 8);
        Fraction actual = minuend.minus(subtrahend);
        assertEquals(expected, actual);
    }

    /**
     * Test of the times function, of class Fraction.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        Fraction multiplicandA = new Fraction(41, 43);
        Fraction multiplicandB = new Fraction(49, 47);
        Fraction expected = new Fraction(2009, 2021);
        Fraction actual = multiplicandA.times(multiplicandB);
        assertEquals(expected, actual);
    }

    /**
     * Another test of the times function, of class Fraction. Since 
     * multiplication is commutative, a * b should equal b * a.
     */
    @Test
    public void testTimesCommutative() {
        int numerator = RANDOM.nextInt(32768);
        int denominator = RANDOM.nextInt(Math.abs(numerator)) + 1;
        Fraction multiplicandA = new Fraction(numerator, denominator);
        Fraction multiplicandB = new Fraction(numerator, denominator + 1);
        Fraction expected = multiplicandA.times(multiplicandB);
        Fraction actual = multiplicandB.times(multiplicandA);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the times function, of class Fraction.
     */
    @Test
    public void testTimesInt() {
        Fraction multiplicandA = new Fraction(9, 5);
        int multiplicandB = 35;
        Fraction expected = new Fraction(63, 1);
        Fraction actual = multiplicandA.times(multiplicandB);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the dividedBy function, of class Fraction.
     */
    @Test
    public void testDividedBy() {
        System.out.println("dividedBy");
        Fraction dividend = new Fraction(7, 8);
        Fraction divisor = new Fraction(9, 5);
        Fraction expected = new Fraction(35, 72);
        Fraction actual = dividend.dividedBy(divisor);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the dividedBy function, of class Fraction.
     */
    @Test
    public void testDividedByInt() {
        Fraction dividend = new Fraction(56, 25);
        int divisor = 7;
        Fraction expected = new Fraction(8, 25);
        Fraction actual = dividend.dividedBy(divisor);
        assertEquals(expected, actual);
    }

    /**
     * Another test of the dividedBy function, of class Fraction. Trying to 
     * divide by zero should cause either {@link IllegalArgumentException} or 
     * {@link ArithmeticException}. Any other exception will fail the test. So 
     * will giving any kind of result.
     */
    @Test
    public void testDivisionByZeroCausesException() {
        int numerator = randomNumber(392);
        int denominator = numerator + 1;
        Fraction fraction = new Fraction(numerator, denominator);
        Fraction zero = new Fraction(0, 1);
        Fraction result;
        try {
            result = fraction.dividedBy(zero);
            String msg = "Trying to divide " + fraction.toString()
                    + " by 0 should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to divide " + fraction.toString()
                    + " by 0 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("Trying to divide " + fraction.toString()
                    + " by 0 correctly caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is not an appropriate exception for trying to divide " 
                    + fraction.toString() + " by 0";
            fail(msg);
        }
    }

    /**
     * Another test of the dividedBy function, of class Fraction. Trying to 
     * divide by zero should cause either {@link IllegalArgumentException} or 
     * {@link ArithmeticException}. Any other exception will fail the test. So 
     * will giving any kind of result.
     */
    @Test
    public void testDivisionByIntZeroCausesException() {
        int numerator = RANDOM.nextInt(784);
        int denominator = numerator + 3;
        Fraction fraction = new Fraction(numerator, denominator);
        Fraction result;
        try {
            result = fraction.dividedBy(0);
            String msg = "Trying to divide " + fraction.toString()
                    + " by 0 should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to divide " + fraction.toString()
                    + " by 0 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("Trying to divide " + fraction.toString()
                    + " by 0 correctly caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is not an appropriate exception for trying to divide " 
                    + fraction.toString() + " by 0";
            fail(msg);
        }
    }

    /**
     * Test of the negate function, of class Fraction. Applying negate twice 
     * should return the original number.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        int numerator = RANDOM.nextInt(4096);
        int denominator = 2 * numerator + 5;
        Fraction fraction = new Fraction(numerator, denominator);
        Fraction expected = new Fraction(-numerator, denominator);
        Fraction actual = fraction.negate();
        assertEquals(expected, actual);
        assertEquals(actual.negate(), fraction);
    }

    /**
     * Test of the reciprocal function, of class Fraction. Checks that applying 
     * the reciprocal function to a reciprocal returns the original number. A 
     * separate test checks the reciprocal of 0.
     */
    @Test
    public void testReciprocal() {
        System.out.println("reciprocal");
        int numerator = randomNumber(8192) + 16;
        int denominator = 5 * randomNumber(numerator) + 7;
        Fraction fraction = new Fraction(numerator, denominator);
        Fraction expected = new Fraction(denominator, numerator);
        Fraction actual = fraction.reciprocal();
        assertEquals(expected, actual);
        assertEquals(actual.reciprocal(), fraction);
    }

    /**
     * Another test of the reciprocal function, of class Fraction. Checks that 
     * trying to take the reciprocal of zero triggers the appropriate exception.
     */
    @Test
    public void testReciprocalOfZero() {
        Fraction zero = new Fraction(0, 1);
        String msgPart = "Reciprocal of zero ";
        try {
            Fraction result = zero.reciprocal();
            String message = msgPart + "should not have given result " 
                    + result.toString();
            fail(message);
        } catch (IllegalArgumentException | ArithmeticException iae) {
            System.out.println(msgPart + "correctly caused " 
                    + iae.getClass().getName());
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = msgPart + " should not have caused " 
                    + re.getClass().getName();
            fail(message);
        }
    }
    
    /**
     * Test of the roundDown function, of the Fraction class.
     */
    @Test
    public void testRoundDown() {
        System.out.println("roundDown");
        int multiplier = randomNumber(14) + 2;
        int denom = multiplier * (randomNumber(60) + 4);
        int numer = 3 * denom / 2 + 1;
        Fraction fract = new Fraction(numer, denom);
        Fraction oneHalf = new Fraction(1, 2);
        Fraction expected = new Fraction(3, 2);
        Fraction actual = fract.roundDown(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the roundDown function, of the Fraction class. If a 
     * fraction is negative, rounding down should not bring the fraction closer 
     * to 0. For example, given a rounding interval of 1/7, -47/70 should round 
     * down to -5/7, not -4/7.
     */
    @Test
    public void testRoundDownNegative() {
        int multiplier = randomNumber(14) + 2;
        int denom = multiplier * (randomNumber(60) + 4);
        int numer = -5 * denom / 7 + 1;
        Fraction fract = new Fraction(numer, denom);
        Fraction oneSeventh = new Fraction(1, 7);
        Fraction expected = new Fraction(-5, 7);
        Fraction actual = fract.roundDown(oneSeventh);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the roundDown function, of the Fraction class. If the 
     * fraction to be rounded by a given interval is already an integer multiple 
     * of that interval, it should be unchanged (though it is acceptable if a 
     * new object is created). For example, 5/8 rounded down by 1/8 should 
     * remain 5/8.
     */
    @Test
    public void testRoundDownNotNeeded() {
        int denom = 40 * (randomNumber(60) + 4);
        int numer = 5 * denom / 8;
        Fraction fract = new Fraction(numer, denom);
        Fraction oneEighth = new Fraction(1, 8);
        Fraction expected = new Fraction(5, 8);
        Fraction actual = fract.roundDown(oneEighth);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the roundDown function, of the Fraction class. Trying to 
     * use 0 for the rounding interval should cause an exception.
     */
    @Test
    public void testRoundDownByZeroCausesException() {
        int multiplier = randomNumber(14) + 2;
        int denom = multiplier * (randomNumber(60) + 4);
        int numer = 3 * denom / 2 + 1;
        Fraction fract = new Fraction(numer, denom);
        Fraction zero = new Fraction(0);
        try {
            Fraction badRound = fract.roundDown(zero);
            String message = "Trying to round " + fract.toString() + " down by " 
                    + zero.toString() + " should not have given result " 
                    + badRound.toString();
            fail(message);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to round " + fract.toString() + " by " 
                    + zero.toString() + " correctly caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to round " + fract.toString() + " by " 
                    + zero.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for trying to round " 
                    + fract.toString() + " by " + zero.toString();
            fail(message);
        }
    }
    
    /**
     * Test of the roundUp function, of the Fraction class.
     */
    @Test
    public void testRoundup() {
        System.out.println("roundUp");
        int multiplier = randomNumber(14) + 2;
        int denom = multiplier * (randomNumber(60) + 4);
        int numer = 3 * denom / 2 - 1;
        Fraction fract = new Fraction(numer, denom);
        Fraction oneHalf = new Fraction(1, 2);
        Fraction expected = new Fraction(3, 2);
        Fraction actual = fract.roundUp(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the roundUp function, of the Fraction class. Trying to 
     * round up a negative fraction should bring it closer to 0. For example, 
     * -47/70 should round up to -4/7, not -5/7.
     */
    @Test
    public void testRoundUpNegative() {
        int multiplier = randomNumber(14) + 2;
        int denom = multiplier * (randomNumber(60) + 4);
        int numer = -5 * denom / 7 - 1;
        Fraction fract = new Fraction(numer, denom);
        Fraction oneSeventh = new Fraction(1, 7);
        Fraction expected = new Fraction(-5, 7);
        Fraction actual = fract.roundUp(oneSeventh);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the roundUp function, of the Fraction class. If the 
     * fraction to be rounded by a given interval is already an integer multiple 
     * of that interval, it should be unchanged (though it is acceptable if a 
     * new object is created). For example, 5/8 rounded down by 1/8 should 
     * remain 5/8.
     */
    @Test
    public void testRoundUpNotNeeded() {
        int denom = 40 * (randomNumber(60) + 4);
        int numer = 5 * denom / 8;
        Fraction fract = new Fraction(numer, denom);
        Fraction oneEighth = new Fraction(1, 8);
        Fraction expected = new Fraction(5, 8);
        Fraction actual = fract.roundUp(oneEighth);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the roundUp function, of the Fraction class. Trying to 
     * use 0 for the rounding interval should cause an exception.
     */
    @Test
    public void testRoundUpByZeroCausesException() {
        int multiplier = randomNumber(14) + 2;
        int denom = multiplier * (randomNumber(60) + 4);
        int numer = 3 * denom / 2 + 1;
        Fraction fract = new Fraction(numer, denom);
        Fraction zero = new Fraction(0);
        try {
            Fraction badRound = fract.roundUp(zero);
            String message = "Trying to round " + fract.toString() + " up by " 
                    + zero.toString() + " should not have given result " 
                    + badRound.toString();
            fail(message);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to round " + fract.toString() + " by " 
                    + zero.toString() 
                    + " correctly caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to round " + fract.toString() + " by " 
                    + zero.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for trying to round " 
                    + fract.toString() + " by " + zero.toString();
            fail(message);
        }
    }
    
    /**
     * Test of the conformDown function, of the Fraction class.
     */
    @Test
    public void testConformDown() {
        System.out.println("conformDown");
        Fraction fraction = new Fraction(513, 50);
        Fraction expected = new Fraction(41, 4);
        Fraction actual = fraction.conformDown(4);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the conformDown function, of the Fraction class. If the 
     * fraction to be conformed is negative, the resulting fraction should still 
     * not be greater than the original fraction.
     */
    @Test
    public void testConformDownNegative() {
        Fraction fraction = new Fraction(-513, 50);
        Fraction expected = new Fraction(-43, 4);
        Fraction actual = fraction.conformDown(4);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the conformDown function, of the Fraction class. If the 
     * fraction to be conformed is already conformed, the result should be the 
     * same as the original fraction. Whether or not a new object is created 
     * does not affect whether or not this test passes.
     */
    @Test
    public void testConformDownNotNeeded() {
        Fraction expected = new Fraction(7, 8);
        Fraction actual = expected.conformDown(8);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the conformDown function, of the Fraction class. If the 
     * denominator to be conformed to is negative, a result should be given, but 
     * at this point I'm not terribly concerned about what that result should 
     * be.
     */
    @Test
    public void testConformDownAcceptsNegativeParameter() {
        Fraction fraction = new Fraction(7, 8);
        Fraction[] acceptables = new Fraction[]{new Fraction(-15, 16), 
            new Fraction(-13, 16), new Fraction(13, 16), 
            new Fraction(15, 16)};
        int param = -16;
        try {
            Fraction result = fraction.conformDown(param);
            List<Fraction> fractions = Arrays.asList(acceptables);
            boolean accepted = fractions.contains(result);
            String msg = "Result " + result.toString() + " should be one of " 
                    + fractions.toString();
            assert accepted : msg;
        } catch (RuntimeException re) {
            String msg = "Trying to conform " + fraction.toString() + " to " 
                    + param + " should not have caused " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    /**
     * Another test of the conformDown function, of the Fraction class. If the 
     * denominator to be conformed to is 0, an exception should occur. Either 
     * ArithmeticException or IllegalArgumentException is acceptable.
     */
    @Test
    public void testConformDownRejectsParameterZero() {
        Fraction fraction = new Fraction(7, 8);
        try {
            Fraction result = fraction.conformDown(0);
            String msg = "Trying to conform " + fraction.toString() 
                    + " to denominator 0 should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to conform " + fraction.toString() 
                    + " to denominator 0 caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to conform " + fraction.toString() 
                    + " to denominator 0 caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to conform " 
                    + fraction.toString() + " to 0"; 
            fail(msg);
        }
    }
    
    /**
     * Test of the conformUp function, of the Fraction class.
     */
    @Test
    public void testConformUp() {
        System.out.println("conformUp");
        Fraction fraction = new Fraction(513, 50);
        Fraction expected = new Fraction(43, 4);
        Fraction actual = fraction.conformUp(4);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the conformUp function, of the Fraction class. If the 
     * fraction to be conformed is negative, the resulting fraction should still 
     * not be less than the original fraction.
     */
    @Test
    public void testConformUpNegative() {
        Fraction fraction = new Fraction(-513, 50);
        Fraction expected = new Fraction(-41, 4);
        Fraction actual = fraction.conformUp(4);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the conformUp function, of the Fraction class. If the 
     * fraction to be conformed is already conformed, the result should be the 
     * same as the original fraction. Whether or not a new object is created 
     * does not affect whether or not this test passes.
     */
    @Test
    public void testConformUpNotNeeded() {
        Fraction expected = new Fraction(7, 8);
        Fraction actual = expected.conformUp(8);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the conformUp function, of the Fraction class. If the 
     * denominator to be conformed to is negative, a result should be given, but 
     * at this point I'm not terribly concerned about what that result should  
     * be.
     */
    @Test
    public void testConformUpAcceptsNegativeParameter() {
        Fraction fraction = new Fraction(7, 8);
        Fraction[] acceptables = new Fraction[]{new Fraction(-15, 16), 
            new Fraction(-13, 16), new Fraction(13, 16), new Fraction(15, 16)};
        int param = -16;
        try {
            Fraction result = fraction.conformUp(param);
            List<Fraction> fractions = Arrays.asList(acceptables);
            boolean accepted = fractions.contains(result);
            String msg = "Result " + result.toString() + " should be one of " 
                    + fractions.toString();
            assert accepted : msg;
        } catch (RuntimeException re) {
            String msg = "Trying to conform " + fraction.toString() + " to " 
                    + param + " should not have caused " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    /**
     * Another test of the conformUp function, of the Fraction class. If the 
     * denominator to be conformed to is 0, an exception should occur. Either 
     * ArithmeticException or IllegalArgumentException is acceptable.
     */
    @Test
    public void testConformUpRejectsParameterZero() {
        Fraction fraction = new Fraction(7, 8);
        try {
            Fraction result = fraction.conformUp(0);
            String msg = "Trying to conform " + fraction.toString() 
                    + " to denominator 0 should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to conform " + fraction.toString() 
                    + " to denominator 0 caused ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to conform " + fraction.toString() 
                    + " to denominator 0 caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to conform " 
                    + fraction.toString() + " to 0"; 
            fail(msg);
        }
    }
    
    /**
     * Test of the toString function, of class Fraction. Spaces are acceptable 
     * in the output, so this test strips them out.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int denominator = 4099;
        int numerator = randomNumber(denominator - 2) + 1;
        Fraction fraction = new Fraction(numerator, denominator);
        String expected = numerator + "/" + denominator;
        String actual = fraction.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toString function, of class Fraction. Spaces are 
     * acceptable in the output, so this test strips them out.
     */
    @Test
    public void testToStringOmitsDenomOne() {
        int numerator = RANDOM.nextInt();
        Fraction fraction = new Fraction(numerator, 1);
        String expected = Integer.toString(numerator);
        String actual = fraction.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Test of the toHTMLString function, of class Fraction. Spaces are 
     * acceptable in the output; this test strips them out.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        int denominator = 8419;
        int numerator = randomNumber(denominator - 2) + 1;
        Fraction fraction = new Fraction(numerator, denominator);
        String expected = "<sup>" + numerator + "</sup>&frasl;<sub>" 
                + denominator + "</sub>";
        String actual = fraction.toHTMLString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toHTMLString function, of class Fraction. If the 
     * fraction is a negative number, the minus sign should not be a superscript 
     * like the numerator. Spaces are acceptable in the output; this test strips 
     * them out.
     */
    @Test
    public void testToHTMLStringNegativeFraction() {
        Fraction negOneHalf = new Fraction(-1, 2);
        String expected = "&minus;<sup>1</sup>&frasl;<sub>2</sub>";
        String actual = negOneHalf.toHTMLString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toHTMLString function, of class Fraction. If the 
     * fraction is a negative integer, this function's output should omit the 
     * tacit denominator 1, but it should still include the proper minus sign as 
     * an HTML character entity.
     */
    @Test
    public void testToHTMLStringOmitsDenomOne() {
        Fraction negOne = new Fraction(-256, 1);
        String expected = "&minus;256";
        String actual = negOne.toHTMLString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Test of the toTeXString function, of class Fraction. Spaces are 
     * acceptable in the output, so this test strips them out.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        int denominator = 2503;
        int numerator = randomNumber(denominator - 2) + 1;
        Fraction fraction = new Fraction(numerator, denominator);
        String expected = "\\frac{" + numerator + "}{" + denominator + "}";
        String actual = fraction.toTeXString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the toTeXString function, of class Fraction. If the 
     * fraction is a negative number, the minus sign should be aligned to the 
     * fraction line rather than the vertical center of the numerator digits. 
     * Spaces are acceptable in the output; this test strips them out.
     */
    @Test
    public void testToTeXStringNegativeFraction() {
        Fraction negOneHalf = new Fraction(-1, 2);
        String expected = "-\\frac{1}{2}";
        String actual = negOneHalf.toTeXString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toTeXString function, of class Fraction. If the 
     * fraction is a negative integer, the tacit denominator 1
     */
    @Test
    public void testToTeXStringOmitsDenomOne() {
        int numerator = -randomNumber(44100);
        Fraction negativeInteger = new Fraction(numerator, 1);
        String expected = Integer.toString(numerator);
        String actual = negativeInteger.toTeXString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Test of the toString, toHTMLString, toTeXString functions of class 
     * Fraction. Fractions that are integers should be reported with the 
     * denominator of 1 as tacit, not explicit.
     */
    @Test
    public void testIntegersHaveTacitDenominator() {
        Fraction actuallyAnInteger = new Fraction(12, 1);
        assertEquals("12", actuallyAnInteger.toString());
        assertEquals("12", actuallyAnInteger.toHTMLString());
        assertEquals("12", actuallyAnInteger.toTeXString());
    }
    
    /**
     * Test of the hashCode function, of class Fraction. More than 400 instances 
     * of Fraction are created, representing distinct fractions with 
     * denominators ranging from 2 to 103. They should all get distinct hash 
     * codes.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        HashSet<Integer> fractHashes = new HashSet<>(435);
        Fraction currUnitFract, currFract;
        int currHash, currSize, prevSize;
        currUnitFract = new Fraction(1, 102);
        currHash = currUnitFract.hashCode();
        fractHashes.add(currHash);
        currUnitFract = new Fraction(1, 103);
        currHash = currUnitFract.hashCode();
        fractHashes.add(currHash);
        String msg = "Set of Fraction hash codes should have two hash codes";
        assertEquals(msg, 2, fractHashes.size());
        prevSize = 2;
        for (int n = 2; n < 102; n++) {
            if (euclideanGCD(n, 102) == 1) {
                currUnitFract = new Fraction(1, n);
                currHash = currUnitFract.hashCode();
                fractHashes.add(currHash);
                currSize = fractHashes.size();
                msg = "Hash code " + currHash + " for " 
                        + currUnitFract.toString() + " should be distinct.";
                assert currSize > prevSize : msg;
                prevSize++;
            }
            currFract = new Fraction(n, 102);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            msg = "Hash code " + currHash + " for " + currFract.toString() 
                    + " should be distinct.";
            assert currSize > prevSize : msg;
            prevSize++;
            currFract = new Fraction(-n, 102);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            msg = "Hash code " + currHash + " for " + currFract.toString() 
                    + " should be distinct.";
            assert currSize > prevSize : msg;
            prevSize++;
            currFract = new Fraction(n, 103);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            msg = "Hash code " + currHash + " for " + currFract.toString() 
                    + " should be distinct.";
            assertTrue(msg, currSize > prevSize);
            prevSize++;
            currFract = new Fraction(-n, 103);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            msg = "Hash code " + currHash + " for " + currFract.toString() 
                    + " should be distinct.";
            assert currSize > prevSize : msg;
            prevSize++;
        }
        System.out.println("Successfully created " + prevSize 
                + " instances of Fraction with " + prevSize 
                + " distinct hash codes.");
    }
    
    /**
     * Test of the compareTo function, of class Fraction, implementing the 
     * Comparable interface.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Fraction negThreeHalves = new Fraction(-3, 2);
        Fraction approxPiFiftieths = new Fraction(157, 50);
        Fraction approxPi113ths = new Fraction(355, 113);
        Fraction approxPiSevenths = new Fraction(22, 7);
        List<Fraction> expectedList = new ArrayList<>();
        expectedList.add(negThreeHalves);
        expectedList.add(approxPiFiftieths);
        expectedList.add(approxPi113ths);
        expectedList.add(approxPiSevenths);
        List<Fraction> unsortedList = new ArrayList<>();
        unsortedList.add(approxPi113ths);
        unsortedList.add(negThreeHalves);
        unsortedList.add(approxPiSevenths);
        unsortedList.add(approxPiFiftieths);
        Collections.sort(unsortedList);
        assertEquals(expectedList, unsortedList);
    }
    
    /**
     * Another test of the compareTo function, of class Fraction. This one tests 
     * whether Fraction can distinguish between 1/9223372036854775807 and 
     * 1/9223372036854775806.
     */
    @Test
    public void testCompareToCloseFraction() {
        Fraction numberA = new Fraction(1, Integer.MAX_VALUE);
        Fraction numberB = new Fraction(1, Integer.MAX_VALUE - 1);
        String msg = numberA.toString() + " should be found to be less than " 
                + numberB.toString();
        assert numberA.compareTo(numberB) < 0 : msg;
        msg = numberB.toString() + " should be found to be greater than " 
                + numberA.toString();
        assert numberB.compareTo(numberA) > 0 : msg;
    }
    
    /**
     * Test of the getNumericApproximation function, of class Fraction. A small 
     * variance in numeric precision is acceptable. The tolerance is 
     * 10<sup>&minus;8</sup>.
     */
    @Test
    public void testGetNumericApproximation() {
        System.out.println("getNumericApproximation");
        Fraction thirds;
        double thirdApprox = 1.0 / 3.0;
        double expected, actual;
        for (int i = 1; i < 31; i++) {
            thirds = new Fraction(i, 3);
            expected = thirdApprox * i;
            actual = thirds.getNumericApproximation();
            assertEquals(expected, actual, 0.00000001);
        }
    }
    
    /**
     * Test of the isUnitFraction function, of class Fraction. Any fraction with 
     * 1 for a numerator is a unit fraction.
     */
    @Test
    public void testIsUnitFraction() {
        System.out.println("isUnitFraction");
        Fraction fraction = new Fraction(1, 7);
        String msg = fraction.toString() 
                + " should be considered a unit fraction";
        assert fraction.isUnitFraction() : msg;
    }
    
    /**
     * Another test of the isUnitFraction function, of class Fraction. Any 
     * fraction with 1 for a numerator is a unit fraction. Any other numerator 
     * means the fraction should not be considered a unit fraction, even if that 
     * numerator is &minus;1.
     */
    @Test
    public void testIsNotUnitFraction() {
        Fraction fraction = new Fraction(-1, 7);
        String msg = fraction.toString() 
                + " should NOT be considered a unit fraction";
        assert !fraction.isUnitFraction() : msg;
    }
    
    /**
     * Test of the isInteger function, of class Fraction. Any Fraction instance 
     * with 1 for a denominator represents an integer.
     */
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        Fraction fraction = new Fraction(7, 1);
        String msg = fraction.toString() + " should be considered an integer";
        assert fraction.isInteger() : msg;
    }

    /**
     * Another test of the isInteger function, of class Fraction. Any Fraction 
     * with 1 for a denominator represents an integer. Any denominator other 
     * than 1 should cause a fraction to not be considered an integer, even 
     * &minus;1 (however, if the constructor receives a negative integer as a 
     * denominator, it should multiply both the numerator and denominator by 
     * &minus;1 &mdash; but that's a separate test).
     */
    @Test
    public void testIsNotInteger() {
        Fraction fraction = new Fraction(7, 2);
        String msg = fraction.toString() 
                + " should NOT be considered an integer";
        assert !fraction.isInteger() : msg;
    }

    /**
     * Test of the parseFract function, of class Fraction.
     */
    @Test
    public void testParseFract() {
        System.out.println("parseFract");
        String s = "-4/7";
        Fraction expected = new Fraction(-4, 7);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }

    /**
     * Another test of the parseFract function, of class Fraction.
     */
    @Test
    public void testParseOtherFract() {
        String s = "355/113";
        Fraction expected = new Fraction(355, 113);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }

    /**
     * Another test of the parseFract function, of class Fraction. If the String 
     * represents an integer (tacit denominator 1), the parsing function should 
     * fill in the tacit denominator.
     */
    @Test
    public void testParseInteger() {
        String s = "32768";
        Fraction expected = new Fraction(32768, 1);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the parseFract function, of class Fraction. The parsing 
     * function should not be thrown by the proper minus sign, U+2212.
     */
    @Test
    public void testParseFractRecognizesProperMinusSign() {
        String s = "\u22121/2";
        try{
            String msg = s + " should be recognized as negative one half";
            Fraction expected = new Fraction(-1, 2);
            Fraction actual = Fraction.parseFract(s);
            assertEquals(msg, expected, actual);
        } catch (NumberFormatException nfe) {
            String msg = "Parsing " + s 
                    + " should not have caused NumberFormatException";
            System.out.println("\"" + nfe.getMessage() + "\"");
            fail(msg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is inappropriate for parsing " + s;
            fail(msg);
        }
    }

    /**
     * Another test of the parseFract function, of class Fraction. Spaces should 
     * be allowed in the parsing function's input.
     */
    @Test
    public void testParseFractIgnoresSpaces() {
        String s = "355 / 113";
        Fraction expected = new Fraction(355, 113);
        try {
            Fraction actual = Fraction.parseFract(s);
            assertEquals(expected, actual);
        } catch (NumberFormatException nfe) {
            String msg = "NumberFormatException shouldn't occur for parsing \"" 
                    + s + "\"";
            System.out.println(msg);
            System.out.println("\"" + nfe.getMessage() + "\"");
            fail(msg);
        }
    }
    
    /**
     * Another test of the parseFract function, of class Fraction. The output of 
     * toHTMLString should be parseable by parseFract.
     */
    @Test
    public void testParseFractHTML() {
        String s = "&minus;<sup>79</sup>&frasl;<sub>43</sub>";
        Fraction expected = new Fraction(-79, 43);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the parseFract function, of class Fraction. The output of 
     * toTeXString should be parseable by parseFract.
     */
    @Test
    public void testParseFractTeX() {
        String s = "\\frac{79}{43}";
        Fraction expected = new Fraction(79, 43);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the correspondence between the toString and parseFract functions 
     * of the Fraction class.
     */
    @Test
    public void testParseFractToStringCorrespondence() {
        Fraction expected = new Fraction(53, 40);
        Fraction actual = Fraction.parseFract(expected.toString());
        assertEquals(expected, actual);
    }

    /**
     * Test of the correspondence between the toHTMLString and parseFract 
     * functions of the Fraction class.
     */
    @Test
    public void testParseFractToHTMLStringCorrespondence() {
        Fraction expected = new Fraction(53, 40);
        Fraction actual = Fraction.parseFract(expected.toHTMLString());
        assertEquals(expected, actual);
    }

    /**
     * Test of the correspondence between the toTeXString and parseFract 
     * functions of the Fraction class.
     */
    @Test
    public void testParseFractToTeXStringCorrespondence() {
        Fraction expected = new Fraction(53, 40);
        Fraction actual = Fraction.parseFract(expected.toTeXString());
        assertEquals(expected, actual);
    }

    /**
     * Test of the Fraction constructor. Even if the constructor parameters are 
     * not in lowest terms, the constructor should change them to lowest terms.
     */
    @Test
    public void testFractionsAreInLowestTerms() {
        Fraction fraction;
        int prime = 103;
        for (int numerator = 1; numerator < prime; numerator++) {
            for (int multiplier = 2; multiplier < 29; multiplier++) {
                fraction = new Fraction(numerator * multiplier, prime 
                        * multiplier);
                assertEquals(numerator, fraction.getNumerator());
                assertEquals(prime, fraction.getDenominator());
            }
        }
    }
    
    /**
     * Test of the Fraction constructor. Even if the denominator passed to the 
     * constructor is negative, the constructor should change it to a positive 
     * integer, and change the numerator accordingly.
     */
    @Test
    public void testNegativeDenomsQuietlyChanged() {
        Fraction fraction = new Fraction(12, -29);
        assertEquals(-12L, fraction.getNumerator());
        assertEquals(29L, fraction.getDenominator());
    }
    
    /**
     * Test of the Fraction constructor. If the denominator passed to the 
     * constructor is negative, the constructor should change it to a positive 
     * integer. However, if the denominator happens to be Long.MIN_VALUE, the 
     * constructor can't multiply it by &minus;1 because the desired number is 
     * Long.MAX_VALUE + 1, so the Java Virtual Machine just gives 0. Therefore, 
     * Long.MIN_VALUE should be just as invalid as 0 as a denominator.
     */
    @Test
    public void testConstructorRejectsLongMinValueDenom() {
        try {
            Fraction badFraction = new Fraction(-7, Long.MIN_VALUE);
            String msg = "Should not have been able to create fraction " 
                    + badFraction.toString();
            fail(msg);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to use denominator " + Long.MIN_VALUE 
                    + " correctly caused ArithmeticException");
            String excMsg = ae.getMessage();
            assert excMsg != null : "Exception message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for denominator " 
                    + Long.MIN_VALUE;
            fail(msg);
        }
    }
        
}
