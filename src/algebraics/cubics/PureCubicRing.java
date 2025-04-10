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
package algebraics.cubics;

import arithmetic.PowerBasis;
import static calculators.NumberTheoreticFunctionsCalculator.isCubefree;
import fractions.Fraction;

/**
 * Represents the ring of integers of a pure cubic ring.
 * @author Alonso del Arte
 */
public class PureCubicRing extends CubicRing {
    
    private static final Fraction ONE = new Fraction(1);
    
    private static final Fraction[] THREE_ONES = {ONE, ONE, ONE};
    
    private static final PowerBasis USUAL_POWER_BASIS 
            = new PowerBasis(THREE_ONES);
    
    final int radicand;

    /**
     * Affirms that this is a purely real ring. Even if <i>d</i> is negative, 
     * &#8731;<i>d</i> is a real number and the extension 
     * <b>Q</b>(&#8731;<i>d</i>) has neither nontrivially imaginary nor 
     * nontrivially complex numbers.
     * @return Always true for purely cubic rings.
     */
    @Override
    public boolean isPurelyReal() {
        return true;
    }
    
    public int getRadicand() {
        return this.radicand;
    }

    public double getCubeRoot() {
        return Math.cbrt(this.radicand);
    }

    // TODO: Write tests for this
    @Override
    public int discriminant() {
        return 0;
    }

    /** WORK IN PROGRESS...
     * Gets the power basis of this ring.
     * @return If <i>d</i> is squarefree and not congruent to &plusmn;1 mod 9, 
     * then this function returns {1, <i>a</i>, <i>a</i><sup>2</sup>}, where 
     * <i>a</i> is &#8731;<i>d</i>. It also returns that for other <i>d</i>, but 
     * that won't be the case once I get around to writing the appropriate 
     * tests.
     */
    @Override
    public PowerBasis getPowerBasis() {
        return USUAL_POWER_BASIS;
    }
    
    @Override
    public String toString() {
        return "Z[" + CubicRing.CUBE_ROOT_SYMBOL + this.radicand + "]";
    }

    @Override
    public String toASCIIString() {
        return "Z[cbrt(" + this.radicand + ")]";
    }

    @Override
    public String toTeXString() {
        return "\\textbf Z[\\root 3 \\of {" + this.radicand + "}]";
    }

    @Override
    public String toTeXStringBlackboardBold() {
        return "\\mathbb Z[\\root 3 \\of {" + this.radicand + "}]";
    }

    @Override
    public String toHTMLString() {
        return "<b>Z</b>[&#x221B;" + this.radicand + "]";
    }

    @Override
    public String toHTMLStringBlackboardBold() {
        return "&#x2124;[&#x221B;" + this.radicand + "]";
    }

    @Override
    public String toFilenameString() {
        return "ZCBRT" + this.radicand;
    }
    
    /**
     * Tests an object for equality. For the examples, suppose this ring is 
     * <b>Z</b>[&#x221B;13].
     * @param obj The object to test for equality. Examples: 
     * <b>Z</b>[&#x221B;13], <i>O</i><sub><b>Q</b>(&#x221B;19)</sub>, 
     * <b>Z</b>[<i>i</i>], null.
     * @return True only if {@code obj} is a {@code PureCubicRing} instance 
     * initialized with the same parameter <i>d</i>, false in all other cases. 
     * In the examples, true for <b>Z</b>[&#x221B;13], false for 
     * <i>O</i><sub><b>Q</b>(&#x221B;19)</sub> (different parameter <i>d</i>), 
     * false for <b>Z</b>[<i>i</i>] (instance of {@code ImaginaryQuadraticRing}, 
     * not {@code PureCubicRing}) and obviously false for null.
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
        return this.radicand == ((PureCubicRing) obj).radicand;
    }

    /**
     * Gives a hash code for this ring. Mathematically guaranteed to be unique.
     * @return A hash code based on the constructor parameter {@code d} in some 
     * way, such as being multiplied by &minus;1.
     */
    @Override
    public int hashCode() {
        return -this.radicand;
    }
    
    // TODO: Write tests for this
    public PureCubicRing(int d) {
        if (!isCubefree(d)) {
            String excMsg = "Number " + d + " is not cubefree";
            throw new IllegalArgumentException(excMsg);
        }
        this.radicand = d;
    }
    
}
