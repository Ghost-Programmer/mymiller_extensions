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

import javafx.scene.control.Tooltip;
import javafx.scene.shape.SVGPath;

import java.util.Locale;

/**
 * Created by hansolo on 20.09.16.
 */
public class CountryPath extends SVGPath {
    private final String NAME;
    private final Locale LOCALE;
    private final Tooltip TOOLTIP;

    // ******************** Constructors **************************************
    public CountryPath(final String NAME) {
        this(NAME, null);
    }

    public CountryPath(final String NAME, final String CONTENT) {
        super();
        this.NAME = NAME;
        this.LOCALE = new Locale("", NAME);
        this.TOOLTIP = new Tooltip(this.LOCALE.getDisplayCountry());
        Tooltip.install(this, this.TOOLTIP);
        if (null == CONTENT) {
            return;
        }
        this.setContent(CONTENT);
    }

    public Locale getLocale() {
        return this.LOCALE;
    }

    // ******************** Methods *******************************************
    public String getName() {
        return this.NAME;
    }

    public Tooltip getTooltip() {
        return this.TOOLTIP;
    }
}
