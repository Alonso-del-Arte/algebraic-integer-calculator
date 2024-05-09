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
import junit.framework.TestCase;

/**
 * Tests of the PureCubicRing class.
 * @author Alonso del Arte
 */
public class PureCubicRingTest extends TestCase {
    
    public PureCubicRingTest(String testName) {
        super(testName);
    }

    /**
     * Test of isPurelyReal method, of class PureCubicRing.
     */
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        PureCubicRing instance = new PureCubicRing();
        boolean expResult = false;
        boolean result = instance.isPurelyReal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of discriminant method, of class PureCubicRing.
     */
    public void testDiscriminant() {
        System.out.println("discriminant");
        PureCubicRing instance = new PureCubicRing();
        int expResult = 0;
        int result = instance.discriminant();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPowerBasis method, of class PureCubicRing.
     */
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        PureCubicRing instance = new PureCubicRing();
        PowerBasis expResult = null;
        PowerBasis result = instance.getPowerBasis();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toASCIIString method, of class PureCubicRing.
     */
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        PureCubicRing instance = new PureCubicRing();
        String expResult = "";
        String result = instance.toASCIIString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTeXString method, of class PureCubicRing.
     */
    public void testToTeXString() {
        System.out.println("toTeXString");
        PureCubicRing instance = new PureCubicRing();
        String expResult = "";
        String result = instance.toTeXString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toHTMLString method, of class PureCubicRing.
     */
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        PureCubicRing instance = new PureCubicRing();
        String expResult = "";
        String result = instance.toHTMLString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toFilenameString method, of class PureCubicRing.
     */
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        PureCubicRing instance = new PureCubicRing();
        String expResult = "";
        String result = instance.toFilenameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
