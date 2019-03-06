/*
 * Copyright (C) 2019 Alonso del Arte
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

import calculators.NumberTheoreticFunctionsCalculator;

import java.util.ArrayList;

/**
 * Defines objects to represent fractions symbolically rather than numerically. 
 * For example, to represent one half as 1/2 rather than 0.5.
 * @author Alonso del Arte
 */
public class Fraction implements Comparable<Fraction> {
    
    private final long fractNumer;
    private final long fractDenom;
    
    private final double numericVal;
    
    protected static final int HASH_SEP = 65536;
    
    /**
     * Gives the numerator of the fraction. It may or may not match the 
     * numerator the constructor was given.
     * @return The numerator of the fraction in lowest terms, regardless of how 
     * the fraction was constructed. For example, if the fraction was 
     * constructed as 2/&minus;4, the fraction will be expressed as &minus;1/2 
     * and this function will return &minus;1, not 2.
     */
    public long getNumerator() {
        return this.fractNumer;
    }
    
    /**
     * Gives the denominator of the fraction. It may or may not match the 
     * denominator the constructor was given.
     * @return The denominator of the fraction in lowest terms and as a positive 
     * number, regardless of how the fraction was constructed. For example, if 
     * the fraction was constructed as 2/&minus;4, the fraction will be 
     * expressed as &minus;1/2 and this will return 2, not &minus;4.
     */
    public long getDenominator() {
        return this.fractDenom;
    }
    
    /**
     * Adds a fraction to this fraction.
     * @param addend The fraction to add. For example, 1/7.
     * @return A new Fraction object with the sum. For example, if this fraction 
     * is 1/2 and the addend is 1/7, the result will be 9/14.
     */
    public Fraction plus(Fraction addend) {
        long interNumerA = this.fractNumer * addend.fractDenom;
        long interNumerB = addend.fractNumer * this.fractDenom;
        long newNumer = interNumerA + interNumerB;
        long newDenom = this.fractDenom * addend.fractDenom;
        return new Fraction(newNumer, newDenom);
    }
    
    /**
     * Adds an integer to this fraction.
     * @param addend The integer to add. For example, 3.
     * @return A new Fraction object with the sum. For example, if this fraction 
     * is 1/2 and the integer addend is 3, the result will be 7/2.
     */
    public Fraction plus(int addend) {
        long newNumer = this.fractNumer + addend * this.fractDenom;
        return new Fraction(newNumer, this.fractDenom);
    }
    
    /**
     * Subtracts a fraction from this fraction.
     * @param subtrahend The fraction to subtract. For example, 1/7.
     * @return A new Fraction object with the subtraction. For example, if this 
     * fraction is 1/2 and the subtrahend is 1/7, the result will be 5/14.
     */
    public Fraction minus(Fraction subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    /**
     * Subtracts an integer from this fraction.
     * @param subtrahend The integer to subtract. For example, 3.
     * @return A new Fraction object with the subtraction. For example, if this 
     * fraction is 1/2 and the integer subtrahend is 3, the result will be 
     * &minus;5/2.
     */
    public Fraction minus(int subtrahend) {
        return this.plus(-subtrahend);
    }
    
    /**
     * Multiplies this fraction by another fraction.
     * @param multiplicand The fraction to multiply by. For example, 1/7.
     * @return A new Fraction object with the product. For example, if this 
     * fraction is 1/2 and the multiplicand is 1/7, the result will be 1/14.
     */
    public Fraction times(Fraction multiplicand) {
        long newNumer = this.fractNumer * multiplicand.fractNumer;
        long newDenom = this.fractDenom * multiplicand.fractDenom;
        return new Fraction(newNumer, newDenom);
    }
    
    /**
     * Multiplies this fraction by an integer.
     * @param multiplicand The integer to multiply by. For example, 3.
     * @return A new Fraction object with the product. For example, if this 
     * fraction is 1/2 and the multiplicand is 3, the result will be 3/2.
     */
    public Fraction times(int multiplicand) {
        return new Fraction(this.fractNumer * multiplicand, this.fractDenom);
    }
    
    /**
     * Divides this fraction by another fraction.
     * @param divisor The fraction to divide by. For example, 1/7.
     * @return A new Fraction object with the division. For example, if this 
     * fraction is 1/2 and the divisor is 1/7, the result will be 7/2.
     * @throws IllegalArgumentException If the divisor is 0, this runtime 
     * exception will be thrown.
     */
    public Fraction dividedBy(Fraction divisor) {
        if (divisor.fractNumer == 0) {
            String exceptionMessage = "Dividing " + this.toString() + " by 0 results in an indeterminate number.";
            throw new IllegalArgumentException(exceptionMessage);
        }
        return this.times(divisor.reciprocal());
    }
    
    /**
     * Divides this fraction by an integer.
     * @param divisor The integer to divide by. For example, 3.
     * @return A new Fraction object with the division. For example, if this 
     * fraction is 1/2 and the divisor is 3, the result will be 1/6.
     * @throws IllegalArgumentException If the divisor is 0, this runtime 
     * exception will be thrown.
     */
    public Fraction dividedBy(int divisor) {
        if (divisor == 0) {
            String exceptionMessage = "Dividing " + this.toString() + " by 0 results in an indeterminate number.";
            throw new IllegalArgumentException(exceptionMessage);
        }
        return new Fraction(this.fractNumer, this.fractDenom * divisor);
    }
    
    /**
     * Multiplies this fraction by &minus;1.
     * @return A new Fraction object with the negation. For example, if this 
     * fraction is 1/2, the result will be &minus;1/2.
     */
    public Fraction negate() {
        return new Fraction(-this.fractNumer, this.fractDenom);
    }
    
    /**
     * Gives the reciprocal of this fraction. If the fraction is a unit 
     * fraction, the reciprocal will be arithmetically equal to an integer.
     * @return A new Fraction object with the reciprocal. For example, if this 
     * fraction is 1/2, the result will be 2; if this fraction is 7/2, the 
     * result will be 2/7.
     * @throws IllegalArgumentException If this fraction is 0, this runtime 
     * exception will be thrown with the exception detail message "Denominator 0 
     * is not allowed."
     */
    public Fraction reciprocal() {
        return new Fraction(this.fractDenom, this.fractNumer);
    }
    
    /**
     * Gives a representation of this fraction as a String, using ASCII 
     * characters only.
     * @return A String with the numerator, followed by the character "/" and 
     * then the denominator. However, if this fraction is an integer, the 
     * characters "/1" will be omitted. For example, if this fraction is 
     * &minus;1/2, the result will be "-1/2".
     */
    @Override
    public String toString() {
        if (this.fractDenom == 1) {
            return Long.toString(this.fractNumer);
        } else {
            return (this.fractNumer + "/" + this.fractDenom);
        }
    }
    
    /**
     * Gives a representation of this fraction as a String suitable for use in 
     * an HTML document. The output placed in the context of an HTML document 
     * viewed in a Web browser should render with the numerator towards the top 
     * left and the denominator towards the bottom right.
     * @return A String with the numerator set as an HTML superscript, followed 
     * by the fraction slash character entity and then the denominator set as an 
     * HTML subscript. However, if this fraction is an integer, the 
     * "&nbsp;&frasl;<sub>1</sub>" part will be omitted and the integer will be 
     * presented with no special formatting markup. If the fraction is negative, 
     * the String will start with "&minus;" rather than "-". For example, if 
     * this fraction is &minus;1/2, the result will be 
     * "<sup>&minus;1</sup>&frasl;<sub>2</sub>".
     */
    public String toHTMLString() {
        if (this.fractDenom == 1) {
            return Long.toString(this.fractNumer);
        } else {
            String fractStr = "<sup>";
            if (this.fractNumer < 0) {
                fractStr = fractStr + "&minus;" + Math.abs(this.fractNumer);
            } else {
                fractStr = fractStr + this.fractNumer;
            }
            fractStr = fractStr + "</sup>&frasl;<sub>" + this.fractDenom + "</sub>";
            return fractStr;
        }
    }
    
    /**
     * Gives a representation of this fraction as a String suitable for use in a 
     * TeX document.
     * @return A String starting with "\frac{", followed by the numerator, then 
     * "}{", then the denominator and lastly "}". For example, if this fraction 
     * is &minus;1/2, the result will be "\frac{-1}{2}". However, if this 
     * fraction is an integer, the output will be the same as {@link 
     * #toString()}.
     */
    public String toTeXString() {
        if (this.fractDenom == 1) {
            return Long.toString(this.fractNumer);
        } else {
            return ("\\frac{" + this.fractNumer + "}{" + this.fractDenom + "}");
        }
    }
    
    /**
     * Gives a hash code for the fraction. This is guaranteed to be the same for 
     * two fractions that are arithmetically equal. It is likely but not 
     * guaranteed to be different for two fractions that are arithmetically 
     * unequal. Also, it is likely but not guaranteed that the sign of the hash 
     * code will match the sign of the fraction.
     * @return A 32-bit integer hash code. For example, if this fraction is 
     * &minus;9/14, the result might be &minus;589810.
     */
    @Override
    public int hashCode() {
        long numerHash = this.fractNumer % HASH_SEP;
        long denomHash = this.fractDenom % HASH_SEP;
        return (int) (numerHash * HASH_SEP + denomHash);
    }

    /**
     * Determines if a Fraction object is equal to another object.
     * @param obj The object to be tested for equality.
     * @return True if both objects are of class Fraction and they represent the 
     * same arithmetical fraction (regardless of what numerators and 
     * denominators were used at the time of construction), false otherwise. For 
     * example, 3/4 and 9/12 should be found to be equal. 3/4 and 3/5 should not 
     * be found to be equal. A Fraction object should not be found to be equal 
     * to an {@link Integer} object even if their values are arithmetically 
     * equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fraction other = (Fraction) obj;
        if (this.fractNumer != other.fractNumer) {
            return false;
        }
        return (this.fractDenom == other.fractDenom);
    }

    /**
     * Compares the number represented by this Fraction object with 
     * the number represented by the specified Fraction object for 
     * order. Returns a negative integer, zero, or a positive integer as this 
     * fraction is less than, equal to, or greater than the 
     * specified fraction. This enables sorting with {@link 
     * java.util.Collections#sort(java.util.List)} without need for a 
     * comparator.
     * @param other The Fraction to compare to. Examples: &minus;1/2, 5/3, 22/7.
     * @return &minus;1 or any other negative integer if the compared Fraction 
     * is greater than this Fraction, 0 if they are equal, 1 or any other 
     * positive integer if the compared Fraction is less than this Fraction. For 
     * example, if this Fraction is 5/3, compared to 22/7 the result would most 
     * likely be &minus;1; compared to 5/3 the result would certainly be 0, and 
     * compared to &minus;1/2 the result would most likely be 1.
     */
    @Override
    public int compareTo(Fraction other) {
        Fraction diff = this.minus(other);
        if (diff.fractNumer < 0) {
            return -1;
        }
        if (diff.fractNumer > 0) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Gives a numeric approximation of the value of this fraction. This is 
     * likely to be precise if the denominator is a small power of 2.
     * @return A numeric approximation of the value of this fraction. For 
     * example, if this fraction is 1/7, the result might be something like 
     * 0.14285714285714285714285714285714. If this fraction is 6/13, the result 
     * might be something like 0.46153846153846153846153846153846.
     */
    public double getNumericApproximation() {
        return this.numericVal;
    }
    
    public Fraction[] getEgyptianFractions() {
        ArrayList<Fraction> fractList = new ArrayList<>();
        long currDenom = 2;
        Fraction currPortion = this;
        Fraction currUnitFract, resetPortion;
        if (this.numericVal <= 0.0 || this.numericVal >= 1.0) {
            Fraction intPart = new Fraction((long) Math.floor(numericVal));
            fractList.add(intPart);
            currPortion = this.minus(intPart);
        }
        while (currPortion.fractNumer > 0) {
            currUnitFract = new Fraction(1, currDenom);
            resetPortion = currPortion.minus(currUnitFract);
            if (resetPortion.fractNumer >= 0) {
                fractList.add(currUnitFract);
                currPortion = resetPortion;
            }
            currDenom++;
        }
        Fraction[] fractArray = fractList.toArray(new Fraction[0]);
        return fractArray;
    }
    
    /**
     * Implicit denominator constructor for integers. Denominator is filled in 
     * as 1.
     * @param numerator The numerator of the Fraction. For example, 7. Then the 
     * Fraction 7/1 is arithmetically equal to the integer 7.
     */
    public Fraction(long numerator) {
        this.fractNumer = numerator;
        this.numericVal = numerator;
        this.fractDenom = 1;
    }
    
    /**
     * This should be considered the default constructor. It makes sure the 
     * fraction is expressed in lowest terms and that the denominator is 
     * positive.
     * @param numerator The numerator of the fraction. For example, 2.
     * @param denominator The denominator of the fraction. It must not be 0. 
     * Other than that, there are no requirements for the denominator. It need 
     * not be coprime to the numerator, and it may be negative. The constructor 
     * will make sure the fraction is in lowest terms, and that it has a 
     * positive denominator. For example, &minus;4.
     */
    public Fraction(long numerator, long denominator) {
        if (denominator == 0) {
            String exceptionMessage = "Denominator 0 is not allowed.";
            throw new IllegalArgumentException(exceptionMessage);
        }
        long gcdNumDen = NumberTheoreticFunctionsCalculator.euclideanGCD(numerator, denominator);
        if (denominator < 0) {
            gcdNumDen *= -1;
        }
        this.fractNumer = numerator / gcdNumDen;
        this.fractDenom = denominator / gcdNumDen;
        this.numericVal = ((double) this.fractNumer / (double) this.fractDenom);
    }

}
