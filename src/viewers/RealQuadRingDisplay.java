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
package viewers;

import algebraics.IntegerRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import cacheops.LRUCache;
import calculators.RealQuadResultsGrouping;

import static calculators.NumberTheoreticFunctionsCalculator.fieldClassNumber;
import static viewers.RingDisplay.PURELY_REAL_RING_CANVAS_DEFAULT_VERTIC_MAX;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.JOptionPane;
import static calculators.NumberTheoreticFunctionsCalculator.isSquarefree;

/**
 * A Swing component in which to display diagrams of prime numbers in various 
 * real quadratic integer rings.
 * @author Alonso del Arte
 */
public final class RealQuadRingDisplay extends RingDisplay {
    
    public static final int DEFAULT_RING_D = 2;
    
    /**
     * The maximum value of <i>d</i> that will be allowed for choosing the ring 
     * <b>Z</b>[&radic<i>d</i>] or <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub>. 
     * This was originally 102, a provisional value while I worked out the 
     * problem with the fundamental unit function (it didn't work correctly for 
     * <i>d</i> = 103).
     */
    public static final int MAXIMUM_RING_D = 8191;
    
    public static final int SPECIFIC_PREFERRED_DOT_RADIUS = 1;
    
    /**
     * Gives the class number of the currently displayed ring. That is unless 
     * there was a problem calculating the class number, in which case this will 
     * be set to 2, as the presumption is that the ring is not an unique 
     * factorization domain (UFD).
     */
    private int classNumber;
    
    private RealQuadraticInteger diagRingMainUnit;
    
    /**
     * The number 1 + 0&radic;<i>d</i>.
     */
    private RealQuadraticInteger diagRingOne;
    
    private final LRUCache<RealQuadraticRing, RealQuadResultsGrouping> cache 
            = new LRUCache<RealQuadraticRing, RealQuadResultsGrouping>(32) {
                
        @Override
        protected RealQuadResultsGrouping create(RealQuadraticRing name) {
            return new RealQuadResultsGrouping(name);
        }
        
    };
    
    private RealQuadResultsGrouping resGroup;
    
    HashSet<RealQuadraticInteger> inertials;
    
    Set<RealQuadraticInteger> splitteds;
    
    HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> splitters;
    
    Set<RealQuadraticInteger> ramifieds;
    
    HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> ramifiers;
    
    private static final String MANUAL_URL_TOP_LEVEL = "https://github.com/";
    
    private static final String MANUAL_URL_SUBDIRS 
            = "Alonso-del-Arte/algebraic-integer-calculator/blob/master/dist-jar/";
    
    private static final String MANUAL_URL_NAME = "MissingManual.md";
    
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
            = "\u00A9 2021 Alonso del Arte_";
    
    private void drawOneLine(Graphics g, int coordX) {
        g.fillRect(coordX - this.dotRadius, 0, this.dotDiameter, 
                this.ringCanvasVerticMax);
    }
    
    private void outlineOneLine(Graphics g, int coordX) {
        g.drawRect(coordX - this.dotRadius, 0, this.dotDiameter, 
                this.ringCanvasVerticMax);
    }
    
    /**
     * Draws two lines, corresponding to the specified real quadratic integer 
     * and its additive inverse. It is assumed the caller has already specified 
     * the appropriate color on the passed-in <code>Graphics</code> object.
     * @param g The <code>Graphics</code> object supplied by the caller.
     * @param x The real quadratic integer to draw lines for. For example, 19 + 
     * 6&radic;10.
     */
    private void drawTwoLines(Graphics g, QuadraticInteger x) {
        double distance = x.getRealPartNumeric() * this.pixelsPerUnitInterval;
        int coordAdjust = (int) Math.round(distance);
        int coordX = this.zeroCoordX + coordAdjust;
        int coordNegativeX = this.zeroCoordX - coordAdjust;
        this.drawOneLine(g, coordX);
        this.drawOneLine(g, coordNegativeX);
    }
    
    private void outlineTwoLines(Graphics g, QuadraticInteger x) {
        double distance = x.getRealPartNumeric() * this.pixelsPerUnitInterval;
        int coordAdjust = (int) Math.round(distance);
        int coordX = this.zeroCoordX + coordAdjust;
        int coordNegativeX = this.zeroCoordX - coordAdjust;
        this.outlineOneLine(g, coordX);
        this.outlineOneLine(g, coordNegativeX);
    }
    
    private void drawFourLines(Graphics g, RealQuadraticInteger x) {
        this.drawTwoLines(g, x);
        this.drawTwoLines(g, x.conjugate());
    }
    
    private void outlineFourLines(Graphics g, RealQuadraticInteger x) {
        this.outlineTwoLines(g, x);
        this.outlineTwoLines(g, x.conjugate());
    }
    
    private void drawLinesMultByUnit(Graphics g, RealQuadraticInteger x) {
        while (x.abs() <= this.boundaryRe) {
            this.drawFourLines(g, x);
            x = (RealQuadraticInteger) x.times(this.diagRingMainUnit);
        }
    }
    
    private void outlineLinesMultByUnit(Graphics g, RealQuadraticInteger x) {
        while (x.abs() <= this.boundaryRe) {
            this.outlineFourLines(g, x);
            x = (RealQuadraticInteger) x.times(this.diagRingMainUnit);
        }
    }
    
    private void drawUnits(Graphics g) {
        g.setColor(this.unitColor);
        this.drawTwoLines(g, this.diagRingOne);
        if (this.unitAvailable) {
            this.drawLinesMultByUnit(g, this.diagRingMainUnit);
        }
    }
    
    private void drawInerts(Graphics g) {
        g.setColor(this.inertPrimeColor);
        this.inertials.forEach((inert) -> {
            this.drawTwoLines(g, inert);
            if (this.unitAvailable) {
                this.drawLinesMultByUnit(g, inert);
            }
        });
    }
    
    private void drawRamifieds(Graphics g) {
        Optional<RealQuadraticInteger> ramifierHolder;
        RealQuadraticInteger ramifier, ramMult;
        g.setColor(this.ramifiedPrimeColor);
        for (RealQuadraticInteger ramified : this.ramifieds) {
            ramifierHolder = this.ramifiers.get(ramified);
            if (ramifierHolder.isPresent()) {
                ramifier = ramifierHolder.get();
                this.drawFourLines(g, ramifier);
                if (this.unitAvailable) {
                    ramMult = (RealQuadraticInteger) 
                            ramifier.times(this.diagRingMainUnit);
                    this.drawLinesMultByUnit(g, ramMult);
                }
                this.drawTwoLines(g, ramified);
                if (this.unitAvailable) {
                    ramMult = (RealQuadraticInteger) 
                            ramified.times(this.diagRingMainUnit);
                    this.drawLinesMultByUnit(g, ramMult);
                }
            } else {
                if (this.classNumber == 1) {
                    this.drawTwoLines(g, ramified);
                    if (this.unitAvailable) {
                        ramMult = (RealQuadraticInteger) ramified
                                .times(this.diagRingMainUnit);
                        this.drawLinesMultByUnit(g, ramMult);
                    }
                } else {
                    this.outlineTwoLines(g, ramified);
                    if (this.unitAvailable) {
                        ramMult = (RealQuadraticInteger) ramified
                                .times(this.diagRingMainUnit);
                        this.outlineLinesMultByUnit(g, ramMult);
                    }
                }
            }
        }
    }
    
    private void drawSplits(Graphics g) {
        Optional<RealQuadraticInteger> splitterHolder;
        RealQuadraticInteger splitter, splitMult;
        g.setColor(this.splitPrimeColor);
        for (RealQuadraticInteger splitted : this.splitteds) {
            splitterHolder = this.splitters.get(splitted);
            if (splitterHolder.isPresent()) {
                splitter = splitterHolder.get();
                this.drawFourLines(g, splitter);
                if (this.unitAvailable) {
                    splitMult = (RealQuadraticInteger) 
                            splitter.times(this.diagRingMainUnit);
                    this.drawLinesMultByUnit(g, splitMult);
                }
                this.drawTwoLines(g, splitted);
                if (this.unitAvailable) {
                    splitMult = (RealQuadraticInteger) 
                            splitted.times(this.diagRingMainUnit);
                    this.drawLinesMultByUnit(g, splitMult);
                }
            } else {
                if (this.classNumber == 1) {
                    this.drawTwoLines(g, splitted);
                    if (this.unitAvailable) {
                        splitMult = (RealQuadraticInteger) splitted
                                .times(this.diagRingMainUnit);
                        this.drawLinesMultByUnit(g, splitMult);
                    }
                } else {
                    this.outlineTwoLines(g, splitted);
                    if (this.unitAvailable) {
                        splitMult = (RealQuadraticInteger) splitted
                                .times(this.diagRingMainUnit);
                        this.outlineLinesMultByUnit(g, splitMult);
                    }
                }
            }
        }
    }

    @Override
    void setPixelsPerBasicImaginaryInterval() {
        // Not applicable
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.zeroColor);
        this.drawOneLine(g, this.zeroCoordX);
        this.drawUnits(g);
        this.drawRamifieds(g);
        this.drawInerts(g);
        this.drawSplits(g);
    }
    
    /**
     * Calls {@link RingDisplay#findUnit()}, and if that sets 
     * {@link RingDisplay#unitAvailable} to true, copies the unit to a private 
     * field conveniently cast to the 
     * {@link algebraics.quadratics.RealQuadraticInteger}.
     */
    @Override
    void findUnit() {
        this.diagRingOne = new RealQuadraticInteger(1, 0, 
                (RealQuadraticRing) this.diagramRing);
        super.findUnit();
        if (this.unitAvailable) {
            this.diagRingMainUnit = (RealQuadraticInteger) this.fundamentalUnit;
            try {
                this.classNumber = fieldClassNumber(this.diagramRing);
            } catch (ArithmeticException ae) {
                System.err.println(ae.getMessage());
                this.classNumber = 2;
            }
        }
    }
    
    private void fillPrimeLists() {
        this.inertials = this.resGroup.inerts();
        this.splitters = this.resGroup.splits();
        this.splitteds = this.splitters.keySet();
        this.ramifiers = this.resGroup.ramifieds();
        this.ramifieds = this.ramifiers.keySet();
    }
    
    @Override
    void validateRing(IntegerRing ring) {
        super.validateRing(ring);
        this.resGroup = this.cache.forName((RealQuadraticRing) ring);
        this.fillPrimeLists();
    }
    
    @Override
    public void chooseDiscriminant() {
        int currDiscr = ((QuadraticRing) this.diagramRing).getRadicand();
        String discrString = Integer.toString(currDiscr);
        String userChoice = (String) JOptionPane.showInputDialog(this.ringFrame, 
                "Please enter a positive, squarefree integer:", discrString);
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
        while (!isSquarefree(discr) 
                && discr < MAXIMUM_RING_D) {
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
            RealQuadraticRing ring = new RealQuadraticRing(discr);
            this.switchToRing(ring);
            this.updateRingHistory(ring);
        }
    }

    @Override
    public void incrementDiscriminant() {
        int discr = ((QuadraticRing) this.diagramRing).getRadicand() + 1;
        while (!isSquarefree(discr) 
                && discr < MAXIMUM_RING_D) {
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
        while (!isSquarefree(discr) && discr > 2) {
            discr--;
        }
        if (discr == 2) {
            this.decreaseDMenuItem.setEnabled(false);
        }
        if (discr == (MAXIMUM_RING_D - 1)) {
            this.increaseDMenuItem.setEnabled(true);
        }
        if (discr > 1) {
            RealQuadraticRing ring = new RealQuadraticRing(discr);
            this.switchToRing(ring);
            this.updateRingHistory(ring);
        }
    }

    /**
     * Updates the <code>boundaryRe</code> field. Since the 
     * <code>boundaryIm</code> field is always 0, there is no need to do 
     * anything about it.
     */
    @Override
    void updateBoundaryNumber() {
        double pixelLength = this.ringCanvasHorizMax - this.zeroCoordX;
        this.boundaryRe = pixelLength / this.pixelsPerUnitInterval;
    }
    
    /**
     * Retries the real part of the boundary number. This is the farthest right 
     * number that can be currently displayed. Negate for the farthest left 
     * number.
     * @return A positive floating point number. For example, 16.0.
     */
    @Override
    double getBoundaryRe() {
        return this.boundaryRe;
    }

    /**
     * Retrieves the imaginary part of the boundary number. A correct but not 
     * very useful number.
     * @return Always 0, since this <code>RingDisplay</code> implementation only 
     * deals with purely real numbers.
     */
    @Override
    double getBoundaryIm() {
        return 0.0;
    }
    
    @Override
    int getPreferredDotRadius() {
        return SPECIFIC_PREFERRED_DOT_RADIUS;
    }

    @Override
    public URI getUserManualURL() {
        String urlStr = MANUAL_URL_TOP_LEVEL + MANUAL_URL_SUBDIRS 
                + MANUAL_URL_NAME;
        try {
            URI url = new URI(urlStr);
            return url;
        } catch (URISyntaxException urise) {
            throw new RuntimeException(urise);
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
        this.dotRadius = SPECIFIC_PREFERRED_DOT_RADIUS;
        this.dotDiameter = 2 * this.dotRadius;
        this.unitApplicable = true;
        this.findUnit();
        this.mouseAlgInt = new RealQuadraticInteger(0, 0, ring);
        this.diagRingOne = new RealQuadraticInteger(1, 0, ring);
        this.findUnit();
        this.resGroup = this.cache.forName(ring);
        this.fillPrimeLists();
    }
    
}
