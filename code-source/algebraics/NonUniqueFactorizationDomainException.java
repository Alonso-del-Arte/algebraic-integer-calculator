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
import algebraics.quadratics.QuadraticInteger;
//import algebraics.quadratics.RealQuadraticInteger;
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
    
    private static final long serialVersionUID = 1058267088;
    
    private final AlgebraicInteger unfactorizedNumber;
    
    /**
     * Just a getter for the algebraic integer that triggered this exception.
     * @return An imaginary quadratic integer object. Calling 
     * {@link ImaginaryQuadraticInteger#getRing()} should return a ring 
     * adjoining &radic;<i>d</i> with <i>d</i> being a negative, squarefree 
     * number other than those listed in 
     * {@link NumberTheoreticFunctionsCalculator#HEEGNER_NUMBERS}.
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
     * &times; 2 &times; 3, on account of the famous alternate factorization (1 
     * &minus; &radic;&minus;5)(1 + &radic;&minus;5).
     * @throws UnsupportedNumberDomainException If the algebraic integer is from 
     * a ring not yet supported for this function, this runtime exception will 
     * be thrown. For example, if this function currently only supports real and 
     * imaginary quadratic integers but it is called on a real cubic integer.
     */
    public List<AlgebraicInteger> tryToFactorizeAnyway() {
        if (this.unfactorizedNumber instanceof QuadraticInteger) {
            QuadraticInteger n = (QuadraticInteger) this.unfactorizedNumber;
            QuadraticInteger unity = n.minus(n).plus(1);
            QuadraticInteger negOne = unity.times(-1);
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
                int testDivRealPartMult = 0;
                int testDivImagPartMult = 2;
                if (n.getRing().hasHalfIntegers()) {
                    testDivRealPartMult = 1;
                    testDivImagPartMult = 1;
                }
                boolean withinRange;
                while (n.norm() > 1) {
                    testDivisor = new ImaginaryQuadraticInteger(testDivRealPartMult, testDivImagPartMult, n.getRing(), 2);
                    withinRange = (testDivisor.norm() <= n.norm());
                    if (NumberTheoreticFunctionsCalculator.isIrreducible(testDivisor)) {
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
                                withinRange = ((Math.abs(nde.getNumericRealPart()) >= 1) || (Math.abs(nde.getNumericImagPart()) >= 1));
                                keepGoing = false;
                            }
                        }
                    }
                    if (withinRange) {
                        testDivRealPartMult += 2;
                    } else {
                        if (!n.getRing().hasHalfIntegers()) {
                            testDivRealPartMult = 0;
                            testDivImagPartMult += 2;
                        } else {
                            if (testDivImagPartMult % 2 == 0) {
                                testDivRealPartMult = 1;
                            } else {
                                testDivRealPartMult = 0;
                            }
                            testDivImagPartMult++;
                        }
                    }
                }
                factors.add(n); // This should be a unit, most likely -1 or 1
            }
            factors = NumberTheoreticFunctionsCalculator.sortListAlgebraicIntegersByNorm(factors);
            int quadrantAdjustStart = 1;
            keepGoing = true;
            while (quadrantAdjustStart < factors.size() && keepGoing) {
                if (factors.get(quadrantAdjustStart).norm() == 1) {
                    quadrantAdjustStart++;
                } else {
                    keepGoing = false;
                }
            }
            QuadraticInteger currFac;
            QuadraticInteger currFirstUnit = unity;
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
        throw new UnsupportedNumberDomainException("Only real and imaginary quadratic supported at this time.", this.unfactorizedNumber);
    }
    
    /**
     * This is an exception to be potentially thrown by a prime factorization 
     * function if called upon to operate on a number from a domain that is not 
     * a unique factorization domain (UFD), such as those adjoining the square 
     * root of a negative number other than those listed in {@link 
     * NumberTheoreticFunctionsCalculator#HEEGNER_NUMBERS}. There are many more 
     * real quadratic integer rings that are UFDs, but that's outside the scope 
     * of this documentation.
     * @param message Should probably just be something like 
 number.getCausingRing().toString() + " is not a unique factorizaton domain." 
 This message is just passed on to the superclass.
     * @param number The number sent to the prime factorization function, like, 
     * for example, 1 + sqrt(-30).
     */
    public NonUniqueFactorizationDomainException(String message, AlgebraicInteger number) {
        super(message);
        this.unfactorizedNumber = number;
    }
    
}
