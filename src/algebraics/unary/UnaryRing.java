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
package algebraics.unary;

import algebraics.IntegerRing;
import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 * Represents the ring of integers of degree 1. Mathematicians notate this ring 
 * as &#x2124; or <b>Z</b>. This class has only one instance, {@link #Z}, and 
 * the only constructor is class private.
 * @author Alonso del Arte
 */
public final class UnaryRing implements IntegerRing {
    
    /**
     * The ring of algebraic integers of degree 1, notated <b>Z</b> or &#x2124;.
     */
    public static final UnaryRing Z = new UnaryRing();
    
    private static final String LABEL = "Z";
    
    private static final Fraction ONE = new Fraction(1);
    
    private static final Fraction[] MULTIPLICAND = {ONE};
    
    private static final PowerBasis BASIS = new PowerBasis(MULTIPLICAND);

    /**
     * Gives the maximum algebraic degree of numbers in this ring. For a nonzero 
     * integer <i>n</i>, the minimum polynomial is <i>x</i> &minus; <i>n</i>, 
     * and for 0 it's just <i>x</i>, which means an implied exponent 1 in both 
     * cases.
     * @return Always 1.
     */
    @Override
    public final int getMaxAlgebraicDegree() {
        return 1;
    }

    /**
     * Indicates that this ring is purely real. There is only one purely 
     * imaginary number in this ring, and that's 0, which is also technically 
     * purely real.
     * @return Always true.
     */
    @Override
    public final boolean isPurelyReal() {
        return true;
    }

    /**
     * Gives the discriminant of this ring.
     * @return Always 1.
     */
    @Override
    public final int discriminant() {
        return 1;
    }

    /**
     * Gives the power basis of the ring.
     * @return Always 1.
     */
    @Override
    public final PowerBasis getPowerBasis() {
        return BASIS;
    }

    /**
     * Gives the ring's label.
     * @return Always just "Z". 
     */
    @Override
    public final String toString() {
        return LABEL;
    }

    /**
     * Formats the ring's label as a {@code String} using ASCII characters only.
     * @return Always just "Z". 
     */
    @Override
    public final String toASCIIString() {
        return LABEL;
    }

    /**
     * Formats the ring's label as a {@code String} that can be used in a TeX 
     * document. Does not use blackboard bold.
     * @return Either "\mathbf Z" or "\mathbf{Z}", which should display as 
     * "<b>Z</b>" in the output document, provided the TeX printer is configured 
     * correctly.
     */
    @Override
    public final String toTeXString() {
        return "\\mathbf Z";
    }

    /**
     * Formats the ring's label as a {@code String} that can be used in a TeX 
     * document. Uses blackboard bold.
     * @return Either "\mathbb Z" or "\mathbb{Z}", which should display as 
     * "&#x2124;" in the output document, provided the TeX printer is configured 
     * correctly.
     */
    @Override
    public final String toTeXStringBlackboardBold() {
        return "\\mathbb Z";
    }

    /**
     * Formats the ring's label as a {@code String} that can be used in an HTML 
     * document. Does not use blackboard bold.
     * @return Something like "&lt;b&gt;Z&lt;/b&gt;", which should display as 
     * "<b>Z</b>" in a Web browser.
     */
    @Override
    public final String toHTMLString() {
        return "<b>Z</b>";
    }

    /**
     * Formats the ring's label as a {@code String} that can be used in an HTML 
     * document. Uses blackboard bold.
     * @return Either "&amp;#x2124;" or "&amp;#8484;", both of which display as 
     * "&#x2124;" in a Web browser.
     */
    @Override
    public final String toHTMLStringBlackboardBold() {
        return "&#x2124;";
    }

    /**
     * Formats the ring's label as a {@code String} that could theoretically be 
     * used in an old MS-DOS file save dialog. 
     * @return Always "Z", the same as {@link #toASCIIString()}.
     */
    @Override
    public final String toFilenameString() {
        return this.toASCIIString();
    }
    
    private UnaryRing() {
    }
    
}
