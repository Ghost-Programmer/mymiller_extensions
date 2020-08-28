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
package name.mymiller.lang;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Measure UnitOfTime based on milliseconds
 *
 * @author jmiller
 */
public enum UnitOfTime {
    // @formatter:off
    /**
     * Millieseconds
     */
    Millisecond("ms", new BigInteger("1")),
    /**
     * Seconds
     */
    Second("secs", new BigInteger("1000")),
    /**
     * Minutes
     */
    Minute("mins", new BigInteger("60000")),
    /**
     * Hours
     */
    Hour("hours", new BigInteger("3600000")),
    /**
     * Days
     */
    Day("days", new BigInteger("86400000")),
    /**
     * Weeks
     */
    Week("weeks", new BigInteger("604800000")),
    /**
     * Years
     */
    Year("years", new BigInteger("31536000000")),
    /**
     * Millennium
     */
    Millennium("millennium", new BigInteger("31536000000000"));

    // @formatter:on

    /**
     * Label to use on this UnitOfTime
     */
    private String label = null;
    /**
     * Total Millieseonds
     */
    private BigInteger milliseconds = null;

    /**
     * Constructor for this UnitOfTime
     *
     * @param label        Label to use
     * @param milliseconds Number of milliseconds to make this unit of time.
     */
    UnitOfTime(String label, BigInteger milliseconds) {
        this.label = label;
        this.milliseconds = milliseconds;
    }

    /**
     * Convert a number of milliseconds into a Years Days Hours Minutes Seconds
     * Milliseconds format
     *
     * @param milliseconds Number of milliseconds to convert
     * @return String containing the conversion
     */
    public static String convert(BigInteger milliseconds) {
        BigInteger working = milliseconds;
        final StringBuilder builder = new StringBuilder();

        final BigInteger years = UnitOfTime.Year.getMilliseconds().divide(working);
        working = working.subtract(years.multiply(UnitOfTime.Year.getMilliseconds()));

        final BigInteger days = UnitOfTime.Day.getMilliseconds().divide(working);
        working = working.subtract(days.multiply(UnitOfTime.Day.getMilliseconds()));

        final BigInteger hours = UnitOfTime.Hour.getMilliseconds().divide(working);
        working = working.subtract(hours.multiply(UnitOfTime.Hour.getMilliseconds()));

        final BigInteger minutes = UnitOfTime.Minute.getMilliseconds().divide(working);
        working = working.subtract(minutes.multiply(UnitOfTime.Minute.getMilliseconds()));

        final BigInteger seconds = UnitOfTime.Second.getMilliseconds().divide(working);
        working = working.subtract(seconds.multiply(UnitOfTime.Second.getMilliseconds()));

        if (years.longValue() != 0L) {
            builder.append(years);
            builder.append(" years ");
        }
        if (days.longValue() != 0L) {
            builder.append(days);
            builder.append(" days ");
        }
        if (hours.longValue() != 0L) {
            builder.append(hours);
            builder.append(" hours ");
        }
        if (minutes.longValue() != 0L) {
            builder.append(minutes);
            builder.append(" minutes ");
        }
        if (seconds.longValue() != 0L) {
            builder.append(seconds);
            builder.append(" seconds ");
        }
        if (working.longValue() != 0L) {
            builder.append(working);
            builder.append(" milliseconds ");
        }

        return builder.toString();
    }

    /**
     * Determine what unit to use based on the number of milliseconds
     *
     * @param milliseconds Total number of milliseconds
     * @return UnitOfTime you should use.
     */
    public static UnitOfTime useUnit(BigInteger milliseconds) {
        if (milliseconds.compareTo(UnitOfTime.Millennium.getMilliseconds()) >= 0) {
            return UnitOfTime.Millennium;
        } else if (milliseconds.compareTo(UnitOfTime.Year.getMilliseconds()) >= 0) {
            return UnitOfTime.Year;
        } else if (milliseconds.compareTo(UnitOfTime.Week.getMilliseconds()) >= 0) {
            return UnitOfTime.Week;
        } else if (milliseconds.compareTo(UnitOfTime.Day.getMilliseconds()) >= 0) {
            return UnitOfTime.Day;
        } else if (milliseconds.compareTo(UnitOfTime.Hour.getMilliseconds()) >= 0) {
            return UnitOfTime.Hour;
        } else if (milliseconds.compareTo(UnitOfTime.Minute.getMilliseconds()) >= 0) {
            return UnitOfTime.Minute;
        } else if (milliseconds.compareTo(UnitOfTime.Second.getMilliseconds()) >= 0) {
            return UnitOfTime.Second;
        } else {
            return UnitOfTime.Millisecond;
        }
    }

    /**
     * Determine what unit to use based on the number of milliseconds
     *
     * @param milliseconds Total number of milliseconds
     * @return UnitOfTime you should use.
     */
    public static UnitOfTime useUnit(Integer milliseconds) {
        return UnitOfTime.useUnit(BigInteger.valueOf(milliseconds));
    }

    /**
     * Determine what unit to use based on the number of milliseconds
     *
     * @param milliseconds Total number of milliseconds
     * @return UnitOfTime you should use.
     */
    public static UnitOfTime useUnit(Long milliseconds) {
        return UnitOfTime.useUnit(BigInteger.valueOf(milliseconds));
    }

    /**
     * @return the label
     */
    public final String getLabel() {
        return this.label;
    }

    /**
     * @return the milliseconds
     */
    public final BigInteger getMilliseconds() {
        return this.milliseconds;
    }

    /**
     * Convert to the UnitOfTime
     *
     * @param milliseconds Milliseconds to convert
     * @return BigDecimal of the number of units
     */
    public BigDecimal getUnits(BigInteger milliseconds) {
        return new BigDecimal(milliseconds.divide(this.getMilliseconds()));
    }

    /**
     * Convert to the UnitOfTime
     *
     * @param milliseconds Milliseconds to convert
     * @return BigDecimal of the number of units
     */
    public BigDecimal getUnits(Integer milliseconds) {
        return this.getUnits(BigInteger.valueOf(milliseconds));
    }

    /**
     * Convert to the UnitOfTime
     *
     * @param milliseconds Milliseconds to convert
     * @return BigDecimal of the number of units
     */
    public BigDecimal getUnits(Long milliseconds) {
        return this.getUnits(BigInteger.valueOf(milliseconds));
    }
}
