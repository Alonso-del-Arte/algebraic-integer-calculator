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
package cacheops;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the LRUCache class.
 * @author Alonso del Arte
 */
public class LRUCacheTest {
    
    private static final Random RANDOM = new Random();
    
    /**
     * Test of the forName function, of the LRUCache class.
     */
    @Test
    public void testForName() {
        System.out.println("forName");
//        Object name = null;
//        LRUCache instance = null;
//        Object expResult = null;
//        Object result = instance.forName(name);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testConstructorRejectsNegativeCapacity() {
        int badSize = -RANDOM.nextInt(128) - 1;
        try {
            LRUCacheImpl cache = new LRUCacheImpl(badSize);
            String msg = "Should not have been able to create " 
                    + cache.toString() + " with size " + badSize;
            fail(msg);
        } catch (NegativeArraySizeException nase) {
            System.out.println("NegativeArraySizeException adequate for size " 
                    + badSize);
            System.out.println("\"" + nase.getMessage() + "\"");
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException preferred for size " 
                    + badSize);
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for size " + badSize;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsZeroCapacity() {
        try {
            LRUCacheImpl cache = new LRUCacheImpl(0);
            String msg = "Should not have been able to create " 
                    + cache.toString() + " with size 0";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException correct for size 0");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for size 0";
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsLowPositiveCapacity() {
        int badSize = LRUCache.MINIMUM_CAPACITY - 1;
        try {
            LRUCacheImpl cache = new LRUCacheImpl(badSize);
            String msg = "Should not have been able to create " 
                    + cache.toString() + " with size " + badSize;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException correct for size " 
                    + badSize);
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for size " + badSize;
            fail(msg);
        }
    }
    
    private static class LRUCacheImpl extends LRUCache<LocalDate, String> {

        @Override
        protected String create(LocalDate name) {
            return DateTimeFormatter.ISO_LOCAL_DATE.format(name);
        }

        @Override
        protected boolean hasName(String value, LocalDate name) {
            return DateTimeFormatter.ISO_LOCAL_DATE.format(name).equals(value);
        }

        public LRUCacheImpl(int size) {
            super(size);
        }
        
    }
    
}
