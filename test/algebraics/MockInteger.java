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
package algebraics;

/**
 *
 * @author Alonso del Arte
 */
public class MockInteger implements AlgebraicInteger {
    
    private final MockRing domain;

    @Override
    public int algebraicDegree() {
        return this.domain.getMaxAlgebraicDegree();
    }

    @Override
    public long trace() {
        return -1;
    }

    @Override
    public long norm() {
        return -1;
    }

    @Override
    public long[] minPolynomialCoeffs() {
        long[] array = {};
        return array;
    }

    @Override
    public String minPolynomialString() {
        return "NOT IMPLEMENTED YET";
    }

    @Override
    public String minPolynomialStringTeX() {
        return "NOT IMPLEMENTED YET";
    }

    @Override
    public String minPolynomialStringHTML() {
        return "NOT IMPLEMENTED YET";
    }

    @Override
    public MockRing getRing() {
        return this.domain;
    }

    @Override
    public String toASCIIString() {
        return "NOT IMPLEMENTED YET";
    }

    @Override
    public String toTeXString() {
        return "NOT IMPLEMENTED YET";
    }

    @Override
    public String toHTMLString() {
        return "NOT IMPLEMENTED YET";
    }

    @Override
    public double abs() {
        return -1;
    }

    @Override
    public double getRealPartNumeric() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getImagPartNumeric() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public boolean isReApprox() {
        return false;
    }

    @Override
    public boolean isImApprox() {
        return false;
    }

    @Override
    public double angle() {
        return Double.NEGATIVE_INFINITY;
    }

    public MockInteger(MockRing ring) {
        this.domain = ring;
    }
    
}
