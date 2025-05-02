/*
 * Copyright (C) 2025 Alonso del Arte
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

import java.math.BigInteger;

/**
 * This interface sets the basic requirements for objects representing algebraic 
 * integers. The implementing classes should provide an algebraic degree 
 * function, trace and norm functions, and the minimal polynomial formatted 
 * both as an integer array of coefficients and as a {@code String}.
 * <p>Almost from the beginning, {@link 
 * algebraics.quadratics.ImaginaryQuadraticInteger}, an implementation of 
 * this interface, had some variants of {@code Object}'s {@code toString()} 
 * specifically for ASCII plaintext, TeX documents and HTML pages, but it wasn't 
 * until July 2018 that I decided that some of those should be required by this 
 * interface.</p>
 * <p>Basic arithmetic functions (addition, subtraction, multiplication and 
 * division) would be nice but are not explicitly required by this interface. It 
 * is then up to the implementer to define basic arithmetic methods as static or 
 * instance functions, or both, or not at all. Other specifics are also left to 
 * the implementer.</p>
 * <p>Those wishing to implement the basic arithmetic functions as instance 
 * functions should consider using the {@link arithmetic.Arithmeticable} 
 * interface.</p>
 * @author Alonso del Arte
 */
public interface AlgebraicInteger {
    
    /**
     * Gives the algebraic degree of the algebraic integer, the maximum exponent 
     * in the algebraic integer's minimal polynomial.
     * @return 0 if the algebraic integer is 0, a positive integer for any other 
     * algebraic integer. For example, for 1 + &#8731;2, the result should be 
     * 3, as that is an algebraic integer of degree 3.
     */
    int algebraicDegree();
    
    /**
     * Gives the trace of the algebraic integer. In the original version, this 
     * was a 32-bit integer, but due to many overflow problems, I changed it to 
     * a 64-bit integer. Overflow problems can still occur, but they're 
     * hopefully less frequent now.
     * @return The trace. For example, given <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;7</sup>&frasl;<sub>2</sub>, the trace would be 5.
     */
    long trace();
    
    /**
     * Gives the norm of the algebraic integer, useful for comparing integers in 
     * the Euclidean GCD algorithm. In the original version, this was a 32-bit 
     * integer, but due to many overflow problems, I changed it to a 64-bit 
     * integer. Overflow problems still occur, and are likelier as the degree of 
     * the algebraic integer gets higher. In cases where it's absolutely 
     * necessary to have the correct value of the norm without "clipping" to the 
     * range of 64-bit integers, use the {@link #fullNorm()} function.
     * @return The norm. For example, given <sup>5</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;7</sup>&frasl;<sub>2</sub>, the norm 
     * would be 8.
     */
    long norm();
    
    /**
     * Gives the norm of the algebraic integer in a format that can represent 
     * numbers outside the range of 64-bit integers. A default implementation is 
     * provided which simply wraps the result of {@link #norm()} in a {@code 
     * BigInteger} instance.
     * @return The norm in a {@code BigInteger} instance.
     * @throws ArithmeticException If there is any arithmetic problem 
     * constructing the {@code BigInteger} instance.
     */
    default BigInteger fullNorm() {
        return BigInteger.valueOf(this.norm());
    }
    
    /**
     * Gives the coefficients for the minimal polynomial of the algebraic 
     * integer. They are given in the reverse order of the normal expression of 
     * the minimal polynomial.
     * @return An array of 64-bit integers, in total one more than the maximum 
     * possible algebraic degree in the applicable ring. If the algebraic degree 
     * of this integer is equal to the maximum possible algebraic degree in the 
     * given ring, then the element at position {@code length} &minus; 1 in the 
     * array ought to be 1, otherwise it might be 0. For example, if the 
     * algebraic integer is 1 + &#8731;2 in <b>Z</b>[&#8731;2], which has 
     * minimal polynomial <i>x</i><sup>3</sup> &minus; 3<i>x</i><sup>2</sup> + 
     * 3<i>x</i> &minus; 3, this function would give {&minus;3, 3, &minus;3, 1}. 
     * However, that same algebraic integer in a ring of higher degree that has 
     * <b>Z</b>[&#8731;2] as an intermediate field might give for this function 
     * instead {&minus;3, 3, &minus;3, 1, 0, 0, 0}, for example.
     */
    long[] minPolynomialCoeffs();
    
    /**
     * Gives the minimal polynomial formatted as text. Spaces in the text are 
     * desirable but not required. The multiplication operator is preferably 
     * tacit. The proper minus sign should be used when applicable. Also, 
     * Unicode exponent characters should be used when applicable, and not the 
     * usual digits preceded by "^". Be aware that although the fonts available 
     * to Java AWT and Swing may have the Unicode exponent characters, those 
     * characters may in some cases be very small and hard to read.
     * @return Text in which the variable <i>x</i> appears as many times as 
     * dictated by the algebraic degree and how many nonzero coefficients there 
     * are. For example, if the algebraic integer is 1 + \u221B2, the result 
     * should be preferably "x<sup>3</sup> &minus; 3x<sup>2</sup> + 3x &minus; 
     * 3", but "x<sup>3</sup>&minus;3x<sup>2</sup>+3x&minus;3" would also be 
     * acceptable.
     */
    String minPolynomialString();
    
    /**
     * Gives the minimal polynomial formatted as text suitable for use in a TeX 
     * document. Spaces in the text are desirable but not required. The 
     * multiplication operator is preferably tacit. The dash should be used 
     * instead of the minus sign, which a TeX rendering engine would convert to 
     * the proper minus sign. When applicable, exponents should be preceded by 
     * "^", and enclosed by curly braces if more than one digit.
     * @return Text in which the variable <i>x</i> appears as many times as 
     * dictated by the algebraic degree and how many nonzero coefficients there 
     * are. For example, if the algebraic integer is 1 + \u221B2, the result 
     * should be preferably "x^3 - 3x^2 + 3x - 3", but "x^3-3x^2+3x-3" would 
     * also be acceptable.
     */
    String minPolynomialStringTeX();
    
    /**
     * Gives the minimal polynomial formatted as text suitable for use in an 
     * HTML document. Spaces in the text are desirable but not required. The 
     * multiplication operator is preferably tacit. The proper minus sign, as an 
     * HTML character entity, should be used instead of the dash. Also, the 
     * variable <i>x</i> should be italicized by means of the HTML italics tag. 
     * Exponents should be enclosed in HTML superscript tags.
     * @return Text in which the variable <i>x</i> appears as many times as 
     * dictated by the algebraic degree and how many nonzero coefficients there 
     * are. For example, if the algebraic integer is 1 + \u221B2, the result 
     * should be preferably "&lt;i&gt;x&lt;/i&gt;&lt;sup&gt;3&lt;/sup&gt; 
     * &amp;minus; 3&lt;i&gt;x&lt;/i&gt;&lt;sup&gt;2&lt;/sup&gt; + 
     * 3&lt;i&gt;x&lt;/i&gt; &amp;minus; 3", but that same text with the spaces 
     * removed would also be acceptable.
     */
    String minPolynomialStringHTML();
    
    /**
     * Retrieves an object representing the ring this algebraic integer belongs 
     * to. This is mainly to enable verification that two algebraic integers 
     * come from the same ring of algebraic integers. Implementing classes can 
     * and probably should narrow down the return type.
     * @return An object subclassed from {@link IntegerRing}. To check degree, 
     * one may use {@link IntegerRing#getMaxAlgebraicDegree()} or one may check 
     * if it's an instance of {@link algebraics.quadratics.QuadraticRing}, 
     * {@link algebraics.cubics.CubicRing}, etc.
     */
    IntegerRing getRing();
        
    /**
     * A text representation of the algebraic integer using only ASCII 
     * characters. It is strongly recommended that any implementations of 
     * this interface also override {@code Object}'s {@code toString()}.
     * @return Text using only ASCII characters. For example, for 
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
     * this interface also override {@code Object}'s {@code toString()}. For 
     * square roots, it is acceptable to use "\sqrt", but for cube roots and 
     * others, the "\root m \of n" syntax should be used, and not "\sqrt[m]{n}".
     * @return Text suitable for use in a TeX document. For example, for 1 + 
     * \u221B2, the result might be "1 + \root 3 \of 2".
     */
    String toTeXString();
    
    /**
     * A text representation of the algebraic integer suitable for use in an 
     * HTML page. It is strongly recommended that any implementations of 
     * this interface also override {@code Object}'s {@code toString()}.
     * @return Text suitable for use in an HTML page. For example, for 1 + 
     * \u221B2, the result might be "1 + &amp;#8731;2", which should then render 
     * as "1 + &#8731;2".
     */
    String toHTMLString();
    
    /**
     * Gives the absolute value of the algebraic integer, essentially its 
     * distance from 0. Sometimes called "radius" in the context of polar 
     * coordinates.
     * @return The absolute value in 64-bit floating point precision, in many 
     * cases a rational approximation. For example, for &minus;1 + <i>i</i>, 
     * this would be 1.4142135623730951, which is &radic;2 correct to all but 
     * the last decimal place quoted. For 1 &minus; &#8731;5, this would be 
     * 0.7099759466766971, which is correct for all but the last two decimal 
     * places quoted.
     */
    double abs();
    
    /**
     * Gives the real part of this algebraic integer according to machine 
     * precision. Most calculations by implementing classes ought to be done 
     * symbolically as much as possible, but sometimes it's absolutely necessary 
     * to use floating point numbers, which bring with them all manner of vexing 
     * problems.
     * @return The real part, which may be a rational approximation in some 
     * cases. For example, for 1 + &#8731;2, this might be 2.2599210498948734. 
     * For <sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;19</sup>&frasl;<sub>2</sub>, this should be exactly 
     * 1.5.
     */
    double getRealPartNumeric();
    
    /**
     * Gives the imaginary part of this algebraic integer, divided by <i>i</i> 
     * or &minus;<i>i</i> as needed. It is strongly recommended that the author 
     * of an implementation write documentation clarifying this and other 
     * points. However, in the case of algebraic integers from purely real 
     * rings, this issue is moot, since the result should always be 0.0.
     * @return The imaginary part, divided by <i>i</i> or &minus;<i>i</i>. In 
     * some cases may be 0.0, in others it may be a rational approximation. For 
     * example, for 1 + &#8731;2, this should be exactly 0.0. For 
     * <sup>3</sup>&frasl;<sub>2</sub> + 
     * <sup>&radic;&minus;19</sup>&frasl;<sub>2</sub>, this would probably be 
     * 2.179449471770337. For &minus;1 + <i>i</i>, this should be exactly 1.0.
     */
    double getImagPartNumeric();
    
    /**
     * Indicates whether the real part of this number, as given by {@link 
     * #getRealPartNumeric()}, is an approximation or not. This tends to be the 
     * case for numbers from purely real rings of degree 2 or higher.
     * @return True if the real part of this number as given by {@code 
     * getRealPartNumeric()} is an approximation, false if it is exact. For 
     * example, this would be true for 1 + &#8731;2, the real part of which 
     * might be given as 2.2599210498948734; it would be false for 1 + 
     * <i>i</i>&#8731;2, the real part of which should be given as 1.0, which is 
     * exact. The imaginary part of the latter number divided by <i>i</i> might 
     * be given as 1.2599210498948732. Notice that those two floating point 
     * approximations differ by 1.0000000000000002 rather than by 1.0.
     */
    boolean isReApprox();
    
    /**
     * Indicates whether the imaginary part of this number, as given by {@link 
     * #getImagPartNumeric()}, is an approximation or not. This tends to be the 
     * case for algebraic integers from imaginary rings of degree 2 or higher.
     * @return True if the imaginary part of this number as given by 
     * {@code getImagPartNumeric()} is an approximation, false if it is exact. 
     * For example, this would be true for 1 + <i>i</i>&#8731;2, since the 
     * imaginary part of that number would be given in floating point as an 
     * approximation such as 1.2599210498948732; this would be false for 1 + 
     * &#8731;2, since the imaginary part of that number should be given exactly 
     * as 0.0.
     */
    boolean isImApprox();
    
    /**
     * Gives the angle on the complex plane formed by a line segment starting at 
     * this algebraic integer and extending to 0, and another line segment going 
     * along on the real axis from 0 to positive infinity. For numbers with 
     * positive nonzero imaginary part, the angle should be positive, and for 
     * numbers with negative nonzero imaginary part, the angle should be 
     * negative. Other terms for this angle include "argument," "phase" and 
     * "amplitude."
     * @return The angle expressed in radians, ranging from &minus;&pi; radians 
     * to &pi; radians. For example, the angle of <i>i</i> should be given as  
     * 1.5707963267948966 radians (90 degrees) and the angle of &minus;<i>i</i> 
     * should be given as &minus;1.5707963267948966 radians (&minus;90 degrees). 
     * If you need this angle in degrees, you can use {@code 
     * Math.toDegrees(double)} to make the conversion.
     */
    double angle();
    
}
