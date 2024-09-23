/*
 * Copyright (C) 2020 Alonso del Arte
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
import fractions.Fraction;

/**
 * This is mainly to provide another way of testing circumstances under which 
 * {@link algebraics.UnsupportedNumberDomainException} should arise. I had been 
 * using {@link algebraics.quadratics.IllDefinedQuadraticRing} for that purpose, 
 * but I've come to need something more generic.
 * @deprecated Marked for removal. Use <code>CubicRingTest.CubicRingImpl</code> 
 * or {@link algebraics.BadRing} instead.
 * @author Alonso del Arte
 */
@Deprecated
public class BadCubicRing extends CubicRing {
    
    private static final Fraction ONE_HALF = new Fraction(1, 2);
    
    private static final Fraction[] BAD_BASIS_FRACTIONS = {ONE_HALF, ONE_HALF, 
        ONE_HALF};
    
    private static final PowerBasis BAD_DEFAULT_BASIS 
            = new PowerBasis(BAD_BASIS_FRACTIONS);

    @Override
    public boolean isPurelyReal() {
        return true;
    }

    @Override
    public int discriminant() {
        return 7;
    }

    @Override
    public PowerBasis getPowerBasis() {
        return BAD_DEFAULT_BASIS;
    }
    
    @Override
    public String toString() {
        return "Bad Cubic Ring";
    }

    @Override
    public String toASCIIString() {
        return "Bad Cubic Ring";
    }

    @Override
    public String toTeXString() {
        return "{\\it Bad} Cubic Ring";
    }

    @Override
    public String toHTMLString() {
        return "<i>Bad</i> Cubic Ring";
    }

    @Override
    public String toFilenameString() {
        return "BADCUBICRING";
    }
    
}
