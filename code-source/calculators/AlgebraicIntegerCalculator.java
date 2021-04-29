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

import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.RealQuadraticRing;
import viewers.ImagQuadRingDisplay;
import viewers.RealQuadRingDisplay;
import viewers.Zeta8RingDisplay;

/**
 * This will eventually be the main class of the project.
 * @author Alonso del Arte
 */
public class AlgebraicIntegerCalculator {

    /**
     * at first this will just the of the
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        System.out.println("Still working on the main program...");
        System.out.println("In the meantime, please enjoy the ring viewers...");
        ImaginaryQuadraticRing imagQuadRing = new ImaginaryQuadraticRing(-1);
        ImagQuadRingDisplay iqrd = new ImagQuadRingDisplay(imagQuadRing);
        iqrd.startRingDisplay();
        RealQuadraticRing realQuadRing = new RealQuadraticRing(2);
        RealQuadRingDisplay rqrd = new RealQuadRingDisplay(realQuadRing);
        rqrd.startRingDisplay();
        Zeta8RingDisplay zrd = new Zeta8RingDisplay();
        zrd.startRingDisplay();
    }
    
}
