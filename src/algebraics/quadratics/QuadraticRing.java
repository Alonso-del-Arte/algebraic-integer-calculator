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

import algebraics.IntegerRing;
import arithmetic.PowerBasis;
import fractions.Fraction;
import calculators.NumberTheoreticFunctionsCalculator;

import java.io.Serializable;

/**
 * Provides a template for defining objects to represent real or imaginary 
 * quadratic rings. Also provides functions for getting the radicand of the 
 * generating algebraic integer and functions for representation as a 
 * <code>String</code>.
 * @author Alonso del Arte
 */
public abstract class QuadraticRing implements IntegerRing, Serializable {
    
    /**
     * The maximum possible algebraic degree of an algebraic integer in a 
     * quadratic integer ring.
     */
    public static final int MAX_ALGEBRAIC_DEGREE = 2;
    
    private static final Fraction ONE_FRACT = new Fraction(1);
    
    private static final Fraction[] ONES = {ONE_FRACT, ONE_FRACT};
    
    /**
     * The power basis for any quadratic integer ring is 1, <i>a</i>.
     */
    public static final PowerBasis QUADRATIC_POWER_BASIS = new PowerBasis(ONES);
    
    private static final String Q_SYMBOL_TEX = "\\mathbf Q";
    
    private static final String Z_SYMBOL_TEX = "\\mathbf Z";
    
    private static final String Q_SYMBOL_TEX_BLACKBOARD_BOLD = "\\mathbb Q";
    
    private static final String Z_SYMBOL_TEX_BLACKBOARD_BOLD = "\\mathbb Z";
    
    private static final String Q_SYMBOL_HTML = "<b>Q</b>";
    
    private static final String Z_SYMBOL_HTML = "<b>Z</b>";
    
    private static final String Q_SYMBOL_HTML_BLACKBOARD_BOLD = "&#x211A;";
    
    private static final String Z_SYMBOL_HTML_BLACKBOARD_BOLD = "&#x2124;";
    
    /**
     * Ought to be a squarefree integer.
     */
    protected final int radicand;
    
    protected final int absRadicand;
    
    /**
     * A rational approximation of either &radic;<i>d</i> for <i>d</i> positive 
     * or (&radic;<i>d</i>)/<i>i</i> for <i>d</i> negative.
     */
    protected final double realRadSqrt;

    /**
     * Should be true only if radicand is congruent to 1 modulo 4. Remember that 
     * &minus;3 &equiv; 1 mod 4; Java needs a little nudge on this.
     */
    protected boolean d1mod4;
    
    /**
     * Indicates whether the ring has what are imprecisely called 
     * "half-integers," numbers like 3/2 + (&radic;&minus;19)/2.
     * @return True if the ring does have such integers, false otherwise. For 
     * example, true for <i>O</i><sub><b>Q</b>(&radic;13)</sub>, false for 
     * <b>Z</b>[&radic;14].
     */
    public boolean hasHalfIntegers() {
        return this.d1mod4;
    }
    
    /**
     * Gives the radicand, <i>d</i> for &radic;<i>d</i>, which depends on the 
     * parameter <code>d</code> at construction time.
     * @return The parameter <code>d</code> that was passed to the constructor. 
     * For example, for <b>Z</b>[&radic;&minus;2], this would be &minus;2, for 
     * <b>Z</b>[&radic;2] this would be 2.
     */
    public int getRadicand() {
        return this.radicand;
    }
    
    /**
     * Gives the absolute value of the radicand. This function is included 
     * merely to simplify the inheritance structure of {@link QuadraticRing} to 
     * {@link ImaginaryQuadraticRing}.
     * @return The same number as {@link QuadraticRing#getRadicand() 
     * getRadicand()} in the case of {@link RealQuadraticRing}. However, for 
     * <code>ImaginaryQuadraticRing</code>, this is the absolute value. For 
     * example, for both <b>Z</b>[&radic;&minus;2] and <b>Z</b>[&radic;2] this 
     * would be 2.
     */
    public int getAbsNegRad() {
        return this.absRadicand;
    }
    
    /**
     * Gives a numeric approximation of the square root of the absolute value of 
     * the radicand.
     * @return The same number as {@link QuadraticRing#getRadSqrt() 
     * getRadSqrt()} in the case of {@link RealQuadraticRing}. However, for 
     * {@link ImaginaryQuadraticRing}, this is a rational approximation of the 
     * square root of the absolute value of the radicand, or 
     * &radic;<i>d</i>/<i>i</i>. For example, for both <b>Z</b>[&radic;&minus;2] 
     * and <b>Z</b>[&radic;2] this would be roughly 1.414213562373. Only in one 
     * case is this number ever an exact value: in the case of 
     * <b>Z</b>[<i>i</i>].
     */
    public double getAbsNegRadSqrt() {
        return this.realRadSqrt;
    }
    
    /**
     * Gives the discriminant of this ring. This number pertains to the 
     * ramifying primes in this ring. If the field is <b>Q</b>(&radic;<i>d</i>), 
     * and <i>d</i> &equiv; 1 mod 4, then the discriminant is simply <i>d</i>, 
     * otherwise it's 4<i>d</i>.
     * @return Same as {@link #getRadicand()} if this ring has so-called 
     * "half-integers," otherwise that number multiplied by 4. For example, for 
     * <b>Z</b>[&omega;], this would be &minus;3; for <b>Z</b>[&radic;2] this 
     * would be 8. Be aware that if |<i>d</i>| more than a quarter of 
     * <code>Integer.MAX_VALUE</code>, then this function might give the wrong 
     * result due to overflowing 32-bit integers.
     */
    @Override
    public int discriminant() {
        if (this.d1mod4) {
            return this.radicand;
        } else {
            return 4 * this.radicand;
        }
    }
    
    /**
     * Gives the power basis of the ring. It's always the same for a quadratic 
     * integer ring.
     * @return The power basis, 1, <i>a</i>.
     */
    @Override
    public PowerBasis getPowerBasis() {
        return QUADRATIC_POWER_BASIS;
    }
    
    /**
     * Gives the ring's label, using mostly alphanumeric ASCII characters but 
     * also often the "&radic;" character and occasionally Greek letters.
     * @return A label for the quadratic ring. For example, "Z[&radic;83]".
     */
    @Override
    public String toString() {
        if (this.radicand == 5) {
            return "Z[\u03C6]";
        }
        if (this.d1mod4) {
            return "O_(Q(\u221A" + this.radicand + "))";
        }
        return "Z[\u221A" + this.radicand + "]";
    }
    
    /**
     * A text representation of the ring's label. The main difference between 
     * this function and {@link #toString() toString()} is that this function 
     * does not use the "\u221A" character, using "sqrt" instead. Greek letters 
     * are spelled out by their Roman letter names.
     * @return A String representing the imaginary quadratic ring which can be 
     * output to the console. Examples: for <i>d</i> = &minus;7, the result is 
     * "O_(Q(sqrt(-7)))"; for <i>d</i> = &minus;5, the result is "Z[sqrt(-5)]"; 
     * for <i>d</i> = &minus;3, the result is "Z[omega]"; for <i>d</i> = 2, the 
     * result is "Z[sqrt(2)]"; for <i>d</i> = 5, the result is "Z[phi]". Then 
     * you don't have to worry about the ability to display the "&radic;", 
     * "&omega;" and "&phi;" characters.
     */
    @Override
    public String toASCIIString() {
        switch (this.radicand) {
            case -3:
                return "Z[omega]";
            case -1:
                return "Z[i]";
            case 5:
                return "Z[phi]";
            default:
                if (this.d1mod4) {
                    return "O_(Q(sqrt(" + this.radicand + ")))";
                } else {
                    return "Z[sqrt(" + this.radicand + ")]";
                }
        }
    }
    
    private String toTeXString(boolean preferBlackboardBold) {
        String qSymbol;
        String zSymbol;
        if (preferBlackboardBold) {
            qSymbol = Q_SYMBOL_TEX_BLACKBOARD_BOLD;
            zSymbol = Z_SYMBOL_TEX_BLACKBOARD_BOLD;
        } else {
            qSymbol = Q_SYMBOL_TEX;
            zSymbol = Z_SYMBOL_TEX;
        }
        switch (this.radicand) {
            case -1:
                return zSymbol + "[i]";
            case -3:
                return zSymbol + "[\\omega]";
            case 5:
                return zSymbol + "[\\phi]";
            default:
                if (this.d1mod4) {
                    return "\\mathcal O_{" + qSymbol + "(\\sqrt{" 
                            + this.radicand + "})}";
                } else {
                    return zSymbol + "[\\sqrt{" + this.radicand + "}]";
            }
        }
    }
    
    /**
     * A text representation of this ring's label suitable for use in a TeX 
     * document. The representation does not use blackboard bold. Use {@link 
     * #toTeXStringBlackboardBold()} if you need TeX with blackboard bold.
     * <p>I have not tested this function in the context of outputting to a TeX 
     * document.</p>
     * @return TeX suitable for display style. For example, for <i>d</i> = 
     * &minus;7, this function gives "\mathcal O_{\mathbf Q(\sqrt{-7})}"; for 
     * <i>d</i> = &minus;5, the result is "\mathbf Z[\sqrt{-5}]"; for <i>d</i> = 
     * &minus;3, the result is "\mathbf Z[\omega]"; for <i>d</i> = 2, the result 
     * is "\mathbf Z[\sqrt{2}]"; for <i>d</i> = 5, the result is "\mathbf 
     * Z[\phi]".
     */
    @Override
    public String toTeXString() {
        return this.toTeXString(false);
    }

    /**
     * A text representation of this ring's label suitable for use in a TeX 
     * document. The representation uses blackboard bold. Use {@link 
     * #toTeXString()} if you want TeX without blackboard bold.
     * <p>I have not tested this function in the context of outputting to a TeX 
     * document.</p>
     * @return TeX suitable for display style. For example, for <i>d</i> = 
     * &minus;7, this function gives "\mathcal O_{\mathbb Q(\sqrt{-7})}"; for 
     * <i>d</i> = &minus;5, the result is "\mathbb Z[\sqrt{-5}]"; for <i>d</i> = 
     * &minus;3, the result is "\mathbb Z[\omega]"; for <i>d</i> = 2, the result 
     * is "\mathbb Z[\sqrt{2}]"; for <i>d</i> = 5, the result is "\mathbb 
     * Z[\phi]".
     */
    @Override
    public String toTeXStringBlackboardBold() {
        return this.toTeXString(true);
    }

    private String toHTMLString(boolean preferBlackboardBold) {
        String qSymbol;
        String zSymbol;
        if (preferBlackboardBold) {
            qSymbol = Q_SYMBOL_HTML_BLACKBOARD_BOLD;
            zSymbol = Z_SYMBOL_HTML_BLACKBOARD_BOLD;
        } else {
            qSymbol = Q_SYMBOL_HTML;
            zSymbol = Z_SYMBOL_HTML;
        }
        switch (this.radicand) {
            case -3:
                return zSymbol + "[&omega;]";
            case -1:
                return zSymbol + "[<i>i</i>]";
            case 5:
                return zSymbol + "[&phi;]";
            default:
                if (this.d1mod4) {
                    return "<i>O</i><sub>" + qSymbol + "(&radic;(" 
                            + this.radicand + "))</sub>";
                } else {
                    return zSymbol + "[&radic;" + this.radicand + "]";
                }
        }
    }
    
    /**
     * A text representation of this ring's label suitable for use in an HTML 
     * document. The representation does not use blackboard bold. Use {@link 
     * #toHTMLStringBlackboardBold()} if you need HTML with blackboard bold. In 
     * both of these I have assumed that the "mathcal O" from TeX is 
     * inconsistently available or completely unavailable.
     * @return Text suitable for an HTML element's inner HTML property. For 
     * example, "&lt;b&gt;Z&lt;/b&gt;[&amp;phi;]" for <i>d</i> = 5, which should 
     * render as "<b>Z</b>[&phi;]".
     */
    @Override
    public String toHTMLString() {
        return this.toHTMLString(false);
    }
    
    /**
     * A text representation of this ring's label suitable for use in an HTML 
     * document. The representation uses blackboard bold. Use {@link 
     * #toHTMLString()} if you need HTML with blackboard bold. In both of these 
     * I have assumed that the "mathcal O" from TeX is inconsistently available 
     * or completely unavailable.
     * @return Text suitable for an HTML element's inner HTML property. For 
     * example, "&amp;#x2124;[&amp;phi;]" for <i>d</i> = 5, which should render  
     * as "&#x2124;[&phi;]".
     */
    @Override
    public String toHTMLStringBlackboardBold() {
        return this.toHTMLString(true);
    }
    
    /**
     * A text representation of the ring's label suitable for use in a filename.
     * With our modern operating systems, it may be unnecessary to worry about 
     * illegal characters in {@link #toString() toString()}. But just in case, 
     * here is a function whose output will hopefully help the calling program 
     * conform to the old MS-DOS 8.3 standard.
     * @return A string suitable for use in a filename. Examples: for <i>d</i> = 
     * &minus;1, returns "ZI"; for <i>d</i> = 2, returns "Z2"; for <i>d</i> = 
     * = &minus;3, returns "ZW" (a horrible kludge); for <i>d</i> = &minus;7, 
     * returns "OQI7"; for <i>d</i> = 13, returns "OQ13".
     */
    @Override
    public String toFilenameString() {
        switch (this.radicand) {
            case -3:
                return "ZW";
            case -1:
                return "ZI";
            case 5:
                return "ZPHI";
            default:
                String intermediate = this.d1mod4 ? "OQ" : "Z";
                if (this.radicand < 0) {
                    intermediate = intermediate + "I";
                }
                return intermediate + this.absRadicand;
        }
    }
    
    /**
     * Compares whether an object is equal to this quadratic ring. This is 
     * another function which the NetBeans IDE wrote for me, and then I tweaked 
     * slightly.
     * @param obj The object to compare this to.
     * @return True if the object is a quadratic ring with the same parameter 
     * <i>d</i> passed to its constructor as this quadratic ring, false 
     * otherwise.
     */
    @Override
    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
            return false;
//        }
//        if (this.getClass() != obj.getClass()) {
//            return false;
//        }
//        final QuadraticRing other = (QuadraticRing) obj;
//        return (this.radicand == other.radicand);
    }
    
    /**
     * Returns a hash code value for the quadratic ring. Overriding {@link 
     * Object#hashCode} on account of needing to override {@link Object#equals}. 
     * The hash code is based solely on <i>d</i> from &radic;<i>d</i>, which 
     * this ring adjoins. The hash code is mathematically guaranteed to be 
     * unique for distinct rings within any possible execution of the program 
     * (unless overridden in a child class).
     * @return The parameter <i>d</i> that was passed to the constructor, minus 
     * 1 if <i>d</i> = 1 mod 4, multiplied by &minus;1 otherwise.
     */
    @Override
    public int hashCode() {
        int hash = this.radicand;
        if (this.d1mod4) {
            hash--;
        } else {
            hash *= -1;
        }
        return hash;
    }

    /**
     * Gives the maximum algebraic degree an algebraic integer in this quadratic 
     * ring can have.
     * @return Always 2 in the case of a quadratic ring. This value is also 
     * available as the constant {@link #MAX_ALGEBRAIC_DEGREE}.
     */
    @Override
    public final int getMaxAlgebraicDegree() {
        return MAX_ALGEBRAIC_DEGREE;
    }
    
    public static QuadraticRing apply(int d) {
        String excMsg;
        switch (Integer.signum(d)) {
            case -1:
                return new ImaginaryQuadraticRing(d);
            case 0:
                excMsg = "0 is not valid for parameter d";
                throw new IllegalArgumentException(excMsg);
            case 1:
                return new RealQuadraticRing(d);
            default:
                excMsg = "Unexpected error for d = " + d;
                throw new RuntimeException(excMsg);
        }
    }
    
    /**
     * Superclass constructor for {@link ImaginaryQuadraticRing} and {@link 
     * RealQuadraticRing}.
     * @param d A squarefree integer. Subclasses can and should place further 
     * restrictions.
     * @throws IllegalArgumentException If <code>d</code> is a multiple of a 
     * square other than 1.
     */
    public QuadraticRing(int d) {
        if (!NumberTheoreticFunctionsCalculator.isSquarefree(d)) {
            String excMsg = "Squarefree integer required for parameter d, " + d 
                    + " is not squarefree";
            throw new IllegalArgumentException(excMsg);
        }
        this.radicand = d;
        this.absRadicand = Math.abs(d);
        this.realRadSqrt = Math.sqrt(this.absRadicand);
    }
    
}
