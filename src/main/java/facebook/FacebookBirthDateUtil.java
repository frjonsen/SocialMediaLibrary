package facebook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
    }

    private static DateTimeFormatter buildFormatter(String format) {

        return new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.YEAR, 0)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
    }

    enum DateType {
        FULL(Pattern.compile("^(\\d{2}/){2}\\d{4}$"), "MM/dd/uuuu"),
        YEAR(Pattern.compile("^\\d{4}$"), "uuuu"),
        MONTHDAY(Pattern.compile("^\\d{2}/\\d{2}$"), "MM/dd"),
        INVALID(null, null);

        Pattern pattern;
        String formatter;

        DateType(Pattern pattern, String formatter) {
            this.pattern = pattern;
            this.formatter = formatter;
        }

        public Pattern getPattern() {
            return this.pattern;
        }

        public String getFormatter() {
            return this.formatter;
        }
    }

    public static DateType getDateType(String date) {

        if (date == null) {
            return DateType.INVALID;
        }
        DateType[] types = new DateType[] {DateType.FULL, DateType.YEAR, DateType.MONTHDAY};
        // Not as terrible as it looks. .filter is lazy, and won't run until findFirst is called
        Optional<DateType> type = Stream.of(types).filter(t -> t.getPattern().matcher(date).matches()).findFirst();
        return type.isPresent() ? type.get() : DateType.INVALID;
    }

    public static FacebookBirthDate formatDate(String date) {
        DateType type = FacebookBirthDateUtil.getDateType(date);
        if (type == DateType.INVALID) {
            return null;
        }
        return new FacebookBirthDate(LocalDate.parse(date, buildFormatter(type.getFormatter())), type);
    }
}
