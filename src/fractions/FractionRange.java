/*
 * Copyright (C) 2020 Alonso del Arte
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
package fractions;

/**
 * Represents an arithmetic progression of fractions.
 * @author Alonso del Arte
 */
public class FractionRange {
    
    private final Fraction begin, finish, interval;
    
    private final boolean easyInferStepFlag;
    
    private final int size;
    
    @Override
    public String toString() {
        String str = this.begin.toString() + " to " + this.finish.toString();
        if (this.easyInferStepFlag) {
            return str;
        } else {
            return str + " by " + this.interval;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass().equals(obj.getClass());
    }
    
    // STUB TO FAIL THE FIRST TEST
    @Override
    public int hashCode() {
        return 0;
    }
    
    public static Fraction inferStep(Fraction start, Fraction end) {
        Fraction diff = end.minus(start);
        return new Fraction(Long.signum(diff.getNumerator()), 
                diff.getDenominator());
    }

    public int length() {
        return this.size;
    }
    
    // STUB TO FAIL THE FIRST TEST
    public Fraction apply(int index) {
        return new Fraction(0);
    }
    
    public FractionRange(Fraction start, Fraction end) {
        this(start, end, inferStep(start, end), true);
    }

    public FractionRange(Fraction start, Fraction end, Fraction step) {
        this(start, end, step, false);
    }
    
    private FractionRange(Fraction start, Fraction end, Fraction step, 
            boolean validated) {
        if (!validated) {
            Fraction span = end.minus(start);
            Fraction check = span.dividedBy(step);
            if (!check.isInteger()) {
                String msg = step.toString() + " is not valid for range from " 
                        + start.toString() + " to " + end.toString();
                throw new IllegalArgumentException(msg);
            }
        }
        this.begin = start;
        this.finish = end;
        this.interval = step;
        this.easyInferStepFlag = (this.begin.getDenominator() 
                == this.interval.getDenominator())
                && (this.finish.getDenominator() 
                == this.interval.getDenominator());
        this.size = (int) 
                this.finish.minus(this.begin).dividedBy(this.interval).getNumerator() 
                + 1;
    }
        
}
