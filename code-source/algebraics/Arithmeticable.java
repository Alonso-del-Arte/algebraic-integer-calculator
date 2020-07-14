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

// TODO: Flesh out Javadoc.
/**
 * For implementations of {@link AlgebraicInteger}.
 * @param <T> An implementation of {@link AlgebraicInteger}.
 * @author Alonso del Arte
 */
public interface Arithmeticable<T extends AlgebraicInteger> {
    
    T plus(T addend);
    
    T plus(int addend);
    
    T negate();
    
    T minus(T subtrahend);
    
    T minus(int subtrahend);
    
    T times(T multiplicand);
    
    T times(int multiplicand);
    
    T divides(T divisor);
    
    T divides(int divisor);
    
}
