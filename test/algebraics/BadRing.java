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
package algebraics;

import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 * A bad implementation to be used for testing purposes only. This class should 
 * not be available in any end user context. Use this for example in situations 
 * that should cause an {@link UnsupportedNumberDomainException} to occur, 
 * rather than {@link AlgebraicDegreeOverflowException}.
 * @author Alonso del Arte
 */
class BadRing implements IntegerRing {
    
    private static final Fraction ONE = new Fraction(1);
    
    private static final Fraction[] ONE_ARRAY = {ONE};
    
    private static final PowerBasis UNARY_POWER_BASIS 
            = new PowerBasis(ONE_ARRAY);
    
    @Override
    public int getMaxAlgebraicDegree() {
        return 1;
    }
    
    @Override
    public boolean isPurelyReal() {
        return false;
    }
    
    @Override
    public int discriminant() {
        return 1;
    }
    
    @Override
    public PowerBasis getPowerBasis() {
        return UNARY_POWER_BASIS;
    }
    
    @Override
    public String toString() {
        return "BAD RING";
    }
    
    @Override
    public String toASCIIString() {
        return this.toString();
    }
    
    @Override
    public String toTeXString() {
        return "\\textrm{Bad Ring}";
    }
    
    @Override

    public String toHTMLString() {
        return "<em>Bad Ring</em>";
    }
    
    @Override
    public String toFilenameString() {
        return "BAD_RING";
    }
    
    BadRing(int maxDegree) {
        //
    }
    
}
