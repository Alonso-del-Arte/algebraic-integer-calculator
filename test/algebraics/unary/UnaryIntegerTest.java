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
package algebraics.unary;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the UnaryInteger class.
 * @author Alonso del Arte
 */
public class UnaryIntegerTest {
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringNegative() {
        int n = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger number = new UnaryInteger(-n);
        String expected = "\u2212" + n;
        String actual = number.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToASCIIStringNegative() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toASCIIString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToTeXStringNegative() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        int n = randomNumber(Short.MAX_VALUE);
        UnaryInteger number = new UnaryInteger(n);
        String expected = Integer.toString(n);
        String actual = number.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToHTMLStringNegative() {
        int n = -randomNumber(Short.MAX_VALUE) - 1;
        UnaryInteger number = new UnaryInteger(n);
        String expected = "&minus;" + Integer.toString(-n);
        String actual = number.toHTMLString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        assertEquals(number, number);
    }
    
    @Test
    public void testNotEqualsNull() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger number = new UnaryInteger(n);
        assertNotEquals(number, null);
    }
    
    public void testPlus() {
        System.out.println("plus");
        int a = randomNumber(Short.MAX_VALUE) + 1;
        int b = randomNumber(Short.MAX_VALUE) + 1;
        UnaryInteger addendA = new UnaryInteger(a);
        UnaryInteger addendB = new UnaryInteger(b);
        UnaryInteger expected = new UnaryInteger(a + b);
        UnaryInteger actual = addendA.plus(addendB);
        String message = "Adding " + addendA.toString() + " to " 
                + addendB.toString();
        assertEquals(message, expected, actual);
    }
    
    public void testDivisionByZero() {
        int n = randomNumber(Integer.MAX_VALUE) - Short.MAX_VALUE;
        UnaryInteger dividend = new UnaryInteger(n);
        UnaryInteger divisor = new UnaryInteger(0);
        fail("HAVEN'T WRITTEN TEST YET");
    }
    
}
