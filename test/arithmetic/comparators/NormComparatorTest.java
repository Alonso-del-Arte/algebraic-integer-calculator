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

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.QuadraticRing;
import static calculators.NumberTheoreticFunctionsCalculator
        .randomSquarefreeNumberMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test of the NormComparator class. This test class only tests the one abstract 
 * function of the Comparator interface.
 * @author Alonso del Arte
 */
public class NormComparatorTest {
    
    /**
     * Test of the compare function, of the NormComparator class.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        int d = -randomSquarefreeNumberMod(2, 4);
        QuadraticRing ring = new ImaginaryQuadraticRing(d);
        QuadraticInteger root = new ImaginaryQuadraticInteger(0, 1, ring);
        QuadraticInteger number = new ImaginaryQuadraticInteger(1, 1, ring);
        int capacity = 4;
        List<QuadraticInteger> expected = new ArrayList<>(capacity);
        while (expected.size() < capacity) {
            expected.add(number);
            number = number.times(root).plus(1);
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
