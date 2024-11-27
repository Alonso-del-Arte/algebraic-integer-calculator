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

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the UnsupportedNumberDomainException class.
 * @author Alonso del Arte
 */
public class UnsupportedNumberDomainExceptionTest {
    
    private static final String TESTING_MESSAGE 
            = "This is for testing purposes only";
    
    /**
     * Test of the getMessage function, of the UnsupportedNumberDomainException 
     * class.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        IntegerRing ring = new MockRing();
        String expected = TESTING_MESSAGE + ' ' + randomNumber();
        RuntimeException exc 
                = new UnsupportedNumberDomainException(expected, ring);
        String actual = exc.getMessage();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetMessageOneNumberConstructor() {
        MockRing ring = new MockRing();
        AlgebraicInteger numberA = new MockInteger(ring);
        String expected = TESTING_MESSAGE + ' ' + randomNumber();
        RuntimeException exc 
                = new UnsupportedNumberDomainException(expected, numberA);
        String actual = exc.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMessageTwoNumberConstructor() {
        MockRing ring = new MockRing();
        AlgebraicInteger numberA = new MockInteger(ring);
        AlgebraicInteger numberB = new MockInteger(ring);
        String expected = TESTING_MESSAGE + ' ' + randomNumber();
        RuntimeException exc 
                = new UnsupportedNumberDomainException(expected, numberA, 
                        numberB);
        String actual = exc.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test of the getCausingNumbers function, of the 
     * UnsupportedNumberDomainException class.
     */
    @Test
    public void testGetCausingNumbers() {
        System.out.println("getCausingNumbers");
        MockRing ring = new MockRing();
        MockInteger numberA = new MockInteger(ring);
        MockInteger numberB = new MockInteger(ring);
        UnsupportedNumberDomainException exc 
                = new UnsupportedNumberDomainException(TESTING_MESSAGE, numberA, 
                        numberB);
        AlgebraicInteger[] expecteds = {numberA, numberB};
        AlgebraicInteger[] actuals = exc.getCausingNumbers();
        assertArrayEquals(expecteds, actuals);
    }
    
    /**
     * Test of the getCausingDomain function, of the 
     * UnsupportedNumberDomainException class.
     */
    @Test
    public void testGetCausingDomain() {
        System.out.println("getCausingDomain");
        MockRing expected = new MockRing();
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
        String msg = "Constructor should reject null ring";
        Throwable t = assertThrows(() -> {
            RuntimeException badInstance 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            badRing);
            System.out.println(msg + ", not created bad instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
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
        String msg = "Constructor should reject null number";
        Throwable t = assertThrows(() -> {
            RuntimeException badInstance 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            badNumber);
            System.out.println(msg + ", not created bad instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullNumberAWhenNumberBNotNull() {
        MockRing ring = new MockRing();
        MockInteger numberB = new MockInteger(ring);
        String msg = "Constructor should reject null numberA";
        Throwable t = assertThrows(() -> {
            RuntimeException badInstance 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            null, numberB);
            System.out.println(msg + ", not created bad instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test of the UnsupportedNumberDomainException constructor. When passing 
     * the constructor an {@link AlgebraicInteger} parameter rather than an 
     * {@link IntegerRing} parameter, the constructor should nevertheless infer 
     * the correct ring.
     */
    @Test
    public void testConstructorInfersRing() {
        MockRing expected = new MockRing();
        MockInteger numberA = new MockInteger(expected);
        UnsupportedNumberDomainException exc 
                = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                        numberA);
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
        int maxDegree = randomSquarefreeNumber(200);
        MockRing ringA = new MockRing(maxDegree);
        MockRing ringB = new MockRing(maxDegree + 1);
        MockInteger numberA = new MockInteger(ringA);
        MockInteger numberB = new MockInteger(ringB);
        String msg = "Constructor should reject numberA from " 
                + ringA.toString() + " and numberB from " + ringB.toString();
        Throwable t = assertThrows(() -> {
            RuntimeException badInstance 
                    = new UnsupportedNumberDomainException(TESTING_MESSAGE, 
                            numberA, numberB);
            System.out.println(msg + ", not created bad instance " 
                    + badInstance.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
