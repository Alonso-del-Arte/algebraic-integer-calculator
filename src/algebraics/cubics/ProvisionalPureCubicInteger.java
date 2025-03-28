/*
 * Copyright (C) 2025 Alonso del Arte
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

import fractions.Fraction;

/**
 * Draft class for PureCubicInteger. Normally I would do this sort of work in a 
 * file not checked into version control. But as I expect this work to take me a 
 * long time, I decided it would be better to have this checked into version 
 * control.
 * @author Alonso del Arte
 */
// TODO: Mark @Deprecated once all useful material is copied over to 
// PureCubicInteger
public class ProvisionalPureCubicInteger extends CubicInteger {
    
    private static final char CUBIC_ROOT_SYMBOL = '\u221B';
    
    private static final char EXPONENT_TWO_SYMBOL = '\u00B2';
    
    private final int partA, partB, partC;
    
    private final PureCubicRing heldRing;
    
    // TODO: Write more tests for this, namely for degrees 0, 1, ? 2 ?
    @Override
    public int algebraicDegree() {
        return 3;
    }
    
    // TODO: Write tests for this
    @Override
    public long trace() {
        return 0;
    }
    
    // TODO: Write tests for this
    @Override
    public long norm() {
        return 0;
    }
    
    // TODO: Write tests for this
    @Override
    public long[] minPolynomialCoeffs() {
        long[] array = {0, 0, 0};
        return array;
    }
    
    // TODO: Write tests for this
    @Override
    public String minPolynomialString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    // TODO: Write tests for this
    @Override
    public String minPolynomialStringTeX() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    // TODO: Write tests for this
    @Override
    public String minPolynomialStringHTML() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    @Override
    public String toString() {
        String cubicRootSymbolWithD = Character.toString(CUBIC_ROOT_SYMBOL) 
                + this.heldRing.radicand;
        return this.partA + " + " + this.partB + cubicRootSymbolWithD + " + " 
                + this.partC + "(" + cubicRootSymbolWithD + ")" 
                + EXPONENT_TWO_SYMBOL;
    }
    
    // TODO: Write tests for this
    @Override
    public String toASCIIString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    // TODO: Write tests for this
    @Override
    public String toTeXString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    // TODO: Write tests for this
    @Override
    public String toHTMLString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    // TODO: Write tests for this
    @Override
    public double abs() {
        return 0.0;
    }
    
    // TODO: Write tests for this
    @Override
    public double getRealPartNumeric() {
        return 0.0;
    }
    
    // TODO: Write tests for this
    @Override
    public double getImagPartNumeric() {
        return 0.0;
    }
    
    // TODO: Write tests for this
    @Override
    public boolean isReApprox() {
        return false;
    }
    
    // TODO: Write tests for this
    @Override
    public boolean isImApprox() {
        return false;
    }
    
    // TODO: Write tests for this
    @Override
    public double angle() {
        return 0.0;
    }
        
    // TODO: Write tests for this
    public ProvisionalPureCubicInteger(int a, int b, int c, CubicRing ring) {
        super(ring);
        if (ring == null) {
            String excMsg = "Ring should not be null";
            throw new NullPointerException(excMsg);
        }
        this.partA = a;
        this.partB = b;
        this.partC = c;
// TODO: Write test requiring check ring is actually PureCubicRing instance
        this.heldRing = (PureCubicRing) ring;
    }
    
    // TODO: Write tests for this
    public ProvisionalPureCubicInteger(Fraction a, Fraction b, Fraction c, 
            CubicRing ring) {
        super(ring);
        if (a == null || b == null || c == null || ring == null) {
            String excMsg = "Fractions, ring, should not be null";
            throw new NullPointerException(excMsg);
        }
        this.partA = 0;
        this.partB = 0;
        this.partC = 0;
// TODO: Write test requiring check ring is actually PureCubicRing instance
        this.heldRing = (PureCubicRing) ring;
    }
    
}
