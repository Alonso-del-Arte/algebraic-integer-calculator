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
package algebraics.quadratics;

import algebraics.AlgebraicDegreeOverflowException;
import algebraics.AlgebraicInteger;
import algebraics.UnsupportedNumberDomainException;
import arithmetic.Arithmeticable;
import arithmetic.NotDivisibleException;
import calculators.NumberTheoreticFunctionsCalculator;
import fractions.Fraction;

import java.io.Serializable;

/**
 * Provides a template for defining objects to represent real or imaginary 
 * quadratic integers. Also defines the four basic arithmetic operations 
 * (addition, subtraction, multiplication and division), so the subclasses don't 
 * have to define them, unless absolutely necessary.
 * @author Aonso del Arte
 */
public abstract class QuadraticInteger implements AlgebraicInteger, 
        Arithmeticable<QuadraticInteger>, Serializable {
    
    private static final String PLUS_SIGN_SPACED = " + ";
    
    private static final char MINUS_SIGN = '\u2212';
    
    private static final char[] MINUS_SIGN_CHARACTER_ARRAY = {MINUS_SIGN};
    
    private static final String MINUS_SIGN_STRING 
            = new String(MINUS_SIGN_CHARACTER_ARRAY);
    
    private static final String MINUS_SIGN_SPACED = " " + MINUS_SIGN + ' ';
    
    private static final String MINUS_SIGN_THEN_SPACE = MINUS_SIGN_STRING + ' ';
    
    private static final String PLUS_SIGN_THEN_DASH = PLUS_SIGN_SPACED + '-';
    
    private static final String PLUS_SIGN_THEN_MINUS = PLUS_SIGN_SPACED 
            + MINUS_SIGN;
    
    private static final char SQRT_SYMBOL = '\u221A';
    
    private static final char[] RADICAND_CHARS = {SQRT_SYMBOL, '(', 'd', ')'};
    
    private static final String RADICAND_CHAR_SEQ = new String(RADICAND_CHARS);
    
    private static final char THETA_LETTER = '\u03B8';
    
    private static final char PHI_LETTER = '\u03C6';
    
    private static final char OMEGA_LETTER = '\u03C9';
    
    private static final Fraction ONE_HALF = new Fraction(1, 2);

    final int regPartMult;
    final int surdPartMult;
    final QuadraticRing quadRing;
    final int denominator;
    
    /**
     * Retrieves an object representing the ring this quadratic integer belongs 
     * to. This function can't be overridden, in order to guarantee that the 
     * return value is the non-null ring parameter the constructor received.
     * @return An object subclassed from {@link QuadraticRing}. In the case of 
     * an {@link ImaginaryQuadraticInteger}, this should be an {@link 
     * ImaginaryQuadraticRing}; and in the case of a {@link 
     * RealQuadraticInteger}, this should be a {@link RealQuadraticRing}.
     */
    @Override
    public final QuadraticRing getRing() {
        return this.quadRing;
    }
    
    /**
     * Retrieves the part of the quadratic integer that is not multiplied by the 
     * square root of the radicand, possibly multiplied by 2.
     * @return In the case of an imaginary quadratic integer, the real part, 
     * possibly multiplied by 2. In the case of a real quadratic integer, the 
     * part of the real quadratic integer that is not multiplied by the square 
     * root of the radicand, possibly multiplied by 2. For example, for 
     * <sup>7</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub>, 
     * this would be 7. For 7 + &radic;3, this would also 
     * be 7.
     */
    public int getRegPartMult() {
        return this.regPartMult;
    }
    
    /**
     * Retrieves the part of the quadratic integer that is multiplied by the 
     * square root of the radicand, possibly multiplied by 2.
     * @return In the case of an imaginary quadratic integer, the imaginary 
     * part, possibly multiplied by 2. In the case of a real quadratic integer, 
     * the part of the real quadratic integer that is multiplied by the square 
     * root of the radicand, possibly multiplied by 2. For example, for 
     * <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>3&radic;&minus;3</sup>&frasl;<sub>2</sub>, this would be 3. For 5 + 
     * 3&radic;3, this would also be 3.
     */
    public int getSurdPartMult() {
        return this.surdPartMult;
    }
    
    /**
     * Gets the denominator of the imaginary quadratic integer when represented 
     * as a fraction in lowest terms.
     * @return 2 only in the case of so-called "half-integers," always 1 
     * otherwise. So, if {@link #getRing()}{@link 
     * ImaginaryQuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is false, 
     * this getter should always return 1. For example, for 
     * <sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;7</sup>&frasl;<sub>2</sub>, this would be 2, for 3 + 
     * &radic;&minus;7 this would be 1. In the ring of the Gaussian integers, 
     * this getter should always return 1. Even if &minus;1 or &minus;2 was 
     * successfully used as a denominator in the constructor, this getter will 
     * still return 1 or 2.
     */
    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Gives the algebraic degree of the algebraic integer. Should not be higher 
     * than 2.
     * @return 0 if the algebraic integer is 0, 1 if it's a purely real integer, 
     * 2 otherwise.
     */
    @Override
    public int algebraicDegree() {
        if (this.surdPartMult == 0) {
            if (this.regPartMult == 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }
    
    /**
     * Calculates the trace of the quadratic integer (twice the "regular" part, 
     * twice the real part in the case of imaginary quadratic integers). 
     * Overflows are mathematically impossible.
     * @return Twice the "regular" part. For example, for 1/2 + 
     * (&radic;&minus;7)/2, this would be 1; for 1 + &radic;&minus;7 it would be 
     * 2; and for 2 + &radic;14, it would be 4.
     */
    @Override
    public long trace() {
        if (this.denominator == 2) {
            return this.regPartMult;
        } else {
            return 2L * this.regPartMult;
        }
    }
    
    /**
     * Calculates the norm of the quadratic integer. 64-bit integers are used 
     * for the computation, but there is no overflow checking.
     * @return Square of the "regular" part minus square of the surd part times 
     * the radicand of the square root that is adjoined to <b>Q</b> (e.g., 3 in 
     * the case of <b>Z</b>[&radic;3]. Should be 0 if and only if the quadratic 
     * integer is 0. Negative norms for imaginary quadratic integers are 
     * mathematically impossible, but could occur in this program as a result of 
     * an arithmetic overflow. Norms for nonzero real quadratic integers may be 
     * positive or negative. For example, the norm of 
     * <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;7</sup>&frasl;<sub>2</sub> is 2; the norm of 1 + 
     * &radic;&minus;7 is 8; and the norm of 2 + &radic;14 is &minus;10.
     */
    @Override
    public long norm() {
        int denomSquared = this.denominator * this.denominator;
        return ((long) this.regPartMult * this.regPartMult 
                - ((long) this.surdPartMult * this.surdPartMult 
                * this.quadRing.radicand)) / denomSquared;
    }
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic 
     * integer.
     * @return An array of three integers. If the algebraic integer is of degree 
     * 2, the array will be {norm, negative trace, 1}; if of degree 1, then 
     * {number, 1, 0}, and for 0, {0, 1, 0}. For example, for 5/2 + 
     * &radic;(&minus;7)/2, the result would be {8, &minus;5, 1}. A return of 
     * {0, 0, 0} would indicate a major malfunction in {@link 
     * #algebraicDegree()}.
     * @throws RuntimeException In the highly unlikely event of a malfunctioning 
     * <code>algebraicDegree()</code> returning a number less than 0 or greater 
     * than 2. This exception will then wrap an {@link 
     * algebraics.AlgebraicDegreeOverflowException 
     * AlgebraicDegreeOverflowException} with the unexpected degree in the 
     * message of that exception.
     */
    @Override
    public long[] minPolynomialCoeffs() {
        long[] coeffs = {0L, 1L, 0L};
        int degree = this.algebraicDegree();
        switch (degree) {
            case 1: 
                coeffs[0] = -1L * this.regPartMult;
            case 0:
                break;
            case 2: 
                coeffs[0] = this.norm();
                coeffs[1] = -this.trace();
                coeffs[2] = 1L;
                break;
            default:
                String excMsg = "Excessive degree " + degree 
                        + " occurred somehow";
                throw new AlgebraicDegreeOverflowException(excMsg, 2, this, 
                        this);
        }
        return coeffs;
    }
    
    /**
     * Gives the minimal polynomial as text, using Unicode characters when 
     * necessary. In particular, U+00B2 (superscript 2) and U+2212 (minus sign) 
     * will be used when applicable.
     * @return The minimal polynomial as text. If the algebraic degree is 2, the 
     * text should start off with "x&#178;". For example, for 5/2 + 
     * &radic;(&minus;7)/2, the result would be "x&#178; &#8722; 5x + 8".
     */
    @Override
    public String minPolynomialString() {
        String polString = this.minPolynomialStringTeX();
        polString = polString.replace("^2", "\u00B2");
        polString = polString.replace("-", "\u2212");
        return polString;
    }
    
    /**
     * Gives the minimal polynomial as plaintext suitable for use in a TeX 
     * document, using ASCII characters only. The TeX rendering engine should 
     * then be able to format appropriately. Since the only explicit exponent 
     * should be 2, that numeric literal is not wrapped in curly braces.
     * @return If the algebraic degree is 2, the text should start 
     * off with "x^2". For example, for 5/2 + &radic;(&minus;7)/2, the result 
     * would be "x^2 - 5x + 8".
     */
    @Override
    public String minPolynomialStringTeX() {
        String polString = "";
        long[] polCoeffs = this.minPolynomialCoeffs();
        switch (this.algebraicDegree()) {
            case 0:
                polString = "x";
                break;
            case 1:
                if (polCoeffs[0] < 0) {
                    polString = "x - " + ((-1) * polCoeffs[0]);
                } else {
                    polString = "x + " + polCoeffs[0];
                }
                break;
            case 2:
                polString = "x^2 ";
                if (polCoeffs[1] < -1) {
                    polString += ("- " + ((-1) * polCoeffs[1]) + "x ");
                }
                if (polCoeffs[1] == -1) {
                    polString += "- x ";
                }
                if (polCoeffs[1] == 1) {
                    polString += "+ x ";
                }
                if (polCoeffs[1] > 1) {
                    polString += ("+ " + polCoeffs[1] + "x ");
                }
                if (polCoeffs[0] < 0) {
                    polString += ("- " + ((-1) * polCoeffs[0]));
                } else {
                    polString += ("+ " + polCoeffs[0]);
                }
                break;
        }
        return polString;
    }

    /**
     * Gives the minimal polynomial as text suitable for use in an HTML page. 
     * Italics tags are used for lowercase X when needed, and superscript tags 
     * are used for the exponent 2 when necessary.
     * @return If the algebraic degree is 2, the text should start 
     * off with "&lt;i&gt;x&lt;/i&gt;&lt;sup&gt;2&lt;/sup&gt;". For example, for 
     * 5/2 + &radic;(&minus;7)/2, the result would be 
     * "&lt;i&gt;x&lt;/i&gt;&lt;sup&gt;2&lt;/sup&gt; &amp;minus; 
     * 5&lt;i&gt;x&lt;/i&gt; + 8". Saved to an HTML page, this ought to render 
     * to look something like this: "<i>x</i><sup>2</sup> &minus; 5<i>x</i> + 
     * 8".
     */
    @Override
    public String minPolynomialStringHTML() {
        String polString = this.minPolynomialStringTeX();
        polString = polString.replace("x", "<i>x</i>");
        polString = polString.replace("^2", "<sup>2</sup>");
        polString = polString.replace("-", "&minus;");
        return polString;
    }

    /**
     * Gives the conjugate of the quadratic integer, the number which multiplied 
     * by this number gives the norm.
     * @return This quadratic integer with the "surd" part negated. For example, 
     * the conjugate of 5/2 + (&radic;&minus;7)/2 is 5/2 &minus; 
     * (&radic;&minus;7)/2.
     */
    public QuadraticInteger conjugate() {
        if (this instanceof ImaginaryQuadraticInteger) {
            return new ImaginaryQuadraticInteger(this.regPartMult, 
                    -this.surdPartMult, this.quadRing, this.denominator);
        }
        if (this instanceof RealQuadraticInteger) {
            return new RealQuadraticInteger(this.regPartMult, 
                    -this.surdPartMult, this.quadRing, this.denominator);
        }
        String excMsg = this.getClass().getName() 
                + " is not a supported number domain for conjugate";
        throw new UnsupportedNumberDomainException(excMsg, this, this);
    }
        
    /**
     * A text representation of the quadratic integer, with the "regular" part  
     * first and the "surd" part second.
     * @return A {@code String} instance representing the quadratic integer.  
     * Because of the square root character "&radic;", this might not be 
     * suitable for console output.
     */
    @Override
    public String toString() {
        String preliminary = this.regPartMult + PLUS_SIGN_SPACED 
                + this.surdPartMult + RADICAND_CHAR_SEQ;
        String numStr = preliminary.replace("0 + ", "")
                .replace(PLUS_SIGN_THEN_DASH, MINUS_SIGN_SPACED).replace("d", 
                        Integer.toString(this.quadRing.radicand));
        return numStr.replace('-', MINUS_SIGN);
    }
    
    /**
     * A text representation of the quadratic integer, using theta notation when 
     * {@link #getRing()}{@link ImaginaryQuadraticRing#hasHalfIntegers() 
     * .hasHalfIntegers()} is true. For some of the examples, suppose this 
     * number is <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;11</sup>&frasl;<sub>2</sub> or 
     * <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;13</sup>&frasl;<sub>2</sub>.
     * @return A representation using theta notation. For both of the examples, 
     * this would be 2 + &theta;. Note that &omega; here is used strictly to 
     * mean &minus;<sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub>. If {@link 
     * #getRing()}{@link QuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is 
     * false, this just returns the same as {@link #toString()}.
     */
    public String toStringAlt() {
        String altQIString;
        if (this.quadRing.d1mod4) {
            int nonThetaPart = this.regPartMult;
            int thetaPart = this.surdPartMult;
            char thetaLetter = THETA_LETTER;
            if (this.denominator == 1) {
                nonThetaPart *= 2;
                thetaPart *= 2;
            }
            switch (this.quadRing.radicand) {
                case -3:
                    nonThetaPart = (nonThetaPart + thetaPart)/2;
                    thetaLetter = OMEGA_LETTER;
                    break;
                case 5:
                    thetaLetter = PHI_LETTER;
                default:
                    nonThetaPart = (nonThetaPart - thetaPart)/2;
            }
            altQIString = Integer.toString(nonThetaPart);
            if (nonThetaPart == 0 && thetaPart != 0) {
                if (thetaPart < -1 || thetaPart > 1) {
                    altQIString = Integer.toString(thetaPart) + thetaLetter;
                }
                if (thetaPart == -1) {
                    altQIString = "-" + thetaLetter;
                }
                if (thetaPart == 1) {
                    altQIString = Character.toString(thetaLetter);
                }
            } else {
                if (thetaPart < -1) {
                    altQIString += (" - " + ((-1) * thetaPart) + thetaLetter);
                }
                if (thetaPart == -1) {
                    altQIString += (" - " + thetaLetter);
                }
                if (thetaPart == 1) {
                    altQIString += (" + " + thetaLetter);
                }
                if (thetaPart > 1) {
                    altQIString += (" + " + thetaPart + thetaLetter);
                }
            }
        } else {
            altQIString = this.toString();
        }
        altQIString = altQIString.replace("-", "\u2212");
        return altQIString;
    }
    
    /**
     * A text representation of the quadratic integer using only ASCII 
     * characters. All this function does is replace "\u221A" with "sqrt".
     * @return A String using only ASCII characters. For example, for 
     * "\u221A(-2)", the result will be "sqrt(-2)"; for "-3/2 + \u221A(37)/2", 
     * the result would be "-3/2 + sqrt(37)/2".
     */
    @Override
    public String toASCIIString() {
        String QIString = this.toString();
        QIString = QIString.replace("\u221A", "sqrt");
        QIString = QIString.replace("\u2212", "-");
        return QIString;
    }
    
    /**
     * A text representation of the quadratic integer with theta notation when 
     * applicable, but using only ASCII characters. After writing {@link 
     * #toASCIIString}, it only made sense to write this one as well.
     * @return A String using only ASCII characters. For instance, for "-1 + 
     * &theta;", the result will be "-1 + theta". If {@link #getRing()}{@link 
     * ImaginaryQuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is false, 
     * this just returns the same String as toASCIIString().
     */
    public String toASCIIStringAlt() {
        if (this.quadRing.d1mod4) {
            String intermediate = this.toStringAlt();
            if (this.quadRing.radicand == -3 || this.quadRing.radicand == 5) {
                intermediate = intermediate.replace("\u03C9", "omega");
                intermediate = intermediate.replace("\u03C6", "phi");
            } else {
                intermediate = intermediate.replace("\u03B8", "theta");
            }
            intermediate = intermediate.replace("\u2212", "-");
            return intermediate;
        } else {
            return this.toASCIIString();
        }
    }
    
    /**
     * A text representation of the quadratic integer suitable for use in a TeX 
     * document. If you prefer a single denominator instead of a separate 
     * denominator for the "regular" and "surd" parts, use {@link 
     * #toTeXStringSingleDenom()}. Although I have a unit test for this 
     * function, I have not tested inserting the output of this function into an 
     * actual TeX document.
     * @return A String. For example, for 1/2 + sqrt(-7)/2, the result should be 
     * "\frac{1}{2} + \frac{\sqrt{-7}}{2}".
     */
    @Override
    public String toTeXString() {
        String QIString;
        if (this.quadRing.radicand == -1) {
            QIString = this.toString();
            QIString = QIString.replace("\u2212", "-");
            return QIString;
        }
        if (this.norm() == 0) {
            return "0";
        }
        if (this.denominator == 1) {
            if (this.regPartMult == 0) {
                switch (this.surdPartMult) {
                    case -1:
                        QIString = "-\\sqrt{" + this.quadRing.radicand + "}";
                        break;
                    case 1:
                        QIString = "\\sqrt{" + this.quadRing.radicand + "}";
                        break;
                    default:
                        QIString = this.surdPartMult + " \\sqrt{" 
                                + this.quadRing.radicand + "}";
                }
            } else {
                if (this.surdPartMult == 0) {
                    return Integer.toString(this.regPartMult);
                } else {
                    QIString = this.regPartMult + " + " + this.surdPartMult 
                            + " \\sqrt{" + this.quadRing.radicand + "}";
                    QIString = QIString.replace("+ -", "- ");
                    QIString = QIString.replace(" 1 \\sqrt", " \\sqrt");
                }
            }
        } else {
            QIString = "\\frac{" + this.regPartMult + "}{2} + \\frac{" 
                    + this.surdPartMult + " \\sqrt{" + this.quadRing.radicand 
                    + "}}{2}";
            QIString = QIString.replace("\\frac{-", "-\\frac{");
            QIString = QIString.replace("\\frac{1 \\sqrt", "\\frac{\\sqrt");
            QIString = QIString.replace("+ -", "- ");
        }
        return QIString;
    }
    
    /**
     * A text representation of the quadratic integer suitable for use in a TeX 
     * document, but only with a single denominator for both the "regular" and 
     * "surd" parts, as opposed to {@link #toTeXString()}. Although I have a 
     * unit test for this function, I have not tested inserting the output of 
     * this function into an actual TeX document.
     * @return A String. For example, for 1/2 + sqrt(-7)/2, the result should be 
     * "\frac{1 + \sqrt{-7}}{2}".
     */
    public String toTeXStringSingleDenom() {
        String QIString;
        if (this.denominator == 2) {
            QIString = "\\frac{" + this.regPartMult + " + " + this.surdPartMult 
                    + " \\sqrt{" + this.quadRing.radicand + "}}{2}";
            QIString = QIString.replace(" 1 \\sqrt", " \\sqrt");
            QIString = QIString.replace("+ -", " - ");
            return QIString;
        } else {
            QIString = this.toTeXString();
            QIString = QIString.replace("\u2212", "-");
            return QIString;
        }
    }
    
    /**
     * A text representation of the quadratic integer suitable for use in a TeX 
     * document, with theta notation when applicable.
     * @return A String. For example, for "-1 + &theta;", the result will be "-1 
     * + \theta". If {@link #getRing()}{@link 
     * ImaginaryQuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is false, 
     * this just returns the same String as {@link #toTeXString()}.
     */
    public String toTeXStringAlt() {
        if (this.quadRing.d1mod4) {
            String QIString = this.toStringAlt();
            QIString = QIString.replace("\u03C9", "\\omega");
            QIString = QIString.replace("\u03B8", "\\theta");
            QIString = QIString.replace("\u03C6", "\\phi");
            QIString = QIString.replace("\u2212", "-");
            return QIString;
        } else {
            return this.toTeXString();
        }
    }
    
    /**
     * A text representation of the quadratic integer suitable for use in an 
     * HTML document. Although I have a unit test for this function, I have not 
     * tested inserting the output of this function into an actual HTML 
     * document.
     * @return  A String. For example, for 1/2 + sqrt(-7)/2, the result should 
     * be "1/2 + &amp;radic;(-7)/2". Note that a character entity is used for 
     * the square root symbol, so in a Web browser, the foregoing should render 
     * as "1/2 + &radic;(-7)/2".
     */
    @Override
    public String toHTMLString() {
        String QIString = this.toString();
        QIString = QIString.replace("i", "<i>i</i>");
        QIString = QIString.replace("\u221A", "&radic;");
        QIString = QIString.replace("\u2212", "&minus;");
        return QIString;
    }
    
    /**
     * A text representation of the quadratic integer suitable for use in an 
     * HTML document, with theta notation when applicable.
     * @return  A String. For example, for "-1 + &theta;", the result will be 
     * "-1 + &theta;". If {@link #getRing()}{@link 
     * ImaginaryQuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is false, 
     * this just returns the same String as {@link #toHTMLString()}. Note that 
     * character entities are used for the square root symbol and the Greek 
     * letters theta and omega.
     */
    public String toHTMLStringAlt() {
        if (this.quadRing.d1mod4) {
            String QIString = this.toStringAlt();
            QIString = QIString.replace("\u03C9", "&omega;");
            QIString = QIString.replace("\u03C6", "&phi;");
            QIString = QIString.replace("\u03B8", "&theta;");
            QIString = QIString.replace("\u2212", "&minus;");
            return QIString;
        } else {
            return this.toHTMLString();
        }
    }
    
    /**
     * Determines whether some object is equal to this number. In addition to 
     * the obvious parameters, the runtime classes need to match, as well as 
     * those of the rings, as applicable.
     * @param obj The object to compare for equality. Examples: 1 + <i>i</i>, 3 
     * + &radic;7, 3 + &radic;13, <sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;13</sup>&frasl;<sub>2</sub>, 
     * 2 from <b>Z</b>[&radic;&minus;2], 2 from <b>Z</b>[&radic;2], 
     * a {@code LocalDate} object for today's date, null.
     * @return True if the runtime class of {@code obj} is the same as the 
     * runtime class of this number, the "regular" and "surd" parts match and 
     * the rings match, false in all other cases. Each of the examples gives 
     * true when compared against itself, and false when compared against the 
     * other examples. This includes 2 from <b>Z</b>[&radic;&minus;2] and 2 from 
     * <b>Z</b>[&radic;2]. Even though the numbers are arithmetically equal, 
     * they represent the same number in different contexts.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        QuadraticInteger other = (QuadraticInteger) obj;
        if (this.regPartMult != other.regPartMult) {
            return false;
        }
        if (this.surdPartMult != other.surdPartMult) {
            return false;
        }
        if (!this.quadRing.equals(other.quadRing)) {
            return false;
        }
        return this.denominator == other.denominator;
    }
    
    /**
     * Gives a hash code for this number. It is impossible to give distinct hash 
     * codes for all possible instances of this class. The hope is that the hash 
     * codes are unique enough in a given session. The formula is subject to 
     * change in later releases of this program.
     * @return A hash code. For example, for <sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;21</sup>&frasl;<sub>2</sub>, the hash code might be 
     * &minus;196587.
     */
    @Override
    public int hashCode() {
        int hash = (int) this.norm();
        hash <<= 16;
        return hash + this.quadRing.radicand;
    }

    // TODO: Implement feature
    public static QuadraticInteger parseQuadraticInteger(QuadraticRing ring, 
            String str) {
        return null;
    }
    
    // TODO: Implement feature
    public static QuadraticInteger parseQuadraticInteger(String str) {
        return null;
    }
    
    private boolean checkCrossDomain(QuadraticInteger operand) {
        boolean flag = (this.regPartMult == 0 && operand.regPartMult == 0);
        flag = flag && (this.surdPartMult != 0 && operand.surdPartMult != 0);
        flag = flag && (this instanceof ImaginaryQuadraticInteger 
                || this instanceof RealQuadraticInteger);
        flag = flag && (operand instanceof ImaginaryQuadraticInteger 
                || operand instanceof RealQuadraticInteger);
        flag = flag && (this.quadRing.radicand != operand.quadRing.radicand);
        return flag;
    }
    
    private static void checkRange(Fraction potentialRegPart, 
            Fraction potentialSurdPart, QuadraticRing ring) {
        long potReg = potentialRegPart.getNumerator();
        long potSurd = potentialSurdPart.getNumerator();
        if (potReg < Integer.MIN_VALUE || potReg > Integer.MAX_VALUE) {
            String excMsg = "Regular part of " + potentialRegPart.toString() 
                    + " + " + potentialSurdPart.toString() + "sqrt(" 
                    + ring.getRadicand() 
                    + ") exceeds range of QuadraticInteger data type";
            throw new ArithmeticException(excMsg);
        }
        if (potSurd < Integer.MIN_VALUE || potSurd > Integer.MAX_VALUE) {
            String excMsg = "Surd part of " + potentialRegPart.toString() 
                    + " + " + potentialSurdPart.toString() + "sqrt(" 
                    + ring.getRadicand() 
                    + ") exceeds range of QuadraticInteger data type";
            throw new ArithmeticException(excMsg);
        }
    }
    
    /**
     * Addition operation, since operator+ (plus) can't be overloaded. 
     * Computations are done with 64-bit variables. Overflow checking is 
     * rudimentary.
     * @param addend The quadratic integer to be added to this quadratic 
     * integer.
     * @return A new QuadraticInteger object with the result of the operation. 
     * If both summands are from the same ring, the result will also be from 
     * that ring. However, if the summand parameter is from a different ring and 
     * this algebraic integer is purely real, the result will be given in the 
     * ring of the summand parameter.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come 
     * from different quadratic rings and both have nonzero "surd" parts, the 
     * result of the sum will be an algebraic integer of degree 4 and this 
     * runtime exception will be thrown.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * "regular" part or the "surd" part of the sum exceeds the range of the int 
     * data type. You may need long or even {@link java.math.BigInteger} for the 
     * calculation.
     */
    @Override
    public QuadraticInteger plus(QuadraticInteger addend) {
        if (addend.surdPartMult == 0) {
            return this.plus(addend.regPartMult);
        }
        if (this.surdPartMult == 0) {
            return addend.plus(this.regPartMult);
        }
        if (this.quadRing.radicand != addend.quadRing.radicand) {
            String excMsg = "This operation's result is of degree 4";
            throw new AlgebraicDegreeOverflowException(excMsg, 2, this, addend);
        }
        Fraction regPartFract = new Fraction(this.regPartMult, 
                this.denominator);
        Fraction summandFract = new Fraction(addend.regPartMult, 
                addend.denominator);
        regPartFract = regPartFract.plus(summandFract);
        Fraction surdPartFract = new Fraction(this.surdPartMult, 
                this.denominator);
        summandFract = new Fraction(addend.surdPartMult, addend.denominator);
        surdPartFract = surdPartFract.plus(summandFract);
        checkRange(regPartFract, surdPartFract, this.quadRing);
        return apply((int) regPartFract.getNumerator(), 
                (int) surdPartFract.getNumerator(), this.quadRing, 
                (int) regPartFract.getDenominator());
    }
    
    /**
     * Addition operation, since operator+ (plus) can't be overloaded. This does 
     * computations with 64-bit variables. Overflow checking is rudimentary. 
     * Although the previous plus function can be passed a QuadraticInteger with 
     * "surd" part equal to 0, this function is to be preferred if you know for 
     * sure the summand is purely real and rational.
     * @param addend The purely real and rational integer to be added to the 
     * "regular" part of the QuadraticInteger.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws ArithmeticException A runtime exception thrown if the "regular" 
     * part of the sum exceeds the range of the int data type. The "surd" part 
     * of the sum should be fine, since the summand has a tacit "surd" part of 
     * 0.
     */
    @Override
    public QuadraticInteger plus(int addend) {
        long sumRegPart = this.regPartMult;
        if (this.denominator == 2) {
            sumRegPart += (2 * addend);
        } else {
            sumRegPart += addend;
        }
        if (sumRegPart < Integer.MIN_VALUE || sumRegPart > Integer.MAX_VALUE) {
            String excMsg = "Real part of sum exceeds int data type: " 
                    + sumRegPart + " + " + this.surdPartMult + "sqrt(" 
                    + this.quadRing.radicand + ")";
            throw new ArithmeticException(excMsg);
        }
        return apply((int) sumRegPart, this.surdPartMult, this.quadRing, 
                this.denominator);
    }
    
    /**
     * Multiplies this quadratic integer by &minus;1. The result is the same as 
     * <code>times(-1)</code>, and there's probably no performance gain 
     * whatsoever. Even so, this function should generally be preferred when one 
     * multiplicand is known in advance to be &minus;1.
     * @return This quadratic integer multiplied by &minus;1. For example, given 
     * &minus;<sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub> this would give 
     * <sup>1</sup>&frasl;<sub>2</sub> &minus; 
     * <sup>&radic;&minus;3</sup>&frasl;<sub>2</sub>; given 3 + &radic;2 this 
     * would give &minus;3 &minus; &radic;2.
     */
    @Override
    public QuadraticInteger negate() {
        return QuadraticInteger.apply(-this.regPartMult, -this.surdPartMult, 
                this.quadRing, this.denominator);
    }

    /**
     * Subtraction operation, since operator- can't be overloaded. This does 
     * computations with 64-bit variables. Overflow checking is rudimentary.
     * @param subtrahend The quadratic integer to be subtracted from this 
     * quadratic integer.
     * @return A new QuadraticInteger object with the result of the operation. 
     * If both operands are from the same ring, the result will also be from 
     * that ring. However, if the subtrahend parameter is from a different ring 
     * and this algebraic integer is purely real and rational, the result will 
     * be given in the ring of the subtrahend parameter.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come 
     * from different quadratic rings and they both have nonzero "surd" parts, 
     * the result of the subtraction will be an algebraic integer of degree 4 
     * and this runtime exception will be thrown.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * "regular" part or the "surd" part of the subtraction exceeds the range of 
     * the int data type. You may need long or even {@link java.math.BigInteger} 
     * for the calculation.
     */
    @Override
    public QuadraticInteger minus(QuadraticInteger subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    /**
     * Subtraction operation, since operator- can't be overloaded. Although the 
     * previous minus function can be passed a QuadraticInteger with "surd" part 
     * equal to 0, this function is to be preferred if you know for sure the 
     * subtrahend is purely real and rational. Computations are done with 64-bit 
     * variables. Overflow checking is rudimentary.
     * @param subtrahend The purely real, rational integer to be subtracted from 
     * this quadratic integer.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws ArithmeticException A runtime exception thrown if the regular 
     * part of the subtraction exceeds the range of the <code>int</code> data 
     * type. The surd part of the sum should be fine, since the subtrahend has a 
     * tacit surd part of 0.
     */
    @Override
    public QuadraticInteger minus(int subtrahend) {
        return this.plus(-subtrahend);
    }
    
    private QuadraticInteger timesFromOtherRing(QuadraticInteger multiplicand) {
        if (this.checkCrossDomain(multiplicand)) {
            int prodSurd = this.surdPartMult * multiplicand.surdPartMult;
            if (this instanceof ImaginaryQuadraticInteger 
                    && multiplicand instanceof ImaginaryQuadraticInteger) {
                prodSurd *= -1;
            }
            int prodRad = this.quadRing.radicand 
                    * multiplicand.quadRing.radicand;
            if (!NumberTheoreticFunctionsCalculator.isSquarefree(prodRad)) {
                int kernel = NumberTheoreticFunctionsCalculator.kernel(prodRad);
                int coat = prodRad / kernel;
                prodSurd *= coat;
                prodRad = prodRad / (coat * coat);
            }
            QuadraticRing prodRing;
            QuadraticInteger product;
            if (prodRad < 0) {
                prodRing = new ImaginaryQuadraticRing(prodRad);
                product = new ImaginaryQuadraticInteger(0, prodSurd, prodRing);
            } else {
                prodRing = new RealQuadraticRing(prodRad);
                product = new RealQuadraticInteger(0, prodSurd, prodRing);
            }
            return product;
        }
        if (this.quadRing.radicand != multiplicand.quadRing.radicand) {
            String excMsg = "This operation's result is of degree 4";
            throw new AlgebraicDegreeOverflowException(excMsg, 2, this, 
                    multiplicand);
        }
        if (this.surdPartMult == 0) {
            return multiplicand.times(this.regPartMult);
        }
        if (multiplicand.surdPartMult == 0) {
            return this.times(multiplicand.regPartMult);
        }
        if (this.quadRing.radicand != multiplicand.quadRing.radicand) {
            if (this.regPartMult == 0 && multiplicand.regPartMult == 0) {
                String excMsg = "This operation would result in " + ((-1) 
                        * this.surdPartMult * multiplicand.surdPartMult) 
                        + "sqrt(" + (this.quadRing.radicand 
                        * multiplicand.quadRing.radicand) 
                        + "), a real quadratic integer???";
                throw new UnsupportedNumberDomainException(excMsg, this, 
                        multiplicand);
            } else {
                String excMsg = "This operation's result is of degree 4";
                throw new AlgebraicDegreeOverflowException(excMsg, 2, this, 
                        multiplicand);
            }
        }
        // TODO: Remember what was the reason for the next line
        throw new RuntimeException("Oops, sorry");
    }

    /**
     * Multiplication operation, since operator* can't be overloaded. 
     * Computations are done with 64-bit variables. Overflow checking is 
     * rudimentary.
     * @param multiplicand The quadratic integer to be multiplied by this 
     * quadratic integer.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come 
     * from different quadratic rings, the product will be an algebraic integer 
     * of degree 4 and this runtime exception will be thrown.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * regular part or the surd part of the product exceeds the range of the int 
     * data type. You may need long or even BigInteger for the calculation.
     */
    @Override
    public QuadraticInteger times(QuadraticInteger multiplicand) {
        if (multiplicand.surdPartMult == 0) {
            return this.times(multiplicand.regPartMult);
        }
        if (this.surdPartMult == 0) {
            return multiplicand.times(this.regPartMult);
        }
        if (this.quadRing.equals(multiplicand.quadRing)) {
            long multRegPart = this.regPartMult * multiplicand.regPartMult 
                    + this.surdPartMult * multiplicand.surdPartMult 
                    * this.quadRing.radicand;
            long multSurdPart = this.regPartMult * multiplicand.surdPartMult 
                    + this.surdPartMult * multiplicand.regPartMult;
            int multDenom = this.denominator * multiplicand.denominator;
            if (multDenom == 4) {
                multRegPart /= 2;
                multSurdPart /= 2;
                multDenom = 2;
            }
//            if (multRegPart < Integer.MIN_VALUE 
//                    || multRegPart > Integer.MAX_VALUE) {
//                String excMsg = "Real part of product exceeds int data type:" 
//                        + multRegPart + " + " + multSurdPart + "sqrt(" 
//                        + this.quadRing.radicand + ")";
//                throw new ArithmeticException(excMsg);
//            }
//            if (multSurdPart < Integer.MIN_VALUE 
//                    || multSurdPart > Integer.MAX_VALUE) {
//                String excMsg = "Imaginary part of product exceeds int data type:" 
//                        + multRegPart + " + " + multSurdPart + "sqrt(" 
//                        + this.quadRing.radicand + ")";
//                throw new ArithmeticException(excMsg);
//            }
            return apply((int) multRegPart, (int) multSurdPart, this.quadRing, 
                    multDenom);
        } else {
            return this.timesFromOtherRing(multiplicand);
        }
    }
    
    /**
     * Multiplication operation, since operator* can't be overloaded. Although 
     * the previous times function can be passed an ImaginaryQuadraticInteger 
     * with imagPartMult equal to 0, this function is to be preferred if you 
     * know for sure the multiplicand is purely real. Computations are done with 
     * 64-bit variables. Overflow checking is rudimentary.
     * @param multiplicand The purely real integer to be multiplied by this 
     * quadratic integer.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * regular part or the surd part of the product exceeds the range of the int 
     * data type.
     */
    @Override
    public QuadraticInteger times(int multiplicand) {
        long multRegPart = this.regPartMult * multiplicand;
        long multSurdPart = this.surdPartMult * multiplicand;
//        if (multRegPart < Integer.MIN_VALUE 
//                || multRegPart > Integer.MAX_VALUE) {
//            String excMsg = "Real part of product exceeds int data type:" 
//                    + multRegPart + " + " + multSurdPart + "sqrt(" 
//                    + this.quadRing.radicand + ")";
//            throw new ArithmeticException(excMsg);
//        }
//        if (multSurdPart < Integer.MIN_VALUE 
//                || multSurdPart > Integer.MAX_VALUE) {
//            String excMsg = "Imaginary part of product exceeds int data type:" 
//                    + multRegPart + " + " + multSurdPart + "sqrt(" 
//                    + this.quadRing.radicand + ")";
//            throw new ArithmeticException(excMsg);
//        }
        return apply((int) multRegPart, (int) multSurdPart, this.quadRing, 
                this.denominator);
    }
    
    private QuadraticInteger dividesFromOtherRing(QuadraticInteger divisor) 
            throws NotDivisibleException {
        if (divisor.surdPartMult == 0) {
            return this.divides(divisor.regPartMult);
        }
        if (this.surdPartMult == 0 && (!this.quadRing.equals(divisor.quadRing))) {
            QuadraticInteger wrappedDividend = apply(this.regPartMult, 0, 
                    divisor.quadRing);
            return wrappedDividend.divides(divisor);
        }
        if (this.checkCrossDomain(divisor)) {
            if ((this.surdPartMult % divisor.surdPartMult != 0) 
                    || (this.quadRing.radicand 
                    % divisor.quadRing.radicand != 0)) {
                if (this.surdPartMult % divisor.quadRing.radicand == 0) {
                    int divSurd = this.surdPartMult / divisor.quadRing.radicand;
                    int divRad = this.quadRing.radicand 
                            * divisor.quadRing.radicand;
                    QuadraticRing ring;
                    if (divRad < 0) {
                        ring = new ImaginaryQuadraticRing(divRad);
                    } else {
                        ring = new RealQuadraticRing(divRad);
                    }
                    return apply(0, divSurd, ring);
                }
                if (this.quadRing.radicand % divisor.surdPartMult == 0) {
                    // TODO: Figure out what's supposed to go here
                }
                String excMsg = this.toASCIIString() + " is not divisible by " 
                        + divisor.toASCIIString() + ".";
                Fraction regPartFract = new Fraction(0);
                Fraction surdPartFract = new Fraction(this.surdPartMult, 
                        divisor.surdPartMult);
                Fraction[] fracts = {regPartFract, surdPartFract};
                throw new NotDivisibleException(excMsg, this, divisor, fracts);
            }
            int divSurd = this.surdPartMult / divisor.surdPartMult;
            int divRad = this.quadRing.radicand / divisor.quadRing.radicand;
            QuadraticRing divRing;
            if (divRad < 0) {
                divRing = new ImaginaryQuadraticRing(divRad);
            } else {
                divRing = new RealQuadraticRing(divRad);
            }
            return apply(0, divSurd, divRing);
        }
        if (((this.surdPartMult != 0) 
                && (divisor.surdPartMult != 0)) 
                && (this.quadRing.radicand != divisor.quadRing.radicand)) {
            if ((this.regPartMult == 0) && (divisor.regPartMult == 0)) {
                String excMsg = "Oops, sorry???";
                throw new UnsupportedNumberDomainException(excMsg, this, divisor);
            } else {
                String excMsg = "This operation's result is of degree 4";
                throw new AlgebraicDegreeOverflowException(excMsg, 2, this, 
                        divisor);
            }
        }
        throw new RuntimeException("Oops");
    }

    /**
     * Division operation, since operator/ can't be overloaded. Computations are 
     * done with 64-bit variables. Overflow checking is rudimentary.
     * @param divisor The quadratic integer by which to divide this quadratic 
     * integer.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come 
     * from different quadratic rings, the result of the division will be an 
     * algebraic integer of degree 4 and this runtime exception will be thrown.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * "regular" part or the "surd" part of the division exceeds the range of 
     * the int data type.
     * @throws IllegalArgumentException Division by 0 is not allowed, and will 
     * trigger this runtime exception.
     * @throws NotDivisibleException If the quadratic integer is not divisible 
     * by the divisor, this checked exception will be thrown.
     */
    @Override
    public QuadraticInteger divides(QuadraticInteger divisor) 
            throws NotDivisibleException {
        if (this.quadRing.equals(divisor.quadRing)) {
            long divDenom = (long) (divisor.norm() * (long) this.denominator 
                    * (long) divisor.denominator);
            Fraction divRegFract = new Fraction((long) this.regPartMult 
                    * (long) divisor.regPartMult - (long) this.surdPartMult 
                            * (long) divisor.surdPartMult 
                            * (long) this.quadRing.radicand, divDenom);
            Fraction divSurdFract = new Fraction((long) this.surdPartMult 
                    * (long) divisor.regPartMult - (long) this.regPartMult 
                            * (long) divisor.surdPartMult, divDenom);
            boolean divisibleFlag = divRegFract.getDenominator() 
                    == divSurdFract.getDenominator();
            int minDisallowedDenom = 2;
            if (this.quadRing.d1mod4) {
                minDisallowedDenom = 3;
            }
            divisibleFlag = divisibleFlag 
                    && (divRegFract.getDenominator() < minDisallowedDenom);
            if (!divisibleFlag) {
                Fraction[] fracts = {divRegFract, divSurdFract};
                throw new NotDivisibleException(this, divisor, fracts);
            }
//            checkRange(divRegFract, divSurdFract, this.quadRing);
            return apply((int) divRegFract.getNumerator(), 
                    (int) divSurdFract.getNumerator(), this.quadRing, 
                    (int) divRegFract.getDenominator());
        } else {
            return this.dividesFromOtherRing(divisor);
        }
    }
    
    /**
     * Division operation, since operator/ can't be overloaded. Although the 
     * previous divides function can be passed a QuadraticInteger with surd part 
     * equal to 0, this function is to be preferred if you know for sure the 
     * divisor is purely real and rational. Computations are done with 64-bit 
     * variables. Overflow checking is rudimentary.
     * @param divisor The purely real, rational integer by which to divide this 
     * quadratic integer.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws NotDivisibleException If the quadratic integer is not divisible 
     * by the divisor, this exception will be thrown.
     * @throws IllegalArgumentException Division by 0 is not allowed, and will 
     * trigger this runtime exception.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * regular part or the surd part of the division exceeds the range of the 
     * int data type.
     */
    @Override
    public QuadraticInteger divides(int divisor) throws NotDivisibleException {
        if (divisor == 0) {
            throw new IllegalArgumentException("Division by 0 is not valid");
        }
        Fraction divRegFract = (new Fraction(this.regPartMult, 
                this.denominator)).dividedBy(divisor);
        Fraction divSurdFract = (new Fraction(this.surdPartMult, 
                this.denominator)).dividedBy(divisor);
        long divDenom = divRegFract.getDenominator();
        boolean divisibleFlag = (divDenom == divSurdFract.getDenominator());
        if (divisibleFlag) {
            if (this.quadRing.d1mod4) {
                divisibleFlag = (divDenom == 1 || divDenom == 2);
            } else {
                divisibleFlag = (divDenom == 1);
            }
        }
        if (!divisibleFlag) {
            QuadraticInteger wrappedDivisor = apply(divisor, 0, this.quadRing);
            Fraction[] fracts = {divRegFract, divSurdFract};
            throw new NotDivisibleException(this, wrappedDivisor, fracts);
        }
//        checkRange(divRegFract, divSurdFract, this.quadRing);
        return apply((int) divRegFract.getNumerator(), 
                (int) divSurdFract.getNumerator(), this.quadRing, 
                (int) divDenom);
    }
    
    @Override
    public QuadraticInteger mod(QuadraticInteger divisor) {
        if (!this.quadRing.equals(divisor.quadRing)) {
            String excMsg = "STUB TO FAIL THE FIRST CROSS DOMAIN TEST";
            throw new ArrayIndexOutOfBoundsException(excMsg);
        }
        long divDenom = (long) (divisor.norm() * (long) this.denominator 
                * (long) divisor.denominator);
        Fraction divRegFract = new Fraction((long) this.regPartMult 
                * (long) divisor.regPartMult - (long) this.surdPartMult 
                * (long) divisor.surdPartMult * (long) this.quadRing.radicand, 
                divDenom);
        Fraction divSurdFract = new Fraction((long) this.surdPartMult 
                * (long) divisor.regPartMult - (long) this.regPartMult 
                        * (long) divisor.surdPartMult, divDenom);
        boolean divisibleFlag = divRegFract.getDenominator() 
                == divSurdFract.getDenominator();
        int minDisallowedDenom = 2;
        if (this.quadRing.d1mod4) {
            minDisallowedDenom = 3;
        }
        divisibleFlag = divisibleFlag && (divRegFract.getDenominator() 
                < minDisallowedDenom);
        if (divisibleFlag) {
            return apply(0, 0, this.quadRing);
        } else {
            if (minDisallowedDenom == 3) {
                divRegFract = divRegFract.roundDown(ONE_HALF);
                divSurdFract = divSurdFract.conformDown(divRegFract
                        .getDenominator());
            } else {
                divRegFract = divRegFract.roundDown();
                divSurdFract = divSurdFract.roundDown();
            }
            QuadraticInteger division = apply((int) divRegFract.getNumerator(), 
                    (int) divSurdFract.getNumerator(), this.quadRing, 
                    (int) divRegFract.getDenominator());
            QuadraticInteger product = division.times(divisor);
            return this.minus(product);
        }
    }
    
    // STUB TO FAIL THE FIRST TEST
    @Override
    public QuadraticInteger mod(int divisor) {
        if (divisor == 0) return this;
        return new ImaginaryQuadraticInteger(-1, -1, 
                new ImaginaryQuadraticRing(-1));
    }
    
    /**
     * Creates a new {@link ImaginaryQuadraticInteger} or {@link 
     * RealQuadraticInteger} object as indicated by the parameters.
     * @param a The "regular" part of the quadratic integer, the real part in 
     * the case of an imaginary quadratic integer. For example, 8.
     * @param b The "surd" part of the quadratic integer, the imaginary part in 
     * the case of an imaginary quadratic integer. For example, 9.
     * @param ring The quadratic ring in which to place the quadratic integer. 
     * This should be either of type {@link ImaginaryQuadraticRing} or of type 
     * {@link RealQuadraticRing}. Two examples: <code>new 
     * ImaginaryQuadraticRing(-7)</code> 
     * (<i>O</i><sub><b>Q</b>(&radic;(&minus;7))</sub>), 
     * <code>new RealQuadraticRing(7)</code> (<b>Z</b>[&radic;7]).
     * @return A <code>QuadraticInteger</code> object of the appropriate 
     * subclass. Two examples: 8 + 9&radic;&minus;7, 8 + 9&radic;7.
     * @throws NullPointerException If <code>ring</code> is null.
     * @throws UnsupportedNumberDomainException If <code>ring</code> is not of 
     * class <code>ImaginaryQuadraticRing</code> nor 
     * <code>RealQuadraticRing</code>.
     */
    public static QuadraticInteger apply(int a, int b, QuadraticRing ring) {
        return apply(a, b, ring, 1);
    }
    
    /**
     * Creates a new {@link ImaginaryQuadraticInteger} or {@link 
     * RealQuadraticInteger} object as indicated by the parameters. The 
     * "denominator" must be expressly given.
     * @param a The "regular" part of the quadratic integer, the real part in 
     * the case of an imaginary quadratic integer. For example, 8.
     * @param b The "surd" part of the quadratic integer, the imaginary part in 
     * the case of an imaginary quadratic integer. For example, 9.
     * @param ring The quadratic ring in which to place the quadratic integer. 
     * This should be either of type {@link ImaginaryQuadraticRing} or of type 
     * {@link RealQuadraticRing}. Two examples: <code>new 
     * ImaginaryQuadraticRing(-7)</code> 
     * (for <i>O</i><sub><b>Q</b>(&radic;(&minus;7))</sub>), 
     * <code>new RealQuadraticRing(7)</code> (for <b>Z</b>[&radic;7]).
     * @param denom The denominator. Preferably 1 or 2, but may use &minus;1 or 
     * &minus;2 if desired. Be sure to check the latter gives the result you 
     * expect.
     * @return A <code>QuadraticInteger</code> object of the appropriate 
     * subclass. Two examples: 8 + 9&radic;&minus;7, 8 + 9&radic;7.
     * @throws NullPointerException If <code>ring</code> is null.
     * @throws UnsupportedNumberDomainException If <code>ring</code> is not of 
     * class <code>ImaginaryQuadraticRing</code> nor 
     * <code>RealQuadraticRing</code>.
     */
    public static QuadraticInteger apply(int a, int b, QuadraticRing ring, 
            int denom) {
        if (ring == null) {
            String excMsg = "Ring parameter must not be null";
            throw new NullPointerException(excMsg);
        }
        if (ring instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger(a, b, ring, denom);
        }
        if (ring instanceof RealQuadraticRing) {
            return new RealQuadraticInteger(a, b, ring, denom);
        }
        String excMsg = ring.toASCIIString() + " of type " 
                + ring.getClass().getName() 
                + " is not supported for the apply operation at this time";
        throw new UnsupportedNumberDomainException(excMsg, ring);
    }
    
    /**
     * Creates a new {@link ImaginaryquadraticInteger} or {@link 
     * RealQuadraticInteger} object as indicated by the parameters using "theta 
     * notation." For example, given &theta; = <sup>1</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;21</sup>&frasl;<sub>2</sub>, this function could be used to 
     * convert the representation 12 + 5&theta; to 
     * <sup>29</sup>&frasl;<sub>2</sub> + 
     * <sup>5&radic;21</sup>&frasl;<sub>2</sub>.
     * @param m The "non-theta" part of the theta notation representation. For 
     * example, 12.
     * @param n The "theta" part of the theta notation representation. For 
     * example, 5.
     * @param ring The quadratic ring in which to place the quadratic integer. 
     * This should be either of type {@link ImaginaryQuadraticRing} or of type 
     * {@link RealQuadraticRing}. Two examples: <code>new 
     * ImaginaryQuadraticRing(-7)</code> 
     * (<i>O</i><sub><b>Q</b>(&radic;(&minus;7))</sub>), 
     * <code>new RealQuadraticRing(21)</code> 
     * (<i>O</i><sub><b>Q</b>(&radic;(21))</sub>).
     * @return A <code>QuadraticInteger</code> object of the appropriate 
     * subclass. Two examples: 8 + 9&radic;&minus;7, 8 + 9&radic;7.
     * @throws IllegalArgumentException If <code>ring</code> does not have 
     * "half-integers," that is, {@link QuadraticRing#hasHalfIntegers() 
     * ring.hasHalfIntegers()} returns false.
     * @throws NullPointerException If <code>ring</code> is null.
     * @throws UnsupportedNumberDomainException If <code>ring</code> is not of 
     * class <code>ImaginaryQuadraticRing</code> nor 
     * <code>RealQuadraticRing</code>.
     */
    public static QuadraticInteger applyTheta(int m, int n, QuadraticRing ring) {
        if (ring == null) {
            String excMsg = "Ring parameter must not be null";
            throw new NullPointerException(excMsg);
        }
        if (!ring.d1mod4) {
            String excMsg = "The ring " + ring.toASCIIString() 
                    + " does not have \"half-integers\"";
            throw new IllegalArgumentException(excMsg);
        }
        return apply(2 * m + n, n, ring, 2);
    }
    
    /**
     * Superclass constructor for {@link ImaginaryQuadraticInteger} and {@link 
     * RealQuadraticInteger}.
     * @param a The "regular" part of the quadratic integer.
     * @param b The "surd" part of the quadratic integer.
     * @param ring The ring of the quadratic integer. It must not be null.
     * @param denom The "denominator" of the quadratic integer. For example, for 
     * <sup>29</sup>&frasl;<sub>2</sub> + 
     * <sup>5&radic;21</sup>&frasl;<sub>2</sub> this would be 2; for 7 + 
     * &radic;&minus;2 this would be 1.
     * @throws NullPointerException If <code>ring</code> is null.
     */
    public QuadraticInteger(int a, int b, QuadraticRing ring, int denom) {
        if (ring == null) {
            String excMsg = "Ring parameter must not be null";
            throw new NullPointerException(excMsg);
        }
        boolean abParityMatch;
        if (denom == -1 || denom == -2) {
            a *= -1;
            b *= -1;
            denom *= -1;
        }
        if (denom < 1 || denom > 2) {
            String excMsg = "Parameter denom must be 1 or 2";
            throw new IllegalArgumentException(excMsg);
        }
        if (denom == 2) {
            abParityMatch = Math.abs(a % 2) == Math.abs(b % 2);
            if (!abParityMatch) {
                String excMsg = "Parity of a must match parity of b";
                throw new IllegalArgumentException(excMsg);
            }
            if (a % 2 == 0) {
                this.regPartMult = a / 2;
                this.surdPartMult = b / 2;
                this.denominator = 1;
            } else {
                if (ring.d1mod4) {
                    this.regPartMult = a;
                    this.surdPartMult = b;
                    this.denominator = 2;
                } else {
                    String excMsg = "a and b should both be even, or denom 1";
                    throw new IllegalArgumentException(excMsg);
                }
            }
        } else {
            this.regPartMult = a;
            this.surdPartMult = b;
            this.denominator = 1;
        }
        this.quadRing = ring;
    }
    
}
