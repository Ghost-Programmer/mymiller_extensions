package name.mymiller.geo;

import name.mymiller.lang.IllegalValueException;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Given a Polygon constructed from a GeoPath providing the boundaries of an
 * area, determine if a GeoLocation falls inside or outside of the area.
 *
 * @author jmiller
 */
public class GeoFence implements Serializable, GeoShape {
    /**
     *
     */
    private static final long serialVersionUID = 3441803877244921194L;

    /**
     * GeoPath that is the fence for this GeoFence
     */
    private GeoPath fence = null;

    /**
     * Basic GeoFence
     */
    public GeoFence() {
        super();
    }

    /**
     * Constructor to create a fence with an existing GeoPath
     *
     * @param fence GeoPath to use as path.
     */
    public GeoFence(GeoPath fence) {
        super();
        this.setFence(fence);
    }

    /**
     * Method to determine if the Latitude falls inside
     *
     * @param sortedPoints Sorted List of GeoLocations
     * @param x            Decimal Degree of the Latitude for the check point.
     * @return Boolean indicating if the check point is on the inside or outside.
     */
    private boolean calculateInside(final List<GeoLocation> sortedPoints, final double x) {
        boolean inside = false;
        for (final GeoLocation point : sortedPoints) {
            if (x < point.getLatitude().getDecimal()) {
                break;
            }
            inside = !inside;
        }
        return inside;
    }

    /**
     * Form the Array that will contain the Lines.
     *
     * @return List of GeoLine's that form the GeoFence.
     */
    protected List<GeoLine> calculateLines() {
        final List<GeoLine> results = this.fence.calculateLines();

        if (this.fence.path.size() > 1) {
            final int i = this.fence.size() - 1;
            final GeoLocation from = this.fence.get(i);
            final GeoLocation to = this.fence.get(0);
            final GeoLine line = new GeoLine(from, to);
            results.add(line);
        }
        return results;
    }

    /**
     * Method to check if a GeoLocation is inside or outside the fence
     *
     * @param location GeoLocation to check
     * @return true if the GeoLocation is inside, otherwise false
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    public boolean checkInside(final GeoLocation location) throws IllegalValueException {
        final List<GeoLine> lines = this.calculateLines();
        final List<GeoLine> intersectionLines = this.filterIntersectingLines(lines,
                location.getLongitude().getDecimal());
        final List<GeoLocation> intersectionPoints = this.fence.calculateIntersectionPoints(intersectionLines,
                location.getLongitude().getDecimal());
        this.sortPointsByX(intersectionPoints);
        return this.calculateInside(intersectionPoints, location.getLatitude().getDecimal());
    }

    /**
     * Create a List of Intersecting Lines
     *
     * @param lines Lines of the Fence
     * @param y     Longitude of the to filter one
     * @return List of the Lines intersecting that Longitude
     */
    private List<GeoLine> filterIntersectingLines(final List<GeoLine> lines, final double y) {
        final List<GeoLine> results = new LinkedList<>();
        for (final GeoLine line : lines) {
            if (this.isLineIntersectingAtY(line, y)) {
                results.add(line);
            }
        }
        return results;
    }

    /**
     * @param action
     * @see name.mymiller.geo.GeoPath#forEach(Consumer)
     */
    @Override
    public void forEach(Consumer<? super GeoLocation> action) {
        this.fence.forEach(action);
    }

    /**
     * @return GeoPath making up the Fence
     */
    public GeoPath getFence() {
        return this.fence;
    }

    /**
     * Set the GeoPath that makes up the fence
     *
     * @param fence Geopath for this fence.
     */
    public void setFence(final GeoPath fence) {
        this.fence = fence;
    }

    @Override
    public GeoPath getGeoPath() {
        return this.getFence();
    }

    /**
     * Method to check if a GeoLine intersects at Longitude
     *
     * @param line GeoLine of the fence to check
     * @param y    Longitude to check if it intersects
     * @return true if it intersects, otherwise false.
     */
    private boolean isLineIntersectingAtY(final GeoLine line, final double y) {
        final double minY = Math.min(line.getFrom().getLongitude().getDecimal(),
                line.getTo().getLongitude().getDecimal());
        final double maxY = Math.max(line.getFrom().getLatitude().getDecimal(),
                line.getTo().getLatitude().getDecimal());
        return (y > minY) && (y <= maxY);
    }

    /**
     * Sort the GeoLocation by Latitude
     *
     * @param points GeoLocations to sort
     */
    private void sortPointsByX(final List<GeoLocation> points) {
        points.sort(Comparator.comparingDouble(p -> p.getLatitude().getDecimal()));
    }

}
