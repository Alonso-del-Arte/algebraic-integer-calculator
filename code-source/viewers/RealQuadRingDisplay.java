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
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import static calculators.NumberTheoreticFunctionsCalculator.*;
import static viewers.RingDisplay.PURELY_REAL_RING_CANVAS_DEFAULT_VERTIC_MAX;

/**
 * A Swing component in which to display diagrams of prime numbers in various 
 * real quadratic integer rings.
 * @author Alonso del Arte
 */
public class RealQuadRingDisplay extends RingDisplay {
    
    public static final int DEFAULT_RING_D = 2;
    
    /**
     * This is a PROVISIONAL value while I work out the problem with the 
     * fundamental unit function (it doesn't work correctly for d = 103).
     */
    public static final int MAXIMUM_RING_D = 102;
    
    private RealQuadraticInteger diagRingMainUnit, diagRingOne;
    
    /**
     * Tells whether the fundamental unit of the currently displayed ring is 
     * available. It might not be if the calculation took too long or if the 
     * number is beyond the range of 
     * {@link algebraics.quadratics.RealQuadraticInteger} to represent.
     */
    private boolean unitAvailable = false;
    
    /**
     * The name of the program to show in the About box.
     */
    private static final String ABOUT_BOX_PROGRAM_NAME 
            = "Real Quadratic Integer Ring Viewer";
    
    /**
     * The version identification to show in the About box.
     */
    private static final String ABOUT_BOX_VERSION_ID = "Version 0.1";
    
    /**
     * The copyright notice to show in the About box.
     */
    private static final String ABOUT_BOX_COPYRIGHT_NOTICE 
            = "\u00A9 2021 Alonso del Arte";
    
    /**
     * Draws two lines, corresponding to the specified real quadratic integer 
     * and its conjugate. It is assumed the caller has already specified the 
     * appropriate color on the passed in <code>Graphics</code> object.
     * @param g The <code>Graphics</code> object supplied by the caller.
     * @param x The real quadratic integer to draw lines for. For example, 19 + 
     * 6&radic;10.
     */
    private void drawTwoLines(Graphics g, QuadraticInteger x) {
        double distance = x.getRealPartNumeric() * this.pixelsPerUnitInterval;
        int coordAdjust = (int) Math.round(distance);
        int coordX = this.zeroCoordX + coordAdjust;
        int coordNegativeX = this.zeroCoordX - coordAdjust;
        g.drawLine(coordX, 0, coordX, this.ringCanvasVerticMax);
        g.drawLine(coordNegativeX, 0, coordNegativeX, this.ringCanvasVerticMax);
    }
    
    private void drawFourLines(Graphics g, RealQuadraticInteger x) {
        this.drawTwoLines(g, x);
        this.drawTwoLines(g, x.conjugate());
    }
    
    private void drawLinesMultByUnit(Graphics g, Color c, RealQuadraticInteger x) {
        g.setColor(c);
        this.drawFourLines(g, x);
    }
    
    private void drawUnits(Graphics g) {
        this.drawLinesMultByUnit(g, this.unitColor, this.diagRingOne);
    }
    
    private void drawPlainInts(Graphics g) {
        //
    }
    
    private void drawRamifieds(Graphics g) {
        //
    }
    
    private void drawSplits(Graphics g) {
        //
    }

    @Override
    protected void setPixelsPerBasicImaginaryInterval() {
        // Not applicable
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawSplits(g);
        this.drawRamifieds(g);
        this.drawPlainInts(g);
        this.drawUnits(g);
    }
    
    private void findUnit() {
        try {
            this.diagRingMainUnit 
                    = (RealQuadraticInteger) fundamentalUnit(this.diagramRing);
            this.unitAvailable = true;
        } catch (ArithmeticException ae) {
            System.err.println(ae.getMessage());
            this.unitAvailable = false;
        }
    }
    
    @Override
    protected void switchToRing(IntegerRing ring) {
        this.ringFrame.setTitle("Ring Diagram for " + ring.toString());
        this.setRing(ring);
        this.diagRingOne = new RealQuadraticInteger(1, 0, 
                (RealQuadraticRing) this.diagramRing);
        this.findUnit();
        this.repaint();
    }

    @Override
    public void chooseDiscriminant() {
        int currDiscr = ((QuadraticRing) this.diagramRing).getRadicand();
        String discrString = Integer.toString(currDiscr);
        String userChoice = (String) JOptionPane.showInputDialog(this.ringFrame, "Please enter a positive, squarefree integer:", discrString);
        int discr;
        boolean repaintNeeded;
        try {
            discr = Integer.parseInt(userChoice);
        } catch (NumberFormatException nfe) {
            System.err.println(nfe.getMessage());
            discr = currDiscr;
        }
        if (discr < 0) {
            discr *= -1;
        }
        if (discr > MAXIMUM_RING_D) {
            discr = MAXIMUM_RING_D;
        }
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(discr) && discr < MAXIMUM_RING_D) {
            discr++;
        }
        repaintNeeded = (discr != currDiscr);
        if (repaintNeeded) {
            if (discr == 2) {
                this.decreaseDMenuItem.setEnabled(false);
            }
            if (discr > 2 && !this.decreaseDMenuItem.isEnabled()) {
                this.decreaseDMenuItem.setEnabled(true);
            }
            if (discr == MAXIMUM_RING_D) {
                this.increaseDMenuItem.setEnabled(false);
            }
            if (discr < MAXIMUM_RING_D && !this.increaseDMenuItem.isEnabled()) {
                this.increaseDMenuItem.setEnabled(true);
            }
            QuadraticRing ring = new RealQuadraticRing(discr);
            this.switchToRing(ring);
            this.updateRingHistory(ring);
        }
    }

    @Override
    public void incrementDiscriminant() {
        int discr = ((QuadraticRing) this.diagramRing).getRadicand() + 1;
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(discr) && discr < MAXIMUM_RING_D) {
            discr++;
        }
        if (discr == MAXIMUM_RING_D) {
            this.increaseDMenuItem.setEnabled(false);
        }
        if (discr == 3) {
            this.decreaseDMenuItem.setEnabled(true);
        }
        RealQuadraticRing ring = new RealQuadraticRing(discr);
        this.switchToRing(ring);
        this.updateRingHistory(ring);
    }

    @Override
    public void decrementDiscriminant() {
        int discr = ((QuadraticRing) this.diagramRing).getRadicand() - 1;
        while (!NumberTheoreticFunctionsCalculator.isSquareFree(discr) && discr > (Integer.MIN_VALUE + 1)) {
            discr--;
        }
        if (discr == 2) {
            this.decreaseDMenuItem.setEnabled(false);
        }
        if (discr == (MAXIMUM_RING_D - 1)) {
            this.increaseDMenuItem.setEnabled(true);
        }
        RealQuadraticRing ring = new RealQuadraticRing(discr);
        this.switchToRing(ring);
        this.updateRingHistory(ring);
    }

    @Override
    public URI getUserManualURL() {
        String urlStr = "https://github.com/Alonso-del-Arte/algebraic-integer-calculator/blob/master/dist-jar/MissingManual.md";
        try {
            URI url = new URI(urlStr);
            return url;
        } catch (URISyntaxException urise) {
            throw new RuntimeException(urise); // Rethrow wrapped in a RuntimeException
        }
    }

    @Override
    public String getAboutBoxMessage() {
        return ABOUT_BOX_PROGRAM_NAME + "\n" + ABOUT_BOX_VERSION_ID + "\n" 
                + ABOUT_BOX_COPYRIGHT_NOTICE;
    }

    @Override
    public void mouseDragged(MouseEvent mauv) {
        // IMPLEMENTATION PLACEHOLDER
    }

    @Override
    public void mouseMoved(MouseEvent mauv) {
        // IMPLEMENTATION PLACEHOLDER
    }
    
    public RealQuadRingDisplay(RealQuadraticRing ring) {
        super(ring);
        this.ringCanvasVerticMax = PURELY_REAL_RING_CANVAS_DEFAULT_VERTIC_MAX;
        this.mouseAlgInt = new RealQuadraticInteger(0, 0, ring);
        this.findUnit();
    }
    
}
