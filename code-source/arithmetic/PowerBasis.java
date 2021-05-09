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
package arithmetic;

import fractions.Fraction;

import java.util.Arrays;

/**
 * Holds together three arrays of fractions to describe the power basis of a 
 * ring of algebraic integers.
 * @author Alonso del Arte
 */
public class PowerBasis {
    
    private static final Fraction ZERO_FRACT = new Fraction(0);
    
    private final int deg;
    
    private final Fraction[] powMults, powAddAdjMults, addAdjs;
    
    @Override
    public String toString() {
        String intermediate = this.toASCIIString();
        intermediate = intermediate.replace("^2", "\u00B2");
        intermediate = intermediate.replace("^3", "\u00B3");
        intermediate = intermediate.replace("^4", "\u00B4");
        return intermediate;
    }
    
    public String toASCIIString() {
        String str = "1";
        for (int i = 1; i < this.deg; i++) {
            str = str + ", " + this.powMults[i].toString() 
                    + "a^" + i;
            if (!this.powAddAdjMults[i].equals(ZERO_FRACT)) {
                str = str + " + " 
                        + this.powAddAdjMults[i].toString() + "a ";
            }
        }
        str = str.replace("a^1 ", "a ");
        str = str.replace("a^1,", "a,");
        if (str.endsWith("^1")) {
            str = str.substring(0, str.length() - 2);
        }
        str = str.replace(" 1a", " a");
        str = str.replace("+ -", "- ");
        return str;
    }
    
    // STUB TO FAIL THE TEST
    public String toTeXString() {
        return "Not implemented yet";
    }
    
    // STUB TO FAIL THE TEST
    public String toHTMLString() {
        return "Not implemented yet";
    }
    
    public int getDegree() {
        return this.deg;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        int currPow = 1;
        for (int i = 0; i < this.deg; i++) {
            hash *= this.powMults[i].hashCode() * currPow;
            hash += this.powAddAdjMults[i].hashCode() * currPow;
            hash += this.addAdjs[i].hashCode();
            currPow *= 128;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PowerBasis other = (PowerBasis) obj;
        if (this.deg != other.deg) {
            return false;
        }
        if (!Arrays.deepEquals(this.powMults, other.powMults)) {
            return false;
        }
        if (!Arrays.deepEquals(this.powAddAdjMults, other.powAddAdjMults)) {
            return false;
        }
        return Arrays.deepEquals(this.addAdjs, other.addAdjs);
    }
    
    public Fraction[] getPowerMultiplicands() {
        return Arrays.copyOf(this.powMults, this.powMults.length);
    }
    
    public Fraction[] getPowerMultiplicandAdditiveAdjustments() {
        return Arrays.copyOf(this.powAddAdjMults, this.powAddAdjMults.length);
    }
    
    public Fraction[] getAdditiveAdjustments() {
        return Arrays.copyOf(this.addAdjs, this.addAdjs.length);
    }
    
    public PowerBasis(Fraction[] powerMultiplicands) {
        this(powerMultiplicands, null, null);
    }
    
    public PowerBasis(Fraction[] powerMultiplicands, 
            Fraction[] additiveAdjustments) {
        this(powerMultiplicands, null, additiveAdjustments);
    }
    
    public PowerBasis(Fraction[] powerMultiplicands, 
            Fraction[] powerMultiplicandAdditiveAdjustments, 
            Fraction[] additiveAdjustments) {
        this.deg = powerMultiplicands.length;
        this.powMults = Arrays.copyOf(powerMultiplicands, this.deg);
        if (powerMultiplicandAdditiveAdjustments == null) {
            Fraction[] adjsArr = new Fraction[this.deg];
            Arrays.fill(adjsArr, ZERO_FRACT);
            this.powAddAdjMults = adjsArr;
        } else {
            if (powerMultiplicandAdditiveAdjustments.length != this.deg) {
                String excMsg = "Power multiplicand adjustments array should be " 
                        + this.deg + " elements long, not " 
                        + powerMultiplicandAdditiveAdjustments.length;
                throw new IllegalArgumentException(excMsg);
            }
            this.powAddAdjMults 
                    = Arrays.copyOf(powerMultiplicandAdditiveAdjustments, 
                            this.deg);
        }
        if (additiveAdjustments == null) {
            Fraction[] adjsArr = new Fraction[this.deg];
            Arrays.fill(adjsArr, ZERO_FRACT);
            this.addAdjs = adjsArr;
        } else {
            if (additiveAdjustments.length != this.deg) {
                String excMsg = "Additive adjustments array should be " 
                        + this.deg + " elements long, not " 
                        + additiveAdjustments.length;
                throw new IllegalArgumentException(excMsg);
            }
            this.addAdjs = Arrays.copyOf(additiveAdjustments, this.deg);
        }
    }
    
}
