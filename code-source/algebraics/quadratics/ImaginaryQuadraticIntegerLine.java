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

import java.util.Objects;

/**
 *
 * @author Alonso del Arte
 */
public class ImaginaryQuadraticIntegerLine {
    
    private final boolean inferredStepFlag;
    private final ImaginaryQuadraticInteger startingPoint, endingPoint, lineStep;
    private final int lineLength;
    
    public int length() {
        return this.lineLength;
    }

    public ImaginaryQuadraticInteger apply(int index) {
//        if (index < 0) {
//            String excMsg = "Negative index " + index + " is not allowed";
//            throw new IndexOutOfBoundsException(excMsg);
//        }
        if (index >= this.lineLength) {
            String excMsg = "Index " + index + " beyond last allowed index " 
                    + (this.lineLength - 1) + " is not allowed";
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
    
    public ImaginaryQuadraticIntegerLine 
        by(ImaginaryQuadraticInteger replacementStep) {
        return new ImaginaryQuadraticIntegerLine(this.startingPoint, 
                this.endingPoint, this.lineStep);// replacementStep);
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
    
    @Override
    public int hashCode() {
        int hash = this.startingPoint.hashCode() + this.endingPoint.hashCode();
        hash *= this.lineStep.hashCode();
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
    
    public ImaginaryQuadraticIntegerLine(ImaginaryQuadraticInteger start, 
            ImaginaryQuadraticInteger end) {
        this(start, end, ImaginaryQuadraticInteger.inferStep(start, end));
    }
    
    public ImaginaryQuadraticIntegerLine(ImaginaryQuadraticInteger start, 
            ImaginaryQuadraticInteger end, ImaginaryQuadraticInteger step) {
        this.startingPoint = start;
        this.endingPoint = end;
        this.lineStep = step;
        this.inferredStepFlag 
                = this.lineStep.equals(ImaginaryQuadraticInteger
                        .inferStep(this.startingPoint, this.endingPoint));
        QuadraticInteger calcLen;
        if (this.startingPoint.equals(this.endingPoint)) {
            this.lineLength = 1;
        } else {
            try {
                calcLen = (this.endingPoint.minus(this.startingPoint))
                        .divides(this.lineStep);
            } catch (NotDivisibleException nde) {
                throw new IllegalArgumentException(nde);
            }
            // The following is not passing the test
            if (!this.inferredStepFlag) {
                QuadraticInteger stepSum = calcLen.times(this.lineStep);
                QuadraticInteger stepCheck = this.startingPoint.plus(stepSum);
                if (!stepCheck.equals(this.endingPoint)) {
                    String excMsg = "Step " + this.lineStep.toASCIIString() 
                            + " is wrong, it ends on " 
                            + stepCheck.toASCIIString() + " not " 
                            + this.endingPoint.toASCIIString();
                    throw new IllegalArgumentException(excMsg);
                }
            }
            this.lineLength = calcLen.regPartMult + 1;
        }
     }
    
}
