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
import algebraics.quadratics.QuadraticRing;

import java.awt.Graphics;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import static calculators.NumberTheoreticFunctionsCalculator.*;

/**
 * A Swing component in which to display diagrams of prime numbers in various 
 * imaginary quadratic integer rings.
 * @author Alonso del Arte
 */
public final class ImagQuadRingDisplay extends RingDisplay {
        
    /**
     * The number to determine which imaginary quadratic integer ring diagram 
     * will be shown at start-up. This constant corresponds to the ring of 
     * Gaussian integers.
     */
    public static final int DEFAULT_RING_D = -1;
    
    /**
     * The minimum integer the square root of which can be used to generate an 
     * imaginary quadratic integer ring for the purpose of display in this ring 
     * window. Although technically an ImaginaryQuadraticRing can be defined 
     * with the square root of &minus;2147483647 (which is a prime number), this 
     * would quickly lead to arithmetic overflow problems. Hopefully this value 
     * of &minus;8191 (also a prime) is "small" enough not to cause arithmetic 
     * overflow problems with the largest zoom out setting, but "large" enough 
     * to be of no interest to most users of this program.
     */
    public static final int MINIMUM_RING_D = -8191;
    
    private static final String MANUAL_URL_TOP_LEVEL = "https://github.com/";
    
    private static final String MANUAL_URL_HIGH_SUBDIRS 
            = "Alonso-del-Arte/visualization-quadratic-imaginary-rings/";
    
    private static final String MANUAL_URL_LOW_SUBDIRS 
            = "blob/master/dist-jar/";
    
    private static final String MANUAL_URL_NAME = "README.md";
    
    /**
     * The name of the program to show in the About box.
     */
    private static final String ABOUT_BOX_PROGRAM_NAME 
            = "Imaginary Quadratic Integer Ring Viewer";
    
    /**
     * The version identification to show in the About box.
     */
    private static final String ABOUT_BOX_VERSION_ID = "Version 0.983";
    
    /**
     * The copyright notice to show in the About box.
     */
    private static final String ABOUT_BOX_COPYRIGHT_NOTICE 
            = "\u00A9 2021 Alonso del Arte";
    
    private void drawHalfIntGrids(Graphics g) {
        int verticalGridDistance;
        int currPixelPos, currReflectPixelPos;
        boolean withinBoundaries = true;
        verticalGridDistance = this.pixelsPerBasicImaginaryInterval;
        currPixelPos = this.zeroCoordY + verticalGridDistance;
        currReflectPixelPos = this.zeroCoordY - verticalGridDistance;
        g.setColor(this.halfIntegerGridColor);
        verticalGridDistance *= 2;
        while (withinBoundaries) {
            withinBoundaries = (currPixelPos < this.ringCanvasVerticMax) 
                    && (currReflectPixelPos > -1);
            if (withinBoundaries) {
                g.drawLine(0, currPixelPos, this.ringCanvasHorizMax, 
                        currPixelPos);
                g.drawLine(0, currReflectPixelPos, this.ringCanvasHorizMax, 
                        currReflectPixelPos);
                currPixelPos += verticalGridDistance;
                currReflectPixelPos -= verticalGridDistance;
            }
        }
        int halfHorizontalGridDistance = this.pixelsPerUnitInterval;
        if (halfHorizontalGridDistance % 2 == 1) {
            halfHorizontalGridDistance--;
        }
        halfHorizontalGridDistance /= 2;
        withinBoundaries = true;
        currPixelPos = this.zeroCoordX + halfHorizontalGridDistance;
        currReflectPixelPos = this.zeroCoordX - halfHorizontalGridDistance;
        while (withinBoundaries) {
            withinBoundaries = (currPixelPos < this.ringCanvasHorizMax) 
                    && (currReflectPixelPos > -1);
            if (withinBoundaries) {
                g.drawLine(currPixelPos, 0, currPixelPos, 
                        this.ringCanvasVerticMax);
                g.drawLine(currReflectPixelPos, 0, currReflectPixelPos, 
                        this.ringCanvasVerticMax);
                currPixelPos += this.pixelsPerUnitInterval;
                currReflectPixelPos -= this.pixelsPerUnitInterval;
            }
        }
    }
    
    /**
     * Draws the grids. Should only be called if the points are spaced far apart 
     * enough for the grids to be visible.
     * @param g The <code>Graphics</code> object supplied by the caller.
     */
    private void drawGrids(Graphics g) {
        if (((QuadraticRing) this.diagramRing).hasHalfIntegers()) {
            this.drawHalfIntGrids(g);
        } 
        int verticalGridDistance;
        int currPixelPos, currReflectPixelPos;
        boolean withinBoundaries = true;
        verticalGridDistance = this.pixelsPerBasicImaginaryInterval;
        g.setColor(this.integerGridColor);
        g.drawLine(0, this.zeroCoordY, this.ringCanvasHorizMax, this.zeroCoordY);
        currPixelPos = this.zeroCoordY;
        currReflectPixelPos = currPixelPos;
        while (withinBoundaries) {
            currPixelPos += verticalGridDistance;
            currReflectPixelPos -= verticalGridDistance;
            withinBoundaries = (currPixelPos < this.ringCanvasVerticMax) 
                    && (currReflectPixelPos > -1);
            if (withinBoundaries) {
                g.drawLine(0, currPixelPos, this.ringCanvasHorizMax, 
                        currPixelPos);
                g.drawLine(0, currReflectPixelPos, this.ringCanvasHorizMax, 
                        currReflectPixelPos);
            }
        }
        g.drawLine(this.zeroCoordX, 0, this.zeroCoordX, 
                this.ringCanvasVerticMax);
        currPixelPos = this.zeroCoordX;
        currReflectPixelPos = currPixelPos;
        withinBoundaries = true;
        while (withinBoundaries) {
            currPixelPos += this.pixelsPerUnitInterval;
            currReflectPixelPos -= this.pixelsPerUnitInterval;
            withinBoundaries = (currPixelPos < this.ringCanvasHorizMax) 
                    && (currReflectPixelPos > -1);
            if (withinBoundaries) {
                g.drawLine(currPixelPos, 0, currPixelPos, 
                        this.ringCanvasVerticMax);
                g.drawLine(currReflectPixelPos, 0, currReflectPixelPos, 
                        this.ringCanvasVerticMax);
            }
        }
    }
    
    /**
     * Draws the points. In the original implementation, this tested the 
     * primality of the norms of the imaginary quadratic integers with nonzero 
     * imaginary parts. However, that was not quite correct in that it skipped 
     * over primes that are real integer multiples of &omega; in 
     * <b>Z</b>[&omega;]. So I changed it to do primality test on the imaginary 
     * quadratic integers themselves, but that caused a slight delay for the 
     * <b>Z</b>[&omega;] diagram at 2 pixels per unit interval. So now the 
     * program is back to doing primality testing on the norms of the numbers 
     * rather than the numbers themselves, and there is a special pass for the 
     * aforementioned primes.
     * @param g The Graphics object supplied by the caller.
     */
    // TODO: Break this procedure up into at least two smaller procedures
    private void drawPoints(Graphics g) {
        int currPointX, currPointY;
        int currNegPointX, currNegPointY;
        int maxX = (int) Math.floor((this.ringCanvasHorizMax - this.zeroCoordX) 
                / this.pixelsPerUnitInterval);
        int maxY;
        int verticalGridDistance = this.pixelsPerBasicImaginaryInterval;
        QuadraticRing ring = (QuadraticRing) this.diagramRing;
        ImaginaryQuadraticInteger currIQI;
        if (ring.hasHalfIntegers()) {
            maxY = (int) Math.floor((this.ringCanvasVerticMax - this.zeroCoordY) 
                    / (2 * this.pixelsPerBasicImaginaryInterval));
            verticalGridDistance *= 2;
        } else {
            maxY = (int) Math.floor((this.ringCanvasVerticMax - this.zeroCoordY) 
                    / this.pixelsPerBasicImaginaryInterval);
        }
        // The central point, 0
        currPointX = this.zeroCoordX;
        currPointY = this.zeroCoordY;
        g.setColor(this.zeroColor);
        g.fillOval(currPointX - this.dotRadius, currPointY - this.dotRadius, 
                this.dotDiameter, this.dotDiameter);
        // The purely real unit points, -1 and 1
        currNegPointX = currPointX - this.pixelsPerUnitInterval;
        currPointX += this.pixelsPerUnitInterval;
        g.setColor(this.unitColor);
        g.fillOval(currPointX - this.dotRadius, currPointY - this.dotRadius, 
                this.dotDiameter, this.dotDiameter);
        g.fillOval(currNegPointX - this.dotRadius, currPointY - this.dotRadius, 
                this.dotDiameter, this.dotDiameter);
        // The even primes, -2 and 2
        currNegPointX -= this.pixelsPerUnitInterval;
        currPointX += this.pixelsPerUnitInterval;
        byte symbol = symbolKronecker(ring.getRadicand(), 2);
        if (ring.getRadicand() % 4 == -1) {
            symbol = 0;
        }
        if (ring.getRadicand() == -3) {
            symbol = -1;
        }
        if (ring.getRadicand() == -1) {
            symbol = 1;
        }
        switch (symbol) {
            case -1:
                g.setColor(this.inertPrimeColor);
                g.fillOval(currPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currNegPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                break;
            case 0:
                g.setColor(this.ramifiedPrimeColor);
                g.drawOval(currPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.drawOval(currNegPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                break;
            case 1:
                g.setColor(this.splitPrimeColor);
                g.drawOval(currPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.drawOval(currNegPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                break;
            default:
                String excMsg = "Unexpected problem computing symbolKronecker(" 
                        + ring.getRadicand() + ", " + 2 + ") = " + symbol;
                throw new RuntimeException(excMsg);
        }
        // Place "nibs" back at -1 and 1
        currNegPointX += this.pixelsPerUnitInterval;
        currPointX -= this.pixelsPerUnitInterval;
        // The other purely real integer points
        for (int x = 3; x <= maxX; x += 2) {
            currPointX += (2 * this.pixelsPerUnitInterval);
            currNegPointX -= (2 * this.pixelsPerUnitInterval);
            if (isPrime(x)) {
                symbol = symbolLegendre(ring.getRadicand(), x);
                switch (symbol) {
                    case -1:
                        g.setColor(this.inertPrimeColor);
                        g.fillOval(currPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currNegPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        break;
                    case 0:
                        g.setColor(this.ramifiedPrimeColor);
                        g.drawOval(currPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.drawOval(currNegPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        break;
                    case 1:
                        g.setColor(this.splitPrimeColor);
                        g.drawOval(currPointX - this.dotRadius + 1, currPointY 
                                - this.dotRadius + 1, this.dotDiameter, 
                                this.dotDiameter);
                        g.drawOval(currNegPointX - this.dotRadius + 1, 
                                currPointY - this.dotRadius + 1, 
                                this.dotDiameter, this.dotDiameter);
                        break;
                    default:
                        String excMsg 
                                = "Unexpected problem computing symbolLegendre(" 
                                + ring.getRadicand() + ", " + x + ") = " 
                                + symbol;
                        throw new RuntimeException(excMsg);
                }
            }
        }
        // The purely imaginary integer points other than 0
        if (ring.getRadicand() == -1) {
            // Take care to color the units in Z[i]
            currPointX = this.zeroCoordX;
            currPointY = this.zeroCoordY + verticalGridDistance;
            currNegPointY = this.zeroCoordY - verticalGridDistance;
            g.setColor(this.unitColor);
            g.fillOval(currPointX - this.dotRadius, currPointY - this.dotRadius, 
                    this.dotDiameter, this.dotDiameter);
            g.fillOval(currPointX - this.dotRadius, currNegPointY 
                    - this.dotRadius, this.dotDiameter, this.dotDiameter);
            g.setColor(this.inertPrimeColor);
            for (int y = 2; y <= maxY; y++) {
                currPointY += verticalGridDistance;
                currNegPointY -= verticalGridDistance;
                if (isPrime(y)) {
                    g.fillOval(currPointX - this.dotRadius, currPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                    g.fillOval(currPointX - this.dotRadius, currNegPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                }
            }
        } else {
            currPointX = this.zeroCoordX;
            currNegPointY = currPointY;
            for (int y = 1; y <= maxY; y++) {
                currPointY += verticalGridDistance;
                currNegPointY -= verticalGridDistance;
                currIQI = new ImaginaryQuadraticInteger(0, y, ring, 1);
                if (isPrime(currIQI.norm())) {
                    if (euclideanGCD(currIQI.norm(), ring.getRadicand()) > 1) {
                        int ramifyPoint = this.zeroCoordX + (int) currIQI.norm() 
                                * this.pixelsPerUnitInterval;
                        int negRamifyPoint = this.zeroCoordX 
                                - (int) currIQI.norm() 
                                * this.pixelsPerUnitInterval;
                        g.setColor(this.ramifiedPrimeColor);
                        g.fillOval(ramifyPoint - this.dotRadius, this.zeroCoordY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(negRamifyPoint - this.dotRadius, 
                                this.zeroCoordY - this.dotRadius, 
                                this.dotDiameter, this.dotDiameter);
                    } else {
                        g.setColor(this.inertPrimeColor);
                    }
                    g.fillOval(currPointX - this.dotRadius, currPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                    g.fillOval(currPointX - this.dotRadius, currNegPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                }
            }
        }
        // Now the complex integer points, but not the "half-integers" yet
        long currSplitPrime;
        int currSplitPrimePointX, currNegSplitPrimePointX;
        for (int x = 1; x <= maxX; x++) {
            currPointX = this.zeroCoordX + (x * this.pixelsPerUnitInterval);
            currNegPointX = this.zeroCoordX - (x * this.pixelsPerUnitInterval);
            for (int y = 1; y <= maxY; y++) {
                currPointY = this.zeroCoordY + (y * verticalGridDistance);
                currNegPointY = this.zeroCoordY - (y * verticalGridDistance);
                currIQI = new ImaginaryQuadraticInteger(x, y, ring);
                if (isPrime(currIQI.norm())) {
                    g.setColor(this.inertPrimeColor);
                    g.fillOval(currPointX - this.dotRadius, currPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                    g.fillOval(currPointX - this.dotRadius, currNegPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                    g.fillOval(currNegPointX - this.dotRadius, currPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                    g.fillOval(currNegPointX - this.dotRadius, currNegPointY 
                            - this.dotRadius, this.dotDiameter, 
                            this.dotDiameter);
                    currSplitPrime = currIQI.norm();
                    if (currSplitPrime <= maxX) {
                        currSplitPrimePointX = this.zeroCoordX 
                                + ((int) currSplitPrime 
                                * this.pixelsPerUnitInterval);
                        currNegSplitPrimePointX = this.zeroCoordX 
                                - ((int) currSplitPrime 
                                * this.pixelsPerUnitInterval);
                        g.setColor(this.splitPrimeColor);
                        g.fillOval(currSplitPrimePointX - this.dotRadius, 
                                this.zeroCoordY - this.dotRadius, 
                                this.dotDiameter, this.dotDiameter);
                        g.fillOval(currNegSplitPrimePointX - this.dotRadius, 
                                this.zeroCoordY - this.dotRadius, 
                                this.dotDiameter, this.dotDiameter);
                    }
                    if (currSplitPrime <= maxY && ring.getRadicand() == -1) {
                        currSplitPrimePointX = this.zeroCoordY 
                                + ((int) currSplitPrime 
                                * this.pixelsPerUnitInterval);
                        currNegSplitPrimePointX = this.zeroCoordY 
                                - ((int) currSplitPrime 
                                * this.pixelsPerUnitInterval);
                        g.fillOval(this.zeroCoordX - this.dotRadius, 
                                currSplitPrimePointX - this.dotRadius, 
                                this.dotDiameter, this.dotDiameter);
                        g.fillOval(this.zeroCoordX - this.dotRadius, 
                                currNegSplitPrimePointX - this.dotRadius, 
                                this.dotDiameter, this.dotDiameter);
                    }
                }
            }
        }
        // Last but not least, the "half-integers"
        if (ring.hasHalfIntegers()) {
            int halfUnitInterval = this.pixelsPerUnitInterval;
            if (halfUnitInterval % 2 == 1) {
                halfUnitInterval--;
            }
            halfUnitInterval /= 2;
            int halfMaxX = 2 * maxX;
            int halfMaxY = (int) Math.floor((this.ringCanvasVerticMax 
                    - this.zeroCoordY)/this.pixelsPerBasicImaginaryInterval);
            currPointX = this.zeroCoordX + halfUnitInterval;
            currNegPointX = this.zeroCoordX - halfUnitInterval;
            currPointY = this.zeroCoordY + this.pixelsPerBasicImaginaryInterval;
            currNegPointY = this.zeroCoordY 
                    - this.pixelsPerBasicImaginaryInterval;
            /* Take care of the other units among the Eisenstein integers. Also 
               primes of the form p * omega, where p is a purely real prime 
               satisfying p = 2 mod 3 */
            if (ring.getRadicand() == -3) {
                // The complex units, like omega
                g.setColor(this.unitColor);
                g.fillOval(currPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currPointX - this.dotRadius, currNegPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currNegPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currNegPointX - this.dotRadius, currNegPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                // Then 2 * omega, and complex associates
                g.setColor(this.inertPrimeColor);
                currPointX += halfUnitInterval;
                currNegPointX -= halfUnitInterval;
                currPointY += this.pixelsPerBasicImaginaryInterval;
                currNegPointY -= this.pixelsPerBasicImaginaryInterval;
                g.fillOval(currPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currPointX - this.dotRadius, currNegPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currNegPointX - this.dotRadius, currPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                g.fillOval(currNegPointX - this.dotRadius, currNegPointY 
                        - this.dotRadius, this.dotDiameter, this.dotDiameter);
                // And then p * omega from p = 5 on, and complex associates 
                for (int auxX = 5; auxX < halfMaxX; auxX += 6) {
                    currPointX = this.zeroCoordX + (auxX * halfUnitInterval);
                    currNegPointX = this.zeroCoordX - (auxX * halfUnitInterval);
                    currPointY = this.zeroCoordY + (auxX 
                            * this.pixelsPerBasicImaginaryInterval);
                    currNegPointY = this.zeroCoordY - (auxX 
                            * this.pixelsPerBasicImaginaryInterval);
                    if (isPrime(auxX)) {
                        g.fillOval(currPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currPointX - this.dotRadius, currNegPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currNegPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currNegPointX - this.dotRadius, currNegPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                    }
                }
            }
            // And now all the other "half-integer" primes
            for (int x = 1; x < halfMaxX; x += 2) {
                currPointX = this.zeroCoordX + (x * halfUnitInterval);
                currNegPointX = this.zeroCoordX - (x * halfUnitInterval);
                for (int y = 1; y <= halfMaxY; y += 2) {
                    currPointY = this.zeroCoordY + (y 
                            * this.pixelsPerBasicImaginaryInterval);
                    currNegPointY = this.zeroCoordY - (y 
                            * this.pixelsPerBasicImaginaryInterval);
                    currIQI = new ImaginaryQuadraticInteger(x, y, ring, 2);
                    if (isPrime(currIQI.norm())) {
                        g.setColor(this.inertPrimeColor);
                        g.fillOval(currPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currPointX - this.dotRadius, currNegPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currNegPointX - this.dotRadius, currPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        g.fillOval(currNegPointX - this.dotRadius, currNegPointY 
                                - this.dotRadius, this.dotDiameter, 
                                this.dotDiameter);
                        currSplitPrime = currIQI.norm();
                        if (currSplitPrime <= maxX) {
                            currSplitPrimePointX = this.zeroCoordX 
                                    + ((int) currSplitPrime 
                                    * this.pixelsPerUnitInterval);
                            currNegSplitPrimePointX = this.zeroCoordX 
                                    - ((int) currSplitPrime 
                                    * this.pixelsPerUnitInterval);
                            g.setColor(this.splitPrimeColor);
                            g.fillOval(currSplitPrimePointX - this.dotRadius, 
                                    this.zeroCoordY - this.dotRadius, 
                                    this.dotDiameter, this.dotDiameter);
                            g.fillOval(currNegSplitPrimePointX - this.dotRadius, 
                                    this.zeroCoordY - this.dotRadius, 
                                    this.dotDiameter, this.dotDiameter);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Changes how many pixels there are per basic imaginary interval according 
     * to the setting of pixels per unit interval. This is the formula: 
     * floor(pxui (&radic;&minus;<i>d</i>)), where pxui is pixels per unit 
     * interval and <i>d</i> corresponds to the currently displayed ring, so 
     * that (&radic;&minus;<i>d</i>) = (&radic;<i>d</i>)/<i>i</i>. However, if 
     * the ring has "half-integers," the formula is floor((pxui 
     * (&radic;&minus;<i>d</i>))/2) instead.
     */
    @Override
    protected void setPixelsPerBasicImaginaryInterval() {
        double imagInterval = this.pixelsPerUnitInterval * Math.sqrt(-((QuadraticRing) this.diagramRing).getRadicand());
        if (((QuadraticRing) this.diagramRing).hasHalfIntegers()) {
            imagInterval /= 2;
        }
        this.pixelsPerBasicImaginaryInterval = (int) Math.floor(imagInterval);
    }

    /**
     * Paints the canvas, by delegating to private procedures to draw the grids 
     * and the points. However, if the points are too close together, the grids 
     * will not be drawn.
     * @param g The Graphics object supplied by the caller.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.pixelsPerUnitInterval > MINIMUM_PIXELS_PER_UNIT_INTERVAL_TO_DRAW_GRIDS) {
            this.drawGrids(g);
        }
        this.drawPoints(g);
    }
    
    /**
     * Function to determine mouse position on the diagram and update readouts 
     * accordingly.
     * @param mauv A MouseEvent object with the relevant information.
     */
    @Override
    public void mouseMoved(MouseEvent mauv) {
        boolean algIntFound;
        int horizCoord, verticCoord;
        String stringForAlgIntReadOut;
        QuadraticRing ring = (QuadraticRing) this.diagramRing;
        if (ring.hasHalfIntegers()) {
            double horizIntermediate = 4 * (mauv.getX() - this.zeroCoordX)/this.pixelsPerUnitInterval;
            horizCoord = (int) Math.round(horizIntermediate/2);
            verticCoord = (int) Math.round((-mauv.getY() + this.zeroCoordY)/this.pixelsPerBasicImaginaryInterval);
            algIntFound = (Math.abs(horizCoord % 2) == Math.abs(verticCoord % 2));
            if (algIntFound) {
                this.mouseAlgInt = new ImaginaryQuadraticInteger(horizCoord, verticCoord, ring, 2);
            }
        } else {
            horizCoord = (int) Math.round((mauv.getX() - this.zeroCoordX)/this.pixelsPerUnitInterval);
            verticCoord = (int) Math.round((-mauv.getY() + this.zeroCoordY)/this.pixelsPerBasicImaginaryInterval);
            this.mouseAlgInt = new ImaginaryQuadraticInteger(horizCoord, verticCoord, ring);
            algIntFound = true;
        }
        if (algIntFound) {
            if (this.preferenceForThetaNotation) {
                stringForAlgIntReadOut = ((QuadraticInteger) this.mouseAlgInt).toStringAlt();
            } else {
                stringForAlgIntReadOut = this.mouseAlgInt.toString();
            }
            this.algIntReadOut.setText(stringForAlgIntReadOut);
            this.algIntTraceReadOut.setText(Long.toString(this.mouseAlgInt.trace()));
            this.algIntNormReadOut.setText(Long.toString(this.mouseAlgInt.norm()));
            this.algIntPolReadOut.setText(this.mouseAlgInt.minPolynomialString());
        }
    }
    
    /**
     * No implementation for time being. Hope to add the capability to drag the 
     * diagram in a future version.
     * @param mauv Mouse event to respond to.
     */
    @Override
    public void mouseDragged(MouseEvent mauv) {
        // IMPLEMENTATION PLACEHOLDER
    }
    
    /**
     * Asks the user to enter a new, negative, squarefree integer for the 
     * parameter <i>d</i> of a ring adjoining &radic;<i>d</i>. If the user 
     * enters a positive integer, the number will be multiplied by &minus;1. And 
     * if that number is not squarefree, this procedure will look for the next 
     * lower squarefree number, taking care not to go below 
     * {@link #MINIMUM_RING_D MINIMUM_RING_D}.
     */
    @Override
    public void chooseDiscriminant() {
        int currDiscr = ((QuadraticRing) this.diagramRing).getRadicand();
        String discrString = Integer.toString(currDiscr);
        String userChoice = (String) JOptionPane.showInputDialog(this.ringFrame, 
                "Please enter a negative, squarefree integer:", discrString);
        int discr;
        boolean repaintNeeded;
        try {
            discr = Integer.parseInt(userChoice);
        } catch (NumberFormatException nfe) {
            System.err.println(nfe.getMessage());
            discr = currDiscr;
        }
        if (discr > 0) {
            discr *= -1;
        }
        if (discr < MINIMUM_RING_D) {
            discr = MINIMUM_RING_D;
        }
        while (!isSquareFree(discr) && discr > MINIMUM_RING_D) {
            discr--;
        }
        repaintNeeded = (discr != currDiscr);
        if (repaintNeeded) {
            if (discr == MINIMUM_RING_D) {
                this.decreaseDMenuItem.setEnabled(false);
            }
            if (discr > MINIMUM_RING_D && !this.decreaseDMenuItem.isEnabled()) {
                this.decreaseDMenuItem.setEnabled(true);
            }
            if (discr == -1) {
                this.increaseDMenuItem.setEnabled(false);
            }
            if (discr < -1 && !this.increaseDMenuItem.isEnabled()) {
                this.increaseDMenuItem.setEnabled(true);
            }
            ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(discr);
            this.switchToRing(ring);
            this.updateRingHistory(ring);
        }
    }
    
    /**
     * Function to choose for parameter <i>d</i> the next higher negative 
     * squarefree integer. If this brings us up to &minus;1, then the "Increase 
     * parameter d" menu item is disabled.
     */
    @Override
    public void incrementDiscriminant() {
        int discr = ((QuadraticRing) this.diagramRing).getRadicand() + 1;
        while (!isSquareFree(discr) && discr < -1) {
            discr++;
        }
        if (discr == -1) {
            this.increaseDMenuItem.setEnabled(false);
        }
        if (discr == (MINIMUM_RING_D + 1)) {
            this.decreaseDMenuItem.setEnabled(true);
        }
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(discr);
        this.switchToRing(ring);
        this.updateRingHistory(ring);
    }

    /**
     * Function to choose for parameter <i>d</i> the next lower negative 
     * squarefree integer. If this brings us down to {@link #MINIMUM_RING_D 
     * MINIMUM_RING_D}, then the "Decrease parameter d" menu item is disabled.
     */
    @Override
    public void decrementDiscriminant() {
        int discr = ((QuadraticRing) this.diagramRing).getRadicand() - 1;
        while (!isSquareFree(discr) && discr > (Integer.MIN_VALUE + 1)) {
            discr--;
        }
        if (discr == MINIMUM_RING_D) {
            this.decreaseDMenuItem.setEnabled(false);
        }
        if (discr == -2) {
            this.increaseDMenuItem.setEnabled(true);
        }
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(discr);
        this.switchToRing(ring);
        this.updateRingHistory(ring);
    }
    
    /**
     * Copies the readouts of the algebraic integer, trace, norm and polynomial 
     * to the clipboard.
     */
    @Override
    public void copyReadoutsToClipboard() {
        String agregReadouts = this.mouseAlgInt.toASCIIString();
        if (((QuadraticRing) this.diagramRing).hasHalfIntegers()) {
            agregReadouts = agregReadouts + " = " + ((QuadraticInteger) this.mouseAlgInt).toASCIIStringAlt();
        }
        agregReadouts = agregReadouts + ", Trace: " + this.mouseAlgInt.trace() + ", Norm: " + this.mouseAlgInt.norm() + ", Polynomial: " + this.mouseAlgInt.minPolynomialStringTeX();
        StringSelection ss = new StringSelection(agregReadouts);
        this.getToolkit().getSystemClipboard().setContents(ss, ss);
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

    /**
     * Gives the location online of the user manual.
     * @return The URL which is currently on GitHub.
     * @throws RuntimeException If any problems are encountered forming the URL.
     */
    @Override
    public URI getUserManualURL() {
        String urlStr = MANUAL_URL_TOP_LEVEL + MANUAL_URL_HIGH_SUBDIRS 
                + MANUAL_URL_LOW_SUBDIRS + MANUAL_URL_NAME;
        try {
            URI url = new URI(urlStr);
            return url;
        } catch (URISyntaxException urise) {
            throw new RuntimeException(urise);
        }
    }
    
    /**
     * Gives the message for the about box. {@link RingDisplay#showAboutBox()} 
     * relies on this to create the message shown by Help -&gt; About.
     * @return The String "Imaginary Quadratic Integer Ring Viewer", followed by 
     * the version number, then the year and the author's name.
     */
    @Override
    public String getAboutBoxMessage() {
        return ABOUT_BOX_PROGRAM_NAME + "\n" + ABOUT_BOX_VERSION_ID + "\n" 
                + ABOUT_BOX_COPYRIGHT_NOTICE;
    }
        
    /**
     * Constructor. The superclass constructor takes care of setting many of the 
     * various instance fields to their default values. To actually display the 
     * window with the diagram, it will be necessary to call {@link 
     * RingDisplay#startRingDisplay()}.
     * @param ring The ring to display first. A good choice is 
     * <b>Z</b>[<i>i</i>], the ring of Gaussian integers.
     */
    public ImagQuadRingDisplay(ImaginaryQuadraticRing ring) {
        super(ring);
        this.mouseAlgInt = new ImaginaryQuadraticInteger(0, 0, ring);
    }
        
}