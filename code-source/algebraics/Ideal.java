/*
 * Copyright (C) 2018 Alonso del Arte
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
package algebraics;

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;

/**
 * Defines objects to represents ideals of algebraic integers in a particular 
 * integral domain. Not designed to represent ideals of polynomials, ideals of 
 * knots nor whatever else there can be ideals of.
 * @author Alonso del Arte
 */
public class Ideal {
    
    // STUB TO FAIL FIRST TEST
    public long norm() {
        return 0L;
    }
    
    // STUB TO FAIL FIRST TEST
    public boolean isPrincipal() {
        return false;
    }
    
    // STUB TO FAIL FIRST TEST
    public boolean isMaximal() {
        return false;
    }
    
    // STUB TO FAIL FIRST TEST
    public boolean contains(AlgebraicInteger number) {
        return false;
    }
    
    // STUB TO FAIL FIRST TEST
    public AlgebraicInteger[] getGenerators() {
        ImaginaryQuadraticInteger gen = new ImaginaryQuadraticInteger(0, 0, new ImaginaryQuadraticRing(-1));
        AlgebraicInteger[] arr = {gen, gen};
        return arr;
    }
    
    @Override // STUB TO FAIL FIRST TEST
    public String toString() {
        return "Feature not implemented yet.";
    }
    
    // STUB TO FAIL FIRST TEST
    public String toASCIIString() {
        return "Feature not implemented yet.";
    }
    
    // STUB TO FAIL FIRST TEST
    public String toTeXString() {
        return "Feature not implemented yet.";
    }
    
    // STUB TO FAIL FIRST TEST
    public String toHTMLString() {
        return "Feature not implemented yet.";
    }
    
    public Ideal(AlgebraicInteger generatorA) {
        // TODO: Fill in setters of private fields
    }
    
    public Ideal(AlgebraicInteger generatorA, AlgebraicInteger generatorB) {
        // TODO: Fill in setters of private fields
    }
    
}
