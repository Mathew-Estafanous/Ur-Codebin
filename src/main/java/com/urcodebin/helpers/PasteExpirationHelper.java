package com.urcodebin.helpers;

import com.urcodebin.enumerators.PasteExpiration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PasteExpirationHelper {

    public static LocalDateTime getLocalDateTimeFromChosenPasteExpiration(PasteExpiration expiration) {
        LocalDateTime offsetDateTime;
        switch (expiration){
            case TENMINUTES:
                offsetDateTime = createLocalDateTimeFromOffset(0, 10);
                break;
            case THIRTYMINUTES:
                offsetDateTime = createLocalDateTimeFromOffset(0, 30);
                break;
            case ONEHOUR:
                offsetDateTime = createLocalDateTimeFromOffset(1, 0);
                break;
            case FIVEHOURS:
                offsetDateTime = createLocalDateTimeFromOffset(5,0);
                break;
            case ONEDAY:
                offsetDateTime = createLocalDateTimeFromOffset(24, 0);
                break;
            case THREEDAYS:
                offsetDateTime = createLocalDateTimeFromOffset(72, 0);
                break;
            default:
                offsetDateTime = createLocalDateTimeFromOffset(0, 0);
        }
        return offsetDateTime;
    }

    private static LocalDateTime createLocalDateTimeFromOffset(int hours, int minutes) {
        return Instant.now().atOffset(ZoneOffset.ofHoursMinutes(hours, minutes)).toLocalDateTime();
    }
}
