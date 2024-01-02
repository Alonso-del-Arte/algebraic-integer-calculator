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
package algebraics;

import arithmetic.PowerBasis;
import fractions.Fraction;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the IntegerRing interface.
 * @author Alonso del Arte
 */
public class IntegerRingTest {
    
    /**
     * Test of the toTeXStringBlackboardBold function of the IntegerRing 
     * interface. The default implementation should give the same result as 
     * {@link IntegerRing#toTeXString()}.
     */
    @Test
    public void testToTeXStringBlackboardBold() {
        System.out.println("toTeXStringBlackboardBold");
        IntegerRing ring = new MockRing();
        String expected = ring.toTeXString();
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    private static class MockRing implements IntegerRing {

        private static final Fraction[] FAKE_POWER_BASIS_FRACTIONS 
                = {new Fraction(22, 7), new Fraction(355, 113)};

        @Override
        public int getMaxAlgebraicDegree() {
            return Integer.MIN_VALUE;
        }

        @Override
        public boolean isPurelyReal() {
            return false;
        }

        @Override
        public int discriminant() {
            return 0;
        }

        @Override
        public PowerBasis getPowerBasis() {
            return new PowerBasis(FAKE_POWER_BASIS_FRACTIONS);
        }

        @Override
        public String toASCIIString() {
            return "For testing purposes only";
        }

        @Override
        public String toTeXString() {
            return "For testing purposes \\emph{only}";
        }

        @Override
        public String toHTMLString() {
            return "For testing purposes <em>only</em>";
        }

        @Override
        public String toFilenameString() {
            return "TEST.MOK";
        }
        
    }
    
}
