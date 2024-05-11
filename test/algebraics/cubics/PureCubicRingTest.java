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

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the PureCubicRing class.
 * @author Alonso del Arte
 */
public class PureCubicRingTest {
    
    /**
     * Test of isPurelyReal method, of class PureCubicRing.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        PureCubicRing instance = new PureCubicRing(2);
        boolean expResult = false;
        boolean result = instance.isPurelyReal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of discriminant method, of class PureCubicRing.
     */
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
     * Test of getPowerBasis method, of class PureCubicRing.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        PureCubicRing instance = new PureCubicRing(5);
        PowerBasis expResult = null;
        PowerBasis result = instance.getPowerBasis();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toASCIIString method, of class PureCubicRing.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        PureCubicRing instance = new PureCubicRing(6);
        String expResult = "";
        String result = instance.toASCIIString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTeXString method, of class PureCubicRing.
     */
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
