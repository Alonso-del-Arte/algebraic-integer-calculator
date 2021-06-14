/*
 * Copyright (C) 2021 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    
    private static final int D = 41;
    
    private static final RealQuadraticRing RING_OQ41 
            = new RealQuadraticRing(D);
    
    private static final RealQuadResultsGrouping GROUPING 
            = new RealQuadResultsGrouping(RING_OQ41);
    
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
        RealQuadraticInteger two = new RealQuadraticInteger(2, 0, RING_OQ41);
        RealQuadraticInteger fortyOne = new RealQuadraticInteger(41, 0, 
                RING_OQ41);
        HashSet<RealQuadraticInteger> expected = new HashSet<>();
        expected.add(two);
        expected.add(fortyOne);
        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> ramifieds 
                = GROUPING.ramifieds();
        Set<RealQuadraticInteger> actual = ramifieds.keySet();
        assertEquals(expected, actual);
        RealQuadraticInteger twoFactor = new RealQuadraticInteger(7, 1, 
                RING_OQ41, 2);
        Optional<RealQuadraticInteger> ramifierHolder = ramifieds.get(two);
        String msg = "Optional for ramifier of 2 should hold a value";
        assert ramifierHolder.isPresent() : msg;
        RealQuadraticInteger ramifier = ramifierHolder.get();
        assertEquals(twoFactor, ramifier);
        RealQuadraticInteger fortyOneFactor = new RealQuadraticInteger(0, 1, 
                RING_OQ41);
        ramifierHolder = ramifieds.get(fortyOne);
        msg = "Optional for ramifier of 41 should hold a value";
        assert ramifierHolder.isPresent() : msg;
        ramifier = ramifierHolder.get();
        assertEquals(fortyOneFactor, ramifier);
    }
    
}
