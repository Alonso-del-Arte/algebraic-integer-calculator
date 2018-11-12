/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algebraics;

/**
 *
 * @author Aonso del Arte
 */
public abstract class QuadraticInteger implements AlgebraicInteger {

    protected int regPartMult;
    protected int surdPartMult;
    protected QuadraticRing quadRing;
    protected int denominator;
    
    public QuadraticRing getRing() {
        return quadRing;
    }
    
    /**
     * Gets the denominator of the imaginary quadratic integer when represented 
     * as a fraction in lowest terms.
     * @return 2 only in the case of so-called "half-integers," always 1 
     * otherwise. So, if {@link #getRing()}{@link 
     * ImaginaryQuadraticRing#hasHalfIntegers() .hasHalfIntegers()} is false, 
     * this getter should always return 1. For example, for 3/2 + sqrt(-7)/2, 
     * this would be 2, for 3 + sqrt(-7) this would be 1. In the ring of the 
     * Gaussian integers, this getter should always return 1. Even if -1 or -2 
     * was successfully used as a denominator in the constructor, this getter 
     * will still return 1 or 2.
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
     * Calculates the trace of the imaginary quadratic integer (real part plus real integer times square root of a negative integer)
     * @return Twice the real part
     */
    @Override
    public long trace() {
        if (quadRing.d1mod4 && denominator == 2) {
            return regPartMult;
        } else {
            return 2 * regPartMult;
        }
    }
    
    /**
     * Calculates the norm of the quadratic integer.
     * @return Square of the "regular" part minus square of the surd part times radicand.
     */
    @Override
    public long norm() {
        int N;
        if (quadRing.d1mod4 && denominator == 2) {
            N = (regPartMult * regPartMult - quadRing.radicand * surdPartMult * surdPartMult)/4;
        } else {
            N = regPartMult * regPartMult - quadRing.radicand * surdPartMult * surdPartMult;
        }
        return N;
    }
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic integer
     * @return An array of three integers. If the algebraic integer is of degree 2, the array will be {norm, negative trace, 1}; if of degree 1, then {number, 1, 0}, and for 0, {0, 0, 0}.
     */
    @Override
    public long[] minPolynomial() {
        long[] coeffs = {0, 0, 0};
        switch (algebraicDegree()) {
            case 0: 
                break;
            case 1: 
                coeffs[0] = -1 * regPartMult;
                coeffs[1] = 1;
                break;
            case 2: 
                coeffs[0] = norm();
                coeffs[1] = -trace();
                coeffs[2] = 1;
                break;
        }
        return coeffs;
    }
    
    @Override
    public String minPolynomialString() {
        String polString = "";
        long[] polCoeffs = minPolynomial();
        switch (algebraicDegree()) {
            case 0:
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
    
    public QuadraticInteger conjugate() {
        QuadraticInteger conjug = null;
        if (this instanceof ImaginaryQuadraticInteger) {
            conjug = new ImaginaryQuadraticInteger(this.regPartMult, -this.surdPartMult, this.quadRing, this.denominator);
        }
        if (this instanceof RealQuadraticInteger) {
            conjug = new RealQuadraticInteger(this.regPartMult, -this.surdPartMult, this.quadRing, this.denominator);
        }
        if (!(this instanceof ImaginaryQuadraticInteger) && !(this instanceof RealQuadraticInteger)) {
            String exceptionMessage = this.getClass().getName() + " is not a supported number domain for this function.";
            exceptionMessage = exceptionMessage + "\nIt may be necessary to override QuadraticInteger.conjugate().";
            throw new UnsupportedNumberDomainException(exceptionMessage, this, this);
        }
        return conjug;
    }
    
    public abstract double abs();
    
    public abstract double getRealPartMultNumeric();
    
    public abstract double getImagPartwRadMultNumeric();
    
    /**
     * A text representation of the quadratic integer, with the "regular" part  
     * first and the "surd" part second.
     * @return A String representing the imaginary quadratic integer which can 
     * be used in a JTextField. Because of the "&radic;" character, this might 
     * not be suitable for console output.
     */
    @Override
    public String toString() {
        String QIString = "";
        if (this.denominator == 2) {
            QIString = this.regPartMult + "/2 ";
            if (this.surdPartMult < -1) {
                QIString += (("- " + ((-1) * this.surdPartMult)) + "\u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult == -1) {
                QIString += ("- \u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult == 1) {
                QIString += ("+ \u221A(" + this.quadRing.radicand + ")/2");
            }
            if (this.surdPartMult > 1) {
                QIString += ("+ " + this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")/2");
            } 
        } else {
            if (this.regPartMult == 0) {
                if (this.surdPartMult == 0) {
                    QIString = "0";
                } else {
                    if (this.surdPartMult < -1 || this.surdPartMult > 1) {
                        QIString = this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")";
                    }
                    if (this.surdPartMult == -1) {
                        QIString = "-\u221A(" + this.quadRing.radicand + ")";
                    }
                    if (this.surdPartMult == 1) {
                        QIString = "\u221A(" + this.quadRing.radicand + ")";
                    }
                }
            } else {
                QIString = Integer.toString(this.regPartMult);
                if (this.surdPartMult < -1) {
                    QIString += ((" - " + ((-1) * this.surdPartMult)) + "\u221A(" + this.quadRing.radicand + ")");
                }
                if (this.surdPartMult == -1) {
                    QIString += (" - \u221A(" + this.quadRing.radicand + ")");
                }
                if (this.surdPartMult == 1) {
                    QIString += (" + \u221A(" + this.quadRing.radicand + ")");
                }
                if (this.surdPartMult > 1) {
                    QIString += (" + " + this.surdPartMult + "\u221A(" + this.quadRing.radicand + ")");
                }
            }
        }
        if (this.quadRing.radicand == -1) {
            QIString = QIString.replace("\u221A(-1)", "i");
        }
        return QIString;
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
            if (this.quadRing.radicand == -3) {
                nonThetaPart = (nonThetaPart + thetaPart)/2;
                thetaLetter = '\u03C9'; // Now this holds omega instead of theta
            } else {
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
    
    public String toASCIIStringAlt() {
        return "Feature not implemented yet.";
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
                QIString = this.regPartMult + " + " + this.surdPartMult + " \\sqrt{" + this.quadRing.radicand + "}";
                QIString = QIString.replace("+ -", " - ");
                QIString = QIString.replace(" 1 \\sqrt", " \\sqrt");
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
            String IQIString;
            IQIString = "\\frac{" + this.regPartMult + " + " + this.surdPartMult + " \\sqrt{" + this.quadRing.radicand + "}}{2}";
            IQIString = IQIString.replace(" 1 \\sqrt", " \\sqrt");
            IQIString = IQIString.replace("+ -", " - ");
            return IQIString;
        } else {
            return this.toTeXString();
        }
    }
    
    public String toTeXStringAlt() {
        return "Feature not implemented yet.";
    }
    
    @Override
    public String toHTMLString() {
        return "Feature not implemented yet.";
    }
    
    public String toHTMLStringAlt() {
        return "Feature not implemented yet.";
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
     * @param summand The quadratic integer to be added.
     * @return A new RealQuadraticInteger or ImaginaryQuadraticInteger object with the result of the operation.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come from different quadratic rings, the result of the sum will be an algebraic integer of degree 4 and this exception will be thrown.
     */
    public abstract QuadraticInteger plus(QuadraticInteger summand);
    
    public abstract QuadraticInteger plus(int summand);

    /**
     * Subtraction operation, since operator- can't be overloaded
     * @param subtrahend The quadratic integer to be subtracted.
     * @return A new RealQuadraticInteger or ImaginaryQuadraticInteger object with the result of the operation.
     * @throws AlgebraicDegreeOverflowException If the algebraic integers come from different quadratic rings, the result of the sum will be an algebraic integer of degree 4 and this exception will be thrown.
     */
    public abstract QuadraticInteger minus(QuadraticInteger subtrahend);
    
    public abstract QuadraticInteger minus(int subtrahend);

    public abstract QuadraticInteger times(QuadraticInteger multiplicand);
    
    public abstract QuadraticInteger times(int multiplicand);

    public abstract QuadraticInteger divides(QuadraticInteger divisor);
    
    public abstract QuadraticInteger divides(int divisor) throws NotDivisibleException;

    
}
