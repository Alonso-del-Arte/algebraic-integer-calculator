/*
 * Copyright (C) 2021 Alonso del Arte
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
package algebraics.quartics;

import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 * Represents the ring of algebraic integers of <b>Z</b>[&radic;(1 + <i>i</i>)]. 
 * The name "quartic Gaussian" is by no means official.
 * <p>Honestly, I don't know much about this ring. What I know is mostly what  
 * I've gleaned from Wolfram Alpha, the LMFDB and my own calculations. This is a 
 * place as good as any to jot down what I know about it.</p>
 * <p>The field generator is &radic;(1 + <i>i</i>), an algebraic integer with 
 * minimal polynomial <i>x</i><sup>4</sup> &minus; 2<i>x</i><sup>2</sup> + 
 * 2. Referring to this number as <i>a</i>, the power basis of the ring is 1, 
 * <i>a</i>, <i>a</i><sup>2</sup>, <i>a</i><sup>3</sup>.</p>
 * <p>The fundamental unit is, according to Wolfram Alpha, &minus;<i>i</i> + 
 * &radic;(&minus;1 &minus; <i>i</i>).</p>
 * @deprecated This is a special case of {@link ComplexBiquadraticRing}.
 * @author Alonso del Arte
 */
@Deprecated
public class QuarticGaussianRing extends QuarticRing {

    private static final Fraction ONE_AS_FRACTION = new Fraction(1);
    
    private static final Fraction[] FOUR_ONES = {ONE_AS_FRACTION, 
        ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_AS_FRACTION};
    
    private static final PowerBasis POWER_BASIS = new PowerBasis(FOUR_ONES);

    /**
     * Indicates whether the ring is purely real or not. In the case of this 
     * ring, it's not purely real.
     * @return Always false for this ring, as it contains, for example, 1 + 
     * <i>i</i>.
     */
    @Override
    public boolean isPurelyReal() {
        return false;
    }

    /**
     * Gives the discriminant of the ring. This number pertains to the prime 
     * numbers ramifying in the ring.
     * @return Always 2<sup>9</sup> = 512 (there are no odd ramifying primes in 
     * this ring).
     */
    @Override
    public int discriminant() {
        return 512;
    }
    
    /**
     * Gives the power basis of the ring.
     * @return The power basis of <b>Z</b>[&radic;(1 + <i>i</i>)] is 1, 
     * <i>a</i>, <i>a</i><sup>2</sup>, <i>a</i><sup>3</sup>.
     */
    @Override
    public PowerBasis getPowerBasis() {
        return POWER_BASIS;
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof QuarticGaussianRing);
    }
    
    @Override
    public int hashCode() {
        return -512;
    }

    @Override
    public String toASCIIString() {
        return "Z[sqrt(1 + i)]";
    }

    @Override
    public String toTeXString() {
        if (QuarticRing.preferBlackboardBold()) {
            return "\\mathbb Z[\\sqrt{1 + i}]";
        } else {
            return "\\textbf Z[\\sqrt{1 + i}]";
        }
    }

    @Override
    public String toHTMLString() {
        if (QuarticRing.preferBlackboardBold()) {
            return "\u2124[&radic;(1 + <i>i</i>)]";
        } else {
            return "<b>Z</b>[&radic;(1 + <i>i</i>)]";
        }
    }

    @Override
    public String toFilenameString() {
        return "ZSQRT1I";
    }
    
}
