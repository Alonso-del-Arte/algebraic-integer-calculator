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
package algebraics.cubics;

import algebraics.IntegerRing;

/**
 * Provides a template for defining objects to represent cubic rings.
 * @author Alonso del Arte
 */
public abstract class CubicRing implements IntegerRing {
        
    /**
     * The maximum possible algebraic degree of an algebraic integer in a cubic 
     * ring.
     */
    public static final int MAX_ALGEBRAIC_DEGREE = 3;
    
    /**
     * Gives the maximum algebraic degree an algebraic integer in a cubic ring 
     * can have.
     * @return Always 3 in the case of a cubic ring. This value is also 
     * available as the constant {@link #MAX_ALGEBRAIC_DEGREE}.
     */
    @Override
    public final int getMaxAlgebraicDegree() {
        return MAX_ALGEBRAIC_DEGREE;
    }
    
}
