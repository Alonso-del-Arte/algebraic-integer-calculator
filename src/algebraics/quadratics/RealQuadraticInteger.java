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
package algebraics.quadratics;

/**
 * Defines objects to represent real quadratic integers, for the most part 
 * symbolically rather than numerically. This class is <code>Comparable</code>, 
 * enabling sorting through <code>Collections.sort()</code> or 
 * <code>Arrays.sort()</code> in ascending order according to position on the 
 * real number line, without needing a <code>Comparator</code>.
 * @author Alonso del Arte
 */
public class RealQuadraticInteger extends QuadraticInteger 
        implements Comparable<RealQuadraticInteger> {
    
    private static final long serialVersionUID = 4547847540095073075L;
    
    private final double numVal;
    private final double absNumVal;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        RealQuadraticInteger other = (RealQuadraticInteger) obj;
        if (this.regPartMult != other.regPartMult) {
            return false;
        }
        if (this.surdPartMult != other.surdPartMult) {
            return false;
        }
        if (!this.quadRing.equals(other.quadRing)) {
            return false;
        }
        return this.denominator == other.denominator;
    }
    
    // TODO: Write tests for this
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public long norm() {
        return (long) this.regPartMult * this.regPartMult 
                - (long) this.surdPartMult * this.surdPartMult 
                * this.quadRing.radicand;
    }
    
    /**
     * Gives the absolute value of the numeric value of this real quadratic 
     * integer. In most cases it will be a rational approximation.
     * @return The absolute value as a double. For example, 1 &minus; &radic;2 
     * is approximately &minus;0.414213562373, so this would return roughly 
     * 0.414213562373 for that number.
     */
    @Override
    public double abs() {
        return this.absNumVal;
    }
    
    /**
     * Gets the real part of the real quadratic integer. Will be the same as 
     * {@link #abs()} if this number is positive or 0.
     * @return The real part of the real quadratic integer. For example, for 
     * &minus;1/2 + 9(&radic;13)/2, the result should be roughly 15.72498.
     */
    @Override
    public double getRealPartNumeric() {
        return this.numVal;
    }
    
    /**
     * Gets the imaginary part of the real quadratic integer. This should always 
     * be exactly 0.
     * @return The imaginary part of the real quadratic integer. For example, 
     * for &minus;1/2 + 9(&radic;13)/2, the result should be 0.0. Or indeed for 
     * any real quadratic integer.
     */
    @Override
    public double getImagPartNumeric() {
        return 0.0;
    }
    
    /**
     * Indicates whether the real part of this number, as given by {@link 
     * #getRealPartNumeric()}, is an approximation or not. It will generally be  
     * an approximation.
     * @return False if and only if the "surd" part is 0, otherwise true.
     */
    @Override
    public boolean isReApprox() {
        return this.surdPartMult != 0;
    }
    
    /**
     * Indicates whether the imaginary part of this number, as given by {@link 
     * #getImagPartNumeric()}, is an approximation or not.
     * @return Always false for a real quadratic integer, since the imaginary 
     * part, 0, can be represented precisely in floating point as 0.0.
     */
    @Override
    public boolean isImApprox() {
        return false;
    }
    
    /**
     * This function, generally called "argument," sometimes "phase" or 
     * "amplitude," is not so useful for purely real numbers like the ones 
     * represented by this class. It is included because it is required by the 
     * {@link AlgebraicInteger} interface, which has implementations that 
     * represent numbers with nonzero imaginary parts.
     * @return &pi; (approximately 3.14) radians if this real quadratic integer 
     * is negative, 0 otherwise.
     */
    @Override
    public double angle() {
        if (this.numVal < 0.0) {
            return Math.PI;
        } else {
            return 0.0;
        }
    }
    
    /**
     * Compares the number represented by this RealQuadraticInteger object with 
     * the number represented by the specified RealQuadraticInteger object for 
     * order. Returns a negative integer, zero, or a positive integer as this 
     * real quadratic integer is less than, equal to, or greater than the 
     * specified real quadratic integer. This enables sorting with {@link 
     * java.util.Collections#sort(java.util.List)} without need for a 
     * comparator. If you need to sort by norm, use {@link 
     * algebraics.NormAbsoluteComparator} or {@link algebraics.NormComparator}.
     * @param other The real quadratic integer to compare this real quadratic 
     * integer to. The comparison is more reliable if all involved real 
     * quadratic integers come from the same ring. Examples: &minus;&radic;21 
     * (roughly &minus;4.58257569), 4 + &radic;21 (roughly 8.58257569), 
     * &minus;3/2 + (13&radic;21)/2 (roughly 28.286742).
     * @return &minus;1 or any other negative integer if this real quadratic 
     * integer is less than the other real quadratic integer; 0 if this and the 
     * other real quadratic integers are equal; 1 or any positive integer if 
     * this real quadratic integer is greater than the other real quadratic 
     * integer. For example, if this real quadratic integer is 4 + &radic;21, 
     * then, compared to &minus;&radic;21, the result will most likely be 1; 
     * compared to 4 + &radic;21, the result must be 0; and compared to 
     * &minus;3/2 + (13&radic;21)/2, the result will most likely be &minus;1.
     */
    @Override
    public int compareTo(RealQuadraticInteger other) {
        double diffRe;
        if (this.quadRing.equals(other.quadRing)) {
            QuadraticInteger diff = this.minus(other);
            diffRe = diff.getRealPartNumeric();
        } else {
            diffRe = this.numVal - other.numVal;
        }
        if (diffRe < 0.0) {
            return -1;
        }
        if (diffRe > 0) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Creates a <code>RealQuadraticInteger</code> based on the parameters 
     * according to "theta" notation. Here &theta; = &phi; = 
     * <sup>1</sup>&frasl;<sub>2</sub> + <sup>&radic;5</sup>&frasl;<sub>2</sub>, 
     * the golden ratio. Thus the returned algebraic integer can also be 
     * instantiated as <code>new RealQuadraticInteger(2 * m + n, n, new 
     * RealQuadraticRing(5), 2)</code>.
     * @param m The "non-phi" part of the phi notation representation of the 
     * number. For example, &minus;2.
     * @param n The "phi" part of the phi notation representation of the number. 
     * For example, 1.
     * @return A <code>RealQuadraticInteger</code> object. For example 
     * &minus;<sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;5</sup>&frasl;<sub>2</sub> = &minus;2 + &phi;.
     */
    public static RealQuadraticInteger applyPhi(int m, int n) {
        return (RealQuadraticInteger) QuadraticInteger.applyTheta(m, n, 
                new RealQuadraticRing(5));
    }
    
    /**
     * Alternative constructor, may be used when the denominator is known to be 
     * 1. For example, this constructor may be used for 1 + &radic;5. For 1/2 + 
     * (&radic;5)/2, it will be necessary to use the primary constructor. One 
     * could always construct 1 + &radic;5 and then use {@link 
     * QuadraticInteger#divides(int)} with a divisor of 2, but that would 
     * probably be too circuitous in most cases.
     * @param a The "regular" part of the real quadratic integer. For example, 
     * for 5 + 4&radic;3, this parameter would be 5.
     * @param b The part to be multiplied by &radic;<i>d</i>. For example, for 5 
     * + 4&radic;3, this parameter would be 4.
     * @param ring The ring to which this algebraic integer belongs to. For 
     * example, for 5 + 4&radic;3, this parameter could be <code>new 
     * RealQuadraticRing(3)</code>.
     * @throws IllegalArgumentException If <code>ring</code> is not of the type 
     * {@link RealQuadraticRing}. This exception will occur even if 
     * <code>b</code> equals 0. There will be no quiet substitution of a real 
     * ring, unlike in an earlier version of this class.
     * @throws NullPointerException If <code>ring</code> is null.
     */
    public RealQuadraticInteger(int a, int b, QuadraticRing ring) {
        this(a, b, ring, 1);
    }
        
    /**
     * Primary constructor. If the denominator is known to be 1, the alternative 
     * constructor may be used.
     * @param a The "regular" part of the real quadratic integer, multiplied by 
     * 2 when applicable. For example, for 7/2 + (31&radic;5)/2, this parameter 
     * would be 7.
     * @param b The part to be multiplied by &radic;<i>d</i>, multiplied by 2 
     * when applicable. For example, for 7/2 + (31&radic;5)/2, this parameter 
     * would be 31.
     * @param ring The ring to which this algebraic integer belongs to. For 
     * example, for 7/2 + (31&radic;5)/2, this parameter could be <code>new 
     * RealQuadraticRing(5)</code>.
     * @param denom In most cases 1, but may be 2 if <code>a</code> and 
     * <code>b</code> have the same parity and 
     * <code>ring</code>{@link QuadraticRing#hasHalfIntegers() 
     * .hasHalfIntegers()} is true. If that is the case, &minus;2 may also be 
     * used, and &minus;1 can always be used; the constructor will quietly 
     * substitute 1 or 2 and multiply <code>a</code> and <code>b</code> by 
     * &minus;1.
     * @throws IllegalArgumentException If <code>ring</code> is not of the type 
     * {@link RealQuadraticRing}. This exception will occur even if 
     * <code>b</code> equals 0. There will be no quiet substitution of a real 
     * ring, unlike in an earlier version of this class.
     * @throws NullPointerException If <code>ring</code> is null.
     */
    public RealQuadraticInteger(int a, int b, QuadraticRing ring, int denom) {
        super(a, b, ring, denom);
        if (!(ring instanceof RealQuadraticRing)) {
            String excMsg = "Ring is not real as needed";
            throw new IllegalArgumentException(excMsg);
        }
        double preNumVal = this.quadRing.realRadSqrt * this.surdPartMult 
                + this.regPartMult;
        this.numVal = preNumVal / this.denominator;
        this.absNumVal = Math.abs(this.numVal);
    }
    
}
