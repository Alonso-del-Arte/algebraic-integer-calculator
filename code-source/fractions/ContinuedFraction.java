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
package fractions;

import java.util.List;

/**
 * Represents a continued fraction, such as 1 + 1/(2 + 1/(2 + 1/(2 + &hellip; 
 * ))), the continued fraction for &radic;2. That particular continued fraction 
 * will be used for several examples in this documentation.
 * @author Alonso del Arte
 */
public abstract class ContinuedFraction {
    
    /**
     * Provides a textual representation suitable for use in an HTML document. 
     * Authors of subclasses are strongly encouraged to also override 
     * <code>toString()</code>.
     * @return A <code>String</code> like "1 + 1/(2 + 1/(2 + 1/(2 + &amp;hellip;
     * )))".
     */
    public abstract String toHTMLString();
    
    /**
     * Provides a textual representation suitable for use in a TeX document. 
     * Authors of subclasses are strongly encouraged to also override 
     * <code>toString()</code>.
     * @return A <code>String</code> like "1 + \cfrac{1}{2 + \cfrac{1}{2 + 
     * \cfrac{1}{ \ddots \cfrac{\ddots}}}}".
     */
    public abstract String toTeXString();
    
    /**
     * Gives a list of convergents. The list is capped at some perhaps arbitrary 
     * threshold, such as a hundred convergents, especially when the number the 
     * continued fraction converges to is an irrational number.
     * @return A list of <code>BigFraction</code> instances. In many cases, both 
     * the numerators and denominators
     */
    public abstract List<BigFraction> convergents();
    
    /**
     * Gives an approximation of this continued fraction as a rational number.
     * @return A fraction approximating this continued fraction. For example, 
     * for the continued fraction for &radic;2, this might be 
     * <sup>54608393</sup>&frasl;<sub>38613965</sub>.
     */
    public abstract Fraction approximation();
    
    /**
     * Gives a floating point approximation of this continued fraction.
     * @return An approximation in 64-bit floating point. For example, for the 
     * continued fraction for &radic;2, this might be something like 
     * 1.4142135623730947.
     */
    public double getNumericApproximation() {
        return this.approximation().getNumericApproximation();
    }
    
}
