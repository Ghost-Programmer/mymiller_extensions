package name.mymiller.geo;

import java.util.function.Consumer;

public interface GeoShape {
    void forEach(final Consumer<? super GeoLocation> action);

    GeoPath getGeoPath();
}
