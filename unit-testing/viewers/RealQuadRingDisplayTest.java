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
import algebraics.quadratics.RealQuadraticRing;

import java.awt.Graphics;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the RealQuadRingDisplay class.
 * @author Alonso del Arte
 */
public class RealQuadRingDisplayTest {
    
    private static final RealQuadraticRing RING_OQ13 
            = new RealQuadraticRing(13);
    
    private RealQuadRingDisplay ringDisplay;
    
    private final ActionEvent closeEvent = new ActionEvent(this, 
            ActionEvent.ACTION_PERFORMED, "close");
    
    @Before
    public void setUp() {
        this.ringDisplay = new RealQuadRingDisplay(RING_OQ13);
        this.ringDisplay.startRingDisplay();
    }
    
    @After
    public void tearDown() {
        this.ringDisplay.actionPerformed(this.closeEvent);
    }

    /**
     * Test of the setPixelsPerBasicImaginaryInterval function, of the 
     * RealQuadRingDisplay class.
     */
    @Test
    public void testSetPixelsPerBasicImaginaryInterval() {
        System.out.println("setPixelsPerBasicImaginaryInterval");
//        RealQuadRingDisplay instance = null;
//        instance.setPixelsPerBasicImaginaryInterval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paintComponent method, of class RealQuadRingDisplay.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
//        Graphics g = null;
//        RealQuadRingDisplay instance = null;
//        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of switchToRing method, of class RealQuadRingDisplay.
     */
    @Test
    public void testSwitchToRing() {
        System.out.println("switchToRing");
//        IntegerRing ring = null;
//        RealQuadRingDisplay instance = null;
//        instance.switchToRing(ring);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chooseDiscriminant method, of class RealQuadRingDisplay.
     */
    @Test
    public void testChooseDiscriminant() {
        System.out.println("chooseDiscriminant");
//        RealQuadRingDisplay instance = null;
//        instance.chooseDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementDiscriminant method, of class RealQuadRingDisplay.
     */
    @Test
    public void testIncrementDiscriminant() {
        System.out.println("incrementDiscriminant");
//        RealQuadRingDisplay instance = null;
//        instance.incrementDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrementDiscriminant method, of class RealQuadRingDisplay.
     */
    @Test
    public void testDecrementDiscriminant() {
        System.out.println("decrementDiscriminant");
//        RealQuadRingDisplay instance = null;
//        instance.decrementDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the copyReadoutsToClipboard procedure, of the RealQuadRingDisplay 
     * class.
     */
    @Test
    public void testCopyReadoutsToClipboard() {
        System.out.println("copyReadoutsToClipboard");
        this.ringDisplay.copyReadoutsToClipboard();
        Clipboard clipboard = this.ringDisplay.getToolkit().getSystemClipboard();
        String msg = "System clipboard should have plain text data flavor";
        assert clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor) : msg;
        String expected = "0, Trace: 0, Norm: 0, Polynomial: x";
        try {
            String actual = (String) clipboard.getData(DataFlavor.stringFlavor);
            assertEquals(expected, actual);
        } catch (UnsupportedFlavorException | IOException e) {
            System.out.println("\"" + e.getMessage() + "\"");
            msg = e.getClass().getName() + " should not have occurred";
            fail(msg);
        }
    }

    /**
     * Test of the getBoundaryRe function, of the RealQuadRingDisplay class.
     */
    @Test
    public void testGetBoundaryRe() {
        System.out.println("getBoundaryRe");
//        RealQuadRingDisplay instance = null;
//        double expResult = 0.0;
//        double result = instance.getBoundaryRe();
//        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the getBoundaryIm function, of the RealQuadRingDisplay class. The 
     * imaginary part of the boundary number should always be 0.0.
     */
    @Test
    public void testGetBoundaryIm() {
        System.out.println("getBoundaryIm");
        double expected = 0.0;
        double actual = this.ringDisplay.getBoundaryIm();
        assertEquals(expected, actual, 0.0);
    }

    /**
     * Test of getUserManualURL method, of class RealQuadRingDisplay.
     */
    @Test
    public void testGetUserManualURL() {
        System.out.println("getUserManualURL");
//        RealQuadRingDisplay instance = null;
//        URI expResult = null;
//        URI result = instance.getUserManualURL();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the getAboutBoxMessage function, of the RealQuadRingDisplay 
     * class.
     */
    @Test
    public void testGetAboutBoxMessage() {
        System.out.println("getAboutBoxMessage");
        String aboutBoxText = this.ringDisplay.getAboutBoxMessage();
        String programName = "Imaginary Quadratic Integer Ring Viewer";
        String msg = "About box message should start with program name \"" 
                + programName + "\"";
        assert aboutBoxText.startsWith(programName) : msg;
        char copyrightChar = '\u00A9';
        msg = "About box message should include character '" + copyrightChar 
                + "'";
        assert aboutBoxText.indexOf(copyrightChar) > -1 : msg;
        String authorsName = "Alonso del Arte";
        msg = "About box message should include author's name, " + authorsName;
        assert aboutBoxText.contains(authorsName) : msg;
    }
    
    /**
     * Test of mouseDragged method, of class RealQuadRingDisplay.
     */
    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
//        MouseEvent mauv = null;
//        RealQuadRingDisplay instance = null;
//        instance.mouseDragged(mauv);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseMoved method, of class RealQuadRingDisplay.
     */
    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
//        MouseEvent mauv = null;
//        RealQuadRingDisplay instance = null;
//        instance.mouseMoved(mauv);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
