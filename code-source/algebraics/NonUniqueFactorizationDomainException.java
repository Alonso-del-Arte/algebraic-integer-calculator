/*
 * Copyright (C) 2018 Alonso del Arte
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

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * This checked exception is to be thrown when a prime factorization function is 
 * called on a number that is not from a unique factorization domain. One 
 * possible way to recover from this exception is to use the exception's {@link 
 * #tryToFactorizeAnyway() tryToFactorizeAnyway()} function.
 * @author Alonso del Arte
 */
public class NonUniqueFactorizationDomainException extends Exception {
    
    private static final long serialVersionUID = 1058571365;
    
    private final AlgebraicInteger unfactorizedNumber;
    
    /**
     * Just a getter for the algebraic integer that triggered this exception.
     * @return An algebraic integer object, the one that this exception was 
     * constructed with in the first place.
     */
    public AlgebraicInteger getUnfactorizedNumber() {
        return this.unfactorizedNumber;
    }
    
    /**
     * Attempts to factorize the unfactorized number that triggered this 
     * exception.
     * @return A list of algebraic integers that should multiply to the 
     * previously unfactorized number. The list will include &minus;1  at least 
     * twice if the algorithm encountered factors that are irreducible but not 
     * prime. This suggests that at least one other distinct factorization may 
     * be possible. For example, the factorization of 6 in 
     * <b>Z</b>[&radic;&minus;5] might be given as &minus;1 &times; &minus;1  
     * &times; &minus;1 &times; &minus;1 &times; 2 &times; 3, on account of the 
     * famous alternate factorization (1 &minus; &radic;&minus;5)(1 + 
     * &radic;&minus;5).
     * @throws UnsupportedNumberDomainException If the algebraic integer is from 
     * a ring not yet supported for this function, this runtime exception will 
     * be thrown. For example, if this function currently only supports real and 
     * imaginary quadratic integers but it is called on a real cubic integer, 
     * then this exception will be thrown, with a detail message to that effect.
     */
    public List<AlgebraicInteger> tryToFactorizeAnyway() {
        if (this.unfactorizedNumber instanceof QuadraticInteger) {
            QuadraticInteger n = (QuadraticInteger) this.unfactorizedNumber;
            boolean realFlag = this.unfactorizedNumber instanceof RealQuadraticInteger;
            QuadraticRing r = n.getRing();
            QuadraticInteger unity = (QuadraticInteger) NumberTheoreticFunctionsCalculator.getOneInRing(r);
            QuadraticInteger negOne = unity.times(-1);
            QuadraticInteger currFirstUnit = unity;
            List<AlgebraicInteger> factors = new ArrayList<>();
            if (Math.abs(n.norm()) < 2) {
                factors.add(n);
                return factors;
            }
            boolean keepGoing = true;
            if (NumberTheoreticFunctionsCalculator.isIrreducible(n)) {
                factors.add(unity);
                factors.add(n);
                if (!NumberTheoreticFunctionsCalculator.isPrime(n)) {
                    factors.add(negOne);
                    factors.add(negOne);
                }
            } else {
                QuadraticInteger testDivisor = unity.plus(1); // Should be 2
                if (NumberTheoreticFunctionsCalculator.isIrreducible(testDivisor)) {
                    while (n.norm() % 4 == 0 && keepGoing) {
                        try {
                            n = n.divides(testDivisor);
                            factors.add(testDivisor);
                            if (!NumberTheoreticFunctionsCalculator.isPrime(testDivisor)) {
                                factors.add(negOne);
                                factors.add(negOne);
                            }
                        } catch (NotDivisibleException nde) {
                            keepGoing = false;
                        }
                    }
                }
                testDivisor = testDivisor.plus(1);
                keepGoing = true;
                while (Math.abs(n.norm()) >= Math.abs(testDivisor.norm()) && keepGoing) {
                    if (NumberTheoreticFunctionsCalculator.isIrreducible(testDivisor)) {
                        while (n.norm() % testDivisor.norm() == 0 && keepGoing) {
                            try {
                                n = n.divides(testDivisor);
                                factors.add(testDivisor);
                                if (!NumberTheoreticFunctionsCalculator.isPrime(testDivisor)) {
                                    factors.add(negOne);
                                    factors.add(negOne);
                                }
                            } catch (NotDivisibleException nde) {
                                keepGoing = false;
                            }
                        }
                    }
                    testDivisor = testDivisor.plus(2);
                }
                int testDivRegPartMult = 0;
                int testDivSurdPartMult = 2;
                if (n.getRing().hasHalfIntegers()) {
                    testDivRegPartMult = 1;
                    testDivSurdPartMult = 1;
                }
                boolean withinRange;
                while (Math.abs(n.norm()) > 1) {
                    if (r instanceof ImaginaryQuadraticRing) {
                        testDivisor = new ImaginaryQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
                    }
                    if (r instanceof RealQuadraticRing) {
                        testDivisor = new RealQuadraticInteger(testDivRegPartMult, testDivSurdPartMult, r, 2);
                    }
                    withinRange = (testDivisor.norm() <= n.norm());
                    if (NumberTheoreticFunctionsCalculator.isIrreducible(testDivisor) && Math.abs(testDivisor.norm()) != 1) {
                        keepGoing = true;
                        while (n.norm() % testDivisor.norm() == 0 && keepGoing) {
                            try {
                                n = n.divides(testDivisor.conjugate());
                                factors.add(testDivisor.conjugate());
                                if (!NumberTheoreticFunctionsCalculator.isPrime(testDivisor)) {
                                    factors.add(negOne);
                                    factors.add(negOne);
                                }
                            } catch (NotDivisibleException nde) {
                                /* We just ignore the exception when it pertains 
                                   to the conjugate */
                            }
                            try {
                                n = n.divides(testDivisor);
                                factors.add(testDivisor);
                                if (!NumberTheoreticFunctionsCalculator.isPrime(testDivisor)) {
                                    factors.add(negOne);
                                    factors.add(negOne);
                                }
                            } catch (NotDivisibleException nde) {
                                if (realFlag) {
                                    withinRange = Math.abs(testDivisor.getRealPartNumeric()) <= Math.abs(n.getRealPartNumeric());
                                } else {
                                    withinRange = ((Math.abs(nde.getNumericRealPart()) >= 1) || (Math.abs(nde.getNumericImagPart()) >= 1));
                                }
                                keepGoing = false;
                            }
                        }
                    }
                    if (withinRange) {
                        testDivRegPartMult += 2;
                    } else {
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
                    }
                }
                factors.add(n); // This should be a unit, most likely -1 or 1
                currFirstUnit = n;
            }
            factors = NumberTheoreticFunctionsCalculator.sortListAlgebraicIntegersByNorm(factors);
            int quadrantAdjustStart = 1;
            keepGoing = true;
            while (quadrantAdjustStart < factors.size() && keepGoing) {
                if (Math.abs(factors.get(quadrantAdjustStart).norm()) == 1) {
                    quadrantAdjustStart++;
                } else {
                    keepGoing = false;
                }
            }
            QuadraticInteger currFac;
            for (int i = quadrantAdjustStart; i < factors.size(); i++) {
                currFac = (QuadraticInteger) factors.get(i);
                if (currFac.getRegPartMult() < 0 || ((currFac.getRegPartMult() == 0 && currFac.getSurdPartMult() < 0))) {
                    factors.set(i, currFac.times(-1));
                    currFirstUnit = currFirstUnit.times(-1);
                    factors.set(0, currFirstUnit);
                }
            }
            int removalIndex = 0;
            while (removalIndex < factors.size()) {
                if (unity.equals(factors.get(removalIndex))) {
                    factors.remove(removalIndex);
                }
                removalIndex++;
            }
            return factors;
        }
        String exceptionMessage = "Only real and imaginary quadratic integers are supported at this time, not numbers from " + this.unfactorizedNumber.getRing().toASCIIString();
        throw new UnsupportedNumberDomainException(exceptionMessage, this.unfactorizedNumber);
    }
    
    /**
     * This is an exception to be potentially thrown by a prime factorization 
     * function if called upon to operate on a number from a domain that is not 
     * a unique factorization domain (UFD), such as those adjoining the square 
     * root of a negative integer other than those listed in {@link 
     * NumberTheoreticFunctionsCalculator#HEEGNER_NUMBERS}. There are many more 
     * real quadratic integer rings that are UFDs, but that's outside the scope 
     * of this documentation.
     * @param message Should probably just be something like "The number is from 
     * a ring that is not a unique factorization domain." This message is just 
     * passed on to the superclass.
     * @param number The number sent to the prime factorization function, like, 
     * for example, 2 + &radic;&minus;30.
     */
    public NonUniqueFactorizationDomainException(String message, AlgebraicInteger number) {
        super(message);
        this.unfactorizedNumber = number;
    }
    
}
