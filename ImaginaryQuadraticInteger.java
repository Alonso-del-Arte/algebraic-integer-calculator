/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algebraicintegercalculator;

/**
 *
 * @author AL
 */
public class ImaginaryQuadraticInteger extends QuadraticInteger {

    protected ImaginaryQuadraticRing quadRing;
    
    /**
     * If d1mod4 is true, then denominator may be 1 or 2, otherwise denominator should be 1.
     */
        
    /**
     * Gets the real part of the imaginary quadratic integer. May be half an integer.
     * @return The real part of the imaginary quadratic integer.
     */
    public double getRealPartMult() {
        double realPart = this.regPartMult;
        if (this.denominator == 2) {
            realPart /= 2;
        }
        return realPart;
    }
    
    public double getImagPartwRadMult() {
        double imagPartwRad = this.surdPartMult * this.quadRing.absRadicandSqrt;
        if (this.denominator == 2) {
            imagPartwRad /= 2;
        }
        return imagPartwRad;
    }
    
    public long getTwiceRealPartMult() {
        long twiceRealPartMult = this.regPartMult;
        if (this.denominator == 1) {
            twiceRealPartMult *= 2;
        }
        return twiceRealPartMult;
    }
    
    public long getTwiceImagPartMult() {
        long twiceImagPartMult = this.surdPartMult;
        if (this.denominator == 1) {
            twiceImagPartMult *= 2;
        }
        return twiceImagPartMult;
    }
    
    /**
     * A text representation of the imaginary quadratic integer, with the real part first and the imaginary part second.
     * @return A string representing the imaginary quadratic integer which can be output to the console.
     */
    @Override
    public String toString() {
        String IQIString;
        if (this.denominator == 2) {
            IQIString = this.regPartMult + "/2 ";
            if (this.surdPartMult < -1) {
                IQIString += (("- " + ((-1) * this.surdPartMult)) + "\u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult == -1) {
                IQIString += ("- \u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult == 1) {
                IQIString += ("+ \u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult > 1) {
                IQIString += ("+ " + this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")/2");
            } 
        } else {
            IQIString = this.regPartMult + " ";
            if (this.surdPartMult < -1) {
                IQIString += (("- " + ((-1) * this.surdPartMult)) + "\u221A(" + this.quadRing.radicand + ")");
            }
            if (this.surdPartMult == -1) {
                IQIString += ("- \u221A(" + this.quadRing.radicand + ")");
            }
            if (this.surdPartMult == 1) {
                IQIString += ("+ \u221A(" + this.quadRing.radicand + ")");
            }
            if (this.surdPartMult > 1) {
                IQIString += ("+ " + this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")");
            }
        }
        return IQIString;
    }
    
    public String toStringAlt() {

        String altIQIString = this.toString();
        if (this.quadRing.radicand == -1) {
            altIQIString = altIQIString.replace("\u221A(-1)", "i");
        }
        if (this.quadRing.d1mod4) {
            // TO DO: Conversion logic for theta notation
        }
        if (this.quadRing.radicand == -3) {
            altIQIString = altIQIString.replace("\u03B8", "\u03C9");
        }
        return altIQIString;
 
    }
    
    /**
     * Addition operation, since operator+ (plus) can't be overloaded.
     * @param summand The imaginary quadratic integer to be added.
     * @return A new ImaginaryQuadraticInteger object with the result of the operation.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come from different quadratic rings, the result of the sum will be an algebraic integer of degree 4 and this exception will be thrown.
     */
    public ImaginaryQuadraticInteger plus(ImaginaryQuadraticInteger summand) throws AlgebraicDegreeOverflowException {
        if (((this.surdPartMult != 0) && (summand.surdPartMult != 0)) && (this.quadRing.radicand != summand.quadRing.radicand)) {
            throw new AlgebraicDegreeOverflowException("This operation would result in an algebraic integer of degree 4.", 2, 4);
        }
        int sumRealPart = 0;
        int sumImagPart = 0;
        int sumDenom = 1;
        if (this.quadRing.d1mod4) {
            if (this.denominator == 1 && summand.denominator == 2) {
                sumRealPart = 2 * this.regPartMult + summand.regPartMult;
                sumImagPart = 2 * this.surdPartMult + summand.surdPartMult;
                sumDenom = 2;
            }
            if (this.denominator == 2 && summand.denominator == 1) {
                sumRealPart = this.regPartMult + 2 * summand.regPartMult;
                sumImagPart = this.surdPartMult + 2 * summand.surdPartMult;
                sumDenom = 2;
            }
            if (this.denominator == summand.denominator) {
                sumRealPart = this.regPartMult + summand.regPartMult;
                sumImagPart = this.surdPartMult + summand.surdPartMult;
                sumDenom = this.denominator;
            }
        } else {
            sumRealPart = this.regPartMult + summand.regPartMult;
            sumImagPart = this.surdPartMult + summand.surdPartMult;
            sumDenom = 1;
        }
        return new ImaginaryQuadraticInteger(sumRealPart, sumImagPart, this.quadRing, sumDenom);
    }

    /**
     * Subtraction operation, since operator- can't be overloaded
     * @param subtrahend The imaginary quadratic integer to be subtracted
     * @return A new ImaginaryQuadraticInteger object with the result of the operation.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come from different quadratic rings, the result of the sum will be an algebraic integer of degree 4 and this exception will be thrown.
     */
    public ImaginaryQuadraticInteger minus(ImaginaryQuadraticInteger subtrahend) throws AlgebraicDegreeOverflowException {
        if (((this.surdPartMult != 0) && (subtrahend.surdPartMult != 0)) && (this.quadRing.radicand != subtrahend.quadRing.radicand)) {
            throw new AlgebraicDegreeOverflowException("This operation would result in an algebraic integer of degree 4.", 2, 4);
        }
        int subtractionRealPart = 0;
        int subtractionImagPart = 0;
        int subtractionDenom = 1;
        if (this.quadRing.d1mod4) {
            if (this.denominator == 1 && subtrahend.denominator == 2) {
                subtractionRealPart = 2 * this.regPartMult - subtrahend.regPartMult;
                subtractionImagPart = 2 * this.surdPartMult - subtrahend.surdPartMult;
                subtractionDenom = 2;
            }
            if (this.denominator == 2 && subtrahend.denominator == 1) {
                subtractionRealPart = this.regPartMult - 2 * subtrahend.regPartMult;
                subtractionImagPart = this.surdPartMult - 2 * subtrahend.surdPartMult;
                subtractionDenom = 2;
            }
            if (this.denominator == subtrahend.denominator) {
                subtractionRealPart = this.regPartMult - subtrahend.regPartMult;
                subtractionImagPart = this.surdPartMult - subtrahend.surdPartMult;
                subtractionDenom = this.denominator;
            }
        } else {
            subtractionRealPart = this.regPartMult - subtrahend.regPartMult;
            subtractionImagPart = this.surdPartMult - subtrahend.surdPartMult;
            subtractionDenom = 1;
        }
        return new ImaginaryQuadraticInteger(subtractionRealPart, subtractionImagPart, this.quadRing, subtractionDenom);
    }
    
    /**
     * Class constructor
     * @param a The real part of the imaginary quadratic integer, multiplied by 2 when applicable
     * @param b The part to be multiplied by sqrt(d), multiplied by 2 when applicable
     * @param R The ring to which this algebraic integer belongs to
     * @param denom In most cases 1, but may be 2 if a and b have the same parity and d = 1 mod 4
     */
    public ImaginaryQuadraticInteger(int a, int b, ImaginaryQuadraticRing R, int denom) {
        
        boolean abParityMatch;
        
        if (denom < 1 || denom > 2) {
            throw new IllegalArgumentException("Parameter denom must be 1 or 2.");
        }
        if (denom == 2) {
            abParityMatch = Math.abs(a % 2) == Math.abs(b % 2);
            if (!abParityMatch) {
                throw new IllegalArgumentException("Parity of parameter a must match parity of parameter b.");
            }
            if (a % 2 == 0) {
                this.regPartMult = a/2;
                this.surdPartMult = b/2;
                this.denominator = 1;
            } else {
                if (R.d1mod4) {
                    this.regPartMult = a;
                    this.surdPartMult = b;
                    this.denominator = 2;
                } else {
                    throw new IllegalArgumentException("Either parameter a and parameter b need to both be even, or parameter denom needs to be 1.");
                }
            }
        } else {
            this.regPartMult = a;
            this.surdPartMult = b;
            this.denominator = 1;
        }
        this.quadRing = R;
        
    }
    
}
