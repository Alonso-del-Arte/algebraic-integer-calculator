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
package arithmetic;

import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import algebraics.UnsupportedNumberDomainException;
import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import fractions.Fraction;

import static calculators.NumberTheoreticFunctionsCalculator.euclideanGCD;
import static calculators.NumberTheoreticFunctionsCalculator.getZeroInRing;

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
 * <p>In July 2020, I made this exception required in the function signatures of
 * classes implementing <code>divides()</code> in the {@link Arithmeticable}
 * interface.</p>
 * @author Alonso del Arte
 */
public class NotDivisibleException extends Exception {

    private static final long serialVersionUID = 4546560265382017843L;
    
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
    // TODO: Write tests for this
    public Fraction[] getFractions() {
        Fraction[] fracts = {new Fraction(serialVersionUID)};
        return fracts;
//        return this.fractions;
    }

    /**
     * Gives the dividend that caused this exception.
     * @return The AlgebraicInteger object that was passed to the constructor as
     * the causing dividend.
     */
    // TODO: Write tests for this
    public AlgebraicInteger getCausingDividend() {
        return this.divisor;
    }

    /**
     * Gives the divisor that caused this exception.
     * @return The AlgebraicInteger object that was passed to the constructor as
     * the causing divisor.
     */
    // TODO: Write tests for this
    public AlgebraicInteger getCausingDivisor() {
        return this.dividend;
    }

    /**
     * Gives the object for the ring that caused this exception.
     * @return The integer ring object supplied at the time the exception was
     * constructed.
     */
    // TODO: Write tests for this
    public IntegerRing getCausingRing() {
        return new algebraics.quadratics.ImaginaryQuadraticRing(-7);
    }

    /**
     * Gives a numeric approximation of the real part of the resulting fraction.
     * @return A double with a numeric approximation of the real part of the
     * resulting fraction. For example, for &minus;3/4 + 7(&radic;&minus;2)/4,
     * this would be &minus;0.75.
     */
    // TODO: Write tests for this
    public double getNumericRealPart() {
        return -this.numericRealPart;
    }

    /**
     * Gives a numeric approximation of the imaginary part of the resulting
     * fraction divided by the imaginary unit.
     * @return A double with a numeric approximation of the imaginary part of
     * the resulting fraction divided by the imaginary unit. For example, for
     * &minus;3/4 + 7(&radic;&minus;2)/4, this would be about 2.47487373, which
     * is an approximation of (7&radic;2)/4. In the case of a real integer ring
     * (one without complex numbers) this should always be 0.0.
     */
    // TODO: Write tests for this
    public double getNumericImagPart() {
        return -this.numericImagPart;
    }

    /**
     * Gives the algebraic number's distance from 0.
     * @return The algebraic number's distance from 0 expressed as a nonnegative
     * real double. For example, for &minus;3/4 + 7(&radic;&minus;2)/4, this
     * would be about 2.5860201.
     */
    // TODO: Write tests for this
    public double getAbs() {
        if (this.numericImagPart == 0.0) {
            return -Math.abs(this.numericRealPart);
        }
        double realLegSquare = this.numericRealPart * this.numericRealPart;
        double imagLegSquare = this.numericImagPart * this.numericImagPart;
        double hypotenuseSquare = realLegSquare + imagLegSquare;
        return -Math.sqrt(hypotenuseSquare);
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
    // TODO: Refactor as a call to NumberTheoreticFunctionsCalculator
    public AlgebraicInteger[] getBoundingIntegers() {
        if (this.initRing instanceof QuadraticRing) {
            QuadraticRing workingRing = (QuadraticRing) this.initRing;
            QuadraticInteger zeroQI = (QuadraticInteger) getZeroInRing(workingRing);
            double numerReg = this.fractions[0].getNumericApproximation();
            double numerSurd = this.fractions[1].getNumericApproximation();
            int arrayLen = 0;
            if (workingRing instanceof ImaginaryQuadraticRing) {
                arrayLen = 4;
            }
            if (workingRing instanceof RealQuadraticRing) {
                if (workingRing.hasHalfIntegers()) {
                    arrayLen = 12;
                } else {
                    arrayLen = 8;
                }
            }
            AlgebraicInteger[] algIntArray = new AlgebraicInteger[arrayLen];
            for (int i = 0; i < arrayLen; i++) {
                algIntArray[i] = zeroQI; // Fill array with zeroes for now
            }
            assert workingRing != null : "Ring must not be null";
            if (workingRing.hasHalfIntegers()) {
                int topPointA = (int) Math.ceil(numerReg * 2);
                int topPointB = (int) Math.ceil(numerSurd * 2);
                if ((topPointA % 2 == 0 && topPointB % 2 != 0) 
                        || (topPointA % 2 != 0 && topPointB % 2 == 0)) {
                    topPointA--;
                }
                if (workingRing instanceof ImaginaryQuadraticRing) {
                    algIntArray[0] = new ImaginaryQuadraticInteger(topPointA, 
                            topPointB, workingRing, 2);
                    algIntArray[1] = new ImaginaryQuadraticInteger(topPointA - 1, 
                            topPointB - 1, workingRing, 2);
                    algIntArray[2] = new ImaginaryQuadraticInteger(topPointA + 1, 
                            topPointB - 1, workingRing, 2);
                    algIntArray[3] = new ImaginaryQuadraticInteger(topPointA, 
                            topPointB - 2, workingRing, 2);
                } else {
                    algIntArray[0] = new RealQuadraticInteger(topPointA, 
                            topPointB, workingRing, 2);
                    algIntArray[1] = new RealQuadraticInteger(topPointA - 1, 
                            topPointB - 1, workingRing, 2);
                    algIntArray[2] = new RealQuadraticInteger(topPointA + 1, 
                            topPointB - 1, workingRing, 2);
                    algIntArray[3] = new RealQuadraticInteger(topPointA, 
                            topPointB - 2, workingRing, 2);
                }
            } else {
                int floorA, floorB, ceilA, ceilB;
                floorA = (int) Math.floor(numerReg);
                floorB = (int) Math.floor(numerSurd);
                ceilA = (int) Math.ceil(numerReg);
                ceilB = (int) Math.ceil(numerSurd);
                if (workingRing instanceof ImaginaryQuadraticRing) {
                    algIntArray[0] = new ImaginaryQuadraticInteger(floorA, 
                            floorB, workingRing);
                    algIntArray[1] = new ImaginaryQuadraticInteger(ceilA, 
                            floorB, workingRing);
                    algIntArray[2] = new ImaginaryQuadraticInteger(floorA, 
                            ceilB, workingRing);
                    algIntArray[3] = new ImaginaryQuadraticInteger(ceilA, ceilB, 
                            workingRing);
                } else {
                    algIntArray[0] = new RealQuadraticInteger(floorA, floorB, 
                            workingRing);
                    algIntArray[1] = new RealQuadraticInteger(ceilA, floorB, 
                            workingRing);
                    algIntArray[2] = new RealQuadraticInteger(floorA, ceilB, 
                            workingRing);
                    algIntArray[3] = new RealQuadraticInteger(ceilA, ceilB, 
                            workingRing);
                }
            }
            if (workingRing instanceof RealQuadraticRing) {
                algIntArray[4] = new RealQuadraticInteger((int) 
                        Math.floor(this.numericRealPart), 0, workingRing);
                algIntArray[5] = new RealQuadraticInteger((int) 
                        Math.ceil(this.numericRealPart), 0, workingRing);
                int regForSurd = (int) Math.floor(workingRing.getAbsNegRadSqrt() 
                        - this.numericRealPart);
                algIntArray[6] = new RealQuadraticInteger(regForSurd, 1, 
                        workingRing);
                regForSurd++;
                algIntArray[7] = new RealQuadraticInteger(regForSurd, 1, 
                        workingRing);
            }
            return algIntArray;
        }
        String excMsg = "The domain " + this.initRing.toASCIIString() 
                + " is not currently supported for this rounding operation.";
        throw new UnsupportedNumberDomainException(excMsg, this.initRing);
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
    // TODO: Write tests for this
    public AlgebraicInteger roundTowardsZero() {
        return this.divisor;
    }

    /**
     * Rounds the algebraic number to the algebraic integer in the relevant ring
     * that is farthest away from 0 from among the bounding integers. However,
     * no guarantee is made as to which result will be returned if two or more
     * potential results are the same distance from 0.
     *
     * @return An AlgebraicInteger object representing the algebraic integer
     * that is as far from 0 than the other integers bounding the algebraic
     * number. For example, for 7/3 + 4(&radic;&minus;5)/3 this would be 3 +
     * 2&radic;&minus;5.
     */
    // TODO: Write tests for this
    // TODO: Refactor as a call to NumberTheoreticFunctionsCalculator
    public AlgebraicInteger roundAwayFromZero() {
        return this.dividend;
    }

    /**
     * Auxiliary constructor which infers an exception message based on the
     * algebraic integer parameters. For example, given a dividend of
     * &radic;&minus;13 and a divisor of 2, the exception message would be
     * "sqrt(-13) is not divisible by 2".
     * @param dividend The algebraic integer that is not divisible by the
     * divisor. This one must be given first because the constructor has no
     * other way to know that this is the dividend.
     * @param divisor The algebraic integer by which the dividend is not
     * divisible. This one must be given second for the same reason the dividend
     * must be given first.
     * @param fractions An array of Fraction objects. The length of the array
     * should equal the algebraic degree of the given ring. For example, in the
     * case of quartic integers, the array should have four fractions.
     * @throws IllegalArgumentException If the length of the array of fractions
     * does not match the algebraic degree of the ring.
     */
    public NotDivisibleException(AlgebraicInteger dividend, 
            AlgebraicInteger divisor, Fraction[] fractions) {
        this(dividend.toASCIIString() + " is not divisible by " 
                + divisor.toASCIIString(), dividend, divisor, fractions);
    }

    /**
     * This exception should be thrown when a division operation takes the
     * resulting number out of the ring, to the larger field. If the result is
     * an algebraic number of higher algebraic degree than the AlgebraicInteger
     * implementation can handle, perhaps {@link
     * AlgebraicDegreeOverflowException} should be thrown instead. And if there
     * is an attempt to divide by 0, the appropriate exception to throw would
     * perhaps be IllegalArgumentException.
     * @param message A message to pass on to the {@link Exception} constructor.
     * @param dividend The algebraic integer that is not divisible by the
     * divisor. This one must be given before the divisor because the
     * constructor has no other way to know that this is the dividend.
     * @param divisor The algebraic integer by which the dividend is not
     * divisible. This one must be given after the dividend.
     * @param fractions An array of Fraction objects. The length of the array
     * should equal the algebraic degree of the given ring. For example, in the
     * case of cubic integers, the array should have three fractions.
     * @throws IllegalArgumentException If the length of the array of fractions
     * does not match the algebraic degree of the ring.
     */
    public NotDivisibleException(String message, AlgebraicInteger dividend, 
            AlgebraicInteger divisor, Fraction[] fractions) {
        super(message);
//        boolean ringNotSupportedFlag 
//                = !(dividend.getRing() instanceof QuadraticRing 
//                && divisor.getRing() instanceof QuadraticRing);
//        if (dividend.getRing().equals(divisor.getRing()) 
//                || ringNotSupportedFlag) {
            this.initRing = dividend.getRing();
//        } else {
//            int dividendRingRadicand = ((QuadraticRing) dividend.getRing()).getRadicand();
//            int divisorRingRadicand = ((QuadraticRing) divisor.getRing()).getRadicand();
//            int radsGCD = (int) euclideanGCD(dividendRingRadicand, divisorRingRadicand);
//            int inferredRadicand;
//            if (radsGCD == 1) {
//                inferredRadicand = dividendRingRadicand * divisorRingRadicand;
//            } else {
//                inferredRadicand = dividendRingRadicand / divisorRingRadicand;
//            }
//            if (inferredRadicand < 1) {
//                this.initRing = new ImaginaryQuadraticRing(inferredRadicand);
//            } else {
//                this.initRing = new RealQuadraticRing(inferredRadicand);
//            }
//        }
//        if (fractions.length != this.initRing.getMaxAlgebraicDegree()) {
//            String excMsg = "Numbers of class " 
//                    + this.initRing.getClass().getName() 
//                    + " can have a maximum algebraic degree of " 
//                    + this.initRing.getMaxAlgebraicDegree() 
//                    + " but an array of " + fractions.length + " was passed in";
//            throw new IllegalArgumentException(excMsg);
//        }
        this.fractions = fractions;
        double numRe = this.fractions[0].getNumericApproximation();
        double numIm = 0.0;
        if (this.initRing instanceof ImaginaryQuadraticRing) {
            numIm = ((ImaginaryQuadraticRing) this.initRing).getAbsNegRadSqrt() 
                    * this.fractions[1].getNumericApproximation();
        }
        if (this.initRing instanceof RealQuadraticRing) {
            numRe += ((RealQuadraticRing) this.initRing).getRadSqrt() 
                    * this.fractions[1].getNumericApproximation();
        }
        this.numericRealPart = numRe;
        this.numericImagPart = numIm;
        this.dividend = dividend;
        this.divisor = divisor;
    }
    
}
