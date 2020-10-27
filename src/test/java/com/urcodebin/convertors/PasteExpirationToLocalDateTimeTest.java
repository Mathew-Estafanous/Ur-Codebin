package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteExpiration;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public class PasteExpirationToLocalDateTimeTest {

    private final Converter<PasteExpiration, LocalDateTime> converter = new PasteExpirationToLocalDateTime();

    @Test
    public void convertToLocalDateTimeWorks() {
        Assert.assertNull(converter.convertToModel(null, new ValueContext())
                            .getOrThrow(AssertionError::new));

        Arrays.stream(PasteExpiration.values())
            .forEach(pasteExpiration -> {
                LocalDateTime expectedDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
                        .plusMinutes(pasteExpiration.getOffsetMin());
                LocalDateTime dateTimeResults = converter.convertToModel(pasteExpiration, new ValueContext())
                        .getOrThrow(AssertionError::new);
                Assert.assertEquals(expectedDateTime.getDayOfMonth(), dateTimeResults.getDayOfMonth());
                Assert.assertEquals(expectedDateTime.getMonth(), dateTimeResults.getMonth());
                Assert.assertEquals(expectedDateTime.getDayOfYear(), dateTimeResults.getDayOfYear());
                Assert.assertEquals(expectedDateTime.getHour(), dateTimeResults.getHour());
                Assert.assertEquals(expectedDateTime.getMinute(), dateTimeResults.getMinute());
            });
    }
}
