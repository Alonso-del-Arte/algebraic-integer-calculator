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
package algebraics.unary;

import algebraics.AlgebraicInteger;
import arithmetic.Arithmeticable;
import arithmetic.NotDivisibleException;
import fractions.Fraction;

/**
 * Represents algebraic integers of degree 1. Essentially this is another 
 * wrapper for <code>int</code> primitives.
 * @author Alonso del Arte
 */
public final class UnaryInteger implements AlgebraicInteger, 
        Arithmeticable<UnaryInteger>, Comparable<UnaryInteger> {
    
    private final int number;
    
    /**
     * Retrieves the 32-bit integer this number object was constructed with. For 
     * the example, suppose this instance was constructed with &minus;1729.
     * @return The 32-bit integer primitive that was passed to the constructor. 
     * For example, &minus;1729.
     */
    public int getNumber() {
        return this.number;
    }
    
    /**
     * Adds an unary integer to this one. For example, let's say this integer is 
     * 1728.
     * @param addend The unary integer to add. For example, 1.
     * @return The sum of the two unary integers. For example, 1729.
     * @throws ArithmeticException If the result of adding this number to  
     * <code>addend</code> can't be represented by a 32-bit signed integer.
     */
    @Override
    public UnaryInteger plus(UnaryInteger addend) {
        return new UnaryInteger(Math.addExact(this.number, addend.number));
    }

    /**
     * Adds an unary integer to this one. For example, let's say this integer is 
     * 1000.
     * @param addend The unary integer to add. For example, 729.
     * @return The sum of the two unary integers. For example, 1729.
     * @throws ArithmeticException If the result of adding this number to 
     * <code>addend</code> can't be represented by a 32-bit signed integer.
     */
    @Override
    public UnaryInteger plus(int addend) {
        return new UnaryInteger(Math.addExact(this.number, addend));
    }
    
    /**
     * Negates this unary integer. Essentially the same as multiplying by 
     * &minus;1.
     * @return This unary integer negated. For example, for 3190 this returns 
     * &minus;3190, and called on &minus;3190 this returns 3190. In the case of 
     * 0, the result is 0. This may or may not be a fresh instance.
     */
    @Override
    public UnaryInteger negate() {
        return new UnaryInteger(-this.number);
    }

    /**
     * Subtracts an unary integer from this one. For example, let's say this 
     * integer is 1000.
     * @param subtrahend The unary integer to subtract. For example, 271.
     * @return The sum of this unary integer and the negated subtrahend. For 
     * example, 729.
     * @throws ArithmeticException If the result of subtracting  
     * <code>subtrahend</code> from this integer can't be represented by a 
     * 32-bit signed integer.
     */
    @Override
    public UnaryInteger minus(UnaryInteger subtrahend) {
        return this.plus(subtrahend.negate());
    }

    /**
     * Subtracts an integer from this unary integer. For example, let's say this 
     * integer is 1000.
     * @param subtrahend The unary integer to subtract. For example, 271.
     * @return The sum of this unary integer and the negated subtrahend. For 
     * example, 729.
     * @throws ArithmeticException If the result of subtracting  
     * <code>subtrahend</code> from this integer can't be represented by a 
     * 32-bit signed integer.
     */
    @Override
    public UnaryInteger minus(int subtrahend) {
        return new UnaryInteger(Math.subtractExact(this.number, subtrahend));
    }

    /**
     * Multiplies this unary integer by another. For the example, say this 
     * number is &minus;1729.
     * @param multiplicand The number to multiply by. For example, 1728.
     * @return The product of this number by {@code multiplicand}. For example, 
     * &minus;2987712.
     * @throws ArithmeticException If the product overflows the range of 32-bit 
     * signed integers.
     */
    @Override
    public UnaryInteger times(UnaryInteger multiplicand) {
        return new UnaryInteger(Math.multiplyExact(this.number, 
                multiplicand.number));
    }

    /**
     * Multiplies this unary integer by a 32-bit integer primitive. For the 
     * example, say this number is 1729.
     * @param multiplicand The number to multiply by. For example, &minus;1728.
     * @return The product of this number by {@code multiplicand}. For example, 
     * &minus;2987712.
     * @throws ArithmeticException If the product overflows the range of 32-bit 
     * signed integers.
     */
    @Override
    public UnaryInteger times(int multiplicand) {
        return new UnaryInteger(Math.multiplyExact(this.number, multiplicand));
    }

    /**
     * Divides this unary integer by another, but only if this number is evenly 
     * divisible by the other. For the example, suppose this integer is 6724, 
     * which factorizes as 2<sup>2</sup> &times; 41<sup>2</sup>.
     * @param divisor The number to divide by. For example, &minus;3362.
     * @return The division. In the example, this would be &minus;2.
     * @throws IllegalArgumentException If {@code divisor} is 0.
     * @throws NotDivisibleException If this number is not evenly divisible by 
     * {@code divisor}. In the example, this exception would occur for any 
     * nonzero number other than &plusmn;1, &plusmn;2, &plusmn;4, &plusmn;41, 
     * &plusmn;82, &plusmn;164, &plusmn;1681, &plusmn;3362, &plusmn;6724. 
     */
    @Override
    public UnaryInteger divides(UnaryInteger divisor) 
            throws NotDivisibleException {
        if (divisor.number == 0) {
            String excMsg = "Can't divide " + this.toASCIIString() + " by 0";
            throw new IllegalArgumentException(excMsg);
        }
        int remainder = this.number % divisor.number;
        if (remainder != 0) {
            String excMsg = "With a remainder of " + remainder + ", " 
                    + this.number + " does not divide " + divisor.number 
                    + " evenly";
            Fraction fraction = new Fraction(this.number, divisor.number);
            Fraction[] fractions = {fraction};
            throw new NotDivisibleException(excMsg, this, divisor, fractions);
        }
        int n = this.number / divisor.number;
        return new UnaryInteger(n);
    }

    @Override
    public UnaryInteger divides(int divisor) throws NotDivisibleException {
        return this.divides(new UnaryInteger(divisor));
    }

    @Override
    public UnaryInteger mod(UnaryInteger divisor) {
        if (divisor.number == 0) {
            String excMsg = "Taking " + this.toASCIIString() + " modulo " 
                    + divisor.toASCIIString() + " is not a valid operation";
            throw new IllegalArgumentException(excMsg);
        }
        int remainder = this.number % divisor.number;
        return new UnaryInteger(remainder);
    }

    @Override
    public UnaryInteger mod(int divisor) {
        return this.mod(new UnaryInteger(divisor));
    }
    
    @Override
    public int compareTo(UnaryInteger other) {
        return Integer.compare(this.number, other.number);
    }
    
    /**
     * Gives the algebraic degree of this number. See also {@link 
     * #minPolynomialCoeffs()} and {@link #minPolynomialString()}.
     * @return 0 if this number is 0, otherwise always 1.
     */
    @Override
    public int algebraicDegree() {
        if (this.number == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Gives the trace of this number. Since unary integers don't have distinct 
     * conjugates, this gives the same number as {@link #getNumber()}, but in a 
     * 64-bit integer. For the example, suppose this number is &minus;7.
     * @return The same number as {@link #norm()}. For example, {@code -7L}.
     */
    @Override
    public long trace() {
        return this.number;
    }

    @Override
    public long norm() {
        return this.number;
    }

    /**
     * Gives the coefficients for the minimal polynomial of this integer. They  
     * are given in the reverse order of the normal expression of the minimal 
     * polynomial.
     * @return The minimum polynomial coefficients. For example, if this number 
     * is 7, the minimum polynomial is <i>x</i> &minus; 7, so the coefficients 1 
     * and &minus;7 are given as {&minus;7, 1}.
     */
    @Override
    public long[] minPolynomialCoeffs() {
        return new long[]{-this.number, 1L};
    }

    // TODO: Write tests for this
    @Override
    public String minPolynomialString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String minPolynomialStringTeX() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String minPolynomialStringHTML() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    /**
     * Gives an object representing the ring <b>Z</b>.
     * @return Always <b>Z</b>.
     */
    @Override
    public UnaryRing getRing() {
        return UnaryRing.Z;
    }

    /**
     * Gives a text representation of this integer using some or all of the 
     * digits 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 and the minus sign when applicable.
     * @return A text representation. Examples: "&minus;28", "0", "1729".
     */
    @Override
    public String toString() {
        if (this.number < 0) {
            return "\u2212" + (-this.number);
        } else {
            return Integer.toString(this.number);
        }
    }
    
    /**
     * Gives a text representation of this integer using only ASCII characters.
     * @return A text representation. Examples: "-28" for &minus;28, "0", 
     * "1729".
     */
    @Override
    public String toASCIIString() {
        return Integer.toString(this.number);
    }

    /**
     * A text representation of the algebraic integer suitable for use in a TeX 
     * document.
     * @return The same as {@link #toASCIIString()}. Examples: "-28" for 
     * &minus;28, "0", "1729".
     */
    @Override
    public String toTeXString() {
        return this.toASCIIString();
    }

    /**
     * A text representation of the algebraic integer suitable for use in an 
     * HTML page. Uses some or all of the digits 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 
     * and when applicable the HTML entity for the minus sign.
     * @return A text representation for an HTML page. Examples: 
     * "&amp;minus;28", "0", "1729".
     */
    @Override
    public String toHTMLString() {
        if (this.number < 0) {
            return "&minus;" + (-this.number);
        } else {
            return Integer.toString(this.number);
        }
    }
    
    /**
     * Determines whether this unary integer is equal to some other object. For 
     * the examples below, let's say this unary integer is 1729.
     * @param obj The object to compare for equality. Examples: the number 1729 
     * in an <code>UnaryInteger</code>, the number 1729 in an 
     * <code>Integer</code>, the number &minus;1729 in an 
     * <code>UnaryInteger</code>, the number 1728 in an 
     * <code>UnaryInteger</code>.
     * @return True if and only if <code>obj</code> is an 
     * <code>UnaryInteger</code> object representing the same number, false in 
     * all other cases. For the examples, this function returns true only for 
     * the first one, false for the othre ones.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnaryInteger)) {
            return false;
        }
        return this.number == ((UnaryInteger) obj).number;
    }
    
    /**
     * Gives a hash code for this unary integer mathematically guaranteed to be 
     * unique. This means that if two <code>UnaryInteger</code> objects are 
     * equal according to {@link #equals(java.lang.Object) equals()}, they both 
     * get the same hash code.
     * @return The hash code, most likely matching this number. For example, if 
     * this number is 1728, the hash code will almost certainly be 1728.
     */
    @Override
    public int hashCode() {
        return this.number;
    }

    /**
     * Gives the absolute value of this integer, which is its distance from 0. 
     * Mathematically guaranteed to be a precise integer value (this is a 
     * guarantee we can't make for other implementations of {@link 
     * algebraics.AlgebraicInteger}). Examples: &minus;7, 0 and 7.
     * @return The absolute value of this integer. For the examples, this would 
     * be 7.0 for both &minus;7 and 7, 0.0 for 0.
     */
    @Override
    public double abs() {
        return Math.abs(this.number);
    }

    /**
     * Gives the real part of this number. Since <b>Z</b> is a purely real ring, 
     * this function just gives this integer as a floating point number.
     * @return This number. The same as {@link #getNumber()} but as a 64-bit 
     * floating number instead of a 32-bit integer. For example, for &minus;7, 
     * this would be &minus;7.0.
     */
    @Override
    public double getRealPartNumeric() {
        return this.number;
    }

    /**
     * Gives the imaginary part of this integer. Since <b>Z</b> is a purely real 
     * ring, every number in this ring has an imaginary part of 0.
     * @return Always 0.0 for all instances of this class.
     */
    @Override
    public double getImagPartNumeric() {
        return 0.0;
    }

    /**
     * Indicates whether the real part of this number, as given by {@link 
     * #getRealPartNumeric()}, is an approximation or not. Since the numbers 
     * represented by this class can all be represented by 32-bit integer 
     * primitives, and {@code getRealPartNumeric()} returns 64-bit floating 
     * point numbers that can represent 53-bit integers precisely, loss of 
     * precision is not a concern here.
     * @return Always false for all instances of this class.
     */
    @Override
    public boolean isReApprox() {
        return false;
    }

    /**
     * Indicates whether the imaginary part of this number, as given by {@link 
     * #getImagPartNumeric()}, is an approximation or not. Since the imaginary 
     * part of any number represented by this class is 0 and 0.0 is easily 
     * represented in 64-bit floating point, the imaginary part does not have to 
     * be approximated.
     * @return Always false for all instances of this class.
     */
    @Override
    public boolean isImApprox() {
        return false;
    }

    /**
     * Gives the angle of this number. Also called "argument," "phase" or 
     * "amplitude."
     * @return 0.0 if this number is 0 or positive, 3.141592653589793 (the 
     * 64-bit floating point approximation to &pi;) if this number is negative.
     */
    @Override
    public double angle() {
        if (this.number < 0) {
            return Math.PI;
        } else {
            return 0.0;
        }
    }
    
    /**
     * Sole constructor.
     * @param n The integer. For example, 1729.
     */
    public UnaryInteger(int n) {
        this.number = n;
    }

}
