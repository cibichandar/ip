package fein;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/** Converts Fein's numeric date-and-time input into a typed date and time. */
public final class DateTimeParser {
    /** The input format: day/month/year followed by a 24-hour time without a colon. */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter
            .ofPattern("d/M/uuuu HHmm", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    /** The readable format shown to the user for parsed date and time values. */
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter
            .ofPattern("MMM d uuuu, h:mm a", Locale.ENGLISH);

    private DateTimeParser() {
        // Utility class; do not instantiate.
    }

    /** Parses supported numeric input, returning null for natural-language deadlines. */
    public static LocalDateTime parse(String value) {
        if (value.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}")) {
            try {
                return LocalDateTime.parse(value, INPUT_FORMAT);
            } catch (DateTimeParseException exception) {
                return null;
            }
        }
        if (value.matches("[A-Za-z]{3} \\d{1,2} \\d{4}, \\d{1,2}:\\d{2} [AP]M")) {
            try {
                return LocalDateTime.parse(value, DISPLAY_FORMAT);
            } catch (DateTimeParseException exception) {
                return null;
            }
        }
        return null;
    }

    /** Formats a typed date and time for display and persistence. */
    public static String format(LocalDateTime value) {
        return value.format(DISPLAY_FORMAT);
    }
}
