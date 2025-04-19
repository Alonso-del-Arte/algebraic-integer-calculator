/*
 * Copyright (C) 2025 Alonso del Arte
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

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import arithmetic.Arithmeticable;
import arithmetic.NonEuclideanDomainException;
import arithmetic.NonUniqueFactorizationDomainException;
import arithmetic.NotDivisibleException;
import arithmetic.comparators.NormAbsoluteComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * A collection of number theoretic functions, including basic primality testing 
 * and the Euclidean GCD algorithm.
 * @author Alonso del Arte
 */
public class NumberTheoreticFunctionsCalculator {
    
    private static final NormAbsoluteComparator COMPARATOR 
            = new NormAbsoluteComparator();
    
    /**
     * The only twenty-one values of <i>d</i> such that 
     * <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> is a unique factorization 
     * domain and a norm-Euclidean domain. These numbers are listed in <a 
     * href="http://oeis.org/A048981">Sloane's A048981</a>.
     */
    public static final int[] NORM_EUCLIDEAN_QUADRATIC_RINGS_D = {-11, -7, -3, 
        -2, -1, 2, 3, 5, 6, 7, 11, 13, 17, 19, 21, 29, 33, 37, 41, 57, 73};
    
    /**
     * The only five negative values <i>d</i> such that 
     * <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> is a norm-Euclidean domain. 
     * These numbers, &minus;11, &minus;7, &minus;3, &minus;2, &minus;1, are the 
     * first five terms listed in <a href="http://oeis.org/A048981">Sloane's 
     * A048981</a>. These numbers correspond to <b>Z</b>[<i>i</i>], 
     * <b>Z</b>[&radic;&minus;2], <b>Z</b>[&omega;], 
     * <i>O</i><sub><b>Q</b>(&radic;&minus;7)</sub> and 
     * <i>O</i><sub><b>Q</b>(&radic;&minus;11)</sub>.
     */
    public static final int[] NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D = {-11, 
        -7, -3, -2, -1};

    /**
     * The sixteen positive values <i>d</i> such that 
     * <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> is a norm-Euclidean domain. 
     * These numbers are listed in <a href="http://oeis.org/A048981">Sloane's 
     * A048981</a> (together with five negative values). They correspond to 
     * <b>Z</b>[&radic;2], <b>Z</b>[&radic;3], <b>Z</b>[&radic;&phi;], 
     * <b>Z</b>[&radic;6], <b>Z</b>[&radic;7], <b>Z</b>[&radic;11], 
     * <i>O</i><sub><b>Q</b>(&radic;13)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;17)</sub>, <b>Z</b>[&radic;19], 
     * <i>O</i><sub><b>Q</b>(&radic;21)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;29)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;33)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;37)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;41)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;57)</sub> and 
     * <i>O</i><sub><b>Q</b>(&radic;73)</sub>.
     */
    public static final int[] NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D = {2, 3, 5, 
        6, 7, 11, 13, 17, 19, 21, 29, 33, 37, 41, 57, 73};
    
    /**
     * There are the only nine negative numbers d such that the ring of 
     * algebraic integers of <b>Q</b>(&radic;d) is a unique factorization domain 
     * (though not necessarily Euclidean). These numbers are, in descending 
     * order: -1, -2, -3, -7, -11, -19, -43, -67, -163 (in this constant array, 
     * they are in the reverse order). These correspond to <b>Z</b>[i], 
     * <b>Z</b>[&radic;-2], <b>Z</b>[&omega;], 
     * <i>O</i><sub><b>Q</b>(&radic;-7)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;-11)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;-19)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;-43)</sub>, 
     * <i>O</i><sub><b>Q</b>(&radic;-67)</sub> and 
     * <i>O</i><sub><b>Q</b>(&radic;-163)</sub>.
     */
    public static final int[] HEEGNER_NUMBERS = {-163, -67, -43, -19, -11, -7, 
        -3, -2, -1};
    
    /**
     * The ring of Gaussian integers, <b>Z</b>[<i>i</i>].
     */
    public static final ImaginaryQuadraticRing RING_GAUSSIAN 
            = new ImaginaryQuadraticRing(-1);
    
    /**
     * The imaginary unit <i>i</i> = &radic;-1.
     */
    public static final ImaginaryQuadraticInteger IMAG_UNIT_I 
            = new ImaginaryQuadraticInteger(0, 1, RING_GAUSSIAN, 1);
    
    /**
     * The additive inverse of the imaginary unit, -<i>i</i> = -&radic;-1.
     */
    public static final QuadraticInteger IMAG_UNIT_NEG_I = IMAG_UNIT_I.times(-1);
    
    /**
     * The ring of Eisenstein integers, <b>Z</b>[&omega;].
     */
    public static final ImaginaryQuadraticRing RING_EISENSTEIN 
            = new ImaginaryQuadraticRing(-3);
    
    /**
     * A complex cubic root of unity, &omega; = -1/2 + (&radic;-3)/2, a number 
     * such that &omega;<sup>3</sup> = 1.
     */
    public static final ImaginaryQuadraticInteger COMPLEX_CUBIC_ROOT_OF_UNITY 
            = new ImaginaryQuadraticInteger(-1, 1, RING_EISENSTEIN, 2);
    
    /**
     * <b>Z</b>[&phi;], the ring of algebraic integers of <b>Q</b>(&radic;5).
     */
    public static final RealQuadraticRing RING_ZPHI = new RealQuadraticRing(5);
    
    /**
     * The number 1/2 + (&radic;5)/2 &asymp; 1.618, the fundamental unit of 
     * <b>Z</b>[&phi;].
     */
    public static final RealQuadraticInteger GOLDEN_RATIO 
            = new RealQuadraticInteger(1, 1, RING_ZPHI, 2);
    
    // TODO: Better algorithm so as to not need units cache
    private static final HashMap<IntegerRing, AlgebraicInteger> UNITS_CACHE 
            = new HashMap<>();
    
    static {
        RealQuadraticRing ring = new RealQuadraticRing(139);
        RealQuadraticInteger unit = new RealQuadraticInteger(77563250, 6578829, 
                ring);
        UNITS_CACHE.put(ring, unit);
        ring = new RealQuadraticRing(151);
        unit = new RealQuadraticInteger(1728148040, 140634693, ring);
        UNITS_CACHE.put(ring, unit);
        ring = new RealQuadraticRing(166);
        unit = new RealQuadraticInteger(1700902565, 132015642, ring);
        UNITS_CACHE.put(ring, unit);
    }
    
    /**
     * Under the assumption the units of real quadratic integer rings with 
     * "surd" part greater than a certain threshold take much more time to 
     * calculate, at least with a brute force search, a static cache is 
     * maintained. If the "surd" part of a found unit is greater than this 
     * threshold, the unit is added to the cache. For example, the fundamental 
     * unit of <b>Z</b>[&radic;102] is 101 + 10&radic;102, which can be 
     * recalculated quickly, even by brute force, so it's not cached. But the 
     * fundamental unit of <b>Z</b>[&radic;103] is 227528 + 22419&radic;103, 
     * which is probably very expensive to have to do a brute force search for 
     * more than once.
     */
    private static final int SURD_PART_CACHE_THRESHOLD = 1000;
    
    private static final HashMap<IntegerRing, Integer> CLASS_NUMBERS_CACHE 
            = new HashMap<>();
    
    static {
        RealQuadraticRing ring = new RealQuadraticRing(199);
        CLASS_NUMBERS_CACHE.put(ring, 1);
    }
    
    private static final Random RANDOM = new Random();
    
    /**
     * Calculates the remainder of the division of <i>n</i> by <i>m</i>. This 
     * function gives the same result as the <code>%</code> operator if <i>n</i> 
     * and <i>m</i> are both positive. But if <i>n</i> is negative and <i>m</i> 
     * is positive, this function will return either 0 or a positive integer, 
     * whereas the <code>%</code> operator returns a negative number. Both 
     * values, and infinitely many others, are mathematically correct, but our 
     * expectation is for the return value to be at least 0 and less than the 
     * modulo.
     * @param n The number to be divided by the modulo. For example, &minus;118.
     * @param m The modulo. For example, 30.
     * @return Given positive <code>m</code>, either 0 or a positive integer 
     * less than <code>m</code> according to whether or not <code>n</code> 
     * divides <code>m</code> evenly. For example, 2, since &minus;4 &times; 30 
     * + 2 = &minus;118. This function has not yet been tested for negative 
     * <code>m</code>.
     * @throws ArithmeticException If <code>m</code> equals 0, as the 
     * calculation involves division by 0.
     */
    public static int mod(int n, int m) {
        if (m == 0) {
            String excMsg = "Calculating " + n 
                    + " modulo 0 involves division by 0";
            throw new ArithmeticException(excMsg);
        }
        int intermediate = n % m;
        if (intermediate < 0) {
            return m + intermediate;
        } else {
            return intermediate;
        }
    }
    
    /**
     * Calculates the remainder of the division of <i>n</i> by <i>m</i>. This 
     * function gives the same result as the <code>%</code> operator if <i>n</i> 
     * and <i>m</i> are both positive. But if <i>n</i> is negative and <i>m</i> 
     * is positive, this function will return either 0 or a positive integer, 
     * whereas the <code>%</code> operator returns a negative number. Both 
     * values, and infinitely many others, are mathematically correct, but our 
     * expectation is for the return value to be at least 0 and less than the 
     * modulo.
     * @param n The number to be divided by the modulo. For example, 
     * &minus;1269933473360286688.
     * @param m The modulo. For example, 30.
     * @return Given positive <code>m</code>, either 0 or a positive integer 
     * less than <code>m</code> according to whether or not <code>n</code> 
     * divides <code>m</code> evenly. For example, 2, since 
     * &minus;42331115778676223 &times; 30 + 2 = &minus;1269933473360286688. 
     * This function has not yet been tested for negative <code>m</code>.
     * @throws ArithmeticException If <code>m</code> equals 0, as the 
     * calculation involves division by 0.
     */
    public static long mod(long n, long m) {
        if (m == 0) {
            String excMsg = "Calculating " + n 
                    + " modulo 0 involves division by 0";
            throw new ArithmeticException(excMsg);
        }
        long intermediate = n % m;
        if (intermediate < 0) {
            return m + intermediate;
        } else {
            return intermediate;
        }
    }

    /**
     * Determines the prime factors of a given number. Uses simple trial 
     * division with only basic optimization.
     * @param num The integer for which to determine prime factors of.
     * @return A list of the prime factors, with some factors repeated as 
     * needed. For example, given num = 44100, the resulting list should be 2, 
     * 2, 3, 3, 5, 5, 7, 7. The factorization of 0 is given as just 0. For a 
     * negative number, the factorization starts with -1 followed by the 
     * factorization of its positive counterpart. For example, given num = 
     * -44100, the resulting list should be -1, 2, 2, 3, 3, 5, 5, 7, 7.
     * <p>Special cases:</p>
     * <ul>
     * <li></li>
     * <li>For 0, this function returns a list of one or more zeroes. This 
     * behavior might change in a later version.</li>
     * <li></li>
     * </ul>
     * @since Version 0.1
     */
    public static List<Integer> primeFactors(int num) {
        int n = num;
        List<Integer> factors = new ArrayList<>();
        if (n == 0 || n == 1) {
            factors.add(num);
        } else {
            if (num < 0) {
                n *= (-1);
                factors.add(-1);
            }
            while (n % 2 == 0) {
                factors.add(2);
                n /= 2;
            }
            for (int i = 3; i <= n; i += 2) {
                while (n % i == 0) {
                    factors.add(i);
                    n /= i;
                }
            }
        }
        return factors;
    }
    
    /**
     * Determines whether a given purely real number is prime or not. The 
     * numbers 0, &minus;1, 1, &minus;2, 2 are treated as special cases (the 
     * first three are not prime, the last two are). For all others, the 
     * function searches only for the least positive prime factor. If the least 
     * positive prime factor is found to be unequal to the absolute value of the 
     * number, the function returns false. This function is open to 
     * optimization.
     * @param num The number to be tested for primality. Examples: &minus;29, 
     * 30, &minus;42, 43.
     * @return True if the number is prime (even if negative), false otherwise.
     * For example, &minus;2 and 47 should both return true, &minus;25, 0 and 91 
     * should all return false.
     * @since Version 0.1
     */
    public static boolean isPrime(int num) {
        switch (num) {
            case -1:
            case 0:
            case 1:
                return false;
            case -2:
            case 2:
                return true;
            default:
                if (num % 2 == 0) {
                    return false;
                } else {
                    boolean primeFlag = true;
                    int potentialFactor = 3;
                    double numSqrt = Math.sqrt(Math.abs(num));
                    while (primeFlag && potentialFactor <= numSqrt) {
                        primeFlag = (num % potentialFactor != 0);
                        potentialFactor += 2;
                    }
                    return primeFlag;
                }
        }
    }
    
    /**
     * Determines whether a given purely real number is prime or not.
     * @param num The number to be tested for primality.
     * @return True if the number is prime, false otherwise.
     * @since Version 0.1
     */
    public static boolean isPrime(long num) {
        if (num == -1 || num == 0 || num == 1) {
            return false;
        }
        if (num == -2 || num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        } else {
            boolean primeFlag = true;
            long potentialFactor = 3;
            double numSqrt = Math.sqrt(Math.abs(num));
            while (primeFlag && potentialFactor <= numSqrt) {
                primeFlag = (num % potentialFactor != 0);
                potentialFactor += 2;
            }
            return primeFlag;
        }
    }
    
    /**
     * Determines whether a given algebraic integer is prime or not. It is not 
     * enough for the number to be irreducible, it must be prime and not a unit, 
     * meaning that if <i>p</i> divides <i>ab</i> for any combination of 
     * <i>a</i> and <i>b</i> from the same ring as <i>p</i>, then either 
     * <i>p</i> divides <i>a</i> or it divides <i>b</i>, maybe both.
     * @param num The number to be tested for primality. Examples: 1 + 
     * &radic;&minus;2, 2 + &radic;10.
     * @return True if the number is prime, false if a unit or composite. For 
     * example, 1 + &radic;&minus;2 is prime, while 2 + &radic;10 is irreducible 
     * but not prime.
     * @throws ArithmeticException If a norm computation error occurs (this is a 
     * runtime exception), such as, for example, if the norm of an imaginary 
     * quadratic integer is erroneously said to be negative.
     * @throws UnsupportedNumberDomainException Thrown when called upon a number
     * from a type of ring that is not fully supported yet. For example, as of 
     * 2018, this program does not really have support for cubic integers, so 
     * asking if 3 &minus; &#8731;2 is prime would probably trigger this 
     * exception.
     * @since Version 0.5
     */
    public static boolean isPrime(AlgebraicInteger num) {
        if (isPrime(num.norm())) {
            return true;
        }
        if (num instanceof ImaginaryQuadraticInteger) {
            if (num.norm() < 0) {
                String excMsg = "Overflow has occurred for the computation of the norm of " 
                        + num.toASCIIString();
                throw  new ArithmeticException(excMsg);
            }
        }
        if (num instanceof ImaginaryQuadraticInteger || num instanceof RealQuadraticInteger) {
            QuadraticInteger n = (QuadraticInteger) num;
            int radic = n.getRing().getRadicand();
            int surdPart = n.getSurdPartMult();
            if (radic == -1 && n.getRegPartMult() == 0) {
                if (isPrime(surdPart)) {
                    return ((Math.abs(surdPart)) % 4 == 3);
                } else {
                    return false;
                }
            }
            if (radic == -3 && surdPart != 0) {
                QuadraticInteger pureReal = n.times(COMPLEX_CUBIC_ROOT_OF_UNITY);
                if (pureReal.getSurdPartMult() != 0) {
                    pureReal = pureReal.times(COMPLEX_CUBIC_ROOT_OF_UNITY);
                }
                if (pureReal.getSurdPartMult() == 0) {
                    if (pureReal.getRegPartMult() < 0) {
                        pureReal = pureReal.times(-1);
                    }
                    if (isPrime(pureReal.getRegPartMult())) {
                        return (pureReal.getRegPartMult() % 3 == 2);
                    } else {
                        return false;
                    }
                }
            }
            if (surdPart == 0) {
                int absRegPartMult = Math.abs(n.getRegPartMult());
                if (absRegPartMult == 2) {
                    byte symbol = symbolKronecker(radic, 2);
                    if (radic % 4 == -1 || radic % 4 == 3) {
                        symbol = 0;
                    }
                    return (symbol == -1);
                }
                if (isPrime(absRegPartMult)) {
                    switch (radic) {
//                        case -163:
//                        case -67:
//                        case -43:
//                        case -19:
//                        case -11:
//                        case -7:
//                            return (symbolLegendre(num.imagQuadRing.negRad, absRealPartMult) == -1);
                        case -3:
                            return (absRegPartMult % 3 == 2);
                        case -2:
                            return (absRegPartMult % 8 == 5 || absRegPartMult % 8 == 7);
                        case -1:
                            return (absRegPartMult % 4 == 3);
//                        cases 2, 3, 5, 6, 7, 11, 13, 17, 19, 21, 29, 33, 37, 41, 57, 73:
                        default:
                            return (symbolLegendre(radic, absRegPartMult) == -1);
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        String excMsg = "The domain of the number " + num.toASCIIString() 
                + " is not yet supported";
        throw new UnsupportedNumberDomainException(excMsg, num);
    }
    
    public static byte symbolLegendre(int a, int p) {
        return -100;
    }
    
    /**
     * The Jacobi symbol, a number theoretic function. This implementation is 
     * almost entirely dependent on the Legendre symbol.
     * @param n Parameter n, for example, 8.
     * @param m Parameter m, for example, 15.
     * @return The result, for example, 1.
     * @throws IllegalArgumentException If m is even or negative (or both). Note 
     * that this is a runtime exception.
     * @since Version 0.2
     */
    public static byte symbolJacobi(int n, int m) {
        if (m % 2 == 0) {
            String excMsg = "The number " + m 
                    + " is not an odd number. Consider using the Kronecker symbol instead.";
            throw new IllegalArgumentException(excMsg);
        }
        if (m < 0) {
            String excMsg = "The number " + m 
                    + " is not a positive number. Consider using the Kronecker symbol instead.";
            throw new IllegalArgumentException(excMsg);
        }
        if (m == 1) {
            return 1;
        }
        if (euclideanGCD(n, m) > 1) {
            return 0;
        }
        List<Integer> mFactors = primeFactors(m);
        List<Integer> symbols = new ArrayList<>();
        int curr;
        for (Integer mFactor : mFactors) {
            curr = symbolLegendre(n, mFactor);
            symbols.add(curr);
        }
        int symbol = symbols.stream().reduce(1, (a, b) -> a * b);
        return (byte) symbol;
    }
    
    private static byte symbolKroneckerNegOne(int n) {
        if (n < 0) {
            return -1;
        } else {
            return 1;
        }
    }
    
    private static byte symbolKroneckerTwo(int n) {
        int nMod8 = n % 8;
        switch (nMod8) {
            case -7:
            case -1:
            case 1:
            case 7:
                return 1;
            case -5:
            case -3:
            case 3:
            case 5:
                return -1;
            default:
                return 0;
        }
    }
    
    /**
     * The Kronecker symbol, a number theoretic function. This implementation 
     * relies in great part on the Legendre symbol, but not at all on the Jacobi 
     * symbol.
     * @param n Parameter n, for example, 3.
     * @param m Parameter m, for example, 2.
     * @return The result, for example, &minus;1.
     * @since Version 0.3
     */
    public static byte symbolKronecker(int n, int m) {
        if (euclideanGCD(n, m) > 1) {
            return 0;
        }
        if (m == 1) {
            return 1;
        }
        if (m == 0) {
            if (n == -1 || n == 1) {
                return 1;
            } else {
                return 0;
            }
        }
        List<Integer> mFactors = primeFactors(m);
        int currMFactorIndex = 0;
        int kindaOmega = mFactors.size();
        byte symbol = 1;
        if (mFactors.get(currMFactorIndex) == -1) {
            symbol *= symbolKroneckerNegOne(n);
            currMFactorIndex++;
        }
        int currFactor;
        boolean keepGoing = true; // Keep going with Kronecker(n, 2)?
        while (currMFactorIndex < kindaOmega && keepGoing) {
            currFactor = mFactors.get(currMFactorIndex);
            keepGoing = (currFactor == 2);
            if (keepGoing) {
                symbol *= symbolKroneckerTwo(n);
                currMFactorIndex++;
            }
        }
        while (currMFactorIndex < kindaOmega) {
            currFactor = mFactors.get(currMFactorIndex);
            symbol *= symbolLegendre(n, currFactor);
            currMFactorIndex++;
        }
        return symbol;
    }
    
    private static boolean isImQuadUFD(ImaginaryQuadraticRing ring) {
        int d = ring.getRadicand();
        boolean ufdFlag = false;
        if (d > -164) {
            for (int heegNum : HEEGNER_NUMBERS) {
                if (d == heegNum) {
                    ufdFlag = true;
                }
            }
        }
        return ufdFlag;
    }
    
    private static boolean isReQuadRUFD(RealQuadraticRing ring) {
        int d = ring.getRadicand();
        boolean ufdFlag = false;
        if (d > 0) {
            for (int normEuclRealD : NORM_EUCLIDEAN_QUADRATIC_REAL_RINGS_D) {
                if (d == normEuclRealD) {
                    ufdFlag = true;
                }
            }
            if (!ufdFlag) {
                ufdFlag = fieldClassNumber(ring) == 1;
            }
        }
        return ufdFlag;
    }
    
    /**
     * Determines whether or not a particular integer ring is a unique 
     * factorization domain (UFD) or not. For an imaginary quadratic ring 
     * <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> (where <i>d</i> is a 
     * negative squarefree integer), the operation is a simple matter of 
     * checking whether <i>d</i> is one of nine numbers known as the Heegner 
     * numbers (&minus;163, &minus;67, &minus;43, &minus;19, &minus;11, 
     * &minus;7, &minus;3, &minus;2, &minus;1). For other kinds of rings, a 
     * field class number computation may be necessary.
     * @param ring The ring for which to make the determination. Two examples: 
     * <i>O</i><sub><b>Q</b>(&radic;29)</sub> and <b>Z</b>[&radic;30].
     * @return True if the ring is a unique factorization domain, false 
     * otherwise. For example, true for <i>O</i><sub><b>Q</b>(&radic;29)</sub>, 
     * false for <b>Z</b>[&radic;30].
     * @throws ArithmeticException If an overflow occurred in the computation of 
     * a fundamental unit (this is applicable to real quadratic rings and many 
     * other kinds of rings, but not all rings).
     * @throws NullPointerException If <code>ring</code> is null.
     * @throws UnsupportedNumberDomainException If the kind of ring is not 
     * currently supported. For example, a cubic integer ring. The occurrence of 
     * this exception does not rule out the possibility that the kind of ring 
     * for which it occurred might be supported in a future version of this 
     * program.
     */
    public static boolean isUFD(IntegerRing ring) {
        if (ring == null) {
            String excMsg = "Can't determine if null ring is UFD";
            throw new NullPointerException(excMsg);
        }
        if (ring instanceof ImaginaryQuadraticRing) {
            return isImQuadUFD((ImaginaryQuadraticRing) ring);
        }
        if (ring instanceof RealQuadraticRing) {
            return isReQuadRUFD((RealQuadraticRing) ring);
        }
        String excMsg = "The domain " + ring.toASCIIString() 
                + " is not yet supported for UFD determination";
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    private static List<AlgebraicInteger> factorize(QuadraticInteger number) {
        ArrayList<AlgebraicInteger> factors = new ArrayList<>();
        QuadraticInteger unity = number.minus(number).plus(1);
        int d = number.getRing().getRadicand();
        if ((d < 0) && (number.norm() < 0)) {
            String excMsg = "A norm computation error occurred for " 
                    + number.toASCIIString() + ", which should not have norm " 
                    + number.norm();
            throw new ArithmeticException(excMsg);
        }
        if (Math.abs(number.norm()) < 2) {
            factors.add(number);
            return factors;
        }
        if (isPrime(number)) {
            factors.add(unity);
            factors.add(number);
            number = unity; // Prime divided by itself is 1
        } else {
            QuadraticInteger testDivisor = unity.plus(1); // Should be 2
            boolean keepGoing = true;
            if (isPrime(testDivisor)) {
                while (number.norm() % 4 == 0) {
                    try {
                        number = number.divides(testDivisor);
                        factors.add(testDivisor);
                    } catch (NotDivisibleException nde) {
                        keepGoing = ((Math.abs(nde.getNumericRealPart()) > 1) || (Math.abs(nde.getNumericImagPart()) > 1));
                    }
                }
            }
            testDivisor = testDivisor.plus(1);
            while (Math.abs(number.norm()) > Math.abs(testDivisor.norm()) 
                    && keepGoing) {
                if (isPrime(testDivisor)) {
                    while (number.norm() % testDivisor.norm() == 0) {
                        try {
                            number = number.divides(testDivisor);
                            factors.add(testDivisor);
                        } catch (NotDivisibleException nde) {
                            if (d < 0) {
                                keepGoing = ((Math.abs(nde.getNumericRealPart()) > 1) 
                                        || (Math.abs(nde.getNumericImagPart()) > 1));
                            } else {
                                keepGoing = (Math.abs(testDivisor.norm()) <= Math.abs(number.norm()));
                            }
                        }
                    }
                }
                testDivisor = testDivisor.plus(2);
            }
            int testDivRegPartMult = 0;
            int testDivSurdPartMult = 2; // These will be divided by 2 regardless of d = 1 mod 4 or not
            if (number.getRing().hasHalfIntegers()) {
                testDivRegPartMult = 1;
                testDivSurdPartMult = 1;
            }
            boolean withinRange;
            while (Math.abs(number.norm()) > 1) {
                if (d < 0) {
                    testDivisor = new ImaginaryQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, number.getRing(), 2);
                } else {
                    testDivisor = new RealQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, number.getRing(), 2);
                }
                if (d < 0) {
                    withinRange = (testDivisor.norm() <= number.norm());
                } else {
                    withinRange = (testDivisor.norm() <= Math.abs(number.norm()));
                }
                if (isPrime(testDivisor)) {
                    while (number.norm() % testDivisor.norm() == 0) {
                        try {
                            number = number.divides(testDivisor.conjugate());
                            factors.add(testDivisor.conjugate());
                        } catch (NotDivisibleException nde) {
                            // withinRange = withinRange && ((Math.abs(nde.getNumericRealPart()) > 1) || (Math.abs(nde.getNumericImagPart()) > 1));
                        }
                        try {
                            number = number.divides(testDivisor);
                            factors.add(testDivisor);
                        } catch (NotDivisibleException nde) {
                            // withinRange = withinRange && ((Math.abs(nde.getNumericRealPart()) > 1) || (Math.abs(nde.getNumericImagPart()) > 1));
                        }
                    }
                }
                if (withinRange) {
                    testDivRegPartMult += 2;
                } else {
                    if (!number.getRing().hasHalfIntegers()) {
                        testDivRegPartMult = 0;
                        testDivSurdPartMult += 2;
                    } else {
                        if (testDivSurdPartMult % 2 == 0) {
                            testDivRegPartMult = 1;
                        } else {
                            testDivRegPartMult = 0;
                        }
                        testDivSurdPartMult++;
                    }
                }
            }
            factors.add(number); // This should be a unit, most likely -1 or 1
        }
        factors.sort(COMPARATOR);
        QuadraticInteger currFac;
        QuadraticInteger currFirstUnit = number;
        for (int i = 1; i < factors.size(); i++) {
            currFac = (QuadraticInteger) factors.get(i);
            if (currFac.getRegPartMult() < 0 || ((currFac.getRegPartMult() == 0 && currFac.getSurdPartMult() < 0))) {
                factors.set(i, currFac.times(-1));
                currFirstUnit = currFirstUnit.times(-1);
                factors.set(0, currFirstUnit);
            }
        }
        if (unity.equals(factors.get(0))) {
            factors.remove(0);
        }
        return factors;
    }
        
    /**
     * Computes the prime factors, and nontrivial unit factors if needed, of an 
     * algebraic integer from a unique factorization domain (UFD). A nontrivial 
     * unit factor is any unit other than 1.
     * @param num The algebraic integer to find the prime factors of. For 
     * example, &minus;4 + 3&radic;(&minus;19).
     * @return A list of algebraic integers, with the first possibly being a 
     * unit, the rest should be primes. For example, &minus;1, 5/2 &minus; 
     * &radic;(&minus;19)/2, 7/2 &minus; &radic;(&minus;19)/2, which multiply to 
     * &minus;4 + 3&radic;(&minus;19). The only time this should return 0 is in 
     * a list by itself when the input number is 0.
     * @throws ArithmeticException If an overflow occurred trying to determine 
     * whether or not the pertinent ring is a unique factorization domain.
     * @throws NonUniqueFactorizationDomainException This checked exception will 
     * be thrown if this function is called upon to compute the prime factors of 
     * a number from a non-UFD, even if a complete factorization into primes is 
     * possible in the given domain, e.g., 5 and 41 in 
     * <b>Z</b>[(&radic;&minus;5)].
     * @throws NullPointerException If <code>num</code> is null.
     * @throws UnsupportedNumberDomainException Thrown when called upon a number
     * from a type of ring that is not fully supported yet. For example, as of 
     * 2021, this program really has no support for cubic integers, so asking 
     * for the prime factorization of &minus;15 + 2&#8731;2 + 
     * (&#8731;2)<sup>2</sup> would probably trigger this exception. Though at 
     * this point I haven't even thought about how such a number would be 
     * represented in this program.
     * @since Version 0.3
     */
    public static List<AlgebraicInteger> primeFactors(AlgebraicInteger num) 
            throws NonUniqueFactorizationDomainException {
        if (num == null) {
            String excMsg = "Can't factorize null number";
            throw new NullPointerException(excMsg);
        }
        if (!isUFD(num.getRing())) {
            String excMsg = num.getRing().toASCIIString() 
                    + " is not a unique factorization domain";
            throw new NonUniqueFactorizationDomainException(excMsg, num);
        }
        if (num instanceof QuadraticInteger) {
            QuadraticInteger number = (QuadraticInteger) num;
            return factorize(number);
        }
        String excMsg = "At this time, " + num.getRing().getClass().getName() 
                + " is not supported for this factorization operation";
        throw new UnsupportedNumberDomainException(excMsg, num);
    }
    
    /**
     * Determines whether a given number is irreducible, not necessarily prime.
     * @param num The number for which to make the determination. For example, 1 
     * + &radic;&minus;5.
     * @return True if num is irreducible, false if not. For example, 1 + 
     * &radic;&minus;5 is famously irreducible but not prime. Also, units are 
     * considered irreducible by this function.
     * @throws ArithmeticException If a norm computation error occurs (this is a 
     * runtime exception), such as, for example, if the norm of an imaginary 
     * quadratic integer is erroneously said to be negative.
     * @throws UnsupportedNumberDomainException Thrown when called upon a number
     * from a type of ring that is not fully supported yet. For example, as of 
     * 2018, this program does not really have support for cubic integers, so 
     * asking if 3 &minus; &#8731;2 is irreducible would probably trigger this 
     * exception.
     * @throws NullPointerException If <code>num</code> is null. The exception 
     * message will be "Null is not an algebraic integer, neither reducible nor 
     * irreducible".
     * @since Version 0.2
     */
    public static boolean isIrreducible(AlgebraicInteger num) {
        if (num instanceof ImaginaryQuadraticInteger 
                || num instanceof RealQuadraticInteger) {
            if (num instanceof ImaginaryQuadraticInteger && num.norm() < 0) {
                String msg = "Overflow for the computation of the norm of " 
                        + num.toASCIIString();
                throw new ArithmeticException(msg);
            }
            if (isPrime(num.norm())) {
                return true;
            } else {
                if (Math.abs(num.norm()) < 2) {
                    return true;
                } else {
                    QuadraticInteger number = (QuadraticInteger) num;
                    QuadraticRing r = number.getRing();
                    int d = r.getRadicand();
                    boolean normEuclD = false;
                    for (int euclD : NORM_EUCLIDEAN_QUADRATIC_RINGS_D) {
                        if (d == euclD) {
                            normEuclD = true;
                        }
                    }
                    if (normEuclD) {
                        return isPrime(number);
                    } else {
                        boolean withinRange = true;
                        boolean presumedIrreducible = true;
                        QuadraticInteger testDivisor, currDivision;
                        int testDivRegPartMult = 4;
                        int testDivSurdPartMult = 0;
                        boolean testDivisorChanged;
                        while (withinRange && presumedIrreducible) {
                            if (d < 0) {
                                testDivisor = new ImaginaryQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
                            } else {
                                testDivisor = new RealQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
                            }
                            testDivisorChanged = false;
                            withinRange = (Math.abs(testDivisor.norm()) < Math.abs(number.norm()));
                            while (withinRange && presumedIrreducible) {
                                if (Math.abs(testDivisor.norm()) == 1) {
                                    withinRange = false;
                                } else {
                                    try {
                                        currDivision = number.divides(testDivisor);
                                        if (Math.abs(currDivision.norm()) > 1) {
                                            presumedIrreducible = false;
                                        } else {
                                            withinRange = false;
                                        }
                                    } catch (NotDivisibleException nde) {
                                        withinRange = (Math.abs(nde.getNumericRealPart()) > 1) || (Math.abs(nde.getNumericImagPart()) > 1);
                                        testDivRegPartMult += 2;
                                        if (d < 0) {
                                            testDivisor = new ImaginaryQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
                                        } else {
                                            testDivisor = new RealQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
                                        }
                                        testDivisorChanged = true;
                                    }
                                }
                            }
                            if (!r.hasHalfIntegers()) {
                                testDivRegPartMult = 0;
                                testDivSurdPartMult += 2;
                            } else {
                                if (testDivSurdPartMult % 2 == 0) {
                                    testDivRegPartMult = 1;
                                } else {
                                    testDivRegPartMult = 0;
                                }
                                testDivSurdPartMult++;
                            }
                            if (testDivisorChanged) {
                                withinRange = true;
                            }
                        }
                        return presumedIrreducible;
                    }
                }
            }
        }
        if (num == null) {
            String excMsg = "Null is not an algebraic integer, neither reducible nor irreducible";
            throw new NullPointerException(excMsg);
        }
        String excMsg = "The domain of the number " + num.toASCIIString() 
                + " is not yet supported";
        throw new UnsupportedNumberDomainException(excMsg, num);
    }
    
    private static List<AlgebraicInteger> findIrreducibleFactorsOf(QuadraticInteger n) {
        boolean realFlag = n instanceof RealQuadraticInteger;
        QuadraticRing r = n.getRing();
        if (r == null) {
            String excMsg = "Ring of " + n.toASCIIString() 
                    + " should not be null";
            throw new NullPointerException(excMsg);
        }
        QuadraticInteger unity = (QuadraticInteger) getOneInRing(r);
        QuadraticInteger negOne = unity.times(-1);
        List<AlgebraicInteger> factors = new ArrayList<>();
//        if (Math.abs(n.norm()) < 2) {
//            factors.add(n);
//            return factors;
//        }
//        boolean keepGoing = true;
//        if (NumberTheoreticFunctionsCalculator.isIrreducible(n)) {
//            factors.add(unity);
//            factors.add(n);
//            if (!NumberTheoreticFunctionsCalculator.isPrime(n)) {
//                factors.add(negOne);
//                factors.add(negOne);
//            }
//        } else {
//            QuadraticInteger testDivisor = unity.plus(1); // Should be 2
//            if (isIrreducible(testDivisor)) {
//                while (n.norm() % 4 == 0 && keepGoing) {
//                    try {
//                        n = n.divides(testDivisor);
//                        factors.add(testDivisor);
//                        if (!isPrime(testDivisor)) {
//                            factors.add(negOne);
//                            factors.add(negOne);
//                        }
//                    } catch (NotDivisibleException nde) {
//                        keepGoing = false;
//                    }
//                }
//            }
//            testDivisor = testDivisor.plus(1); // Now 3
//            keepGoing = true;
//            while (Math.abs(n.norm()) >= Math.abs(testDivisor.norm()) && keepGoing) {
//                if (isIrreducible(testDivisor)) {
//                    while (n.norm() % testDivisor.norm() == 0 && keepGoing) {
//                        try {
//                            n = n.divides(testDivisor);
//                            factors.add(testDivisor);
//                            if (!isPrime(testDivisor)) {
//                                factors.add(negOne);
//                                factors.add(negOne);
//                            }
//                        } catch (NotDivisibleException nde) {
//                            keepGoing = false;
//                        }
//                    }
//                }
//                testDivisor = testDivisor.plus(2); // Next odd integer
//            }
//            int testDivRegPartMult = 0;
//            int testDivSurdPartMult = 2;
//            if (n.getRing().hasHalfIntegers()) {
//                testDivRegPartMult = 1;
//                testDivSurdPartMult = 1;
//            }
//            boolean withinRange;
//            while (Math.abs(n.norm()) > 1) {
//                if (r instanceof ImaginaryQuadraticRing) {
//                    testDivisor = new ImaginaryQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
//                }
//                if (r instanceof RealQuadraticRing) {
//                    testDivisor = new RealQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
//                }
//                withinRange = (testDivisor.norm() <= n.norm());
//                if (isIrreducible(testDivisor) && Math.abs(testDivisor.norm()) != 1) {
//                    keepGoing = true;
//                    while (n.norm() % testDivisor.norm() == 0 && keepGoing) {
//                        try {
//                            n = n.divides(testDivisor.conjugate());
//                            factors.add(testDivisor.conjugate());
//                            if (!isPrime(testDivisor)) {
//                                factors.add(negOne);
//                                factors.add(negOne);
//                            }
//                        } catch (NotDivisibleException nde) {
//                            // We just ignore the exception when it pertains 
//                            // to the conjugate 
//                        }
//                        try {
//                            n = n.divides(testDivisor);
//                            factors.add(testDivisor);
//                            if (!isPrime(testDivisor)) {
//                                factors.add(negOne);
//                                factors.add(negOne);
//                            }
//                        } catch (NotDivisibleException nde) {
//                            if (realFlag) {
//                                withinRange = Math.abs(testDivisor.getRealPartNumeric()) <= Math.abs(n.getRealPartNumeric());
//                            } else {
//                                withinRange = ((Math.abs(nde.getNumericRealPart()) >= 1) || (Math.abs(nde.getNumericImagPart()) >= 1));
//                            }
//                            keepGoing = false;
//                        }
//                    }
//                }
//                if (withinRange) {
//                    testDivRegPartMult += 2;
//                } else {
//                    if (!r.hasHalfIntegers()) {
//                        testDivRegPartMult = 0;
//                        testDivSurdPartMult += 2;
//                    } else {
//                        if (testDivSurdPartMult % 2 == 0) {
//                            testDivRegPartMult = 1;
//                        } else {
//                            testDivRegPartMult = 0;
//                        }
//                        testDivSurdPartMult++;
//                    }
//                }
//            }
//            factors.add(n); // This should be a unit, most likely -1 or 1
//        }
//        factors.sort(COMPARATOR);
//        int quadrantAdjustStart = 1;
//        keepGoing = true;
//        while (quadrantAdjustStart < factors.size() && keepGoing) {
//            if (Math.abs(factors.get(quadrantAdjustStart).norm()) == 1) {
//                quadrantAdjustStart++;
//            } else {
//                keepGoing = false;
//            }
//        }
//        QuadraticInteger currFac;
//        QuadraticInteger currFirstUnit = (QuadraticInteger) factors.get(0);
//        int unitAdjustCount = 0;
//        for (int i = quadrantAdjustStart; i < factors.size(); i++) {
//            currFac = (QuadraticInteger) factors.get(i);
//            if (currFac.getRegPartMult() < 0 || ((currFac.getRegPartMult() == 0 && currFac.getSurdPartMult() < 0))) {
//                factors.set(i, currFac.times(-1));
//                currFirstUnit = currFirstUnit.times(-1);
//                factors.set(0, currFirstUnit);
//                unitAdjustCount++;
//            }
//        }
//        int removalIndex = 0;
//        int unitRemovalCount = 0;
//        while (removalIndex < factors.size()) {
//            if (unity.equals(factors.get(removalIndex))) {
//                factors.remove(removalIndex);
//                unitRemovalCount++;
//            }
//            removalIndex++;
//        }
        return factors;
    }
    
    /**
     * Determines the irreducible factors of an algebraic integer.
     * @param num The number to factorize into irreducibles. For example, 577 in 
     * <b>Z</b>[&radic;577].
     * @return A list of irreducible factors. No exponents are used, repeated 
     * factors are simply repeated. For example, &radic;577 listed twice, rather 
     * than (&radic;577)<sup>2</sup>.
     * @throws RuntimeException In the unlikely event that this function is 
     * called with a number from a unique factorization domain (UFD) but it 
     * causes a <code>NonUniqueFactorizationDomainException</code>, that 
     * exception will be wrapped in this exception. Or in the still unlikelier 
     * event that <code>UnsupportedNumberDomainException</code> should have 
     * occurred but didn't, this exception will occur without wrapping any 
     * exception.
     * @throws UnsupportedNumberDomainException If this function is called with 
     * an algebraic integer from a domain that is not fully supported yet.
     */
    public static List<AlgebraicInteger> irreducibleFactors(AlgebraicInteger num) {
        IntegerRing ring = num.getRing();
        if (isUFD(ring)) {
            try {
                List<AlgebraicInteger> factors = primeFactors(num);
                return factors;
            } catch (NonUniqueFactorizationDomainException nufde) {
                throw new RuntimeException(nufde);
            }
        }
        if (ring instanceof QuadraticRing) {
            List<AlgebraicInteger> factors 
                    = findIrreducibleFactorsOf((QuadraticInteger) num);
            return factors;
        }
        throw new RuntimeException("Unexpected circumstance occurred");
    }
    
    /**
     * Determines whether or not one integer is divisible by another. No 
     * exceptions will occur calling this function.
     * @param dividend The number to divide. For example, 1729 = 7 &times; 13 
     * &times; 19.
     * @param divisor The number to divide by. Examples: &minus;133, 0, 92.
     * @return True if and only if {@code dividend} is divisible by {@code 
     * divisor}, false in all other cases. In the examples, true for &minus;133 
     * = &minus;1 &times; 7 &times; 19, false for 0 and false for 92 = 
     * 2<sup>2</sup> &times; 23.
     */
    public static boolean isDivisibleBy(int dividend, int divisor) {
        if (divisor == 0) {
            return false;
        }
        return dividend % divisor == 0;
    }
    
    /**
     * Tests whether one algebraic integer is divisible by another.
     * @param dividend The number to test for divisibility by another. For example, 
     * &radic;14.
     * @param divisor The number to test as a potential divisor of the first number. 
     * For example, 4 + &radic;14.
     * @return True if a is divisible by b, false otherwise. For example, it 
     * should return true that &radic;14 is indeed divisible by 4 + &radic;14, 
     * false for &radic;14 being divisible by 3 + &radic;14 (it's not 
     * divisible). Sure to be false if b is 0 regardless of what a is, sure to 
     * be true is a is 0 and b is any nonzero number.
     * @throws AlgebraicDegreeOverflowException If the two numbers are from 
     * different rings, even if one or both have zero "surd" parts, this runtime 
     * exception will be thrown. For example, is 1 + &radic;&minus;2 divisible 
     * by &radic;3? Or how about is 9 from <b>Z</b>[&radic;15] divisible by 3 
     * from <b>Z</b>[&radic;&minus;10]? The former can't be resolved in a 
     * quadratic ring, the latter can, though the choice of ring could have 
     * unexpected consequences for subsequent calculations, so this exception is 
     * thrown just the same.
     * @throws UnsupportedNumberDomainException Thrown when called upon numbers 
     * from a type of ring that is not fully supported yet. For example, as of 
     * 2018, this program does not really have support for cubic integers, so 
     * asking if 35 + 5&#8731;2 is divisible by 3 &minus; &#8731;2 would 
     * probably trigger this exception.
     * @since Version 0.4
     */
    public static boolean isDivisibleBy(AlgebraicInteger dividend, 
            AlgebraicInteger divisor) {
        if (!dividend.getRing().equals(divisor.getRing())) {
            String excMsg = "Ring mismatch: " + dividend.toASCIIString() 
                    + " is from " + dividend.getRing().toASCIIString() + " but " 
                    + divisor.toASCIIString() + " is from " 
                    + divisor.getRing().toASCIIString();
            int deg;
            if (dividend.algebraicDegree() > divisor.algebraicDegree()) {
                deg = dividend.algebraicDegree();
            } else {
                deg = divisor.algebraicDegree();
            }
            throw new AlgebraicDegreeOverflowException(excMsg, deg, dividend, 
                    divisor);
        }
        if (divisor.norm() == 0) {
            return false;
        }
        if (dividend.norm() == 0) {
            return true;
        }
        if (dividend instanceof QuadraticInteger) {
            QuadraticInteger divA = (QuadraticInteger) dividend;
            QuadraticInteger divB = (QuadraticInteger) divisor;
            QuadraticInteger remainder = divA.mod(divB);
            return remainder.norm() == 0;
        }
        String excMsg = "Testing divisibility in the domain of the number " 
                + dividend.toASCIIString() + " is not yet supported";
        throw new UnsupportedNumberDomainException(excMsg, dividend, divisor);
    }
        
    /**
     * Determines whether a given number is squarefree or not. The original 
     * implementation depended on {@link #primeFactors(int) primeFactors(int)}. 
     * For version 0.95, this was optimized to try the number modulo 4, and if 
     * it's not divisible by 4, to try dividing it by odd squares. Although this 
     * includes odd squares like 9 and 81, it still makes for a performance 
     * improvement over relying on <code>primeFactors(int)</code>.
     * @param num The number to be tested for being squarefree.
     * @return True if the number is squarefree, false otherwise. For example, 
     * &minus;3 and 7 should each return true, &minus;4, 0 and 25 should each 
     * return false. Note that 1 is considered to be squarefree. Therefore, for 
     * num = 1, this function should return true.
     */
    public static boolean isSquarefree(int num) {
        switch (num) {
            case -1:
            case 1:
                return true;
            case 0:
                return false;
            default:
                boolean noDupFactorFound = (num % 4 != 0);
                if (noDupFactorFound) {
                    double threshold = Math.sqrt(Math.abs(num));
                    int currRoot = 3;
                    int currSquare;
                    do {
                        currSquare = currRoot * currRoot;
                        noDupFactorFound = (num % currSquare != 0);
                        currRoot += 2;
                    } while (noDupFactorFound && currRoot <= threshold);
                }
                return noDupFactorFound;
        }
    }
    
    /**
     * Determines whether or not this number is cubefree. If a number is 
     * cubefree, that means it's not divisible by any nonzero nontrivial cubes.
     * @param num The number to check for being cubefree.
     * @return True if the number is not divisible by any nontrivial cubes, 
     * false otherwise. Examples: true for &minus;44, 7, 82; false for &minus;8, 
     * 27, &minus;64, 125. Special cases: &minus;1 and 1 are cubefree, 0 is not.
     */
    public static boolean isCubefree(int num) {
        boolean noTripledFactorFound = (num % 8 != 0);
        if (noTripledFactorFound) {
            double threshold = Math.cbrt(Math.abs(num));
            int currRoot = 3;
            int currCube;
            do {
                currCube = currRoot * currRoot * currRoot;
                noTripledFactorFound = (num % currCube != 0);
                currRoot += 2;
            } while (noTripledFactorFound && currRoot <= threshold);
        }
        return noTripledFactorFound;
    }
    
    // TODO: Write tests for this
    public static boolean isPowerfree(int num, int powerLevel) {
        return true;
    }
    
    private static int nextSquarefree(int n, int direction) {
        int num = n;
        do {
            num += direction;
        } while (!isSquarefree(num));
        return num;
    }
    
    /**
     * Gives the next lowest squarefree number after a given number. No overflow 
     * checking is provided.
     * @param n The number for which to get the next lowest squarefree number. 
     * Need not itself be squarefree. Examples: 4752, 4753, 4754. These examples 
     * factorize as 2<sup>4</sup> &times; 3<sup>3</sup> &times; 11, 
     * 7<sup>2</sup> &times; 97 and 2 &times; 2377 respectively.
     * @return The next lowest squarefree number. It will be different from 
     * <code>n</code> if <code>n</code> happens to itself be squarefree. For the 
     * three examples given above, the result would be 4751, which is a prime  
     * number. If <code>n</code> happens to be 
     * <code>Integer.MIN_VALUE + 1</code> or <code>Integer.MIN_VALUE</code>, the 
     * result will be incorrectly given as <code>Integer.MAX_VALUE</code>, which 
     * is wrong; the correct result is just outside the range of 
     * <code>int</code> (namely &minus;2147483649).
     */
    public static int nextLowestSquarefree(int n) {
        return nextSquarefree(n, -1);
    }
    
    /**
     * Gives the next highest squarefree number after a given number. No overflow 
     * checking is provided.
     * @param n The number for which to get the next highest squarefree number. 
     * Need not itself be squarefree. Examples: 4751, 4752, 4753. These examples 
     * factorize as 4751, 2<sup>4</sup> &times; 3<sup>3</sup> &times; 11 and 
     * 7<sup>2</sup> &times; 97, respectively.
     * @return The next lowest squarefree number. It will be different from 
     * <code>n</code> if <code>n</code> happens to itself be squarefree. For the 
     * three examples given above, the result would be 4754, which factorizes as 
     * 2 &times; 2377. If <code>n</code> happens to be 
     * <code>Integer.MAX_VALUE</code>, the result will be incorrectly given as 
     * <code>Integer.MIN_VALUE + 1</code>, which is wrong; the correct result is 
     * just outside the range of <code>int</code> (namely 2147483649).
     */
    public static int nextHighestSquarefree(int n) {
        return nextSquarefree(n, 1);
    }
    
    /**
     * Determines whether or not an integer is a perfect square. Note that 
     * although 1 is considered by this program to be squarefree, it is also 
     * considered to be a perfect square.
     * <p>Be aware that since this function uses floating point operations, 
     * results are not guaranteed to be correct for integers greater than 
     * 2<sup>53</sup>.</p>
     * @param num The number to be tested. Examples: 81, &minus;81, 82.
     * @return True if the number is 0 or a square of a nonzero integer, false 
     * in any other case. For example, 81 is a perfect square since &minus;9 
     * &times; &minus;9 = 9 &times; 9 = 81. But &minus;81 is not a perfect 
     * square since its square root is imaginary. And 82 is not a perfect square 
     * either since it is 2 &times; 41.
     */
    public static boolean isPerfectSquare(long num) {
        if (num < 0) return false;
        double sqrt = Math.sqrt(num);
        double flooredRoot = Math.floor(sqrt);
        return sqrt == flooredRoot;
    }
    
    /**
     * Gives the squarefree kernel of an integer. The current implementation 
     * works by obtaining the prime factorization of the number, deleting 
     * duplicate factors and then multiplying the factors that remain.
     * @param num The number for which to find the squarefree kernel of. May be 
     * negative. For example, &minus;392.
     * @return The squarefree kernel of the given number. Should be positive for 
     * positive inputs and negative for negative inputs. For example, the kernel 
     * of &minus;392 is &minus;14. The current implementation returns 0 for an 
     * input of 0, but this is not guaranteed for future implementations.
     */
    public static int kernel(int num) {
        List<Integer> factors = primeFactors(num);
        for (int i = factors.size() - 1; i > 0; i--) {
            if (factors.get(i).equals(factors.get(i - 1))) {
                factors.remove(i);
            }
        }
        int product = 1;
        product = factors.stream().map((factor) 
                -> factor).reduce(product, (accumulator, _item) 
                        -> accumulator * _item);
        return product;
    }
    
    /**
     * Computes the M&ouml;bius function &mu; for a given integer.
     * @param num The integer for which to compute the M&ouml;bius function.
     * @return 1 if num is squarefree with an even number of prime factors, 
     * &minus;1 if num is squarefree with an odd number of prime factors, 0 if 
     * num is not squarefree. Since &minus;1 is a unit, not a prime, 
     * &mu;(&minus;<i>n</i>) = &mu;(<i>n</i>). For example, &mu;(31) = &minus;1, 
     * &mu;(32) = 0 and &mu;(33) = 1.
     */
    public static byte moebiusMu(int num) {
        switch (num) {
            case -1:
            case 1:
                return 1;
            default:
                if (isSquarefree(num)) {
                    List<Integer> prFacts = primeFactors(num);
                    if (prFacts.get(0) == -1) {
                        prFacts.remove(0);
                    }
                    if (prFacts.size() % 2 == 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    return 0;
                }
        }
    }
    
    /**
     * Computes the greatest common divisor (GCD) of two purely real integers by 
     * using the Euclidean algorithm.
     * @param a One of the two integers. May be negative, need not be greater 
     * than the other. For example, 114.
     * @param b One of the two integers. May be negative, need not be smaller 
     * than the other. For example, 152.
     * @return The GCD as a 64-bit integer (it should be okay to cast the result 
     * to a 32-bit integer if both parameters are 32-bit integers, aside from a 
     * few edge cases involving <code>Integer.MIN_VALUE</code>). For example, 
     * 38. If one of <code>a</code> or <code>b</code> is 0 and the other number 
     * is nonzero, the result will be the nonzero number. If both <code>a</code> 
     * and <code>b</code> are 0, then the result will be 0, which is perhaps 
     * technically wrong, but I think it's good enough for the purpose here.
     */
    public static long euclideanGCD(long a, long b) {
        long currA = Math.abs(a);
        long currB = Math.abs(b);
        long remainder = currA;
        while (remainder != 0) {
            currA = currB;
            currB = remainder;
            remainder = currA % currB;
        }
        return currB;
    }

    /**
     * Computes the greatest common divisor (GCD) of two algebraic integers by 
     * using the Euclidean algorithm.
     * @param a One of the two algebraic integers. Need not have greater norm 
     * than the other algebraic integer.
     * @param b One of the two algebraic integers. Need not have smaller norm 
     * than the other algebraic integer.
     * @return The GCD of the two algebraic integers.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come 
     * from different rings of the same degree, the GCD might be a number from a 
     * ring of greater degree.
     * @throws NonEuclideanDomainException In the case of quadratic integers, if 
     * they come from a ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> for 
     * <i>d</i> other than those listed in {@link 
     * #NORM_EUCLIDEAN_QUADRATIC_RINGS_D NORM_EUCLIDEAN_QUADRATIC_RINGS_D}, this 
     * function assumes the Euclidean GCD algorithm with the absolute value of 
     * the norm function will fail without even trying, and throws this checked 
     * exception. However, for some pairs drawn from a non-Euclidean domain, the 
     * Euclidean GCD algorithm might nevertheless work. For this reason, the 
     * exception provides {@link 
     * NonEuclideanDomainException#tryEuclideanGCDAnyway() 
     * tryEuclideanGCDAnyway()}.
     * @throws UnsupportedNumberDomainException If the algebraic integers come 
     * from a domain for which this function has not been defined yet, this 
     * runtime exception will be thrown. For example, this function might have 
     * been programmed for imaginary quadratic integers but not yet programmed 
     * for real quadratic integers, so the latter will trigger this exception.
     */
    public static AlgebraicInteger euclideanGCD(AlgebraicInteger a, AlgebraicInteger b) throws NonEuclideanDomainException {
        if (a instanceof QuadraticInteger && b instanceof QuadraticInteger) {
            QuadraticInteger currA = (QuadraticInteger) a;
            QuadraticInteger currB = (QuadraticInteger) b;
            if (!currA.getRing().equals(currB.getRing())) {
                if (currA.getSurdPartMult() == 0 || currB.getSurdPartMult() == 0) {
                    if (currA.getSurdPartMult() == 0 && currB.getSurdPartMult() != 0) {
                        if (currB instanceof ImaginaryQuadraticInteger) {
                            currA = new ImaginaryQuadraticInteger(currA.getRegPartMult(), 0, currB.getRing());
                        } else {
                            if (currB instanceof RealQuadraticInteger) {
                                currA = new RealQuadraticInteger(currA.getRegPartMult(), 0, currB.getRing());
                            } else {
                                String excMsg = currB.getClass().getName() 
                                        + " is not currently supported for this Euclidean GCD operation";
                                throw new UnsupportedNumberDomainException(excMsg, currB.getRing());
                            }
                        }
                    }
                    if (currB.getSurdPartMult() == 0 && currA.getSurdPartMult() != 0) {
                        if (currA instanceof ImaginaryQuadraticInteger) {
                            currB = new ImaginaryQuadraticInteger(currB.getRegPartMult(), 0, currA.getRing());
                        } else {
                            if (currA instanceof RealQuadraticInteger) {
                                currB = new RealQuadraticInteger(currB.getRegPartMult(), 0, currA.getRing());
                            } else {
                                String excMsg = currA.getClass().getName() 
                                        + " is not currently supported for this Euclidean GCD operation";
                                throw new UnsupportedNumberDomainException(excMsg, currA.getRing());
                            }
                        }
                    }
                } else {
                    String excMsg = a.toASCIIString() + " is from " 
                            + currA.getRing().toASCIIString() + " but " 
                            + b.toASCIIString() + " is from " 
                            + currB.getRing().toASCIIString();
                    throw new AlgebraicDegreeOverflowException(excMsg, 2, a, b);
                }
            }
            boolean nonNormEuclFlag = true;
            int counter = 0;
            int radic = currA.getRing().getRadicand();
            while (nonNormEuclFlag && (counter < NORM_EUCLIDEAN_QUADRATIC_RINGS_D.length)) {
                nonNormEuclFlag = (radic != NORM_EUCLIDEAN_QUADRATIC_RINGS_D[counter]);
                counter++;
            }
            if (nonNormEuclFlag) {
                String excMsg = currA.getRing().toASCIIString() 
                        + " is not a norm-Euclidean domain";
                throw new NonEuclideanDomainException(excMsg, currA, currB);
            }
            if (Math.abs(currA.norm()) < Math.abs(currB.norm())) {
                QuadraticInteger swapper = currA;
                currA = currB;
                currB = swapper;
            }
            QuadraticInteger tempMultiple, currRemainder;
            AlgebraicInteger[] bounds;
            while (currB.norm() != 0) {
                try {
                    tempMultiple = currA.divides(currB);
                    tempMultiple = tempMultiple.times(currB);
                    currRemainder = currA.minus(tempMultiple);
                } catch (NotDivisibleException nde) {
                    bounds = nde.getBoundingIntegers();
                    boolean notFound;
                    counter = 0;
                    QuadraticInteger holder;
                    do {
                        holder = (QuadraticInteger) bounds[counter];
                        tempMultiple = holder.times(currB);
                        currRemainder = currA.minus(tempMultiple);
                        notFound = Math.abs(currRemainder.norm()) >= Math.abs(currB.norm());
                        counter++;
                    } while (notFound);
                }
                currA = currB;
                currB = currRemainder;
            }
            if (radic == -1 && currA.getRegPartMult() == 0) {
                currA = currA.times(IMAG_UNIT_NEG_I);
            }
            if (currA.getRegPartMult() < 0) {
                currA = currA.times(-1);
            }
            return currA;
        }
        throw new UnsupportedNumberDomainException("Not supported yet, sorry", a, b);
    }

    /**
     * Computes the greatest common divisor (GCD) of an algebraic integer which 
     * may or may not be of degree 1 or 0, passed in as an AlgebraicInteger, and 
     * an algebraic integer of degree 1 or 0 passed in as an int. 
     * @param a An algebraic integer from any supported domain in this program. 
     * For example, 3&radic;&minus;2.
     * @param b An algebraic integer of degree 1 or 0. For example, 4.
     * @return The GCD. For example, &radic;&minus;2.
     * @throws NonEuclideanDomainException In the case of quadratic integers, if 
     * they come from a ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> for 
     * <i>d</i> other than those listed in {@link 
     * #NORM_EUCLIDEAN_QUADRATIC_RINGS_D NORM_EUCLIDEAN_QUADRATIC_RINGS_D}, this 
     * function assumes the Euclidean GCD algorithm with the absolute value of 
     * the norm function will fail without even trying, and throws this checked 
     * exception. However, for some pairs drawn from a non-Euclidean domain, the 
     * Euclidean GCD algorithm might nevertheless work. For this reason, the 
     * exception provides {@link 
     * NonEuclideanDomainException#tryEuclideanGCDAnyway() 
     * tryEuclideanGCDAnyway()}.
     * @throws UnsupportedNumberDomainException If parameter a comes from a 
     * domain for which this function has not been defined yet, this runtime 
     * exception will be thrown. For example, this function might have been 
     * programmed for imaginary quadratic integers but not yet programmed for 
     * real quadratic integers, so the latter will trigger this exception.
     */
    public static AlgebraicInteger euclideanGCD(AlgebraicInteger a, int b) throws NonEuclideanDomainException {
        if (a instanceof QuadraticInteger) {
            QuadraticInteger wrappedB = new ImaginaryQuadraticInteger(b, 0, RING_GAUSSIAN);
            return euclideanGCD(a, wrappedB);
        }
        throw new UnsupportedNumberDomainException("Not supported yet, sorry", a);
    }
    
    /**
     * Computes the greatest common divisor (GCD) of an algebraic integer which 
     * may or may not be of degree 1 or 0, passed in as an AlgebraicInteger, and 
     * an algebraic integer of degree 1 or 0 passed in as an int. 
     * @param a An algebraic integer of degree 1 or 0. For example, 4.
     * @param b An algebraic integer from any supported domain in this program. 
     * For example, 3&radic;&minus;2.
     * @return The GCD. For example, &radic;&minus;2.
     * @throws NonEuclideanDomainException In the case of quadratic integers, if 
     * they come from a ring <i>O</i><sub><b>Q</b>(&radic;<i>d</i>)</sub> for 
     * <i>d</i> other than those listed in {@link 
     * #NORM_EUCLIDEAN_QUADRATIC_RINGS_D NORM_EUCLIDEAN_QUADRATIC_RINGS_D}, this 
     * function assumes the Euclidean GCD algorithm with the absolute value of 
     * the norm function will fail without even trying, and throws this checked 
     * exception. However, for some pairs drawn from a non-Euclidean domain, the 
     * Euclidean GCD algorithm might nevertheless work. For this reason, the 
     * exception provides {@link 
     * NonEuclideanDomainException#tryEuclideanGCDAnyway() 
     * tryEuclideanGCDAnyway()}.
     * @throws UnsupportedNumberDomainException If parameter b comes from a 
     * domain for which this function has not been defined yet, this runtime 
     * exception will be thrown. For example, this function might have been 
     * programmed for imaginary quadratic integers but not yet programmed for 
     * real quadratic integers, so the latter will trigger this exception.
     */
    public static AlgebraicInteger euclideanGCD(int a, AlgebraicInteger b) throws NonEuclideanDomainException {
        if (b instanceof QuadraticInteger) {
            QuadraticInteger wrappedA = new ImaginaryQuadraticInteger(a, 0, RING_GAUSSIAN);
            return euclideanGCD(wrappedA, b);
        }
        throw new UnsupportedNumberDomainException("Not supported yet, sorry", b);
    }
    
    private static QuadraticInteger realQuadUnitFind(RealQuadraticRing r) {
        QuadraticInteger potentialUnit;
        int d = r.getRadicand();
        long xd, trialRegNeg;
        long trialSurd = 1;
        boolean notFoundYet = true;
        do {
            xd = trialSurd * trialSurd * d;
            trialRegNeg = (long) Math.floor(Math.sqrt(xd));
            potentialUnit = new RealQuadraticInteger((int) trialRegNeg, 
                    (int) trialSurd, r);
            if (potentialUnit.norm() == -1) {
                notFoundYet = false;
            }
            if (notFoundYet) {
                potentialUnit = potentialUnit.plus(1);
                if (potentialUnit.norm() == 1) {
                    notFoundYet = false;
                }
            }
            trialSurd++;
        } while ((trialRegNeg < Integer.MAX_VALUE) && notFoundYet);
        if (r.hasHalfIntegers()) {
            double threshold = potentialUnit.abs();
            double currAbs;
            QuadraticInteger potentialHalfUnit;
            trialSurd = 1;
            do {
                xd = trialSurd * trialSurd * d;
                trialRegNeg = (long) Math.floor(Math.sqrt(xd - 4));
                trialRegNeg += ((trialRegNeg % 2) - 1);
                potentialHalfUnit = new RealQuadraticInteger((int) trialRegNeg, 
                        (int) trialSurd, r, 2);
                if (potentialHalfUnit.norm() == -1) {
                    return potentialHalfUnit;
                } else {
                    currAbs = potentialHalfUnit.abs();
                }
                potentialHalfUnit = potentialHalfUnit.plus(1);
                if (potentialHalfUnit.norm() == 1) {
                    return potentialHalfUnit;
                }
                trialSurd += 2;
            } while (currAbs < threshold);
        }
        if (notFoundYet) {
            String excMsg = "Overflow occurred considering potential unit " 
                    + potentialUnit.toASCIIString();
            throw new ArithmeticException(excMsg);
        } else {
            if (trialSurd > SURD_PART_CACHE_THRESHOLD) {
                UNITS_CACHE.put(r, potentialUnit);
            }
            return potentialUnit;
        }
    }
    
    /**
     * Gives the fundamental unit of a ring that has infinitely many units. In a  
     * domain of purely real numbers, the fundamental unit is the smallest unit 
     * greater than 1. It may have norm &minus;1 or 1. At least for now, this 
     * function uses a brute force algorithm that may be unacceptably slow in 
     * some cases.
     * @param ring The ring of algebraic integers for which to find the 
     * fundamental unit.
     * @return The fundamental unit. For example, for <b>Z</b>[&radic;2], this 
     * would be 1 + &radic;2; for <b>Z</b>[&radic;3] this would be 2 + &radic;3.
     * @throws ArithmeticException Since computations are performed using 32-bit 
     * and 64-bit integers, an overflow could occur, especially if the real or 
     * imaginary part of the fundamental unit is less than 
     * &minus;(2<sup>31</sup>) or greater than 2<sup>31</sup> &minus; 1.
     * @throws IllegalArgumentException If called upon with a supported domain 
     * known to not have infinitely many units, such as an imaginary quadratic 
     * integer ring, this runtime exception will be thrown.
     * @throws NullPointerException If <code>ring</code> is null. The exception 
     * message will be "Null ring has no fundamental unit".
     * @throws UnsupportedNumberDomainException If called upon with a number 
     * domain for which support has not been fully fleshed out yet, such as a 
     * simple cubic integer ring. This exception should never be used to 
     * indicate an immutable mathematical fact about the pertinent number 
     * domain, but rather a condition that may change in the future.
     */
    public static AlgebraicInteger fundamentalUnit(IntegerRing ring) {
        if (ring instanceof ImaginaryQuadraticRing) {
            String excMsg = "Since " + ring.toASCIIString() 
                    + " has a finite unit group, there is no fundamental unit";
            throw new IllegalArgumentException(excMsg);
        }
        if (ring instanceof RealQuadraticRing) {
            if (UNITS_CACHE.containsKey(ring)) {
                return UNITS_CACHE.get(ring);
            } else {
                return realQuadUnitFind((RealQuadraticRing) ring);
            }
        }
        if (ring == null) {
            String excMsg = "Null ring has no fundamental unit";
            throw new NullPointerException(excMsg);
        }
        String excMsg = "Fundamental unit function not yet supported for " 
                + ring.toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    private static AlgebraicInteger inferMinForUnitSearch(IntegerRing ring) {
        if (ring instanceof RealQuadraticRing) {
            RealQuadraticRing r = (RealQuadraticRing) ring;
            if (r.hasHalfIntegers()) {
                return new RealQuadraticInteger(1, 1, r, 2);
            } else {
                return new RealQuadraticInteger(2, 0, r);
            }
        }
        throw new RuntimeException("Oops, sorry!");
    }
    
    private static Optional<AlgebraicInteger> unitSearchVerifiedParams(AlgebraicInteger min, 
            AlgebraicInteger max) {
        return Optional.of(max);
    }
    
    // STUB TO FAIL THE TEST
    public static Optional<AlgebraicInteger> searchForUnit(AlgebraicInteger max) {
        if (max == null) {
            String excMsg = "Can't do unit search with null max";
            throw new NullPointerException(excMsg);
        }
        return unitSearchVerifiedParams(inferMinForUnitSearch(max.getRing()), max);
    }
    
    // STUB TO FAIL THE TEST
    public static Optional<AlgebraicInteger> searchForUnit(AlgebraicInteger min, 
            AlgebraicInteger max) {
        if (min == null || max == null) {
            String excMsg = "Can't do unit search with null min and/or null max";
            throw new NullPointerException(excMsg);
        }
        IntegerRing minRing = min.getRing();
        IntegerRing maxRing = max.getRing();
        if (!minRing.equals(maxRing)) {
            String excMsg = "Ring mismatch: " + minRing.toASCIIString() 
                    + " and " + maxRing.toASCIIString();
            int deg = Math.max(minRing.getMaxAlgebraicDegree(), 
                    maxRing.getMaxAlgebraicDegree());
            throw new AlgebraicDegreeOverflowException(excMsg, deg, min, max);
        }
        if (min instanceof RealQuadraticInteger) {
            RealQuadraticInteger minimum = (RealQuadraticInteger) min;
            RealQuadraticInteger maximum = (RealQuadraticInteger) max;
            if (minimum.compareTo(maximum) > 0) {
                String excMsg = "Minimum " + minimum.toASCIIString() 
                        + " is not less than maximum " + maximum.toASCIIString();
                throw new IllegalArgumentException(excMsg);
            }
        }
        ImaginaryQuadraticInteger num = new ImaginaryQuadraticInteger(5, 7, RING_EISENSTEIN, 2);
        return Optional.of(num);
    }
    
    /**
     * Places an algebraic integer in a sector of the complex plane considered 
     * preferable for the canonical form of a prime factorization. This is done 
     * by multiplying the number by a suitable unit, so that its norm remains 
     * the same.
     * @param num The number which may or may not be in the primary sector and 
     * needs to be placed there. For example, 1 &minus; 10<i>i</i>, which has a 
     * norm of 101 and an angle of approximately &minus;1.47 radians (roughly 
     * &minus;84 degrees).
     * @return An algebraic integer placed in the primary sector, having the 
     * same norm as num but a different angle (unless num was already in the 
     * primary sector to begin with). For example, for 1 &minus; 10<i>i</i>, 
     * this would be 10 + <i>i</i>, which also has a norm of 101 but an angle of 
     * approximately 0.09966865249 (roughly 5.71 degrees).
     * @throws UnsupportedNumberDomainException Thrown when called upon a number
     * from a type of ring that is not fully supported yet.
     */
    public static AlgebraicInteger placeInPrimarySector(AlgebraicInteger num) {
        if (num.abs() == 0) {
            return num;
        }
        if (num instanceof QuadraticInteger) {
            QuadraticInteger n = (QuadraticInteger) num;
            QuadraticRing r = n.getRing();
            int d = r.getRadicand();
            double minAngle, maxAngle;
            QuadraticInteger turnUnit;
            switch (d) {
                case -1:
                    minAngle = -0.7853981633974483; // -45 degrees
                    maxAngle = 0.7853981633974483; // 45 degrees
                    turnUnit = IMAG_UNIT_NEG_I;
                    break;
                case -3:
                    minAngle = -0.5235987755982988730771; // -30 degrees
                    maxAngle = 0.5235987755982988730771; // 30 degrees
                    turnUnit = new ImaginaryQuadraticInteger(1, -1, r, 2); // -omega
                    break;
                default:
                    if ((n.getRegPartMult() < 0) || ((n.getRegPartMult() == 0) && (n.getSurdPartMult() < 0))) {
                        return n.times(-1);
                    } else {
                        return n;
                    }
            }
            while ((n.angle() <= minAngle) || (n.angle() > maxAngle)) {
                n = n.times(turnUnit);
            }
            return n;
        }
        String excMsg = "Place in primary sector function not yet supported for " 
                + num.getRing().toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, num);
    }

    /**
     * Divides the non-trivial units out of an algebraic integer, so as to place 
     * a number close to 0 but to the right and/or above 1 on the complex plane. 
     * This enables the determination of whether two numbers with the same norm 
     * belong to the same principal ideal. The number returned by this function 
     * can still be divided by units, but that should place the number to the 
     * left and/or below 1.
     * @param num The number out of which to divide units. For example, 2 + 
     * &radic;2, which has norm 2.
     * @return A number which can be factorized into primes without need for 
     * nontrivial units. For example, for 2 + &radic;2, this would be &radic;2, 
     * which has norm &minus;2 (since &radic;2 times the fundamental unit 1 + 
     * &radic;2, which has norm &minus;1, is 2 + &radic;2).
     * @throws UnsupportedNumberDomainException If called upon to divide the 
     * units out of an algebraic integer from a type of ring not yet supported 
     * by this function. As of 2018, real and imaginary quadratic rings are 
     * supported, but none of higher algebraic degree.
     */
    public static AlgebraicInteger divideOutUnits(AlgebraicInteger num) {
        if (num.norm() == 0) {
            return num;
        }
        if (num instanceof ImaginaryQuadraticInteger) {
            return placeInPrimarySector(num);
        }
        if (num instanceof RealQuadraticInteger) {
            QuadraticInteger unit = (QuadraticInteger) fundamentalUnit(num.getRing());
            QuadraticInteger n;
            if (num.getRealPartNumeric() < 0.0) {
                n = ((QuadraticInteger) num).times(-1);
            } else {
                n = (QuadraticInteger) num;
            }
            while (n.getRealPartNumeric() < 1.0) {
                n = n.times(unit);
            }
            QuadraticInteger nextN;
            do {
                try {
                    nextN = n.divides(unit);
                    if (nextN.getRealPartNumeric() > 1.0) {
                        n = nextN;
                    }
                } catch (NotDivisibleException nde) {
                    System.err.println("NotDivisibleException should not have happened dividing by a unit: \"" + nde.getMessage() + "\"");
                    throw new RuntimeException(nde); // Rethrow wrapped in RuntimeException
                }
            } while (n.equals(nextN));
            return n;
        }
        String excMsg = "Place in primary section function not yet supported for " 
                + num.getRing().toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, num);
    }
    
    // TODO: Write tests for this
    public static <T extends AlgebraicInteger & Arithmeticable<T>> T[] 
            getBoundingIntegers(T dividend, T divisor) {
        return null;
    }

    public static AlgebraicInteger getNegOneInRing(IntegerRing ring) {
        if (ring instanceof QuadraticRing) {
            return QuadraticInteger.apply(-1, 0, (QuadraticRing) ring);
        }
        if (ring == null) {
            String excMsg = "Null ring does not contain the number -1";
            throw new NullPointerException(excMsg);
        }
        String excMsg = "-1 from given ring function not yet supported for " 
                + ring.toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    public static AlgebraicInteger getZeroInRing(IntegerRing ring) {
        if (ring instanceof QuadraticRing) {
            return QuadraticInteger.apply(0, 0, (QuadraticRing) ring);
        }
        if (ring == null) {
            String excMsg = "Null ring does not contain the number 0";
            throw new NullPointerException(excMsg);
        }
        String excMsg = "0 from given ring function not yet supported for " 
                + ring.toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    /**
     * Wraps the purely real, rational unit 1 into an implementation of 
     * <code>AlgebraicInteger</code>.
     * @param ring The ring of algebraic integers from which to get 1.
     * @return An object implementing <code>AlgebraicInteger</code> representing 
     * the number with real part 1 and imaginary part 0 in the given ring.
     * @throws UnsupportedNumberDomainException If called upon with a type of 
     * ring for which support has not been fleshed out yet.
     */
    public static AlgebraicInteger getOneInRing(IntegerRing ring) {
        if (ring instanceof QuadraticRing) {
            return QuadraticInteger.apply(1, 0, (QuadraticRing) ring);
        }
        if (ring == null) {
            String excMsg = "Null ring does not contain the number 1";
            throw new NullPointerException(excMsg);
        }
        String excMsg = "1 from given ring function not yet supported for " 
                + ring.toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    private static short w(int d) {
        switch (d) {
            case -3:
                return 6;
            case -1:
                return 4;
            default:
                return 2;
        }
    }
    
    private static int imagQuadClassNumFind(int d) {
        double sumMult, interNum;
        sumMult = (double) w(d) / (2.0 * d);
        int kronSum = 0;
        if (d == -4) {
            kronSum = -2;
        }
        for (int i = 1; i < Math.abs(d); i++) {
            kronSum += symbolKronecker(d, i) * i;
        }
        interNum = sumMult * kronSum;
        return (int) Math.round(interNum);
    }
    
    private static int realQuadClassNumFind(int d, RealQuadraticRing r) {
        double sumMult, interNum;
        sumMult = fundamentalUnit(r).getRealPartNumeric();
        sumMult = 2 * Math.log(sumMult);
        sumMult = -1.0 / sumMult;
        double indKron;
        double kronSum = 0.0;
        for (int i = 1; i < d; i++) {
            indKron = Math.log(Math.sin((Math.PI * i)/d));
            indKron *= symbolKronecker(d, i);
            kronSum += indKron;
        }
        interNum = sumMult * kronSum;
        return (int) Math.round(interNum);
    }
    
    /**
     * Gives the class number of a ring of algebraic integers.
     * @param ring The ring for which for which to compute the class number. For 
     * example, <b>Z</b>[&radic;2], <b>Z</b>[&radic;&minus;5].
     * @return A positive integer with the class number, should be 1 for unique 
     * factorization domains, 2 or greater for non-unique factorization domains. 
     * For example, the class number of <b>Z</b>[&radic;2] is 1, the class 
     * number of <b>Z</b>[&radic;&minus;5] is 2. A return of 0 or a negative 
     * integer could indicate either an arithmetic overflow problem or a mistake 
     * on the programmer's part.
     * @throws ArithmeticException If an overflow occurs during the computation. 
     * This is likelier to occur for real quadratic rings than for imaginary 
     * quadratic rings, because the former requires a calculation of the 
     * fundamental unit. I recommend calling {@link 
     * #fundamentalUnit(algebraics.IntegerRing) fundamentalUnit()} directly 
     * first whenever real quadratic rings are involved, as that function might 
     * cause this exception. If this exception does occur, then you know that 
     * the field class number computation would be likewise unsuccessful.
     * @throws NullPointerException If <code>ring</code> is null. The exception 
     * message will be "Null ring does not have class number".
     * @throws UnsupportedNumberDomainException If called upon for a type of 
     * ring which is not yet supported by this function. For example, as of 
     * 2018, this function supports real and imaginary quadratic rings but no 
     * kind of cubic ring, so asking this function to compute the class number 
     * of <b>Z</b>[&#8731;2] should trigger this exception.
     */
    public static int fieldClassNumber(IntegerRing ring) {
        if (ring == null) {
            String excMsg = "Null ring does not have class number";
            throw new NullPointerException(excMsg);
        }
        if (CLASS_NUMBERS_CACHE.containsKey(ring)) {
            return CLASS_NUMBERS_CACHE.get(ring);
        }
        if (ring instanceof QuadraticRing) {
            QuadraticRing r = (QuadraticRing) ring;
            int d = r.getRadicand();
            if (!r.hasHalfIntegers()) {
                d *= 4;
            }
            if (d < 0) {
                int h = imagQuadClassNumFind(d);
                CLASS_NUMBERS_CACHE.put(ring, h);
                return h;
            } else {
                int h = realQuadClassNumFind(d, (RealQuadraticRing) r);
                CLASS_NUMBERS_CACHE.put(ring, h);
                return h;
            }
        }
        String excMsg = "Class number function not yet supported for " 
                + ring.toASCIIString();
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    /**
     * Gives a pseudorandomly chosen power of two.
     * @return A power of two, one of 1, 2, 4, 8, 16, 32, 64, 128, ..., 
     * 1073741824.
     */
    public static int randomPowerOfTwo() {
        int shift = randomNumber(31);
        return 1 << shift;
    }
    
    /**
     * Gives a pseudorandom number. The number may be negative, 0 or positive. 
     * If you need to bound the number more narrowly than the range of 
     * <code>int</code>, use {@link #randomNumber(int)}.
     * @return A pseudorandom number. Examples: 2068000921, &minus;1782689428.
     */
    public static int randomNumber() {
        return RANDOM.nextInt();
    }
    
    /**
     * Gives a pseudorandom positive number. If you need any number in the range 
     * of <code>int</code>, use {@link #randomNumber()}.
     * @param bound The bound for the pseudorandom number. For example, 32768.
     * @return A pseudorandom number, at least 0 but not more than 
     * <code>bound</code>. For example, 19397.
     */
    public static int randomNumber(int bound) {
        return RANDOM.nextInt(bound);
    }

    /**
     * Provides a pseudorandom positive squarefree integer. A pseudorandom 
     * number generator is queried for a pseudorandom integer between 0 and a 
     * specified bound. If that number is not squarefree, this function adds 1 
     * to that number as many times as needed to reach a squarefree number, even 
     * if that number is beyond the specified bound.
     * @param bound The limiting number, the returned number ought not be equal 
     * to or higher than this number &mdash; but a negative integer may be used, 
     * in which case it will be multiplied by &minus;1. For example, for a 
     * pseudorandom squarefree number between 1 and 97, either &minus;100 or 100 
     * can be passed in. However, if this parameter is a number that is not 
     * squarefree and in the middle of or at the end of a range of consecutive 
     * numbers that are not squarefree either, the result can potentially be the 
     * lowest squarefree number greater than this parameter.
     * @return A pseudorandom positive squarefree integer that will usually be 
     * less than the absolute value of <code>bound</code>. For example, given a 
     * bound of 100, this might return 91. However, it's also possible that this 
     * function might return 101 if the first choice was 98 = 2 &times; 
     * 7<sup>2</sup> or 99 = 3<sup>2</sup> &times; 11.
     */
    public static int randomSquarefreeNumber(int bound) {
        if (bound < 0) {
            bound *= -1;
        }
        int choice = RANDOM.nextInt(bound);
        while (!isSquarefree(choice)) {
            choice++;
        }
        return choice;
    }
    
    /**
     * Gives a pseudorandom positive squarefree number in a specified residue 
     * class. This is to obtain pseudorandom numbers of the form <i>xm</i> + 
     * <i>n</i>, where <i>x</i> is some arbitrary integer.
     * @param n The number to add to a multiple of <code>m</code>. For example, 
     * 29. Ought to be at least 0 and preferably less than <code>m</code>.
     * @param m The modulus to reckon <code>n</code> against. Must not be 0.
     * @return A pseudorandom positive squarefree integer.
     * @throws ArithmeticException If <code>m</code> equals 0.
     */
    public static int randomSquarefreeNumberMod(int n, int m) {
        if (m == 0) {
            String excMsg = "Modulus m must not be 0";
            throw new ArithmeticException(excMsg);
        }
        if (!isSquarefree(m)) {
            int gcd = (int) euclideanGCD(m, n);
            if (!isSquarefree(gcd)) {
                String excMsg = "Given that gcd(" + m + ", " + n + ") = " + gcd 
                        + ", there is no squarefree number congruent to " + n 
                        + " mod " + m;
                throw new IllegalArgumentException(excMsg);
            }
        }
        int choice = m * RANDOM.nextInt(4096) + n;
        while (!isSquarefree(choice)) {
            choice += m;
        }
        return choice;
    }
    
    /**
     * Gives a pseudorandom positive squarefree integer other than the one 
     * specified.
     * @param n The number not to return. For example, 133.
     * @param bound The limiting number, which the returned number must not be 
     * equal nor greater to. For example, 200. This parameter ought to be 
     * greater than <code>n</code>, but this is not actually checked. If this 
     * parameter is less than <code>n</code>, then the effect will be much the 
     * same as using {@link #randomSquarefreeNumber(int)} instead.
     * @return A positive squarefree number other than <code>n</code> that is 
     * less than <code>bound</code>. For example, 105.
     */
    public static int randomSquarefreeNumberOtherThan(int n, int bound) {
        int choice;
        do {
            choice = RANDOM.nextInt(bound);
        } while (choice == n || !isSquarefree(choice));
        return choice;
    }
    
}
