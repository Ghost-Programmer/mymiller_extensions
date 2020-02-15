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

import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import name.mymiller.javafx.graph.heatmap.ColorMapping;
import name.mymiller.javafx.graph.heatmap.OpacityDistribution;
import name.mymiller.javafx.graph.world.World.Resolution;
import org.kordamp.ikonli.Ikon;

import java.util.HashMap;

/**
 * Created by hansolo on 21.11.16.
 */
public class WorldBuilder<B extends WorldBuilder<B>> {
    private final HashMap<String, Property> properties = new HashMap<>();
    private Resolution resolution = Resolution.HI_RES;

    // ******************** Constructors **************************************
    protected WorldBuilder() {
    }

    // ******************** Methods *******************************************
    public static final WorldBuilder create() {
        return new WorldBuilder();
    }

    public final B backgroundColor(final Color COLOR) {
        this.properties.put("backgroundColor", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final World build() {
        final ColorMapping colorMapping = this.properties.containsKey("colorMapping")
                ? ((ObjectProperty<ColorMapping>) this.properties.get("colorMapping")).get()
                : ColorMapping.INFRARED_3;
        final double eventRadius = this.properties.containsKey("eventRadius")
                ? ((DoubleProperty) this.properties.get("eventRadius")).get()
                : 5;
        final boolean fadeColors = this.properties.containsKey("fadeColors") && ((BooleanProperty) this.properties.get("fadeColors")).get();
        final double heatMapOpacity = this.properties.containsKey("heatMapOpacity")
                ? ((DoubleProperty) this.properties.get("heatMapOpacity")).get()
                : 0.5;
        final OpacityDistribution opacityDistribution = this.properties.containsKey("opacityDistribution")
                ? ((ObjectProperty<OpacityDistribution>) this.properties.get("opacityDistribution")).get()
                : OpacityDistribution.EXPONENTIAL;

        final World CONTROL = new World(this.resolution, colorMapping, eventRadius, fadeColors, opacityDistribution,
                heatMapOpacity);

        for (final String key : this.properties.keySet()) {
            if ("prefSize".equals(key)) {
                final Dimension2D dim = ((ObjectProperty<Dimension2D>) this.properties.get(key)).get();
                CONTROL.setPrefSize(dim.getWidth(), dim.getHeight());
            } else if ("minSize".equals(key)) {
                final Dimension2D dim = ((ObjectProperty<Dimension2D>) this.properties.get(key)).get();
                CONTROL.setMinSize(dim.getWidth(), dim.getHeight());
            } else if ("maxSize".equals(key)) {
                final Dimension2D dim = ((ObjectProperty<Dimension2D>) this.properties.get(key)).get();
                CONTROL.setMaxSize(dim.getWidth(), dim.getHeight());
            } else if ("prefWidth".equals(key)) {
                CONTROL.setPrefWidth(((DoubleProperty) this.properties.get(key)).get());
            } else if ("prefHeight".equals(key)) {
                CONTROL.setPrefHeight(((DoubleProperty) this.properties.get(key)).get());
            } else if ("minWidth".equals(key)) {
                CONTROL.setMinWidth(((DoubleProperty) this.properties.get(key)).get());
            } else if ("minHeight".equals(key)) {
                CONTROL.setMinHeight(((DoubleProperty) this.properties.get(key)).get());
            } else if ("maxWidth".equals(key)) {
                CONTROL.setMaxWidth(((DoubleProperty) this.properties.get(key)).get());
            } else if ("maxHeight".equals(key)) {
                CONTROL.setMaxHeight(((DoubleProperty) this.properties.get(key)).get());
            } else if ("scaleX".equals(key)) {
                CONTROL.setScaleX(((DoubleProperty) this.properties.get(key)).get());
            } else if ("scaleY".equals(key)) {
                CONTROL.setScaleY(((DoubleProperty) this.properties.get(key)).get());
            } else if ("layoutX".equals(key)) {
                CONTROL.setLayoutX(((DoubleProperty) this.properties.get(key)).get());
            } else if ("layoutY".equals(key)) {
                CONTROL.setLayoutY(((DoubleProperty) this.properties.get(key)).get());
            } else if ("translateX".equals(key)) {
                CONTROL.setTranslateX(((DoubleProperty) this.properties.get(key)).get());
            } else if ("translateY".equals(key)) {
                CONTROL.setTranslateY(((DoubleProperty) this.properties.get(key)).get());
            } else if ("padding".equals(key)) {
                CONTROL.setPadding(((ObjectProperty<Insets>) this.properties.get(key)).get());
            } else if ("backgroundColor".equals(key)) {
                CONTROL.setBackgroundColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("fillColor".equals(key)) {
                CONTROL.setFillColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("strokeColor".equals(key)) {
                CONTROL.setStrokeColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("hoverColor".equals(key)) {
                CONTROL.setHoverColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("pressedColor".equals(key)) {
                CONTROL.setPressedColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("selectedColor".equals(key)) {
                CONTROL.setSelectedColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("locationColor".equals(key)) {
                CONTROL.setLocationColor(((ObjectProperty<Color>) this.properties.get(key)).get());
            } else if ("hoverEnabled".equals(key)) {
                CONTROL.setHoverEnabled(((BooleanProperty) this.properties.get(key)).get());
            } else if ("selectionEnabled".equals(key)) {
                CONTROL.setSelectionEnabled(((BooleanProperty) this.properties.get(key)).get());
            } else if ("zoomEnabled".equals(key)) {
                CONTROL.setZoomEnabled(((BooleanProperty) this.properties.get(key)).get());
            } else if ("mouseEnterHandler".equals(key)) {
                CONTROL.setMouseEnterHandler(
                        ((ObjectProperty<EventHandler<MouseEvent>>) this.properties.get(key)).get());
            } else if ("mousePressHandler".equals(key)) {
                CONTROL.setMousePressHandler(
                        ((ObjectProperty<EventHandler<MouseEvent>>) this.properties.get(key)).get());
            } else if ("mouseReleaseHandler".equals(key)) {
                CONTROL.setMouseReleaseHandler(
                        ((ObjectProperty<EventHandler<MouseEvent>>) this.properties.get(key)).get());
            } else if ("mouseExitHandler".equals(key)) {
                CONTROL.setMouseExitHandler(
                        ((ObjectProperty<EventHandler<MouseEvent>>) this.properties.get(key)).get());
            } else if ("locations".equals(key)) {
                CONTROL.addLocations(((ObjectProperty<Location[]>) this.properties.get(key)).get());
            } else if ("showLocations".equals(key)) {
                CONTROL.showLocations(((BooleanProperty) this.properties.get(key)).get());
            } else if ("locationIconCode".equals(key)) {
                CONTROL.setLocationIconCode(((ObjectProperty<Ikon>) this.properties.get(key)).get());
            }
        }
        return CONTROL;
    }

    public final B colorMapping(final ColorMapping COLOR_MAPPING) {
        this.properties.put("colorMapping", new SimpleObjectProperty<>(COLOR_MAPPING));
        return (B) this;
    }

    public final B eventRadius(final double EVENT_RADIUS) {
        this.properties.put("eventRadius", new SimpleDoubleProperty(EVENT_RADIUS));
        return (B) this;
    }

    public final B fadeColors(final boolean FADE_COLORS) {
        this.properties.put("fadeColors", new SimpleBooleanProperty(FADE_COLORS));
        return (B) this;
    }

    public final B fillColor(final Color COLOR) {
        this.properties.put("fillColor", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final B heatMapOpacity(final double HEAT_MAP_OPACITY) {
        this.properties.put("heatMapOpacity", new SimpleDoubleProperty(HEAT_MAP_OPACITY));
        return (B) this;
    }

    public final B hoverColor(final Color COLOR) {
        this.properties.put("hoverColor", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final B hoverEnabled(final boolean ENABLED) {
        this.properties.put("hoverEnabled", new SimpleBooleanProperty(ENABLED));
        return (B) this;
    }

    public final B layoutX(final double LAYOUT_X) {
        this.properties.put("layoutX", new SimpleDoubleProperty(LAYOUT_X));
        return (B) this;
    }

    public final B layoutY(final double LAYOUT_Y) {
        this.properties.put("layoutY", new SimpleDoubleProperty(LAYOUT_Y));
        return (B) this;
    }

    public final B locationColor(final Color COLOR) {
        this.properties.put("locationColor", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final B locationIconCode(final Ikon ICON_CODE) {
        this.properties.put("locationIconCode", new SimpleObjectProperty<>(ICON_CODE));
        return (B) this;
    }

    public final B locations(final Location... LOCATIONS) {
        this.properties.put("locations", new SimpleObjectProperty(LOCATIONS));
        return (B) this;
    }

    public final B maxHeight(final double MAX_HEIGHT) {
        this.properties.put("maxHeight", new SimpleDoubleProperty(MAX_HEIGHT));
        return (B) this;
    }

    public final B maxSize(final double WIDTH, final double HEIGHT) {
        this.properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B maxWidth(final double MAX_WIDTH) {
        this.properties.put("maxWidth", new SimpleDoubleProperty(MAX_WIDTH));
        return (B) this;
    }

    public final B minHeight(final double MIN_HEIGHT) {
        this.properties.put("minHeight", new SimpleDoubleProperty(MIN_HEIGHT));
        return (B) this;
    }

    public final B minSize(final double WIDTH, final double HEIGHT) {
        this.properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B minWidth(final double MIN_WIDTH) {
        this.properties.put("minWidth", new SimpleDoubleProperty(MIN_WIDTH));
        return (B) this;
    }

    public final B mouseEnterHandler(final EventHandler<MouseEvent> HANDLER) {
        this.properties.put("mouseEnterHandler", new SimpleObjectProperty(HANDLER));
        return (B) this;
    }

    public final B mouseExitHandler(final EventHandler<MouseEvent> HANDLER) {
        this.properties.put("mouseExitHandler", new SimpleObjectProperty(HANDLER));
        return (B) this;
    }

    public final B mousePressHandler(final EventHandler<MouseEvent> HANDLER) {
        this.properties.put("mousePressHandler", new SimpleObjectProperty(HANDLER));
        return (B) this;
    }

    public final B mouseReleaseHandler(final EventHandler<MouseEvent> HANDLER) {
        this.properties.put("mouseReleaseHandler", new SimpleObjectProperty(HANDLER));
        return (B) this;
    }

    public final B opacityDistribution(final OpacityDistribution OPACITY_DISTRIBUTION) {
        this.properties.put("opacityDistribution", new SimpleObjectProperty<>(OPACITY_DISTRIBUTION));
        return (B) this;
    }

    public final B padding(final Insets INSETS) {
        this.properties.put("padding", new SimpleObjectProperty<>(INSETS));
        return (B) this;
    }

    public final B prefHeight(final double PREF_HEIGHT) {
        this.properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B) this;
    }

    public final B prefSize(final double WIDTH, final double HEIGHT) {
        this.properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B prefWidth(final double PREF_WIDTH) {
        this.properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B) this;
    }

    public final B pressedColor(final Color COLOR) {
        this.properties.put("pressedColor", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final B resolution(final Resolution RESOLUTION) {
        this.resolution = RESOLUTION;
        return (B) this;
    }

    public final B scaleX(final double SCALE_X) {
        this.properties.put("scaleX", new SimpleDoubleProperty(SCALE_X));
        return (B) this;
    }

    public final B scaleY(final double SCALE_Y) {
        this.properties.put("scaleY", new SimpleDoubleProperty(SCALE_Y));
        return (B) this;
    }

    public final B selectedColor(final Color COLOR) {
        this.properties.put("selectedColor", new SimpleObjectProperty(COLOR));
        return (B) this;
    }

    public final B selectionEnabled(final boolean ENABLED) {
        this.properties.put("selectionEnabled", new SimpleBooleanProperty(ENABLED));
        return (B) this;
    }

    public final B showLocations(final boolean VISIBLE) {
        this.properties.put("showLocations", new SimpleBooleanProperty(VISIBLE));
        return (B) this;
    }

    public final B strokeColor(final Color COLOR) {
        this.properties.put("strokeColor", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final B translateX(final double TRANSLATE_X) {
        this.properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return (B) this;
    }

    public final B translateY(final double TRANSLATE_Y) {
        this.properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return (B) this;
    }

    public final B zoomEnabled(final boolean ENABLED) {
        this.properties.put("zoomEnabled", new SimpleBooleanProperty(ENABLED));
        return (B) this;
    }
}
