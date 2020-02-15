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

/**
 * Created by User: hansolo Date: 30.12.12 Time: 16:43
 */
public class HeatMapEvent {
    private final double x;
    private final double y;
    private double radius;
    private OpacityDistribution opacityDistribution;

    // ******************** Constructors **************************************
    public HeatMapEvent(final double X, final double Y) {
        this(X, Y, 15.5, OpacityDistribution.CUSTOM);
    }

    public HeatMapEvent(final double X, final double Y, final double RADIUS) {
        this(X, Y, RADIUS, OpacityDistribution.CUSTOM);
    }

    public HeatMapEvent(final double X, final double Y, final double RADIUS,
                        final OpacityDistribution OPACITY_GRADIENT) {
        this.x = X;
        this.y = Y;
        this.radius = RADIUS;
        this.opacityDistribution = OPACITY_GRADIENT;
    }

    public OpacityDistribution getOpacityDistribution() {
        return this.opacityDistribution;
    }

    public void setOpacityDistribution(final OpacityDistribution OPACITY_GRADIENT) {
        this.opacityDistribution = OPACITY_GRADIENT;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(final double RADIUS) {
        this.radius = RADIUS;
    }

    // ******************** Methods *******************************************
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
