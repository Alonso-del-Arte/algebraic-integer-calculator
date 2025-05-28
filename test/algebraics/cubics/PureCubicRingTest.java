/*
 * Copyright (C) 2025 Alonso del Arte
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
package algebraics.cubics;

import algebraics.MockRing;
import arithmetic.PowerBasis;

import static calculators.EratosthenesSieve.randomPrime;
import static calculators.EratosthenesSieve.randomPrimeOtherThan;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import fractions.Fraction;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the PureCubicRing class.
 * @author Alonso del Arte
 */
public class PureCubicRingTest {
    
    /**
     * Test of the isPurelyReal function, of the PureCubicRing class.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        int n = randomNumber(6) + 2;
        int signAdjust = Integer.signum(2 * randomNumber() + 1);
        int d = signAdjust * randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String msg = "Ring " + instance.toString() + " should be purely real";
        assert instance.isPurelyReal() : msg;
    }

    /**
     * Test of discriminant method, of class PureCubicRing.
     */
    @org.junit.Ignore
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        PureCubicRing instance = new PureCubicRing(3);
        int expResult = 0;
        int result = instance.discriminant();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the getPowerBasis function, of the PureCubicRing class.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        int n = randomNumber(6) + 2;
        int signAdjust = Integer.signum(2 * randomNumber() + 1);
        int d = signAdjust * randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        Fraction one = new Fraction(1);
        Fraction[] powerMultiplicands = {one, one, one};
        PowerBasis expected = new PowerBasis(powerMultiplicands);
        PowerBasis actual = instance.getPowerBasis();
        String message = "Reckoning power basis of " + instance.toString();
        assertEquals(message, expected, actual);
    }
    
    @org.junit.Ignore
    @Test
    public void testGetPowerBasisNegativeDMinusOneMod9() {
        int d = -randomSquarefreeNumberMod(8, 9);
        PureCubicRing instance = new PureCubicRing(d);
        // TODO: Figure out if PowerBasis needs to be adjusted to represent 
        // power basis of a ring such as this one
        fail("FINISH WRITING THIS TEST");
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "Z[\u221B" + d + "]";
        String actual = instance.toString();
        assertEquals(expected, actual);
    }
    
    // TODO: Write toString() tests for d not squarefree (though still 
    // cubefree), negative d

    /**
     * Test of the toASCIIString function, of the PureCubicRing class.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "Z[cbrt(" + d + ")]";
        String actual = instance.toASCIIString();
        assertEquals(expected, actual);
    }

    // TODO: Write toASCIIString() tests for d not squarefree (though still 
    // cubefree), negative d

    /**
     * Test of the toTeXString function, of the PureCubicRing class.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "\\textbf Z[\\root 3 \\of {" + d + "}]";
        String actual = instance.toTeXString();
        assertEquals(expected, actual);
    }

    // TODO: Write toTeXString() tests for d not squarefree (though still 
    // cubefree), negative d

    /**
     * Test of the toTeXStringBlackboardBold function, of the PureCubicRing 
     * class.
     */
    @Test
    public void testToTeXStringBlackboardBold() {
        System.out.println("toTeXStringBlackboardBold");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "\\mathbb Z[\\root 3 \\of {" + d + "}]";
        String actual = instance.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }

    // TODO: Write toTeXStringBlackboardBold() tests for d not squarefree 
    // (though still cubefree), negative d

    /**
     * Test of the toHTMLString function, of the PureCubicRing class.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "<b>Z</b>[&#x221B;" + d + "]";
        String actual = instance.toHTMLString()
                .replace("&#x221b;", "&#x221B;")
                .replace("&#8731;", "&#x221B;");
        assertEquals(expected, actual);
    }

    // TODO: Write toHTMLString() tests for d not squarefree (though still 
    // cubefree), negative d

    /**
     * Test of the toHTMLStringBlackboardBold function, of the PureCubicRing 
     * class.
     */
    @Test
    public void testToHTMLStringBlackboardBold() {
        System.out.println("toHTMLStringBlackboardBold");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "&#x2124;[&#x221B;" + d + "]";
        String actual = instance.toHTMLStringBlackboardBold()
                .replace("&#8484;", "&#x2124;")
                .replace("&#x221b;", "&#x221B;")
                .replace("&#8731;", "&#x221B;");
        assertEquals(expected, actual);
    }

    // TODO: Write toHTMLStringBlackboardBold() tests for d not squarefree 
    // (though still cubefree), negative d

    /**
     * Test of the toFilenameString function, of the PureCubicRing class.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String expected = "ZCBRT" + d;
        String actual = instance.toFilenameString();
        assertEquals(expected, actual);
    }
    
    // TODO: Write toFilenameString() tests for d not squarefree (though still 
    // cubefree), negative d
    
    @Test
    public void testGetRadicand() {
        System.out.println("getRadicand");
        int expected = randomSquarefreeNumber(128);
        PureCubicRing ring = new PureCubicRing(expected);
        int actual = ring.getRadicand();
        String message = "Getting radicand for " + ring.toString();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetCubeRoot() {
        System.out.println("getCubeRoot");
        int d = randomSquarefreeNumber(128);
        PureCubicRing ring = new PureCubicRing(d);
        double expected = Math.cbrt(d);
        double actual = ring.getCubeRoot();
        double delta = 0.00000001;
        String message = "Getting cube root for " + ring.toString();
        assertEquals(message, expected, actual, delta);
    }
    
    // TODO: Write tests for when radicand is not squarefree (but is cubefree)
    
    @Test
    public void testReferentialEquality() {
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        assertEquals(instance, instance);
    }
    
    @Test
    public void testNotEqualsNull() {
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing instance = new PureCubicRing(d);
        String message = instance.toString() + " should not equal null";
        assertNotEquals(message, instance, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing cubicRing = new PureCubicRing(d);
        MockRing testRing = new MockRing(d);
        String message = cubicRing.toString() + " should not equal " 
                + testRing.toString();
        assertNotEquals(message, cubicRing, testRing);
    }
    
    @Test
    public void testNotEqualsDiffParamD() {
        int n = randomNumber(5) + 2;
        int dA = randomSquarefreeNumberMod(n, 9);
        int dB = randomSquarefreeNumberMod(n + 1, 9);
        PureCubicRing ringA = new PureCubicRing(dA);
        PureCubicRing ringB = new PureCubicRing(dB);
        String message = ringA.toString() + " should not equal " 
                + ringB.toString();
        assertNotEquals(message, ringA, ringB);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        PureCubicRing someRing = new PureCubicRing(d);
        PureCubicRing sameRing = new PureCubicRing(d);
        assertEquals(someRing, sameRing);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int initialCapacity = randomNumber(128) + 2;
        Set<PureCubicRing> rings = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            int d = randomSquarefreeNumber(i + 64);
            PureCubicRing ring = new PureCubicRing(d);
            rings.add(ring);
            hashes.add(ring.hashCode());
        }
        int expected = rings.size();
        int actual = hashes.size();
        String message = "Given " + expected 
                + " distinct rings, there should be as many hash codes";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testConstructorTurnsNegativeRadicandPositive() {
        int n = randomNumber(6) + 2;
        int expected = randomSquarefreeNumberMod(n, 9);
        int d = -expected;
        PureCubicRing instance = new PureCubicRing(d);
        int actual = instance.getRadicand();
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorRejectsCubefullNumber() {
        int p = randomPrime(Byte.MAX_VALUE);
        int q = randomPrimeOtherThan(p);
        int d = p * p * p * q;
        String msg = "Constructor should reject d = " + d;
        Throwable t = assertThrows(() -> {
            CubicRing badRing = new PureCubicRing(d);
            System.out.println("Should not have been able to create " 
                    + badRing.toString() + " with parameter d = "+ d);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String numStr = Integer.toString(d);
        String containsMsg = "Exception message should include number " + d;
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
}
