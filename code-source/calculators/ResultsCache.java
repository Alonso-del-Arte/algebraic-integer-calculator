/*
 * Copyright (C) 2021 Alonso del Arte
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
package calculators;

import algebraics.AlgebraicInteger;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;

import static calculators.NumberTheoreticFunctionsCalculator.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Caches results pertaining to real quadratic rings that might be too expensive 
 * to recalculate. This is entirely for the benefit of {@link 
 * viewers.RealQuadRingDisplay}.
 * <p>I might generalize this to other kinds of rings. I'll cross that bridge 
 * when I get to it.</p>
 * @author Alonso del Arte
 */
public class ResultsCache {
    
    private final RealQuadraticRing cachedRing;
    
    private final Optional<RealQuadraticInteger> cachedUnit;
    
    private final Optional<Integer> cachedClassNumber;
    
    private final HashMap<RealQuadraticInteger, List<AlgebraicInteger>> factors 
            = new HashMap<>();
    
    private final HashMap<RealQuadraticInteger, RealQuadraticInteger> splitters 
            = new HashMap<>();
    
    // STUB TO FAIL THE FIRST TEST
    public Optional<RealQuadraticInteger> getUnit() {
        RealQuadraticRing ring = new RealQuadraticRing(Integer.MAX_VALUE);
        RealQuadraticInteger wrongNum 
                = new RealQuadraticInteger(Integer.MIN_VALUE, 0, ring);
        return Optional.of(wrongNum);// this.cachedUnit;
    }
    
    // STUB TO FAIL THE FIRST TEST
    public Optional<Integer> getClassNumber() {
        return Optional.of(Integer.MIN_VALUE);// this.cachedClassNumber;
    }
    
    private void cacheFactors(RealQuadraticInteger num) {
        if (!factors.containsKey(num)) {
            List<AlgebraicInteger> value = irreducibleFactors(num);
            factors.put(num, value);
        }
    }
    
    // STUB TO FAIL THE FIRST TEST
    public RealQuadraticInteger mainSplitter(RealQuadraticInteger num) {
        RealQuadraticRing ring = new RealQuadraticRing(Integer.MAX_VALUE);
        RealQuadraticInteger wrongNum 
                = new RealQuadraticInteger(Integer.MIN_VALUE, 0, ring);
        return wrongNum;
    }
    
    public ResultsCache(RealQuadraticRing ring) {
        this.cachedRing = ring;
        Optional<RealQuadraticInteger> unitHolder = Optional.empty();
        Optional<Integer> classNumberHolder = Optional.empty();
        try {
            AlgebraicInteger unit = fundamentalUnit(ring);
            unitHolder = Optional.of((RealQuadraticInteger) unit);
            int classNumber = fieldClassNumber(ring);
            classNumberHolder = Optional.of(classNumber);
        } catch (ArithmeticException ae) {
            System.err.println("Filling in empties for " + ring.toASCIIString() 
                    + " fundamental unit and/or field class number");
            System.err.println("\"" + ae.getMessage() + "\"");
        }
        this.cachedUnit = unitHolder;
        this.cachedClassNumber = classNumberHolder;
    }
    
}
