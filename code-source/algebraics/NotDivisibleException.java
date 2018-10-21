/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algebraicintegercalculator;

/**
 *
 * @author Alonso del Arte
 */
public class NotDivisibleException extends Exception {

    private static final long serialVersionUID = 1;
    private final int resultingFractionNumerator;
    private final int resultingFractionDenominator;
    private final int resultingFractionRadicand;
    
    /**
     * Gives the numerator of the resulting fraction.
     * @return The integer supplied at the time the exception was constructed.
     */
    public int getResFractNumer() {
        return resultingFractionNumerator;
    }
    
    /**
     * Gives the denominator of the resulting fraction.
     * @return The integer supplied at the time the exception was constructed. It may be almost any integer, but most certainly it should not be 0.
     */
    public int getResFractDenom() {
        return resultingFractionDenominator;
    }
    
    /**
     * Gives the negative integer in the radical in the numerator of the resulting fraction.
     * @return The integer supplied at the time the exception was constructed. It ought to be a negative, squarefree integer.
     */
    public int getResFractNegRad() {
        return resultingFractionRadicand;
    }

    /**
     * This exception should be thrown when a division operation takes the resulting number out of the ring, to the larger field.
     * If the result is an algebraic number of degree 4, perhaps AlgebraicDegreeOverflowException should be thrown instead.
     * And if there is an attempt to divide by 0, throw IllegalArgumentException.
     * @param message A message to pass on to the Exception constructor.
     * @param resFractNumer The numerator of the resulting fraction. For example, given 2 * sqrt(-5)/3, this parameter would be 2.
     * @param resFractDenom The denominator of the resulting fraction. For example, given 2 * sqrt(-5)/3, this parameter would be 3.
     * @param resFractRadicand The negative integer in the radical in the numerator of the resulting fraction. For example, given 2 * sqrt(-5)/3, this parameter would be -5.
     */
    public NotDivisibleException(String message, int resFractNumer, int resFractDenom, int resFractRadicand) {
        super(message);
        resultingFractionNumerator = resFractNumer;
        resultingFractionDenominator = resFractDenom;
        resultingFractionRadicand = resFractRadicand;
    }
    
}
