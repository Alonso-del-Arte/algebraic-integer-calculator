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
package algebraics;

import arithmetic.PowerBasis;

/**
 * This interface sets the basic requirements for objects representing rings of 
 * algebraic integers. The implementing classes should provide an algebraic 
 * degree function that gives the maximum algebraic degree an integer in the 
 * ring can have. The implementing classes should also provide ways to represent 
 * the given ring in TeX, HTML and ASCII documents.
 * @author Alonso del Arte
 */
public interface IntegerRing {
    
    /**
     * Gives the maximum algebraic degree an algebraic integer in the ring can 
     * have. It is recommended that this function be marked final at the highest 
     * level of the inheritance hierarchy that it is possible to do so.
     * @return A positive integer, e.g., 3 in the case of a cubic integer ring.
     */
    int getMaxAlgebraicDegree();
    
    /**
     * Indicates whether the ring is purely real or not. This is useful in 
     * contexts where the program has to distinguish in some way between real 
     * and imaginary rings.
     * @return True if all the nonzero numbers in the ring are purely real, 
     * false otherwise. For example, <b>Z</b>[<i>i</i>], the ring of Gaussian 
     * integers, contains purely imaginary numbers like <i>i</i> and complex 
     * numbers like 1 + <i>i</i>, so it's false that the ring is purely real. 
     * <b>Z</b>[&#8731;2], on the other hand, is purely real: 0 is the only 
     * purely imaginary number, and the ring has no numbers with nonzero 
     * imaginary part.
     */
    boolean isPurelyReal();
    
    /**
     * Gives the discriminant of the ring. This number pertains to the prime 
     * numbers ramifying in the ring.
     * @return A nonzero integer, may be negative. For example, for 
     * <b>Z</b>[<i>i</i>], this would be &minus;4; for <b>Z</b>[&#8731;2] this 
     * would be &minus;108; and for the ring of integers of <b>Q</b>(&radic;2 + 
     * &radic;7), this would be 12544.
     */
    int discriminant();
    
    /**
     * Gives the power basis of the ring. For example, the power basis of 
     * <i>O</i><sub><b>Q</b>(&radic;2 + &radic;7)</sub> is 1, <i>a</i>, 
     * <i>a</i><sup>2</sup>, <sup>1</sup>&frasl;<sub>3</sub><i>a</i><sup>3</sup> 
     * + <sup>1</sup>&frasl;<sub>3</sub><i>a</i>.
     * @return An object that can be queried for the elements of the power 
     * basis, which can then in turn be used to perform arithmetic on numbers 
     * from the ring.
     */
    PowerBasis getPowerBasis();
    
    /**
     * Formats the ring's label as a String using ASCII characters only. It is 
     * strongly recommended that any implementations of IntegerRing also 
     * override {@link Object#toString}.
     * @return A String. For example, for <b>Z</b>[&radic;2], this would be 
     * "Z[sqrt(2)]".
     */
    String toASCIIString();
    
    /**
     * Formats the ring's label as a String that can be used in a TeX document. 
     * It is strongly recommended that any implementations of IntegerRing also 
     * override {@link Object#toString}.
     * @return A String. For example, for <b>Z</b>[&#8731;2], this might be 
     * "\mathbb Z[\root 3 \of 2]".
     */
    String toTeXString();
    
    /**
     * Formats the ring's label as a String that can be used in an HTML 
     * document. It is strongly recommended that any implementations of 
     * IntegerRing also override {@link Object#toString}.
     * @return A String. For example, for <b>Z</b>[&#8731;2], this might be 
     * "<b>Z</b>[&#8731;2]".
     */
    String toHTMLString();
    
    /**
     * Formats the ring's label as a String that could theoretically be used in 
     * an old MS-DOS file save dialog. It is strongly recommended that any 
     * implementations of IntegerRing also override {@link Object#toString}.
     * @return A String. For example, for <b>Z</b>[&#8731;2], this might be 
     * "ZCBRT2".
     */
    String toFilenameString();
    
}
