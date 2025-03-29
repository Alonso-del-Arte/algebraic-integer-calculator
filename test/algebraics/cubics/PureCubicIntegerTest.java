/*
 * Copyright (C) 2025 Alonso del Arte
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
package algebraics.cubics;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;
import fractions.Fraction;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the PureCubicInteger class.
 * @author Alonso del Arte
 */
public class PureCubicIntegerTest {
    
    private static PureCubicRing chooseRing() {
        int n = randomNumber(6) + 2;
        int d = randomSquarefreeNumberMod(n, 9);
        return new PureCubicRing(d);
    }
    
    private static Fraction wrapInteger(int n) {
        return new Fraction(n);
    }
    
    /**
     * Test of the getRing function, of the PureCubicInteger class.
     */
    @Test
    public void testGetRing() {
        System.out.println("getRing");
        PureCubicRing expected = chooseRing();
        CubicInteger number = new PureCubicInteger(randomNumber(), 
                randomNumber(), randomNumber(), expected);
        CubicRing actual = number.getRing();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        PureCubicRing ring = chooseRing();
        int bound = 128;
        int a = randomNumber(bound) + 2;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        int d = ring.radicand;
        CubicInteger number = new PureCubicInteger(a, b, c, ring);
//        String expected = a + "+" + b + "(" + d + ")+" +
fail("FINISH WRITING THIS TEST");
    }
    
    // TODO: Write tests for toString() such as for a - b cbrt(d) - c cbrt(d)^2
    
    @Test
    public void testIntParamsConstructorRejectsNullRing() {
        int bound = 2048;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        int c = randomNumber(bound) + 2;
        String msg = "Null ring should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new PureCubicInteger(a, b, c, null);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullFractionA() {
        Fraction b = wrapInteger(randomNumber());
        Fraction c = wrapInteger(randomNumber());
        PureCubicRing ring = chooseRing();
        String msg = "Null fraction A should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new PureCubicInteger(null, b, 
                    c, ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullFractionB() {
        Fraction a = wrapInteger(randomNumber());
        Fraction c = wrapInteger(randomNumber());
        PureCubicRing ring = chooseRing();
        String msg = "Null fraction B should have caused NPE";
        Throwable t = assertThrows(() -> {
            CubicInteger badInstance = new PureCubicInteger(a, null,  
                    c, ring);
            System.out.println(msg + ", not created instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(badInstance.hashCode()));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
