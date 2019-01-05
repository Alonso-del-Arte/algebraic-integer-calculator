/*
 * Copyright (C) 2018 Alonso del Arte
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

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the AlgebraicDegreeOverflowException class.
 * @author Alonso del Arte
 */
public class AlgebraicDegreeOverflowExceptionTest {
    
    private static AlgebraicDegreeOverflowException algDegOvflExcImag;
    private static AlgebraicDegreeOverflowException algDegOvflExcReal;
    
    @BeforeClass
    public static void setUpClass() {
        algDegOvflExcImag = new AlgebraicDegreeOverflowException("Initialization state, not the result of an actually thrown exception.", 2, NumberTheoreticFunctionsCalculator.IMAG_UNIT_I, NumberTheoreticFunctionsCalculator.IMAG_UNIT_NEG_I);
        algDegOvflExcReal = new AlgebraicDegreeOverflowException("Initialization state, not the result of an actually thrown exception.", 2, NumberTheoreticFunctionsCalculator.IMAG_UNIT_I, NumberTheoreticFunctionsCalculator.IMAG_UNIT_NEG_I);
        QuadraticRing ring = new ImaginaryQuadraticRing(-2);
        QuadraticInteger numberA = new ImaginaryQuadraticInteger(14, 7, ring);
        ring = new ImaginaryQuadraticRing(-7);
        QuadraticInteger numberB = new ImaginaryQuadraticInteger(7, 1, ring);
        QuadraticInteger sum;
        try {
            sum = numberA.plus(numberB);
            System.out.println(numberA.toASCIIString() + " + " + numberB.toASCIIString() + " = " + sum.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            algDegOvflExcImag = adoe;
        } catch (Exception e) {
            System.out.println("Exception " + e.getClass().getName() + " encountered.");
            System.out.println("\"" + e.getMessage() + "\"");
            System.out.println("Probably all the tests will fail now.");
        }
        System.out.println("AlgebraicDegreeOverflowException for the imaginary quadratic example has this message: \"" + algDegOvflExcImag.getMessage() + "\"");
        ring = new RealQuadraticRing(3);
        numberA = new RealQuadraticInteger(14, 7, ring);
        ring = new RealQuadraticRing(10);
        numberB = new RealQuadraticInteger(7, 1, ring);
        try {
            sum = numberA.plus(numberB);
            System.out.println(numberA.toASCIIString() + " + " + numberB.toASCIIString() + " = " + sum.toASCIIString());
        } catch (AlgebraicDegreeOverflowException adoe) {
            algDegOvflExcReal = adoe;
        } catch (Exception e) {
            System.out.println("Exception " + e.getClass().getName() + " encountered.");
            System.out.println("\"" + e.getMessage() + "\"");
            System.out.println("Probably all the tests will fail now.");
        }
        System.out.println("AlgebraicDegreeOverflowException for the real quadratic example has this message: \"" + algDegOvflExcReal.getMessage() + "\"");
    }
    
    /**
     * Test of getMaxExpectedAlgebraicDegree method, of class 
     * AlgebraicDegreeOverflowException.
     */
    @Test
    public void testGetMaxExpectedAlgebraicDegree() {
        System.out.println("getMaxExpectedAlgebraicDegree");
        int expAlgDeg = 2;
        assertEquals(expAlgDeg, algDegOvflExcImag.getMaxExpectedAlgebraicDegree());
        assertEquals(expAlgDeg, algDegOvflExcReal.getMaxExpectedAlgebraicDegree());
    }

    /**
     * Test of getNecessaryAlgebraicDegree method, of class 
     * AlgebraicDegreeOverflowException.
     */
    @Test
    public void testGetNecessaryAlgebraicDegree() {
        System.out.println("getNecessaryAlgebraicDegree");
        int expAlgDeg = 4;
        assertEquals(expAlgDeg, algDegOvflExcImag.getNecessaryAlgebraicDegree());
        assertEquals(expAlgDeg, algDegOvflExcReal.getNecessaryAlgebraicDegree());
    }

    /**
     * Test of getCausingNumbers method, of class 
     * AlgebraicDegreeOverflowException.
     */
    @Test
    public void testGetCausingNumbers() {
        System.out.println("getCausingNumbers");
        QuadraticRing ring = new ImaginaryQuadraticRing(-2);
        QuadraticInteger numberA = new ImaginaryQuadraticInteger(14, 7, ring);
        ring = new ImaginaryQuadraticRing(-7);
        QuadraticInteger numberB = new ImaginaryQuadraticInteger(7, 1, ring);
        AlgebraicInteger[] expResult = new AlgebraicInteger[]{numberA, numberB};
        AlgebraicInteger[] result = algDegOvflExcImag.getCausingNumbers();
        assertArrayEquals(expResult, result);
        ring = new RealQuadraticRing(3);
        numberA = new RealQuadraticInteger(14, 7, ring);
        ring = new RealQuadraticRing(10);
        numberB = new RealQuadraticInteger(7, 1, ring);
        expResult = new AlgebraicInteger[]{numberA, numberB};
        result = algDegOvflExcReal.getCausingNumbers();
        assertArrayEquals(expResult, result);
    }
    
}
