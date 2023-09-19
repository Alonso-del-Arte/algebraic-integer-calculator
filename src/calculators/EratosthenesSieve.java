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
    private static final ArrayList<Integer> PRIMES = new ArrayList<>();
    
    static {
        for (int p : FIRST_25_PRIMES) {
            PRIMES.add(p);
        }
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
        if (thresh < currThresh) {
            int trimIndex = PRIMES.size();
            int p;
            do {
                trimIndex--;
                p = PRIMES.get(trimIndex);
            } while (p > thresh);
            return new ArrayList<>(PRIMES.subList(0, trimIndex + 1));
        }
        if (thresh > currThresh) {
            for (int n = currThresh + 1; n <= thresh; n++) {
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
            currThresh = thresh;
        }
        return new ArrayList<>(PRIMES);
    }
    
    public static List<Integer> listPrimes(int start, int threshold) {
        List<Integer> primes = listPrimes(threshold);
        int index = 0;
        while (primes.get(index) < start) {
            index++;
        }
        return primes.subList(index, primes.size());
    }

    // TODO: Write tests for this
    public static List<Integer> listPrimesMod(int n, int m) {
        List<Integer> primes = new ArrayList<>();
        return primes;
    }

    // TODO: Write tests for this
    public static int prime(int index) {
        return Integer.MIN_VALUE;
    }
    

    
}
