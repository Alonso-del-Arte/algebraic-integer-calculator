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
package fractions;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a periodic continued fraction.
 * @author Alonso del Arte
 */
public class PeriodicContinuedFraction extends ContinuedFraction {

    // STUB TO FAIL THE FIRST TEST
    @Override
    public String toHTMLString() {
        return "Sorry, not implemented yet";
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public String toTeXString() {
        return "Sorry, not implemented yet";
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public List<BigFraction> convergents() {
        Fraction oneHalf = new Fraction(1, 2);
        ArrayList<BigFraction> list = new ArrayList<>();
        BigFraction fraction = new BigFraction(oneHalf);
        list.add(fraction);
        return list;
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public Fraction approximation() {
        return new Fraction(-7, 8);
    }
    
}
