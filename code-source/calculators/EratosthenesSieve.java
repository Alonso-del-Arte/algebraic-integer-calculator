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

import java.util.ArrayList;

/**
 * Sieve of Eratosthenes to list prime numbers up to a given threshold. Primes 
 * will be cached. They're not expensive to calculate, but having to recalculate 
 * the same primes over and over might slow things down unacceptably.
 * @author Alonso del Arte
 */
public class EratosthenesSieve {
    
    private static int currThresh = 100;
    
    private static final int[] FIRST_25_PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 
        23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
    
    private static final ArrayList<Integer> PRIMES = new ArrayList<>();
    
    static {
        for (int p : FIRST_25_PRIMES) {
            PRIMES.add(p);
        }
    }
    
    public static ArrayList<Integer> listPrimes(int threshold) {
        int thresh = Math.abs(threshold);
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
    
}
