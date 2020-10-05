package com.urcodebin.enumerators;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class StringToSyntaxHighlight implements Converter<String, SyntaxHighlight> {

    @Override
    public Result<SyntaxHighlight> convertToModel(String s, ValueContext valueContext) {
        if(s == null)
            return Result.ok(null);
        else {
            return Result.ok(SyntaxHighlight.valueOf(s));
        }
    }

    @Override
    public String convertToPresentation(SyntaxHighlight syntaxHighlight, ValueContext valueContext) {
        return (syntaxHighlight != null)? syntaxHighlight.getValue(): null;
    }
}
