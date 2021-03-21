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
package algebraics.quadratics;

import algebraics.NotDivisibleException;

/**
 * A data structure for imaginary quadratic integers in arithmetic progression. 
 * Or more poetically, a line of imaginary quadratic integers on the complex 
 * plane.
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticIntegerLine {
    
    private final boolean inferredStepFlag;
    private final ImaginaryQuadraticInteger startingPoint, endingPoint, 
            lineStep;
    private final int lineLength;
    
    // STUB TO FAIL THE FIRST TEST
    public ImaginaryQuadraticInteger getStart() {
        return new ImaginaryQuadraticInteger(0, -1, 
                new ImaginaryQuadraticRing(-1));
    }
    
    // STUB TO FAIL THE FIRST TEST
    public ImaginaryQuadraticInteger getEnd() {
        return new ImaginaryQuadraticInteger(0, 1, 
                new ImaginaryQuadraticRing(-1));
    }
    
    // STUB TO FAIL THE FIRST TEST
    public ImaginaryQuadraticInteger getStep() {
        return new ImaginaryQuadraticInteger(-1, -1, 
                new ImaginaryQuadraticRing(-1));
    }
    
    /**
     * Tells how many imaginary quadratic integers there are in this line of 
     * imaginary quadratic integers. Guaranteed to always be positive.
     * @return The integer 1 if the starting point and ending point are the 
     * same, a greater integer if they differ. For example, if this line starts 
     * at 0 and ends at 19&radic;(&minus;3), this function will return 20.
     */
    public int length() {
        return this.lineLength;
    }

    /**
     * Retrieves a number from this line by its index.
     * @param index The index of the number to retrieve. Use 0 for the starting 
     * point, {@link #length()} &minus; 1 for the ending point, and index in 
     * between for the numbers in between. For example, if this line has twenty 
     * numbers, index 9 will return the tenth number.
     * @return The number at the specified index. For example, if this line 
     * starts at 0 and ends at 19 + 19<i>i</i>, this function will return 10 + 
     * 10<i>i</i> for index 9.
     * @throws IndexOutOfBoundsException If <code>index</code> is negative, or 
     * equal to or greater than {@link #length()}.
     */
    public ImaginaryQuadraticInteger apply(int index) {
        if (index < 0 || index >= this.lineLength) {
            String excMsg = "Index " + index 
                    + " is outside permissible range of indices 0 to " 
                    + (this.lineLength - 1);
            throw new IndexOutOfBoundsException(excMsg);
        }
        QuadraticInteger indexed 
                = this.lineStep.times(index).plus(this.startingPoint);
        return (ImaginaryQuadraticInteger) indexed;
    }
    
    // STUB TO FAIL THE FIRST TEST
    public ImaginaryQuadraticInteger get(int index) {
        return new ImaginaryQuadraticInteger(index, index, 
                new ImaginaryQuadraticRing(-1));
    }
    
    /**
     * Creates a new line with the same starting and ending points but a 
     * different step.
     * @param replacementStep The step to replace the step of this line. It 
     * actually may be equal to the current step, but then there's no point in 
     * passing it through this function. For example, 3<i>i</i>.
     * @return A new line with the specified step. For example, if this line 
     * starts at <i>i</i> and ends at 10<i>i</i> with a step of <i>i</i>, 
     * calling this function with a <code>replacementStep</code> of 3<i>i</i> 
     * will give a new line that also starts at <i>i</i> and ends at 10<i>i</i>, 
     * but with a step of 3<i>i</i>.
     * @throws algebraics.AlgebraicDegreeOverflowException If 
     * <code>replacementStep</code> is not from the same ring as the starting 
     * and ending points.
     * @throws IllegalArgumentException If the distance between the starting 
     * point and the ending point is not divisible by 
     * <code>replacementStep</code> (then this exception will wrap an instance 
     * of {@link algebraics.NotDivisibleException NotDivisibleException}) or if 
     * <code>replacementStep</code> goes in the wrong direction (for example if 
     * this line starts at <i>i</i> and ends at 10<i>i</i> but 
     * <code>replacementStep</code> is &minus;3<i>i</i> instead of 3<i>i</i>).
     * @throws NullPointerException If <code>replacementStep</code> is null.
     */
    public ImaginaryQuadraticIntegerLine 
        by(ImaginaryQuadraticInteger replacementStep) {
        return new ImaginaryQuadraticIntegerLine(this.startingPoint, 
                this.endingPoint, replacementStep);
    }
    
    public String toASCIIString() {
        String iqilStr = this.startingPoint.toASCIIString() + " to " 
                + this.endingPoint.toASCIIString();
        if (!this.inferredStepFlag) {
            iqilStr = iqilStr + " by " + this.lineStep.toASCIIString();
        }
        return iqilStr;
    }
    
    @Override
    public String toString() {
        String iqilStr = this.startingPoint.toString() + " to " 
                + this.endingPoint.toString();
        if (!this.inferredStepFlag) {
            iqilStr = iqilStr + " by " + this.lineStep.toString();
        }
        return iqilStr;
    }
    
    // TODO: Fill in example in the following Javadoc
    /**
     * Gives a hash code for this line. Not guaranteed to be unique, but I do 
     * believe it's unique enough for most purposes.
     * @return A hash code. [EXAMPLE GOES HERE]
     */
    @Override
    public int hashCode() {
        int hash = 127 * this.startingPoint.hashCode();
        hash += 31 * this.endingPoint.hashCode();
        hash += this.lineStep.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImaginaryQuadraticIntegerLine other 
                = (ImaginaryQuadraticIntegerLine) obj;
        if (!this.startingPoint.equals(other.startingPoint)) {
            return false;
        }
        if (!this.endingPoint.equals(other.endingPoint)) {
            return false;
        }
        return this.lineStep.equals(other.lineStep);
    }
    
    private static int determineLength(ImaginaryQuadraticInteger start, 
            ImaginaryQuadraticInteger end, ImaginaryQuadraticInteger step) {
        QuadraticInteger calcLen;
        try {
            calcLen = (end.minus(start)).divides(step);
        } catch (NotDivisibleException nde) {
            throw new IllegalArgumentException(nde);
        }
        if (calcLen.regPartMult < 0 || calcLen.surdPartMult != 0) {
        String excMsg = "Encountered problem calculating line length " 
                + calcLen.toASCIIString() + " for line from " 
                + start.toASCIIString() + " to " + end.toASCIIString();
        throw new IllegalArgumentException(excMsg);
        }
        return calcLen.regPartMult + 1;
    }
    
    /**
     * Auxiliary constructor. The step parameter is inferred using {@link 
     * ImaginaryQuadraticInteger#inferStep}.
     * @param start The imaginary quadratic integer to start with. For example, 
     * &radic;(&minus;2).
     * @param end The imaginary quadratic integer to end up on. Preferably not 
     * the same as <code>start</code>. For example, 160 + 21&radic;(&minus;2).
     * @throws algebraics.AlgebraicDegreeOverflowException If <code>start</code> 
     * and <code>end</code> come from different rings.
     * @throws IllegalArgumentException If the distance between 
     * <code>start</code> and <code>end</code> is not divisible by 
     * <code>step</code>. This exception will wrap an instance of {@link 
     * algebraics.NotDivisibleException NotDivisibleException}.
     * @throws NullPointerException If either <code>start</code> or 
     * <code>end</code> is null.
     */
    public ImaginaryQuadraticIntegerLine(ImaginaryQuadraticInteger start, 
            ImaginaryQuadraticInteger end) {
        this(start, end, ImaginaryQuadraticInteger.inferStep(start, end));
    }
    
    /**
     * Primary constructor. The starting point, ending point and step must all 
     * be explicitly specified. Use the auxiliary constructor if you'd prefer to 
     * have the step inferred.
     * @param start The imaginary quadratic integer to start with. For example, 
     * &radic;(&minus;2).
     * @param end The imaginary quadratic integer to end up on. Preferably not 
     * the same as <code>start</code>. For example, 160 + 21&radic;(&minus;2).
     * @param step The step by which to split the line into segments. It ought 
     * to be divisible by the distance between <code>start</code> and 
     * <code>end</code> and also such that adding it to <code>start</code> a 
     * number of times leads to <code>end</code>. Use the auxiliary constructor 
     * if you would prefer to have this parameter inferred. For example, 8 + 
     * &radic;(&minus;2).
     * @throws algebraics.AlgebraicDegreeOverflowException If 
     * <code>start</code>, <code>end</code> and <code>step</code> are not all 
     * from the same ring.
     * @throws IllegalArgumentException If the distance between 
     * <code>start</code> and <code>end</code> is not divisible by 
     * <code>step</code> (then this exception will wrap an instance of {@link 
     * algebraics.NotDivisibleException NotDivisibleException}) or if 
     * <code>step</code> goes in the wrong direction (for example if 
     * <code>start</code> is <i>i</i> and <code>end</code> is 10<i>i</i> but 
     * <code>step</code> is &minus;<i>i</i> instead of <i>i</i>).
     * @throws NullPointerException If <code>start</code>, <code>end</code> or 
     * <code>step</code> is null.
     */
    public ImaginaryQuadraticIntegerLine(ImaginaryQuadraticInteger start, 
            ImaginaryQuadraticInteger end, ImaginaryQuadraticInteger step) {
        if (start.equals(end)) {
            this.lineLength = 1;
            this.inferredStepFlag = true;
        } else {
            this.lineLength = determineLength(start, end, step);
            this.inferredStepFlag 
                    = step.equals(ImaginaryQuadraticInteger.inferStep(start, 
                            end));
        }
        this.startingPoint = start;
        this.endingPoint = end;
        this.lineStep = step;
     }
    
}
