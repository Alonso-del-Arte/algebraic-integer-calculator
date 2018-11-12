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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the class QuadraticInteger. Since this is an abstract class and the 
 * test classes for the derived classes have a lot of the pertinent tests, this 
 * test class is concerned mainly with making sure that {@link 
 * UnsupportedNumberDomainException} is thrown when appropriate.
 * @author Alonso del Arte
 */
public class QuadraticIntegerTest {
    
    private static final int R_D = 21;
    private static final int R_A = 5;
    private static final int R_B = 2;
    
    private static final IllDefinedQuadraticRing ILL_DEF_RING = new IllDefinedQuadraticRing(R_D);
    private static final QuadraticInteger ILL_DEF_INT_A = new IllDefinedQuadraticInteger(R_A, R_B, ILL_DEF_RING);
    private static final QuadraticInteger ILL_DEF_INT_B = new IllDefinedQuadraticInteger(R_B, -R_A, ILL_DEF_RING);

    /**
     * Test of conjugate method of class QuadraticInteger. Testing that trying 
     * to take the conjugate of a quadratic integer from an unsupported ring 
     * triggers {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testConjugateUnsupportedCausesException() {
        System.out.println("Testing that conjugate on an unsupported quadratic integer causes the appropriate exception");
        QuadraticInteger conj;
        try {
            conj = ILL_DEF_INT_A.conjugate();
            System.out.println("Trying to get conjugate of " + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " + conj.toASCIIString() + ".");
            fail("Trying to get conjugate of an ill-defined quadratic integer should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted conjugate.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        try {
            conj = ILL_DEF_INT_B.conjugate();
            System.out.println("Trying to get conjugate of " + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " + conj.toASCIIString() + ".");
            fail("Trying to get conjugate of an ill-defined quadratic integer should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted conjugate.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
        
    /**
     * Test of plus method of class QuadraticInteger. Testing that adding two 
     * quadratic integers from an unsupported quadratic ring correctly triggers 
     * {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testPlusUnsupportedCausesException() {
        System.out.println("Testing that plus on unsupported quadratic integers causes the appropriate exception");
        QuadraticInteger sum;
        try {
            sum = ILL_DEF_INT_A.plus(ILL_DEF_INT_B);
            System.out.println("Trying to add " + ILL_DEF_INT_A.toASCIIString() + " to " + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " + sum.toASCIIString() + ".");
            fail("Trying to add two ill-defined quadratic integers should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted addition.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        try {
            sum = ILL_DEF_INT_B.plus(ILL_DEF_INT_A);
            System.out.println("Trying to add " + ILL_DEF_INT_B.toASCIIString() + " to " + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " + sum.toASCIIString() + ".");
            fail("Trying to add two ill-defined quadratic integers should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted addition.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of minus method of class QuadraticInteger. Testing that subtracting 
     * one quadratic integers from another quadratic integer in an unsupported 
     * quadratic ring correctly triggers {@link 
     * UnsupportedNumberDomainException}.
     */
    @Test
    public void testMinusUnsupportedCausesException() {
        System.out.println("Testing that minus on unsupported quadratic integers causes the appropriate exception");
        QuadraticInteger subtraction;
        try {
            subtraction = ILL_DEF_INT_A.minus(ILL_DEF_INT_B);
            System.out.println("Trying to subtract " + ILL_DEF_INT_B.toASCIIString() + " from " + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " + subtraction.toASCIIString() + ".");
            fail("Trying to subtract one ill-defined quadratic integer from another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted subtraction.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        try {
            subtraction = ILL_DEF_INT_B.minus(ILL_DEF_INT_A);
            System.out.println("Trying to subtract " + ILL_DEF_INT_A.toASCIIString() + " from " + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " + subtraction.toASCIIString() + ".");
            fail("Trying to subtract one ill-defined quadratic integer from another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted subtraction.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * two quadratic integers from an unsupported quadratic ring correctly 
     * triggers {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testTimesUnsupportedCausesException() {
        System.out.println("Testing that times on unsupported quadratic integers causes the appropriate exception");
        QuadraticInteger product;
        try {
            product = ILL_DEF_INT_A.times(ILL_DEF_INT_B);
            System.out.println("Trying to multiply " + ILL_DEF_INT_A.toASCIIString() + " by " + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " + product.toASCIIString() + ".");
            fail("Trying to multiply two ill-defined quadratic integers should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted multiplication.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        try {
            product = ILL_DEF_INT_B.times(ILL_DEF_INT_A);
            System.out.println("Trying to multiply " + ILL_DEF_INT_B.toASCIIString() + " by " + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " + product.toASCIIString() + ".");
            fail("Trying to multiply two ill-defined quadratic integers should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted multiplication.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * a purely imaginary quadratic integer by a real quadratic integer with no 
     * rational part results in the appropriate purely imaginary quadratic 
     * integer. For example, &radic;&minus;2 &times; &radic;5 = 
     * &radic;&minus;10.
     */
    @Test
    public void testTimesCrossDomain() {
        System.out.println("Testing that times can correctly go from real to imaginary domain and back when appropriate");
        ImaginaryQuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        ImaginaryQuadraticInteger multiplicandA = new ImaginaryQuadraticInteger(0, 1, multiplicandARing);
        RealQuadraticRing multiplicandBRing = new RealQuadraticRing(5);
        RealQuadraticInteger multiplicandB = new RealQuadraticInteger(0, 1, multiplicandBRing);
        ImaginaryQuadraticRing expResultRing = new ImaginaryQuadraticRing(-10);
        ImaginaryQuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 1, expResultRing);
        QuadraticInteger result = null;
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " + multiplicandB.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandA.toASCIIString() + " by " + multiplicandB.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandA.toASCIIString() + " by " + multiplicandB.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(expResult, result);
        // Commutative check
        try {
            result = multiplicandB.times(multiplicandA);
            System.out.println(multiplicandB.toASCIIString() + " times " + multiplicandA.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandB.toASCIIString() + " by " + multiplicandA.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandB.toASCIIString() + " by " + multiplicandA.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(expResult, result);
    }
    
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * two purely imaginary quadratic integers results in the appropriate real 
     * quadratic integer with no rational part. For example, &radic;&minus;2 
     * &times; &radic;&minus;5 = &radic;10.
     */
    @Test
    public void testTimesCrossDomainResult() {
        System.out.println("Testing that times can correctly go from real to imaginary domain and back when appropriate");
        ImaginaryQuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        ImaginaryQuadraticInteger multiplicandA = new ImaginaryQuadraticInteger(0, 1, multiplicandARing);
        ImaginaryQuadraticRing multiplicandBRing = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticInteger multiplicandB = new ImaginaryQuadraticInteger(0, 1, multiplicandBRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(10);
        RealQuadraticInteger expResult = new RealQuadraticInteger(0, 1, expResultRing);
        QuadraticInteger result = null;
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " + multiplicandB.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandA.toASCIIString() + " by " + multiplicandB.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandA.toASCIIString() + " by " + multiplicandB.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(expResult, result);
        // Commutative check
        try {
            result = multiplicandB.times(multiplicandA);
            System.out.println(multiplicandB.toASCIIString() + " times " + multiplicandA.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandB.toASCIIString() + " by " + multiplicandA.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandB.toASCIIString() + " by " + multiplicandA.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(expResult, result);
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing 
     * one quadratic integers by another quadratic integer in an unsupported 
     * quadratic ring correctly triggers {@link 
     * UnsupportedNumberDomainException}.
     */
    @Test
    public void testDividesUnsupportedCausesException() {
        System.out.println("Testing that divides on unsupported quadratic integers causes the appropriate exception");
        QuadraticInteger division;
        try {
            division = ILL_DEF_INT_A.divides(ILL_DEF_INT_B);
            System.out.println("Trying to divide " + ILL_DEF_INT_A.toASCIIString() + " by " + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " + division.toASCIIString() + ".");
            fail("Trying to divide one ill-defined quadratic integer by another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted division.");
            System.out.println("Message: " + unde.getMessage());
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException is not appropriate in this context: " + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        try {
            division = ILL_DEF_INT_B.divides(ILL_DEF_INT_A);
            System.out.println("Trying to divide " + ILL_DEF_INT_B.toASCIIString() + " by " + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " + division.toASCIIString() + ".");
            fail("Trying to divide one ill-defined quadratic integer by another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted division.");
            System.out.println("Message: " + unde.getMessage());
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException is not appropriate in this context: " + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing 
     * a purely imaginary quadratic integer by a real quadratic integer with no 
     * rational part, or vice-versa, results in the appropriate purely imaginary 
     * quadratic integer. For example, &radic;&minus;10 &divide; &radic;5 = 
     * &radic;&minus;2.
     */
    @Test
    public void testDividesCrossDomain() {
        System.out.println("Testing that divides can correctly go from real to imaginary domain and back when appropriate");
        ImaginaryQuadraticRing dividendRing = new ImaginaryQuadraticRing(-10);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(0, 1, dividendRing);
        RealQuadraticRing divisorRing = new RealQuadraticRing(5);
        RealQuadraticInteger divisor = new RealQuadraticInteger(0, 1, divisorRing);
        ImaginaryQuadraticRing expResultRing = new ImaginaryQuadraticRing(-2);
        ImaginaryQuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 1, expResultRing);
        QuadraticInteger result = null;
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " times " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(expResult, result);
        // Now to check dividend divided by expResult is divisor
        try {
            result = dividend.divides(expResult);
            System.out.println(dividend.toASCIIString() + " times " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(divisor, result);
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing 
     * a purely imaginary quadratic integer by another results in a real 
     * quadratic integer with no rational part. For example, &radic;&minus;10 
     * &divide; &radic;&minus;5 = &radic;2.
     */
    @Test
    public void testDividesCrossDomainResult() {
        System.out.println("Testing that divides can correctly go from real to imaginary domain and back when appropriate");
        ImaginaryQuadraticRing dividendRing = new ImaginaryQuadraticRing(-10);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(0, 1, dividendRing);
        ImaginaryQuadraticRing divisorRing = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(0, 1, divisorRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(2);
        RealQuadraticInteger expResult = new RealQuadraticInteger(0, 1, expResultRing);
        QuadraticInteger result = null;
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " times " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(expResult, result);
        // Now to check dividend divided by expResult is divisor
        try {
            result = dividend.divides(expResult);
            System.out.println(dividend.toASCIIString() + " times " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        assertEquals(divisor, result);
    }
    
}
