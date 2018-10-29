/*
 * Copyright (C) 2018 Alonso del Arte
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
package algebraics;

import calculators.NumberTheoreticFunctionsCalculator;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the RealQuadraticRing class.
 * @author Alonso del Arte, from template generated by NetBeans IDE.
 */
public class RealQuadraticRingTest {
    
    private static RealQuadraticRing ringZ2;
    private static RealQuadraticRing ringZPhi;
    private static RealQuadraticRing ringOQ13;
    private static RealQuadraticRing ringRandom;
    
    private static int randomDiscr;
    private static boolean ringRandomd1mod4;
    
    /**
     * Sets up three RealQuadraticRing objects, corresponding to 
     * <b>Z</b>[&radic;2], <b>Z</b>[&phi;], 
     * <i>O</i><sub><b>Q</b>(&radic;13)</sub> and a randomly chosen ring. The 
     * randomly chosen ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> is 
     * determined by <i>d</i> being at least 6.
     */
    @BeforeClass
    public static void setUpClass() {
        randomDiscr = NumberTheoreticFunctionsCalculator.randomSquarefreeNumber(100);
        if (randomDiscr < 6) {
            randomDiscr = 6;
        }
        ringRandomd1mod4 = (randomDiscr % 4 == 1);
        ringZ2 = new RealQuadraticRing(2);
        ringZPhi = new RealQuadraticRing(5);
        ringOQ13 = new RealQuadraticRing(13);
        ringRandom = new RealQuadraticRing(randomDiscr);
        System.out.println(ringRandom.toASCIIString() + " has been randomly chosen for testing purposes.");
    }
    
    /**
     * Test of getRadicand method, of class RealQuadraticRing, inherited from 
     * QuadraticRing.
     */
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        assertEquals(2, ringZ2.getRadicand());
        assertEquals(5, ringZPhi.getRadicand());
        assertEquals(13, ringOQ13.getRadicand());
        assertEquals(randomDiscr, ringRandom.getRadicand());
    }
    
    /**
     * Test of getAbsNegRad method, of class RealQuadraticRing.
     */
    @Test
    public void testGetAbsNegRad() {
        System.out.println("getAbsNegRad");
        assertEquals(2, ringZ2.getAbsNegRad());
        assertEquals(5, ringZPhi.getAbsNegRad());
        assertEquals(13, ringOQ13.getAbsNegRad());
        assertEquals(randomDiscr, ringRandom.getAbsNegRad());
    }
    
    /**
     * Test of getRadicand and getAbsNegRad methods, of class RealQuadraticRing.
     */
    @Test
    public void testRadAbsNegRadCorr() {
        System.out.println("getRadicand and getAbsNegRad should be the same for an instance of RealQuadraticRing.");
        assertEquals(ringZ2.getRadicand(), ringZ2.getAbsNegRad());
        assertEquals(ringZPhi.getRadicand(), ringZPhi.getAbsNegRad());
        assertEquals(ringOQ13.getRadicand(), ringOQ13.getAbsNegRad());
        assertEquals(ringRandom.getRadicand(), ringRandom.getAbsNegRad());
    }

    /**
     * Test of getRadSqrt method, of class RealQuadraticRing.
     */
    @Test
    public void testGetRadSqrt() {
        System.out.println("getRadSqrt");
        assertEquals(Math.sqrt(2), ringZ2.getRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(5), ringZPhi.getRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(13), ringOQ13.getRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(randomDiscr), ringRandom.getRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of getAbsNegRadSqrt method, of class RealQuadraticRing.
     */
    @Test
    public void testGetAbsNegRadSqrt() {
        System.out.println("getAbsNegRadSqrt");
        assertEquals(Math.sqrt(2), ringZ2.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(5), ringZPhi.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(13), ringOQ13.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(randomDiscr), ringRandom.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of getRadSqrt and getAbsNegRadSqrt methods, of class 
     * RealQuadraticRing.
     */
    @Test
    public void testGetRadSqrtGetAbsNegRadSqrtCorr() {
        System.out.println("getRadSqrt and getAbsNegRadSqrt should be the same for an instance of RealQuadraticRing.");
        assertEquals(ringZ2.getRadSqrt(), ringZ2.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(ringZPhi.getRadSqrt(), ringZPhi.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(ringOQ13.getRadSqrt(), ringOQ13.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
        assertEquals(ringRandom.getRadSqrt(), ringRandom.getAbsNegRadSqrt(), ImaginaryQuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of hasHalfIntegers method, of class ImaginaryQuadraticRing, 
     * inherited from QuadraticInteger.
     */
    @Test
    public void testHasHalfIntegers() {
        System.out.println("hasHalfIntegers");
        String assertionMessage = ringZ2.toASCIIString() + " should not be said to have half-integers.";
        assertFalse(assertionMessage, ringZ2.hasHalfIntegers());
        assertionMessage = ringZPhi.toASCIIString() + " should be said to have half-integers.";
        assertTrue(assertionMessage, ringZPhi.hasHalfIntegers());
        assertionMessage = ringOQ13.toASCIIString() + " should be said to have half-integers.";
        assertTrue(assertionMessage, ringOQ13.hasHalfIntegers());
        assertionMessage = ringRandom.toASCIIString() + " should ";
        if (ringRandomd1mod4) {
            assertionMessage = assertionMessage + "be said to have half-integers.";
        } else {
            assertionMessage = assertionMessage + "not be said to have half-integers.";
        }
        assertEquals(assertionMessage, ringRandomd1mod4, ringRandom.hasHalfIntegers());
    }
    
    /**
     * Test of preferBlackboardBold method, of class RealQuadraticRing, 
     * inherited from QuadraticRing. Without arguments, preferBlackboardBold is 
     * the getter method. With arguments, preferBlackboardBold is the setter 
     * method. This is perhaps an unnecessary test. The results of {@link 
     * #testToString()} and {@link #testToHTMLString()} are far more important.
     */
    @Test
    public void testPreferBlackboardBold() {
        System.out.println("preferBlackboardBold");
        RealQuadraticRing.preferBlackboardBold(true);
        assertTrue(RealQuadraticRing.preferBlackboardBold());
        RealQuadraticRing.preferBlackboardBold(false);
        assertFalse(RealQuadraticRing.preferBlackboardBold());
    }
    
    /**
     * Test of hashCode method, of class ImaginaryQuadraticRing, inherited from 
     * QuadraticRing. The purpose here isn't to test that any specific ring maps 
     * to any specific hash code, but rather that two rings that are equal get 
     * the same hash code, and two rings that are not equal get different hash 
     * codes.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int expResult = ringZ2.hashCode();
        System.out.println("BeforeClass-initialized " + ringZ2.toASCIIString() + " hashed as " + expResult);
        RealQuadraticRing someRing = new RealQuadraticRing(2);
        int result = someRing.hashCode();
        System.out.println("Test-initialized " + someRing.toASCIIString() + " hashed as " + expResult);
        String assertionMessage = "BeforeClass-initialized and test-initialized Z[sqrt(2)] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = ringZPhi.hashCode();
        System.out.println("BeforeClass-initialized " + ringZPhi.toASCIIString() + " hashed as " + expResult);
        someRing = new RealQuadraticRing(5);
        result = someRing.hashCode();
        System.out.println("Test-initialized " + someRing.toASCIIString() + " hashed as " + expResult);
        assertionMessage = "BeforeClass-initialized and test-initialized Z[omega] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = ringOQ13.hashCode();
        System.out.println("BeforeClass-initialized " + ringOQ13.toASCIIString() + " hashed as " + expResult);
        someRing = new RealQuadraticRing(13);
        result = someRing.hashCode();
        System.out.println("Test-initialized " + someRing.toASCIIString() + " hashed as " + expResult);
        assertionMessage = "BeforeClass-initialized and test-initialized Z[sqrt(-7)] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = ringRandom.hashCode();
        System.out.println("BeforeClass-initialized " + ringRandom.toASCIIString() + " hashed as " + expResult);
        someRing = new RealQuadraticRing(randomDiscr);
        result = someRing.hashCode();
        System.out.println("Test-initialized " + someRing.toASCIIString() + " hashed as " + expResult);
        assertionMessage = "BeforeClass-initialized and test-initialized " + ringRandom.toASCIIString() + " should hash the same.";
        assertEquals(assertionMessage, expResult, result);
    }

    /**
     * Test of toString method, of class RealQuadraticRing.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Z[\u221A2]";
        String result = ringZ2.toString();
        assertEquals(expResult, result);
        expResult = "Z[\u03C6]";
        result = ringZPhi.toString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "O_(Q(\u221A" + randomDiscr + "))";
        } else {
            expResult = "Z[\u221A" + randomDiscr + "]";
        }
        result = ringRandom.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class RealQuadraticRing, inherited from 
     * QuadraticRing.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "Z[sqrt(2)]";
        String result = ringZ2.toASCIIString();
        assertEquals(expResult, result);
        expResult = "Z[phi]";
        result = ringZPhi.toASCIIString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "O_(Q(sqrt(" + randomDiscr + ")))";
        } else {
            expResult = "Z[sqrt(" + randomDiscr + ")]";
        }
        result = ringRandom.toASCIIString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toTeXString method, of class RealQuadraticRing, inherited from 
     * QuadraticRing. Note that the blackboard preference has an effect on the 
     * output.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        ImaginaryQuadraticRing.preferBlackboardBold(true);
        String expResult = "\\mathbb Z[\\sqrt{2}]";
        String result = ringZ2.toTeXString();
        assertEquals(expResult, result);
        expResult = "\\mathbb Z[\\phi]";
        result = ringZPhi.toTeXString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "\\mathcal O_{\\mathbb Q(\\sqrt{" + randomDiscr + "})}";
        } else {
            expResult = "\\mathbb Z[\\sqrt{" + randomDiscr + "}]";
        }
        result = ringRandom.toTeXString();
        assertEquals(expResult, result);
        ImaginaryQuadraticRing.preferBlackboardBold(false);
        expResult = "\\textbf Z[\\sqrt{2}]";
        result = ringZ2.toTeXString();
        assertEquals(expResult, result);
        expResult = "\\textbf Z[\\phi]";
        result = ringZPhi.toTeXString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "\\mathcal O_{\\textbf Q(\\sqrt{" + randomDiscr + "})}";
        } else {
            expResult = "\\textbf Z[\\sqrt{" + randomDiscr + "}]";
        }
        result = ringRandom.toTeXString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class RealQuadraticRing, inherited from 
     * QuadraticRing. Note that the blackboard preference has an effect on the 
     * output.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        ImaginaryQuadraticRing.preferBlackboardBold(true);
        String expResult = "\u2124[&radic;2]";
        String result = ringZ2.toHTMLString();
        assertEquals(expResult, result);
        expResult = "\u2124[&phi;]";
        result = ringZPhi.toHTMLString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "<i>O</i><sub>\u211A(&radic;(" + randomDiscr + ")</sub>";
        } else {
            expResult = "\u2124[&radic;" + randomDiscr + "]";
        }
        result = ringRandom.toHTMLString();
        assertEquals(expResult, result);
        ImaginaryQuadraticRing.preferBlackboardBold(false);
        expResult = "<b>Z</b>[&radic;2]";
        result = ringZ2.toHTMLString();
        assertEquals(expResult, result);
        expResult = "<b>Z</b>[&phi;]";
        result = ringZPhi.toHTMLString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "<i>O</i><sub><b>Q</b>(&radic;(" + randomDiscr + ")</sub>";
        } else {
            expResult = "<b>Z</b>[&radic;" + randomDiscr + "]";
        }
        result = ringRandom.toHTMLString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toFilenameString method, of class RealQuadraticRing, inherited 
     * from QuadraticRing.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        // Preference for blackboard bold is irrelevant for this particular test.
        String expResult = "Z2";
        String result = ringZ2.toFilenameString();
        assertEquals(expResult, result);
        expResult = "ZPhi";
        result = ringZPhi.toFilenameString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "OQ" + randomDiscr;
        } else {
            expResult = "Z" + randomDiscr;
        }
        result = ringRandom.toFilenameString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of RealQuadraticRing class constructor. The main thing we're 
     * testing here is that an invalid argument triggers an 
     * {@link IllegalArgumentException}. That the other tests pass makes us 
     * plenty confident that the constructor works correctly on valid arguments.
     */
    @Test
    public void testConstructor() {
        System.out.println("ImaginaryQuadraticRing (constructor)");
        RealQuadraticRing ringZ10 = new RealQuadraticRing(10); // This should work fine
        System.out.println("Created " + ringZ10.toASCIIString() + " without problem.");
        RealQuadraticRing ringZ11 = new RealQuadraticRing(13); // This should also work fine
        System.out.println("Created " + ringZ11.toASCIIString() + " without problem.");
        try {
            RealQuadraticRing ringZ1 = new RealQuadraticRing(1);
            System.out.println("Somehow created " + ringZ1.toASCIIString() + " without problem.");
            fail("Attempt to use 1 should have caused an UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Attempt to use 1 correctly triggered UnsupportedNumberDomainException \"" + unde.getMessage() + "\"");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use 1 correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
        }
        try {
            RealQuadraticRing ringZ12 = new RealQuadraticRing(12);
            System.out.println("Somehow created " + ringZ12.toASCIIString() + " without problem.");
            fail("Attempt to use 12 should have caused an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use 12 correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
        }
        try {
            RealQuadraticRing ringZi7 = new RealQuadraticRing(-7);
            System.out.println("Somehow created " + ringZi7.toASCIIString() + " without problem.");
            fail("Attempt to use -7 should have caused an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use -7 correctly triggered IllegalArgumentException \"" + iae.getMessage() + "\"");
        }
    }    
}
