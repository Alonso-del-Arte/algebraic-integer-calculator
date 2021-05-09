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
package arithmetic.comparators;

import algebraics.AlgebraicInteger;

import java.util.Comparator;

/**
 * A comparator for use in sorting procedures or functions from the JDK, to sort 
 * instances of {@link AlgebraicInteger} by the absolute values of their norms. 
 * Another comparator is necessary if you need to sort algebraic instances with 
 * the same absolute value of norm by some other criterion.
 * <p>An early version of {@link calculators.NumberTheoreticFunctionsCalculator} 
 * had a <code>sortListAlgebraicIntegersByNorm()</code> function that was 
 * deprecated in Version 0.9 and removed completely before Version 1.0.</p>
 * @since Version 0.9
 * @author Alonso del Arte
 */
public class NormAbsoluteComparator implements Comparator<AlgebraicInteger> {
    
    /**
     * Compares two algebraic integers according to the absolute values of their 
     * norms. The two algebraic integers ought to be from the same ring, but 
     * this function does not check that the rings match.
     * @param numberA The first number to compare. For example, 2 + &radic;7, 
     * which has norm &minus;3.
     * @param numberB The second number to compare. Three examples: 3 + 
     * &radic;7, which has norm 2; 2 + &radic;7, which has norm &minus;3; and 
     * &minus;7 + 2&radic;7, which has norm 21.
     * @return A negative integer if <code>numberA</code> has a norm with lesser 
     * absolute value than <code>numberB</code>, 0 if <code>numberA</code> and 
     * <code>numberB</code> have the same absolute value of norm, and a positive 
     * integer if <code>numberA</code> has a norm with greater absolute value 
     * than <code>numberB</code>. For example, given <code>numberA</code> being 
     * 2 + &radic;7, if <code>numberB</code> is 3 + &radic;7, this function will 
     * return 1 or greater (because, although &minus;3 &lt; 2, this goes by 
     * absolute value, so we have that 3 &gt; 2); if <code>numberB</code> is 2 +
     * &radic;7, this function will return 0; and if <code>numberB</code> is 
     * &minus;7 + 2&radic;7, this function will return &minus;1 or less.
     */
    @Override
    public int compare(AlgebraicInteger numberA, AlgebraicInteger numberB) {
        return Long.compare(Math.abs(numberA.norm()), Math.abs(numberB.norm()));
    }
    
}
