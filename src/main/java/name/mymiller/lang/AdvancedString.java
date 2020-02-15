/**
 * Copyright 2018 MyMiller Consulting LLC.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 *
 */
package name.mymiller.lang;

import name.mymiller.log.LogManager;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author jmiller
 *
 */
public class AdvancedString extends HTMLEditorKit.ParserCallback implements Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -6540815077831838076L;

    /**
     * Regex contain possible tags for Starting
     */
    private final static String HTML_TAG_START = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";

    /**
     * Regex containing possible tags for ending
     */
    private final static String HTML_TAG_END = "\\</\\w+\\>";

    /**
     * Regex containing possible tags for Self Closing
     */
    private final static String HTML_TAG_SELF_CLOSING = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";

    /**
     * Regext to match HTML Entity
     */
    private final static String HTML_ENTITY = "&[a-zA-Z][a-zA-Z0-9]+;";

    /**
     * Pattern including all of the tags.
     */
    private final static Pattern htmlPattern = Pattern
            .compile(
                    "(" + AdvancedString.HTML_TAG_START + ".*" + AdvancedString.HTML_TAG_END + ")|("
                            + AdvancedString.HTML_TAG_SELF_CLOSING + ")|(" + AdvancedString.HTML_ENTITY + ")",
                    Pattern.DOTALL);
    /**
     * Internal reference to the String
     */
    private String string = null;

    /**
     * @param string String to base the Advanced String on.
     */
    public AdvancedString(final String string) {
        super();
        this.string = string;
    }

    /**
     * Creates a new AdvancedString
     *
     * @param string Value to set as String
     * @return reference to this AdvancedString
     */
    public static AdvancedString create(final String string) {
        return new AdvancedString(string);
    }

    /**
     * Convert an array of strings to AdvancedStrings
     *
     * @param strings Strings to convert
     * @return Array of AdvancedStrings
     */
    public static AdvancedString[] create(final String[] strings) {
        final AdvancedString[] array = new AdvancedString[strings.length];

        for (int i = 0; i < strings.length; i++) {
            array[i] = AdvancedString.create(strings[i]);
        }

        return array;
    }

    /**
     * Returns a formatted string using the specified format string and arguments.
     *
     * @param format A format Strings
     * @param args   Arguments referenced by the format specifiers in the format
     *               string. If there are more arguments than format specifiers, the
     *               extra arguments are ignored. The number of arguments is
     *               variable and may be zero. The maximum number of arguments is
     *               limited by the maximum dimension of a Java array. The behaviour
     *               on a null argument depends on the conversion.
     * @return A formatted AdvancedString
     */
    public static AdvancedString format(final String format, Object... args) {
        return new AdvancedString(String.format(format, args));
    }

    /**
     * Formatable string to append to existing string
     *
     * @param format Format of string to append
     * @return reference to this AdvancedString
     */
    public AdvancedString append(String format) {
        this.string = this.string + format;

        return this;
    }

    /**
     * Formatable string to append to existing string
     *
     * @param format Format of string to append
     * @param args   Arguments for format specification
     * @return reference to this AdvancedString
     */
    public AdvancedString append(String format, Object args) {
        final String formattedString = String.format(format, args);
        this.string = this.string + formattedString;
        return this;
    }

    /**
     * Assigns a new String value to this object
     *
     * @param string New value to set as String
     * @return reference to this AdvancedString
     */
    public AdvancedString assign(final String string) {
        this.string = string;

        return this;
    }

    /**
     * @param index the index of the char value.
     * @return the char value at the specified index of this string. The first char
     *         value is at index 0.
     * @see String#charAt(int)
     */
    public char charAt(final int index) {
        return this.string.charAt(index);
    }

    /**
     * @return an IntStream of char values from this sequence
     * @see CharSequence#chars()
     */
    public IntStream chars() {
        return this.string.chars();
    }

    /**
     * @param index the index of the char value.
     * @return the code point value of the character at the index
     * @see String#codePointAt(int)
     */
    public int codePointAt(final int index) {
        return this.string.codePointAt(index);
    }

    /**
     * @param index the index of the char value.
     * @return the Unicode code point value before the given index.
     * @see String#codePointBefore(int)
     */
    public int codePointBefore(final int index) {
        return this.string.codePointBefore(index);
    }

    /**
     * @param beginIndex the index to the first char of the text range.
     * @param endIndex   the index after the last char of the text range.
     * @return the number of Unicode code points in the specified text range
     * @see String#codePointCount(int, int)
     */
    public int codePointCount(final int beginIndex, final int endIndex) {
        return this.string.codePointCount(beginIndex, endIndex);
    }

    /**
     * @return an IntStream of Unicode code points from this sequence
     * @see CharSequence#codePoints()
     */
    public IntStream codePoints() {
        return this.string.codePoints();
    }

    /**
     * @param anotherString the String to be compared.
     * @return the value 0 if the argument string is equal to this string; a value
     *         less than 0 if this string is lexicographically less than the string
     *         argument; and a value greater than 0 if this string is
     *         lexicographically greater than the string argument.
     * @see String#compareTo(String)
     */
    public int compareTo(final String anotherString) {
        return this.string.compareTo(anotherString);
    }

    /**
     * @param str the String to be compared.
     * @return a negative integer, zero, or a positive integer as the specified
     *         String is greater than, equal to, or less than this String, ignoring
     *         case considerations.
     *
     * @see String#compareToIgnoreCase(String)
     */
    public int compareToIgnoreCase(final String str) {
        return this.string.compareToIgnoreCase(str);
    }

    /**
     * @param str the String that is concatenated to the end of this String.
     * @return the String that is concatenated to the end of this String.
     * @see String#concat(String)
     */
    public AdvancedString concat(final String str) {
        return AdvancedString.create(this.string.concat(str));
    }

    /**
     * @param str he sequence to search for
     * @return true if this string contains s, false otherwise
     * @see String#contains(CharSequence)
     */
    public boolean contains(final CharSequence str) {
        return this.string.contains(str);
    }

    /**
     * Will return true if s contains HTML markup tags or entities.
     *
     * @return true if string contains HTML
     */
    public boolean containsHtml() {
        boolean ret = false;
        if (this.string != null) {
            ret = AdvancedString.htmlPattern.matcher(this.string).find();
        }
        return ret;
    }

    /**
     * @param cs The sequence to compare this String against
     * @return true if this String represents the same sequence of char values as
     *         the specified sequence, false otherwise
     * @see String#contentEquals(CharSequence)
     */
    public boolean contentEquals(final CharSequence cs) {
        return this.string.contentEquals(cs);
    }

    /**
     * @param sb The StringBuffer to compare this String against
     * @return true if this String represents the same sequence of characters as the
     *         specified StringBuffer, false otherwise
     * @see String#contentEquals(StringBuffer)
     */
    public boolean contentEquals(final StringBuffer sb) {
        return this.string.contentEquals(sb);
    }

    /**
     * @param suffix the suffix.
     * @return true if the character sequence represented by the argument is a
     *         suffix of the character sequence represented by this object; false
     *         otherwise. Note that the result will be true if the argument is the
     *         empty string or is equal to this String object as determined by the
     *         equals(Object) method.
     * @see String#endsWith(String)
     */
    public boolean endsWith(final String suffix) {
        return this.string.endsWith(suffix);
    }

    /**
     * Compares this string to the specified object. The result is true if and only
     * if the argument is not null and is a String object that represents the same
     * sequence of characters as this object.
     *
     * @param anObject The object to compare this String against
     * @return true if the given object represents a String equivalent to this
     *         string, false otherwise
     * @see String#equals(Object)
     */
    @Override
    public boolean equals(final Object anObject) {
        return this.string.equals(anObject);
    }

    /**
     * Compares this String to another String, ignoring case considerations. Two
     * strings are considered equal ignoring case if they are of the same length and
     * corresponding characters in the two strings are equal ignoring case. Two
     * characters c1 and c2 are considered the same ignoring case if at least one of
     * the following is true:
     *
     * The two characters are the same (as compared by the == operator) Applying the
     * method Character.toUpperCase(char) to each character produces the same result
     * Applying the method Character.toLowerCase(char) to each character produces
     * the same result
     *
     * @param anotherString The String to compare this String against
     * @return true if the argument is not null and it represents an equivalent
     *         String ignoring case; false otherwise
     * @see String#equalsIgnoreCase(String)
     */
    public boolean equalsIgnoreCase(final String anotherString) {
        return this.string.equalsIgnoreCase(anotherString);
    }

    /**
     * @return The resultant byte array
     * @see String#getBytes()
     */
    public byte[] getBytes() {
        return this.string.getBytes();
    }

    /**
     * Encodes this String into a sequence of bytes using the given charset, storing
     * the result into a new byte array. This method always replaces malformed-input
     * and unmappable-character sequences with this charset's default replacement
     * byte array. The CharsetEncoder class should be used when more control over
     * the encoding process is required.
     *
     * @param charset The Charset to be used to encode the String
     * @return The resultant byte array
     * @see String#getBytes(Charset)
     */
    public byte[] getBytes(final Charset charset) {
        return this.string.getBytes(charset);
    }

    /**
     * @param charsetName The name of a supported charset
     * @return The resultant byte array
     * @throws UnsupportedEncodingException If the named charset is not supported
     * @see String#getBytes(String)
     */
    public byte[] getBytes(final String charsetName) throws UnsupportedEncodingException {
        return this.string.getBytes(charsetName);
    }

    /**
     * @param srcBegin index of the first character in the string to copy.
     * @param srcEnd   index after the last character in the string to copy.
     * @param dst      the destination array.
     * @param dstBegin the start offset in the destination array.
     * @see String#getChars(int, int, char[], int)
     */
    public void getChars(final int srcBegin, final int srcEnd, final char[] dst, final int dstBegin) {
        this.string.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    /**
     * Split the string into paragraphs based on blank lines between paragraphs.
     *
     * @return Array of AdvancedString containing each paragraph.
     */
    public AdvancedString[] getParagraphs() {
        final ArrayList<AdvancedString> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(this.string)) {
            scanner.useDelimiter("(?m:^$)");
            while (scanner.hasNext()) {
                list.add(new AdvancedString(scanner.next()));
            }
        }

        return list.toArray(new AdvancedString[list.size()]);
    }

    /**
     * Determines the number of words this string contains, based on whitespace.
     *
     * @return int for the number of words in this string.
     */
    public int getWordCount() {
        int wordCount = 0;
        try (Scanner scanner = new Scanner(this.string)) {
            while (scanner.hasNext()) {
                scanner.next();
                wordCount++;
            }
        }
        return wordCount;
    }

    /**
     * Method used by the Remove Text to process calls for removing HTML.
     */
    @Override
    public void handleText(final char[] text, final int pos) {
        final StringBuffer buffer = new StringBuffer(this.string);
        buffer.append(text);
        this.string = buffer.toString();
    }

    /**
     *
     * @return List of hastags contained in the string.
     */
    public List<AdvancedString> hashTags() {
        final String[] split = this.string.split(" ");
        if ((split == null) || (split.length == 0)) {
            return Collections.emptyList();
        }
        return Arrays.stream(split).filter(tag -> tag.startsWith("#")).map(tag -> new AdvancedString(tag))
                .collect(Collectors.toList());
    }

    /**
     * @param ch a character (Unicode code point).
     * @return the index of the first occurrence of the character in the character
     *         sequence represented by this object, or -1 if the character does not
     *         occur.
     * @see String#indexOf(int)
     */
    public int indexOf(final int ch) {
        return this.string.indexOf(ch);
    }

    /**
     * @param ch        a character (Unicode code point).
     * @param fromIndex the index to start the search from.
     * @return the index of the first occurrence of the character in the character
     *         sequence represented by this object that is greater than or equal to
     *         fromIndex, or -1 if the character does not occur.
     * @see String#indexOf(int, int)
     */
    public int indexOf(final int ch, final int fromIndex) {
        return this.string.indexOf(ch, fromIndex);
    }

    /**
     * @param str the substring to search for.
     * @return the index of the first occurrence of the specified substring, or -1
     *         if there is no such occurrence.
     * @see String#indexOf(String)
     */
    public int indexOf(final String str) {
        return this.string.indexOf(str);
    }

    /**
     * @param str       the substring to search for.
     * @param fromIndex the index from which to start the search.
     * @return the index of the first occurrence of the specified substring,
     *         starting at the specified index, or -1 if there is no such
     *         occurrence.
     * @see String#indexOf(String, int)
     */
    public int indexOf(final String str, final int fromIndex) {
        return this.string.indexOf(str, fromIndex);
    }

    /**
     * @return a string that has the same contents as this string, but is guaranteed
     *         to be from a pool of unique strings.
     * @see String#intern()
     */
    public AdvancedString intern() {
        return AdvancedString.create(this.string.intern());
    }

    /**
     * @return true if length() is 0, otherwise false
     * @see String#isEmpty()
     */
    public boolean isEmpty() {
        return this.string.isEmpty();
    }

    /**
     * @param ch a character (Unicode code point).
     * @return the index of the last occurrence of the character in the character
     *         sequence represented by this object, or -1 if the character does not
     *         occur.
     * @see String#lastIndexOf(int)
     */
    public int lastIndexOf(final int ch) {
        return this.string.lastIndexOf(ch);
    }

    /**
     * @param ch        a character (Unicode code point).
     * @param fromIndex the index to start the search from. There is no restriction
     *                  on the value of fromIndex. If it is greater than or equal to
     *                  the length of this string, it has the same effect as if it
     *                  were equal to one less than the length of this string: this
     *                  entire string may be searched. If it is negative, it has the
     *                  same effect as if it were -1: -1 is returned.
     * @return the index of the last occurrence of the character in the character
     *         sequence represented by this object that is less than or equal to
     *         fromIndex, or -1 if the character does not occur before that point.
     * @see String#lastIndexOf(int, int)
     */
    public int lastIndexOf(final int ch, final int fromIndex) {
        return this.string.lastIndexOf(ch, fromIndex);
    }

    /**
     * @param str the substring to search for.
     * @return the index of the last occurrence of the specified substring, or -1 if
     *         there is no such occurrence.
     * @see String#lastIndexOf(String)
     */
    public int lastIndexOf(final String str) {
        return this.string.lastIndexOf(str);
    }

    /**
     * @param str       the substring to search for.
     * @param fromIndex the index to start the search from.
     * @return the index of the last occurrence of the specified substring,
     *         searching backward from the specified index, or -1 if there is no
     *         such occurrence.
     * @see String#lastIndexOf(String, int)
     */
    public int lastIndexOf(final String str, final int fromIndex) {
        return this.string.lastIndexOf(str, fromIndex);
    }

    /**
     * @return the length of the sequence of characters represented by this object.
     * @see String#length()
     */
    public int length() {
        return this.string.length();
    }

    /**
     * @param regex the regular expression to which this string is to be matched
     * @return true if, and only if, this string matches the given regular
     *         expression
     * @see String#matches(String)
     */
    public boolean matches(final String regex) {
        return this.string.matches(regex);
    }

    /**
     * @param index           the index to be offset
     * @param codePointOffset the offset in code points
     * @return the index within this String
     * @see String#offsetByCodePoints(int, int)
     */
    public int offsetByCodePoints(final int index, final int codePointOffset) {
        return this.string.offsetByCodePoints(index, codePointOffset);
    }

    /**
     * Method to format strings into paragraphs.
     *
     * @param maxWidth Maximum width to make paragraph.
     * @return Formatted String with \n
     */
    public AdvancedString paragraphFormat(final int maxWidth) {
        String formatted = "";

        if (this.string.length() < maxWidth) {
            formatted = this.string;
        } else {
            final String work = this.string.trim();
            int charCount = 0;

            for (final char c : work.toCharArray()) {
                if (c != '\n') {
                    if (charCount < maxWidth) {
                        formatted += c;
                        charCount++;
                    } else {
                        formatted += '\n';
                        formatted += c;
                        charCount = 0;
                    }
                } else {
                    formatted += c;
                    charCount = 0;
                }
            }
        }

        return AdvancedString.create(formatted);
    }

    /**
     * @param ignoreCase if true, ignore case when comparing characters.
     * @param toffset    the starting offset of the subregion in this string.
     * @param other      the string argument.
     * @param ooffset    the starting offset of the subregion in the string
     *                   argument.
     * @param len        the number of characters to compare.
     * @return true if the specified subregion of this string matches the specified
     *         subregion of the string argument; false otherwise. Whether the
     *         matching is exact or case insensitive depends on the ignoreCase
     *         argument. startsWith
     * @see String#regionMatches(boolean, int, String, int, int)
     */
    public boolean regionMatches(final boolean ignoreCase, final int toffset, final String other, final int ooffset,
                                 final int len) {
        return this.string.regionMatches(ignoreCase, toffset, other, ooffset, len);
    }

    /**
     * @param toffset the starting offset of the subregion in this string.
     * @param other   the string argument.
     * @param ooffset the starting offset of the subregion in the string argument.
     * @param len     the number of characters to compare.
     * @return true if the specified subregion of this string exactly matches the
     *         specified subregion of the string argument; false otherwise.
     * @see String#regionMatches(int, String, int, int)
     */
    public boolean regionMatches(final int toffset, final String other, final int ooffset, final int len) {
        return this.string.regionMatches(toffset, other, ooffset, len);
    }

    /**
     * Remove any HTML from this string.
     */
    public void removeHTML() {
        if (this.containsHtml()) {
            final String original = this.string;
            final Reader in = new StringReader(original);

            try {
                final ParserDelegator delegator = new ParserDelegator();
                delegator.parse(in, this, Boolean.TRUE);
                in.close();
            } catch (final IOException e) {
                LogManager.getLogger(this.getClass()).log(Level.SEVERE, "Failed to remove HTML from: " + original, e);
            }
        }
    }

    /**
     * @param oldChar the old character.
     * @param newChar the new character.
     * @return a string derived from this string by replacing every occurrence of
     *         oldChar with newChar.
     * @see String#replace(char, char)
     */
    public AdvancedString replace(final char oldChar, final char newChar) {
        return AdvancedString.create(this.string.replace(oldChar, newChar));
    }

    /**
     * @param target      The sequence of char values to be replaced
     * @param replacement The replacement sequence of char values
     * @return The resulting string
     * @see String#replace(CharSequence, CharSequence)
     */
    public AdvancedString replace(final CharSequence target, final CharSequence replacement) {
        return AdvancedString.create(this.string.replace(target, replacement));
    }

    /**
     * @param regex       the regular expression to which this string is to be
     *                    matched
     * @param replacement the string to be substituted for each match
     * @return The resulting String
     * @see String#replaceAll(String, String)
     */
    public AdvancedString replaceAll(final String regex, final String replacement) {
        return AdvancedString.create(this.string.replaceAll(regex, replacement));
    }

    /**
     * @param regex       the regular expression to which this string is to be
     *                    matched
     * @param replacement the string to be substituted for each match
     * @return The resulting String
     * @see String#replaceFirst(String, String)
     */
    public AdvancedString replaceFirst(final String regex, final String replacement) {
        return AdvancedString.create(this.string.replaceFirst(regex, replacement));
    }

    /**
     * @param regex the delimiting regular expression
     * @return the array of strings computed by splitting this string around matches
     *         of the given regular expression
     * @see String#split(String)
     */
    public AdvancedString[] split(final String regex) {
        return AdvancedString.create(this.string.split(regex));
    }

    /**
     * Splits this string around matches of the given regular expression. The array
     * returned by this method contains each substring of this string that is
     * terminated by another substring that matches the given expression or is
     * terminated by the end of the string. The substrings in the array are in the
     * order in which they occur in this string. If the expression does not match
     * any part of the input then the resulting array has just one element, namely
     * this string.
     *
     * When there is a positive-width match at the beginning of this string then an
     * empty leading substring is included at the beginning of the resulting array.
     * A zero-width match at the beginning however never produces such empty leading
     * substring.
     *
     * The limit parameter controls the number of times the pattern is applied and
     * therefore affects the length of the resulting array. If the limit n is
     * greater than zero then the pattern will be applied at most n - 1 times, the
     * array's length will be no greater than n, and the array's last entry will
     * contain all input beyond the last matched delimiter. If n is non-positive
     * then the pattern will be applied as many times as possible and the array can
     * have any length. If n is zero then the pattern will be applied as many times
     * as possible, the array can have any length, and trailing empty strings will
     * be discarded.
     *
     *
     * @param regex the delimiting regular expression
     * @param limit the result threshold, as described above
     * @return the array of strings computed by splitting this string around matches
     *         of the given regular expression
     * @see String#split(String, int)
     */
    public AdvancedString[] split(final String regex, final int limit) {
        return AdvancedString.create(this.string.split(regex, limit));
    }

    /**
     * @param prefix the prefix.
     * @return true if the character sequence represented by the argument is a
     *         prefix of the character sequence represented by this string; false
     *         otherwise. Note also that true will be returned if the argument is an
     *         empty string or is equal to this String object as determined by the
     *         equals(Object) method.
     * @see String#startsWith(String)
     */
    public boolean startsWith(final String prefix) {
        return this.string.startsWith(prefix);
    }

    /**
     * @param prefix  the prefix.
     * @param toffset where to begin looking in this string.
     * @return true if the character sequence represented by the argument is a
     *         prefix of the substring of this object starting at index toffset;
     *         false otherwise. The result is false if toffset is negative or
     *         greater than the length of this String object; otherwise the result
     *         is the same as the result of the expression
     *         this.substring(toffset).startsWith(prefix)
     *
     * @see String#startsWith(String, int)
     */
    public boolean startsWith(final String prefix, final int toffset) {
        return this.string.startsWith(prefix, toffset);
    }

    /**
     * @param beginIndex the begin index, inclusive.
     * @param endIndex   the end index, exclusive.
     * @return the specified subsequence.
     * @see String#subSequence(int, int)
     */
    public CharSequence subSequence(final int beginIndex, final int endIndex) {
        return this.string.subSequence(beginIndex, endIndex);
    }

    /**
     * @param beginIndex the beginning index, inclusive.
     * @return the specified substring.
     * @see String#substring(int)
     */
    public AdvancedString substring(final int beginIndex) {
        return new AdvancedString(this.string.substring(beginIndex));
    }

    /**
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @return the specified substring.
     * @see String#substring(int, int)
     */
    public AdvancedString substring(final int beginIndex, final int endIndex) {
        return new AdvancedString(this.string.substring(beginIndex, endIndex));
    }

    /**
     * Convert a string with Date/Time stamp to an AdvancedCalendar object.
     *
     * @param pattern String containing the Pattern to parse the dateTime
     * @return AdvancedCalendar specifying the date.
     * @throws ParseException Error parsing the string by the described pattern.
     */
    public AdvancedCalendar toAdvancedCalendar(String pattern) throws ParseException {
        return AdvancedCalendar.parse(pattern, this.string);
    }

    /**
     * @return a newly allocated character array whose length is the length of this
     *         string and whose contents are initialized to contain the character
     *         sequence represented by this string.
     * @see String#toCharArray()
     */
    public char[] toCharArray() {
        return this.string.toCharArray();
    }

    /**
     * @return the String, converted to lowercase.
     * @see String#toLowerCase()
     */
    public AdvancedString toLowerCase() {
        return new AdvancedString(this.string.toLowerCase());
    }

    /**
     * @param locale use the case transformation rules for this locale
     * @return the String, converted to lowercase.
     * @see String#toLowerCase(Locale)
     */
    public AdvancedString toLowerCase(final Locale locale) {
        return new AdvancedString(this.string.toLowerCase(locale));
    }

    /**
     * @return the string
     * @see String#toString()
     */
    @Override
    public String toString() {
        return this.string;
    }

    /**
     * @return the String, converted to uppercase.
     * @see String#toUpperCase()
     */
    public AdvancedString toUpperCase() {
        return new AdvancedString(this.string.toUpperCase());
    }

    /**
     * @param locale use the case transformation rules for this locale
     * @return the String, converted to uppercase.
     * @see String#toUpperCase(Locale)
     */
    public AdvancedString toUpperCase(final Locale locale) {
        return new AdvancedString(this.string.toUpperCase(locale));
    }

    /**
     * @return A string whose value is this string, with any leading and trailing
     *         white space removed, or this string if it has no leading or trailing
     *         white space.
     * @see String#trim()
     */
    public AdvancedString trim() {
        return new AdvancedString(this.string.trim());
    }
}
