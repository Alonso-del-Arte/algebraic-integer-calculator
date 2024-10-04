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

import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 * A mock implementation to be used for testing purposes only. This class should 
 * not be available in any end user context. Use this for example in situations 
 * that should cause an {@link UnsupportedNumberDomainException} to occur, 
 * rather than {@link AlgebraicDegreeOverflowException}.
 * @author Alonso del Arte
 */
public class MockRing implements IntegerRing {
    
    private static final Fraction ONE = new Fraction(1);
    
    private static final Fraction[] ONE_ARRAY = {ONE};
    
    private static final PowerBasis UNARY_POWER_BASIS 
            = new PowerBasis(ONE_ARRAY);
    
    private final int maximumDegree;
    
    private final boolean onlyReals;
    
    @Override
    public int getMaxAlgebraicDegree() {
        return this.maximumDegree;
    }
    
    @Override
    public boolean isPurelyReal() {
        return this.onlyReals;
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
        return "Mock Ring of Degree " + this.maximumDegree;
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
    
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Auxiliary constructor. Use this constructor when it's not necessary to 
     * specify maximum degree nor whether or not the ring includes imaginary 
     * numbers.
     */
    public MockRing() {
        this(1, false);
    }
    
    /**
     * Auxiliary constructor. Use this constructor when it's necessary to 
     * specify maximum degree but not whether or not the ring includes imaginary 
     * numbers.
     * @param maxDegree The maximum degree. For example, 3.
     */
    public MockRing(int maxDegree) {
        this(maxDegree, false);
    }
    
    /**
     * Primary constructor. Use this constructor when it's necessary to specify 
     * that an example ring is not purely real.
     * @param maxDegree The maximum degree. For example, 5.
     * @param includeImaginary Whether or not the example ring contains 
     * imaginary numbers. For example, true.
     */
    public MockRing(int maxDegree, boolean includeImaginary) {
        this.maximumDegree = maxDegree;
        this.onlyReals = !includeImaginary;
    }
    
}
