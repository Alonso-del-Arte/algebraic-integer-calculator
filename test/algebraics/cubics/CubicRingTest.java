/*
 * Copyright (C) 2023 Alonso del Arte
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
package algebraics.cubics;

import arithmetic.PowerBasis;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CubicInteger class.
 * @author Alonso del Arte
 */
public class CubicRingTest {
    
    /**
     * Test of preferBlackboardBold method, of class CubicRing.
     */
    @Test
    public void testPreferBlackboardBold_0args() {
        System.out.println("preferBlackboardBold");
        boolean expResult = false;
        boolean result = CubicRing.preferBlackboardBold();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of preferBlackboardBold method, of class CubicRing.
     */
    @Test
    public void testPreferBlackboardBold_boolean() {
        System.out.println("preferBlackboardBold");
        boolean preferenceForBB = false;
        CubicRing.preferBlackboardBold(preferenceForBB);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxAlgebraicDegree method, of class CubicRing.
     */
    @Test
    public void testGetMaxAlgebraicDegree() {
        System.out.println("getMaxAlgebraicDegree");
        CubicRing instance = new CubicRingImpl();
        int expResult = 0;
        int result = instance.getMaxAlgebraicDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    class CubicRingImpl extends CubicRing {

        @Override
        public boolean isPurelyReal() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public int discriminant() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public PowerBasis getPowerBasis() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toASCIIString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toTeXString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toHTMLString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public String toFilenameString() {
            throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
}
