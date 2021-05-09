/*
 * Copyright (C) 2021 Alonso del Arte
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

import algebraics.AlgebraicInteger;

/**
 * Provides a template for defining objects to represent quartic integers. Does 
 * not provide any arithmetic operations.
 * @author Alonso del Arte
 */
public abstract class QuarticInteger implements AlgebraicInteger {
    
    protected QuarticRing quartRing;
    
    /**
     * Retrieves an object representing the ring this quartic integer belongs 
     * to.
     * @return An object subclassed from {@link QuarticRing}.
     */
    @Override
    public QuarticRing getRing() {
        return this.quartRing;
    }
    
    /**
     * Superclass constructor. The subclass constructors should probably require 
     * a lot more parameters.
     * @param ring The ring this quartic integer is in.
     */
    public QuarticInteger(QuarticRing ring) {
        this.quartRing = ring;
    }
    
}
