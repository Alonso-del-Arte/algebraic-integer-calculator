/*
 * Copyright (C) 2020 Alonso del Arte
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

import java.util.Comparator;

/**
 * A comparator for use in sorting procedures or functions from the JDK, to sort 
 * instances of {@link AlgebraicInteger} by norm.
 * An early version of {@link calculators.NumberTheoreticFunctionsCalculator} 
 * has a <code>sortListAlgebraicIntegersByNorm()</code> function that is 
 * deprecated as of Version 0.9 and will be removed before Version 1.0.
 * @since Version 0.9
 * @author Alonso del Arte
 */
public class NormAbsoluteComparator implements Comparator<AlgebraicInteger> {
    
    // TODO: Write tests for this
    // TODO: Write Javadoc
    @Override
    public int compare(AlgebraicInteger numberA, AlgebraicInteger numberB) {
        return 0;
    }
    
}
