package com.urcodebin.convertors;

import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeTest {

    private final Converter<String, LocalDateTime> converter = new StringToLocalDateTime();

    private final String testString = "2020-10-26 20:16:56";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime testDateTime = LocalDateTime.parse(testString, formatter);

    @Test
    public void convertToLocalDateTimeWorks() {
        Assert.assertNull(converter.convertToModel(null, new ValueContext())
                .getOrThrow(AssertionError::new));
        Assert.assertEquals(testDateTime, converter.convertToModel(testString, new ValueContext())
                .getOrThrow(AssertionError::new));
    }

    @Test
    public void convertToStringWorks() {
        Assert.assertNull(converter.convertToPresentation(null, new ValueContext()));
        Assert.assertEquals(testString, converter.convertToPresentation(testDateTime, new ValueContext()));
    }
}
