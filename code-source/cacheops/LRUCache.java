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

/**
 * A least recently used (LRU) cache. The idea is that the cache makes the most 
 * recently used items available. The cache has a capacity specified at the time 
 * of construction. New items can still be added when capacity is reached, the 
 * cache simply discards the least recently used item.
 * <p>One way to implement the cache is with an array. New items are added at 
 * the first index of the array and the other items are pushed back one index. 
 * Whatever was at the last index is simply discarded.</p>
 * <p>As long as an item is in the cache, it can't be collected by the garbage 
 * collector. Once it's out of the cache, there might be no more references to 
 * the object, in which case the memory it takes up can be reclaimed.</p>
 * <p>This class is modeled on the <code>sun.misc.LRUCache</code> class that 
 * <code>java.util.Scanner</code> uses in some implementations of the JDK. But 
 * unlike that one, this one uses no "native methods."</p>
 * @param <N> The name for the value to cache.
 * @param <V> The value to cache.
 * @author Alonso del Arte
 */
public abstract class LRUCache<N, V> {
    
    /**
     * The minimum capacity for the cache. Obviously this should not be a 
     * negative number, and zero doesn't make sense either. A value of 1 would 
     * be pointless, since the cache would be constantly pushing items out. So 
     * perhaps 2 is the smallest value that makes sense. But I think 4 is the 
     * smallest value likely to be used with any frequency. I don't think the 
     * cache should be too large, however.
     */
    public static final int MINIMUM_CAPACITY = 4;
    
    private final V[] values;
    
    private final int capacity;
    
    private final int nextUp = 0;
    
    protected abstract V create(N name);
    
    private static void moveToFront(Object[] objects, int i) {
        //
    }
    
    // STUB TO FAIL THE FIRST TEST
    boolean has(V value) {
        return false;
    }
    
    protected abstract boolean hasName(V value, N name);
    
    public V forName(N name) {
        return this.create(name);
    }
    
    public LRUCache(int size) {
        if (size < MINIMUM_CAPACITY) {
            String excMsg = "Proposed cache size " + size 
                    + " is less than minimum capacity " + MINIMUM_CAPACITY;
            throw new IllegalArgumentException(excMsg);
        }
        this.capacity = size;
        this.values = (V[]) new Object[this.capacity];
    }
    
}
