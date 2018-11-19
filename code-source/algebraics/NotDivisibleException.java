/*
 * Copyright (C) 2018 Alonso del Arte
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

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

/**
 * An exception to indicate when the division of one algebraic integer by 
 * another algebraic integer results in an algebraic number that is in the 
 * relevant field but not the relevant ring. For example, sqrt(-2)/3 is in 
 * Q(sqrt(-2)) but not Z[sqrt(-2)].
 * <p>This is the wrong exception to throw for division by 0. Throwing this 
 * exception implies the result of a division is an algebraic number but not an 
 * algebraic integer. Whatever we think an algebraic integer divided by 0 is, it 
 * is neither an algebraic number nor an algebraic integer. Also, throwing this 
 * exception implies the result of a division can be rounded to an algebraic 
 * integer nearby in the relevant ring.</p>
 * <p>At some point in the future I want to make this exception usable for any 
 * implementation of {@link AlgebraicInteger}, not just {@link 
 * ImaginaryQuadraticInteger}.</p>
 * @author Alonso del Arte
 */
public class NotDivisibleException extends Exception {
    
    ////////////////////////////////////////////////////
    
    
    // UPDATE SERIAL VERSION UID
    // AFTER ALL THE TESTS ARE PASSING
    
    
    ///////////////////////////////////////////
    
    private static final long serialVersionUID = 1058394389;
    
    private final long[] numers;
    private final long[] denoms;
    
    private final IntegerRing initRing;
    
    private final double numericRealPart;
    private final double numericImagPart;
    
    /**
     * Gives the numerators of the resulting fraction.
     * @return The integers for the numerators that were supplied at the time 
     * the exception was constructed. For example, given 7/3 + 2 * sqrt(-5)/3, 
     * this might be {7, 2}.
     */
    public long[] getFractNumers() {
        return numers;
    }

    /**
     * Gives the denominator of the resulting fraction.
     * @return The integer supplied at the time the exception was constructed. 
     * It may be almost any integer, but most certainly it should not be 0. 
     * Actually, it should not be -1 nor 1 either, and if the ring has 
     * "half-integers," it should not be -2 nor 2. For example, given 7/3 + 2 * 
     * sqrt(-5)/3, this would be 3.
     */
    public long[] getFractDenoms() {
        return denoms;
    }
    
    /**
     * Gives the object for the ring that caused this exception.
     * @return The integer ring object supplied at the time the exception was 
     * constructed. 
     */
    public IntegerRing getRing() {
        return initRing;
    }
    
    /**
     * Gives a numeric approximation of the real part of the resulting fraction.
     * @return A double with a numeric approximation of the real part of the 
     * resulting fraction. For example, for 3/4 + 7sqrt(-2)/4, this would be 
     * 0.75.
     */
    public double getNumericRealPart() {
        return this.numericRealPart;
    }
    
    /**
     * Gives a numeric approximation of the imaginary part of the resulting 
     * fraction divided by the imaginary unit.
     * @return A double with a numeric approximation of the imaginary part of 
     * the resulting fraction divided by the imaginary unit. For example, for 
     * 3/4 + 7sqrt(-2)/4, this would be about 2.4748737341, which is 
     * approximately 7sqrt(2)/4. In the case of a real integer ring (one without 
     * complex numbers) this should always be 0.0.
     */
    public double getNumericImagPart() {
        return this.numericImagPart;
    }
    
    /**
     * Gets the algebraic integers which surround the algebraic number 
     * represented by ({@link #getResReFractNumer()} + 
     * {@link getResImFractNumer()} * sqrt({@link getResFractNegRad()})) / 
     * {@link getResFractDenom()}. WARNING: There is no overflow checking.
     * @return An array of algebraic integer objects Do not expect the integers 
     * to be in any particular order: I or anyone else working on this project 
     * in the future is free to change the implementation in the interest of 
     * efficiency. If you need the bounding integers in a specific order, sort 
     * the array returned by this function prior to the point where you need the 
     * contents to be in a specific order. For example, for 1/2 + <i>i</i>/2, 
     * this function should return {0, <i>i</i>, 1 + <i>i</i>, 1}, not 
     * necessarily in that order.
     */
    public AlgebraicInteger[] getBoundingIntegers() {
        if (this.initRing instanceof QuadraticRing) {
            QuadraticRing workingRing = (QuadraticRing) initRing;
            QuadraticInteger zeroQI = new ImaginaryQuadraticInteger(0, 0, new ImaginaryQuadraticRing(-1));
            double numerReg = 0.0;
            double numerSurd = 0.0;
            if (workingRing instanceof ImaginaryQuadraticRing || workingRing instanceof RealQuadraticRing) {
                int arrayLen = 0;
                if (workingRing instanceof ImaginaryQuadraticRing) {
                    arrayLen = 4;
                    zeroQI = new ImaginaryQuadraticInteger(0, 0, workingRing);
                    numerReg = this.numericRealPart;
                    numerSurd = this.numericImagPart;
                }
                if (workingRing instanceof RealQuadraticRing) {
                    arrayLen = 8;
                    zeroQI = new RealQuadraticInteger(0, 0, workingRing);
                    numerReg = (double) numers[0] / denoms[0];
                    numerSurd = (double) numers[1] / denoms[1];
                }
                AlgebraicInteger[] algIntArray = new AlgebraicInteger[arrayLen];
                for (int i = 0; i < arrayLen; i++) {
                    algIntArray[i] = zeroQI;
                }
                if (workingRing.hasHalfIntegers()) {
                    int topPointA, topPointB;
                    topPointA = (int) Math.ceil(numerReg * 2);
                    topPointB = (int) Math.ceil(numerSurd * 2);
                    if ((topPointA % 2 == 0 && topPointB % 2 != 0) || (topPointA % 2 != 0 && topPointB % 2 == 0)) {
                        topPointA--;
                    }
                    if (workingRing instanceof ImaginaryQuadraticRing) {
                        algIntArray[0] = new ImaginaryQuadraticInteger(topPointA, topPointB, workingRing, 2);
                        algIntArray[1] = new ImaginaryQuadraticInteger(topPointA - 1, topPointB - 1, workingRing, 2);
                        algIntArray[2] = new ImaginaryQuadraticInteger(topPointA + 1, topPointB - 1, workingRing, 2);
                        algIntArray[3] = new ImaginaryQuadraticInteger(topPointA, topPointB - 2, workingRing, 2);
                    } else {
                        algIntArray[0] = new RealQuadraticInteger(topPointA, topPointB, workingRing, 2);
                        algIntArray[1] = new RealQuadraticInteger(topPointA - 1, topPointB - 1, workingRing, 2);
                        algIntArray[2] = new RealQuadraticInteger(topPointA + 1, topPointB - 1, workingRing, 2);
                        algIntArray[3] = new RealQuadraticInteger(topPointA, topPointB - 2, workingRing, 2);
                    }
                } else {
                    int floorA, floorB, ceilA, ceilB;
                    floorA = (int) Math.floor(numerReg);
                    floorB = (int) Math.floor(numerSurd);
                    ceilA = (int) Math.ceil(numerReg);
                    ceilB = (int) Math.ceil(numerSurd);
                    if (workingRing instanceof ImaginaryQuadraticRing) {
                        algIntArray[0] = new ImaginaryQuadraticInteger(floorA, floorB, workingRing);
                        algIntArray[1] = new ImaginaryQuadraticInteger(ceilA, floorB, workingRing);
                        algIntArray[2] = new ImaginaryQuadraticInteger(floorA, ceilB, workingRing);
                        algIntArray[3] = new ImaginaryQuadraticInteger(ceilA, ceilB, workingRing);
                    } else {
                        algIntArray[0] = new RealQuadraticInteger(floorA, floorB, workingRing);
                        algIntArray[1] = new RealQuadraticInteger(ceilA, floorB, workingRing);
                        algIntArray[2] = new RealQuadraticInteger(floorA, ceilB, workingRing);
                        algIntArray[3] = new RealQuadraticInteger(ceilA, ceilB, workingRing);
                    }
                }
                return algIntArray;
            }
        }
        String exceptionMessage = "The domain " + this.initRing.toASCIIString() + " is not currently supported for this rounding operation.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this.initRing);
    }
    
    /**
     * Rounds the quadratic number to the quadratic integer in the relevant ring 
     * that is closest to 0. However, no guarantee is made as to which result 
     * will be returned if two potential results are the same distance from 0.
     * @return An AlgebraicInteger object representing the algebraic integer 
     * that is as close if not closer to 0 than the other integers bounding the 
     * algebraic number. For example, for 7/3 + 4 * sqrt(-5)/3 this would be 2 + 
     * sqrt(-5). Now, given, for example, 7/8 + 7i/8, no guarantee is made as to 
     * whether this function would return i or 1.
     */
    public AlgebraicInteger roundTowardsZero() {
        // STUB TO FAIL NEXT TEST // STUB TO FAIL NEXT TEST // STUB TO FAIL NEXT TEST
        return new ImaginaryQuadraticInteger(1, 0, new ImaginaryQuadraticRing(-1));
//        if (this.initRing instanceof ImaginaryQuadraticRing) {
//            QuadraticRing workingRing = (QuadraticRing) initRing;
//        
//        if (workingRing.hasHalfIntegers()) {
//            AlgebraicInteger[] bounds = this.getBoundingIntegers();
//            double currAbs = bounds[0].abs();
//            double closestSoFar = currAbs;
//            int currIndex = 1;
//            int bestIndex = 0;
//            while (currIndex < bounds.length) {
//                currAbs = bounds[currIndex].abs();
//                if (currAbs < closestSoFar) {
//                    closestSoFar = currAbs;
//                    bestIndex = currIndex;
//                }
//                currIndex++;
//            }
//            return bounds[bestIndex];
//        } else {
//            double intermediateRealPart = (double) resultingFractionRealPartNumerator / (double) resultingFractionDenominator;
//            double intermediateImagPart = (double) resultingFractionImagPartNumerator / (double) resultingFractionDenominator;
//            if (intermediateRealPart < 0) {
//                intermediateRealPart = Math.ceil(intermediateRealPart);
//            } else {
//                intermediateRealPart = Math.floor(intermediateRealPart);
//            }
//            if (intermediateImagPart < 0) {
//                intermediateImagPart = Math.ceil(intermediateImagPart);
//            } else {
//                intermediateImagPart = Math.floor(intermediateImagPart);
//            }
//            boolean overflowFlag = (intermediateRealPart < Integer.MIN_VALUE) || (intermediateRealPart > Integer.MAX_VALUE);
//            overflowFlag = overflowFlag || ((intermediateImagPart < Integer.MIN_VALUE) || (intermediateImagPart > Integer.MAX_VALUE));
//            if (overflowFlag) {
//                throw new ArithmeticException("Real part " + intermediateRealPart + ", imaginary part " + intermediateImagPart + " times sqrt" + resultingFractionNegRad + " is outside the range of this implmentation of ImaginaryQuadraticInteger, which uses 32-bit signed integers.");
//            }
//            return new ImaginaryQuadraticInteger((int) intermediateRealPart, (int) intermediateImagPart, initRing);
//        }
//        }
//        String exceptionMessage = "The domain " + this.initRing.toASCIIString() + " is not currently supported for this rounding operation.";
//        throw new UnsupportedNumberDomainException(exceptionMessage, this.initRing);
    }
    
    // TODO: Rewrite Javadoc
    public AlgebraicInteger roundAwayFromZero() {
        ImaginaryQuadraticInteger zeroIQI = new ImaginaryQuadraticInteger(0, 0, new ImaginaryQuadraticRing(-1));
        return zeroIQI;
//        if (workingRing.hasHalfIntegers()) {
//            ImaginaryQuadraticInteger[] bounds = this.getBoundingIntegers();
//            double currAbs = bounds[0].abs();
//            double farthestSoFar = currAbs;
//            int currIndex = 1;
//            int bestIndex = 0;
//            while (currIndex < bounds.length) {
//                currAbs = bounds[currIndex].abs();
//                if (currAbs > farthestSoFar) {
//                    farthestSoFar = currAbs;
//                    bestIndex = currIndex;
//                }
//                currIndex++;
//            }
//            return bounds[bestIndex];
//        } else {
//            double intermediateRealPart = (double) resultingFractionRealPartNumerator / (double) resultingFractionDenominator;
//            double intermediateImagPart = (double) resultingFractionImagPartNumerator / (double) resultingFractionDenominator;
//            if (intermediateRealPart < 0) {
//                intermediateRealPart = Math.floor(intermediateRealPart);
//            } else {
//                intermediateRealPart = Math.ceil(intermediateRealPart);
//            }
//            if (intermediateImagPart < 0) {
//                intermediateImagPart = Math.floor(intermediateImagPart);
//            } else {
//                intermediateImagPart = Math.ceil(intermediateImagPart);
//            }
//            boolean overflowFlag = (intermediateRealPart < Integer.MIN_VALUE) || (intermediateRealPart > Integer.MAX_VALUE);
//            overflowFlag = overflowFlag || ((intermediateImagPart < Integer.MIN_VALUE) || (intermediateImagPart > Integer.MAX_VALUE));
//            if (overflowFlag) {
//                throw new ArithmeticException("Real part " + intermediateRealPart + ", imaginary part " + intermediateImagPart + " times sqrt" + resultingFractionNegRad + " is outside the range of this implmentation of ImaginaryQuadraticInteger, which uses 32-bit signed integers.");
//            }
//            return new ImaginaryQuadraticInteger((int) intermediateRealPart, (int) intermediateImagPart, workingRing);
//        }
    }
    
    /**
     * This exception should be thrown when a division operation takes the 
     * resulting number out of the ring, to the larger field. If the result is 
     * an algebraic number of degree 4, perhaps AlgebraicDegreeOverflowException 
     * should be thrown instead. And if there is an attempt to divide by 0, the 
     * appropriate exception to throw would perhaps be IllegalArgumentException.
     * @param message A message to pass on to the Exception constructor.
     * @param numerators An array of numerators.
     * @param denominators An array of denominators. It should be the same 
     * length as the array of numerators. May contain negative numbers but it 
     * must not contain 0.
     * @param ring The ring of algebraic integers into which to round the 
     * algebraic number.
     * @throws IllegalArgumentException If any of these is true of the 
     * parameters: the array of numerators has more or fewer numbers than the 
     * array of denominators; the two arrays are of the same length but they 
     * have more numbers than the maximum algebraic degree of the ring (e.g., 
     * passing two arrays with fourteen numbers each for a cubic ring); any of 
     * the denominators is equal to 0.
     */
    public NotDivisibleException(String message, long[] numerators, long[] denominators, IntegerRing ring) {
        super(message);
        if (numerators.length != denominators.length) {
            String exceptionMessage = "Array of numerators has " + numerators.length + " numbers but array of denominators has " + denominators.length + " numbers.";
            throw new IllegalArgumentException(exceptionMessage);
        }
        if (numerators.length != ring.getMaxAlgebraicDegree()) {
            String exceptionMessage = "Numbers of class " + ring.getClass().getName() + " can have a maximum algebraic degree of " + ring.getMaxAlgebraicDegree() + " but an array of " + numerators.length + " was passed in.";
            throw new IllegalArgumentException(exceptionMessage);
        }
        for (long denom : denominators) {
            if (denom == 0) {
                String exceptionMessage = "Zero is not a valid denominator.";
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
        this.numers = numerators;
        this.denoms = denominators;
        double numRe = (double) this.numers[0] / this.denoms[0];
        double numIm = 0.0;
        if (ring instanceof ImaginaryQuadraticRing) {
            numIm = ((ImaginaryQuadraticRing) ring).getAbsNegRadSqrt() * this.numers[1] / this.denoms[1];
        }
        if (ring instanceof RealQuadraticRing) {
            numRe += ((RealQuadraticRing) ring).getRadSqrt() * this.numers[1] / this.denoms[1];
        }
        this.numericRealPart = numRe;
        this.numericImagPart = numIm;
        this.initRing = ring;
    }
}