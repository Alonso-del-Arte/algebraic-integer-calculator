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
package fractions;

import org.junit.Test;
import static org.junit.Assert.*;



/**
 * Tests of the FractionRange class.
 * @author Alonso del Arte
 */
public class FractionRangeTest {

    private final Fraction start = new Fraction(-1, 2);

    private final Fraction end = new Fraction(15, 8);

    /**
     * Test of length method, of class FractionRange.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        FractionRange range = new FractionRange(start, end);
        int expResult = 21;
        int result = range.length();
        assertEquals(expResult, result);
    }

    /**
     * Test of apply method, of class FractionRange.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        FractionRange range = new FractionRange(start, end);
        Fraction expResult = new Fraction(-3, 8);
        Fraction result = range.apply(1);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        Fraction begin = new Fraction(5, 8);
        Fraction finish = new Fraction(7, 8);
        Fraction invalidStep = new Fraction(3, 2);
        try {
            FractionRange range = new FractionRange(begin, finish, invalidStep);
            String failMsg = "Should not have been able to construct FractionRange " + range.toString() + " with invalid step " + invalidStep.toString();
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to construct FractionRange with invalid step " + invalidStep.toString() + " correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is wrong exception for trying to construct FractionRange with invalid step " + invalidStep.toString() + ".";
            fail(failMsg);
        }
    }

}
