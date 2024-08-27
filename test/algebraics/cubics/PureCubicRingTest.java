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
package algebraics.cubics;

import arithmetic.PowerBasis;

import static calculators.EratosthenesSieve.randomPrime;
import static calculators.EratosthenesSieve.randomPrimeOtherThan;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import fractions.Fraction;

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

    /**
     * Test of toTeXString method, of class PureCubicRing.
     */
    @org.junit.Ignore
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        PureCubicRing instance = new PureCubicRing(7);
        String expResult = "";
        String result = instance.toTeXString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toHTMLString method, of class PureCubicRing.
     */
    @org.junit.Ignore
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        PureCubicRing instance = new PureCubicRing(10);
        String expResult = "";
        String result = instance.toHTMLString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toFilenameString method, of class PureCubicRing.
     */
    @org.junit.Ignore
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        PureCubicRing instance = new PureCubicRing(11);
        String expResult = "";
        String result = instance.toFilenameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        System.out.println("\"" + excMsg + "\"");
    }
    
}
