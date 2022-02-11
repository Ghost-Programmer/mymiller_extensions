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

import java.util.Properties;
import java.util.Set;

/**
 * String Cache to remove redundant String objects that contain the same data.
 *
 * @author jmiller
 */
public class StringCache extends Cache<String> {
    /**
     * Global static instance of the cache.
     */
    private static final StringCache globalInstance = new StringCache();

    /**
     * Method to get a cache with default settings
     *
     * @return Global Cache
     */
    public static StringCache getInstance() {
        return StringCache.globalInstance;
    }

    /**
     * Return a Properties object with all strings Cached.
     *
     * @param properties Properties to convert to Cached Strings
     * @return new Properties object with cached strings.
     */
    public Properties cacheProperties(Properties properties) {
        Properties cacheProperties = new Properties();
        Set<String> propertyNames = properties.stringPropertyNames();
        propertyNames.forEach(propertyName -> cacheProperties.put(this.cache(propertyName), this.cache(properties.getProperty(propertyName))));

        return cacheProperties;
    }
}
