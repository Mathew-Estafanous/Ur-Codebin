package com.urcodebin.convertors;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTime implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Result<LocalDateTime> convertToModel(String s, ValueContext valueContext) {
        if(s == null)
            return Result.ok(null);
        else {
            LocalDateTime parsedDateTime = LocalDateTime.parse(s, parseFormat);
            return Result.ok(parsedDateTime);
        }
    }

    @Override
    public String convertToPresentation(LocalDateTime localDateTime, ValueContext valueContext) {
        return (localDateTime != null)? localDateTime.format(parseFormat): null;
    }
}
