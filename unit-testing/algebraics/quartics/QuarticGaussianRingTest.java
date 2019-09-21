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

import algebraics.quadratics.ImaginaryQuadraticRing;
import fileops.PNGFileFilter;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class QuarticGaussianRingTest {
    
    private final QuarticGaussianRing ring = new QuarticGaussianRing();
    
    /**
     * Test of isPurelyReal method, of class QuarticGaussianRing.
     */
    @Test
    public void testIsPurelyReal() {
        System.out.println("isPurelyReal");
        assertFalse(ring.isPurelyReal());
    }

    /**
     * Test of discriminant method, of class QuarticGaussianRing.
     */
    @Test
    public void testDiscriminant() {
        System.out.println("discriminant");
        assertEquals(512, ring.discriminant());
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuarticGaussianRing sameRing = new QuarticGaussianRing();
        assertEquals(sameRing, ring);
        ImaginaryQuadraticRing otherRing = new ImaginaryQuadraticRing(-1);
        assertNotEquals(otherRing, ring);
        PNGFileFilter filter = new PNGFileFilter();
        assertNotEquals(filter, ring);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals(-512, ring.hashCode());
    }

    /**
     * Test of toASCIIString method, of class QuarticGaussianRing.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        assertEquals("Z[sqrt(1 + i)]", ring.toASCIIString());
    }

    /**
     * Test of toTeXString method, of class QuarticGaussianRing.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        QuarticRing.preferBlackboardBold(true);
        assertEquals("\\mathbb Z[\\sqrt{1 + i}]", ring.toTeXString());
        QuarticRing.preferBlackboardBold(false);
        assertEquals("\\textbf Z[\\sqrt{1 + i}]", ring.toTeXString());
    }

    /**
     * Test of toHTMLString method, of class QuarticGaussianRing.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        QuarticRing.preferBlackboardBold(true);
        assertEquals("\u2124[&radic;(1 + <i>i</i>)]", ring.toHTMLString());
        QuarticRing.preferBlackboardBold(false);
        assertEquals("<b>Z</b>[&radic;(1 + <i>i</i>)]", ring.toHTMLString());
    }

    /**
     * Test of toFilenameString method, of class QuarticGaussianRing.
     */
    @Test
    public void testToFilenameString() {
        System.out.println("toFilenameString");
        assertEquals("ZSQRT1I", ring.toFilenameString());
    }
    
}
