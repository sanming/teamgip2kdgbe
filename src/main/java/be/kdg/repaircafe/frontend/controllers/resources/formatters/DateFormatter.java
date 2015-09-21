package be.kdg.repaircafe.frontend.controllers.resources.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDateTime>
{
    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException
    {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", locale));
    }

    @Override
    public String print(LocalDateTime localDateTime, Locale locale)
    {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", locale));
    }
}
