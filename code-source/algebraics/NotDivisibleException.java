/*
 * Copyright (C) 2019 Alonso del Arte
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
import fractions.Fraction;

/**
 * An exception to indicate when the division of one algebraic integer by 
 * another algebraic integer results in an algebraic number that is in the 
 * relevant field but not the relevant ring. For example, (&radic;&minus;2)/3 is 
 * in <b>Q</b>(&radic;&minus;2) but not <b>Z</b>[&radic;&minus;2].
 * <p>This is the wrong exception to throw for division by 0. Throwing this 
 * exception implies the result of a division is an algebraic number but not an 
 * algebraic integer. Whatever we think an algebraic integer divided by 0 is, it 
 * is neither an algebraic number nor an algebraic integer. Also, throwing this 
 * exception implies the result of a division can be rounded to an algebraic 
 * integer nearby in the relevant ring.</p>
 * <p>At some point in the future I want to make this exception usable for any 
 * implementation of {@link AlgebraicInteger}, not just {@link 
 * algebraics.quadratics.QuadraticInteger}.</p>
 * @author Alonso del Arte
 */
public class NotDivisibleException extends Exception {
    
    private static final long serialVersionUID = 1058578553;
    
    private final Fraction[] fractions;
    
    private final AlgebraicInteger dividend;
    private final AlgebraicInteger divisor;
    
    private final IntegerRing initRing;
    
    private final double numericRealPart;
    private final double numericImagPart;
    
    /**
     * Gives the fractions with which this exception was constructed.
     * @return The array of Fraction objects that was supplied to the exception 
     * constructor.
     */
    public Fraction[] getFractions() {
        return this.fractions;
    }
    
    /**
     * Gives the numbers that caused this exception. In the original version of 
     * this exception, the exception constructor just took the detail message 
     * and fractions. In 2018 this was changed so that the constructor takes in 
     * two AlgebraicInteger objects and an {@link IntegerRing} object in 
     * addition to an array of fractions.
     * @return An array of two AlgebraicInteger objects. It is assumed that the 
     * first is the dividend and the second is the divisor, and that they are 
     * both from the same ring passed to the exception constructor.
     */
    public AlgebraicInteger[] getCausingNumbers() {
        AlgebraicInteger[] numbers = {this.dividend, this.divisor};
        return numbers;
    }
    
    /**
     * Gives the object for the ring that caused this exception.
     * @return The integer ring object supplied at the time the exception was 
     * constructed. 
     */
    public IntegerRing getCausingRing() {
        return this.initRing;
    }
    
    /**
     * Gives a numeric approximation of the real part of the resulting fraction.
     * @return A double with a numeric approximation of the real part of the 
     * resulting fraction. For example, for 3/4 + 7(&radic;&minus;2)/4, this 
     * would be 0.75.
     */
    public double getNumericRealPart() {
        return this.numericRealPart;
    }
    
    /**
     * Gives a numeric approximation of the imaginary part of the resulting 
     * fraction divided by the imaginary unit.
     * @return A double with a numeric approximation of the imaginary part of 
     * the resulting fraction divided by the imaginary unit. For example, for 
     * 3/4 + 7(&radic;&minus;2)/4, this would be about 2.4748737341, which is 
     * approximately 7(&radic;&minus;2)/4. In the case of a real integer ring 
     * (one without complex numbers) this should always be 0.0.
     */
    public double getNumericImagPart() {
        return this.numericImagPart;
    }
    
    /**
     * Gets the algebraic integers which surround the algebraic number 
     * represented by the fractions that were passed to the constructor. 
     * WARNING: There is no overflow checking.
     * @return An array of algebraic integer objects. Do not expect the integers 
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
            double numerReg = this.fractions[0].getNumericApproximation();
            double numerSurd = this.fractions[1].getNumericApproximation();
            if (workingRing instanceof ImaginaryQuadraticRing || workingRing instanceof RealQuadraticRing) {
                int arrayLen = 0;
                if (workingRing instanceof ImaginaryQuadraticRing) {
                    arrayLen = 4;
                    zeroQI = new ImaginaryQuadraticInteger(0, 0, workingRing);
                }
                if (workingRing instanceof RealQuadraticRing) {
                    if (workingRing.hasHalfIntegers()) {
                        arrayLen = 12;
                    } else {
                        arrayLen = 8;
                    }
                    zeroQI = new RealQuadraticInteger(0, 0, workingRing);
                }
                AlgebraicInteger[] algIntArray = new AlgebraicInteger[arrayLen];
                for (int i = 0; i < arrayLen; i++) {
                    algIntArray[i] = zeroQI; // Fill array with zeroes for now
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
                if (workingRing instanceof RealQuadraticRing) {
                    algIntArray[4] = new RealQuadraticInteger((int) Math.floor(this.numericRealPart), 0, workingRing);
                    algIntArray[5] = new RealQuadraticInteger((int) Math.ceil(this.numericRealPart), 0, workingRing);
                    int regForSurd = (int) Math.floor(workingRing.getRadSqrt() - this.numericRealPart);
                    algIntArray[6] = new RealQuadraticInteger(regForSurd, 1, workingRing);
                    regForSurd++;
                    algIntArray[7] = new RealQuadraticInteger(regForSurd, 1, workingRing);
                }
                return algIntArray;
            }
        }
        String exceptionMessage = "The domain " + this.initRing.toASCIIString() + " is not currently supported for this rounding operation.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this.initRing);
    }
    
    /**
     * Rounds the algebraic number to the algebraic integer in the relevant ring 
     * that is closest to 0 from among the bounding integers. However, no 
     * guarantee is made as to which result will be returned if two or more 
     * potential results are the same distance from 0.
     * @return An AlgebraicInteger object representing the algebraic integer 
     * that is as close if not closer to 0 than the other integers bounding the 
     * algebraic number. For example, for 7/3 + 4(&radic;&minus;5)/3 this would 
     * be 2 + &radic;&minus;5. Now, given, for example, 7/8 + 7<i>i</i>/8, no 
     * guarantee is made as to whether this function would return <i>i</i> or 1.
     */
    public AlgebraicInteger roundTowardsZero() {
        if (this.initRing instanceof QuadraticRing) {
            QuadraticRing workingRing = (QuadraticRing) initRing;
            if (workingRing instanceof ImaginaryQuadraticRing && workingRing.hasHalfIntegers()) {
                AlgebraicInteger[] bounds = this.getBoundingIntegers();
                double currAbs = bounds[0].abs();
                double closestSoFar = currAbs;
                int currIndex = 1;
                int bestIndex = 0;
                while (currIndex < bounds.length) {
                    currAbs = bounds[currIndex].abs();
                    if (currAbs < closestSoFar) {
                        closestSoFar = currAbs;
                        bestIndex = currIndex;
                    }
                    currIndex++;
                }
                return bounds[bestIndex];
            }
            double intermediateRegPart = this.fractions[0].getNumericApproximation();
            double intermediateSurdPart = this.fractions[1].getNumericApproximation();
            if (intermediateRegPart < 0) {
                intermediateRegPart = Math.ceil(intermediateRegPart);
            } else {
                intermediateRegPart = Math.floor(intermediateRegPart);
            }
            if (intermediateSurdPart < 0) {
                intermediateSurdPart = Math.ceil(intermediateSurdPart);
            } else {
                intermediateSurdPart = Math.floor(intermediateSurdPart);
            }
            boolean overflowFlag = (intermediateRegPart < Integer.MIN_VALUE) || (intermediateRegPart > Integer.MAX_VALUE);
            overflowFlag = overflowFlag || ((intermediateSurdPart < Integer.MIN_VALUE) || (intermediateSurdPart > Integer.MAX_VALUE));
            if (overflowFlag) {
                throw new ArithmeticException("Real part " + intermediateRegPart + ", imaginary part " + intermediateSurdPart + " times sqrt(" + workingRing.getRadicand() + ") is outside the range of this implementation of ImaginaryQuadraticInteger, which uses 32-bit signed integers.");
            }
            if (workingRing instanceof ImaginaryQuadraticRing) {
                return new ImaginaryQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, workingRing);
            }
            if (workingRing instanceof RealQuadraticRing) {
                return new RealQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, workingRing);
            }
        }
        String exceptionMessage = "The domain " + this.initRing.toASCIIString() + " is not currently supported for this rounding operation.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this.initRing);
    }
    
    /**
     * Rounds the algebraic number to the algebraic integer in the relevant ring 
     * that is farthest away from 0 from among the bounding integers. However, no guarantee is made as to which result 
     * will be returned if two or more potential results are the same distance 
     * from 0.
     * @return An AlgebraicInteger object representing the algebraic integer 
     * that is as far from 0 than the other integers bounding the 
     * algebraic number. For example, for 7/3 + 4(&radic;&minus;5)/3 this would 
     * be 3 + 2&radic;&minus;5.
     */
    public AlgebraicInteger roundAwayFromZero() {
        if (this.initRing instanceof QuadraticRing) {
            QuadraticRing workingRing = (QuadraticRing) initRing;
            if (workingRing instanceof ImaginaryQuadraticRing && workingRing.hasHalfIntegers()) {
                AlgebraicInteger[] bounds = this.getBoundingIntegers();
                double currAbs = bounds[0].abs();
                double farthestSoFar = currAbs;
                int currIndex = 1;
                int bestIndex = 0;
                while (currIndex < bounds.length) {
                    currAbs = bounds[currIndex].abs();
                    if (currAbs > farthestSoFar) {
                        farthestSoFar = currAbs;
                        bestIndex = currIndex;
                    }
                    currIndex++;
                }
                return bounds[bestIndex];
            }
            double intermediateRegPart = this.fractions[0].getNumericApproximation();
            double intermediateSurdPart = this.fractions[1].getNumericApproximation();
            if (intermediateRegPart < 0) {
                intermediateRegPart = Math.floor(intermediateRegPart);
            } else {
                intermediateRegPart = Math.ceil(intermediateRegPart);
            }
            if (intermediateSurdPart < 0) {
                intermediateSurdPart = Math.floor(intermediateSurdPart);
            } else {
                intermediateSurdPart = Math.ceil(intermediateSurdPart);
            }
            boolean overflowFlag = (intermediateRegPart < Integer.MIN_VALUE) || (intermediateRegPart > Integer.MAX_VALUE);
            overflowFlag = overflowFlag || ((intermediateSurdPart < Integer.MIN_VALUE) || (intermediateSurdPart > Integer.MAX_VALUE));
            if (overflowFlag) {
                throw new ArithmeticException("Real part " + intermediateRegPart + ", imaginary part " + intermediateSurdPart + " times sqrt(" + workingRing.getRadicand() + ") is outside the range of this implementation of ImaginaryQuadraticInteger, which uses 32-bit signed integers.");
            }
            if (workingRing instanceof ImaginaryQuadraticRing) {
                return new ImaginaryQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, workingRing);
            }
            if (workingRing instanceof RealQuadraticRing) {
                return new RealQuadraticInteger((int) intermediateRegPart, (int) intermediateSurdPart, workingRing);
            }
        }
        String exceptionMessage = "The domain " + this.initRing.toASCIIString() + " is not currently supported for this rounding operation.";
        throw new UnsupportedNumberDomainException(exceptionMessage, this.initRing);
    }
    
    /**
     * This exception should be thrown when a division operation takes the 
     * resulting number out of the ring, to the larger field. If the result is 
     * an algebraic number of higher algebraic degree than the AlgebraicInteger 
     * implementation can handle, perhaps {@link 
     * AlgebraicDegreeOverflowException} should be thrown instead. And if there 
     * is an attempt to divide by 0, the appropriate exception to throw would 
     * perhaps be {@link IllegalArgumentException}.
     * @param message A message to pass on to the {@link Exception} constructor.
     * @param dividend The algebraic integer that is not divisible by the 
     * divisor. This one must be given first because the constructor has no 
     * other way to know that this is the dividend.
     * @param divisor The algebraic integer by which the dividend is not 
     * divisible. This one must be given second for the same reason the dividend 
     * must be given first.
     * @param fractions An array of Fraction objects. The length of the array 
     * should equal the algebraic degree of the given ring. For example, in the 
     * case of cubic integers, the array should have three fractions.
     * @param ring The ring of algebraic integers into which to round the 
     * algebraic number.
     * @throws IllegalArgumentException If the length of the array of fractions 
     * does not match the algebraic degree of the ring.
     */
    public NotDivisibleException(String message, AlgebraicInteger dividend, AlgebraicInteger divisor, Fraction[] fractions, IntegerRing ring) {
        super(message);
        if (fractions.length != ring.getMaxAlgebraicDegree()) {
            String exceptionMessage = "Numbers of class " + ring.getClass().getName() + " can have a maximum algebraic degree of " + ring.getMaxAlgebraicDegree() + " but an array of " + fractions.length + " was passed in.";
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.fractions = fractions;
        double numRe = this.fractions[0].getNumericApproximation();
        double numIm = 0.0;
        if (ring instanceof ImaginaryQuadraticRing) {
            numIm = ((ImaginaryQuadraticRing) ring).getAbsNegRadSqrt() * this.fractions[1].getNumericApproximation();
        }
        if (ring instanceof RealQuadraticRing) {
            numRe += ((RealQuadraticRing) ring).getRadSqrt() * this.fractions[1].getNumericApproximation();
        }
        this.numericRealPart = numRe;
        this.numericImagPart = numIm;
        this.dividend = dividend;
        this.divisor = divisor;
        this.initRing = ring;
    }
}