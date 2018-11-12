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

import calculators.NumberTheoreticFunctionsCalculator;

/**
 * Defines objects to represent imaginary quadratic rings.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticRing extends QuadraticRing {

    /**
     * A convenient holder for the absolute value of radicand
     */
    protected int absRadicand;
    
    /**
     * This function is included strictly only to simplify inheritance from 
     * {@link QuadraticRing} to {@link RealQuadraticRing}.
     * @return Nothing, ever.
     * @throws UnsupportedOperationException Always thrown, because the double 
     * primitive can't represent a purely imaginary number. If you need the 
     * square root of the radicand divided by <i>i</i>, use {@link 
     * #getAbsNegRadSqrt()} instead.
     */
    @Override
    public double getRadSqrt() {
        String exceptionMessage = "Since the radicand " + this.radicand + " is negative, this operation requires an object that can represent an imaginary number.";
        throw new UnsupportedOperationException(exceptionMessage);
    }
    
    @Override
    public int getAbsNegRad() {
        return this.absRadicand;
    }
    
    /**
     * Gives the numeric value of the square root of the radicand divided by the 
     * imaginary unit <i>i</i>.
     * @return A double with a rational approximation of the square root of the 
     * radicand divided by <i>i</i>. For example, for <b>Z</b>[&radic;&minus;2], 
     * this would be roughly 1.414; for <b>Z</b>[&radic;&minus;3], this would be 
     * roughly 1.732.
     */
    @Override
    public double getAbsNegRadSqrt() {
        return this.realRadSqrt;
    }
   
    public ImaginaryQuadraticRing(int d) {
        if (d > -1) {
            throw new IllegalArgumentException("Negative integer required for parameter d.");
        }
        if (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) {
            throw new IllegalArgumentException("Squarefree integer required for parameter d.");
        }
        this.d1mod4 = (d % 4 == -3);
        this.radicand = d;
        this.absRadicand = Math.abs(this.radicand);
        this.realRadSqrt = Math.sqrt(this.absRadicand);
    }
    
}