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

import calculators.NumberTheoreticFunctionsCalculator;

/**
 * Defines objects to represent imaginary quadratic rings.
 * @author Alonso del Arte
 */
public final class ImaginaryQuadraticRing extends QuadraticRing {
    
    private static final long serialVersionUID = 4547847535800105779L;

    /**
     * Indicates that this ring is not purely real. This ring contains purely 
     * imaginary numbers as well as complex numbers, in addition to purely real 
     * integers.
     * @return Always false, since this is an imaginary quadratic ring.
     */
    @Override
    public final boolean isPurelyReal() {
        return false;
    }
    
    @Override
    public int getExponentForRadicand() {
        return 2;
    }
    
    // TODO: Refactor by deleting after rewriting corresponding test for RQI
    @Override
    public int getRadicand() {
        return this.radicand;
    }
    
    /**
     * Gives the absolute value of the radicand. With imaginary quadratic rings, 
     * this function gives a different result from {@link #getRadicand()}, while 
     * for real quadratic rings both functions give the same result.
     * @return The absolute value of the radicand. For example, for 
     * <b>Z</b>[&radic;&minus;10], this would be 10.
     */
    @Override
    public int getAbsNegRad() {
        return this.absRadicand;
    }
    
    /**
     * Gives a numeric approximation of the square root of the absolute value of 
     * the radicand. For imaginary quadratic rings, use this function instead of 
     * {@link #getRadSqrt()}.
     * @return The rational approximation of the square root of the radicand 
     * divided by <i>i</i>. For example, for <b>Z</b>[&radic;&minus;2], this 
     * would be 1.4142135623730951; for <b>Z</b>[&radic;&minus;3], this would be 
     * 1.7320508075688772.
     */
    @Override
    public double getAbsNegRadSqrt() {
        return this.realRadSqrt;
    }
    
    /**
     * Gives the ring's label, using mostly alphanumeric ASCII characters but 
     * also often the "&radic;" character and occasionally Greek letters.
     * @return A label for the quadratic ring. For example, 
     * "O_(Q(&radic;&minus;19))".
     */
    @Override
    public String toString() {
        switch (this.radicand) {
            case -1:
                return "Z[i]";
            case -3:
                return "Z[\u03C9]";
            default:
                if (this.d1mod4) {
                    return "O_(Q(\u221A\u2212" + this.absRadicand + "))";
                } else {
                    return "Z[\u221A\u2212" + this.absRadicand + "]";
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
     * example, "&lt;b&gt;Z&lt;/b&gt;[&lt;i&gt;i&lt;/i&gt;]" for <i>d</i> = 
     * &minus;1, which should render as "<b>Z</b>[<i>i</i>]".
     */
    @Override
    public String toHTMLString() {
        switch (this.radicand) {
            case -1:
                return "<b>Z</b>[<i>i</i>]";
            case -3:
                return "<b>Z</b>[&omega;]";
            default:
                if (this.d1mod4) {
                    return "<i>O</i><sub><b>Q</b>(&radic;(&minus;" 
                            + this.absRadicand + "))</sub>";
                } else {
                    return "<b>Z</b>[&radic;&minus;" + this.absRadicand + "]";
                }
        }
    }
    
    /**
     * A text representation of this ring's label suitable for use in an HTML 
     * document. The representation uses blackboard bold. Use {@link 
     * #toHTMLString()} if you need HTML with blackboard bold. In both of these 
     * I have assumed that the "mathcal O" from TeX is inconsistently available 
     * or completely unavailable.
     * @return Text suitable for an HTML element's inner HTML property. For 
     * example, "&amp;#x2124;[&lt;i&gt;i&lt;/i&gt;]" for <i>d</i> = &minus;1, 
     * which should render as "&#x2124;[<i>i</i>]".
     */
    @Override
    public String toHTMLStringBlackboardBold() {
        switch (this.radicand) {
            case -1:
                return "&#x2124;[<i>i</i>]";
            case -3:
                return "&#x2124;[&omega;]";
            default:
                if (this.d1mod4) {
                    return "<i>O</i><sub>&#x211A;(&radic;(&minus;" 
                            + this.absRadicand + "))</sub>";
                } else {
                    return "&#x2124;[&radic;&minus;" + this.absRadicand + "]";
                }
        }
    }
    
    /**
     * Constructs a new object representing an imaginary quadratic ring.
     * @param d A squarefree, negative integer. Examples: &minus;3, &minus;58, 
     * &minus;163.
     * @throws IllegalArgumentException If <code>d</code> is 0 or positive, or 
     * negative but the multiple of a nontrivial square. For example, 3583 would 
     * cause this exception on account of being positive; &minus;12 would cause 
     * it because it's a multiple of 2<sup>2</sup>, even though it's negative.
     */
    public ImaginaryQuadraticRing(int d) {
        super(d);
        if (d > -1) {
            String excMsg = "Negative integer required for parameter d, not " 
                    + d;
            throw new IllegalArgumentException(excMsg);
        }
        this.d1mod4 = (d % 4 == -3);
    }
    
}