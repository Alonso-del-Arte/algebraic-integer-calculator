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
package algebraics.quartics;

import algebraics.IntegerRing;
import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 *
 * @author Alonso del Arte
 */
public class PureQuarticRing implements IntegerRing {

    // TODO: Write tests for this
    @Override
    public int getMaxAlgebraicDegree() {
        return -1;
    }

    // TODO: Write tests for this
    @Override
    public boolean isPurelyReal() {
        return false;
    }

    // TODO: Write tests for this
    @Override
    public int discriminant() {
        return -1;
    }

    // TODO: Write tests for this
    @Override
    public PowerBasis getPowerBasis() {
        Fraction fraction = new Fraction(-1);
        Fraction[] powerMultiplicands = {fraction};
        return new PowerBasis(powerMultiplicands);
    }

    // TODO: Write tests for this
    @Override
    public String toASCIIString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String toTeXString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String toHTMLString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String toFilenameString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
}
