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
 * Represents the ring of integers of degree 1. Essentially this is another 
 * wrapper for <code>int</code> primitives.
 * @author Alonso del Arte
 */
class UnaryRing implements IntegerRing {
    
    public static final UnaryRing Z = new UnaryRing();
    
    private static final Fraction[] DRAFT_POWER_BASIS_FRACTIONS 
            = {new Fraction(1, 2), new Fraction(1, 3)};

    @Override
    public int getMaxAlgebraicDegree() {
        return 1;
    }

    @Override
    public boolean isPurelyReal() {
        return true;
    }

    @Override
    public int discriminant() {
        return 0;
    }

    @Override
    public PowerBasis getPowerBasis() {
        return new PowerBasis(DRAFT_POWER_BASIS_FRACTIONS);
    }

    @Override
    public String toASCIIString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    @Override
    public String toTeXString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    @Override
    public String toHTMLString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    @Override
    public String toFilenameString() {
        return "NOT_IMPL.YET";
    }
    
    private UnaryRing() {
    }
    
}
