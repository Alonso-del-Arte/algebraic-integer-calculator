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
 * A least recently used (LRU) cache. This is modeled on the 
 * <code>sun.misc.LRUCache</code> class that <code>java.util.Scanner</code> uses 
 * in some implementations of the JDK. But unlike that one, this one uses no 
 * "native methods."
 * @param <N> The name for the value to cache.
 * @param <V> The value to cache.
 * @author Alonso del Arte
 */
public class LRUCache<N, V> {
    
    // STUB TO FAIL THE FIRST TEST
    V create(N name) {
        return null;
    }
    
    // STUB TO FAIL THE FIRST TEST
    public static void moveToFront(Object[] objects, int i) {
        //
    }
    
    // STUB TO FAIL THE FIRST TEST
    boolean hasName(V value, N name) {
        return false;
    }
    
    // STUB TO FAIL THE FIRST TEST
    public V forName(N name) {
        return null;
    }
    
    public LRUCache(int i) {
        //
    }
    
}
