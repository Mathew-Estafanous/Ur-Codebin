package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteExpiration;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PasteExpirationToLocalDateTimeTest {

    private final Converter<PasteExpiration, LocalDateTime> converter = new PasteExpirationToLocalDateTime();

    @Test
    public void convertToLocalDateTimeWorks() {
        Assert.assertNull(converter.convertToModel(null, new ValueContext())
                            .getOrThrow(AssertionError::new));

        LocalDateTime testDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusHours(1);
        LocalDateTime timeResults = converter.convertToModel(PasteExpiration.ONEHOUR, new ValueContext())
                    .getOrThrow(AssertionError::new);

        Assert.assertEquals(testDateTime.getDayOfMonth(), timeResults.getDayOfMonth());
        Assert.assertEquals(testDateTime.getMonth(), timeResults.getMonth());
        Assert.assertEquals(testDateTime.getDayOfYear(), timeResults.getDayOfYear());
        Assert.assertEquals(testDateTime.getHour(), timeResults.getHour());
        Assert.assertEquals(testDateTime.getMinute(), timeResults.getMinute());
    }
}
