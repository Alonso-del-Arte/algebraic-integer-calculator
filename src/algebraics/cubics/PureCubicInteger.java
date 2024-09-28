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

import fractions.Fraction;

/**
 * Represents an algebraic integer in a pure cubic ring.
 * @author Alonso del Arte
 */
public class PureCubicInteger {
    
    private final PureCubicRing domain;
    
    // TODO: Write Javadoc
    public PureCubicRing getRing() {
        return this.domain;
    }
    
    // TODO: Write tests for this
    public PureCubicInteger(int a, int b, int c, PureCubicRing ring) {
        this.domain = ring;
    }
    
    // TODO: Write tests for this
    public PureCubicInteger(Fraction a, Fraction b, Fraction c, 
            PureCubicRing ring) {
        this.domain = ring;
    }
    
}
