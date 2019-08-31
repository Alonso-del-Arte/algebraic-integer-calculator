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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Zeta8Ring class.
 * @author Alonso del Arte
 */
public class Zeta8RingTest {
    
    private final Zeta8Ring ring = new Zeta8Ring();
    
    /**
     * Test of isPurelyReal method, of class Zeta8Ring.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        assertFalse(ring.isPurelyReal());
    }

    /**
     * Test of discriminant method, of class Zeta8Ring.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        assertEquals(256, ring.discriminant());
    }

    /**
     * Test of toString method, of class Zeta8Ring.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("O_Q(\u03B6\u2088)", ring.toString());
    }

    /**
     * Test of toASCIIString method, of class Zeta8Ring.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        assertEquals("O_Q(zeta_8)", ring.toASCIIString());
    }

    /**
     * Test of toTeXString method, of class Zeta8Ring.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        QuarticRing.preferBlackboardBold(true);
        assertEquals("\\mathcal O_{\\mathbb Q(\\zeta_8)}", ring.toTeXString());
        QuarticRing.preferBlackboardBold(false);
        assertEquals("\\mathcal O_{\\textbf Q(\\zeta_8)}", ring.toTeXString());
    }

    /**
     * Test of toHTMLString method, of class Zeta8Ring.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        QuarticRing.preferBlackboardBold(true);
        assertEquals("<i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>", ring.toHTMLString());
        QuarticRing.preferBlackboardBold(false);
        assertEquals("<i>O</i><sub><b>Q</b>(&zeta;<sub>8</sub>)</sub>", ring.toHTMLString());
    }

    /**
     * Test of toFilenameString method, of class Zeta8Ring.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        assertEquals("OQ_ZETA8", ring.toFilenameString());
    }
    
}
