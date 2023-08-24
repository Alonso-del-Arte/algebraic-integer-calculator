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
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import algebraics.quartics.Zeta8Integer;
import algebraics.quartics.Zeta8Ring;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import static calculators.NumberTheoreticFunctionsCalculator.*;

/**
 * WORK IN PROGRESS.
 * @author Alonso del Arte
 */
public final class Zeta8RingDisplay extends RingDisplay {
    
    private double thresholdRe, thresholdIm;
    
    /**
     * The ring <b>Z</b>[&radic;&minus;2].
     */
    public static final ImaginaryQuadraticRing RING_ZI2 
            = new ImaginaryQuadraticRing(-2);
    
    /**
     * The ring <b>Z</b>[<i>i</i>], the ring of Gaussian integers.
     */
    public static final ImaginaryQuadraticRing RING_GAUSSIAN 
            = new ImaginaryQuadraticRing(-1);
    
    /**
     * The ring <b>Z</b>[&radic;2].
     */
    public static final RealQuadraticRing RING_Z2 
            = new RealQuadraticRing(2);
    
    public static final RealQuadraticInteger UNIT_Z2 
            = new RealQuadraticInteger(1, 1, RING_Z2);
    
    public static final RealQuadraticInteger SQRT_2 
            = new RealQuadraticInteger(0, 1, RING_Z2);
    
    public static final Zeta8Integer UNIT_ZETA8 = new Zeta8Integer(0, 1, 0, 0);
    
    public static final Zeta8Integer SILVER_RATIO 
            = Zeta8Integer.convertFromQuadraticInteger(UNIT_Z2);
    
    public static final Zeta8Integer SILVER_RATIO_CONJUGATE 
            = Zeta8Integer.convertFromQuadraticInteger(UNIT_Z2.conjugate());
    
    /**
     * Draws eight dots corresponding to the algebraic integer <i>z</i> 
     * multiplied by powers of (&radic;2)/2 + (&radic;&minus;2)/2.
     * @param g A Graphics object that gets passed around.
     * @param c The Color to use for the eight dots.
     * @param z An algebraic integer from <b>Q</b>(&zeta;<sub>8</sub>), 
     * preferably a nonzero number. It may be 0, but that would probably just be 
     * a waste of a couple of computer clock cycles.
     */
    private void drawEightDots(Graphics g, Color c, Zeta8Integer z) {
        g.setColor(c);
        Zeta8Integer currNum = z;
        double currRe, currIm;
        int x, y;
        for (int i = 0; i < 8; i++) {
            currRe = currNum.getRealPartNumeric();
            currIm = currNum.getImagPartNumeric();
            x = (int) Math.round(currRe * this.pixelsPerUnitInterval);
            y = (int) Math.round(currIm * this.pixelsPerUnitInterval);
            x += this.zeroCoordX;
            y += this.zeroCoordY;
            g.fillOval(x - this.dotRadius, y - this.dotRadius, this.dotDiameter, 
                    this.dotDiameter);
            currNum = currNum.times(UNIT_ZETA8);
        }
    }
    
    private void drawUnits(Graphics g) {
        Zeta8Integer currUnit = new Zeta8Integer(1, 0, 0, 0);
        this.drawEightDots(g, this.unitColor, currUnit);
        Zeta8Integer currUnitConjugate = new Zeta8Integer(1, 0, 0, 0);
        boolean withinBoundaries;
        do {
            currUnit = currUnit.times(SILVER_RATIO);
            this.drawEightDots(g, this.unitColor, currUnit);
            currUnitConjugate = currUnitConjugate.times(SILVER_RATIO_CONJUGATE);
            this.drawEightDots(g, this.unitColor, currUnitConjugate);
            withinBoundaries 
                    = (currUnit.getRealPartNumeric() < this.thresholdRe);
        } while (withinBoundaries);
    }
    
    private void drawPlainInts(Graphics g) {
        int currInt = 1;
        Zeta8Integer plainInt;
        boolean withinBoundaries = true;
        while (withinBoundaries) {
            currInt += 2;
            if (isPrime(currInt)) {
                plainInt = new Zeta8Integer(currInt, 0, 0, 0);
                this.drawEightDots(g, this.splitPrimeColor, plainInt);
            }
            withinBoundaries = (currInt < this.thresholdRe);
        }
    }
    
    private void drawRamifieds(Graphics g) {
        Zeta8Integer currRam = new Zeta8Integer(1, -1, 0, 0);
        this.drawEightDots(g, this.ramifiedPrimeColor, currRam);
        Zeta8Integer currRamConjugate = new Zeta8Integer(1, 1, 0, 0);
        boolean withinBoundaries;
        do {
            currRam = currRam.times(SILVER_RATIO);
            this.drawEightDots(g, this.ramifiedPrimeColor, currRam);
            currRamConjugate = currRamConjugate.times(SILVER_RATIO_CONJUGATE);
            this.drawEightDots(g, this.ramifiedPrimeColor, currRamConjugate);
            withinBoundaries 
                    = (currRam.getRealPartNumeric() < this.thresholdRe);
        } while (withinBoundaries);
        currRam = Zeta8Integer.convertFromQuadraticInteger(SQRT_2);
        this.drawEightDots(g, this.ramifiedPrimeMidDegreeColor, currRam);
        currRamConjugate = currRam;
//        boolean withinBoundaries;
        do {
            currRam = currRam.times(SILVER_RATIO);
            this.drawEightDots(g, this.ramifiedPrimeMidDegreeColor, currRam);
            currRamConjugate = currRamConjugate.times(SILVER_RATIO_CONJUGATE);
            this.drawEightDots(g, this.ramifiedPrimeMidDegreeColor, 
                    currRamConjugate);
            withinBoundaries 
                    = (currRam.getRealPartNumeric() < this.thresholdRe);
        } while (withinBoundaries);
        currRam = new Zeta8Integer(2, 0, 0, 0);
        this.drawEightDots(g, this.ramifiedPrimeMidDegreeColor, currRam);
        currRamConjugate = currRam;
        do {
            currRam = currRam.times(SILVER_RATIO);
            this.drawEightDots(g, this.ramifiedPrimeMidDegreeColor, currRam);
            this.drawEightDots(g, this.ramifiedPrimeMidDegreeColor, 
                    currRamConjugate);
            withinBoundaries 
                    = (currRam.getRealPartNumeric() < this.thresholdRe);
        } while (withinBoundaries);
    }
    
    private void drawSplits(Graphics g) {
        int currA = 0;
        int currB;
        QuadraticInteger currGaussianInt, currZi2Int; // ??? , currZ2Int; ????
        Zeta8Integer currGaussZ8, currZi2Z8; // ??? , currZ2Z8; ????
        long currNorm;
        Color currColor;
        boolean withinBoundaries = true;
        while (withinBoundaries) {
            currA++;
            currB = 0;
            while (withinBoundaries) {
                currB++;
                currGaussianInt = new ImaginaryQuadraticInteger(currA, currB, 
                        RING_GAUSSIAN);
                if (isPrime(currGaussianInt.norm())) {
                    currGaussZ8 
                            = Zeta8Integer.convertFromQuadraticInteger(currGaussianInt);
                    this.drawEightDots(g, this.splitPrimeMidDegreeColor, 
                            currGaussZ8);
                }
                currZi2Int = new ImaginaryQuadraticInteger(currA, currB, 
                        RING_ZI2);
                currNorm = currZi2Int.norm();
                if (isPrime(currNorm)) {
                    if (currNorm % 4 == 3) {
                        currColor = this.splitPrimeColor;
                    } else {
                        currColor = this.splitPrimeMidDegreeColor;
                    }
                    currZi2Z8 
                            = Zeta8Integer.convertFromQuadraticInteger(currZi2Int);
                    this.drawEightDots(g, currColor, currZi2Z8);
                    currZi2Z8 
                            = Zeta8Integer.convertFromQuadraticInteger(currZi2Int.conjugate());
                    this.drawEightDots(g, currColor, currZi2Z8);
                }
                withinBoundaries = (currB < this.thresholdIm);
            }
            withinBoundaries = (currA < this.thresholdRe);
        }
    }

    /**
     * Sets pixels per basic imaginary interval to correspond to &radic;2. Also 
     * takes care of a couple of private fields. This is called automatically 
     * from {@link RingDisplay#setPixelsPerUnitInterval(int)}.
     */
    @Override
    protected void setPixelsPerBasicImaginaryInterval() {
        this.pixelsPerBasicImaginaryInterval = (int) Math.floor(Math.sqrt(2) 
                * this.pixelsPerUnitInterval);
        this.thresholdRe = (double) this.ringCanvasHorizMax 
                / (double) this.pixelsPerUnitInterval * 3 / 4;
        this.thresholdIm = (double) this.ringCanvasVerticMax 
                / (double) this.pixelsPerUnitInterval * 2 / 3;
    }
    
    /**
     * Paints the canvas by delegating to private procedures to the draw the 
     * points.
     * @param g The Graphics object supplied by the caller.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawSplits(g);
        this.drawRamifieds(g);
        this.drawPlainInts(g);
        this.drawUnits(g);
        g.setColor(this.zeroColor);
        g.fillOval(this.zeroCoordX - this.dotRadius, this.zeroCoordY 
                - this.dotRadius, this.dotDiameter, this.dotDiameter);
    }
    
    @Override
    protected void validateRing(IntegerRing ring) {
        if (ring == null) {
            throw new NullPointerException("Null ring can't be validated");
        }
        if (!(ring instanceof Zeta8Ring)) {
            String excMsg = ring.toASCIIString() + " is not " 
                    + Zeta8Integer.ZETA8RING.toASCIIString();
            throw new IllegalArgumentException(excMsg);
        }
    }

    @Override
    public void chooseDiscriminant() {
        System.err.println("chooseDiscriminant not applicable to Zeta8RingDisplay");
    }

    @Override
    public void incrementDiscriminant() {
        System.err.println("incrementDiscriminant not applicable to Zeta8RingDisplay");
    }
    
    @Override
    public void decrementDiscriminant() {
        System.err.println("decrementDiscriminant not applicable to Zeta8RingDisplay");
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    protected void updateBoundaryNumber() {
        //
    }
    
    // STUB TO FAIL THE FIRST TEST
    @Override
    protected double getBoundaryRe() {
        return -1.0;
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    protected double getBoundaryIm() {
        return -1.0;
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
        return "Zeta8 Ring Viewer, work in progress";
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // PLACEHOLDER
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // PLACEHOLDER
    }
    
    public Zeta8RingDisplay() {
        super(Zeta8Integer.ZETA8RING);
        this.includeRingChoice = false;
        this.includeReadoutsUpdate = false;
        this.includeThetaToggle = false;
        this.thresholdRe = (double) this.ringCanvasHorizMax 
                / (double) this.pixelsPerUnitInterval * 3 / 4;
        this.thresholdIm = (double) this.ringCanvasVerticMax 
                / (double) this.pixelsPerUnitInterval * 2 / 3;
        this.mouseAlgInt = new Zeta8Integer(0, 0, 0, 0);
    }

}
