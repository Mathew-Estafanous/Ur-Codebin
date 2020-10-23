package com.urcodebin.helpers;

import com.urcodebin.enumerators.PasteExpiration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PasteExpirationHelper {

    public static LocalDateTime getLocalDateTimeFromChosenPasteExpiration(PasteExpiration expiration) {
        LocalDateTime offsetDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        switch (expiration){
            case TENMINUTES:
                offsetDateTime = offsetDateTime.plusMinutes(10);
                break;
            case THIRTYMINUTES:
                offsetDateTime = offsetDateTime.plusMinutes(30);
                break;
            case ONEHOUR:
                offsetDateTime = offsetDateTime.plusHours(1);
                break;
            case FIVEHOURS:
                offsetDateTime = offsetDateTime.plusHours(5);
                break;
            case ONEDAY:
                offsetDateTime = offsetDateTime.plusDays(1);
                break;
            case THREEDAYS:
                offsetDateTime = offsetDateTime.plusDays(3);
                break;
        }
        return offsetDateTime;
    }
}
