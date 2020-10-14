/*
 * Copyright (C) 2020 Alonso del Arte
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

/**
 * Represents a fraction.
 * @author Alonso del Arte
 */
public class BigFraction {
    
    private final BigInteger numerator, denominator;
    
    @Override
    public String toString() {
        return this.numerator.toString() + "/" + this.denominator.toString();
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigDecimal getNumericApproximation() {
        return BigDecimal.ZERO;
    }
    
    public double getDouble() {
        return this.getNumericApproximation().doubleValue();
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction plus(BigFraction addend) {
        return this;
    }
    
    public BigFraction plus(Fraction addend) {
        BigFraction wrap = new BigFraction(addend);
        return this.plus(wrap);
    }
    
    public BigFraction plus(int addend) {
        BigInteger wrap = this.numerator.add(this.denominator
                .multiply(BigInteger.valueOf(addend)));
        return new BigFraction(wrap, this.denominator);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction negate() {
        return this;
    }
    
    public BigFraction minus(BigFraction subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    public BigFraction minus(Fraction subtrahend) {
        BigFraction wrap = new BigFraction(subtrahend.negate());
        return this.plus(wrap);
    }
    
    public BigFraction minus(int subtrahend) {
        return this.plus(-subtrahend);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction times(BigFraction multiplicand) {
        return this;
    }
    
    public BigFraction times(Fraction multiplicand) {
        BigFraction wrap = new BigFraction(multiplicand);
        return this.times(wrap);
    }
    
    public BigFraction times(int multiplicand) {
        BigInteger wrap 
                = this.numerator.multiply(BigInteger.valueOf(multiplicand));
        return new BigFraction(wrap, this.denominator);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction reciprocal() {
        return this;
    }

    // STUB TO FAIL THE FIRST TEST
    public BigFraction dividedBy(BigFraction divisor) {
        return new BigFraction(BigInteger.ONE, BigInteger.TEN);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction dividedBy(Fraction divisor) {
        return new BigFraction(BigInteger.ONE, BigInteger.TEN);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction dividedBy(int divisor) {
        return new BigFraction(BigInteger.ONE, BigInteger.TEN);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction(Fraction fraction) {
        this(BigInteger.ONE, BigInteger.ONE);
    }
    
    // STUB TO FAIL THE FIRST TEST
    public BigFraction(BigInteger numer, BigInteger denom) {
        this.numerator = numer;
        this.denominator = denom;
    }
    
}
