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

import java.util.Optional;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ResultsCache class.
 * @author Alonso del Arte
 */
public class ResultsCacheTest {
    
    private static final RealQuadraticRing RING_OQ21 
            = new RealQuadraticRing(21);
    
    /**
     * Test of the getUnit function, of the ResultsCache class.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        ResultsCache cache = new ResultsCache(RING_OQ21);
        RealQuadraticInteger unit = new RealQuadraticInteger(5, 1, RING_OQ21, 
                2);
        Optional<RealQuadraticInteger> expected = Optional.of(unit);
        Optional<RealQuadraticInteger> actual = cache.getUnit();
        assertEquals(expected, actual);
    }

    /**
     * Test of the getClassNumber function, of the ResultsCache class.
     */
    @Test
    public void testGetClassNumber() {
        System.out.println("getClassNumber");
        ResultsCache cache = new ResultsCache(RING_OQ21);
        Optional<Integer> expected = Optional.of(1);
        Optional<Integer> actual = cache.getClassNumber();
        assertEquals(expected, actual);
    }

    /**
     * Test of the mainSplitter function, of the ResultsCache class.
     */
    @Test
    public void testMainSplitter() {
        System.out.println("mainSplitter");
        RealQuadraticRing ring = new RealQuadraticRing(57);
        ResultsCache cache = new ResultsCache(ring);
        int num = 7;
        RealQuadraticInteger splitter = new RealQuadraticInteger(8, 1, ring);
        Optional<RealQuadraticInteger> expected = Optional.of(splitter);
        Optional<RealQuadraticInteger> actual = cache.mainSplitter(num);
        assertEquals(expected, actual);
    }
    
    @Test(timeout = 5000)
    public void testSplitterGetsCached() {
        fail("Haven't written test yet");
    }
    
}
