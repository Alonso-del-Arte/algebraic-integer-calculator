/*
 * Copyright (C) 2025 Alonso del Arte
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
package fractions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the BigFraction class.
 * @author Alonso del Arte
 */
public class BigFractionTest {
    
    private static final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
    
    private static final Random RANDOM = new Random();
    
    /**
     * Test of the getNumerator function of the BigFraction class.
     */
    @Test
    public void testGetNumerator() {
        System.out.println("getNumerator");
        BigInteger expected = new BigInteger(72, RANDOM);
        BigInteger denom = expected.add(BigInteger.ONE);
        BigFraction fraction = new BigFraction(expected, denom);
        BigInteger actual = fraction.getNumerator();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getDenominator function of the BigFraction class.
     */
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        BigInteger expected = new BigInteger(72, RANDOM).add(BigInteger.ONE);
        BigInteger multiplier = new BigInteger(16, RANDOM);
        BigInteger numer = expected.multiply(multiplier).add(BigInteger.ONE);
        BigFraction fraction = new BigFraction(numer, expected);
        BigInteger actual = fraction.getDenominator();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toString function of the BigFraction class.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        String expected = "1/2";
        String actual = oneHalf.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the toString function of the BigFraction class. If the 
     * fraction is an integer, the denominator is 1 and it should be omitted.
     */
    @Test
    public void testToStringOmitsDenomOne() {
        BigFraction ten = new BigFraction(BigInteger.TEN, BigInteger.ONE);
        String expected = "10";
        String actual = ten.toString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toHTMLString function of the BigFraction class.
     */
    @Test
    public void testToHTMLString() {
        System.out.println("toHTMLString");
        BigFraction fraction = new BigFraction(TWO.negate(), 
                BigInteger.valueOf(Long.MAX_VALUE));
        String expected = "<sup>&minus;2</sup>&frasl;<sub>" + Long.MAX_VALUE 
                + "</sub>";
        String actual = fraction.toHTMLString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the toTeXString function of the BigFraction class.
     */
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        BigFraction fraction = new BigFraction(TWO.negate(), 
                BigInteger.valueOf(Long.MAX_VALUE));
        String expected = "-\\frac{2}{" + Long.MAX_VALUE + "}";
        String actual = fraction.toTeXString();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the equals function of the BigFraction class. A 
     * BigFraction instance should be equal to itself.
     */
    @Test
    public void testReferentialEquality() {
        BigFraction someFrac = new BigFraction(BigInteger.ONE, TWO);
        assertEquals(someFrac, someFrac);
    }
    
    /**
     * Another test of the equals function of the BigFraction class. A 
     * BigFraction instance should not be equal to null.
     */
    @Test
    public void testNotEqualsNull() {
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        assertNotEquals(oneHalf, null);
    }
    
    /**
     * Another test of the equals function of the BigFraction class. A 
     * BigFraction instance should not be equal to an instance of a different 
     * class.
     */
    @Test
    public void testNotEqualsDiffClass() {
        BigFraction bigFrac = new BigFraction(BigInteger.ONE, TWO);
        Fraction regFrac = new Fraction(1, 2);
        assertNotEquals(bigFrac, regFrac);
    }
    
    /**
     * Another test of the equals function of the BigFraction class. Two 
     * fractions that have different numerators when in lowest terms should not 
     * be found to be equal. For example, 1/2 is not equal to 2/3.
     */
    @Test
    public void testUnequalNumerators() {
        BigInteger three = BigInteger.ONE.add(TWO);
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        BigFraction twoThirds = new BigFraction(TWO, three);
        assertNotEquals(oneHalf, twoThirds);
    }
    
    /**
     * Test of the equals function of the BigFraction class.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        BigFraction someFrac = new BigFraction(BigInteger.ONE, TWO);
        BigFraction sameFrac = new BigFraction(BigInteger.ONE, TWO);
        assertEquals(someFrac, sameFrac);
    }
    
    /**
     * Another test of the equals function of the BigFraction class. Two 
     * fractions that have different denominators when in lowest terms should  
     * not be found to be equal. For example, 2/3 is not equal to 2/5.
     */
    @Test
    public void testUnequalDenominators() {
        BigInteger three = BigInteger.ONE.add(TWO);
        BigInteger five = three.add(TWO);
        BigFraction twoThirds = new BigFraction(TWO, three);
        BigFraction twoFifths = new BigFraction(TWO, five);
        assertNotEquals(twoThirds, twoFifths);
    }
    
    /**
     * Test of the correspondence between the equals and compareTo functions of 
     * the BigFraction class.
     */
    @Test
    public void testEqualsCompareToCorrespondence() {
        BigInteger sixty = BigInteger.valueOf(60);
        BigFraction fractionA, fractionB;
        int comparisonAToB, comparisonBToA, signProd;
        String msg;
        for (BigInteger numer = BigInteger.ONE; numer.compareTo(sixty) < 0;
                numer = numer.add(BigInteger.ONE)) {
            for (BigInteger denom = BigInteger.ONE; denom.compareTo(sixty) < 0; 
                    denom = denom.add(BigInteger.ONE)) {
                fractionA = new BigFraction(numer, denom);
                fractionB = new BigFraction(numer.add(TWO), denom.add(TWO));
                comparisonAToB = fractionA.compareTo(fractionB);
                comparisonBToA = fractionB.compareTo(fractionA);
                msg = fractionA.toString() + " compareTo " + fractionB.toString();
                if (fractionA.equals(fractionB)) {
                    msg = msg + " should be 0, and vice-versa";
                    assert comparisonAToB == 0 : msg;
                    assert comparisonBToA == 0 : msg;
                } else {
                    msg = msg + " should have sign opposite " 
                            + fractionB.toString() + " compareTo " 
                            + fractionA.toString();
                    signProd = Integer.signum(comparisonAToB) 
                            * Integer.signum(comparisonBToA);
                    assert signProd == -1 : msg;
                }
            }
        }
    }
    
    /**
     * Test of the compareTo function of the BigFraction class.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        ArrayList<BigFraction> expected = new ArrayList<>();
        BigInteger numer = BigInteger.ONE.negate();
        BigFraction negativeOneHalf = new BigFraction(numer, TWO);
        expected.add(negativeOneHalf);
        BigFraction zero = new BigFraction(BigInteger.ZERO, TWO);
        expected.add(zero);
        numer = numer.add(TWO);
        BigInteger denom = BigInteger.valueOf(Long.MAX_VALUE);
        BigFraction smallFraction = new BigFraction(numer, denom);
        expected.add(smallFraction);
        numer = denom.subtract(TWO);
        BigFraction nearOne = new BigFraction(numer, denom);
        expected.add(nearOne);
        numer = BigInteger.valueOf(355);
        denom = BigInteger.valueOf(113);
        BigFraction piApprox = new BigFraction(numer, denom);
        expected.add(piApprox);
        ArrayList<BigFraction> actual = new ArrayList<>();
        actual.add(smallFraction);
        actual.add(negativeOneHalf);
        actual.add(nearOne);
        actual.add(piApprox);
        actual.add(zero);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getNumericApproximation function of the BigFraction class.
     */
    @Test
    public void testGetNumericApproximation() {
        System.out.println("getNumericApproximation");
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        BigDecimal expected = new BigDecimal(0.5);
        BigDecimal actual = oneHalf.getNumericApproximation();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the getNumericApproximation function of the BigFraction 
     * class. If the fraction's decimal representation is non-terminating, such 
     * as is the case with <sup>1</sup>&frasl;<sub>3</sub>, the result should be 
     * a <code>BigDecimal</code> with a scale of 128 and a rounding mode of half 
     * even. This is very arbitrary, and subject to change. The corresponding 
     * Javadoc in Source Packages makes no promises about this and suggests 
     * using the numerator and denominator getters and then performing division 
     * directly on those BigInteger instances converted to BigDecimal if the 
     * getNumericApproximation function does not give the desired scale and 
     * rounding mode.
     */
    @Test
    public void testNonTerminatingRepresentation() {
        BigInteger three = BigInteger.ONE.add(TWO);
        BigFraction oneThird = new BigFraction(BigInteger.ONE, three);
        BigDecimal oneAsDec = new BigDecimal(BigInteger.ONE);
        BigDecimal threeAsDec = new BigDecimal(three);
        BigDecimal expected = oneAsDec.divide(threeAsDec, 128, 
                RoundingMode.HALF_EVEN);
        try {
            BigDecimal actual = oneThird.getNumericApproximation();
            assertEquals(expected, actual);
        } catch (ArithmeticException ae) {
            String msg = "Trying to get BigDecimal for " + oneThird.toString() 
                    + " should not have caused ArithmeticException";
            System.out.println(msg);
            System.out.println("\"" + ae.getMessage() + "\"");
            fail(msg);
        }
    }
    
    /**
     * Test of the hashCode function of the BigFraction class. Within reason, 
     * two distinct BigFraction instances should have distinct hash codes.
     */
    @Test
    public void testDiffFractionDiffHashCode() {
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        BigFraction two = new BigFraction(TWO, BigInteger.ONE);
        assertNotEquals(oneHalf.hashCode(), two.hashCode());
    }
    
    /**
     * Test of the hashCode function of the BigFraction class. If two 
     * BigFraction instances represent the same number, their hash codes should 
     * match.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        BigFraction someFrac = new BigFraction(BigInteger.ONE, TWO);
        BigFraction sameFrac = new BigFraction(BigInteger.ONE, TWO);
        assertEquals(someFrac.hashCode(), sameFrac.hashCode());
    }
    
    /**
     * Another test of the hashCode function of the BigFraction class. This test 
     * tries to come up with more than a hundred distinct BigFraction instances. 
     * If it comes up with 112 instances, for example, then those 112 instances 
     * should have 112 distinct hash codes. It is of course impossible to avoid 
     * repeated hash codes for all possible BigFraction instances, but hopefully 
     * the hash codes will be unique enough for most practical purposes.
     */
    @Test
    public void testHashCodeThroughCollection() {
        HashSet<BigFraction> fractions = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        BigInteger numer, denom;
        BigFraction fraction;
        int hash;
        for (int i = 16; i < 128; i++) {
            numer = BigInteger.probablePrime(i, RANDOM);
            denom = BigInteger.probablePrime(i, RANDOM);
            fraction = new BigFraction(numer, denom);
            fractions.add(fraction);
            hash = fraction.hashCode();
            hashes.add(hash);
        }
        int fractSetSize = fractions.size();
        int hashSetSize = hashes.size();
        System.out.println("Created set of " + fractSetSize + " fractions with " 
                + hashSetSize + " hash codes");
        String msg = "Set of fractions should be the same size as set of hashes";
        assertEquals(msg, fractSetSize, hashSetSize);
    }
    
    /**
     * Test of the plus function, of the BigFraction class.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        String smallDenomStr = "18446744073709551616";
        BigInteger denom = new BigInteger(smallDenomStr);
        BigInteger numer = denom.add(BigInteger.ONE);
        BigFraction addendA = new BigFraction(BigInteger.ONE, BigInteger.ONE);
        BigFraction addendB = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = addendA.plus(addendB);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the plus function, of the BigFraction class.
     */
    @Test
    public void testPlusSmallFraction() {
        Fraction oneHalf = new Fraction(1, 2);
        BigInteger numer = BigInteger.valueOf(Long.MIN_VALUE / (-2) + 1);
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction smallFract = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = smallFract.plus(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the plus function, of the BigFraction class.
     */
    @Test
    public void testPlusInt() {
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigInteger numer = denom.add(BigInteger.ONE);
        BigFraction smallFract = new BigFraction(BigInteger.ONE, denom);
        int addend = 1;
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = smallFract.plus(addend);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the negate function, of the BigFraction class.
     */
    @Test
    public void testNegate() {
        BigInteger numer = BigInteger.ONE.negate();
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction fraction = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = fraction.negate();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the minus function of the BigFraction class.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        String smallDenomStr = "18446744073709551616";
        BigInteger denom = new BigInteger(smallDenomStr);
        BigInteger numer = denom.subtract(BigInteger.ONE);
        BigFraction minuend = new BigFraction(BigInteger.ONE, BigInteger.ONE);
        BigFraction subtrahend = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = minuend.minus(subtrahend);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the minus function, of the BigFraction class.
     */
    @Test
    public void testMinusSmallFraction() {
        Fraction oneHalf = new Fraction(1, 2);
        BigInteger numer = BigInteger.valueOf(Long.MIN_VALUE / 2 + 1);
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction smallFract = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = smallFract.minus(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the minus function, of the BigFraction class.
     */
    @Test
    public void testMinusInt() {
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigInteger numer = BigInteger.valueOf(Long.MIN_VALUE + 1);
        BigFraction smallFract = new BigFraction(BigInteger.ONE, denom);
        int subtrahend = 1;
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = smallFract.minus(subtrahend);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the times function of the BigFraction class.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        BigInteger numer = BigInteger.ONE.negate();
        BigInteger denom = TWO.multiply(TWO).add(BigInteger.ONE);
        BigFraction multiplicandA = new BigFraction(numer, TWO);
        BigFraction multiplicandB = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom.multiply(TWO));
        BigFraction actual = multiplicandA.times(multiplicandB);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the times function, of the BigFraction class.
     */
    @Test
    public void testTimesSmallFraction() {
        BigInteger smallDenom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction smallFract = new BigFraction(BigInteger.ONE, smallDenom);
        Fraction oneHalf = new Fraction(1, 2);
        BigFraction expected = new BigFraction(BigInteger.ONE, 
                smallDenom.multiply(TWO));
        BigFraction actual = smallFract.times(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the times function, of the BigFraction class.
     */
    @Test
    public void testTimesInt() {
        int multiplicand = 3;
        BigInteger numer = BigInteger.valueOf(multiplicand);
        BigInteger denom = BigInteger.TEN.pow(84);
        BigFraction fraction = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = fraction.times(multiplicand);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the reciprocal function of the BigFraction class.
     */
    @Test
    public void testReciprocal() {
        System.out.println("reciprocal");
        BigInteger three = TWO.add(BigInteger.ONE);
        BigInteger seven = three.multiply(TWO).add(BigInteger.ONE);
        BigFraction threeSevenths = new BigFraction(three, seven);
        BigFraction expected = new BigFraction(seven, three);
        BigFraction actual = threeSevenths.reciprocal();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the dividedBy function of the BigFraction class.
     */
    @Test
    public void testDividedBy() {
        System.out.println("dividedBy");
        BigFraction one = new BigFraction(BigInteger.ONE, BigInteger.ONE);
        BigInteger numer = BigInteger.valueOf(Long.MAX_VALUE);
        BigFraction divisor = new BigFraction(numer, BigInteger.ONE);
        BigFraction expected = new BigFraction(BigInteger.ONE, numer);
        BigFraction actual = one.dividedBy(divisor);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the dividedBy function, of the BigFraction class.
     */
    @Test
    public void testDividedBySmallFraction() {
        BigInteger numer = TWO.pow(21);
        BigInteger denom = TWO.pow(65).subtract(BigInteger.ONE);
        BigFraction fraction = new BigFraction(numer, denom);
        Fraction oneHalf = new Fraction(1, 2);
        BigFraction expected = new BigFraction(numer.multiply(TWO), denom);
        BigFraction actual = fraction.dividedBy(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the dividedBy function, of the BigFraction class.
     */
    @Test
    public void testDividedByInt() {
        int divisor = 7;
        BigInteger seven = BigInteger.valueOf(divisor);
        BigInteger numer = seven.multiply(TWO).add(BigInteger.ONE);
        BigInteger denom = seven.pow(23);
        BigFraction fraction = new BigFraction(numer, denom);
        BigFraction expected = new BigFraction(numer, denom.multiply(seven));
        BigFraction actual = fraction.dividedBy(divisor);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the getDouble function of the BigFraction class.
     */
    @Test
    public void testGetDouble() {
        System.out.println("getDouble");
        BigInteger seven = TWO.add(TWO).add(TWO).add(BigInteger.ONE);
        BigFraction oneSeventh = new BigFraction(BigInteger.ONE, seven);
        double expected = 0.142857;
        double actual = oneSeventh.getDouble();
        double delta = 0.000001;
        assertEquals(expected, actual, delta);
    }
    
    /**
     * Test of the canDownsample function of the BigFraction class.
     */
    @Test
    public void testCanDownsample() {
        System.out.println("canDownsample");
        int numBits = 16;
        BigInteger numer = new BigInteger(numBits, RANDOM);
        BigInteger denom = new BigInteger(numBits, RANDOM);
        BigFraction fraction = new BigFraction(numer, denom);
        String msg = "It should be possible to convert the fraction "
                + fraction.toString() 
                + " to 64-bit integers for numerator, denominator";
        assert fraction.canDownsample() : msg;
    }
    
    /**
     * Another test of the canDownsample function, of the BigFraction class.
     */
    @Test
    public void testCanNotDownsample() {
        BigInteger denominator = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigInteger numerator = denominator.multiply(TWO).add(BigInteger.ONE);
        BigFraction fraction = new BigFraction(numerator, denominator);
        String msg = "It should NOT be possible to convert the fraction "
                + fraction.toString() 
                + " to 64-bit integers for numerator, denominator";
        assert !fraction.canDownsample() : msg;
    }
    
    /**
     * Test of the downsample function of the BigFraction class.
     */
    @Test
    public void testDownsample() {
        System.out.println("downsample");
        int numer = -RANDOM.nextInt(32768) - 1;
        int denom = RANDOM.nextInt(65536) + 1;
        BigInteger numerator = BigInteger.valueOf(numer);
        BigInteger denominator = BigInteger.valueOf(denom);
        BigFraction fraction = new BigFraction(numerator, denominator);
        Fraction expected = new Fraction(numer, denom);
        Fraction actual = fraction.downsample();
        assertEquals(expected, actual);
        System.out.println("Successfully downsampled " + fraction.toString() 
                + " to " + actual.toString());
    }
    
    /**
     * Another test of the downsample function, of the BigFraction class.
     */
    @Test
    public void testDownsampleShouldRejectNarrowingConversion() {
        BigInteger numerator = new BigInteger("18446744073709551629");
        BigInteger adjustment = BigInteger.valueOf(RANDOM.nextInt(32768));
        BigInteger overflowLong = new BigInteger("19599947053293109248");
        BigInteger denominator = overflowLong.add(adjustment);
        BigFraction fraction = new BigFraction(numerator, denominator);
        try {
            Fraction result = fraction.downsample();
            String msg = "Trying to downsample " + fraction.toString() 
                    + " to use 64-bit integers should not have given result " 
                    + result.toString();
            fail(msg);
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException correct for trying to fit "
                    + fraction.toString() + " to use 64-bit integers");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is not appropriate for trying to fit " 
                    + fraction.toString() + " to use 64-bit integers";
            fail(msg);
        }
    }
    
    /**
     * Test of the parseFract function of the BigFraction class.
     */
    @Test
    public void testParseFract() {
        System.out.println("parseFract");
        String numerStr = "18446744073709551629";
        BigInteger numerator = new BigInteger(numerStr);
        BigInteger adjustment = BigInteger.valueOf(RANDOM.nextInt(32768));
        BigInteger overflowLong = new BigInteger("19599947053293109248");
        BigInteger denominator = overflowLong.add(adjustment);
        String denomStr = denominator.toString();
        BigFraction expected = new BigFraction(numerator, denominator);
        BigFraction actual = BigFraction.parseFract(numerStr + "/" + denomStr);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the BigFraciton constructor which takes a Fraction instance as 
     * its single parameter.
     */
    @Test
    public void testCopyConstructor() {
        Fraction oneHalf = new Fraction(1, 2);
        BigFraction expected = new BigFraction(BigInteger.ONE, TWO);
        BigFraction actual = new BigFraction(oneHalf);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the main BigFraction constructor. That constructor should reject 
     * 0 as a denominator.
     */
    @Test
    public void testConstructorRejectsZeroDenom() {
        try {
            BigFraction badFraction = new BigFraction(BigInteger.ONE, 
                    BigInteger.ZERO);
            String msg = "Should not have been able to create " 
                    + badFraction.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Denominator 0 caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for denominator zero";
            fail(msg);
        }
    }
    
    /**
     * Another test of the main BigFraction constructor. That constructor should 
     * put the numerator and denominator in lowest terms.
     */
    @Test
    public void testConstructorPutsInLowestTerms() {
        BigInteger three = BigInteger.ONE.add(TWO);
        BigInteger four = TWO.add(TWO);
        BigInteger six = three.multiply(TWO);
        BigInteger eight = six.add(TWO);
        BigFraction threeQuarters = new BigFraction(three, four);
        BigFraction sixEighths = new BigFraction(six, eight);
        assertEquals(threeQuarters, sixEighths);
    }
    
}
