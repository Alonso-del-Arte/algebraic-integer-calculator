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

import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;

import java.util.Objects;

/**
 * Defines objects to represents ideals of algebraic integers in a particular 
 * integral domain. Not designed to represent ideals of polynomials, ideals of 
 * knots nor whatever else there can be ideals of.
 * @author Alonso del Arte
 */
public class Ideal {
    
    private final boolean principalIdealFlag;
    private final boolean wholeRingFlag;
    
    private final AlgebraicInteger idealGeneratorA;
    private final AlgebraicInteger idealGeneratorB;
    
    private final IntegerRing workingRing;
    
    // STUB TO FAIL FIRST TEST
    public long norm() {
        return 0L;
    }
    
    public boolean isPrincipal() {
        return principalIdealFlag;
    }
    
    // STUB TO FAIL FIRST TEST
    public boolean isMaximal() {
        return false;
    }
    
    public boolean contains(AlgebraicInteger number) {
        if (!number.getRing().equals(this.workingRing)) {
            return false;
        }
        if (this.wholeRingFlag) {
            return true;
        }
        if (this.workingRing instanceof QuadraticRing) {
            QuadraticInteger n = (QuadraticInteger) number;
            QuadraticInteger a = (QuadraticInteger) this.idealGeneratorA;
            if (this.principalIdealFlag) {
                try {
                    n.divides(a);
                    return true;
                } catch (NotDivisibleException nde) {
                    return false;
                }
            } else {
                QuadraticInteger b = (QuadraticInteger) this.idealGeneratorB;
            }
            return false; // *** REMOVE WHEN FOREGOING IS COMPLETE ****
        }
        String exceptionMessage = "Arithmetic operations are not yet supported for the domain of " + number.toASCIIString() + "; they're necessary to determine containment in this case.";
        throw new UnsupportedNumberDomainException(exceptionMessage, number);
    }
    
    // STUB TO FAIL FIRST TEST
    public boolean contains(Ideal ideal) {
        return false;
    }
    
    public AlgebraicInteger[] getGenerators() {
        if (this.principalIdealFlag) {
            AlgebraicInteger[] gens = {this.idealGeneratorA};
            return gens;
        } else {
            AlgebraicInteger[] gens = {this.idealGeneratorA, this.idealGeneratorB};
            return gens;
        }
    }

    @Override
    public int hashCode() {
        int hash = this.workingRing.hashCode();
        if (!this.wholeRingFlag) {
            hash *= this.idealGeneratorA.hashCode();
            if (!this.principalIdealFlag) {
                hash *= this.idealGeneratorB.hashCode();
            }
        }
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ideal other = (Ideal) obj;
        if (!Objects.equals(this.idealGeneratorA, other.idealGeneratorA)) {
            return false;
        }
        return (Objects.equals(this.idealGeneratorB, other.idealGeneratorB));
    }
    
    @Override
    public String toString() {
        if (this.wholeRingFlag) {
            return this.workingRing.toString();
        }
        String idealString = "\u27E8" + this.idealGeneratorA.toString();
        if (!this.principalIdealFlag) {
            idealString = idealString + ", " + this.idealGeneratorB.toString();
        }
        idealString = idealString + "\u27E9";
        return idealString;
    }
    
    public String toASCIIString() {
        if (this.wholeRingFlag) {
            return this.workingRing.toASCIIString();
        }
        String idealString = "(" + this.idealGeneratorA.toASCIIString();
        if (!this.principalIdealFlag) {
            idealString = idealString + ", " + this.idealGeneratorB.toASCIIString();
        }
        idealString = idealString + ")";
        return idealString;
    }
    
    public String toTeXString() {
        if (this.wholeRingFlag) {
            return this.workingRing.toTeXString();
        }
        String idealString = "\\langle" + this.idealGeneratorA.toTeXString();
        if (!this.principalIdealFlag) {
            idealString = idealString + ", " + this.idealGeneratorB.toTeXString();
        }
        idealString = idealString + "\\rangle";
        return idealString;
    }
    
    public String toHTMLString() {
        if (this.wholeRingFlag) {
            return this.workingRing.toHTMLString();
        }
        String idealString = "&#10216;" + this.idealGeneratorA.toHTMLString();
        if (!this.principalIdealFlag) {
            idealString = idealString + ", " + this.idealGeneratorB.toHTMLString();
        }
        idealString = idealString + "&#10217;";
        return idealString;
    }
    
    public Ideal(IntegerRing ring) {
        this.wholeRingFlag = true;
        this.workingRing = ring;
        this.principalIdealFlag = true;
        this.idealGeneratorA = NumberTheoreticFunctionsCalculator.getOneInRing(ring);
        this.idealGeneratorB = null;
    }
    
    public Ideal(AlgebraicInteger generatorA) {
        this.principalIdealFlag = true;
        this.workingRing = generatorA.getRing();
        if (Math.abs(generatorA.norm()) == 1) {
            this.wholeRingFlag = true;
            this.idealGeneratorA = NumberTheoreticFunctionsCalculator.getOneInRing(this.workingRing);
        } else {
            this.idealGeneratorA = generatorA;
            this.wholeRingFlag = false;
        }
        this.idealGeneratorB = null;
    }
    
    public Ideal(AlgebraicInteger generatorA, AlgebraicInteger generatorB) {
        if (!generatorA.getRing().equals(generatorB.getRing())) {
            String exceptionMessage = generatorA.toASCIIString() + " is from " + generatorA.getRing().toASCIIString() + " but " + generatorB.toASCIIString() + " is from " + generatorB.getRing().toASCIIString() + ".";
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.principalIdealFlag = false;
        this.idealGeneratorA = generatorA;
        this.idealGeneratorB = generatorB;
        this.workingRing = this.idealGeneratorA.getRing();
        this.wholeRingFlag = ((Math.abs(this.idealGeneratorA.norm()) == 1) || (Math.abs(this.idealGeneratorB.norm()) == 1));
    }
    
}
