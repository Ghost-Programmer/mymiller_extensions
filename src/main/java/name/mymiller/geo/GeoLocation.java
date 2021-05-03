package name.mymiller.geo;

import name.mymiller.lang.IllegalValueException;
import name.mymiller.lang.UnitOfDistance;

import java.io.Serializable;

/**
 * Class to represent a Latitude/Longitude location on Earth. Methods to move to
 * a new location.
 *
 * @author jmiller
 */
public class GeoLocation implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3667455081379978811L;

    /**
     * Earth Diameter in in Kilometers
     */
    public static final double EARTH_DIAMETER = 12756.274D;

    /**
     * The latitude of the location
     */
    private Latitude latitude = null;

    /**
     * The longitude of the location
     */
    private Longitude longitude = null;

    /**
     * Constructor to create a Geolocation based on a decimal degree.
     *
     * @param latitude  Decimal Degree of the Latitude
     * @param longitude Decimal Degree of the Longitude
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    public GeoLocation(final Double latitude, final Double longitude) throws IllegalValueException {
        this.setLatitude(new Latitude(latitude));
        this.setLongitude(new Longitude(longitude));
    }

    /**
     * Constructor to crate a GeoLocation from Latitude/Longitude directly
     *
     * @param latitude  Latitude of the GeoLocation
     * @param longitude Longitude of the GeoLocation
     */
    public GeoLocation(final Latitude latitude, final Longitude longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructor to create a GeoLocation from Latitude/Longitude String directly
     *
     * @param latitude  Latitude of the GeoLocation
     * @param longitude Longitude of the GeoLocation
     * @throws NumberFormatException String wrong format for Double
     * @throws IllegalValueException Value not a Double.
     */
    public GeoLocation(String latitude, String longitude) throws NumberFormatException, IllegalValueException {
        this(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    /**
     * A method to add distance in an easterly direction to a coordinate
     *
     * @param distance the distance to add in metres
     * @param unit     The UnitOfDistance to use for measuring the distance.
     * @return the new coordinate
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    public GeoLocation addDistanceEast(final int distance, final UnitOfDistance unit) throws IllegalValueException {
        // convert the distance from metres to kilometers
        final double kilometers = (distance * unit.getConversionRateToMeters()) / 1000.0;

        // calculate the new longitude
        final double newLng = this.getLongitude().getDecimal()
                + (kilometers / this.longitudeConstant(this.getLatitude().getDecimal()));

        return new GeoLocation(this.getLatitude().getDecimal(), newLng);
    }

    /**
     * A method to add distance in a northerly direction to a coordinate
     *
     * @param distance the distance to add in metres
     * @param unit     The UnitOfDistance to use for measuring the distance.
     * @return the new coordinate
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    public GeoLocation addDistanceNorth(final int distance, final UnitOfDistance unit) throws IllegalValueException {
        // convert the distance from metres to kilometers
        final double kilometers = (distance * unit.getConversionRateToMeters()) / 1000d;

        // calculate the new latitude
        final double newLat = this.getLatitude().getDecimal() + (kilometers / this.latitudeConstant());

        return new GeoLocation(newLat, this.getLongitude().getDecimal());

    }

    /**
     * A method to add distance in a southerly direction to a coordinate
     *
     * @param distance the distance to add in metres
     * @param unit     The UnitOfDistance to use for measuring the distance.
     * @return the new coordinate
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    public GeoLocation addDistanceSouth(final int distance, final UnitOfDistance unit) throws IllegalValueException {
        // convert the distance from metres to kilometers
        final double kilometers = (distance * unit.getConversionRateToMeters()) / 1000d;

        // calculate the new latitude
        final double newLat = this.getLatitude().getDecimal() - (kilometers / this.latitudeConstant());

        return new GeoLocation(newLat, this.getLatitude().getDecimal());

    }

    /**
     * A method to add distance in an westerly direction to a coordinate
     *
     * @param distance the distance to add in metres
     * @param unit     The UnitOfDistance to use for measuring the distance.
     * @return the new coordinate
     * @throws IllegalValueException Illegal value, outside of the range of values.
     */
    public GeoLocation addDistanceWest(final int distance, final UnitOfDistance unit) throws IllegalValueException {
        // convert the distance from metres to kilometers
        final double kilometers = (distance * unit.getConversionRateToMeters()) / 1000;

        // calculate the new longitude
        final double newLng = this.getLongitude().getDecimal()
                - (kilometers / this.longitudeConstant(this.getLatitude().getDecimal()));

        return new GeoLocation(this.getLatitude().getDecimal(), newLng);
    }

    /**
     * Converts decimal degrees to radians
     *
     * @param degree Decimal Degree to convert
     * @return Radians
     */
    private Double deg2rad(final Double degree) {
        return ((degree * Math.PI) / 180.0);
    }

    /**
     * Calculate the distance between two GeoLocations and return the results in the
     * indicated UnitOfDistance
     *
     * @param to   GeoLocation to measure the distance to.
     * @param unit The UnitOfDistance to use for measuring the distance.
     * @return The distance between the two location in the UnitOfDistance.
     */
    public Double distanceTo(final GeoLocation to, final UnitOfDistance unit) {
        final double theta = this.getLongitude().getDecimal() - to.getLongitude().getDecimal();
        double dist = (Math.sin(this.deg2rad(this.getLatitude().getDecimal()))
                * Math.sin(this.deg2rad(to.getLatitude().getDecimal())))
                + (Math.cos(this.deg2rad(this.getLatitude().getDecimal()))
                * Math.cos(this.deg2rad(to.getLatitude().getDecimal())) * Math.cos(this.deg2rad(theta)));
        dist = Math.acos(dist);
        dist = this.rad2deg(dist);
        dist = dist * 60 * UnitOfDistance.Nautical_Miles.getConversionRateToMeters();

        return (dist * unit.getConversionRateFromMeters());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GeoLocation other = (GeoLocation) obj;
        if (this.latitude == null) {
            if (other.latitude != null) {
                return false;
            }
        } else if (!this.latitude.equals(other.latitude)) {
            return false;
        }
        if (this.longitude == null) {
            return other.longitude == null;
        } else {
            return this.longitude.equals(other.longitude);
        }
    }

    /**
     * @return The Latitude of this GeoLocation
     */
    public Latitude getLatitude() {
        return this.latitude;
    }

    /**
     * Set the Latitude for this GeoLocation
     *
     * @param latitude The Latitude to use for this GeoLocation
     */
    public void setLatitude(final Latitude latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The Longitude of this GeoLocation
     */
    public Longitude getLongitude() {
        return this.longitude;
    }

    /**
     * Set the Longitude for this GeoLocation
     *
     * @param longitude The Longitude to use for this GeoLocation
     */
    public void setLongitude(final Longitude longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.latitude == null) ? 0 : this.latitude.hashCode());
        result = (prime * result) + ((this.longitude == null) ? 0 : this.longitude.hashCode());
        return result;
    }

    /**
     * A private method to calculate the latitude constant
     *
     * @return a double representing the latitude constant
     */
    public double latitudeConstant() {
        return GeoLocation.EARTH_DIAMETER * (Math.PI / 360.0D);
    }

    /**
     * A private method to caluclate the longitude constant
     *
     * @param latitude a latitude coordinate in decimal notation
     * @return a double representing the longitude constant
     */
    public double longitudeConstant(final double latitude) {
        return (GeoLocation.EARTH_DIAMETER * Math.PI * Math.abs(Math.cos(Math.abs(latitude)))) / 360.0D;

    }

    /**
     * Converts radians to decimal degrees
     *
     * @param radians Radians to convert
     * @return Decimal Degree
     */
    private Double rad2deg(final Double radians) {
        return ((radians * 180) / Math.PI);
    }

    @Override
    public String toString() {
        return "GeoLocation [latitude=" + this.latitude + ", longitude=" + this.longitude + "]";
    }

}
