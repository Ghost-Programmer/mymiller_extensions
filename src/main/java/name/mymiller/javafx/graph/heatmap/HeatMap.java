/*
 * This file originated from: https://github.com/HanSolo/worldheatmap
 * This belongs and credited to Gerrit Grunwald, and I thank him for
 * his excellent work in extending and teaching JavaFX.
 */

/*
 * Copyright (c) 2014 by Gerrit Grunwald
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

package name.mymiller.javafx.graph.heatmap;

import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by User: hansolo Date: 27.12.12 Time: 05:46
 */
public class HeatMap extends ImageView {
	private static final SnapshotParameters SNAPSHOT_PARAMETERS = new SnapshotParameters();
	private final List<HeatMapEvent> eventList;
	private final Map<String, Image> eventImages;
	private final Canvas monochrome;
	private final GraphicsContext ctx;
	private ColorMapping colorMapping;
	private LinearGradient mappingGradient;
	private boolean fadeColors;
	private double radius;
	private OpacityDistribution opacityDistribution;
	private Image eventImage;
	private WritableImage monochromeImage;
	private WritableImage heatMap;

	// ******************** Constructors **************************************
	public HeatMap() {
		this(100, 100);
	}

	public HeatMap(final double WIDTH, final double HEIGHT) {
		this(WIDTH, HEIGHT, ColorMapping.LIME_YELLOW_RED);
	}

	public HeatMap(final double WIDTH, final double HEIGHT, ColorMapping COLOR_MAPPING) {
		this(WIDTH, HEIGHT, COLOR_MAPPING, 15.5);
	}

	public HeatMap(final double WIDTH, final double HEIGHT, ColorMapping COLOR_MAPPING, final double EVENT_RADIUS) {
		this(WIDTH, HEIGHT, COLOR_MAPPING, EVENT_RADIUS, true, 0.5, OpacityDistribution.CUSTOM);
	}

	public HeatMap(final double WIDTH, final double HEIGHT, ColorMapping COLOR_MAPPING, final double EVENT_RADIUS,
				   final boolean FADE_COLORS, final double HEAT_MAP_OPACITY, final OpacityDistribution OPACITY_DISTRIBUTION) {
		super();
		HeatMap.SNAPSHOT_PARAMETERS.setFill(Color.TRANSPARENT);
		this.eventList = new ArrayList<>();
		this.eventImages = new HashMap<>();
		this.colorMapping = COLOR_MAPPING;
		this.mappingGradient = this.colorMapping.mapping;
		this.fadeColors = FADE_COLORS;
		this.radius = EVENT_RADIUS;
		this.opacityDistribution = OPACITY_DISTRIBUTION;
		this.eventImage = this.createEventImage(this.radius, this.opacityDistribution);
		this.monochrome = new Canvas(WIDTH, HEIGHT);
		this.ctx = this.monochrome.getGraphicsContext2D();
		this.monochromeImage = new WritableImage((int) WIDTH, (int) HEIGHT);
		this.setImage(this.heatMap);
		this.setMouseTransparent(true);
		this.setOpacity(HEAT_MAP_OPACITY);
		this.registerListeners();
	}

	/**
	 * If you don't need to weight events you could use this method which will
	 * create events that always use the global weight
	 *
	 * @param X
	 * @param Y
	 */
	public void addEvent(final double X, final double Y) {
		this.addEvent(X, Y, this.eventImage, this.radius, this.radius);
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
		this.eventImage = this.createEventImage(RADIUS, OPACITY_GRADIENT);
		this.addEvent(X, Y, this.eventImage, OFFSET_X, OFFSET_Y);
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
		this.eventList.add(new HeatMapEvent(X, Y, this.radius, this.opacityDistribution));
		this.ctx.drawImage(EVENT_IMAGE, X - OFFSET_X, Y - OFFSET_Y);
		this.updateHeatMap();
	}

	/**
	 * Add a list of events and update the heatmap after all events have been added
	 *
	 * @param EVENTS
	 */
	public void addEvents(final List<Point2D> EVENTS) {
		EVENTS.forEach(event -> {
			this.eventList.add(new HeatMapEvent(event.getX(), event.getY(), this.radius, this.opacityDistribution));
			this.ctx.drawImage(this.eventImage, event.getX() - this.radius, event.getY() - this.radius);
		});
		this.updateHeatMap();
	}

	// ******************** Methods *******************************************

	/**
	 * Add a list of events and update the heatmap after all events have been added
	 *
	 * @param EVENTS
	 */
	public void addEvents(final Point2D... EVENTS) {
		this.addEvents(Arrays.asList(EVENTS));
	}

	/**
	 * Calling this method will lead to a clean new heat map without any data
	 */
	public void clearHeatMap() {
		this.eventList.clear();
		this.ctx.clearRect(0, 0, this.monochrome.getWidth(), this.monochrome.getHeight());
		this.monochromeImage = new WritableImage(this.monochrome.widthProperty().intValue(),
				this.monochrome.heightProperty().intValue());
		this.updateHeatMap();
	}

	/**
	 * Create an image that contains a circle filled with a radial gradient from
	 * white to transparent
	 *
	 * @param RADIUS
	 * @return an image that contains a filled circle
	 */
	public Image createEventImage(final double RADIUS, final OpacityDistribution OPACITY_DISTRIBUTION) {
		final Double radius = RADIUS < 1 ? 1 : RADIUS;
		if (this.eventImages.containsKey(OPACITY_DISTRIBUTION.name() + radius)) {
			return this.eventImages.get(OPACITY_DISTRIBUTION.name() + radius);
		}
		final Stop[] stops = new Stop[11];
		for (int i = 0; i < 11; i++) {
			stops[i] = new Stop(i * 0.1, Color.rgb(255, 255, 255, OPACITY_DISTRIBUTION.distribution[i]));
		}

		final int size = (int) (radius * 2);
		final WritableImage raster = new WritableImage(size, size);
		final PixelWriter pixelWriter = raster.getPixelWriter();
		final double maxDistFactor = 1 / radius;
		Color pixelColor;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				final double deltaX = radius - x;
				final double deltaY = radius - y;
				final double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
				final double fraction = maxDistFactor * distance;
				for (int i = 0; i < 10; i++) {
					if ((Double.compare(fraction, stops[i].getOffset()) >= 0)
							&& (Double.compare(fraction, stops[i + 1].getOffset()) <= 0)) {
						pixelColor = (Color) Interpolator.LINEAR.interpolate(stops[i].getColor(),
								stops[i + 1].getColor(), (fraction - stops[i].getOffset()) / 0.1);
						pixelWriter.setColor(x, y, pixelColor);
						break;
					}
				}
			}
		}
		this.eventImages.put(OPACITY_DISTRIBUTION.name() + radius, raster);
		return raster;
	}

	/**
	 * Calculates the color in a linear gradient at the given fraction
	 *
	 * @param GRADIENT
	 * @param FRACTION
	 * @return the color in a linear gradient at the given fraction
	 */
	private Color getColorAt(final LinearGradient GRADIENT, final double FRACTION) {
		final List<Stop> stops = GRADIENT.getStops();
		final double fraction = FRACTION < 0f ? 0f : (FRACTION > 1 ? 1 : FRACTION);
		Stop lowerStop = new Stop(0.0, stops.get(0).getColor());
		Stop upperStop = new Stop(1.0, stops.get(stops.size() - 1).getColor());

		for (final Stop stop : stops) {
			final double currentFraction = stop.getOffset();
			if (Helper.equals(currentFraction, fraction)) {
				return stop.getColor();
			} else if (Helper.lessThan(currentFraction, fraction)) {
				lowerStop = new Stop(currentFraction, stop.getColor());
			} else {
				upperStop = new Stop(currentFraction, stop.getColor());
				break;
			}
		}

		final double interpolationFraction = (fraction - lowerStop.getOffset())
				/ (upperStop.getOffset() - lowerStop.getOffset());
		return (Color) Interpolator.LINEAR.interpolate(lowerStop.getColor(), upperStop.getColor(),
				interpolationFraction);
	}

	/**
	 * Returns the used color mapping with the gradient that is used to visualize
	 * the data
	 *
	 * @return
	 */
	public ColorMapping getColorMapping() {
		return this.colorMapping;
	}

	/**
	 * The ColorMapping enum contains some examples for color mappings that might be
	 * useful to visualize data and here you could set the one you like most.
	 * Setting another color mapping will recreate the heat map automatically.
	 *
	 * @param COLOR_MAPPING
	 */
	public void setColorMapping(final ColorMapping COLOR_MAPPING) {
		this.colorMapping = COLOR_MAPPING;
		this.mappingGradient = COLOR_MAPPING.mapping;
		this.updateHeatMap();
	}

	/**
	 * Returns the radius of the circle that is used to visualize an event.
	 *
	 * @return the radius of the circle that is used to visualize an event
	 */
	public double getEventRadius() {
		return this.radius;
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
		this.radius = RADIUS < 1 ? 1 : RADIUS;
		this.eventImage = this.createEventImage(this.radius, this.opacityDistribution);
	}

	/**
	 * Returns the opacity distribution that will be used to visualize the events in
	 * the monochrome map. If you have lot's of events it makes sense to reduce the
	 * radius and the set the opacity distribution to exponential.
	 *
	 * @return the opacity distribution of events in the monochrome map
	 */
	public OpacityDistribution getOpacityDistribution() {
		return this.opacityDistribution;
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
		this.opacityDistribution = OPACITY_DISTRIBUTION;
		this.eventImage = this.createEventImage(this.radius, this.opacityDistribution);
	}

	/**
	 * Returns true if the heat map is used to visualize frequencies (default)
	 *
	 * @return true if the heat map is used to visualize frequencies
	 */
	public boolean isFadeColors() {
		return this.fadeColors;
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
		this.fadeColors = FADE_COLORS;
		this.updateHeatMap();
	}

	public void registerListeners() {
		this.fitWidthProperty().addListener(observable -> this.resize());
		this.fitHeightProperty().addListener(observable -> this.resize());
	}

	private void resize() {
		final double width = this.getFitWidth();
		final double height = this.getFitHeight();

		this.monochrome.resize(width, height);

		if ((width > 0) && (height > 0)) {
			this.monochromeImage = new WritableImage(this.monochrome.widthProperty().intValue(),
					this.monochrome.heightProperty().intValue());
			this.updateHeatMap();
		}
	}

	/**
	 * Saves the given node as png with the given name to the desktop folder of the
	 * current user
	 *
	 * @param NODE
	 * @param FILE_NAME
	 */
	public void saveAsPng(final Node NODE, final String FILE_NAME) {
		new Thread(() -> Platform.runLater(() -> {
			final String TARGET = System.getProperty("user.home") + "/Desktop/" + FILE_NAME + ".png";
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(NODE.snapshot(HeatMap.SNAPSHOT_PARAMETERS, null), null), "png",
						new File(TARGET));
			} catch (final IOException exception) {
				// handle exception here
			}
		})).start();
	}

	/**
	 * Saves the current heat map image as png with the given name to the desktop
	 * folder of the current user
	 *
	 * @param FILE_NAME
	 */
	public void saveAsPng(final String FILE_NAME) {
		this.saveAsPng(this, FILE_NAME + ".png");
	}

	/**
	 * Because the heat map is based on images you have to create a new writeable
	 * image each time you would like to change the size of the heatmap
	 *
	 * @param WIDTH
	 * @param HEIGHT
	 */
	public void setSize(final double WIDTH, final double HEIGHT) {
		this.setFitWidth(WIDTH);
		this.setFitHeight(HEIGHT);
	}

	/**
	 * Recreates the heatmap based on the current monochrome map. Using this
	 * approach makes it easy to change the used color mapping.
	 */
	private void updateHeatMap() {
		this.monochrome.snapshot(HeatMap.SNAPSHOT_PARAMETERS, this.monochromeImage);
		this.heatMap = new WritableImage(this.monochromeImage.widthProperty().intValue(),
				this.monochromeImage.heightProperty().intValue());
		Color colorFromMonoChromeImage;
		double brightness;
		Color mappedColor;
		final PixelWriter pixelWriter = this.heatMap.getPixelWriter();
		final PixelReader pixelReader = this.monochromeImage.getPixelReader();
		final int width = (int) this.monochromeImage.getWidth();
		final int height = (int) this.monochromeImage.getHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				colorFromMonoChromeImage = pixelReader.getColor(x, y);
				brightness = colorFromMonoChromeImage.getOpacity();
				mappedColor = this.getColorAt(this.mappingGradient, brightness);
				pixelWriter.setColor(x, y,
						this.fadeColors
								? Color.color(mappedColor.getRed(), mappedColor.getGreen(), mappedColor.getBlue(),
								brightness)
								: mappedColor);
			}
		}
		this.setImage(this.heatMap);
	}

	/**
	 * Updates each event in the monochrome map to the given opacity gradient which
	 * could be useful to reduce oversmoothing
	 *
	 * @param OPACITY_GRADIENT
	 */
	public void updateMonochromeMap(final OpacityDistribution OPACITY_GRADIENT) {
		this.ctx.clearRect(0, 0, this.monochrome.getWidth(), this.monochrome.getHeight());
		this.eventList.forEach(event -> {
			event.setOpacityDistribution(OPACITY_GRADIENT);
			this.ctx.drawImage(this.createEventImage(event.getRadius(), OPACITY_GRADIENT),
					event.getX() - (event.getRadius() * 0.5), event.getY() - (event.getRadius() * 0.5));
		});
		this.updateHeatMap();
	}
}
