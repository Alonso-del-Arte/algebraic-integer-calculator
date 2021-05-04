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
 * @param <N> The type of the names for the values to cache. Ideally this should 
 * be an immutable class that is easily calculated anew.
 * @param <V> The type of the values to cache. To be worth caching, the values 
 * should be too expensive to recalculate each and every time they're needed.
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
    
    private final N[] names;
    
    private final V[] values;
    
    private final int capacity;
    
    private final int lastIndex;
    
    private int nextUp = 0;
    
    protected abstract V create(N name);
    
    private static int indexOf(Object obj, Object[] array, int endBound) {
        boolean found = false;
        int curr = 0;
        while (!found && (curr < endBound)) {
            found = obj.equals(array[curr]);
            curr++;
        }
        if (found) {
            return curr - 1;
        } else {
            return -1;
        }
    }
    
    /**
     * Gives the index of the specified value in this cache's values array.
     * @param value The value to search for.
     * @return The index where the value is found in this cache's values array, 
     * or &minus;1 if the value is not in that array.
     */
    private int indexOf(V value) {
        boolean found = false;
        int curr = 0;
        while (!found && (curr < this.capacity)) {//.nextUp)) {
            found = value.equals(this.values[curr]);
            curr++;
        }
        if (found) {
            return curr - 1;
        } else {
            return -1;
        }
    }
    
    /**
     * Indicates whether or not a particular value is in this cache. Note that 
     * this function is package private. It exists for the sake of testing how 
     * items are retained or removed from the cache.
     * @param value The value to search for.
     * @return True if the value is currently in the cache, false if it is not.
     */
    boolean has(V value) {
        int index = this.indexOf(value);
        return index > -1;
    }
    
    protected abstract boolean hasName(V value, N name);
    
    private static void moveToFront(Object[] objects, int position) {
        Object mostRecent = objects[position];
        for (int i = position; i > 0; i--) {
            objects[i] = objects[i - 1];
        }
        objects[0] = mostRecent;    
    }
    
    public V forName(N name) {
        V value;
        int retrievalIndex = indexOf(name, this.names, this.nextUp);
        if (retrievalIndex == -1) {
            value = this.create(name);
            this.names[this.nextUp] = name;
            this.values[this.nextUp] = value;
            retrievalIndex = this.nextUp;
            if (this.nextUp < this.lastIndex) {
                this.nextUp++;
            }
        } else {
            value = this.values[retrievalIndex];
        }
        moveToFront(this.names, retrievalIndex);
        moveToFront(this.values, retrievalIndex);
        System.out.println("About to return " + value.toString());
        return value;
    }
    
    public LRUCache(int size) {
        if (size < MINIMUM_CAPACITY) {
            String excMsg = "Proposed cache size " + size 
                    + " is less than minimum capacity " + MINIMUM_CAPACITY;
            throw new IllegalArgumentException(excMsg);
        }
        this.capacity = size;
        this.names = (N[]) new Object[this.capacity];
        this.values = (V[]) new Object[this.capacity];
        this.lastIndex = this.capacity - 1;
    }
    
}
