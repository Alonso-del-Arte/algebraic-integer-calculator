/*
 * Copyright (C) 2019 AL
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
package algebraics.quartics;

import algebraics.quadratics.RealQuadraticRing;
import algebraics.quadratics.QuadraticRing;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the PurelyRealBiquadraticRing class.
 * @author Alonso del Arte
 */
public class PurelyRealBiquadraticRingTest {
    
    private final RealQuadraticRing ringA = new RealQuadraticRing(2);
    private final RealQuadraticRing ringB = new RealQuadraticRing(7);
    private final RealQuadraticRing ringC = new RealQuadraticRing(14);
    
    /**
     * Test of getIntermediateRings method, of class PurelyRealBiquadraticRing.
     */
    @Test
    public void testGetIntermediateRings() {
        System.out.println("getIntermediateRings()");
        RealQuadraticRing[] expResult = {ringA, ringB, ringC};
        PurelyRealBiquadraticRing testRing = new PurelyRealBiquadraticRing(ringA, ringB);
        QuadraticRing[] result = testRing.getIntermediateRings();
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of isPurelyReal method, of class PurelyRealBiquadraticRing.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        PurelyRealBiquadraticRing testRing = new PurelyRealBiquadraticRing(ringA, ringB);
        assertTrue(testRing.isPurelyReal());
    }
    
    /**
     * Test of discriminant method, of class PurelyRealBiquadraticRing, 
     * inherited from {@link BiquadraticRing}.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        PurelyRealBiquadraticRing testRing = new PurelyRealBiquadraticRing(ringA, ringB);
        assertEquals(12544, testRing.discriminant());
    }
    
    /**
     * Test of equals method, of class PurelyRealBiquadraticRing. Regardless of 
     * which two of the three intermediate rings are used to instantiate, if two 
     * rings are equal they should register as equal.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        PurelyRealBiquadraticRing testRing = new PurelyRealBiquadraticRing(ringA, ringB);
        PurelyRealBiquadraticRing sameRing = new PurelyRealBiquadraticRing(ringA, ringC);
        assertEquals(testRing, sameRing);
        sameRing = new PurelyRealBiquadraticRing(ringB, ringC);
        assertEquals(testRing, sameRing);
        RealQuadraticRing intermediateForDiffRing = new RealQuadraticRing(3);
        PurelyRealBiquadraticRing diffRing = new PurelyRealBiquadraticRing(ringA, intermediateForDiffRing);
        assertNotEquals(testRing, diffRing);
    }
    
    /**
     * Test of hashCode method, of class PurelyRealBiquadraticRing.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        PurelyRealBiquadraticRing testRing = new PurelyRealBiquadraticRing(ringA, ringB);
        PurelyRealBiquadraticRing sameRing = new PurelyRealBiquadraticRing(ringA, ringC);
        assertEquals(testRing.hashCode(), sameRing.hashCode());
        sameRing = new PurelyRealBiquadraticRing(ringB, ringC);
        assertEquals(testRing.hashCode(), sameRing.hashCode());
        RealQuadraticRing intermediateForDiffRing = new RealQuadraticRing(3);
        PurelyRealBiquadraticRing diffRing = new PurelyRealBiquadraticRing(ringA, intermediateForDiffRing);
        assertNotEquals(testRing.hashCode(), diffRing.hashCode());
    }
    
    /**
     * Test of the constructor of class ComplexBiquadraticRing.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        try {
            PurelyRealBiquadraticRing badRing = new PurelyRealBiquadraticRing(ringA, ringA);
            String failMsg = "Should not have been able to create " + badRing.toString() + " using " + ringA.toString() + " twice";
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + ringA.toASCIIString() + " twice to create biquadratic ring correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception to throw for trying to use " + ringA.toASCIIString() + " twice to create biquadratic ring";
            fail(failMsg);
        }
    }

}
