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

import algebraics.UnsupportedNumberDomainException;
import calculators.NumberTheoreticFunctionsCalculator;

/**
 * Defines objects to represent real quadratic rings.
 * @author Alonso del Arte
 */
public class RealQuadraticRing extends QuadraticRing {
    
    /**
     * Gives the numeric value of the square root of the radicand.
     * @return A double with a rational approximation of the square root of the 
     * radicand. For example, for <b>Z</b>[&radic;2], this would be roughly 
     * 1.414; for <b>Z</b>[&radic;3], this would be roughly 1.732.
     */
    @Override
    public double getRadSqrt() {
        return this.realRadSqrt;
    }
    
    /**
     * This function is included merely to simplify the inheritance structure of 
     * {@link QuadraticRing} to {@link ImaginaryQuadraticRing}.
     * @return The same number as {@link QuadraticRing#getRadicand()}.
     */
    @Override
    public int getAbsNegRad() {
        return this.radicand;
    }
    
    @Override
    public double getAbsNegRadSqrt() {
        return this.realRadSqrt;
    }
    
    public RealQuadraticRing(int d) {
        if (d < 1) {
            throw new IllegalArgumentException("Positive integer required for parameter d.");
        }
        if (d == 1) {
            throw new UnsupportedNumberDomainException("Sorry, O_(Q(sqrt(1))) is not supported. Did you mean Z[i]?", NumberTheoreticFunctionsCalculator.IMAG_UNIT_I);
        }
        if (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) {
            throw new IllegalArgumentException("Squarefree integer required for parameter d.");
        }
        this.d1mod4 = (d % 4 == 1);
        this.radicand = d;
        this.realRadSqrt = Math.sqrt(this.radicand);
    }

}
