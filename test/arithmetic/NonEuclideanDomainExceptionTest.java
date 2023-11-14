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
package arithmetic;

import algebraics.AlgebraicInteger;
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
 * Tests for the NonEuclideanDomainException class. The purpose of this test 
 * class is only to make sure the exception object works as it should. Testing 
 * whether this exception is thrown for the right reasons or not is the 
 * responsibility of other test classes.
 * @author Alonso del Arte
 */
public class NonEuclideanDomainExceptionTest {
    
    /**
     * Test of getEuclideanGCDAttemptedNumbers method, of class 
     * NonEuclideanDomainException.
     */
    @Test
    public void testGetEuclideanGCDAttemptedNumbers() {
        System.out.println("getEuclideanGCDAttemptedNumbers");
        fail("REWRITE THIS TEST");
//        QuadraticInteger iqia = new ImaginaryQuadraticInteger(2, 0, RING_ZI5);
//        QuadraticInteger iqib = new ImaginaryQuadraticInteger(1, 1, RING_ZI5);
//        QuadraticInteger[] expResult = new QuadraticInteger[]{iqia, iqib};
//        AlgebraicInteger[] result = nonEuclExc6.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new ImaginaryQuadraticInteger(29, 0, RING_ZI5);
//        iqib = new ImaginaryQuadraticInteger(-7, 5, RING_ZI5);
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc29.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new ImaginaryQuadraticInteger(0, 11, RING_ZI5);
//        iqib = new ImaginaryQuadraticInteger(0, 13, RING_ZI5);
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc143A.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new ImaginaryQuadraticInteger(0, 11, RING_ZI5);
//        try {
//            iqib = iqib.divides(ZI5_RAMIFIER);
//        } catch (NotDivisibleException nde) {
//            System.out.println("NotDivisibleException \"" + nde.getMessage() + "\" should not have occurred.");
//            fail("Tests for NotDivisibleException may need review.");
//        }
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc143B.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new ImaginaryQuadraticInteger(10, 0, RING_OQI19);
//        iqib = new ImaginaryQuadraticInteger(3, 1, RING_OQI19, 2);
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc700.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new RealQuadraticInteger(2, 0, RING_Z14);
//        iqib = new RealQuadraticInteger(1, 1, RING_Z14);
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc13.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new RealQuadraticInteger(0, 39, RING_Z14);
//        iqib = new RealQuadraticInteger(-40, 12, RING_Z14);
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc39.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
//        iqia = new RealQuadraticInteger(18, 2, RING_OQ69);
//        iqib = new RealQuadraticInteger(23, 3, RING_OQ69, 2);
//        expResult = new QuadraticInteger[]{iqia, iqib};
//        result = nonEuclExc48.getEuclideanGCDAttemptedNumbers();
//        assertArrayEquals(expResult, result);
    }
    
}
