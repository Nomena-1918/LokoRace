package org.race.loko.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtils {
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

	public LocalDateTime stringToDateTime(String string) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]
        {
            DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm"),

            DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm")
        };
        string = string.trim();
        LocalDateTime dateTime = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                dateTime = LocalDateTime.parse(string, formatter);
            }
            catch (DateTimeParseException ignored) {
            }
        }
        return dateTime;
    }


}
