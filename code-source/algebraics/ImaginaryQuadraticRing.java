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
public class ImaginaryQuadraticRing extends QuadraticRing {

    /**
     * A convenient holder for the absolute value of radicand
     */
    protected int absRadicand;
    
    /**
     * A convenient holder for the square root of the absRadicand
     */
    protected double absRadicandSqrt;
   
    public ImaginaryQuadraticRing(int d) {
        //
        if (d > -1) {
            throw new IllegalArgumentException("Negative integer required for parameter d.");
        }
        if (!NumberTheoreticFunctionsCalculator.isSquareFree(d)) {
            throw new IllegalArgumentException("Squarefree integer required for parameter d.");
        }
        this.d1mod4 = (d % 4 == -3);
        this.radicand = d;
        this.absRadicand = Math.abs(radicand);
        this.absRadicandSqrt = Math.sqrt(absRadicand);
    }
    
}
