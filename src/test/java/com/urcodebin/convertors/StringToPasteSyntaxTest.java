package com.urcodebin.convertors;

import com.urcodebin.enumerators.PasteSyntax;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class StringToPasteSyntaxTest {
    private final Converter<String, PasteSyntax> converter = new StringToPasteSyntax();

    @Test
    public void convertToSyntaxHighlightWorks() {
        Assert.assertNull(converter.convertToModel(null, new ValueContext())
                .getOrThrow(AssertionError::new));

        Arrays.stream(PasteSyntax.values())
            .forEach(pasteSyntax -> {
                String val = pasteSyntax.getValue();
                Assert.assertEquals(pasteSyntax, converter.convertToModel(val ,new ValueContext())
                            .getOrThrow(AssertionError::new));
            });
    }

    @Test
    public void convertToStringWorks() {
        Arrays.stream(PasteSyntax.values())
            .forEach(pasteSyntax -> {
                String expectedResult = pasteSyntax.getValue();
                Assert.assertEquals(expectedResult, converter.convertToPresentation(pasteSyntax, new ValueContext()));
            });
    }
}
