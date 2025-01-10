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

import static algebraics.IntegerRingTest.RANDOM;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertMinimum;
import static org.testframe.api.Asserters.assertThrows;

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
        IntegerRing ring = new MockRing(maxDegree, includeImaginary);
        assertEquals(ring, ring);
    }
    
    public static Object provideNull() {
        return null;
    }
    
    @Test
    public void testNotEqualsNull() {
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        IntegerRing ring = new MockRing(maxDegree, includeImaginary);
        Object obj = provideNull();
        String msg = ring.toString() + " should not equal null";
        assert !ring.equals(obj) : msg;
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        IntegerRing ring = new MockRing(maxDegree, includeImaginary);
        Object obj = new Object();
        String msg = ring.toString() + " should not equal " + obj.toString();
        assert !ring.equals(obj) : msg;
    }
    
    @Test
    public void testNotEqualsDiffDegree() {
        int maxDegreeA = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        IntegerRing ringA = new MockRing(maxDegreeA, includeImaginary);
        int maxDegreeB = maxDegreeA + randomNumber(8) + 2;
        IntegerRing ringB = new MockRing(maxDegreeB, includeImaginary);
        String msg = ringA.toString() + " should not equal " + ringB.toString();
        assert !ringA.equals(ringB) : msg;
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        IntegerRing someRing = new MockRing(maxDegree, includeImaginary);
        IntegerRing sameRing = new MockRing(maxDegree, includeImaginary);
        assertEquals(someRing, sameRing);
    }
    
    @Test
    public void testNotEqualsDiffInclImag() {
        int maxDegree = randomNumber(16) + 4;
        boolean includeImaginary = RANDOM.nextBoolean();
        IntegerRing ringA = new MockRing(maxDegree, includeImaginary);
        IntegerRing ringB = new MockRing(maxDegree, !includeImaginary);
        String msg = ringA.toString() + " initialized with include imaginary " 
                + includeImaginary + " should not equal " + ringB.toString() 
                + " initialized with opposite include imaginary";
        assert !ringA.equals(ringB) : msg;
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int initialCapacity = randomNumber(64) + 16;
        Set<IntegerRing> rings = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            int maxDegree = randomNumber(initialCapacity) + i + 1;
            IntegerRing ring = new MockRing(maxDegree, RANDOM.nextBoolean());
            rings.add(ring);
            hashes.add(ring.hashCode());
        }
        int numberOfRings = rings.size();
        int minimum = 3 * numberOfRings / 5;
        int actual = hashes.size();
        String msg = "Given " + numberOfRings 
                + " distinct rings, there should be at least " + minimum 
                + " distinct hash codes";
        assertMinimum(minimum, actual, msg);
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
    
    @Test
    public void testConstructorRejectsNegativeDegree() {
        int maxDegree = -randomNumber(4096) - 1;
        String msg = "Constructor should reject degree " + maxDegree;
        Throwable t = assertThrows(() -> {
            MockRing badRing = new MockRing(maxDegree);
            System.out.println(msg + ", not given " + badRing.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
