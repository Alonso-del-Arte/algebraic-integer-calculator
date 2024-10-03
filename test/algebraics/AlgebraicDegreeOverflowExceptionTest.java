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

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        MockRing ring = new MockRing(expected);
        AlgebraicInteger numberA = new MockInteger(ring);
        AlgebraicInteger numberB = new MockInteger(ring);
        AlgebraicDegreeOverflowException exc 
                = new AlgebraicDegreeOverflowException(DEFAULT_MESSAGE, 
                        expected, numberA, numberB);
        int actual = exc.getMaxExpectedAlgebraicDegree();
        assertEquals(expected, actual);
    }

    /**
     * Test of the getNecessaryAlgebraicDegree function, of the  
     * AlgebraicDegreeOverflowException class. The idea here is, though I 
     * haven't verified this is mathematically correct, that the necessary 
     * degree is the product of the maximum degrees of the classes of the 
     * causing numbers.
     */
    @Test
    public void testGetNecessaryAlgebraicDegree() {
        System.out.println("getNecessaryAlgebraicDegree");
        int degreeA = randomNumber(128) + 2;
        int degreeB = randomNumber(128) + 2;
        MockRing ringA = new MockRing(degreeA);
        MockRing ringB = new MockRing(degreeB);
        MockInteger numberA = new MockInteger(ringA);
        MockInteger numberB = new MockInteger(ringB);
        AlgebraicDegreeOverflowException exc 
                = new AlgebraicDegreeOverflowException(DEFAULT_MESSAGE, degreeA, 
                        numberA, numberB);
        int expected = degreeA * degreeB;
        int actual = exc.getNecessaryAlgebraicDegree();
        assertEquals(expected, actual);
    }

    /**
     * Test of getCausingNumbers method, of class 
     * AlgebraicDegreeOverflowException.
     */
    @Test
    public void testGetCausingNumbers() {
        System.out.println("getCausingNumbers");
        int degreeA = randomNumber(128) + 2;
        int degreeB = randomNumber(128) + 2;
        MockRing ringA = new MockRing(degreeA);
        MockRing ringB = new MockRing(degreeB);
        MockInteger numberA = new MockInteger(ringA);
        MockInteger numberB = new MockInteger(ringB);
        AlgebraicDegreeOverflowException exc 
                = new AlgebraicDegreeOverflowException(DEFAULT_MESSAGE, degreeA, 
                        numberA, numberB);
        AlgebraicInteger[] array = {numberA, numberB};
        Set<AlgebraicInteger> expected = new HashSet<>(Arrays.asList(array));
        Set<AlgebraicInteger> actual 
                = new HashSet<>(Arrays.asList(exc.getCausingNumbers()));
        assertEquals(expected, actual);
    }
    
}
