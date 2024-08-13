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
package algebraics.cubics;

import arithmetic.PowerBasis;
import static calculators.NumberTheoreticFunctionsCalculator.isCubefree;
import fractions.Fraction;

/**
 * Represents the ring of integers of a pure cubic ring.
 * @author Alonso del Arte
 */
public class PureCubicRing extends CubicRing {
    
    final int radicand;

    // TODO: Write tests for this
    @Override
    public boolean isPurelyReal() {
        return false;
    }

    // TODO: Write tests for this
    @Override
    public int discriminant() {
        return 0;
    }

    // TODO: Write tests for this
    @Override
    public PowerBasis getPowerBasis() {
        Fraction fraction = new Fraction(1, 3);
        Fraction[] array = {fraction};
        return new PowerBasis(array);
    }
    
    @Override
    public String toString() {
        return "Z[\u221B" + this.radicand + "]";
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
        return "NOT_IMPL.YET";
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
