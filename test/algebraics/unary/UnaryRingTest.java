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
package algebraics.unary;

import arithmetic.PowerBasis;
import fractions.Fraction;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the UnaryRing class.
 * @author Alonso del Arte
 */
public class UnaryRingTest {
    
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        assertEquals(1, UnaryRing.Z.getMaxAlgebraicDegree());
    }
    
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        assert UnaryRing.Z.isPurelyReal() : "Z should be purely real";
    }
    
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        assertEquals(1, UnaryRing.Z.discriminant());
    }
    
    // TODO: Write tests for discriminant(), getPowerBasis()
    
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        assertEquals("Z", UnaryRing.Z.toASCIIString());
    }
    
}
