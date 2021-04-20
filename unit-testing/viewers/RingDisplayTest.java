/*
 * Copyright (C) 2021 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package viewers;

import algebraics.IntegerRing;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.RealQuadraticRing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URI;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the RingDisplay class.
 * @author Alonso del Arte
 */
public class RingDisplayTest {
    
    /**
     * Test of setPixelsPerBasicImaginaryInterval method, of class RingDisplay.
     */
    @Test
    public void testSetPixelsPerBasicImaginaryInterval() {
        System.out.println("setPixelsPerBasicImaginaryInterval");
//        RingDisplay instance = null;
//        instance.setPixelsPerBasicImaginaryInterval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPixelsPerUnitInterval method, of class RingDisplay.
     */
    @Test
    public void testSetPixelsPerUnitInterval() {
        System.out.println("setPixelsPerUnitInterval");
//        int pixelLength = 0;
//        RingDisplay instance = null;
//        instance.setPixelsPerUnitInterval(pixelLength);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeRingWindowDimensions method, of class RingDisplay.
     */
    @Test
    public void testChangeRingWindowDimensions() {
        System.out.println("changeRingWindowDimensions");
//        int proposedHorizMax = 0;
//        int proposedVerticMax = 0;
//        RingDisplay instance = null;
//        instance.changeRingWindowDimensions(proposedHorizMax, proposedVerticMax);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeBackgroundColor method, of class RingDisplay.
     */
    @Test
    public void testChangeBackgroundColor() {
        System.out.println("changeBackgroundColor");
//        Color newBackgroundColor = null;
//        RingDisplay instance = null;
//        instance.changeBackgroundColor(newBackgroundColor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeGridColors method, of class RingDisplay.
     */
    @Test
    public void testChangeGridColors() {
        System.out.println("changeGridColors");
//        Color proposedHalfIntegerGridColor = null;
//        Color proposedIntegerGridColor = null;
//        RingDisplay instance = null;
//        instance.changeGridColors(proposedHalfIntegerGridColor, proposedIntegerGridColor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePointColors method, of class RingDisplay.
     */
    @Test
    public void testChangePointColors() {
        System.out.println("changePointColors");
//        Color proposedZeroColor = null;
//        Color proposedUnitColor = null;
//        Color proposedInertPrimeColor = null;
//        Color proposedSplitPrimeColor = null;
//        Color proposedRamifiedPrimeColor = null;
//        RingDisplay instance = null;
//        instance.changePointColors(proposedZeroColor, proposedUnitColor, proposedInertPrimeColor, proposedSplitPrimeColor, proposedRamifiedPrimeColor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeDotRadius method, of class RingDisplay.
     */
    @Test
    public void testChangeDotRadius() {
        System.out.println("changeDotRadius");
//        int radius = 0;
//        RingDisplay instance = null;
//        instance.changeDotRadius(radius);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeZoomStep method, of class RingDisplay.
     */
    @Test
    public void testChangeZoomStep() {
        System.out.println("changeZoomStep");
//        int step = 0;
//        RingDisplay instance = null;
//        instance.changeZoomStep(step);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeZeroCoords method, of class RingDisplay.
     */
    @Test
    public void testChangeZeroCoords() {
        System.out.println("changeZeroCoords");
//        int proposedCoordX = 0;
//        int proposedCoordY = 0;
//        RingDisplay instance = null;
//        instance.changeZeroCoords(proposedCoordX, proposedCoordY);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveDiagramAs method, of class RingDisplay.
     */
    @Test
    public void testSaveDiagramAs() {
        System.out.println("saveDiagramAs");
//        RingDisplay instance = null;
//        instance.saveDiagramAs();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the validateRing procedure, of the RingDisplay class.
     */
    @Test
    public void testValidateRing() {
        System.out.println("validateRing");
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(38);
        RingDisplay display = new RingDisplayImpl(ring);
        try {
            display.validateRing(ring);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred while validating " 
                    + ring.toString();
            fail(msg);
        }
    }
    
    /**
     * Another test of the validateRing procedure, of the RingDisplay class. 
     * Null should cause <code>NullPointerException</code>.
     */
    @Test
    public void testValidateRingRejectsNull() {
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(39);
        RingDisplay display = new RingDisplayImpl(ring);
        try {
            display.validateRing(null);
            String msg = "Validating null should have caused an exception";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Validating null caused NullPointerException");
            String excMsg = npe.getMessage();
            String msg = "Exception message should not be null";
            assert excMsg != null : msg;
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to validate null";
            fail(msg);
        }
    }

    /**
     * Another test of the validateRing procedure, of the RingDisplay class. 
     * Trying to validate a ring of the wrong type should cause <code></code>
     */
    @Test
    public void testValidateRingRejectsWrongType() {
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(39);
        RingDisplay display = new RingDisplayImpl(ring);
        RealQuadraticRing wrongTypeRing = new RealQuadraticRing(39);
        try {
            display.validateRing(wrongTypeRing);
            String msg = "Trying to validate ring of type " 
                    + wrongTypeRing.getClass().getName() 
                    + " where required type is " 
                    + ring.getClass().getName()
                    + " should have caused an exception";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to validate ring of type " 
                    + wrongTypeRing.getClass().getName() 
                    + " where required type is " 
                    + ring.getClass().getName()
                    + " correctly caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            String msg = "Exception message should not be null";
            assert excMsg != null : msg;
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to validate null";
            fail(msg);
        }
    }

    /**
     * Test of the switchToRing procedure, of the RingDisplay class.
     */
    @Test
    public void testSwitchToRing() {
        System.out.println("switchToRing");
        IllDefinedQuadraticRing ring = new IllDefinedQuadraticRing(35);
        RingDisplay display = new RingDisplayImpl(ring);
        display.startRingDisplay();
        IllDefinedQuadraticRing expected = new IllDefinedQuadraticRing(449);
        display.switchToRing(expected);
        IntegerRing actual = display.getRing();
        assertEquals(expected, actual);
    }

    /**
     * Test of updateRingHistory method, of class RingDisplay.
     */
    @Test
    public void testUpdateRingHistory() {
        System.out.println("updateRingHistory");
//        IntegerRing ring = null;
//        RingDisplay instance = null;
//        instance.updateRingHistory(ring);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of previousDiscriminant method, of class RingDisplay.
     */
    @Test
    public void testPreviousDiscriminant() {
        System.out.println("previousDiscriminant");
//        RingDisplay instance = null;
//        instance.previousDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextDiscriminant method, of class RingDisplay.
     */
    @Test
    public void testNextDiscriminant() {
        System.out.println("nextDiscriminant");
//        RingDisplay instance = null;
//        instance.nextDiscriminant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyReadoutsToClipboard method, of class RingDisplay.
     */
    @Test
    public void testCopyReadoutsToClipboard() {
        System.out.println("copyReadoutsToClipboard");
//        RingDisplay instance = null;
//        instance.copyReadoutsToClipboard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyDiagramToClipboard method, of class RingDisplay.
     */
    @Test
    public void testCopyDiagramToClipboard() {
        System.out.println("copyDiagramToClipboard");
//        RingDisplay instance = null;
//        instance.copyDiagramToClipboard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkZoomInOutEnablements method, of class RingDisplay.
     */
    @Test
    public void testCheckZoomInOutEnablements() {
        System.out.println("checkZoomInOutEnablements");
//        RingDisplay instance = null;
//        instance.checkZoomInOutEnablements();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of zoomIn method, of class RingDisplay.
     */
    @Test
    public void testZoomIn() {
        System.out.println("zoomIn");
//        RingDisplay instance = null;
//        instance.zoomIn();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of zoomOut method, of class RingDisplay.
     */
    @Test
    public void testZoomOut() {
        System.out.println("zoomOut");
//        RingDisplay instance = null;
//        instance.zoomOut();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkZoomStepEnablements method, of class RingDisplay.
     */
    @Test
    public void testCheckZoomStepEnablements() {
        System.out.println("checkZoomStepEnablements");
//        RingDisplay instance = null;
//        instance.checkZoomStepEnablements();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of informZoomStepChange method, of class RingDisplay.
     */
    @Test
    public void testInformZoomStepChange() {
        System.out.println("informZoomStepChange");
//        RingDisplay instance = null;
//        instance.informZoomStepChange();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decreaseZoomStep method, of class RingDisplay.
     */
    @Test
    public void testDecreaseZoomStep() {
        System.out.println("decreaseZoomStep");
//        RingDisplay instance = null;
//        instance.decreaseZoomStep();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseZoomStep method, of class RingDisplay.
     */
    @Test
    public void testIncreaseZoomStep() {
        System.out.println("increaseZoomStep");
//        RingDisplay instance = null;
//        instance.increaseZoomStep();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decreaseDotRadius method, of class RingDisplay.
     */
    @Test
    public void testDecreaseDotRadius() {
        System.out.println("decreaseDotRadius");
//        RingDisplay instance = null;
//        instance.decreaseDotRadius();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseDotRadius method, of class RingDisplay.
     */
    @Test
    public void testIncreaseDotRadius() {
        System.out.println("increaseDotRadius");
//        RingDisplay instance = null;
//        instance.increaseDotRadius();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetViewDefaults method, of class RingDisplay.
     */
    @Test
    public void testResetViewDefaults() {
        System.out.println("resetViewDefaults");
//        RingDisplay instance = null;
//        instance.resetViewDefaults();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toggleThetaNotation method, of class RingDisplay.
     */
    @Test
    public void testToggleThetaNotation() {
        System.out.println("toggleThetaNotation");
//        RingDisplay instance = null;
//        instance.toggleThetaNotation();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toggleReadOutsEnabled method, of class RingDisplay.
     */
    @Test
    public void testToggleReadOutsEnabled() {
        System.out.println("toggleReadOutsEnabled");
//        RingDisplay instance = null;
//        instance.toggleReadOutsEnabled();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateBoundaryNumber method, of class RingDisplay.
     */
    @Test
    public void testUpdateBoundaryNumber() {
        System.out.println("updateBoundaryNumber");
//        RingDisplay instance = null;
//        instance.updateBoundaryNumber();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoundaryRe method, of class RingDisplay.
     */
    @Test
    public void testGetBoundaryRe() {
        System.out.println("getBoundaryRe");
//        RingDisplay instance = null;
//        double expResult = 0.0;
//        double result = instance.getBoundaryRe();
//        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoundaryIm method, of class RingDisplay.
     */
    @Test
    public void testGetBoundaryIm() {
        System.out.println("getBoundaryIm");
//        RingDisplay instance = null;
//        double expResult = 0.0;
//        double result = instance.getBoundaryIm();
//        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserManualURL method, of class RingDisplay.
     */
    @Test
    public void testGetUserManualURL() {
        System.out.println("getUserManualURL");
//        RingDisplay instance = null;
//        URI expResult = null;
//        URI result = instance.getUserManualURL();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showUserManual method, of class RingDisplay.
     */
    @Test
    public void testShowUserManual() {
        System.out.println("showUserManual");
//        RingDisplay instance = null;
//        instance.showUserManual();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAboutBoxMessage method, of class RingDisplay.
     */
    @Test
    public void testGetAboutBoxMessage() {
        System.out.println("getAboutBoxMessage");
//        RingDisplay instance = null;
//        String expResult = "";
//        String result = instance.getAboutBoxMessage();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showAboutBox method, of class RingDisplay.
     */
    @Test
    public void testShowAboutBox() {
        System.out.println("showAboutBox");
//        RingDisplay instance = null;
//        instance.showAboutBox();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionPerformed method, of class RingDisplay.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
//        ActionEvent ae = null;
//        RingDisplay instance = null;
//        instance.actionPerformed(ae);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRing method, of class RingDisplay.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        IllDefinedQuadraticRing expected = new IllDefinedQuadraticRing(15);
        RingDisplay display = new RingDisplayImpl(expected);
        IntegerRing actual = display.getRing();
        assertEquals(expected, actual);
    }

    /**
     * Test of setRing method, of class RingDisplay.
     */
    @Test
    public void testSetRing() {
        System.out.println("setRing");
//        IntegerRing ring = null;
//        RingDisplay instance = null;
//        instance.setRing(ring);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpRingFrame method, of class RingDisplay.
     */
    @Test
    public void testSetUpRingFrame() {
        System.out.println("setUpRingFrame");
//        RingDisplay instance = null;
//        instance.setUpRingFrame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startRingDisplay method, of class RingDisplay.
     */
    @Test
    public void testStartRingDisplay() {
        System.out.println("startRingDisplay");
//        RingDisplay instance = null;
//        instance.startRingDisplay();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class RingDisplayImpl extends RingDisplay {

        @Override
        public void setPixelsPerBasicImaginaryInterval() {
        }

        @Override
        public void chooseDiscriminant() {
        }

        @Override
        public void incrementDiscriminant() {
        }

        @Override
        public void decrementDiscriminant() {
        }

        @Override
        public void updateBoundaryNumber() {
        }

        @Override
        public double getBoundaryRe() {
            return 0.0;
        }

        @Override
        public double getBoundaryIm() {
            return 0.0;
        }

        @Override
        public URI getUserManualURL() {
            return null;
        }

        @Override
        public String getAboutBoxMessage() {
            return "THIS IS FOR TESTING PURPOSES ONLY";
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public RingDisplayImpl(IllDefinedQuadraticRing ring) {
            super(ring);
        }

    }
    
}
