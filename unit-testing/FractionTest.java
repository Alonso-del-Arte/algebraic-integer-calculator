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
package fractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Fraction class.
 * @author Alonso del Arte
 */
public class FractionTest {
    
    /**
     * The test delta, 10^(-8).
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
    
    @Before
    public void setUp() {
        operandA = new Fraction(7, 8);
        operandB = new Fraction(1, 3);
    }
    
    /**
     * Test of getNumerator method, of class Fraction.
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
     * Test of getDenominator method, of class Fraction.
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
        result = operandB.times(operandA);
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
            String failMessage = operandA.toString() + " divided by 0 should have caused an Exception, not given result " + result.toString();
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException is considered preferable for division by zero.");
            System.out.println(iae.getMessage());
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is deemed acceptable for division by zero.");
            System.out.println(ae.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is not an appropriate exception for division by zero.";
            fail(failMessage);
        }
        try {
            result = operandB.dividedBy(zero);
            String failMessage = operandA.toString() + " divided by 0 should have caused an Exception, not given result " + result.toString();
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException is considered preferable for division by zero.");
            System.out.println(iae.getMessage());
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is deemed acceptable for division by zero.");
            System.out.println(ae.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is not an appropriate exception for division by zero.";
            fail(failMessage);
        }
    }

    /**
     * Test of negate method, of class Fraction.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Fraction expResult = new Fraction(-7, 8);
        Fraction result = operandA.negate();
        assertEquals(expResult, result);
        expResult = new Fraction(-1, 3);
        result = operandB.negate();
        assertEquals(expResult, result);
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
    }

    /**
     * Test of getNumericApproximation method, of class Fraction.
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

    /**
     * Test of reciprocal method, of class Fraction.
     */
    @Test
    public void testReciprocal() {
        System.out.println("reciprocal");
        Fraction expResult = new Fraction(8, 7);
        Fraction result = operandA.reciprocal();
        assertEquals(expResult, result);
        expResult = new Fraction(3, 1);
        result = operandB.reciprocal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEgyptianFractions method, of class Fraction.
     */
    @Test
    public void testGetEgyptianFractions() {
        System.out.println("getEgyptianFractions: 7/8");
        Fraction oneHalf = new Fraction(1, 2);
        Fraction oneThird = new Fraction(1, 3);
        Fraction one24th = new Fraction(1, 24);
        Fraction[] expResult = {oneHalf, oneThird, one24th};
        Fraction[] result = operandA.getEgyptianFractions();
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of getEgyptianFractions method, of class Fraction, specifically for 
     * the case of 5/4.
     */
    @Test
    public void testGetEgyptianFractionsFiveQuarters() {
        System.out.println("getEgyptianFractions: 5/4");
        Fraction one = new Fraction(1);
        Fraction oneQuarter = new Fraction(1, 4);
        Fraction fiveQuarters = new Fraction(5, 4);
        Fraction[] expResult = {one, oneQuarter};
        Fraction[] result = fiveQuarters.getEgyptianFractions();
        assertArrayEquals(expResult, result);
    }
        
    /**
     * Test of getEgyptianFractions method, of class Fraction, specifically for 
     * the cases of sums of reciprocals of odd-indexed factorials.
     */
    @Ignore
    @Test
    public void testGetEgyptianFractionsAccumOddFactRecips() {
        long currFact = 6L;
        long currN = 4L;
        Fraction currUnitFract = new Fraction(1, currFact);
        Fraction currFract = new Fraction(0);
        List<Fraction> expResult = new ArrayList<>();
        List<Fraction> result;
        while (currFact < Integer.MAX_VALUE) {
            expResult.add(currUnitFract);
            currFract = currFract.plus(currUnitFract);
            result = new ArrayList<>(Arrays.asList(currFract.getEgyptianFractions()));
            System.out.print(currFract.toString() + " = " + result.get(0).toString());
            for (int i = 1; i < result.size(); i++) {
                System.out.print(" + " + result.get(i).toString());
            }
            System.out.println(".");
            assertEquals(expResult, result);
            currFact *= currN;
            currN++;
            currFact *= currN;
            currUnitFract = new Fraction(1, currFact);
            currN++;
        }
    }
    
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
    
    @Test
    public void testNegativeDenomsQuietlyChanged() {
        System.out.println("Negative denominators should be quietly changed to positive");
        Fraction fraction = new Fraction(12, -29);
        assertEquals(-12L, fraction.getNumerator());
        assertEquals(29L, fraction.getDenominator());
    }
    
}
