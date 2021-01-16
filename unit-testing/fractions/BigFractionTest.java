/*
 * Copyright (C) 2021 Alonso del Arte
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
    
    private final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
    
    @Test
    public void testGetNumerator() {
        System.out.println("getNumerator");
        BigInteger expected = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction fraction = new BigFraction(expected, denom);
        BigInteger actual = fraction.getNumerator();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetDenominator() {
        System.out.println("getDenominator");
        BigInteger numer = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger expected = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction fraction = new BigFraction(numer, expected);
        BigInteger actual = fraction.getDenominator();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        String expected = "1/2";
        String actual = oneHalf.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringOmitsDenomOne() {
        BigFraction ten = new BigFraction(BigInteger.TEN, BigInteger.ONE);
        String expected = "10";
        String actual = ten.toString();
        assertEquals(expected, actual);
    }
    
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
    
    @Test
    public void testToTeXString() {
        System.out.println("toTeXString");
        BigFraction fraction = new BigFraction(TWO.negate(), 
                BigInteger.valueOf(Long.MAX_VALUE));
        String expected = "-\\frac{2}{" + Long.MAX_VALUE + "}";
        String actual = fraction.toTeXString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        BigFraction someFrac = new BigFraction(BigInteger.ONE, TWO);
        assertEquals(someFrac, someFrac);
    }
    
    @Test
    public void testNotEqualsNull() {
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        assertNotEquals(oneHalf, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        BigFraction bigFrac = new BigFraction(BigInteger.ONE, TWO);
        Fraction regFrac = new Fraction(1, 2);
        assertNotEquals(bigFrac, regFrac);
    }
    
    @Test
    public void testUnequalNumerators() {
        BigInteger three = BigInteger.ONE.add(TWO);
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        BigFraction twoThirds = new BigFraction(TWO, three);
        assertNotEquals(oneHalf, twoThirds);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        BigFraction someFrac = new BigFraction(BigInteger.ONE, TWO);
        BigFraction sameFrac = new BigFraction(BigInteger.ONE, TWO);
        assertEquals(someFrac, sameFrac);
    }
    
    @Test
    public void testUnequalDenominators() {
        BigInteger three = BigInteger.ONE.add(TWO);
        BigInteger five = three.add(TWO);
        BigFraction twoThirds = new BigFraction(TWO, three);
        BigFraction twoFifths = new BigFraction(TWO, five);
        assertNotEquals(twoThirds, twoFifths);
    }
    
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
    
    @Test
    public void testGetNumericApproximation() {
        System.out.println("getNumericApproximation");
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        BigDecimal expected = new BigDecimal(0.5);
        BigDecimal actual = oneHalf.getNumericApproximation();
        assertEquals(expected, actual);
    }
    
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
    
    @Test
    public void testDiffFractionDiffHashCode() {
        BigFraction oneHalf = new BigFraction(BigInteger.ONE, TWO);
        BigFraction two = new BigFraction(TWO, BigInteger.ONE);
        assertNotEquals(oneHalf.hashCode(), two.hashCode());
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        BigFraction someFrac = new BigFraction(BigInteger.ONE, TWO);
        BigFraction sameFrac = new BigFraction(BigInteger.ONE, TWO);
        assertEquals(someFrac.hashCode(), sameFrac.hashCode());
    }
    
    @Test
    public void testHashCodeThroughCollection() {
        HashSet<BigFraction> fractions = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        Random random = new Random();
        BigInteger numer, denom;
        BigFraction fraction;
        int hash;
        for (int i = 16; i < 128; i++) {
            numer = BigInteger.probablePrime(i, random);
            denom = BigInteger.probablePrime(i, random);
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
    
    @Test
    public void testNegate() {
        BigInteger numer = BigInteger.ONE.negate();
        BigInteger denom = BigInteger.valueOf(Long.MIN_VALUE).negate();
        BigFraction fraction = new BigFraction(BigInteger.ONE, denom);
        BigFraction expected = new BigFraction(numer, denom);
        BigFraction actual = fraction.negate();
        assertEquals(expected, actual);
    }
    
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
    
    @Test
    public void testCopyConstructor() {
        Fraction oneHalf = new Fraction(1, 2);
        BigFraction expected = new BigFraction(BigInteger.ONE, TWO);
        BigFraction actual = new BigFraction(oneHalf);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsZeroDenom() {
        try {
            BigFraction badFraction = new BigFraction(BigInteger.ONE, 
                    BigInteger.ZERO);
            String msg = "Should not have been able to create " 
                    + badFraction.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use zero denominator correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for denominator zero";
            fail(msg);
        }
    }
    
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
