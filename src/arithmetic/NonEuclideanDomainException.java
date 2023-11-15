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
package arithmetic;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.AlgebraicInteger;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.QuadraticInteger;

/**
 * This checked exception is to be thrown when an Euclidean GCD function is 
 * called on numbers that are not from an Euclidean domain. If the numbers are 
 * from two different non-Euclidean domains, {@link 
 * AlgebraicDegreeOverflowException} should be thrown instead.
 * @author Alonso del Arte
 */
public class NonEuclideanDomainException extends Exception {
    
    // TODO: Change serialVersionUID after removing tryToFactorizeAnyway( )
    private static final long serialVersionUID = 1058267512;
        
    private final AlgebraicInteger attemptedA, attemptedB;
    
    /**
     * Simply returns the algebraic integers that caused this exception.
     * @return An array of two <code>AlgebraicInteger</code> objects, most 
     * likely in the same order that they were passed to the exception 
     * constructor.
     */
    public AlgebraicInteger[] getEuclideanGCDAttemptedNumbers() {
        return (new AlgebraicInteger[]{attemptedA, attemptedB});
    }
    
    /**
     * Attempts to apply the Euclidean GCD algorithm to the algebraic integers 
     * that triggered this exception. This will deliver some result, but with no 
     * guarantee as to the correctness of that result.
     * @return An algebraic integer with either the successful result of the 
     * algorithm or a number that represents the farthest the algorithm was able 
     * to get before encountering a problem. To indicate that there was a 
     * problem in applying the algorithm, the real part will be negative in the 
     * case of imaginary quadratic integers; for numbers from other kinds of 
     * domains, there will be some other indication that the algorithm did not 
     * reach a proper conclusion. For example, calling this function for gcd(29, 
     * 6 + 4&radic;&minus;5) should return 3 + 2&radic;&minus;5, while calling 
     * it for gcd(2, 1 + &radic;&minus;5) could return &minus;2 or &minus;1 
     * &minus; &radic;&minus;5 or &minus;1 + &radic;&minus;5; in each case the 
     * real part being negative indicates the algorithm was unable to find the 
     * GCD of the two numbers.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come 
     * from different rings, e.g., trying to compute gcd(&radic;&minus;2, 
     * &radic;2) with both of those numbers represented as quadratic integers 
     * from two different quadratic rings rather than quartic integers from the 
     * same quartic ring.
     * @throws UnsupportedNumberDomainException If the particular implementation 
     * of {@link AlgebraicInteger} is not yet supported for this function.
     * @deprecated A function in {@link 
     * calculators.NumberTheoreticFunctionsCalculator} will be provided.
     */
    @Deprecated
    public AlgebraicInteger tryEuclideanGCDAnyway() {
        if (!this.attemptedA.getRing().equals(this.attemptedB.getRing())) {
            String excMsg = "AlgebraicDegreeOverflowException for one number from " 
                    + this.attemptedA.getRing().toASCIIString() 
                    + " and another from " 
                    + this.attemptedB.getRing().toASCIIString();
            int deg = this.attemptedA.algebraicDegree();
            if (this.attemptedB.algebraicDegree() > deg) {
                deg = this.attemptedB.algebraicDegree();
            }
            throw new AlgebraicDegreeOverflowException(excMsg, deg, 
                    this.attemptedA, this.attemptedB);
        }
        if (this.attemptedA instanceof QuadraticInteger) {
            QuadraticInteger currA, currB, tempMultiple, currRemainder;
            if (Math.abs(this.attemptedA.norm()) 
                    < Math.abs(this.attemptedB.norm())) {
                currA = (QuadraticInteger) this.attemptedB;
                currB = (QuadraticInteger) this.attemptedA;
            } else {
                currA = (QuadraticInteger) this.attemptedA;
                currB = (QuadraticInteger) this.attemptedB;
            }
            currRemainder = currA.minus(currA); // Initialize currRemainder to 0
            AlgebraicInteger bounds[];
            boolean troubleFlag = false;
            while ((currB.norm() != 0) && !troubleFlag) {
                try {
                    tempMultiple = currA.divides(currB);
                    tempMultiple = tempMultiple.times(currB);
                    currRemainder = currA.minus(tempMultiple);
                } catch (NotDivisibleException nde) {
                    bounds = nde.getBoundingIntegers();
                    boolean notFound = true;
                    int counter = 0;
                    QuadraticInteger currQI;
                    while (notFound && counter < bounds.length) {
                        currQI = (QuadraticInteger) bounds[counter];
                        tempMultiple = currQI.times(currB);
                        currRemainder = currA.minus(tempMultiple);
                        notFound = Math.abs(currRemainder.norm()) 
                                >= Math.abs(currB.norm());
                        counter++;
                    }
                    troubleFlag = notFound;
                }
                currA = currB;
                currB = currRemainder;
            }
            if ((currA.getRegPartMult() < 0 && !troubleFlag) 
                    || (currA.getRegPartMult() > 0 && troubleFlag)) {
                currA = currA.times(-1);
            }
            return currA;
        }
        throw new UnsupportedNumberDomainException("not supported yet, sorry", 
                this.attemptedA, this.attemptedB);
    }

    /**
     * This is an exception to be thrown by an Euclidean GCD function if called 
     * upon a and b in a ring of Q(sqrt(d)) for d other than the ones listed in 
     * {@link calculators.NumberTheoreticFunctionsCalculator#NORM_EUCLIDEAN_QUADRATIC_IMAGINARY_RINGS_D}.
     * @param message Should probably just be something like "These numbers are 
     * not from an Euclidean domain." This message is just passed on to the 
     * superclass.
     * @param a One of the two algebraic integers for which the request to 
     * compute the Euclidean GCD was declined. If desired, the calling function 
     * may choose the number with larger norm to be a, but this is not required. 
     * However, a and b ought to be in the same ring, otherwise 
     * {@link AlgebraicDegreeOverflowException} should have been used instead.
     * @param b One of the two algebraic integers for which the request to 
     * compute the Euclidean GCD was declined. If desired, the calling function 
     * may choose the number with smaller norm to be b, but this is not 
     * required. However, b and a ought to be in the same ring, otherwise 
     * {@link AlgebraicDegreeOverflowException} should have been used instead.
     */
    public NonEuclideanDomainException(String message, AlgebraicInteger a, 
            AlgebraicInteger b) {
        super(message);
        this.attemptedA = a;
        this.attemptedB = b;
    }
    
}
