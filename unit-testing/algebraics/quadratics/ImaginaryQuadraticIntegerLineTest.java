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

import fileops.PNGFileFilter;

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ImaginaryQuadraticIntegerLine class.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticIntegerLineTest {
    
    private static final ImaginaryQuadraticRing RING_OQI7 
            = new ImaginaryQuadraticRing(-7);
    
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
     * Test of toASCIIString method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testToASCIIString() {
        fail("Haven't written test yet");
        System.out.println("toASCIIString");
    }
    
    /**
     * Test of length method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testLength() {
        fail("Haven't written test yet");
//        System.out.println("length");
//        int expResult = 5;
//        int result = gaussianLine.length();
//        assertEquals(expResult, result);
    }

    /**
     * Test of apply method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testApply() {
        fail("Haven't written test yet");
        System.out.println("apply");
//        QuadraticInteger expResult = gaussianLineStart;
//        ImaginaryQuadraticInteger result;
//        for (int index = 0; index < 5; index++) {
//            result = gaussianLine.apply(index);
//            assertEquals(expResult, result);
//            expResult = expResult.plus(gaussianLineStart);
//        }
    }
    
    /**
     * Test of apply method, of class ImaginaryQuadraticIntegerLine. Negative 
     * indexes and positive indexes beyond the last index should both cause 
     * IndexOutOfBoundsException.
     */
    @Test
    public void testApplyOutOfBounds() {
        fail("Haven't written test yet");
        int badIndex = -5;
//        try {
//            ImaginaryQuadraticInteger result = gaussianLine.apply(badIndex);
//            String msg = "Trying to access index " + badIndex + " of " 
//                    + gaussianLine.toString() + " should have caused an exception, not given result " + result.toString();
//            fail(msg);
//        } catch (IndexOutOfBoundsException ioobe) {
//            System.out.println("Trying to access index " + badIndex + " of  " 
//                    + gaussianLine.toASCIIString() 
//                    + " correctly triggered IndexOutOfBoundsException");
//            System.out.println("\"" + ioobe.getMessage() + "\"");
//        } catch (Exception e) {
//            String msg = e.getClass().getName() 
//                    + " is the wrong exception for trying to access index " 
//                    + badIndex + " of " + gaussianLine.toString();
//            fail(msg);
//        }
//        badIndex = eisensteinLine.length() + 1000;
//        try {
//            ImaginaryQuadraticInteger result = eisensteinLine.apply(badIndex);
//            String msg = "Trying to access index " + badIndex + " of " 
//                    + eisensteinLine.toString() 
//                    + " should have caused an exception, not given result " 
//                    + result.toString();
//            fail(msg);
//        } catch (IndexOutOfBoundsException ioobe) {
//            System.out.println("Trying to access index " + badIndex + " of  " 
//                    + eisensteinLine.toASCIIString() 
//                    + " correctly triggered IndexOutOfBoundsException");
//            System.out.println("\"" + ioobe.getMessage() + "\"");
//        } catch (Exception e) {
//            String msg = e.getClass().getName() 
//                    + " is the wrong exception for trying to access index " 
//                    + badIndex + " of " + eisensteinLine.toString();
//            fail(msg);
//        }
    }
    
    /**
     * Test of by method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testBy() {
        fail("Haven't written test yet");
        System.out.println("by");
//        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
//        ImaginaryQuadraticInteger replacingStep 
//                = new ImaginaryQuadraticInteger(2, 2, ring);
//        ImaginaryQuadraticIntegerLine doubleStepLine 
//                = gaussianLine.by(replacingStep);
//        assertEquals(doubleStepLine.apply(0), gaussianLine.apply(0));
//        assertEquals(doubleStepLine.apply(doubleStepLine.length() - 1), 
//                gaussianLine.apply(gaussianLine.length() - 1));
//        assertNotEquals(doubleStepLine.length(), gaussianLine.length());
//        ring = new ImaginaryQuadraticRing(-3);
//        ImaginaryQuadraticInteger altEisensteinLineEnd 
//                = new ImaginaryQuadraticInteger(-17, -7, ring, 2);
//        ImaginaryQuadraticIntegerLine altEisensteinLine 
//                = new ImaginaryQuadraticIntegerLine(eisensteinLineStart, 
//                        altEisensteinLineEnd);
//        replacingStep = new ImaginaryQuadraticInteger(-1, -1, ring);
//        doubleStepLine = altEisensteinLine.by(replacingStep);
//        assertEquals(doubleStepLine.apply(0), altEisensteinLine.apply(0));
//        assertEquals(doubleStepLine.apply(doubleStepLine.length() - 1), 
//                altEisensteinLine.apply(altEisensteinLine.length() - 1));
//        assertNotEquals(doubleStepLine.length(), altEisensteinLine.length());
    }
    
    /**
     * Test of equals method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testEquals() {
        fail("Haven't written test yet");
        System.out.println("equals");
//        ImaginaryQuadraticIntegerLine sameLine 
//                = new ImaginaryQuadraticIntegerLine(gaussianLineStart, 
//                        gaussianLineEnd);
//        assertEquals(gaussianLine, sameLine);
//        assertNotEquals(gaussianLine, eisensteinLine);
//        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
//        ImaginaryQuadraticInteger replacingStep 
//                = new ImaginaryQuadraticInteger(2, 2, ring);
//        ImaginaryQuadraticIntegerLine doubleStepLine 
//                = gaussianLine.by(replacingStep);
//        assertNotEquals(gaussianLine, doubleStepLine);
//        sameLine = new ImaginaryQuadraticIntegerLine(eisensteinLineStart, 
//                eisensteinLineEnd);
//        assertEquals(eisensteinLine, sameLine);
//        PNGFileFilter objDiffClass = new PNGFileFilter();
//        assertNotEquals(gaussianLine, objDiffClass);
    }
    
    /**
     * Test of hashCode method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testHashCode() {
        fail("Haven't written test yet");
        System.out.println("hashCode");
//        HashSet<Integer> hashCodes = new HashSet<>();
//        int hash = gaussianLine.hashCode();
//        System.out.println(gaussianLine.toASCIIString() + " hashed as " + hash);
//        hashCodes.add(hash);
//        hash = eisensteinLine.hashCode();
//        System.out.println(eisensteinLine.toASCIIString() + " hashed as " 
//                + hash);
//        hashCodes.add(hash);
//        HashSet<ImaginaryQuadraticIntegerLine> lines = new HashSet<>();
//        lines.add(gaussianLine);
//        lines.add(eisensteinLine);
//        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-7);
//        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-8, -15, 
//                ring);
//        QuadraticInteger end = start;
//        ImaginaryQuadraticInteger incr = new ImaginaryQuadraticInteger(1, 1, 
//                ring, 2);
//        ImaginaryQuadraticIntegerLine extraLine;
//        for (int i = 0; i < 100; i++) {
//            extraLine = new ImaginaryQuadraticIntegerLine(start, 
//                    (ImaginaryQuadraticInteger) end);
//            lines.add(extraLine);
//            hashCodes.add(extraLine.hashCode());
//            end = end.plus(incr);
//        }
//        String msg = "Set of lines should have same size as set of hash codes";
//        assertEquals(msg, lines.size(), hashCodes.size());
    }
    
}
