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
package arithmetic;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import fractions.Fraction;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the NotDivisibleException class. The purpose of this test class is 
 * only to make sure the exception object works as it should. Testing whether 
 * this exception is thrown for the right reasons or not is the responsibility 
 * of other test classes. However, these tests do require that the division 
 * function in the pertinent classes satisfies the requirements of the 
 * NotDivisibleException class. 
 * @author Alonso del Arte
 */
public class NotDivisibleExceptionTest {
    
    /**
     * The delta value to use when assertEquals() requires a delta value.
     */
    public static final double TEST_DELTA = 0.00000001;
    
    /**
     * An exception to correspond to (5 + i)/(3 + i) after setUpClass().
     */
    private static NotDivisibleException notDivGaussian;

    /**
     * An exception to correspond to 61/(1 + 9sqrt(-3)) after setUpClass().
     */
    private static NotDivisibleException notDivEisenstein;
    
    /**
     * An exception to correspond to (3 - 8sqrt(2))/7 after setUpClass().
     */
    private static NotDivisibleException notDivZ2;
    
    /**
     * An exception to correspond to (sqrt(5))/(15/2 - 3sqrt(5)/2) after 
     * setUpClass().
     */
    private static NotDivisibleException notDivZPhi;

    /**
     * Test of getFractions method, of class NotDivisibleException.
     */
    @Test
    public void testGetFractions() {
        fail("REWRITE THIS TEST");
        System.out.println("getFractions");
    }
        
    /**
     * Test of getCausingDividend method, of class NotDivisibleException.
     */
    @Test
    public void testGetCausingDividend() {
        fail("REWRITE THIS TEST");
        System.out.println("getCausingDividend");
    }

    /**
     * Test of getCausingDividend method, of class NotDivisibleException.
     */
    @Test
    public void testGetCausingDivisor() {
        fail("REWRITE THIS TEST");
        System.out.println("getCausingDivisor");
    }

    /**
     * Test of getCausingRing method, of class NotDivisibleException.
     */
    @Test
    public void testGetCausingRing() {
        fail("REWRITE THIS TEST");
        System.out.println("getCausingRing");
    }
    
    /**
     * Another test of getCausingRing method, of class NotDivisibleException. 
     * This one checks that the correct ring can be inferred if the dividend and 
     * divisor are of different rings, and the applicable radicands are coprime.
     */
    @Test
    public void testGetCausingRingInferredCoprime() {
        fail("REWRITE THIS TEST");
    }

    /**
     * Another test of getCausingRing method, of class NotDivisibleException. 
     * This one checks that the correct ring can be inferred if the dividend and 
     * divisor are of different rings, and the applicable radicands have a 
     * common divisor greater than 1.
     */
    @Test
    public void testGetCausingRingInferredCommonDivisor() {
        fail("REWRITE THIS TEST");
    }

    /**
     * Test of getNumericRealPart method, of class NotDivisibleException.
     */
    @Test
    public void testGetNumericRealPart() {
        fail("REWRITE THIS TEST");
        System.out.println("getNumericRealPart");
    }

    /**
     * Test of getNumericImagPart method, of class NotDivisibleException.
     */
    @Test
    public void testGetNumericImagPart() {
        fail("REWRITE THIS TEST");
        System.out.println("getNumericImagPart");
    }

    /**
     * Test of getAbs method, of class NotDivisibleException.
     */
    @Test
    public void testGetAbs() {
        fail("REWRITE THIS TEST");
        System.out.println("getAbs");
    }

    private void sortAlgIntArray(AlgebraicInteger[] algIntArray) {
        AlgebraicInteger swapper;
        boolean swapFlag;
        do {
            swapFlag = false;
            for (int i = 0; i < algIntArray.length - 1; i++) {
                if (algIntArray[i].norm() > algIntArray[i + 1].norm()) {
                    swapper = algIntArray[i];
                    algIntArray[i] = algIntArray[i + 1];
                    algIntArray[i + 1] = swapper;
                    swapFlag = true;
                }
            }
        } while (swapFlag);
    }
    
    /**
     * Test of getBoundingIntegers method, of class NotDivisibleException.
     */
    @Test
    public void testGetBoundingIntegers() {
        fail("REWRITE THIS TEST");
        System.out.println("getBoundingIntegers");
    }

    /**
     * Test of roundTowardsZero method, of class NotDivisibleException. In the 
     * case of real quadratic integers, I've gone back and forth on what the 
     * result of this function should be. For now, that part of the test is 
     * inactive.
     */
    @Test
    public void testRoundTowardsZero() {
        fail("REWRITE THIS TEST");
        System.out.println("roundTowardsZero");
    }

    /**
     * Test of roundAwayFromZero method, of class NotDivisibleException. In the 
     * case of real quadratic integers, I've gone back and forth on what the 
     * result of this function should be. For now, that part of the test is 
     * inactive.
     */
    @Test
    public void testRoundAwayFromZero() {
        fail("REWRITE THIS TEST");
        System.out.println("roundAwayFromZero");
    }
    
    /**
     * Test of the auxiliary NotDivisibleException constructor. This only tests 
     * that the exception message is correctly inferred, using {@link 
     * algebraics.quadratics.QuadraticInteger#toASCIIString()} (which is assumed 
     * to have been properly tested in the relevant test classes).
     */
    @Test
    public void testAuxiliaryConstructor() {
        fail("REWRITE THIS TEST");
    }
    
    /**
     * Test of the NotDivisibleException constructor. The only thing we're 
     * testing here is that the constructor throws IllegalArgumentException if 
     * passed an array of fractions with a length that doesn't match the 
     * algebraic degree of the pertinent ring.
     */
    @Test
    public void testConstructor() {
        fail("REWRITE THIS TEST");
    }
    
}
