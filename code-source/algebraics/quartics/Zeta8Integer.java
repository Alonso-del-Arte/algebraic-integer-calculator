// TODO: Consider deprecation
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
package algebraics.quartics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.Arithmeticable;
import algebraics.NotDivisibleException;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import fractions.Fraction;

/**
 * Defines objects to represent numbers in the ring of algebraic integers of 
 * <b>Q</b>(&zeta;<sub>8</sub>), where &zeta;<sub>8</sub> = (&radic;2)/2 + 
 * (&radic;&minus;2)/2. The numbers in this ring are of the form <i>a</i> + 
 * <i>b</i>&zeta;<sub>8</sub> + <i>ci</i> + 
 * <i>d</i>(&zeta;<sub>8</sub>)<sup>3</sup>, where <i>i</i> is the imaginary 
 * unit, the principal square root of &minus;1.
 * @author Alonso del Arte
 */
public final class Zeta8Integer extends QuarticInteger 
        implements Arithmeticable<Zeta8Integer> {
    
    private final int realIntPart;
    
    private final int zeta8Part;
    
    private final int imagIntPart;
    
    private final int zeta8CuPart;
    
    public static final Zeta8Ring ZETA8RING = new Zeta8Ring();
    
    private final double numValRe;
    private final double numValIm;
    
    private static final int HASH_SEP = 256;
    private static final int HASH_SEP_SQ = HASH_SEP * HASH_SEP;
    private static final int HASH_SEP_CU = HASH_SEP_SQ * HASH_SEP;
    
    /**
     * The irrational number &radic;(1/2), roughly 0.7071.
     */
    private static final double SQRT_ONE_HALF = Math.sqrt(2)/2;
    
    private static final RealQuadraticRing RING_Z2 = new RealQuadraticRing(2);

    @Override
    public int algebraicDegree() {
        if (this.zeta8Part == 0 && this.zeta8CuPart == 0) {
            if (this.imagIntPart == 0) {
                if (this.realIntPart == 0) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 2;
            }
        } else {
            if (Math.abs(this.zeta8Part) == Math.abs(this.zeta8CuPart)) {
                return 2;
            } else {
                return 4;
            }
        }
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public long trace() {
        return 0;
    }

    @Override
    public long norm() {
        // (a^2 - c^2 + 2bd)^2 + (b^2 - d^2 - 2ac)^2
        long Na = this.realIntPart * this.realIntPart;
        Na -= this.imagIntPart * this.imagIntPart;
        Na += 2 * this.zeta8Part * this.zeta8CuPart;
        Na *= Na;
        long Nb = this.zeta8Part * this.zeta8Part;
        Nb -= this.zeta8CuPart * this.zeta8CuPart;
        Nb += 2 * this.realIntPart * this.imagIntPart;
        Nb *= Nb;
        return Na + Nb;
    }
    
    @Override
    public double abs() {
        if (this.numValIm == 0.0) {
            return Math.abs(this.numValRe);
        } else {
            double reSq = this.numValRe * this.numValRe;
            double imSq = this.numValIm * this.numValIm;
            return Math.sqrt(reSq + imSq);
        }
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public long[] minPolynomialCoeffs() {
        long[] polCoeffs = {0, 0, 0, 0, 0};
        return polCoeffs;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String minPolynomialString() {
        return "Not implemented yet.";
    }
    
    // STUB TO FAIL FIRST TEST
    @Override
    public String minPolynomialStringTeX() {
        return "Not implemented yet";
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String minPolynomialStringHTML() {
        return "Not implemented yet";
    }
    
    private long complexAdjustedNorm() {
        long Na = this.realIntPart + this.zeta8Part - this.zeta8CuPart;
        Na *= Na;
        long Nb = this.zeta8Part + this.imagIntPart + this.zeta8CuPart;
        Nb *= Nb;
        return Na + Nb;
    }
    
    public Zeta8Integer complexConjugate() {
        return new Zeta8Integer(this.realIntPart, -this.zeta8CuPart, -this.imagIntPart, -this.zeta8Part);
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
        z8iStr = z8iStr.replace("removethislater + ", "");
        z8iStr = z8iStr.replace("removethislater -", "-");
        z8iStr = z8iStr.replace("  ", " ");
        z8iStr = z8iStr.replace("removethislater", "");
        z8iStr = z8iStr.replace("-", "\u2212");
        if (z8iStr.isEmpty()) {
            return "0";
        } else {
            return z8iStr;
        }
    }

    @Override
    public String toASCIIString() {
        String z8iStr = this.toString();
        z8iStr = z8iStr.replace("\u2212", "-");
        z8iStr = z8iStr.replace("\u03B6\u2088", "zeta_8");
        z8iStr = z8iStr.replace("\u00B3", "^3");
        return z8iStr;
    }

    @Override
    public String toTeXString() {
        String z8iStr = this.toString();
        z8iStr = z8iStr.replace("\u2212", "-");
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
        z8iStr = z8iStr.replace("\u2212", "&minus;");
        return z8iStr;
    }
    
    /**
     * Returns a hash code value for this algebraic integer from 
     * <b>Q</b>(&zeta;<sub>8</sub>). The hash code is guaranteed to be equal for 
     * integers that are equal, but not guaranteed to be distinct for integers 
     * that are distinct.
     * @return A 32-bit signed integer
     */
    @Override
    public int hashCode() {
        int hash = this.zeta8CuPart % HASH_SEP;
        hash += (this.imagIntPart % HASH_SEP) * HASH_SEP;
        hash += (this.zeta8Part % HASH_SEP) * HASH_SEP_SQ;
        hash += (this.realIntPart % HASH_SEP) * HASH_SEP_CU;
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one. The numerical 
     * value of this Zeta8Integer may be arithmetically equal to a {@link 
     * algebraics.quadratics.QuadraticInteger} or even to an {@link Integer}, 
     * but the two are not equal as far as the Java Virtual Machine is 
     * concerned.
     * @param obj The object to compare this Zeta8Integer object to.
     * @return True if both obj is also a Zeta8Integer object with the same 
     * arithmetical value, false otherwise.
     */
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
    
    /**
     * Addition operation, since operator+ (plus) can't be overloaded. 
     * Computations are done with 64-bit variables. Overflow checking is 
     * rudimentary.
     * @param addend The algebraic integer from <b>Q</b>(&zeta;<sub>8</sub>) to 
     * add to this algebraic integer from <b>Q</b>(&zeta;<sub>8</sub>). For 
     * example,
     * @return The sum
     */
    @Override
    public Zeta8Integer plus(Zeta8Integer addend) {
        int sumRe = this.realIntPart + addend.realIntPart;
        int sumZ8 = this.zeta8Part + addend.zeta8Part;
        int sumIm = this.imagIntPart + addend.imagIntPart;
        int sumZ8Cu = this.zeta8CuPart + addend.zeta8CuPart;
        return new Zeta8Integer(sumRe, sumZ8, sumIm, sumZ8Cu);
    }
    
    @Override
    public Zeta8Integer plus(int addend) {
        int sumRe = this.realIntPart + addend;
        return new Zeta8Integer(sumRe, this.zeta8Part, this.imagIntPart, this.zeta8CuPart);
    }
    
    @Override
    public Zeta8Integer minus(Zeta8Integer subtrahend) {
        int subtRe = this.realIntPart - subtrahend.realIntPart;
        int subtZ8 = this.zeta8Part - subtrahend.zeta8Part;
        int subtIm = this.imagIntPart - subtrahend.imagIntPart;
        int subtZ8Cu = this.zeta8CuPart - subtrahend.zeta8CuPart;
        return new Zeta8Integer(subtRe, subtZ8, subtIm, subtZ8Cu);
    }
    
    @Override
    public Zeta8Integer minus(int subtrahend) {
        int subtRe = this.realIntPart - subtrahend;
        return new Zeta8Integer(subtRe, this.zeta8Part, this.imagIntPart, this.zeta8CuPart);
    }
    
    @Override
    public Zeta8Integer times(Zeta8Integer multiplicand) {
        int multRe = this.realIntPart * multiplicand.realIntPart;
        int multZ8 = this.realIntPart * multiplicand.zeta8Part;
        int multIm = this.realIntPart * multiplicand.imagIntPart;
        int multZ8Cu = this.realIntPart * multiplicand.zeta8CuPart;
        multZ8 += (this.zeta8Part * multiplicand.realIntPart);
        multIm += (this.zeta8Part * multiplicand.zeta8Part);
        multZ8Cu += (this.zeta8Part * multiplicand.imagIntPart);
        multRe -= (this.zeta8Part * multiplicand.zeta8CuPart);
        multIm += (this.imagIntPart * multiplicand.realIntPart);
        multZ8Cu += (this.imagIntPart * multiplicand.zeta8Part);
        multRe -= (this.imagIntPart * multiplicand.imagIntPart);
        multZ8 -= (this.imagIntPart * multiplicand.zeta8CuPart);
        multZ8Cu += (this.zeta8CuPart * multiplicand.realIntPart);
        multRe -= (this.zeta8CuPart * multiplicand.zeta8Part);
        multZ8 -= (this.zeta8CuPart * multiplicand.imagIntPart);
        multIm -= (this.zeta8CuPart * multiplicand.zeta8CuPart);
        return new Zeta8Integer(multRe, multZ8, multIm, multZ8Cu);
    }
    
    @Override
    public Zeta8Integer times(int multiplicand) {
        int multRe = this.realIntPart * multiplicand;
        int multZ8 = this.zeta8Part * multiplicand;
        int multIm = this.imagIntPart * multiplicand;
        int multZ8Cu = this.zeta8CuPart * multiplicand;
        return new Zeta8Integer(multRe, multZ8, multIm, multZ8Cu);
    }
    
    @Override
    public Zeta8Integer divides(Zeta8Integer divisor) throws NotDivisibleException {
        Zeta8Integer precursor = this.times(divisor).complexConjugate();
        // a^2 + b^2 + c^2 + d^2 + (ab - ad + bc + cd)sqrt(2)
        int adjDivNa = divisor.realIntPart + divisor.realIntPart;
        adjDivNa += divisor.zeta8Part * divisor.zeta8Part;
        adjDivNa += divisor.imagIntPart * divisor.imagIntPart;
        adjDivNa += divisor.zeta8CuPart * divisor.zeta8CuPart;
        int adjDivNb = divisor.realIntPart * divisor.zeta8Part;
        adjDivNb -= divisor.realIntPart * divisor.zeta8CuPart;
        adjDivNb += divisor.zeta8Part * divisor.imagIntPart;
        adjDivNb += divisor.imagIntPart * divisor.zeta8CuPart;
        RealQuadraticInteger adjDivNorm = new RealQuadraticInteger(adjDivNa, adjDivNb, RING_Z2);
        Fraction divReFract, divBSqrt2Fract, divBSqrtNeg2Fract, divImFract, divDSqrt2Fract, divDSqrtNeg2Fract;
        QuadraticInteger partHold;
        try {
            partHold = adjDivNorm.divides(adjDivNorm);
            divReFract = new Fraction(partHold.getRegPartMult());
            divBSqrt2Fract = new Fraction(partHold.getSurdPartMult());
        } catch (NotDivisibleException nde) {
            divReFract = nde.getFractions()[0];
            divBSqrt2Fract = nde.getFractions()[1];
        }
        long divisorNorm = divisor.complexAdjustedNorm();
        Fraction divRe = new Fraction(precursor.realIntPart, divisorNorm);
        Fraction divZ8 = new Fraction(precursor.zeta8Part, divisorNorm);
        Fraction divIm = new Fraction(precursor.imagIntPart, divisorNorm);
        Fraction divZ8Cu = new Fraction(precursor.zeta8CuPart, divisorNorm);
        boolean algIntFlag = divRe.getDenominator() == 1;
        algIntFlag = algIntFlag && divZ8.getDenominator() == 1;
        algIntFlag = algIntFlag && divIm.getDenominator() == 1;
        algIntFlag = algIntFlag && divZ8Cu.getDenominator() == 1;
        if (algIntFlag) {
            long divReL = divRe.getNumerator();
            long divZ8L = divZ8.getNumerator();
            long divImL = divIm.getNumerator();
            long divZ8CuL = divZ8Cu.getNumerator();
            boolean inRangeFlag = (divReL >= Integer.MIN_VALUE) && (divReL <= Integer.MAX_VALUE);
            inRangeFlag = inRangeFlag && ((divZ8L >= Integer.MIN_VALUE) && (divZ8L <= Integer.MAX_VALUE));
            inRangeFlag = inRangeFlag && ((divImL >= Integer.MIN_VALUE) && (divImL <= Integer.MAX_VALUE));
            inRangeFlag = inRangeFlag && ((divZ8CuL >= Integer.MIN_VALUE) && (divZ8CuL <= Integer.MAX_VALUE));
            if (inRangeFlag) {
                return new Zeta8Integer((int) divReL, (int) divZ8L, (int) divImL, (int) divZ8CuL);
            } else {
                String excMsg = "The number " + divReL + " + " + divZ8L + "zeta_8 + " + divImL + "i + " + divZ8CuL + "(zeta_8)^3 exceeds the range of the Zeta8Integer data type.";
                throw new ArithmeticException(excMsg);
            }
        } else {
            Fraction[] fracts = {divRe, divZ8, divIm, divZ8Cu};
            String excMsg = "The number " + divRe.toString() + " + " + divZ8.toString() + "zeta_8 + " + divIm.toString() + "i + " + divZ8Cu.toString() + "(zeta_8)^3 is an algebraic number but not an algebraic integer.";
            throw new NotDivisibleException(excMsg, this, divisor, fracts);
        }
    }
    
    @Override
    public Zeta8Integer divides(int divisor) throws NotDivisibleException {
//        if (divisor == 0) {
//            String exceptionMessage = "Can't divide " + this.toASCIIString() + " by 0.";
//            throw new ArithmeticException(exceptionMessage);
//        }
        boolean divisibilityFlag = (this.realIntPart % divisor == 0);
        divisibilityFlag = (divisibilityFlag && (this.zeta8Part % divisor == 0));
        divisibilityFlag = (divisibilityFlag && (this.imagIntPart % divisor == 0));
        divisibilityFlag = (divisibilityFlag && (this.zeta8CuPart % divisor == 0));
        if (divisibilityFlag) {
            int divRe = this.realIntPart / divisor;
            int divZ8 = this.zeta8Part / divisor;
            int divIm = this.imagIntPart / divisor;
            int divZ8Cu = this.zeta8CuPart / divisor;
            return new Zeta8Integer(divRe, divZ8, divIm, divZ8Cu);
        } else {
            String exceptionMessage = this.toASCIIString() + " is not divisible by " + divisor;
            Zeta8Integer wrappedDivisor = new Zeta8Integer(divisor, 0, 0, 0);
            Fraction reFract = new Fraction(this.realIntPart, divisor);
            Fraction z8Fract = new Fraction(this.zeta8Part, divisor);
            Fraction imFract = new Fraction(this.imagIntPart, divisor);
            Fraction z83Fract = new Fraction(this.zeta8CuPart, divisor);
            Fraction[] fracts = {reFract, z8Fract, imFract, z83Fract};
            throw new NotDivisibleException(exceptionMessage, this, wrappedDivisor, fracts);
        }
    }
    
    // STUB TO FAIL THE FIRST TEST
    @Override
    public Zeta8Integer mod(Zeta8Integer divisor) {
        return new Zeta8Integer(-1, -1, -1, -1);
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public Zeta8Integer mod(int divisor) {
        return new Zeta8Integer(-1, -1, -1, -1);
    }
    
    public QuadraticInteger convertToQuadraticInteger() {
        QuadraticRing quadRing;
        QuadraticInteger quadInt = null;
        if (this.imagIntPart == 0) {
            if (this.zeta8Part == -this.zeta8CuPart) {
                quadRing = new RealQuadraticRing(2);
                quadInt = new RealQuadraticInteger(this.realIntPart, 
                        this.zeta8Part, quadRing);
            }
            if (this.zeta8Part == this.zeta8CuPart) {
                quadRing = new ImaginaryQuadraticRing(-2);
                quadInt = new ImaginaryQuadraticInteger(this.realIntPart, 
                        this.zeta8Part, quadRing);
            }
        }
        if (this.zeta8Part == 0 && this.zeta8CuPart == 0) {
            quadRing = new ImaginaryQuadraticRing(-1);
            quadInt = new ImaginaryQuadraticInteger(this.realIntPart, 
                    this.imagIntPart, quadRing);
        }
        if (quadInt != null) {
            return quadInt;
        } else {
            String excMsg = this.toASCIIString() 
                    + " is a number of algebraic degree " 
                    + this.algebraicDegree();
            throw new AlgebraicDegreeOverflowException(excMsg, 4, this, this);
        }
    }
    
    public static Zeta8Integer convertFromQuadraticInteger(QuadraticInteger quadInt) {
        Zeta8Integer z8i;
        switch (quadInt.getRing().getRadicand()) {
            case -2:
                z8i = new Zeta8Integer(quadInt.getRegPartMult(), 
                        quadInt.getSurdPartMult(), 0, quadInt.getSurdPartMult());
                return z8i;
            case -1:
                z8i = new Zeta8Integer(quadInt.getRegPartMult(), 0, 
                        quadInt.getSurdPartMult(), 0);
                return z8i;
            case 2:
                z8i = new Zeta8Integer(quadInt.getRegPartMult(), 
                        quadInt.getSurdPartMult(), 0, -quadInt.getSurdPartMult());
                return z8i;
            default:
                if (quadInt.getSurdPartMult() == 0) {
                    z8i = new Zeta8Integer(quadInt.getRegPartMult(), 0, 0, 0);
                    return z8i;
                }
                String excMsg = quadInt.getRing().toASCIIString() 
                        + " is outside of " + ZETA8RING.toASCIIString();
                throw new IllegalArgumentException(excMsg);
        }
    }
    
    @Override
    public double getRealPartNumeric() {
        return this.numValRe;
    }
    
    @Override
    public double getImagPartNumeric() {
        return this.numValIm;
    }
    
    @Override
    public double angle() {
        return Math.atan2(this.numValIm, this.numValRe);
    }
    
    /**
     * Constructor. There is no need to pass in an {@link 
     * algebraics.IntegerRing} object.
     * @param a An integer. For example, for &minus;1 + 7&zeta;<sub>8</sub> + 
     * 2<i>i</i> + 8(&zeta;<sub>8</sub>)<sup>3</sup>, this would be &minus;1.
     * @param b The integer to be multiplied by &zeta;<sub>8</sub>. For example, 
     * for &minus;1 + 7&zeta;<sub>8</sub> + 2<i>i</i> + 
     * 8(&zeta;<sub>8</sub>)<sup>3</sup>, this would be 7.
     * @param c The integer to be multiplied by <i>i</i>. For example, for 
     * &minus;1 + 7&zeta;<sub>8</sub> + 2<i>i</i> + 
     * 8(&zeta;<sub>8</sub>)<sup>3</sup>, this would be 2.
     * @param d  The integer to be multiplied by 
     * (&zeta;<sub>8</sub>)<sup>3</sup>. For example, for &minus;1 + 
     * 7&zeta;<sub>8</sub> + 2<i>i</i> + 8(&zeta;<sub>8</sub>)<sup>3</sup>, this 
     * would be 8.
     */
    public Zeta8Integer(int a, int b, int c, int d) {
        super(ZETA8RING);
        this.realIntPart = a;
        this.zeta8Part = b;
        this.imagIntPart = c;
        this.zeta8CuPart = d;
        double re = -SQRT_ONE_HALF * this.zeta8CuPart;
        re += SQRT_ONE_HALF * this.zeta8Part;
        re += this.realIntPart;
        this.numValRe = re;
        double im = SQRT_ONE_HALF * this.zeta8CuPart;
        im += this.imagIntPart;
        im += SQRT_ONE_HALF * this.zeta8Part;
        this.numValIm = im;
    }

}
