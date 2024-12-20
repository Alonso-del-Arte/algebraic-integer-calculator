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
package calculators;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the TextCalculator class.
 * @author Alonso del Arte
 */
public class TextCalculatorTest {
    
    /**
     * Test of makeBinomialString method, of class TextCalculator.
     */
    @Test
    public void testMakeBinomialString() {
        System.out.println("makeBinomialString");
        int a = 0;
        int b = 0;
        char symbol = ' ';
        String expResult = "";
        String result = TextCalculator.makeBinomialString(a, b, symbol);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
