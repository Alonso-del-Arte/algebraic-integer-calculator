/*
 * Copyright (C) 2019 Alonso del Arte
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
import algebraics.NotDivisibleException;
import algebraics.UnsupportedNumberDomainException;
import calculators.NumberTheoreticFunctionsCalculator;
import fractions.Fraction;

import java.util.Objects;

/**
 * Provides a template for defining objects to represent real or imaginary 
 * quadratic integers. Also defines the four basic arithmetic operations 
 * (addition, subtraction, multiplication and division), so the subclasses don't 
 * have to define them, unless absolutely necessary.
 * @author Aonso del Arte
 */
public abstract class QuadraticInteger implements AlgebraicInteger {

    protected int regPartMult;
    protected int surdPartMult;
    protected QuadraticRing quadRing;
    protected int denominator;
    
    /**
     * Retrieves an object representing the ring this quadratic integer belongs 
     * to.
     * @return An object subclassed from {@link QuadraticRing}.
     */
    @Override
    public QuadraticRing getRing() {
        return this.quadRing;
    }
    
    /**
     * Retrieves the part of the quadratic integer that is not multiplied by the 
     * square root of the radicand, possibly multiplied by 2.
     * @return In the case of an imaginary quadratic integer, the real part, 
     * possibly multiplied by 2. In the case of a real quadratic integer, the 
     * part of the real quadratic integer that is not multiplied by the square 
     * root of the radicand, possibly multiplied by 2. For example, for 7/2 + 
     * (&radic;&minus;3)/2, this would be 7. For 7 + &radic;3, this would also 
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
     * root of the radicand, possibly multiplied by 2. For example, for 5/2 + 
     * 3(&radic;&minus;3)/2, this would be 3. For 5 + 3&radic;3, this would also 
     * be 3.
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
     * this getter should always return 1. For example, for 3/2 + 
     * (&radic;&minus;7)/2, this would be 2, for 3 + &radic;&minus;7 this would 
     * be 1. In the ring of the Gaussian integers, this getter should always 
     * return 1. Even if &minus;1 or &minus;2 was successfully used as a 
     * denominator in the constructor, this getter will still return 1 or 2.
     */
    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Gives the algebraic degree of the algebraic integer. Should not be higher than 2.
     * @return 0 if the algebraic integer is 0, 1 if it's a purely real integer, 2 otherwise.
     */
    @Override
    public int algebraicDegree() {
        if (surdPartMult == 0) {
            if (regPartMult == 0) {
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
     * @return Twice the "regular" part. For example, for 1/2 + 
     * (&radic;&minus;7)/2, this would be 1; for 1 + &radic;&minus;7 it would be 
     * 2; and for 2 + &radic;14, it would be 4.
     */
    @Override
    public long trace() {
        if (this.quadRing.d1mod4 && this.denominator == 2) {
            return this.regPartMult;
        } else {
            return 2 * this.regPartMult;
        }
    }
    
    /**
     * Calculates the norm of the quadratic integer. 64-bit integers are used 
     * for the computation, but there is no overflow checking.
     * @return Square of the "regular" part minus square of the surd part times 
     * radicand. Should be 0 if an only if the quadratic integer is 0. Negative 
     * norms for imaginary quadratic integers are mathematically impossible, but 
     * could occur in this program as a result of an arithmetic overflow. Norms 
     * for nonzero real quadratic integers may be positive or negative. For 
     * example, the norm of 1/2 + (&radic;&minus;7)/2 is 2; the norm of 1 + 
     * &radic;&minus;7 is 8; and the norm of 2 + &radic;14 is &minus;10.
     */
    @Override
    public long norm() {
        long Na = (long) this.regPartMult * (long) this.regPartMult;
        long Nb = (long) this.surdPartMult * (long) this.surdPartMult * (long) this.quadRing.radicand;
        long N = Na - Nb;
        if (this.quadRing.d1mod4 && (this.denominator == 2)) {
            N /= 4L;
        }
        return N;
    }
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic 
     * integer.
     * @return An array of three integers. If the algebraic integer is of degree 
     * 2, the array will be {norm, negative trace, 1}; if of degree 1, then 
     * {number, 1, 0}, and for 0, {0, 1, 0}. For example, for 5/2 + sqrt(-7)/2, 
     * the result would be {8, -5, 1}. A return of {0, 0, 0} would indicate a 
     * major malfunction in {@link #algebraicDegree()}.
     */
    @Override
    public long[] minPolynomial() {
        long[] coeffs = {0, 0, 0};
        switch (this.algebraicDegree()) {
            case 0:
                coeffs[1] = 1;
                break;
            case 1: 
                coeffs[0] = -1 * this.regPartMult;
                coeffs[1] = 1;
                break;
            case 2: 
                coeffs[0] = this.norm();
                coeffs[1] = -this.trace();
                coeffs[2] = 1;
                break;
        }
        return coeffs;
    }
    
    /**
     * Gives the minimal polynomial in a format suitable for plain text or TeX.
     * @return A String. If the algebraic degree is 2, the String should start 
     * off with "x^2". For example, for 5/2 + sqrt(-7)/2, the result would be 
     * "x^2 - 5x + 8". The return of an empty String would indicate a major 
     * malfunction in {@link #algebraicDegree()}.
     */
    @Override
    public String minPolynomialString() {
        String polString = "";
        long[] polCoeffs = this.minPolynomial();
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
     * Gives the conjugate of the quadratic integer, the number which multiplied 
     * by this number gives the norm.
     * @return This quadratic integer with the "surd" part negated. For example, 
     * the conjugate of 5/2 + (&radic;&minus;7)/2 is 5/2 &minus; 
     * (&radic;&minus;7)/2.
     */
    public QuadraticInteger conjugate() {
        if (this.surdPartMult == 0) {
            return this;
        }
        QuadraticInteger conjug = null;
        if (this instanceof ImaginaryQuadraticInteger) {
            conjug = new ImaginaryQuadraticInteger(this.regPartMult, -this.surdPartMult, this.quadRing, this.denominator);
        }
        if (this instanceof RealQuadraticInteger) {
            conjug = new RealQuadraticInteger(this.regPartMult, -this.surdPartMult, this.quadRing, this.denominator);
        }
        if (!(this instanceof ImaginaryQuadraticInteger) && !(this instanceof RealQuadraticInteger)) {
            String exceptionMessage = this.getClass().getName() + " is not a supported number domain for the conjugate function.";
            exceptionMessage = exceptionMessage + "\nIt may be necessary to override QuadraticInteger.conjugate().";
            throw new UnsupportedNumberDomainException(exceptionMessage, this, this);
        }
        return conjug;
    }
        
    /**
     * A text representation of the quadratic integer, with the "regular" part  
     * first and the "surd" part second.
     * @return A String representing the imaginary quadratic integer which can 
     * be used in a JTextField. Because of the "&radic;" character, this might 
     * not be suitable for console output.
     */
    @Override
    public String toString() {
        String IQIString = "";
        if (this.denominator == 2) {
            IQIString = this.regPartMult + "/2 ";
            if (this.surdPartMult < -1) {
                IQIString += (("- " + ((-1) * this.surdPartMult)) + "\u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult == -1) {
                IQIString += ("- \u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult == 1) {
                IQIString += ("+ \u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult > 1) {
                IQIString += ("+ " + this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")/2");
            } 
        } else {
            if (this.regPartMult == 0) {
                if (this.surdPartMult == 0) {
                    IQIString = "0";
                } else {
                    if (this.surdPartMult < -1 || this.surdPartMult > 1) {
                        IQIString = this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")";
                    }
                    if (this.surdPartMult == -1) {
                        IQIString = "-\u221A(" + this.quadRing.radicand + ")";
                    }
                    if (this.surdPartMult == 1) {
                        IQIString = "\u221A(" + this.quadRing.radicand + ")";
                    }
                }
            } else {
                IQIString = Integer.toString(this.regPartMult);
                if (this.surdPartMult < -1) {
                    IQIString += ((" - " + ((-1) * this.surdPartMult)) + "\u221A(" + this.quadRing.radicand + ")");
                }
                if (this.surdPartMult == -1) {
                    IQIString += (" - \u221A(" + this.quadRing.radicand + ")");
                }
                if (this.surdPartMult == 1) {
                    IQIString += (" + \u221A(" + this.quadRing.radicand + ")");
                }
                if (this.surdPartMult > 1) {
                    IQIString += (" + " + this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")");
                }
            }
        }
        if (this.quadRing.radicand == -1) {
            IQIString = IQIString.replace("\u221A(-1)", "i");
        }
        return IQIString;
    }
    
    /**
     * A text representation of the quadratic integer, using theta notation when 
     * {@link #getRing()}{@link ImaginaryQuadraticRing#hasHalfIntegers() 
     * .hasHalfIntegers()} is true.
     * @return A String representing the quadratic integer which can be used in 
     * a JTextField. If {@link #getRing()}{@link 
     * ImaginaryQuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is false, 
     * this just returns the same String as {@link #toString()}. Note that 
     * &omega; here is used strictly only to mean -1/2 + (&radic;-3)/2, but 
     * &theta; can mean 1/2 + (&radic;<i>d</i>)/2 for any <i>d</i> congruent to 
     * 1 modulo 4 other than -3. Thus, for example, 5/2 + (&radic;-3)/2 = 3 + 
     * &omega;, but 5/2 + (&radic;-7)/2 = 2 + &theta;, 5/2 + (&radic;-11)/2 = 2 
     * + &theta;, 5/2 + (&radic;-15)/2 = 2 + &theta;, etc.
     */
    public String toStringAlt() {
        String altQIString;
        if (this.quadRing.d1mod4) {
            int nonThetaPart = this.regPartMult;
            int thetaPart = this.surdPartMult;
            char thetaLetter = '\u03B8';
            if (this.denominator == 1) {
                nonThetaPart *= 2;
                thetaPart *= 2;
            }
            switch (this.quadRing.radicand) {
                case -3:
                    nonThetaPart = (nonThetaPart + thetaPart)/2;
                    thetaLetter = '\u03C9'; // omega instead of theta
                    break;
                case 5:
                    thetaLetter = '\u03C6'; // phi instead of theta
                    // no break, deliberate fall-through to default
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
        return this.toString().replace("\u221A", "sqrt");
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
            String intermediateString = this.toStringAlt();
            if (this.quadRing.radicand == -3 || this.quadRing.radicand == 5) {
                intermediateString = intermediateString.replace("\u03C9", "omega");
                intermediateString = intermediateString.replace("\u03C6", "phi");
            } else {
                intermediateString = intermediateString.replace("\u03B8", "theta");
            }
            return intermediateString;
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
        if (this.quadRing.radicand == -1) {
            return this.toString();
        }
        if (this.norm() == 0) {
            return "0";
        }
        String QIString;
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
                        QIString = this.surdPartMult + " \\sqrt{" + this.quadRing.radicand + "}";
                }
            } else {
                if (this.surdPartMult == 0) {
                    return Integer.toString(this.regPartMult);
                } else {
                    QIString = this.regPartMult + " + " + this.surdPartMult + " \\sqrt{" + this.quadRing.radicand + "}";
                    QIString = QIString.replace("+ -", " - ");
                    QIString = QIString.replace(" 1 \\sqrt", " \\sqrt");
                }
            }
        } else {
            QIString = "\\frac{" + this.regPartMult + "}{2} + \\frac{" + this.surdPartMult + " \\sqrt{" + this.quadRing.radicand + "}}{2}";
            QIString = QIString.replace("\\frac{-", "-\\frac{");
            QIString = QIString.replace("\\frac{1 \\sqrt", "\\frac{\\sqrt");
            QIString = QIString.replace("+ -", " - ");
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
        if (this.denominator == 2) {
            String QIString;
            QIString = "\\frac{" + this.regPartMult + " + " + this.surdPartMult + " \\sqrt{" + this.quadRing.radicand + "}}{2}";
            QIString = QIString.replace(" 1 \\sqrt", " \\sqrt");
            QIString = QIString.replace("+ -", " - ");
            return QIString;
        } else {
            return this.toTeXString();
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
        QIString = QIString.replace("-", "&minus;");
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
            QIString = QIString.replace("-", "&minus;");
            return QIString;
        } else {
            return this.toHTMLString();
        }
    }

    /**
     * Returns a hash code value for the quadratic integer. Overriding {@link 
     * Object#hashCode} on account of needing to override {@link Object#equals}. 
     * The hash code is based on the "regular" part (multiplied by 2 when 
     * applicable), the "surd" part (multiplied by 2 when applicable), the 
     * discriminant and the denominator. However, if the "surd" part is 0, the 
     * purely real integer is treated as a Gaussian integer. This was done in 
     * the hope of satisfying the contract that two objects that evaluate as 
     * equal also hash equal. However, uniqueness of hash codes for distinct 
     * numbers is not mathematically guaranteed.
     * @return An integer which is hopefully unique from the hash codes of 
     * algebraic integers which are different that might occur in the same 
     * execution of the program.
     */
    @Override
    public int hashCode() {
        if (this.surdPartMult == 0) {
            return Objects.hash(this.regPartMult, this.surdPartMult, -1);
        } else {
            return Objects.hash(this.regPartMult, this.surdPartMult, this.quadRing.radicand, this.denominator);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final QuadraticInteger other = (QuadraticInteger) obj;
        if (this.regPartMult != other.regPartMult) {
            return false;
        }
        if (this.surdPartMult != other.surdPartMult) {
            return false;
        }
        if (this.denominator != other.denominator) {
            return false;
        }
        if (this.surdPartMult == 0) {
            return true; // The radicand might be different, but its square root multiplied by 0 is still 0
        }
        return (this.quadRing.radicand == other.quadRing.radicand);
    }
   
    // FEATURE NOT IMPLEMENTED YET
    public static QuadraticInteger parseQuadraticInteger(QuadraticRing ring, String str) {
        return null;
    }
    
    // FEATURE NOT IMPLEMENTED YET
    public static QuadraticInteger parseQuadraticInteger(String str) {
        return null;
    }
    
    /**
     * Addition operation, since operator+ (plus) can't be overloaded. 
     * Computations are done with 64-bit variables. Overflow checking is 
     * rudimentary.
     * @param summand The quadratic integer to be added to this quadratic 
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
    public QuadraticInteger plus(QuadraticInteger summand) {
        if (summand.surdPartMult == 0) {
            return this.plus(summand.regPartMult);
        }
        if (this.surdPartMult == 0) {
            return summand.plus(this.regPartMult);
        }
        if (this.quadRing.radicand != summand.quadRing.radicand) {
            throw new AlgebraicDegreeOverflowException("This operation would result in an algebraic integer of degree 4.", 2, this, summand);
        }
        long sumRealPart = 0;
        long sumImagPart = 0;
        int sumDenom = 1;
        if (this.quadRing.d1mod4) {
            if (this.denominator == 1 && summand.denominator == 2) {
                sumRealPart = 2 * this.regPartMult + summand.regPartMult;
                sumImagPart = 2 * this.surdPartMult + summand.surdPartMult;
                sumDenom = 2;
            }
            if (this.denominator == 2 && summand.denominator == 1) {
                sumRealPart = this.regPartMult + 2 * summand.regPartMult;
                sumImagPart = this.surdPartMult + 2 * summand.surdPartMult;
                sumDenom = 2;
            }
            if (this.denominator == summand.denominator) {
                sumRealPart = this.regPartMult + summand.regPartMult;
                sumImagPart = this.surdPartMult + summand.surdPartMult;
                sumDenom = this.denominator;
            }
        } else {
            sumRealPart = this.regPartMult + summand.regPartMult;
            sumImagPart = this.surdPartMult + summand.surdPartMult;
            sumDenom = 1;
        }
        if (sumRealPart < Integer.MIN_VALUE || sumRealPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Real part of sum exceeds int data type:" + sumRealPart + " + " + sumImagPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (sumImagPart < Integer.MIN_VALUE || sumImagPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Imaginary part of sum exceeds int data type:" + sumRealPart + " + " + sumImagPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (this.quadRing instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger((int) sumRealPart, (int) sumImagPart, this.quadRing, sumDenom);
        }
        if (this.quadRing instanceof RealQuadraticRing) {
            return new RealQuadraticInteger((int) sumRealPart, (int) sumImagPart, this.quadRing, sumDenom);
        }
        String exceptionMessage = "plus function does not support the " + this.quadRing.getClass().getName() + " implementation of QuadraticRing.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this, summand);
    }
    
    /**
     * Addition operation, since operator+ (plus) can't be overloaded. This does 
     * computations with 64-bit variables. Overflow checking is rudimentary. 
     * Although the previous plus function can be passed a QuadraticInteger with 
     * "surd" part equal to 0, this function is to be preferred if you know for 
     * sure the summand is purely real and rational.
     * @param summand The purely real and rational integer to be added to the 
     * "regular" part of the QuadraticInteger.
     * @return A new QuadraticInteger object with the result of the operation.
     * @throws ArithmeticException A runtime exception thrown if the "regular" 
     * part of the sum exceeds the range of the int data type. The "surd" part 
     * of the sum should be fine, since the summand has a tacit "surd" part of 
     * 0.
     */
    public QuadraticInteger plus(int summand) {
        long sumRealPart = this.regPartMult;
        if (this.denominator == 2) {
            sumRealPart += (2 * summand);
        } else {
            sumRealPart += summand;
        }
        if (sumRealPart < Integer.MIN_VALUE || sumRealPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Real part of sum exceeds int data type:" + sumRealPart + " + " + this.surdPartMult + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (this.quadRing instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger((int) sumRealPart, this.surdPartMult, this.quadRing, this.denominator);
        }
        if (this.quadRing instanceof RealQuadraticRing) {
            return new RealQuadraticInteger((int) sumRealPart, this.surdPartMult, this.quadRing, this.denominator);
        }
        String exceptionMessage = "plus or minus function does not support the " + this.quadRing.getClass().getName() + " implementation of QuadraticRing.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this);
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
    public QuadraticInteger minus(QuadraticInteger subtrahend) {
        QuadraticInteger negAddend = subtrahend.times(-1);
        return this.plus(negAddend);
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
     * @throws ArithmeticException A runtime exception thrown if the regular part 
     * of the subtraction exceeds the range of the int data type. The surd part 
     * of the sum should be fine, since the subtrahend has a tacit surd part of 
     * 0.
     */
    public QuadraticInteger minus(int subtrahend) {
        return this.plus(-subtrahend);
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
    public QuadraticInteger times(QuadraticInteger multiplicand) {
        if (multiplicand.surdPartMult == 0) {
            return this.times(multiplicand.regPartMult);
        }
        if (this.surdPartMult == 0) {
            return multiplicand.times(this.regPartMult);
        }
        boolean crossDomainFlag = (this.regPartMult == 0 && multiplicand.regPartMult == 0);
        crossDomainFlag = crossDomainFlag && (this.surdPartMult != 0 && multiplicand.surdPartMult != 0);
        crossDomainFlag = crossDomainFlag && (this instanceof ImaginaryQuadraticInteger || this instanceof RealQuadraticInteger);
        crossDomainFlag = crossDomainFlag && (multiplicand instanceof ImaginaryQuadraticInteger || multiplicand instanceof RealQuadraticInteger);
        crossDomainFlag = crossDomainFlag && (this.quadRing.radicand != multiplicand.quadRing.radicand);
        if (crossDomainFlag) {
            int prodSurd = this.surdPartMult * multiplicand.surdPartMult;
            if (this instanceof ImaginaryQuadraticInteger && multiplicand instanceof ImaginaryQuadraticInteger) {
                prodSurd *= -1;
            }
            int prodRad = this.quadRing.radicand * multiplicand.quadRing.radicand;
            if (!NumberTheoreticFunctionsCalculator.isSquareFree(prodRad)) {
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
            throw new AlgebraicDegreeOverflowException("This operation would result in an algebraic integer of degree 4.", 2, this, multiplicand);
        }
        if (this.surdPartMult == 0) {
            return multiplicand.times(this.regPartMult);
        }
        if (multiplicand.surdPartMult == 0) {
            return this.times(multiplicand.regPartMult);
        }
        if (this.quadRing.radicand != multiplicand.quadRing.radicand) {
            if (this.regPartMult == 0 && multiplicand.regPartMult == 0) {
                String exceptionMessage = "This operation would result in " + ((-1) * this.surdPartMult * multiplicand.surdPartMult) + "sqrt(" + (this.quadRing.radicand * multiplicand.quadRing.radicand) + "), a real quadratic integer which this package can't properly represent.";
                throw new UnsupportedNumberDomainException(exceptionMessage, this, multiplicand);
            } else {
                throw new AlgebraicDegreeOverflowException("This operation would result in an algebraic integer of degree 4.", 2, this, multiplicand);
            }
        }
        long intermediateRegPart = this.regPartMult * multiplicand.regPartMult + this.surdPartMult * multiplicand.surdPartMult * this.quadRing.radicand;
        long intermediateSurdPart = this.regPartMult * multiplicand.surdPartMult + this.surdPartMult * multiplicand.regPartMult;
        int intermediateDenom = this.denominator * multiplicand.denominator;
        if (intermediateDenom == 4) {
            intermediateRegPart /= 2;
            intermediateSurdPart /= 2;
            intermediateDenom = 2;
        }
        if (intermediateRegPart < Integer.MIN_VALUE || intermediateRegPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Real part of product exceeds int data type:" + intermediateRegPart + " + " + intermediateSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (intermediateSurdPart < Integer.MIN_VALUE || intermediateSurdPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Imaginary part of product exceeds int data type:" + intermediateRegPart + " + " + intermediateSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (this.quadRing instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, this.quadRing, intermediateDenom);
        }
        if (this.quadRing instanceof RealQuadraticRing) {
            return new RealQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, this.quadRing, intermediateDenom);
        }
        String exceptionMessage = "times function does not support the " + this.quadRing.getClass().getName() + " implementation of QuadraticRing.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this, multiplicand);
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
    public QuadraticInteger times(int multiplicand) {
        long multiplicationRegPart = this.regPartMult * multiplicand;
        long multiplicationSurdPart = this.surdPartMult * multiplicand;
        // No need to worry about denominator, constructor will take care of it if necessary.
        if (multiplicationRegPart < Integer.MIN_VALUE || multiplicationRegPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Real part of product exceeds int data type:" + multiplicationRegPart + " + " + multiplicationSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (multiplicationSurdPart < Integer.MIN_VALUE || multiplicationSurdPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Imaginary part of product exceeds int data type:" + multiplicationRegPart + " + " + multiplicationSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (this.quadRing instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger((int) multiplicationRegPart, (int) multiplicationSurdPart, this.quadRing, this.denominator);
        }
        if (this.quadRing instanceof RealQuadraticRing) {
            return new RealQuadraticInteger((int) multiplicationRegPart, (int) multiplicationSurdPart, this.quadRing, this.denominator);
        }
        String exceptionMessage = "times function does not support the " + this.quadRing.getClass().getName() + " implementation of QuadraticRing.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this);
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
     * @throws NotDivisibleException If the quadratic integer is not divisible 
     * by the divisor, this checked exception will be thrown.
     * @throws IllegalArgumentException Division by 0 is not allowed, and will 
     * trigger this runtime exception.
     * @throws ArithmeticException A runtime exception thrown if either the 
     * "regular" part or the "surd" part of the division exceeds the range of 
     * the int data type.
     */
    public QuadraticInteger divides(QuadraticInteger divisor) throws NotDivisibleException {
        if (divisor.surdPartMult == 0) {
            return this.divides(divisor.regPartMult);
        }
        boolean crossDomainFlag = (this.regPartMult == 0 && divisor.regPartMult == 0);
        crossDomainFlag = crossDomainFlag && (this.surdPartMult != 0 && divisor.surdPartMult != 0);
        crossDomainFlag = crossDomainFlag && (this instanceof ImaginaryQuadraticInteger || this instanceof RealQuadraticInteger);
        crossDomainFlag = crossDomainFlag && (divisor instanceof ImaginaryQuadraticInteger || divisor instanceof RealQuadraticInteger);
        crossDomainFlag = crossDomainFlag && (this.quadRing.radicand != divisor.quadRing.radicand);
        if (crossDomainFlag) {
            if ((this.surdPartMult % divisor.surdPartMult != 0) || (this.quadRing.radicand % divisor.quadRing.radicand != 0)) {
                if (this.surdPartMult % divisor.quadRing.radicand == 0) {
                    int divSurd = this.surdPartMult / divisor.quadRing.radicand;
                    int divRad = this.quadRing.radicand * divisor.quadRing.radicand;
                    QuadraticRing ring;
                    QuadraticInteger div;
                    if (divRad < 0) {
                        ring = new ImaginaryQuadraticRing(divRad);
                        div = new ImaginaryQuadraticInteger(0, divSurd, ring);
                    } else {
                        ring = new RealQuadraticRing(divRad);
                        div = new RealQuadraticInteger(0, divSurd, ring);
                    }
                    return div;
                }
                if (this.quadRing.radicand % divisor.surdPartMult == 0) {
                    //
                }
                String exceptionMessage = this.toASCIIString() + " is not divisible by " + divisor.toASCIIString() + ".";
                Fraction regPartFract = new Fraction(0);
                Fraction surdPartFract = new Fraction(this.surdPartMult, divisor.surdPartMult);
                Fraction[] fracts = {regPartFract, surdPartFract};
                throw new NotDivisibleException(exceptionMessage, this, divisor, fracts, this.quadRing);
            }
            int divSurd = this.surdPartMult / divisor.surdPartMult;
            int divRad = this.quadRing.radicand / divisor.quadRing.radicand;
            QuadraticRing divRing;
            QuadraticInteger division;
            if (divRad < 0) {
                divRing = new ImaginaryQuadraticRing(divRad);
                division = new ImaginaryQuadraticInteger(0, divSurd, divRing);
            } else {
                divRing = new RealQuadraticRing(divRad);
                division = new RealQuadraticInteger(0, divSurd, divRing);
            }
            return division;
        }
        if (((this.surdPartMult != 0) && (divisor.surdPartMult != 0)) && (this.quadRing.radicand != divisor.quadRing.radicand)) {
            if ((this.regPartMult == 0) && (divisor.regPartMult == 0)) {
                throw new UnsupportedNumberDomainException("This operation could result in an algebraic integer in a real quadratic integer ring, which is not currently supported by this package.", this, divisor);
            } else {
                throw new AlgebraicDegreeOverflowException("This operation could result in an algebraic integer of degree 4.", 2, this, divisor);
            }
        }
        long intermediateRegPart = (long) this.regPartMult * (long) divisor.regPartMult - (long) this.surdPartMult * (long) divisor.surdPartMult * (long) this.quadRing.radicand;
        long intermediateSurdPart = (long) this.surdPartMult * (long) divisor.regPartMult - (long) this.regPartMult * (long) divisor.surdPartMult;
        long intermediateDenom = (long) (divisor.norm() * (long) this.denominator * (long) divisor.denominator);
        if (intermediateDenom < 0) {
            intermediateRegPart *= -1;
            intermediateSurdPart *= -1;
            intermediateDenom *= -1;
        }
        long realCutDown = NumberTheoreticFunctionsCalculator.euclideanGCD(intermediateRegPart, intermediateDenom);
        long imagCutDown = NumberTheoreticFunctionsCalculator.euclideanGCD(intermediateSurdPart, intermediateDenom);
        if (realCutDown < imagCutDown) {
            intermediateRegPart /= realCutDown;
            intermediateSurdPart /= realCutDown;
            intermediateDenom /= realCutDown;
        } else {
            intermediateRegPart /= imagCutDown;
            intermediateSurdPart /= imagCutDown;
            intermediateDenom /= imagCutDown;
        }
        boolean divisibleFlag;
        if (this.quadRing.d1mod4) {
            divisibleFlag = (intermediateDenom == 1 || intermediateDenom == 2);
            if (intermediateDenom == 2) {
                divisibleFlag = (Math.abs(intermediateRegPart % 2) == Math.abs(intermediateSurdPart % 2));
            }
        } else {
            divisibleFlag = (intermediateDenom == 1);
        }
        if (!divisibleFlag) {
            String exceptionMessage = this.toASCIIString() + " is not divisible by " + divisor.toASCIIString() + ".";
            Fraction regPartFract = new Fraction(intermediateRegPart, intermediateDenom);
            Fraction surdPartFract = new Fraction(intermediateSurdPart, intermediateDenom);
            Fraction[] fracts = {regPartFract, surdPartFract};
            throw new NotDivisibleException(exceptionMessage, this, divisor, fracts, this.quadRing);
        }
        if (intermediateRegPart < Integer.MIN_VALUE || intermediateRegPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Real part of division exceeds int data type:" + intermediateRegPart + " + " + intermediateSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (intermediateSurdPart < Integer.MIN_VALUE || intermediateSurdPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Imaginary part of division exceeds int data type:" + intermediateRegPart + " + " + intermediateSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (this.quadRing instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, this.quadRing, (int) intermediateDenom);
        }
        if (this.quadRing instanceof RealQuadraticRing) {
            return new RealQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, this.quadRing, (int) intermediateDenom);
        }
        String exceptionMessage = "divides function does not support the " + this.quadRing.getClass().getName() + " implementation of QuadraticRing.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this, divisor);
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
    public QuadraticInteger divides(int divisor) throws NotDivisibleException {
        if (divisor == 0) {
            throw new IllegalArgumentException("Division by 0 is not allowed.");
        }
        long intermediateRegPart = this.regPartMult;
        long intermediateSurdPart = this.surdPartMult;
        long intermediateDenom = this.denominator * divisor;
        long realCutDown = NumberTheoreticFunctionsCalculator.euclideanGCD(intermediateRegPart, intermediateDenom);
        long imagCutDown = NumberTheoreticFunctionsCalculator.euclideanGCD(intermediateSurdPart, intermediateDenom);
        if (realCutDown < imagCutDown) {
            intermediateRegPart /= realCutDown;
            intermediateSurdPart /= realCutDown;
            intermediateDenom /= realCutDown;
        } else {
            intermediateRegPart /= imagCutDown;
            intermediateSurdPart /= imagCutDown;
            intermediateDenom /= imagCutDown;
        }
        if (intermediateDenom < 0) {
            intermediateRegPart *= -1;
            intermediateSurdPart *= -1;
            intermediateDenom *= -1;
        }
        boolean divisibleFlag;
        if (this.quadRing.d1mod4) {
            divisibleFlag = (intermediateDenom == 1 || intermediateDenom == 2);
            if (intermediateDenom == 2) {
                divisibleFlag = (Math.abs(intermediateRegPart % 2) == Math.abs(intermediateSurdPart % 2));
            }
        } else {
            divisibleFlag = (intermediateDenom == 1);
        }
        if (!divisibleFlag) {
            String exceptionMessage = this.toASCIIString() + " is not divisible by " + divisor + ".";
            QuadraticInteger wrappedDivisor = null;
            if (this instanceof ImaginaryQuadraticInteger) {
                wrappedDivisor = new ImaginaryQuadraticInteger(divisor, 0, this.quadRing);
            }
            if (this instanceof RealQuadraticInteger) {
                wrappedDivisor = new RealQuadraticInteger(divisor, 0, this.quadRing);
            }
            Fraction regPartFract = new Fraction(intermediateRegPart, intermediateDenom);
            Fraction surdPartFract = new Fraction(intermediateSurdPart, intermediateDenom);
            Fraction[] fracts = {regPartFract, surdPartFract};
            throw new NotDivisibleException(exceptionMessage, this, wrappedDivisor, fracts, this.quadRing);
        }
        if (intermediateRegPart < Integer.MIN_VALUE || intermediateRegPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Real part of division exceeds int data type:" + intermediateRegPart + " + " + intermediateSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (intermediateSurdPart < Integer.MIN_VALUE || intermediateSurdPart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Imaginary part of division exceeds int data type:" + intermediateRegPart + " + " + intermediateSurdPart + "sqrt(" + this.quadRing.radicand + ")");
        }
        if (this.quadRing instanceof ImaginaryQuadraticRing) {
            return new ImaginaryQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, this.quadRing, (int) intermediateDenom);
        }
        if (this.quadRing instanceof RealQuadraticRing) {
            return new RealQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, this.quadRing, (int) intermediateDenom);
        }
        String exceptionMessage = "divides function does not support the " + this.quadRing.getClass().getName() + " implementation of QuadraticRing.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this);
    }
    
}
