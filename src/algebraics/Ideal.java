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
package algebraics;

import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticRing;
import arithmetic.NonEuclideanDomainException;
import arithmetic.NotDivisibleException;

import java.util.Objects;

import static calculators.NumberTheoreticFunctionsCalculator.divideOutUnits;
import static calculators.NumberTheoreticFunctionsCalculator.euclideanGCD;
import static calculators.NumberTheoreticFunctionsCalculator.getOneInRing;
import static calculators.NumberTheoreticFunctionsCalculator.isPrime;

/**
 * Defines objects to represents ideals of algebraic integers in a particular 
 * integral domain. Not designed to represent ideals of polynomials, ideals of 
 * knots nor whatever else there can be ideals of.
 * <p>A <b>principal ideal</b> is the set of all multiples of a particular 
 * number in a given ring. For example, &#10216;1 + <i>i</i>&#10217;, the set of 
 * all multiples of 1 + <i>i</i> in <b>Z</b>[<i>i</i>], is a principal 
 * ideal. A principal ideal &#10216;<i>a</i>&#10217; is said to be generated by 
 * the number <i>a</i>.</p>
 * <p>A <b>secondary ideal</b> (this is not standard terminology) is an ideal 
 * that can't be generated by a single number in a given ring, but is the sum of 
 * two principal ideals. For example, &#10216;2, 1 + &radic;&minus;5&#10217; 
 * consists of all numbers of the form 2<i>x</i> + (1 + 
 * &radic;&minus;5)<i>y</i>, where <i>x</i> and <i>y</i> are arbitrary numbers 
 * in <b>Z</b>[&radic;&minus;5]. A secondary ideal &#10216;<i>a</i>, 
 * <i>b</i>&#10217; in a given ring consists of all numbers of the form 
 * <i>ax</i> + <i>by</i> in that ring, where <i>x</i> and <i>y</i> are arbitrary 
 * numbers in that ring.</p>
 * <p>In a <b>principal ideal domain</b>, all ideals are principal ideals even 
 * if presented to look like ideals generated by more than one number. Rings of 
 * algebraic integers that are not principal ideal domains do have both 
 * principal ideals and secondary ideals. Ideals generated by more than two 
 * numbers are not necessary since those can always be generated by just one or 
 * two numbers.</p>
 * <p>The main challenges in writing a program that can handle ideals in rings 
 * of algebraic integers is enabling the objects representing ideals to be 
 * recognized as equal when they are equal, and enabling an object representing 
 * an ideal to recognize when an object representing an algebraic integer 
 * represents an algebraic integer contained in that ideal.</p>
 * @author Alonso del Arte
 */
public class Ideal {
    
    private final boolean principalIdealFlag;
    private final boolean wholeRingFlag;
    
    private final AlgebraicInteger idealGeneratorA;
    private final AlgebraicInteger idealGeneratorB;
    
    private final IntegerRing workingRing;
    
    // THIS SHOULD PASS FOR PRINCIPAL IDEALS BUT FAIL FOR SECONDARY IDEALS
    public long norm() {
        if (this.principalIdealFlag) {
            return Math.abs(this.idealGeneratorA.norm());
        }
        return 0L;
    }
    
    public boolean isPrincipal() {
        return this.principalIdealFlag;
    } 
    
    public boolean isMaximal() {
        if (this.principalIdealFlag) {
            return isPrime(this.idealGeneratorA);
        }
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
        String excMsg = "Contains function not yet supported for " 
                + number.toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, number);
    }
    
    public boolean contains(Ideal ideal) {
//        if (this.equals(ideal)) {
//            return true;
//        }
        return false;
    }
    
    public AlgebraicInteger[] getGenerators() {
        if (this.principalIdealFlag) {
            AlgebraicInteger[] gens = {this.idealGeneratorA};
            return gens;
        } else {
            AlgebraicInteger[] gens = {this.idealGeneratorA, 
                this.idealGeneratorB};
            return gens;
        }
    }

    /**
     * Gives a hash code for the ideal. This is guaranteed to be the same for 
     * two ideals that are the same, but it is likely though not guaranteed to 
     * be different for two ideals that are different.
     * @return A 32-bit integer hash code. For example, if this ideal is 
     * &#10216;2, &radic;10&#10217;, it might be hashed as &minus;1212580032.
     */
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
            idealString = idealString + ", " 
                    + this.idealGeneratorB.toASCIIString();
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
            idealString = idealString + ", " 
                    + this.idealGeneratorB.toTeXString();
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
            idealString = idealString + ", " 
                    + this.idealGeneratorB.toHTMLString();
        }
        idealString = idealString + "&#10217;";
        return idealString;
    }
    
    public Ideal(IntegerRing ring) {
        this.wholeRingFlag = true;
        this.workingRing = ring;
        this.principalIdealFlag = true;
        this.idealGeneratorA = getOneInRing(ring);
        this.idealGeneratorB = null;
    }
    
    public Ideal(AlgebraicInteger generatorA) {
        this.principalIdealFlag = true;
        this.workingRing = generatorA.getRing();
        if (Math.abs(generatorA.norm()) == 1) {
            this.wholeRingFlag = true;
            this.idealGeneratorA = getOneInRing(this.workingRing);
        } else {
            AlgebraicInteger genA = generatorA;
            if (this.workingRing instanceof ImaginaryQuadraticRing 
                    || this.workingRing instanceof RealQuadraticRing) {
                QuadraticInteger gA = (QuadraticInteger) generatorA;
                gA = (QuadraticInteger) divideOutUnits(gA);
                genA = gA;
            }
                this.idealGeneratorA = genA;
                this.wholeRingFlag = false;
        }
        this.idealGeneratorB = null;
    }
    
    public Ideal(AlgebraicInteger generatorA, AlgebraicInteger generatorB) {
        if (!generatorA.getRing().equals(generatorB.getRing())) {
            String excMsg = generatorA.toASCIIString() + " is from " 
                    + generatorA.getRing().toASCIIString() + " but " 
                    + generatorB.toASCIIString() + " is from " 
                    + generatorB.getRing().toASCIIString();
            throw new IllegalArgumentException(excMsg);
        }
        AlgebraicInteger genA = generatorA;
        AlgebraicInteger genB = generatorB;
        boolean principalFlag = false;
        this.workingRing = generatorA.getRing();
        if (this.workingRing instanceof ImaginaryQuadraticRing 
                || this.workingRing instanceof RealQuadraticRing) {
            QuadraticInteger gA = (QuadraticInteger) generatorA;
            QuadraticInteger gB = (QuadraticInteger) generatorB;
            AlgebraicInteger gcd;
            try {
                gcd = euclideanGCD(gA, gB);
                gA = (QuadraticInteger) gcd;
                gB = gB.minus(gB); // Zero out, avoid deref null pointer warning
                principalFlag = true;
            } catch (NonEuclideanDomainException nede) {
                gcd = nede.tryEuclideanGCDAnyway();
                if (((QuadraticInteger) gcd).getRegPartMult() >= 0) {
                    gA = (QuadraticInteger) gcd;
                    gB = gB.minus(gB); // Zero out
                    principalFlag = true;
                }
            }
            if (!principalFlag) {
                if ((gA.getSurdPartMult() != 0) && (gB.getSurdPartMult() == 0)) {
                    QuadraticInteger swapper = gA;
                    gA = gB;
                    gB = swapper;
                }
            }
            gA = (QuadraticInteger) divideOutUnits(gA);
            gB = (QuadraticInteger) divideOutUnits(gB);
            genA = gA;
            genB = gB;
        }
        this.principalIdealFlag = principalFlag;
        this.idealGeneratorA = genA;
        if (this.principalIdealFlag) {
            this.idealGeneratorB = null;
        } else {
            this.idealGeneratorB = genB;
        }
        this.wholeRingFlag = ((Math.abs(this.idealGeneratorA.norm()) == 1) 
                || (Math.abs(genB.norm()) == 1));
    }
    
}