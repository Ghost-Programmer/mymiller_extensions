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

import javafx.application.Platform;
import javafx.beans.DefaultProperty;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.css.*;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.event.WeakEventHandler;
import javafx.geometry.*;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import name.mymiller.javafx.graph.heatmap.ColorMapping;
import name.mymiller.javafx.graph.heatmap.HeatMap;
import name.mymiller.javafx.graph.heatmap.HeatMapBuilder;
import name.mymiller.javafx.graph.heatmap.OpacityDistribution;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hansolo on 22.11.16.
 */
@DefaultProperty("children")
public class World extends Region {
	private static final StyleablePropertyFactory<World> FACTORY = new StyleablePropertyFactory<>(
			Region.getClassCssMetaData());
	private static final String HIRES_PROPERTIES = "name/mymiller/javafx/graph/world/hires.properties";
	private static final String LORES_PROPERTIES = "name/mymiller/javafx/graph/world/lores.properties";
	private static final double PREFERRED_WIDTH = 1009;
	private static final double PREFERRED_HEIGHT = 665;
	private static final double MINIMUM_WIDTH = 100;
	private static final double MINIMUM_HEIGHT = 66;
	private static final double MAXIMUM_WIDTH = 2018;
	private static final double MAXIMUM_HEIGHT = 1330;
	private static final double ASPECT_RATIO = World.PREFERRED_HEIGHT / World.PREFERRED_WIDTH;
	private static final CssMetaData<World, Color> BACKGROUND_COLOR = World.FACTORY
			.createColorCssMetaData("-background-color", s -> s.backgroundColor, Color.web("#3f3f4f"), false);
	private static final CssMetaData<World, Color> FILL_COLOR = World.FACTORY.createColorCssMetaData("-fill-color",
			s -> s.fillColor, Color.web("#d9d9dc"), false);
	private static final CssMetaData<World, Color> STROKE_COLOR = World.FACTORY.createColorCssMetaData("-stroke-color",
			s -> s.strokeColor, Color.BLACK, false);
	private static final CssMetaData<World, Color> HOVER_COLOR = World.FACTORY.createColorCssMetaData("-hover-color",
			s -> s.hoverColor, Color.web("#456acf"), false);
	private static final CssMetaData<World, Color> PRESSED_COLOR = World.FACTORY
			.createColorCssMetaData("-pressed-color", s -> s.pressedColor, Color.web("#789dff"), false);
	private static final CssMetaData<World, Color> SELECTED_COLOR = World.FACTORY
			.createColorCssMetaData("-selected-color", s -> s.selectedColor, Color.web("#9dff78"), false);
	private static final CssMetaData<World, Color> LOCATION_COLOR = World.FACTORY
			.createColorCssMetaData("-location-color", s -> s.locationColor, Color.web("#ff0000"), false);
	private static final double MAP_OFFSET_X = -World.PREFERRED_WIDTH * 0.0285;
	private static final double MAP_OFFSET_Y = World.PREFERRED_HEIGHT * 0.195;
	private final StyleableProperty<Color> backgroundColor;
	private final StyleableProperty<Color> fillColor;
	private final StyleableProperty<Color> strokeColor;
	private final StyleableProperty<Color> hoverColor;
	private final StyleableProperty<Color> pressedColor;
	private final StyleableProperty<Color> selectedColor;
	private final StyleableProperty<Color> locationColor;
	// internal event handlers
	protected EventHandler<MouseEvent> _mouseEnterHandler;
	protected EventHandler<MouseEvent> _mousePressHandler;
	protected EventHandler<MouseEvent> _mouseReleaseHandler;
	protected EventHandler<MouseEvent> _mouseExitHandler;
	private final BooleanProperty hoverEnabled;
	private final BooleanProperty selectionEnabled;
	private final ObjectProperty<Country> selectedCountry;
	private final BooleanProperty zoomEnabled;
	private final DoubleProperty scaleFactor;
	private final Properties resolutionProperties;
	private Country formerSelectedCountry;
	private double zoomSceneX;
	private double zoomSceneY;
	private double width;
	private double height;
	private Ikon locationIconCode;
	private final Pane pane;
	private final Group group;
	private HeatMap heatMap;
	private final Map<String, List<CountryPath>> countryPaths;
	private final ObservableMap<Location, Shape> locations;
	private final ColorMapping colorMapping;
	private final double eventRadius;
	private final boolean fadeColors;
	private final OpacityDistribution opacityDistribution;
	private final double heatMapOpacity;
	private final BooleanProperty heatMapVisible;
	private final EventHandler<ScrollEvent> _scrollEventHandler;
	// exposed event handlers
	private EventHandler<MouseEvent> mouseEnterHandler;
	private EventHandler<MouseEvent> mousePressHandler;
	private EventHandler<MouseEvent> mouseReleaseHandler;
	private EventHandler<MouseEvent> mouseExitHandler;

	// ******************** Constructors **************************************
	public World() {
		this(Resolution.HI_RES, ColorMapping.INFRARED_3, 5, false, OpacityDistribution.EXPONENTIAL, 0.75);
	}

	public World(final Resolution RESOLUTION) {
		this(RESOLUTION, ColorMapping.INFRARED_3, 5, false, OpacityDistribution.EXPONENTIAL, 0.75);
	}

	public World(final Resolution RESOLUTION, final ColorMapping COLOR_MAPPING, final double EVENT_RADIUS,
				 final boolean FADE_COLORS, final OpacityDistribution OPACITY_DISTRIBUTION, final double HEAT_MAP_OPACITY) {
		this.resolutionProperties = this
				.readProperties(Resolution.HI_RES == RESOLUTION ? World.HIRES_PROPERTIES : World.LORES_PROPERTIES);
		this.backgroundColor = new StyleableObjectProperty<>(World.BACKGROUND_COLOR.getInitialValue(World.this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.BACKGROUND_COLOR;
			}

			@Override
			public String getName() {
				return "backgroundColor";
			}

			@Override
			protected void invalidated() {
				World.this
						.setBackground(new Background(new BackgroundFill(this.get(), CornerRadii.EMPTY, Insets.EMPTY)));
			}
		};
		this.fillColor = new StyleableObjectProperty<>(World.FILL_COLOR.getInitialValue(World.this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.FILL_COLOR;
			}

			@Override
			public String getName() {
				return "fillColor";
			}

			@Override
			protected void invalidated() {
				World.this.setFillAndStroke();
			}
		};
		this.strokeColor = new StyleableObjectProperty<>(World.STROKE_COLOR.getInitialValue(World.this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.STROKE_COLOR;
			}

			@Override
			public String getName() {
				return "strokeColor";
			}

			@Override
			protected void invalidated() {
				World.this.setFillAndStroke();
			}
		};
		this.hoverColor = new StyleableObjectProperty<>(World.HOVER_COLOR.getInitialValue(World.this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.HOVER_COLOR;
			}

			@Override
			public String getName() {
				return "hoverColor";
			}

			@Override
			protected void invalidated() {
			}
		};
		this.pressedColor = new StyleableObjectProperty<>(World.PRESSED_COLOR.getInitialValue(this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.PRESSED_COLOR;
			}

			@Override
			public String getName() {
				return "pressedColor";
			}

			@Override
			protected void invalidated() {
			}
		};
		this.selectedColor = new StyleableObjectProperty<>(World.SELECTED_COLOR.getInitialValue(this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.SELECTED_COLOR;
			}

			@Override
			public String getName() {
				return "selectedColor";
			}

			@Override
			protected void invalidated() {
			}
		};
		this.locationColor = new StyleableObjectProperty<>(World.LOCATION_COLOR.getInitialValue(this)) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public CssMetaData<? extends Styleable, Color> getCssMetaData() {
				return World.LOCATION_COLOR;
			}

			@Override
			public String getName() {
				return "locationColor";
			}

			@Override
			protected void invalidated() {
				World.this.locations.forEach((location, shape) -> shape
						.setFill(null == location.getColor() ? this.get() : location.getColor()));
			}
		};
		this.hoverEnabled = new BooleanPropertyBase(true) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public String getName() {
				return "hoverEnabled";
			}

			@Override
			protected void invalidated() {
			}
		};
		this.selectionEnabled = new BooleanPropertyBase(false) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public String getName() {
				return "selectionEnabled";
			}

			@Override
			protected void invalidated() {
			}
		};
		this.selectedCountry = new ObjectPropertyBase<>() {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public String getName() {
				return "selectedCountry";
			}

			@Override
			protected void invalidated() {
			}
		};
		this.zoomEnabled = new BooleanPropertyBase(false) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public String getName() {
				return "zoomEnabled";
			}

			@Override
			protected void invalidated() {
				if (null == World.this.getScene()) {
					return;
				}
				if (this.get()) {
					World.this.getScene().addEventFilter(ScrollEvent.ANY, World.this._scrollEventHandler);
				} else {
					World.this.getScene().removeEventFilter(ScrollEvent.ANY, World.this._scrollEventHandler);
				}
			}
		};
		this.scaleFactor = new DoublePropertyBase(1.0) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public String getName() {
				return "scaleFactor";
			}

			@Override
			protected void invalidated() {
				if (World.this.isZoomEnabled()) {
					World.this.setScaleX(this.get());
					World.this.setScaleY(this.get());
				}
			}
		};
		this.countryPaths = this.createCountryPaths();
		this.locations = FXCollections.observableHashMap();
		this.colorMapping = COLOR_MAPPING;
		this.eventRadius = EVENT_RADIUS;
		this.fadeColors = FADE_COLORS;
		this.opacityDistribution = OPACITY_DISTRIBUTION;
		this.heatMapOpacity = HEAT_MAP_OPACITY;
		this.heatMapVisible = new BooleanPropertyBase(true) {
			@Override
			public Object getBean() {
				return World.this;
			}

			@Override
			public String getName() {
				return "heatMapVisible";
			}

			@Override
			protected void invalidated() {
				World.this.heatMap.setVisible(this.get());
				World.this.heatMap.setManaged(this.get());
			}
		};

		this.locationIconCode = MaterialDesign.MDI_CHECKBOX_BLANK_CIRCLE;
		this.pane = new Pane();
		this.group = new Group();

		this._mouseEnterHandler = evt -> this.handleMouseEvent(evt, this.mouseEnterHandler);
		this._mousePressHandler = evt -> this.handleMouseEvent(evt, this.mousePressHandler);
		this._mouseReleaseHandler = evt -> this.handleMouseEvent(evt, this.mouseReleaseHandler);
		this._mouseExitHandler = evt -> this.handleMouseEvent(evt, this.mouseExitHandler);
		this._scrollEventHandler = evt -> {
			if ((this.group.getTranslateX() != 0) || (this.group.getTranslateY() != 0)) {
				this.resetZoom();
			}
			final double delta = 1.2;
			double scale = this.getScaleFactor();
			final double oldScale = scale;
			scale = evt.getDeltaY() < 0 ? scale / delta : scale * delta;
			scale = this.clamp(1, 10, scale);
			final double factor = (scale / oldScale) - 1;
			if (Double.compare(1, this.getScaleFactor()) == 0) {
				this.zoomSceneX = evt.getSceneX();
				this.zoomSceneY = evt.getSceneY();
				this.resetZoom();
			}
			final double deltaX = (this.zoomSceneX
					- ((this.getBoundsInParent().getWidth() / 2) + this.getBoundsInParent().getMinX()));
			final double deltaY = (this.zoomSceneY
					- ((this.getBoundsInParent().getHeight() / 2) + this.getBoundsInParent().getMinY()));
			this.setScaleFactor(scale);
			this.setPivot(deltaX * factor, deltaY * factor);

			evt.consume();
		};

		this.initGraphics();
		this.registerListeners();
	}

	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return World.FACTORY.getCssMetaData();
	}

	public static double[] latLonToXY(final double LATITUDE, final double LONGITUDE) {
		final double x = ((LONGITUDE + 180) * (World.PREFERRED_WIDTH / 360)) + World.MAP_OFFSET_X;
		final double y = ((World.PREFERRED_HEIGHT / 2)
				- ((World.PREFERRED_WIDTH * (Math.log(Math.tan((Math.PI / 4) + (Math.toRadians(LATITUDE) / 2)))))
				/ (2 * Math.PI)))
				+ World.MAP_OFFSET_Y;
		return new double[]{x, y};
	}

	/**
	 * If you don't need to weight events you could use this method which will
	 * create events that always use the global weight
	 *
	 * @param X
	 * @param Y
	 */
	public void addEvent(final double X, final double Y) {
		this.heatMap.addEvent(X, Y);
	}

	/**
	 * Visualizes an event with the given radius and opacity gradient
	 *
	 * @param X
	 * @param Y
	 * @param OFFSET_X
	 * @param OFFSET_Y
	 * @param RADIUS
	 * @param OPACITY_GRADIENT
	 */
	public void addEvent(final double X, final double Y, final double OFFSET_X, final double OFFSET_Y,
						 final double RADIUS, final OpacityDistribution OPACITY_GRADIENT) {
		this.heatMap.addEvent(X, Y, OFFSET_X, OFFSET_Y, RADIUS, OPACITY_GRADIENT);
	}

	/**
	 * Visualizes an event with a given image at the given position and with the
	 * given offset. So one could use different weighted images for different kinds
	 * of events (e.g. important events more opaque as unimportant events)
	 *
	 * @param X
	 * @param Y
	 * @param EVENT_IMAGE
	 * @param OFFSET_X
	 * @param OFFSET_Y
	 */
	public void addEvent(final double X, final double Y, final Image EVENT_IMAGE, final double OFFSET_X,
						 final double OFFSET_Y) {
		this.heatMap.addEvent(X, Y, EVENT_IMAGE, OFFSET_X, OFFSET_Y);
	}

	/**
	 * Add a list of events and update the heatmap after all events have been added
	 *
	 * @param EVENTS
	 */
	public void addEvents(final List<Point2D> EVENTS) {
		this.heatMap.addEvents(EVENTS);
	}

	public void addEvents(final Point2D... EVENTS) {
		this.heatMap.addEvents(EVENTS);
	}

	public void addLocation(final Location LOCATION) {
		final double x = ((LOCATION.getLongitude() + 180) * (World.PREFERRED_WIDTH / 360)) + World.MAP_OFFSET_X;
		final double y = ((World.PREFERRED_HEIGHT / 2) - ((World.PREFERRED_WIDTH
				* (Math.log(Math.tan((Math.PI / 4) + (Math.toRadians(LOCATION.getLatitude()) / 2))))) / (2 * Math.PI)))
				+ World.MAP_OFFSET_Y;

		final FontIcon locationIcon = new FontIcon(
				null == LOCATION.getIconCode() ? this.locationIconCode : LOCATION.getIconCode());
		locationIcon.setIconSize(LOCATION.getIconSize());
		locationIcon.setTextOrigin(VPos.CENTER);
		locationIcon.setIconColor(null == LOCATION.getColor() ? this.getLocationColor() : LOCATION.getColor());
		locationIcon.setX(x - (LOCATION.getIconSize() * 0.5));
		locationIcon.setY(y);

		final StringBuilder tooltipBuilder = new StringBuilder();
		if (!LOCATION.getName().isEmpty()) {
			tooltipBuilder.append(LOCATION.getName());
		}
		if (!LOCATION.getInfo().isEmpty()) {
			tooltipBuilder.append("\n").append(LOCATION.getInfo());
		}
		final String tooltipText = tooltipBuilder.toString();
		if (!tooltipText.isEmpty()) {
			final Tooltip tooltip = new Tooltip(tooltipText);
			tooltip.setFont(Font.font(10));
			Tooltip.install(locationIcon, tooltip);
		}

		if (null != LOCATION.getMouseEnterHandler()) {
			locationIcon.setOnMouseEntered(new WeakEventHandler<>(LOCATION.getMouseEnterHandler()));
		}
		if (null != LOCATION.getMousePressHandler()) {
			locationIcon.setOnMousePressed(new WeakEventHandler<>(LOCATION.getMousePressHandler()));
		}
		if (null != LOCATION.getMouseReleaseHandler()) {
			locationIcon.setOnMouseReleased(new WeakEventHandler<>(LOCATION.getMouseReleaseHandler()));
		}
		if (null != LOCATION.getMouseExitHandler()) {
			locationIcon.setOnMouseExited(new WeakEventHandler<>(LOCATION.getMouseExitHandler()));
		}

		this.locations.put(LOCATION, locationIcon);
	}

	public void addLocations(final Location... LOCATIONS) {
		for (final Location location : LOCATIONS) {
			this.addLocation(location);
		}
	}

	private void addShapesToScene(final Collection<Shape> SHAPES) {
		if (null == this.getScene()) {
			return;
		}
		Platform.runLater(() -> this.pane.getChildren().addAll(SHAPES));
	}

	private void addShapesToScene(final Shape... SHAPES) {
		this.addShapesToScene(Arrays.asList(SHAPES));
	}

	public ObjectProperty<Color> backgroundColorProperty() {
		return (ObjectProperty<Color>) this.backgroundColor;
	}

	private double clamp(final double MIN, final double MAX, final double VALUE) {
		if (VALUE < MIN) {
			return MIN;
		}
		if (VALUE > MAX) {
			return MAX;
		}
		return VALUE;
	}

	/**
	 * Calling this method will lead to a clean new heat map without any data
	 */
	public void clearHeatMap() {
		this.heatMap.clearHeatMap();
	}

	public void clearLocations() {
		this.locations.clear();
	}

	@Override
	protected double computeMaxHeight(final double WIDTH) {
		return World.MAXIMUM_HEIGHT;
	}

	@Override
	protected double computeMaxWidth(final double HEIGHT) {
		return World.MAXIMUM_WIDTH;
	}

	@Override
	protected double computeMinHeight(final double WIDTH) {
		return World.MINIMUM_HEIGHT;
	}

	// ******************** Methods *******************************************
	@Override
	protected double computeMinWidth(final double HEIGHT) {
		return World.MINIMUM_WIDTH;
	}

	@Override
	protected double computePrefHeight(final double WIDTH) {
		return super.computePrefHeight(WIDTH);
	}

	@Override
	protected double computePrefWidth(final double HEIGHT) {
		return super.computePrefWidth(HEIGHT);
	}

	private Map<String, List<CountryPath>> createCountryPaths() {
		final Map<String, List<CountryPath>> countryPaths = new HashMap<>();
		this.resolutionProperties.forEach((key, value) -> {
			final String name = key.toString();
			final List<CountryPath> pathList = new ArrayList<>();
			for (final String path : value.toString().split(";")) {
				pathList.add(new CountryPath(name, path));
			}
			countryPaths.put(name, pathList);
		});
		return countryPaths;
	}

	public ObjectProperty<Color> fillColorProperty() {
		return (ObjectProperty<Color>) this.fillColor;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor.getValue();
	}

	public void setBackgroundColor(final Color COLOR) {
		this.backgroundColor.setValue(COLOR);
	}

	private double[] getBounds(final Country... COUNTRIES) {
		return this.getBounds(Arrays.asList(COUNTRIES));
	}

	private double[] getBounds(final List<Country> COUNTRIES) {
		double upperLeftX = World.PREFERRED_WIDTH;
		double upperLeftY = World.PREFERRED_HEIGHT;
		double lowerRightX = 0;
		double lowerRightY = 0;
		for (final Country country : COUNTRIES) {
			final List<CountryPath> paths = this.countryPaths.get(country.getName());
			for (int i = 0; i < paths.size(); i++) {
				final CountryPath path = paths.get(i);
				final Bounds bounds = path.getLayoutBounds();
				upperLeftX = Math.min(bounds.getMinX(), upperLeftX);
				upperLeftY = Math.min(bounds.getMinY(), upperLeftY);
				lowerRightX = Math.max(bounds.getMaxX(), lowerRightX);
				lowerRightY = Math.max(bounds.getMaxY(), lowerRightY);
			}
		}
		return new double[]{upperLeftX, upperLeftY, lowerRightX, lowerRightY};
	}

	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildren();
	}

	/**
	 * Returns the used color mapping with the gradient that is used to visualize
	 * the data
	 *
	 * @return
	 */
	public ColorMapping getColorMapping() {
		return this.heatMap.getColorMapping();
	}

	/**
	 * The ColorMapping enum contains some examples for color mappings that might be
	 * useful to visualize data and here you could set the one you like most.
	 * Setting another color mapping will recreate the heat map automatically.
	 *
	 * @param COLOR_MAPPING
	 */
	public void setColorMapping(final ColorMapping COLOR_MAPPING) {
		this.heatMap.setColorMapping(COLOR_MAPPING);
	}

	public Map<String, List<CountryPath>> getCountryPaths() {
		return this.countryPaths;
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return World.FACTORY.getCssMetaData();
	}

	/**
	 * Returns the radius of the circle that is used to visualize an event.
	 *
	 * @return the radius of the circle that is used to visualize an event
	 */
	public double getEventRadius() {
		return this.heatMap.getEventRadius();
	}

	/**
	 * Each event will be visualized by a circle filled with a radial gradient with
	 * decreasing opacity from the inside to the outside. If you have lot's of
	 * events it makes sense to set the event radius to a smaller value. The default
	 * value is 15.5
	 *
	 * @param RADIUS
	 */
	public void setEventRadius(final double RADIUS) {
		this.heatMap.setEventRadius(RADIUS);
	}

	public Color getFillColor() {
		return this.fillColor.getValue();
	}

	public void setFillColor(final Color COLOR) {
		this.fillColor.setValue(COLOR);
	}

	public HeatMap getHeatMap() {
		return this.heatMap;
	}

	public double getHeatMapOpacity() {
		return this.heatMap.getOpacity();
	}

	public void setHeatMapOpacity(final double OPACITY) {
		this.heatMap.setOpacity(this.clamp(0.0, 1.0, OPACITY));
	}

	public Color getHoverColor() {
		return this.hoverColor.getValue();
	}

	public void setHoverColor(final Color COLOR) {
		this.hoverColor.setValue(COLOR);
	}

	public Color getLocationColor() {
		return this.locationColor.getValue();
	}

	public void setLocationColor(final Color COLOR) {
		this.locationColor.setValue(COLOR);
	}

	public Ikon getLocationIconCode() {
		return this.locationIconCode;
	}

	public void setLocationIconCode(final Ikon ICON_CODE) {
		this.locationIconCode = ICON_CODE;
	}

	/**
	 * Returns the opacity distribution that will be used to visualize the events in
	 * the monochrome map. If you have lot's of events it makes sense to reduce the
	 * radius and the set the opacity distribution to exponential.
	 *
	 * @return the opacity distribution of events in the monochrome map
	 */
	public OpacityDistribution getOpacityDistribution() {
		return this.heatMap.getOpacityDistribution();
	}

	/**
	 * Changing the opacity distribution will affect the smoothing of the heat map.
	 * If you choose a linear opacity distribution you will see bigger colored dots
	 * for each event than using the exponential opacity distribution (at the same
	 * event radius).
	 *
	 * @param OPACITY_DISTRIBUTION
	 */
	public void setOpacityDistribution(final OpacityDistribution OPACITY_DISTRIBUTION) {
		this.heatMap.setOpacityDistribution(OPACITY_DISTRIBUTION);
	}

	public Color getPressedColor() {
		return this.pressedColor.getValue();
	}

	public void setPressedColor(final Color COLOR) {
		this.pressedColor.setValue(COLOR);
	}

	public double getScaleFactor() {
		return this.scaleFactor.get();
	}

	public void setScaleFactor(final double FACTOR) {
		this.scaleFactor.set(FACTOR);
	}

	public Color getSelectedColor() {
		return this.selectedColor.getValue();
	}

	public void setSelectedColor(final Color COLOR) {
		this.selectedColor.setValue(COLOR);
	}

	public Country getSelectedCountry() {
		return this.selectedCountry.get();
	}

	public void setSelectedCountry(final Country COUNTRY) {
		this.selectedCountry.set(COUNTRY);
	}

	public Color getStrokeColor() {
		return this.strokeColor.getValue();
	}

	public void setStrokeColor(final Color COLOR) {
		this.strokeColor.setValue(COLOR);
	}

	// ******************** Style related *************************************
	@Override
	public String getUserAgentStylesheet() {
		return World.class.getResource("world.css").toExternalForm();
	}

	private void handleMouseEvent(final MouseEvent EVENT, final EventHandler<MouseEvent> HANDLER) {
		final CountryPath COUNTRY_PATH = (CountryPath) EVENT.getSource();
		final String COUNTRY_NAME = COUNTRY_PATH.getName();
		final Country COUNTRY = Country.valueOf(COUNTRY_NAME);
		final List<CountryPath> PATHS = this.countryPaths.get(COUNTRY_NAME);

		final EventType TYPE = EVENT.getEventType();
		if (MouseEvent.MOUSE_ENTERED == TYPE) {
			if (this.isHoverEnabled()) {
				final Color color = this.isSelectionEnabled() && COUNTRY.equals(this.getSelectedCountry())
						? this.getSelectedColor()
						: this.getHoverColor();
				for (final SVGPath path : PATHS) {
					path.setFill(color);
				}
			}
		} else if (MouseEvent.MOUSE_PRESSED == TYPE) {
			if (this.isSelectionEnabled()) {
				Color color;
				if (null == this.getSelectedCountry()) {
					this.setSelectedCountry(COUNTRY);
					color = this.getSelectedColor();
				} else {
					color = null == this.getSelectedCountry().getColor() ? this.getFillColor()
							: this.getSelectedCountry().getColor();
				}
				for (final SVGPath path : this.countryPaths.get(this.getSelectedCountry().getName())) {
					path.setFill(color);
				}
			} else {
				if (this.isHoverEnabled()) {
					for (final SVGPath path : PATHS) {
						path.setFill(this.getPressedColor());
					}
				}
			}
		} else if (MouseEvent.MOUSE_RELEASED == TYPE) {
			Color color;
			if (this.isSelectionEnabled()) {
				if (this.formerSelectedCountry == COUNTRY) {
					this.setSelectedCountry(null);
					color = null == COUNTRY.getColor() ? this.getFillColor() : COUNTRY.getColor();
				} else {
					this.setSelectedCountry(COUNTRY);
					color = this.getSelectedColor();
				}
				this.formerSelectedCountry = this.getSelectedCountry();
			} else {
				color = this.getHoverColor();
			}
			if (this.isHoverEnabled()) {
				for (final SVGPath path : PATHS) {
					path.setFill(color);
				}
			}
		} else if (MouseEvent.MOUSE_EXITED == TYPE) {
			if (this.isHoverEnabled()) {
				final Color color = this.isSelectionEnabled() && COUNTRY.equals(this.getSelectedCountry())
						? this.getSelectedColor()
						: this.getFillColor();
				for (final SVGPath path : PATHS) {
					path.setFill((null == COUNTRY.getColor()) || (COUNTRY == this.getSelectedCountry()) ? color
							: COUNTRY.getColor());
				}
			}
		}

		if (null != HANDLER) {
			HANDLER.handle(EVENT);
		}
	}

	public BooleanProperty heatMapVisibleProperty() {
		return this.heatMapVisible;
	}

	public ObjectProperty<Color> hoverColorProperty() {
		return (ObjectProperty<Color>) this.hoverColor;
	}

	public BooleanProperty hoverEnabledProperty() {
		return this.hoverEnabled;
	}

	// ******************** Initialization ************************************
	private void initGraphics() {
		if ((Double.compare(this.getPrefWidth(), 0.0) <= 0) || (Double.compare(this.getPrefHeight(), 0.0) <= 0)
				|| (Double.compare(this.getWidth(), 0.0) <= 0) || (Double.compare(this.getHeight(), 0.0) <= 0)) {
			if ((this.getPrefWidth() > 0) && (this.getPrefHeight() > 0)) {
				this.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
			} else {
				this.setPrefSize(World.PREFERRED_WIDTH, World.PREFERRED_HEIGHT);
			}
		}

		this.getStyleClass().add("world");

		final Color fill = this.getFillColor();
		final Color stroke = this.getStrokeColor();

		this.countryPaths.forEach((name, pathList) -> {
			final Country country = Country.valueOf(name);
			pathList.forEach(path -> {
				path.setFill(null == country.getColor() ? fill : country.getColor());
				path.setStroke(stroke);
				path.setStrokeWidth(0.2);
				path.setOnMouseEntered(new WeakEventHandler<>(this._mouseEnterHandler));
				path.setOnMousePressed(new WeakEventHandler<>(this._mousePressHandler));
				path.setOnMouseReleased(new WeakEventHandler<>(this._mouseReleaseHandler));
				path.setOnMouseExited(new WeakEventHandler<>(this._mouseExitHandler));
			});
			this.pane.getChildren().addAll(pathList);
		});

		this.group.getChildren().add(this.pane);

		this.heatMap = HeatMapBuilder.create().prefSize(1009, 665).colorMapping(this.colorMapping)
				.eventRadius(this.eventRadius).fadeColors(this.fadeColors).opacityDistribution(this.opacityDistribution)
				.heatMapOpacity(this.heatMapOpacity).build();

		this.getChildren().setAll(this.group, this.heatMap);

		this.setBackground(
				new Background(new BackgroundFill(this.getBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/**
	 * Returns true if the heat map is used to visualize frequencies (default)
	 *
	 * @return true if the heat map is used to visualize frequencies
	 */
	public boolean isFadeColors() {
		return this.heatMap.isFadeColors();
	}

	/**
	 * If true each event will be visualized by a radial gradient with the colors
	 * from the given color mapping and decreasing opacity from the inside to the
	 * outside. If you set it to false the color opacity won't fade out but will be
	 * opaque. This might be handy if you would like to visualize the density
	 * instead of the frequency
	 *
	 * @param FADE_COLORS
	 */
	public void setFadeColors(final boolean FADE_COLORS) {
		this.heatMap.setFadeColors(FADE_COLORS);
	}

	public boolean isHeatMapVisible() {
		return this.heatMapVisible.get();
	}

	public void setHeatMapVisible(final boolean VISIBLE) {
		this.heatMapVisible.set(VISIBLE);
	}

	public boolean isHoverEnabled() {
		return this.hoverEnabled.get();
	}

	public void setHoverEnabled(final boolean ENABLED) {
		this.hoverEnabled.set(ENABLED);
	}

	public boolean isSelectionEnabled() {
		return this.selectionEnabled.get();
	}

	public void setSelectionEnabled(final boolean ENABLED) {
		this.selectionEnabled.set(ENABLED);
	}

	public boolean isZoomEnabled() {
		return this.zoomEnabled.get();
	}

	public void setZoomEnabled(final boolean ENABLED) {
		this.zoomEnabled.set(ENABLED);
	}

	public ObjectProperty<Color> locationColorProperty() {
		return (ObjectProperty<Color>) this.locationColor;
	}

	public ObjectProperty<Color> pressedColorProperty() {
		return (ObjectProperty<Color>) this.pressedColor;
	}

	private Properties readProperties(final String FILE_NAME) {
		final ClassLoader LOADER = Thread.currentThread().getContextClassLoader();
		final Properties PROPERTIES = new Properties();
		try (InputStream resourceStream = LOADER.getResourceAsStream(FILE_NAME)) {
			PROPERTIES.load(resourceStream);
		} catch (final IOException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,"Error Reading Properties",exception);
		}
		return PROPERTIES;
	}

	private void registerListeners() {
		this.widthProperty().addListener(o -> this.resize());
		this.heightProperty().addListener(o -> this.resize());
		this.sceneProperty().addListener(o -> {
			if (!this.locations.isEmpty()) {
				this.addShapesToScene(this.locations.values());
			}
			if (this.isZoomEnabled()) {
				this.getScene().addEventFilter(ScrollEvent.ANY, new WeakEventHandler<>(this._scrollEventHandler));
			}

			this.locations.addListener((MapChangeListener<Location, Shape>) CHANGE -> {
				if (CHANGE.wasAdded()) {
					this.addShapesToScene(CHANGE.getValueAdded());
				} else if (CHANGE.wasRemoved()) {
					Platform.runLater(() -> this.pane.getChildren().remove(CHANGE.getValueRemoved()));
				}
			});
		});
	}

	public void removeLocation(final Location LOCATION) {
		this.locations.remove(LOCATION);
	}

	public void resetZoom() {
		this.setScaleFactor(1.0);
		this.setTranslateX(0);
		this.setTranslateY(0);
		this.group.setTranslateX(0);
		this.group.setTranslateY(0);
	}

	// ******************** Resizing ******************************************
	private void resize() {
		this.width = this.getWidth() - this.getInsets().getLeft() - this.getInsets().getRight();
		this.height = this.getHeight() - this.getInsets().getTop() - this.getInsets().getBottom();

		if ((World.ASPECT_RATIO * this.width) > this.height) {
			this.width = 1 / (World.ASPECT_RATIO / this.height);
		} else if ((1 / (World.ASPECT_RATIO / this.height)) > this.width) {
			this.height = World.ASPECT_RATIO * this.width;
		}

		if ((this.width > 0) && (this.height > 0)) {
			if (this.isZoomEnabled()) {
				this.resetZoom();
			}

			this.pane.setCache(true);
			this.pane.setCacheHint(CacheHint.SCALE);

			this.pane.setScaleX(this.width / World.PREFERRED_WIDTH);
			this.pane.setScaleY(this.height / World.PREFERRED_HEIGHT);

			this.group.resize(this.width, this.height);
			this.group.relocate((this.getWidth() - this.width) * 0.5, (this.getHeight() - this.height) * 0.5);

			this.heatMap.setSize(this.width, this.height);
			this.heatMap.relocate(
					((this.getWidth() - this.getInsets().getLeft() - this.getInsets().getRight()) - this.width) * 0.5,
					((this.getHeight() - this.getInsets().getTop() - this.getInsets().getBottom()) - this.height)
							* 0.5);

			this.pane.setCache(false);
		}
	}

	public DoubleProperty scaleFactorProperty() {
		return this.scaleFactor;
	}

	public ObjectProperty<Color> selectedColorProperty() {
		return (ObjectProperty<Color>) this.selectedColor;
	}

	public ObjectProperty<Country> selectedCountryProperty() {
		return this.selectedCountry;
	}

	public BooleanProperty selectionEnabledProperty() {
		return this.selectionEnabled;
	}

	private void setCountryFillAndStroke(final Country COUNTRY, final Color FILL, final Color STROKE) {
		final List<CountryPath> paths = this.countryPaths.get(COUNTRY.getName());
		for (final CountryPath path : paths) {
			path.setFill(FILL);
			path.setStroke(STROKE);
		}
	}

	private void setFillAndStroke() {
		this.countryPaths.keySet().forEach(name -> {
			final Country country = Country.valueOf(name);
			this.setCountryFillAndStroke(country, null == country.getColor() ? this.getFillColor() : country.getColor(),
					this.getStrokeColor());
		});
	}

	public void setMouseEnterHandler(final EventHandler<MouseEvent> HANDLER) {
		this.mouseEnterHandler = HANDLER;
	}

	public void setMouseExitHandler(final EventHandler<MouseEvent> HANDLER) {
		this.mouseExitHandler = HANDLER;
	}

	public void setMousePressHandler(final EventHandler<MouseEvent> HANDLER) {
		this.mousePressHandler = HANDLER;
	}

	public void setMouseReleaseHandler(final EventHandler<MouseEvent> HANDLER) {
		this.mouseReleaseHandler = HANDLER;
	}

	private void setPivot(final double X, final double Y) {
		this.setTranslateX(this.getTranslateX() - X);
		this.setTranslateY(this.getTranslateY() - Y);
	}

	public void showLocations(final boolean SHOW) {
		for (final Shape shape : this.locations.values()) {
			shape.setManaged(SHOW);
			shape.setVisible(SHOW);
		}
	}

	public ObjectProperty<Color> strokeColorProperty() {
		return (ObjectProperty<Color>) this.strokeColor;
	}

	public BooleanProperty zoomEnabledProperty() {
		return this.zoomEnabled;
	}

	private void zoomToArea(final double[] BOUNDS) {
		this.group.setTranslateX(0);
		this.group.setTranslateY(0);
		final double areaWidth = BOUNDS[2] - BOUNDS[0];
		final double areaHeight = BOUNDS[3] - BOUNDS[1];
		final double areaCenterX = BOUNDS[0] + (areaWidth * 0.5);
		final double areaCenterY = BOUNDS[1] + (areaHeight * 0.5);
		final Orientation orientation = areaWidth < areaHeight ? Orientation.VERTICAL : Orientation.HORIZONTAL;
		double sf = 1.0;
		switch (orientation) {
			case VERTICAL:
				sf = this.clamp(1.0, 10.0, 1 / (areaHeight / this.height));
				break;
			case HORIZONTAL:
				sf = this.clamp(1.0, 10.0, 1 / (areaWidth / this.width));
				break;
		}

		/*
		 * Rectangle bounds = new Rectangle(BOUNDS[0], BOUNDS[1], areaWidth,
		 * areaHeight); bounds.setFill(Color.TRANSPARENT); bounds.setStroke(Color.RED);
		 * bounds.setStrokeWidth(0.5); bounds.setMouseTransparent(true);
		 * group.getChildren().add(bounds);
		 */

		this.setScaleFactor(sf);
		this.group.setTranslateX((this.width * 0.5) - (areaCenterX));
		this.group.setTranslateY((this.height * 0.5) - (areaCenterY));
	}

	public void zoomToCountry(final Country COUNTRY) {
		if (!this.isZoomEnabled()) {
			return;
		}
		if (null != this.getSelectedCountry()) {
			this.setCountryFillAndStroke(this.getSelectedCountry(), this.getFillColor(), this.getStrokeColor());
		}
		this.zoomToArea(this.getBounds(COUNTRY));
	}

	public void zoomToRegion(final CRegion REGION) {
		if (!this.isZoomEnabled()) {
			return;
		}
		if (null != this.getSelectedCountry()) {
			this.setCountryFillAndStroke(this.getSelectedCountry(), this.getFillColor(), this.getStrokeColor());
		}
		this.zoomToArea(this.getBounds(REGION.getCountries()));
	}

	public enum Resolution {
		HI_RES, LO_RES
	}
}
