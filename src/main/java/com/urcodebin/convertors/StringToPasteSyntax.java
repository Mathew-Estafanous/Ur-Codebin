package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteSyntax;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.Arrays;

public class StringToPasteSyntax implements Converter<String, PasteSyntax> {

    @Override
    public Result<PasteSyntax> convertToModel(String s, ValueContext valueContext) {
        if(s == null)
            return Result.ok(null);
        else {
            return Result.ok(getByEnumValue(s));
        }
    }

    @Override
    public String convertToPresentation(PasteSyntax pasteSyntax, ValueContext valueContext) {
        return (pasteSyntax != null)? pasteSyntax.getStringValue(): null;
    }

    private PasteSyntax getByEnumValue(String s) {
        return Arrays.stream(PasteSyntax.values())
                .filter(enumVal -> enumVal.getStringValue().equals(s))
                .findFirst()
                .orElse(PasteSyntax.NONE);
    }
}
