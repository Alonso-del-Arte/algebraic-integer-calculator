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
package algebraics;

/**
 *
 * @author Alonso del Arte
 */
public interface IntegerRing {
    
    /**
     * Gives the maximum algebraic degree an algebraic integer in the ring can 
     * have.
     * @return A positive integer, e.g., 3 in the case of a cubic integer ring.
     */
    int getMaxAlgebraicDegree();
    
    /**
     * Formats the ring's label as a String using ASCII characters only.
     * @return A String. For example, for <b>Z</b>[&radic;2], this would be 
     * "Z[sqrt(2)]".
     */
    String toASCIIString();
    
    /**
     * Formats the ring's label as a String that can be used in a TeX document.
     * @return A String. For example, for <b>Z</b>[&#8731;2], this might be 
     * "\mathbb Z[\root 3 \of 2]".
     */
    String toTeXString();
    
    String toHTMLString();
    
}
