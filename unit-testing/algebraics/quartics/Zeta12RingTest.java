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
package algebraics.quartics;

import arithmetic.PowerBasis;
import fractions.Fraction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Zeta8Ring class.
 * @author Alonso del Arte
 */
public class Zeta12RingTest {
    
    private final Zeta12Ring ring = new Zeta12Ring();
    
    /**
     * Test of isPurelyReal method, of class Zeta12Ring.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        assertFalse(ring.isPurelyReal());
    }

    /**
     * Test of discriminant method, of class Zeta12Ring.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        assertEquals(144, ring.discriminant());
    }

    /**
     * Test of getPowerBasis method, of class Zeta12Ring. The power basis should 
     * be 1, <i>a</i>, <i>a</i><sup>2</sup>, <i>a</i><sup>3</sup>, where 
     * <i>a</i> stands for &zeta;<sub>12</sub>.
     */
    @Test
    public void testGetPowerBasis() {
        System.out.println("getPowerBasis");
        Fraction oneAsFraction = new Fraction(1);
        Fraction[] fourOnes = {oneAsFraction, oneAsFraction, oneAsFraction, oneAsFraction};
        PowerBasis expResult = new PowerBasis(fourOnes);
        PowerBasis result = ring.getPowerBasis();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Zeta12Ring.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("O_Q(\u03B6\u2081\u2082)", ring.toString());
    }

    /**
     * Test of toASCIIString method, of class Zeta12Ring.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        assertEquals("O_Q(zeta_12)", ring.toASCIIString());
    }

    /**
     * Test of toTeXString method, of class Zeta12Ring.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        QuarticRing.preferBlackboardBold(true);
        assertEquals("\\mathcal O_{\\mathbb Q(\\zeta_{12})}", ring.toTeXString());
        QuarticRing.preferBlackboardBold(false);
        assertEquals("\\mathcal O_{\\textbf Q(\\zeta_{12})}", ring.toTeXString());
    }

    /**
     * Test of toHTMLString method, of class Zeta12Ring.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        QuarticRing.preferBlackboardBold(true);
        assertEquals("<i>O</i><sub>\u211A(&zeta;<sub>12</sub>)</sub>", ring.toHTMLString());
        QuarticRing.preferBlackboardBold(false);
        assertEquals("<i>O</i><sub><b>Q</b>(&zeta;<sub>12</sub>)</sub>", ring.toHTMLString());
    }

    /**
     * Test of toFilenameString method, of class Zeta12Ring.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        assertEquals("OQ_ZETA12", ring.toFilenameString());
    }
    
}
