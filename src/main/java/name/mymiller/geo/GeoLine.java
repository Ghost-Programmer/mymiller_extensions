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
