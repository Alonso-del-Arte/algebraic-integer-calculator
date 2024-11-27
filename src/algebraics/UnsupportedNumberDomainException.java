/*
 * Copyright (C) 2024 Alonso del Arte
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
 * of {@link algebraics.AlgebraicInteger} can properly represent. Or that some 
 * other operation is not yet supported for a particular kind of ring of 
 * integers. However, if the result of the arithmetic operation is an algebraic 
 * integer of higher degree than the pertinent implementations supports, {@link 
 * algebraics.AlgebraicDegreeOverflowException} should be used instead.
 * @author Alonso del Arte
 */
public class UnsupportedNumberDomainException extends RuntimeException {
    
    private static final long serialVersionUID = 1058433824;
    
    private final AlgebraicInteger unsupRingNumberA;
    private final AlgebraicInteger unsupRingNumberB;
    
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
        return (new AlgebraicInteger[]{this.unsupRingNumberA, 
            this.unsupRingNumberB});
    }
    
    /**
     * Retrieves the ring that triggered this exception.
     * @return The algebraic integer ring object that this exception was 
     * constructed with, if it was constructed with the single {@link 
     * IntegerRing} parameter constructor, or the ring of the first algebraic 
     * integer parameter if the exception was constructed with either the 1- or 
     * 2-number constructor. This is guaranteed to not be null.
     */
    public IntegerRing getCausingDomain() {
        return this.unsupDomain;
    }
    
    /**
     * Constructor requiring only a ring parameter. The two numbers are filled 
     * in as nulls. Use this constructor when no specific numbers from the 
     * unsupported ring have been dealt with.
     * @param message A message to pass on to the {@code Exception} constructor.
     * @param ring An object representing the ring that triggered this 
     * exception. This suggests that supporting this domain may be a simple 
     * matter of adding something to a class that needs to be able to handle 
     * numbers from this domain. This constructor is to be used when the 
     * throwing function has an object for the ring but no objects for the 
     * numbers in the ring. Must not be null.
     * @throws NullPointerException If {@code ring} is null.
     */
    public UnsupportedNumberDomainException(String message, IntegerRing ring) {
        this(message, ring, null, null);
    }

    /**
     * Constructor requiring only a single number parameter. The ring is 
     * inferred from the number parameter, and the second number is filled in as 
     * null. Use this constructor when only one number from the unsupported ring 
     * has been dealt with.
     * @param message A message to pass on to the {@code Exception} constructor.
     * @param numberA The number that caused the exception. Then the second 
     * number will be set to null. So if the exception is caused by two 
     * different numbers, or two numbers believed to be distinct, you should use 
     * the 2-number constructor instead.
     * @throws NullPointerException If {@code numberB} is null.
     */
    public UnsupportedNumberDomainException(String message, 
            AlgebraicInteger numberA) {
        this(message, numberA, null);
    }
    
    /**
     * Primary constructor. The ring is inferred from the first number, but both 
     * numbers should come from the same ring.
     * @param message A message to pass on to the {@code Exception} constructor.
     * @param numberA One of the two numbers that caused the exception. It need 
     * not be smaller or larger than {@code numberB} in any sense (norm, 
     * absolute value, etc.) but it is expected to come from the same ring of 
     * algebraic integers. For example, 1 + &#8731;2. Must not be null.
     * @param numberB One of the two numbers that caused the exception. It need 
     * not be larger or smaller than {@code numberA} in any sense (norm, 
     * absolute value, etc.) but it is expected to come from the same ring of 
     * algebraic integers. For example, 7 + 5&#8731;2. May be null. But if the 
     * exception is caused by a single number (such as, for example, in a prime 
     * factorization function) use the single-number constructor instead.
     * @throws IllegalArgumentException If {@code numberA} and {@code numberB} 
     * come from different rings. Of course the possibility exists that once the 
     * pertinent rings are supported, trying to use them for the operation that 
     * caused this exception will instead cause an {@link 
     * AlgebraicDegreeOverflowException}.
     * @throws NullPointerException If {@code numberA} is null. {@code numberB}
     * is allowed to be null.
     */
    public UnsupportedNumberDomainException(String message, 
            AlgebraicInteger numberA, AlgebraicInteger numberB) {
        this(message, numberA.getRing(), numberA, numberB);
    }
    
    private UnsupportedNumberDomainException(String message, IntegerRing ring, 
            AlgebraicInteger numberA, AlgebraicInteger numberB) {
        super(message);
        if (ring == null) {
            String excMsg = "Ring parameter must not be null";
            throw new NullPointerException(excMsg);
        }
        if (numberB != null) {
            IntegerRing checkRing = numberB.getRing();
            if (!numberA.getRing().equals(checkRing)) {
                String excMsg = numberA.toASCIIString() + " is from " 
                        + ring.toASCIIString() + " but " 
                        + numberB.toASCIIString() + " is from " 
                        + checkRing.toASCIIString();
                throw new IllegalArgumentException(excMsg);
            }
        }
        this.unsupDomain = ring;
        this.unsupRingNumberA = numberA;
        this.unsupRingNumberB = numberB;
    }
    
}
