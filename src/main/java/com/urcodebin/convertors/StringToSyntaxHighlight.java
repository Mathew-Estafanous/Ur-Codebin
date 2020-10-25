package com.urcodebin.convertors;

import com.urcodebin.enumerators.SyntaxHighlight;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.Arrays;

public class StringToSyntaxHighlight implements Converter<String, SyntaxHighlight> {

    @Override
    public Result<SyntaxHighlight> convertToModel(String s, ValueContext valueContext) {
        if(s == null)
            return Result.ok(null);
        else {
            return Result.ok(getByEnumValue(s));
        }
    }

    @Override
    public String convertToPresentation(SyntaxHighlight syntaxHighlight, ValueContext valueContext) {
        return (syntaxHighlight != null)? syntaxHighlight.getValue(): null;
    }

    private SyntaxHighlight getByEnumValue(String s) {
        return Arrays.stream(SyntaxHighlight.values())
                .filter(enumVal -> enumVal.getValue().equals(s))
                .findFirst()
                .orElse(SyntaxHighlight.NONE);
    }
}
