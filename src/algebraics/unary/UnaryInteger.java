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
import algebraics.IntegerRing;
import arithmetic.Arithmeticable;
import arithmetic.NotDivisibleException;

/**
 * Represents algebraic integers of degree 1. Essentially this is another 
 * wrapper for <code>int</code> primitives.
 * @author Alonso del Arte
 */
public class UnaryInteger implements AlgebraicInteger, 
        Arithmeticable<UnaryInteger>, Comparable<UnaryInteger> {
    
    private final int number;
    
    // TODO: Write tests for this
    @Override
    public UnaryInteger plus(UnaryInteger addend) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger plus(int addend) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger minus(UnaryInteger subtrahend) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger minus(int subtrahend) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger times(UnaryInteger multiplicand) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger times(int multiplicand) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger divides(UnaryInteger divisor) 
            throws NotDivisibleException {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger divides(int divisor) throws NotDivisibleException {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger mod(UnaryInteger divisor) {
        return this;
    }

    // TODO: Write tests for this
    @Override
    public UnaryInteger mod(int divisor) {
        return this;
    }
    
    // TODO: Write tests for this
    @Override
    public int compareTo(UnaryInteger other) {
        return 0;
    }
    
    // TODO: Write tests for this
    @Override
    public int algebraicDegree() {
        return Integer.MAX_VALUE;
    }

    // TODO: Write tests for this
    @Override
    public long trace() {
        return Long.MIN_VALUE;
    }

    // TODO: Write tests for this
    @Override
    public long norm() {
        return Long.MAX_VALUE;
    }

    // TODO: Write tests for this
    @Override
    public long[] minPolynomialCoeffs() {
        long[] array = {Long.MIN_VALUE, Long.MAX_VALUE};
        return array;
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

    // TODO: Write tests for this
    @Override
    public IntegerRing getRing() {
        return new algebraics.quadratics.ImaginaryQuadraticRing(-1);
    }

    // TODO: Write tests for this
    @Override
    public String toASCIIString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String toTeXString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String toHTMLString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public double abs() {
        return Double.NaN;
    }

    // TODO: Write tests for this
    @Override
    public double getRealPartNumeric() {
        return Double.NEGATIVE_INFINITY;
    }

    // TODO: Write tests for this
    @Override
    public double getImagPartNumeric() {
        return Double.POSITIVE_INFINITY;
    }

    // TODO: Write tests for this
    @Override
    public boolean isReApprox() {
        return true;
    }

    // TODO: Write tests for this
    @Override
    public boolean isImApprox() {
        return true;
    }

    // TODO: Write tests for this
    @Override
    public double angle() {
        return Math.PI / 128;
    }
    
    @Override
    public String toString() {
        if (this.number < 0) {
            return "\u2212" + (-this.number);
        } else {
            return Integer.toString(this.number);
        }
    }
    
    public UnaryInteger(int n) {
        this.number = n;
    }

}
