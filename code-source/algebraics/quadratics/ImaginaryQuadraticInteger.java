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
package algebraics.quadratics;

import java.text.DecimalFormatSymbols;

/**
 * Defines objects to represent imaginary quadratic integers, for the most part 
 * symbolically rather than numerically.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticInteger extends QuadraticInteger {
    
    private final double numValRe;
    private final double numValIm;
    
    public long getTwiceRealPartMult() {
        long twiceRealPartMult = this.regPartMult;
        if (this.denominator == 1) {
            twiceRealPartMult *= 2;
        }
        return twiceRealPartMult;
    }
    
    public long getTwiceImagPartMult() {
        long twiceImagPartMult = this.surdPartMult;
        if (this.denominator == 1) {
            twiceImagPartMult *= 2;
        }
        return twiceImagPartMult;
    }

    /**
     * Gives the imaginary quadratic integer's distance from 0.
     * @return This distance from 0 of the imaginary quadratic integer expressed 
     * as a nonnegative real double. For example, for 5/2 + (&radic;-7)/2, this 
     * would be approximately 2.82842712. For a purely real positive integer, 
     * just the integer itself as a double, likewise for purely real negative 
     * integers this is the integer itself multiplied by -1.
     */
    @Override
    public double abs() {
        if (this.surdPartMult == 0) {
            if (this.regPartMult < 0) {
                return -this.regPartMult;
            } else {
                return this.regPartMult;
            }
        }
        if (this.regPartMult == 0 && this.quadRing.radicand == -1) {
            if (this.surdPartMult < 0) {
                return -this.surdPartMult;
            } else {
                return this.surdPartMult;
            }
        }
        double realLegSquare = this.regPartMult * this.regPartMult;
        double imagLegSquare = this.surdPartMult * this.surdPartMult * (-this.quadRing.radicand);
        double hypotenuseSquare = realLegSquare + imagLegSquare;
        if (this.denominator == 2) {
            hypotenuseSquare /= 4;
        }
        return Math.sqrt(hypotenuseSquare);
    }

    /**
     * Gets the real part of the imaginary quadratic integer. May be half an 
     * integer.
     * @return The real part of the imaginary quadratic integer. For example, 
     * for &minus;1/2 + (&radic;&minus;7)/2, the result should be &minus;0.5.
     */
    @Override
    public double getRealPartNumeric() {
        return this.numValRe;
    }
    
    /**
     * Gets the imaginary part of the imaginary quadratic integer multiplied by 
     * &minus;<i>i</i>. It will most likely be the rational approximation of an 
     * irrational real number.
     * @return The imaginary part of the imaginary quadratic integer multiplied 
     * by &minus;<i>i</i>. For example, for &minus;1/2 + (&radic;&minus;7)/2, 
     * the result should be roughly 1.32287565553229529525.
     */
    @Override
    public double getImagPartNumeric() {
        return this.numValIm;
    }
    
    @Override
    public double angle() {
        return Math.atan2(this.numValIm, this.numValRe);
    }
      
    /**
     * Interprets a String that contains 0s, 1s, 2s and/or 3s as the 
     * quater-imaginary representation of a Gaussian integer. Computer pioneer 
     * Donald Knuth is the first person known to propose this system, in which 
     * any Gaussian integer can be represented without the need for minus signs 
     * and without the need to separate the real and imaginary parts of the 
     * number.
     * @param str The String to parse. May contain spaces, which will be 
     * stripped out prior to parsing. May also contain a "decimal" dot followed 
     * by either "2" and zero or more zeroes, or just zeroes.
     * @return An ImaginaryQuadraticInteger object containing the Gaussian 
     * integer represented by the quater-imaginary String.
     * @throws NumberFormatException If str has a "decimal" dot followed by any 
     * digit other than a single 2 or a bunch of zeroes, or if it contains 
     * digits other than 0, 1, 2 or 3, this runtime exception will be thrown. 
     * The problematic character mentioned in the exception message may or may 
     * not be the only parsing obstacle.
     */
    public static QuadraticInteger parseQuaterImaginary(String str) {
        ImaginaryQuadraticRing ringGaussian = new ImaginaryQuadraticRing(-1);
        ImaginaryQuadraticInteger base = new ImaginaryQuadraticInteger(0, 2, ringGaussian);
        QuadraticInteger currPower = new ImaginaryQuadraticInteger(1, 0, ringGaussian);
        QuadraticInteger currPowerMult;
        QuadraticInteger parsedSoFar = new ImaginaryQuadraticInteger(0, 0, ringGaussian);
        ImaginaryQuadraticInteger gaussianZero = new ImaginaryQuadraticInteger(0, 0, ringGaussian);
        str = str.replace(" ", ""); // Strip out spaces
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        int dotPlace = str.indexOf(dfs.getDecimalSeparator());
        if (dotPlace > - 1) {
            boolean keepGoing = true;
            int currFractPlace = dotPlace + 2;
            while ((currFractPlace < str.length()) && keepGoing) {
                keepGoing = (str.charAt(currFractPlace) == '0');
                currFractPlace++;
            }
            if (!keepGoing) {
                throw new NumberFormatException("'" + str.charAt(currFractPlace - 1) + "' after \"decimal\" separator is not a valid digit for the quater-imaginary representation of a Gaussian integer.");
            }
            if (str.length() == dotPlace + 1) {
                str = str + "0";
            }
            str = str.substring(0, dotPlace + 2); // Discard trailing "decimal" zeroes
        }
        String dotZeroEnding = dfs.getDecimalSeparator() + "0";
        if (str.endsWith(dotZeroEnding)) {
            str = str.substring(0, str.length() - 2);
        }
        String dotTwoEnding = dfs.getDecimalSeparator() + "2";
        if (str.endsWith(dotTwoEnding)) {
            parsedSoFar = new ImaginaryQuadraticInteger(0, -1, ringGaussian);
            str = str.substring(0, str.length() - 2);
        }
        char currDigit;
        for (int i = str.length() - 1; i > -1; i--) {
            currDigit = str.charAt(i);
            switch (currDigit) {
                case '0':
                    currPowerMult = gaussianZero;
                    break;
                case '1':
                    currPowerMult = currPower;
                    break;
                case '2':
                    currPowerMult = currPower.times(2);
                    break;
                case '3':
                    currPowerMult = currPower.times(3);
                    break;
                default:
                    String exceptionMessage = "'" + currDigit + "' is not a valid quater-imaginary digit (should be one of 0, 1, 2, 3).";
                    throw new NumberFormatException(exceptionMessage);
            }
            parsedSoFar = parsedSoFar.plus(currPowerMult);
            currPower = currPower.times(base);
        }
        return parsedSoFar;
    }
      
    /**
     * Alternative object constructor, may be used when the denominator is known 
     * to be 1.
     * @param a The real part of the imaginary quadratic integer. For example, 
     * for 5 + &radic;-3, this parameter would be 5.
     * @param b The part to be multiplied by &radic;<i>d</i>. For example, for 5 
     * + &radic;-3, this parameter would be 1.
     * @param R The ring to which this algebraic integer belongs to. For 
     * example, for 5 + &radic;-3, this parameter could be <code>new 
     * ImaginaryQuadraticRing(-3)</code>.
     */
    public ImaginaryQuadraticInteger(int a, int b, QuadraticRing R) {
        this(a, b, R, 1);
    }
    
    /**
     * Class constructor
     * @param a The real part of the imaginary quadratic integer, multiplied by 2 when applicable
     * @param b The part to be multiplied by sqrt(d), multiplied by 2 when applicable
     * @param R The ring to which this algebraic integer belongs to
     * @param denom In most cases 1, but may be 2 if a and b have the same parity and d = 1 mod 4
     * @throws IllegalArgumentException If R is not of type {@link ImaginaryQuadraticRing}, or if there is parity mismatch with a and b and denom = 2
     */
    public ImaginaryQuadraticInteger(int a, int b, QuadraticRing R, int denom) {
        ImaginaryQuadraticRing ring;
        if (R instanceof ImaginaryQuadraticRing) {
            ring = (ImaginaryQuadraticRing) R;
        } else {
            if (b == 0) {
                ring = new ImaginaryQuadraticRing(-R.radicand);
            } else {
                String exceptionMessage = R.toASCIIString() + " is not an imaginary quadratic ring as needed.";
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
        boolean abParityMatch;
        if (denom == -1 || denom == -2) {
            a *= -1;
            b *= -1;
            denom *= -1;
        }
        if (denom < 1 || denom > 2) {
            throw new IllegalArgumentException("Parameter denom must be 1 or 2.");
        }
        if (denom == 2) {
            abParityMatch = Math.abs(a % 2) == Math.abs(b % 2);
            if (!abParityMatch) {
                throw new IllegalArgumentException("Parity of parameter a must match parity of parameter b.");
            }
            if (a % 2 == 0) {
                this.regPartMult = a/2;
                this.surdPartMult = b/2;
                this.denominator = 1;
            } else {
                if (R.d1mod4) {
                    this.regPartMult = a;
                    this.surdPartMult = b;
                    this.denominator = 2;
                } else {
                    throw new IllegalArgumentException("Either parameter a and parameter b need to both be even, or parameter denom needs to be 1.");
                }
            }
        } else {
            this.regPartMult = a;
            this.surdPartMult = b;
            this.denominator = 1;
        }
        this.quadRing = ring;
        double realPart = this.regPartMult;
        if (this.denominator == 2) {
            realPart /= 2;
        }
        this.numValRe = realPart;
        double imagPartwRad = this.surdPartMult * this.quadRing.getAbsNegRadSqrt();
        if (this.denominator == 2) {
            imagPartwRad /= 2;
        }
        this.numValIm = imagPartwRad;
    }
    
}
