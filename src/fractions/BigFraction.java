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
package fractions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Represents a fraction. Uses {@code BigInteger} for both the numerator and the
 * denominator. Thus a {@code BigFraction} instance can represent all the same 
 * numbers as a {@link Fraction} instance, as well as fractions with much larger 
 * numerators and/or denominators. For example, the number 
 * <sup>9223372036854775809</sup>&frasl;<sub>9223372036854775808</sub> is beyond 
 * the capability of {@code Fraction} to represent, but is no problem for 
 * {@code BigFraction}. Therefore this class can represent much bigger 
 * numbers, but also much smaller numbers.
 * @author Alonso del Arte
 */
public class BigFraction implements Comparable<BigFraction> {
    
    private static final BigInteger MIN_LONG 
            = BigInteger.valueOf(Long.MIN_VALUE);
    
    private static final BigInteger MAX_LONG 
            = BigInteger.valueOf(Long.MAX_VALUE);
    
    private final BigInteger numerator, denominator;
    
    /**
     * Gets the numerator of this fraction. It may or may not match the 
     * numerator the constructor was given.
     * @return The numerator of the fraction in lowest terms, regardless of how 
     * the fraction was constructed. For example, if the fraction was 
     * constructed as <sup>1</sup>&frasl;<sub>&minus;9223372036854775808</sub>, 
     * the fraction will be expressed as 
     * <sup>&minus;1</sup>&frasl;<sub>9223372036854775808</sub>, and this 
     * function will return &minus;1, not 1.
     */
    public BigInteger getNumerator() {
        return this.numerator;
    }
    
    /**
     * Gets the denominator of this fraction. It may or may not match the 
     * denominator the constructor was given.
     * @return The denominator of the fraction in lowest terms, regardless of 
     * how the fraction was constructed. This will always be a positive integer. 
     * For example, if the fraction was constructed as 
     * <sup>1</sup>&frasl;<sub>&minus;9223372036854775808</sub>, the fraction 
     * will be expressed as 
     * <sup>&minus;1</sup>&frasl;<sub>9223372036854775808</sub>, and this 
     * function will return 9223372036854775808, not &minus;9223372036854775808.
     */
    public BigInteger getDenominator() {
        return this.denominator;
    }
    
    /**
     * Gives a textual representation of this fraction in lowest terms. Uses 
     * ASCII characters only.
     * @return The numerator followed by "/" and then the denominator, except in 
     * the case of integers, for which the "/1" is omitted. For example, if this 
     * fraction was constructed as <sup>9</sup>&frasl;<sub>6144</sub>, this 
     * function will return "3/2048". If this fraction is the integer 7, this 
     * function will simply return "7".
     */
    @Override
    public String toString() {
        String numerStr = this.numerator.toString();
        if (this.denominator.equals(BigInteger.ONE)) {
            return numerStr;
        } else {
            return numerStr + "/" + this.denominator.toString();
        }
    }
    
    /**
     * Gives a textual representation of this fraction in lowest terms, suitable 
     * for use in an HTML document. Uses HTML character entities when 
     * applicable.
     * @return The numerator as a superscript, followed by the fraction slash 
     * character entity, and then the denominator as a subscript, except in the 
     * case of integers, for which the output is the same as {@link 
     * #toString()}. For example, if this fraction was constructed as 
     * <sup>9</sup>&frasl;<sub>6144</sub>, this function will return 
     * "<sup>3</sup>&frasl;<sub>2048</sub>". If this fraction is the integer 7, 
     * this function will simply return "7".
     */
    public String toHTMLString() {
        if (this.denominator.equals(BigInteger.ONE)) {
            return this.numerator.toString();
        } else {
            String str = "<sup>" + this.numerator.toString() 
                    + "</sup>&frasl;<sub>" + this.denominator.toString() 
                    + "</sub>";
            return str.replace("-", "&minus;");
        }
    }
    
    /**
     * Gives a textual representation of this fraction in lowest terms, suitable 
     * for use in a TeX document.
     * @return "\frac{" followed by the numerator, then "}{", the denominator 
     * and "}" to close. Except in the case of integers, for which the output is 
     * the same as {@link #toString()}. For example, if this fraction was 
     * constructed as <sup>9</sup>&frasl;<sub>6144</sub>, this function will 
     * return "\frac{3}{2048}". If this fraction is the integer 7, this function 
     * will simply return "7".
     */
    public String toTeXString() {
        if (this.denominator.equals(BigInteger.ONE)) {
            return this.numerator.toString();
        } else {
            String str = "\\frac{" + this.numerator.toString() + "}{" 
                    + this.denominator.toString() + "}";
            return str.replace("\\frac\u007B-", "-\\frac\u007B");
        }
    }
    
    /**
     * Determines whether an object is equal to this {@code BigFraction} 
     * object. A {@link fractions.Fraction Fraction} object will not be 
     * considered equal even if it represents the exact same rational number. 
     * For the examples, suppose this {@code BigFraction} object represents 
     * <sup>7</sup>&frasl;<sub>8</sub>.
     * @param obj The object to compare for equality. Examples: a 
     * {@code BigFraction} object constructed with numerator &minus;14 and 
     * denominator &minus;16; a {@code Fraction} object representing 
     * <sup>7</sup>&frasl;<sub>8</sub>; a {@code BigFraction} object 
     * representing <sup>1</sup>&frasl;<sub>12157665459056928801</sub>; an 
     * {@code SQLException}; and a null pointer.
     * @return True if {@code obj} is a {@code BigFraction} object with the same 
     * numerator and denominator (regardless of the original constructor 
     * parameters), false in any other case. In the examples, this would return 
     * true only for the {@code BigFraction} object constructed with numerator 
     * &minus;14 and denominator &minus;16. It would return false for the {@code 
     * Fraction} object representing <sup>7</sup>&frasl;<sub>8</sub> even though 
     * it represents the same number. And of course false for the {@code 
     * SQLException} and the null pointer.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        final BigFraction other = (BigFraction) obj;
        if (!this.numerator.equals(other.numerator)) {
            return false;
        }
        return this.denominator.equals(other.denominator);
    }
    
    /**
     * Gives a 32-bit integer hash code for this {@code BigFraction} 
     * object, based on the numerator and denominator. Not guaranteed to be 
     * unique, but hopefully unique enough.
     * @return A hash code. For example, if this {@code BigFraction} is 
     * &minus;<sup>5</sup>&frasl;<sub>18446744073709551614</sub>, its hash code 
     * might be &minus;327713. In the case of a {@code BigFraction} 
     * constructed from a {@link Fraction} instance, the hash codes of the two 
     * objects will probably match, but this is not guaranteed and you should 
     * not rely on that.
     */
    @Override
    public int hashCode() {
        return (this.numerator.hashCode() << 16) + this.denominator.hashCode();
    }
    
    /**
     * Compares this {@code BigFraction} instance to another 
     * {@code BigFraction} instance. For the examples, suppose this 
     * {@code BigFraction} instance is <sup>7</sup>&frasl;<sub>8</sub>.
     * @param other The {@code BigFraction} to compare this 
     * {@code BigFraction} instance to. Three examples: 
     * &minus;<sup>3</sup>&frasl;<sub>2</sub>, <sup>7</sup>&frasl;<sub>8</sub>, 
     * <sup>9223372036854775809</sup>&frasl;<sub>9223372036854775808</sub>.
     * @return A negative integer, generally &minus;1, if this instance is less 
     * than {@code other}; 0 if it is equal to {@code other}; or a positive 
     * integer, generally 1, if it is greater than {@code other}. Given the 
     * example of this instance being <sup>7</sup>&frasl;<sub>8</sub> and {@code 
     * other} being &minus;<sup>3</sup>&frasl;<sub>2</sub>, 
     * <sup>7</sup>&frasl;<sub>8</sub> or 
     * <sup>9223372036854775809</sup>&frasl;<sub>9223372036854775808</sub>, this 
     * function would return 1, 0 and &minus;1 respectively.
     */
    @Override
    public int compareTo(BigFraction other) {
        BigFraction diff = this.minus(other);
        return diff.numerator.signum();
    }
    
    /**
     * Gives a numeric approximation of this fraction. The level of precision 
     * depends mostly on the denominator.
     * @return An exact numeric representation in decimal if this fraction can 
     * be represented precisely with a reasonable number of finite digits, or, 
     * if not, a numeric approximation rounded or truncated arbitrarily (I am 
     * not committing to any particular rounding mode). For example, given 
     * <sup>27021597764222975</sup>&frasl;<sub>18014398509481984</sub> (slightly 
     * less than <sup>3</sup>&frasl;<sub>2</sub>, this function would return 
     * 1.499999999999999944488848768742172978818416595458984375. But for 
     * <sup>1</sup>&frasl;<sub>7</sub>, this function would return something 
     * like 0.14285714285714285714285714285714285714285714285714285714285714. 
     * Depending on your use case, you might prefer to use {@link 
     * #getNumerator()} and {@link #getDenominator()} to obtain {@code 
     * BigInteger} instances, convert those to {@code BigDecimal} and then 
     * perform the division with the desired scale and rounding mode.
     */
    public BigDecimal getNumericApproximation() {
        BigDecimal numer = new BigDecimal(this.numerator);
        BigDecimal denom = new BigDecimal(this.denominator);
        BigDecimal approx;
        try {
            approx = numer.divide(denom);
        } catch (ArithmeticException ae) {
            assert ae.getMessage() != null;
            approx = numer.divide(denom, 128, RoundingMode.HALF_EVEN);
        }
        return approx;
    }
    
    /**
     * Gives a numeric approximation of this fraction as a 64-bit IEEE 754 
     * floating point number. This is subject to all the caveats for IEEE 754 
     * floating point numbers.
     * @return A numeric approximation of this fraction. For example, for 
     * <sup>1</sup>&frasl;<sub>7</sub>, this would be 0.14285714285714285; for 
     * <sup>27021597764222975</sup>&frasl;<sub>18014398509481984</sub> (slightly 
     * less than <sup>3</sup>&frasl;<sub>2</sub>), this function would return 
     * exactly 1.5, even though the difference between 
     * <sup>3</sup>&frasl;<sub>2</sub> and 
     * <sup>27021597764222975</sup>&frasl;<sub>18014398509481984</sub> can be 
     * approximated in 64-bit floating point as 5.551115123125783 &times; 
     * 10<sup>&minus;17</sup>. Nonzero numbers that are very close to 0, such as 
     * the reciprocal of 2<sup>1075</sup> (roughly 2.47 &times; 
     * 10<sup>&minus;324</sup>) will be reported as exactly &plusmn;0.0 
     * (according to the sign of this fraction). However, if this fraction is 
     * exactly 0, this function will always return +0.0, never &minus;0.0. Thus 
     * &minus;0.0 indicates a loss of precision for a negative number. Most 
     * numbers with absolute value greater than {@code Double.MAX_VALUE} will be  
     * lost as positive or negative infinity depending on sign.
     */
    public double getDouble() {
        return this.getNumericApproximation().doubleValue();
    }
    
    /**
     * Adds a fraction to this one.
     * @param addend The fraction to add. For example, 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>.
     * @return The sum of this fraction and {@code addend}. For example, if this 
     * fraction is <sup>3</sup>&frasl;<sub>2</sub> and {@code addend} is 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, the result would be 
     * <sup>13835058055282163713</sup>&frasl;<sub>9223372036854775808</sub>.
     */
    public BigFraction plus(BigFraction addend) {
        BigInteger interNumerA = this.numerator.multiply(addend.denominator);
        BigInteger interNumerB = addend.numerator.multiply(this.denominator);
        BigInteger numer = interNumerA.add(interNumerB);
        BigInteger denom = this.denominator.multiply(addend.denominator);
        return new BigFraction(numer, denom);
    }
    
    /**
     * Adds the value of a {@code Fraction} instance to the value of this 
     * {@code BigFraction} instance.
     * @param addend The fraction to add.
     * @return The sum of this fraction and {@code addend}.
     */
    public BigFraction plus(Fraction addend) {
        BigFraction wrap = new BigFraction(addend);
        return this.plus(wrap);
    }
    
    /**
     * Adds an integer to this fraction.
     * @param addend The integer to add. For example, 1.
     * @return The sum. For example, if this fraction is 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, adding 1 gives 
     * <sup>9223372036854775809</sup>&frasl;<sub>9223372036854775808</sub>.
     */
    public BigFraction plus(int addend) {
        BigInteger wrap = this.numerator.add(this.denominator
                .multiply(BigInteger.valueOf(addend)));
        return new BigFraction(wrap, this.denominator);
    }
    
    /**
     * Gives the additive inverse of this fraction. Only 0 is its own additive 
     * inverse.
     * @return This fraction multiplied by &minus;1. For example, if this 
     * fraction is <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, this 
     * function will return 
     * &minus;<sup>1</sup>&frasl;<sub>9223372036854775808</sub>. This function 
     * will always return a new instance, even in the case of 0.
     */
    public BigFraction negate() {
        return new BigFraction(this.numerator.negate(), this.denominator);
    }
    
    /**
     * Subtracts a fraction from this one.
     * @param subtrahend The fraction to subtract. For example, 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>.
     * @return This fraction minus {@code subtrahend}. For example, if this 
     * fraction is <sup>3</sup>&frasl;<sub>2</sub> and {@code subtrahend} is 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, the result would be 
     * <sup>13835058055282163711</sup>&frasl;<sub>9223372036854775808</sub>.
     */
    public BigFraction minus(BigFraction subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    /**
     * Subtracts the value of a {@code Fraction} instance from the value of 
     * this {@code BigFraction} instance.
     * @param subtrahend The {@code Fraction} instance to subtract.
     * @return This fraction minus {@code subtrahend}.
     */
    public BigFraction minus(Fraction subtrahend) {
        BigFraction addend = new BigFraction(subtrahend.negate());
        return this.plus(addend);
    }
    
    /**
     * Subtracts an integer from this fraction.
     * @param subtrahend The integer to subtract. For example, 1.
     * @return The result of the subtraction. For example, if this fraction is 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, subtracting 1 gives 
     * <sup>&minus;9223372036854775807</sup>&frasl;<sub>9223372036854775808</sub>.
     */
    public BigFraction minus(int subtrahend) {
        return this.plus(-subtrahend);
    }
    
    /**
     * Multiplies this fraction by another fraction.
     * @param multiplicand The fraction to multiply by. For example, 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>.
     * @return The product of this fraction and  {@code multiplicand}. For 
     * example, if this fraction is <sup>3</sup>&frasl;<sub>2</sub> and {@code 
     * multiplicand} is <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, the 
     * result would be <sup>3</sup>&frasl;<sub>18446744073709551616</sub>.
     */
    public BigFraction times(BigFraction multiplicand) {
        BigInteger numer = this.numerator.multiply(multiplicand.numerator);
        BigInteger denom = this.denominator.multiply(multiplicand.denominator);
        return new BigFraction(numer, denom);
    }
    
    /**
     * Multiplies the value of a {@code Fraction} instance by the value of 
     * this {@code BigFraction} instance.
     * @param multiplicand The {@code Fraction} instance to multiply by.
     * @return The product of this fraction and  {@code multiplicand}.
     */
    public BigFraction times(Fraction multiplicand) {
        BigFraction wrap = new BigFraction(multiplicand);
        return this.times(wrap);
    }
    
    /**
     * Multiplies this fraction by an integer.
     * @param multiplicand The integer to multiply by. For example, 32768.
     * @return The product. For example, if this fraction is 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, multiplying by 32768 
     * gives <sup>1</sup>&frasl;<sub>281474976710656</sub>.
     */
    public BigFraction times(int multiplicand) {
        BigInteger wrap 
                = this.numerator.multiply(BigInteger.valueOf(multiplicand));
        return new BigFraction(wrap, this.denominator);
    }
    
    /**
     * Gives the reciprocal of this fraction. The reciprocal of 
     * <sup><i>a</i></sup>&frasl;<sub><i>b</i></sub> is 
     * <sup><i>b</i></sup>&frasl;<sub><i>a</i></sub>. Except for 0, all rational 
     * numbers have reciprocals. Except for &minus;1 and 1, no rational number 
     * is equal to its own reciprocal.
     * @return The reciprocal of this fraction. For example, if this fraction is 
     * <sup>2147483649</sup>&frasl;<sub>9223372036854775808</sub>, this function 
     * returns <sup>9223372036854775808</sup>&frasl;<sub>2147483649</sub>.
     * @throws IllegalArgumentException If this fraction is 0.
     */
    public BigFraction reciprocal() {
        return new BigFraction(this.denominator, this.numerator);
    }

    /**
     * Divides this fraction by another.
     * @param divisor The fraction to divide by. For example, 
     * <sup>3</sup>&frasl;<sub>2</sub>.
     * @return The division of this fraction by {@code divisor}. For example, if 
     * this fraction is <sup>1</sup>&frasl;<sub>9223372036854775808</sub> and 
     * {@code divisor} is <sup>3</sup>&frasl;<sub>2</sub>, the result would be 
     * <sup>1</sup>&frasl;<sub>13835058055282163712</sub>.
     * @throws IllegalArgumentException If {@code divisor} is 0.
     */
    public BigFraction dividedBy(BigFraction divisor) {
        return this.times(divisor.reciprocal());
    }
    
    /**
     * Divides the value of this {@code BigFraction} instance by the value 
     * of a {@code Fraction} instance.
     * @param divisor The {@code Fraction} instance to divide by.
     * @return The division of this fraction by {@code divisor}.
     * @throws IllegalArgumentException If {@code divisor} is 0.
     */
    public BigFraction dividedBy(Fraction divisor) {
        BigFraction multiplicand = new BigFraction(divisor.reciprocal());
        return this.times(multiplicand);
    }
    
    /**
     * Divides this fraction by an integer.
     * @param divisor The integer to divide by. For example, 32768.
     * @return The division. For example, if this fraction is 
     * <sup>1</sup>&frasl;<sub>9223372036854775808</sub>, dividing by 32768 
     * gives <sup>1</sup>&frasl;<sub>302231454903657293676544</sub>.
     * @throws IllegalArgumentException If {@code divisor} is 0.
     */
    public BigFraction dividedBy(int divisor) {
        BigInteger wrap = this.denominator.multiply(BigInteger
                .valueOf(divisor));
        return new BigFraction(this.numerator, wrap);
    }
    
    /**
     * Indicates whether or not this {@code BigFraction} instance can be 
     * downsampled to a {@link Fraction} instance, which uses 64-bit integers 
     * for the numerator and denominator. If so, the {@link #downsample()} 
     * function can give a {@code Fraction} instance that is arithmetically 
     * equal to this {@code BigFraction} instance.
     * @return True if both the numerator and denominator can be correctly 
     * converted to 64-bit integers. Examples: true for 
     * <sup>3</sup>&frasl;<sub>262144</sub>, false for 
     * &minus;<sup>1570042899082081611640534563</sup>&frasl;<sub>262144</sub> 
     * (numerator is too low), false for 
     * <sup>4710128697246244834921603689</sup>&frasl;<sub>262144</sub> 
     * (numerator is too high) and false for 
     * <sup>3</sup>&frasl;<sub>36893488147419103232</sub> (denominator is too 
     * high).
     */
    public boolean canDownsample() {
        return this.numerator.bitLength() < 64 
                && this.denominator.bitLength() < 64;
    }

    /**
     * Converts this {@code BigFraction} instance to a {@code Fraction} 
     * instance. See also {@link #BigFraction(fractions.Fraction) the auxiliary 
     * constructor}.
     * @return The downsampled fraction.
     * @throws ArithmeticException If either this fraction's numerator or 
     * denominator is outside the range of 64-bit integers.
     */
    public Fraction downsample() {
        if (this.numerator.compareTo(MIN_LONG) < 0 
                || this.numerator.compareTo(MAX_LONG) > 0 
                || this.denominator.compareTo(MAX_LONG) > 0) {
            String excMsg = "Numerator and/or denominator of " + this.toString() 
                    + " is outside the range of 64-bit integers";
            throw new ArithmeticException(excMsg);
        }
        return new Fraction(this.numerator.longValue(), 
                this.denominator.longValue());
    }

    public static BigFraction parseFract(String s) {
        int slashIndex = s.indexOf('/');
        try {
            String numerStr = s.substring(0, slashIndex);
            String denomStr = s.substring(slashIndex + 1);
            BigInteger numer = new BigInteger(numerStr);
            BigInteger denom = new BigInteger(denomStr);
            return new BigFraction(numer, denom);
        } catch (RuntimeException re) {
            return new BigFraction(BigInteger.ZERO, BigInteger.ONE);
        }
    }
    
    /**
     * Auxiliary constructor. Essentially a copy constructor that performs a 
     * widening conversion. See also {@link #downsample()}.
     * @param fraction The {@code Fraction} instance to base this {@code 
     * BigFraction} instance on.
     */
    public BigFraction(Fraction fraction) {
        this(BigInteger.valueOf(fraction.getNumerator()), 
                BigInteger.valueOf(fraction.getDenominator()));
    }
    
    /**
     * Primary constructor. It makes sure the fraction is in lowest terms (even 
     * if the parameters are not) and the denominator is positive (changing the 
     * sign of the numerator if necessary).
     * @param numer The numerator of the fraction.
     * @param denom The denominator of the fraction. It must not be 0, but it 
     * may be negative.
     * @throws IllegalArgumentException If the denominator is 0.
     */
    public BigFraction(BigInteger numer, BigInteger denom) {
        if (denom.equals(BigInteger.ZERO)) {
            String excMsg = "Denominator zero is not allowed";
            throw new IllegalArgumentException(excMsg);
        }
        BigInteger sign = BigInteger.valueOf(denom.signum());
        BigInteger adjustment = numer.gcd(denom).multiply(sign);
        this.numerator = numer.divide(adjustment);
        this.denominator = denom.divide(adjustment);
    }
    
}
