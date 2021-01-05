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
package algebraics.quadratics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.NotDivisibleException;
import algebraics.UnsupportedNumberDomainException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the class QuadraticInteger. Since this is an abstract class and the 
 * test classes for the derived classes have a lot of the pertinent tests, this 
 * test class is concerned mainly with making sure that {@link 
 * UnsupportedNumberDomainException} is thrown when appropriate, and that cross-
 * domain results are given when appropriate.
 * @author Alonso del Arte
 */
public class QuadraticIntegerTest {
    
    // TODO: Write arithmetic overflow tests
    
    private static final int R_D = 21;
    private static final int R_A = 5;
    private static final int R_B = 2;
    private static final int R_C_REG = -830;
    private static final int R_C_SURD = -21;
    
    private static final IllDefinedQuadraticRing ILL_DEF_RING 
            = new IllDefinedQuadraticRing(R_D);
    private static final QuadraticInteger ILL_DEF_INT_A 
            = new IllDefinedQuadraticInteger(R_A, R_B, ILL_DEF_RING);
    private static final QuadraticInteger ILL_DEF_INT_B 
            = new IllDefinedQuadraticInteger(R_B, -R_A, ILL_DEF_RING);
    private static final QuadraticInteger ILL_DEF_INT_C 
            = new IllDefinedQuadraticInteger(R_C_REG, R_C_SURD, ILL_DEF_RING);

    /**
     * Another test of the trace function of class QuadraticInteger. Although 
     * (&minus;2)<sup>31</sup> is the lowest value an <code>int</code> can have, 
     * as the "regular" part of a quadratic integer it should not cause an 
     * overflow for the trace function, since the return type is 
     * <code>long</code>, which can represent &minus;(2<sup>32</sup>) easily. 
     * So, for example, the trace of &minus;2147483648 &minus; &radic;70 is 
     * &minus;4294967296. But if the function returns 0 instead, there is a 
     * problem that needs correcting.
     */
    @Test
    public void testTraceEdgeCasesLow() {
        QuadraticRing ring = new ImaginaryQuadraticRing(-43);
        QuadraticInteger num = QuadraticInteger.apply(Integer.MIN_VALUE, 1, 
                ring);
        final long expected = 2L * Integer.MIN_VALUE;
        long actual = num.trace();
        assertEquals(expected, actual);
        ring = new RealQuadraticRing(70);
        num = QuadraticInteger.apply(Integer.MIN_VALUE, -1, ring);
        actual = num.trace();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the trace function of class QuadraticInteger. Although 
     * 2<sup>31</sup> &minus; 1 is the greatest value an <code>int</code> can 
     * have, as the "regular" part of a quadratic integer it should not cause an 
     * overflow for the trace function, since the return type is 
     * <code>long</code>, which can represent 2<sup>32</sup> &minus; 2 easily. 
     * So, for example, the trace of 2147483647 + &radic;(&minus;89) is 
     * 4294967294. But if the function returns &minus;2 instead, there is a 
     * problem that needs correcting.
     */
    @Test
    public void testTraceEdgeCasesHigh() {
        QuadraticRing ring = new ImaginaryQuadraticRing(-89);
        QuadraticInteger num = QuadraticInteger.apply(Integer.MAX_VALUE, -3, 
                ring);
        final long expected = 2L * Integer.MAX_VALUE;
        long actual = num.trace();
        assertEquals(expected, actual);
        ring = new RealQuadraticRing(70);
        num = QuadraticInteger.apply(Integer.MAX_VALUE, 3, ring);
        actual = num.trace();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of conjugate method of class QuadraticInteger. Testing that trying 
     * to take the conjugate of a quadratic integer from an unsupported ring 
     * triggers {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testConjugateUnsupportedCausesException() {
        System.out.println("Testing conjugate on unsupported quadratic integer");
        QuadraticInteger conj;
        try {
            conj = ILL_DEF_INT_A.conjugate();
            System.out.println("Trying to get conjugate of " 
                    + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " 
                    + conj.toASCIIString() + ".");
            String msg = "Conjugate of ill-defined integer should have caused exception";
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted conjugate.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String msg = e.getClass().getName() + " triggered: " 
                    + e.getMessage();
            fail(msg);
        }
        try {
            conj = ILL_DEF_INT_B.conjugate();
            System.out.println("Trying to get conjugate of " 
                    + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " 
                    + conj.toASCIIString() + ".");
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
        try {
            QuadraticInteger sum = ILL_DEF_INT_A.plus(ILL_DEF_INT_B);
            String msg = "Trying to add " + ILL_DEF_INT_A.toString() + " to " 
                    + ILL_DEF_INT_B.toString() 
                    + " should have caused an exception, not given result " 
                    + sum.toString() + ".";
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted addition.");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add " 
                    + ILL_DEF_INT_A.toString() + " to " 
                    + ILL_DEF_INT_B.toString();
            fail(msg);
        }
    }
    
    /**
     * Test of plus method of class QuadraticInteger. Testing that adding an 
     * ordinary purely real, rational integer (algebraic degree 1) presented as 
     * a quadratic integer, regardless of which quadratic ring is used, to a 
     * genuine quadratic integer gives the correct result, without tripping up 
     * any exceptions.
     */
    @Test
    public void testPlusUnaryAsQuadGivesResult() {
        System.out.println("Testing that adding to an unary integer presented as a quadratic integer gives correct result even if invoked ring is different");
        QuadraticRing ring = new ImaginaryQuadraticRing(-15);
        ImaginaryQuadraticInteger unaryAddend = new ImaginaryQuadraticInteger(3, 
                0, ring);
        ring = new RealQuadraticRing(97);
        QuadraticInteger quadraticAddend = new RealQuadraticInteger(5, 1, ring, 
                2);
        QuadraticInteger expResult = new RealQuadraticInteger(11, 1, ring, 2);
        QuadraticInteger result;
        try {
            result = unaryAddend.plus(quadraticAddend);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String msg = "Exception should not have occurred for trying to add " 
                    + unaryAddend.toString() + " to " + quadraticAddend.toString();
            System.out.println(unaryAddend.toASCIIString() 
                    + " is of algebraic degree " 
                    + unaryAddend.algebraicDegree() + " and " 
                    + quadraticAddend.toASCIIString() 
                    + " is of algebraic degree " 
                    + quadraticAddend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(msg);
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " should not have occurred for trying to add " 
                    + unaryAddend.toString() + " to " 
                    + quadraticAddend.toString();
            fail(msg);
        }
        ring = new ImaginaryQuadraticRing(-2);
        quadraticAddend = new ImaginaryQuadraticInteger(5, 2, ring);
        expResult = new ImaginaryQuadraticInteger(8, 2, ring);
        try {
            result = unaryAddend.plus(quadraticAddend);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "AlgebraicDegreeOverflowException should not have occurred for trying to add 3 to " + quadraticAddend.toASCIIString();
            System.out.println(failMessage);
            System.out.println("3 is of algebraic degree " + unaryAddend.algebraicDegree() + " and " + quadraticAddend.toASCIIString() + " is of algebraic degree " + quadraticAddend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " should not have occurred for trying to add 3 to " + quadraticAddend.toASCIIString();
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
            String msg = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(msg);
        }
        try {
            subtraction = ILL_DEF_INT_B.minus(ILL_DEF_INT_A);
            System.out.println("Trying to subtract " 
                    + ILL_DEF_INT_A.toASCIIString() + " from " 
                    + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " 
                    + subtraction.toASCIIString() + ".");
            fail("Trying to subtract one ill-defined quadratic integer from another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted subtraction.");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String msg = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(msg);
        }
    }

    /**
     * Test of minus method of class QuadraticInteger. Testing that subtracting 
     * an ordinary purely real, rational integer (algebraic degree 1) presented 
     * as a quadratic integer, regardless of which quadratic ring is used, from 
     * a genuine quadratic integer gives the correct result, without tripping up 
     * any exceptions.
     */
    @Test
    public void testMinusUnaryAsQuadGivesResult() {
        System.out.println("Testing that subtracting an unary integer presented as a quadratic integer gives correct result even if invoked ring is different");
        QuadraticRing ring = new ImaginaryQuadraticRing(-15);
        ImaginaryQuadraticInteger unaryMinuend = new ImaginaryQuadraticInteger(3, 0, ring);
        ring = new RealQuadraticRing(97);
        QuadraticInteger quadraticSubtrahend = new RealQuadraticInteger(5, 1, ring, 2);
        QuadraticInteger expResult = new RealQuadraticInteger(1, -1, ring, 2);
        QuadraticInteger result;
        try {
            result = unaryMinuend.minus(quadraticSubtrahend);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String msg = "AlgebraicDegreeOverflowException should not have occurred for trying to subtract " 
                    + quadraticSubtrahend.toString() + " from " 
                    + unaryMinuend.toString();
            System.out.println(msg);
            System.out.println(unaryMinuend.toString() 
                    + " is of algebraic degree " 
                    + unaryMinuend.algebraicDegree() + " and " 
                    + quadraticSubtrahend.toASCIIString() 
                    + " is of algebraic degree " 
                    + quadraticSubtrahend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(msg);
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " should not have occurred for trying to subtract " 
                    + quadraticSubtrahend.toString() + " from " 
                    + unaryMinuend.toString();
            fail(msg);
        }
        ring = new ImaginaryQuadraticRing(-2);
        quadraticSubtrahend = new ImaginaryQuadraticInteger(5, 2, ring);
        expResult = new ImaginaryQuadraticInteger(-2, -2, ring);
        try {
            result = unaryMinuend.minus(quadraticSubtrahend);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String msg = "AlgebraicDegreeOverflowException should not have occurred for trying to subtract " 
                    + quadraticSubtrahend.toString() + " from " 
                    + unaryMinuend.toString();
            System.out.println(unaryMinuend.toASCIIString() 
                    + " is of algebraic degree " 
                    + unaryMinuend.algebraicDegree() + " and " 
                    + quadraticSubtrahend.toASCIIString() 
                    + " is of algebraic degree " 
                    + quadraticSubtrahend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(msg);
        } catch (Exception e) {
            String msg = e.getClass().getName() 
                    + " should not have occurred for trying to subtract " 
                    + quadraticSubtrahend.toString() + " from " 
                    + unaryMinuend.toString();
            fail(msg);
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
            String msg = "Trying to multiply " + ILL_DEF_INT_A.toString() 
                    + " by " + ILL_DEF_INT_B.toString() 
                    + " somehow resulted in " + product.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted multiplication.");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for unsupported multiplication";
            fail(msg);
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
        QuadraticInteger result;
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " 
                    + multiplicandB.toASCIIString() + " is said to be " 
                    + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String msg = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " should not have caused an exception";
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("\"" + adoe.getMessage() + "\"");
            String msg = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " should not have caused AlgebraicDegreeOverflowException.";
            fail(msg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred for cross-domain multiplication";
            fail(msg);
        }
        // Commutative check
        try {
            result = multiplicandB.times(multiplicandA);
            System.out.println(multiplicandB.toASCIIString() + " times " 
                    + multiplicandA.toASCIIString() + " is said to be " 
                    + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String msg = "Multiplying " + multiplicandB.toString() + " by " 
                    + multiplicandA.toString() 
                    + " should not have caused UnsupportedNumberDomainException.";
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("\"" + adoe.getMessage() + "\"");
            String msg = "Multiplying " + multiplicandB.toString() + " by " 
                    + multiplicandA.toString() 
                    + " should not have caused AlgebraicDegreeOverflowException.";
            fail(msg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " should not have occurred for cross-domain multiplicatoin";
            fail(msg);
        }
    }
    
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * quadratic integers from different domains gives the correct result, when 
     * the radicands of the generating square roots have nontrivial factors in 
     * common. For example, &radic;&minus;2 &times; &radic;&minus;10 = 
     * &minus;2&radic;5.
     */
    @Test
    public void testTimesCrossDomainRamification() {
        System.out.println("Testing that times can correctly go from real to imaginary domain and back when appropriate, and extract squares as needed");
        QuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        QuadraticInteger multiplicandA = new ImaginaryQuadraticInteger(0, 1, multiplicandARing);
        QuadraticRing multiplicandBRing = new ImaginaryQuadraticRing(-10);
        QuadraticInteger multiplicandB = new ImaginaryQuadraticInteger(0, 1, multiplicandBRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(5);
        RealQuadraticInteger expResult = new RealQuadraticInteger(0, -2, expResultRing);
        QuadraticInteger result;
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " + multiplicandB.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("It appears that times cross-domain has not been configured to recognize radicands divisible by squares.");
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " has caused IllegalArgumentException.\n" + iae.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // Real to real
        multiplicandARing = new RealQuadraticRing(2);
        multiplicandA = new RealQuadraticInteger(0, 1, multiplicandARing);
        multiplicandBRing = new RealQuadraticRing(10);
        multiplicandB = new RealQuadraticInteger(0, 1, multiplicandBRing);
        expResultRing = new RealQuadraticRing(5);
        expResult = new RealQuadraticInteger(0, 2, expResultRing);
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " + multiplicandB.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * two purely imaginary quadratic integers results in the appropriate real 
     * quadratic integer with no rational part. For example, &radic;&minus;2 
     * &times; &radic;&minus;5 = &minus;&radic;10.
     */
    @Test
    public void testTimesCrossDomainResult() {
        System.out.println("Testing that times can correctly go from real to imaginary domain and back when appropriate for the result");
        ImaginaryQuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        ImaginaryQuadraticInteger multiplicandA = new ImaginaryQuadraticInteger(0, 1, multiplicandARing);
        ImaginaryQuadraticRing multiplicandBRing = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticInteger multiplicandB = new ImaginaryQuadraticInteger(0, 1, multiplicandBRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(10);
        RealQuadraticInteger expResult = new RealQuadraticInteger(0, -1, expResultRing);
        QuadraticInteger result;
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " + multiplicandB.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandA.toString() + " by " + multiplicandB.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // Commutative check
        try {
            result = multiplicandB.times(multiplicandA);
            System.out.println(multiplicandB.toASCIIString() + " times " + multiplicandA.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Multiplying " + multiplicandB.toString() + " by " + multiplicandA.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Multiplying " + multiplicandB.toString() + " by " + multiplicandA.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * an ordinary purely real, rational integer (algebraic degree 1) presented 
     * as a quadratic integer, regardless of which quadratic ring is used, by a 
     * genuine quadratic integer gives the correct result, without tripping up 
     * any exceptions.
     */
    @Test
    public void testTimesUnaryAsQuadGivesResult() {
        System.out.println("Testing that multiplying an unary integer presented as a quadratic integer gives correct result even if invoked ring is different");
        QuadraticRing ring = new ImaginaryQuadraticRing(-15);
        ImaginaryQuadraticInteger unaryMultiplicand = new ImaginaryQuadraticInteger(3, 0, ring);
        ring = new RealQuadraticRing(97);
        QuadraticInteger quadraticMultiplicand = new RealQuadraticInteger(5, 1, ring, 2);
        QuadraticInteger expResult = new RealQuadraticInteger(15, 3, ring, 2);
        QuadraticInteger result;
        try {
            result = unaryMultiplicand.times(quadraticMultiplicand);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "AlgebraicDegreeOverflowException should not have occurred for trying to multiply 3 by " + quadraticMultiplicand.toASCIIString();
            System.out.println(failMessage);
            System.out.println("3 is of algebraic degree " + unaryMultiplicand.algebraicDegree() + " and " + quadraticMultiplicand.toASCIIString() + " is of algebraic degree " + quadraticMultiplicand.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " should not have occurred for trying to multiply 3 by " + quadraticMultiplicand.toASCIIString();
            fail(failMessage);
        }
        ring = new ImaginaryQuadraticRing(-2);
        quadraticMultiplicand = new ImaginaryQuadraticInteger(5, 2, ring);
        expResult = new ImaginaryQuadraticInteger(15, 6, ring);
        try {
            result = unaryMultiplicand.times(quadraticMultiplicand);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "AlgebraicDegreeOverflowException should not have occurred for trying to multiply 3 by " + quadraticMultiplicand.toASCIIString();
            System.out.println(failMessage);
            System.out.println("3 is of algebraic degree " + unaryMultiplicand.algebraicDegree() + " and " + quadraticMultiplicand.toASCIIString() + " is of algebraic degree " + quadraticMultiplicand.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " should not have occurred for trying to multiply 3 by " + quadraticMultiplicand.toASCIIString();
            fail(failMessage);
        }
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
            division = ILL_DEF_INT_C.divides(ILL_DEF_INT_A);
            System.out.println("Trying to divide " 
                    + ILL_DEF_INT_C.toASCIIString() + " by " 
                    + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " 
                    + division.toASCIIString() + ".");
            fail("Trying to divide one ill-defined quadratic integer by another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted division.");
            System.out.println("Message: " + unde.getMessage());
        } catch (NotDivisibleException nde) {
            String msg = "NotDivisibleException is not appropriate for " 
                    + nde.getMessage();
            fail(msg);
        } catch (Exception e) {
            String msg = e.getClass().getName() + " triggered: " 
                    + e.getMessage();
            fail(msg);
        }
        try {
            division = ILL_DEF_INT_C.divides(ILL_DEF_INT_B);
            System.out.println("Trying to divide " + ILL_DEF_INT_C.toASCIIString() + " by " + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " + division.toASCIIString() + ".");
            fail("Trying to divide one ill-defined quadratic integer by another should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException correctly triggered for attempted division.");
            System.out.println("Message: " + unde.getMessage());
        } catch (NotDivisibleException nde) {
            String msg = "NotDivisibleException is not appropriate in this context: " + nde.getMessage();
            fail(msg);
        } catch (Exception e) {
            String msg = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(msg);
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
        QuadraticInteger result;
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // Now to check dividend divided by expResult is divisor
        try {
            result = dividend.divides(expResult);
            System.out.println(dividend.toASCIIString() + " divided by " + expResult.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(divisor, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expResult.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expResult.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expResult.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing a 
     * real quadratic integer with "regular" part 0 and nonzero surd part by a 
     * the square root of a number coprime to the radicand of the dividend but 
     * having a common factor with the "surd" multiple results in the correct 
     * quadratic integer. For example, (3&radic;10)/(&radic;&minus;3) = 
     * &minus;&radic;&minus;30.
     */
    @Test
    public void testDividesCrossDomainRamification() {
        System.out.println("Testing that divides can correctly distinguish when a division results in a greater radicand");
        RealQuadraticRing dividendRing = new RealQuadraticRing(10);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 3, dividendRing);
        QuadraticRing divisorRing = new ImaginaryQuadraticRing(-3);
        QuadraticInteger divisor = new ImaginaryQuadraticInteger(0, 1, divisorRing);
        QuadraticRing expResultRing = new ImaginaryQuadraticRing(-30);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(0, -1, expResultRing);
        QuadraticInteger result;
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Division should have returned quadratic result, yet need for degree " + adoe.getNecessaryAlgebraicDegree() + " reported");
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // Same dividend for this next test
        divisorRing = new RealQuadraticRing(3);
        divisor = new RealQuadraticInteger(0, 1, divisorRing);
        expResultRing = new RealQuadraticRing(30);
        expResult = new RealQuadraticInteger(0, 1, expResultRing);
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Division should have returned quadratic result, yet need for degree " + adoe.getNecessaryAlgebraicDegree() + " reported");
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // And now different dividend and divisor
        dividendRing = new RealQuadraticRing(15);
        dividend = new RealQuadraticInteger(0, 28, dividendRing);
        divisorRing = new RealQuadraticRing(7);
        divisor = new RealQuadraticInteger(0, 2, divisorRing);
        expResultRing = new RealQuadraticRing(105);
        expResult = new RealQuadraticInteger(0, 2, expResultRing);
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Division should have returned quadratic result, yet need for degree " + adoe.getNecessaryAlgebraicDegree() + " reported");
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // Same dividend for this next test
        divisorRing = new ImaginaryQuadraticRing(-7);
        divisor = new ImaginaryQuadraticInteger(0, 2, divisorRing);
        expResultRing = new ImaginaryQuadraticRing(-105);
        expResult = new ImaginaryQuadraticInteger(0, -2, expResultRing);
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Division should have returned quadratic result, yet need for degree " + adoe.getNecessaryAlgebraicDegree() + " reported");
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing 
     * a purely imaginary quadratic integer by another results in a real 
     * quadratic integer with no rational part. For example, &radic;&minus;10 
     * &divide; &radic;&minus;5 = &radic;2.
     */
    @Test
    public void testDividesCrossDomainResult() {
        System.out.println("Testing that divides can correctly go from real to imaginary domain and back when appropriate for the result");
        ImaginaryQuadraticRing dividendRing = new ImaginaryQuadraticRing(-10);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(0, 1, dividendRing);
        ImaginaryQuadraticRing divisorRing = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(0, 1, divisorRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(2);
        RealQuadraticInteger expResult = new RealQuadraticInteger(0, 1, expResultRing);
        QuadraticInteger result;
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + divisor.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
        // Now to check dividend divided by expResult is divisor
        try {
            result = dividend.divides(expResult);
            System.out.println(dividend.toASCIIString() + " divided by " + expResult.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(divisor, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expResult.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expResult.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expResult.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " triggered: " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that when a 
     * division of algebraic integers from different quadratic rings results in 
     * an algebraic number of degree 2 that is not an algebraic integer, the 
     * correct exception ({@link NotDivisibleException}) is thrown.
     */
    @Test
    public void testCrossDomainNotDivisible() {
        System.out.println("Testing that NotDivisibleException occurs when it should for cross-domain divisions");
        RealQuadraticRing dividendRing = new RealQuadraticRing(15);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 27, dividendRing);
        QuadraticRing divisorRing = new RealQuadraticRing(7);
        QuadraticInteger divisor = new RealQuadraticInteger(0, 2, divisorRing);
        try {
            QuadraticInteger result = dividend.divides(divisor);
            String failMessage = "Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should have caused an exception, not given result " + result.toASCIIString();
            System.out.println(failMessage);
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            System.out.println("Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " correctly triggered NotDivisibleException");
            System.out.println("\"" + nde.getMessage() + "\"");
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Necessary algebraic degree reported as " + adoe.getNecessaryAlgebraicDegree());
            String failMessage = "Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException";
            System.out.println(failMessage);
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception for trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString();
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
            fail(failMessage);
        }
        // Same dividend for this next test
        divisorRing = new ImaginaryQuadraticRing(-7);
        divisor = new ImaginaryQuadraticInteger(0, 2, divisorRing);
        try {
            QuadraticInteger result = dividend.divides(divisor);
            String failMessage = "Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should have caused an exception, not given result " + result.toASCIIString();
            System.out.println(failMessage);
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            System.out.println("Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " correctly triggered NotDivisibleException");
            System.out.println("\"" + nde.getMessage() + "\"");
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("Necessary algebraic degree reported as " + adoe.getNecessaryAlgebraicDegree());
            String failMessage = "Trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString() + " should not have caused AlgebraicDegreeOverflowException";
            System.out.println(failMessage);
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception for trying to divide " + dividend.toASCIIString() + " by " + divisor.toASCIIString();
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
            fail(failMessage);
        }
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing 
     * an ordinary purely real, rational integer (algebraic degree 1) presented 
     * as a quadratic integer, regardless of which quadratic ring is used, by a 
     * genuine quadratic integer gives the correct result, without tripping up 
     * any exceptions.
     */
    @Test
    public void testDividesUnaryAsQuadGivesResult() {
        System.out.println("Testing that dividing an unary integer presented as a quadratic integer by a genuine quadratic integer gives correct result even if invoked ring is different");
        QuadraticRing ring = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger unaryDividend = new ImaginaryQuadraticInteger(2, 0, ring);
        ring = new RealQuadraticRing(79);
        QuadraticInteger quadraticDivisor = new RealQuadraticInteger(9, 1, ring);
        QuadraticInteger expResult = new RealQuadraticInteger(9, -1, ring);
        QuadraticInteger result;
        try {
            result = unaryDividend.divides(quadraticDivisor);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "AlgebraicDegreeOverflowException should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            System.out.println(failMessage);
            System.out.println("2 is of algebraic degree " + unaryDividend.algebraicDegree() + " and " + quadraticDivisor.toASCIIString() + " is of algebraic degree " + quadraticDivisor.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Since 2 is divisible by " + quadraticDivisor.toASCIIString() + ", NotDivisibleException should not have occurred";
            System.out.println(failMessage);
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            fail(failMessage);
        }
        ring = new ImaginaryQuadraticRing(-7);
        quadraticDivisor = new ImaginaryQuadraticInteger(1, 1, ring, 2);
        expResult = new ImaginaryQuadraticInteger(1, -1, ring, 2);
        try {
            result = unaryDividend.divides(quadraticDivisor);
            assertEquals(expResult, result);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "AlgebraicDegreeOverflowException should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            System.out.println(failMessage);
            System.out.println("2 is of algebraic degree " + unaryDividend.algebraicDegree() + " and " + quadraticDivisor.toASCIIString() + " is of algebraic degree " + quadraticDivisor.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Since 2 is divisible by " + quadraticDivisor.toASCIIString() + ", NotDivisibleException should not have occurred";
            System.out.println(failMessage);
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(failMessage);
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            fail(failMessage);
        }
    }
    
    /**
     * Test of divides method of class QuadraticInteger. Testing that dividing 
     * by 0 causes the correct exception.
     */
    @Test
    public void testDivisionByZero() {
        System.out.println("Division by zero");
        RealQuadraticRing dividendRing = new RealQuadraticRing(15);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 27, dividendRing);
        RealQuadraticRing divisorRing = new RealQuadraticRing(7);
        RealQuadraticInteger divisor = new RealQuadraticInteger(0, 0, divisorRing);
        try {
            QuadraticInteger result = dividend.divides(divisor);
            String failMessage = "Trying to divide " + dividend.toASCIIString() + " by 0 should have caused an exception, not given result " + result.toASCIIString();
            System.out.println(failMessage);
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "NotDivisibleException is inappropriate for division by zero";
            System.out.println(failMessage);
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to divide " + dividend.toASCIIString() + " by 0 correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is acceptable for trying to divide " + dividend.toASCIIString() + " by 0");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception for trying to divide " + dividend.toASCIIString() + " by 0";
            System.out.println(failMessage);
            System.out.println("\"" + e.getMessage() + "\"");
            fail(failMessage);
        }
    }
    
    /**
     * Test of apply method of class QuadraticInteger.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        QuadraticRing ring = new ImaginaryQuadraticRing(-35);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(7, 3, ring, 2);
        QuadraticInteger result = QuadraticInteger.apply(7, 3, ring, 2);
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(42);
        expResult = new RealQuadraticInteger(19, 10, ring);
        result = QuadraticInteger.apply(19, 10, ring);
        assertEquals(expResult, result);
        try {
            result = QuadraticInteger.apply(R_A, R_B, ILL_DEF_RING);
            String failMessage = "Trying to use apply with " + ILL_DEF_RING.toASCIIString() + " should have caused an exception, not given result " + result.toASCIIString();
            fail(failMessage);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to use apply with " + ILL_DEF_RING.toASCIIString() + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " should not have occurred for trying to use apply with " + ILL_DEF_RING.toASCIIString();
            fail(failMessage);
        }
    }
    
    /**
     * Another test of apply method of class QuadraticInteger. Trying to use 
     * null as a ring parameter should cause an exception with a message to that 
     * effect.
     */
    @Test
    public void testApplyRejectsNullRing() {
        int a = 5403;
        int b = 29;
        int denom = 2;
        try {
            QuadraticInteger badQuadInt = QuadraticInteger.apply(a, b, null, denom);
            String msg = "Should not have been able to apply null ring to " 
                    + badQuadInt.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to apply null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            if (excMsg == null) {
                fail("Exception message should not be null");
            } else {
                System.out.println("\"" + excMsg + "\"");
                String expected = "ring";
                String msg = "Exception message contains the word \"" + expected 
                        + "\"";
                assert excMsg.toLowerCase().contains(expected) : msg;
            }
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use null ring";
            fail(msg);
        }
    }
    
    /**
     * Test of applyTheta method of class QuadraticInteger. It should be the 
     * case that, given &theta; = <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;7</sup>&frasl;<sub>2</sub>, that the reverse holds 
     * true; and that given &theta; = <sup>1</sup>&frasl;<sub>2</sub> 
     * + <sup>&radic;13</sup>&frasl;<sub>2</sub>, then 
     * &minus;<sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;13</sup>&frasl;<sub>2</sub> = &minus;2 + &theta;.
     */
    @Test
    public void testApplyTheta() {
        System.out.println("applyTheta");
        QuadraticRing ring = new ImaginaryQuadraticRing(-7);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(1, 1, ring, 2);
        QuadraticInteger result = QuadraticInteger.applyTheta(0, 1, ring);
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(13);
        expResult = new RealQuadraticInteger(-3, 1, ring, 2);
        result = QuadraticInteger.applyTheta(-2, 1, ring);
        assertEquals(expResult, result);
    }

    /**
     * Another test of applyTheta method of class QuadraticInteger. It should be 
     * the case that, given &theta; = <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;7</sup>&frasl;<sub>2</sub>,then 1 + &radic;&minus;7 = 
     * 2&theta;; and that given &theta; = <sup>1</sup>&frasl;<sub>2</sub> 
     * + <sup>&radic;13</sup>&frasl;<sub>2</sub>, then 
     * &minus;3 + &radic;13 = &minus;4 + 2&theta;.
     */
    @Test
    public void testApplyThetaFullIntegers() {
        System.out.println("applyTheta");
        QuadraticRing ring = new ImaginaryQuadraticRing(-7);
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(1, 1, ring);
        QuadraticInteger result = QuadraticInteger.applyTheta(0, 2, ring);
        assertEquals(expResult, result);
        ring = new RealQuadraticRing(13);
        expResult = new RealQuadraticInteger(-3, 1, ring);
        result = QuadraticInteger.applyTheta(-4, 2, ring);
        assertEquals(expResult, result);
    }

    /**
     * Another test of applyTheta method of class QuadraticInteger. Trying to 
     * use null as a ring parameter should cause an exception with a message to 
     * that effect.
     */
    @Test
    public void testApplyThetaRejectsNullRing() {
        int a = -1;
        int b = 44438;
        try {
            QuadraticInteger badQuadInt = QuadraticInteger.applyTheta(a, b, null);
            String msg = "Should not have been able to apply null ring to " 
                    + badQuadInt.toString();
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to apply null ring correctly caused NullPointerException");
            String excMsg = npe.getMessage();
            if (excMsg == null) {
                fail("Exception message should not be null");
            } else {
                System.out.println("\"" + excMsg + "\"");
                String expected = "ring";
                String msg = "Exception message contains the word \"" + expected 
                        + "\"";
                assert excMsg.toLowerCase().contains(expected) : msg;
            }
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use null ring";
            fail(msg);
        }
    }
    
    /**
     * Another test of applyTheta method of class QuadraticInteger. Trying to 
     * use a ring that does not have so-called "half-integers" should cause an 
     * exception.
     */
    @Test
    public void testApplyThetaWithUnsuitableRing() {
        RealQuadraticRing ring = new RealQuadraticRing(2);
        try {
            QuadraticInteger result = QuadraticInteger.applyTheta(R_A, R_B, ring);
            String msg = "Trying to use " + ring.toString() 
                    + " for applyTheta should have caused an exception, not given result " 
                    + result.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + ring.toASCIIString() 
                    + " for applyTheta correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + ring.toString() + " for applyTheta";
            fail(msg);
        }
    }

}
