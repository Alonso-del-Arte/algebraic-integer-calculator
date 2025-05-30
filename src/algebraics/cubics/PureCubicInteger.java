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
 * Represents an algebraic integer in a pure cubic ring.
 * @author Alonso del Arte
 */
public class PureCubicInteger extends CubicInteger {
    
    // TODO: Write tests for this
    @Override
    public int algebraicDegree() {
        return -1;
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
        return -1.0;
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
        
    public PureCubicInteger(int a, int b, int c, PureCubicRing ring) {
        super(ring);
    }
    
    public PureCubicInteger(Fraction a, Fraction b, Fraction c, 
            PureCubicRing ring) {
        super(ring);
        if (a == null || b == null || c == null) {
            String excMsg = "Fractions should not be null";
            throw new NullPointerException(excMsg);
        }
    }
    
}
