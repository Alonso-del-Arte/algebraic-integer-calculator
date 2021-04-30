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
    
    private static void fillCache(LRUCacheImpl cache, int size) {
        String current;
        System.out.print("Added values ");
        for (int i = 0; i < size; i++) {
            current = cache.forName(LocalDate.now().minusDays(i));
            System.out.print("\"" + current + "\", ");
        }
        System.out.println(" to " + cache.toString());
    }
    
    /**
     * Test of the forName function, of the LRUCache class.
     */
    @Test
    public void testForName() {
        System.out.println("forName");
        LRUCacheImpl cache = new LRUCacheImpl(LRUCache.MINIMUM_CAPACITY);
        int dayOffset = RANDOM.nextInt(65536) - 32768;
        LocalDate date = LocalDate.now().plusDays(dayOffset);
        String expected = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
        String actual = cache.forName(date);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the has function, of the LRUCache class. A value that was just 
     * cached should register as cached.
     */
    @Test
    public void testHas() {
        System.out.println("has");
        LRUCacheImpl cache = new LRUCacheImpl(LRUCache.MINIMUM_CAPACITY);
        LocalDate today = LocalDate.now();
        String value = cache.forName(today);
        String msg = "Cache should have value \"" + value + "\"";
        assert cache.has(value) : msg;
    }
    
    /**
     * Another test of the has function, of the LRUCache class. A value that has 
     * not been cached should not register as cached.
     */
    @Test
    public void testDoesNotHave() {
        LRUCacheImpl cache = new LRUCacheImpl(LRUCache.MINIMUM_CAPACITY);
        LocalDate today = LocalDate.now();
        String value = DateTimeFormatter.ISO_LOCAL_DATE.format(today);
        String msg = "Cache should not have value \"" + value + "\"";
        assert !cache.has(value) : msg;
    }
    
    @Test
    public void testCacheRetainsValueWhileCapacityAvailable() {
        int size = LRUCache.MINIMUM_CAPACITY + RANDOM.nextInt(4);
        LRUCacheImpl cache = new LRUCacheImpl(size);
        LocalDate firstDate = LocalDate.now().plusDays(1);
        String firstValue = cache.forName(firstDate);
        fillCache(cache, size);
        fail("Finish writing this test");
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
