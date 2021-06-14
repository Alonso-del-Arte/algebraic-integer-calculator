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

import algebraics.IntegerRing;
import algebraics.quadratics.IllDefinedQuadraticInteger;
import algebraics.quadratics.IllDefinedQuadraticRing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests of the ResultsGrouping class.
 * @author Alonso del Arte
 */
public class ResultsGroupingTest {
    
    private static final Random RANDOM = new Random();
    
    private static final IllDefinedQuadraticRing ILL_DEF_QUAD_RING_21 
            = new IllDefinedQuadraticRing(21);
    
    /**
     * Test of the getRing function, of the ResultsGrouping class.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        IllDefinedQuadraticRing expected = new IllDefinedQuadraticRing(-35);
        ResultsGrouping instance = new ResultsGroupingImpl(expected);
        IntegerRing actual = instance.getRing();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getPrimePi function, of the ResultsGrouping class. For a new 
     * instance, the function should return {@link 
     * ResultsGrouping#DEFAULT_PRIME_PI}.
     */
    @Test
    public void testGetPrimePi() {
        ResultsGrouping instance 
                = new ResultsGroupingImpl(ILL_DEF_QUAD_RING_21);
        int expected = ResultsGrouping.DEFAULT_PRIME_PI;
        int actual = instance.getPrimePi();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the raisePrimePi procedure, of the ResultsGrouping class.
     */
    @Test
    public void testRaisePrimePi() {
        System.out.println("raisePrimePi");
        ResultsGrouping instance 
                = new ResultsGroupingImpl(ILL_DEF_QUAD_RING_21);
        int increment = RANDOM.nextInt(720) + 1;
        instance.raisePrimePi(increment);
        int expected = ResultsGrouping.DEFAULT_PRIME_PI + increment;
        int actual = instance.getPrimePi();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the raisePrimePi procedure, of the ResultsGrouping class. 
     * Trying to use a negative number for the increment should either cause an 
     * exception or leave the primePi setting the same.
     */
    @Test
    public void testLowerPrimePiDisallowed() {
        ResultsGrouping instance 
                = new ResultsGroupingImpl(ILL_DEF_QUAD_RING_21);
        int decrement = -RANDOM.nextInt(720) - 1;
        try {
            instance.raisePrimePi(decrement);
            String msg = "Should not have been able to raise pi by " + decrement 
                    + " to " + instance.getPrimePi();
            assertEquals(msg, ResultsGrouping.DEFAULT_PRIME_PI, 
                    instance.getPrimePi());
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to raise pi by " + decrement 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for pi increment " + decrement;
            fail(msg);
        }
    }

    public class ResultsGroupingImpl 
            extends ResultsGrouping<IllDefinedQuadraticInteger> {

        @Override
        public HashSet<IllDefinedQuadraticInteger> inerts() {
            throw new UnsupportedOperationException("Not supported, sorry...");
        }

        @Override
        public HashMap<IllDefinedQuadraticInteger, 
        Optional<IllDefinedQuadraticInteger>> splits() {
            throw new UnsupportedOperationException("Not supported, sorry...");
        }

        @Override
        public HashMap<IllDefinedQuadraticInteger, 
        Optional<IllDefinedQuadraticInteger>> ramifieds() {
            throw new UnsupportedOperationException("Not supported, sorry...");
        }
        
        public ResultsGroupingImpl(IllDefinedQuadraticRing ring) {
            super(ring);
        }

    }
    
}
