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
    
    private static final char MINUS_SIGN = '\u2212';
    
    private static final String MINUS_SIGN_STRING 
            = Character.toString(MINUS_SIGN);
    
    private static final String MINUS_SIGN_SPACED = new String(new char[]{' ', 
        MINUS_SIGN, ' '});
    
    private static Set<Character> gatherGreekLetters() {
        char blockStart = '\u0370';
        char nextBlockBegin = '\u0400';
        int initialCapacity = nextBlockBegin - blockStart;
        Set<Character> letters = new HashSet<>(initialCapacity);
        for (char ch = blockStart; ch < nextBlockBegin; ch++) {
            if (Character.isDefined(ch) && Character.isLetter(ch)) {
                letters.add(ch);
            }
        }
        System.out.println("Successfully gathered " + letters.size() 
                + " Greek and Coptic letters");
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
            String letterMsg = "Character '" + ch + "' " 
                    + Character.getName(ch)
                    + " should be a defined Greek or Coptic letter";
            assert Character.isDefined(ch) : letterMsg;
            assert Character.isLetter(ch) : letterMsg;
            assert Character.UnicodeBlock.of(ch)
                    .equals(Character.UnicodeBlock.GREEK) : letterMsg;
            actual.add(ch);
        }
        assertContainsSame(expected, actual);
    }
    
    /**
     * Test of  the makeBinomialString function, of the TextCalculator class.
     */
    @Test
    public void testMakeBinomialString() {
        System.out.println("makeBinomialString");
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = a + " + " + b + symbol;
        String actual = TextCalculator.makeBinomialString(a, b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringPositiveAPlusNegativeB() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = a + MINUS_SIGN_SPACED + b + symbol;
        String actual = TextCalculator.makeBinomialString(a, -b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAPlusPositiveB() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + a + " + " + b + symbol;
        String actual = TextCalculator.makeBinomialString(-a, b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAPlusNegativeB() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + a + MINUS_SIGN_SPACED + b 
                + symbol;
        String actual = TextCalculator.makeBinomialString(-a, -b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringPositiveAPlusOneOfX() {
        System.out.println("makeBinomialString");
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = a + " + " + symbol;
        String actual = TextCalculator.makeBinomialString(a, 1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringPositiveAMinusOneOfX() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = a + MINUS_SIGN_SPACED + symbol;
        String actual = TextCalculator.makeBinomialString(a, -1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAPlusOneOfX() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + a + " + " + symbol;
        String actual = TextCalculator.makeBinomialString(-a, 1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAMinusOneOfX() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + a + MINUS_SIGN_SPACED + symbol;
        String actual = TextCalculator.makeBinomialString(-a, -1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithAPositive() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = Integer.toString(a);
        String actual = TextCalculator.makeBinomialString(a, 0, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithAZero() {
        char symbol = TextCalculator.randomGreekLetter();
        String expected = "0";
        String actual = TextCalculator.makeBinomialString(0, 0, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithANegative() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + a;
        String actual = TextCalculator.makeBinomialString(-a, 0, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithAZeroBPositive() {
        int bound = 8192;
        int b = randomNumber(bound) + 2;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = Integer.toString(b) + symbol;
        String actual = TextCalculator.makeBinomialString(0, b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithAZeroBNegative() {
        int bound = 8192;
        int b = randomNumber(bound) + 2;
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + Integer.toString(b) + symbol;
        String actual = TextCalculator.makeBinomialString(0, -b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithAZeroBPositiveOne() {
        char symbol = TextCalculator.randomGreekLetter();
        String expected = Character.toString(symbol);
        String actual = TextCalculator.makeBinomialString(0, 1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBinomialBecomesMonomialWithAZeroBNegativeOne() {
        char symbol = TextCalculator.randomGreekLetter();
        String expected = MINUS_SIGN_STRING + symbol;
        String actual = TextCalculator.makeBinomialString(0, -1, symbol);
        assertEquals(expected, actual);
    }
    
    private static String randomGreekString() {
        int len = randomNumber(5) + 1;
        char[] value = new char[len];
        for (int i = 0; i < len; i++) {
            value[i] = TextCalculator.randomGreekLetter();
        }
        return new String(value);
    }
    
    @Test
    public void testMakeBinomialStringWithSymbolParamString() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        String symbol = randomGreekString();
        String expected = a + " + " + b + symbol;
        String actual = TextCalculator.makeBinomialString(a, b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringPositiveAPlusNegativeBWSymbolParamStr() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        String symbol = randomGreekString();
        String expected = a + MINUS_SIGN_SPACED + b + symbol;
        String actual = TextCalculator.makeBinomialString(a, -b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAPlusPositiveBWSymbolParamStr() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        String symbol = randomGreekString();
        String expected = MINUS_SIGN_STRING + a + " + " + b + symbol;
        String actual = TextCalculator.makeBinomialString(-a, b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAPlusNegativeBWSymbolParamStr() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        int b = randomNumber(bound) + 2;
        String symbol = randomGreekString();
        String expected = MINUS_SIGN_STRING + a + MINUS_SIGN_SPACED + b 
                + symbol;
        String actual = TextCalculator.makeBinomialString(-a, -b, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringPositiveAPlusOneOfXWSymbolParamStr() {
        System.out.println("makeBinomialString");
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        String symbol = randomGreekString();
        String expected = a + " + " + symbol;
        String actual = TextCalculator.makeBinomialString(a, 1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringPositiveAMinusOneOfXWSymbolParamStr() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        String symbol = randomGreekString();
        String expected = a + MINUS_SIGN_SPACED + symbol;
        String actual = TextCalculator.makeBinomialString(a, -1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAPlusOneOfXWSymbolParamStr() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        String symbol = randomGreekString();
        String expected = MINUS_SIGN_STRING + a + " + " + symbol;
        String actual = TextCalculator.makeBinomialString(-a, 1, symbol);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMakeBinomialStringNegativeAMinusOneOfXWSymbolParamStr() {
        int bound = 8192;
        int a = randomNumber(bound) + 1;
        String symbol = randomGreekString();
        String expected = MINUS_SIGN_STRING + a + MINUS_SIGN_SPACED + symbol;
        String actual = TextCalculator.makeBinomialString(-a, -1, symbol);
        assertEquals(expected, actual);
    }
    
}
