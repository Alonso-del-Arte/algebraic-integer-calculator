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

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Ideal class. In setup, five ideals from quadratic integer rings 
 * are constructed. Perhaps a future version of this test class will also use 
 * ideals from rings of higher degree. As support for those is added, in their 
 * respective classes, tests will need to be added to this test class, so as to 
 * develop Ideal accordingly.
 * @author Alonso del Arte
 */
public class IdealTest {
    
    private static final QuadraticRing RING_ZI5 = new ImaginaryQuadraticRing(-5);
    private static final QuadraticRing RING_Z10 = new RealQuadraticRing(10);
    
    private static final QuadraticInteger ALG_INT_2_IN_ZI5 = new ImaginaryQuadraticInteger(2, 0, RING_ZI5);
    private static final QuadraticInteger ALG_INT_1PLUSSQRTNEG5 = new ImaginaryQuadraticInteger(1, 1, RING_ZI5);
    private static final QuadraticInteger ALG_INT_2_IN_Z10 = new RealQuadraticInteger(2, 0, RING_Z10);
    private static final QuadraticInteger ALG_INT_SQRT10 = new RealQuadraticInteger(0, 1, RING_Z10);
    
    /**
     * In setUpClass, this will be initialized to &#10216;3 + &radic;10&#10217;. 
     * Since the generating number is a unit (the fundamental unit, no less), 
     * this ideal should be found to be the whole of <b>Z</b>[&radic;10].
     */
    private static Ideal idealWholeRing;
    
    /**
     * An ideal that will be initialized to &#10216;2&#10217; from 
     * <b>Z</b>[&radic;&minus;5] in setUpClass.
     */
    private static Ideal idealPrincipalZi5;
    
    /**
     * An ideal that will be initialized to &#10216;&radic;10&#10217; in 
     * setUpClass.
     */
    private static Ideal idealPrincipalZ10;
    
    /**
     * An ideal that will be initialized to &#10216;2, 1 + 
     * &radic;&minus;5&#10217; in setUpClass.
     */
    private static Ideal idealSecondaryZi5;
    
    /**
     * An ideal that will be initialized to &#10216;2, &radic;10&#10217; in 
     * setUpClass.
     */
    private static Ideal idealSecondaryZ10;
    
    @BeforeClass
    public static void setUpClass() {
        QuadraticInteger unit = new RealQuadraticInteger(3, 1, RING_Z10);
        idealWholeRing = new Ideal(unit);
        idealPrincipalZi5 = new Ideal(ALG_INT_2_IN_ZI5);
        idealPrincipalZ10 = new Ideal(ALG_INT_SQRT10);
        idealSecondaryZi5 = new Ideal(ALG_INT_2_IN_ZI5, ALG_INT_1PLUSSQRTNEG5);
        idealSecondaryZ10 = new Ideal(ALG_INT_2_IN_Z10, ALG_INT_SQRT10);
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
        String assertionMessage = idealPrincipalZi5.toString() + " should be found to be a principal ideal.";
        assertTrue(assertionMessage, idealPrincipalZi5.isPrincipal());
        assertionMessage = idealPrincipalZ10.toString() + " should be found to be a principal ideal.";
        assertTrue(assertionMessage, idealPrincipalZ10.isPrincipal());
        assertionMessage = idealSecondaryZi5.toString() + " should not be found to be a principal ideal.";
        assertFalse(assertionMessage, idealSecondaryZi5.isPrincipal());
        assertionMessage = idealSecondaryZ10.toString() + " should not be found to be a principal ideal.";
        assertFalse(assertionMessage, idealSecondaryZ10.isPrincipal());
        QuadraticInteger ramified = new ImaginaryQuadraticInteger(5, 0, RING_ZI5);
        QuadraticInteger ramifier = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
        Ideal testIdeal = new Ideal(ramified, ramifier);
        assertionMessage = testIdeal.toString() + " should be found to be a principal ideal.";
        assertTrue(assertionMessage, testIdeal.isPrincipal());
        ramified = new RealQuadraticInteger(10, 0, RING_Z10);
        ramifier = new RealQuadraticInteger(0, 1, RING_Z10);
        testIdeal = new Ideal(ramified, ramifier);
        assertionMessage = testIdeal.toString() + " should be found to be a principal ideal.";
        assertTrue(assertionMessage, testIdeal.isPrincipal());
    }

    /**
     * Test of isMaximal method, of class Ideal.
     */
    @Test
    public void testIsMaximal() {
        System.out.println("isMaximal");
        String assertionMessage = idealSecondaryZi5.toString() + " should be found to be a maximal ideal.";
        assertTrue(assertionMessage, idealSecondaryZi5.isMaximal());
        QuadraticInteger num = new ImaginaryQuadraticInteger(11, 0, RING_ZI5);
        Ideal testIdeal = new Ideal(num);
        assertionMessage = testIdeal.toString() + " in " + RING_ZI5.toString() + " should be found to be a maximal ideal.";
        assertTrue(assertionMessage, testIdeal.isMaximal());
        num = new RealQuadraticInteger(11, 0, RING_Z10);
        testIdeal = new Ideal(num);
        assertionMessage = testIdeal.toString() + " in " + RING_Z10.toString() + " should be found to be a maximal ideal.";
        assertTrue(assertionMessage, testIdeal.isMaximal());
        assertionMessage = idealSecondaryZ10.toString() + " should be found to be a maximal ideal.";
        assertTrue(assertionMessage, idealSecondaryZ10.isMaximal());
        assertionMessage = idealPrincipalZi5.toString() + " should not be found to be a maximal ideal.";
        assertFalse(assertionMessage, idealPrincipalZi5.isMaximal());
        assertionMessage = idealPrincipalZi5.toString() + " should not be found to be a maximal ideal.";
        assertFalse(assertionMessage, idealPrincipalZ10.isMaximal());
        num = new ImaginaryQuadraticInteger(3, 0, RING_ZI5);
        testIdeal = new Ideal(num);
        assertionMessage = testIdeal.toString() + " in " + RING_ZI5.toString() + " should not be found to be a maximal ideal.";
        assertTrue(assertionMessage, testIdeal.isMaximal());
        num = new RealQuadraticInteger(3, 0, RING_Z10);
        testIdeal = new Ideal(num);
        assertionMessage = testIdeal.toString() + " in " + RING_Z10.toString() + " should not be found to be a maximal ideal.";
        assertTrue(assertionMessage, testIdeal.isMaximal());    }

    /**
     * Test of contains method, of class Ideal.
     */
    @Test
    public void testContains() {
        System.out.println("contains(AlgebraicInteger)");
        QuadraticInteger num = new ImaginaryQuadraticInteger(3, 0, RING_ZI5);
        String assertionMessage = idealSecondaryZi5.toString() + " should be found to contain " + num.toString() + ".";
        assertTrue(assertionMessage, idealSecondaryZi5.contains(num));
        num = new ImaginaryQuadraticInteger(7, 0, RING_ZI5);
        assertionMessage = idealSecondaryZi5.toString() + " should not be found to contain " + num.toString() + ".";
        assertFalse(assertionMessage, idealSecondaryZi5.contains(num));
        num = new RealQuadraticInteger(10, 3, RING_Z10);
        assertionMessage = idealSecondaryZ10.toString() + " should be found to contain " + num.toString() + ".";
        assertTrue(assertionMessage, idealSecondaryZ10.contains(num));
        num = new RealQuadraticInteger(3, 1, RING_Z10);
        assertionMessage = idealSecondaryZ10.toString() + " should not be found to contain " + num.toString() + ".";
        assertFalse(assertionMessage, idealSecondaryZ10.contains(num));
        QuadraticRing nonContainingRing = new ImaginaryQuadraticRing(-10);
        num = new ImaginaryQuadraticInteger(1, 1, nonContainingRing);
        assertionMessage = idealSecondaryZi5.toString() + " should not be found to contain " + num.toString() + " from " + nonContainingRing.toString() + ".";
        assertFalse(assertionMessage, idealSecondaryZi5.contains(num));
        nonContainingRing = new RealQuadraticRing(5);
        num = new RealQuadraticInteger(1, 1, nonContainingRing);
        assertionMessage = idealSecondaryZ10.toString() + " should not be found to contain " + num.toString() + " from " + nonContainingRing.toString() + ".";
        assertFalse(assertionMessage, idealSecondaryZ10.contains(num));
        System.out.println("contains(Ideal)");
        assertionMessage = idealSecondaryZi5.toString() + " should be found to contain " + idealPrincipalZi5.toString() + ".";
        assertTrue(assertionMessage, idealSecondaryZi5.contains(idealPrincipalZi5));
        assertionMessage = idealPrincipalZi5.toString() + " should not be found to contain " + idealSecondaryZi5.toString() + ".";
        assertFalse(assertionMessage, idealPrincipalZi5.contains(idealSecondaryZi5));
    }

    /**
     * Test of getGenerators method, of class Ideal.
     */
    @Test
    public void testGetGenerators() {
        System.out.println("getGenerators");
        AlgebraicInteger[] expResultTwoGens = {ALG_INT_2_IN_ZI5, ALG_INT_1PLUSSQRTNEG5};
        AlgebraicInteger[] result = idealSecondaryZi5.getGenerators();
        assertArrayEquals(expResultTwoGens, result);
        expResultTwoGens[0] = ALG_INT_2_IN_Z10;
        expResultTwoGens[1] = ALG_INT_SQRT10;
        result = idealSecondaryZ10.getGenerators();
        assertArrayEquals(expResultTwoGens, result);
        AlgebraicInteger[] expResultOneGen = {ALG_INT_2_IN_ZI5};
        result = idealPrincipalZi5.getGenerators();
        assertArrayEquals(expResultOneGen, result);
        expResultOneGen[0] = ALG_INT_SQRT10;
        result = idealPrincipalZ10.getGenerators();
        assertArrayEquals(expResultOneGen, result);
        expResultOneGen[0] = new RealQuadraticInteger(1, 0, RING_Z10);
        result = idealWholeRing.getGenerators();
        assertArrayEquals(expResultOneGen, result);
    }
    
    /**
     * Test of hashCode method, of class Ideal. The main requirement is that two 
     * distinct ideals from the same ring hash differently. If an ideal from one 
     * ring hashes the same as another from a different ring, that's acceptable. 
     * Lastly, an ideal equal to a whole ring should hash the same as that ring.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        System.out.println(idealWholeRing.toASCIIString() + " hashed as " + idealWholeRing.hashCode());
        System.out.println(idealPrincipalZi5.toASCIIString() + " hashed as " + idealPrincipalZi5.hashCode());
        System.out.println(idealSecondaryZi5.toASCIIString() + " hashed as " + idealSecondaryZi5.hashCode());
        System.out.println(idealPrincipalZ10.toASCIIString() + " hashed as " + idealPrincipalZ10.hashCode());
        System.out.println(idealSecondaryZ10.toASCIIString() + " hashed as " + idealSecondaryZ10.hashCode());
        assertNotEquals(idealPrincipalZi5.hashCode(), idealSecondaryZi5.hashCode());
        assertNotEquals(idealPrincipalZ10.hashCode(), idealSecondaryZ10.hashCode());
        assertEquals(idealWholeRing.hashCode(), RING_Z10.hashCode());
    }
    
    /**
     * Test of equals method, of class Ideal. After some obvious equalities, the 
     * commutativity of the generators will be tested. This means that 
     * &#10216;2, 1 + &radic;&minus;5&#10217; = &#10216;1 + &radic;&minus;5, 
     * 2&#10217; and &#10216;2, &radic;10&#10217; = &#10216;&radic;10, 
     * 2&#10217;.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals(idealPrincipalZi5, idealPrincipalZi5);
        assertEquals(idealPrincipalZ10, idealPrincipalZ10);
        assertNotEquals(idealPrincipalZi5, idealPrincipalZ10);
        assertNotEquals(idealPrincipalZ10, idealPrincipalZi5);
        assertEquals(idealSecondaryZi5, idealSecondaryZi5);
        assertEquals(idealSecondaryZ10, idealSecondaryZ10);
        assertNotEquals(idealSecondaryZi5, idealSecondaryZ10);
        assertNotEquals(idealSecondaryZ10, idealSecondaryZi5);
        assertNotEquals(idealPrincipalZi5, idealSecondaryZi5);
        assertNotEquals(idealPrincipalZ10, idealSecondaryZ10);
        assertNotEquals(idealPrincipalZi5, idealSecondaryZ10);
        assertNotEquals(idealPrincipalZ10, idealSecondaryZi5);
        assertNotEquals(idealSecondaryZi5, idealPrincipalZi5);
        assertNotEquals(idealSecondaryZ10, idealPrincipalZ10);
        assertNotEquals(idealSecondaryZi5, idealPrincipalZ10);
        assertNotEquals(idealSecondaryZ10, idealPrincipalZi5);
        Ideal testIdeal = new Ideal(ALG_INT_1PLUSSQRTNEG5, ALG_INT_2_IN_ZI5);
        assertEquals(idealSecondaryZi5, testIdeal);
        testIdeal = new Ideal(ALG_INT_SQRT10, ALG_INT_2_IN_Z10);
        assertEquals(idealSecondaryZ10, testIdeal);
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
    
    /**
     * Test of Ideal constructor. The main thing we're testing here is that an 
     * invalid argument triggers an {@link IllegalArgumentException}.
     */
    @Test
    public void testConstructor() {
        System.out.println("Ideal (constructor)");
        Ideal testIdeal = new Ideal(ALG_INT_1PLUSSQRTNEG5);
        System.out.println("Created the ideal " + testIdeal.toASCIIString() + " without problem.");
        try {
            testIdeal = new Ideal(ALG_INT_1PLUSSQRTNEG5, ALG_INT_SQRT10);
            System.out.println("Somehow created " + testIdeal.toASCIIString() + " without problem.");
            fail("Attempting to create ideal generated from numbers of different rings should have triggered IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Attempt to create ideal generated from numbers of different rings correctly triggered IllegalArgumentException.");
            System.out.println("\"" + iae.getMessage() + "\"");
        }
        RealQuadraticRing ring = new RealQuadraticRing(29);
        testIdeal = new Ideal(ring);
        System.out.println("Created an ideal, " + testIdeal.toASCIIString() + " mathematically equal to the ring " + ring.toASCIIString() + " without problem.");
    }
    
}
