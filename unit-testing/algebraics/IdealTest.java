/*
 * Copyright (C) 2018 Alonso del Arte
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

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AL
 */
public class IdealTest {
    
    private static final QuadraticRing RING_ZI5 = new ImaginaryQuadraticRing(-5);
    private static final QuadraticRing RING_Z10 = new RealQuadraticRing(10);
    
    private static final QuadraticInteger ALG_INT_2_IN_ZI5 = new ImaginaryQuadraticInteger(2, 0, RING_ZI5);
    private static final QuadraticInteger ALG_INT_1PLUSSQRTNEG5 = new ImaginaryQuadraticInteger(1, 1, RING_ZI5);
    private static final QuadraticInteger ALG_INT_2_IN_Z10 = new RealQuadraticInteger(2, 0, RING_Z10);
    private static final QuadraticInteger ALG_INT_SQRT10 = new RealQuadraticInteger(0, 1, RING_Z10);
    
    private static Ideal idealWholeRing;
    private static Ideal idealPrincipalZi5;
    private static Ideal idealPrincipalZ10;
    private static Ideal idealSecondaryZi5;
    private static Ideal idealSecondaryZ10;
    
    public IdealTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        QuadraticInteger unit = new RealQuadraticInteger(3, 1, RING_Z10);
        idealWholeRing = new Ideal(unit);
        idealPrincipalZi5 = new Ideal(ALG_INT_2_IN_ZI5);
        idealPrincipalZ10 = new Ideal(ALG_INT_SQRT10);
        idealSecondaryZi5 = new Ideal(ALG_INT_2_IN_ZI5, ALG_INT_1PLUSSQRTNEG5);
        idealSecondaryZ10 = new Ideal(ALG_INT_2_IN_Z10, ALG_INT_SQRT10);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of norm method, of class Ideal.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
//        long expResult = 0L;
//        long result = instance.norm();
//        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPrincipal method, of class Ideal.
     */
    @Test
    public void testIsPrincipal() {
        System.out.println("isPrincipal");
        assertTrue(idealPrincipalZi5.isPrincipal());
        assertTrue(idealPrincipalZ10.isPrincipal());
        assertFalse(idealSecondaryZi5.isPrincipal());
        assertFalse(idealSecondaryZ10.isPrincipal());
    }

    /**
     * Test of isMaximal method, of class Ideal.
     */
    @Test
    public void testIsMaximal() {
        System.out.println("isMaximal");
        assertTrue(idealPrincipalZi5.isMaximal());
        assertTrue(idealPrincipalZ10.isMaximal());
        assertFalse(idealSecondaryZi5.isMaximal());
        assertFalse(idealSecondaryZ10.isMaximal());
    }

    /**
     * Test of contains method, of class Ideal.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        QuadraticInteger num = new ImaginaryQuadraticInteger(3, 0, RING_ZI5);
        assertTrue(idealSecondaryZi5.contains(num));
        num = new ImaginaryQuadraticInteger(7, 0, RING_ZI5);
        assertFalse(idealSecondaryZi5.contains(num));
    }

    /**
     * Test of getGenerators method, of class Ideal.
     */
    @Test
    public void testGetGenerators() {
        System.out.println("getGenerators");
        AlgebraicInteger[] expResult = {ALG_INT_2_IN_ZI5, ALG_INT_1PLUSSQRTNEG5};
        AlgebraicInteger[] result = idealSecondaryZi5.getGenerators();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Ideal. Spaces are desirable but not 
     * required, so the test will strip them out before the equality assertion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "\u27E82,1+\u221A(-5)\u27E9";
        String result = idealSecondaryZi5.toString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toString().replace(" ", "");
        result = idealWholeRing.toString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toASCIIString method, of class Ideal. Spaces are desirable but 
     * not required, so the test will strip them out before the equality 
     * assertion.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "(2,1+sqrt(-5))";
        String result = idealSecondaryZi5.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toASCIIString().replace(" ", "");
        result = idealWholeRing.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toTeXString method, of class Ideal. Spaces are desirable but not 
     * required, so the test will strip them out before the equality assertion.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        String expResult = "\\langle2,1+\\sqrt{-5}\\rangle";
        String result = idealSecondaryZi5.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toTeXString().replace(" ", "");
        result = idealWholeRing.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of toHTMLString method, of class Ideal. Spaces are desirable but not 
     * required, so the test will strip them out before the equality assertion.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        String expResult = "&#10216;2,1+&radic;(&minus;5)&#10217;";
        String result = idealSecondaryZi5.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toHTMLString().replace(" ", "");
        result = idealWholeRing.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
    }
    
}
