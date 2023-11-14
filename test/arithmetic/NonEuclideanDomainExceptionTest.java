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
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import java.util.HashSet;
import java.util.Set;

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
     * Test of the getEuclideanGCDAttemptedNumbers function, of the 
     * NonEuclideanDomainException class.
     */
    @Test
    public void testGetEuclideanGCDAttemptedNumbers() {
        System.out.println("getEuclideanGCDAttemptedNumbers");
        QuadraticRing ring = new ImaginaryQuadraticRing(-5);
        QuadraticInteger a = new ImaginaryQuadraticInteger(randomNumber(128), 1, 
                ring);
        QuadraticInteger b = a.times(a).plus(1);
        String message = "FOR TESTING PURPOSES ONLY";
        NonEuclideanDomainException exc 
                = new NonEuclideanDomainException(message, a, b);
        AlgebraicInteger[] expNumbers = {a, b};
        Set<AlgebraicInteger> expected = Set.of(expNumbers);
        Set<AlgebraicInteger> actual 
                = Set.of(exc.getEuclideanGCDAttemptedNumbers());
        assertEquals(expected, actual);
    }
    
}
