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
package viewers;

import algebraics.IntegerRing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.net.URI;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class Zeta8RingDisplayTest {
    
    /**
     * Test of setPixelsPerBasicImaginaryInterval method, of class Zeta8RingDisplay.
     */
    @Test
    public void testSetPixelsPerBasicImaginaryInterval() {
        System.out.println("setPixelsPerBasicImaginaryInterval");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.setPixelsPerBasicImaginaryInterval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paintComponent method, of class Zeta8RingDisplay.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        Graphics g = null;
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateRing method, of class Zeta8RingDisplay.
     */
    @Test
    public void testValidateRing() {
        System.out.println("validateRing");
        IntegerRing ring = null;
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.validateRing(ring);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chooseDiscriminant method, of class Zeta8RingDisplay.
     */
    @Test
    public void testChooseDiscriminant() {
        System.out.println("chooseDiscriminant");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.chooseDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementDiscriminant method, of class Zeta8RingDisplay.
     */
    @Test
    public void testIncrementDiscriminant() {
        System.out.println("incrementDiscriminant");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.incrementDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrementDiscriminant method, of class Zeta8RingDisplay.
     */
    @Test
    public void testDecrementDiscriminant() {
        System.out.println("decrementDiscriminant");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.decrementDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateBoundaryNumber method, of class Zeta8RingDisplay.
     */
    @Test
    public void testUpdateBoundaryNumber() {
        System.out.println("updateBoundaryNumber");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.updateBoundaryNumber();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoundaryRe method, of class Zeta8RingDisplay.
     */
    @Test
    public void testGetBoundaryRe() {
        System.out.println("getBoundaryRe");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        double expResult = 0.0;
        double result = instance.getBoundaryRe();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoundaryIm method, of class Zeta8RingDisplay.
     */
    @Test
    public void testGetBoundaryIm() {
        System.out.println("getBoundaryIm");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        double expResult = 0.0;
        double result = instance.getBoundaryIm();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserManualURL method, of class Zeta8RingDisplay.
     */
    @Test
    public void testGetUserManualURL() {
        System.out.println("getUserManualURL");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        URI expResult = null;
        URI result = instance.getUserManualURL();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAboutBoxMessage method, of class Zeta8RingDisplay.
     */
    @Test
    public void testGetAboutBoxMessage() {
        System.out.println("getAboutBoxMessage");
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        String expResult = "";
        String result = instance.getAboutBoxMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseDragged method, of class Zeta8RingDisplay.
     */
    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
        MouseEvent e = null;
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.mouseDragged(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseMoved method, of class Zeta8RingDisplay.
     */
    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
        MouseEvent e = null;
        Zeta8RingDisplay instance = new Zeta8RingDisplay();
        instance.mouseMoved(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
