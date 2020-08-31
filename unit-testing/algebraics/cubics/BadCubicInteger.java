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

/**
 * This is mainly to provide another way of testing circumstances under which 
 * {@link algebraics.UnsupportedNumberDomainException} should arise. I had been 
 * using {@link algebraics.quadratics.IllDefinedQuadraticInteger} for that 
 * purpose, but I've come to need something more generic.
 * @author Alonso del Arte
 */
public class BadCubicInteger extends CubicInteger {

    @Override
    public int algebraicDegree() {
        return -3;
    }

    @Override
    public long trace() {
        return -5;
    }

    @Override
    public long norm() {
        return -7;
    }

    @Override
    public long[] minPolynomialCoeffs() {
        long[] badCoeffs = {0, 0, 0};
        return badCoeffs;
    }

    @Override
    public String minPolynomialString() {
        return "x - x + x";
    }

    @Override
    public String minPolynomialStringTeX() {
        return "x + x - x";
    }

    @Override
    public String minPolynomialStringHTML() {
        return "&minus;x + x + x";
    }

    @Override
    public String toASCIIString() {
        return "Bad";
    }

    @Override
    public String toTeXString() {
        return "$Bad$";
    }

    @Override
    public String toHTMLString() {
        return "<i>Bad</i>";
    }

    @Override
    public double abs() {
        return -2;
    }

    @Override
    public double getRealPartNumeric() {
        return 0.0;
    }

    @Override
    public double getImagPartNumeric() {
        return -1.0;
    }

    @Override
    public double angle() {
        return 361.2;
    }
    
}
