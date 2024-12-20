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

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertContainsSame;

/**
 * Tests of the TextCalculator class.
 * @author Alonso del Arte
 */
public class TextCalculatorTest {
    
    private static Set<Character> gatherGreekLetters() {
        char blockStart = '\u0370';
        char nextBlockBegin = '\u0400';
        int initialCapacity = nextBlockBegin - blockStart;
        Set<Character> letters = new HashSet<>(initialCapacity);
        for (char ch = blockStart; ch < nextBlockBegin; ch++) {
            if (Character.isDefined(ch)) {
                letters.add(ch);
            }
        }
        System.out.println("Successfully gathered " + letters.size() 
                + " Greek and Coptic characters");
        return letters;
    }
    
    @Test
    public void testRandomGreekLetter() {
        System.out.println("randomGreekLetter");
        Set<Character> expected = gatherGreekLetters();
        int initialCapacity = expected.size();
        Set<Character> actual = new HashSet<>(initialCapacity);
        int totalNumberOfCalls = 24 * initialCapacity;
        for (int i = 0; i < totalNumberOfCalls; i++) {
            char ch = TextCalculator.randomGreekLetter();
            String definedMsg = "Character '" + ch + "' " 
                    + Character.getName(ch)
                    + " should be a defined Greek or Coptic character";
            assert Character.isDefined(ch) : definedMsg;
            assert Character.UnicodeBlock.of(ch)
                    .equals(Character.UnicodeBlock.GREEK) : definedMsg;
            actual.add(ch);
        }
        assertContainsSame(expected, actual);
    }
    
    /**
     * Test of makeBinomialString method, of class TextCalculator.
     */
//    @Test
    public void testMakeBinomialString() {
        System.out.println("makeBinomialString");
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        char symbol = ' ';
        String expResult = "";
        String result = TextCalculator.makeBinomialString(a, b, symbol);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
