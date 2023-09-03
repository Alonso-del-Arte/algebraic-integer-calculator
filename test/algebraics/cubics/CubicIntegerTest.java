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
        CubicRing expected = new BadCubicRing();
        CubicInteger instance = new BadCubicInteger(expected);
        CubicRing actual = instance.getRing();
        assertEquals(expected, actual);
    }

    class CubicIntegerImpl extends CubicInteger {

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
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String minPolynomialString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String minPolynomialStringTeX() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String minPolynomialStringHTML() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toASCIIString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toTeXString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toHTMLString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public double abs() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public double getRealPartNumeric() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public double getImagPartNumeric() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean isReApprox() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean isImApprox() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
