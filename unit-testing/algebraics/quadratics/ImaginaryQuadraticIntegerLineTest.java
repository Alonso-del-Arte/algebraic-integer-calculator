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
package algebraics.quadratics;

import algebraics.NotDivisibleException;
import fileops.PNGFileFilter;

import java.util.HashSet;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ImaginaryQuadraticIntegerLine class.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticIntegerLineTest {
    
    private static final ImaginaryQuadraticRing RING_OQI7 
            = new ImaginaryQuadraticRing(-7);
    
    private static final Random RANDOM = new Random();
    
    private static ImaginaryQuadraticInteger pickRandomNumber() {
        int x = RANDOM.nextInt(256) - 128;
        int y = RANDOM.nextInt(256) - 128;
        int denom = (x % 2 == y % 2) ? 2 : 1;
        return new ImaginaryQuadraticInteger(x, y, RING_OQI7, denom);
    }
    
    private static ImaginaryQuadraticIntegerLine makeRandomLine() {
        ImaginaryQuadraticInteger start = pickRandomNumber();
        ImaginaryQuadraticInteger end = pickRandomNumber();
        return new ImaginaryQuadraticIntegerLine(start, end);
    }
    
    /**
     * Test of the toString function, of the ImaginaryQuadraticIntegerLine 
     * class.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(9, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(2, 0,  
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        String expected = start.toString() + " to " + end.toString() + " by " 
                + step.toString();
        String actual = line.toString();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the toString function, of the 
     * ImaginaryQuadraticIntegerLine class. If the step can be inferred, it 
     * should be omitted from the description, even if it was explicitly 
     * included in the constructor call.
     */
    @Test
    public void testToStringInferredStep() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(1, 0,  
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        String expected = start.toString() + " to " + end.toString();
        String actual = line.toString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toASCIIString function, of the ImaginaryQuadraticIntegerLine 
     * class.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(9, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(2, 0,  
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        String expected = start.toASCIIString() + " to " + end.toASCIIString() 
                + " by " + step.toASCIIString();
        String actual = line.toASCIIString();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the toASCIIString function, of the 
     * ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testToASCIIStringInferredString() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(1, 0,  
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        String expected = start.toASCIIString() + " to " + end.toASCIIString();
        String actual = line.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        assertEquals(line, line);
    }
    
    @Test
    public void testNotEqualsNull() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        assertNotEquals(line, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        PNGFileFilter filter = new PNGFileFilter();
        assertNotEquals(line, filter);
    }
    
    @Test
    public void testNotEqualsDiffStart() {
        ImaginaryQuadraticInteger startA = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger startB = new ImaginaryQuadraticInteger(-5, 0, 
                RING_OQI7);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine lineA 
                = new ImaginaryQuadraticIntegerLine(startA, end, step);
        ImaginaryQuadraticIntegerLine lineB 
                = new ImaginaryQuadraticIntegerLine(startB, end, step);
        assertNotEquals(lineA, lineB);
    }
    
    @Test
    public void testNotEqualsDiffEnd() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger endA = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger endB = new ImaginaryQuadraticInteger(16, 3, 
                RING_OQI7);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine lineA 
                = new ImaginaryQuadraticIntegerLine(start, endA, step);
        ImaginaryQuadraticIntegerLine lineB 
                = new ImaginaryQuadraticIntegerLine(start, endB, step);
        assertNotEquals(lineA, lineB);
    }
    
    @Test
    public void testNotEqualsDiffStep() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger stepA = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger stepB = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7);
        ImaginaryQuadraticIntegerLine lineA 
                = new ImaginaryQuadraticIntegerLine(start, end, stepA);
        ImaginaryQuadraticIntegerLine lineB 
                = new ImaginaryQuadraticIntegerLine(start, end, stepB);
        assertNotEquals(lineA, lineB);
    }
    
    /**
     * Test of the equals function, of the ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-3, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(25, 5, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(7, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine someLine 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        ImaginaryQuadraticIntegerLine sameLine 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        assertEquals(someLine, sameLine);
    }
    
    /**
     * Test of hashCode method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        HashSet<ImaginaryQuadraticIntegerLine> lines = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        ImaginaryQuadraticInteger start, end;
        ImaginaryQuadraticIntegerLine line;
        int denom;
        for (int x = -10; x < 10; x++) {
            for (int y = -10; y < 10; y++) {
                if (x % 2 == y % 2) {
                    denom = 2;
                } else {
                    denom = 1;
                }
                start = new ImaginaryQuadraticInteger(x, y, RING_OQI7, denom);
                end = new ImaginaryQuadraticInteger(y, x, RING_OQI7, denom);
                line = new ImaginaryQuadraticIntegerLine(start, end);
                lines.add(line);
                hashes.add(line.hashCode());
            }
        }
        int expected = lines.size();
        int actual = hashes.size();
        System.out.println("Successfully created " + expected 
                + " distinct lines with " + actual 
                + " distinct hash codes");
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getStart function, of the ImaginaryQuadraticIntegerLine 
     * class.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(-315, 
                75, RING_OQI7, 2);
        ImaginaryQuadraticInteger end = pickRandomNumber();
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(expected, end);
        ImaginaryQuadraticInteger actual = line.getStart();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the getStart function, of the 
     * ImaginaryQuadraticIntegerLine class. The calls getStart() and apply(0) 
     * should give the same result.
     */
    @Test
    public void testGetStartApplyZeroCorrespondence() {
        ImaginaryQuadraticIntegerLine line = makeRandomLine();
        ImaginaryQuadraticInteger expected = line.getStart();
        ImaginaryQuadraticInteger actual = line.apply(0);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getEnd function, of the ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        ImaginaryQuadraticInteger start = pickRandomNumber();
        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(315, 
                -75, RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, expected);
        ImaginaryQuadraticInteger actual = line.getEnd();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the getEnd function, of the ImaginaryQuadraticIntegerLine 
     * class. The calls getEnd() and apply(length - 1)) should give the same 
     * result.
     */
    @Test
    public void testGetEndApplyLengthMinusOneCorrespondence() {
        ImaginaryQuadraticIntegerLine line = makeRandomLine();
        ImaginaryQuadraticInteger expected = line.getEnd();
        ImaginaryQuadraticInteger actual = line.apply(line.length() - 1);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getStep function, of the ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testGetStep() {
        System.out.println("getStep");
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-10, 1, 
                RING_OQI7);
        ImaginaryQuadraticInteger expected = new ImaginaryQuadraticInteger(1, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = (ImaginaryQuadraticInteger) 
                start.plus(expected.times(RANDOM.nextInt(32) + 8));
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, expected);
        ImaginaryQuadraticInteger actual = line.getStep();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testApplyGetCorrespondence() {
        ImaginaryQuadraticIntegerLine line = makeRandomLine();
        ImaginaryQuadraticInteger expected, actual;
        for (int i = 0; i < line.length() - 1; i++) {
            expected = line.apply(i);
            actual = line.get(i);
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Test of the length function, of the ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testLength() {
        int expected = RANDOM.nextInt(128) + 2;
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(3, 1, 
                RING_OQI7);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(3, 
                expected, RING_OQI7);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(0, 1, 
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        int actual = line.length();
        assertEquals(expected, actual);
    }

    /**
     * Test of the apply function, of the ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testApply() {
        int endRe = RANDOM.nextInt(64) + 16;
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(0, 7, 
                RING_OQI7);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(-endRe, 7, 
                RING_OQI7);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(-1, 0, 
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        QuadraticInteger expected, actual;
        for (int i = 0; i <= endRe; i++) {
            expected = new ImaginaryQuadraticInteger(-i, 7, RING_OQI7);
            actual = line.apply(i);
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testApplyNegativeIndex() {
        ImaginaryQuadraticIntegerLine line = makeRandomLine();
        int badIndex = -RANDOM.nextInt(128) - 1;
        System.out.print("Trying to use negative index " + badIndex 
                + " on line " + line.toASCIIString() + "... ");
        try {
            ImaginaryQuadraticInteger badResult = line.apply(badIndex);
            System.out.println("should not have given result " 
                    + badResult.toASCIIString());
            String msg = "Index " + badIndex + " gave result " 
                    + badResult.toString() + " instead of causing an exception";
            fail(msg);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("correctly caused IndexOutOfBoundsException");
            String excMsg = ioobe.getMessage();
            assert excMsg != null;
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for bad index " + badIndex;
            fail(msg);
        }
    }
    
    /**
     * Another test of the apply function, of the ImaginaryQuadraticIntegerLine 
     * class. Positive indexes beyond the last index should cause 
     * IndexOutOfBoundsException.
     */
    @Test
    public void testApplyOutOfBounds() {
        int endRe = RANDOM.nextInt(64) + 16;
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(0, 7, 
                RING_OQI7);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(endRe, 7, 
                RING_OQI7);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(1, 0, 
                RING_OQI7);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        int badIndex = RANDOM.nextInt(4096) + 128;
        System.out.print("Trying to use out-of-bounds index " + badIndex 
                + " on line " + line.toASCIIString() + "... ");
        try {
            ImaginaryQuadraticInteger badResult = line.apply(badIndex);
            System.out.println("should not have given result " 
                    + badResult.toASCIIString());
            String msg = "Index " + badIndex + " gave result " 
                    + badResult.toString() + " instead of causing an exception";
            fail(msg);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("correctly caused IndexOutOfBoundsException");
            String excMsg = ioobe.getMessage();
            assert excMsg != null;
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for bad index " + badIndex;
            fail(msg);
        }
    }
    
    /**
     * Test of the by function, of the ImaginaryQuadraticIntegerLine class.
     */
    @Test
    public void testBy() {
        System.out.println("by");
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-9, -7, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(11, 13, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(1, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        ImaginaryQuadraticInteger replacementStep 
                = new ImaginaryQuadraticInteger(2, 2, RING_OQI7);
        ImaginaryQuadraticIntegerLine expected 
                = new ImaginaryQuadraticIntegerLine(start, end, 
                        replacementStep);
        ImaginaryQuadraticIntegerLine actual = line.by(replacementStep);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the by function, of the ImaginaryQuadraticIntegerLine 
     * class.
     */
    @Test
    public void testByRejectsNotDivisibleStep() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-9, -7, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(11, 13, 
                RING_OQI7, 2);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(1, 1, 
                RING_OQI7, 2);
        ImaginaryQuadraticIntegerLine line 
                = new ImaginaryQuadraticIntegerLine(start, end, step);
        ImaginaryQuadraticInteger badReplacementStep 
                = new ImaginaryQuadraticInteger(3, 2, RING_OQI7);
        System.out.print("Trying to use bad replacement step " 
                + badReplacementStep.toASCIIString() + " for line " 
                + line.toASCIIString() + "... ");
        try {
            ImaginaryQuadraticIntegerLine badLine = line.by(badReplacementStep);
            System.out.println("incorrectly gave result " 
                    + badLine.toASCIIString());
            String msg = "Bad step " + badReplacementStep.toString() 
                    + " should have caused an exception, not given result " 
                    + badLine.toASCIIString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("correctly caused IllegalArgumentException");
            Throwable cause = iae.getCause();
            String msg = "RuntimeException should wrap NotDivisibleException";
            assert cause instanceof NotDivisibleException : msg;
            System.out.println("\"" + cause.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad step " 
                    + badReplacementStep.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsNotDivisibleStep() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(14, 1, 
                RING_OQI7);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(-21, 13, 
                RING_OQI7);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(1, 1, 
                RING_OQI7, 2);
        System.out.print("Trying to use step " + step.toASCIIString() 
                + " to go from " + start.toASCIIString() + " to " 
                + end.toASCIIString() + "... ");
        try {
            ImaginaryQuadraticIntegerLine badLine 
                    = new ImaginaryQuadraticIntegerLine(start, end, step);
            System.out.println("incorrectly created " 
                    + badLine.toASCIIString());
            String msg = "Step " + step.toString() + " for " + start.toString() 
                    + " to " + end.toString() 
                    + " should have caused an exception";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("correctly caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            assert excMsg != null : "Exception message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for step " + step.toString() 
                    + " for " + start.toString() + " to " + end.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsWrongDirectionStep() {
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-8, 0, 
                RING_OQI7);
        ImaginaryQuadraticInteger end = new ImaginaryQuadraticInteger(-8, 13, 
                RING_OQI7);
        ImaginaryQuadraticInteger step = new ImaginaryQuadraticInteger(0, -1, 
                RING_OQI7);
        System.out.print("Trying to use step " + step.toASCIIString() 
                + " to go from " + start.toASCIIString() + " to " 
                + end.toASCIIString() + "... ");
        try {
            ImaginaryQuadraticIntegerLine badLine 
                    = new ImaginaryQuadraticIntegerLine(start, end, step);
            System.out.println("incorrectly created " 
                    + badLine.toASCIIString());
            String msg = "Step " + step.toString() + " for " + start.toString() 
                    + " to " + end.toString() 
                    + " should have caused an exception";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("correctly caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            assert excMsg != null : "Exception message should not be null";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for step " + step.toString() 
                    + " for " + start.toString() + " to " + end.toString();
            fail(msg);
        }
    }
    
}
