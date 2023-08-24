// TODO: Consider deprecation
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
 * Defines an object to represent the quartic ring 
 * <i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>, where &zeta;<sub>8</sub> = 
 * (&radic;2)/2 + (&radic;&minus;2)/2, which is a fourth root of &minus;1. There 
 * is really no point to instantiating this class more than once in a given run.
 * @author Alonso del Arte
 */
public final class Zeta8Ring extends QuarticRing {
    
    private static final Fraction ONE_AS_FRACTION = new Fraction(1);
    
    private static final Fraction[] FOUR_ONES = {ONE_AS_FRACTION, 
        ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_AS_FRACTION};
    
    private static final PowerBasis POWER_BASIS = new PowerBasis(FOUR_ONES);

    /**
     * Indicates that this ring is not purely real. It contains purely imaginary 
     * numbers as well as complex numbers.
     * @return Always false for <i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>.
     */
    @Override
    public final boolean isPurelyReal() {
        return false;
    }
    
    /**
     * Gives the discriminant of <i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>.
     * @return Always 256 for <i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>.
     */
    @Override
    public int discriminant() {
        return 256;
    }
    
    @Override
    public PowerBasis getPowerBasis() {
        return POWER_BASIS;
    }
    
    /**
     * Gives the ring's label as a String using Unicode characters.
     * @return The String "O_Q(&zeta;<sub>8</sub>)".
     */
    @Override
    public String toString() {
        return "O_Q(\u03B6\u2088)";
    }

    /**
     * Formats the ring's label as a String using ASCII characters only.
     * @return The String "O_Q(zeta_8)".
     */
    @Override
    public String toASCIIString() {
        return "O_Q(zeta_8)";
    }

    /**
     * Formats the ring's label as a String that can be used in a TeX document.
     * @return "\mathcal O_{\mathbb Q(\zeta_8)}" if preference for blackboard 
     * bold is true, "\mathcal O_{\textbf Q(\zeta_8)}" if false.
     */
    @Override
    public String toTeXString() {
        if (QuarticRing.preferenceForBlackboardBold) {
            return "\\mathcal O_{\\mathbb Q(\\zeta_8)}";
        } else {
            return "\\mathcal O_{\\textbf Q(\\zeta_8)}";
        }
    }

    /**
     * Formats the ring's label as a String that can be used in an HTML 
     * document.
     * @return "<i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>" if preference for 
     * blackboard bold is true, 
     * "<i>O</i><sub><b>Q</b>(&zeta;<sub>8</sub>)</sub>" if false.
     */
    @Override
    public String toHTMLString() {
        if (preferenceForBlackboardBold) {
            return "<i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>";
        } else {
            return "<i>O</i><sub><b>Q</b>(&zeta;<sub>8</sub>)</sub>";
        }
    }
    
    /**
     * Formats the ring's label as a String that could theoretically be used in 
     * an old MS-DOS file save dialog.
     * @return The String "O_QZETA8".
     */
    @Override
    public String toFilenameString() {
        return "OQ_ZETA8";
    }
    
}
