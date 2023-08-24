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

    /**
     * Returns the set of inert primes, starting with 2 (when applicable) and 
     * going up to the <code>primePi</code> setting. If a subclass simply places 
     * the inert primes in the appropriate internal set, there is no need to 
     * override this function.
     * @return A set of inert primes. For example, for <b>Z</b>[&radic;7], the  
     * set should include 5, 11, 13, 17, 23, 41, 43, etc., as those are all 
     * inert in <b>Z</b>[&radic;7]. In other words, for those numbers <i>p</i>, 
     * the equation <i>x</i><sup>2</sup> &minus; 7<i>y</i><sup>2</sup> = 
     * <i>p</i> has no solution in integers. The set is a new copy of the 
     * internally held values, so the caller is free to modify it as needed.
     */
    public HashSet<T> inerts() {
        return new HashSet(this.inertialPrimes);
    }
    
    /**
     * Returns the map of split primes, starting with 2 (when applicable) and 
     * going up to the <code>primePi</code> setting, and their corresponding 
     * splitters. If a subclass simply places the split primes and their 
     * splitters in the appropriate internal map, there is no need to override 
     * this function.
     * @return A map of split primes and their splitters. For example, for 
     * <b>Z</b>[&radic;7], the map should include 3, since 3 splits as 
     * (&minus;1)(2 &minus; &radic;7)(2 + &radic;7). The key 3 should correspond 
     * to an <code>Optional</code> holding the value 2 + &radic;7. The map is a 
     * new copy of the internally held keys and values, so the caller is free to 
     * modify it as needed. 
     * <p>Note that in the case of domains without unique factorization, a prime 
     * might be listed among the split primes with an empty 
     * <code>Optional</code> for the splitter. Even with a unique factorization 
     * domain there is the possibility of an empty <code>Optional</code> if the 
     * calculation could not be performed in a timely manner or an overflow 
     * would have resulted.</p>
     */
    public HashMap<T, Optional<T>> splits() {
            return new HashMap<>(this.splitPrimes);
    }
    
    /**
     * Returns the map of ramified primes, starting with 2 (when applicable) and 
     * going up to the <code>primePi</code> setting, and their corresponding 
     * ramifiers. If a subclass simply places the ramified primes and their 
     * ramifiers in the appropriate internal map, there is no need to override 
     * this function.
     * @return A map of ramified primes and their ramifiers. For example, for 
     * <b>Z</b>[&radic;7], the map should definitely include 7, since 7 ramifies  
     * as (&radic;7)<sup>2</sup>. The key 7 should correspond to an 
     * <code>Optional</code> holding the value &radic;7. The map is a new copy 
     * of the internally held keys and values, so the caller is free to modify 
     * it as needed.
     * <p>Note that in the case of domains without unique factorization, a prime 
     * might be listed among the ramified primes with an empty 
     * <code>Optional</code> for the ramifier. Even with a unique factorization 
     * domain there is the possibility of an empty <code>Optional</code> if the 
     * calculation could not be performed in a timely manner or an overflow 
     * would have resulted.</p>
     */
    public HashMap<T, Optional<T>> ramifieds() {
            return new HashMap<>(this.ramifiedPrimes);
    }
    
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
