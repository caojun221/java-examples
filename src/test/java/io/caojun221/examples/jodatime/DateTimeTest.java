package io.caojun221.examples.jodatime;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

public class DateTimeTest {

    @Test
    public void testDateTimeUTC() {
        DateTime dateTime = new DateTime(DateTimeZone.UTC);
        long millisUTC = dateTime.getMillis();
        DateTime result = new DateTime(millisUTC, DateTimeZone.UTC);
        assertEquals(dateTime, result);
    }

    @Test
    public void testISODateTimeFormat() {
        DateTime dateTime = DateTime.now();
        System.out.println(dateTime);
        String str = dateTime.toString();
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        DateTime dateTime1 = parser.parseDateTime(str);
        System.out.println(dateTime1);
    }
}
