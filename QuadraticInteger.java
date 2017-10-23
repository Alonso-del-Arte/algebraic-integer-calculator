/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algebraicintegercalculator;

/**
 *
 * @author Aonso del Arte
 */
public class QuadraticInteger implements AlgebraicInteger {

    protected int regPartMult;
    protected int surdPartMult;
    protected QuadraticRing quadRing;
    protected int denominator;
    
    /**
     * Gives the algebraic degree of the algebraic integer. Should not be higher than 2.
     * @return 0 if the algebraic integer is 0, 1 if it's a purely real integer, 2 otherwise.
     */
    @Override
    public int algebraicDegree() {
        if (surdPartMult == 0) {
            if (regPartMult == 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }
    
    /**
     * Calculates the trace of the imaginary quadratic integer (real part plus real integer times square root of a negative integer)
     * @return Twice the real part
     */
    @Override
    public int trace() {
        if (quadRing.d1mod4 && denominator == 2) {
            return regPartMult;
        } else {
            return 2 * regPartMult;
        }
    }
    
    /**
     * Calculates the norm of the imaginary quadratic integer (real part plus real integer times square root of a negative integer)
     * @return Square of the real part minus square of the imaginary part. May be 0 but never negative.
     */
    @Override
    public int norm() {
        int N;
        if (quadRing.d1mod4 && denominator == 2) {
            N = (regPartMult * regPartMult + quadRing.absRadicand * surdPartMult * surdPartMult)/4;
        } else {
            N = regPartMult * regPartMult + quadRing.absRadicand * surdPartMult * surdPartMult;
        }
        return N;
    }
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic integer
     * @return An array of three integers. If the algebraic integer is of degree 2, the array will be {norm, negative trace, 1}; if of degree 1, then {number, 1, 0}, and for 0, {0, 0, 0}.
     */
    @Override
    public int[] minPolynomial() {
        int[] coeffs = {0, 0, 0};
        switch (algebraicDegree()) {
            case 0: 
                break;
            case 1: 
                coeffs[0] = -1 * regPartMult;
                coeffs[1] = 1;
                break;
            case 2: 
                coeffs[0] = norm();
                coeffs[1] = -trace();
                coeffs[2] = 1;
                break;
        }
        return coeffs;
    }
    
    @Override
    public String minPolynomialString() {
        String polString = "";
        int[] polCoeffs = minPolynomial();
        switch (algebraicDegree()) {
            case 0:
                break;
            case 1:
                if (polCoeffs[0] < 0) {
                    polString = "x - " + ((-1) * polCoeffs[0]);
                } else {
                    polString = "x + " + polCoeffs[0];
                }
                break;
            case 2:
                polString = "x^2 ";
                if (polCoeffs[1] < -1) {
                    polString += ("- " + ((-1) * polCoeffs[1]) + "x ");
                }
                if (polCoeffs[1] == -1) {
                    polString += "- x ";
                }
                if (polCoeffs[1] == 1) {
                    polString += "+ x ";
                }
                if (polCoeffs[1] > 1) {
                    polString += ("+ " + polCoeffs[1] + "x ");
                }
                if (polCoeffs[0] < 0) {
                    polString += ("- " + ((-1) * polCoeffs[0]));
                } else {
                    polString += ("+ " + polCoeffs[0]);
                }
                break;
        }
        return polString;
    }
    
}
