/*
 * Copyright (C) 2023 Alonso del Arte
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

import algebraics.quadratics.IllDefinedQuadraticInteger;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the AlgebraicDegreeOverflowException class.
 * @author Alonso del Arte
 */
public class AlgebraicDegreeOverflowExceptionTest {
    
    private static final String DEFAULT_MESSAGE = "FOR TESTING PURPOSES ONLY";
    
    /**
     * Test of the getMaxExpectedAlgebraicDegree function, of the 
     * AlgebraicDegreeOverflowException class.
     */
    @Test
    public void testGetMaxExpectedAlgebraicDegree() {
        System.out.println("getMaxExpectedAlgebraicDegree");
        int expected = randomNumber(128) + 2;
        BadRing ring = new BadRing(expected);
        AlgebraicInteger numberA = new BadInteger(ring);
        AlgebraicInteger numberB = new BadInteger(ring);
        AlgebraicDegreeOverflowException exc 
                = new AlgebraicDegreeOverflowException(DEFAULT_MESSAGE, 
                        expected, numberA, numberB);
        int actual = exc.getMaxExpectedAlgebraicDegree();
        assertEquals(expected, actual);
    }

    /**
     * Test of getNecessaryAlgebraicDegree method, of class 
     * AlgebraicDegreeOverflowException.
     */@org.junit.Ignore
    @Test
    public void testGetNecessaryAlgebraicDegree() {
        System.out.println("getNecessaryAlgebraicDegree");
        fail("REWRITE THIS TEST");
//        int expAlgDeg = 4;
//        assertEquals(expAlgDeg, algDegOvflExcImag.getNecessaryAlgebraicDegree());
//        assertEquals(expAlgDeg, algDegOvflExcReal.getNecessaryAlgebraicDegree());
    }

    /**
     * Test of getCausingNumbers method, of class 
     * AlgebraicDegreeOverflowException.
     */@org.junit.Ignore
    @Test
    public void testGetCausingNumbers() {
        System.out.println("getCausingNumbers");
        fail("REWRITE THIS TEST");
//        QuadraticRing ring = new ImaginaryQuadraticRing(-2);
//        QuadraticInteger numberA = new ImaginaryQuadraticInteger(14, 7, ring);
//        ring = new ImaginaryQuadraticRing(-7);
//        QuadraticInteger numberB = new ImaginaryQuadraticInteger(7, 1, ring);
//        AlgebraicInteger[] expResult = new AlgebraicInteger[]{numberA, numberB};
//        AlgebraicInteger[] result = algDegOvflExcImag.getCausingNumbers();
//        assertArrayEquals(expResult, result);
//        ring = new RealQuadraticRing(3);
//        numberA = new RealQuadraticInteger(14, 7, ring);
//        ring = new RealQuadraticRing(10);
//        numberB = new RealQuadraticInteger(7, 1, ring);
//        expResult = new AlgebraicInteger[]{numberA, numberB};
//        result = algDegOvflExcReal.getCausingNumbers();
//        assertArrayEquals(expResult, result);
    }
    
}
