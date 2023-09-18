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
package arithmetic.comparators;

import algebraics.AlgebraicInteger;

import java.util.Comparator;

/**
 * A comparator for use in sorting procedures or functions from the JDK, to sort 
 * instances of {@link AlgebraicInteger} by norm. Another comparator is 
 * necessary if you need to sort algebraic instances with the same norm by some 
 * other criterion.
 * <p>An early version of {@link calculators.NumberTheoreticFunctionsCalculator} 
 * had a <code>sortListAlgebraicIntegersByNorm()</code> function that was 
 * deprecated in Version 0.9 and removed completely before Version 1.0.</p>
 * @since Version 0.9
 * @author Alonso del Arte
 */
public class NormComparator implements Comparator<AlgebraicInteger> {
    
    /**
     * Compares two algebraic integers according to norm. The two algebraic 
     * integers ought to be from the same ring, but this function does not check 
     * that the rings match.
     * @param numberA The first number to compare. For example, 3 + &radic;7, 
     * which has norm 2.
     * @param numberB The second number to compare. Three examples: 2 + 
     * &radic;7, which has norm &minus;3; 3 + &radic;7, which has norm 2; and 
     * &minus;7 + 2&radic;7, which has norm 21.
     * @return A negative integer if <code>numberA</code> has lesser norm than 
     * <code>numberB</code>, 0 if <code>numberA</code> and <code>numberB</code> 
     * have the same norm, and a positive integer if <code>numberA</code> has 
     * greater norm than <code>numberB</code>. For example, given 
     * <code>numberA</code> being 3 + &radic;7, if <code>numberB</code> is 2 + 
     * &radic;7, this function will return 1 or greater; if <code>numberB</code> 
     * is 3 + &radic;7, this function will return 0; and if <code>numberB</code> 
     * is &minus;7 + 2&radic;7, this function will return &minus;1 or less.
     */
    @Override
    public int compare(AlgebraicInteger numberA, AlgebraicInteger numberB) {
        return 0;// Long.compare(numberA.norm(), numberB.norm());
    }
    
}
