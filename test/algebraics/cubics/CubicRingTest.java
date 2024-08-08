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

import arithmetic.PowerBasis;
import fractions.Fraction;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CubicInteger class.
 * @author Alonso del Arte
 */
public class CubicRingTest {
    
    private static final Fraction ONE = new Fraction(1);
    
    private static final Fraction[] THREE_ONES = {ONE, ONE, ONE};
    
    private static final PowerBasis DEFAULT_BASIS = new PowerBasis(THREE_ONES);
    
    @Test
    public void testMaxAlgebraicDegreeConstant() {
        int expected = 3;
        int actual = CubicRing.MAX_ALGEBRAIC_DEGREE;
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getMaxAlgebraicDegree function, of the CubicRing class.
     */
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        CubicRing instance = new CubicRingImpl();
        int expected = CubicRing.MAX_ALGEBRAIC_DEGREE;
        int actual = instance.getMaxAlgebraicDegree();
        assertEquals(expected, actual);
    }

    static class CubicRingImpl extends CubicRing {

        @Override
        public boolean isPurelyReal() {
            return true;
        }

        @Override
        public int discriminant() {
            return -1;
        }

        @Override
        public PowerBasis getPowerBasis() {
            return CubicRingTest.DEFAULT_BASIS;
        }

        @Override
        public String toASCIIString() {
            return "SORRY, NOT IMPLEMENTED YET";
        }

        @Override
        public String toTeXString() {
            return "SORRY, NOT IMPLEMENTED YET";
        }

        @Override
        public String toHTMLString() {
            return "<b>SORRY, NOT IMPLEMENTED YET</b>";
        }

        @Override
        public String toFilenameString() {
            return "NOT_IMPL.YET";
        }
    }
    
}
