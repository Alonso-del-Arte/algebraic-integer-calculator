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
 * unlike that one, this one uses no "native methods," and, perhaps more 
 * importantly, is not proprietary.</p>
 * @param <N> The type of the names for the values to cache. Ideally this should 
 * be an immutable class that is easily calculated anew.
 * @param <V> The type of the values to cache. To be worth caching, the values 
 * should be too expensive to recalculate each and every time they're needed, so 
 * that it's easier to retrieve from the cache.
 * @author Alonso del Arte
 */
public abstract class LRUCache<N, V> {
    
    /**
     * The minimum capacity for a cache. Obviously this should not be a negative 
     * number, and zero doesn't make sense either. A value of 1 would be 
     * pointless, since the cache would be constantly pushing items out. So 
     * perhaps 2 is the smallest value that makes sense. But I think 4 is the 
     * smallest value likely to be used with any frequency. I don't think the 
     * cache should be too large, however, though I'm not providing a maximum 
     * capacity constant.
     */
    public static final int MINIMUM_CAPACITY = 4;
    
    private final N[] names;
    
    private final V[] values;
    
    private final int capacity;
    
    private final int lastIndex;
    
    private int nextUp = 0;
    
    /**
     * Creates a value for a given name. Ideally this function should only be 
     * called by {@link #forName(java.lang.Object) forName()}.
     * @param name The name to create a value for. Once the value is in the 
     * cache, this name can be used to retrieve it.
     * @return A new value. Preferably not null.
     */
    protected abstract V create(N name);
    
    /**
     * Gives the index of an object in an array. This will be the first 
     * occurrence of the object even if the object occurs in the array more than 
     * once.
     * @param obj The object to search for.
     * @param array The array in which to search for the object.
     * @param endBound The index after the last index to search. May be less 
     * than or equal to <code>array.length</code>, but it should not be greater.
     * @return The index where the object was found, or &minus;1 if the object 
     * is not in the array at an index prior to <code>endBound</code>.
     * @throws ArrayIndexOutOfBoundsException If <code>endBound</code> is 
     * greater than <code>array.length</code> and <code>obj</code> is not in the 
     * array (because if it is in the array there's no reason for this function 
     * to iterate out of bounds).
     * @throws NullPointerException If <code>obj</code> is null. The exception 
     * message will probably be empty, and thus not very helpful.
     */
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
     * @throws NullPointerException If <code>value</code> is null. The exception 
     * message will probably be empty, and thus not very helpful.
     */
    private int indexOf(V value) {
        return indexOf(value, this.values, this.capacity);
    }
    
    /**
     * Indicates whether or not a particular value is in this cache. Note that 
     * this function is package private. It exists for the sake of testing how 
     * items are retained in the cache or removed from the cache.
     * @param value The value to search for.
     * @return True if the value is currently in the cache, false if it is not.
     */
    boolean has(V value) {
        int index = this.indexOf(value);
        return index > -1;
    }
    
    /**
     * Moves an object in an array to index 0. The other objects are shifted 
     * accordingly, e.g., the object at index 0 is moved to index 1, the object 
     * at index 1 is moved to index 2, etc.
     * @param objects The array of objects.
     * @param position A nonnegative integer, preferably positive.
     * @throws ArrayIndexOutOfBoundsException If <code>position</code> is 
     * negative, equal to or greater than <code>objects.length</code>.
     */
    private static void moveToFront(Object[] objects, int position) {
        Object mostRecent = objects[position];
        for (int i = position; i > 0; i--) {
            objects[i] = objects[i - 1];
        }
        objects[0] = mostRecent;    
    }
    
    /**
     * Retrieves a value from the cache by its name, or creates it anew and adds 
     * it to the cache if it wasn't already stored. In either case, the cache 
     * notes the value is the most recently used. If a value is added to the 
     * cache and the cache was already at capacity, the least recently used 
     * value will be removed from the cache. If the name of a removed value is 
     * called for later, it will have to be created anew.
     * @param name The name for the value.
     * @return The value.
     * @throws NullPointerException If <code>name</code> is null. The exception 
     * message will probably be empty, and thus not very helpful.
     */
    public V forName(N name) {
        V value;
        int index = indexOf(name, this.names, this.nextUp);
        if (index == -1) {
            value = this.create(name);
            this.names[this.nextUp] = name;
            this.values[this.nextUp] = value;
            index = this.nextUp;
            if (this.nextUp < this.lastIndex) {
                this.nextUp++;
            }
        } else {
            value = this.values[index];
        }
        moveToFront(this.names, index);
        moveToFront(this.values, index);
        return value;
    }
    
    /**
     * Sole constructor. The cache is initialized to the specified capacity, 
     * which can't be altered later. Use {@link #forName(java.lang.Object) 
     * forName()} to add values to the cache, and that same function to retrieve 
     * the values.
     * @param size The capacity for the cache. Should be at least {@link 
     * #MINIMUM_CAPACITY}. Should probably not be greater than a hundred, though 
     * no specific upper bound is specified, much less enforced.
     * @throws IllegalArgumentException If <code>size</code> is less than {@link 
     * #MINIMUM_CAPACITY}.
     */
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
