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
package algebraics;

import algebraics.quadratics.IllDefinedQuadraticInteger;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.QuadraticInteger;

import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the UnsupportedNumberDomainException class.
 * @author Alonso del Arte
 */
public class UnsupportedNumberDomainExceptionTest {
    
    private static final Random RANDOM = new Random();
    
    private static final String TESTING_MESSAGE 
            = "This is for testing purposes only";
    
    /**
     * Test of the getMessage function, of the UnsupportedNumberDomainException 
     * class, inherited from RuntimeException. This test only requires that the 
     * message not be an empty String and that it contain characters other than 
     * whitespace characters. Whether the message is helpful to someone 
     * debugging the program is beyond the scope of this test.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        fail("REWRITE THIS TEST");
        int d = randomSquarefreeNumber(200);
        MockRing ring = new MockRing();
        UnsupportedNumberDomainException exc 
                = new UnsupportedNumberDomainException(TESTING_MESSAGE, ring);
        String msg = "Exception message should not be empty";
        assert !exc.getMessage().isEmpty() : msg;
    }

    /**
     * Test of the getCausingNumbers function, of the 
     * UnsupportedNumberDomainException class.
     */
    @Test
    public void testGetCausingNumbers() {
        System.out.println("getCausingNumbers");
        int d = -randomSquarefreeNumber(200);
        int re = RANDOM.nextInt(16384) - 8192;
        int im = RANDOM.nextInt(16384) - 8192;
        IllDefinedQuadraticRing r = new IllDefinedQuadraticRing(d);
        QuadraticInteger a = new IllDefinedQuadraticInteger(re, im, r);
        QuadraticInteger b = new IllDefinedQuadraticInteger(im, re, r);
        UnsupportedNumberDomainException exc 
                = new UnsupportedNumberDomainException(TESTING_MESSAGE, a, b);
        AlgebraicInteger[] expected = {a, b};
        AlgebraicInteger[] actual = exc.getCausingNumbers();
        assertArrayEquals(expected, actual);
    }
    
    /**
     * Test of the getCausingDomain function, of the 
     * UnsupportedNumberDomainException class.
     */
    @Test
    public void testGetCausingDomain() {
        System.out.println("getCausingDomain");
        int d = -randomSquarefreeNumber(200);
        IllDefinedQuadraticRing expected = new IllDefinedQuadraticRing(d);
        UnsupportedNumberDomainException exc 
                = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                        expected);
        IntegerRing actual = exc.getCausingDomain();
        assertEquals(expected, actual);
    }
    
    /**
     * This is necessary only because trying to pass a null literal to the 
     * UnsupportedNumberDomainException 2-parameter constructor results in an 
     * ambiguity the compiler refuses to process. The possibility of a null 
     * falling through the cracks remains, hence this null provider.
     * @return Null, always.
     */
    private static IntegerRing provideNullRing() {
        return null;
    }
    
    /**
     * Test of the UnsupportedNumberDomainException constructor. Trying to use 
     * null for the ring parameter should cause an exception.
     */
    @Test
    public void testConstructorRejectsNullRing() {
        IntegerRing badRing = provideNullRing();
        try {
            UnsupportedNumberDomainException badExc 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            badRing);
            String msg = "Should not have been able to create " 
                    + badExc.toString() + " with null ring";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null ring correctly caused exception");
            String excMsg = npe.getMessage();
            assert excMsg != null : "Message should not be null";
            assert !excMsg.isEmpty() : "Message should not be empty";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null ring";
            fail(msg);
        }
    }
    
    /**
     * This is necessary only because trying to pass a null literal to the 
     * UnsupportedNumberDomainException 2-parameter constructor results in an 
     * ambiguity the compiler refuses to process. The possibility of a null 
     * falling through the cracks remains, hence this null provider.
     * @return Null, always.
     */
    private static AlgebraicInteger provideNullNumber() {
        return null;
    }
    
    /**
     * Test of the UnsupportedNumberDomainException constructor. Trying to use 
     * null for the numberA parameter should cause an exception.
     */
    @Test
    public void testConstructorRejectsNullNumberA() {
        AlgebraicInteger badNumber = provideNullNumber();
        try {
            UnsupportedNumberDomainException badExc 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            badNumber);
            String msg = "Should not have been able to create " 
                    + badExc.toString() + " with null numberA";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null numberA correctly caused exception");
            String excMsg = npe.getMessage();
            assert excMsg != null : "Message should not be null";
            assert !excMsg.isEmpty() : "Message should not be empty";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null numberA";
            fail(msg);
        }
    }
    
    /**
     * Test of the UnsupportedNumberDomainException constructor. When using the 
     * 3-parameter constructor, the numberA parameter must not be null. As it 
     * turns out, it's unnecessary to test the 2-parameter constructors for a 
     * null literal, because using null for the second parameter causes an 
     * ambiguous reference that the compiler will refuse to process.
     */
    @Test
    public void testConstructorRejectsNullNumberARegardlessOfNumberB() {
        int d = randomSquarefreeNumber(200);
        int a = RANDOM.nextInt(16384) - 8192;
        int b = RANDOM.nextInt(16384) - 8192;
        IllDefinedQuadraticRing expected = new IllDefinedQuadraticRing(d);
        QuadraticInteger num = new IllDefinedQuadraticInteger(a, b, expected);
        try {
            UnsupportedNumberDomainException badExc 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            null, num);
            String msg = "Should not have been able to create " 
                    + badExc.toString() + " with null numberA";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null for numberA correctly caused exception");
            String excMsg = npe.getMessage();
            assert excMsg != null : "Message should not be null";
            assert !excMsg.isEmpty() : "Message should not be empty";
            System.out.println("\"" + excMsg + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null numberA";
            fail(msg);
        }
    }
    
    /**
     * Test of the UnsupportedNumberDomainException constructor. When passing 
     * the constructor an {@link AlgebraicInteger} parameter rather than an 
     * {@link IntegerRing} parameter, the constructor should nevertheless infer 
     * the correct ring.
     */
    @Test
    public void testConstructorInfersRing() {
        int d = randomSquarefreeNumber(200);
        int a = RANDOM.nextInt(16384) - 8192;
        int b = RANDOM.nextInt(16384) - 8192;
        IllDefinedQuadraticRing expected = new IllDefinedQuadraticRing(d);
        QuadraticInteger num = new IllDefinedQuadraticInteger(a, b, expected);
        UnsupportedNumberDomainException exc 
                = new UnsupportedNumberDomainException(TESTING_MESSAGE, num);
        IntegerRing actual = exc.getCausingDomain();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the UnsupportedNumberDomainException constructor. When using the 
     * 2-number constructor, it should check that the numbers come from the same 
     * ring.
     */
    @Test
    public void testConstructorChecksRingsMatch() {
        int d = randomSquarefreeNumber(200);
        int a = RANDOM.nextInt(16384) - 8192;
        int b = RANDOM.nextInt(16384) - 8192;
        IllDefinedQuadraticRing ringA = new IllDefinedQuadraticRing(d);
        IllDefinedQuadraticRing ringB = new IllDefinedQuadraticRing(-d);
        QuadraticInteger numberA = new IllDefinedQuadraticInteger(a, b, ringA);
        QuadraticInteger numberB = new IllDefinedQuadraticInteger(a, b, ringB);
        try {
            UnsupportedNumberDomainException badExc 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            numberA, numberB);
            String msg = "Should not have been able to create " 
                    + badExc.toString() + " with " + numberA.toString() 
                    + " and " + numberB.toString() 
                    + ", which come from different rings";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + numberA.toString() 
                    + " and " + numberB.toString() 
                    + " correctly caused exception");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to use " 
                    + numberA.toString() + " and " + numberB.toString() 
                    + ", which come from different rings";
            fail(msg);
        }
    }
    
}
