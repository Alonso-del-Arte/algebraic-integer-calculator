/*
 * Copyright (C) 2023 Alonso del Arte
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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Ideal class. Five ideals from two quadratic integer rings are 
 * used here. Rings that are not unique factorization domains were chosen 
 * precisely because they contain ideals that are not principal ideals.
 * <p>Perhaps a future version of this test class will also use ideals from 
 * rings of higher degree. As support for those is added, in their respective 
 * classes, tests will need to be added to this test class, so as to develop 
 * Ideal accordingly.</p>
 * @author Alonso del Arte
 */
public class IdealTest {
    
    /**
     * The ring <b>Z</b>[&radic;&minus;5]. It is not a unique factorization 
     * domain for its numbers, but all ideals can be factored uniquely into 
     * ideals.
     */
    private static final QuadraticRing RING_ZI5 
            = new ImaginaryQuadraticRing(-5);
    
    /**
     * The ring <b>Z</b>[&radic;10]. It is not a unique factorization domain for 
     * its numbers, but all ideals can be factored uniquely into ideals.
     */
    private static final QuadraticRing RING_Z10 = new RealQuadraticRing(10);
    
    /**
     * The irreducible number 2 in <b>Z</b>[&radic;&minus;5]. Except for units 
     * and associates, 2 is not divisible by any other number in this ring. 
     * However, there are several pairs of numbers <i>a</i> and <i>b</i> such 
     * that <i>ab</i> is divisible by 2 but neither <i>a</i> nor <i>b</i> are, 
     * the most famous example being (1 &minus; &radic;&minus;5)(1 + 
     * &radic;&minus;5) = 6.
     */
    private static final QuadraticInteger ALG_INT_2_IN_ZI5 
            = new ImaginaryQuadraticInteger(2, 0, RING_ZI5);
    
    /**
     * The irreducible number 1 + &radic;&minus;5 in <b>Z</b>[&radic;&minus;5]. 
     * Except for units and associates, this number is not divisible by any 
     * other number in this ring.
     */
    private static final QuadraticInteger ALG_INT_1PLUSSQRTNEG5 
            = new ImaginaryQuadraticInteger(1, 1, RING_ZI5);

    /**
     * The irreducible number 2 in <b>Z</b>[&radic;10]. Except for units and 
     * associates, 2 is not divisible by any other number in this ring. However, 
     * there are several pairs of numbers <i>a</i> and <i>b</i> such that 
     * <i>ab</i> is divisible by 2 but neither <i>a</i> nor <i>b</i> are, the 
     * most famous example being (&radic;10)<sup>2</sup> = 10.
     */
    private static final QuadraticInteger ALG_INT_2_IN_Z10 
            = new RealQuadraticInteger(2, 0, RING_Z10);
    
    /**
     * The irreducible number &radic;10 in <b>Z</b>[&radic;10]. Except for units 
     * and associates, this number is not divisible by any other number in this 
     * ring.
     */
    private static final QuadraticInteger ALG_INT_SQRT10 
            = new RealQuadraticInteger(0, 1, RING_Z10);
    
    /**
     * The number 3 + &radic;10 in <b>Z</b>[&radic;10]. This number of norm 
     * &minus;1 is the fundamental unit of <b>Z</b>[&radic;10].
     */
    private static final QuadraticInteger ALG_INT_Z10_FUNDAMENTAL_UNIT 
            = new RealQuadraticInteger(3, 1, RING_Z10);
    
    /**
     * The ideal &#10216;3 + &radic;10&#10217;. Since the generating number is a 
     * unit (the fundamental unit, no less), this ideal should be found to be 
     * the whole of <b>Z</b>[&radic;10].
     */
    private static final Ideal IDEAL_WHOLE_RING 
            = new Ideal(ALG_INT_Z10_FUNDAMENTAL_UNIT);
    
    /**
     * The ideal &#10216;2&#10217; from <b>Z</b>[&radic;&minus;5]. Although 2 is 
     * irreducible in this ring, the ideal is not maximal because it is 
     * contained in &#10216;2, 1 + &radic;&minus;5&#10217;. The ideal is a 
     * principal, however.
     */
    private static final Ideal IDEAL_PRINCIPAL_ZI5 
            = new Ideal(ALG_INT_2_IN_ZI5);
    
    /**
     * The ideal &#10216;&radic;10&#10217; from <b>Z</b>[&radic;10]. Although 
     * &radic;10 is irreducible in this ring, the ideal is not maximal because 
     * it is contained in &#10216;2, &radic;10&#10217;. The ideal is a principal
     * ideal, however.
     */
    private static final Ideal IDEAL_PRINCIPAL_Z10 = new Ideal(ALG_INT_SQRT10);
    
    /**
     * The ideal &#10216;2, 1 + &radic;&minus;5&#10217; from 
     * <b>Z</b>[&radic;&minus;5]. This is clearly not a principal ideal, but it 
     * is a maximal ideal.
     */
    private static final Ideal IDEAL_SECONDARY_ZI5 
            = new Ideal(ALG_INT_2_IN_ZI5, ALG_INT_1PLUSSQRTNEG5);
    
    /**
     * The ideal &#10216;2, &radic;10&#10217; from <b>Z</b>[&radic;10]. This is 
     * clearly not a principal ideal, but it is a maximal ideal.
     */
    private static final Ideal IDEAL_SECONDARY_Z10 
            = new Ideal(ALG_INT_2_IN_Z10, ALG_INT_SQRT10);
    
    /**
     * Test of the norm function, of the Ideal class.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        long expected = 1L;
        long actual = IDEAL_WHOLE_RING.norm();
        assertEquals(expected, actual);
        expected = 2L;
        actual = IDEAL_SECONDARY_ZI5.norm();
        assertEquals(expected, actual);
        actual = IDEAL_SECONDARY_Z10.norm();
        assertEquals(expected, actual);
        expected = 4L;
        actual = IDEAL_PRINCIPAL_ZI5.norm();
        assertEquals(expected, actual);
        expected = 10L;
        actual = IDEAL_PRINCIPAL_Z10.norm();
        assertEquals(expected, actual);
    }

    /**
     * Test of the isPrincipal function, of the Ideal class.
     */
    @Test
    public void testIsPrincipal() {
        System.out.println("isPrincipal");
        String affirmativeMsgPart = " should be found to be a principal ideal";
        String msg = IDEAL_PRINCIPAL_ZI5.toString() + affirmativeMsgPart;
        assert IDEAL_PRINCIPAL_ZI5.isPrincipal() : msg;
        msg = IDEAL_PRINCIPAL_Z10.toString() + affirmativeMsgPart;
        assert IDEAL_PRINCIPAL_Z10.isPrincipal() : msg;
        String negativeMsgPart = " should not be found to be a principal ideal";
        msg = IDEAL_SECONDARY_ZI5.toString() + negativeMsgPart;
        assert !IDEAL_SECONDARY_ZI5.isPrincipal() : msg;
        msg = IDEAL_SECONDARY_Z10.toString() + negativeMsgPart;
        assert !IDEAL_SECONDARY_Z10.isPrincipal() : msg;
        QuadraticInteger ramified = new ImaginaryQuadraticInteger(5, 0, 
                RING_ZI5);
        QuadraticInteger ramifier = new ImaginaryQuadraticInteger(0, 1, 
                RING_ZI5);
        Ideal testIdeal = new Ideal(ramified, ramifier);
        msg = testIdeal.toString() + affirmativeMsgPart;
        assert testIdeal.isPrincipal() : msg;
        ramified = new RealQuadraticInteger(10, 0, RING_Z10);
        ramifier = new RealQuadraticInteger(0, 1, RING_Z10);
        testIdeal = new Ideal(ramified, ramifier);
        msg = testIdeal.toString() + affirmativeMsgPart;
        assert testIdeal.isPrincipal() : msg;
    }

    /**
     * Test of the isMaximal function, of the Ideal class.
     */
    @Test
    public void testIsMaximal() {
        System.out.println("isMaximal");
        String affirmativeMsgPart = " should be found to be a maximal ideal";
        String msg = IDEAL_SECONDARY_ZI5.toString() + affirmativeMsgPart;
        assert IDEAL_SECONDARY_ZI5.isMaximal() : msg;
        QuadraticInteger num = new ImaginaryQuadraticInteger(11, 0, RING_ZI5);
        Ideal testIdeal = new Ideal(num);
        msg = testIdeal.toString() + " in " + RING_ZI5.toString() 
                + affirmativeMsgPart;
        assert testIdeal.isMaximal() : msg;
        num = new RealQuadraticInteger(11, 0, RING_Z10);
        testIdeal = new Ideal(num);
        msg = testIdeal.toString() + " in " + RING_Z10.toString() 
                + affirmativeMsgPart;
        assert testIdeal.isMaximal() : msg;
        msg = IDEAL_SECONDARY_Z10.toString() + affirmativeMsgPart;
        assert IDEAL_SECONDARY_Z10.isMaximal() : msg;
        String negativeMsgPart = " should not be found to be a maximal ideal";
        msg = IDEAL_PRINCIPAL_ZI5.toString() + negativeMsgPart;
        assert !IDEAL_PRINCIPAL_ZI5.isMaximal() : msg;
        msg = IDEAL_PRINCIPAL_ZI5.toString() + negativeMsgPart;
        assert !IDEAL_PRINCIPAL_Z10.isMaximal() : msg;
        num = new ImaginaryQuadraticInteger(3, 0, RING_ZI5);
        testIdeal = new Ideal(num);
        msg = testIdeal.toString() + " in " + RING_ZI5.toString() 
                + negativeMsgPart;
        assert !testIdeal.isMaximal() : msg;
        num = new RealQuadraticInteger(3, 0, RING_Z10);
        testIdeal = new Ideal(num);
        msg = testIdeal.toString() + " in " + RING_Z10.toString() 
                + negativeMsgPart;
        assert !testIdeal.isMaximal() : msg;
    }
    
    // TODO: Finish writing this test
//    @Test
    public void testContains() {
        System.out.println("contains");
        fail("Haven't finished writing test");
    }

    /**
     * Test of the contains function, of the Ideal class.
     */
    @Test
    public void testContainsAlgebraicInteger() {
        QuadraticInteger num = new ImaginaryQuadraticInteger(3, 0, RING_ZI5);
        String affirmativeMsgPart = " should be found to contain ";
        String msg = IDEAL_SECONDARY_ZI5.toString() + affirmativeMsgPart 
                + num.toString();
        assert IDEAL_SECONDARY_ZI5.contains(num) : msg;
        num = new ImaginaryQuadraticInteger(7, 0, RING_ZI5);
        String negativeMsgPart = " should not be found to contain ";
        msg = IDEAL_SECONDARY_ZI5.toString() + negativeMsgPart 
                + num.toString();
        assert !IDEAL_SECONDARY_ZI5.contains(num) : msg;
        num = new RealQuadraticInteger(10, 3, RING_Z10);
        msg = IDEAL_SECONDARY_Z10.toString() + affirmativeMsgPart 
                + num.toString();
        assert IDEAL_SECONDARY_Z10.contains(num) : msg;
        num = new RealQuadraticInteger(3, 1, RING_Z10);
        msg = IDEAL_SECONDARY_Z10.toString() + negativeMsgPart + num.toString();
        assert !IDEAL_SECONDARY_Z10.contains(num) : msg;
        QuadraticRing nonContainingRing = new ImaginaryQuadraticRing(-10);
        num = new ImaginaryQuadraticInteger(1, 1, nonContainingRing);
        msg = IDEAL_SECONDARY_ZI5.toString() + negativeMsgPart + num.toString() 
                + " from " + nonContainingRing.toString();
        assert !IDEAL_SECONDARY_ZI5.contains(num) : msg;
        nonContainingRing = new RealQuadraticRing(5);
        num = new RealQuadraticInteger(1, 1, nonContainingRing);
        msg = IDEAL_SECONDARY_Z10.toString() + negativeMsgPart + num.toString() 
                + " from " + nonContainingRing.toString();
        assert !IDEAL_SECONDARY_Z10.contains(num) : msg;
        System.out.println("contains(Ideal)");
        msg = IDEAL_SECONDARY_ZI5.toString() + affirmativeMsgPart 
                + IDEAL_PRINCIPAL_ZI5.toString();
        assert IDEAL_SECONDARY_ZI5.contains(IDEAL_PRINCIPAL_ZI5) : msg;
        msg = IDEAL_PRINCIPAL_ZI5.toString() + negativeMsgPart 
                + IDEAL_SECONDARY_ZI5.toString();
        assert !IDEAL_PRINCIPAL_ZI5.contains(IDEAL_SECONDARY_ZI5) : msg;
    }

    /**
     * Test of the getGenerators function, of the Ideal class.
     */
    @Test
    public void testGetGenerators() {
        System.out.println("getGenerators");
        AlgebraicInteger[] expResultTwoGens = {ALG_INT_2_IN_ZI5, 
            ALG_INT_1PLUSSQRTNEG5};
        AlgebraicInteger[] result = IDEAL_SECONDARY_ZI5.getGenerators();
        assertArrayEquals(expResultTwoGens, result);
        expResultTwoGens[0] = ALG_INT_2_IN_Z10;
        expResultTwoGens[1] = ALG_INT_SQRT10;
        result = IDEAL_SECONDARY_Z10.getGenerators();
        assertArrayEquals(expResultTwoGens, result);
        AlgebraicInteger[] expResultOneGen = {ALG_INT_2_IN_ZI5};
        result = IDEAL_PRINCIPAL_ZI5.getGenerators();
        assertArrayEquals(expResultOneGen, result);
        expResultOneGen[0] = ALG_INT_SQRT10;
        result = IDEAL_PRINCIPAL_Z10.getGenerators();
        assertArrayEquals(expResultOneGen, result);
        expResultOneGen[0] = new RealQuadraticInteger(1, 0, RING_Z10);
        result = IDEAL_WHOLE_RING.getGenerators();
        assertArrayEquals(expResultOneGen, result);
    }
    
    /**
     * Test of the hashCode function, of the Ideal class. The main requirement 
     * is that two distinct ideals from the same ring hash differently. If an 
     * ideal from one ring hashes the same as another from a different ring, 
     * that's acceptable. Lastly, an ideal equal to a whole ring should hash the 
     * same as that ring.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        System.out.println(IDEAL_WHOLE_RING.toASCIIString() + " hashed as " 
                + IDEAL_WHOLE_RING.hashCode());
        System.out.println(IDEAL_PRINCIPAL_ZI5.toASCIIString() + " hashed as " 
                + IDEAL_PRINCIPAL_ZI5.hashCode());
        System.out.println(IDEAL_SECONDARY_ZI5.toASCIIString() + " hashed as " 
                + IDEAL_SECONDARY_ZI5.hashCode());
        System.out.println(IDEAL_PRINCIPAL_Z10.toASCIIString() + " hashed as " 
                + IDEAL_PRINCIPAL_Z10.hashCode());
        System.out.println(IDEAL_SECONDARY_Z10.toASCIIString() + " hashed as " 
                + IDEAL_SECONDARY_Z10.hashCode());
        assertNotEquals(IDEAL_PRINCIPAL_ZI5.hashCode(), 
                IDEAL_SECONDARY_ZI5.hashCode());
        assertNotEquals(IDEAL_PRINCIPAL_Z10.hashCode(), 
                IDEAL_SECONDARY_Z10.hashCode());
        assertEquals(IDEAL_WHOLE_RING.hashCode(), RING_Z10.hashCode());
    }
    
    /**
     * Test of the equals function, of the Ideal class. After some obvious 
     * equalities, the commutativity of the generators will be tested. This 
     * means that &#10216;2, 1 + &radic;&minus;5&#10217; = &#10216;1 + 
     * &radic;&minus;5, 2&#10217; and &#10216;2, &radic;10&#10217; = 
     * &#10216;&radic;10, 2&#10217;.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals(IDEAL_PRINCIPAL_ZI5, IDEAL_PRINCIPAL_ZI5);
        assertEquals(IDEAL_PRINCIPAL_Z10, IDEAL_PRINCIPAL_Z10);
        assertNotEquals(IDEAL_PRINCIPAL_ZI5, IDEAL_PRINCIPAL_Z10);
        assertNotEquals(IDEAL_PRINCIPAL_Z10, IDEAL_PRINCIPAL_ZI5);
        assertEquals(IDEAL_SECONDARY_ZI5, IDEAL_SECONDARY_ZI5);
        assertEquals(IDEAL_SECONDARY_Z10, IDEAL_SECONDARY_Z10);
        assertNotEquals(IDEAL_SECONDARY_ZI5, IDEAL_SECONDARY_Z10);
        assertNotEquals(IDEAL_SECONDARY_Z10, IDEAL_SECONDARY_ZI5);
        assertNotEquals(IDEAL_PRINCIPAL_ZI5, IDEAL_SECONDARY_ZI5);
        assertNotEquals(IDEAL_PRINCIPAL_Z10, IDEAL_SECONDARY_Z10);
        assertNotEquals(IDEAL_PRINCIPAL_ZI5, IDEAL_SECONDARY_Z10);
        assertNotEquals(IDEAL_PRINCIPAL_Z10, IDEAL_SECONDARY_ZI5);
        assertNotEquals(IDEAL_SECONDARY_ZI5, IDEAL_PRINCIPAL_ZI5);
        assertNotEquals(IDEAL_SECONDARY_Z10, IDEAL_PRINCIPAL_Z10);
        assertNotEquals(IDEAL_SECONDARY_ZI5, IDEAL_PRINCIPAL_Z10);
        assertNotEquals(IDEAL_SECONDARY_Z10, IDEAL_PRINCIPAL_ZI5);
        Ideal testIdealP = new Ideal(ALG_INT_1PLUSSQRTNEG5, ALG_INT_2_IN_ZI5);
        assertEquals(IDEAL_SECONDARY_ZI5, testIdealP);
        testIdealP = new Ideal(ALG_INT_SQRT10, ALG_INT_2_IN_Z10);
        assertEquals(IDEAL_SECONDARY_Z10, testIdealP);
        // TODO: Write tests like for <3omega> == <3 + 3omega>
        QuadraticRing r = new ImaginaryQuadraticRing(-3);
        QuadraticInteger norm7Int = new ImaginaryQuadraticInteger(5, 1, r, 2);
        testIdealP = new Ideal(norm7Int);
        norm7Int = new ImaginaryQuadraticInteger(-1, -3, r, 2);
        Ideal testIdealQ = new Ideal(norm7Int);
        assertEquals(testIdealP, testIdealQ);
        r = new RealQuadraticRing(2);
        norm7Int = new RealQuadraticInteger(3, 1, r);
        testIdealP = new Ideal(norm7Int);
        assertNotEquals(testIdealP, testIdealQ);
        norm7Int = new RealQuadraticInteger(65, -46, r);
        testIdealQ = new Ideal(norm7Int);
        assertEquals(testIdealP, testIdealQ);
    }

    /**
     * Test of the toString function, of the Ideal class. Spaces are desirable 
     * but not required, so the test will strip them out before the equality 
     * assertion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expected = "\u27E82," 
                + ALG_INT_1PLUSSQRTNEG5.toString().replace(" ", "") 
                + "\u27E9";
        String actual = IDEAL_SECONDARY_ZI5.toString().replace(" ", "");
        assertEquals(expected, actual);
        expected = RING_Z10.toString().replace(" ", "");
        actual = IDEAL_WHOLE_RING.toString().replace(" ", "");
        assertEquals(expected, actual);
    }

    /**
     * Test of the toASCIIString function, of the Ideal class. Spaces are 
     * desirable but not required, so the test will strip them out before the 
     * equality assertion.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        String expResult = "(2,1+sqrt(-5))";
        String result = IDEAL_SECONDARY_ZI5.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toASCIIString().replace(" ", "");
        result = IDEAL_WHOLE_RING.toASCIIString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of the toTeXString function, of the Ideal class. Spaces are 
     * desirable but not required, so the test will strip them out before the 
     * equality assertion.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        String expResult = "\\langle2,1+\\sqrt{-5}\\rangle";
        String result = IDEAL_SECONDARY_ZI5.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toTeXString().replace(" ", "");
        result = IDEAL_WHOLE_RING.toTeXString().replace(" ", "");
        assertEquals(expResult, result);
    }

    /**
     * Test of the toHTMLString function, of the Ideal class. Spaces are 
     * desirable but not required, so the test will strip them out before the 
     * equality assertion.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        String expResult = "&#10216;2,1+&radic;(&minus;5)&#10217;";
        String result = IDEAL_SECONDARY_ZI5.toHTMLString().replace(" ", "");
        assertEquals(expResult, result);
        expResult = RING_Z10.toHTMLString().replace(" ", "");
        result = IDEAL_WHOLE_RING.toHTMLString().replace(" ", "");
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
        System.out.println("Created the ideal " + testIdeal.toASCIIString() 
                + " without problem.");
        try {
            testIdeal = new Ideal(ALG_INT_1PLUSSQRTNEG5, ALG_INT_SQRT10);
            System.out.println("Somehow created " + testIdeal.toASCIIString() 
                    + " without problem.");
            String msg = "Ideal from diff ring numbers needs exception";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Ideal from diff rings caused exception");
            System.out.println("\"" + iae.getMessage() + "\"");
        }
        RealQuadraticRing ring = new RealQuadraticRing(29);
        testIdeal = new Ideal(ring);
        System.out.println("Created an ideal, " + testIdeal.toASCIIString() 
                + " mathematically equal to the ring " + ring.toASCIIString() 
                + " without problem");
    }
    
}
