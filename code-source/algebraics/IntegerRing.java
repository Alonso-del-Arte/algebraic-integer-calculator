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
public interface IntegerRing {
    
    /**
     * Gives the maximum algebraic degree an algebraic integer in the ring can have.
     * @return A positive integer, e.g., 3 in the case of a cubic integer ring.
     */
    public int getMaxAlgebraicDegree();
    
}
