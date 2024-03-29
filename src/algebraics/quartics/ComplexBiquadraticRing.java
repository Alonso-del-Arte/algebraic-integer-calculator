/*
 * Copyright (C) 2023 Alonso del Arte
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

import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticRing;

/**
 * Represents a complex biquadratic ring. The intermediate fields of a complex 
 * biquadratic are either two imaginary quadratic fields or an imaginary 
 * quadratic field and a real quadratic field.
 * @author Alonso del Arte
 */
public class ComplexBiquadraticRing extends BiquadraticRing {

    @Override
    public boolean isPurelyReal() {
        return false;
    }
    
    public ComplexBiquadraticRing(ImaginaryQuadraticRing ringA, 
            QuadraticRing ringB) {
        super(ringA, ringB, ringB);
    }

}
