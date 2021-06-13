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

import static calculators.NumberTheoreticFunctionsCalculator.fieldClassNumber;
import static calculators.NumberTheoreticFunctionsCalculator.fundamentalUnit;
import static calculators.NumberTheoreticFunctionsCalculator.isPerfectSquare;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * Defines a grouping of results about a ring of quadratic integers.
 * @author Alonso del Arte
 */
public final class RealQuadResultsGrouping 
        extends ResultsGrouping<RealQuadraticInteger> {
    
    /**
     * The default maximum "surd" part for which to search for splitters. If for 
     * a given number there is a splitter but its "surd" part is greater than 
     * this value, and the cache's threshold hasn't been raised from this 
     * initial value, the list of splitters might have an empty 
     * <code>Optional</code> for the corresponding prime.
     */
    public static final int DEFAULT_SURD_PART_SEARCH_THRESHOLD = 4800;
    
    private int surdPartSearchThreshold;
    
    private final RealQuadraticRing cachedRing;
    
    private final int radicand;
    
    private final boolean halfIntFlag;
    
    private final Optional<RealQuadraticInteger> cachedUnit;
    
    private final Optional<Integer> cachedClassNumber;
    
    private Optional<RealQuadraticInteger> wrap(long trialReg, int trialSurd, 
            int denom) {
        int reg = (int) Math.floor(Math.sqrt(trialReg));
        RealQuadraticInteger number = new RealQuadraticInteger(reg, trialSurd, 
                this.cachedRing, denom);
        return Optional.of(number);
    }
    
    private Optional<RealQuadraticInteger> lookForSplitter(int num, int denom) {
        Optional<RealQuadraticInteger> option = Optional.empty();
        int trialSurd = 1;
        long xd, trialReg;
        boolean notFound = true;
        while (notFound && trialSurd < this.surdPartSearchThreshold) {
            xd = trialSurd * trialSurd * this.radicand;
            trialReg = xd - num;
            if (isPerfectSquare(trialReg)) {
                option = wrap(trialReg, trialSurd, denom);
                notFound = false;
            } else {
                trialReg = xd + num;
                if (isPerfectSquare(trialReg)) {
                    option = wrap(trialReg, trialSurd, denom);
                    notFound = false;
                }
            }
            trialSurd++;
        }
        return option;
    }
    
    private Optional<RealQuadraticInteger> findSplitter(int num) {
        if (this.halfIntFlag) {
            return lookForSplitter(4 * num, 2);
        } else {
            return lookForSplitter(num, 1);
        }
    }
    
    @Override
    public HashSet<RealQuadraticInteger> inerts() {
        return new HashSet(this.inertialPrimes);
    }

    @Override
    public HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> 
        splits() {
            return new HashMap<>(this.splitPrimes);
    }

    @Override
    public HashMap<RealQuadraticInteger, Optional<RealQuadraticInteger>> 
        ramifieds() {
            return new HashMap<>(this.ramifiedPrimes);
    }
    
    public RealQuadResultsGrouping(RealQuadraticRing ring) {
        super(ring);
        this.cachedRing = ring;
        this.radicand = this.cachedRing.getRadicand();
        this.halfIntFlag = this.cachedRing.hasHalfIntegers();
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
