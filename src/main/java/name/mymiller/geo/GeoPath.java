/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.geo;

import name.mymiller.lang.IllegalValueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * Class used to track a series of GeoLocations that make up a path.
 *
 * @author jmiller
 */
public class GeoPath implements Serializable, GeoShape {
    /**
     *
     */
    private static final long serialVersionUID = 1346991231306503158L;
    /**
     * ArrayList to contain the Path
     */
    protected ArrayList<GeoLocation> path = null;

    /**
     * Constructor to create an empty Path
     */
    public GeoPath() {
        super();
        this.path = new ArrayList<>();
    }

    /**
     * Constructor to populate with a list of GeoLocations
     *
     * @param path List of GeoLocations to mark the path
     */
    public GeoPath(final List<GeoLocation> path) {
        super();
        this.path = new ArrayList<>();
        this.path.addAll(path);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return true (as specified by Collection.add(E))
     */
    public boolean add(final GeoLocation e) {
        return this.path.add(e);
    }

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    public void add(final int index, final GeoLocation element) {
        this.path.add(index, element);
    }

    /**
     * Calculate the Intersection Points on the lines for the Longitude Degree
     *
     * @param lines List of Lines making up the fence.
     * @param y     Decimal Degree for the Longitude.
     * @return List of GeoLocations and their intersection points
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    protected List<GeoLocation> calculateIntersectionPoints(final List<GeoLine> lines, final double y)
            throws IllegalValueException {
        final List<GeoLocation> results = new LinkedList<>();
        for (final GeoLine line : lines) {
            final double x = this.calculateLineXAtY(line, y);
            results.add(new GeoLocation(x, y));
        }
        return results;
    }

    /**
     * Form the Array that will contain the Lines.
     *
     * @return List of GeoLine's that form the GeoFence.
     */
    protected List<GeoLine> calculateLines() {
        final List<GeoLine> results = new LinkedList<>();

        // form lines by connecting the points
        GeoLocation lastPoint = null;
        for (final GeoLocation point : this.path) {
            if (lastPoint != null) {
                results.add(new GeoLine(lastPoint, point));
            }
            lastPoint = point;
        }
        return results;
    }

    /**
     * Calculate the Latitude for a line at Longitude
     *
     * @param line GeoLine at Longitude
     * @param y    Longitude to calculate the Latitude to correspond
     * @return Decimal Degree for the corresponding Latitude
     */
    protected double calculateLineXAtY(final GeoLine line, final double y) {
        final GeoLocation from = line.getFrom();
        final double slope = this.calculateSlope(line);
        return from.getLatitude().getDecimal() + ((y - from.getLongitude().getDecimal()) / slope);
    }

    /**
     * Calculate the Slope of a line
     *
     * @param line GeoLine to calculate the slope
     * @return Deboule for the slope of the line.
     */
    protected double calculateSlope(final GeoLine line) {
        final GeoLocation from = line.getFrom();
        final GeoLocation to = line.getTo();
        return (to.getLongitude().getDecimal() - from.getLongitude().getDecimal())
                / (to.getLatitude().getDecimal() - from.getLatitude().getDecimal());
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        this.path.clear();
    }

    /**
     * Returns true if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    public boolean contains(final Object o) {
        return this.path.contains(o);
    }

    /**
     * Performs the given action for each element of the Iterable until all elements
     * have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(final Consumer<? super GeoLocation> action) {
        this.path.forEach(action);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    public GeoLocation get(final int index) {
        return this.path.get(index);
    }

    @Override
    public GeoPath getGeoPath() {
        return this;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in this
     * list, or -1 if this list does not contain the element
     */
    public int indexOf(final Object o) {
        return this.path.indexOf(o);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element
     */
    public int lastIndexOf(final Object o) {
        return this.path.lastIndexOf(o);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     *
     * @return a list iterator over the elements in this list (in proper sequence)
     */
    public ListIterator<GeoLocation> listIterator() {
        return this.path.listIterator();
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     */
    public GeoLocation remove(final int index) {
        return this.path.remove(index);
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it
     * is present.
     *
     * @param o element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    public boolean remove(final Object o) {
        return this.path.remove(o);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return this.path.size();
    }

    /**
     * @return Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element)
     */
    public GeoLocation[] toArray() {
        return this.path.toArray(new GeoLocation[this.size()]);
    }

}
