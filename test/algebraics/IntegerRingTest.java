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
package algebraics;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the IntegerRing interface.
 * @author Alonso del Arte
 */
public class IntegerRingTest {
    
    static final Random RANDOM = new Random();
    
    /**
     * Test of the toTeXStringBlackboardBold function of the IntegerRing 
     * interface. The default implementation should give the same result as 
     * {@link IntegerRing#toTeXString()}.
     */
    @Test
    public void testToTeXStringBlackboardBold() {
        System.out.println("toTeXStringBlackboardBold");
        IntegerRing ring = new MockRing();
        String expected = ring.toTeXString();
        String actual = ring.toTeXStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toHTMLStringBlackboardBold function of the IntegerRing 
     * interface. The default implementation should give the same result as 
     * {@link IntegerRing#toHTMLString()}.
     */
    @Test
    public void testToHTMLStringBlackboardBold() {
        System.out.println("toHTMLStringBlackboardBold");
        IntegerRing ring = new MockRing();
        String expected = ring.toHTMLString();
        String actual = ring.toHTMLStringBlackboardBold();
        assertEquals(expected, actual);
    }
    
}
