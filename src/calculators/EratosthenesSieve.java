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
package calculators;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Sieve of Eratosthenes to list prime numbers up to a given threshold. Primes 
 * will be cached. They're not expensive to calculate, at least not in the range 
 * of primes that can fit in an <code>int</code> or even a <code>byte</code>, 
 * but having to recalculate the same primes over and over might slow things 
 * down unacceptably.
 * @author Alonso del Arte
 */
public class EratosthenesSieve {
    
    private static int currThresh = 100;
    
    private static final int[] FIRST_25_PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 
        23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
    
    /**
     * The list of prime numbers that have been stored so far during the current 
     * session. This is initialized with the first 25 positive primes: 2, 3, 5, 
     * 7, ..., 97.
     */
    private static final List<Integer> PRIMES = new ArrayList<>();
    
    static {
        for (int p : FIRST_25_PRIMES) {
            PRIMES.add(p);
        }
    }
    
    private static final Random RANDOM = new Random(-System.currentTimeMillis() 
            * 127 + 1);
    
    private static void increaseListLength(int target) {
        for (int n = currThresh + 1; n <= target; n++) {
            double root = Math.sqrt(n);
            boolean noDivisorFound = true;
            int index = 0;
            int p;
            do {
                p = PRIMES.get(index);
                noDivisorFound = (n % p != 0);
                index++;
            } while (p <= root && noDivisorFound);
            if (noDivisorFound) PRIMES.add(n);
        }
        currThresh = target;
    }
    
    /**
     * Gives a list of prime numbers up to a given threshold. If the threshold 
     * is below what has already been calculated, this function will simply 
     * return a sublist. Otherwise it will look for more primes to meet the 
     * specified threshold.
     * @param threshold The number to go up to, possibly including that number 
     * (if it is itself prime). For example, 50. May be negative, in which case 
     * it will be multiplied by &minus;1; e.g., &minus;50.
     * @return A list of primes, starting with 2 and ending with the largest 
     * prime not greater than the absolute value of <code>threshold</code>. For 
     * example, 2, 3, 5, 7, ..., 47. If <code>threshold</code> is &minus;1, 0 or 
     * 1, the returned list will be an empty list.
     */
    public static List<Integer> listPrimes(int threshold) {
        int thresh = Math.abs(threshold);
        if (thresh < 2) {
            return new ArrayList<>();
        }
        boolean signChangeNeeded = threshold < 0;
        if (thresh > currThresh) {
            increaseListLength(thresh);
        }
        List<Integer> list = new ArrayList<>(PRIMES);
        if (thresh < currThresh) {
            int trimIndex = list.size();
            int p;
            do {
                trimIndex--;
                p = PRIMES.get(trimIndex);
            } while (p > thresh);
            list.subList(trimIndex + 1, PRIMES.size()).clear();
        }
        if (signChangeNeeded) {
            List<Integer> negated = list.stream().map(n -> -n)
                    .collect(Collectors.toList());
            list = negated;
        }
        return list;
    }
    
    public static List<Integer> listPrimes(int start, int threshold) {
        List<Integer> primes = listPrimes(threshold);
        int index = 0;
        while (primes.get(index) < start) {
            index++;
        }
        return primes.subList(index, primes.size());
    }

    /**
     * Gives primes of a specified residue class. There are of course infinitely 
     * many primes in some residue classes, but how many primes are returned by 
     * this function will depend on how many primes have already been calculated 
     * prior to calling this function. This is not a pure function.
     * @param n The number of the residue class. For example, 3.
     * @param m The modulus. For example, 10.
     * @return A list of primes congruent to <code>n</code> modulo 
     * <code>m</code>. For example, 3, 13, 23, 43, 53, 73, 83, 103, 113, etc. 
     * The list may be empty, for example, if <code>n</code> is 4 and 
     * <code>m</code> is 8.
     */
    public static List<Integer> listPrimesMod(int n, int m) {
        return PRIMES.stream().filter(p -> p % m == n)
                .collect(Collectors.toList());
    }

    // TODO: Write tests for this
    public static int prime(int index) {
        return Integer.MIN_VALUE;
    }
    
    /**
     * Gives a pseudorandom prime for a given bound. This is not a pure 
     * function, a bound beyond the previous highest bound in a given session 
     * could cause some extra calculations to take place.
     * @param bound The bound. May be negative. Examples: 1000, &minus;1000.
     * @return A pseudorandom prime between 0 and <code>bound</code>. For 
     * example, for a bound of 1000, this function might give 883 or 617, etc.  
     * For a bound of &minus;1000, this function might give &minus;157 or 
     * &minus;991, etc.
     * @throws NoSuchElementException If <code>bound</code> is &minus;1, 0 or 1.
     */
    public static int randomPrime(int bound) {
        if (Math.abs(bound) < 2) {
            String excMsg = "There are no primes between 0 and " + bound;
            throw new NoSuchElementException(excMsg);
        }
        List<Integer> candidates = listPrimes(bound);
        int index = RANDOM.nextInt(candidates.size());
        return candidates.get(index);
    }
    
    /**
     * Gives a pseudorandom odd prime for a given bound. This is not a pure 
     * function, a bound beyond the previous highest bound in a given session 
     * could cause some extra calculations to take place.
     * @param bound The bound. May be negative. Examples: 1000, &minus;1000.
     * @return A pseudorandom prime between 0 and <code>bound</code>. For 
     * example, for a bound of 1000, this function might give 47 or 599, etc.  
     * For a bound of &minus;1000, this function might give &minus;797 or 
     * &minus;131, etc.
     * @throws NoSuchElementException If <code>bound</code> is &minus;2, 
     * &minus;1, 0, 1 or 2, given that none of those numbers are odd primes.
     */
    public static int randomOddPrime(int bound) {
        if (Math.abs(bound) < 3) {
            String excMsg = "There are no odd primes between 0 and " + bound;
            throw new NoSuchElementException(excMsg);
        }
        List<Integer> candidates = listPrimes(bound);
        int index = RANDOM.nextInt(candidates.size() - 1) + 1;
        return candidates.get(index);
    }
    
    /**
     * Gives a pseudorandom prime other than a specified prime. The pool of 
     * potential prime numbers is limited to those that have been calculated in 
     * the session so far.
     * @param p A prime number. However, this number is <em>not</em> checked for 
     * primality. For example, 47. May be negative. For example, &minus;709.
     * @return A prime number other than <code>p</code>, but of the same sign as 
     * <code>p</code>. For example, for <code>p</code> other than 47, this 
     * function might give 653, 521, 647, etc.; for <code>p</code> other than 
     * &minus;709, this function might give &minus;563, &minus;349, &minus;239, 
     * etc.
     * <p>Note that although for <code>p = 0</code> this function currently 
     * gives positive primes like 383 and 281, this behavior is not guaranteed 
     * to remain unchanged in later versions of this program.</p>
     */
    public static int randomPrimeOtherThan(int p) {
        int signum = (p < 0) ? -1 : 1;
        int absP = Math.abs(p);
        int curr = p;
        int bound = PRIMES.size();
        do {
            curr = PRIMES.get(RANDOM.nextInt(bound));
        } while (curr == absP);
        return curr * signum;
    }
    
    // TODO: Write tests for this
    public static int randomPrimeMod(int n, int m) {
        List<Integer> candidates = listPrimesMod(n, m);
        return candidates.get(RANDOM.nextInt(candidates.size()));
    }
    
}
