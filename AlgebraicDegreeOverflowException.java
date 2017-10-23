/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algebraicintegercalculator;

/**
 * An exception to indicate when the result of an arithmetical operation results in an algebraic integer of higher algebraic degree than the implementation of AlgebraicInteger was designed for.
 * @author Alonso del Arte
 */
public class AlgebraicDegreeOverflowException extends Exception {
    
    private static final long serialVersionUID = 1;
    private final int maxExpectedAlgebraicDegree;
    private final int necessaryAlgebraicDegree;
    
    /**
     * Tells what is the maximum algebraic degree the function that threw the exception was expecting.
     * @return An integer greater than 1 but less than the necessary algebraic degree. For example, this would be 2 if thrown by ImaginaryQuadraticInteger.plus().
     */
    public int getMaxExpectedAlgebraicDegree() {
        return maxExpectedAlgebraicDegree;
    }
    
    /**
     * Tells what is the algebraic degree an object implementing AlgebraicInteger would need to handle to properly represent the result of the operation.
     * @return An integer greater than the expected algebraic degree. For example, this could be 4 if thrown by ImaginaryQuadraticInteger.plus().
     */
    public int getNecessaryAlgebraicDegree() {
        return necessaryAlgebraicDegree;
    }
    
    /**
     * This exception should be thrown when the result of an arithmetic operation on two objects implementing the AlgebraicInteger interface results in an algebraic integer of higher algebraic degree than either object can represent.
     * For instance, if the multiplication of two quadratic integers represented by the ImaginaryQuadraticInteger class results in an algebraic integer of degree 4, it would be appropriate to throw this exception.
     * @param message A message to pass on to the Exception constructor.
     * @param maxDegree The maximum algebraic degree the object can handle (e.g., 2 in the case of ImaginaryQuadraticInteger).
     * @param necessaryDegree The algebraic degree the object should be capable of handling to properly represent the result (e.g., 4 in the case of two quadratic integers that multiply to an algebraic integer of degree 4).
     */
    public AlgebraicDegreeOverflowException(String message, int maxDegree, int necessaryDegree) {
        
        super(message);
        maxExpectedAlgebraicDegree = maxDegree;
        necessaryAlgebraicDegree = necessaryDegree;
        
    }
    
}