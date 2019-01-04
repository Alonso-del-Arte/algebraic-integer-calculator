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
 * Defines objects to represent numbers in the ring of algebraic integers of 
 * <b>Q</b>(&zeta;<sub>8</sub>), where &zeta;<sub>8</sub> = (&radic;2)/2 + 
 * (&radic;&minus;2)/2. The numbers in this ring are of the form <i>a</i> + 
 * <i>b</i>&zeta;<sub>8</sub> + <i>ci</i> + 
 * <i>d</i>(&zeta;<sub>8</sub>)<sup>3</sup>, where <i>i</i> is the imaginary 
 * unit, the principal square root of &minus;1.
 * @author Alonso del Arte
 */
public final class Zeta8Integer extends QuarticInteger {
    
    private final int realIntPart;
    
    private final int zeta8Part;
    
    private final int imagIntPart;
    
    private final int zeta8CuPart;
    
    public static final Zeta8Ring ZETA8RING = new Zeta8Ring();
    
    private static final int HASH_SEP = 256;
    private static final int HASH_SEP_SQ = HASH_SEP * HASH_SEP;
    private static final int HASH_SEP_CU = HASH_SEP_SQ * HASH_SEP;
    
    /**
     * The irrational number &radic;(1/2), roughly 0.7071.
     */
    private static final double SQRT_ONE_HALF = Math.sqrt(2)/2;

    // STUB TO FAIL FIRST OR SECOND TEST
    @Override
    public int algebraicDegree() {
        return 4;
    }

    // STUB TO FAIL FIRST TEST
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
    
    // STUB TO FAIL FIRST TEST
    @Override
    public double abs() {
        return 0.0;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public long[] minPolynomial() {
        long[] polCoeffs = {0, 0, 0, 0, 0};
        return polCoeffs;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String minPolynomialString() {
        return "Not implemented yet.";
    }
    
    @Override
    public String toString() {
        String z8iStr = "removethislater";
        if (this.realIntPart != 0) {
            z8iStr = z8iStr + this.realIntPart;
        }
        if (this.zeta8Part != 0) {
            z8iStr = z8iStr + " + " + this.zeta8Part + "\u03B6\u2088";
        }
        if (this.imagIntPart != 0) {
            z8iStr = z8iStr + " + " + this.imagIntPart + "i";
        }
        if (this.zeta8CuPart != 0) {
            z8iStr = z8iStr + " + " + this.zeta8CuPart + "(\u03B6\u2088)\u00B3";
        }
        z8iStr = z8iStr.replace(" + -", " - ");
        z8iStr = z8iStr.replace(" 1\u03B6", " \u03B6");
        z8iStr = z8iStr.replace(" 1i", " i");
        z8iStr = z8iStr.replace(" 1(\u03B6", " (\u03B6");
        z8iStr = z8iStr.replace(" + 0\u03B6\u2088", "");
        z8iStr = z8iStr.replace(" 0\u03B6\u2088", "");
        z8iStr = z8iStr.replace(" + 0i", "");
        z8iStr = z8iStr.replace(" + 0(\u03B6\u2088)\u00B3", "");
        z8iStr = z8iStr.replace("removethislater + \u03B6", "\u03B6");
        z8iStr = z8iStr.replace("removethislater + i", "i");
        z8iStr = z8iStr.replace("removethislater + (\u03B6", "(\u03B6");
        z8iStr = z8iStr.replace("removethislater -", "-");
        z8iStr = z8iStr.replace("  ", " ");
        z8iStr = z8iStr.replace("removethislater", "");
        if (z8iStr.isEmpty()) {
            z8iStr = "0";
        }
        return z8iStr;
    }

    @Override
    public String toASCIIString() {
        String z8iStr = this.toString();
        z8iStr = z8iStr.replace("\u03B6\u2088", "zeta_8");
        z8iStr = z8iStr.replace("\u00B3", "^3");
        return z8iStr;
    }

    @Override
    public String toTeXString() {
        String z8iStr = this.toString();
        z8iStr = z8iStr.replace("\u03B6\u2088", "\\zeta_8");
        z8iStr = z8iStr.replace("\u00B3", "^3");
        return z8iStr;
    }

    @Override
    public String toHTMLString() {
        String z8iStr = this.toString();
        z8iStr = z8iStr.replace("\u03B6\u2088", "&zeta;<sub>8</sub>");
        z8iStr = z8iStr.replace("\u00B3", "<sup>3</sup>");
        z8iStr = z8iStr.replace("i", "<i>i</i>");
        z8iStr = z8iStr.replace("-", "&minus;");
        return z8iStr;
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
        if (this.getClass() != obj.getClass()) {
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
        int newRe = this.realIntPart - subtrahend;
        return new Zeta8Integer(newRe, this.zeta8Part, this.imagIntPart, this.zeta8CuPart);
    }
    
    public Zeta8Integer times(Zeta8Integer multiplicand) {
        // Given x = zeta_8, this = a + bx + cx^2 + dx^3 and ...
        // ... multiplicand = p + qx + rx^2 + sx^3, then ...
        // (a + bx + cx^2 + dx^3)(p + qx + rx^2 + sx^3) = ...
        // ... ap + aqx + arx^2 + asx^3 + ...
        int newRe = this.realIntPart * multiplicand.realIntPart;
        int newZ8 = this.realIntPart * multiplicand.zeta8Part;
        int newIm = this.realIntPart * multiplicand.imagIntPart;
        int newZ83 = this.realIntPart * multiplicand.zeta8CuPart;
        // ... bpx + bqx^2 + brx^3 - bs +  ...
        newZ8 += (this.zeta8Part * multiplicand.realIntPart);
        newIm += (this.zeta8Part * multiplicand.zeta8Part);
        newZ83 += (this.zeta8Part * multiplicand.imagIntPart);
        newRe -= (this.zeta8Part * multiplicand.zeta8CuPart);
        // ... cpx^2 + cqx^3 - cr - csx + ...
        newIm += (this.imagIntPart * multiplicand.realIntPart);
        newZ83 += (this.imagIntPart * multiplicand.zeta8Part);
        newRe -= (this.imagIntPart * multiplicand.imagIntPart);
        newZ8 -= (this.imagIntPart * multiplicand.zeta8CuPart);
        // ... dpx^3 - dq - drx - dsx^2.
        newZ83 += (this.zeta8CuPart * multiplicand.realIntPart);
        newRe -= (this.zeta8CuPart * multiplicand.zeta8Part);
        newZ8 -= (this.zeta8CuPart * multiplicand.imagIntPart);
        newIm -= (this.zeta8CuPart * multiplicand.zeta8CuPart);
        return new Zeta8Integer(newRe, newZ8, newIm, newZ83);
    }
    
    public Zeta8Integer times(int multiplicand) {
        int newRe = this.realIntPart * multiplicand;
        int newZ8 = this.zeta8Part * multiplicand;
        int newIm = this.imagIntPart * multiplicand;
        int newZ83 = this.zeta8CuPart * multiplicand;
        return new Zeta8Integer(newRe, newZ8, newIm, newZ83);
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
    
    // STUB TO FAIL FIRST TEST
    @Override
    public double angle() {
        return 0.0;
    }
    
    public Zeta8Integer(int a, int b, int c, int d) {
        this.quartRing = ZETA8RING;
        this.realIntPart = a;
        this.zeta8Part = b;
        this.imagIntPart = c;
        this.zeta8CuPart = d;
    }

}
