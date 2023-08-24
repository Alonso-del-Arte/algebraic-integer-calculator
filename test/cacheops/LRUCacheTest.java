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
     * Adds a specified number of elements to the cache. This helper procedure 
     * is actually unaware of the size of the cache, so it may be used to almost 
     * fill the cache, stopping short of capacity, as well as to add enough 
     * items that the cache has to drop some items.
     * @param cache The cache to add elements to. Upon returning, the cache 
     * should contain <code>String</code> instances for dates from today to 
     * <code>size</code> &minus; 1 days ago.
     * @param size The number of elements to add to the cache to. Should 
     * generally be the same as the cache capacity, but it can just as easily be 
     * shy of capacity or in excess of capacity.
     */
    private static void fillCache(LRUCacheImpl cache, int size) {
        String current;
        System.out.print("Added values ");
        for (int i = 0; i < size; i++) {
            current = cache.forName(LocalDate.now().minusDays(i));
            System.out.print("\"" + current + "\", ");
        }
        System.out.println("to " + cache.toString());
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
     * cached should register as cached. Note that the has function is a package 
     * private function.
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
    
    /**
     * Test that the first added value stays in the cache as new items are 
     * added, provided the capacity is not exceeded. However, this doesn't test 
     * whether the cache is keeping track of how recently an item was accessed.
     */
    @Test
    public void testCacheRetainsValueWhileCapacityAvailable() {
        int size = LRUCache.MINIMUM_CAPACITY + 1;
        LRUCacheImpl cache = new LRUCacheImpl(size);
        LocalDate firstAddedName = LocalDate.now().plusDays(1);
        String firstAddedValue = cache.forName(firstAddedName);
        fillCache(cache, size - 1);
        String msg = "After adding " + size 
                + " items to cache of size " + size 
                + ", first added value should still be in the cache";
        assert cache.has(firstAddedValue) : msg;
    }
    
    /**
     * Test that the cache drops the first added value once enough new items are 
     * added that capacity is exceeded and it becomes necessary to remove items 
     * from the cache. However, this test is not sufficient to prove that the 
     * cache is correctly working as a least recently used cache.
     */
    @Test
    public void testCacheDropsLeastRecentAfterReachingFullCapacity() {
        int size = LRUCache.MINIMUM_CAPACITY;
        LRUCacheImpl cache = new LRUCacheImpl(size);
        LocalDate firstAddedName = LocalDate.now().plusDays(1);
        String firstAddedValue = cache.forName(firstAddedName);
        fillCache(cache, size);
        String msg = "After adding " + (size + 1) 
                + " items to cache of size " + size 
                + ", first added value should no longer be in the cache";
        assert !cache.has(firstAddedValue) : msg;
    }
    
    /**
     * Test that the cache does not drop an item from the cache if it's 
     * repeatedly accessed so that it stays recent. More than twice as many 
     * distinct elements as the cache has capacity for will be added, but the 
     * first added value will be repeatedly retrieved so that it stays as the 
     * most recent or second most recent item.
     */
    @Test
    public void testCacheKeepsTrackOfRecentness() {
        int size = LRUCache.MINIMUM_CAPACITY + RANDOM.nextInt(4);
        LRUCacheImpl cache = new LRUCacheImpl(size);
        LocalDate firstAddedName = LocalDate.now().plusDays(7);
        String firstAddedValue = cache.forName(firstAddedName);
        System.out.println("Added \"" + firstAddedValue + "\" to the cache");
        int twiceCapacityPlusTwo = 2 * size + 2;
        LocalDate currName;
        String wrongValue = "wrong value";
        String currValue = wrongValue;
        String msg;
        for (int i = 1; i < twiceCapacityPlusTwo; i++) {
            currName = LocalDate.now().minusWeeks(i);
            msg = "Repeated recall of " + cache.forName(firstAddedName) 
                    + " should ensure it's still in the cache after adding " + i 
                    + " other elements, including " + currName.toString();
            currValue = cache.forName(currName);
            assert cache.has(firstAddedValue) : msg;
        }
        assert !currValue.equals(wrongValue);
        System.out.println("Last added value was \"" + currValue);
    }
    
    /**
     * Test of the LRUCache constructor. A negative pseudorandom number will be 
     * passed to the constructor. This should cause one of two exceptions.
     */
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
    
    /**
     * Test of the LRUCache constructor. Zero will be passed to the constructor. 
     * This should cause an exception.
     */
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
    
    /**
     * Test of the LRUCache constructor. A number that is one less than {@link 
     * LRUCache#MINIMUM_CAPACITY} will be passed to the constructor. This should 
     * cause an exception.
     */
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
    
    /**
     * Concrete subclass of {@link LRUCache}, with <code>LocalDate</code> 
     * instances as the names and <code>String</code> instances as the values. 
     * Although <code>String</code> instances are not particularly worth 
     * caching, they're easy enough to understand for writing tests.
     */
    private static class LRUCacheImpl extends LRUCache<LocalDate, String> {

        @Override
        protected String create(LocalDate name) {
            return DateTimeFormatter.ISO_LOCAL_DATE.format(name);
        }

        public LRUCacheImpl(int size) {
            super(size);
        }
        
    }
    
}
