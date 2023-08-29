// TODO: Consider deprecation
/*
 * Copyright (C) 2023 Alonso del Arte
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
 * <i>O</i><sub>\u211A(&zeta;<sub>12</sub>)</sub>, where &zeta;<sub>12</sub> = 
 * &minus;(&radic;3)/2 + <i>i</i>/2, which is a sixth root of &minus;1. There 
 * is really no point to instantiating this class more than once in a given run.
 * @author Alonso del Arte
 */
public class Zeta12Ring extends QuarticRing {

    private static final Fraction ONE_AS_FRACTION = new Fraction(1);
    
    private static final Fraction[] FOUR_ONES = {ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_AS_FRACTION, ONE_AS_FRACTION};
    
    private static final PowerBasis POWER_BASIS = new PowerBasis(FOUR_ONES);

    @Override
    public boolean isPurelyReal() {
        return false;
    }
    
    @Override
    public int discriminant() {
        return 144;
    }
    
    @Override
    public PowerBasis getPowerBasis() {
        return POWER_BASIS;
    }
    
    @Override
    public String toString() {
        return "O_Q(\u03B6\u2081\u2082)";
    }

    @Override
    public String toASCIIString() {
        return "O_Q(zeta_12)";
    }

    @Override
    public String toTeXString() {
        if (preferenceForBlackboardBold) {
            return "\\mathcal O_{\\mathbb Q(\\zeta_{12})}";
        } else {
            return "\\mathcal O_{\\textbf Q(\\zeta_{12})}";
        }
    }

    @Override
    public String toHTMLString() {
        if (preferenceForBlackboardBold) {
            return "<i>O</i><sub>\u211A(&zeta;<sub>12</sub>)</sub>";
        } else {
            return "<i>O</i><sub><b>Q</b>(&zeta;<sub>12</sub>)</sub>";
        }
    }

    @Override
    public String toFilenameString() {
        return "OQ_ZETA12";
    }
    
}
