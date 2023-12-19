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
package fractions;

import static calculators.NumberTheoreticFunctionsCalculator.euclideanGCD;
import static calculators.NumberTheoreticFunctionsCalculator.mod;

import java.io.Serializable;

/**
 * Defines objects to represent fractions symbolically rather than numerically. 
 * For example, to represent one half as <sup>1</sup>&frasl;<sub>2</sub> rather 
 * than 0.5. Note that there is hardly any overflow checking. Therefore, it's 
 * best to keep numerators and denominators within the range of <code>int</code> 
 * even though those fields are of type <code>long</code>. If you're definitely 
 * going to need numerators and denominators outside the range of 
 * <code>int</code>, you should use {@link BigFraction} instead of this class.
 * @author Alonso del Arte
 */
public class Fraction implements Comparable<Fraction>, Serializable {
    
    private static final long serialVersionUID = 4547844323164568372L;
    
    private final long numerator;
    private final long denominator;
    
    /**
     * Specifies an interval by which to separate the numerator and the 
     * denominator in a hash code.
     */
    protected static final int HASH_SEP = 65536;
    
    /**
     * Gives the numerator of the fraction. It may or may not match the 
     * numerator the constructor was given.
     * @return The numerator of the fraction in lowest terms, regardless of how 
     * the fraction was constructed. For example, if the fraction was 
     * constructed as <sup>2</sup>&frasl;<sub>&minus;4</sub>, the fraction will 
     * be expressed as <sup>&minus;1</sup>&frasl;<sub>2</sub> and this function 
     * will return &minus;1, not 2.
     */
    public long getNumerator() {
        return this.numerator;
    }
    
    /**
     * Gives the denominator of the fraction. It may or may not match the 
     * denominator the constructor was given.
     * @return The denominator of the fraction in lowest terms and as a positive 
     * number, regardless of how the fraction was constructed. For example, if 
     * the fraction was constructed as <sup>2</sup>&frasl;<sub>&minus;4</sub>, 
     * the fraction will be expressed as <sup>&minus;1</sup>&frasl;<sub>2</sub> 
     * and this function will return 2, not &minus;4.
     */
    public long getDenominator() {
        return this.denominator;
    }
    
    /**
     * Adds a fraction to this fraction. There is no overflow checking, but this 
     * shouldn't be a problem as long as all numerators and denominators are 
     * well within the range of <code>int</code>.
     * @param addend The fraction to add. For example, 
     * <sup>1</sup>&frasl;<sub>7</sub>.
     * @return A new <code>Fraction</code> object with the sum. For example, if 
     * this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the addend is 
     * <sup>1</sup>&frasl;<sub>7</sub>, the result will be 
     * <sup>9</sup>&frasl;<sub>14</sub>.
     */
    public Fraction plus(Fraction addend) {
        long interNumerA = this.numerator * addend.denominator;
        long interNumerB = addend.numerator * this.denominator;
        long resNumer = interNumerA + interNumerB;
        long resDenom = this.denominator * addend.denominator;
        return new Fraction(resNumer, resDenom);
    }
    
    /**
     * Adds an integer to this fraction.
     * @param addend The integer to add. For example, 3.
     * @return A new <code>Fraction</code> object with the sum. For example, if 
     * this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the integer addend 
     * is 3, the result will be <sup>7</sup>&frasl;<sub>2</sub>.
     */
    public Fraction plus(int addend) {
        long resNumer = this.numerator + addend * this.denominator;
        return new Fraction(resNumer, this.denominator);
    }
    
    /**
     * Subtracts a fraction from this fraction. There is no overflow checking, 
     * but this shouldn't be a problem as long as all numerators and 
     * denominators are well within the range of <code>int</code>.
     * @param subtrahend The fraction to subtract. For example, 
     * <sup>1</sup>&frasl;<sub>7</sub>.
     * @return A new <code>Fraction</code> object with the subtraction. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the 
     * subtrahend is <sup>1</sup>&frasl;<sub>7</sub>, the result will be 
     * <sup>5</sup>&frasl;<sub>14</sub>.
     */
    public Fraction minus(Fraction subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    /**
     * Subtracts an integer from this fraction.
     * @param subtrahend The integer to subtract. For example, 3.
     * @return A new <code>Fraction</code> object with the subtraction. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the 
     * integer subtrahend is 3, the result will be 
     * &minus;<sup>5</sup>&frasl;<sub>2</sub>.
     */
    public Fraction minus(int subtrahend) {
        return this.plus(-subtrahend);
    }
    
    /**
     * Multiplies this fraction by another fraction. There is no overflow 
     * checking, but this shouldn't be a problem as long as all numerators and 
     * denominators are well within the range of <code>short</code>.
     * @param multiplicand The fraction to multiply by. For example, 
     * <sup>1</sup>&frasl;<sub>7</sub>.
     * @return A new <code>Fraction</code> object with the product. For example, 
     * if this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the multiplicand 
     * is <sup>1</sup>&frasl;<sub>7</sub>, the result will be 
     * <sup>1</sup>&frasl;<sub>14</sub>.
     */
    public Fraction times(Fraction multiplicand) {
        long resNumer = this.numerator * multiplicand.numerator;
        long resDenom = this.denominator * multiplicand.denominator;
        return new Fraction(resNumer, resDenom);
    }
    
    /**
     * Multiplies this fraction by an integer.
     * @param multiplicand The integer to multiply by. For example, 3.
     * @return A new <code>Fraction</code> object with the product. For example, 
     * if this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the multiplicand 
     * is 3, the result will be <sup>3</sup>&frasl;<sub>2</sub>.
     */
    public Fraction times(int multiplicand) {
        return new Fraction(this.numerator * multiplicand, this.denominator);
    }
    
    /**
     * Divides this fraction by another fraction.  There is no overflow 
     * checking, but this shouldn't be a problem as long as all numerators and 
     * denominators are well within the range of <code>short</code>.
     * @param divisor The fraction to divide by. For example, 
     * <sup>1</sup>&frasl;<sub>7</sub>.
     * @return A new <code>Fraction</code> object with the division. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the 
     * divisor is <sup>1</sup>&frasl;<sub>7</sub>, the result will be 
     * <sup>7</sup>&frasl;<sub>2</sub>.
     * @throws ArithmeticException If <code>divisor</code> is 0, this runtime 
     * exception might be thrown.
     * @throws IllegalArgumentException If <code>divisor</code> is 0, this 
     * runtime exception might be thrown.
     */
    public Fraction dividedBy(Fraction divisor) {
        return this.times(divisor.reciprocal());
    }
    
    /**
     * Divides this fraction by an integer.
     * @param divisor The integer to divide by. For example, 3.
     * @return A new <code>Fraction</code> object with the division. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>2</sub> and the 
     * divisor is 3, the result will be <sup>1</sup>&frasl;<sub>6</sub>.
     * @throws ArithmeticException If <code>divisor</code> is 0, this runtime 
     * exception might be thrown.
     * @throws IllegalArgumentException If <code>divisor</code> is 0, this 
     * runtime exception might be thrown.
     */
    public Fraction dividedBy(int divisor) {
        return new Fraction(this.numerator, this.denominator * divisor);
    }
    
    /**
     * Multiplies this fraction by &minus;1.
     * @return A new <code>Fraction</code> object with the negation. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>2</sub>, the result 
     * will be &minus;<sup>1</sup>&frasl;<sub>2</sub>.
     */
    public Fraction negate() {
        return new Fraction(-this.numerator, this.denominator);
    }
    
    /**
     * Gives the reciprocal of this fraction. If this fraction is a unit 
     * fraction, the reciprocal will be arithmetically equal to an integer.
     * @return A new <code>Fraction</code> object with the reciprocal. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>2</sub>, the result 
     * will be 2; if this fraction is <sup>7</sup>&frasl;<sub>2</sub>, the 
     * result will be <sup>2</sup>&frasl;<sub>7</sub>.
     * @throws ArithmeticException If this fraction is 0, this runtime exception 
     * might be thrown with the exception detail message "Denominator 0 is 
     * invalid or unavailable."
     * @throws IllegalArgumentException If this fraction is 0, this runtime 
     * exception might be thrown with the exception detail message "Denominator 
     * 0 is invalid or unavailable."
     */
    public Fraction reciprocal() {
        return new Fraction(this.denominator, this.numerator);
    }

    /**
     * Rounds this fraction down to an integer. Note that negative numbers round 
     * down to the ceiling (not the floor) of the absolute value multiplied by 
     * &minus;1.
     * @return This fraction rounded down to an integer. For example, if this 
     * fraction is <sup>22</sup>&frasl;<sub>7</sub>, the result is 3. If this 
     * fraction is &minus;<sup>1</sup>&frasl;<sub>2</sub>, the result is 
     * &minus;1, not 0. If this fraction is already an integer, the result of 
     * this function may or may not be a fresh new instance; that is an 
     * implementation detail that may change without notice.
     */
    public Fraction roundDown() {
        long remainder = mod(this.numerator, this.denominator);
        long numer = this.numerator - remainder;
        return new Fraction(numer, this.denominator);
    }

    /**
     * Rounds a fraction down to an integer multiple of a given interval. No 
     * overflow checking is provided. Therefore it's best to limit denominators 
     * to the range of <code>int</code>.
     * @param interval The interval to round down by. Preferably a unit 
     * fraction, but this is not required. For example, 
     * <sup>1</sup>&frasl;<sub>10</sub>.
     * @return The fraction rounded down. For example, 
     * <sup>73</sup>&frasl;<sub>100</sub> rounded down to the nearest tenth 
     * would be <sup>7</sup>&frasl;<sub>10</sub>.
     * @throws ArithmeticException If <code>interval</code> is 0, depending on 
     * the implementation.
     * @throws IllegalArgumentException If <code>interval</code> is 0, depending 
     * on the implementation.
     */
    public Fraction roundDown(Fraction interval) {
        Fraction multiplicand = this.dividedBy(interval).roundDown();
        return interval.times(multiplicand);
    }
    
    /**
     * Rounds this fraction up to an integer. Note that negative numbers round 
     * up to the floor (not the ceiling) of the absolute value multiplied by 
     * &minus;1.
     * @return This fraction rounded down to an integer. For example, if this 
     * fraction is <sup>22</sup>&frasl;<sub>7</sub>, the result is 4. If this 
     * fraction is &minus;<sup>1</sup>&frasl;<sub>2</sub>, the result is 0, not 
     * &minus;1. If this fraction is already an integer, the result of this 
     * function may or may not be a fresh new instance; that is an 
     * implementation detail that may change without notice.
     */
    public Fraction roundUp() {
        if (this.denominator == 1L) {
            return this;
        } else {
            long remainder = mod(this.numerator, this.denominator);
            long numer = this.numerator - remainder + this.denominator;
            return new Fraction(numer, this.denominator);
        }
    }

    /**
     * Rounds a fraction up to an integer multiple of a given interval. No 
     * overflow checking is provided. Therefore it's best to limit denominators 
     * to the range of <code>int</code>.
     * @param interval The interval to round up by. Preferably a unit fraction,  
     * but this is not required. For example, <sup>1</sup>&frasl;<sub>10</sub>.
     * @return The fraction rounded up. For example, 
     * <sup>73</sup>&frasl;<sub>100</sub> rounded up to the nearest tenth 
     * would be <sup>8</sup>&frasl;<sub>10</sub> = 
     * <sup>4</sup>&frasl;<sub>5</sub>.
     * @throws ArithmeticException If <code>interval</code> is 0, depending on 
     * the implementation.
     * @throws IllegalArgumentException If <code>interval</code> is 0, depending 
     * on the implementation.
     */
    public Fraction roundUp(Fraction interval) {
        Fraction division = this.dividedBy(interval);
        long roundedNumer 
                = (long) Math.ceil(division.getNumericApproximation());
        Fraction adjustedDivision = new Fraction(roundedNumer);
        return adjustedDivision.times(interval);
    }
    
    /**
     * Rounds this fraction down to a fraction with the specified numerator. 
     * This could be useful for rounding algebraic numbers to algebraic 
     * integers. For example, <sup>1</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;19</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;361</sup>&frasl;<sub>2</sub> is not an algebraic integer, but 
     * it can be rounded down to <sup>1</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;19</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;361</sup>&frasl;<sub>3</sub>, which is an algebraic integer.
     * @param denom The denominator for this fraction to conform to. For 
     * example, 32. Should not be 0. May be negative, but the behavior for 
     * negative parameters is subject to change in later versions of this 
     * program.
     * @return The conformed fraction. For example, if this fraction is 
     * <sup>5</sup>&frasl;<sub>8</sub>, conformed to denominator 32 the result 
     * would be <sup>19</sup>&frasl;<sub>32</sub>.
     * @throws ArithmeticException If <code>denom</code> is 0, depending on the 
     * implementation.
     * @throws IllegalArgumentException If <code>denom</code> is 0, depending on 
     * the implementation.
     */
    public Fraction conformDown(long denom) {
        if (this.denominator == denom) {
            return this;
        }
        Fraction unitFraction = new Fraction(1, denom);
        Fraction division = this.dividedBy(unitFraction);
        long propNumer = (long) Math.floor(division.getNumericApproximation());
        while (euclideanGCD(propNumer, denom) > 1) {
            propNumer--;
        }
        return new Fraction(propNumer, denom);
    }
    
    /**
     * Rounds this fraction up to a fraction with the specified numerator. This 
     * could be useful for rounding algebraic numbers to algebraic integers. For 
     * example, <sup>1</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;19</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;361</sup>&frasl;<sub>4</sub> is not an algebraic integer, but 
     * it can be rounded up to <sup>1</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;19</sup>&frasl;<sub>3</sub> + 
     * <sup>&#8731;361</sup>&frasl;<sub>3</sub>, which is an algebraic integer.
     * @param denom The denominator for this fraction to conform to. For 
     * example, 32. Should not be 0. May be negative, but the behavior for 
     * negative parameters is subject to change in later versions of this 
     * program.
     * @return The conformed fraction. For example, if this fraction is 
     * <sup>5</sup>&frasl;<sub>8</sub>, conformed to denominator 32 the result 
     * would be <sup>19</sup>&frasl;<sub>32</sub>.
     * @throws ArithmeticException If <code>denom</code> is 0, depending on the 
     * implementation.
     * @throws IllegalArgumentException If <code>denom</code> is 0, depending on 
     * the implementation.
     */
    public Fraction conformUp(long denom) {
        if (this.denominator == denom) {
            return this;
        }
        Fraction unitFraction = new Fraction(1, denom);
        Fraction division = this.dividedBy(unitFraction);
        long propNumer = (long) Math.ceil(division.getNumericApproximation());
        while (euclideanGCD(propNumer, denom) > 1) {
            propNumer++;
        }
        return new Fraction(propNumer, denom);
    }
    
    /**
     * Gives a text representation of this fraction, using ASCII characters 
     * only.
     * @return A <code>String</code> with the numerator, followed by the 
     * character "/" and then the denominator. However, if this fraction is an 
     * integer, the characters "/1" will be omitted. If this fraction is 
     * negative, the "-" character will be used instead of "&minus;" (the latter 
     * is not an ASCII character). For example, if this fraction is 
     * &minus;<sup>1</sup>&frasl;<sub>2</sub>, the result will be "-1/2". If 
     * this fraction is 3, the result will be just "3".
     */
    @Override
    public String toString() {
        if (this.denominator == 1) {
            return Long.toString(this.numerator);
        } else {
            return (this.numerator + "/" + this.denominator);
        }
    }
    
    /**
     * Gives a textual representation of this fraction suitable for use in an 
     * HTML document. The output placed in the context of an HTML document 
     * viewed in a Web browser should render with the numerator towards the top 
     * left and the denominator towards the bottom right of the space allotted 
     * for the text.
     * @return A <code>String</code> with the numerator set as an HTML 
     * superscript, followed by the fraction slash character entity and then the 
     * denominator set as an HTML subscript. However, if this fraction is an 
     * integer, the "&nbsp;&frasl;<sub>1</sub>" part will be omitted and the 
     * integer will be presented with no special formatting markup. If the 
     * fraction is negative, the <code>String</code> will start with "&minus;" 
     * rather than "-" for the minus sign, and the minus sign will not be a 
     * superscript regardless of whether or not the fraction is an integer. For 
     * example, if this fraction is <sup>&minus;1</sup>&frasl;<sub>2</sub>, the 
     * result will be "&minus;<sup>1</sup>&frasl;<sub>2</sub>".
     */
    public String toHTMLString() {
        if (this.denominator == 1) {
            return Long.toString(this.numerator).replace("-", "&minus;");
        } else {
            String str = "<sup>";
            if (this.numerator < 0) {
                str = "&minus;" + str + Math.abs(this.numerator);
            } else {
                str = str + this.numerator;
            }
            str = str + "</sup>&frasl;<sub>" + this.denominator + "</sub>";
            return str;
        }
    }
    
    /**
     * Gives a representation of this fraction suitable for use in a TeX 
     * document.
     * @return A <code>String</code> starting with "\frac{", followed by the 
     * numerator, then "}{", then the denominator and lastly "}". For example, 
     * if this fraction is &minus;<sup>1</sup>&frasl;<sub>2</sub>, the result 
     * will be "\frac{-1}{2}". However, if this fraction is an integer, the 
     * output will be the same as {@link #toString()}.
     */
    public String toTeXString() {
        if (this.denominator == 1) {
            return Long.toString(this.numerator);
        } else {
            String str = "\\frac{" + this.numerator + "}{" + this.denominator 
                    + "}";
            return str.replace("\\frac\u007B-", "-\\frac\u007B");
        }
    }
    
    /**
     * Gives a hash code for this fraction. This is guaranteed to be the same  
     * for two fractions that are arithmetically equal. It is likely but not 
     * guaranteed to be different for two fractions that are arithmetically 
     * unequal. Also, it is likely but not guaranteed that the sign of the hash 
     * code will match the sign of the fraction.
     * @return A 32-bit integer hash code. For example, if this fraction is 
     * &minus;<sup>9</sup>&frasl;<sub>14</sub>, the result might be 
     * &minus;589810.
     */
    @Override
    public int hashCode() {
        long numerHash = this.numerator % HASH_SEP;
        long denomHash = this.denominator % HASH_SEP;
        return (int) (numerHash * HASH_SEP + denomHash);
    }

    /**
     * Determines if this <code>Fraction</code> object is equal to another 
     * object.
     * @param obj The object to be tested for equality.
     * @return True if both objects are of the <code>Fraction</code> class and 
     * they represent the same arithmetical fraction (regardless of what 
     * numerators and denominators were used at the time of construction), false 
     * otherwise. For example, <sup>3</sup>&frasl;<sub>4</sub> and 
     * <sup>9</sup>&frasl;<sub>12</sub> should be found to be equal. 
     * <sup>3</sup>&frasl;<sub>4</sub> and <sup>3</sup>&frasl;<sub>5</sub> 
     * should not be found to be equal. A <code>Fraction</code> object should 
     * not be found to be equal to an <code>Integer</code> object even if their 
     * values are arithmetically equal, nor should it be found to be equal to 
     * null.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Fraction other = (Fraction) obj;
        if (this.numerator != other.numerator) {
            return false;
        }
        return (this.denominator == other.denominator);
    }

    /**
     * Compares this fraction to another fraction for order. Returns a negative 
     * integer, zero, or a positive integer as this fraction is less than, equal 
     * to, or greater than the other fraction. This enables sorting with  
     * <code>java.util.Collections.sort(java.util.List)</code> without need for 
     * a comparator.
     * @param other The fraction to compare to. Examples: 
     * &minus;<sup>1</sup>&frasl;<sub>2</sub>, <sup>5</sup>&frasl;<sub>3</sub>, 
     * <sup>22</sup>&frasl;<sub>7</sub>.
     * @return &minus;1 or any other negative integer if the compared fraction 
     * is greater than this fraction, 0 if they are equal, 1 or any other 
     * positive integer if the compared fraction is less than this fraction. For 
     * example, if this fraction is <sup>5</sup>&frasl;<sub>3</sub>, compared to 
     * <sup>22</sup>&frasl;<sub>7</sub> the result would most 
     * likely be &minus;1; compared to <sup>5</sup>&frasl;<sub>3</sub> the 
     * result would certainly be 0, and compared to 
     * &minus;<sup>1</sup>&frasl;<sub>2</sub> the result would most likely be 1.
     */
    @Override
    public int compareTo(Fraction other) {
        Fraction diff = this.minus(other);
        if (diff.numerator < 0) {
            return -1;
        }
        if (diff.numerator > 0) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Gives a numeric approximation of the value of this fraction. This is 
     * likely to be precise if the denominator is a small power of 2.
     * @return A floating point approximation of the value of this fraction. For 
     * example, if this fraction is <sup>1</sup>&frasl;<sub>7</sub>, the result 
     * should be 0.14285714285714285. If this fraction is 
     * <sup>6</sup>&frasl;<sub>13</sub>, the result should be 
     * 0.46153846153846156. In no case should the floating point value of a 
     * fraction be lost as infinity or not a number (NaN), nor should numbers 
     * very close to 0 be lost as &plusmn;0.0.
     */
    public double getNumericApproximation() {
        return (double) this.numerator / this.denominator;
    }
    
    /**
     * Determines whether this fraction is a unit fraction. A unit fraction is a 
     * number of the form <sup>1</sup>&frasl;<sub><i>n</i></sub>, where <i>n</i> 
     * is a positive integer. The reciprocal of a unit fraction is an integer.
     * @return True if the numerator is 1, false otherwise. For example, true 
     * for <sup>1</sup>&frasl;<sub>512</sub>, false for 
     * &minus;<sup>1</sup>&frasl;<sub>512</sub> and 512.
     */
    public boolean isUnitFraction() {
        return this.numerator == 1L;
    }
    
    /**
     * Determines whether this fraction is an integer. An integer is a rational 
     * number of the form <sup><i>n</i></sup>&frasl;<sub>1</sub>, where <i>n</i> 
     * is an integer. The reciprocal of a positive integer is a unit fraction.
     * @return True if the denominator is 1, false otherwise. For example, true 
     * for 512, false for <sup>1</sup>&frasl;<sub>512</sub> and 
     * &minus;<sup>1</sup>&frasl;<sub>512</sub>.
     */
    public boolean isInteger() {
        return this.denominator == 1L;
    }
    
    private static String parseFractHTML(String s) {
        s = s.replace("&minus;", "-");
        s = s.replace("&#x2212;", "-");
        s = s.replace("&#8722;", "-");
        int slashIndex = s.indexOf("&frasl;");
        String numerStr = s.substring(0, slashIndex);
        numerStr = numerStr.replace("<sup>", "");
        numerStr = numerStr.replace("</sup>", "");
        String denomStr = s.substring(slashIndex + 7);
        denomStr = denomStr.replace("<sub>", "");
        denomStr = denomStr.replace("</sub>", "");
        return numerStr + "/" + denomStr;
    }
    
    private static String parseFractTeX(String s) {
        s = s.replace("\\frac{", "");
        s = s.replace("}{", "/");
        s = s.replace("}", "");
        return s;
    }
    
    /**
     * Parses a <code>String</code> into a <code>Fraction</code> object.
     * @param s The <code>String</code> to parse. It may contain spaces. For 
     * example, "22 / 7". Currently this function can parse HTML or TeX if it 
     * conforms to certain expectations: namely, a fraction from an HTML 
     * document includes the fraction slash character entity "&amp;frasl;", and 
     * a fraction from a TeX document uses the "\frac{numerator}{denominator}" 
     * syntax.
     * @return A <code>Fraction</code> object. For example, <code>new 
     * Fraction(22, 7)</code>.
     * @throws NumberFormatException If <code>s</code> contains characters other 
     * than digits, spaces, "-" (may occur once or twice, but only preceding 
     * digits) or "/" (may only occur once or not at all). The exception message 
     * will most likely contain only what this function understood to be the 
     * intended as the denominator.
     */
    public static Fraction parseFract(String s) {
        s = s.replace(" ", "");
        s = s.replace("\u2212", "-");
        if (s.contains("&frasl;")) {
            s = parseFractHTML(s);
        }
        if (s.contains("\\frac")) {
            s = parseFractTeX(s);
        }
        int slashIndex = s.indexOf('/');
        if (slashIndex == -1) {
            long numer = Long.parseLong(s);
            return new Fraction(numer);
        }
        long numer = Long.parseLong(s.substring(0, slashIndex));
        long denom = Long.parseLong(s.substring(slashIndex + 1));
        return new Fraction(numer, denom);
    }
    
    /**
     * Implicit denominator constructor for integers. Denominator is filled in 
     * as 1. Note that this is <em>not</em> a chained constructor. I figure it 
     * will be much more useful to someone using this class on a REPL (like the 
     * Scala REPL) than to other classes or to interfaces.
     * @param numer The numerator of the fraction. For example, 7. Then the 
     * fraction <sup>7</sup>&frasl;<sub>1</sub> is arithmetically equal to the 
     * integer 7.
     */
    public Fraction(long numer) {
        this.numerator = numer;
        this.denominator = 1;
    }
    
    /**
     * The default constructor. It makes sure the fraction is expressed in 
     * lowest terms and that the denominator is positive.
     * @param numer The numerator of the fraction. For example, 2. Preferably in 
     * the range of <code>int</code>, to avoid overflows in operations with 
     * other fractions.
     * @param denom The denominator of the fraction. It must not be 0 nor 
     * <code>Long.MIN_VALUE</code>. Other than that, there are no requirements 
     * for the denominator, though I do recommend that it be in the range of 
     * <code>int</code>, to avoid overflows in operations with other fractions. 
     * It need not be coprime to the numerator, and it may be negative. The 
     * constructor will make sure the fraction is in lowest terms, and that it 
     * has a positive denominator. For example, &minus;4.
     * @throws ArithmeticException If <code>denom</code> is 0 or 
     * <code>Long.MIN_VALUE</code>. The problem with the latter is that its 
     * absolute value is just outside the range of <code>long</code> to 
     * represent. The problem with the former is obvious.
     * @throws IllegalArgumentException If <code>denom</code> is 0. I consider 
     * this one a better exception for division by 0, but I also consider it 
     * more elegant to have only one validation check per parameter. I've been 
     * going back and forth on this one. Clearly this exception would be 
     * inappropriate for <code>denom == Long.MIN_VALUE</code>, so this exception 
     * should never occur for that particular parameter.
     */
    public Fraction(long numer, long denom) {
        if ((denom & Long.MAX_VALUE) == 0) {
            String excMsg = "Denominator " + denom 
                    + " is invalid or unavailable";
            throw new ArithmeticException(excMsg);
        }
        long adjustment = euclideanGCD(numer, denom);
        adjustment *= Long.signum(denom);
        this.numerator = numer / adjustment;
        this.denominator = denom / adjustment;
    }

}
