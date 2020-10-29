package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.repository.CodePasteRepository;
import com.urcodebin.enumerators.SyntaxHighlight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpirationServiceTest {

    @Mock
    CodePasteRepository codePasteRepository;

    @InjectMocks
    ExpirationServiceImpl expirationService;

    List<CodePaste> listOfExpired = new ArrayList<>();

    @Before
    public void setup() {
        CodePaste tempPaste = new CodePaste();
        tempPaste.setPasteExpiration(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
                .minusHours(1));
        tempPaste.setPasteTitle("Test Paste Title");
        tempPaste.setSyntaxHighlighting(SyntaxHighlight.JAVA);
        listOfExpired.add(tempPaste);
    }

    @Test
    public void deletingAllExpiredCode() {
        when(codePasteRepository.findByPasteExpirationDateLessThan(any(LocalDateTime.class)))
                .thenReturn(listOfExpired);
        List<CodePaste> deletedPastes = expirationService.deleteExpiredCodePastes();
        Assert.assertEquals(listOfExpired, deletedPastes);
    }
}
