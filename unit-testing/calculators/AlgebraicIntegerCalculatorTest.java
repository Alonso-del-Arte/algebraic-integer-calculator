/*
 * Copyright (C) 2021 Alonso del Arte
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
package calculators;

import algebraics.BadRing;
import algebraics.IntegerRing;
import algebraics.UnsupportedNumberDomainException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the AlgebraicIntegerCalculator class.
 * @author Alonso del Arte
 */
public class AlgebraicIntegerCalculatorTest {
    
    @Test
    public void testConstructorRejectsNullRing() {
        try {
            AlgebraicIntegerCalculator calculator 
                    = new AlgebraicIntegerCalculator(null);
            String msg = "Should not have been able to create " 
                    + calculator.toString() + " with null ring";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to use null ring correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null ring parameter";
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsUnsupportedRing() {
        IntegerRing unsupRing = new BadRing();
        try {
            AlgebraicIntegerCalculator calculator 
                    = new AlgebraicIntegerCalculator(unsupRing);
            String msg = "Should not have been able to create " 
                    + calculator.toString() + " with " + unsupRing.toString();
            fail(msg);
        } catch (UnsupportedNumberDomainException unde) {
            System.out.println("Trying to use " + unsupRing 
                    + " correctly caused UnsupportedNumberDomainException");
            System.out.println("\"" + unde.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for unsupported ring parameter";
            fail(msg);
        }
    }
    
}
