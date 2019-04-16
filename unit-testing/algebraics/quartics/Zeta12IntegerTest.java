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
package algebraics.quartics;

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
public class Zeta12IntegerTest {
    
    public Zeta12IntegerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of algebraicDegree method, of class Zeta12Integer.
     */
    @Test
    public void testAlgebraicDegree() {
        System.out.println("algebraicDegree");
        Zeta12Integer instance = new Zeta12Integer();
        int expResult = 0;
        int result = instance.algebraicDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trace method, of class Zeta12Integer.
     */
    @Test
    public void testTrace() {
        System.out.println("trace");
        Zeta12Integer instance = new Zeta12Integer();
        long expResult = 0L;
        long result = instance.trace();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of norm method, of class Zeta12Integer.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        Zeta12Integer instance = new Zeta12Integer();
        long expResult = 0L;
        long result = instance.norm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomial method, of class Zeta12Integer.
     */
    @Test
    public void testMinPolynomial() {
        System.out.println("minPolynomial");
        Zeta12Integer instance = new Zeta12Integer();
        long[] expResult = null;
        long[] result = instance.minPolynomial();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialString method, of class Zeta12Integer.
     */
    @Test
    public void testMinPolynomialString() {
        System.out.println("minPolynomialString");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.minPolynomialString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialStringTeX method, of class Zeta12Integer.
     */
    @Test
    public void testMinPolynomialStringTeX() {
        System.out.println("minPolynomialStringTeX");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.minPolynomialStringTeX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minPolynomialStringHTML method, of class Zeta12Integer.
     */
    @Test
    public void testMinPolynomialStringHTML() {
        System.out.println("minPolynomialStringHTML");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.minPolynomialStringHTML();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Zeta12Integer.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toASCIIString method, of class Zeta12Integer.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.toASCIIString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTeXString method, of class Zeta12Integer.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.toTeXString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toHTMLString method, of class Zeta12Integer.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        Zeta12Integer instance = new Zeta12Integer();
        String expResult = "";
        String result = instance.toHTMLString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abs method, of class Zeta12Integer.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        Zeta12Integer instance = new Zeta12Integer();
        double expResult = 0.0;
        double result = instance.abs();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRealPartNumeric method, of class Zeta12Integer.
     */
    @Test
    public void testGetRealPartNumeric() {
        System.out.println("getRealPartNumeric");
        Zeta12Integer instance = new Zeta12Integer();
        double expResult = 0.0;
        double result = instance.getRealPartNumeric();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImagPartNumeric method, of class Zeta12Integer.
     */
    @Test
    public void testGetImagPartNumeric() {
        System.out.println("getImagPartNumeric");
        Zeta12Integer instance = new Zeta12Integer();
        double expResult = 0.0;
        double result = instance.getImagPartNumeric();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of angle method, of class Zeta12Integer.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        Zeta12Integer instance = new Zeta12Integer();
        double expResult = 0.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
