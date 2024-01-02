/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algebraics;

import arithmetic.PowerBasis;
import fractions.Fraction;

/**
 *
 * @author al
 */
public class IntegerRingTest {
    
    private static class MockRing implements IntegerRing {

        private static final Fraction[] DRAFT_POWER_BASIS_FRACTIONS 
                = {new Fraction(1, 2), new Fraction(1, 3)};

        @Override
        public int getMaxAlgebraicDegree() {
            return Integer.MIN_VALUE;
        }

        @Override
        public boolean isPurelyReal() {
            return false;
        }

        @Override
        public int discriminant() {
            return 0;
        }

        @Override
        public PowerBasis getPowerBasis() {
            return new PowerBasis(DRAFT_POWER_BASIS_FRACTIONS);
        }

        @Override
        public String toASCIIString() {
            return "For testing purposes only";
        }

        @Override
        public String toTeXString() {
            return "For testing purposes \\emph{only}";
        }

        @Override
        public String toHTMLString() {
            return "For testing purposes <em>only</em>";
        }

        @Override
        public String toFilenameString() {
            return "TEST.MOK";
        }
        
    }
    
}
