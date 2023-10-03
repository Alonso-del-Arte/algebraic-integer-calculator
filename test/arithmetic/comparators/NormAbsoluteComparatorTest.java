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
package arithmetic.comparators;

import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the NormAbsoluteComparator class. These tests use no algebraic 
 * integers of algebraic degree higher than 2.
 * @author Alonso del Arte
 */
public class NormAbsoluteComparatorTest {
    
    /**
     * Test of the compare function, of the NormAbsoluteComparator class.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        int prospD = randomSquarefreeNumber(128);
        int d = (prospD == 1) ? 2 : prospD;
        QuadraticRing ring = new RealQuadraticRing(d);
        QuadraticInteger root = new RealQuadraticInteger(0, 1, ring);
        QuadraticInteger curr = new RealQuadraticInteger(1, 0, ring);
        int capacity = 4;
        List<QuadraticInteger> expected = new ArrayList<>(capacity);
        while (expected.size() < capacity) {
            expected.add(curr);
            curr = curr.times(root);
        }
        List<Long> expNorms = expected.stream().map(QuadraticInteger::norm)
                .collect(Collectors.toList());
        System.out.println("Expecting norms " + expNorms.toString());
        List<QuadraticInteger> actual = new ArrayList<>(expected);
        Collections.shuffle(actual);
        actual.sort(new NormComparator());
        assertEquals(expected, actual);
    }
    
}
