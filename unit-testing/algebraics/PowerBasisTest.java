/*
 * Copyright (C) 2019 AL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package algebraics;

import fractions.Fraction;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AL
 */
public class PowerBasisTest {
    
    private static final Fraction ZERO_AS_FRACTION = new Fraction(0);
    
    private static final Fraction ONE_AS_FRACTION = new Fraction(1);
    
    private static final Fraction ONE_THIRD = new Fraction(1, 3);
    
    private static final Fraction ONE_SEVENTH = new Fraction(1, 7);
    
    private static final Fraction[] TWO_ZEROES = {ZERO_AS_FRACTION, ZERO_AS_FRACTION};
    
    private static final Fraction[] THREE_ZEROES = {ZERO_AS_FRACTION, ZERO_AS_FRACTION, ZERO_AS_FRACTION};
    
    private static final Fraction[] FOUR_ZEROES = {ZERO_AS_FRACTION, ZERO_AS_FRACTION, ZERO_AS_FRACTION, ZERO_AS_FRACTION};
    
    private static final Fraction[] TWO_ONES = {ONE_AS_FRACTION, ONE_AS_FRACTION};
    
    private static final Fraction[] TWO_ONES_AND_TWO_ONE_SEVENTHS = {ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_SEVENTH, ONE_SEVENTH};
    
    private static final Fraction[] THREE_ONES = {ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_AS_FRACTION};
    
    private static final Fraction[] THREE_ZEROES_AND_ONE_THIRD = {ZERO_AS_FRACTION, ZERO_AS_FRACTION, ZERO_AS_FRACTION, ONE_THIRD};
    
    private static final Fraction[] THREE_ONES_AND_ONE_THIRD = {ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_THIRD};
    
    private static final PowerBasis QUADRATIC_POWER_BASIS = new PowerBasis(TWO_ONES);
    
    private static final PowerBasis CUBIC_POWER_BASIS = new PowerBasis(THREE_ONES);
    
    private static final PowerBasis QUARTIC_POWER_BASIS = new PowerBasis(TWO_ONES_AND_TWO_ONE_SEVENTHS);
    
    private static final PowerBasis QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS = new PowerBasis(THREE_ONES_AND_ONE_THIRD, THREE_ONES_AND_ONE_THIRD, null);
    
    /**
     * Test of toString method, of class PowerBasis. Spaces are desirable but 
     * not required, so the test strips them out before making any assertions.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "1,a";
        String result = QUADRATIC_POWER_BASIS.toString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class PowerBasis. Spaces are desirable 
     * but not required, so the test strips them out before making any 
     * assertions.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "1,a,a^2";
        String result = CUBIC_POWER_BASIS.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1,a,1/7a^2,1/7a^3";
        result = QUARTIC_POWER_BASIS.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1,a,a^2,1/3a^3+1/3a";
        result = QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXString method, of class PowerBasis. Spaces are desirable but 
     * not required, so the test strips them out before making any assertions.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        String expResult = "1,a,a^2";
        String result = CUBIC_POWER_BASIS.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1,a,\\frac{1}{7}a^2,\\frac{1}{7}a^3";
        result = QUARTIC_POWER_BASIS.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1,a,a^2,\\frac{1}{3}a^3+\\frac{1}{3}a";
        result = QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class PowerBasis. Spaces are desirable 
     * but not required, so the test strips them out before making any 
     * assertions.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        fail("Haven't written test yet");
    }

    /**
     * Test of getDegree method, of class PowerBasis.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        assertEquals(2, QUADRATIC_POWER_BASIS.getDegree());
        assertEquals(3, CUBIC_POWER_BASIS.getDegree());
        assertEquals(4, QUARTIC_POWER_BASIS.getDegree());
        assertEquals(4, QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.getDegree());
    }

    /**
     * Test of getPowerMultiplicands method, of class PowerBasis.
     */
    @Test
    public void testGetPowerMultiplicands() {
        System.out.println("getPowerMultiplicands");
        assertArrayEquals(TWO_ONES, QUADRATIC_POWER_BASIS.getPowerMultiplicands());
        assertArrayEquals(THREE_ONES, CUBIC_POWER_BASIS.getPowerMultiplicands());
        assertArrayEquals(TWO_ONES_AND_TWO_ONE_SEVENTHS, QUARTIC_POWER_BASIS.getPowerMultiplicands());
        assertArrayEquals(THREE_ONES_AND_ONE_THIRD, QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.getPowerMultiplicands());
    }

    /**
     * Test of getPowerMultiplicandAdditiveAdjustments method, of class PowerBasis.
     */
    @Test
    public void testGetPowerMultiplicandAdditiveAdjustments() {
        System.out.println("getPowerMultiplicandAdditiveAdjustments");
        assertArrayEquals(TWO_ZEROES, QUADRATIC_POWER_BASIS.getPowerMultiplicandAdditiveAdjustments());
        assertArrayEquals(THREE_ZEROES, CUBIC_POWER_BASIS.getPowerMultiplicandAdditiveAdjustments());
        assertArrayEquals(FOUR_ZEROES, QUARTIC_POWER_BASIS.getPowerMultiplicandAdditiveAdjustments());
        assertArrayEquals(THREE_ZEROES_AND_ONE_THIRD, QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.getPowerMultiplicandAdditiveAdjustments());
    }

    /**
     * Test of getAdditiveAdjustments method, of class PowerBasis.
     */
    @Test
    public void testGetAdditiveAdjustments() {
        System.out.println("getAdditiveAdjustments");
        assertArrayEquals(TWO_ZEROES, QUADRATIC_POWER_BASIS.getAdditiveAdjustments());
        assertArrayEquals(THREE_ZEROES, CUBIC_POWER_BASIS.getAdditiveAdjustments());
        assertArrayEquals(FOUR_ZEROES, QUARTIC_POWER_BASIS.getAdditiveAdjustments());
        assertArrayEquals(FOUR_ZEROES, QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.getAdditiveAdjustments());
    }
    
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        try {
            PowerBasis badBasis = new PowerBasis(THREE_ONES, TWO_ZEROES, FOUR_ZEROES);
            String failMsg = "Trying to build power basis with fraction arrays of mismatched lengths should not have given result " + badBasis.toASCIIString();
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to build power basis with fraction arrays of mismatched lengths correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception to throw for trying to build power basis with fraction arrays of mismatched lengths";
            fail(failMsg);
        }
        try {
            PowerBasis badBasis = new PowerBasis(THREE_ONES, FOUR_ZEROES);
            String failMsg = "Trying to build power basis with fraction arrays of mismatched lengths should not have given result " + badBasis.toASCIIString();
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to build power basis with fraction arrays of mismatched lengths correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception to throw for trying to build power basis with fraction arrays of mismatched lengths";
            fail(failMsg);
        }
    }
    
}
