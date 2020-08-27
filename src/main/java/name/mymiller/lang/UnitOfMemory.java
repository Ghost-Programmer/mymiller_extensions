/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.lang;

import java.io.Serializable;

/**
 * Unit of Mesurement for Memory
 *
 * @author jmiller
 */
public enum UnitOfMemory implements Serializable {
    // @formatter:off
    /**
     * Byte Units
     */
    Byte("B", 1L),
    /**
     * KiloByte Units
     */
    kiloByte("KB", 1024L),
    /**
     * MegaByte Units
     */
    megaByte("MB", 1024L * 1024L),
    /**
     * GigaByte Units
     */
    gigaByte("GB", 1024L * 1024L * 1024L),
    /**
     * TeraByte Units
     */
    teraByte("TB", 1024L * 1024L * 1024L * 1024L);
    // @formatter:on

    /**
     * Label to use for this UnitOfMemory
     */
    private final String label;
    /**
     * Number of Bytes in each UnitOfMemory
     */
    private final long totalBytes;

    /**
     * Constructor for the ENUM
     *
     * @param label      Label to use on this UnitOfMemory
     * @param totalBytes Number of Bytes in each UnitOfMemory
     */
    UnitOfMemory(String label, long totalBytes) {
        this.label = label;
        this.totalBytes = totalBytes;
    }

    /**
     * Convert Bytes from one Unit to another
     *
     * @param convertTo   UnitOfMemory to convert to
     * @param convertFrom UnitOfMemory to covert from
     * @param units       Number of units to convert
     * @return double indicating the number of units
     */
    public static double convert(UnitOfMemory convertTo, UnitOfMemory convertFrom, long units) {
        return convertTo.getUnits(convertFrom.getTotalBytes() * units);
    }

    /**
     * Utility Function to quickly and easily get the unit of measurement.
     *
     * @param totalBytes Number of bytes being measured.
     * @return Formatted String of the measurement.
     */
    public static String measurement(long totalBytes) {
        return UnitOfMemory.useUnit(totalBytes).getMeasurement(totalBytes);
    }

    /**
     * Determine what unit to use
     *
     * @param totalBytes Number of bytes being measured.
     * @return UnitOfMemory to use.
     */
    public static UnitOfMemory useUnit(long totalBytes) {
        if (totalBytes >= UnitOfMemory.teraByte.getTotalBytes()) {
            return UnitOfMemory.teraByte;
        } else if (totalBytes >= UnitOfMemory.gigaByte.getTotalBytes()) {
            return UnitOfMemory.gigaByte;
        } else if (totalBytes >= UnitOfMemory.megaByte.getTotalBytes()) {
            return UnitOfMemory.megaByte;
        } else if (totalBytes >= UnitOfMemory.kiloByte.getTotalBytes()) {
            return UnitOfMemory.kiloByte;
        }

        return UnitOfMemory.Byte;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Return a formatted string with Units to two decimel places with label.
     *
     * @param totalBytes Number of bytes
     * @return Formatted string.
     */
    public String getMeasurement(long totalBytes) {
        return String.format("%1$,.2f", this.getUnits(totalBytes)) + this.getLabel();
    }

    /**
     * @return the totalBytes
     */
    public long getTotalBytes() {
        return this.totalBytes;
    }

    /**
     * Determine the number of units
     *
     * @param totalBytes Number of Bytes to measure
     * @return Number of units to measure
     */
    public double getUnits(long totalBytes) {
        return (totalBytes / this.getTotalBytes())
                + (((double) (totalBytes % this.getTotalBytes())) / this.getTotalBytes());
    }
}
