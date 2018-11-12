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
package algebraics.quartics;

/**
 *
 * @author Alonso del Arte
 */
public final class Zeta8Ring extends QuarticRing {

    @Override
    public String toASCIIString() {
        return "O_Q(zeta_8)";
    }

    @Override
    public String toTeXString() {
        if (preferenceForBlackboardBold) {
            return "\\mathcal O_{\\mathbb Q(\\zeta_8)}";
        } else {
            return "\\mathcal O_{\\textbf Q(\\zeta_8)}";
        }
    }

    @Override
    public String toHTMLString() {
        if (preferenceForBlackboardBold) {
            return "<i>O</i><sub>\u211A(&zeta;<sub>8</sub>)</sub>";
        } else {
            return "<i>O</i><sub><b>Q</b>(&zeta;<sub>8</sub>)</sub>";
        }
    }
    
}
