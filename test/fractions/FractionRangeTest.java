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
package fractions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the FractionRange class.
 * @author Alonso del Arte
 */
public class FractionRangeTest {
    
    private final Fraction testStart = new Fraction(-1, 2);

    private final Fraction testEnd = new Fraction(15, 8);
    
    private final Fraction testStep = new Fraction(1, 8);
    
    @Test
    public void testToString() {
        System.out.println("toString");
        Fraction step = new Fraction(1, 8);
        FractionRange range = new FractionRange(this.testStart, this.testEnd, 
                this.testStep);
        String expected = this.testStart.toString() + "to" 
                + this.testEnd.toString() + "by" + step.toString();
        String actual = range.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringOmitsEasilyInferredStep() {
        Fraction start = new Fraction(3, 13);
        Fraction end = new Fraction(29, 13);
        Fraction step = new Fraction(1, 13);
        FractionRange range = new FractionRange(start, end, step);
        String expected = start.toString() + "to" + end.toString();
        String actual = range.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        FractionRange range = new FractionRange(this.testStart, this.testEnd, 
                this.testStep);
        assertEquals(range, range);
    }
    
    @Test
    public void testNotEqualsNull() {
        FractionRange range = new FractionRange(this.testStart, this.testEnd, 
                this.testStep);
        assertNotEquals(range, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        FractionRange range = new FractionRange(this.testStart, this.testEnd, 
                this.testStep);
        assertNotEquals(range, this.testStep);
    }
    
    @Test
    public void testNotEqualsDiffStart() {
        FractionRange someRange = new FractionRange(this.testStart, 
                this.testEnd, this.testStep);
        FractionRange diffRange 
                = new FractionRange(this.testStart.plus(this.testStep), 
                        this.testEnd, this.testStep);
        assertNotEquals(someRange, diffRange);
    }
    
    @Test
    public void testNotEqualsDiffEnd() {
        FractionRange someRange = new FractionRange(this.testStart, 
                this.testEnd, this.testStep);
        FractionRange diffRange 
                = new FractionRange(this.testStart, 
                        this.testEnd.plus(this.testStep), this.testStep);
        assertNotEquals(someRange, diffRange);
    }
    
    @Test
    public void testNotEqualsDiffStep() {
        FractionRange someRange = new FractionRange(this.testStart, 
                this.testEnd, this.testStep);
        Fraction smallerStep = this.testStep.dividedBy(7);
        FractionRange diffRange 
                = new FractionRange(this.testStart, this.testEnd, smallerStep);
        assertNotEquals(someRange, diffRange);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        FractionRange someRange = new FractionRange(this.testStart, 
                this.testEnd, this.testStep);
        FractionRange sameRange = new FractionRange(this.testStart, 
                this.testEnd, this.testStep);
        assertEquals(someRange, sameRange);
    }
    
    @Test
    public void testInferStep() {
        System.out.println("inferStep");
        Fraction start = new Fraction(47, 20);
        Fraction end = new Fraction(73, 10);
        Fraction expected = new Fraction(1, 20);
        Fraction actual = FractionRange.inferStep(start, end);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testInferNegativeStep() {
        Fraction start = new Fraction(73, 10);
        Fraction end = new Fraction(47, 20);
        Fraction expected = new Fraction(-1, 20);
        Fraction actual = FractionRange.inferStep(start, end);
        assertEquals(expected, actual);
    }

    /**
     * Test of length method, of class FractionRange.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        FractionRange range = new FractionRange(testStart, testEnd);
        int expResult = 20;
        int result = range.length();
        assertEquals(expResult, result);
    }

    /**
     * Test of apply method, of class FractionRange.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        FractionRange range = new FractionRange(this.testStart, this.testEnd);
        Fraction expected = this.testStart;
        Fraction actual;
        for (int i = 0; i < 20; i++) {
            actual = range.apply(i);
            assertEquals(expected, actual);
            expected = expected.plus(this.testStep);
        }
    }
    
    /**
     * Another test of apply method, of class FractionRange. Negative indices 
     * should cause <code>IndexOutOfBoundsException</code>.
     */
    @Test
    public void testApplyRejectsNegativeIndex() {
        FractionRange range = new FractionRange(this.testStart, this.testEnd);
        int badIndex = -1;
        try {
            Fraction result = range.apply(badIndex);
            String msg = "Trying to apply bad index " + badIndex 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("Trying to apply bad index " + badIndex 
                    + " correctly caused IndexOutOfBoundsException");
            System.out.println("\"" + ioobe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to apply " 
                    + badIndex;
            fail(msg);
        }
    }
    
    /**
     * Another test of apply method, of class FractionRange. Excessive indices  
     * should cause <code>IndexOutOfBoundsException</code>.
     */
    @Test
    public void testApplyRejectsExcessiveIndex() {
        FractionRange range = new FractionRange(this.testStart, this.testEnd);
        int badIndex = 20;
        try {
            Fraction result = range.apply(badIndex);
            String msg = "Trying to apply bad index " + badIndex 
                    + " should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("Trying to apply bad index " + badIndex 
                    + " correctly caused IndexOutOfBoundsException");
            System.out.println("\"" + ioobe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to apply " 
                    + badIndex;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsInvalidStep() {
        Fraction begin = new Fraction(5, 8);
        Fraction finish = new Fraction(7, 8);
        Fraction invalidStep = new Fraction(3, 2);
        try {
            FractionRange range = new FractionRange(begin, finish, invalidStep);
            String msg = "Should not have been able to construct range " 
                    + range.toString() + " with invalid step " 
                    + invalidStep.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to construct range with invalid step " 
                    + invalidStep.toString() 
                    + " correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for range with invalid step " 
                    + invalidStep.toString() + ".";
            fail(msg);
        }
    }
    
}
