package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.enumerators.PasteVisibility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CodePasteRepositoryTest {

    @Autowired
    private CodePasteRepository codePasteRepository;

    private UUID javaWindowCodePasteUUID;
    private UUID csharpCodePasteUUID;
    private LocalDateTime testDateTime;

    @Before
    public void setup() {
        javaWindowCodePasteUUID = UUID.fromString("1f45a7b3-e179-4d03-994f-9ac550ec5e4d");
        csharpCodePasteUUID = UUID.fromString("2f6aff3d-105c-41aa-8b05-646d1ba94cba");
        testDateTime = LocalDateTime.parse("2020-10-24T18:00:00");
    }

    @Test
    public void repositoryProperlyFindsAValidCodePasteById() {
        Optional<CodePaste> foundPaste = codePasteRepository.findById(javaWindowCodePasteUUID);
        Assert.assertTrue(foundPaste.isPresent());
        Assert.assertEquals(javaWindowCodePasteUUID, foundPaste.get().getPasteId());
    }

    @Test
    public void repositoryFindsCorrectCodePasteByTitle() {
        List<CodePaste> foundSecondPaste = codePasteRepository
                .findByPasteTitleContainsAndPasteVisibilityIs("C#", PasteVisibility.PUBLIC);
        Assert.assertEquals(foundSecondPaste.get(0).getPasteId(), csharpCodePasteUUID);
    }

    @Test
    public void repositoryFindsAllCodePastesWhenTitleIsEmpty() {
        List<CodePaste> foundAllPastes = codePasteRepository
                .findByPasteTitleContainsAndPasteVisibilityIs("", PasteVisibility.PUBLIC);
        Assert.assertEquals(foundAllPastes.size(), codePasteRepository.count());
    }

    @Test
    public void repositoryFindsAllExpiredCodePastes() {
        List<CodePaste> expiredPastes = codePasteRepository.findByPasteExpirationDateLessThan(testDateTime);
        boolean areAllPastesExpired = expiredPastes.stream()
                .allMatch(codePaste -> codePaste.getPasteExpiration().isBefore(testDateTime));
        Assert.assertTrue(areAllPastesExpired);
    }
}
