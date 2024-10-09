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
package algebraics.quadratics;

import arithmetic.PowerBasis;
import calculators.NumberTheoreticFunctionsCalculator;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberOtherThan;
import fractions.Fraction;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the RealQuadraticRing class.
 * @author Alonso del Arte, from template generated by NetBeans IDE.
 */
public class RealQuadraticRingTest {
    
    private static final RealQuadraticRing RING_Z2 = new RealQuadraticRing(2);
    
    private static final RealQuadraticRing RING_ZPHI = new RealQuadraticRing(5);
    
    private static final RealQuadraticRing RING_OQ13 
            = new RealQuadraticRing(13);
    
    private static RealQuadraticRing ringRandom;
    
    private static int randomDiscr;
    private static boolean ringRandomD1Mod4;
    
    /**
     * Sets up four RealQuadraticRing objects, corresponding to 
     * <b>Z</b>[&radic;2], <b>Z</b>[&phi;], 
     * <i>O</i><sub><b>Q</b>(&radic;13)</sub> and a randomly chosen ring. The 
     * randomly chosen ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> is 
     * determined by <i>d</i> being at least 6.
     */
    @BeforeClass
    public static void setUpClass() {
        randomDiscr = NumberTheoreticFunctionsCalculator
                .randomSquarefreeNumber(100);
        if (randomDiscr < 6) {
            randomDiscr = 6;
        }
        ringRandomD1Mod4 = (randomDiscr % 4 == 1);
        ringRandom = new RealQuadraticRing(randomDiscr);
        System.out.println(ringRandom.toASCIIString() 
                + " has been randomly chosen for testing purposes.");
    }
    
    private static int choosePositiveRandomSquarefreeNot1() {
        int number;
        do {
            number = randomSquarefreeNumber(Short.MAX_VALUE);
        } while (number == 1);
        return number;
    }
    
    /**
     * Test of the isPurelyReal function, of the RealQuadraticRing class.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        int propD = randomSquarefreeNumber(Short.MAX_VALUE);
        int d = (propD == 1) ? 2 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String msg = ring.toString() 
                + " should be said to be a purely real ring";
        assert ring.isPurelyReal() : msg;
    }
    
    /**
     * Test of the discriminant function, of the RealQuadraticRing class, 
     * inherited from {@link QuadraticRing}. For <b>Z</b>[&radic;<i>d</i>] with 
     * <i>d</i> &equiv; 2 or 3 (mod 4), the discriminant should be 4<i>d</i>. 
     * And for <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> with <i>d</i> 
     * &equiv; 1 (mod 4), the discriminant should just be <i>d</i>.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        int expected = 4 * d;
        int actual = ring.discriminant();
        String message = "Discriminant of " + ring.toString() + " should be " 
                + expected;
        assertEquals(message, expected, actual);
    }

    @Test
    public void testDiscriminant1Mod4() {
        int expected = randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new RealQuadraticRing(expected);
        int actual = ring.discriminant();
        String message = "Discriminant of " + ring.toString() + " should be " 
                + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testDiscriminant3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        int expected = 4 * d;
        int actual = ring.discriminant();
        String message = "Discriminant of " + ring.toString() + " should be " 
                + expected;
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getPowerBasis function of the RealQuadraticRing class, 
     * inherited from {@link QuadraticRing}. The power basis of any quadratic 
     * ring should be 1, <i>a</i>, where <i>a</i> may be either &radic;<i>d</i> 
     * or <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;<i>d</i></sup>&frasl;<sub>2</sub>.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        int propD = randomSquarefreeNumber(Short.MAX_VALUE);
        int d = (propD == 1) ? 2 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        Fraction one = new Fraction(1);
        Fraction[] powerMultiplicands = {one, one};
        PowerBasis expected = new PowerBasis(powerMultiplicands);
        PowerBasis actual = ring.getPowerBasis();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getRadicand function, of the RealQuadraticRing class, 
     * inherited from {@link QuadraticRing}.
     */
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        int expected = choosePositiveRandomSquarefreeNot1();
        QuadraticRing ring = new RealQuadraticRing(expected);
        int actual = ring.getRadicand();
        String message = "Inquiring for radicand of " + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getAbsNegRad function, of the RealQuadraticRing class.
     */
    @Test
    public void testGetAbsNegRad() {
        System.out.println("getAbsNegRad");
        int expected = choosePositiveRandomSquarefreeNot1();
        QuadraticRing ring = new RealQuadraticRing(expected);
        int actual = ring.getAbsNegRad();
        String message = "Inquiring for radicand of " + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getRadSqrt function, of the RealQuadraticRing class.
     */
    @Test
    public void testGetRadSqrt() {
        System.out.println("getRadSqrt");
        int d = choosePositiveRandomSquarefreeNot1();
        RealQuadraticRing ring = new RealQuadraticRing(d);
        double expected = Math.sqrt(d);
        double actual = ring.getRadSqrt();
        double delta = 0.00000001;
        String message = "Inquiring square root of " + d;
        assertEquals(message, expected, actual, delta);
    }
    
    /**
     * Test of getAbsNegRadSqrt method, of class RealQuadraticRing.
     */
    @Test
    public void testGetAbsNegRadSqrt() {
        System.out.println("getAbsNegRadSqrt");
        assertEquals(Math.sqrt(2), RING_Z2.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(5), RING_ZPHI.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(13), RING_OQ13.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(randomDiscr), ringRandom.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of getRadSqrt and getAbsNegRadSqrt methods, of class 
     * RealQuadraticRing. Since these rings adjoin numbers that are real and 
     * positive, getRadSqrt and getAbsNegRadSqrt should give the same result.
     */
    @Test
    public void testGetRadSqrtGetAbsNegRadSqrtCorr() {
        assertEquals(RING_Z2.getRadSqrt(), RING_Z2.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(RING_ZPHI.getRadSqrt(), RING_ZPHI.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(RING_OQ13.getRadSqrt(), RING_OQ13.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(ringRandom.getRadSqrt(), ringRandom.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of hasHalfIntegers method, of class RealQuadraticRing, inherited 
     * from {@link QuadraticInteger}.
     */
    @Test
    public void testHasHalfIntegers() {
        System.out.println("hasHalfIntegers");
        String msgNoHalves = " should not be said to have half-integers";
        String message = RING_Z2.toString() + msgNoHalves;
        assert !RING_Z2.hasHalfIntegers() : message;
        String msgHalves = " should be said to have half-integers";
        message = RING_ZPHI.toString() + msgHalves;
        assert RING_ZPHI.hasHalfIntegers() : message;
        message = RING_OQ13.toString() + msgHalves;
        assertTrue(message, RING_OQ13.hasHalfIntegers());
        if (ringRandomD1Mod4) {
            message = ringRandom.toString() + msgHalves;
        } else {
            message = ringRandom.toString() + msgNoHalves;
        }
        assertEquals(message, ringRandomD1Mod4, ringRandom.hasHalfIntegers());
    }
    
    @Test
    public void testReferentialEquality() {
        int d = choosePositiveRandomSquarefreeNot1();
        QuadraticRing ring = new RealQuadraticRing(d);
        assertEquals(ring, ring);
    }
    
    @Test
    public void testNotEqualsNull() {
        int d = choosePositiveRandomSquarefreeNot1();
        QuadraticRing ring = new RealQuadraticRing(d);
        assertNotEquals(ring, null);
    }

    @Test
    public void testNotEqualsDiffClass() {
        int d = choosePositiveRandomSquarefreeNot1();
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticRing diffClassRing 
                = new QuadraticRingTest.QuadraticRingImpl(d);
        assertNotEquals(ring, diffClassRing);
    }
    
    @Test
    public void testNotEqualsDiffDiscr() {
        int bound = 8192;
        int paramA = randomSquarefreeNumber(bound);
        int paramB = randomSquarefreeNumberOtherThan(paramA, bound);
        QuadraticRing ringA = new RealQuadraticRing(paramA);
        QuadraticRing ringB = new RealQuadraticRing(paramB);
        String message = ringA.toString() 
                + " should not be said to be the same as " + ringB.toString();
        assertNotEquals(message, ringA, ringB);
    }

    /**
     * Test of the equals function, of the RealQuadraticRing class, inherited 
     * from {@link QuadraticRing}.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int d = randomSquarefreeNumber(4096);
        QuadraticRing someRing = new RealQuadraticRing(d);
        QuadraticRing sameRing = new RealQuadraticRing(d);
        assertEquals(someRing, sameRing);
    }

/**
     * Test of the hashCode function, of the RealQuadraticRing class, inherited  
     * from {@link QuadraticRing}.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int initialCapacity = randomNumber(64) + 16;
        Set<QuadraticRing> rings = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            int d = choosePositiveRandomSquarefreeNot1();
            QuadraticRing ring = new RealQuadraticRing(d);
            rings.add(ring);
            hashes.add(ring.hashCode());
        }
        int expected = rings.size();
        int actual = hashes.size();
        String message = "Set of " + expected 
                + " rings should correspond to as many hashes";
        assertEquals(message, expected, actual);
    }

    @Test
    public void testToStringZPhi() {
        String expected = "Z[\u03C6]";
        String actual = RING_ZPHI.toString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toString function, of the RealQuadraticRing class, inherited 
     * from {@link QuadraticRing}.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "Z[\u221A" + d + "]";
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "O_(Q(\u221A" + d + "))";
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringD3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "Z[\u221A" + d + "]";
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringZPhi() {
        String expected = "Z[phi]";
        String actual = RING_ZPHI.toASCIIString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of toASCIIString method, of class RealQuadraticRing, inherited from 
     * {@link QuadraticRing}.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "Z[sqrt(" + d + ")]";
        String actual = ring.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "O_(Q(sqrt(" + d + ")))";
        String actual = ring.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringD3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "Z[sqrt(" + d + ")]";
        String actual = ring.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringZPhi() {
        String expected = "\\mathbf Z[\\phi]";
        String actual = RING_ZPHI.toTeXString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toTeXString function, of the RealQuadraticRing class.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "\\mathbf Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "\\mathcal O_{\\mathbf Q(\\sqrt{" + d + "})}";
        String actual = ring.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringD3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "\\mathbf Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldZPhi() {
        String expected = "\\mathbb Z[\\phi]";
        String actual = RING_ZPHI.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toTeXStringBlackboardBold function, of the RealQuadraticRing 
     * class.
     */
    @Test
    public void testToTeXStringBlackboardBold() {
        System.out.println("toTeXStringBlackboardBold");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "\\mathbb Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "\\mathcal O_{\\mathbb Q(\\sqrt{" + d + "})}";
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldD3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "\\mathbb Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringZPhi() {
        String expected = "<b>Z</b>[&phi;]";
        String actual = RING_ZPHI.toHTMLString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toHTMLString function, of the ImaginaryQuadraticRing class.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "<b>Z</b>[&radic;" + d + "]";
        String actual = ring.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "<i>O</i><sub><b>Q</b>(&radic;(" + d + "))</sub>";
        String actual = ring.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringD3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "<b>Z</b>[&radic;" + d + "]";
        String actual = ring.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldZPhi() {
        String expectedA = "&#x2124;[&phi;]";
        String expectedB = "&#8484;[&phi;]";
        String actual = RING_ZPHI.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    /**
     * Test of the toHTMLStringBlackboardBold function, of the 
     * RealQuadraticRing class.
     */
    @Test
    public void testToHTMLStringBlackboardBold() {
        System.out.println("toHTMLStringBlackboardBold");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expectedA = "&#x2124;[&radic;" + d + "]";
        String expectedB = "&#8484;[&radic;" + d + "]";
        String actual = ring.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expectedA = "<i>O</i><sub>&#x211A;(&radic;(" + d + "))</sub>";
        String expectedB = "<i>O</i><sub>&#8474;(&radic;(" + d + "))</sub>";
        String actual = ring.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldD3Mod4() {
        int d = randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expectedA = "&#x2124;[&radic;" + d + "]";
        String expectedB = "&#8484;[&radic;" + d + "]";
        String actual = ring.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToFilenameStringZPhi() {
        String expected = "ZPHI";
        String actual = RING_ZPHI.toFilenameString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toFilenameString function, of the RealQuadraticRing class, 
     * inherited from {@link QuadraticRing}.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        int d = randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "Z" + d;
        String actual = ring.toFilenameString();
        assertEquals(expected, actual);
    }    
    
    @Test
    public void testToFilenameStringD1Mod4() {
        int propD = randomSquarefreeNumberMod(1, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "OQ" + d;
        String actual = ring.toFilenameString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToFilenameStringD3Mod4() {
        int propD = randomSquarefreeNumberMod(3, 4);
        int d = (propD < 9) ? 13 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        String expected = "Z" + d;
        String actual = ring.toFilenameString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the RealQuadraticRing constructor. Any number divisible by a 
     * nontrivial perfect square should be rejected.
     */
    @Test
    public void testConstructorRejectsNonSquarefreeD() {
        int n = randomSquarefreeNumber(Byte.MAX_VALUE);
        int d = n * n * randomSquarefreeNumber(Short.MAX_VALUE);
        String msg = "Parameter d = " + d 
                + " should be rejected for real quadratic ring";
        Throwable t = assertThrows(() -> {
            QuadraticRing badRing = new RealQuadraticRing(d);
            System.out.println("Should not have been able to create " 
                    + badRing.toString() + " with d = " + d);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test of the RealQuadraticRing constructor. The parameter d = 1 should be 
     * rejected.
     */
    @Test
    public void testConstructorRejectsD1() {
        int d = 1;
        String msg = "Parameter d = " + d 
                + " should be rejected for real quadratic ring";
        Throwable t = assertThrows(() -> {
            QuadraticRing badRing = new RealQuadraticRing(d);
            System.out.println("Should not have been able to create " 
                    + badRing.toString() + " with d = " + d);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test of the RealQuadraticRing constructor. The parameter d = 0 should be 
     * rejected.
     */
    @Test
    public void testConstructorRejectsD0() {
        int d = 0;
        String msg = "Parameter d = " + d 
                + " should be rejected for real quadratic ring";
        Throwable t = assertThrows(() -> {
            QuadraticRing badRing = new RealQuadraticRing(d);
            System.out.println("Should not have been able to create " 
                    + badRing.toString() + " with d = " + d);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test of the RealQuadraticRing constructor. A negative parameter, even if 
     * squarefree, should be rejected.
     */
    @Test
    public void testConstructorRejectsNegativeD() {
        int d = -randomSquarefreeNumber(Short.MAX_VALUE);
        String msg = "Parameter d = " + d 
                + " should be rejected for real quadratic ring";
        Throwable t = assertThrows(() -> {
            QuadraticRing badRing = new RealQuadraticRing(d);
            System.out.println("Should not have been able to create " 
                    + badRing.toString() + " with d = " + d);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
