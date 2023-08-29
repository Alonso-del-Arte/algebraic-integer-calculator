/*
 * Copyright (C) 2023 Alonso del Arte
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
package algebraics.quartics;

/**
 * Represents an algebraic integer from the ring of <b>Z</b> adjoining the 
 * twelfth complex root of unity.
 * @deprecated Use a class to represent integers from a ring represented by an 
 * instance of {@link ComplexBiquadraticRing} instead.
 * @author Alonso del Arte
 */
@Deprecated
public class Zeta12Integer extends QuarticInteger {
    
    public static final Zeta12Ring ZETA12RING = new Zeta12Ring();

    @Override
    public int algebraicDegree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long trace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long norm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[] minPolynomialCoeffs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String minPolynomialString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String minPolynomialStringTeX() {
        return "Not implemented yet";
    }

    @Override
    public String minPolynomialStringHTML() {
        return "Not implemented yet";
    }

    @Override
    public String toString() {
        return "Not implemented yet.";
    }

    @Override
    public String toASCIIString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toTeXString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toHTMLString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double abs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getRealPartNumeric() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getImagPartNumeric() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public boolean isReApprox() {
        return false;
    }
    
    // STUB TO FAIL THE FIRST TEST
    @Override
    public boolean isImApprox() {
        return true;
    }
    
    @Override
    public double angle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Zeta12Integer() {
        super(ZETA12RING);
    }
    
}
