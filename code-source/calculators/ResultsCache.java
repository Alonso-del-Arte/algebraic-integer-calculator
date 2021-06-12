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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Caches results pertaining to real quadratic rings that might be too expensive 
 * to recalculate. This is entirely for the benefit of {@link 
 * viewers.RealQuadRingDisplay}.
 * <p>I might generalize this to other kinds of rings. I'll cross that bridge 
 * when I get to it.</p>
 * @deprecated Will delete before Version 1.0.
 * @author Alonso del Arte
 */
@Deprecated
public class ResultsCache {
    
    private final RealQuadraticRing cachedRing;
    
    private final int radicand;
    
    private final boolean halfIntFlag;
    
    private final Optional<RealQuadraticInteger> cachedUnit;
    
    private final Optional<Integer> cachedClassNumber;
    
    private final HashMap<RealQuadraticInteger, List<AlgebraicInteger>> factors 
            = new HashMap<>();
    
    private final HashMap<Integer, RealQuadraticInteger> splitters 
            = new HashMap<>();
    
    public Optional<RealQuadraticInteger> getUnit() {
        return this.cachedUnit;
    }
    
    public Optional<Integer> getClassNumber() {
        return this.cachedClassNumber;
    }
    
    private RealQuadraticInteger cacheSplitter(int num) {
        RealQuadraticInteger number = new RealQuadraticInteger(num, 0, 
                this.cachedRing);
        List<AlgebraicInteger> list = irreducibleFactors(number);
        RealQuadraticInteger splitter 
                = (RealQuadraticInteger) list.get(list.size() - 1);
        this.splitters.put(num, splitter);
        return splitter;
    }
    
    private Optional<RealQuadraticInteger> wrap(long trialReg, int trialSurd, 
            int denom) {
        int reg = (int) Math.floor(Math.sqrt(trialReg));
        RealQuadraticInteger number = new RealQuadraticInteger(reg, trialSurd, 
                this.cachedRing, denom);
        return Optional.of(number);
    }
    
    private Optional<RealQuadraticInteger> lookForSplitterHalfInt(int num) {
        Optional<RealQuadraticInteger> option = Optional.empty();
        int quadrupled = 4 * num;
        int trialSurd = 1;
        long xd, trialReg;
        boolean notFound = true;
        while (notFound) {
            xd = trialSurd * trialSurd * this.radicand;
            trialReg = xd - quadrupled;
            if ((trialReg % 2 == xd % 2) && isPerfectSquare(trialReg)) {
                option = wrap(trialReg, trialSurd, 2);
                notFound = false;
            } else {
                trialReg = xd - num;
                if (isPerfectSquare(trialReg)) {
                    option = wrap(trialReg, trialSurd, 1);
                    notFound = false;
                } else {
                    trialReg = xd + quadrupled;
                    if ((trialReg % 2 == xd % 2) && isPerfectSquare(trialReg)) {
                        option = wrap(trialReg, trialSurd, 2);
                        notFound = false;
                    } else {
                        trialReg = xd + num;
                        if (isPerfectSquare(trialReg)) {
                            option = wrap(trialReg, trialSurd, 1);
                            notFound = false;
                        }
                    }
                }
            }
            trialSurd++;
        }
        return option;
    }
    
    private Optional<RealQuadraticInteger> lookForSplitter(int num) {
        Optional<RealQuadraticInteger> option = Optional.empty();
        int trialSurd = 1;
        long xd, trialReg;
        boolean notFound = true;
        while (notFound) {
            xd = trialSurd * trialSurd * this.radicand;
            trialReg = xd - num;
            if (isPerfectSquare(trialReg)) {
                option = wrap(trialReg, trialSurd, 1);
                notFound = false;
            } else {
                trialReg = xd + num;
                if (isPerfectSquare(trialReg)) {
                    option = wrap(trialReg, trialSurd, 1);
                    notFound = false;
                }
            }
            trialSurd++;
        }
        return option;
    }
    
    public Optional<RealQuadraticInteger> mainSplitter(int num) {
        if (this.halfIntFlag) {
            return lookForSplitterHalfInt(num);
        } else {
            return lookForSplitter(num);
        }
    }
    
    public ResultsCache(RealQuadraticRing ring) {
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
