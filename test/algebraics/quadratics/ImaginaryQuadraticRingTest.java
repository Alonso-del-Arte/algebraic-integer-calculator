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
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import fileops.PNGFileFilter;
import fractions.Fraction;
import viewers.ImagQuadRingDisplay;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ImaginaryQuadraticRing class.
 * @author Alonso del Arte, from template generated by NetBeans IDE.
 */
public class ImaginaryQuadraticRingTest {
    
    private static final ImaginaryQuadraticRing RING_GAUSSIAN 
            = new ImaginaryQuadraticRing(-1);
    
    private static final ImaginaryQuadraticRing RING_ZI2 
            = new ImaginaryQuadraticRing(-2);
    
    private static final ImaginaryQuadraticRing RING_EISENSTEIN 
            = new ImaginaryQuadraticRing(-3);
    
    private static final ImaginaryQuadraticRing RING_OQI7 
            = new ImaginaryQuadraticRing(-7);
    
    private static ImaginaryQuadraticRing ringRandom;
    
    private static int randomDiscr;
    
    /**
     * Sets up five ImaginaryQuadraticRing objects, corresponding to 
     * <b>Z</b>[<i>i</i>], <b>Z</b>[&radic;&minus;2], <b>Z</b>[&omega;], 
     * <i>O</i><sub><b>Q</b>(&radic;&minus;7)</sub> and a randomly chosen ring.
     * The randomly chosen ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> is 
     * determined by <i>d</i> being at most &minus;5. It is unlikely but not 
     * impossible that this could turn out to be 
     * <i>O</i><sub><b>Q</b>(&radic;&minus;7)</sub>, which would be just fine if 
     * it just made some of the tests redundant. But since it could make {@link 
     * #testEquals()} fail, it is necessary to guard against this unlikely 
     * eventuality.
     */
    @BeforeClass
    public static void setUpClass() {
        randomDiscr = -NumberTheoreticFunctionsCalculator
                .randomSquarefreeNumber(ImagQuadRingDisplay.MINIMUM_RING_D);
        if (randomDiscr > -5) {
            randomDiscr = -5;
        }
        if (randomDiscr == -7) {
            randomDiscr = -11;
        }
        ringRandom = new ImaginaryQuadraticRing(randomDiscr);
        System.out.println(ringRandom.toASCIIString() 
                + " has been randomly chosen for testing purposes");
    }
    
    @Test
    public void testToStringZI() {
        String expected = "Z[i]";
        String actual = RING_GAUSSIAN.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringZOmega() {
        String expected = "Z[\u03C9]";
        String actual = RING_EISENSTEIN.toString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toString function, of the ImaginaryQuadraticRing class, 
     * inherited from {@link QuadraticRing}.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "Z[\u221A\u2212" + (-d) + "]";
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "O_(Q(\u221A\u2212" + (-d) + "))";
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "Z[\u221A\u2212" + (-d) + "]";
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringZI() {
        String expected = "Z[i]";
        String actual = RING_GAUSSIAN.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringZOmega() {
        String expected = "Z[omega]";
        String actual = RING_EISENSTEIN.toASCIIString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toASCIIString function, of the ImaginaryQuadraticRing class, 
     * inherited from {@link QuadraticRing}.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "Z[sqrt(" + d + ")]";
        String actual = ring.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "O_(Q(sqrt(" + d + ")))";
        String actual = ring.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "Z[sqrt(" + d + ")]";
        String actual = ring.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringZI() {
        String expected = "\\mathbf Z[i]";
        String actual = RING_GAUSSIAN.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringZOmega() {
        String expected = "\\mathbf Z[\\omega]";
        String actual = RING_EISENSTEIN.toTeXString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toTeXString function, of the ImaginaryQuadraticRing class.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "\\mathbf Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "\\mathcal O_{\\mathbf Q(\\sqrt{" + d + "})}";
        String actual = ring.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "\\mathbf Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldZI() {
        String expected = "\\mathbb Z[i]";
        String actual = RING_GAUSSIAN.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldZOmega() {
        String expected = "\\mathbb Z[\\omega]";
        String actual = RING_EISENSTEIN.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toTeXString function, of the ImaginaryQuadraticRing class.
     */
    @Test
    public void testToTeXStringBlackboardBold() {
        System.out.println("toTeXStringBlackboardBold");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "\\mathbb Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "\\mathcal O_{\\mathbb Q(\\sqrt{" + d + "})}";
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringBlackboardBoldD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "\\mathbb Z[\\sqrt{" + d + "}]";
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringZI() {
        String expected = "<b>Z</b>[<i>i</i>]";
        String actual = RING_GAUSSIAN.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringZOmega() {
        String expected = "<b>Z</b>[&omega;]";
        String actual = RING_EISENSTEIN.toHTMLString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toHTMLString function, of the ImaginaryQuadraticRing class.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "<b>Z</b>[&radic;&minus;" + (-d) + "]";
        String actual = ring.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "<i>O</i><sub><b>Q</b>(&radic;(&minus;" + (-d) 
                + "))</sub>";
        String actual = ring.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "<b>Z</b>[&radic;&minus;" + (-d) + "]";
        String actual = ring.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldZI() {
        String expectedA = "&#x2124;[<i>i</i>]";
        String expectedB = "&#8484;[<i>i</i>]";
        String actual = RING_GAUSSIAN.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldZOmega() {
        String expectedA = "&#x2124;[&omega;]";
        String expectedB = "&#8484;[&omega;]";
        String actual = RING_EISENSTEIN.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    /**
     * Test of the toHTMLStringBlackboardBold function, of the 
     * ImaginaryQuadraticRing class.
     */
    @Test
    public void testToHTMLStringBlackboardBold() {
        System.out.println("toHTMLStringBlackboardBold");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expectedA = "&#x2124;[&radic;&minus;" + (-d) + "]";
        String expectedB = "&#8484;[&radic;&minus;" + (-d) + "]";
        String actual = ring.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expectedA = "<i>O</i><sub>&#x211A;(&radic;(&minus;" + (-d) 
                + "))</sub>";
        String expectedB = "<i>O</i><sub>&#8474;(&radic;(&minus;" + (-d) 
                + "))</sub>";
        String actual = ring.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToHTMLStringBlackboardBoldD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expectedA = "&#x2124;[&radic;&minus;" + (-d) + "]";
        String expectedB = "&#8484;[&radic;&minus;" + (-d) + "]";
        String actual = ring.toHTMLStringBlackboardBold();
        String msg = "Function should give \"" + expectedA + "\" or \"" 
                + expectedB + "\", was \"" + actual + "\"";
        assert expectedA.equals(actual) || expectedB.equals(actual) : msg;
    }
    
    @Test
    public void testToFilenameStringZI() {
        String expected = "ZI";
        String actual = RING_GAUSSIAN.toFilenameString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToFilenameStringZOmega() {
        String expected = "ZW";
        String actual = RING_EISENSTEIN.toFilenameString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toFilenameString function, of the ImaginaryQuadraticRing 
     * class, inherited from {@link QuadraticRing}. Preference for blackboard 
     * bold is irrelevant for this function.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "ZI" + (-d);
        String actual = ring.toFilenameString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToFilenameStringD1Mod4() {
        int propD = -randomSquarefreeNumberMod(3, 4);
        int d = (propD == -3) ? -7 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "OQI" + (-d);
        String actual = ring.toFilenameString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToFilenameStringD3Mod4() {
        int propD = -randomSquarefreeNumberMod(1, 4);
        int d = (propD == -1) ? -5 : propD;
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String expected = "ZI" + (-d);
        String actual = ring.toFilenameString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the isPurelyReal function, of the ImaginaryQuadraticRing class.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        int d = -NumberTheoreticFunctionsCalculator
                .randomSquarefreeNumber(4096);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String msg = ring.toString() 
                + " should not be said to be a purely real ring";
        assert !ring.isPurelyReal() : msg;
    }
    
    /**
     * Test of the discriminant function of the ImaginaryQuadraticRing class, 
     * inherited from {@link QuadraticRing}. For <b>Z</b>[&radic;<i>d</i>] with 
     * <i>d</i> &equiv; 2 or 3 (mod 4), the discriminant should be 4<i>d</i>. 
     * And for <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> with <i>d</i> 
     * &equiv; 1 (mod 4), the discriminant should just be <i>d</i>.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        int expected = 4 * d;
        int actual = ring.discriminant();
        String message = "Discriminant of " + ring.toString() + " should be " 
                + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testDiscriminant1Mod4() {
        int expected = -randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(expected);
        int actual = ring.discriminant();
        String message = "Discriminant of " + ring.toString() + " should be " 
                + expected;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testDiscriminant3Mod4() {
        int d = -randomSquarefreeNumberMod(1, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        int expected = 4 * d;
        int actual = ring.discriminant();
        String message = "Discriminant of " + ring.toString() + " should be " 
                + expected;
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the getPowerBasis function of the ImaginaryQuadraticRing class, 
     * inherited from {@link QuadraticRing}. The power basis of any quadratic 
     * ring should be 1, <i>a</i>, where <i>a</i> may be either &radic;<i>d</i> 
     * or <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;<i>d</i></sup>&frasl;<sub>2</sub>.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        int d = -randomSquarefreeNumber(Short.MAX_VALUE);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        Fraction one = new Fraction(1L);
        Fraction[] powerMultiplicands = {one, one};
        PowerBasis expected = new PowerBasis(powerMultiplicands);
        PowerBasis actual = ring.getPowerBasis();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of getRadicand method, of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}.
     */
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        int expected = -randomSquarefreeNumber(1024);
        QuadraticRing ring = new ImaginaryQuadraticRing(expected);
        int actual = ring.getRadicand();
        String message = "Inquiring for radicand of " + ring.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the getAbsNegRad function, of the ImaginaryQuadraticRing class.
     */
    @Test
    public void testGetAbsNegRad() {
        System.out.println("getAbsNegRad");
        int expected = randomSquarefreeNumber(1024);
        QuadraticRing ring = new ImaginaryQuadraticRing(-expected);
        int actual = ring.getAbsNegRad();
        String message = "Inquiring as the absolute value of radicand for " 
                + ring.toString();
        assertEquals(message, expected, actual);
    }

    /**
     * Test of the getAbsNegRadSqrt function, of the ImaginaryQuadraticRing 
     * class.
     */
    @Test
    public void testGetAbsNegRadSqrt() {
        System.out.println("getAbsNegRadSqrt");
        int d = -randomSquarefreeNumber(2048);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        double expected = Math.sqrt(-d);
        double actual = ring.getAbsNegRadSqrt();
        String message = "Inquiring as the absolute value of square root for " 
                + ring.toString();
        assertEquals(message, expected, actual, QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of the hasHalfIntegers function, of the ImaginaryQuadraticRing  
     * class, inherited from {@link QuadraticInteger}. If d = 1 mod 4 (or 
     * equivalently d = -3 mod 4), the ring should be said to have so-called 
     * "half-integers."
     */
    @Test
    public void testHasHalfIntegers() {
        System.out.println("hasHalfIntegers");
        int d = -randomSquarefreeNumberMod(3, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String msg = ring.toString() 
                + " should be said to have \"half-integers\"";
        assert ring.hasHalfIntegers() : msg;
    }

    /**
     * Another test of the hasHalfIntegers function, of the 
     * ImaginaryQuadraticRing class, inherited from {@link QuadraticInteger}. If 
     * d = 2 mod 4, the ring should not be said to have so-called 
     * "half-integers."
     */
    @Test
    public void testHasHalfIntegers2Mod4() {
        System.out.println("hasHalfIntegers");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        String msg = ring.toString() 
                + " should not be said to have \"half-integers\"";
        assert !ring.hasHalfIntegers() : msg;
    }

    /**
     * Test of hashCode method, of class ImaginaryQuadraticRing, inherited from 
     * {@link QuadraticRing}. The purpose here isn't to test that any specific 
     * ring maps to any specific hash code, but rather that two rings that are 
     * equal get the same hash code, and two rings that are not equal get 
     * different hash codes.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int expResult = RING_GAUSSIAN.hashCode();
        System.out.println("BeforeClass-initialized " + RING_GAUSSIAN.toASCIIString() + " hashed as " + expResult);
        ImaginaryQuadraticRing someRing = new ImaginaryQuadraticRing(-1);
        int result = someRing.hashCode();
        String assertionMessage = "BeforeClass-initialized and test-initialized Z[i] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = RING_ZI2.hashCode();
        System.out.println("BeforeClass-initialized " + RING_ZI2.toASCIIString() + " hashed as " + expResult);
        someRing = new ImaginaryQuadraticRing(-2);
        result = someRing.hashCode();
        assertionMessage = "BeforeClass-initialized and test-initialized Z[sqrt(-2)] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = RING_EISENSTEIN.hashCode();
        System.out.println("BeforeClass-initialized " + RING_EISENSTEIN.toASCIIString() + " hashed as " + expResult);
        someRing = new ImaginaryQuadraticRing(-3);
        result = someRing.hashCode();
        assertionMessage = "BeforeClass-initialized and test-initialized Z[omega] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = RING_OQI7.hashCode();
        System.out.println("BeforeClass-initialized " + RING_OQI7.toASCIIString() + " hashed as " + expResult);
        someRing = new ImaginaryQuadraticRing(-7);
        result = someRing.hashCode();
        assertionMessage = "BeforeClass-initialized and test-initialized Z[sqrt(-7)] should hash the same.";
        assertEquals(assertionMessage, expResult, result);
        expResult = ringRandom.hashCode();
        System.out.println("BeforeClass-initialized " + ringRandom.toASCIIString() + " hashed as " + expResult);
        someRing = new ImaginaryQuadraticRing(randomDiscr);
        result = someRing.hashCode();
        assertionMessage = "BeforeClass-initialized and test-initialized " + ringRandom.toASCIIString() + " should hash the same.";
        assertEquals(assertionMessage, expResult, result);
    }
    
    /**
     * Test of equals method, of class ImaginaryQuadraticRing, inherited from 
     * {@link QuadraticRing}. The reflexive, symmetric and transitive properties 
     * are tested for rings that should register as equal. Then five different 
     * rings are tested to check that they're not registering as equal.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ImaginaryQuadraticRing someRing = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticRing transitiveHold = new ImaginaryQuadraticRing(-1);
        assertTrue(RING_GAUSSIAN.equals(RING_GAUSSIAN)); // Reflexive test
        assertEquals(RING_GAUSSIAN, someRing);
        assertEquals(someRing, RING_GAUSSIAN); // Symmetric test
        assertEquals(someRing, transitiveHold);
        assertEquals(transitiveHold, RING_GAUSSIAN); // Transitive test
        // Now to test that rings that are not equal are reported as not equal
        assertNotEquals(RING_GAUSSIAN, RING_ZI2);
        assertNotEquals(RING_ZI2, RING_EISENSTEIN);
        assertNotEquals(RING_EISENSTEIN, RING_OQI7);
        assertNotEquals(RING_OQI7, ringRandom);
        // Lastly, a ring should not be equal to an unrelated object
        PNGFileFilter obj = new PNGFileFilter();
        assertNotEquals(ringRandom, obj);
    }

    /**
     * Test of ImaginaryQuadraticRing class constructor. The main thing we're 
     * testing here is that an invalid argument triggers an 
     * IllegalArgumentException. That the other tests pass makes us plenty 
     * confident that the constructor works correctly on valid arguments.
     */
    @Test
    public void testConstructor() {
        System.out.println("ImaginaryQuadraticRing (constructor)");
        ImaginaryQuadraticRing ringZi10 = new ImaginaryQuadraticRing(-10); // This should work fine
        System.out.println("Created " + ringZi10.toASCIIString() + " without problem.");
        ImaginaryQuadraticRing ringOQi11 = new ImaginaryQuadraticRing(-11); // This should also work fine
        System.out.println("Created " + ringOQi11.toASCIIString() + " without problem.");
        try {
            ImaginaryQuadraticRing ringZi12 = new ImaginaryQuadraticRing(-12);
            System.out.println("Somehow created " + ringZi12.toASCIIString() + " without problem.");
            fail("Attempt to use -12 should have caused an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use -12 correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        }
        try {
            ImaginaryQuadraticRing ringZ7 = new ImaginaryQuadraticRing(7);
            System.out.println("Somehow created " + ringZ7.toASCIIString() + " without problem.");
            fail("Attempt to use 7 should have caused an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to use 7 correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        }
    }
    
}