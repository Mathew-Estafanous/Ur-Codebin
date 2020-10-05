package com.urcodebin.enumerators;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class StringToPasteExpiration implements Converter<String, PasteExpiration> {
    @Override
    public Result<PasteExpiration> convertToModel(String s, ValueContext valueContext) {
        if(s == null)
            return Result.ok(null);
        else
            return Result.ok(PasteExpiration.valueOf(s));
    }

    @Override
    public String convertToPresentation(PasteExpiration pasteExpiration, ValueContext valueContext) {
        return (pasteExpiration != null)? pasteExpiration.getValue(): null;
    }
}
