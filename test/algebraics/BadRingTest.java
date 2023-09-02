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
 * Tests of the BadRing class.
 * @author Alonso del Arte
 */
public class BadRingTest {
    
    @Test
    public void testGetDiscriminant() {
        System.out.println("discriminant");
        int expected = randomNumber(1024) + 16;
        BadRing ring = new BadRing(expected);
        int actual = ring.discriminant();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        int expected = randomNumber(1024) + 16;
        BadRing badRing = new BadRing(expected);
        int actual = badRing.getMaxAlgebraicDegree();
        assertEquals(expected, actual);
    }
    
}
