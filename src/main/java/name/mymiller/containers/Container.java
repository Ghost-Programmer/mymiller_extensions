/**
 * Copyright 2018 MyMiller Consulting LLC.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 *
 */
package name.mymiller.containers;

/**
 * @author jmiller
 * @param <E> Object type for this Container
 *
 */
public interface Container<E> {
    /**
     * Removes all elements in this container.
     */
    void clear();

    /**
     * Checks to see if the container contains value
     *
     * @param element Element to check
     * @return boolean indicating if the value is found.
     */
    boolean contains(E element);

    /**
     *
     * @return True if the container is empty;
     */
    boolean isEmpty();

    /**
     *
     * @return int indicating the number of elements in the container.
     */
    int size();

    /**
     *
     * @param array Array of type E[] the size of this container.
     * @return Array containing the data of this container
     */
    E[] toArray(E[] array);
}
