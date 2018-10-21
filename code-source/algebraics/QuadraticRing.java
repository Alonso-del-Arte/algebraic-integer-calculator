/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algebraicintegercalculator;

/**
 *
 * @author Alonso del Arte
 */
public class QuadraticRing implements IntegerRing {
    
    /**
     * Ought to be a squarefree negative integer
     */
    protected int radicand;

    /**
     * Should be true only if radicand is congruent to 1 modulo 4
     */
    protected boolean d1mod4;
    
    public static final int MAX_ALGEBRAIC_DEGREE = 2;
    
    private static boolean preferenceForBlackboardBold = true;
    
    /**
     * Query the setting of the preference for blackboard bold.
     * @return true if blackboard bold is preferred, false if plain bold is preferred.
     */
    public static boolean preferBlackboardBold() {
        return preferenceForBlackboardBold;
    }
    
    /**
     * Set preference for blackboard bold or plain bold. This is only relevant for the functions toTeXString() and toHTMLString().
     * @param preferenceForBB true if blackboard bold is preferred, false if plain bold is preferred.
     */
    public static void preferBlackboardBold(boolean preferenceForBB) {
        preferenceForBlackboardBold = preferenceForBB;
    }
    
    /**
     * A text representation of the ring. In some contexts, toTeXString() or toHTMLString() may be preferable.
     * @return A string representing the imaginary quadratic ring which can be output to the console.
     */
    @Override
    public String toString() {
        String IQRString;
        switch (this.radicand) {
            case 5:
                IQRString = "Z[\u03C6]";
                break;
            case -1:
                IQRString = "Z[i]";
                break;
            case -3:
                IQRString = "Z[\u03C9]";
                break;
            default:
                if (this.d1mod4) {
                    IQRString = "O_(Q(\u221A" + this.radicand + "))";
                } else {
                    IQRString = "Z[\u221A" + this.radicand + "]";
                }
        }
        return IQRString;
    }

    /**
     * I have not tested this function.
     * @return A string suitable for use in a TeX document, if I haven't made any mistakes.
     */
    public String toTeXString() {
        String IQRString;
        String QChar;
        String ZChar;
        if (preferenceForBlackboardBold) {
            QChar = "\\mathbb Q";
            ZChar = "\\mathbb Z";
        } else {
            QChar = "\\textbf Q";
            ZChar = "\\textbf Z";
        }
        if (this.d1mod4) {
            IQRString = "\\mathcal O_{" + QChar + "(\\sqrt{" + this.radicand + "})}";
        } else {
            IQRString = ZChar + "[\\sqrt{" + this.radicand + "}]";
        }
        return IQRString;
    }
    
    public String toHTMLString() {
        String IQRString;
        String QChar;
        String ZChar;
        if (preferenceForBlackboardBold) {
            QChar = "\\mathbb Q";
            ZChar = "\\mathbb Z";
        } else {
            QChar = "<b>Q</b>";
            ZChar = "<b>Z</b>";
        }
        if (this.d1mod4) {
            IQRString = "\\mathcal O_{" + QChar + "(\\sqrt{" + this.radicand + "})}";
        } else {
            IQRString = ZChar + "[\\sqrt{" + this.radicand + "}]";
        }
        return IQRString;
    }
    
    @Override
    public int getMaxAlgebraicDegree() {
        return MAX_ALGEBRAIC_DEGREE;
    }
    
}
