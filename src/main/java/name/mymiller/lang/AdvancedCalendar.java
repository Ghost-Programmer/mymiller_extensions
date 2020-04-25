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

import java.text.*;
import java.time.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Advanced class that combines Date, Calendar, and SimpleDateformat into a
 * single object for simplicity.
 *
 * @author jmiller
 * @see Calendar
 */
public class AdvancedCalendar {
	/**
	 * A style specifier for getDisplayNames indicating names in all styles, such as
	 * "January" and "Jan".
	 */
	public static int ALL_STYLES = Calendar.ALL_STYLES;

	/**
	 * Value of the AM_PM field indicating the period of the day from midnight to
	 * just before noon.
	 */
	public static int AM = Calendar.AM;
	/**
	 * Field number for get and set indicating whether the HOUR is before or after
	 * noon.
	 */
	public static int AM_PM = Calendar.AM_PM;

	/**
	 * Value of the MONTH field indicating the fourth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int APRIL = Calendar.APRIL;

	/**
	 * Value of the MONTH field indicating the eighth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int AUGUST = Calendar.AUGUST;

	/**
	 * Field number for get and set indicating the day of the month.
	 */
	public static int DATE = Calendar.DATE;

	/**
	 * Field number for get and set indicating the day of the month.
	 */
	public static int DAY_OF_MONTH = Calendar.DAY_OF_MONTH;

	/**
	 * Field number for get and set indicating the day of the week.
	 */
	public static int DAY_OF_WEEK = Calendar.DAY_OF_WEEK;

	/**
	 * Field number for get and set indicating the ordinal number of the day of the
	 * week within the current month.
	 */
	public static int DAY_OF_WEEK_IN_MONTH = Calendar.DAY_OF_WEEK_IN_MONTH;

	/**
	 * Field number for get and set indicating the day number within the current
	 * year.
	 */
	public static int DAY_OF_YEAR = Calendar.DAY_OF_YEAR;

	/**
	 * Value of the MONTH field indicating the twelfth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int DECEMBER = Calendar.DECEMBER;

	/**
	 * Field number for get and set indicating the daylight saving offset in
	 * milliseconds.
	 */
	public static int DST_OFFSET = Calendar.DST_OFFSET;

	/**
	 * Field number for get and set indicating the era, e.g., AD or BC in the Julian
	 * calendar.
	 */
	public static int ERA = Calendar.ERA;

	/**
	 * Value of the MONTH field indicating the second month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int FEBRUARY = Calendar.FEBRUARY;

	/**
	 * The number of distinct fields recognized by get and set.
	 */
	public static int FIELD_COUNT = Calendar.FIELD_COUNT;

	/**
	 * Value of the DAY_OF_WEEK field indicating Friday.
	 */
	public static int FRIDAY = Calendar.FRIDAY;

	/**
	 * Field number for get and set indicating the hour of the morning or afternoon.
	 */
	public static int HOUR = Calendar.HOUR;

	/**
	 * Field number for get and set indicating the hour of the day.
	 */
	public static int HOUR_OF_DAY = Calendar.HOUR_OF_DAY;

	/**
	 * Value of the MONTH field indicating the first month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int JANUARY = Calendar.JANUARY;

	/**
	 * Value of the MONTH field indicating the seventh month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int JULY = Calendar.JULY;

	/**
	 * Value of the MONTH field indicating the sixth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int JUNE = Calendar.JUNE;

	/**
	 * A style specifier for getDisplayName and getDisplayNames equivalent to
	 * LONG_FORMAT.
	 */
	public static int LONG = Calendar.LONG;

	/**
	 * A style specifier for getDisplayName and getDisplayNames indicating a long
	 * name used for format.
	 */
	public static int LONG_FORMAT = Calendar.LONG_FORMAT;

	/**
	 * A style specifier for getDisplayName and getDisplayNames indicating a long
	 * name used independently, such as a month name as calendar headers.
	 */
	public static int LONG_STANDALONE = Calendar.LONG_STANDALONE;

	/**
	 * Value of the MONTH field indicating the third month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int MARCH = Calendar.MARCH;

	/**
	 * Value of the MONTH field indicating the fifth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int MAY = Calendar.MAY;

	/**
	 * Field number for get and set indicating the millisecond within the second.
	 */
	public static int MILLISECOND = Calendar.MILLISECOND;

	/**
	 * Field number for get and set indicating the minute within the hour.
	 */
	public static int MINUTE = Calendar.MINUTE;

	/**
	 * Value of the DAY_OF_WEEK field indicating Monday.
	 */
	public static int MONDAY = Calendar.MONDAY;

	/**
	 *
	 */
	public static int MONTH = Calendar.MONTH;

	/**
	 * A style specifier for getDisplayName and getDisplayNames indicating a narrow
	 * name used for format.
	 */
	public static int NARROW_FORMAT = Calendar.NARROW_FORMAT;

	/**
	 * A style specifier for getDisplayName and getDisplayNames indicating a narrow
	 * name independently.
	 */
	public static int NARROW_STANDALONE = Calendar.NARROW_STANDALONE;

	/**
	 * Value of the MONTH field indicating the eleventh month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int NOVEMBER = Calendar.NOVEMBER;

	/**
	 * Value of the MONTH field indicating the tenth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int OCTOBER = Calendar.OCTOBER;

	/**
	 * Value of the AM_PM field indicating the period of the day from noon to just
	 * before midnight.
	 */
	public static int PM = Calendar.PM;

	/**
	 * Value of the DAY_OF_WEEK field indicating Saturday.
	 */
	public static int SATURDAY = Calendar.SATURDAY;

	/**
	 * Field number for get and set indicating the second within the minute.
	 */
	public static int SECOND = Calendar.SECOND;

	/**
	 * Value of the MONTH field indicating the ninth month of the year in the
	 * Gregorian and Julian calendars.
	 */
	public static int SEPTEMBER = Calendar.SEPTEMBER;

	/**
	 * A style specifier for getDisplayName and getDisplayNames equivalent to
	 * SHORT_FORMAT.
	 */
	public static int SHORT = Calendar.SHORT;

	/**
	 * A style specifier for getDisplayName and getDisplayNames indicating a short
	 * name used for format.
	 */
	public static int SHORT_FORMAT = Calendar.SHORT_FORMAT;

	/**
	 * A style specifier for getDisplayName and getDisplayNames indicating a short
	 * name used independently, such as a month abbreviation as calendar headers.
	 */
	public static int SHORT_STANDALONE = Calendar.SHORT_STANDALONE;

	/**
	 * Value of the DAY_OF_WEEK field indicating Sunday.
	 */
	public static int SUNDAY = Calendar.SUNDAY;

	/**
	 * Value of the DAY_OF_WEEK field indicating Thursday.
	 */
	public static int THURSDAY = Calendar.THURSDAY;

	/**
	 * Value of the DAY_OF_WEEK field indicating Tuesday.
	 */
	public static int TUESDAY = Calendar.TUESDAY;

	/**
	 * Value of the MONTH field indicating the thirteenth month of the year.
	 */
	public static int UNDECIMBER = Calendar.UNDECIMBER;

	/**
	 * Value of the DAY_OF_WEEK field indicating Wednesday.
	 */
	public static int WEDNESDAY = Calendar.WEDNESDAY;

	/**
	 * Field number for get and set indicating the week number within the current
	 * month.
	 */
	public static int WEEK_OF_MONTH = Calendar.WEEK_OF_MONTH;

	/**
	 * Field number for get and set indicating the week number within the current
	 * year.
	 */
	public static int WEEK_OF_YEAR = Calendar.WEEK_OF_YEAR;

	/**
	 * Field number for get and set indicating the year.
	 */
	public static int YEAR = Calendar.YEAR;

	/**
	 * Field number for get and set indicating the raw offset from GMT in
	 * milliseconds.
	 */
	public static int ZONE_OFFSET = Calendar.ZONE_OFFSET;
	/**
	 * SimpleDateFormat for this AdvancedCalendar
	 */
	private final SimpleDateFormat format = new SimpleDateFormat();
	/**
	 * Calendar object for maintaining the internal time
	 */
	private Calendar calendar = null;

	/**
	 * Constructs a AdvancedCalendar with the default time zone and locale.
	 */
	public AdvancedCalendar() {
		this.calendar = Calendar.getInstance();
	}

	/**
	 * Constructs an AdvancedCalendar with an existing Calendar object
	 *
	 * @param calendar Calendar object to initialize the AdvancedCalendar
	 */
	public AdvancedCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * Constructs a AdvancedCalendar with the default time zone and locale, and set
	 * the time to the indicated Date
	 *
	 * @param date Date containing the date/time to set
	 */
	public AdvancedCalendar(Date date) {
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(date);
	}

	/**
	 * Constructs a AdvancedCalendar with the default time zone and specified
	 * locale, and set the time to the indicated Date
	 *
	 * @param date    Date containing the date/time to set
	 * @param aLocale the locale for the week data
	 */
	public AdvancedCalendar(Date date, Locale aLocale) {
		this.calendar = Calendar.getInstance(aLocale);
		this.calendar.setTime(date);
	}

	/**
	 * Constructs an AdvancedCalendar with a LocaleDate.
	 * @param localDate LocaleDate containing the date to set;
	 */
	public AdvancedCalendar(LocalDate localDate) {
		this.calendar = Calendar.getInstance();
		this.calendar.set(localDate.getYear(),localDate.getMonthValue()-1,localDate.getDayOfMonth());
	}

	/**
	 * Constructs an AdvancedCalendar with a LocaleDateTime
	 * @param localDateTime
	 */
	public AdvancedCalendar(LocalDateTime localDateTime) {
		this.calendar = Calendar.getInstance();
		this.calendar.set(localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
				localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
	}

	/**
	 * Construct an AdvancedCalendar with a LocalDate and LocalTime
	 * @param localDate LocalDate containing the Date to set
	 * @param localTime LocalTime containing the Time to set
	 */
	public AdvancedCalendar(LocalDate localDate, LocalTime localTime) {
		this.calendar = Calendar.getInstance();
		this.calendar.set(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth(),
				localTime.getHour(), localTime.getMinute(), localTime.getSecond());
	}

	/**
	 * Constructo an AdvancedCalendar based on a ZonedDateTime
	 * @param zonedDateTime ZonedDateTime containing the Date/Time to set.
	 */
	public AdvancedCalendar(ZonedDateTime zonedDateTime) {
		this(zonedDateTime.toLocalDateTime());
	}

	/**
	 * Constructs a AdvancedCalendar with the default locale and specified time
	 * zone, and set the time to the indicated Date
	 *
	 * @param date Date containing the date/time to set
	 * @param zone the time zone to use
	 */
	public AdvancedCalendar(Date date, TimeZone zone) {
		this.calendar = Calendar.getInstance(zone);
		this.calendar.setTime(date);
	}

	/**
	 * Constructs a AdvancedCalendar with the specified time zone and locale, and
	 * set the time to the indicated Date
	 *
	 * @param date    Date containing the date/time to set
	 * @param zone    the time zone to use
	 * @param aLocale the locale for the week data
	 */
	public AdvancedCalendar(Date date, TimeZone zone, Locale aLocale) {
		this.calendar = Calendar.getInstance(zone, aLocale);
		this.calendar.setTime(date);
	}

	/**
	 * Convert a GregorianCalendar into an AdvancedCalendar
	 *
	 * @param gregorianCalendar GregorianCalendar to use.
	 */
	public AdvancedCalendar(GregorianCalendar gregorianCalendar) {
		this(gregorianCalendar.getTime());
	}

	/**
	 * Constructs an AdvancedCalendar with an existing Instant object
	 *
	 * @param instant Instant object o initialize the AdvancedCalendar
	 */
	public AdvancedCalendar(Instant instant) {
		this.calendar = Calendar.getInstance();
		this.setTimeInMillis(instant.getEpochSecond());
	}

	/**
	 * Constructs a AdvancedCalendar with the default time zone and locale
	 *
	 * @param aLocale the locale for the week data
	 */
	public AdvancedCalendar(Locale aLocale) {
		this.calendar = Calendar.getInstance(aLocale);
	}

	/**
	 * Constructs a AdvancedCalendar with the default locale and specified time zone
	 *
	 * @param zone the time zone to use
	 */
	public AdvancedCalendar(TimeZone zone) {
		this.calendar = Calendar.getInstance(zone);
	}

	/**
	 * Constructs a AdvancedCalendar with the specified time zone and locale
	 *
	 * @param zone    the time zone to use
	 * @param aLocale the locale for the week data
	 */
	public AdvancedCalendar(TimeZone zone, Locale aLocale) {
		this.calendar = Calendar.getInstance(zone, aLocale);
	}

	/**
	 * Create and AdvancedCalendar from a String with a pattern date time.
	 *
	 * @param pattern  String containing the Pattern to parse the dateTime
	 * @param dateTime String containing the date time in the pattern format
	 * @return AdvancedCalendar object for this date time
	 * @throws ParseException Error in parsing the date time
	 * @see SimpleDateFormat
	 */
	public static AdvancedCalendar parse(String pattern, String dateTime) throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat(pattern);
		final Date date = format.parse(dateTime);

		return new AdvancedCalendar(date);
	}

	/**
	 * Create and AdvancedCalendar from a String with a pattern date time.
	 *
	 * @param pattern   String containing the Pattern to parse the dateTime
	 * @param dateTime  String containing the date time in the pattern format
	 * @param startDate During parsing, two digit years will be placed in the range
	 *                  startDate to startDate + 100 years.
	 * @return AdvancedCalendar object for this date time
	 * @throws ParseException Error in parsing the date time
	 * @see SimpleDateFormat
	 */
	public static AdvancedCalendar parse(String pattern, String dateTime, Date startDate) throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.set2DigitYearStart(startDate);
		final Date date = format.parse(dateTime);

		return new AdvancedCalendar(date);
	}

	/**
	 * Create and AdvancedCalendar from a String with a pattern date time.
	 *
	 * @param pattern   String containing the Pattern to parse the dateTime
	 * @param dateTime  String containing the date time in the pattern format
	 * @param startDate During parsing, two digit years will be placed in the range
	 *                  startDate to startDate + 100 years.
	 * @param zone      the given new time zone.
	 * @return AdvancedCalendar object for this date time
	 * @throws ParseException Error in parsing the date time
	 * @see SimpleDateFormat
	 */
	public static AdvancedCalendar parse(String pattern, String dateTime, Date startDate, TimeZone zone)
			throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat(pattern);
		if (startDate != null) {
			format.set2DigitYearStart(startDate);
		}
		if (zone != null) {
			format.setTimeZone(zone);
		}
		final Date date = format.parse(dateTime);

		return new AdvancedCalendar(date);
	}

	/**
	 * Create and AdvancedCalendar from a String with a pattern date time.
	 *
	 * @param pattern   String containing the Pattern to parse the dateTime
	 * @param dateTime  String containing the date time in the pattern format
	 * @param startDate During parsing, two digit years will be placed in the range
	 *                  startDate to startDate + 100 years.
	 * @param zone      the given new time zone.
	 * @param lenient   when true, parsing is lenient
	 * @return AdvancedCalendar object for this date time
	 * @throws ParseException Error in parsing the date time
	 * @see SimpleDateFormat
	 */
	public static AdvancedCalendar parse(String pattern, String dateTime, Date startDate, TimeZone zone,
										 boolean lenient) throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat(pattern);
		if (startDate != null) {
			format.set2DigitYearStart(startDate);
		}
		if (zone != null) {
			format.setTimeZone(zone);
		}
		format.setLenient(lenient);
		final Date date = format.parse(dateTime);

		return new AdvancedCalendar(date);
	}

	/**
	 * Adds or subtracts the specified amount of time to the given calendar field,
	 * based on the calendar's rules. For example, to subtract 5 days from the
	 * current time of the calendar, you can achieve it by calling:
	 * add(Calendar.DAY_OF_MONTH, -5).
	 *
	 * @param field  the calendar field.
	 * @param amount the amount of date or time to be added to the field.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#add(int, int)
	 */
	public AdvancedCalendar add(int field, int amount) {
		this.calendar.add(field, amount);
		return this;
	}

	/**
	 * Add a number of days to this Advanced Calendar
	 *
	 * @param amount The number to Add
	 * @return this Calendar reference
	 */
	public AdvancedCalendar addDays(int amount) {
		return this.add(Calendar.DAY_OF_YEAR, amount);
	}

	/**
	 * Add a number of hours to this Advanced Calendar
	 *
	 * @param amount The number to Add
	 * @return this Calendar reference
	 */
	public AdvancedCalendar addHours(int amount) {
		return this.add(Calendar.HOUR, amount);
	}

	/**
	 * Add a number of minutes to this Advanced Calendar
	 *
	 * @param amount The number to Add
	 * @return this Calendar reference
	 */
	public AdvancedCalendar addMinutes(int amount) {
		return this.add(Calendar.MINUTE, amount);
	}

	/**
	 * Add a number of months to this Advanced Calendar
	 *
	 * @param amount The number to Add
	 * @return this Calendar reference
	 */
	public AdvancedCalendar addMonths(int amount) {
		return this.add(Calendar.MONTH, amount);
	}

	/**
	 * Add a number of seconds to this Advanced Calendar
	 *
	 * @param amount The number to Add
	 * @return this Calendar reference
	 */
	public AdvancedCalendar addSeconds(int amount) {
		return this.add(Calendar.SECOND, amount);
	}

	/**
	 * Add a number of years to this Advanced Calendar
	 *
	 * @param amount The number to Add
	 * @return this Calendar reference
	 */
	public AdvancedCalendar addYears(int amount) {
		return this.add(Calendar.YEAR, amount);
	}

	/**
	 * Returns whether this Calendar represents a time after the time represented by
	 * the specified Object. This method is equivalent to:
	 * <p>
	 * compareTo(when) &gt; 0
	 *
	 * @param when the Object to be compared
	 * @return true if the time of this Calendar is after the time represented by
	 * when; false otherwise.
	 * @see Calendar#after(Object)
	 */
	public boolean after(Object when) {
		return this.calendar.after(when);
	}

	/**
	 * Applies the given pattern string to this date format.
	 *
	 * @param pattern the new date and time pattern for this date format
	 * @see SimpleDateFormat#applyPattern(String)
	 */
	public void applyPattern(String pattern) {
		this.format.applyPattern(pattern);
	}

	/**
	 * Returns whether this Calendar represents a time before the time represented
	 * by the specified Object. This method is equivalent to:
	 * <p>
	 * compareTo(when) &lt; 0
	 *
	 * @param when he Object to be compared
	 * @return true if the time of this Calendar is before the time represented by
	 * when; false otherwise.
	 * @see Calendar#before(Object)
	 */
	public boolean before(Object when) {
		return this.calendar.before(when);
	}

	/**
	 * Sets all the calendar field values and the time value (millisecond offset
	 * from the Epoch) of this Calendar undefined. This means that isSet() will
	 * return false for all the calendar fields, and the date and time calculations
	 * will treat the fields as if they had never been set. A Calendar
	 * implementation class may use its specific default field values for date/time
	 * calculations. For example, GregorianCalendar uses 1970 if the YEAR field
	 * value is undefined.
	 *
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#clear()
	 */
	public final AdvancedCalendar clear() {
		this.calendar.clear();
		return this;
	}

	/**
	 * Sets the given calendar field value and the time value (millisecond offset
	 * from the Epoch) of this Calendar undefined. This means that isSet(field) will
	 * return false, and the date and time calculations will treat the field as if
	 * it had never been set. A Calendar implementation class may use the field's
	 * specific default value for date and time calculations. The HOUR_OF_DAY, HOUR
	 * and AM_PM fields are handled independently and the the resolution rule for
	 * the time of day is applied. Clearing one of the fields doesn't reset the hour
	 * of day value of this Calendar. Use set(Calendar.HOUR_OF_DAY, 0) to reset the
	 * hour value.
	 *
	 * @param field the calendar field to be cleared.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#clear(int)
	 */
	public final AdvancedCalendar clear(int field) {
		this.calendar.clear(field);
		return this;
	}

	/**
	 * Creates and returns a copy of this object.
	 *
	 * @return a copy of this object.
	 * @see Calendar#clone()
	 */
	@Override
	public AdvancedCalendar clone() {
		return new AdvancedCalendar((Calendar) this.calendar.clone());
	}

	/**
	 * Compares the time values (millisecond offsets from the Epoch) represented by
	 * two Calendar objects.
	 *
	 * @param anotherCalendar the Calendar to be compared.
	 * @return the value 0 if the time represented by the argument is equal to the
	 * time represented by this Calendar; a value less than 0 if the time of
	 * this Calendar is before the time represented by the argument; and a
	 * value greater than 0 if the time of this Calendar is after the time
	 * represented by the argument.
	 * @see Calendar#compareTo(Calendar)
	 */
	public int compareTo(Calendar anotherCalendar) {
		return this.calendar.compareTo(anotherCalendar);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.name.mymiller.lang.Object#equals(java.name.mymiller.
	 * extensions.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AdvancedCalendar)) {
			return false;
		}
		final AdvancedCalendar other = (AdvancedCalendar) obj;
		if (this.calendar == null) {
			if (other.calendar != null) {
				return false;
			}
		} else if (!this.calendar.equals(other.calendar)) {
			return false;
		}
		if (this.format == null) {
			return other.format == null;
		} else {
			return this.format.equals(other.format);
		}
	}

	/**
	 * Formats a Date into a date/time string.
	 *
	 * @return the formatted time string.
	 * @see java.text.DateFormat#format(Date)
	 */
	public final String format() {
		return this.format.format(this.calendar.getTime());
	}

	/**
	 * Formats a Date into a date/time string.
	 *
	 * @param pattern Pattern to format string
	 * @return the formatted time string.
	 * @see java.text.DateFormat#format(Date)
	 */
	public final String format(String pattern) {
		final String savedPattern = this.format.toPattern();
		this.format.applyPattern(pattern);
		final String formatted = this.format.format(this.calendar.getTime());
		this.format.applyPattern(savedPattern);
		return formatted;
	}

	/**
	 * Overrides Format. Formats a time object into a time string. Examples of time
	 * objects are a time value expressed in milliseconds and a Date object.
	 *
	 * @param pattern    Pattern to format string
	 * @param toAppendTo the string buffer for the returning time string.
	 * @param pos        keeps track of the position of the field within the
	 *                   returned string. On input: an alignment field, if desired.
	 *                   On output: the offsets of the alignment field. For example,
	 *                   given a time text "1996.07.10 AD at 15:08:56 PDT", if the
	 *                   given fieldPosition is DateFormat.YEAR_FIELD, the begin
	 *                   index and end index of fieldPosition will be set to 0 and
	 *                   4, respectively. Notice that if the same time field appears
	 *                   more than once in a pattern, the fieldPosition will be set
	 *                   for the first occurrence of that time field. For instance,
	 *                   formatting a Date to the time string "1 PM PDT (Pacific
	 *                   Daylight Time)" using the pattern "h a z (zzzz)" and the
	 *                   alignment field DateFormat.TIMEZONE_FIELD, the begin index
	 *                   and end index of fieldPosition will be set to 5 and 8,
	 *                   respectively, for the first occurrence of the timezone
	 *                   pattern character 'z'.
	 * @return the string buffer passed in as toAppendTo, with formatted text
	 * appended.
	 * @see SimpleDateFormat#format(Date, StringBuffer, FieldPosition)
	 */
	public StringBuffer format(String pattern, StringBuffer toAppendTo, FieldPosition pos) {
		final String savedPattern = this.format.toPattern();
		this.format.applyPattern(pattern);
		final StringBuffer stringBuffer = this.format.format(this.calendar.getTime(), toAppendTo, pos);
		this.format.applyPattern(savedPattern);
		return stringBuffer;
	}

	/**
	 * Overrides Format. Formats a time object into a time string. Examples of time
	 * objects are a time value expressed in milliseconds and a Date object.
	 *
	 * @param toAppendTo the string buffer for the returning time string.
	 * @param pos        keeps track of the position of the field within the
	 *                   returned string. On input: an alignment field, if desired.
	 *                   On output: the offsets of the alignment field. For example,
	 *                   given a time text "1996.07.10 AD at 15:08:56 PDT", if the
	 *                   given fieldPosition is DateFormat.YEAR_FIELD, the begin
	 *                   index and end index of fieldPosition will be set to 0 and
	 *                   4, respectively. Notice that if the same time field appears
	 *                   more than once in a pattern, the fieldPosition will be set
	 *                   for the first occurrence of that time field. For instance,
	 *                   formatting a Date to the time string "1 PM PDT (Pacific
	 *                   Daylight Time)" using the pattern "h a z (zzzz)" and the
	 *                   alignment field DateFormat.TIMEZONE_FIELD, the begin index
	 *                   and end index of fieldPosition will be set to 5 and 8,
	 *                   respectively, for the first occurrence of the timezone
	 *                   pattern character 'z'.
	 * @return the string buffer passed in as toAppendTo, with formatted text
	 * appended.
	 * @see SimpleDateFormat#format(Date, StringBuffer, FieldPosition)
	 */
	public StringBuffer format(StringBuffer toAppendTo, FieldPosition pos) {
		return this.format.format(this.calendar.getTime(), toAppendTo, pos);
	}

	/**
	 * Returns the value of the given calendar field. In lenient mode, all calendar
	 * fields are normalized. In non-lenient mode, all calendar fields are validated
	 * and this method throws an exception if any calendar fields have out-of-range
	 * values. The normalization and validation are handled by the complete()
	 * method, which process is calendar system dependent.
	 *
	 * @param field the given calendar field.
	 * @return the value for the given calendar field.
	 * @see Calendar#get(int)
	 */
	public int get(int field) {
		return this.calendar.get(field);
	}

	/**
	 * Returns the maximum value that the specified calendar field could have, given
	 * the time value of this Calendar. For example, the actual maximum value of the
	 * MONTH field is 12 in some years, and 13 in other years in the Hebrew calendar
	 * system. The default implementation of this method uses an iterative algorithm
	 * to determine the actual maximum value for the calendar field. Subclasses
	 * should, if possible, override this with a more efficient implementation.
	 *
	 * @param field the calendar field
	 * @return the maximum of the given calendar field for the time value of this
	 * Calendar
	 * @see Calendar#getActualMaximum(int)
	 */
	public int getActualMaximum(int field) {
		return this.calendar.getActualMaximum(field);
	}

	/**
	 * Returns the minimum value that the specified calendar field could have, given
	 * the time value of this Calendar. The default implementation of this method
	 * uses an iterative algorithm to determine the actual minimum value for the
	 * calendar field. Subclasses should, if possible, override this with a more
	 * efficient implementation - in many cases, they can simply return
	 * getMinimum().
	 *
	 * @param field the calendar field
	 * @return the minimum of the given calendar field for the time value of this
	 * Calendar
	 * @see Calendar#getActualMinimum(int)
	 */
	public int getActualMinimum(int field) {
		return this.calendar.getActualMinimum(field);
	}

	/**
	 * Returns an unmodifiable Set containing all calendar types supported by
	 * Calendar in the runtime environment. The available calendar types can be used
	 * for the Unicode locale extensions. The Set returned contains at least
	 * "gregory". The calendar types don't include aliases, such as "gregorian" for
	 * "gregory".
	 *
	 * @return an unmodifiable Set containing all available calendar types
	 */
	public Set<String> getAvailableCalendarTypes() {
		return Calendar.getAvailableCalendarTypes();
	}

	/**
	 * Returns an array of all locales for which the getInstance methods of this
	 * class can return localized instances.
	 *
	 * @return An array of locales for which localized Calendar instances are
	 * available.
	 */
	public Locale[] getAvailableLocales() {
		return Calendar.getAvailableLocales();
	}

	/**
	 * @return the calendar
	 */
	public final Calendar getCalendar() {
		return this.calendar;
	}

	/**
	 * @param calendar the calendar to set
	 * @return reference to this AdvancedCalendar
	 */
	public final AdvancedCalendar setCalendar(Calendar calendar) {
		this.calendar = calendar;
		return this;
	}

	/**
	 * Returns the calendar type of this Calendar. Calendar types are defined by the
	 * Unicode Locale Data Markup Language (LDML) specification. The default
	 * implementation of this method returns the class name of this Calendar
	 * instance. Any subclasses that implement LDML-defined calendar systems should
	 * override this method to return appropriate calendar types.
	 *
	 * @return the LDML-defined calendar type or the class name of this Calendar
	 * instance
	 * @see Calendar#getCalendarType()
	 */
	public String getCalendarType() {
		return this.calendar.getCalendarType();
	}

	/**
	 * @return Date object representing this date time
	 * @see Calendar#getTime()
	 */
	public final Date getDate() {
		return this.calendar.getTime();
	}

	/**
	 * Set the Date Time in this AdvancedCalendar
	 *
	 * @param date Date object to set this calendar to.
	 * @see Calendar#setTime(Date)
	 */
	public final void setDate(Date date) {
		this.calendar.setTime(date);
	}

	/**
	 * Gets a copy of the date and time format symbols of this date format.
	 *
	 * @return the date and time format symbols of this date format
	 * @see SimpleDateFormat#getDateFormatSymbols()
	 */
	public DateFormatSymbols getDateFormatSymbols() {
		return this.format.getDateFormatSymbols();
	}

	/**
	 * Returns the string representation of the calendar field value in the given
	 * style and locale. If no string representation is applicable, null is
	 * returned. This method calls get(field) to get the calendar field value if the
	 * string representation is applicable to the given calendar field. For example,
	 * if this Calendar is a GregorianCalendar and its date is 2005-01-01, then the
	 * string representation of the MONTH field would be "January" in the long style
	 * in an English locale or "Jan" in the short style. However, no string
	 * representation would be available for the DAY_OF_MONTH field, and this method
	 * would return null.
	 * <p>
	 * The default implementation supports the calendar fields for which a
	 * DateFormatSymbols has names in the given locale.
	 *
	 * @param field  the calendar field for which the string representation is
	 *               returned
	 * @param style  the style applied to the string representation; one of
	 *               SHORT_FORMAT (SHORT), SHORT_STANDALONE, LONG_FORMAT (LONG),
	 *               LONG_STANDALONE, NARROW_FORMAT, or NARROW_STANDALONE.
	 * @param locale the locale for the string representation (any calendar types
	 *               specified by locale are ignored)
	 * @return the string representation of the given field in the given style, or
	 * null if no string representation is applicable.
	 * @see Calendar#getDisplayName(int, int, Locale)
	 */
	public String getDisplayName(int field, int style, Locale locale) {
		return this.calendar.getDisplayName(field, style, locale);
	}

	/**
	 * Returns a Map containing all names of the calendar field in the given style
	 * and locale and their corresponding field values. For example, if this
	 * Calendar is a GregorianCalendar, the returned map would contain "Jan" to
	 * JANUARY, "Feb" to FEBRUARY, and so on, in the short style in an English
	 * locale. Narrow names may not be unique due to use of single characters, such
	 * as "S" for Sunday and Saturday. In that case narrow names are not included in
	 * the returned Map.
	 * <p>
	 * The values of other calendar fields may be taken into account to determine a
	 * set of display names. For example, if this Calendar is a lunisolar calendar
	 * system and the year value given by the YEAR field has a leap month, this
	 * method would return month names containing the leap month name, and month
	 * names are mapped to their values specific for the year.
	 * <p>
	 * The default implementation supports display names contained in a
	 * DateFormatSymbols. For example, if field is MONTH and style is ALL_STYLES,
	 * this method returns a Map containing all strings returned by
	 * DateFormatSymbols.getShortMonths() and DateFormatSymbols.getMonths().
	 *
	 * @param field  the calendar field for which the display names are returned
	 * @param style  the style applied to the string representation; one of
	 *               SHORT_FORMAT (SHORT), SHORT_STANDALONE, LONG_FORMAT (LONG),
	 *               LONG_STANDALONE, NARROW_FORMAT, or NARROW_STANDALONE
	 * @param locale the locale for the display names
	 * @return a Map containing all display names in style and locale and their
	 * field values, or null if no display names are defined for field
	 * @see Calendar#getDisplayNames(int, int, Locale)
	 */
	public Map<String, Integer> getDisplayNames(int field, int style, Locale locale) {
		return this.calendar.getDisplayNames(field, style, locale);
	}

	/**
	 * Gets what the first day of the week is; e.g., SUNDAY in the U.S., MONDAY in
	 * France.
	 *
	 * @return the first day of the week.
	 * @see Calendar#getFirstDayOfWeek()
	 */
	public int getFirstDayOfWeek() {
		return this.calendar.getFirstDayOfWeek();
	}

	/**
	 * Sets what the first day of the week is; e.g., SUNDAY in the U.S., MONDAY in
	 * France.
	 *
	 * @param value the given first day of the week.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#setFirstDayOfWeek(int)
	 */
	public AdvancedCalendar setFirstDayOfWeek(int value) {
		this.calendar.setFirstDayOfWeek(value);
		return this;
	}

	/**
	 * Returns the highest minimum value for the given calendar field of this
	 * Calendar instance. The highest minimum value is defined as the largest value
	 * returned by getActualMinimum(int) for any possible time value. The greatest
	 * minimum value depends on calendar system specific parameters of the instance.
	 *
	 * @param field the calendar field.
	 * @return the highest minimum value for the given calendar field.
	 * @see Calendar#getGreatestMinimum(int)
	 */
	public int getGreatestMinimum(int field) {
		return this.calendar.getGreatestMinimum(field);
	}

	/**
	 * @return a GregorianCalendar
	 */
	public final GregorianCalendar getGregorianCalendar() {
		final GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(this.calendar.getTime());
		return gregorianCalendar;
	}

	/**
	 * Returns the lowest maximum value for the given calendar field of this
	 * Calendar instance. The lowest maximum value is defined as the smallest value
	 * returned by getActualMaximum(int) for any possible time value. The least
	 * maximum value depends on calendar system specific parameters of the instance.
	 * For example, a Calendar for the Gregorian calendar system returns 28 for the
	 * DAY_OF_MONTH field, because the 28th is the last day of the shortest month of
	 * this calendar, February in a common year.
	 *
	 * @param field the calendar field.
	 * @return the lowest maximum value for the given calendar field.
	 * @see Calendar#getLeastMaximum(int)
	 */
	public int getLeastMaximum(int field) {
		return this.calendar.getLeastMaximum(field);
	}

	/**
	 * Returns the maximum value for the given calendar field of this Calendar
	 * instance. The maximum value is defined as the largest value returned by the
	 * get method for any possible time value. The maximum value depends on calendar
	 * system specific parameters of the instance.
	 *
	 * @param field the calendar field.
	 * @return the maximum value for the given calendar field.
	 * @see Calendar#getMaximum(int)
	 */
	public int getMaximum(int field) {
		return this.calendar.getMaximum(field);
	}

	/**
	 * Gets what the minimal days required in the first week of the year are; e.g.,
	 * if the first week is defined as one that contains the first day of the first
	 * month of a year, this method returns 1. If the minimal days required must be
	 * a full week, this method returns 7.
	 *
	 * @return the minimal days required in the first week of the year.
	 * @see Calendar#getMinimalDaysInFirstWeek()
	 */
	public int getMinimalDaysInFirstWeek() {
		return this.calendar.getMinimalDaysInFirstWeek();
	}

	/**
	 * Sets what the minimal days required in the first week of the year are; For
	 * example, if the first week is defined as one that contains the first day of
	 * the first month of a year, call this method with value 1. If it must be a
	 * full week, use value 7.
	 *
	 * @param value the given minimal days required in the first week of the year.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#setMinimalDaysInFirstWeek(int)
	 */
	public AdvancedCalendar setMinimalDaysInFirstWeek(int value) {
		this.calendar.setMinimalDaysInFirstWeek(value);
		return this;
	}

	/**
	 * Returns the minimum value for the given calendar field of this Calendar
	 * instance. The minimum value is defined as the smallest value returned by the
	 * get method for any possible time value. The minimum value depends on calendar
	 * system specific parameters of the instance.
	 *
	 * @param field the calendar field.
	 * @return the minimum value for the given calendar field.
	 * @see Calendar#getMinimum(int)
	 */
	public int getMinimum(int field) {
		return this.calendar.getMinimum(field);
	}

	/**
	 * Gets the number formatter which this date/time formatter uses to format and
	 * parse a time.
	 *
	 * @return the number formatter which this date/time formatter uses.
	 * @see java.text.DateFormat#getNumberFormat()
	 */
	public NumberFormat getNumberFormat() {
		return this.format.getNumberFormat();
	}

	/**
	 * Allows you to set the number formatter.
	 *
	 * @param newNumberFormat the given new NumberFormat.
	 * @see java.text.DateFormat#setNumberFormat(NumberFormat)
	 */
	public void setNumberFormat(NumberFormat newNumberFormat) {
		this.format.setNumberFormat(newNumberFormat);
	}

	/**
	 * Returns a string representing the current pattern
	 *
	 * @return String with the current pattern
	 */
	public final String getPattern() {
		return this.format.toPattern();
	}

	/**
	 * Returns this Calendar's time value in milliseconds.
	 *
	 * @return the current time as UTC milliseconds from the epoch.
	 * @see Calendar#getTimeInMillis()
	 */
	public long getTimeInMillis() {
		return this.calendar.getTimeInMillis();
	}

	/**
	 * Sets this Calendar's current time from the given long value.
	 *
	 * @param millis the new time in UTC milliseconds from the epoch.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#setTimeInMillis(long)
	 */
	public AdvancedCalendar setTimeInMillis(long millis) {
		this.calendar.setTimeInMillis(millis);
		return this;
	}

	/**
	 * Gets the time zone.
	 *
	 * @return the time zone object associated with this calendar.
	 * @see Calendar#getTimeZone()
	 */
	public TimeZone getTimeZone() {
		return this.calendar.getTimeZone();
	}

	/**
	 * Sets the time zone with the given time zone value.
	 *
	 * @param value the given time zone.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#setTimeZone(TimeZone)
	 */
	public AdvancedCalendar setTimeZone(TimeZone value) {
		this.calendar.setTimeZone(value);
		this.format.setTimeZone(value);
		return this;
	}

	/**
	 * Returns the number of weeks in the week year represented by this Calendar.
	 * The default implementation of this method throws an
	 * UnsupportedOperationException.
	 *
	 * @return the number of weeks in the week year.
	 * @see Calendar#getWeeksInWeekYear()
	 */
	public int getWeeksInWeekYear() {
		return this.calendar.getWeeksInWeekYear();
	}

	/**
	 * Returns the week year represented by this Calendar. The week year is in sync
	 * with the week cycle. The first day of the first week is the first day of the
	 * week year. The default implementation of this method throws an
	 * UnsupportedOperationException.
	 *
	 * @return the week year of this Calendar
	 * @see Calendar#getWeekYear()
	 */
	public int getWeekYear() {
		return this.calendar.getWeekYear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.name.mymiller.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.calendar == null) ? 0 : this.calendar.hashCode());
		result = (prime * result) + ((this.format == null) ? 0 : this.format.hashCode());
		return result;
	}

	/**
	 * Tells whether date/time interpretation is to be lenient.
	 *
	 * @return true if the interpretation mode of this calendar is lenient; false
	 * otherwise.
	 * @see Calendar#isLenient()
	 */
	public boolean isLenient() {
		return this.calendar.isLenient();
	}

	/**
	 * Specifies whether or not date/time interpretation is to be lenient. With
	 * lenient interpretation, a date such as "February 942, 1996" will be treated
	 * as being equivalent to the 941st day after February 1, 1996. With strict
	 * (non-lenient) interpretation, such dates will cause an exception to be
	 * thrown. The default is lenient.
	 *
	 * @param lenient true if the lenient mode is to be turned on; false if it is to
	 *                be turned off.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#setLenient(boolean)
	 */
	public AdvancedCalendar setLenient(boolean lenient) {
		this.calendar.setLenient(lenient);
		this.format.setLenient(lenient);
		return this;
	}

	/**
	 * Determines if the given calendar field has a value set, including cases that
	 * the value has been set by internal fields calculations triggered by a get
	 * method call. Parameters:
	 *
	 * @param field the calendar field.
	 * @return true if the given calendar field has a value set; false otherwise.
	 * @see Calendar#isSet(int)
	 */
	public final boolean isSet(int field) {
		return this.calendar.isSet(field);
	}

	/**
	 * Returns whether this Calendar supports week dates. The default implementation
	 * of this method returns false.
	 *
	 * @return true if this Calendar supports week dates; false otherwise.
	 * @see Calendar#isWeekDateSupported()
	 */
	public boolean isWeekDateSupported() {
		return this.calendar.isWeekDateSupported();
	}

	/**
	 * Adds or subtracts (up/down) a single unit of time on the given time field
	 * without changing larger fields. For example, to roll the current date up by
	 * one day, you can achieve it by calling: roll(Calendar.DATE, true). When
	 * rolling on the year or Calendar.YEAR field, it will roll the year value in
	 * the range between 1 and the value returned by calling
	 * getMaximum(Calendar.YEAR). When rolling on the month or Calendar.MONTH field,
	 * other fields like date might conflict and, need to be changed. For instance,
	 * rolling the month on the date 01/31/96 will result in 02/29/96. When rolling
	 * on the hour-in-day or Calendar.HOUR_OF_DAY field, it will roll the hour value
	 * in the range between 0 and 23, which is zero-based.
	 *
	 * @param field the calendar field.
	 * @param up    indicates if the value of the specified time field is to be
	 *              rolled up or rolled down. Use true if rolling up, false
	 *              otherwise.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#roll(int, boolean)
	 */
	public AdvancedCalendar roll(int field, boolean up) {
		this.calendar.roll(field, up);
		return this;
	}

	/**
	 * Adds the specified (signed) amount to the specified calendar field without
	 * changing larger fields. A negative amount means to roll down. NOTE: This
	 * default implementation on Calendar just repeatedly calls the version of
	 * roll() that rolls by one unit. This may not always do the right thing. For
	 * example, if the DAY_OF_MONTH field is 31, rolling through February will leave
	 * it set to 28. The GregorianCalendar version of this function takes care of
	 * this problem. Other subclasses should also provide overrides of this function
	 * that do the right thing.
	 *
	 * @param field  the calendar field.
	 * @param amount the signed amount to add to the calendar field.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#roll(int, int)
	 */
	public AdvancedCalendar roll(int field, int amount) {
		this.calendar.roll(field, amount);
		return this;
	}

	/**
	 * Compares two AdvancedCalendar entries to determine if they are the same day.
	 *
	 * @param compare AdvancedCalendar entry to compare against this one.
	 * @return True if they are the same year, month, day, Otherwise False.
	 */
	public boolean sameDay(AdvancedCalendar compare) {
		return (compare != null) && (this.get(AdvancedCalendar.YEAR) == compare.get(AdvancedCalendar.YEAR))
				&& (this.get(AdvancedCalendar.MONTH) == compare.get(AdvancedCalendar.MONTH))
				&& (this.get(AdvancedCalendar.DAY_OF_MONTH) == compare.get(AdvancedCalendar.DAY_OF_MONTH));
	}

	/**
	 * Sets the given calendar field to the given value. The value is not
	 * interpreted by this method regardless of the leniency mode.
	 *
	 * @param field the calendar field.
	 * @param value the value to be set for the given calendar field.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#set(int, int)
	 */
	public AdvancedCalendar set(int field, int value) {
		this.calendar.set(field, value);
		return this;
	}

	/**
	 * Sets the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
	 * Previous values of other calendar fields are retained. If this is not
	 * desired, call clear() first.
	 *
	 * @param year  the value used to set the YEAR calendar field.
	 * @param month the value used to set the MONTH calendar field. Month value is
	 *              0-based. e.g., 0 for January.
	 * @param date  the value used to set the DAY_OF_MONTH calendar field.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#set(int, int, int)
	 */
	public final AdvancedCalendar set(int year, int month, int date) {
		this.calendar.set(year, month, date);
		return this;
	}

	/**
	 * Sets the values for the calendar fields YEAR, MONTH, DAY_OF_MONTH,
	 * HOUR_OF_DAY, and MINUTE. Previous values of other fields are retained. If
	 * this is not desired, call clear() first.
	 *
	 * @param year      the value used to set the YEAR calendar field.
	 * @param month     the value used to set the MONTH calendar field. Month value
	 *                  is 0-based. e.g., 0 for January.
	 * @param date      the value used to set the DAY_OF_MONTH calendar field.
	 * @param hourOfDay the value used to set the HOUR_OF_DAY calendar field.
	 * @param minute    the value used to set the MINUTE calendar field.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#set(int, int, int, int, int)
	 */
	public final AdvancedCalendar set(int year, int month, int date, int hourOfDay, int minute) {
		this.calendar.set(year, month, date, hourOfDay, minute);
		return this;
	}

	/**
	 * Sets the values for the fields YEAR, MONTH, DAY_OF_MONTH, HOUR_OF_DAY,
	 * MINUTE, and SECOND. Previous values of other fields are retained. If this is
	 * not desired, call clear() first.
	 *
	 * @param year      the value used to set the YEAR calendar field.
	 * @param month     the value used to set the MONTH calendar field. Month value
	 *                  is 0-based. e.g., 0 for January.
	 * @param date      the value used to set the DAY_OF_MONTH calendar field.
	 * @param hourOfDay the value used to set the HOUR_OF_DAY calendar field.
	 * @param minute    the value used to set the MINUTE calendar field.
	 * @param second    the value used to set the SECOND calendar field.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#set(int, int, int, int, int, int)
	 */
	public final AdvancedCalendar set(int year, int month, int date, int hourOfDay, int minute, int second) {
		this.calendar.set(year, month, date, hourOfDay, minute, second);
		return this;
	}

	/**
	 * Sets the date of this Calendar with the the given date specifiers - week
	 * year, week of year, and day of week. Unlike the set method, all of the
	 * calendar fields and time values are calculated upon return.
	 * <p>
	 * If weekOfYear is out of the valid week-of-year range in weekYear, the
	 * weekYear and weekOfYear values are adjusted in lenient mode, or an
	 * IllegalArgumentException is thrown in non-lenient mode.
	 * <p>
	 * The default implementation of this method throws an
	 * UnsupportedOperationException.
	 *
	 * @param weekYear   the week year
	 * @param weekOfYear the week number based on weekYear
	 * @param dayOfWeek  the day of week value: one of the constants for the
	 *                   DAY_OF_WEEK field: SUNDAY, ..., SATURDAY.
	 * @return reference to this AdvancedCalendar
	 * @see Calendar#setWeekDate(int, int, int)
	 */
	public AdvancedCalendar setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
		this.calendar.setWeekDate(weekYear, weekOfYear, dayOfWeek);
		return this;
	}

	/**
	 * Subtract a number of days to this Advanced Calendar
	 *
	 * @param amount The number to subtract
	 * @return this Calendar reference
	 */
	public AdvancedCalendar subtractDays(int amount) {
		return this.add(Calendar.DAY_OF_YEAR, 0 - amount);
	}

	/**
	 * Subtract a number of years to this Advanced Calendar
	 *
	 * @param amount The number to subtract
	 * @return this Calendar reference
	 */
	public AdvancedCalendar subtractHours(int amount) {
		return this.add(Calendar.HOUR, 0 - amount);
	}

	/**
	 * Subtract a number of minutes to this Advanced Calendar
	 *
	 * @param amount The number to subtract
	 * @return this Calendar reference
	 */
	public AdvancedCalendar subtractMinutes(int amount) {
		return this.add(Calendar.MINUTE, 0 - amount);
	}

	/**
	 * Subtract a number of months to this Advanced Calendar
	 *
	 * @param amount The number to subtract
	 * @return this Calendar reference
	 */
	public AdvancedCalendar subtractMonths(int amount) {
		return this.add(Calendar.MONTH, 0 - amount);
	}

	/**
	 * Subtract a number of seconds to this Advanced Calendar
	 *
	 * @param amount The number to subtract
	 * @return this Calendar reference
	 */
	public AdvancedCalendar subtractSeconds(int amount) {
		return this.add(Calendar.SECOND, 0 - amount);
	}

	/**
	 * Subtract a number of years to this Advanced Calendar
	 *
	 * @param amount The number to subtract
	 * @return this Calendar reference
	 */
	public AdvancedCalendar subtractYears(int amount) {
		return this.add(Calendar.YEAR, 0 - amount);
	}

	/**
	 * Converts this object to an Instant. The conversion creates an Instant that
	 * represents the same point on the time-line as this Calendar.
	 *
	 * @return the instant representing the same point on the time-line
	 * @see Calendar#toInstant()
	 */
	public final Instant toInstant() {
		return this.calendar.toInstant();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.name.mymiller.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.format();
	}

	/**
	 * Returns the last day of the month for the Current Advanced Calendar
	 * @return AdvancedCalendar representing the last date of the month.
	 */
	public AdvancedCalendar getLastDateOfMonth(){
		Calendar cal = (Calendar)this.calendar.clone();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new AdvancedCalendar(cal.getTime().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
	}

	/**
	 * Returns the last day of the week for the Current Advanced Calendar
	 * @param endDayOfWeek Integer representing the day of the week that should be ended on.
	 * @return AdvancedCalendar representing the last date of the month.
	 */
	public AdvancedCalendar getLastDayOfWeek(int endDayOfWeek){
		Calendar cal = (Calendar)this.calendar.clone();
		cal.set(Calendar.DAY_OF_WEEK, endDayOfWeek);
		return new AdvancedCalendar(cal.getTime().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
	}

	/**
	 * Determine if an AdvancedCalendar is after another AdcancedCalendar
	 * @param calendar AdvancedCalendar to compare
	 * @return boolean indicating if this AdvancedCalendar is after the the compare one.
	 */
	private boolean isAfter(AdvancedCalendar calendar) {
		return this.getDate().after(calendar.getDate());
	}

	/**
	 * Determine if an AdvancedCalendar is before another AdcancedCalendar
	 * @param calendar AdvancedCalendar to compare
	 * @return boolean indicating if this AdvancedCalendar is before the the compare one.
	 */
	private boolean isBefore(AdvancedCalendar calendar) {
		return this.getDate().before(calendar.getDate());
	}

	/**
	 *
	 * @return AdvancedCalendar represented as a LocalDate
	 */
	public LocalDate toLocaleDate() {
		return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
	}

	/**
	 *
	 * @return AdvancedCalendar represented as a LocalDateTime
	 */
	public LocalDateTime toLocaleDateTime() {
		return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
	}

	/**
	 *
	 * @return AdvancedCalendar represented as a ZonedDateTime
	 */
	public ZonedDateTime toZonedDateTime() {
		return ZonedDateTime.ofInstant(this.calendar.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Apply a Function to Date Range of this AdvancedCalendar to end, by breaking the time period into months.
	 * @param end AdvancedCalendar that is the end of the range.
	 * @param function BiFunction<AdvancedCalendar,AdvancedCalendar,R> taking in a start and end AdvancedCalendar for the
	 *                 begining of the month and end of month. R the return type from the BiFunction
	 * @param <R> R the return type from the BiFunction
	 * @return List<R> containing the results from the BiFunction
	 */
	public <R> List<R> applyByMonth(AdvancedCalendar end, BiFunction<AdvancedCalendar, AdvancedCalendar, R> function) {
		ArrayList<R> results = new ArrayList<>();

		LocalDate startDate = this.toLocaleDate();
		LocalDate endDate = end.toLocaleDate();
		int startYear = startDate.getYear();
		int endYear = endDate.getYear();
		int startMonth = startDate.getMonthValue();
		int endMonth = endDate.getMonthValue();

		boolean exitLoop = false;

		for(int currentYear =  startYear; currentYear <= endYear; currentYear++) {
			for(int currentMonth = startMonth; currentMonth <= 12 ; currentMonth++) {

				AdvancedCalendar currentStartDateOfMonth = new AdvancedCalendar(LocalDate.of(currentYear,currentMonth,1));

				if(currentYear == startYear && currentMonth == startMonth) {
					currentStartDateOfMonth = this;
				}

				AdvancedCalendar lastDateOfCurrentMonth = currentStartDateOfMonth.getLastDateOfMonth();

				if(currentYear == endYear && currentMonth == endMonth) {
					lastDateOfCurrentMonth = end;
					exitLoop = true;
				}

				results.add(function.apply(currentStartDateOfMonth,
						lastDateOfCurrentMonth));

				if(exitLoop) {
					break;
				}
			}
			if(exitLoop) {
				break;
			}
		}

		return results;
	}

	/**
	 * Apply a Function to Date Range of this AdvancedCalendar to end, by breaking the time period into Weeks Sunday to Saturday.
	 * @param end AdvancedCalendar that is the end of the range.
	 * @param function BiFunction<AdvancedCalendar,AdvancedCalendar,R> taking in a start and end AdvancedCalendar for the
	 *                 begining of the month and end of month. R the return type from the BiFunction
	 * @param endDayOfWeek int representing the day to use as end of the week.
	 * @param <R> R the return type from the BiFunction
	 * @return List<R> containing the results from the BiFunction
	 */
	public <R> List<R> applyByWeek(AdvancedCalendar end,int endDayOfWeek, BiFunction<AdvancedCalendar, AdvancedCalendar, R> function) {
		ArrayList<R> results = new ArrayList<>();
		AdvancedCalendar currentStartDateOfWeek = this;
		AdvancedCalendar lastDateOfCurrentWeek = this.getLastDayOfWeek(endDayOfWeek);
		boolean exitLoop = false;

		do {
			if(lastDateOfCurrentWeek.isAfter(end)) {
				lastDateOfCurrentWeek = end;
				exitLoop = true;
			}

			if(!currentStartDateOfWeek.isAfter(end)) {
				results.add(function.apply(currentStartDateOfWeek,
						lastDateOfCurrentWeek));

				currentStartDateOfWeek = lastDateOfCurrentWeek.add(AdvancedCalendar.DAY_OF_MONTH, 1);
				lastDateOfCurrentWeek = currentStartDateOfWeek.getLastDateOfMonth();
			} else {
				exitLoop = true;
			}

		} while(!exitLoop);



		return results;
	}

	/**
	 * Apply a Function to Date Range of this AdvancedCalendar to end, by breaking the time period into Days
	 * @param end AdvancedCalendar that is the end of the range.
	 * @param function Function<AdvancedCalendar,R> taking in as the day. R the return type from the BiFunction
	 * @param <R> R the return type from the BiFunction
	 * @return List<R> containing the results from the BiFunction
	 */
	public <R> List<R> applyByDay(AdvancedCalendar end, Function< AdvancedCalendar, R> function) {
		ArrayList<R> results = new ArrayList<>();
		AdvancedCalendar currentStartDateOfMonth = this;
		boolean exitLoop = false;

		do {
			results.add(function.apply(currentStartDateOfMonth));
			currentStartDateOfMonth = currentStartDateOfMonth.add(AdvancedCalendar.DAY_OF_MONTH,1);

			if(currentStartDateOfMonth.isAfter(end)){
				exitLoop = true;
			}

		} while(!exitLoop);

		return results;
	}
}
