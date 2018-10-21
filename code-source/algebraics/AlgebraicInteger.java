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
public interface AlgebraicInteger {

    /**
     * Gives the algebraic degree of the algebraic integer
     * @return 0 if the algebraic integer is 0, a positive integer for any other algebraic integer
     */
    int algebraicDegree();
    
    /**
     * Gives the trace of the algebraic integer
     * @return The trace
     */
    int trace();
    
    /**
     * Gives the norm of the algebraic integer, useful for comparing
     * @return The norm
     */
    int norm();
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic integer
     * @return An array of integers, in total one more than the algebraic degree
     */
    int[] minPolynomial();
    
    /**
     * Gives the minimal polynomial formatted as a string, e.g., "x^3 + x^2 - x + 1"
     * @return A string in which the variable x appears as many times as dictated by the algebraic degree
     */
    String minPolynomialString();
    
}
