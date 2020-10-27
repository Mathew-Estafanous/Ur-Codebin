package com.urcodebin.convertors;

import com.urcodebin.enumerators.SyntaxHighlight;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class StringToSyntaxHighlightTest {
    private final Converter<String, SyntaxHighlight> converter = new StringToSyntaxHighlight();

    @Test
    public void convertToSyntaxHighlightWorks() {
        Assert.assertNull(converter.convertToModel(null, new ValueContext())
                .getOrThrow(AssertionError::new));

        Arrays.stream(SyntaxHighlight.values())
            .forEach(syntaxHighlight -> {
                String val = syntaxHighlight.getValue();
                Assert.assertEquals(syntaxHighlight, converter.convertToModel(val ,new ValueContext())
                            .getOrThrow(AssertionError::new));
            });
    }

    @Test
    public void convertToStringWorks() {
        Arrays.stream(SyntaxHighlight.values())
            .forEach(syntaxHighlight -> {
                String expectedResult = syntaxHighlight.getValue();
                Assert.assertEquals(expectedResult, converter.convertToPresentation(syntaxHighlight, new ValueContext()));
            });
    }
}
