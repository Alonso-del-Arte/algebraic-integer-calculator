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
import algebraics.quadratics.ImaginaryQuadraticRing;

//import java.awt.Graphics;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
//import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class ImagQuadRingDisplayTest {
    
    private static final ImaginaryQuadraticRing RING_ZI14 
            = new ImaginaryQuadraticRing(-14);
    
    private ImagQuadRingDisplay ringDisplay;
    
    private final ActionEvent closeEvent = new ActionEvent(this, 
            ActionEvent.ACTION_PERFORMED, "close");
    
    @Before
    public void setUp() {
        this.ringDisplay = new ImagQuadRingDisplay(RING_ZI14);
        this.ringDisplay.startRingDisplay();
    }
    
    @After
    public void tearDown() {
        this.ringDisplay.actionPerformed(this.closeEvent);
    }

    /* (NO TEST YET) *
     * Test of setPixelsPerBasicImaginaryInterval method, of class ImagQuadRingDisplay.
     */
//    (AT)Test
//    public void testSetPixelsPerBasicImaginaryInterval() {
//        System.out.println("setPixelsPerBasicImaginaryInterval");
//        ImagQuadRingDisplay instance = null;
//        instance.setPixelsPerBasicImaginaryInterval();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /* (NO TEST YET) *
     * Test of paintComponent method, of class ImagQuadRingDisplay.
     */
//    (AT)Test
//    public void testPaintComponent() {
//        System.out.println("paintComponent");
//        Graphics g = null;
//        ImagQuadRingDisplay instance = null;
//        instance.paintComponent(g);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /* (NO TEST YET) *
     * Test of mouseMoved method, of class ImagQuadRingDisplay.
     */
//    (AT)Test
//    public void testMouseMoved() {
//        System.out.println("mouseMoved");
//        MouseEvent mauv = null;
//        ImagQuadRingDisplay instance = null;
//        instance.mouseMoved(mauv);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /* (NO TEST YET) *
     * Test of mouseDragged method, of class ImagQuadRingDisplay.
     */
//    (AT)Test
//    public void testMouseDragged() {
//        System.out.println("mouseDragged");
//        MouseEvent mauv = null;
//        ImagQuadRingDisplay instance = null;
//        instance.mouseDragged(mauv);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /* (NO TEST YET) *
     * Test of chooseDiscriminant method, of class ImagQuadRingDisplay.
     */
//    (AT)Test
//    public void testChooseDiscriminant() {
//        System.out.println("chooseDiscriminant");
//        ImagQuadRingDisplay instance = null;
//        instance.chooseDiscriminant();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of incrementDiscriminant method, of class ImagQuadRingDisplay.
     */
    @Test
    public void testIncrementDiscriminant() {
        System.out.println("incrementDiscriminant");
        ImaginaryQuadraticRing expectedRing = new ImaginaryQuadraticRing(-13);
        this.ringDisplay.incrementDiscriminant();
        IntegerRing actualRing = this.ringDisplay.getRing();
        assertEquals(expectedRing, actualRing);
    }

    /**
     * Test of decrementDiscriminant method, of class ImagQuadRingDisplay.
     */
    @Test
    public void testDecrementDiscriminant() {
        System.out.println("decrementDiscriminant");
        ImaginaryQuadraticRing expectedRing = new ImaginaryQuadraticRing(-15);
        this.ringDisplay.decrementDiscriminant();
        IntegerRing actualRing = this.ringDisplay.getRing();
        assertEquals(expectedRing, actualRing);
    }

    /**
     * Test of the copyReadoutsToClipboard procedure, of the ImagQuadRingDisplay 
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
     * Test of the getBoundaryRe function, of the ImagQuadRingDisplay class.
     */
    @Test
    public void testGetBoundaryRe() {
        System.out.println("getBoundaryRe");
        fail("Haven't written test yet");
    }
    
    /**
     * Test of the getBoundaryIm function, of the ImagQuadRingDisplay class.
     */
    @Test
    public void testGetBoundaryIm() {
        System.out.println("getBoundaryIm");
        fail("Haven't written test yet");
    }
    
    /**
     * Test of getUserManualURL method, of class ImagQuadRingDisplay.
     */
    @Test
    public void testGetUserManualURL() {
        System.out.println("getUserManualURL");
        String urlStr = "https://github.com/Alonso-del-Arte/" 
                + "visualization-quadratic-imaginary-rings/blob/master/" 
                + "dist-jar/README.md";
        URI expectedURL = null;
        try {
            expectedURL = new URI(urlStr);
        } catch (URISyntaxException urise) {
            fail(urise.getMessage());
        }
        URI actualURL = this.ringDisplay.getUserManualURL();
        assertEquals(expectedURL, actualURL);
    }

    /**
     * Test of the getAboutBoxMessage function, of the ImagQuadRingDisplay 
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
    
}
