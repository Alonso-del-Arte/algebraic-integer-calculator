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
package algebraics.cubics;

import algebraics.AlgebraicInteger;

/**
 * Defines a template for objects to represent algebraic integers of degree 3. 
 * Integers that may be represented include integers of degree 1 in the context 
 * of a cubic ring.
 * @author Alonso del Arte
 */
public abstract class CubicInteger implements AlgebraicInteger {
    
    protected final CubicRing cubicRing;
    
    /* *
     * Retrieves an object representing the ring this cubic integer belongs to.
     * @return An object of type {@link CubicRing}.
     */
    // TODO: Write tests for this, reactivate Javadoc
    @Override
    public CubicRing getRing() {
        return null;// this.cubicRing;
    }
    
    public CubicInteger(CubicRing ring) {
        this.cubicRing = ring;
    }
    
}
