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

import algebraics.IntegerRing;

/**
 *
 * @author Alonso del Arte
 */
public abstract class QuadraticRing implements IntegerRing {
    
    /**
     * Ought to be a squarefree integer
     */
    protected int radicand;
    
    protected double realRadSqrt;

    /**
     * Should be true only if radicand is congruent to 1 modulo 4
     */
    protected boolean d1mod4;
    
    public static final int MAX_ALGEBRAIC_DEGREE = 2;
    
    private static boolean preferenceForBlackboardBold = true;
    
    /**
     * Query the setting of the preference for blackboard bold.
     * @return True if blackboard bold is preferred, false if plain bold is 
     * preferred.
     */
    public static boolean preferBlackboardBold() {
        return preferenceForBlackboardBold;
    }
    
    /**
     * Set preference for blackboard bold or plain bold. This is only relevant 
     * for the functions toTeXString() and toHTMLString().
     * @param preferenceForBB True if blackboard bold is preferred, false if 
     * plain bold is preferred.
     */
    public static void preferBlackboardBold(boolean preferenceForBB) {
        preferenceForBlackboardBold = preferenceForBB;
    }
    
    /**
     * Indicates whether the ring has what are imprecisely called 
     * "half-integers," numbers like 3/2 + (&radic;&minus;19)/2.
     * @return True if the ring does have such integers, false otherwise. For 
     * example, true for <i>O</i><sub><b>Q</b>(&radic;13)</sub>, false for 
     * <b>Z</b>[&radic;14].
     */
    public boolean hasHalfIntegers() {
        return d1mod4;
    }
    
    /**
     * Gives the radicand, <i>d</i> for &radic;<i>d</i>, which depends on the 
     * parameter d at construction time.
     * @return The parameter d. For example, for <b>Z</b>[&radic;&minus;2], this 
     * would be -2, for <b>Z</b>[&radic;2] this would be 2.
     */
    public int getRadicand() {
        return radicand;
    }
    
    public abstract double getRadSqrt();
    
    /**
     * This function is included merely to simplify the inheritance structure of 
     * {@link QuadraticRing} to {@link ImaginaryQuadraticRing}.
     * @return The same number as {@link QuadraticRing#getRadicand()} in the 
     * case of {@link RealQuadraticRing}. However, for ImaginaryQuadraticRing, 
     * this is the absolute value. For example, for both 
     * <b>Z</b>[&radic;&minus;2] and <b>Z</b>[&radic;2] this would be 2.
     */
    public abstract int getAbsNegRad();
    
    public abstract double getAbsNegRadSqrt();
    
    /**
     * A text representation of the ring. In some contexts, toTeXString() or toHTMLString() may be preferable.
     * @return A string representing the imaginary quadratic ring which can be output to the console.
     */
    @Override
    public String toString() {
        String IQRString;
        switch (this.radicand) {
            case 5:
                IQRString = "Z[\u03C6]";
                break;
            case -1:
                IQRString = "Z[i]";
                break;
            case -3:
                IQRString = "Z[\u03C9]";
                break;
            default:
                if (this.d1mod4) {
                    IQRString = "O_(Q(\u221A" + this.radicand + "))";
                } else {
                    IQRString = "Z[\u221A" + this.radicand + "]";
                }
        }
        return IQRString;
    }
    
    /**
     * A text representation of the ring's label. The main difference between 
     * this function and toString() is that this function does not use the 
     * "\u221A" character, using "sqrt" instead.
     * @return A String representing the imaginary quadratic ring which can be 
     * output to the console. Examples: for d = -7, the result is 
     * "O_(Q(sqrt(-7)))"; for d = -5, the result is "Z[sqrt(-5)]"; for d = -3, 
     * the result is "Z[omega]"; for d = 2, the result is "Z[sqrt(2)]"; for d = 
     * 5, the result is "Z[phi]". Then you don't have to worry about the ability 
     * to display the "\u221A", "\u03C9" and "\u03C6" characters.
     */
    @Override
    public String toASCIIString() {
        String IQRString;
        switch (this.radicand) {
            case -3:
                IQRString = "Z[omega]"; // omega = -1/2 + sqrt(-3)/2 is a complex cubic root of 1
                break;
            case -1:
                IQRString = "Z[i]"; // i is the imaginary unit, sqrt(-1)
                break;
            case 5:
                IQRString = "Z[phi]"; // phi = 1/2 + sqrt(5)/2, the golden ratio
                break;
            default:
                if (this.d1mod4) {
                    IQRString = "O_(Q(sqrt(" + this.radicand + ")))";
                } else {
                    IQRString = "Z[sqrt(" + this.radicand + ")]";
                }
        }
        return IQRString;
    }

    /**
     * A text representation of the ring's label suitable for use in a TeX 
     * document. The representation uses blackboard bold unless 
     * <code>preferBlackboardBold(false)</code> is in effect.
     * I have not tested this function in the context of outputting to a TeX 
     * document.
     * @return A String suitable for use in a TeX document, if I haven't made 
     * any mistakes. Examples: assuming the default preference for blackboard 
     * bold, for d = -7, the result is "\mathcal O_{\mathbb Q(\sqrt{-7})}"; for 
     * d = -5, the result is "\mathbb Z[\sqrt{-5}]"; for d = -3, the result is 
     * "\mathbb Z[\omega]"; for d = 2, the result is "\mathbb Z[\sqrt{2}]"; for 
     * d = 5, the result is "\mathbb Z[\phi]".
     */
    @Override
    public String toTeXString() {
        String IQRString;
        String QChar;
        String ZChar;
        if (preferenceForBlackboardBold) {
            QChar = "\\mathbb Q";
            ZChar = "\\mathbb Z";
        } else {
            QChar = "\\textbf Q";
            ZChar = "\\textbf Z";
        }
        switch (this.radicand) {
            case -1:
                IQRString = ZChar + "[i]";
                break;
            case -3:
                IQRString = ZChar + "[\\omega]";
                break;
            case 5:
                IQRString = ZChar + "[\\phi]";
                break;
            default:
                if (this.d1mod4) {
                    IQRString = "\\mathcal O_{" + QChar + "(\\sqrt{" + this.radicand + "})}";
                } else {
                    IQRString = ZChar + "[\\sqrt{" + this.radicand + "}]";
            }
        }
        return IQRString;
    }
    
    /**
     * A text representation of the ring's label suitable for use in an HTML 
     * document. The representation uses blackboard bold unless 
     * <code>preferBlackboardBold(false)</code> is in effect.
     * I have not tested this function in the context of outputting to an HTML 
     * document.
     * @return A String suitable for use in an HTML document, if I haven't made 
     * any mistakes. If preferenceForBlackboardBold is true, this also assumes 
     * the font chosen by the browser has the relevant Unicode characters. 
     * Examples: with preference for blackboard bold on, we should get 
     * "<i>O</i><sub>\u211A(&radic;-7)</sub>" for d = -7; for d = -5, the result 
     * is "\u2124[&radic;-5]"; for d = -3, the result is "\u2124[&omega;]"; for 
     * d = 2, the result should be "\u2124[&radic;2]"; for d = 5, the result 
     * should be "\u2124[&phi;]". If instead preference for blackboard bold is 
     * off, we should get "<i>O</i><sub><b>Q</b>(&radic;-7)</sub>" for d = -7; 
     * for d = -5, the result is "<b>Z</b>[&radic;-5]"; for d = -3, the result 
     * is "<b>Z</b>[&omega;]", for d = 2, result is "<b>Z</b>[&radic;2]"; for d 
     * = 5, the result is "<b>Z</b>[&phi;]".
     */
    @Override
    public String toHTMLString() {
        String IQRString;
        String QChar;
        String ZChar;
        if (preferenceForBlackboardBold) {
            QChar = "\u211A"; // Double-struck capital Q
            ZChar = "\u2124"; // Double-struck capital Z
        } else {
            QChar = "<b>Q</b>";
            ZChar = "<b>Z</b>";
        }
        switch (this.radicand) {
            case -3:
                IQRString = ZChar + "[&omega;]";
                break;
            case -1:
                IQRString = ZChar + "[<i>i</i>]";
                break;
            case 5:
                IQRString = ZChar + "[&phi;]";
                break;
            default:
                if (this.d1mod4) {
                    IQRString = "<i>O</i><sub>" + QChar + "(&radic;(" + this.radicand + ")</sub>";
                } else {
                    IQRString = ZChar + "[&radic;" + this.radicand + "]";
                }
        }
        return IQRString;
    }
    
    /**
     * A text representation of the ring's label suitable for use in a filename.
     * With our modern operating systems, it may be unnecessary to worry about 
     * illegal characters in {@link #toString()}. But just in case, here is a 
     * function whose output will hopefully help the calling program conform to 
     * the old MS-DOS 8.3 standard. There is one context in which I have 
     * definitely found this function useful, and that is in the output of the 
     * test suite, for which the font seems to lack Greek letters.
     * @return A string suitable for use in a filename. Examples: for d = -1, 
     * returns "ZI"; for d = -2, returns "ZI2"; for d = -3, returns "ZW" (a 
     * horrible kludge); for d = -7, returns "OQI7".
     */
    public String toFilenameString() {
        String IQRString;
        int radNum = this.radicand;
        switch (radNum) {
            case -3:
                IQRString = "ZW"; // It is frowned upon to use "w" as a makeshift omega, but for the purpose here it is acceptable.
                break;
            case -1:
                IQRString = "ZI";
                break;
            case 5:
                IQRString = "ZPhi";
                break;
            default:
                if (this.d1mod4) {
                    IQRString = "OQ";
                } else {
                    IQRString = "Z";
                }
                if (this.radicand < 0) {
                    IQRString = IQRString + "I";
                    radNum *= -1;
                }
                IQRString = IQRString + radNum;
        }
        return IQRString;
    }
    
    /**
     * Compares whether an object is equal to this quadratic ring. This is 
     * another function which the NetBeans IDE wrote for me.
     * @param obj The object to compare this to.
     * @return True if the object is a quadratic ring with the same parameter d 
     * passed to its constructor as this imaginary quadratic ring, false 
     * otherwise.
     */
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
        final QuadraticRing other = (QuadraticRing) obj;
        return (this.radicand == other.radicand);
    }
    
    /**
     * Returns a hash code value for the quadratic ring. Overriding {@link 
     * Object#hashCode} on account of needing to override {@link Object#equals}. 
     * The hash code is based solely on <i>d</i> from &radic;<i>d</i>, which 
     * this ring adjoins. The hash code is mathematically guaranteed to be 
     * unique for distinct rings within any possible execution of the program 
     * (unless overridden in a child class).
     * @return The parameter <i>d</i> that was passed to the constructor, minus 
     * 1 if <i>d</i> = 1 mod 4, multiplied by -1 otherwise.
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

    @Override
    public int getMaxAlgebraicDegree() {
        return MAX_ALGEBRAIC_DEGREE;
    }
    
}
