/*
 * Copyright (C) 2019 AL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package algebraics.quadratics;

import fileops.PNGFileFilter;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AL
 */
public class ImaginaryQuadraticIntegerLineTest {
    
    private static ImaginaryQuadraticInteger gaussianLineStart, gaussianLineEnd, eisensteinLineStart, eisensteinLineEnd;
    private static ImaginaryQuadraticIntegerLine gaussianLine, eisensteinLine;
    
    @BeforeClass
    public static void setUpClass() {
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
        gaussianLineStart = new ImaginaryQuadraticInteger(1, 1, ring);
        gaussianLineEnd = new ImaginaryQuadraticInteger(5, 5, ring);
        gaussianLine = new ImaginaryQuadraticIntegerLine(gaussianLineStart, gaussianLineEnd, gaussianLineStart);
        ring = new ImaginaryQuadraticRing(-3);
        eisensteinLineStart = new ImaginaryQuadraticInteger(7, 5, ring, 2);
        eisensteinLineEnd = new ImaginaryQuadraticInteger(-8, -3, ring);
        eisensteinLine = new ImaginaryQuadraticIntegerLine(eisensteinLineStart, eisensteinLineEnd);
    }
    
    /**
     * Test of length method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        int expResult = 5;
        int result = gaussianLine.length();
        assertEquals(expResult, result);
    }

    /**
     * Test of apply method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        QuadraticInteger expResult = gaussianLineStart;
        ImaginaryQuadraticInteger result;
        for (int index = 0; index < 5; index++) {
            result = gaussianLine.apply(index);
            assertEquals(expResult, result);
            expResult = expResult.plus(gaussianLineStart);
        }
    }
    
    /**
     * Test of apply method, of class ImaginaryQuadraticIntegerLine. Negative 
     * indexes and positive indexes beyond the last index should both cause 
     * IndexOutOfBoundsException.
     */
    @Test
    public void testApplyOutOfBounds() {
        int badIndex = -5;
        try {
            ImaginaryQuadraticInteger result = gaussianLine.apply(badIndex);
            String failMessage = "Trying to access index " + badIndex + " of " + gaussianLine.toString() + " should have caused an exception, not given result " + result.toString();
            fail(failMessage);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("Trying to access index " + badIndex + " of  " + gaussianLine.toASCIIString() + " correctly triggered IndexOutOfBoundsException");
            System.out.println("\"" + ioobe.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception to throw for trying to access index " + badIndex + " of " + gaussianLine.toString();
            fail(failMessage);
        }
        badIndex = eisensteinLine.length() + 1000;
        try {
            ImaginaryQuadraticInteger result = eisensteinLine.apply(badIndex);
            String failMessage = "Trying to access index " + badIndex + " of " + eisensteinLine.toString() + " should have caused an exception, not given result " + result.toString();
            fail(failMessage);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("Trying to access index " + badIndex + " of  " + eisensteinLine.toASCIIString() + " correctly triggered IndexOutOfBoundsException");
            System.out.println("\"" + ioobe.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception to throw for trying to access index " + badIndex + " of " + eisensteinLine.toString();
            fail(failMessage);
        }
    }
    
    /**
     * Test of by method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testBy() {
        System.out.println("by");
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger replacingStep = new ImaginaryQuadraticInteger(2, 2, ring);
        ImaginaryQuadraticIntegerLine doubleStepLine = gaussianLine.by(replacingStep);
        assertEquals(doubleStepLine.apply(0), gaussianLine.apply(0));
        assertEquals(doubleStepLine.apply(doubleStepLine.length() - 1), gaussianLine.apply(gaussianLine.length() - 1));
        assertNotEquals(doubleStepLine.length(), gaussianLine.length());
        ring = new ImaginaryQuadraticRing(-3);
        ImaginaryQuadraticInteger altEisensteinLineEnd = new ImaginaryQuadraticInteger(-17, -7, ring, 2);
        ImaginaryQuadraticIntegerLine altEisensteinLine = new ImaginaryQuadraticIntegerLine(eisensteinLineStart, altEisensteinLineEnd);
        replacingStep = new ImaginaryQuadraticInteger(-1, -1, ring);
        doubleStepLine = altEisensteinLine.by(replacingStep);
        assertEquals(doubleStepLine.apply(0), altEisensteinLine.apply(0));
//        assertEquals(doubleStepLine.apply(doubleStepLine.length() - 1), altEisensteinLine.apply(altEisensteinLine.length() - 1));
        assertNotEquals(doubleStepLine.length(), altEisensteinLine.length());
    }
    
    /**
     * Test of toString method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = gaussianLineStart.toString() + " to " + gaussianLineEnd.toString();
        String result = gaussianLine.toString();
        assertEquals(expResult, result);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger replacingStep = new ImaginaryQuadraticInteger(2, 2, ring);
        ImaginaryQuadraticIntegerLine doubleStepLine = gaussianLine.by(replacingStep);
        expResult = expResult + " by " + replacingStep.toString();
        result = doubleStepLine.toString();
        assertEquals(expResult, result);
        expResult = eisensteinLineStart.toString() + " to " + eisensteinLineEnd.toString();
        result = eisensteinLine.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toASCIIString method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = gaussianLineStart.toASCIIString() + " to " + gaussianLineEnd.toASCIIString();
        String result = gaussianLine.toASCIIString();
        assertEquals(expResult, result);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger replacingStep = new ImaginaryQuadraticInteger(2, 2, ring);
        ImaginaryQuadraticIntegerLine doubleStepLine = gaussianLine.by(replacingStep);
        expResult = expResult + " by " + replacingStep.toASCIIString();
        result = doubleStepLine.toString();
        assertEquals(expResult, result);
        expResult = eisensteinLineStart.toASCIIString() + " to " + eisensteinLineEnd.toASCIIString();
        result = eisensteinLine.toASCIIString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ImaginaryQuadraticIntegerLine sameLine = new ImaginaryQuadraticIntegerLine(gaussianLineStart, gaussianLineEnd);
        assertEquals(gaussianLine, sameLine);
        assertNotEquals(gaussianLine, eisensteinLine);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger replacingStep = new ImaginaryQuadraticInteger(2, 2, ring);
        ImaginaryQuadraticIntegerLine doubleStepLine = gaussianLine.by(replacingStep);
        assertNotEquals(gaussianLine, doubleStepLine);
        sameLine = new ImaginaryQuadraticIntegerLine(eisensteinLineStart, eisensteinLineEnd);
        assertEquals(eisensteinLine, sameLine);
        PNGFileFilter objDiffClass = new PNGFileFilter();
        assertNotEquals(gaussianLine, objDiffClass);
    }
    
    /**
     * Test of hashCode method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        HashSet<Integer> hashCodes = new HashSet<>();
        int hash = gaussianLine.hashCode();
        System.out.println(gaussianLine.toASCIIString() + " hashed as " + hash);
        hashCodes.add(hash);
        hash = eisensteinLine.hashCode();
        System.out.println(eisensteinLine.toASCIIString() + " hashed as " + hash);
        hashCodes.add(hash);
        HashSet<ImaginaryQuadraticIntegerLine> lines = new HashSet<>();
        lines.add(gaussianLine);
        lines.add(eisensteinLine);
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-7);
        ImaginaryQuadraticInteger start = new ImaginaryQuadraticInteger(-8, -15, ring);
        QuadraticInteger end = start;
        ImaginaryQuadraticInteger incr = new ImaginaryQuadraticInteger(1, 1, ring, 2);
        ImaginaryQuadraticIntegerLine extraLine;
        for (int i = 0; i < 100; i++) {
            extraLine = new ImaginaryQuadraticIntegerLine(start, (ImaginaryQuadraticInteger) end);
            lines.add(extraLine);
            hashCodes.add(extraLine.hashCode());
            end = end.plus(incr);
        }
        String assertionMessage = "Set of lines should have same size as set of hash codes";
        assertEquals(assertionMessage, lines.size(), hashCodes.size());
    }
    
}
