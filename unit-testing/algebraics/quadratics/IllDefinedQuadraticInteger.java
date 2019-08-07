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
 * This is strictly to define objects for use in {@link 
 * algebraics.UnsupportedNumberDomainExceptionTest}, and other cases where it's 
 * necessary to test that {@link algebraics.UnsupportedNumberDomainException} 
 * arises. It should be in a test package, not in a source package, and it 
 * should not be compiled to any JARs.
 * @author Alonso del Arte
 */
public class IllDefinedQuadraticInteger extends QuadraticInteger {
    
    private final double numValRe, numValIm;

    /**
     * Possibly the absolute value of the ill-defined quadratic integer.
     * @return A value not guaranteed to be correct.
     */
    @Override
    public double abs() {
        return Math.abs(this.numValRe + this.numValIm);
    }

    /**
     * This function is implemented only because it is required by the {@link 
     * algebraics.AlgebraicInteger} interface.
     * @return A value not guaranteed to be correct, even allowing for loss of 
     * machine precision.
     */
    @Override
    public double getRealPartNumeric() {
        return this.numValRe;
    }

    /**
     * This function is implemented only because it is required by the {@link 
     * algebraics.AlgebraicInteger} interface.
     * @return A value not guaranteed to be correct, even allowing for loss of 
     * machine precision.
     */
    @Override
    public double getImagPartNumeric() {
        return this.numValIm;
    }
    
    /**
     * This function is implemented here only because it is abstract in {@link 
     * QuadraticInteger}.
     * @return A value guaranteed to be correct only if the ill-defined 
     * quadratic integer is purely real.
     */
    @Override
    public double angle() {
        if (this.numValRe < 0) {
            return Math.PI;
        } else {
            return 0.0;
        }
    }
    
    /**
     * Constructs an IllDefinedQuadraticInteger object.
     * @param a The integer for the "regular" part.
     * @param b The integer to be multiplied by the square root of the radicand.
     * @param ring Preferably an {@link IllDefinedQuadraticRing} object, but any 
     * object subclassed from {@link QuadraticRing} will do; there is no 
     * checking for the class of this parameter.
     */
    public IllDefinedQuadraticInteger(int a, int b, QuadraticRing ring) {
        super(a, b, ring, 1);
        double re = this.regPartMult;
        double im = 0.0;
        double y = this.quadRing.realRadSqrt * this.surdPartMult;
        if (this.quadRing.radicand < 0) {
            im += y;
        } else {
            re += y;
        }
        this.numValRe = re;
        this.numValIm = im;
    }
    
}
