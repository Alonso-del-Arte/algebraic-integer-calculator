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

import algebraics.quadratics.ImaginaryQuadraticInteger;
import algebraics.quadratics.ImaginaryQuadraticRing;
import algebraics.quadratics.QuadraticInteger;
import algebraics.quadratics.RealQuadraticInteger;
import algebraics.quadratics.RealQuadraticRing;
import calculators.NumberTheoreticFunctionsCalculator;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the NonUniqueFactorizationDomainException class. The purpose of 
 * this test class is only to make sure the exception object works as it should. 
 * Testing whether this exception is thrown for the right reasons or not is the 
 * responsibility of other test classes. However, proper setup of this test 
 * class requires that {@link 
 * NumberTheoreticFunctionsCalculator#primeFactors(imaginaryquadraticinteger.ImaginaryQuadraticInteger)}
 * throws this exception when called upon numbers from imaginary quadratic rings 
 * that are not unique factorization domains (UFDs). With one exception, all the 
 * tests in this test class use the famous domain <b>Z</b>[&radic;-5], which is 
 * a non-UFD.
 * @author Alonso del Arte
 */
public class NonUniqueFactorizationDomainExceptionTest {
    
    /**
     * The famous ring <b>Z</b>[&radic;&minus;5], consisting of numbers of the 
     * form <i>a</i> + <i>b</i>&radic;&minus;5. It does not have unique 
     * factorization, since, for example, 21 = 3 &times; 7 = (4 &minus; 
     * &radic;&minus;5)(4 + &radic;&minus;5).
     */
    public static final ImaginaryQuadraticRing RING_ZI5 = new ImaginaryQuadraticRing(-5);
    
    /**
     * Not as famous as <b>Z</b>[&radic;&minus;5], <b>Z</b>[&radic;10], 
     * consisting of numbers of the form <i>a</i> + <i>b</i>&radic;10. It does 
     * not have unique factorization either, since, for example, 15 = 3 &times; 
     * 5 = (5 &minus; &radic;10)(5 + &radic;10).
     */
    public static final RealQuadraticRing RING_Z10 = new RealQuadraticRing(10);
    
    /**
     * This is for the example of &radic;&minus;5, a ramifying prime.
     */
    private static NonUniqueFactorizationDomainException nufdeSqrti5;
    
    /**
     * This is for the example of 5, a ramified prime, 
     * (&radic;&minus;5)<sup>2</sup>.
     */
    private static NonUniqueFactorizationDomainException nufde05;
    
    /**
     * This is for the example of 6, famously 2 &times; 3 = (1 &minus; 
     * &radic;&minus;5)(1 + &radic;&minus;5).
     */
    private static NonUniqueFactorizationDomainException nufde06;
    
    /**
     * This is for the example of 6 + &radic;&minus;5, a prime with norm 41.
     */
    private static NonUniqueFactorizationDomainException nufde41PF;
    
    /**
     * This is for the example of 41, a split prime, (6 &minus; 
     * &radic;&minus;5)(6 + &radic;&minus;5).
     */
    private static NonUniqueFactorizationDomainException nufde41;
    
    /**
     * This is for the example of 10, which has two distinct factorizations in 
     * <b>Z</b>[&radic;10].
     */
    private static NonUniqueFactorizationDomainException nufde10;
    
    /**
     * This is for the example of 77, which should only have one distinct 
     * factorization even in <b>Z</b>[&radic;10].
     */
    private static NonUniqueFactorizationDomainException nufde77;
    
    /**
     * Sets up five NonUniqueFactorizationDomainException objects. First they 
     * are initialized with 1, then exceptions are tried to be caught for the 
     * factorizations of five specific numbers in <b>Z</b>[&radic;-5]. If that 
     * happens as expected, five messages will be printed to the console reading 
     * "NonUniqueFactorizationDomainException for ..." rather than 
     * "Initialization state, not the result of an actually thrown exception."
     */
    @BeforeClass
    public static void setUpClass() {
        QuadraticInteger init = new ImaginaryQuadraticInteger(1, 0, RING_ZI5);
        nufdeSqrti5 = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        nufde05 = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        nufde06 = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        nufde41PF = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        nufde41 = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        nufde10 = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        nufde77 = new NonUniqueFactorizationDomainException("Initialization state, not the result of an actually thrown exception.", init);
        List<AlgebraicInteger> factorsList;
        init = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufdeSqrti5 = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufdeSqrti5.getMessage() + "\"");
        init = new ImaginaryQuadraticInteger(5, 0, RING_ZI5);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufde05 = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufde05.getMessage() + "\"");
        init = new ImaginaryQuadraticInteger(6, 0, RING_ZI5);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufde06 = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufde06.getMessage() + "\"");
        init = new ImaginaryQuadraticInteger(6, 1, RING_ZI5);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufde41PF = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufde41PF.getMessage() + "\"");
        init = new ImaginaryQuadraticInteger(41, 0, RING_ZI5);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufde41 = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufde41.getMessage() + "\"");
        init = new RealQuadraticInteger(10, 0, RING_Z10);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufde10 = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufde10.getMessage() + "\"");
        init = new RealQuadraticInteger(77, 0, RING_Z10);
        try {
            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(init);
            System.out.println(init.toASCIIString() + " has " + factorsList.size() + " factors.");
        } catch (NonUniqueFactorizationDomainException nufde) {
            nufde77 = nufde;
        }
        System.out.println("NonUniqueFactorizationDomainException for the " + init.toASCIIString() + " example has this message: \"" + nufde77.getMessage() + "\"");
    }
    
    /**
     * Test of getUnfactorizedNumber method, of class 
     * NonUniqueFactorizationDomainException. This is a simple getter, so it's a 
     * fairly straightforward test.
     */
    @Test
    public void testGetUnfactorizedNumber() {
        System.out.println("getUnfactorizedNumber");
        QuadraticInteger expResult = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
        AlgebraicInteger result = nufdeSqrti5.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
        result = nufdeSqrti5.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(5, 0, RING_ZI5);
        result = nufde05.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(6, 0, RING_ZI5);
        result = nufde06.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(6, 1, RING_ZI5);
        result = nufde41PF.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new ImaginaryQuadraticInteger(41, 0, RING_ZI5);
        result = nufde41.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(10, 0, RING_Z10);
        result = nufde10.getUnfactorizedNumber();
        assertEquals(expResult, result);
        expResult = new RealQuadraticInteger(77, 0, RING_Z10);
        result = nufde77.getUnfactorizedNumber();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of tryToFactorizeAnyway method, of class 
     * NonUniqueFactorizationDomainException. The expectation is that when a 
     * number in a non-UFD can be properly factorized into primes, 
     * tryToFactorizeAnyway() will return a list of those primes. But when a 
     * number is the product of irreducible numbers that are not prime, 
     * tryToFactorizeAnyway() will return a list of factors that includes -1 as 
     * a factor at least twice, so as to alert the recipient of the information 
     * that another factorization might be possible.
     */
    @Test
    public void testTryToFactorizeAnyway() {
        System.out.println("tryToFactorizeAnyway");
        List<QuadraticInteger> expResult = new ArrayList<>();
        QuadraticInteger number = new ImaginaryQuadraticInteger(0, 1, RING_ZI5);
        expResult.add(number); // Add sqrt(-5) to the list of expected factors
        System.out.println("Try to factorize " + nufdeSqrti5.getUnfactorizedNumber().toASCIIString());
        List<AlgebraicInteger> result = nufdeSqrti5.tryToFactorizeAnyway();
        assertEquals(expResult, result);
        expResult.add(number); // Add sqrt(-5) to the list of expected factors again
        number = new ImaginaryQuadraticInteger(-1, 0, RING_ZI5); // Good old -1, a unit
        expResult.add(0, number);
        System.out.println("Try to factorize " + nufde05.getUnfactorizedNumber().toASCIIString());
        result = nufde05.tryToFactorizeAnyway();
        assertEquals(expResult, result);
        expResult.clear();
        expResult.add(number);
        expResult.add(number); // Add -1 to the list twice
        System.out.println("Try to factorize " + nufde06.getUnfactorizedNumber().toASCIIString());
        result = nufde06.tryToFactorizeAnyway();
        String assertionMessage = "Factorization of " + nufde06.getUnfactorizedNumber().toASCIIString() + " should include -1 twice.";
        assertTrue(assertionMessage, result.containsAll(expResult));
        expResult.clear();
        number = new ImaginaryQuadraticInteger(6, -1, RING_ZI5); // 6 - sqrt(-5)
        expResult.add(number);
        expResult.add(number.conjugate()); // 6 + sqrt(-5)
        System.out.println("Try to factorize " + nufde41.getUnfactorizedNumber().toASCIIString());
        result = nufde41.tryToFactorizeAnyway();
        assertEquals(expResult, result);
        expResult.remove(number);
        System.out.println("Try to factorize " + nufde41PF.getUnfactorizedNumber().toASCIIString());
        result = nufde41PF.tryToFactorizeAnyway();
        assertEquals(expResult, result);
        expResult.clear();
        number = new RealQuadraticInteger(-1, 0, RING_Z10);
        expResult.add(number);
        expResult.add(number);
        expResult.add(number);
        expResult.add(number); // Add -1 to the list four times
        number = new RealQuadraticInteger(2, 0, RING_Z10);
        expResult.add(number);
        number = new RealQuadraticInteger(5, 0, RING_Z10);
        expResult.add(number);
        result = nufde10.tryToFactorizeAnyway();
        assertEquals(expResult, result);
        expResult.clear();
        number = new RealQuadraticInteger(7, 0, RING_Z10);
        expResult.add(number);
        number = new RealQuadraticInteger(11, 0, RING_Z10);
        expResult.add(number);
        result = nufde77.tryToFactorizeAnyway();
        assertEquals(expResult, result);
    }
    
    /**
     * Another test of tryToFactorizeAnyway method, of class 
     * NonUniqueFactorizationDomainException. The previous test (in source code 
     * order, not necessarily running order) operated on just five numbers in 
     * <b>Z</b>[&radic;-5]. This test operates on several numbers in several 
     * rings, from <b>Z</b>[&radic;-5] to <b>Z</b>[&radic;-97]. A few of those 
     * are UFDs, but almost all of them are not, so this provides a good workout 
     * of tryToFactorizeAnyway().
     */
    @Test
    public void testTryToFactorizeAnywayInSeveralImagRings() {
        System.out.println("tryToFactorizeAnyway in several different imaginary rings");
        ImaginaryQuadraticRing ring;
        ImaginaryQuadraticInteger number = new ImaginaryQuadraticInteger(1, 0, NumberTheoreticFunctionsCalculator.RING_GAUSSIAN);
        QuadraticInteger zero, negOne, facProd;
        String assertionMessage;
        int negOneCount, irrNotPrCount;
        List<AlgebraicInteger> factorsList = new ArrayList<>();
        factorsList.add(number); // This and previous line just to avoid might not have been initialized errors
        for (int r = -5; r > -100; r--) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(r)) {
                ring = new ImaginaryQuadraticRing(r);
                zero = new ImaginaryQuadraticInteger(0, 0, ring);
                negOne = new ImaginaryQuadraticInteger(-1, 0, ring);
                if (ring.hasHalfIntegers()) {
                    for (int a = -9; a < 10; a += 2) {
                        number = new ImaginaryQuadraticInteger(a, 3, ring, 2);
                        try {
                            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(number);
                        } catch (NonUniqueFactorizationDomainException nufde) {
                            factorsList = nufde.tryToFactorizeAnyway();
                        }
                        System.out.print(number.toASCIIString() + " = (" + factorsList.get(0).toASCIIString() + ")");
                        for (int i = 1; i < factorsList.size(); i++) {
                            System.out.print(" * (" + factorsList.get(i).toASCIIString() + ")");
                        }
                        System.out.print("?????");
                        negOneCount = 0;
                        irrNotPrCount = 0;
                        facProd = zero.plus(1); // Reset facProd to 1
                        for (AlgebraicInteger factor : factorsList) {
                            if (factor.equals(negOne)) {
                                negOneCount++;
                            }
                            if ((factor.norm() > 1) && (NumberTheoreticFunctionsCalculator.isIrreducible(factor) && !NumberTheoreticFunctionsCalculator.isPrime(factor))) {
                                irrNotPrCount++;
                            }
                            facProd = facProd.times((QuadraticInteger) factor);
                        }
                        assertionMessage = "Product of factors of " + facProd.toString() + " should match " + number.toString();
                        assertEquals(assertionMessage, number, facProd);
                        if (irrNotPrCount > 0) {
                            assertionMessage = "Since " + number.toString() + " is the product of at least one irreducible but not prime number, its attempted factorization should include at least two instances of -1";
                            assertTrue(assertionMessage, negOneCount > 1);
                        }
                    }
                    System.out.println(" Confirmed.");
                }
                for (int m = -5; m < 5; m++) {
                    number = new ImaginaryQuadraticInteger(m, 1, ring);
                    try {
                        factorsList = NumberTheoreticFunctionsCalculator.primeFactors(number);
                    } catch (NonUniqueFactorizationDomainException nufde) {
                        factorsList = nufde.tryToFactorizeAnyway();
                    }
                    System.out.print(number.toASCIIString() + " = (" + factorsList.get(0).toASCIIString() + ")");
                    for (int i = 1; i < factorsList.size(); i++) {
                        System.out.print(" * (" + factorsList.get(i).toASCIIString() + ")");
                    }
                    System.out.print("?????");
                    negOneCount = 0;
                    irrNotPrCount = 0;
                    facProd = zero.plus(1); // Reset facProd to 1
                    for (AlgebraicInteger factor : factorsList) {
                        if (factor.equals(negOne)) {
                            negOneCount++;
                        }
                        if ((factor.norm() > 1) && (NumberTheoreticFunctionsCalculator.isIrreducible(factor) && !NumberTheoreticFunctionsCalculator.isPrime(factor))) {
                            irrNotPrCount++;
                        }
                        facProd = facProd.times((QuadraticInteger) factor);
                    }
                    assertionMessage = "Product of factors of " + facProd.toString() + " should match " + number.toString();
                    assertEquals(assertionMessage, number, facProd);
                    if (irrNotPrCount > 0) {
                        assertionMessage = "Since " + number.toString() + " is the product of at least one irreducible but not prime number, its attempted factorization should include at least two instances of -1";
                        assertTrue(assertionMessage, negOneCount > 1);
                    }
                }
                System.out.println(" Confirmed.");
                System.out.print(number.toASCIIString() + " has " + factorsList.get(factorsList.size() - 1).toASCIIString() + " as one factor");
                if (!NumberTheoreticFunctionsCalculator.isPrime(factorsList.get(factorsList.size() - 1))) {
                    System.out.println(", which is irreducible but not prime.");
                } else {
                    System.out.println(".");
                }
            }
        }
    }

    /**
     * Another test of tryToFactorizeAnyway method, of class 
     * NonUniqueFactorizationDomainException. This test operates on several numbers in several 
     * rings, from <b>Z</b>[&radic;-5] to <b>Z</b>[&radic;-97]. A few of those 
     * are UFDs, but almost all of them are not, so this provides a good workout 
     * of tryToFactorizeAnyway().
     */
    @Ignore
    @Test
    public void testTryToFactorizeAnywayInSeveralRealRings() {
        System.out.println("tryToFactorizeAnyway in several different real rings");
        RealQuadraticRing ring;
        RealQuadraticInteger number = new RealQuadraticInteger(1, 0, NumberTheoreticFunctionsCalculator.RING_ZPHI);
        QuadraticInteger zero, negOne, facProd;
        String assertionMessage;
        int negOneCount, irrNotPrCount;
        List<AlgebraicInteger> factorsList = new ArrayList<>();
        factorsList.add(number); // This and previous line just to avoid might not have been initialized errors
        for (int r = 5; r < 200; r += 5) {
            if (NumberTheoreticFunctionsCalculator.isSquareFree(r)) {
                ring = new RealQuadraticRing(r);
                zero = new ImaginaryQuadraticInteger(0, 0, ring);
                negOne = new ImaginaryQuadraticInteger(-1, 0, ring);
                if (ring.hasHalfIntegers()) {
                    for (int a = -9; a < 10; a += 2) {
                        number = new RealQuadraticInteger(a, 3, ring, 2);
                        try {
                            factorsList = NumberTheoreticFunctionsCalculator.primeFactors(number);
                        } catch (NonUniqueFactorizationDomainException nufde) {
                            factorsList = nufde.tryToFactorizeAnyway();
                        }
                        System.out.print(number.toASCIIString() + " = (" + factorsList.get(0).toASCIIString() + ")");
                        for (int i = 1; i < factorsList.size(); i++) {
                            System.out.print(" * (" + factorsList.get(i).toASCIIString() + ")");
                        }
                        System.out.print("?????");
                        negOneCount = 0;
                        irrNotPrCount = 0;
                        facProd = zero.plus(1); // Reset facProd to 1
                        for (AlgebraicInteger factor : factorsList) {
                            if (factor.equals(negOne)) {
                                negOneCount++;
                            }
                            if ((factor.norm() > 1) && (NumberTheoreticFunctionsCalculator.isIrreducible(factor) && !NumberTheoreticFunctionsCalculator.isPrime(factor))) {
                                irrNotPrCount++;
                            }
                            facProd = facProd.times((QuadraticInteger) factor);
                        }
                        assertionMessage = "Product of factors of " + facProd.toString() + " should match " + number.toString();
                        assertEquals(assertionMessage, number, facProd);
                        if (irrNotPrCount > 0) {
                            assertionMessage = "Since " + number.toString() + " is the product of at least one irreducible but not prime number, its attempted factorization should include at least two instances of -1";
                            assertTrue(assertionMessage, negOneCount > 1);
                        }
                    }
                    System.out.println(" Confirmed.");
//                    System.out.println(number.toASCIIString() + " has " + factorsList.get(factorsList.size() - 1).toASCIIString() + " as one factor.");
                }
                for (int m = -5; m < 5; m++) {
                    number = new RealQuadraticInteger(m, 1, ring);
                    try {
                        factorsList = NumberTheoreticFunctionsCalculator.primeFactors(number);
                    } catch (NonUniqueFactorizationDomainException nufde) {
                        factorsList = nufde.tryToFactorizeAnyway();
                    }
                    System.out.print(number.toASCIIString() + " = (" + factorsList.get(0).toASCIIString() + ")");
                    for (int i = 1; i < factorsList.size(); i++) {
                        System.out.print(" * (" + factorsList.get(i).toASCIIString() + ")");
                    }
                    System.out.print("?????");
                    negOneCount = 0;
                    irrNotPrCount = 0;
                    facProd = zero.plus(1); // Reset facProd to 1
                    for (AlgebraicInteger factor : factorsList) {
                        if (factor.equals(negOne)) {
                            negOneCount++;
                        }
                        if ((factor.norm() > 1) && (NumberTheoreticFunctionsCalculator.isIrreducible(factor) && !NumberTheoreticFunctionsCalculator.isPrime(factor))) {
                            irrNotPrCount++;
                        }
                        facProd = facProd.times((QuadraticInteger) factor);
                    }
                    assertionMessage = "Product of factors of " + facProd.toString() + " should match " + number.toString();
                    assertEquals(assertionMessage, number, facProd);
                    if (irrNotPrCount > 0) {
                        assertionMessage = "Since " + number.toString() + " is the product of at least one irreducible but not prime number, its attempted factorization should include at least two instances of -1";
                        assertTrue(assertionMessage, negOneCount > 1);
                    }
                }
                System.out.println(" Confirmed.");
                System.out.print(number.toASCIIString() + " has " + factorsList.get(factorsList.size() - 1).toASCIIString() + " as one factor");
                if (!NumberTheoreticFunctionsCalculator.isPrime(factorsList.get(factorsList.size() - 1))) {
                    System.out.println(", which is irreducible but not prime.");
                } else {
                    System.out.println(".");
                }
            }
        }
    }
    
}
