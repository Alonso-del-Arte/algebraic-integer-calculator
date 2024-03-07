/*
 * Copyright (C) 2024 Alonso del Arte
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

import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 * Defines objects to represent real quadratic rings.
 * @author Alonso del Arte
 */
public final class RealQuadraticRing extends QuadraticRing {
    
    private static final long serialVersionUID = 4547847540095073075L;
    
    /**
     * Indicates that this ring is purely real. All the numbers of this ring are 
     * on the real number line. The imaginary part of any number in this ring is 
     * always 0.0.
     * @return Always true for a real quadratic ring.
     */
    @Override
    public final boolean isPurelyReal() {
        return true;
    }
    
    /**
     * Gives the numeric value of the square root of the radicand. For real 
     * quadratic rings, this is the same as {@link #getAbsNegRadSqrt()}.
     * @return The rational approximation of the square root of the radicand. 
     * For example, for <b>Z</b>[&radic;2], this would be 1.4142135623730951;  
     * for <b>Z</b>[&radic;3], this would be 1.7320508075688772.
     */
    public double getRadSqrt() {
        return this.realRadSqrt;
    }
    
    /**
     * Gives the absolute value of the radicand. This function is included 
     * merely to simplify the inheritance structure of {@link QuadraticRing} to 
     * {@link ImaginaryQuadraticRing}.
     * @return The same number as {@link QuadraticRing#getRadicand()}.
     */
    @Override
    public int getAbsNegRad() {
        return this.radicand;
    }
    
    /**
     * Gives the square root of the absolute value of the radicand. This 
     * function is included merely to simplify the inheritance structure of 
     * {@link QuadraticRing} to {@link ImaginaryQuadraticRing}.
     * @return The same number as {@link RealQuadraticRing#getRadSqrt()}.
     */
    @Override
    public double getAbsNegRadSqrt() {
        return this.realRadSqrt;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    /**
     * Constructs a new object representing a real quadratic ring.
     * @param d A squarefree, positive integer greater than 1. Examples: 5, 21, 
     * 1729.
     * @throws IllegalArgumentException If <code>d</code> is any negative 
     * integer, or if it's 0 or 1, or positive but the multiple of a nontrivial 
     * square. Examples: &minus;7, 28.
     */
    public RealQuadraticRing(int d) {
        super(d);
        if (d < 1) {
            String excMsg = "Positive integer required for parameter d";
            throw new IllegalArgumentException(excMsg);
        }
        if (d == 1) {
            String excMsg = "Sorry, O_(Q(sqrt(1))) is not supported";
            throw new IllegalArgumentException(excMsg);
        }
        this.d1mod4 = (d % 4 == 1);
    }

}
