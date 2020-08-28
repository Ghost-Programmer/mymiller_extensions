/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of static methods for various algorithm implementations
 *
 * @author jmiller
 */
public class ArrayUtils {
    /**
     * Process an sorted array to identify the MAX difference between elements
     *
     * @param array Array to analyze
     * @return Maximum difference between two consecutive elements in the array.
     * @throws OutOfOrderException Exception throw to indicate Array is out of order
     */
    static public int maxDiff(final int[] array) throws OutOfOrderException {

        return ArrayUtils.maxDiff(array, 0, array.length - 1, 0);
    }

    /**
     * Process an sorted array to identify the MAX difference between elements
     *
     * @param array Array to analyze
     * @param start Position to start analyzing
     * @param end   Position to stop analyzing
     * @return Maximum difference between two consecutive elements in the array.
     * @throws OutOfOrderException Exception throw to indicate Array is out of order
     */
    static public int maxDiff(final int[] array, final int start, final int end) throws OutOfOrderException {
        return ArrayUtils.maxDiff(array, start, end, 0);
    }

    /**
     * Process an sorted array to identify the MAX difference between elements
     *
     * @param array      Array to analyze
     * @param start      Position to start analyzing
     * @param end        Position to stop analyzing
     * @param currentMax Maximum difference known
     * @return Maximum difference between two consecutive elements in the array.
     * @throws OutOfOrderException Exception throw to indicate Array is out of order
     */
    static private int maxDiff(final int[] array, final int start, final int end, final int currentMax)
            throws OutOfOrderException {
        final int range = Math.abs(array[end] - array[start]);
        if (array[start] > array[end]) {
            throw new OutOfOrderException("Array is not ordered. Index:(" + start + ") " + array[start]
                    + " is greater than Index:(" + end + ") " + array[end]);
        }
        if (start >= end) {
            return 0;
        } else if (range <= currentMax) {
            return 0;
        } else if (((start + 1) == end) || (range <= 1)) {
            return range;
        } else {
            final int mid = ((end - start) / 2) + start;

            final int maxFront = ArrayUtils.maxDiff(array, start, mid, currentMax);
            final int maxBack = ArrayUtils.maxDiff(array, mid, end, (maxFront > currentMax) ? maxFront : currentMax);

            return (maxFront > maxBack) ? maxFront : maxBack;
        }
    }

    /**
     * Method to extract duplicate elements from a sorted array
     *
     * @param <T>   element type for the list
     * @param array Array of elements remove duplicates
     * @return ArrayList of the unique elements
     */
    public static <T> List<T> uniqueElements(final T[] array) {
        final ArrayList<T> list = new ArrayList<>();

        if (array.length > 0) {
            list.add(array[0]);

            for (final T item : array) {
                if (!list.get(list.size()).equals(item)) {
                    list.add(item);
                }
            }
        }

        return list;
    }
}
