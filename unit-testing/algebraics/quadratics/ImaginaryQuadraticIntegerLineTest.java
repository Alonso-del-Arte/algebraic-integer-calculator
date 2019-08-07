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
package algebraics.quadratics;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AL
 */
public class ImaginaryQuadraticIntegerLineTest {
    
    private static ImaginaryQuadraticInteger gaussianLineStart, gaussianLineEnd;
    private static ImaginaryQuadraticIntegerLine gaussianLine;
    
    @BeforeClass
    public static void setUpClass() {
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-1);
        gaussianLineStart = new ImaginaryQuadraticInteger(1, 1, ring);
        gaussianLineEnd = new ImaginaryQuadraticInteger(5, 5, ring);
        gaussianLine = new ImaginaryQuadraticIntegerLine(gaussianLineStart, gaussianLineEnd, gaussianLineStart);
    }
    
    /**
     * Test of length method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        int expResult = 5;
        int result = gaussianLine.length();
        assertEquals(expResult, result);
    }

    /**
     * Test of apply method, of class ImaginaryQuadraticIntegerLine.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        QuadraticInteger expResult = gaussianLineStart;
        ImaginaryQuadraticInteger result;
        for (int index = 1; index < 6; index++) {
            result = gaussianLine.apply(index);
            assertEquals(expResult, result);
            expResult = expResult.plus(gaussianLineStart);
        }
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testApplyOutOfBounds() {
        ImaginaryQuadraticInteger result = gaussianLine.apply(500);
    }
    
}
