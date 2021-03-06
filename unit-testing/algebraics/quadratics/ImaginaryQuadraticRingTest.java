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

import arithmetic.PowerBasis;
import calculators.NumberTheoreticFunctionsCalculator;
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
    private static boolean ringRandomd1mod4;
    
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
        ringRandomd1mod4 = (randomDiscr % 4 == -3);
        ringRandom = new ImaginaryQuadraticRing(randomDiscr);
        System.out.println(ringRandom.toASCIIString() 
                + " has been randomly chosen for testing purposes");
    }
    
    /**
     * Test of isPurelyReal method, of class ImaginaryQuadraticRing. Asserting 
     * false for all the test rings in this test class.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        String msgPart = " should not be said to be a purely real ring.";
        String assertionMessage = RING_GAUSSIAN.toString() + msgPart;
        assertFalse(assertionMessage, RING_GAUSSIAN.isPurelyReal());
        assertionMessage = RING_ZI2.toString() + msgPart;
        assertFalse(assertionMessage, RING_ZI2.isPurelyReal());
        assertionMessage = RING_EISENSTEIN.toString() + msgPart;
        assertFalse(assertionMessage, RING_EISENSTEIN.isPurelyReal());
        assertionMessage = RING_OQI7.toString() + msgPart;
        assertFalse(assertionMessage, RING_OQI7.isPurelyReal());
        assertionMessage = ringRandom.toString() + msgPart;
        assertFalse(assertionMessage, ringRandom.isPurelyReal());
    }
    
    /**
     * Test of discriminant method of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}. For <b>Z</b>[&radic;<i>d</i>] with <i>d</i> 
     * &equiv; 2 or 3 (mod 4), the discriminant should be 4<i>d</i>. And for 
     * <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> with <i>d</i> &equiv; 1 (mod 
     * 4), the discriminant should just be <i>d</i>.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        int expResult = -4;
        int result = RING_GAUSSIAN.discriminant();
        assertEquals(expResult, result);
        expResult = -8;
        result = RING_ZI2.discriminant();
        assertEquals(expResult, result);
        expResult = -3;
        result = RING_EISENSTEIN.discriminant();
        assertEquals(expResult, result);
        expResult = -7;
        result = RING_OQI7.discriminant();
        assertEquals(expResult, result);
        expResult = randomDiscr;
        if (randomDiscr % 4 != -3) {
            expResult *= 4;
        }
        result = ringRandom.discriminant();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getPowerBasis method of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}. The power basis of any quadratic ring should 
     * be 1, <i>a</i>, where <i>a</i> may be either &radic;<i>d</i> or 
     * <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;<i>d</i></sup>&frasl;<sub>2</sub>.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        Fraction one = new Fraction(1);
        Fraction[] powMults = {one, one};
        PowerBasis expResult = new PowerBasis(powMults);
        PowerBasis result = RING_GAUSSIAN.getPowerBasis();
        assertEquals(expResult, result);
        result = RING_ZI2.getPowerBasis();
        assertEquals(expResult, result);
        result = RING_EISENSTEIN.getPowerBasis();
        assertEquals(expResult, result);
        result = RING_OQI7.getPowerBasis();
        assertEquals(expResult, result);
        result = ringRandom.getPowerBasis();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getRadicand method, of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}.
     */
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        assertEquals(-1, RING_GAUSSIAN.getRadicand());
        assertEquals(-2, RING_ZI2.getRadicand());
        assertEquals(-3, RING_EISENSTEIN.getRadicand());
        assertEquals(-7, RING_OQI7.getRadicand());
        assertEquals(randomDiscr, ringRandom.getRadicand());
    }

    /**
     * Test of getRadSqrt method, of class ImaginaryQuadraticRing.
     */
    @Test
    public void testGetRadSqrt() {
        System.out.println("getRadSqrt");
        int startD = randomDiscr + 1;
        if (startD % 29 == 0) {
            startD++;
        }
        ImaginaryQuadraticRing ring;
        double result;
        String msg;
        for (int i = startD; i % 29 != 0; i++) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(i)) {
                ring = new ImaginaryQuadraticRing(i);
                try {
                    result = ring.getRadSqrt();
                    msg = "getRadSqrt() for " + ring.toString() 
                            + " should not have given result " + result;
                    fail(msg);
                } catch (UnsupportedOperationException uoe) {
                    System.out.println("Calling getRadSqrt() for " 
                            + ring.toASCIIString() 
                            + " correctly caused UnsupportedOperationException");
                    System.out.println("\"" + uoe.getMessage() + "\"");
                } catch (RuntimeException re) {
                    msg = re.getClass().getName() 
                            + " is the wrong exception for getRadSqrt() on " 
                            + ring.toString();
                    fail(msg);
                }
            }
        }
    }

    /**
     * Test of getAbsNegRad method, of class ImaginaryQuadraticRing.
     */
    @Test
    public void testGetAbsNegRad() {
        System.out.println("getAbsNegRad");
        assertEquals(1, RING_GAUSSIAN.getAbsNegRad());
        assertEquals(2, RING_ZI2.getAbsNegRad());
        assertEquals(3, RING_EISENSTEIN.getAbsNegRad());
        assertEquals(7, RING_OQI7.getAbsNegRad());
        assertEquals(-randomDiscr, ringRandom.getAbsNegRad());
    }

    /**
     * Test of getAbsNegRadSqrt method, of class ImaginaryQuadraticRing.
     */
    @Test
    public void testGetAbsNegRadSqrt() {
        System.out.println("getAbsNegRadSqrt");
        assertEquals(1.0, RING_GAUSSIAN.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(2), RING_ZI2.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(3), RING_EISENSTEIN.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(7), RING_OQI7.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
        assertEquals(Math.sqrt(-randomDiscr), ringRandom.getAbsNegRadSqrt(), 
                QuadraticRingTest.TEST_DELTA);
    }
    
    /**
     * Test of hasHalfIntegers method, of class ImaginaryQuadraticRing, 
     * inherited from {@link QuadraticInteger}.
     */
    @Test
    public void testHasHalfIntegers() {
        System.out.println("hasHalfIntegers");
        String msgNoHalves = " should not be said to have half-integers";
        String msgHalves = " should be said to have half-integers";
        String msg = RING_GAUSSIAN.toString() + msgNoHalves;
        assert !RING_GAUSSIAN.hasHalfIntegers() : msg;
        msg = RING_ZI2.toString() + msgNoHalves;
        assert !RING_ZI2.hasHalfIntegers() : msg;
        msg = RING_EISENSTEIN.toString() + msgHalves;
        assert RING_EISENSTEIN.hasHalfIntegers() : msg;
        msg = RING_OQI7.toString() + msgHalves;
        assert RING_OQI7.hasHalfIntegers() : msg;
        if (ringRandomd1mod4) {
            msg = ringRandom.toString() + msgHalves;
        } else {
            msg = ringRandom.toString() + msgNoHalves;
        }
        assert ringRandomd1mod4 == ringRandom.hasHalfIntegers() : msg;
    }

    /**
     * Test of preferBlackboardBold method, of class ImaginaryQuadraticRing, 
     * inherited from {@link QuadraticRing}. Without arguments, preferBlackboardBold is 
     * the getter method. With arguments, preferBlackboardBold is the setter 
     * method. This is perhaps an unnecessary test. The results of {@link 
     * #testToString()} and {@link #testToHTMLString()} are far more important.
     */
    @Test
    public void testPreferBlackboardBold() {
        System.out.println("preferBlackboardBold");
        ImaginaryQuadraticRing.preferBlackboardBold(true);
        assertTrue(QuadraticRing.preferBlackboardBold());
        ImaginaryQuadraticRing.preferBlackboardBold(false);
        assertFalse(QuadraticRing.preferBlackboardBold());
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
     * Test of toString method, of class ImaginaryQuadraticRing, inherited from 
     * {@link QuadraticRing}.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Z[i]";
        String result = RING_GAUSSIAN.toString();
        assertEquals(expResult, result);
        expResult = "Z[\u221A\u22122]";
        result = RING_ZI2.toString();
        assertEquals(expResult, result);
        expResult = "Z[\u03C9]";
        result = RING_EISENSTEIN.toString();
        assertEquals(expResult, result);
        expResult = "O_(Q(\u221A\u22127))";
        result = RING_OQI7.toString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "O_(Q(\u221A\u2212" + Math.abs(randomDiscr) + "))";
        } else {
            expResult = "Z[\u221A\u2212" + Math.abs(randomDiscr) + "]";
        }
        result = ringRandom.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "Z[i]";
        String result = RING_GAUSSIAN.toASCIIString();
        assertEquals(expResult, result);
        expResult = "Z[sqrt(-2)]";
        result = RING_ZI2.toASCIIString();
        assertEquals(expResult, result);
        expResult = "Z[omega]";
        result = RING_EISENSTEIN.toASCIIString();
        assertEquals(expResult, result);
        expResult = "O_(Q(sqrt(-7)))";
        result = RING_OQI7.toASCIIString();
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
     * Test of toTeXString method, of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}. Note that the blackboard preference has an 
     * effect on the output.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        QuadraticRing.preferBlackboardBold(true);
        String expected = "\\mathbb Z[i]";
        String actual = RING_GAUSSIAN.toTeXString();
        assertEquals(expected, actual);
        expected = "\\mathbb Z[\\sqrt{-2}]";
        actual = RING_ZI2.toTeXString();
        assertEquals(expected, actual);
        expected = "\\mathbb Z[\\omega]";
        actual = RING_EISENSTEIN.toTeXString();
        assertEquals(expected, actual);
        expected = "\\mathcal O_{\\mathbb Q(\\sqrt{-7})}";
        actual = RING_OQI7.toTeXString();
        assertEquals(expected, actual);
        if (ringRandomd1mod4) {
            expected = "\\mathcal O_{\\mathbb Q(\\sqrt{" + randomDiscr + "})}";
        } else {
            expected = "\\mathbb Z[\\sqrt{" + randomDiscr + "}]";
        }
        actual = ringRandom.toTeXString();
        assertEquals(expected, actual);
        QuadraticRing.preferBlackboardBold(false);
        expected = "\\textbf Z[i]";
        actual = RING_GAUSSIAN.toTeXString();
        assertEquals(expected, actual);
        expected = "\\textbf Z[\\sqrt{-2}]";
        actual = RING_ZI2.toTeXString();
        assertEquals(expected, actual);
        expected = "\\textbf Z[\\omega]";
        actual = RING_EISENSTEIN.toTeXString();
        assertEquals(expected, actual);
        expected = "\\mathcal O_{\\textbf Q(\\sqrt{-7})}";
        actual = RING_OQI7.toTeXString();
        assertEquals(expected, actual);
        if (ringRandomd1mod4) {
            expected = "\\mathcal O_{\\textbf Q(\\sqrt{" + randomDiscr + "})}";
        } else {
            expected = "\\textbf Z[\\sqrt{" + randomDiscr + "}]";
        }
        actual = ringRandom.toTeXString();
        assertEquals(expected, actual);
    }

    /**
     * Test of toHTMLString method, of class ImaginaryQuadraticRing, inherited 
     * from {@link QuadraticRing}. Note that the blackboard preference has an 
     * effect on the output.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        QuadraticRing.preferBlackboardBold(true);
        String expResult = "\u2124[<i>i</i>]";
        String result = RING_GAUSSIAN.toHTMLString();
        assertEquals(expResult, result);
        expResult = "\u2124[&radic;-2]";
        result = RING_ZI2.toHTMLString();
        assertEquals(expResult, result);
        expResult = "\u2124[&omega;]";
        result = RING_EISENSTEIN.toHTMLString();
        assertEquals(expResult, result);
        expResult = "<i>O</i><sub>\u211A(&radic;(-7))</sub>";
        result = RING_OQI7.toHTMLString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "<i>O</i><sub>\u211A(&radic;(" + randomDiscr + "))</sub>";
        } else {
            expResult = "\u2124[&radic;" + randomDiscr + "]";
        }
        result = ringRandom.toHTMLString();
        assertEquals(expResult, result);
        QuadraticRing.preferBlackboardBold(false);
        expResult = "<b>Z</b>[<i>i</i>]";
        result = RING_GAUSSIAN.toHTMLString();
        assertEquals(expResult, result);
        expResult = "<b>Z</b>[&radic;-2]";
        result = RING_ZI2.toHTMLString();
        assertEquals(expResult, result);
        expResult = "<b>Z</b>[&omega;]";
        result = RING_EISENSTEIN.toHTMLString();
        assertEquals(expResult, result);
        expResult = "<i>O</i><sub><b>Q</b>(&radic;(-7))</sub>";
        result = RING_OQI7.toHTMLString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "<i>O</i><sub><b>Q</b>(&radic;(" + randomDiscr + "))</sub>";
        } else {
            expResult = "<b>Z</b>[&radic;" + randomDiscr + "]";
        }
        result = ringRandom.toHTMLString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toFilenameString method, of class ImaginaryQuadraticRing, 
     * inherited from {@link QuadraticRing}.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        // Preference for blackboard bold is irrelevant for this particular test.
        String expResult = "ZI";
        String result = RING_GAUSSIAN.toFilenameString();
        assertEquals(expResult, result);
        expResult = "ZI2";
        result = RING_ZI2.toFilenameString();
        assertEquals(expResult, result);
        expResult = "ZW";
        result = RING_EISENSTEIN.toFilenameString();
        assertEquals(expResult, result);
        expResult = "OQI7";
        result = RING_OQI7.toFilenameString();
        assertEquals(expResult, result);
        if (ringRandomd1mod4) {
            expResult = "OQI" + (-1 * randomDiscr);
        } else {
            expResult = "ZI" + (-1 * randomDiscr);
        }
        result = ringRandom.toFilenameString();
        assertEquals(expResult, result);
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