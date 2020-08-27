package name.mymiller.utils;

import name.mymiller.lang.AdvancedString;
import java.util.logging.Logger;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils extends ObjectUtils {

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
                    "(" + StringUtils.HTML_TAG_START + ".*" + StringUtils.HTML_TAG_END + ")|("
                            + StringUtils.HTML_TAG_SELF_CLOSING + ")|(" + StringUtils.HTML_ENTITY + ")",
                    Pattern.DOTALL);

    /**
     * ObjectUtil a String against another string checking for NULL
     *
     * @param object Object to compare
     * @param value  Value to to check
     * @return True if equals()
     */
    static public boolean compare(String object, String value) {
        if ((object == null) && (value == null)) {
            return true;
        }
        if (object != null) {
            return object.equals(value);
        }

        return false;
    }

    /**
     * Convert a BigDecimal to String if not null
     *
     * @param big       BigDecimal to convert
     * @param nullValue String to output if BigDecimal is null
     * @return String value of big
     */
    static public String notNull(BigDecimal big, String nullValue) {
        if (big == null) {
            return nullValue;
        }
        return big.toPlainString();
    }

    /**
     * Return a String if not null
     *
     * @param value     Long.toString() to return
     * @param nullValue String to return if null
     * @return String to use for value.
     */
    static public String notNull(Long value, String nullValue) {
        if (value == null) {
            return nullValue;
        }
        return value.toString();
    }

    /**
     * Return a String if not null
     *
     * @param string    String to return
     * @param nullValue String to return if null
     * @return String to use for value.
     */
    static public String notNull(String string, String nullValue) {
        if (string == null) {
            return nullValue;
        }
        return string;
    }

    /**
     * Determines the number of words this string contains, based on whitespace.
     *
     * @return int for the number of words in this string.
     */
    public int getWordCount(String string) {
        int wordCount = 0;
        try (Scanner scanner = new Scanner(string)) {
            while (scanner.hasNext()) {
                scanner.next();
                wordCount++;
            }
        }
        return wordCount;
    }

    /**
     *
     * @return List of hastags contained in the string.
     */
    public List<String> hashTags(String string) {
        final String[] split = string.split(" ");
        if ((split == null) || (split.length == 0)) {
            return Collections.emptyList();
        }
        return Arrays.stream(split).filter(tag -> tag.startsWith("#")).collect(Collectors.toList());
    }

    /**
     * Method to format strings into paragraphs.
     *
     * @param maxWidth Maximum width to make paragraph.
     * @return Formatted String with \n
     */
    public static String paragraphFormat(String string, final int maxWidth) {
        String formatted = "";

        if (string.length() < maxWidth) {
            formatted = string;
        } else {
            final String work = string.trim();
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

        return formatted;
    }

    /**
     * Remove any HTML from this string.
     */
    public static String removeHTML(String string) {
        String cleanString = "";
        if (StringUtils.containsHtml(string)) {
            final Reader in = new StringReader(string);
            final StringBuilder cleaned = new StringBuilder();

            HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback()
            {
                public void handleText(char[] data, int pos)
                {
                    cleaned.append(new String(data)).append(' ');
                }
            };

            try {
                final ParserDelegator delegator = new ParserDelegator();
                delegator.parse(in, callback, Boolean.TRUE);
                in.close();
            } catch (final IOException e) {
                Logger.getLogger(StringUtils.class.getName()).log(Level.SEVERE, "Failed to remove HTML from: " + string, e);
            }

            return cleaned.toString();
        }

        return string;
    }

    /**
     * Will return true if s contains HTML markup tags or entities.
     *
     * @return true if string contains HTML
     */
    public static boolean containsHtml(String string) {
        boolean ret = false;
        if (string != null) {
            ret = StringUtils.htmlPattern.matcher(string).find();
        }
        return ret;
    }
}
