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
 * This is strictly to define objects for use in {@link 
 * UnsupportedNumberDomainExceptionTest}. It should be in a test package, not in 
 * a source package, and it should not be compiled to any JARs.
 * @author Alonso del Arte
 */
public class IllDefinedQuadraticInteger extends QuadraticInteger {

    @Override
    public double abs() {
        return 0;
    }

    @Override
    public double getRealPartNumeric() {
        return 0.0;
    }

    @Override
    public double getImagPartNumeric() {
        return 0.0;
    }
    
    public IllDefinedQuadraticInteger(int a, int b, IllDefinedQuadraticRing ring) {
        this.regPartMult = a;
        this.surdPartMult = b;
        this.denominator = -1;
        this.quadRing = ring;
    }
    
}
