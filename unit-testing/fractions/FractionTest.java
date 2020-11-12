/*
 * Copyright (C) 2020 Alonso del Arte
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

import calculators.NumberTheoreticFunctionsCalculator;
import clipboardops.TestImagePanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Fraction class.
 * @author Alonso del Arte
 */
public class FractionTest {
    
    /**
     * The test delta, 10<sup>&minus;8</sup>.
     */
    public static final double TEST_DELTA = 0.00000001;
    
    /**
     * This one should be initialized to 7/8 in setUp().
     */
    private Fraction operandA;
    
    /**
     * This one should be initialized to 1/3 in setUp().
     */
    private Fraction operandB;
    
    /**
     * Initializes two Fraction objects that will be used in a lot of the tests. 
     * Ideally, a Fraction object should be immutable, but these tests should 
     * not be affected if it's actually mutable.
     */
    @Before
    public void setUp() {
        operandA = new Fraction(7, 8);
        operandB = new Fraction(1, 3);
    }
    
    /**
     * Test of getNumerator method, of class Fraction. A separate test checks 
     * that a Fraction object expresses a fraction in lowest terms, this test 
     * does not.
     */
    @Test
    public void testGetNumerator() {
        System.out.println("getNumerator");
        long expResult = 7L;
        long result = operandA.getNumerator();
        assertEquals(expResult, result);
        expResult = 1L;
        result = operandB.getNumerator();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDenominator method, of class Fraction. A separate test checks 
     * that a Fraction object expresses a fraction in lowest terms, this test 
     * does not.
     */
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        long expResult = 8L;
        long result = operandA.getDenominator();
        assertEquals(expResult, result);
        expResult = 3L;
        result = operandB.getDenominator();
        assertEquals(expResult, result);
    }

    /**
     * Test of plus method, of class Fraction.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        Fraction expResult = new Fraction(29, 24);
        Fraction result = operandA.plus(operandB);
        assertEquals(expResult, result);
        result = operandB.plus(operandA); // Commutative test
        assertEquals(expResult, result);
        expResult = new Fraction(15, 8);
        result = operandA.plus(1);
        assertEquals(expResult, result);
        expResult = new Fraction(4, 3);
        result = operandB.plus(1);
        assertEquals(expResult, result);
    }

    /**
     * Test of minus method, of class Fraction.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        Fraction expResult = new Fraction(13, 24);
        Fraction result = operandA.minus(operandB);
        assertEquals(expResult, result);
        expResult = new Fraction(-1, 8);
        result = operandA.minus(1);
        assertEquals(expResult, result);
        expResult = new Fraction(-2, 3);
        result = operandB.minus(1);
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Fraction.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        Fraction expResult = new Fraction(7, 24);
        Fraction result = operandA.times(operandB);
        assertEquals(expResult, result);
        result = operandB.times(operandA); // Commutative test
        assertEquals(expResult, result);
        expResult = new Fraction(21, 2);
        result = operandA.times(12);
        assertEquals(expResult, result);
        expResult = new Fraction(4);
        result = operandB.times(12);
        assertEquals(expResult, result);
    }

    /**
     * Test of dividedBy method, of class Fraction.
     */
    @Test
    public void testDividedBy() {
        System.out.println("dividedBy");
        Fraction expResult = new Fraction(21, 8);
        Fraction result = operandA.dividedBy(operandB);
        assertEquals(expResult, result);
        expResult = new Fraction(7, 96);
        result = operandA.dividedBy(12);
        assertEquals(expResult, result);
        expResult = new Fraction(1, 36);
        result = operandB.dividedBy(12);
        assertEquals(expResult, result);
    }

    /**
     * Test of dividedBy method, of class Fraction. Trying to divide by zero 
     * should cause either {@link IllegalArgumentException} or {@link 
     * ArithmeticException}. Any other exception will fail the test. So will 
     * giving any kind of result.
     */
    @Test
    public void testDivisionByZeroCausesException() {
        System.out.println("Division by zero should cause an exception");
        Fraction zero = new Fraction(0, 1);
        Fraction result;
        try {
            result = operandA.dividedBy(zero);
            String msg = operandA.toString() 
                    + " divided by 0 should have caused exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException is considered preferable for division by zero.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is deemed acceptable for division by zero.");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (Exception e) {
            String msg = e.getClass().getName() + " is not an appropriate exception for division by zero.";
            fail(msg);
        }
        try {
            result = operandB.dividedBy(0);
            String msg = operandB.toString() + " divided by 0 should have caused an Exception, not given result " + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException is considered preferable for division by zero.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is deemed acceptable for division by zero.");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (Exception e) {
            String msg = e.getClass().getName() + " is not an appropriate exception for division by zero.";
            fail(msg);
        }
    }

    /**
     * Test of negate method, of class Fraction. Applying negate twice should 
     * return the original number.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Fraction expResult = new Fraction(-7, 8);
        Fraction result = operandA.negate();
        assertEquals(expResult, result);
        result = expResult.negate();
        assertEquals(operandA, result);
        expResult = new Fraction(-1, 3);
        result = operandB.negate();
        assertEquals(expResult, result);
        result = expResult.negate();
        assertEquals(operandB, result);
    }

    /**
     * Test of toString method, of class Fraction. Spaces are acceptable in the 
     * output, so this test strips them out.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "7/8";
        String result = operandA.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1/3";
        result = operandB.toString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class Fraction. Spaces are acceptable in 
     * the output, so this test strips them out.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        String expResult = "<sup>7</sup>&frasl;<sub>8</sub>";
        String result = operandA.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "<sup>1</sup>&frasl;<sub>3</sub>";
        result = operandB.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        Fraction negOneHalf = new Fraction(-1, 2);
        expResult = "<sup>&minus;1</sup>&frasl;<sub>2</sub>";
        result = negOneHalf.toHTMLString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXString method, of class Fraction. Spaces are acceptable in 
     * the output, so this test strips them out.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        String expResult = "\\frac{7}{8}";
        String result = operandA.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "\\frac{1}{3}";
        result = operandB.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toString, toHTMLString, toTeXString methods of class Fraction. 
     * Fractions that are integers should be reported with the denominator of 1 
     * as tacit, not explicit.
     */
    @Test
    public void testIntegersHaveTacitDenominator() {
        System.out.println("Integers should be reported with a tacit, not explicit, denominator");
        Fraction actualInt = new Fraction(12, 1);
        assertEquals("12", actualInt.toString());
        assertEquals("12", actualInt.toHTMLString());
        assertEquals("12", actualInt.toTeXString());
    }
    
    /**
     * Test of hashCode method, of class Fraction.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int operAHash = operandA.hashCode();
        int operBHash = operandB.hashCode();
        System.out.println(operandA.toString() + " hashed as " + operAHash);
        System.out.println(operandB.toString() + " hashed as " + operBHash);
        Fraction operADup = new Fraction(7, 8);
        Fraction operBDup = new Fraction(1, 3);
        int operAHashDup = operADup.hashCode();
        int operBHashDup = operBDup.hashCode();
        System.out.println(operADup.toString() + " hashed as " + operAHashDup);
        System.out.println(operBDup.toString() + " hashed as " + operBHashDup);
        String assertionMessage = operandA.toString() + " and " + operADup.toString() + " should hash the same";
        assertEquals(assertionMessage, operAHash, operAHashDup);
        assertionMessage = operandB.toString() + " and " + operBDup.toString() + " should hash the same";
        assertEquals(assertionMessage, operBHash, operBHashDup);
        assertionMessage = operandA.toString() + " and " + operBDup.toString() + " should hash differently";
        assertNotEquals(assertionMessage, operAHash, operBHashDup);
        assertionMessage = operandB.toString() + " and " + operADup.toString() + " should hash differently";
        assertNotEquals(assertionMessage, operBHash, operAHashDup);
    }
    
    /**
     * Test of hashCode method, of class Fraction. More than 400 instances of 
     * Fraction are created, representing distinct fractions with denominators 
     * ranging from 2 to 103. They should all get distinct hash codes.
     */
    @Test
    public void testHashCodeOnManyFractions() {
        System.out.println("hashCode on several instances of Fraction");
        HashSet<Integer> fractHashes = new HashSet<>(435);
        Fraction currUnitFract, currFract;
        int currHash, currSize, prevSize;
        currUnitFract = new Fraction(1, 102);
        currHash = currUnitFract.hashCode();
        fractHashes.add(currHash);
        currUnitFract = new Fraction(1, 103);
        currHash = currUnitFract.hashCode();
        fractHashes.add(currHash);
        String assertionMessage = "Set of Fraction hash codes should have two hash codes";
        assertEquals(assertionMessage, 2, fractHashes.size());
        prevSize = 2;
        for (int n = 2; n < 102; n++) {
            if (NumberTheoreticFunctionsCalculator.euclideanGCD(n, 102) == 1) {
                currUnitFract = new Fraction(1, n);
                currHash = currUnitFract.hashCode();
                fractHashes.add(currHash);
                currSize = fractHashes.size();
                assertionMessage = "Hash code " + currHash + " for " + currUnitFract.toString() + " should be distinct.";
                assertTrue(assertionMessage, currSize > prevSize);
                prevSize++;
            }
            currFract = new Fraction(n, 102);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            assertionMessage = "Hash code " + currHash + " for " + currFract.toString() + " should be distinct.";
            assertTrue(assertionMessage, currSize > prevSize);
            prevSize++;
            currFract = new Fraction(-n, 102);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            assertionMessage = "Hash code " + currHash + " for " + currFract.toString() + " should be distinct.";
            assertTrue(assertionMessage, currSize > prevSize);
            prevSize++;
            currFract = new Fraction(n, 103);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            assertionMessage = "Hash code " + currHash + " for " + currFract.toString() + " should be distinct.";
            assertTrue(assertionMessage, currSize > prevSize);
            prevSize++;
            currFract = new Fraction(-n, 103);
            currHash = currFract.hashCode();
            fractHashes.add(currHash);
            currSize = fractHashes.size();
            assertionMessage = "Hash code " + currHash + " for " + currFract.toString() + " should be distinct.";
            assertTrue(assertionMessage, currSize > prevSize);
            prevSize++;
        }
        System.out.println("Successfully created " + prevSize + " instances of Fraction with " + prevSize + " distinct hash codes.");
    }
    
    /**
     * Test of equals method, of class Fraction.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertTrue(operandA.equals(operandA)); // Reflexive test
        assertTrue(operandB.equals(operandB));
        Fraction tempHold = new Fraction(7, 8);
        assertTrue(operandA.equals(tempHold));
        assertTrue(tempHold.equals(operandA)); // Symmetric test
        Fraction transitiveHold = new Fraction(7, 8);
        assertTrue(tempHold.equals(transitiveHold));
        assertTrue(transitiveHold.equals(tempHold)); // Transitive test
        tempHold = new Fraction(1, 3); // Now to do symmetric and transitive with operandB
        assertTrue(operandB.equals(tempHold));
        assertTrue(tempHold.equals(operandB)); 
        transitiveHold = new Fraction(1, 3);
        assertTrue(tempHold.equals(transitiveHold));
        assertTrue(transitiveHold.equals(tempHold));
        TestImagePanel obj = new TestImagePanel();
        assertNotEquals(operandA, obj);
    }
    
    /**
     * Test of compareTo method, of class Fraction, implementing Comparable.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Fraction negThreeHalves = new Fraction(-3, 2);
        Fraction approxPiFiftieths = new Fraction(157, 50);
        Fraction approxPi113ths = new Fraction(355, 113);
        Fraction approxPiSevenths = new Fraction(22, 7);
        int comparison = negThreeHalves.compareTo(approxPiFiftieths);
        String assertionMessage = negThreeHalves.toString() + " should be found to be less than " + approxPiFiftieths.toString();
        assertTrue(assertionMessage, comparison < 0);
        comparison = operandB.compareTo(operandA);
        assertionMessage = operandB.toString() + " should be found to be less than " + operandA.toString();
        assertTrue(assertionMessage, comparison < 0);
        comparison = approxPiFiftieths.compareTo(approxPi113ths);
        assertionMessage = approxPiFiftieths.toString() + " should be found to be less than " + approxPi113ths.toString();
        assertTrue(assertionMessage, comparison < 0);
        comparison = approxPi113ths.compareTo(approxPiSevenths);
        assertionMessage = approxPi113ths.toString() + " should be found to be less than " + approxPiSevenths.toString();
        assertTrue(assertionMessage, comparison < 0);
        comparison = approxPiFiftieths.compareTo(approxPiFiftieths);
        assertEquals(0, comparison);
        comparison = approxPi113ths.compareTo(approxPi113ths);
        assertEquals(0, comparison);
        comparison = approxPiSevenths.compareTo(approxPiSevenths);
        assertEquals(0, comparison);
        comparison = operandA.compareTo(operandB);
        assertionMessage = operandA.toString() + " should be found to be greater than " + operandB.toString();
        assertTrue(assertionMessage, comparison > 0);
        comparison = approxPiFiftieths.compareTo(negThreeHalves);
        assertionMessage = approxPiFiftieths.toString() + " should be found to be greater than " + negThreeHalves.toString();
        assertTrue(assertionMessage, comparison > 0);
        comparison = approxPi113ths.compareTo(approxPiFiftieths);
        assertionMessage = approxPi113ths.toString() + " should be found to be greater than " + approxPiFiftieths.toString();
        assertTrue(assertionMessage, comparison > 0);
        comparison = approxPiSevenths.compareTo(approxPi113ths);
        assertionMessage = approxPiSevenths.toString() + " should be found to be greater than " + approxPi113ths.toString();
        assertTrue(assertionMessage, comparison > 0);
        try {
            comparison = approxPiSevenths.compareTo(null);
            String msg = "Comparing " + approxPiSevenths.toString() + " to null should have caused an exception, not given result " + comparison + ".";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Comparing " + approxPiSevenths.toString() + " to null correctly triggered NullPointerException.");
            System.out.println("NullPointerException had this message: \"" + npe.getMessage() + "\"");
        } catch (Exception e) {
            String msg = e.getClass().getName() + " is the wrong exception to throw for comparing " + approxPiSevenths.toString() + " to null.";
            fail(msg);
        }
    }
    
    /**
     * Another test of compareTo method, of class Fraction. This one tests 
     * whether Fraction can distinguish between 1/9223372036854775807 and 
     * 1/9223372036854775806.
     */
    @Test
    public void testCompareToCloseFraction() {
        Fraction numberA = new Fraction(1, Integer.MAX_VALUE);
        Fraction numberB = new Fraction(1, Integer.MAX_VALUE - 1);
        String assertionMessage = numberA.toString() + " should be found to be less than " + numberB.toString();
        assertTrue(assertionMessage, numberA.compareTo(numberB) < 0);
        assertionMessage = numberB.toString() + " should be found to be greater than " + numberA.toString();
        assertTrue(assertionMessage, numberB.compareTo(numberA) > 0);
    }
    
    /**
     * Yet another test of compareTo method, of class Fraction. This one checks 
     * that {@link Collections#sort(java.util.List)} can use compareTo to sort a 
     * list of fractions in ascending order.
     */
    @Test
    public void testCompareToThroughCollectionSort() {
        Fraction negThreeHalves = new Fraction(-3, 2);
        Fraction approxPiFiftieths = new Fraction(157, 50);
        Fraction approxPi113ths = new Fraction(355, 113);
        Fraction approxPiSevenths = new Fraction(22, 7);
        List<Fraction> expectedList = new ArrayList<>();
        expectedList.add(negThreeHalves);
        expectedList.add(operandB);
        expectedList.add(operandA);
        expectedList.add(approxPiFiftieths);
        expectedList.add(approxPi113ths);
        expectedList.add(approxPiSevenths);
        List<Fraction> unsortedList = new ArrayList<>();
        unsortedList.add(approxPi113ths);
        unsortedList.add(negThreeHalves);
        unsortedList.add(approxPiSevenths);
        unsortedList.add(operandA);
        unsortedList.add(approxPiFiftieths);
        unsortedList.add(operandB);
        Collections.sort(unsortedList);
        assertEquals(expectedList, unsortedList);
    }

    /**
     * Test of getNumericApproximation method, of class Fraction. A small 
     * variance in numeric precision is acceptable.
     */
    @Test
    public void testGetNumericApproximation() {
        System.out.println("getNumericApproximation");
        double expResult = 0.875;
        double result = operandA.getNumericApproximation();
        assertEquals(expResult, result, TEST_DELTA);
        expResult = 0.33333333;
        result = operandB.getNumericApproximation();
        assertEquals(expResult, result, TEST_DELTA);
    }
    
    @Test
    public void testIsUnitFraction() {
        System.out.println("isUnitFraction");
        Fraction fraction = new Fraction(1, 7);
        String msg = fraction.toString() 
                + " should be considered a unit fraction";
        assert fraction.isUnitFraction() : msg;
    }
    
    @Test
    public void testIsNotUnitFraction() {
        Fraction fraction = new Fraction(2, 7);
        String msg = fraction.toString() 
                + " should NOT be considered a unit fraction";
        assert !fraction.isUnitFraction() : msg;
    }
    
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        Fraction fraction = new Fraction(7, 1);
        String msg = fraction.toString() + " should be considered an integer";
        assert fraction.isInteger() : msg;
    }

    @Test
    public void testIsNotInteger() {
        Fraction fraction = new Fraction(7, 2);
        String msg = fraction.toString() + " should NOT be considered an integer";
        assert !fraction.isInteger() : msg;
    }

    /**
     * Test of reciprocal method, of class Fraction. Checks that applying the 
     * reciprocal function to a reciprocal returns the original number. A 
     * separate test checks the reciprocal of 0.
     */
    @Test
    public void testReciprocal() {
        System.out.println("reciprocal");
        Fraction expResult = new Fraction(8, 7);
        Fraction result = operandA.reciprocal();
        assertEquals(expResult, result);
        result = expResult.reciprocal();
        assertEquals(operandA, result);
        expResult = new Fraction(3);
        result = operandB.reciprocal();
        assertEquals(expResult, result);
        result = expResult.reciprocal();
        assertEquals(operandB, result);
    }

    /**
     * Another test of reciprocal method, of class Fraction. Checks that trying 
     * to take the reciprocal of zero triggers the appropriate exception.
     */
    @Test
    public void testReciprocalOfZero() {
        System.out.println("reciprocal(0)");
        Fraction zero = new Fraction(0, 1);
        try {
            Fraction result = zero.reciprocal();
            String msg = "Trying to get reciprocal of 0 should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException is appropriate for trying to take the reciprocal of 0.");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is deemed acceptable for trying to take the reciprocal of 0.");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " is not appropriate for trying to take the reciprocal of 0.";
            fail(msg);
        }
    }
    
    /**
     * Test of parseFract method, of class Fraction.
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
     * Another test of parseFract method, of class Fraction.
     */
    @Test
    public void testParseOtherFract() {
        String s = "355/113";
        Fraction expected = new Fraction(355, 113);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }

    /**
     * Another test of parseFract method, of class Fraction.
     */
    @Test
    public void testParseInteger() {
        String s = "32768";
        Fraction expected = new Fraction(32768);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of parseFract method, of class Fraction.
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
     * Another test of parseFract method, of class Fraction.
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
     * Another test of parseFract method, of class Fraction.
     */
    @Test
    public void testParseFractHTML() {
        String s = "<sup>79</sup>&frasl;<sub>43</sub>";
        Fraction expected = new Fraction(79, 43);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of parseFract method, of class Fraction.
     */
    @Test
    public void testParseFractTeX() {
        String s = "\\frac{79}{43}";
        Fraction expected = new Fraction(79, 43);
        Fraction actual = Fraction.parseFract(s);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testParseFractToStringCorrespondence() {
        Fraction expected = new Fraction(53, 40);
        Fraction actual = Fraction.parseFract(expected.toString());
        assertEquals(expected, actual);
    }

    @Test
    public void testParseFractToHTMLStringCorrespondence() {
        Fraction expected = new Fraction(53, 40);
        Fraction actual = Fraction.parseFract(expected.toHTMLString());
        assertEquals(expected, actual);
    }

    @Test
    public void testParseFractToTeXStringCorrespondence() {
        Fraction expected = new Fraction(53, 40);
        Fraction actual = Fraction.parseFract(expected.toTeXString());
        assertEquals(expected, actual);
    }

    /**
     * Test of Fraction constructor. Even if the constructor parameters are not 
     * in lowest terms, the constructor should change them to lowest terms.
     */
    @Test
    public void testFractionsAreInLowestTerms() {
        System.out.println("Fractions should be in lowest terms");
        Fraction fraction = new Fraction(21, 24);
        assertEquals(operandA, fraction);
        assertEquals(7L, fraction.getNumerator());
        assertEquals(8L, fraction.getDenominator());
        assertEquals("7/8", fraction.toString().replace(" ", ""));
        fraction = new Fraction(8, 24);
        assertEquals(operandB, fraction);
        assertEquals(1L, fraction.getNumerator());
        assertEquals(3L, fraction.getDenominator());
        assertEquals("1/3", fraction.toString().replace(" ", ""));
    }
    
    /**
     * Test of Fraction constructor. Even if the denominator passed to the 
     * constructor is negative, the constructor should change it to a positive 
     * integer.
     */
    @Test
    public void testNegativeDenomsQuietlyChanged() {
        Fraction fraction = new Fraction(12, -29);
        assertEquals(-12L, fraction.getNumerator());
        assertEquals(29L, fraction.getDenominator());
    }
    
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
                    + " is the wrong exception to throw for trying to use denominator " 
                    + Long.MIN_VALUE;
            fail(msg);
        }
    }
        
}
