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
package algebraics.quadratics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.UnsupportedNumberDomainException;
import arithmetic.NotDivisibleException;

import static calculators.EratosthenesSieve.listPrimes;
import static calculators.EratosthenesSieve.randomOddPrime;
import static calculators.EratosthenesSieve.randomPrimeOtherThan;
import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the class QuadraticInteger. Since this is an abstract class and the 
 * test classes for the derived classes have a lot of the pertinent tests, this 
 * test class is concerned mainly with making sure that {@link 
 * UnsupportedNumberDomainException} is thrown when appropriate, and that cross-
 * domain results are given when appropriate.
 * @author Alonso del Arte
 */
public class QuadraticIntegerTest {
    
    private static final Random RANDOM = new Random();
    
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
    
    private static QuadraticRing chooseRing() {
        int propD = randomSquarefreeNumber(1024);
        int d = (randomNumber() % 2 == 0) ? propD : -propD;
        return new QuadraticRingTest.QuadraticRingImpl(d);
    }
    
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        QuadraticRing ring = chooseRing();
        int a = randomNumber();
        int b = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new QuadraticIntegerImpl(a, b, ring);
        int expected = 2;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testAlgebraicDegreeOrdinaryIntegers() {
        QuadraticRing ring = chooseRing();
        int a = randomNumber() | (randomNumber(16) + 1);
        QuadraticInteger number = new QuadraticIntegerImpl(a, 0, ring);
        int expected = 1;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString() 
                + " in ring " + ring.toString();
        assertEquals(message, expected, actual);
    }

    @Test
    public void testAlgebraicDegreeZero() {
        QuadraticRing ring = chooseRing();
        QuadraticInteger number = new QuadraticIntegerImpl(0, 0, ring);
        int expected = 0;
        int actual = number.algebraicDegree();
        String message = "Reckoning algebraic degree of " + number.toString() 
                + " in ring " + ring.toString();
        assertEquals(message, expected, actual);
    }

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
     * Another test of the conjugate function of the QuadraticInteger class. 
     * Testing that trying to take the conjugate of a quadratic integer from an 
     * unsupported ring triggers {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testConjugateUnsupportedCausesException() {
        int d = randomSquarefreeNumber(4096);
        QuadraticRing ring = new QuadraticRingTest.QuadraticRingImpl(d);
        int a = RANDOM.nextInt();
        int b = RANDOM.nextInt();
        QuadraticInteger number = new QuadraticIntegerImpl(a, b, ring);
        String msg = "Conjugate of " + number.toString() + " of class " 
                + number.getClass().getName() + " should cause exception";
        Throwable t = assertThrows(() -> {
            QuadraticInteger badConjugate = number.conjugate();
            System.out.println(msg + ", not given result " 
                    + badConjugate.toString());
        }, UnsupportedNumberDomainException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        assert !excMsg.isBlank() : "Message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    /**
     * Test of plus function, of class QuadraticInteger. Adding imaginary 
     * quadratic integers at the edges of what the QuadraticInteger type can 
     * represent should cause arithmetic overflows indicated by 
     * ArithmeticException being thrown.
     */
    @Test
    public void testPlusArithmeticOverflowImag() {
        fail("REWRITE USING ASSERT THROWS");
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-R_D);
        ImaginaryQuadraticInteger addendA = new ImaginaryQuadraticInteger(1, 
                Integer.MAX_VALUE, ring);
        ImaginaryQuadraticInteger addendB = new ImaginaryQuadraticInteger(3, 7, 
                ring);
        QuadraticInteger result;
        try {
            result = addendA.plus(addendB);
            String message = "Trying to add " + addendA.toString() + " to " 
                    + addendB.toString() 
                    + " should've caused arithmetic overflow, not given result " 
                    + result.toString();
            fail(message);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to add " + addendA.toASCIIString() 
                    + " to " + addendB.toASCIIString() 
                    + " correctly triggered ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add " 
                    + addendA.toString() + " to " + addendB.toString();
            fail(message);
        }
    }
    
    /**
     * Test of plus function, of class QuadraticInteger. Adding real quadratic 
     * integers at the edges of what the QuadraticInteger type can represent 
     * should cause arithmetic overflows indicated by ArithmeticException being 
     * thrown.
     */
    @Test
    public void testPlusArithmeticOverflowReal() {
        fail("REWRITE USING ASSERT THROWS");
        RealQuadraticRing ring = new RealQuadraticRing(R_D);
        RealQuadraticInteger addendA = new RealQuadraticInteger(1, 
                Integer.MAX_VALUE, ring);
        RealQuadraticInteger addendB = new RealQuadraticInteger(3, 7, ring);
        QuadraticInteger result;
        try {
            result = addendA.plus(addendB);
            String message = "Trying to add " + addendA.toString() + " to " 
                    + addendB.toString() 
                    + " should've caused arithmetic overflow, not given result " 
                    + result.toString();
            fail(message);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to add " + addendA.toASCIIString() 
                    + " to " + addendB.toASCIIString() 
                    + " correctly triggered ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add " 
                    + addendA.toString() + " to " + addendB.toString();
            fail(message);
        }
    }
    
    /**
     * Test of the plus function of the QuadraticInteger class. Trying to add 8 
     * to <sup>2147483647</sup>&frasl;<sub>2</sub> + 
     * <sup>13&radic;&minus;7</sup>&frasl;<sub>2</sub> should cause an overflow, 
     * not give the incorrect result 
     * <sup>&minus;2147483633</sup>&frasl;<sub>2</sub> + 
     * <sup>13&radic;&minus;7</sup>&frasl;<sub>2</sub>.
     */
    @Test
    public void testPlusIntArithmeticOverflowImag() {
        fail("REWRITE USING ASSERT THROWS");
        ImaginaryQuadraticRing ring = new ImaginaryQuadraticRing(-7);
        ImaginaryQuadraticInteger addendA 
                = new ImaginaryQuadraticInteger(Integer.MAX_VALUE, 13, ring, 2);
        int rationalAddend = 8;
        try {
            QuadraticInteger result = addendA.plus(rationalAddend);
            String message = "Trying to add " + addendA.toString() + " to " 
                    + rationalAddend 
                    + " should've caused arithmetic overflow, not given result " 
                    + result.toString();
            fail(message);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to add " + addendA.toASCIIString() 
                    + " to " + rationalAddend 
                    + " correctly triggered ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add " 
                    + addendA.toString() + " to " + rationalAddend;
            fail(message);
        }
    }
    
    /**
     * Test of the plus function of the QuadraticInteger class. Trying to add 
     * &minus;8 to <sup>&minus;2147483647</sup>&frasl;<sub>2</sub> + 
     * <sup>13&radic;5</sup>&frasl;<sub>2</sub> (alternatively 1073741818 + 
     * 13&phi;) should cause an overflow, not give the incorrect result 
     * <sup>2147483633</sup>&frasl;<sub>2</sub> + 
     * <sup>13&radic;5</sup>&frasl;<sub>2</sub>.
     */
    @Test
    public void testPlusIntArithmeticOverflowReal() {
        fail("REWRITE USING ASSERT THROWS");
        RealQuadraticRing ring = new RealQuadraticRing(5);
        RealQuadraticInteger addendA 
                = new RealQuadraticInteger(Integer.MIN_VALUE + 1, 13, ring, 2);
        int rationalAddend = -8;
        try {
            QuadraticInteger result = addendA.plus(rationalAddend);
            String message = "Trying to add " + addendA.toString() + " to " 
                    + rationalAddend 
                    + " should've caused arithmetic overflow, not given result " 
                    + result.toString();
            fail(message);
        } catch (ArithmeticException ae) {
            System.out.println("Trying to add " + addendA.toASCIIString() 
                    + " to " + rationalAddend 
                    + " correctly triggered ArithmeticException");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add " 
                    + addendA.toString() + " to " + rationalAddend;
            fail(message);
        }
    }
    
    /**
     * Test of plus method of class QuadraticInteger. Testing that adding two 
     * quadratic integers from an unsupported quadratic ring correctly triggers 
     * {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testPlusUnsupportedCausesException() {
        fail("REWRITE USING ASSERT THROWS");
        try {
            QuadraticInteger sum = ILL_DEF_INT_A.plus(ILL_DEF_INT_B);
            String message = "Trying to add " + ILL_DEF_INT_A.toString() 
                    + " to " + ILL_DEF_INT_B.toString() 
                    + " should have caused an exception, not given result " 
                    + sum.toString();
            fail(message);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Exception triggered for attempted addition");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add " 
                    + ILL_DEF_INT_A.toString() + " to " 
                    + ILL_DEF_INT_B.toString();
            fail(message);
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
        fail("REWRITE WITH ASSERT THROWS");
        fail("BREAK UP INTO SMALLER TESTS");
        QuadraticRing ring = new ImaginaryQuadraticRing(-15);
        ImaginaryQuadraticInteger unaryAddend = new ImaginaryQuadraticInteger(3, 
                0, ring);
        ring = new RealQuadraticRing(97);
        QuadraticInteger quadraticAddend = new RealQuadraticInteger(5, 1, ring, 
                2);
        QuadraticInteger expected = new RealQuadraticInteger(11, 1, ring, 2);
        QuadraticInteger actual;
        try {
            actual = unaryAddend.plus(quadraticAddend);
            assertEquals(expected, actual);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Exception shouldn't've occurred for trying " 
                    + unaryAddend.toString() + " plus " 
                    + quadraticAddend.toString();
            System.out.println(unaryAddend.toASCIIString() 
                    + " is of algebraic degree " 
                    + unaryAddend.algebraicDegree() + " and " 
                    + quadraticAddend.toASCIIString() 
                    + " is of algebraic degree " 
                    + quadraticAddend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() 
                    + " should not have occurred for trying to add " 
                    + unaryAddend.toString() + " to " 
                    + quadraticAddend.toString();
            fail(message);
        }
        ring = new ImaginaryQuadraticRing(-2);
        quadraticAddend = new ImaginaryQuadraticInteger(5, 2, ring);
        expected = new ImaginaryQuadraticInteger(8, 2, ring);
        try {
            actual = unaryAddend.plus(quadraticAddend);
            assertEquals(expected, actual);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Exception shouldn't've occurred for adding 3 to " 
                    + quadraticAddend.toASCIIString();
            System.out.println(message);
            System.out.println("3 is of algebraic degree " 
                    + unaryAddend.algebraicDegree() + " and " 
                    + quadraticAddend.toASCIIString() 
                    + " is of algebraic degree " 
                    + quadraticAddend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
        } catch (RuntimeException e) {
            String message = e.getClass().getName() 
                    + " shouldn't've occurred for trying to add 3 to " 
                    + quadraticAddend.toASCIIString();
            fail(message);
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
        fail("REWRITE WITH ASSERT THROWS, and with QuadraticIntegerImpl");
        QuadraticInteger subtraction;
        try {
            subtraction = ILL_DEF_INT_A.minus(ILL_DEF_INT_B);
            System.out.println("Trying to subtract " 
                    + ILL_DEF_INT_B.toASCIIString() + " from " 
                    + ILL_DEF_INT_A.toASCIIString() + " somehow resulted in " 
                    + subtraction.toASCIIString());
            fail("Trying to subtract one ill-defined quadratic ...");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException ...");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String message = e.getClass().getName() + " triggered: " 
                    + e.getMessage();
            fail(message);
        }
        try {
            subtraction = ILL_DEF_INT_B.minus(ILL_DEF_INT_A);
            System.out.println("Trying to subtract " 
                    + ILL_DEF_INT_A.toASCIIString() + " from " 
                    + ILL_DEF_INT_B.toASCIIString() + " somehow resulted in " 
                    + subtraction.toASCIIString() + ".");
            fail("Trying to subtract one ill-defined quadratic ...");
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException ...");
            System.out.println("Message: " + unde.getMessage());
        } catch (Exception e) {
            String message = e.getClass().getName() + " triggered: " 
                    + e.getMessage();
            fail(message);
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
        fail("REWRITE WITH ASSERT THROWS, also more randomness; breakup");
        QuadraticRing ring = new ImaginaryQuadraticRing(-15);
        ImaginaryQuadraticInteger unaryMinuend 
                = new ImaginaryQuadraticInteger(3, 0, ring);
        ring = new RealQuadraticRing(97);
        QuadraticInteger quadraticSubtrahend = new RealQuadraticInteger(5, 1, 
                ring, 2);
        QuadraticInteger expected = new RealQuadraticInteger(1, -1, ring, 2);
        QuadraticInteger actual;
        try {
            actual = unaryMinuend.minus(quadraticSubtrahend);
            assertEquals(expected, actual);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String msg = "AlgDegOverExc shouldn't've occurred for subtracting " 
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
        expected = new ImaginaryQuadraticInteger(-2, -2, ring);
        try {
            actual = unaryMinuend.minus(quadraticSubtrahend);
            assertEquals(expected, actual);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "AlgebraicDegreeOverflowException should not ... " 
                    + quadraticSubtrahend.toString() + " from " 
                    + unaryMinuend.toString();
            System.out.println(unaryMinuend.toASCIIString() 
                    + " is of algebraic degree " 
                    + unaryMinuend.algebraicDegree() + " and " 
                    + quadraticSubtrahend.toASCIIString() 
                    + " is of algebraic degree " 
                    + quadraticSubtrahend.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() 
                    + " should not have occurred for trying to subtract " 
                    + quadraticSubtrahend.toString() + " from " 
                    + unaryMinuend.toString();
            fail(message);
        }
    }
        
    /**
     * Test of times method of class QuadraticInteger. Testing that multiplying 
     * two quadratic integers from an unsupported quadratic ring correctly 
     * triggers {@link UnsupportedNumberDomainException}.
     */
    @Test
    public void testTimesUnsupportedCausesException() {
        fail("REWRITE WITH ASSERT THROWS");
        QuadraticInteger product;
        try {
            product = ILL_DEF_INT_A.times(ILL_DEF_INT_B);
            String msg = "Trying to multiply " + ILL_DEF_INT_A.toString() 
                    + " by " + ILL_DEF_INT_B.toString() 
                    + " somehow resulted in " + product.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException ...");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for unsupported multiplication";
            fail(message);
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
        fail("REWRITE WITH ASSERT THROWS");
        ImaginaryQuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        ImaginaryQuadraticInteger multiplicandA 
                = new ImaginaryQuadraticInteger(0, 1, multiplicandARing);
        RealQuadraticRing multiplicandBRing = new RealQuadraticRing(5);
        RealQuadraticInteger multiplicandB 
                = new RealQuadraticInteger(0, 1, multiplicandBRing);
        ImaginaryQuadraticRing expResultRing = new ImaginaryQuadraticRing(-10);
        ImaginaryQuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 
                1, expResultRing);
        QuadraticInteger result;
        try {
            result = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " 
                    + multiplicandB.toASCIIString() + " is said to be " 
                    + result.toASCIIString() + ".");
            assertEquals(expResult, result);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " should not have caused an exception";
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("\"" + adoe.getMessage() + "\"");
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " shouldn't have caused AlgebraicDegreeOverflowException";
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " shouldn't've occurred for cross-domain multiplication";
            fail(message);
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
            String message = "Multiplying " + multiplicandB.toString() + " by " 
                    + multiplicandA.toString() 
                    + " shouldn't have caused UnsupportedNumberDomainException";
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println("\"" + adoe.getMessage() + "\"");
            String message = "Multiplying " + multiplicandB.toString() + " by " 
                    + multiplicandA.toString() 
                    + " shouldn't have caused AlgebraicDegreeOverflowException";
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " shouldn't've occurred for cross-domain multiplication";
            fail(message);
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
        fail("REWRITE WITH ASSERT THROWS, break up into smaller tests");
        QuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        QuadraticInteger multiplicandA = new ImaginaryQuadraticInteger(0, 1, 
                multiplicandARing);
        QuadraticRing multiplicandBRing = new ImaginaryQuadraticRing(-10);
        QuadraticInteger multiplicandB = new ImaginaryQuadraticInteger(0, 1, 
                multiplicandBRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(5);
        RealQuadraticInteger expected = new RealQuadraticInteger(0, -2, 
                expResultRing);
        QuadraticInteger actual;
        try {
            actual = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " 
                    + multiplicandB.toASCIIString() + " is " + actual.toASCIIString() + ".");
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " shouldn't've caused UnsupportedNumberDomainException.\n" 
                    + unde.getMessage();
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " shouldn't've caused AlgebraicDegreeOverflowException.\n" 
                    + adoe.getMessage();
            fail(message);
        } catch (IllegalArgumentException iae) {
            System.out.println("It appears that times cross-domain has ...");
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " has caused IllegalArgumentException.\n" 
                    + iae.getMessage();
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() + " triggered: " 
                    + re.getMessage();
            fail(message);
        }
        // Real to real
        multiplicandARing = new RealQuadraticRing(2);
        multiplicandA = new RealQuadraticInteger(0, 1, multiplicandARing);
        multiplicandBRing = new RealQuadraticRing(10);
        multiplicandB = new RealQuadraticInteger(0, 1, multiplicandBRing);
        expResultRing = new RealQuadraticRing(5);
        expected = new RealQuadraticInteger(0, 2, expResultRing);
        try {
            actual = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " 
                    + multiplicandB.toASCIIString() + " is " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " shouldn't've caused UnsupportedNumberDomainException.\n" 
                    + unde.getMessage();
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() + " triggered: " 
                    + re.getMessage();
            fail(message);
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
        fail("REWRITE WITH ASSERT THROWS");
        ImaginaryQuadraticRing multiplicandARing = new ImaginaryQuadraticRing(-2);
        ImaginaryQuadraticInteger multiplicandA 
                = new ImaginaryQuadraticInteger(0, 1, multiplicandARing);
        ImaginaryQuadraticRing multiplicandBRing 
                = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticInteger multiplicandB 
                = new ImaginaryQuadraticInteger(0, 1, multiplicandBRing);
        RealQuadraticRing expResultRing = new RealQuadraticRing(10);
        RealQuadraticInteger expected = new RealQuadraticInteger(0, -1, 
                expResultRing);
        QuadraticInteger actual;
        try {
            actual = multiplicandA.times(multiplicandB);
            System.out.println(multiplicandA.toASCIIString() + " times " 
                    + multiplicandB.toASCIIString() + " is " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            String message = "Multiplying " + multiplicandA.toString() 
                    + " by " + multiplicandB.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Multiplying " + multiplicandA.toString() + " by " 
                    + multiplicandB.toString() 
                    + " should not have caused ADOE.\n" + adoe.getMessage();
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() + " triggered: " 
                    + e.getMessage();
            fail(message);
        }
        // Commutative check
        try {
            actual = multiplicandB.times(multiplicandA);
            System.out.println(multiplicandB.toASCIIString() + " times " 
                    + multiplicandA.toASCIIString() + " is " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            String message = "Multiplying " + multiplicandB.toString() 
                    + " by " + multiplicandA.toString() 
                    + " shouldn't've caused UnsupportedNumberDomainException.\n" 
                    + unde.getMessage();
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Multiplying " + multiplicandB.toString() + " by " 
                    + multiplicandA.toString() 
                    + " should not have caused ADOE.\n" + adoe.getMessage();
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() + " triggered: " 
                    + e.getMessage();
            fail(message);
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
            String message = "AlgebraicDegreeOverflowException should not have occurred for trying to multiply 3 by " + quadraticMultiplicand.toASCIIString();
            System.out.println(message);
            System.out.println("3 is of algebraic degree " + unaryMultiplicand.algebraicDegree() + " and " + quadraticMultiplicand.toASCIIString() + " is of algebraic degree " + quadraticMultiplicand.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
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
            String message = "AlgebraicDegreeOverflowException should not have occurred for trying to multiply 3 by " + quadraticMultiplicand.toASCIIString();
            System.out.println(message);
            System.out.println("3 is of algebraic degree " + unaryMultiplicand.algebraicDegree() + " and " + quadraticMultiplicand.toASCIIString() + " is of algebraic degree " + quadraticMultiplicand.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() 
                    + " should not have occurred for trying to multiply 3 by " 
                    + quadraticMultiplicand.toASCIIString();
            fail(message);
        }
    }
    
    /**
     * Another test of the divides function, of the QuadraticInteger class. 
     * Testing that dividing one quadratic integers by another quadratic integer 
     * in an unsupported quadratic ring correctly triggers {@link 
     * algebraics.UnsupportedNumberDomainException}.
     */
    @Test
    public void testDividesUnsupportedCausesException() {
        int a = RANDOM.nextInt(128) + 2;
        int b = RANDOM.nextInt(128) + 2;
        int d = randomSquarefreeNumber(a + b);
        QuadraticRing ring = new IllDefinedQuadraticRing(d);
        IllDefinedQuadraticInteger unsupDividend 
                = new IllDefinedQuadraticInteger(a, b, ring);
        IllDefinedQuadraticInteger unsupDivisor 
                = new IllDefinedQuadraticInteger(a, b, ring);
        try {
            QuadraticInteger division = unsupDividend.divides(unsupDivisor);
            String message = "Trying to divide " + unsupDividend.toASCIIString() 
                    + " by " + unsupDivisor.toASCIIString() 
                    + " somehow resulted in " + division.toASCIIString() 
                    + " instead of an exception";
            fail(message);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("UnsupportedNumberDomainException is correct");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (NotDivisibleException nde) {
            System.out.println("\"" + nde.getMessage() + "\"");
            String message = "NotDivisibleException is not appropriate for " 
                    + unsupDividend.toString() + " divided by " 
                    + unsupDivisor.toString();
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception for trying to divide "  
                    + unsupDividend.toString() + " by " 
                    + unsupDivisor.toString();
            fail(message);
        }
    }
    
    /**
     * Another test of the divides function, of the QuadraticInteger class. 
     * Testing that dividing a purely imaginary quadratic integer by a real 
     * quadratic integer with no rational part, or vice-versa, results in the 
     * appropriate purely imaginary quadratic integer. For example, 
     * &radic;&minus;10 &divide; &radic;5 = &radic;&minus;2.
     */
    @Test
    public void testDividesCrossDomain() {
        int p = randomOddPrime(-100);
        int q = randomPrimeOtherThan(-p);
        QuadraticRing dividendRing = new ImaginaryQuadraticRing(p * q);
        QuadraticInteger dividend = new ImaginaryQuadraticInteger(0, 1, 
                dividendRing);
        QuadraticRing divisorRing = new RealQuadraticRing(q);
        QuadraticInteger divisor = new RealQuadraticInteger(0, 1, divisorRing);
        QuadraticRing expectedRing = new ImaginaryQuadraticRing(p);
        QuadraticInteger expected = new ImaginaryQuadraticInteger(0, 1, 
                expectedRing);
        QuadraticInteger actual;
        try {
            actual = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused an exception";
            fail(message);
        } catch (AlgebraicDegreeOverflowException | NotDivisibleException de) {
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() + " should not have caused " 
                    + de.getClass().getName();
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " should not have occurred for dividing " 
                    + dividend.toString() + " by " + divisor.toString();
            fail(message);
        }
    }
    
    /**
     * Another test of the divides function, of the QuadraticInteger class. 
     * Testing that dividing a real quadratic integer with "regular" part 0 and 
     * nonzero surd part by a the square root of a number coprime to the 
     * radicand of the dividend but having a common factor with the "surd" 
     * multiple results in the correct quadratic integer. For example, 
     * <sup>3&radic;10</sup>&frasl;<sub>&radic;&minus;3</sub> = 
     * &minus;&radic;&minus;30.
     */
    @Test
    public void testDividesCrossDomainRamificationRealToImaginaryCoprimeB() {
        int p = randomOddPrime(100);
        int q = randomOddPrime(-100);
        if (p == -q) {
            q = -101;
        }
        QuadraticRing dividendRing = new RealQuadraticRing(2 * p);
        QuadraticInteger dividend = new RealQuadraticInteger(0, q, 
                dividendRing);
        QuadraticRing divisorRing = new ImaginaryQuadraticRing(q);
        QuadraticInteger divisor = new ImaginaryQuadraticInteger(0, 1, 
                divisorRing);
        QuadraticRing expectedRing = new ImaginaryQuadraticRing(2 * p * q);
        QuadraticInteger expected = new ImaginaryQuadraticInteger(0, -1, 
                expectedRing);
        try {
            QuadraticInteger actual = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is said to be " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " shouldn't have caused UnsupportedNumberDomainException";
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused overflow for algebraic degree " 
                    + adoe.getNecessaryAlgebraicDegree();
            fail(message);
        } catch (NotDivisibleException nde) {
            System.out.println("\"" + nde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused NotDivisibleException";
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " should not have occurred for trying to divide " 
                    + dividend.toString() + " by " + divisor.toString();
            fail(message);
        }
    }
    
    /**
     * Another test of the divides function of the QuadraticInteger class. 
     * Testing that dividing a real quadratic integer with "regular" part 0 and 
     * nonzero surd part by a the square root of a number coprime to the 
     * radicand of the dividend but having a common factor with the "surd" 
     * multiple results in the correct quadratic integer. For example, 
     * <sup>3&radic;10</sup>&frasl;<sub>&radic;3</sub> = &radic;30.
     */
    @Test
    public void testDividesCrossDomainRamificationRealToRealCoprimeB() {
        int p = randomOddPrime(100);
        int q = randomPrimeOtherThan(p);
        if (q == 2) {
            q = 101;
        }
        RealQuadraticRing dividendRing = new RealQuadraticRing(2 * p);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, q, 
                dividendRing);
        RealQuadraticRing divisorRing = new RealQuadraticRing(q);
        RealQuadraticInteger divisor = new RealQuadraticInteger(0, 1, 
                divisorRing);
        RealQuadraticRing expectedRing = new RealQuadraticRing(2 * p * q);
        RealQuadraticInteger expected = new RealQuadraticInteger(0, 1, 
                expectedRing);
        try {
            QuadraticInteger actual = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is said to be " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " shouldn't have caused UnsupportedNumberDomainException";
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused overflow for algebraic degree " 
                    + adoe.getNecessaryAlgebraicDegree();
            fail(message);
        } catch (NotDivisibleException nde) {
            System.out.println("\"" + nde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused NotDivisibleException";
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() 
                    + " shouldn't have occurred trying to divide " 
                    + dividend.toString() + " by " + divisor.toString();
            fail(message);
        }
    }
    
    /**
     * Another test of the divides function of the QuadraticInteger class. 
     * Testing that dividing a real quadratic integer with "regular" part 0 and 
     * nonzero surd part by a the square root of a number coprime to the 
     * radicand of the dividend but having a common factor with the "surd" 
     * multiple results in the correct quadratic integer. For example, 
     * <sup>28&radic;15</sup>&frasl;<sub>2&radic;7</sub> = 2&radic;105.
     */
    @Test//p = 3, q = 5, r = 7
    public void testDividesCrossDomainRamificationRealToReal() {
        List<Integer> primes = listPrimes(50);
        int index = randomNumber(primes.size() - 3) + 1;
        int p = primes.get(index);
        int q = primes.get(index + 1);
        int r = primes.get(index + 2);
        RealQuadraticRing dividendRing = new RealQuadraticRing(p * q);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 4 * r, 
                dividendRing);
        RealQuadraticRing divisorRing = new RealQuadraticRing(r);
        RealQuadraticInteger divisor = new RealQuadraticInteger(0, 2, 
                divisorRing);
        RealQuadraticRing expectedRing = new RealQuadraticRing(p * q * r);
        RealQuadraticInteger expected = new RealQuadraticInteger(0, 2, 
                expectedRing);
        try {
            QuadraticInteger actual = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is said to be " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " shouldn't have caused UnsupportedNumberDomainException";
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused overflow for algebraic degree " 
                    + adoe.getNecessaryAlgebraicDegree();
            fail(message);
        } catch (NotDivisibleException nde) {
            System.out.println("\"" + nde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused NotDivisibleException";
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() 
                    + " shouldn't have occurred for trying to divide " 
                    + dividend.toString() + " by " + divisor.toString();
            fail(message);
        }
    }
    
    /**
     * Test of the divides function of the QuadraticInteger class. Testing that 
     * dividing a real quadratic integer with "regular" part 0 and nonzero surd 
     * part by a the square root of a number coprime to the radicand of the 
     * dividend but having a common factor with the "surd" multiple results in 
     * the correct quadratic integer. For example, 
     * <sup>3&radic;10</sup>&frasl;<sub>&radic;&minus;3</sub> =  
     * &minus;&radic;&minus;30.
     */
    @Test
    public void testDividesCrossDomainRamificationRealToImaginary() {
        List<Integer> primes = listPrimes(50);
        int index = randomNumber(primes.size() - 3) + 1;
        int p = primes.get(index);
        int q = primes.get(index + 1);
        int r = primes.get(index + 2);
        RealQuadraticRing dividendRing = new RealQuadraticRing(15);
        RealQuadraticInteger dividend = new RealQuadraticInteger(0, 28, 
                dividendRing);
        QuadraticRing divisorRing = new ImaginaryQuadraticRing(-7);
        QuadraticInteger divisor = new ImaginaryQuadraticInteger(0, 2, 
                divisorRing);
        QuadraticRing expectedRing = new ImaginaryQuadraticRing(-105);
        QuadraticInteger expected = new ImaginaryQuadraticInteger(0, -2, 
                expectedRing);
        QuadraticInteger actual;
        try {
            actual = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " 
                    + divisor.toASCIIString() + " is said to be " 
                    + actual.toASCIIString());
            assertEquals(expected, actual);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("\"" + unde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " shouldn't have caused UnsupportedNumberDomainException";
            fail(message);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused overflow for algebraic degree " 
                    + adoe.getNecessaryAlgebraicDegree();
            fail(message);
        } catch (NotDivisibleException nde) {
            System.out.println("\"" + nde.getMessage() + "\"");
            String message = "Dividing " + dividend.toString() + " by " 
                    + divisor.toString() 
                    + " should not have caused NotDivisibleException";
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() 
                    + " is the wrong exception for trying to divide " 
                    + dividend.toString() + " by " + divisor.toString();
            fail(message);
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
        ImaginaryQuadraticRing dividendRing = new ImaginaryQuadraticRing(-10);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(0, 1, 
                dividendRing);
        ImaginaryQuadraticRing divisorRing = new ImaginaryQuadraticRing(-5);
        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(0, 1, 
                divisorRing);
        RealQuadraticRing expectedRing = new RealQuadraticRing(2);
        RealQuadraticInteger expected = new RealQuadraticInteger(0, 1, 
                expectedRing);
        QuadraticInteger result;
        try {
            result = dividend.divides(divisor);
            System.out.println(dividend.toASCIIString() + " divided by " + divisor.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(expected, result);
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
            result = dividend.divides(expected);
            System.out.println(dividend.toASCIIString() + " divided by " + expected.toASCIIString() + " is " + result.toASCIIString() + ".");
            assertEquals(divisor, result);
        } catch (UnsupportedNumberDomainException unde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expected.toString() + " should not have caused UnsupportedNumberDomainException.\n" + unde.getMessage();
            fail(failMessage);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expected.toString() + " should not have caused AlgebraicDegreeOverflowException.\n" + adoe.getMessage();
            fail(failMessage);
        } catch (NotDivisibleException nde) {
            String failMessage = "Dividing " + dividend.toString() + " by " + expected.toString() + " should not have caused NotDivisibleException.\n" + nde.getMessage();
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
        QuadraticRing ring = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger unaryDividend = new ImaginaryQuadraticInteger(2, 0, ring);
        ring = new RealQuadraticRing(79);
        QuadraticInteger quadraticDivisor = new RealQuadraticInteger(9, 1, ring);
        QuadraticInteger expected = new RealQuadraticInteger(9, -1, ring);
        QuadraticInteger actual;
        try {
            actual = unaryDividend.divides(quadraticDivisor);
            assertEquals(expected, actual);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "AlgebraicDegreeOverflowException should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            System.out.println(message);
            System.out.println("2 is of algebraic degree " + unaryDividend.algebraicDegree() + " and " + quadraticDivisor.toASCIIString() + " is of algebraic degree " + quadraticDivisor.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
        } catch (NotDivisibleException nde) {
            String message = "Since 2 is divisible by " + quadraticDivisor.toASCIIString() + ", NotDivisibleException should not have occurred";
            System.out.println(message);
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() + " should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            fail(message);
        }
        ring = new ImaginaryQuadraticRing(-7);
        quadraticDivisor = new ImaginaryQuadraticInteger(1, 1, ring, 2);
        expected = new ImaginaryQuadraticInteger(1, -1, ring, 2);
        try {
            actual = unaryDividend.divides(quadraticDivisor);
            assertEquals(expected, actual);
        } catch (AlgebraicDegreeOverflowException adoe) {
            String message = "AlgebraicDegreeOverflowException should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            System.out.println(message);
            System.out.println("2 is of algebraic degree " + unaryDividend.algebraicDegree() + " and " + quadraticDivisor.toASCIIString() + " is of algebraic degree " + quadraticDivisor.algebraicDegree());
            System.out.println("\"" + adoe.getMessage() + "\"");
            fail(message);
        } catch (NotDivisibleException nde) {
            String message = "Since 2 is divisible by " + quadraticDivisor.toASCIIString() + ", NotDivisibleException should not have occurred";
            System.out.println(message);
            System.out.println("\"" + nde.getMessage() + "\"");
            fail(message);
        } catch (Exception e) {
            String message = e.getClass().getName() + " should not have occurred for trying to divide 2 by " + quadraticDivisor.toASCIIString();
            fail(message);
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
     * Another test of the mod function, of the QuadraticInteger class. If the 
     * dividend and the divisor are from different rings, an {@link 
     * AlgebraicDegreeOverflowException} should occur.
     */
    @org.junit.Ignore
    @Test // TODO: Rework into two separate tests, to address one case in which 
    // there is an algebraic degree overflow and one case in which there isn't
    public void testModCrossDomainImagDivisor() {
        int a = RANDOM.nextInt(2048) + 1;
        int b = RANDOM.nextInt(a) + 127;
        a -= 1024;
        ImaginaryQuadraticRing imagRing = new ImaginaryQuadraticRing(-47);
        RealQuadraticRing realRing = new RealQuadraticRing(47);
        RealQuadraticInteger dividend = new RealQuadraticInteger(a, b, realRing);
        ImaginaryQuadraticInteger divisor = new ImaginaryQuadraticInteger(a, b, 
                imagRing);
        try {
            QuadraticInteger result = dividend.mod(divisor);
            String msg = dividend.toString() + " mod " + divisor.toString() 
                    + " should not have given result " + result.toString();
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println(dividend.toASCIIString() + " mod " 
                    + divisor.toASCIIString()
                    + " correctly caused AlgebraicDegreeOverflowException");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for " 
                    + dividend.toString() + " mod " + divisor.toString();
            fail(msg);
        }
    }
    
    /**
     * Another test of the mod function, of the QuadraticInteger class. If the 
     * dividend and the divisor are from different rings, an {@link 
     * AlgebraicDegreeOverflowException} should occur.
     */
    @org.junit.Ignore
    @Test // TODO: Rework into two separate tests, to address one case in which 
    public void testModCrossDomainRealDivisor() {
        int a = RANDOM.nextInt(2048) + 1;
        int b = RANDOM.nextInt(a) + 127;
        a -= 1024;
        ImaginaryQuadraticRing imagRing = new ImaginaryQuadraticRing(-53);
        RealQuadraticRing realRing = new RealQuadraticRing(53);
        ImaginaryQuadraticInteger dividend = new ImaginaryQuadraticInteger(a, b, 
                imagRing);
        RealQuadraticInteger divisor = new RealQuadraticInteger(a, b, realRing);
        try {
            QuadraticInteger result = dividend.mod(divisor);
            String msg = dividend.toString() + " mod " + divisor.toString() 
                    + " should not have given result " + result.toString();
            fail(msg);
        } catch (AlgebraicDegreeOverflowException adoe) {
            System.out.println(dividend.toASCIIString() + " mod " 
                    + divisor.toASCIIString()
                    + " correctly caused AlgebraicDegreeOverflowException");
            System.out.println("\"" + adoe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for " 
                    + dividend.toString() + " mod " + divisor.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testModZero() {
        int a = randomNumber(256) + 4;
        int b = randomNumber(256) + 4;
        int d = -randomSquarefreeNumber(8192);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger divisor = new ImaginaryQuadraticInteger(a, b, ring);
        QuadraticInteger dividend = divisor.times(divisor.conjugate().negate());
        QuadraticInteger expected = new ImaginaryQuadraticInteger(0, 0, ring);
        QuadraticInteger actual = dividend.mod(divisor);
        String message = dividend.toString() + " modulo " + divisor.toString() 
                + " should be 0";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testMod() {
        System.out.println("mod");
        int propD = randomSquarefreeNumber(1024);
        int d = (propD == 1) ? 1027 : propD;
        QuadraticRing ring = new RealQuadraticRing(d);
        int denom = (d % 4 == 1) ? 2 : 1;
        int potModReg = 2 * randomNumber(5) + 1;
        int a = 2 * d * d + potModReg;
        QuadraticInteger dividend = new RealQuadraticInteger(a, 1, ring, denom);
        QuadraticInteger divisor = new RealQuadraticInteger(0, 1, ring);
        QuadraticInteger remainder = dividend.mod(divisor);
        QuadraticInteger multiple = dividend.minus(remainder);
        long actual = multiple.mod(divisor).norm();
        String message = dividend.toString() + " modulo " + divisor.toString() 
                + " is said to be " + remainder.toString() + ", hence " 
                + dividend.toString() + " minus " + remainder.toString() 
                + " which equals " + multiple.toString() + " should be 0 mod " 
                + divisor.toString();
        assertEquals(message, 0L, actual);
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
    
    // TODO: Assess access level
    static class QuadraticIntegerImpl extends QuadraticInteger {
        
        private static final long serialVersionUID = 4558707313363386368L;
    
        private final double numValRe, numValIm;
    
        /**
         * This function is implemented only because it is required by the 
         * {@link algebraics.Arithmeticable} interface. This class can't use 
         * {@link QuadraticInteger#negate()} because that would cause {@link 
         * algebraics.UnsupportedNumberDomainException}, perhaps resulting in 
         * misleading test results.
         * @return A value that is actually correct. Though I haven't actually 
         * tested this, so I don't guarantee correctness.
         */
        @Override
        public QuadraticInteger negate() {
            return new QuadraticIntegerImpl(-this.regPartMult, 
                    -this.regPartMult, this.quadRing);
        }

        /**
         * Possibly the absolute value of this quadratic integer.
         * @return A value not guaranteed to be correct.
         */
        @Override
        public double abs() {
            return Math.abs(this.numValRe + this.numValIm);
        }

        /**
         * This function is implemented only because it is required by the 
         * {@link algebraics.AlgebraicInteger} interface.
         * @return A value not guaranteed to be correct, even allowing for loss 
         * of machine precision.
         */
        @Override
        public double getRealPartNumeric() {
            return this.numValRe;
        }

        /**
         * This function is implemented only because it is required by the 
         * {@link algebraics.AlgebraicInteger} interface.
         * @return A value not guaranteed to be correct, even allowing for loss 
         * of machine precision.
         */
        @Override
        public double getImagPartNumeric() {
            return this.numValIm;
        }
    
        @Override
        public boolean isReApprox() {
            return this.quadRing.radicand > 0;
        }
    
        @Override
        public boolean isImApprox() {
            return this.quadRing.radicand < 0;
        }
    
        /**
         * This function is implemented here only because it is abstract in 
         * {@link QuadraticInteger}.
         * @return A value guaranteed to be correct only if the ill-defined 
         * quadratic integer is purely real.
         */
        @Override
        public double angle() {
            if (this.numValRe < 0) {
                return Math.PI;
            } else {
               return 0.0;
            }
        }
    
        /**
         * Constructor.
         * @param a The integer for the "regular" part.
         * @param b The integer to be multiplied by the square root of the 
         * radicand.
         * @param ring Preferably an {@link IllDefinedQuadraticRing} object, but 
         * any object subclassed from {@link QuadraticRing} will do; there is no 
         * checking for the class of this parameter.
         */
        public QuadraticIntegerImpl(int a, int b, QuadraticRing ring) {
            super(a, b, ring, 1);
            double re = this.regPartMult;
            double im = 0.0;
            double y = this.quadRing.realRadSqrt * this.surdPartMult;
            if (this.quadRing.radicand < 0) {
                im += y;
            } else {
                re += y;
            }
            this.numValRe = re;
            this.numValIm = im;
        }
        
    }

}
