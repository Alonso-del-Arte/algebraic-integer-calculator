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
package algebraics.quadratics;

import algebraics.AlgebraicDegreeOverflowException;
import arithmetic.NotDivisibleException;
import static calculators.TextCalculator.makeBinomialString;

import java.math.BigInteger;
import java.text.DecimalFormatSymbols;

/**
 * Defines objects to represent imaginary quadratic integers, for the most part 
 * symbolically rather than numerically.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticInteger extends QuadraticInteger {
    
    private static final long serialVersionUID = 4547649335944297267L;
    
    private static final char MINUS_SIGN_CHARACTER = '\u2212';
    
    private static final char OMEGA_LETTER = '\u03C9';
    
    private static final String OMEGA_WORD_ASCII = "omega";
    
    private static final char SQRT_SYMBOL = '\u221A';
    
    private static final char[] SQRT_NEG_ONE_CHARS = {SQRT_SYMBOL, '(', 
        MINUS_SIGN_CHARACTER, '1', ')'};
    
    private static final String SQRT_NEG_ONE_SEQ 
            = new String(SQRT_NEG_ONE_CHARS);
    
    private static final QuadraticRing RING_GAUSSIAN 
            = new ImaginaryQuadraticRing(-1);
    
    private static final QuadraticInteger QUATER_BASE 
            = new ImaginaryQuadraticInteger(0, 2, RING_GAUSSIAN);
    
    private static final QuadraticInteger ZERO_IN_GAUSSIAN 
            = new ImaginaryQuadraticInteger(0, 0, RING_GAUSSIAN);
    
    private static final QuadraticInteger ONE_IN_GAUSSIAN 
            = new ImaginaryQuadraticInteger(1, 0, RING_GAUSSIAN);
    
    private static final QuadraticInteger NEG_IMAG_UNIT 
            = new ImaginaryQuadraticInteger(0, -1, RING_GAUSSIAN);
    
    private final double numValRe;
    private final double numValIm;

    /**
     * Gives the norm of the algebraic integer in a format that can represent 
     * numbers outside the range of 64-bit integers. This computation involves 
     * the creation of multiple intermediate {@code BigInteger} objects. Thus it 
     * may be slower than {@link #norm()}, but is likelier to be correct. As a 
     * rule of thumb, if the real and imaginary parts of the number fit in the 
     * range of {@code short}, this function and {@code norm()} should give the 
     * same result. For the example, suppose this algebraic integer is 
     * 1265089225 + 1723621595&radic;(&minus;35).
     * @return The norm, even if it's outside the range of {@code long} to 
     * represent. In the example, this would be 105580949843473141500, whereas 
     * {@code norm()} would give the obviously incorrect value 
     * &minus;5099514598784168196.
     */
    @Override
    public BigInteger fullNorm() {
        BigInteger wrappedA = BigInteger.valueOf(this.regPartMult);
        BigInteger wrappedB = BigInteger.valueOf(this.surdPartMult);
        BigInteger wrappedD = BigInteger.valueOf(this.quadRing.absRadicand);
        BigInteger aSquared = wrappedA.multiply(wrappedA);
        BigInteger bSquared = wrappedB.multiply(wrappedB);
        BigInteger bSquaredTimesD = bSquared.multiply(wrappedD);
        BigInteger numersOnly = aSquared.add(bSquaredTimesD);
        if (this.quadRing.d1mod4 && this.denominator == 2) {
            return numersOnly.divide(QuadraticInteger.FOUR);
        } else {
            return numersOnly;
        }
    }
    
    @Override
    public String toString() {
        return super.toString().replace(SQRT_NEG_ONE_SEQ, "i");
    }
    
    private String toStringAltOmega() {
        int nonOmegaInit = this.regPartMult;
        int omegaPart = this.surdPartMult;
        if (this.denominator == 1) {
            nonOmegaInit *= 2;
            omegaPart *= 2;
        }
        int nonOmegaPart = (nonOmegaInit + omegaPart) / 2;
        return makeBinomialString(nonOmegaPart, omegaPart, OMEGA_LETTER);
    }

    /**
     * A text representation of the quadratic integer, using theta or omega 
     * notation when {@link #getRing()}{@link QuadraticRing#hasHalfIntegers() 
     * .hasHalfIntegers()} is true. For the first example, suppose this number 
     * is <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;15</sup>&frasl;<sub>2</sub>, and for the second 
     * example suppose this number is <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub>.
     * @return A representation using theta or omega notation. For the first 
     * example, this would be would be 2 + &theta;, and for the second example 
     * this would be 3 + &omega;. Note that &theta; is any &theta; = 
     * <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;<i>d</i></sup>&frasl;<sub>2</sub> for <i>d</i> &equiv; 1 mod 
     * 4 but &omega; = &minus;<sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub> specifically. If {@code 
     *  getRing().hasHalfIntegers()} is false, this just returns the same as 
     * {@link #toString()}.
     */
    @Override
    public String toStringAlt() {
        if (this.quadRing.radicand == -3 && this.surdPartMult != 0) {
            return this.toStringAltOmega();
        } else {
            return super.toStringAlt();
        }
    }
    
    @Override
    public String toASCIIString() {
        if (this.quadRing.radicand != -1 && (this.regPartMult != 0 
                && this.surdPartMult != 0)) {
            String initial = this.regPartMult + " + " + this.surdPartMult 
                    + "sqrt(" + this.quadRing.radicand + ')';
            return initial.replace(" + -", " - ");
        }
        if (this.regPartMult == 0 && this.surdPartMult != 0) {
            return switch (this.surdPartMult) {
                case -1 -> "-i";
                case 1 -> "i";
                default -> this.surdPartMult + "i";
            };
        }
        if (this.surdPartMult != 0) {
            String initial = this.regPartMult + " + " + this.surdPartMult + "i";
            String intermediate = initial.replace(" + -", " - ");
            return intermediate.replace(" 1i", " i");
        }
        return Integer.toString(this.regPartMult);
    }
    
    @Override
    public String toASCIIStringAlt() {
        if (this.quadRing.d1mod4) {
            if (this.surdPartMult == 0 && this.quadRing.radicand == -3) {
                return Integer.toString(this.regPartMult);
            }
            if (this.regPartMult == 1) return "-omega";
            if (this.regPartMult == -1) return OMEGA_WORD_ASCII;
            if (this.regPartMult == -this.surdPartMult 
                    && Math.abs(this.regPartMult) != 1) {
                int adjust = (this.denominator == 1) ? 2 : 1;
                return (this.surdPartMult * adjust) + OMEGA_WORD_ASCII;
            }
            return this.toStringAltOmega().replace(Character
                    .toString(OMEGA_LETTER), OMEGA_WORD_ASCII)
                    .replace(MINUS_SIGN_CHARACTER, '-');
        }
        if ((this.quadRing.radicand == -1 && this.surdPartMult != 0) 
                || (this.quadRing.radicand % 4 == -2 && this.surdPartMult != 0) 
                || (this.quadRing.radicand % 4 == -1 && this.surdPartMult != 0)) {
            return this.toASCIIString();
        }
        if (this.quadRing.radicand == -1 || this.quadRing.radicand % 4 == -2 
                || this.quadRing.radicand % 4 == -1) {
            return Integer.toString(this.regPartMult);
        }
        return "REWIND TO FAILING";
    }
    
    @Override
    public String minPolynomialString() {
        return "x";
    }
    
    /**
     * Gives twice the real part of the imaginary quadratic integer. If the ring 
     * has so-called "half-integers," this might be an odd number, otherwise it 
     * should always be an even number.
     * @return Twice the real part. For example, for &minus;3/2 + 
     * (5&radic;&minus;7)/2, this would be &minus;3; for &minus;3 + 
     * 5&radic;&minus;7, this would be &minus;6.
     */
    public long getTwiceRealPartMult() {
        long twiceRealPartMult = this.regPartMult;
        if (this.denominator == 1) {
            twiceRealPartMult *= 2;
        }
        return twiceRealPartMult;
    }
    
    /**
     * Gives twice the imaginary part of the imaginary quadratic integer, before 
     * multiplication by &radic;<i>d</i>. If the ring has so-called 
     * "half-integers," this might be an odd number, otherwise it should always 
     * be an even number.
     * @return Twice the imaginary part before multiplication by 
     * &radic;<i>d</i>. For example, for &minus;3/2 + (5&radic;&minus;7)/2, this 
     * would be 5; for &minus;3 + 5&radic;&minus;7, this would be 10.
     */
    public long getTwiceImagPartMult() {
        long twiceImagPartMult = this.surdPartMult;
        if (this.denominator == 1) {
            twiceImagPartMult *= 2;
        }
        return twiceImagPartMult;
    }

    /**
     * Gives this imaginary quadratic integer's distance from 0 as a floating 
     * point number. Sometimes called "complex norm," "complex argument," 
     * "phase" or "amplitude."
     * @return This distance from 0 of the imaginary quadratic integer as a 
     * floating point number. For example, for 5/2 + (&radic;&minus;7)/2, this 
     * would be approximately 2.82842712. For a purely real positive integer, 
     * just the integer itself as a floating point number, and likewise for 
     * purely real negative integers this is the integer itself multiplied by 
     * &minus;1.0.
     */
    @Override
    public double abs() {
        double realLeg = ((double) this.regPartMult) * this.regPartMult;
        double imagLeg = ((double) this.surdPartMult) * this.surdPartMult 
                * this.quadRing.absRadicand;
        double hypotenuseSquared = realLeg + imagLeg;
        return Math.sqrt(hypotenuseSquared);
    }

    /**
     * Gets the real part of this imaginary quadratic integer. May be half an 
     * integer.
     * @return The real part of the imaginary quadratic integer. For example, 
     * for &minus;1/2 + (&radic;&minus;7)/2, the result should be &minus;0.5.
     */
    @Override
    public double getRealPartNumeric() {
        return this.numValRe;
    }
    
    /**
     * Gets the imaginary part of this imaginary quadratic integer multiplied by 
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
    
    /**
     * Indicates whether the real part of this number, as given by {@link 
     * #getRealPartNumeric()}, is an approximation or not. It should not be an 
     * approximation.
     * @return Always false for imaginary quadratic integers, since the real 
     * part is either an ordinary integer of half an ordinary integer.
     */
    @Override
    public boolean isReApprox() {
        return false;
    }
    
    /**
     * Indicates whether the imaginary part of this number, as given by {@link 
     * #getImagPartNumeric()}, is an approximation or not. This is often the 
     * case for imaginary quadratic integers that are not from 
     * <b>Z</b>[<i>i</i>].
     * @return False if the imaginary part is 0 or this is an algebraic integer 
     * from <b>Z</b>[<i>i</i>], true otherwise.
     */
    @Override
    public boolean isImApprox() {
        return this.surdPartMult != 0 && this.quadRing.radicand != -1;
    }
    
    /**
     * Gives the angle on the complex plane formed by a line segment starting at 
     * this imaginary quadratic integer and extending to 0, and another line 
     * segment going along on the real axis from 0 to positive infinity. For 
     * numbers with positive nonzero imaginary part, the angle should be 
     * positive, and for numbers with negative nonzero imaginary part, the angle 
     * should be negative.
     * @return The angle expressed in radians, ranging from &minus;&pi; radians 
     * to &pi; radians. For example, the angle of <i>i</i> should be roughly 
     * 1.57 radians (90 degrees) and the angle of &minus;<i>i</i> should be 
     * roughly &minus;1.57 radians (&minus;90 degrees). If you need this angle 
     * in degrees, you can use <code>Math.toDegrees(double)</code> to make the 
     * conversion.
     */
    @Override
    public double angle() {
        return Math.atan2(this.numValIm, this.numValRe);
    }
    
    /**
     * Infers a step between two points, so that the distance between them can 
     * be split up into intervals of equal length. This is used by an auxiliary 
     * {@link ImaginaryQuadraticIntegerLine} constructor for which the caller 
     * does not have to specify a step.
     * @param startPoint The point to start from. For example, &minus;21/2 + 
     * 7&radic;(&minus;3)/2.
     * @param endPoint The point to end on. Preferably not the same as 
     * <code>startPoint</code>. For example, 49/2 + 21&radic;(&minus;3)/2.
     * @return The inferred step. It will be, at most, the distance between 
     * <code>startPoint</code> and <code>endPoint</code>, and at least a number 
     * with norm 1 (provided <code>startPoint</code> and <code>endPoint</code> 
     * are distinct. If they are the same, the result will simply be 0. For the 
     * examples given above, the example result would be 5/2 + 
     * &radic;(&minus;3)/2.
     * @throws AlgebraicDegreeOverflowException If <code>startPoint</code> and 
     * <code>endPoint</code> come from different rings. For example, if 
     * <code>startPoint</code> is 6 + <i>i</i> but <code>endPoint</code> is 
     * &minus;&radic;(&minus;2).
     */
    public static ImaginaryQuadraticInteger 
        inferStep(ImaginaryQuadraticInteger startPoint, 
                ImaginaryQuadraticInteger endPoint) {
        if (startPoint.equals(endPoint)) {
            return new ImaginaryQuadraticInteger(0, 0, startPoint.quadRing);
        }
        int startRe = startPoint.regPartMult;
        int startIm = startPoint.surdPartMult;
        int endRe = endPoint.regPartMult;
        int endIm = endPoint.surdPartMult;
        if (startPoint.quadRing.d1mod4) {
            if (startPoint.denominator == 1) {
                startRe *= 2;
                startIm *= 2;
            }
            if (endPoint.denominator == 1) {
                endRe *= 2;
                endIm *= 2;
            }
        }
        QuadraticInteger step;
        if (startRe == endRe) {
            step = new ImaginaryQuadraticInteger(0, 1, startPoint.quadRing);
            if (startIm > endIm) {
                step = step.times(-1);
            }
        } else if (startIm == endIm) {
            step = new ImaginaryQuadraticInteger(1, 0, startPoint.quadRing);
            if (startRe > endRe) {
                step = step.times(-1);
            }
        } else {
            step = endPoint.minus(startPoint);
            boolean keepGoing = true;
            int divisor = 2;
            while (keepGoing) {
                try {
                    step = step.divides(divisor);
                    keepGoing = step.abs() > 1.0;
                    divisor = 1;
                } catch (NotDivisibleException nde) {
                    keepGoing = nde.getAbs() > 1.0;
                }
                divisor++;
            }
        }
        return (ImaginaryQuadraticInteger) step;
    }
    
    /*(TEMPORARY JAVADOC DISABLE)*
     * Makes a list of the imaginary quadratic integers in a given particular 
     * imaginary quadratic ring which on the complex plane are on straight line 
     * starting with this imaginary quadratic integer and the specified other 
     * imaginary quadratic integer. Infers direction and step as needed.
     * @param endPoint The imaginary quadratic integer to end with.
     * @return A list of imaginary quadratic integers, starting with this 
     * imaginary quadratic integer and ending with <code>endPoint</code>, with 
     * other imaginary quadratic integers in between if applicable.
     * @throws AlgebraicDegreeOverflowException If this imaginary quadratic 
     * integer and <code>endPoint</code> come from different imaginary quadratic 
     * rings.
     */
    public ImaginaryQuadraticIntegerLine 
        to(ImaginaryQuadraticInteger endPoint) {
        if (!this.quadRing.equals(endPoint.quadRing)) {
            String exceptionMessage = "Ring that contains both " 
                    + this.toASCIIString() + " and " + endPoint.toASCIIString() 
                    + " contains infinitely many algebraic integers between them";
            throw new AlgebraicDegreeOverflowException(exceptionMessage, 2, 
                    this, endPoint);
        }
        return new ImaginaryQuadraticIntegerLine(endPoint, this);
    }
      
    /**
     * Interprets a String that contains 0s, 1s, 2s and/or 3s as the 
     * quater-imaginary representation of a Gaussian integer. Computer pioneer 
     * Donald Knuth is the first person known to propose this system, in which 
     * any Gaussian integer can be represented without the need for minus signs 
     * and without the need to separate the real and imaginary parts of the 
     * number.
     * @param str The text to parse. May contain spaces, which will be stripped 
     * out prior to parsing. May also contain a "decimal" dot followed by either 
     * "2" and zero or more zeroes, or just zeroes.
     * @return An imaginary quadratic integer object containing the Gaussian 
     * integer represented by the quater-imaginary String.
     * @throws NumberFormatException If <code>str</code> has a "decimal" dot 
     * followed by any digit other than a single 2 or a bunch of zeroes, or if 
     * it contains digits other than 0, 1, 2 or 3, this runtime exception will 
     * be thrown. The problematic character mentioned in the exception message 
     * may or may not be the only parsing obstacle.
     */
    public static QuadraticInteger parseQuaterImaginary(String str) {
        QuadraticInteger currPower = ONE_IN_GAUSSIAN;
        QuadraticInteger currPowerMult;
        QuadraticInteger parsedSoFar = ZERO_IN_GAUSSIAN;
        str = str.replace(" ", "");
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
                throw new NumberFormatException("'" 
                        + str.charAt(currFractPlace - 1) 
                        + "' after \"decimal\" separator is not a valid digit");
            }
            if (str.length() == dotPlace + 1) {
                str = str + "0";
            }
            str = str.substring(0, dotPlace + 2);
        }
        String dotZeroEnding = dfs.getDecimalSeparator() + "0";
        if (str.endsWith(dotZeroEnding)) {
            str = str.substring(0, str.length() - 2);
        }
        String dotTwoEnding = dfs.getDecimalSeparator() + "2";
        if (str.endsWith(dotTwoEnding)) {
            parsedSoFar = NEG_IMAG_UNIT;
            str = str.substring(0, str.length() - 2);
        }
        char currDigit;
        for (int i = str.length() - 1; i > -1; i--) {
            currDigit = str.charAt(i);
            switch (currDigit) {
                case '0' -> currPowerMult = ZERO_IN_GAUSSIAN;
                case '1' -> currPowerMult = currPower;
                case '2' -> currPowerMult = currPower.times(2);
                case '3' -> currPowerMult = currPower.times(3);
                default -> {
                    String excMsg = "'" + currDigit 
                            + "' is not a valid quater-imaginary digit";
                    throw new NumberFormatException(excMsg);
                }
            }
            parsedSoFar = parsedSoFar.plus(currPowerMult);
            currPower = currPower.times(QUATER_BASE);
        }
        return parsedSoFar;
    }
    
    /**
     * Creates an <code>ImaginaryQuadraticInteger</code> based on the parameters 
     * according to "omega" notation. Here &omega; = 
     * &minus;<sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub>, a complex cubic root of 
     * unity. Thus the returned algebraic integer can also be instantiated as 
     * <code>new ImaginaryQuadraticInteger(2 * m - n, n, new
     * ImaginaryQuadraticRing(-3), 2)</code>.
     * @param m The "non-omega" part of the phi notation representation of the 
     * number. For example, &minus;2.
     * @param n The "omega" part of the phi notation representation of the 
     * number. For example, 1.
     * @return An <code>ImaginaryQuadraticInteger</code> object. For example 
     * &minus;<sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub> = &minus;2 + &omega;.
     */
    public static ImaginaryQuadraticInteger applyOmega(int m, int n) {
        return (ImaginaryQuadraticInteger) QuadraticInteger.apply(2 * m - n, n, 
                new ImaginaryQuadraticRing(-3), 2);
    }
    
    /**
     * Alternative constructor, may be used when the denominator is known to be 
     * 1. For example, this constructor may be used for &minus;1 + 
     * &radic;&minus;3. For &minus;1/2 + (&radic;&minus;3)/2, it will be 
     * necessary to use the primary constructor. One could always construct 
     * &minus;1 + &radic;&minus;3 and then use {@link 
     * QuadraticInteger#divides(int)} with a divisor of 2, but that would 
     * probably be too circuitous in most cases.
     * @param a The real part of the imaginary quadratic integer. For example, 
     * for 5 + 4&radic;&minus;7, this parameter would be 5.
     * @param b The imaginary part of the imaginary quadratic integer divided by 
     * &radic;<i>d</i>. For example, for 5 + 4&radic;&minus;7, this parameter 
     * would be 4.
     * @param ring The ring to which this algebraic integer belongs to. For 
     * example, for 5 + &radic;&minus;7, this parameter could be <code>new 
     * ImaginaryQuadraticRing(-7)</code>.
     * @throws IllegalArgumentException If <code>ring</code> is not of type 
     * {@link ImaginaryQuadraticRing}. This exception will occur even if 
     * <code>b</code> equals 0 (there will be no quiet substitutions like in an 
     * earlier version of this class).
     * @throws NullPointerException If <code>ring</code> is null.
     */
    public ImaginaryQuadraticInteger(int a, int b, QuadraticRing ring) {
        this(a, b, ring, 1);
    }
    
    /**
     * Primary constructor. If the denominator is known to be 1, the alternative 
     * constructor may be used.
     * @param a The real part of the imaginary quadratic integer, multiplied by 
     * 2 when applicable. For example, for 7/2 + (29&radic;&minus;3)/2, this 
     * parameter would be 7.
     * @param b The imaginary part divided by &radic;<i>d</i> and multiplied by 
     * 2 when applicable. For example, for 7/2 + (29&radic;&minus;3)/2, this 
     * parameter would be 29.
     * @param ring The ring to which this algebraic integer belongs to. For 
     * example, for 7/2 + (29&radic;&minus;3)/2, this parameter could be 
     * <code>new ImaginaryQuadraticRing(-3)</code>.
     * @param denom In most cases 1, but may be 2 if <code>a</code> and 
     * <code>b</code> have the same parity and 
     * <code>ring</code>{@link QuadraticRing#hasHalfIntegers() 
     * .hasHalfIntegers()} is true. If that is the case, &minus;2 may also be 
     * used, and &minus;1 can always be used; the constructor will quietly 
     * substitute 1 or 2 and multiply <code>a</code> and <code>b</code> by 
     * &minus;1.
     * @throws IllegalArgumentException If <code>ring</code> is not of type 
     * {@link ImaginaryQuadraticRing}. This exception will occur even if 
     * <code>b</code> equals 0 (there will be no quiet substitutions like in an 
     * earlier version of this class).
     * @throws NullPointerException If <code>ring</code> is null.
     */
    public ImaginaryQuadraticInteger(int a, int b, QuadraticRing ring, int denom) {
        super(a, b, ring, denom);
        if (!(ring instanceof ImaginaryQuadraticRing)) {
            String excMsg = "Ring is not imaginary as needed";
            throw new IllegalArgumentException(excMsg);
        }
        double realPart = this.regPartMult;
        if (this.denominator == 2) {
            realPart /= 2;
        }
        this.numValRe = realPart;
        double imagPartwRad = this.surdPartMult 
                * this.quadRing.getAbsNegRadSqrt();
        if (this.denominator == 2) {
            imagPartwRad /= 2;
        }
        this.numValIm = imagPartwRad;
    }
    
}
