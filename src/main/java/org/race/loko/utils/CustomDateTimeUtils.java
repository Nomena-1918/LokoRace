package org.race.loko.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class CustomDateTimeUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Utilisation de Locale.FRENCH pour avoir les noms des jours et des mois en français
    private static final DateTimeFormatter formatterFR = DateTimeFormatter.ofPattern("EEE d MMM yyyy à HH:mm:ss", Locale.FRENCH);

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static String formatFR(LocalDateTime dateTime) {
        String formattedDate = dateTime.format(formatterFR);

        // Capitalize the first letter of the day of the week
        return formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
    }

    public static LocalDateTime stringToDateTime(String string) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]
                {
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
                        DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss"),

                        DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm:ss"),
                        DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss"),

                        /*
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm")
                        */
                };
        string = string.trim();
        LocalDateTime dateTime = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                dateTime = LocalDateTime.parse(string, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        return dateTime;
    }

    public static LocalDate stringToDate(String string) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]
                {
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                        DateTimeFormatter.ofPattern("d/MM/yyyy"),

                        DateTimeFormatter.ofPattern("dd/M/yyyy"),
                        DateTimeFormatter.ofPattern("d/M/yyyy"),

                        /*
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm")
                        */
                };
        string = string.trim();
        LocalDate date = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                date = LocalDate.parse(string, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        return date;
    }


    public static LocalTime stringToTime(String string) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]
                {
                        DateTimeFormatter.ofPattern("HH:mm:ss"),
                        DateTimeFormatter.ofPattern("HH:mm"),

                };
        string = string.trim();
        LocalTime time = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                time = LocalTime.parse(string, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        return time;
    }


    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }


}
