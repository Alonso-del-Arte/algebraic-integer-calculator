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
package arithmetic;

import algebraics.AlgebraicInteger;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.QuadraticRing;
import arithmetic.comparators.NormAbsoluteComparator;

import static calculators.NumberTheoreticFunctionsCalculator.irreducibleFactors;

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
    
    private static final NormAbsoluteComparator COMPARATOR 
            = new NormAbsoluteComparator();
    
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
     * prime. When this happens, it would suggest that at least one other 
     * distinct factorization may be possible. For example, the factorization of 
     * 6 in <b>Z</b>[&radic;&minus;5] might be given as &minus;1 &times; 
     * &minus;1  &times; &minus;1 &times; &minus;1 &times; 2 &times; 3, on 
     * account of the famous alternate factorization (1 &minus; 
     * &radic;&minus;5)(1 + &radic;&minus;5).
     * @throws UnsupportedNumberDomainException If the algebraic integer is from 
     * a ring not yet supported for this function, this runtime exception will 
     * be thrown. For example, if this function currently only supports real and 
     * imaginary quadratic integers but it is called on a real cubic integer, 
     * then this exception will be thrown, with a detail message to that effect.
     */
    public List<AlgebraicInteger> tryToFactorizeAnyway() {
        if (this.unfactorizedNumber.getRing() instanceof QuadraticRing) {
            return irreducibleFactors(this.unfactorizedNumber);
        }
        String excMsg = "Numbers from " 
                + this.unfactorizedNumber.getRing().toASCIIString() 
                + " are not supported yet";
        throw new UnsupportedNumberDomainException(excMsg, 
                this.unfactorizedNumber);
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
    public NonUniqueFactorizationDomainException(String message, 
            AlgebraicInteger number) {
        super(message);
        this.unfactorizedNumber = number;
    }
    
}
