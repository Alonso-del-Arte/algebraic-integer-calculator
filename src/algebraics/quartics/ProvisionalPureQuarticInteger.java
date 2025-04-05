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

import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;

/**
 *
 * @author Alonso del Arte
 */
// TODO: Mark as deprecated once PureQuarticInteger is available
public class ProvisionalPureQuarticInteger implements AlgebraicInteger {
    
    // TODO: Write tests for this
    @Override
    public int algebraicDegree() {
        return -1;
    }

    // TODO: Write tests for this
    @Override
    public long trace() {
        return -1;
    }

    // TODO: Write tests for this
    @Override
    public long norm() {
        return -1;
    }

    // TODO: Write tests for this
    @Override
    public long[] minPolynomialCoeffs() {
        long[] coeffs = {-1, 0};
        return coeffs;
    }

    // TODO: Write tests for this
    @Override
    public String minPolynomialString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String minPolynomialStringTeX() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public String minPolynomialStringHTML() {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    @Override
    public IntegerRing getRing() {
        return new PureQuarticRing();
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
    public double abs() {
        return -1.0;
    }

    // TODO: Write tests for this
    @Override
    public double getRealPartNumeric() {
        return -1.0;
    }

    // TODO: Write tests for this
    @Override
    public double getImagPartNumeric() {
        return -1.0;
    }

    // TODO: Write tests for this
    @Override
    public boolean isReApprox() {
        return false;
    }

    // TODO: Write tests for this
    @Override
    public boolean isImApprox() {
        return false;
    }

    // TODO: Write tests for this
    @Override
    public double angle() {
        return -1.0;
    }
    
}
