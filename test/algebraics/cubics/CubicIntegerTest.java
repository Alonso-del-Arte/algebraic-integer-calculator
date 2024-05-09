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
package algebraics.cubics;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CubicInteger class.
 * @author Alonso del Arte
 */
public class CubicIntegerTest {
    
    /**
     * Test of the getRing function, of the CubicInteger class.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        CubicRing expected = new CubicRingTest.CubicRingImpl();
        CubicInteger instance = new CubicIntegerImpl(expected);
        CubicRing actual = instance.getRing();
        assertEquals(expected, actual);
    }

    static class CubicIntegerImpl extends CubicInteger {

        @Override
        public int algebraicDegree() {
            return 3;
        }

        @Override
        public long trace() {
            return -1;
        }

        @Override
        public long norm() {
            return -1;
        }

        @Override
        public long[] minPolynomialCoeffs() {
            long[] coeffs = {1, 2, 3, 4};
            return coeffs;
        }

        @Override
        public String minPolynomialString() {
            return "FOR TESTING PURPOSES ONLY";
        }

        @Override
        public String minPolynomialStringTeX() {
            return "FOR TESTING PURPOSES ONLY";
        }

        @Override
        public String minPolynomialStringHTML() {
            return "<b>FOR TESTING PURPOSES ONLY</b>";
        }

        @Override
        public String toASCIIString() {
            return "FOR TESTING PURPOSES ONLY";
        }

        @Override
        public String toTeXString() {
            return "FOR TESTING PURPOSES ONLY";
        }

        @Override
        public String toHTMLString() {
            return "FOR TESTING PURPOSES ONLY";
        }

        @Override
        public double abs() {
            return 0.0;
        }

        @Override
        public double getRealPartNumeric() {
            return 0.0;
        }

        @Override
        public double getImagPartNumeric() {
            return 0.0;
        }

        @Override
        public boolean isReApprox() {
            return true;
        }

        @Override
        public boolean isImApprox() {
            return true;
        }

        @Override
        public double angle() {
            return Double.NaN;
        }
        
        public CubicIntegerImpl(CubicRing ring) {
            super(ring);
        }

    }
    
}
