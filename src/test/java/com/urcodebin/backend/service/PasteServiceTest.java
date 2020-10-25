package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.repository.CodePasteRepository;
import com.urcodebin.enumerators.SyntaxHighlight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PasteServiceTest {

    @Mock
    CodePasteRepository codePasteRepository;

    @InjectMocks
    PasteServiceImpl pasteService;

    CodePaste codePaste;

    @Before
    public void setup() {
        codePaste = new CodePaste();
        codePaste.setPasteExpiration(LocalDateTime.now());
        codePaste.setPasteTitle("Test Paste Title");
        codePaste.setSyntaxHighlighting(SyntaxHighlight.JAVA);
    }

    @Test
    public void creatingNewPasteShouldReturnNonNull() {
        when(codePasteRepository.save(any(CodePaste.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        CodePaste paste = pasteService.createNewPaste(codePaste);

        Assert.assertNotNull(paste);
    }

    @Test
    public void findByPasteIdServiceShouldWorkWithValidGivenId() {
        when(codePasteRepository.findById(codePaste.getPasteId())).thenReturn(Optional.of(codePaste));
        Optional<CodePaste> foundCodePaste = pasteService.findByPasteId(codePaste.getPasteId());

        Assert.assertTrue(foundCodePaste.isPresent());
        Assert.assertEquals(codePaste, foundCodePaste.get());
    }
}
