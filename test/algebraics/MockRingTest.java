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

import static algebraics.IntegerRingTest.RANDOM;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the MockRing class.
 * @author Alonso del Arte
 */
public class MockRingTest {
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        MockRing ring = new MockRing(maxDegree, includeImaginary);
        String expected = "Mock Ring of Degree " + maxDegree;
        String actual = ring.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        MockRing ring = new MockRing(maxDegree, includeImaginary);
        assertEquals(ring, ring);
    }
    
    private static Object provideNull() {
        return null;
    }
    
    @Test
    public void testNotEqualsNull() {
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        MockRing ring = new MockRing(maxDegree, includeImaginary);
        Object obj = provideNull();
        String msg = ring.toString() + " should not equal null";
        assert !ring.equals(obj) : msg;
    }
    
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        int expected = randomNumber(1024) + 16;
        MockRing badRing = new MockRing(expected);
        int actual = badRing.getMaxAlgebraicDegree();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        int maxDegree = randomNumber(1024) + 16;
        MockRing ring = new MockRing(maxDegree, false);
        String msg = ring.toString() 
                + " constructed w/ incl. imaginary false should be purely real";
        assert ring.isPurelyReal() : msg;
    }
    
    @Test
    public void testIsPurelyRealButIsNot() {
        int maxDegree = randomNumber(1024) + 16;
        MockRing ring = new MockRing(maxDegree, true);
        String msg = ring.toString() 
                + " constructed w/ incl. imag. true shouldn't be purely real";
        assert !ring.isPurelyReal() : msg;
    }
    
}
