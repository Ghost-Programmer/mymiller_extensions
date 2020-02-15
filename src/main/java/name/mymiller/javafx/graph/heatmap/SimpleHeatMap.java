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
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User: hansolo Date: 31.12.12 Time: 07:49
 */
public class SimpleHeatMap {
	private static final SnapshotParameters SNAPSHOT_PARAMETERS = new SnapshotParameters();
	private final Canvas monochromeCanvas;
	private final GraphicsContext ctx;
	private final ImageView heatMapView;
	private ColorMapping colorMapping;
	private LinearGradient mappingGradient;
	private boolean fadeColors;
	private double radius;
	private OpacityDistribution opacityDistribution;
	private Image eventImage;
	private WritableImage monochromeImage;
	private WritableImage heatMap;

	// ******************** Constructors **************************************
	public SimpleHeatMap(final double WIDTH, final double HEIGHT) {
		this(WIDTH, HEIGHT, ColorMapping.LIME_YELLOW_RED);
	}

	public SimpleHeatMap(final double WIDTH, final double HEIGHT, ColorMapping COLOR_MAPPING) {
		this(WIDTH, HEIGHT, COLOR_MAPPING, 15.5);
	}

	public SimpleHeatMap(final double WIDTH, final double HEIGHT, ColorMapping COLOR_MAPPING,
						 final double EVENT_RADIUS) {
		this(WIDTH, HEIGHT, COLOR_MAPPING, EVENT_RADIUS, true);
	}

	public SimpleHeatMap(final double WIDTH, final double HEIGHT, ColorMapping COLOR_MAPPING, final double EVENT_RADIUS,
						 final boolean FADE_COLORS) {
		SimpleHeatMap.SNAPSHOT_PARAMETERS.setFill(Color.TRANSPARENT);
		this.colorMapping = COLOR_MAPPING;
		this.mappingGradient = this.colorMapping.mapping;
		this.fadeColors = FADE_COLORS;
		this.radius = EVENT_RADIUS;
		this.opacityDistribution = OpacityDistribution.CUSTOM;
		this.eventImage = this.createEventImage(this.radius, this.opacityDistribution);
		this.monochromeCanvas = new Canvas(WIDTH, HEIGHT);
		this.ctx = this.monochromeCanvas.getGraphicsContext2D();
		this.monochromeImage = new WritableImage((int) WIDTH, (int) HEIGHT);
		this.heatMapView = new ImageView(this.heatMap);
		this.heatMapView.setMouseTransparent(true);
		this.heatMapView.setOpacity(0.5);
	}

	public void addEvent(final double X, final double Y) {
		this.addEvent(X, Y, this.eventImage, this.radius, this.radius);
	}

	public void addEvent(final double X, final double Y, final Image EVENT_IMAGE, final double OFFSET_X,
						 final double OFFSET_Y) {
		this.ctx.drawImage(EVENT_IMAGE, X - OFFSET_X, Y - OFFSET_Y);
		this.updateHeatMap();
	}

	public void addEvents(final List<Point2D> EVENTS) {
		for (final Point2D event : EVENTS) {
			this.ctx.drawImage(this.eventImage, event.getX() - this.radius, event.getY() - this.radius);
		}
		this.updateHeatMap();
	}

	public void addEvents(final Point2D... EVENTS) {
		this.addEvents(Arrays.asList(EVENTS));
	}

	public void clearHeatMap() {
		this.ctx.clearRect(0, 0, this.monochromeCanvas.getWidth(), this.monochromeCanvas.getHeight());
		this.monochromeImage = new WritableImage(this.monochromeCanvas.widthProperty().intValue(),
				this.monochromeCanvas.heightProperty().intValue());
		this.updateHeatMap();
	}

	private double computeBrightness(final double RED, final double GREEN, final double BLUE) {
		return ((0.2126 * RED) + (0.7152 * GREEN) + (0.0722 * BLUE));
	}

	private double computeBrightnessFast(final double RED, final double GREEN, final double BLUE) {
		return ((RED + RED + BLUE + GREEN + GREEN + GREEN) / 6.0);
	}

	private double computeLuminance(final double RED, final double GREEN, final double BLUE) {
		return Math.sqrt((0.241 * (RED * RED)) + (0.691 * (GREEN * GREEN)) + (0.068 * (BLUE * BLUE)));
	}

	private double computePerceivedBrightness(final double RED, final double GREEN, final double BLUE) {
		return ((0.299 * RED) + (0.587 * GREEN) + (0.114 * BLUE));
	}

	private double computePerceivedBrightnessFast(final double RED, final double GREEN, final double BLUE) {
		return ((RED + RED + RED + BLUE + GREEN + GREEN + GREEN + GREEN) * 0.5);
	}

	public Image createEventImage(final double RADIUS, final OpacityDistribution OPACITY_DISTRIBUTION) {
		this.radius = RADIUS < 1 ? 1 : RADIUS;
		final Stop[] stops = new Stop[11];
		for (int i = 0; i < 11; i++) {
			stops[i] = new Stop(i * 0.1, Color.rgb(255, 255, 255, OPACITY_DISTRIBUTION.distribution[i]));
		}
		final int size = (int) (this.radius * 2);
		final WritableImage raster = new WritableImage(size, size);
		final PixelWriter pixelWriter = raster.getPixelWriter();
		final double maxDistFactor = 1 / this.radius;
		Color pixelColor;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				final double distanceX = this.radius - x;
				final double distanceY = this.radius - y;
				final double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
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
		return raster;
	}

	private Color getColorAt(final LinearGradient GRADIENT, final double FRACTION) {
		final double fraction = FRACTION < 0f ? 0f : (FRACTION > 1 ? 1 : FRACTION);
		final List<Stop> stops = GRADIENT.getStops();
		Stop lowerLimit = new Stop(0.0, stops.get(0).getColor());
		Stop upperLimit = new Stop(1.0, stops.get(stops.size() - 1).getColor());

		for (final Stop stop : stops) {
			if (Helper.lessThan(stop.getOffset(), fraction)) {
				lowerLimit = new Stop(stop.getOffset(), stop.getColor());
			} else if (Helper.equals(stop.getOffset(), fraction)) {
				return stop.getColor();
			} else {
				upperLimit = new Stop(stop.getOffset(), stop.getColor());
			}
		}

		final double interpolationFraction = (fraction - lowerLimit.getOffset())
				/ (upperLimit.getOffset() - lowerLimit.getOffset());
		return (Color) Interpolator.LINEAR.interpolate(lowerLimit.getColor(), upperLimit.getColor(),
				interpolationFraction);
	}

	public ColorMapping getColorMapping() {
		return this.colorMapping;
	}

	public void setColorMapping(final ColorMapping COLOR_MAPPING) {
		this.colorMapping = COLOR_MAPPING;
		this.mappingGradient = COLOR_MAPPING.mapping;
		this.updateHeatMap();
	}

	public double getEventRadius() {
		return this.radius;
	}

	public void setEventRadius(final double RADIUS) {
		this.radius = RADIUS < 1 ? 1 : RADIUS;
		this.eventImage = this.createEventImage(this.radius, this.opacityDistribution);
	}

	// ******************** Methods *******************************************
	public ImageView getHeatMapImage() {
		return this.heatMapView;
	}

	public double getHeatMapOpacity() {
		return this.heatMapView.getOpacity();
	}

	public void setHeatMapOpacity(final double HEAT_MAP_OPACITY) {
		this.heatMapView.setOpacity(HEAT_MAP_OPACITY < 0 ? 0 : (HEAT_MAP_OPACITY > 1 ? 1 : HEAT_MAP_OPACITY));
	}

	public OpacityDistribution getOpacityDistribution() {
		return this.opacityDistribution;
	}

	public void setOpacityDistribution(final OpacityDistribution OPACITY_DISTRIBUTION) {
		this.opacityDistribution = OPACITY_DISTRIBUTION;
		this.eventImage = this.createEventImage(this.radius, this.opacityDistribution);
	}

	public boolean isFadeColors() {
		return this.fadeColors;
	}

	public void setFadeColors(final boolean FADE_COLORS) {
		this.fadeColors = FADE_COLORS;
		this.updateHeatMap();
	}

	public void setSize(final double WIDTH, final double HEIGHT) {
		this.monochromeCanvas.resize(WIDTH, HEIGHT);
		if ((WIDTH > 0) && (HEIGHT > 0)) {
			this.monochromeImage = new WritableImage(this.monochromeCanvas.widthProperty().intValue(),
					this.monochromeCanvas.heightProperty().intValue());
			this.updateHeatMap();
		}
	}

	private void updateHeatMap() {
		this.monochromeCanvas.snapshot(SimpleHeatMap.SNAPSHOT_PARAMETERS, this.monochromeImage);
		this.heatMap = new WritableImage(this.monochromeImage.widthProperty().intValue(),
				this.monochromeImage.heightProperty().intValue());
		final PixelWriter pixelWriter = this.heatMap.getPixelWriter();
		final PixelReader pixelReader = this.monochromeImage.getPixelReader();
		Color colorFromMonoChromeImage;
		double brightness;
		Color mappedColor;
		for (int y = 0; y < this.monochromeImage.getHeight(); y++) {
			for (int x = 0; x < this.monochromeImage.getWidth(); x++) {
				colorFromMonoChromeImage = pixelReader.getColor(x, y);
				// brightness = computeLuminance(colorFromMonoChromeImage.getRed(),
				// colorFromMonoChromeImage.getGreen(), colorFromMonoChromeImage.getBlue());
				// brightness = computeBrightness(colorFromMonoChromeImage.getRed(),
				// colorFromMonoChromeImage.getGreen(), colorFromMonoChromeImage.getBlue());
				brightness = this.computeBrightnessFast(colorFromMonoChromeImage.getRed(),
						colorFromMonoChromeImage.getGreen(), colorFromMonoChromeImage.getBlue());
				mappedColor = this.getColorAt(this.mappingGradient, brightness);
				if (this.fadeColors) {
					// pixelWriter.setColor(x, y, Color.color(mappedColor.getRed(),
					// mappedColor.getGreen(), mappedColor.getBlue(), brightness));
					pixelWriter.setColor(x, y, Color.color(mappedColor.getRed(), mappedColor.getGreen(),
							mappedColor.getBlue(), colorFromMonoChromeImage.getOpacity()));
				} else {
					pixelWriter.setColor(x, y, mappedColor);
				}
			}
		}
		this.heatMapView.setImage(this.heatMap);
	}
}
