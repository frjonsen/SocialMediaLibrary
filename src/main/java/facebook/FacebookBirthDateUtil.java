package facebook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Utility class to handle the different cases for how Facebook handles birthdates.
 */
public class FacebookBirthDateUtil {

    static class FacebookBirthDate {
        private LocalDate date;
        private DateType type;

        public FacebookBirthDate(LocalDate date, DateType type) {
            this.type = type;
            this.date = date;
        }

        public LocalDate getDate() {
            return date;
        }

        public DateType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "FacebookBirthDate{" +
                    "date=" + date +
                    ", type=" + type +
                    '}';
        }
    }

    private static DateTimeFormatter buildFormatter(String format) {

        return new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseStrict()
                .parseDefaulting(ChronoField.YEAR, 0)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
    }

    /**
     * The date types that may be received from Facebook
     * <li>{@link #FULL}</li>
     * <li>{@link #YEAR}</li>
     * <li>{@link #MONTHDAY}</li>
     * <li>{@link #INVALID}</li>
     */
    enum DateType {
        /**
         * A full date, formatted as MM/DD/YYYY
         */
        FULL(Pattern.compile("^(\\d{2}/){2}\\d{4}$"), "MM/dd/uuuu"),
        /**
         * A date consisting of only a year, as YYYY
         */
        YEAR(Pattern.compile("^\\d{4}$"), "uuuu"),
        /**
         * Date consisting of only a month and day of the month, as MM/DD
         */
        MONTHDAY(Pattern.compile("^\\d{2}/\\d{2}$"), "MM/dd"),
        /**
         * Should only be used when the date type couldn't be deduced
         */
        INVALID(null, null);

        Pattern pattern;
        String formatter;

        /**
         * Constructor for date type
         * @param pattern A regex which can be used to identify the date type
         * @param formatter The format of the date, as specified by DateTimeFormatter
         * @see DateTimeFormatter
         */
        DateType(Pattern pattern, String formatter) {
            this.pattern = pattern;
            this.formatter = formatter;
        }

        /**
         * Gets the regex pattern of this type
         * @return Regex Pattern
         */
        public Pattern getPattern() {
            return this.pattern;
        }

        /**
         * Gets the DateTimeFormatter format of this type
         * @return String formatted as specified by DateTimeFormatter
         */
        public String getFormatter() {
            return this.formatter;
        }
    }

    /**
     * Figures out the date type of a facebook date. Note that this function makes no checks whether the date
     * itself is valid. A date using the 13th month, as 13/01/2001 will as such still be considered a FULL type.
     * @see DateType
     * @param date A date formatted as one of the types specified by DateType
     * @return The deduced DateType of the input string. INVALID is returned if no type matched.
     */
    public static DateType getDateType(String date) {

        if (date == null) {
            return DateType.INVALID;
        }
        DateType[] types = new DateType[] {DateType.FULL, DateType.YEAR, DateType.MONTHDAY};
        // Not as terrible as it looks. .filter is lazy, and won't run until findFirst is called
        Optional<DateType> type = Stream.of(types).filter(t -> t.getPattern().matcher(date).matches()).findFirst();
        return type.isPresent() ? type.get() : DateType.INVALID;
    }

    /**
     * Converts a string date to a LocalDate object. As NULL is valid for parts of a date, missing parts will
     * default to day 1, month 1, year 0. The type of the date should always be checked before using the date contents.
     * @param date A date string, as ŕeturned by the Facebook API
     * @return Object consisting of the type of date, as well as a LocalDate
     */
    public static FacebookBirthDate formatDate(String date) {
        DateType type = FacebookBirthDateUtil.getDateType(date);
        if (type == DateType.INVALID) {
            return null;
        }
        return new FacebookBirthDate(LocalDate.parse(date, buildFormatter(type.getFormatter())), type);
    }


}
