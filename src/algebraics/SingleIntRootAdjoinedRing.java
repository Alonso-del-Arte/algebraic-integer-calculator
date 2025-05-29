/*
 * Copyright (C) 2025 Alonso del Arte
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
 * Indicates an integer ring is formed by adjoining <b>Z</b> to the <i>n</i>th 
 * root of an integer <i>d</i>. For example, <b>Z</b>[&#8731;5] is formed by 
 * adjoining <b>Z</b> to &#8731;5, and it consists of numbers of the form 
 * <i>a</i> + <i>b</i>&#8731;5 + <i>c</i>(&#8731;5)<sup>2</sup>, where <i>a</i>, 
 * <i>b</i>, <i>c</i> &isin; <b>Z</b>.
 * <p>Rings of this sort are distinguished from rings like the one formed by 
 * adjoining <b>Z</b> to a number like &radic;2 + &radic;3. There are even cubic 
 * rings involving numbers in which square roots are enclosed in cubic roots 
 * enclosed in fractions. Objects to represent rings such as those should 
 * implement the {@link IntegerRing} interface but not this interface.</p>
 * @author Alonso del Arte
 */
public interface SingleIntRootAdjoinedRing extends IntegerRing {
    
    // TODO: Write test for this
    default int getExponentForRadicand() {
        return 0;
    }
    
    /**
     * Retrieves the radicand for the one root that is adjoined to <b>Z</b> to 
     * form this ring. For the exponent, call {@link #getExponentForRadicand()}.
     * @return The integer <i>d</i> for the expression 
     * <sup><i>n</i></sup>&radic;<i>d</i>.
     */
    int getRadicand();
    
}
