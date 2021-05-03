package name.mymiller.geo;

import java.util.function.Consumer;

/**
 * Class used to represent a line between points on the GeoPath
 *
 * @author jmiller
 */
public class GeoLine implements GeoShape {
    /**
     * GeoLocation the GeoLine start.
     */
    private final GeoLocation from;
    /**
     * GeoLocation the GeoLine ends at.
     */
    private final GeoLocation to;

    /**
     * Constructor taking two GeoLocation to form the line between
     *
     * @param from Starting GeoLocation
     * @param to   Ending GeoLocation
     */
    public GeoLine(final GeoLocation from, final GeoLocation to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Call for each step in the Geo Line
     *
     * @param action Action to perform.
     */
    @Override
    public void forEach(Consumer<? super GeoLocation> action) {
        this.getGeoPath().forEach(action);
    }

    /**
     * @return Return the From GeoLocation
     */
    public GeoLocation getFrom() {
        return this.from;
    }

    @Override
    public GeoPath getGeoPath() {
        final GeoPath path = new GeoPath();
        path.add(this.getFrom());
        path.add(this.getTo());

        return path;
    }

    /**
     * @return Return the To GeoLocation
     */
    public GeoLocation getTo() {
        return this.to;
    }
}
