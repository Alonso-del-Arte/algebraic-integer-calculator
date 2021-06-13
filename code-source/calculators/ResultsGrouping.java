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
package calculators;

import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * Defines a grouping of results about a ring of algebraic integers.
 * @param <T> A type implementing the {@link AlgebraicInteger} interface.
 * @author Alonso del Arte
 */
public abstract class ResultsGrouping<T extends AlgebraicInteger> {

    private final IntegerRing domain;
    
    HashSet<T> inertialPrimes = new HashSet<>();
    
    HashMap<T, Optional<T>> splitPrimes = new HashMap<>();
    
    HashMap<T, Optional<T>> ramifiedPrimes = new HashMap<>();
    
    public IntegerRing getRing() {
        return this.domain;
    }
    
    public abstract HashSet<T> inerts();
    
    public abstract HashMap<T, Optional<T>> splits();
    
    public abstract HashMap<T, Optional<T>> ramifieds();
    
    public ResultsGrouping(IntegerRing ring) {
        this.domain = ring;
    }
    
}
