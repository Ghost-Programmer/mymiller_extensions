/*
  Copyright 2018 MyMiller Consulting LLC.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License.  You may obtain a copy
  of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
  License for the specific language governing permissions and limitations under
  the License.
 */
/*

 */
package name.mymiller.containers;

import name.mymiller.utils.ListUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Object Cache to remove redundant Objects that contain the same data.
 *
 * @author jmiller
 */
public class Cache<E> {
    /**
     * Initial capacity of the cache.
     */
    private static final int globalInitialCapacity = 65535;
    /**
     * Initial Load factor to grow the map.
     */
    private static final float globalLoadFactor = (float) 0.75;

    /**
     * Number of collisions the cache has saved.
     */
    private int collisionCount = 0;
    /**
     * Weak has map to allow Objects to be removed when only the cache has a link to
     * them.
     */
    private WeakHashMap<E, WeakReference<E>> cache = null;

    /**
     * Protected constructor forcing the use of the getInstance methods.
     */
    public Cache() {
        this(Cache.globalInitialCapacity, Cache.globalLoadFactor);
    }

    /**
     * Protected constructor allowing the configuration of the capacity and load
     * factor
     *
     * @param globalInitialCapacity Initial Capacity of the cache
     * @param globalLoadFactor      Load factor for growth.
     */
    public Cache(final int globalInitialCapacity, final float globalLoadFactor) {
        this.cache = new WeakHashMap<>(globalInitialCapacity, globalLoadFactor);
    }

    /**
     * Returns the string matching the string from the cache.
     *
     * @param key Object to find cache value.
     * @return Cached instance of the string.
     */
    public synchronized E cache(final E key) {
        E cached = null;
        if (this.cache.containsKey(key)) {
            final WeakReference<E> ref = this.cache.get(key);

            if (ref != null) {
                cached = ref.get();
                this.collisionCount++;
            }
        }

        if (cached == null) {
            this.cache.put(key, new WeakReference<>(key));
            cached = key;
        }

        return cached;
    }

    /**
     * Clears the cache
     */
    public synchronized void clear() {
        this.cache.clear();
        this.collisionCount = 0;
    }

    /**
     * Determines if the cache contains an Object.
     *
     * @param key Object to check if it is in the Cache.
     * @return boolean indicating if the Object was found.
     * @see WeakHashMap#containsKey(Object)
     */
    public synchronized boolean containsKey(final E key) {
        return this.cache.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.name.mymiller.lang.Object#equals(java.name.mymiller.
     * extensions.lang.Object)
     */
    @Override
    public synchronized boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cache)) {
            return false;
        }
        final Cache other = (Cache) obj;
        if (this.cache == null) {
            return other.cache == null;
        } else {
            return this.cache.equals(other.cache);
        }
    }

    /**
     * Returns a Set view of the strings this cache.
     *
     * @return Set View
     */
    public synchronized List<E> getCached() {
        return ListUtils.iterate(this.cache.keySet().iterator());
    }

    /**
     * @return Number of Collisions that have occurred.
     */
    public int getCollisionCount() {
        return this.collisionCount;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.name.mymiller.lang.Object#hashCode()
     */
    @Override
    public synchronized int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.cache == null) ? 0 : this.cache.hashCode());
        return result;
    }

    /**
     * Inserts an existing Object cache into this instance.
     *
     * @param insert Cache to insert.
     */
    protected synchronized void insertCache(final Cache<E> insert) {
        this.cache.putAll(insert.cache);
    }

    /**
     * Remove an Object from the Cache.
     *
     * @param key Object to remove.
     */
    public synchronized void remove(final E key) {
        this.cache.remove(key);
    }

    /**
     * @return The size of the Cache.
     * @see WeakHashMap#size()
     */
    public synchronized int size() {
        return this.cache.size();
    }
}
