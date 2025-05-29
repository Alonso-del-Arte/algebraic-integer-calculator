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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of the SingleIntRootAdjoinedRing interface.
 * @author Alonso del Arte
 */
public class SingleIntRootAdjoinedRingTest {
    
    @Test
    public void testGetExponentForRadicand() {
        System.out.println("getExponentForRadicand");
        int maxDegree = randomNumber(128) + 2;
        SingleIntRootAdjoinedRing instance = new MockSingleRootRing(maxDegree);
        int expected = instance.getMaxAlgebraicDegree();
        int actual = instance.getExponentForRadicand();
        String message = "Default exponent should equal max algebraic degree";
        assertEquals(message, expected, actual);
    }
    
    private static class MockSingleRootRing extends MockRing 
            implements SingleIntRootAdjoinedRing {
        
        @Override
        public int getRadicand() {
            return -1;
        }

        public MockSingleRootRing(int maxDegree) {
            super(maxDegree);
        }
        
    }
    
}
