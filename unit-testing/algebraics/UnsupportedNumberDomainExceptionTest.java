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

import algebraics.quadratics.IllDefinedQuadraticInteger;
import algebraics.quadratics.IllDefinedQuadraticRing;
import algebraics.quadratics.RealQuadraticRing;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the UnsupportedNumberDomainException class.
 * @author Alonso del Arte
 */
public class UnsupportedNumberDomainExceptionTest {
    
    private static UnsupportedNumberDomainException unsNumDomExcOneNum;
    private static UnsupportedNumberDomainException unsNumDomExcTwoNums;
    
    private static final int R_D = -55;
    private static final int R_A = 2;
    private static final int R_B = 1;
    
    @BeforeClass
    public static void setUpClass() {
        IllDefinedQuadraticRing r = new IllDefinedQuadraticRing(R_D);
        QuadraticInteger a = new IllDefinedQuadraticInteger(R_A, R_B, r);
        QuadraticInteger b = new IllDefinedQuadraticInteger(R_B, R_A, r);
        unsNumDomExcOneNum = new UnsupportedNumberDomainException("Initialization state, not the result of an actually thrown exception.", a, a);
        unsNumDomExcTwoNums = new UnsupportedNumberDomainException("Initialization state, not the result of an actually thrown exception.", a, a);
        try {
            QuadraticRing rZ = new RealQuadraticRing(1);
            System.out.println("Somehow created real quadratic ring " + rZ.toASCIIString());
        } catch (UnsupportedNumberDomainException unde) {
            unsNumDomExcOneNum = unde;
        }
        try {
            QuadraticInteger sum = a.plus(b);
            System.out.println("Trying to add " + a.toASCIIString() + " to " + b.toASCIIString() + " somehow resulted in " + sum.toASCIIString() + ".");
            fail("Trying to add two ill-defined quadratic integers should have caused UnsupportedNumberDomainException.");
        } catch (UnsupportedNumberDomainException unde) {
            unsNumDomExcTwoNums = unde;
        }
    }
    
    /**
     * Test of getMessage method, of class UnsupportedNumberDomainException, 
     * inherited from RuntimeException. This test only requires that the message 
     * not be an empty String and that it contain characters other than 
     * whitespace characters. Whether the message is helpful to someone 
     * debugging the program is beyond the scope of this test.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        String msg = unsNumDomExcOneNum.getMessage();
        String assertionMessage1 = "Exception message should not be empty String.";
        assertFalse(assertionMessage1, msg.isEmpty());
        String assertionMessage2 = "Exception message should contain characters other than space, 0x20.";
        msg = msg.replace(" ", ""); // Strip out whitespace
        assertFalse(assertionMessage2, msg.isEmpty());
        msg = unsNumDomExcTwoNums.getMessage();
        assertFalse(assertionMessage1, msg.isEmpty());
        msg = msg.replace(" ", "");
        assertFalse(assertionMessage2, msg.isEmpty());
    }

    /**
     * Test of getCausingNumbers method, of class 
     * UnsupportedNumberDomainException.
     */
    @Test
    public void testGetCausingNumbers() {
        System.out.println("getCausingNumbers");
        IllDefinedQuadraticRing r = new IllDefinedQuadraticRing(R_D);
        QuadraticInteger a = new IllDefinedQuadraticInteger(R_A, R_B, r);
        QuadraticInteger b = new IllDefinedQuadraticInteger(R_B, R_A, r);
        AlgebraicInteger[] expResult = {a, b};
        AlgebraicInteger[] result = unsNumDomExcTwoNums.getCausingNumbers();
        assertArrayEquals(expResult, result);
    }
    
}
