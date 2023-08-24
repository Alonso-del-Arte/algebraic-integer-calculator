/*
 * Copyright (C) 2019 Alonso del Arte
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
 * This is mostly to define instances of {@link IllDefinedQuadraticInteger}, 
 * which in turn are objects for use in {@link 
 * algebraics.UnsupportedNumberDomainExceptionTest}. The results of the 
 * associated functions are <em>not</em> guaranteed to be mathematically 
 * correct. Therefore, it should be in a test package, not in a source package, 
 * and it should not be compiled to any JARs meant for public distribution.
 * @author Alonso del Arte
 */
public class IllDefinedQuadraticRing extends QuadraticRing {
    
    private final double radSqrt;
    
    @Override
    public boolean isPurelyReal() {
        return (this.radicand > -1);
    }
    
    @Override
    public double getRadSqrt() {
        return this.radSqrt;
    }

    @Override
    public int getAbsNegRad() {
        return Math.abs(this.radicand);
    }

    @Override
    public double getAbsNegRadSqrt() {
        return this.radSqrt;
    }
    
    /**
     * Constructor. Performs no validation whatsoever on the input parameter 
     * (other than what the superclass does), hence the integer ring described 
     * by this object is ill-defined.
     * @param d Any squarefree integer in the range of int.
     */
    public IllDefinedQuadraticRing(int d) {
        super(d);
        this.d1mod4 = false;
        this.radicand = 4 * d;
        this.radSqrt = Math.sqrt(Math.abs(this.radicand));
    }
    
}
