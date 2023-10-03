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
package algebraics;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the BadInteger class.
 * @author Alonso del Arte
 */
public class BadIntegerTest {
    
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        int maxDegree = randomNumber(2048) + 32;
        BadRing expected = new BadRing(maxDegree);
        BadInteger integer = new BadInteger(expected);
        BadRing actual = integer.getRing();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAlgebraicDegree() {
        int expected = randomNumber(2048) + 32;
        BadRing ring = new BadRing(expected);
        AlgebraicInteger number = new BadInteger(ring);
        int actual = number.algebraicDegree();
        assertEquals(expected, actual);
    }
    
}
