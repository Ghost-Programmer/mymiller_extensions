/*
 * This file originated from: https://github.com/HanSolo/worldheatmap
 * This belongs and credited to Gerrit Grunwald, and I thank him for
 * his excellent work in extending and teaching JavaFX.
 */

/*
 * Copyright (c) 2016 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package name.mymiller.javafx.graph.world;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;

/**
 * Created by hansolo on 20.11.16.
 */
public class Location {
    private static final double EARTH_RADIUS = 6_371_000; // [m]
    private static final int DEFAULT_ICON_SIZE = 12;
    private String name;
    private double latitude;
    private double longitude;
    private String info;
    private Color color;
    private Ikon iconCode;
    private int iconSize;
    private EventHandler<MouseEvent> mouseEnterHandler;
    private EventHandler<MouseEvent> mousePressHandler;
    private EventHandler<MouseEvent> mouseReleaseHandler;
    private EventHandler<MouseEvent> mouseExitHandler;

    // ******************** Constructors **************************************
    public Location() {
        this("", 0, 0, "", null, null, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final double LATITUDE, final double LONGITUDE) {
        this("", LATITUDE, LONGITUDE, "", null, null, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final double LATITUDE, final double LONGITUDE, final Color COLOR, final Ikon ICON_CODE) {
        this("", LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final double LATITUDE, final double LONGITUDE, final Color COLOR, final Ikon ICON_CODE,
                    final int ICON_SIZE) {
        this("", LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, ICON_SIZE);
    }

    public Location(final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE) {
        this("", LATITUDE, LONGITUDE, "", null, ICON_CODE, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE, final int ICON_SIZE) {
        this("", LATITUDE, LONGITUDE, "", null, ICON_CODE, ICON_SIZE);
    }

    public Location(final double LATITUDE, final double LONGITUDE, final int ICON_SIZE) {
        this("", LATITUDE, LONGITUDE, "", null, null, ICON_SIZE);
    }

    public Location(final String NAME) {
        this(NAME, 0, 0, "", null, null, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, null, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, null, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR,
                    final Ikon ICON_CODE) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR,
                    final Ikon ICON_CODE, final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR,
                    final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, null, ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, ICON_CODE, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE,
                    final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, ICON_CODE, ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, null, ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final String INFO,
                    final Color COLOR, final Ikon ICON_CODE) {
        this(NAME, LATITUDE, LONGITUDE, INFO, COLOR, ICON_CODE, Location.DEFAULT_ICON_SIZE);
    }

    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final String INFO,
                    final Color COLOR, final Ikon ICON_CODE, final int ICON_SIZE) {
        this.name = NAME;
        this.latitude = LATITUDE;
        this.longitude = LONGITUDE;
        this.info = INFO;
        this.color = COLOR;
        this.iconCode = ICON_CODE;
        this.iconSize = ICON_SIZE;
    }

    public Location(final String NAME, final int ICON_SIZE) {
        this(NAME, 0, 0, "", null, null, ICON_SIZE);
    }

    public double calcDistanceInKilometer(final Location P1, final Location P2) {
        return this.calcDistanceInMeter(P1, P2) / 1000.0;
    }

    public double calcDistanceInMeter(final double LAT_1, final double LON_1, final double LAT_2, final double LON_2) {
        final double LAT_1_RADIANS = Math.toRadians(LAT_1);
        final double LAT_2_RADIANS = Math.toRadians(LAT_2);
        final double DELTA_LAT_RADIANS = Math.toRadians(LAT_2 - LAT_1);
        final double DELTA_LON_RADIANS = Math.toRadians(LON_2 - LON_1);

        final double A = (Math.sin(DELTA_LAT_RADIANS * 0.5) * Math.sin(DELTA_LAT_RADIANS * 0.5))
                + (Math.cos(LAT_1_RADIANS) * Math.cos(LAT_2_RADIANS) * Math.sin(DELTA_LON_RADIANS * 0.5)
                * Math.sin(DELTA_LON_RADIANS * 0.5));
        final double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1 - A));

        return Location.EARTH_RADIUS * C;
    }

    public double calcDistanceInMeter(final Location P1, final Location P2) {
        return this.calcDistanceInMeter(P1.getLatitude(), P1.getLongitude(), P2.getLatitude(), P2.getLongitude());
    }

    private int clamp(final int MIN_VALUE, final int MAX_VALUE, final int VALUE) {
        if (VALUE < MIN_VALUE) {
            return MIN_VALUE;
        }
        if (VALUE > MAX_VALUE) {
            return MAX_VALUE;
        }
        return VALUE;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(final Color COLOR) {
        this.color = COLOR;
    }

    public double getDistanceTo(final Location LOCATION) {
        return this.calcDistanceInMeter(this, LOCATION);
    }

    public Ikon getIconCode() {
        return this.iconCode;
    }

    public void setIconCode(final Ikon ICON_CODE) {
        this.iconCode = ICON_CODE;
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public void setIconSize(final int SIZE) {
        this.iconSize = this.clamp(6, 24, SIZE);
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(final String INFO) {
        this.info = INFO;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(final double LATITUDE) {
        this.latitude = LATITUDE;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(final double LONGITUDE) {
        this.longitude = LONGITUDE;
    }

    public EventHandler<MouseEvent> getMouseEnterHandler() {
        return this.mouseEnterHandler;
    }

    public void setMouseEnterHandler(final EventHandler<MouseEvent> HANDLER) {
        this.mouseEnterHandler = HANDLER;
    }

    public EventHandler<MouseEvent> getMouseExitHandler() {
        return this.mouseExitHandler;
    }

    public void setMouseExitHandler(final EventHandler<MouseEvent> HANDLER) {
        this.mouseExitHandler = HANDLER;
    }

    public EventHandler<MouseEvent> getMousePressHandler() {
        return this.mousePressHandler;
    }

    public void setMousePressHandler(final EventHandler<MouseEvent> HANDLER) {
        this.mousePressHandler = HANDLER;
    }

    public EventHandler<MouseEvent> getMouseReleaseHandler() {
        return this.mouseReleaseHandler;
    }

    public void setMouseReleaseHandler(final EventHandler<MouseEvent> HANDLER) {
        this.mouseReleaseHandler = HANDLER;
    }

    // ******************** Methods *******************************************
    public String getName() {
        return this.name;
    }

    public void setName(final String NAME) {
        this.name = NAME;
    }
}
