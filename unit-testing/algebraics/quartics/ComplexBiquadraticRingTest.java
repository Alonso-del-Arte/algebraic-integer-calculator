/*
 * Copyright (C) 2019 Alonso del Arte
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

import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.RealQuadraticRing;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class ComplexBiquadraticRingTest {
    
    private final ImaginaryQuadraticRing ringA = new ImaginaryQuadraticRing(-2);
    private final RealQuadraticRing ringB = new RealQuadraticRing(7);
    private final ImaginaryQuadraticRing ringC = new ImaginaryQuadraticRing(-10);
    
    /**
     * Test of isPurelyReal method, of class ComplexBiquadraticRing.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        ComplexBiquadraticRing testRing = new ComplexBiquadraticRing(ringA, ringB);
        assertFalse(testRing.isPurelyReal());
    }
    
    /**
     * Test of discriminant method, of class ComplexBiquadraticRing, inherited 
     * from {@link BiquadraticRing}.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        ComplexBiquadraticRing testRing = new ComplexBiquadraticRing(ringA, ringB);
        assertEquals(1600, testRing.discriminant());
    }

    /**
     * Test of equals method, of class ComplexBiquadraticRing. Regardless of 
     * which two of the three intermediate rings are used to instantiate, if two 
     * rings are equal they should register as equal.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ComplexBiquadraticRing testRing = new ComplexBiquadraticRing(ringA, ringB);
        ComplexBiquadraticRing sameRing = new ComplexBiquadraticRing(ringA, ringC);
        assertEquals(testRing, sameRing);
        sameRing = new ComplexBiquadraticRing(ringC, ringB);
        assertEquals(testRing, sameRing);
        RealQuadraticRing intermediateForDiffRing = new RealQuadraticRing(3);
        ComplexBiquadraticRing diffRing = new ComplexBiquadraticRing(ringA, intermediateForDiffRing);
        assertNotEquals(testRing, diffRing);
    }
    
    /**
     * Test of the constructor of class ComplexBiquadraticRing.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        try {
            ComplexBiquadraticRing badRing = new ComplexBiquadraticRing(ringA, ringA);
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
