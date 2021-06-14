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
    
    /**
     * The default threshold for which to look for integers that are prime in 
     * <b>Z</b> but split or ramify in the ring for which this grouping holds 
     * results. This is hopefully sufficient for most purposes.
     */
    public static final int DEFAULT_PRIME_PI = 720;
    
    int primePi;

    private final IntegerRing domain;
    
    HashSet<T> inertialPrimes = new HashSet<>();
    
    HashMap<T, Optional<T>> splitPrimes = new HashMap<>();
    
    HashMap<T, Optional<T>> ramifiedPrimes = new HashMap<>();
    
    /**
     * Retrieves the ring object with which this results grouping object was 
     * created.
     * @return The same ring object that was passed to the constructor.
     */
    public IntegerRing getRing() {
        return this.domain;
    }
    
    /**
     * Retrieves the current <code>primePi</code> setting. This setting 
     * determines how far to look for inert primes.
     * @return The current <code>primePi</code> setting. If there have been no 
     * calls to {@link #raisePrimePi(int)}, this should be {@link 
     * #DEFAULT_PRIME_PI}.
     */
    public int getPrimePi() {
        return this.primePi;
    }
    
    /**
     * Raises the current <code>primePi</code> setting. Note that there is no 
     * checking for overflows (e.g., if the setting is currently 2<sup>16</sup> 
     * and a caller tries to raise it by another 2<sup>16</sup>).
     * @param increment An integer that is not negative, preferably positive. If 
     * 0, nothing happens.
     * @throws IllegalArgumentException If <code>increment</code> is negative.
     */
    public void raisePrimePi(int increment) {
        if (increment < 0) {
            String excMsg = "Increment should be positive but " + increment 
                    + " is negative";
            throw new IllegalArgumentException(excMsg);
        }
        this.primePi += increment;
    }

    public abstract HashSet<T> inerts();
    
    public abstract HashMap<T, Optional<T>> splits();
    
    public abstract HashMap<T, Optional<T>> ramifieds();
    
    /**
     * Sole constructor. The <code>primePi</code> setting is initialized to 
     * {@link #DEFAULT_PRIME_PI}.
     * @param ring A ring object, preferably related to the generic type 
     * parameter <code>T</code>.
     */
    public ResultsGrouping(IntegerRing ring) {
        this.domain = ring;
        this.primePi = DEFAULT_PRIME_PI;
    }
    
}
