package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteExpiration;
import com.urcodebin.helpers.PasteExpirationHelper;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDateTime;

public class PasteExpirationToLocalDateTime implements Converter<PasteExpiration, LocalDateTime> {
    @Override
    public Result<LocalDateTime> convertToModel(PasteExpiration pasteExpiration, ValueContext valueContext) {
        if(pasteExpiration == null)
            return Result.ok(null);
        else {
            LocalDateTime dateTime = PasteExpirationHelper.getLocalDateTimeFromChosenPasteExpiration(pasteExpiration);
            return Result.ok(dateTime);
        }
    }

    @Override
    public PasteExpiration convertToPresentation(LocalDateTime s, ValueContext valueContext) {
        return PasteExpiration.TENMINUTES;
    }
}
