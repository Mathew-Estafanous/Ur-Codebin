package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteExpiration;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PasteExpirationToLocalDateTime implements Converter<PasteExpiration, LocalDateTime> {
    @Override
    public Result<LocalDateTime> convertToModel(PasteExpiration pasteExpiration, ValueContext valueContext) {
        if(pasteExpiration == null)
            return Result.ok(null);
        else {
            LocalDateTime dateTime = getLocalDateTimeFromChosenPasteExpiration(pasteExpiration);
            return Result.ok(dateTime);
        }
    }

    @Override
    public PasteExpiration convertToPresentation(LocalDateTime s, ValueContext valueContext) {
        return PasteExpiration.TENMINUTES;
    }

    private LocalDateTime getLocalDateTimeFromChosenPasteExpiration(PasteExpiration expiration) {
        LocalDateTime offsetDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        return offsetDateTime.plusMinutes(expiration.getOffsetMin());
    }
}
