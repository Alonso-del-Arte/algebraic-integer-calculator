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
package algebraics;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import java.math.BigInteger;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of the AlgebraicInteger interface.
 * @author Alonso del Arte
 */
public class AlgebraicIntegerTest {
    
    @Test
    public void testFullTrace() {
        System.out.println("fullTrace");
        int n = randomNumber();
        AlgebraicInteger instance = new AlgebraicIntegerImpl(n);
        BigInteger expected = BigInteger.valueOf(instance.trace());
        BigInteger actual = instance.fullTrace();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testFullNorm() {
        System.out.println("fullNorm");
        int n = randomNumber();
        AlgebraicInteger instance = new AlgebraicIntegerImpl(n);
        BigInteger expected = BigInteger.valueOf(instance.norm());
        BigInteger actual = instance.fullNorm();
        assertEquals(expected, actual);
    }
    
    private static class AlgebraicIntegerImpl implements AlgebraicInteger {
        
        private final int number;
        
        @Override
        public int algebraicDegree() {
            return 1;
        }

        @Override
        public long trace() {
            return this.number;
        }

        @Override
        public long norm() {
            return this.number;
        }

        @Override
        public long[] minPolynomialCoeffs() {
            return new long[]{-this.number, 1L};
        }

        @Override
        public String minPolynomialString() {
            return this.minPolynomialStringTeX();
        }

        @Override
        public String minPolynomialStringTeX() {
            String x = "x ";
            String sign = (this.number < 0) ? "+ " : "- ";
            return x + sign + Math.abs(this.number);
        }

        @Override
        public String minPolynomialStringHTML() {
            return this.minPolynomialString();
        }

        @Override
        public IntegerRing getRing() {
            return new MockRing();
        }

        @Override
        public String toString() {
            return this.toASCIIString();
        }
    
        @Override
        public String toASCIIString() {
            return Integer.toString(this.number);
        }

        @Override
        public String toTeXString() {
            return this.toASCIIString();
        }

        @Override
        public String toHTMLString() {
            return this.toASCIIString();
        }
    
        @Override
        public double abs() {
            return Math.abs(this.number);
        }

        @Override
        public double getRealPartNumeric() {
            return this.number;
        }

        @Override
        public double getImagPartNumeric() {
            return 0.0;
        }

        @Override
        public boolean isReApprox() {
            return false;
        }

        @Override
        public boolean isImApprox() {
            return false;
        }

        @Override
        public double angle() {
            if (this.number < 0) {
                return Math.PI;
            } else {
                return 0.0;
            }
        }
        
        private AlgebraicIntegerImpl(int n) {
            this.number = n;
        }    
        
    }
    
}
