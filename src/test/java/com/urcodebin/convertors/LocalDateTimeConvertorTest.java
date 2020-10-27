package com.urcodebin.convertors;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeConvertorTest {
    private final AttributeConverter<LocalDateTime, Timestamp> convertor = new LocalDateTimeAttributeConvertor();

    private final LocalDateTime testDateTime = LocalDateTime.parse("2020-10-24T10:11:30");
    private final Timestamp testDateTimestamp = Timestamp.valueOf(testDateTime);

    @Test
    public void convertToDatabaseTypeWorks() {
        Assert.assertNull(convertor.convertToDatabaseColumn(null));
        Assert.assertEquals(testDateTimestamp, convertor.convertToDatabaseColumn(testDateTime));
    }

    @Test
    public void convertToLocalDateTimeWorks() {
        Assert.assertNull(convertor.convertToEntityAttribute(null));
        Assert.assertEquals(testDateTime, convertor.convertToEntityAttribute(testDateTimestamp));
    }
}
