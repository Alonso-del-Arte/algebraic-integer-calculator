/*
 * Copyright (C) 2018 Alonso del Arte
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
package algebraics;

/**
 * This interface sets the basic requirements for objects representing algebraic 
 * integers. The implementing classes should provide an algebraic degree 
 * function, trace and norm functions, and the minimal polynomial formatted 
 * both as an integer array of coefficients and as a String.
 * <p>Almost from the beginning, {@link ImaginaryQuadraticInteger}, an 
 * implementation of AlgebraicInteger, had some variants of {@link 
 * Object#toString()} specifically for ASCII plaintext, TeX documents and HTML 
 * pages, but it wasn't until July 2018 that I decided that some of those should 
 * be required by this interface.</p>
 * <p>Basic arithmetic functions (addition, subtraction, multiplication and 
 * division) would be nice but are not explicitly required by this interface. It 
 * is then up to the implementer to define basic arithmetic methods as static or 
 * instance methods, or both, or not at all. Other specifics are also left to 
 * the implementer.</p>
 * @author Alonso del Arte
 */
public interface AlgebraicInteger {
    
    /**
     * Gives the algebraic degree of the algebraic integer.
     * @return 0 if the algebraic integer is 0, a positive integer for any other 
     * algebraic integer. For example, for 1 + &#8731;2, the result should be 
     * 3, as that is an algebraic integer of degree 3.
     */
    int algebraicDegree();
    
    /**
     * Gives the trace of the algebraic integer. In the original version, this 
     * was an int, but due to many overflow problems, I'm changing this to a 
     * long. Overflow problems can still occur, but they're hopefully less 
     * frequent now.
     * @return The trace. For example, given 5/2 + sqrt(-7)/2, the trace would 
     * be 5.
     */
    long trace();
    
    /**
     * Gives the norm of the algebraic integer, useful for comparing integers in 
     * the Euclidean GCD algorithm. In the original version, this was an int, 
     * but due to many overflow problems, I'm changing this to a long. Overflow 
     * problems can still occur, but they're hopefully less frequent now.
     * @return The norm. For example, given 5/2 + (&radic;&minus;7)/2, the norm 
     * would be 8.
     */
    long norm();
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic 
     * integer, in the reverse order of the normal expression of the minimal 
     * polynomial.
     * @return An array of 64-bit integers, in total one more than the maximum 
     * possible algebraic degree in the applicable ring. If the algebraic degree 
     * of this integer is equal to that maximum possible algebraic degree, then 
     * the element at position length - 1 in the array ought to be 1. For 
     * example, if the algebraic integer is 1 + &#8731;2, the result would be 
     * {3, 3, 3, 1}.
     */
    long[] minPolynomial();
    
    /**
     * Gives the minimal polynomial formatted as a String. Spaces in the 
     * polynomial String are desirable but not required. The multiplication 
     * operator is preferably tacit, for easy transfer to TeX.
     * @return A String in which the variable x appears as many times as 
     * dictated by the algebraic degree. For example, if the algebraic integer 
     * is 1 + \u221B2, the result should be preferably "x^3 - 3x^2 + 3x - 
     * 3", but "x^3-3x^2+3x-3" and "x^3-3*x^2+3*x-3" are also acceptable.
     */
    String minPolynomialString();
    
    /**
     * A text representation of the algebraic integer using only ASCII 
     * characters. It is strongly recommended that any implementations of 
     * AlgebraicInteger also override {@link Object#toString}.
     * @return A String using only ASCII characters. For example, for 
     * &omega;\u221B2, this might be "omega cbrt(2)". Spaces are very desirable 
     * but not strictly required. However, if spaces are omitted in certain 
     * cases, such as this example, it might be necessary to make the previously 
     * tacit multiplication operator explicit as an ASCII character, e.g., 
     * "omega*cbrt(2)".
     */
    String toASCIIString();
    
    /**
     * A text representation of the algebraic integer suitable for use in a TeX 
     * document. It is strongly recommended that any implementations of 
     * AlgebraicInteger also override {@link Object#toString}.
     * @return A String suitable for use in a TeX document. For example, for 1 + 
     * \u221B2, the result might be "1 + \root 3 \of 2".
     */
    String toTeXString();
    
    /**
     * A text representation of the algebraic integer suitable for use in an 
     * HTML page. It is strongly recommended that any implementations of 
     * AlgebraicInteger also override {@link Object#toString}.
     * @return A String suitable for use in an HTML page. For example, for 1 + 
     * \u221B2, the result might be "1 + &amp;#8731;2", which should then render 
     * as "1 + &#8731;2".
     */
    String toHTMLString();
    
    /**
     * Retrieves the real part of this algebraic integer.
     * @return The real part, which may be a rational approximation in some 
     * cases. For example, for 1 + &#8731;2, this would be roughly 2.259921. For 
     * 3/2 + (&radic;&minus;19)/2, this should be exactly 1.5.
     */
    double getRealPartNumeric();
    
    /**
     * Retrieves the imaginary part of this algebraic integer, divided by 
     * <i>i</i> or &minus;<i>i</i> as needed. It is strongly recommended that 
     * the author of an implementation write Javadoc clarifying this and other 
     * points.
     * @return The imaginary part, divided by <i>i</i> or &minus;<i>i</i>. In 
     * some cases may be 0, in others it may be a rational approximation. For 
     * example, for 1 + &#8731;2, this should be exactly 0. For 3/2 + 
     * (&radic;&minus;19)/2, this would be roughly 2.17944947177.
     */
    double getImagPartNumeric();
        
}