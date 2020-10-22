package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteExpiration;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class StringToPasteExpiration implements Converter<PasteExpiration, String> {
    @Override
    public Result<String> convertToModel(PasteExpiration pasteExpiration, ValueContext valueContext) {
        return Result.ok(pasteExpiration.getValue());
    }

    @Override
    public PasteExpiration convertToPresentation(String s, ValueContext valueContext) {
        return PasteExpiration.valueOf(s);
    }
}
