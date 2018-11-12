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
package algebraics.quartics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

/**
 *
 * @author Alonso del Arte
 */
public final class Zeta8Integer extends QuarticInteger {
    
    private final int realIntPart;
    
    private final int zeta8Part;
    
    private final int imagIntPart;
    
    private final int zeta8CuPart;
    
    private static final int HASH_SEP = 256;
    private static final int HASH_SEP_SQ = HASH_SEP * HASH_SEP;
    private static final int HASH_SEP_CU = HASH_SEP_SQ * HASH_SEP;
    
    private static final double SQRT_ONE_HALF = Math.sqrt(2)/2;

    @Override
    public int algebraicDegree() {
        return 0;
    }

    @Override
    public long trace() {
        return 0;
    }

    @Override
    public long norm() {
        // (a^2 - c^2 + 2bd)^2 + (b^2 - d^2 - 2ac)^2
        long Na = this.realIntPart * this.realIntPart - this.imagIntPart * this.imagIntPart + 2 * this.zeta8Part * this.zeta8CuPart;
        Na *= Na;
        long Nb = this.zeta8Part * this.zeta8Part - this.zeta8CuPart * this.zeta8CuPart + 2 * this.realIntPart * this.imagIntPart;
        Nb *= Nb;
        return Na + Nb;
    }

    @Override
    public long[] minPolynomial() {
        long[] polCoeffs = {0, 0, 0, 0, 0};
        return polCoeffs;
    }

    @Override
    public String minPolynomialString() {
        return "Not implemented yet.";
    }
    
    @Override
    public String toString() {
        String z8iStr = this.realIntPart + " + " + this.zeta8Part + "\u03B6\u2088 + " + this.imagIntPart + "i + " + this.zeta8CuPart + "(\u03B6\u2088)\u00B3";
        z8iStr = z8iStr.replace(" + -", " - ");
        z8iStr = z8iStr.replace(" 1\u03B6", " \u03B6");
        z8iStr = z8iStr.replace(" 1i", " i");
        z8iStr = z8iStr.replace(" 1(\u03B6", " (\u03B6");
        z8iStr = z8iStr.replace(" + 0\u03B6\u2088", "");
        z8iStr = z8iStr.replace(" + 0i", "");
        z8iStr = z8iStr.replace(" + 0(\u03B6\u2088)\u00B3", "");
        if (z8iStr.startsWith("0 + ")) {
            z8iStr = z8iStr.substring(4, z8iStr.length() - 1);
        }
        if (z8iStr.isEmpty()) {
            z8iStr = "0";
        }
        return z8iStr;
    }

    @Override
    public String toASCIIString() {
        return "Not implemented yet.";
    }

    @Override
    public String toTeXString() {
        return "Not implemented yet.";
    }

    @Override
    public String toHTMLString() {
        return "Not implemented yet.";
    }
    
    @Override
    public int hashCode() {
        int hash = this.zeta8CuPart % HASH_SEP;
        hash += (this.imagIntPart % HASH_SEP) * HASH_SEP;
        hash += (this.zeta8Part % HASH_SEP) * HASH_SEP_SQ;
        hash += (this.realIntPart % HASH_SEP) * HASH_SEP_CU;
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
        final Zeta8Integer other = (Zeta8Integer) obj;
        if (this.realIntPart != other.realIntPart) {
            return false;
        }
        if (this.zeta8Part != other.zeta8Part) {
            return false;
        }
        if (this.imagIntPart != other.imagIntPart) {
            return false;
        }
        return (this.zeta8CuPart == other.zeta8CuPart);
    }
    
    public Zeta8Integer plus(Zeta8Integer addend) {
        int newRe = this.realIntPart + addend.realIntPart;
        int newZ8 = this.zeta8Part + addend.zeta8Part;
        int newIm = this.imagIntPart + addend.imagIntPart;
        int newZ83 = this.zeta8CuPart + addend.zeta8CuPart;
        return new Zeta8Integer(newRe, newZ8, newIm, newZ83);
    }
    
    public Zeta8Integer plus(int addend) {
        int newRe = this.realIntPart + addend;
        return new Zeta8Integer(newRe, this.zeta8Part, this.imagIntPart, this.zeta8CuPart);
    }
    
    public Zeta8Integer minus(Zeta8Integer subtrahend) {
        int newRe = this.realIntPart - subtrahend.realIntPart;
        int newZ8 = this.zeta8Part - subtrahend.zeta8Part;
        int newIm = this.imagIntPart - subtrahend.imagIntPart;
        int newZ83 = this.zeta8CuPart - subtrahend.zeta8CuPart;
        return new Zeta8Integer(newRe, newZ8, newIm, newZ83);
    }
    
    public Zeta8Integer minus(int subtrahend) {
        return this;
    }
    
    // STUB FOR FAILING FIRST TEST
    public Zeta8Integer times(Zeta8Integer multiplicand) {
        return this;
    }
    
    // STUB FOR FAILING FIRST TEST
    public Zeta8Integer times(int multiplicand) {
        return this;
    }
    
    // STUB FOR FAILING FIRST TEST
    public Zeta8Integer divides(Zeta8Integer divisor) {
        return this;
    }
    
    // STUB FOR FAILING FIRST TEST
    public Zeta8Integer divides(int divisor) {
        return this;
    }
    
    public QuadraticInteger convertToQuadraticInteger() {
        QuadraticRing quadRing;
        QuadraticInteger quadInt = null;
        if (this.imagIntPart == 0) {
            if (this.zeta8Part == -this.zeta8CuPart) {
                quadRing = new RealQuadraticRing(2);
                quadInt = new RealQuadraticInteger(this.realIntPart, this.zeta8Part, quadRing);
            }
            if (this.zeta8Part == this.zeta8CuPart) {
                quadRing = new ImaginaryQuadraticRing(-2);
                quadInt = new ImaginaryQuadraticInteger(this.realIntPart, this.zeta8Part, quadRing);
            }
        }
        if (this.zeta8Part == 0 && this.zeta8CuPart == 0) {
            quadRing = new ImaginaryQuadraticRing(-1);
            quadInt = new ImaginaryQuadraticInteger(this.realIntPart, this.imagIntPart, quadRing);
        }
        if (quadInt != null) {
            return quadInt;
        } else {
            String exceptionMessage = this.toASCIIString() + " is a number of algebraic degree " + this.algebraicDegree();
            throw new AlgebraicDegreeOverflowException(exceptionMessage, 4, this, this);
        }
    }
    
    public static Zeta8Integer convertFromQuadraticInteger(QuadraticInteger quadInt) {
        Zeta8Integer z8i;
        switch (quadInt.getRing().getRadicand()) {
            case -2:
                z8i = new Zeta8Integer(quadInt.getRegPartMult(), quadInt.getSurdPartMult(), 0, quadInt.getSurdPartMult());
                break;
            case -1:
                z8i = new Zeta8Integer(quadInt.getRegPartMult(), 0, quadInt.getSurdPartMult(), 0);
                break;
            case 2:
                z8i = new Zeta8Integer(quadInt.getRegPartMult(), quadInt.getSurdPartMult(), 0, -quadInt.getSurdPartMult());
                break;
            default:
                String exceptionMessage = quadInt.getRing().toASCIIString() + " is not supported for this conversion.";
                throw new UnsupportedNumberDomainException(exceptionMessage, quadInt);
        }
        return z8i;
    }
    
    @Override
    public double getRealPartNumeric() {
        double re = -SQRT_ONE_HALF * this.zeta8CuPart;
        re += SQRT_ONE_HALF * this.zeta8Part;
        re += this.realIntPart;
        return re;
    }
    
    @Override
    public double getImagPartNumeric() {
        double im = SQRT_ONE_HALF * this.zeta8CuPart;
        im += this.imagIntPart;
        im += SQRT_ONE_HALF * this.zeta8Part;
        return im;
    }
    
    public Zeta8Integer(int a, int b, int c, int d) {
        this.quartRing = new Zeta8Ring();
        this.realIntPart = a;
        this.zeta8Part = b;
        this.imagIntPart = c;
        this.zeta8CuPart = d;
    }

}
