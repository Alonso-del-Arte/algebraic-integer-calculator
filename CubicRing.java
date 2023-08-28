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
    
    protected static boolean preferenceForBlackboardBold = true;
    
    /**
     * Query the setting of the preference for blackboard bold.
     * @return True if blackboard bold is preferred, false if plain bold is 
     * preferred.
     */
    public static boolean preferBlackboardBold() {
        return preferenceForBlackboardBold;
    }
    
    /**
     * Set preference for blackboard bold or plain bold. This is only relevant 
     * for the functions {@link #toHTMLString()} and {@link #toTeXString()}.
     * @param preferenceForBB true if blackboard bold is preferred, false if 
     * plain bold is preferred.
     */
    public static void preferBlackboardBold(boolean preferenceForBB) {
        preferenceForBlackboardBold = preferenceForBB;
    }
    
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
