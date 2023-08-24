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
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import arithmetic.Arithmeticable;
import arithmetic.NotDivisibleException;
import fractions.Fraction;

/**
 * Represents an integer in <b>Z</b>[&radic;(1 + <i>i</i>)].
 * @author Alonso del Arte
 */
public class QuarticGaussianInteger extends QuarticInteger 
        implements Arithmeticable<QuarticGaussianInteger> {
    
    public static final ImaginaryQuadraticRing QUADRATIC_GAUSSIAN_RING 
            = new ImaginaryQuadraticRing(-1);
    
    public static final QuarticGaussianRing QUARTIC_GAUSSIAN_RING 
            = new QuarticGaussianRing();
    
    private final int realIntPart;
    
    private final int sqrt1IPart;
    
    private final int cmplx1IPart;
    
    private final int sqrt1ICuPart;

    // STUB TO FAIL FIRST TEST
    @Override
    public int algebraicDegree() {
        return 0;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public long trace() {
        return 0L;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public long norm() {
        return 0L;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public long[] minPolynomialCoeffs() {
        long[] coeffs = {0, 0, 0, 0, 0};
        return coeffs;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String minPolynomialString() {
        return "Not implemented yet";
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
    
    @Override
    public String toString() {
        return this.realIntPart + " + " + this.sqrt1IPart + "\u2212(1 + i) + " 
                + this.cmplx1IPart + "(1 + i) + " + this.sqrt1ICuPart 
                + "\u2212(1 + i)\u00B3";
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String toASCIIString() {
        return this.realIntPart + " + " + this.sqrt1IPart + "sqrt(1 + i) + " 
                + this.cmplx1IPart + "(1 + i) + " + this.sqrt1ICuPart 
                + "sqrt(1 + i)^3";
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String toTeXString() {
        return "Not implemented yet";
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public String toHTMLString() {
        return "Not implemented yet";
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
        final QuarticGaussianInteger other = (QuarticGaussianInteger) obj;
        if (this.realIntPart != other.realIntPart) {
            return false;
        }
        if (this.sqrt1IPart != other.sqrt1IPart) {
            return false;
        }
        if (this.cmplx1IPart != other.cmplx1IPart) {
            return false;
        }
        return this.sqrt1ICuPart == other.sqrt1ICuPart;
    }
    
    @Override
    public int hashCode() {
        return this.realIntPart + this.sqrt1IPart * 256 + this.cmplx1IPart 
                * 65536 + this.sqrt1ICuPart * 16777216;
    }

    /**
     * Converts the quartic Gaussian integer to a quadratic Gaussian integer, if 
     * possible.
     * @return An algebraic integer of degree 2.
     * @throws algebraics.AlgebraicDegreeOverflowException
     */
    public ImaginaryQuadraticInteger convertToQuadraticInteger() {
        if (this.sqrt1IPart == 0 && this.sqrt1ICuPart == 0) {
            return new ImaginaryQuadraticInteger(this.realIntPart 
                    + this.cmplx1IPart, this.cmplx1IPart, 
                    QUADRATIC_GAUSSIAN_RING);
        } else {
            String excMsg = this.toASCIIString() 
                    + " is an algebraic number of degree 4";
            throw new AlgebraicDegreeOverflowException(excMsg, 4, this, this);
        }
    }
    
    // STUB TO FAIL FIRST TEST
    public static QuarticGaussianInteger convertFromQuadraticInteger(ImaginaryQuadraticInteger quadInt) {
        return new QuarticGaussianInteger(0, 0, 0, 0);
    }
    
    /**
     * 
     * @param summand
     * @return 
     */
    @Override
    public QuarticGaussianInteger plus(QuarticGaussianInteger summand) {
        return new QuarticGaussianInteger(this.realIntPart + summand.realIntPart, 
                this.sqrt1IPart + summand.sqrt1IPart, 
                this.cmplx1IPart + summand.cmplx1IPart, 
                this.sqrt1ICuPart + summand.cmplx1IPart);
    }

    /**
     * 
     * @param summand
     * @return 
     */
    @Override
    public QuarticGaussianInteger plus(int summand) {
        return new QuarticGaussianInteger(this.realIntPart + summand, 
                this.sqrt1IPart, this.cmplx1IPart, this.sqrt1ICuPart);
    }
    
    // STUB TO FAIL THE FIRST TEST
    @Override
    public QuarticGaussianInteger negate() {
        return this;
    }

    /**
     * 
     * @param subtrahend
     * @return 
     */
    @Override
    public QuarticGaussianInteger minus(QuarticGaussianInteger subtrahend) {
        return new QuarticGaussianInteger(this.realIntPart - subtrahend.realIntPart, 
                this.sqrt1IPart - subtrahend.sqrt1IPart, 
                this.cmplx1IPart - subtrahend.cmplx1IPart, 
                this.sqrt1ICuPart - subtrahend.sqrt1ICuPart);
    }

    /**
     * 
     * @param subtrahend
     * @return 
     */
    @Override
    public QuarticGaussianInteger minus(int subtrahend) {
        return new QuarticGaussianInteger(this.realIntPart - subtrahend, 
                this.sqrt1IPart, this.cmplx1IPart, this.sqrt1ICuPart);
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public QuarticGaussianInteger times(QuarticGaussianInteger multiplicand) {
        return new QuarticGaussianInteger(0, 0, 0, 0);
    }

    /**
     * 
     * @param multiplicand
     * @return 
     */
    @Override
    public QuarticGaussianInteger times(int multiplicand) {
        return new QuarticGaussianInteger(this.realIntPart * multiplicand, 
                this.sqrt1IPart * multiplicand, 
                this.cmplx1IPart * multiplicand, 
                this.sqrt1ICuPart * multiplicand);
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public QuarticGaussianInteger divides(QuarticGaussianInteger divisor) throws NotDivisibleException {
        if (divisor.realIntPart == 0 
                && divisor.sqrt1IPart == 0 
                && divisor.cmplx1IPart == 0 
                && divisor.sqrt1ICuPart == 0) {
            String excMsg = this.toASCIIString() + " divided by 0 is not a quartic Gaussian integer";
            throw new IllegalArgumentException(excMsg);
        }
        return new QuarticGaussianInteger(0, 0, 0, 0);
    }

    /**
     * Division
     * @param divisor
     * @return The division
     * @throws IllegalArgumentException If the divisor is 0.
     * @throws algebraics.NotDivisibleException If this quartic Gaussian integer 
     * is not divisible by the divisor.
     */
    @Override
    public QuarticGaussianInteger divides(int divisor) throws NotDivisibleException {
        Fraction divReIntFract = new Fraction(this.realIntPart, divisor);
        Fraction divSqrt1IFract = new Fraction(this.sqrt1IPart, divisor);
        Fraction divCmplx1IFract = new Fraction(this.cmplx1IPart, divisor);
        Fraction divSqrt1ICuFract = new Fraction(this.sqrt1ICuPart, divisor);
        boolean divisibilityFlag = (divReIntFract.getDenominator() == 1);
        divisibilityFlag = (divisibilityFlag && (divSqrt1IFract.getDenominator() == 1));
        divisibilityFlag = (divisibilityFlag && (divCmplx1IFract.getDenominator() == 1));
        divisibilityFlag = (divisibilityFlag && (divSqrt1ICuFract.getDenominator() == 1));
        if (divisibilityFlag) {
            return new QuarticGaussianInteger((int) divReIntFract.getNumerator(), 
                    (int) divSqrt1IFract.getNumerator(), 
                    (int) divCmplx1IFract.getNumerator(), 
                    (int) divSqrt1ICuFract.getNumerator());
        } else {
            String excMsg = this.toASCIIString() + " is not divisible by " + divisor;
            QuarticGaussianInteger wrappedDivisor = new QuarticGaussianInteger(divisor, 0, 0, 0);
            Fraction[] fracts = {divReIntFract, divSqrt1IFract, divCmplx1IFract, divSqrt1ICuFract};
            throw new NotDivisibleException(excMsg, this, wrappedDivisor, fracts);
        }
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public QuarticGaussianInteger mod(QuarticGaussianInteger divisor) {
        return new QuarticGaussianInteger(-1, -1, -1, -1);
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public QuarticGaussianInteger mod(int divisor) {
        return new QuarticGaussianInteger(-1, -1, -1, -1);
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public double abs() {
        return 0.0;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public double getRealPartNumeric() {
        double re = Math.sqrt(0.5 + 1/Math.sqrt(2)) * this.sqrt1IPart;
        re += Math.sqrt(Math.sqrt(2) - 1) * this.sqrt1ICuPart;
        re += this.realIntPart;
        re += this.cmplx1IPart;
        return re;
    }

    // STUB TO FAIL FIRST TEST
    @Override
    public double getImagPartNumeric() {
        double im = Math.sqrt(1/Math.sqrt(2) - 0.5) * this.sqrt1IPart;
        im += Math.sqrt(1 + Math.sqrt(2)) * this.sqrt1ICuPart;
        im += this.cmplx1IPart;
        return im;
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
    
    // STUB TO FAIL FIRST TEST
    @Override
    public double angle() {
        return 0.0;
    }
    
    public QuarticGaussianInteger(int a, int b, int c, int d) {
        super(QUARTIC_GAUSSIAN_RING);
        this.realIntPart = a;
        this.sqrt1IPart = b;
        this.cmplx1IPart = c;
        this.sqrt1ICuPart = d;
    }
    
}
