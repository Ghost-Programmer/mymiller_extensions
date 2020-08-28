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

import java.lang.ref.WeakReference;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * String Cache to remove redundant String objects that contain the same data.
 *
 * @author jmiller
 */
public class StringCache {
    /**
     * Initial capacity of the cache.
     */
    private static final int globalInitialCapacity = 65535;
    /**
     * Initial Load factor to grow the map.
     */
    private static final float globalLoadFactor = (float) 0.75;

    /**
     * Global static instance of the cache.
     */
    private static final StringCache globalInstance = new StringCache();
    /**
     * Number of collisions the cache has saved.
     */
    private int collisionCount = 0;
    /**
     * Weak has map to allow Strings to be removed when only the cache has a link to
     * them.
     */
    private WeakHashMap<String, WeakReference<String>> cache = null;

    /**
     * Protected constructor forcing the use of the getInstance methods.
     */
    protected StringCache() {
        this.cache = new WeakHashMap<>(StringCache.globalInitialCapacity, StringCache.globalLoadFactor);
    }

    /**
     * Protected constructor allowing the configuration of the capacity and load
     * factor
     *
     * @param globalInitialCapacity Initial Capacity of the cache
     * @param globalLoadFactor      Load factor for growth.
     */
    protected StringCache(final int globalInitialCapacity, final float globalLoadFactor) {
        this.cache = new WeakHashMap<>(globalInitialCapacity, globalLoadFactor);
    }

    /**
     * Method to get a cache with default settings
     *
     * @return Global Cache
     */
    public static StringCache getInstance() {
        return StringCache.globalInstance;
    }

    /**
     * Returns the string matching the string from the cache.
     *
     * @param key String to find cache value.
     * @return Cached instance of the string.
     */
    public synchronized String cache(final String key) {
        String cachedString = null;
        if (this.cache.containsKey(key)) {
            final WeakReference<String> ref = this.cache.get(key);

            if (ref != null) {
                cachedString = ref.get();
                this.collisionCount++;
            }
        }

        if (cachedString == null) {
            this.cache.put(key, new WeakReference<>(key));
            cachedString = key;
        }

        return cachedString;
    }

    /**
     * Clears the cache
     */
    public synchronized void clear() {
        this.cache.clear();
        this.collisionCount = 0;
    }

    /**
     * Determines if the cache contains a String.
     *
     * @param key String to check if it is in the Cache.
     * @return boolean indicating if the String was found.
     * @see WeakHashMap#containsKey(Object)
     */
    public synchronized boolean containsKey(final String key) {
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
        if (!(obj instanceof StringCache)) {
            return false;
        }
        final StringCache other = (StringCache) obj;
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
    public synchronized String[] getCached() {
        return this.cache.keySet().toArray(new String[this.cache.size()]);
    }

    /**
     *
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
     * Inserts an existing String cache into this instance.
     *
     * @param insert Cache to insert.
     */
    protected synchronized void insertCache(final StringCache insert) {
        this.cache.putAll(insert.cache);
    }

    /**
     * Remove a String from the Cache.
     *
     * @param key String to remove.
     */
    public synchronized void remove(final String key) {
        this.cache.remove(key);
    }

    /**
     * @return The size of the Cache.
     * @see WeakHashMap#size()
     */
    public synchronized int size() {
        return this.cache.size();
    }

    /**
     * Return a Properties object with all strings Cached.
     * @param properties Properties to convert to Cached Strings
     * @return new Properties object with cached strings.
     */
    public Properties cacheProperties(Properties properties) {
        Properties cacheProperties = new Properties();
        Set<String> propertyNames = properties.stringPropertyNames();
        propertyNames.forEach(propertyName -> {
            cacheProperties.put(this.cache(propertyName),this.cache(properties.getProperty(propertyName)));
        });

        return cacheProperties;
    }
}
