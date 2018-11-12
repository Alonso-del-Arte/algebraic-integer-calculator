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
package algebraics.quadratics;

/**
 * Defines objects to represent real quadratic integers, for the most part 
 * symbolically rather than numerically.
 * @author Alonso del Arte
 */
public class RealQuadraticInteger extends QuadraticInteger {
    
    private final double numVal;
    private final double absNumVal;
    
    /**
     * Gives the absolute value of the numeric value of this real quadratic 
     * integer. In most cases it will be a rational approximation.
     * @return The absolute value as a double. For example, 1 &minus; &radic;2 
     * is approximately &minus;0.414213562373, so this would return roughly 
     * 0.414213562373 for that number.
     */
    @Override
    public double abs() {
        return absNumVal;
    }
    
    /**
     * Gets the real part of the real quadratic integer. May be half an 
     * integer.
     * @return The real part of the real quadratic integer. For example, for 
     * &minus;1/2 + 9(&radic;13)/2, the result should be roughly 15.72498.
     */
    @Override
    public double getRealPartNumeric() {
        return numVal;
    }
    
    /**
     * Gets the imaginary part of the real quadratic integer. But for a real 
     * quadratic integer, this should actually always be exactly 0.
     * @return The imaginary part of the real quadratic integer. For example, 
     * for &minus;1/2 + 9(&radic;13)/2, the result should be 0.0.
     */
    @Override
    public double getImagPartNumeric() {
        return 0.0;
    }
    
    /**
     * Alternative object constructor, may be used when the denominator is known 
     * to be 1.
     * @param a The "regular" part of the real quadratic integer. For example, 
     * for 5 + &radic;3, this parameter would be 5.
     * @param b The part to be multiplied by &radic;<i>d</i>. For example, for 5 
     * + &radic;3, this parameter would be 1.
     * @param R The ring to which this algebraic integer belongs to. For 
     * example, for 5 + &radic;3, this parameter could be <code>new 
     * RealQuadraticRing(3)</code>.
     */
    public RealQuadraticInteger(int a, int b, QuadraticRing R) {
        this(a, b, R, 1);
    }
        
    public RealQuadraticInteger(int a, int b, QuadraticRing R, int denom) {
        RealQuadraticRing ring;
        if (R instanceof RealQuadraticRing) {
            ring = (RealQuadraticRing) R;
        } else {
            if ((R instanceof ImaginaryQuadraticRing) && (b == 0)) {
                ring = new RealQuadraticRing(-R.radicand);
            } else {
                String exceptionMessage = R.toASCIIString() + " is not a real quadratic ring as needed.";
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
        boolean abParityMatch;
        if (denom == -1 || denom == -2) {
            a *= -1;
            b *= -1;
            denom *= -1;
        }
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
        this.quadRing = ring;
        double preNumVal = this.quadRing.realRadSqrt * this.surdPartMult + this.regPartMult;
        numVal = preNumVal / this.denominator;
        absNumVal = Math.abs(numVal);
    }
    
}
