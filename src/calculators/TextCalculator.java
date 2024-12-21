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

/**
 *
 * @author Alonso del Arte
 */
public class TextCalculator {
    
    private static final char GREEK_BLOCK_BEGIN = '\u0370';
    
    private static final char NEXT_BLOCK_BEGIN = '\u0400';
    
    private static final int GREEK_BLOCK_SIZE 
            = NEXT_BLOCK_BEGIN - GREEK_BLOCK_BEGIN;
    
    private static final String PLUS_SIGN_SPACED = " + ";
    
    private static final String PLUS_THEN_DASH = PLUS_SIGN_SPACED + '-';
    
    private static final String DASH_SPACED = " - ";
    
    private static final char MINUS_SIGN = '\u2212';
    
    private static final String MINUS_SIGN_STRING 
            = Character.toString(MINUS_SIGN);
    
    private static final String MINUS_SIGN_SPACED = new String(new char[]{' ', 
        MINUS_SIGN, ' '});
    
    /**
     * Chooses a Greek or Coptic letter pseudorandomly. The letter may be 
     * archaic.
     * @return A defined letter from the Greek and Coptic block of Unicode's 
     * Basic Multilingual Plane. Examples: &#x0370; (Greek capital heta, which 
     * is archaic), &#x03CB; (Greek small letter upsilon with dialytika), &Rho; 
     * (Greek capital letter rho).
     */
    static char randomGreekLetter() {
        char propChar = '\u03A2';
        do {
            propChar = (char) (GREEK_BLOCK_BEGIN 
                    + randomNumber(GREEK_BLOCK_SIZE));
        } while (!Character.isDefined(propChar) 
                || !Character.isLetter(propChar));
        return propChar;
    }
    
    /**
     * 
     * @param a
     * @param b
     * @param symbol
     * @return 
     */
    public static String makeBinomialString(int a, int b, char symbol) {
        if (b == 0) {
            if (a < 0) {
                return MINUS_SIGN_STRING + Math.abs(a);
            } else {
                return Integer.toString(a);
            }
        }
        String initial = a + PLUS_SIGN_SPACED + b + symbol;
        String intermediate = initial.replace(PLUS_THEN_DASH, DASH_SPACED);
        String target = " 1" + symbol;
        String replacement = " " + symbol;
        String tweaked = intermediate.replace(target, replacement);
        return tweaked.replace('-', MINUS_SIGN);
    }
    
}
