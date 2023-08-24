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

import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

import java.awt.Graphics;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
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
    
    private void setUpClipboard() {
        StringSelection ss 
                = new StringSelection(RingDisplayTest.TEST_CLIPBOARD_TEXT);
        this.ringDisplay.getToolkit().getSystemClipboard().setContents(ss, ss);
    }
    
    /**
     * Test of the setPixelsPerBasicImaginaryInterval procedure, of the 
     * RealQuadRingDisplay class. As long as this call doesn't cause an 
     * exception, the test will pass.
     */
    @Test
    public void testSetPixelsPerBasicImaginaryInterval() {
        System.out.println("setPixelsPerBasicImaginaryInterval");
        this.ringDisplay.setPixelsPerBasicImaginaryInterval();
    }

    /**
     * Test of paintComponent method, of class RealQuadRingDisplay.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        fail("Haven't written test yet");
    }
    
    /**
     * Test of the getFundamentalUnit function, of the RealQuadRingDisplay 
     * class.
     */
    @Test
    public void testGetFundamentalUnit() {
        System.out.println("getFundamentalUnit");
        RealQuadraticInteger expected = new RealQuadraticInteger(3, 1, 
                RING_OQ13, 2);
        AlgebraicInteger actual = this.ringDisplay.getFundamentalUnit();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the validateRing procedure, of the RealQuadRingDisplay class.
     */
    @Test
    public void testValidateRing() {
        System.out.println("validateRing");
        RealQuadraticRing ring = new RealQuadraticRing(499);
        try {
            this.ringDisplay.validateRing(ring);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred trying to validate " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    /**
     * Another test of the validateRing procedure, of the RealQuadRingDisplay 
     * class. If the ring to switch to is not of type RealQuadraticRing, it 
     * should be rejected.
     */
    @Test
    public void testValidateRingRejectsOnType() {
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(499);
        try {
            this.ringDisplay.validateRing(ring);
            String msg = "Should not have validated " + ring.toString() 
                    + ", which is of type " + ring.getClass().getName() 
                    + ", not " + RealQuadraticRing.class.getName();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to validate " + ring.toASCIIString() 
                    + ", which is of type " + ring.getClass().getName() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to validate " 
                    + ring.toString() + ", which is of type " 
                    + ring.getClass().getName();
            fail(msg);
        }
    }

    /**
     * Another test of the validateRing procedure, of the RealQuadRingDisplay 
     * class. If the ring to switch to null, it should be rejected.
     */
    @Test
    public void testValidateRingRejectsNull() {
        try {
            this.ringDisplay.validateRing(null);
            String msg = "Should not have validated null ring";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("NullPointerException is correct for null ring");
            String excMsg = npe.getMessage();
            String msg = "Exception message must not be null";
            assert excMsg != null : msg;
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for validate null ring attempt"; 
            fail(msg);
        }
    }

    /**
     * Test of the switchToRing procedure, of the RealQuadRingDisplay class.
     */@org.junit.Ignore
    @Test
    public void testSwitchToRing() {
        System.out.println("switchToRing");
        RealQuadraticRing expected = new RealQuadraticRing(247);
        this.ringDisplay.switchToRing(expected);
        IntegerRing actual = this.ringDisplay.getRing();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the switchToRing procedure, of the RealQuadRingDisplay 
     * class. If switching to a ring for which the fundamental unit can readily 
     * be found, such as for example <b>Z</b>[&radic;2] (the fundamental unit of 
     * which is 1 + &radic;2), the fundamental unit property should be updated 
     * accordingly.
     */
    @Test
    public void testSwitchToRingChangesUnitIfFound() {
        RealQuadraticRing ring = new RealQuadraticRing(2);
        this.ringDisplay.switchToRing(ring);
        RealQuadraticInteger expected = new RealQuadraticInteger(1, 1, ring);
        AlgebraicInteger actual = this.ringDisplay.getFundamentalUnit();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the switchToRing procedure, of the RealQuadRingDisplay 
     * class.
     */
    @Test
    public void testSwitchToRingRejectsWrongTypeRing() {
        IllDefinedQuadraticRing badRing = new IllDefinedQuadraticRing(13);
        try {
            this.ringDisplay.switchToRing(badRing);
            String msg = "Trying to switch to ring of type " 
                    + badRing.getClass().getName() 
                    + " should have caused an exception";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to switch to ring of type " 
                    + badRing.getClass().getName() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + "is wrong exception to throw to switch to ring of type " 
                    + badRing.getClass().getName();
            fail(msg);
        }
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
    
    @Test
    public void testCanNotDecrementToOQSqrt1() {
        RealQuadraticRing expected = new RealQuadraticRing(2);
        this.ringDisplay.switchToRing(expected);
        try {
            this.ringDisplay.decrementDiscriminant();
            IntegerRing actual = this.ringDisplay.getRing();
            String msg = "Should not have been able to decrement from " 
                    + expected.toString();
            assertEquals(msg, expected, actual);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred for trying to decrement from " 
                    + expected.toString();
            fail(msg);
        }
    }

    /**
     * Test of the copyReadoutsToClipboard procedure, of the RealQuadRingDisplay 
     * class.
     */
    @Test
    public void testCopyReadoutsToClipboard() {
        System.out.println("copyReadoutsToClipboard");
        this.setUpClipboard();
        this.ringDisplay.copyReadoutsToClipboard();
        Clipboard clipboard = this.ringDisplay.getToolkit().getSystemClipboard();
        String msg = "System clipboard should have plain text data flavor";
        assert clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor) : msg;
        RealQuadraticInteger unit = new RealQuadraticInteger(3, 1, RING_OQ13, 2);
        String expected 
                = "0, Trace: 0, Norm: 0, Polynomial: x, Fundamental unit: " 
                + unit.toString();
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
        double expected = 16.0;
        double actual = this.ringDisplay.getBoundaryRe();
        assertEquals(expected, actual, RingDisplayTest.TEST_DELTA);
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
        assertEquals(expected, actual, RingDisplayTest.TEST_DELTA);
    }
    
    @Test
    public void testZoomInDecreasesBoundaryRe() {
        double initial = this.ringDisplay.getBoundaryRe();
        this.ringDisplay.zoomIn();
        double updated = this.ringDisplay.getBoundaryRe();
        String msg = "Zooming in should decrease boundaryRe (from " + initial 
                + " to " + updated + ")";
        assert initial > updated : msg;
    }

    @Test
    public void testZoomOutIncreasesBoundaryRe() {
        double initial = this.ringDisplay.getBoundaryRe();
        this.ringDisplay.zoomOut();
        double updated = this.ringDisplay.getBoundaryRe();
        String msg = "Zooming out should increase boundaryRe (from " + initial 
                + " to " + updated + ")";
        assert initial < updated : msg;
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
        String programName = "Real Quadratic Integer Ring Viewer";
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
    
    @After
    public void tearDown() {
        this.ringDisplay.actionPerformed(this.closeEvent);
    }

}
