/*
 * Copyright (C) 2021 Alonso del Arte
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
package calculators;

import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

import static calculators.NumberTheoreticFunctionsCalculator.isPrime;
import static calculators.NumberTheoreticFunctionsCalculator.symbolLegendre;;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests of the RealQuadResultsGrouping class.
 * @author Alonso del Arte
 */
public class RealQuadResultsGroupingTest {
    
    private static final int D = 14;
    
    private static final RealQuadraticRing RING_Z14 
            = new RealQuadraticRing(D);
    
    private static final RealQuadResultsGrouping GROUPING 
            = new RealQuadResultsGrouping(RING_Z14);
    
    /**
     * Test of the inerts function, of the RealQuadResultsGrouping class.
     */
    @Test
    public void testInerts() {
        System.out.println("inerts");
        HashSet<RealQuadraticInteger> inertials = GROUPING.inerts();
        int p;
        byte symbol;
        String msg;
        for (RealQuadraticInteger inertial : inertials) {
            msg = "Surd part of " + inertial.toString() + " should be 0";
            assert inertial.getSurdPartMult() == 0 : msg;
            p = inertial.getRegPartMult();
            msg = "The number " + p + " should be an odd prime";
            assert isPrime(p) : msg;
            symbol = symbolLegendre(D, p);
            msg = "(" + D + "/" + p + ") should be -1";
            assert symbol == -1 : msg;
        }
    }

    /**
     * Test of the splits function, of the RealQuadResultsGrouping class.
     */
    @Test
    public void testSplits() {
        System.out.println("splits");
        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> splits 
                = GROUPING.splitPrimes;
        Set<RealQuadraticInteger> keys = splits.keySet();
        int p;
        byte symbol;
        Optional<RealQuadraticInteger> splitterHolder;
        RealQuadraticInteger splitter;
        String msg;
        for (RealQuadraticInteger split : keys) {
            msg = "Surd part of " + split.toString() + " should be 0";
            assert split.getSurdPartMult() == 0 : msg;
            p = split.getRegPartMult();
            msg = "The number " + p + " should be an odd prime";
            assert isPrime(p) : msg;
            symbol = symbolLegendre(D, p);
            msg = "(" + D + "/" + p + ") should be 1";
            assert symbol == 1 : msg;
            splitterHolder = splits.get(split);
            msg = "Optional for splitter of " + p + " should hold a value";
            assert splitterHolder.isPresent() : msg;
            splitter = splitterHolder.get();
            msg = "Norm of " + splitter.toString() + " should match " + p;
            assert Math.abs(splitter.norm()) == p : msg;
        }
    }

    /**
     * Test of the ramifieds function, of the RealQuadResultsGrouping class.
     */
    @Test
    public void testRamifieds() {
        System.out.println("ramifieds");
        RealQuadraticInteger two = new RealQuadraticInteger(2, 0, RING_Z14);
        RealQuadraticInteger seven = new RealQuadraticInteger(7, 0, RING_Z14);
        HashSet<RealQuadraticInteger> expected = new HashSet<>();
        expected.add(two);
        expected.add(seven);
        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> ramifieds 
                = GROUPING.ramifieds();
        Set<RealQuadraticInteger> actual = ramifieds.keySet();
        assertEquals(expected, actual);
        RealQuadraticInteger twoFactor = new RealQuadraticInteger(4, 1, 
                RING_Z14);
        Optional<RealQuadraticInteger> ramifierHolder = ramifieds.get(two);
        String msg = "Optional for ramifier of 2 should hold a value";
        assert ramifierHolder.isPresent() : msg;
        RealQuadraticInteger ramifier = ramifierHolder.get();
        assertEquals(twoFactor, ramifier);
        RealQuadraticInteger sevenFactor = new RealQuadraticInteger(7, 2, 
                RING_Z14);
        ramifierHolder = ramifieds.get(seven);
        msg = "Optional for ramifier of " + D + " should hold a value";
        assert ramifierHolder.isPresent() : msg;
        ramifier = ramifierHolder.get();
        assertEquals(sevenFactor, ramifier);
    }
    
    /**
     * The number 2 is inert in O_Q(sqrt(21)), and the grouping should report it 
     * as such. This test is necessary because 2 ramifies in the ring used for 
     * the previous tests.
     */
    @Test
    public void testTwoIsInert() {
        RealQuadraticRing ring = new RealQuadraticRing(21);
        RealQuadraticInteger two = new RealQuadraticInteger(2, 0, ring);
        RealQuadResultsGrouping grouping = new RealQuadResultsGrouping(ring);
        String msg = "Set of inert primes in " + ring.toString() 
                + " should contain 2";
        assert grouping.inerts().contains(two) : msg;
        msg = "Set of ramified primes in " + ring.toString() 
                + " should NOT contain 2";
        assert !grouping.ramifieds().containsKey(two) : msg;
        msg = "Set of split primes in " + ring.toString() 
                + " should NOT contain 2";
        assert !grouping.splits().containsKey(two) : msg;
    }
    
    /**
     * The number 2 splits in O_Q(sqrt(41)), and the grouping should report it 
     * as such. Furthermore, the splitter should be reported as 7/2 + 
     * sqrt(41)/2. The conjugate of 7/2 + sqrt(41)/2 is obtained easily enough  
     * by the caller.
     */
    @Test
    public void testTwoSplits() {
        RealQuadraticRing ring = new RealQuadraticRing(41);
        RealQuadraticInteger two = new RealQuadraticInteger(2, 0, ring);
        RealQuadResultsGrouping grouping = new RealQuadResultsGrouping(ring);
        String msg = "Set of split primes in " + ring.toString() 
                + " should contain 2";
        assert grouping.splits().containsKey(two) : msg;
        Optional<RealQuadraticInteger> splitterHolder 
                = grouping.splits().get(two);
        RealQuadraticInteger expected = new RealQuadraticInteger(7, 1, ring, 2);
        RealQuadraticInteger actual = splitterHolder.get();
        assertEquals(expected, actual);
        msg = "Set of ramified primes in " + ring.toString() 
                + " should NOT contain 2";
        assert !grouping.ramifieds().containsKey(two) : msg;
        msg = "Set of inert primes in " + ring.toString() 
                + " should NOT contain 2";
        assert !grouping.inerts().contains(two) : msg;
    }
    
    /**
     * The number 7 is inert in Z[sqrt(3)], and the grouping should report it as 
     * such. This test is necessary because 7 ramifies in the ring used for some 
     * of the previous tests.
     */
    @Test
    public void testSevenIsInert() {
        RealQuadraticRing ring = new RealQuadraticRing(3);
        RealQuadraticInteger seven = new RealQuadraticInteger(7, 0, ring);
        RealQuadResultsGrouping grouping = new RealQuadResultsGrouping(ring);
        String msg = "Set of inert primes in " + ring.toString() 
                + " should contain 7";
        assert grouping.inerts().contains(seven) : msg;
        msg = "Set of ramified primes in " + ring.toString() 
                + " should NOT contain 7";
        assert !grouping.ramifieds().containsKey(seven) : msg;
        msg = "Set of split primes in " + ring.toString() 
                + " should NOT contain 7";
        assert !grouping.splits().containsKey(seven) : msg;
    }
    
    /**
     * The number 7 splits in Z[sqrt(2)], and the grouping should report it as 
     * such. Furthermore, the splitter should be reported as 3 + sqrt(2). The  
     * conjugate of 3 + sqrt(2) is obtained easily enough by the caller.
     */
    @Test
    public void testSevenSplits() {
        RealQuadraticRing ring = new RealQuadraticRing(2);
        RealQuadraticInteger seven = new RealQuadraticInteger(7, 0, ring);
        RealQuadResultsGrouping grouping = new RealQuadResultsGrouping(ring);
        String msg = "Set of split primes in " + ring.toString() 
                + " should contain 7";
        assert grouping.splits().containsKey(seven) : msg;
        Optional<RealQuadraticInteger> splitterHolder 
                = grouping.splits().get(seven);
        RealQuadraticInteger expected = new RealQuadraticInteger(3, 1, ring);
        RealQuadraticInteger actual = splitterHolder.get();
        assertEquals(expected, actual);
        msg = "Set of ramified primes in " + ring.toString() 
                + " should NOT contain 7";
        assert !grouping.ramifieds().containsKey(seven) : msg;
        msg = "Set of inert primes in " + ring.toString() 
                + " should NOT contain 7";
        assert !grouping.inerts().contains(seven) : msg;
    }
    
}
