/*
 * Copyright (C) 2019 Alonso del Arte
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

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the PowerBasis class.
 * @author Alonso del Arte
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
    
    /**
     * The power basis of any quadratic integer ring: 1, <i>a</i>.
     */
    private static final PowerBasis QUADRATIC_POWER_BASIS = new PowerBasis(TWO_ONES);
    
    /**
     * The power basis of <b>Z</b>[&#8731;2] and other cubic rings.
     */
    private static final PowerBasis CUBIC_POWER_BASIS = new PowerBasis(THREE_ONES);
    
    /**
     * The power basis of <i>O</i><sub><b>Q</b>(<i>i</i> + &radic;14)</sub>: 1, 
     * <i>a</i>, <sup>1</sup>&frasl;<sub>7</sub><i>a</i><sup>2</sup>, 
     * <sup>1</sup>&frasl;<sub>7</sub><i>a</i><sup>3</sup>.
     */
    private static final PowerBasis QUARTIC_POWER_BASIS = new PowerBasis(TWO_ONES_AND_TWO_ONE_SEVENTHS);
    
    /**
     * The power basis of <i>O</i><sub><b>Q</b>(&radic;2 + &radic;7)</sub>: 1, 
     * <i>a</i>, <i>a</i><sup>2</sup>, 
     * <sup>1</sup>&frasl;<sub>3</sub><i>a</i><sup>3</sup> + 
     * <sup>1</sup>&frasl;<sub>3</sub><i>a</i>.
     */
    private static final PowerBasis QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS = new PowerBasis(THREE_ONES_AND_ONE_THIRD, THREE_ZEROES_AND_ONE_THIRD, null);
    
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
        expResult = "1,a,a\u00B2";
        result = CUBIC_POWER_BASIS.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1,a,1/7a\u00B2,1/7a\u00B3";
        result = QUARTIC_POWER_BASIS.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = "1,a,a\u00B2,1/3a\u00B3+1/3a";
        result = QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.toString().replace(" ", "");
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
     * Test of hashCode method, of class PowerBasis.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        HashSet<Integer> hashes = new HashSet<>();
        int hash = QUADRATIC_POWER_BASIS.hashCode();
        System.out.println("Quadratic power basis hashed as " + hash);
        hashes.add(hash);
        hash = CUBIC_POWER_BASIS.hashCode();
        System.out.println("Cubic power basis hashed as " + hash);
        hashes.add(hash);
        hash = QUARTIC_POWER_BASIS.hashCode();
        System.out.println("Power basis of O_(i + sqrt(14)) hashed as " + hash);
        hashes.add(hash);
        hash = QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS.hashCode();
        System.out.println("Power basis of O_(sqrt(2) + sqrt(7)) hashed as " + hash);
        hashes.add(hash);
        assertEquals("Set of hashes should have four elements", 4, hashes.size());
    }
    
    /**
     * Test of equals method, of class PowerBasis.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        PowerBasis sameBasis = new PowerBasis(TWO_ONES);
        assertEquals(QUADRATIC_POWER_BASIS, sameBasis);
        sameBasis = new PowerBasis(THREE_ONES);
        assertEquals(CUBIC_POWER_BASIS, sameBasis);
        sameBasis = new PowerBasis(TWO_ONES_AND_TWO_ONE_SEVENTHS);
        assertEquals(QUARTIC_POWER_BASIS, sameBasis);
        sameBasis = new PowerBasis(THREE_ONES_AND_ONE_THIRD, THREE_ZEROES_AND_ONE_THIRD, null);
        assertEquals(QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS, sameBasis);
        assertNotEquals(QUADRATIC_POWER_BASIS, CUBIC_POWER_BASIS);
        assertNotEquals(CUBIC_POWER_BASIS, QUARTIC_POWER_BASIS);
        assertNotEquals(QUARTIC_POWER_BASIS, QUARTIC_POWER_BASIS_WITH_POWER_ADDITIVE_ADJUSTMENTS);
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
     * Test of getPowerMultiplicandAdditiveAdjustments method, of class 
     * PowerBasis.
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
    
    /**
     * Test of the PowerBasis constructor. The constructor should require that 
     * the arrays of Fraction objects it's passed in to all have the same 
     * length.
     */
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
