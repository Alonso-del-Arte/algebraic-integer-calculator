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
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the PureCubicInteger class.
 * @author Alonso del Arte
 */
public class PureCubicIntegerTest {
    
    private static CubicRing chooseRing() {
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        return new PureCubicRing(d);
    }
    
    /**
     * Test of getRing method, of class PureCubicInteger.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        CubicRing expected = chooseRing();
        PureCubicInteger number = new PureCubicInteger(randomNumber(), 
                randomNumber(), randomNumber(), expected);
        CubicRing actual = number.getRing();
        assertEquals(expected, actual);
    }
    
}
