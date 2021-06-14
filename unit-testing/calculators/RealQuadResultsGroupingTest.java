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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests of the RealQuadResultsGrouping class.
 * @author Alonso del Arte
 */
public class RealQuadResultsGroupingTest {
    
    private static final RealQuadraticRing RING_OQ21 
            = new RealQuadraticRing(21);
    
    private static final RealQuadResultsGrouping GROUPING 
            = new RealQuadResultsGrouping(RING_OQ21);
    
    /**
     * Test of the inerts function, of the RealQuadResultsGrouping class.
     */
    @Test
    public void testInerts() {
        System.out.println("inerts");
//        RealQuadResultsGrouping instance = null;
//        HashSet<RealQuadraticInteger> expResult = null;
//        HashSet<RealQuadraticInteger> result = instance.inerts();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the splits function, of the RealQuadResultsGrouping class.
     */
    @Test
    public void testSplits() {
        System.out.println("splits");
//        RealQuadResultsGrouping instance = null;
//        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> expResult 
//                = null;
//        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> result 
//                = instance.splits();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the ramifieds function, of the RealQuadResultsGrouping class.
     */
    @Test
    public void testRamifieds() {
        System.out.println("ramifieds");
//        RealQuadResultsGrouping instance = null;
//        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> expResult 
//                = null;
//        HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> result 
//                = instance.ramifieds();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
