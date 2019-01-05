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

/**
 * A runtime exception to indicate when the result of an arithmetic operation 
 * results in an algebraic integer which no currently available implementation 
 * of {@link AlgebraicInteger} can properly represent. Or that some other 
 * operation is not yet supported for a particular kind of ring of integers. 
 * However, if the result of the arithmetic operation is an algebraic integer of 
 * higher degree than the pertinent implementations supports, {@link 
 * AlgebraicDegreeOverflowException} should be used instead.
 * @author Alonso del Arte
 */
public class UnsupportedNumberDomainException extends RuntimeException {
    
    private static final long serialVersionUID = 1058433824;
    
    private final AlgebraicInteger diffRingNumberA;
    private final AlgebraicInteger diffRingNumberB;
    
    private final IntegerRing unsupDomain;
    
    /**
     * Retrieves the number or the two numbers that triggered this exception.
     * @return An array of two objects implementing the {@link AlgebraicInteger} 
     * interface. They should both be instances of the same class (e.g., both 
     * {@link algebraics.quadratics.ImaginaryQuadraticInteger}), and they should 
     * come from the same unsupported ring. These are just the numbers that were 
     * supplied to the constructor. There is the possibility that the second 
     * array element could be null, or they could both be null, depending on how 
     * the exception was constructed.
     */
    public AlgebraicInteger[] getCausingNumbers() {
        return (new AlgebraicInteger[]{this.diffRingNumberA, this.diffRingNumberB});
    }
    
    /**
     * Retrieves the ring that triggered this exception.
     * @return The algebraic integer ring object that this exception was 
     * constructed with, if it was constructed with the single {@link 
     * IntegerRing} parameter constructor, or the ring of the first algebraic 
     * integer parameter if the exception was constructed with either the 1- or 
     * 2-number constructor.
     */
    public IntegerRing getCausingDomain() {
        return this.unsupDomain;
    }
    
    /**
     * This exception should be thrown when a particular class does not yet have 
     * the means for handling an algebraic integer from a specific 
     * implementation of {@link AlgebraicInteger}.
     * @param message A message to pass on to the {@link Exception} constructor.
     * @param ring An object representing the ring that triggered this 
     * exception. This suggests that supporting this domain may be a simple 
     * matter of adding something to a class that needs to be able to handle 
     * numbers from this domain. This constructor is to be used when the 
     * throwing function has an object for the ring but no objects for the 
     * numbers in the ring.
     */
    public UnsupportedNumberDomainException(String message, IntegerRing ring) {
        super(message);
        this.diffRingNumberA = null;
        this.diffRingNumberB = null;
        this.unsupDomain = ring;
    }

    /**
     * This exception should be thrown when a particular class does not have the 
     * means for handling an algebraic integer from a specific implementation of 
     * {@link AlgebraicInteger}.
     * @param message A message to pass on to the {@link Exception} constructor.
     * @param numberA The number that caused the exception. Then numberB will be 
     * set to null. So if the exception is caused by two different numbers, or 
     * two numbers believed to be distinct, you should use the 2-number 
     * constructor instead.
     */
    public UnsupportedNumberDomainException(String message, AlgebraicInteger numberA) {
        super(message);
        this.diffRingNumberA = numberA;
        this.diffRingNumberB = null;
        this.unsupDomain = this.diffRingNumberA.getRing();
    }
    
    /**
     * This exception should be thrown when the result of an arithmetic 
     * operation on two objects implementing the {@link AlgebraicInteger} 
     * results in an algebraic integer which no currently available 
     * implementation of AlgebraicInteger can handle.
     * @param message A message to pass on to the {@link Exception} constructor.
     * @param numberA One of the two numbers that caused the exception. It need 
     * not be smaller or larger than numberB in any sense (norm, absolute value, 
     * etc.) but it is expected to come from the same ring of algebraic 
     * integers. For example, 1 + &#8731;2.
     * @param numberB One of the two numbers that caused the exception. It need 
     * not be larger or smaller than numberA in any sense (norm, absolute value, 
     * etc.) but it is expected to come from the same ring of algebraic 
     * integers. For example, 7 + 5&#8731;2. If the exception is caused by a 
     * single number (such as, for example, in a prime factorization function) 
     * use the single-number constructor instead.
     */
    public UnsupportedNumberDomainException(String message, AlgebraicInteger numberA, AlgebraicInteger numberB) {
        super(message);
        this.diffRingNumberA = numberA;
        this.diffRingNumberB = numberB;
        this.unsupDomain = this.diffRingNumberA.getRing();
    }
    
}
